/*
 * 
 * TOM - To One Matching Expander
 * 
 * Copyright (c) 2000-2007, INRIA
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
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/

package tom.engine.expander;

import java.util.*;
import java.util.logging.Level;

import tom.engine.exception.TomRuntimeException;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;

import tom.engine.adt.tominstruction.types.constraintinstructionlist.concConstraintInstruction;
import tom.engine.adt.tomslot.types.slotlist.concSlot;
import tom.engine.adt.tomsignature.types.tomvisitlist.concTomVisit;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.tools.ASTFactory;
import tom.engine.tools.TomGenericPlugin;
import tom.engine.tools.Tools;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;

import tom.library.sl.*;

/**
 * The Expander plugin.
 */
public class Expander extends TomGenericPlugin {

  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../../library/mapping/java/sl.tom }
  %include { ../../library/mapping/java/util/types/Collection.tom}
  %include { ../../library/mapping/java/util/types/Map.tom}
  
  %typeterm Expander {
    implement { Expander }
    is_sort(t) { ($t instanceof Expander) }
  }

  %op Strategy ChoiceTopDown(s1:Strategy) {
    make(v) { (`mu(MuVar("x"),ChoiceId(v,All(MuVar("x"))))) }
  }

  /** some output suffixes */
  public static final String COMPILED_SUFFIX = ".tfix.compiled";

  /** the declared options string*/
  public static final String DECLARED_OPTIONS = "<options>" +
    "<boolean name='compile' altName='' description='Expander (activated by default)' value='true'/>" +
    "<boolean name='autoDispatch' altName='ad' description='The content of \"visitor_fwd\" is ignored, and a dispatch mechanism is automatically generated in %strategy ' value='false'/>" +
    "</options>";
  
  private static final String basicStratName = "tom.library.sl.BasicStrategy";
  private static final TomType objectType = `TomTypeAlone("Object");
  private static final TomType introspectorType = `TomTypeAlone("tom.library.sl.Introspector");
  // introspector argument of visitLight
  private static final TomTerm introspectorVar = `Variable(concOption(),Name("introspector"),introspectorType,concConstraint());
    
  /** if the flag is true, the class generated from %strategy inherits from BasicStrategy and handles the dispatch*/
  private static boolean autoDispatch = false;

  /** unicity var counter */
  private static int absVarNumber;

  /** Constructor */
  public Expander() {
    super("Expander");
  }

  public void run() {
    long startChrono = System.currentTimeMillis();
    boolean intermediate = getOptionBooleanValue("intermediate");    
    try {
      // reinit absVarNumber to generate reproducible output
      absVarNumber = 0;
      autoDispatch = getOptionBooleanValue("autoDispatch");
      TomTerm expandedTerm = (TomTerm) `Expand(this).visitLight((TomTerm)getWorkingTerm());
     // verbose
      getLogger().log(Level.INFO, TomMessage.tomCompilationPhase.getMessage(),
          new Integer((int)(System.currentTimeMillis()-startChrono)) );
      setWorkingTerm(expandedTerm);
      if(intermediate) {
        Tools.generateOutput(getStreamManager().getOutputFileName() + COMPILED_SUFFIX, (TomTerm)getWorkingTerm());
      }
    } catch (Exception e) {
      getLogger().log(Level.SEVERE, TomMessage.exceptionMessage.getMessage(),
          new Object[]{getStreamManager().getInputFileName(), "Expander", e.getMessage()} );
      e.printStackTrace();
    }
  }

  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(Expander.DECLARED_OPTIONS);
  }

  /*
   * Expand:
   * replaces BuildReducedTerm by BuildList, BuildArray or BuildTerm
   *
   * abstract list-matching patterns
   */

  %op Strategy Expand(compiler:Expander){
    make(compiler) { `ChoiceTopDown(Expand_once(compiler)) }
  }

  %strategy Expand_once(compiler:Expander) extends `Identity(){
    visit TomTerm {
      BuildReducedTerm[TomTerm=var@(Variable|VariableStar)[]] -> {
        return `var;
      }

      BuildReducedTerm[TomTerm=RecordAppl[Option=optionList,NameList=(name@Name(tomName)),Slots=termArgs],AstType=astType] -> {
        TomSymbol tomSymbol = compiler.symbolTable().getSymbolFromName(`tomName);
        SlotList newTermArgs = (SlotList) `Expand_makeTerm(compiler).visitLight(`termArgs);
        TomList tomListArgs = TomBase.slotListToTomList(newTermArgs);

        if(TomBase.hasConstant(`optionList)) {
          return `BuildConstant(name);
        } else if(tomSymbol != null) {
          if(TomBase.isListOperator(tomSymbol)) {
            return ASTFactory.buildList(`name,tomListArgs,compiler.symbolTable());
          } else if(TomBase.isArrayOperator(tomSymbol)) {
            return ASTFactory.buildArray(`name,tomListArgs,compiler.symbolTable());
          } else if(TomBase.isDefinedSymbol(tomSymbol)) {
            return `FunctionCall(name,TomBase.getSymbolCodomain(tomSymbol),tomListArgs);
          } else {
            String moduleName = TomBase.getModuleName(`optionList);
            if(moduleName==null) {
              moduleName = TomBase.DEFAULT_MODULE_NAME;
            }
            return `BuildTerm(name,tomListArgs,moduleName);
          }
        } else {
          return `FunctionCall(name,astType,tomListArgs);
        }

      }

    } // end match

    visit Instruction {
      Match(constraintInstructionList, matchOptionList)  -> {
        Option orgTrack = TomBase.findOriginTracking(`matchOptionList);
        ConstraintInstructionList newConstraintInstructionList = `concConstraintInstruction();
        ConstraintList negativeConstraint = `concConstraint();        
        for(ConstraintInstruction constraintInstruction:(concConstraintInstruction)`constraintInstructionList) {
          /*
           * the call to Expand performs the recursive expansion
           * of nested match constructs
           */
          ConstraintInstruction newConstraintInstruction = (ConstraintInstruction) `Expand(compiler).visitLight(constraintInstruction);

matchBlock: {
              %match(newConstraintInstruction) {
                ConstraintInstruction(constraint,actionInst, option) -> {
                  Instruction newAction = `actionInst;
                  /* expansion of RawAction into TypedAction */
                  %match(actionInst) {
                    RawAction(x) -> {
                      newAction=`TypedAction(If(TrueTL(),x,Nop()),constraint,negativeConstraint);
                    }
                  }
                  negativeConstraint = `concConstraint(negativeConstraint*,constraint);

                  /* generate equality checks */
                  newConstraintInstruction = `ConstraintInstruction(constraint,newAction, option);
                  /* do nothing */
                  break matchBlock;
                }

                _ -> {
                  System.out.println("Expander.Expand: strange ConstraintInstruction: " + `newConstraintInstruction);
                  throw new TomRuntimeException("Expander.Expand: strange ConstraintInstruction: " + `newConstraintInstruction);
                }
              }
            } // end matchBlock

            newConstraintInstructionList = `concConstraintInstruction(newConstraintInstructionList*,newConstraintInstruction);
        }

        Instruction newMatch = `Match(newConstraintInstructionList, matchOptionList);
        return newMatch;
      }

    } // end visit

    visit Declaration {
      Strategy(name,extendsTerm,visitList,orgTrack) -> {
        //System.out.println("extendsTerm = " + `extendsTerm);
        DeclarationList l = `concDeclaration();//represents compiled Strategy
        TomForwardType visitorFwd = null;             
        HashMap<TomType,String> dispatchInfo = new HashMap<TomType,String>(); // contains info needed for dispatch
        for(TomVisit visit:(concTomVisit)`visitList) {
          TomList subjectListAST = `concTomTerm();
          %match(visit) {
            VisitTerm(vType@Type[TomType=ASTTomType(type)],constraintInstructionList,_) -> {              
              if(visitorFwd == null) {//first time in loop
                visitorFwd = compiler.symbolTable().getForwardType(`type);//do the job only once
              }
              TomTerm arg = `Variable(concOption(),Name("tom__arg"),vType,concConstraint());//arg subjectList
              subjectListAST = `concTomTerm(subjectListAST*,arg,introspectorVar);
              String funcName = "visit_" + `type;//function name
              Instruction matchStatement = `Match(constraintInstructionList, concOption(orgTrack));
              //return default strategy.visitLight(arg)
              // FIXME: put superclass keyword in backend, in c# 'super' is 'base'
              Instruction returnStatement = null;
              if (compiler.autoDispatch) {
                returnStatement = `Return(FunctionCall(Name("_" + funcName),vType,subjectListAST));
              } else {
                returnStatement = `Return(FunctionCall(Name("super."+ funcName),vType,subjectListAST));
              }
              InstructionList instructions = `concInstruction(matchStatement, returnStatement);
              l = `concDeclaration(l*,MethodDef(Name(funcName),concTomTerm(arg,introspectorVar),vType,TomTypeAlone("tom.library.sl.VisitFailure"),AbstractBlock(instructions)));
              if (compiler.autoDispatch) {
                dispatchInfo.put(`vType,funcName);
              }
            }              
          }
        }
        if ( compiler.autoDispatch ) { 
         /*
          * // Generates the following dispatch mechanism
          *           
          * public Visitable visitLight(Visitable v) throws VisitFailure {
          *       if (is_sort(v, Term1))
          *               return this.visit_Term1((Term1) v);
          *       .....................        
          *       if (is_sort(v, Termn))
          *               return this.visit_Termn((Termn) v);               
          *       return any.visitLight(v);
          * }
          *
          * public Term1 _visit_Term1(Term1 arg) throws VisitFailure {
          *        if (environment != null) {
          *                return (Term1) any.visit(environment);
          *        } else {
          *                return (Term1) any.visitLight(arg);
          *        }
          * }
          * ..............
          * public Termn _visit_Termn(Termn arg) throws VisitFailure {
          *        if (environment != null) {
          *                return (Termn) any.visit(environment);
          *        } else {
          *                return (Termn) any.visitLight(arg);
          *        }
          * }
          *
          */        
          visitorFwd = `TLForward(Expander.basicStratName);         
          TomTerm vVar = `Variable(concOption(),Name("v"),objectType,concConstraint());// v argument of visitLight
          InstructionList ifList = `concInstruction(); // the list of ifs in visitLight
          Expression testEnvNotNull = null;
          // generate the visitLight
          for(TomType type:dispatchInfo.keySet()){
            TomList funcArg = `concTomTerm(ExpressionToTomTerm(Cast(type,TomTermToExpression(vVar))),introspectorVar);            
            Instruction returnStatement = `Return(FunctionCall(Name(dispatchInfo.get(type)),type,funcArg));
            Instruction ifInstr = `If(IsSort(type,vVar),returnStatement,Nop());
            ifList = `concInstruction(ifList*,ifInstr);
            // generate the _visit_Term
            TomTerm arg = `Variable(concOption(),Name("arg"),type,concConstraint());
            TomTerm environmentVar = `Variable(concOption(),Name("environment"),EmptyType(),concConstraint());
            Instruction return1 = `Return(ExpressionToTomTerm(Cast(type,TomInstructionToExpression(TargetLanguageToInstruction(ITL("any.visit(environment,introspector)"))))));
            Instruction return2 = `Return(ExpressionToTomTerm(Cast(type,TomInstructionToExpression(TargetLanguageToInstruction(ITL("any.visitLight(arg,introspector)"))))));
            testEnvNotNull = `Negation(EqualTerm(compiler.getStreamManager().getSymbolTable().getBooleanType(),
                environmentVar,ExpressionToTomTerm(Bottom(TomTypeAlone("Object")))));
            Instruction ifThenElse = `If(testEnvNotNull,return1,return2);
            l = `concDeclaration(l*,MethodDef(
                                      Name("_" + dispatchInfo.get(type)),
                                      concTomTerm(arg,introspectorVar),
                                      type,
                                      TomTypeAlone("tom.library.sl.VisitFailure"),
                                      ifThenElse));
          }
          ifList = `concInstruction(ifList*,              
                      If(testEnvNotNull,
                          Return(InstructionToTomTerm(TargetLanguageToInstruction(ITL("any.visit(environment,introspector)")))),
                          Return(InstructionToTomTerm(TargetLanguageToInstruction(ITL("any.visitLight(v,introspector)"))))));
          Declaration visitLightDeclaration = `MethodDef(
                                      Name("visitLight"),
                                      concTomTerm(vVar,introspectorVar),
                                      objectType,
                                      TomTypeAlone("tom.library.sl.VisitFailure"),
                                      AbstractBlock(ifList));
          l = `concDeclaration(l*,visitLightDeclaration);
        }// end if autoDispatch
        return (Declaration) `Expand(compiler).visitLight(`Class(name,visitorFwd,extendsTerm,AbstractDecl(l)));
      }        
    }//end visit Declaration
  } // end strategy

  %op Strategy Expand_makeTerm(compiler:Expander){
     make(compiler) { `ChoiceTopDown(Expand_makeTerm_once(compiler)) }
  }

  %strategy Expand_makeTerm_once(compiler:Expander) extends `Identity()  {
    visit TomTerm {
      t -> {return (TomTerm) `Expand(compiler).visitLight(`BuildReducedTerm(t,compiler.getTermType(t)));}
    }
  }

  private TomTerm abstractPattern(TomTerm subject, ArrayList abstractedPattern, ArrayList introducedVariable)  {
    TomTerm abstractedTerm = subject;
    %match(subject) {
      RecordAppl[NameList=(Name(tomName),_*), Slots=arguments] -> {
        TomSymbol tomSymbol = symbolTable().getSymbolFromName(`tomName);

        SlotList newArgs = `concSlot();
        if(TomBase.isListOperator(tomSymbol) || TomBase.isArrayOperator(tomSymbol)) {
          for(Slot elt:(concSlot)`arguments) {
            TomTerm newElt = elt.getAppl();
            %match(newElt) {
              appl@RecordAppl[NameList=(Name(tomName2),_*)] -> {
                /*
                 * we no longer abstract syntactic subterm
                 * they are compiled by the KernelExpander
                 */

                //System.out.println("Abstract: " + appl);
                TomSymbol tomSymbol2 = symbolTable().getSymbolFromName(`tomName2);
                if(TomBase.isListOperator(tomSymbol2) || TomBase.isArrayOperator(tomSymbol2)) {
                  TomType type2 = tomSymbol2.getTypesToType().getCodomain();
                  abstractedPattern.add(`appl);

                  TomNumberList path = `concTomNumber();
                  absVarNumber++;
                  path = `concTomNumber(path*,AbsVar(absVarNumber));

                  TomTerm newVariable = `Variable(concOption(),PositionName(path),type2,concConstraint());

                  //System.out.println("newVariable = " + newVariable);

                  introducedVariable.add(newVariable);
                  newElt = newVariable;
                }
              }
            }
            newArgs = `concSlot(newArgs*,PairSlotAppl(elt.getSlotName(),newElt));
          }
        } else {
          newArgs = TomBase.mergeTomListWithSlotList(abstractPatternList(TomBase.slotListToTomList(`arguments),abstractedPattern,introducedVariable),`arguments);
        }
        abstractedTerm = subject.setSlots(newArgs);
      }
    } // end match
    return abstractedTerm;
  }

  private TomList abstractPatternList(TomList subjectList, ArrayList abstractedPattern, ArrayList introducedVariable)  {
    %match(subjectList) {
      concTomTerm() -> { return subjectList; }
      concTomTerm(head,tail*) -> {
        TomTerm newElt = abstractPattern(`head,abstractedPattern,introducedVariable);
        TomList tl = abstractPatternList(`tail,abstractedPattern,introducedVariable);
        return `concTomTerm(newElt,tl*);
      }
    }
    throw new TomRuntimeException("abstractPatternList: " + subjectList);
  }  
  
  /*
   * add a prefix (tom_) to back-quoted variables which comes from the lhs
   */
  %strategy findRenameVariable(context:Collection) extends `Identity() {
    visit TomTerm {
      var@(Variable|VariableStar)[AstName=astName@Name(name)] -> {
        if(context.contains(`astName)) {          
          return `var.setAstName(`Name(ASTFactory.makeTomVariableName(name)));
        }
      }
    }

    visit Instruction {
      CompiledPattern(patternList,instruction) -> {
        // only variables found in LHS have to be renamed (this avoids that the JAVA ones are renamed)
        Collection newContext = new ArrayList();
        `TopDown(CollectLHSVars(newContext)).visitLight(`patternList);        
        newContext.addAll(context);
        return (Instruction)`TopDown(findRenameVariable(newContext)).visitLight(`instruction);
      }
    }  
  }  
  
  %strategy CollectLHSVars(Collection bag) extends Identity() {
    visit Constraint {
      MatchConstraint(p,_) -> {        
        Map map = TomBase.collectMultiplicity(`p);
        Collection newContext = new HashSet(map.keySet());
        bag.addAll(newContext);
      }
    }
  }
}
