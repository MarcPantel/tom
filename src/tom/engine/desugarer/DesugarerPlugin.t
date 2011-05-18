/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2011, INPL, INRIA
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

package tom.engine.desugarer;

import java.util.Map;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.engine.exception.TomRuntimeException;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomname.types.tomname.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.code.types.*;
import tom.engine.adt.tomtype.types.tomtypelist.concTomType;
import tom.engine.adt.tomterm.types.tomlist.concTomTerm;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.tools.ASTFactory;
import tom.engine.tools.TomGenericPlugin;
import tom.engine.tools.Tools;
import tom.engine.tools.SymbolTable;
import tom.engine.xml.Constants;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import aterm.ATerm;

import tom.library.sl.*;

/**
 * The Desugarer plugin.
 * Perform syntax expansion and more.
 *
 * replaces  _  by a fresh variable _* by a fresh varstar 
 * replaces 'TermAppl' by its 'RecordAppl' form
 * replaces 'XMLAppl' by its 'RecordAppl' form
 *   when no slotName exits, the position becomes the slotName
 */
public class DesugarerPlugin extends TomGenericPlugin {

  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../../library/mapping/java/sl.tom }
  %typeterm DesugarerPlugin { implement { tom.engine.desugarer.DesugarerPlugin }}

  private static Logger logger = Logger.getLogger("tom.engine.desugarer.DesugarerPlugin");
  private SymbolTable symbolTable;
  private int freshCounter = 0;

  // FIXME : generate truly fresh variables
  private TomName getFreshVariable() {
    freshCounter++;
    return `Name("_f_r_e_s_h_v_a_r_" + freshCounter);
  }

  public SymbolTable getSymbolTable() {
    return this.symbolTable;
  } 

  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  public DesugarerPlugin() {
    super("DesugarerPlugin");
  }

  public void run(Map informationTracker) {
    long startChrono = System.currentTimeMillis();
    try {
      setSymbolTable(getStreamManager().getSymbolTable());
      updateSymbolTable();
      Code code = (Code) getWorkingTerm();

      // replace underscores by fresh variables
      code = `TopDown(DesugarUnderscore(this)).visitLight(code);

      // replace TermAppl and XmlAppl by RecordAppl
      code = `TopDownIdStopOnSuccess(replaceXMLApplTomSyntax(this)).visitLight(code);
      code = `TopDownIdStopOnSuccess(replaceTermApplTomSyntax(this)).visitLight(code);

      setWorkingTerm(code);      

      // verbose
      TomMessage.info(logger,null,0,TomMessage.tomDesugaringPhase,
          Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
    } catch (Exception e) {
      TomMessage.error(logger,
          getStreamManager().getInputFileName(), 0,
          TomMessage.exceptionMessage, e.getMessage());
      e.printStackTrace();
      return;
    }
  }

  /* 
   * replaces  _  by a fresh variable _* by a fresh varstar    
   */
  %strategy DesugarUnderscore(desugarer:DesugarerPlugin) extends Identity() {
    visit TomTerm {
      Variable[Options=optionList,AstName=EmptyName(),AstType=ty,Constraints=constr] -> {
        return `Variable(optionList,desugarer.getFreshVariable(),ty,constr);
      }
      VariableStar[Options=optionList,AstName=EmptyName(),AstType=ty,Constraints=constr] -> {
        return `VariableStar(optionList,desugarer.getFreshVariable(),ty,constr);
      }
    }
  }

  /**
   * updateSymbol is called before a first syntax expansion phase
   * this phase updates the symbolTable 
   * this is performed by recursively traversing each symbol
   * - default IsFsymDecl and MakeDecl are added
   * - TermAppl are transformed into RecordAppl
   */
  public void updateSymbolTable() {
    for(String tomName:getSymbolTable().keySymbolIterable()) {
      TomSymbol tomSymbol = getSymbolFromName(tomName);
      /*
       * add default IsFsymDecl unless it is a builtin type
       * add default IsFsymDecl and MakeDecl unless:
       *  - it is a builtin type
       *  - another option (if_sfsym, get_slot, etc) is already defined for this operator
       */
      if(!getSymbolTable().isBuiltinType(TomBase.getTomType(TomBase.getSymbolCodomain(tomSymbol)))) {
        tomSymbol = addDefaultMake(tomSymbol);
        tomSymbol = addDefaultIsFsym(tomSymbol);
      }
      try {
        tomSymbol = `TopDownIdStopOnSuccess(replaceTermApplTomSyntax(this)).visitLight(tomSymbol);
      } catch(tom.library.sl.VisitFailure e) {
        System.out.println("should not be there");
      }
      //System.out.println("symbol = " + tomSymbol);
      getSymbolTable().putSymbol(tomName,tomSymbol);
    }
  }

  private TomSymbol addDefaultIsFsym(TomSymbol tomSymbol) {
    %match(tomSymbol) {
      Symbol[Options=concOption(_*,DeclarationToOption(IsFsymDecl[]),_*)] -> {
        return tomSymbol;
      }

      Symbol(name,t@TypesToType(_,codom),l,concOption(X1*,origin@OriginTracking(_,line,file),X2*)) -> {
        Declaration isfsym = `IsFsymDecl(name,BQVariable(concOption(OriginTracking(Name("t"),line,file)),Name("t"),codom),FalseTL(),OriginTracking(Name("is_fsym"),line,file));
        return `Symbol(name,t,l,concOption(X1*,origin,DeclarationToOption(isfsym),X2*));
      }
    }
    return tomSymbol;
  }

  private TomSymbol addDefaultMake(TomSymbol tomSymbol) {
    %match(tomSymbol) {
      Symbol[Options=concOption(_*,DeclarationToOption((MakeDecl|MakeEmptyList|MakeEmptyArray|MakeAddList|MakeAddArray|IsFsymDecl|GetImplementationDecl|GetSlotDecl|GetDefaultDecl|GetHeadDecl|GetTailDecl|IsEmptyDecl|GetElementDecl|GetSizeDecl)[]),_*)] -> {
        return tomSymbol;
      }
      Symbol(name,t@TypesToType(domain,codomain),l,concOption(X1*,origin@OriginTracking(_,line,file),X2*)) -> {
        //build variables for make
        BQTermList argsAST = `concBQTerm();
        int index = 0;
        for(TomType subtermType:(concTomType)`domain) {
          BQTerm variable = `BQVariable(concOption(),Name("t"+index),subtermType);
          argsAST = `concBQTerm(argsAST*,variable);
          index++;
        }
        BQTerm functionCall = `FunctionCall(name,codomain,argsAST);
        Declaration make = `MakeDecl(name,codomain,argsAST,BQTermToInstruction(functionCall),
            OriginTracking(Name("make"),line,file));
        return `Symbol(name,t,l,concOption(X1*,origin,DeclarationToOption(make),X2*));
      }
    }
    return tomSymbol;
  }

  /**
   * The 'replaceTermApplTomSyntax' phase replaces:
   * - each 'TermAppl' by its typed record form:
   *    placeholders are not removed
   *    slotName are attached to arguments
   */
  %strategy replaceTermApplTomSyntax(desugarer:DesugarerPlugin) extends Identity() {
    visit TomTerm {
      TermAppl[Options=optionList,NameList=nameList,Args=args,Constraints=constraints] -> {
        return desugarer.replaceTermAppl(`optionList,`nameList,`args,`constraints);
      }
    }
  }

  /**
   * The 'replaceXMLApplTomSyntax' phase replaces:
   * - each 'XMLAppl' by its typed record form:
   */
  %strategy replaceXMLApplTomSyntax(desugarer:DesugarerPlugin) extends Identity() {
    visit TomTerm {
      XMLAppl[Options=optionList,NameList=nameList,AttrList=list1,ChildList=list2,Constraints=constraints] -> {
        //System.out.println("replaceXML in:\n" + `subject);
        return desugarer.replaceXMLAppl(`optionList, `nameList, `list1, `list2,`constraints);
      }
    }
  }

  /**
   * Replace 'TermAppl' by its 'RecordAppl' form
   * when no slotName exits, the position becomes the slotName
   */
  protected TomTerm replaceTermAppl(OptionList option, TomNameList nameList, TomList args, ConstraintList constraints) {
    TomName headName = nameList.getHeadconcTomName();
    if(headName instanceof AntiName) {
      headName = ((AntiName)headName).getName();
    }
    String opName = headName.getString();
    TomSymbol tomSymbol = getSymbolFromName(opName);

    //System.out.println("replaceTermAppl: " + tomSymbol);
    //System.out.println("  nameList = " + nameList);

    /*
     * may be for constant patterns: f(1) for instance
     */
    if(tomSymbol==null && args.isEmptyconcTomTerm()) {
      return `RecordAppl(option,nameList,concSlot(),constraints);
    }

    SlotList slotList = `concSlot();
    if(opName.equals("") || tomSymbol==null || TomBase.isListOperator(tomSymbol) || TomBase.isArrayOperator(tomSymbol)) {
      for(TomTerm arg:(concTomTerm)args) {
        try {
          TomTerm subterm = `TopDownIdStopOnSuccess(replaceTermApplTomSyntax(this)).visitLight(arg);
          TomName slotName = `EmptyName();
          /*
           * we cannot optimize when subterm.isUnamedVariable
           * since it can be constrained
           */	  
          slotList = `concSlot(slotList*,PairSlotAppl(slotName,subterm));
        } catch(tom.library.sl.VisitFailure e) {
          System.out.println("should not be there");
        }
      }
    } else {
      PairNameDeclList pairNameDeclList = tomSymbol.getPairNameDeclList();
      for(TomTerm arg:(concTomTerm)args) {
        try {
          TomTerm subterm = `TopDownIdStopOnSuccess(replaceTermApplTomSyntax(this)).visitLight(arg);
          TomName slotName = pairNameDeclList.getHeadconcPairNameDecl().getSlotName();
          /*
           * we cannot optimize when subterm.isUnamedVariable
           * since it can be constrained
           */	  
          slotList = `concSlot(slotList*,PairSlotAppl(slotName,subterm));
          pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
        } catch(tom.library.sl.VisitFailure e) {
          System.out.println("should not be there");
        }
      }
    }
    return `RecordAppl(option,nameList,slotList,constraints);
  }

  /**
   * Replace 'XMLAppl' by its 'RecordAppl' form
   * when no slotName exits, the position becomes the slotName
   */
  protected TomTerm replaceXMLAppl(OptionList optionList, TomNameList nameList,
      TomList attrList, TomList childList, ConstraintList constraints) {
    boolean implicitAttribute = TomBase.hasImplicitXMLAttribut(optionList);
    boolean implicitChild     = TomBase.hasImplicitXMLChild(optionList);

    TomList newAttrList  = `concTomTerm();
    TomList newChildList = `concTomTerm();

    if(implicitAttribute) { newAttrList  = `concTomTerm(VariableStar(convertOriginTracking("_*",optionList),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint()),newAttrList*); }
    if(implicitChild)     { newChildList = `concTomTerm(VariableStar(convertOriginTracking("_*",optionList),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint()),newChildList*); }

    /*
     * the list of attributes should not be typed before the sort
     * the sortAttribute is extended to compare RecordAppl
     */

    attrList = sortAttributeList(attrList);

    /*
     * Attributes: go from implicit notation to explicit notation
     */
    Strategy typeStrategy = `TopDownIdStopOnSuccess(replaceXMLApplTomSyntax(this));
    for(TomTerm attr:(concTomTerm)attrList) {
      try {
        TomTerm newPattern = typeStrategy.visitLight(attr);
        newAttrList = `concTomTerm(newPattern,newAttrList*);
        if(implicitAttribute) {
          newAttrList = `concTomTerm(VariableStar(convertOriginTracking("_*",optionList),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint()),newAttrList*);
        }
      } catch(tom.library.sl.VisitFailure e) {
        System.out.println("should not be there");
      }
    }
    newAttrList = newAttrList.reverse();

    /*
     * Childs: go from implicit notation to explicit notation
     */
    for(TomTerm child:(concTomTerm)childList) {
      try {
        TomTerm newPattern = typeStrategy.visitLight(child);
        newChildList = `concTomTerm(newPattern,newChildList*);
        if(implicitChild) {
          if(newPattern.isVariableStar()) {
            // remove the previously inserted pattern
            newChildList = newChildList.getTailconcTomTerm();
            if(newChildList.getHeadconcTomTerm().isVariableStar()) {
              // remove the previously inserted star
              newChildList = newChildList.getTailconcTomTerm();
            }
            // re-insert the pattern
            newChildList = `concTomTerm(newPattern,newChildList*);
          } else {
            newChildList = `concTomTerm(VariableStar(convertOriginTracking("_*",optionList),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint()),newChildList*);
          }
        }
      } catch(tom.library.sl.VisitFailure e) {
        System.out.println("should not be there");
      }
    }
    newChildList = newChildList.reverse();

    /*
     * encode the name and put it into the table of symbols
     */
    TomNameList newNameList = `concTomName();
matchBlock: 
    {
      %match(nameList) {
        concTomName(Name("_")) -> {
          break matchBlock;
        }

        concTomName(_*,Name(name),_*) -> {
          newNameList = `concTomName(newNameList*,Name(ASTFactory.encodeXMLString(getSymbolTable(),name)));
        }
      }
    }

    /*
     * any XML node
     */
    TomTerm xmlHead;

    if(newNameList.isEmptyconcTomName()) {
      xmlHead = `Variable(concOption(),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint());
    } else {
      xmlHead = `TermAppl(convertOriginTracking(newNameList.getHeadconcTomName().getString(),optionList),newNameList,concTomTerm(),concConstraint());
    }
    try {
      SlotList newArgs = `concSlot(
          PairSlotAppl(Name(Constants.SLOT_NAME),
            typeStrategy.visitLight(xmlHead)),
          PairSlotAppl(Name(Constants.SLOT_ATTRLIST),
            typeStrategy.visitLight(TermAppl(convertOriginTracking("CONC_TNODE",optionList),concTomName(Name(Constants.CONC_TNODE)), newAttrList,concConstraint()))),
          PairSlotAppl(Name(Constants.SLOT_CHILDLIST),
            typeStrategy.visitLight(TermAppl(convertOriginTracking("CONC_TNODE",optionList),concTomName(Name(Constants.CONC_TNODE)), newChildList,concConstraint()))));

      TomTerm result = `RecordAppl(optionList,concTomName(Name(Constants.ELEMENT_NODE)),newArgs,constraints);

      //System.out.println("replaceXML out:\n" + result);
      return result;
    } catch(tom.library.sl.VisitFailure e) {
      //must never be executed
    TomTerm star = `VariableStar(convertOriginTracking("_*",optionList),getFreshVariable(),getSymbolTable().TYPE_UNKNOWN,concConstraint());
      return star;
    }
  }

  /* auxilliary methods */

  private TomList sortAttributeList(TomList attrList) {
    %match(attrList) {
      concTomTerm() -> { return attrList; }
      concTomTerm(X1*,e1,X2*,e2,X3*) -> {
        %match(e1, e2) {
          TermAppl[Args=concTomTerm(RecordAppl[NameList=concTomName(Name(name1))],_*)],
          TermAppl[Args=concTomTerm(RecordAppl[NameList=concTomName(Name(name2))],_*)] -> {
              if(`name1.compareTo(`name2) > 0) {
                return `sortAttributeList(concTomTerm(X1*,e2,X2*,e1,X3*));
              }
            }

          TermAppl[Args=concTomTerm(TermAppl[NameList=concTomName(Name(name1))],_*)],
          TermAppl[Args=concTomTerm(TermAppl[NameList=concTomName(Name(name2))],_*)] -> {
              if(`name1.compareTo(`name2) > 0) {
                return `sortAttributeList(concTomTerm(X1*,e2,X2*,e1,X3*));
              }
            }

          RecordAppl[Slots=concSlot(PairSlotAppl(slotName,RecordAppl[NameList=concTomName(Name(name1))]),_*)],
          RecordAppl[Slots=concSlot(PairSlotAppl(slotName,RecordAppl[NameList=concTomName(Name(name2))]),_*)] -> {
              if(`name1.compareTo(`name2) > 0) {
                return `sortAttributeList(concTomTerm(X1*,e2,X2*,e1,X3*));
              }
            }

          RecordAppl[Slots=concSlot(PairSlotAppl(slotName,TermAppl[NameList=concTomName(Name(name1))]),_*)],
          RecordAppl[Slots=concSlot(PairSlotAppl(slotName,TermAppl[NameList=concTomName(Name(name2))]),_*)] -> {
              if(`name1.compareTo(`name2) > 0) {
                return `sortAttributeList(concTomTerm(X1*,e2,X2*,e1,X3*));
              }
            }
        }
      }
    }
    return attrList;
  }

  private OptionList convertOriginTracking(String name,OptionList optionList) {
    Option originTracking = TomBase.findOriginTracking(optionList);
    %match(originTracking) {
      OriginTracking[Line=line, FileName=fileName] -> {
        return `concOption(OriginTracking(Name(name),line,fileName));
      }
    }
    System.out.println("Warning: no OriginTracking information");
    return `concOption();
  }
    
}
