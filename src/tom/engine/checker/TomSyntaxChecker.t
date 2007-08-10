/*
 *
 * TOM - To One Matching Compiler
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
 * Julien Guyon
 *
 **/

package tom.engine.checker;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.exception.TomRuntimeException;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomconstraint.types.constraint.*;
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

import tom.engine.xml.Constants;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import aterm.ATerm;
import tom.engine.tools.ASTFactory;

import tom.library.sl.*;

/**
 * The TomSyntaxChecker plugin.
 */
public class TomSyntaxChecker extends TomChecker {

  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../../library/mapping/java/sl.tom }
  %include { ../../library/mapping/java/util/types/Collection.tom}

  /** the declared options string */
  public static final String DECLARED_OPTIONS = "<options><boolean name='noSyntaxCheck' altName='' description='Do not perform syntax checking' value='false'/></options>";

  /** op and type declarator */
  private final static String OPERATOR    = "Operator";
  private final static String CONSTRUCTOR = "%op";
  private final static String OP_ARRAY    = "%oparray";
  private final static String OP_LIST     = "%oplist";
  private final static String TYPE        = "Type";
  private final static String TYPE_TERM   = "%typeterm";

  /** type function symbols */
  private final static String EQUALS      = "equals";
  private final static String GET_ELEMENT = "get_element";
  private final static String GET_SIZE    = "get_size";
  private final static String GET_HEAD    = "get_head";
  private final static String GET_TAIL    = "get_tail";
  private final static String IS_EMPTY    = "is_empty";
  /** operator function symbols */
  private final static String MAKE_APPEND = "make_append";
  private final static String MAKE_EMPTY  = "make_empty";
  private final static String MAKE_INSERT = "make_insert";
  private final static String MAKE        = "make";

  /** the list of already studied and declared Types */
  private  ArrayList alreadyStudiedTypes =  new ArrayList();
  /** the list of already studied and declared Symbol */
  private  ArrayList alreadyStudiedSymbols =  new ArrayList();

  /** List of expected functional declaration in each type declaration */
  private final static ArrayList TypeTermSignature =
    new ArrayList( Arrays.asList(new String[]{ TomSyntaxChecker.EQUALS }));

  /** Constructor */
  public TomSyntaxChecker() {
    super("TomSyntaxChecker");
    reinit();
  }

  /**
   * inherited from OptionOwner interface (plugin)
   */
  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(TomSyntaxChecker.DECLARED_OPTIONS);
  }

  protected void reinit() {
    super.reinit();
    alreadyStudiedTypes   = new ArrayList();
    alreadyStudiedSymbols = new ArrayList();
  }

  public void run() {       
    if(isActivated()) {
      strictType = !getOptionBooleanValue("lazyType");
      long startChrono = System.currentTimeMillis();
      try {
        // clean up internals
        reinit();
        // perform analyse
        try {
          `mu(MuVar("x"),Try(Sequence(checkSyntax(this),All(MuVar("x"))))).visitLight((TomTerm)getWorkingTerm());
        } catch(tom.library.sl.VisitFailure e) {
          System.out.println("strategy failed");
        }
        // verbose
        getLogger().log(Level.INFO, TomMessage.tomSyntaxCheckingPhase.getMessage(),
            new Integer((int)(System.currentTimeMillis()-startChrono)));
      } catch (Exception e) {
        getLogger().log(Level.SEVERE, TomMessage.exceptionMessage.getMessage(),
                        new Object[]{getClass().getName(),
                                     getStreamManager().getInputFileName(),
                                     e.getMessage() });
        e.printStackTrace();
      }
    } else {
      // syntax checker desactivated
      getLogger().log(Level.INFO, TomMessage.syntaxCheckerInactivated.getMessage());
    }
  }

  private boolean isActivated() {
    return !getOptionBooleanValue("noSyntaxCheck");
  }

  /**
   * Syntax checking entry point: Catch and verify all type and operator
   * declaration, Match instruction
   */
  %typeterm TomSyntaxChecker { 
    implement { TomSyntaxChecker }
    is_sort(t) { t instanceof TomSyntaxChecker }
  }
  %strategy checkSyntax(tsc:TomSyntaxChecker) extends `Identity() {
    visit Declaration {
      Strategy[VisitList = list,OrgTrack=origin] -> {
        if(`list.isEmptyconcTomVisit()) {
          %match(Option `origin) {
            OriginTracking[FileName=fileName,Line=line] -> { 
              tsc.messageError(`fileName,`line,TomMessage.emptyStrategy,new Object[]{});
            }
          }
          tsc.messageError("unknown",-1,TomMessage.emptyStrategy,new Object[]{});
        }
        /* STRATEGY MATCH STRUCTURE */
        tsc.verifyStrategy(`list);
      }
      // Types
      TypeTermDecl(Name(tomName), declarationList, orgTrack) -> {
        tsc.verifyTypeDecl(TomSyntaxChecker.TYPE_TERM, `tomName, `declarationList, `orgTrack);
        throw new tom.library.sl.VisitFailure();
      }
      // Symbols
      SymbolDecl(Name(tomName)) -> {
        tsc.verifySymbol(TomSyntaxChecker.CONSTRUCTOR, tsc.getSymbolFromName(`tomName));
        throw new tom.library.sl.VisitFailure();
      }
      ArraySymbolDecl(Name(tomName)) -> {
        tsc.verifySymbol(TomSyntaxChecker.OP_ARRAY, tsc.getSymbolFromName(`tomName));
        throw new tom.library.sl.VisitFailure();
      }
      ListSymbolDecl(Name(tomName))  -> {
        tsc.verifySymbol(TomSyntaxChecker.OP_LIST, tsc.getSymbolFromName(`tomName));
        throw new tom.library.sl.VisitFailure();
      }
    }

   visit Instruction {
     Match(constraintInstructionList, list) -> {
       /* TOM MATCH STRUCTURE */
       tsc.verifyMatch(`constraintInstructionList, `list);
     }
   }
  }

  // /////////////////////////////
  // TYPE DECLARATION CONCERNS //
  // ////////////////////////////
  private void verifyTypeDecl(String declType, String tomName, DeclarationList listOfDeclaration, Option typeOrgTrack) {
    currentTomStructureOrgTrack = typeOrgTrack;
    // ensure first definition
    verifyMultipleDefinition(tomName, declType, TYPE);
    // verify Macro functions
    ArrayList verifyList = new ArrayList(TomSyntaxChecker.TypeTermSignature);

    %match(DeclarationList listOfDeclaration) {
      (_*, d, _*) -> { // for each Declaration
        Declaration decl = `d;
        matchblock:{
          %match(Declaration decl) {
            // Common Macro functions
            EqualTermDecl(Variable[AstName=Name(name1)],Variable[AstName=Name(name2)],_, orgTrack) -> {
              `checkFieldAndLinearArgs(TomSyntaxChecker.EQUALS,verifyList,orgTrack,name1,name2, declType);
              break matchblock;
            }
            // List specific Macro functions
            GetHeadDecl[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.GET_HEAD,verifyList,orgTrack, declType);
              break matchblock;
            }
            GetTailDecl[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.GET_TAIL,verifyList,orgTrack, declType);
              break matchblock;
            }
            IsEmptyDecl[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.IS_EMPTY,verifyList,orgTrack, declType);
              break matchblock;
            }
            // Array specific Macro functions
            GetElementDecl[Variable=Variable[AstName=Name(name1)],Index=Variable[AstName=Name(name2)],OrgTrack=orgTrack] -> {
              `checkFieldAndLinearArgs(TomSyntaxChecker.GET_ELEMENT,verifyList,orgTrack,name1,name2, declType);
              break matchblock;
            }
            GetSizeDecl[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.GET_SIZE,verifyList,orgTrack, declType);
              break matchblock;
            }
          }
                   }
      }
    }
    // remove non mandatory functions
    if(verifyList.contains(TomSyntaxChecker.EQUALS)) {
      verifyList.remove(verifyList.indexOf(TomSyntaxChecker.EQUALS));
    }
    if(!verifyList.isEmpty()) {
      messageMissingMacroFunctions(declType, verifyList);
    }
  } // verifyTypeDecl

  private void verifyMultipleDefinition(String name, String symbolType, String OperatorOrType) {
    ArrayList list;
    if (OperatorOrType.equals(TomSyntaxChecker.OPERATOR)) {
      list = alreadyStudiedSymbols;
    } else {
      list = alreadyStudiedTypes;
    }
    if(list.contains(name)) {
      messageError(currentTomStructureOrgTrack.getFileName(),
          currentTomStructureOrgTrack.getLine(),
          TomMessage.multipleSymbolDefinitionError,
          new Object[]{name});
    } else {
      list.add(name);
    }
  } // verifyMultipleDefinition

  private  void checkField(String function, ArrayList foundFunctions, Option orgTrack, String symbolType) {
    if(foundFunctions.contains(function)) {
      foundFunctions.remove(foundFunctions.indexOf(function));
    } else {
      messageError(orgTrack.getFileName(),orgTrack.getLine(),
                   TomMessage.macroFunctionRepeated,
                   new Object[]{function});
    }
  } // checkField

  private  void checkFieldAndLinearArgs(String function, ArrayList foundFunctions, Option orgTrack, String name1, String name2, String symbolType) {
    checkField(function,foundFunctions, orgTrack, symbolType);
    if(name1.equals(name2)) {
      messageError(orgTrack.getFileName(),orgTrack.getLine(),
                   TomMessage.nonLinearMacroFunction,
                   new Object[]{function, name1});
    }
  } // checkFieldAndLinearArgs

  // ///////////////////////////////
  // SYMBOL DECLARATION CONCERNS //
  // ///////////////////////////////
  private  void verifySymbol(String symbolType, TomSymbol tomSymbol){
    int domainLength;
    String symbStrName = tomSymbol.getAstName().getString();
    OptionList optionList = tomSymbol.getOption();
    // We save first the origin tracking of the symbol declaration
    currentTomStructureOrgTrack = TomBase.findOriginTracking(optionList);

    // ensure first definition then Codomain, Domain, Macros and Slots (Simple
    // operator)
    verifyMultipleDefinition(symbStrName, symbolType, TomSyntaxChecker.OPERATOR);
    verifySymbolCodomain(TomBase.getSymbolCodomain(tomSymbol), symbStrName, symbolType);
    domainLength = verifySymbolDomain(TomBase.getSymbolDomain(tomSymbol), symbStrName, symbolType);
    verifySymbolMacroFunctions(optionList, domainLength, symbolType);
  } // verifySymbol

  private  void verifySymbolCodomain(TomType codomain, String symbName, String symbolType) {
    %match(TomType codomain) {
      Codomain(Name(opName)) -> {
        if(symbolTable().getSymbolFromName(`opName) == null) {
          messageError(currentTomStructureOrgTrack.getFileName(),currentTomStructureOrgTrack.getLine(),
              TomMessage.symbolCodomainError,
              new Object[]{symbName, codomain});
        }
        return;
      }

      TomTypeAlone(typeName) -> {
        if(!testTypeExistence(`typeName)) {
          messageError(currentTomStructureOrgTrack.getFileName(),currentTomStructureOrgTrack.getLine(),
              TomMessage.symbolCodomainError,
              new Object[]{symbName, `(typeName)});
        }
        return;
      }

      EmptyType() -> {
        messageError(currentTomStructureOrgTrack.getFileName(),currentTomStructureOrgTrack.getLine(),
            TomMessage.symbolCodomainError,
            new Object[]{symbName, ""});
        return;
      }
    }
    throw new TomRuntimeException("Strange codomain "+codomain);
  }

  private  int verifySymbolDomain(TomTypeList args, String symbName, String symbolType) {
    int position = 1;
    if(symbolType.equals(TomSyntaxChecker.CONSTRUCTOR)) {
      %match(TomTypeList args) {
        (_*,  TomTypeAlone(typeName),_*) -> { // for each symbol types
          if(!testTypeExistence(`typeName)) {
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.symbolDomainError,
                new Object[]{new Integer(position), symbName, `(typeName)});
          }
          position++;
        }
      }
      return (position-1);
    } else { // OPARRAY and OPLIST
      %match(TomTypeList args) {
        (TomTypeAlone(typeName)) -> {
          if(!testTypeExistence(`typeName)) {
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.listSymbolDomainError,
                new Object[]{symbName, `(typeName)});
          }
        }
      } // match
      return 1;
    }
  } // verifySymbolDomain

  private  void verifySymbolMacroFunctions(OptionList option, int domainLength, String symbolType) {
    ArrayList verifyList = new ArrayList();
    boolean foundOpMake = false;
    if(symbolType.equals(TomSyntaxChecker.CONSTRUCTOR)) { // Nothing absolutely
                                                          // necessary
    } else if(symbolType == TomSyntaxChecker.OP_ARRAY ) {
      verifyList.add(TomSyntaxChecker.MAKE_EMPTY);
      verifyList.add(TomSyntaxChecker.MAKE_APPEND);
    } else if(symbolType == TomSyntaxChecker.OP_LIST) {
      verifyList.add(TomSyntaxChecker.MAKE_EMPTY);
      verifyList.add(TomSyntaxChecker.MAKE_INSERT);
    }

    %match(OptionList option) {
      (_*, DeclarationToOption(d), _*) -> { // for each Declaration
        Declaration decl=`d;
        matchblock:{
          %match(Declaration decl ) {
            // for a array symbol
            MakeEmptyArray[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.MAKE_EMPTY,verifyList,orgTrack, symbolType);
              break matchblock;
            }
            MakeAddArray[VarList=Variable[AstName=Name(name1)], VarElt=Variable[AstName=Name(name2)], OrgTrack=orgTrack] -> {
              `checkFieldAndLinearArgs(TomSyntaxChecker.MAKE_APPEND, verifyList, orgTrack, name1, name2, symbolType);
              break matchblock;
            }
            // for a List symbol
            MakeEmptyList[OrgTrack=orgTrack] -> {
              `checkField(TomSyntaxChecker.MAKE_EMPTY,verifyList,orgTrack, symbolType);
              break matchblock;
            }
            MakeAddList[VarList=Variable[AstName=Name(name1)], VarElt=Variable[AstName=Name(name2)], OrgTrack=orgTrack] -> {
              `checkFieldAndLinearArgs(TomSyntaxChecker.MAKE_INSERT, verifyList, orgTrack, name1, name2, symbolType);
              break matchblock;
            }
            // for a symbol
            MakeDecl[Args=makeArgsList, OrgTrack=og@OriginTracking[FileName=fileName,Line=line]] -> {
              if (!foundOpMake) {
                foundOpMake = true;
                `verifyMakeDeclArgs(makeArgsList, domainLength, og, symbolType);
              } else {
                messageError(`fileName, `line,
                             TomMessage.macroFunctionRepeated,
                             new Object[]{TomSyntaxChecker.MAKE});
              }
              break matchblock;
            }
          }
        }
      }
    }
    if(!verifyList.isEmpty()) {
      messageMissingMacroFunctions(symbolType, verifyList);
    }
  }  // verifySymbolMacroFunctions

  private  void verifyMakeDeclArgs(TomList argsList, int domainLength, Option orgTrack, String symbolType){
      // we test the necessity to use different names for each
      // variable-parameter.
    int nbArgs = 0;
    ArrayList listVar = new ArrayList();
    %match(TomList argsList) {
      (_*, Variable[AstName=Name(name)] ,_*) -> { // for each Macro variable
        if(listVar.contains(`name)) {
          messageError(orgTrack.getFileName(),orgTrack.getLine(),
                       TomMessage.nonLinearMacroFunction,
                       new Object[]{TomSyntaxChecker.MAKE, `(name)});
        } else {
          listVar.add(`name);
        }
        nbArgs++;
      }
    }
    if(nbArgs != domainLength) {
      messageError(orgTrack.getFileName(),orgTrack.getLine(),
                   TomMessage.badMakeDefinition,
                   new Object[]{new Integer(nbArgs), new Integer(domainLength)});
    }
  } // verifyMakeDeclArgs

  private  void verifySymbolPairNameDeclList(PairNameDeclList pairNameDeclList, String symbolType) {
      // we test the existence of 2 same slot names
    ArrayList listSlot = new ArrayList();
    %match(PairNameDeclList pairNameDeclList) {
      (_*, PairNameDecl[SlotName=Name(name)], _*) -> { // for each Slot
        if(listSlot.contains(`name)) {
            // TODO
            // messageWarningTwoSameSlotDeclError(name, orgTrack, symbolType);
        } else {
          listSlot.add(`name);
        }
      }
    }
  } // verifySymbolPairNameDeclList

  private  void messageMissingMacroFunctions(String symbolType, ArrayList list) {
    StringBuffer listOfMissingMacros = new StringBuffer();
    for(int i=0;i<list.size();i++) {
      listOfMissingMacros.append(list.get(i) + ",  ");
    }
    String stringListOfMissingMacros = listOfMissingMacros.substring(0, listOfMissingMacros.length()-3);
    messageError(currentTomStructureOrgTrack.getFileName(),
        currentTomStructureOrgTrack.getLine(),
                 TomMessage.missingMacroFunctions,
                 new Object[]{stringListOfMissingMacros});
  } // messageMissingMacroFunctions

  // ////////////////////////////// /
  // MATCH VERIFICATION CONCERNS ///
  // ////////////////////////////////
  /**
   * Verifies the match construct
   * 1. Verifies all MatchConstraints
   * 2. Verifies all NumericConstraints (left side a variable, and the type of the right side numeric)
   */
  private void verifyMatch(ConstraintInstructionList constraintInstructionList, OptionList option) throws VisitFailure{
    currentTomStructureOrgTrack = TomBase.findOriginTracking(option);
    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    `TopDown(CollectConstraints(constraints)).visitLight(constraintInstructionList);
    TomType typeMatch = null;
    for(Constraint constr: constraints){
      %match(constr){
        MatchConstraint(pattern,subject) -> {
          %match(subject) {            
            TomTypeToTomTerm(TomTypeAlone[]) -> {
              // this is from %strategy construct and is already cheked in verifyStrategy
              return;
            }
            _ ->{
              typeMatch = getSubjectType(`subject,constraints);
            }
          }
          if (typeMatch == null) {
            Object messageContent = `subject;
            %match(subject){
              Variable[AstName=Name(stringName)] -> {
                messageContent = `stringName;
              }
              TermAppl[NameList=concTomName(Name(stringName),_*)] -> {
                messageContent = `stringName;
              }
            }
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.cannotGuessMatchType,
                new Object[]{`(messageContent)});

            return;
          }

          // we now compare the pattern to its definition
          verifyMatchPattern(`pattern, typeMatch);
        }

        NumericConstraint[Left=left,Right=right] -> {
          // the left side should always be a variable
          if (!`(left).isVariable()) {
            Object messageContent = `left;
            %match(left){            
              TermAppl[NameList=concTomName(Name(stringName),_*)] -> {
                messageContent = `stringName;             
              }
              UnamedVariable[] -> {
                messageContent = "_";             
              }
              UnamedVariableStar[] -> {
                messageContent = "_*";             
              }
              AntiTerm[] -> {
                messageError(currentTomStructureOrgTrack.getFileName(),
                    currentTomStructureOrgTrack.getLine(),
                    TomMessage.forbiddenAntiTermInNumeric,
                    new Object[]{});
                return;                
              }
            }
          } 
          if (`(left).getConstraints() != `concConstraint()){
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.forbiddenAnnotationsNumeric,
                new Object[]{});
            return;   
          }
          typeMatch = getSubjectType(`right,constraints);  
          // the right side should have a numeric type
          Object messageContent = `right;
          %match(right){
            Variable[AstName=Name(stringName)] -> {
              messageContent = `stringName;
            }
            TermAppl[NameList=concTomName(Name(stringName),_*)] -> {
              messageContent = `stringName;
            }
          }
          if (typeMatch == null) {          
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.cannotGuessMatchType,
                new Object[]{messageContent});

            return;
          } else {            
            if (!symbolTable().isNumericType(typeMatch)){
              messageError(currentTomStructureOrgTrack.getFileName(),
                  currentTomStructureOrgTrack.getLine(),
                  TomMessage.numericTypeRequired,
                  new Object[]{messageContent});

              return;
            }
          }
          // we now compare the pattern to its definition
          verifyMatchPattern(`left, typeMatch);
        }
      }
    } // for
  }
  
  /**
   * tries to give the type of the tomTerm received as parameter
   */
  private TomType getSubjectType(TomTerm subject, ArrayList<Constraint> constraints){
    %match(subject) {
      Variable[AstName=Name(name),AstType=tomType@TomTypeAlone(type)] -> {              
        if(`type.equals("unknown type")) {
          // try to guess
          return guessSubjectType(`subject,constraints);
        } else if(testTypeExistence(`type)) {
          return `tomType;
        } else {
          messageError(currentTomStructureOrgTrack.getFileName(),
              currentTomStructureOrgTrack.getLine(),
              TomMessage.unknownMatchArgumentTypeInSignature,
              new Object[]{`name, `(type)});                
        }
      }
      term@TermAppl[NameList=concTomName(Name(name))] -> {
        TomSymbol symbol = getSymbolFromName(`name);
        if(symbol!=null) {
          TomType type = TomBase.getSymbolCodomain(symbol);
          String typeName = TomBase.getTomType(`type);
          if(!testTypeExistence(typeName)) {
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.unknownMatchArgumentTypeInSignature,
                new Object[]{`name, typeName});
          }          
          validateTerm(`term, type, false, true, true);
          return type;
        } else {
          // try to guess
          return guessSubjectType(`subject,constraints);
        }
      }
      // the user specified the type
      BuildReducedTerm(TermAppl[NameList=concTomName(Name(name))],userType) -> {
        TomSymbol symbol = getSymbolFromName(`name);
        if(symbol != null) { // check that the type provided by the user is consistent
          TomType type = TomBase.getSymbolCodomain(symbol);
          if (!`(userType).equals(type)){
            messageError(currentTomStructureOrgTrack.getFileName(),
                currentTomStructureOrgTrack.getLine(),
                TomMessage.inconsistentTypes,
                new Object[]{`name, TomBase.getTomType(type), TomBase.getTomType(`userType)});
          }
        }
        // a function call 
        return `userType;
      }
    }
    return null;
  }
  
  /**
   * if a type is not specified 
   * 1. we look for a type in all match constraints where we can find this subject
   * 2. TODO: if the subject is in a constraint with a variable (the pattern is a variable for instance),
   * try to see if a variable with the same name already exists and can be typed, and if yes, get that type
   */
  private TomType guessSubjectType(TomTerm subject,ArrayList<Constraint> constraints){    
    for(Constraint constr:constraints){
      %match(constr){        
        MatchConstraint(patt,s) -> {
          // we want two terms to be equal even if their option is different 
          //( because of their possition for example )
 matchL:  %match(subject,s){
            Variable[AstName=astName,AstType=tomType],Variable[AstName=astName,AstType=tomType] -> {break matchL;}
            TermAppl[NameList=tomNameList,Args=tomList],TermAppl[NameList=tomNameList,Args=tomList] -> {break matchL;}
            RecordAppl[NameList=tomNameList,Slots=slotList],RecordAppl[NameList=tomNameList,Slots=slotList] -> {break matchL;}
            XMLAppl[NameList=tomNameList,AttrList=tomList,ChildList=tomList],XMLAppl[NameList=tomNameList,AttrList=tomList,ChildList=tomList] -> { break matchL; }
            BuildReducedTerm(TermAppl[NameList=tomNameList,Args=tomList],type),BuildReducedTerm(TermAppl[NameList=tomNameList,Args=tomList],type) -> {break matchL;}
            _,_ -> { continue; }
          }
          TomTerm pattern = `patt;
          %match(pattern) {
            AntiTerm(p) -> { pattern = `p; }
          }
          %match(pattern) {
            (TermAppl|RecordAppl|XMLAppl)[NameList=concTomName(Name(name),_*)] -> {        
                TomSymbol symbol = null;
                if(`pattern.isXMLAppl()) {
                  symbol = getSymbolFromName(Constants.ELEMENT_NODE);
                } else {
                  symbol = getSymbolFromName(`name);
                }                
                if(symbol!=null) {
                  TomType type = TomBase.getSymbolCodomain(symbol);
                  // System.out.println("type = " + type);            
                  String typeName = TomBase.getTomType(`type);
                  if(!testTypeExistence(typeName)) {
                    messageError(currentTomStructureOrgTrack.getFileName(),
                        currentTomStructureOrgTrack.getLine(),
                        TomMessage.unknownMatchArgumentTypeInSignature,
                        new Object[]{`name, typeName});
                  }
                  return type;
                }
             }
// TOBE CONTINUED            
//            var@Variable[] -> {
//              TomType type = getVarTypeFromConstraints(var,constraints);
//              if ( type != null ) {
//                return type;
//              }
//            }
          }         
        }
// TOBE CONTINUED        
//        NumericConstraint[Left=left,Right=right] -> {
//          // we want two terms to be equal even if their option is different 
//          //( because of their possition for example )
//          if ((`right.setOption(`concOption())) != (subject.setOption(`concOption()))) { continue; }
//          if (`left.isVariable()) {
//            TomType type = guessVarTypeFromConstraints(var,matchConstraints);
//            if ( type != null ) {
//              return type;
//            }
//          }
//        }
      }
    }// for    
    return null;
  }
  
//  /**
//   * trys to guess the type of the variable by looking into all constraints 
//   * if it can find it somewhere where it is typed
//   */
//  private TomType guessVarTypeFromConstraints(TomTerm var, ArrayList<Constraint> constraints){
//    
//  }
  
  /**
   * Collect the matchConstraints in a list of constraints   
   */
  %strategy CollectMatchConstraints(constrList:Collection) extends Identity(){
    visit Constraint{
      m@MatchConstraint[] -> {        
        constrList.add(`m);         
      }      
    }// end visit
  }// end strategy   
 
  /**
   * Collect the constraints (match and numeric)
   */
  %strategy CollectConstraints(constrList:Collection) extends Identity(){
    visit Constraint{
      c@(MatchConstraint|NumericConstraint)[] -> {        
        constrList.add(`c);         
      }      
    }// end visit
  }// end strategy   
  
  /**
   * the term should be valid
   */
  private  void verifyMatchPattern(TomTerm term, TomType type) {      
    // no term can be a Var* nor _*: not
    // allowed as top leftmost symbol
    TermDescription termDesc = analyseTerm(term);
    if(termDesc.getTermClass() == UNAMED_VARIABLE_STAR || termDesc.getTermClass() == VARIABLE_STAR) {
      messageError(termDesc.getFileName(),termDesc.getLine(),
          TomMessage.incorrectVariableStarInMatch,
          new Object[]{termDesc.getName()});
    } else {    
      // Analyse the term if type != null
      if (type != null) {
        // the type is known and found in the match signature
        validateTerm(`term, type, false, true, false);
      }
    }
  }

  // ///////////////////////////////
  // STRATEGY VERIFICATION CONCERNS /
  // ///////////////////////////////
  private  void verifyStrategy(TomVisitList visitList) throws VisitFailure {
    while(!visitList.isEmptyconcTomVisit()) {
      TomVisit visit = visitList.getHeadconcTomVisit();
      verifyVisit(visit);
      // next visit
      visitList = visitList.getTailconcTomVisit();
    }
  }

  private  void verifyVisit(TomVisit visit) throws VisitFailure {
    %match(TomVisit visit) {
      VisitTerm(type,constraintInstructionList,option) -> {        
        %match(constraintInstructionList){
          concConstraintInstruction(_*,ConstraintInstruction[Constraint=constraint],_*) -> {
            ArrayList<MatchConstraint> matchConstraints = new ArrayList<MatchConstraint>();
            `TopDown(CollectMatchConstraints(matchConstraints)).visitLight(`constraint);   
            // for the first constraint, check that the type is conform to the type specified in visit
            MatchConstraint matchConstr = (MatchConstraint)matchConstraints.get(0); 
            `verifyMatchPattern(matchConstr.getpattern(), type);
          }
        }    
        // check the rest of the constraints
        `verifyMatch(constraintInstructionList,option);
      }
    }
  }

  private static boolean findMakeDecl(OptionList option) {
    %match(OptionList option) {
      (_*, DeclarationToOption(MakeDecl[]), _*) -> {
        return true;
      }
    }
    return false;
  }


  /**
   * Analyse a term given an expected type and re-enter recursively on children
   */
  public  TermDescription validateTerm(TomTerm term, TomType expectedType, boolean listSymbol, boolean topLevel, boolean permissive) {
    String termName = "emptyName";
    TomType type = null;
    int termClass=-1;
    String fileName = "unknown";
    int decLine=-1;
    Option orgTrack;
    matchblock:{
      %match(TomTerm term) {
        TermAppl[Option=options, NameList=(Name("")), Args=args] -> {
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          termClass = UNAMED_APPL;
            // there shall be only one list symbol with expectedType as Codomain
            // else ensureValidUnamedList returns null
          TomSymbol symbol = ensureValidUnamedList(expectedType, fileName,decLine);
          if(symbol == null) {
            break matchblock;
          } else {
            // there is only one list symbol and its type is the expected one
            // (ensure by ensureValidUnamedList call)
            type = expectedType;
            termName = symbol.getAstName().getString();
              // whatever the arity is, we continue recursively and there is
              // only one element in the Domain
            validateListOperatorArgs(`args, symbol.getTypesToType().getDomain().getHeadconcTomType(),
                symbol.getTypesToType().getCodomain(),permissive);
            if(permissive) { System.out.println("UnamedList but permissive");}
            break matchblock;
          }
        }

        TermAppl[Option=options, NameList=symbolNameList, Args=arguments] -> {
          TomList args = `arguments;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          termClass = TERM_APPL;

          TomSymbol symbol = ensureValidApplDisjunction(`symbolNameList, expectedType, fileName, decLine, permissive, topLevel);

          if(symbol == null) {
            validateTermThrough(term,permissive);
            break matchblock;
          }
            // Type is OK
          type = expectedType;
          TomName headName = `symbolNameList.getHeadconcTomName();
          if (headName instanceof AntiName){
            headName = ((AntiName)headName).getName();
          }
          termName = headName.getString();
          boolean listOp = (TomBase.isListOperator(symbol) || TomBase.isArrayOperator(symbol));
          if(listOp) {
              // whatever the arity is, we continue recursively and there is
              // only one element in the Domain
              // - we can also have children that are sublists
            validateListOperatorArgs(args, symbol.getTypesToType().getDomain().getHeadconcTomType(),
                symbol.getTypesToType().getCodomain(),permissive);
          } else {
            // the arity is important also there are different types in Domain
            TomTypeList types = symbol.getTypesToType().getDomain();
            int nbArgs = args.length();
            int nbExpectedArgs = types.length();
            if(nbArgs != nbExpectedArgs) {
              messageError(fileName,decLine, TomMessage.symbolNumberArgument,
                  new Object[]{termName, new Integer(nbExpectedArgs), new Integer(nbArgs)});
              break matchblock;
            }
            while(!args.isEmptyconcTomTerm()) {
                // repeat analyse with associated expected type and control
                // arity
              validateTerm(args.getHeadconcTomTerm(), types.getHeadconcTomType(), listOp/* false */, false, permissive);
              args = args.getTailconcTomTerm();
              types = types.getTailconcTomType();
            }
          }
          break matchblock;
        }

        rec@RecordAppl[Option=options,NameList=symbolNameList,Slots=slotList] -> {
          if(permissive) {
            // Record are not allowed in a rhs
            messageError(findOriginTrackingFileName(`options),findOriginTrackingLine(`options), TomMessage.incorrectRuleRHSClass, new Object[]{getName(`rec)+"[...]"});
          }
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          termClass = RECORD_APPL;
          TomSymbol symbol = ensureValidRecordDisjunction(`symbolNameList, `slotList, expectedType, fileName, decLine, true);
          if(symbol == null) {
            break matchblock;
          }

          %match(TomNameList symbolNameList) {
            /*
             * We perform tests as we have different RecordAppls: they all must
             * be valid and have the expected return type
             */
            (_*, Name(name), _*) -> {
              verifyRecordStructure(`options, `name, `slotList, fileName,decLine);
            }
          }

          type = expectedType;
          TomName headName = `symbolNameList.getHeadconcTomName();
          if (headName 
        		  	instanceof AntiName){
        	  headName = ((AntiName)headName).getName();
          }
          termName = headName.getString();
          break matchblock;
        }

        XMLAppl[Option=options, NameList=(_*, Name(_), _*), ChildList=childList] -> {
            // TODO: can we do it
            // ensureValidDisjunction(symbolNameList); ??????????
          termClass = XML_APPL;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          type = TomBase.getSymbolCodomain(getSymbolFromName(Constants.ELEMENT_NODE));
          termName = Constants.ELEMENT_NODE;

          TomList args = `childList;
          /*
           * we cannot use the following expression TomType TNodeType =
           * symbolTable().getType(Constants.TNODE); because TNodeType should be
           * a TomTypeAlone and not an expanded type
           */
          TomType TNodeType = TomBase.getSymbolCodomain(symbolTable().getSymbolFromName(Constants.ELEMENT_NODE));
          // System.out.println("TNodeType = " + TNodeType);
          while(!args.isEmptyconcTomTerm()) {
            // repeat analyse with associated expected type and control arity
            validateTerm(args.getHeadconcTomTerm(), TNodeType, true, false, permissive);
            args = args.getTailconcTomTerm();
          }

          break matchblock;
        }

        Variable[Option=options, AstName=Name(name)] -> {
          termClass = VARIABLE;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          type = null;
          termName = `name;
          break matchblock;
        }

        VariableStar[Option=options, AstName=Name(name)] -> {
          termClass = VARIABLE_STAR;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          type = null;
          termName = `name+"*";
          if(!listSymbol) {
            messageError(fileName,decLine, TomMessage.invalidVariableStarArgument, new Object[]{termName});
          }
          break matchblock;
        }

        UnamedVariable[Option=options] -> {
          termClass = UNAMED_VARIABLE;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          type = null;
          termName = "_";
          if(permissive) {
            messageError(fileName,decLine, TomMessage.incorrectRuleRHSClass, new Object[]{termName});
          }
          break matchblock;
        }

        UnamedVariableStar[Option=options] -> {
          termClass = UNAMED_VARIABLE_STAR;
          fileName = findOriginTrackingFileName(`options);
          decLine = findOriginTrackingLine(`options);
          type = null;
          termName = "_*";
          if(!listSymbol) {
            messageError(fileName,decLine, TomMessage.invalidVariableStarArgument, new Object[]{termName});
          }
          if(permissive) {
            messageError(fileName,decLine, TomMessage.incorrectRuleRHSClass, new Object[]{termName});
          }
          break matchblock;
        }
      }
      throw new TomRuntimeException("Strange Term "+term);
    }
    return new TermDescription(termClass, termName, fileName,decLine, type);
  }

  private  void validateTermThrough(TomTerm term, boolean permissive) {
    %match(TomTerm term) {
      TermAppl[Args=arguments] -> {
        TomList args = `arguments;
        while(!args.isEmptyconcTomTerm()) {
          TomTerm child = args.getHeadconcTomTerm();
          TomSymbol sym = getSymbolFromName(getName(child));
          if(sym != null) {
            validateTerm(child,sym.getTypesToType().getCodomain(),false,false,permissive);
          } else {
            validateTermThrough(child,permissive);
          }
          args = args.getTailconcTomTerm();
        }
      }
    }
  }

  public TermDescription analyseTerm(TomTerm term) {
    matchblock:{
      %match(TomTerm term) {
        TermAppl[Option=options, NameList=(Name(str))] -> {
          if (`str.equals("")) {
            return new TermDescription(UNAMED_APPL, `str,
                findOriginTrackingFileName(`options),
                findOriginTrackingLine(`options), 
                null);
              // TODO
          } else {
            return new TermDescription(TERM_APPL, `str,
                findOriginTrackingFileName(`options),
                findOriginTrackingLine(`options),
                TomBase.getSymbolCodomain(getSymbolFromName(`str)));
          }
        }
        TermAppl[Option=options, NameList=(Name(name), _*)] -> {
          return new TermDescription(APPL_DISJUNCTION, `name,
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),
              TomBase.getSymbolCodomain(getSymbolFromName(`name)));
        }
        RecordAppl[Option=options,NameList=(Name(name))] ->{
          return new TermDescription(RECORD_APPL, `name,
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),
              TomBase.getSymbolCodomain(getSymbolFromName(`name)));
        }
        RecordAppl[Option=options,NameList=(Name(name), _*)] ->{
          return new TermDescription(RECORD_APPL_DISJUNCTION,`name,
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),
              TomBase.getSymbolCodomain(getSymbolFromName(`name)));
        }
        XMLAppl[Option=options] -> {
          return new TermDescription(XML_APPL, Constants.ELEMENT_NODE,
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),
              TomBase.getSymbolCodomain(getSymbolFromName(Constants.ELEMENT_NODE)));
        }
        Variable[Option=options, AstName=Name(name)] -> {
          return new TermDescription(VARIABLE, `name,
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),  null);
        }
        VariableStar[Option=options, AstName=Name(name)] -> {
          return new TermDescription(VARIABLE_STAR, `name+"*",
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),  null);
        }
        UnamedVariable[Option=options] -> {
          return new TermDescription(UNAMED_VARIABLE, "_",
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),  null);
        }
        UnamedVariableStar[Option=options] -> {
          return new TermDescription(UNAMED_VARIABLE_STAR, "_*",
                findOriginTrackingFileName(`options),
              findOriginTrackingLine(`options),  null);
        }
      }
      throw new TomRuntimeException("Strange Term "+term);
    }
  }

  private  TomSymbol ensureValidUnamedList(TomType expectedType, String fileName,int decLine) {
    TomSymbolList symbolList = symbolTable().getSymbolFromType(expectedType);
    TomSymbolList filteredList = `concTomSymbol();
    %match(TomSymbolList symbolList) {
      (_*, symbol , _*) -> {
        if(TomBase.isArrayOperator(`symbol) || TomBase.isListOperator(`symbol)) {
          filteredList = `concTomSymbol(symbol,filteredList*);
        }
      }
    }

    if(filteredList.isEmptyconcTomSymbol()) {
      messageError(fileName,decLine,
                   TomMessage.unknownUnamedList,
                   new Object[]{expectedType.getString()});
      return null;
    } else if(!filteredList.getTailconcTomSymbol().isEmptyconcTomSymbol()) {
      StringBuffer symbolsString = new StringBuffer();
      while(!filteredList.isEmptyconcTomSymbol()) {
        symbolsString .append(" " + filteredList.getHeadconcTomSymbol().getAstName().getString());
        filteredList= filteredList.getTailconcTomSymbol();
      }
      messageError(fileName,decLine,
                   TomMessage.ambigousUnamedList,
                   new Object[]{expectedType.getString(), symbolsString.toString()});
      return null;
    } else {
      return filteredList.getHeadconcTomSymbol();
    }
  }

  private TomSymbol ensureValidApplDisjunction(TomNameList symbolNameList, TomType expectedType, 
      String fileName, int decLine, boolean permissive, boolean topLevel) {

    if(symbolNameList.length()==1) { // Valid but has it a good type?
      String res = symbolNameList.getHeadconcTomName().getString();
      TomSymbol symbol = getSymbolFromName(res);
      if (symbol == null ) {
        // this correspond to a term like 'unknown()' or unknown(s1, s2, ...)
        if(!permissive) {
          messageError(fileName,decLine, TomMessage.unknownSymbol, new Object[]{res});
        } else {
          messageWarning(fileName,decLine, TomMessage.unknownPermissiveSymbol, new Object[]{res});
        }
      } else { // known symbol
        if ( strictType  || !topLevel ) {
          if (!ensureSymbolCodomain(TomBase.getSymbolCodomain(symbol), expectedType, TomMessage.invalidCodomain, res, fileName,decLine)) {
            return null;
          }
        }
      }
      return symbol;
    } else {
      // this is a disjunction
      if(permissive) {
	messageError(fileName,decLine, TomMessage.impossiblePermissiveAndDisjunction, new Object[]{});
      }

      TomSymbol symbol = null;
      TomTypeList domainReference = null;
      PairNameDeclList slotReference = null;
      String nameReference = null;
      %match(TomNameList symbolNameList) {
	(_*, Name(dijName), _*) -> { // for each SymbolName
	  symbol =  getSymbolFromName(`dijName);
	  if (symbol == null) {
	    // In disjunction we can only have known symbols
	    messageError(fileName,decLine, TomMessage.unknownSymbolInDisjunction, new Object[]{`(dijName)});
	    return null;
	  }
	  if ( strictType  || !topLevel ) {
	    // ensure codomain is correct
	    if (!ensureSymbolCodomain(TomBase.getSymbolCodomain(symbol), expectedType, TomMessage.invalidDisjunctionCodomain, `dijName, fileName,decLine)) {
	      return null;
	    }
	  }

	  if (domainReference == null) { // save Domain reference
	    domainReference = TomBase.getSymbolDomain(symbol);
	    slotReference = symbol.getPairNameDeclList();
	    nameReference = `dijName;
	  } else {
	    if(TomBase.getSymbolDomain(symbol) != domainReference) {
	      messageError(fileName,decLine, TomMessage.invalidDisjunctionDomain, new Object[]{nameReference, `(dijName) });
	      return null;
	    }
	    if(symbol.getPairNameDeclList() != slotReference) {
	      PairNameDeclList l1 = slotReference;
	      PairNameDeclList l2 = symbol.getPairNameDeclList();
	      while(!l1.isEmptyconcPairNameDecl()) {
		if(l1.getHeadconcPairNameDecl().getSlotName() != l2.getHeadconcPairNameDecl().getSlotName()) {
		  messageError(fileName,decLine, TomMessage.invalidDisjunctionDomain, new Object[]{nameReference, `(dijName) });
		  return null;
		}
		l1=l1.getTailconcPairNameDecl();
		l2=l2.getTailconcPairNameDecl();
	      }
	    }
	  }
	}
      }
      return symbol;
    }
  }

  private  boolean ensureSymbolCodomain(TomType currentCodomain, TomType expectedType, TomMessage msg, String symbolName, String fileName,int decLine) {
    if(currentCodomain != expectedType) {
      // System.out.println(currentCodomain+"!="+expectedType);
      messageError(fileName,decLine,
                   msg,
                   new Object[]{symbolName, currentCodomain.getString(), expectedType.getString()});
      return false;
    }
    return true;
  }

  private  TomSymbol ensureValidRecordDisjunction(TomNameList symbolNameList, SlotList slotList, 
      TomType expectedType, String fileName, int decLine, boolean topLevel) {
    if(symbolNameList.length()==1) { // Valid but has it a good type?
      String res = symbolNameList.getHeadconcTomName().getString();
      TomSymbol symbol =  getSymbolFromName(res);
      if (symbol == null ) { // this correspond to: unknown[]
          // it is not correct to use Record with unknown symbols
        messageError(fileName,decLine, TomMessage.unknownSymbol, new Object[]{res});
        return null;
      } else { // known symbol
          // ensure type correctness if necessary
        if ( strictType  || !topLevel ) {
          if (!ensureSymbolCodomain(TomBase.getSymbolCodomain(symbol), expectedType, TomMessage.invalidCodomain, res, fileName,decLine)) {
            return null;
          }
        }
      }
      return symbol;
    } else {
      TomSymbol symbol = null;
      TomSymbol referenceSymbol = null;
      TomTypeList referenceDomain = null;
      String referenceName = null;
      %match(TomNameList symbolNameList) {
        (_*, Name(dijName), _*) -> { // for each SymbolName
          symbol =  getSymbolFromName(`dijName);
          if (symbol == null) {
            // In disjunction we can only have known symbols
            messageError(fileName,decLine, TomMessage.unknownSymbolInDisjunction, new Object[]{`(dijName)});
            return null;
          }
          if ( strictType  || !topLevel ) {
            // ensure codomain is correct
            if (!ensureSymbolCodomain(TomBase.getSymbolCodomain(symbol), expectedType, TomMessage.invalidDisjunctionCodomain, `dijName, fileName,decLine)) {
              return null;
            }
          }
          // System.out.println("domain = " + getSymbolDomain(symbol));

          if (referenceDomain == null) { // save Domain reference
            referenceSymbol = symbol;
            referenceName = `dijName;
            referenceDomain = TomBase.getSymbolDomain(symbol);
          } else {
            // check that domains are compatible
            TomTypeList currentDomain = TomBase.getSymbolDomain(symbol);
            // restrict the domain to the record
            while(!slotList.isEmptyconcSlot()) {
              Slot slot = slotList.getHeadconcSlot();
              TomName slotName = slot.getSlotName();
              int currentSlotIndex = TomBase.getSlotIndex(symbol,slotName);
              int referenceSlotIndex = TomBase.getSlotIndex(referenceSymbol,slotName);

              // System.out.println("index1 = " + currentSlotIndex);
              // System.out.println("type1 = " +
              // TomBase.elementAt(currentDomain,currentSlotIndex));
              // System.out.println("index2 = " + referenceSlotIndex);
              // System.out.println("type2 = " +
              // TomBase.elementAt(referenceDomain,referenceSlotIndex));
              
              if (referenceSlotIndex == -1){
                messageError(fileName,decLine, TomMessage.invalidDisjunctionSlotName, new Object[]{referenceName,((Name)slotName).getString()});
                return null;                
              }
              
              if (currentSlotIndex == -1){
                messageError(fileName,decLine, TomMessage.invalidDisjunctionSlotName, new Object[]{`(dijName),((Name)slotName).getString() });
                return null;                
              }
              
              if(TomBase.elementAt(currentDomain,currentSlotIndex) != TomBase.elementAt(referenceDomain,referenceSlotIndex)) {
                messageError(fileName,decLine, TomMessage.invalidDisjunctionDomain, new Object[]{referenceName, `(dijName) });
                return null;
              }

              slotList = slotList.getTailconcSlot();
            }

          }
        }
      }
      return symbol;
    }
  }

  // /////////////////////
  // RECORDS CONCERNS ///
  // /////////////////////
  private  void verifyRecordStructure(OptionList option, String tomName, SlotList slotList, String fileName, int decLine)  {
    TomSymbol symbol = getSymbolFromName(tomName);
    if(symbol != null) {
        // constants have an emptyPairNameDeclList
        // the length of the pairNameDeclList corresponds to the arity of the
        // operator
        // list operator with [] no allowed
      if(slotList.isEmptyconcSlot() && (TomBase.isListOperator(symbol) ||  TomBase.isArrayOperator(symbol)) ) {
        messageError(fileName,decLine,
                     TomMessage.bracketOnListSymbol,
                     new Object[]{tomName});
      }
        // TODO verify type
      verifyRecordSlots(slotList,symbol, TomBase.getSymbolDomain(symbol), tomName, fileName, decLine);
    } else {
      messageError(fileName,decLine,
                   TomMessage.unknownSymbol,
                   new Object[]{tomName});
    }
  }

    // We test the existence/repetition of slotName contained in pairSlotAppl
    // and then the associated term
  private  void verifyRecordSlots(SlotList slotList, TomSymbol tomSymbol, TomTypeList typeList, String methodName, String fileName, int decLine) {
  TomName pairSlotName = null;
  ArrayList listOfPossibleSlot = null;
  ArrayList studiedSlotIndexList = new ArrayList();
    // for each pair slotName <=> Appl
  while( !slotList.isEmptyconcSlot() ) {
      pairSlotName = slotList.getHeadconcSlot().getSlotName();
        // First check for slot name correctness
      int index = TomBase.getSlotIndex(tomSymbol,pairSlotName);
      if(index < 0) {// Error: bad slot name
        if(listOfPossibleSlot == null) {
          // calculate list of possible slot names..
          listOfPossibleSlot = new ArrayList();
          PairNameDeclList listOfSlots = tomSymbol.getPairNameDeclList();
          while ( !listOfSlots.isEmptyconcPairNameDecl() ) {
            TomName sname = listOfSlots.getHeadconcPairNameDecl().getSlotName();
            if(!sname.isEmptyName()) {
              listOfPossibleSlot.add(sname.getString());
            }
            listOfSlots = listOfSlots.getTailconcPairNameDecl();
          }
        }
        messageError(fileName,decLine,
                     TomMessage.badSlotName,
                     new Object[]{pairSlotName.getString(), methodName, listOfPossibleSlot.toString()});
        return; // break analyses
      } else { // then check for repeated good slot name
        Integer integerIndex = new Integer(index);
        if(studiedSlotIndexList.contains(integerIndex)) {
            // Error: repeated slot
          messageError(fileName,decLine,
                       TomMessage.slotRepeated,
                       new Object[]{methodName, pairSlotName.getString()});
          return; // break analyses
        }
        studiedSlotIndexList.add(integerIndex);
      }

        // Now analyses associated term
      PairNameDeclList listOfSlots =  tomSymbol.getPairNameDeclList();
      TomTypeList listOfTypes = typeList;
      while(!listOfSlots.isEmptyconcPairNameDecl()) {
        SlotList listOfPair = slotList;
        TomName slotName = listOfSlots.getHeadconcPairNameDecl().getSlotName();
        TomType expectedType = listOfTypes.getHeadconcTomType();
        if(!slotName.isEmptyName()) {
          // look for a same name (from record)
          whileBlock: {
            while(!listOfPair.isEmptyconcSlot()) {
              Slot pairSlotTerm = listOfPair.getHeadconcSlot();
              %match(TomName slotName, Slot pairSlotTerm) {
                Name[String=name1], PairSlotAppl(Name[String=name1],slotSubterm) -> {
                   validateTerm(`slotSubterm ,expectedType, false, true, false);
                   break whileBlock;
                 }
                _ , _ -> {listOfPair = listOfPair.getTailconcSlot();}
              }
            }
          }
        }
        // prepare next step
        listOfSlots = listOfSlots.getTailconcPairNameDecl();
        listOfTypes = listOfTypes.getTailconcTomType();
      }

      slotList = slotList.getTailconcSlot();
    }
  }

  public  void validateListOperatorArgs(TomList args, TomType expectedType, TomType parentListCodomain, boolean permissive) {
    while(!args.isEmptyconcTomTerm()) {
      TomTerm currentArg = args.getHeadconcTomTerm();      
      TomSymbol argSymbol = getSymbolFromName(getName(currentArg));
      // if we have a sublist 
      if (TomBase.isListOperator(argSymbol)){
        // we can have two cases:
        // 1. the sublist has the codomain = parentListCodomain
        // 2. the sublist has the codomain = expectedType
        if (argSymbol.getTypesToType().getCodomain() == parentListCodomain){
            validateTerm(currentArg, parentListCodomain, true, false, permissive);            
        }else{
            validateTerm(currentArg, expectedType, true, false, permissive);    
        }        
      }else{
        validateTerm(currentArg, expectedType, true, false, permissive);
      }
      args = args.getTailconcTomTerm();
    }
  }

  private  boolean testTypeExistence(String typeName) {
    return symbolTable().getType(typeName) != null;
  }

  protected static class TermDescription {
    private int termClass;
    private String fileName;
    private int decLine;
    private String name ="";
    private TomType tomType = null;

    public TermDescription(int termClass, String name, String fileName, int decLine, TomType tomType) {
      this.termClass = termClass;
      this.fileName = fileName;
      this.decLine = decLine;
      this.name = name;
      this.tomType = tomType;
    }

    public int getTermClass() {
      return termClass;
    }

    public String getName() {
      return name;
    }
    
    public String getFileName() {
      return fileName;
    }

    public int getLine() {
      return decLine;
    }

    public TomType getType() {
      if(tomType != null && !tomType.isEmptyType()) {
        return tomType;
      } else {
        return `EmptyType();
      }
    }
  }
}
