/*
 *
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2009, INRIA
 * Nancy, France.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 * 
 * Cláudia Tavares  e-mail: Claudia.Tavares@loria.fr
 * Jean-Christophe Bach e-mail: Jeanchristophe.Bach@loria.fr
 *
 **/


//TODO

package tom.engine.typer;

import java.util.ArrayList;
import java.util.HashMap;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomname.types.tomname.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomtype.types.tomtypelist.concTomType;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomterm.types.tomlist.concTomTerm;
import tom.engine.adt.code.types.*;

import tom.engine.adt.typeconstraints.*;
import tom.engine.adt.typeconstraints.types.*;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.exception.TomRuntimeException;

import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;

import tom.library.sl.*;

public class NewKernelTyper {
  %include { ../../library/mapping/java/sl.tom}
//  %include { ../../library/mapping/java/util/types/Collection.tom}
  %include { ../adt/tomsignature/TomSignature.tom }
//  %include { ../../library/mapping/java/util/types/HashMap.tom}

  %typeterm NewKernelTyper {
    implement { NewKernelTyper }
    is_sort(t) { ($t instanceof NewKernelTyper) }
  }

//  %typeterm SubstitutionList { implement { HashMap<TomType,TomType> } }

  private SymbolTable symbolTable;
  private int freshTypeVarCounter = 0;
  // List for variables of pattern (match constraints)
  private TomList varPatternList;
  // List for variables of subject and of numeric constraints
  private BQTermList varList;
  // List for type constraints (for fresh type variables)
  private TypeConstraintList typeConstraints;
  // Set of pairs (freshVar,groundVar)
  private HashMap<TomType,TomType> substitutions;

  public NewKernelTyper() {
    super();
  }

  public SymbolTable getSymbolTable() {
    return this.symbolTable;
  }

  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  protected TomSymbol getSymbolFromName(String tomName) {
    return TomBase.getSymbolFromName(tomName, getSymbolTable());
  }

  protected TomSymbol getSymbolFromType(TomType type) {
    %match(type) {
      TypeWithSymbol[TomType=tomType, TlType=tlType] -> {
        return TomBase.getSymbolFromType(`Type(tomType,tlType), getSymbolTable()); 
      }
    }
    return TomBase.getSymbolFromType(type, getSymbolTable()); 
  }

  protected TomType getFreshTypeVar() {
    return `TypeVar(freshTypeVarCounter++);
  }

  protected void addConstraint(TypeConstraint constraint) {
    this.typeConstraints = `concTypeConstraint(constraint,typeConstraints*);
  }

//=====

  private void init(){
    varPatternList = `concTomTerm();
    varList = `concBQTerm();
    typeConstraints = `concTypeConstraint();
    substitutions = new HashMap<TomType,TomType>();
  }

  private Code inferTypeCode(Code code) {
    try {
//      Code result =
       return `TopDownStopOnSuccess(splitConstraintInstruction(this)).visitLight(code);
    } catch(tom.library.sl.VisitFailure e) {
      throw new TomRuntimeException("inferTypeCode: failure on " + code);
    }
  }

  %strategy splitConstraintInstruction(nkt:NewKernelTyper) extends Fail() {
    visit Instruction {
      Match(constraintInstructionList,options) -> {
        nkt.init();
        // Generate type constraints for a %match
        ConstraintInstructionList result = nkt.inferConstraintInstructionList(`constraintInstructionList);
        nkt.solveConstraints();
        result = nkt.replaceInConstraintInstructionList(`result);
        return `Match(result,options);
      }
    } 
  }

  // TODO: réécrire tout ici
  private ConstraintInstructionList inferConstraintInstructionList(ConstraintInstructionList cilist) {
    %match(cilist) {
      concConstraintInstruction() -> { return cilist; }
      concConstraintInstruction(head_cilist@ConstraintInstruction(constraint,action,option),tail_cilist*) -> {
        try {
          // Collect variables and type them with fresh type variables
          // Rename variables of pattern that already exist in varPList
          `TopDownCollect(CollectVars()).visitLight(`head_cilist);
          inferConstraint(`constraint);
          // Dans inferAction on peut appeler cette methode
          // inferConstraintInstructionList 
          inferInstruction(`action);

          ConstraintInstructionList typedTail = `inferConstraintInstructionList(tail_cilist);
          // old version
          //return `concConstraintInstruction(typedConstraint, typedTail);
          //TODO : temporary return in order to be  able to compile
          return `concConstraintInstruction(head_cilist, typedTail*);

        } catch(tom.library.sl.VisitFailure e) {
          throw new TomRuntimeException("inferConstraintInstructionList: failure on " + cilist);

        }
      }
    }
    // TODO
    return cilist;
  }

  //TODO
  %strategy CollectVars() extends Identity() {
    visit ConstraintInstruction {
    } 
  } 

  //TODO
  private void inferInstruction(Instruction action) {
    // addTypeConstraints
  }

  //TODO
  private void inferConstraint(Constraint constraint){
    // addTypeConstraints
  }
/*
   public void typeVariableList(TomSymbol symb, TomTypeList domain, TomType te, SlotList slist) {
    if(TomBase.isListOperator(`symb) || TomBase.isArrayOperator(`symb)) {
      %match(slist) {
        concSlot(VariableStar(_, n, t, _), s*) -> {
              //inferTerm(fs, t);
              addConstraint(`Equation(t,te));
              typeVariableList(symb, domain, te, s*);
              break;
            }
            concSlot(Variable(_, n, t, _), s*) -> {
              TomType tt = getFreshTypeVar();
              //inferTerm(,);
              addConstraint(`Equation(t,tt));
              typeVariableList();
              break;
            }
            concSlot(RecordAppl(_,concTomName(Name(tn),_*),_,_), s*) -> {
                //fdom = getSimpleDom();
                //syme = findSymblo(tn, fdom);
                //if(syme!= symb) {
                //tt = getFreshTypeVar();
                //addConstraint(`Equation(tt, fdom));
                //inferTerm(fs, tt);
                //} else {
                //  inferTerm(fs, te);
                //}
                typeVariableList(symb, domain, te, s);
                break;
            }
      }
    } else {
      while(!domain.isEmptyconcTomType()) {
        TomType tt = getFreshTypeVar();
        addconstraint(`Equation(tt, domain.getHeadconcTomType())); //Eq(tt, fdom)
        TomTerm farg = slist.getHeadconcSlot().getAppl();
        inferTerm(farg, tt);
        domain = domain.getTailconcTomType();
        slist = slist.getTailconcSlot();
      }
    } 
  }
*/
  /*
  public void inferNumMatch(TomTerm e1, TomTerm e2) {
    TomType t1 = getFreshTypeVar();
    TomType t2 = getFreshTypeVar();
    addConstraint(`Equation(t1,t2));
    inferTerm(e1, t1);
    inferTerm(e2, t2);
  }

  public void inferTerm(TomTerm e, TomType te) {
    %match(e) {
      (Variable|VariableStar)(_, _,type, _) -> { addConstraint(`Equation(type,te)); }
      RecordAppl(optionList, NameList(headName,_*), slotList, constraintList) -> {
        String name = null;
        TomType t;
        if(`(headName) instanceof AntiName) {
          name = ((AntiName)`headName).getName().getString();
        } else {
          name = ((TomName)`headName).getString();
        }
        TomSymbol symbol = getSymbolTable().getSymbolFromName(name);
        if(symbol!=null) {
          t = symbol.getTypesToType().getCodomain();
        } else {
          t = `EmptyType();
        }

        addConstraint(`Equation(t,te));

        concTomType tl;
        if(symbol!=null) {
          tl = symbol.getTypesToType().getDomain();
        } else {
          tl = `concTomType();
        }

        typeVariableList(symbol, tl, te, slotList);
      }
    }
  }

  public void inferCond() {}

  public void inferBlock() {
    //initGlobal()
    //collectSubjectVariables()
  }

  public void inferRule(Instruction instr, TomList localContext) {
    //  %match(instr) {
    //   Instruction(cond, action) -> {
    //initLocal()
    //inferCond()
    //propagate()
    // }
    //  }
  }

  public TypeConstraintList addConstraint(TypeConstraint constraint, TypeConstraintList
      constraintList) {
    return `concTypeConstraint(constraint,constraintList);
  }
*/
  // TODO
  private void solveConstraints() {
    // Add a new substitution in "substitutions"
  }

  // TODO
  private ConstraintInstructionList replaceInCode(ConstraintInstructionList
      cilist) {
    return cilist;
  }

  //TODO
  private ConstraintInstructionList replaceInConstraintInstructionList(ConstraintInstructionList cil) {
    return `cil;
  }


/*
  %strategy propagate(nkt:NewKernelTyper) extends Fail() {
  }
*/
} // NewKernelTyper
