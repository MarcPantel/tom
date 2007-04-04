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
 *
 **/

package tom.engine.tools;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import tom.engine.TomBase;
import tom.engine.adt.tomterm.*;
import tom.engine.xml.Constants;

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

import tom.engine.exception.TomRuntimeException;
import aterm.ATerm;

public class ASTFactory {
  %include { adt/tomsignature/TomSignature.tom }
   // Suppresses default constructor, ensuring non-instantiability.
  private ASTFactory() {
  }

  public static int length(TomList l) {
    if(l.isEmptyconcTomTerm()) {
      return 0;
    } else {
      return 1 + length(l.getTailconcTomTerm());
    }
  }

  public static TomList reverse(TomList l) {
    TomList reverse = `concTomTerm();
    while(!l.isEmptyconcTomTerm()){
      reverse = `concTomTerm(l.getHeadconcTomTerm(),reverse*);
      l = l.getTailconcTomTerm();
    }
    return reverse;
  }

  public static TomList makeList(Collection c) {
    Object array[] = c.toArray();
    TomList list = `concTomTerm();
    for(int i=array.length-1; i>=0 ; i--) {
      Object elt = array[i];
      TomTerm term;
      if(elt instanceof TargetLanguage) {
        term = `TargetLanguageToTomTerm((TargetLanguage)elt);
      } else if(elt instanceof TomType) {
        term = `TomTypeToTomTerm((TomType)elt);
      } else if(elt instanceof Declaration) {
        term = `DeclarationToTomTerm((Declaration)elt);
      } else if(elt instanceof Expression) {
        term = `ExpressionToTomTerm((Expression)elt);
      } else if(elt instanceof TomName) {
        term = `TomNameToTomTerm((TomName)elt);
      } else if(elt instanceof Instruction) {
        term = `InstructionToTomTerm((Instruction)elt);
      } else {
        term = (TomTerm)elt;
      }
      list = `concTomTerm(term,list*);
    }
    return list;
  }

  public static InstructionList makeInstructionList(Collection c) {
    Object array[] = c.toArray();
    InstructionList list = `concInstruction();
    for(int i=array.length-1; i>=0 ; i--) {
      Object elt = array[i];
      Instruction term;
      if(elt instanceof TargetLanguage) {
        term = `TargetLanguageToInstruction((TargetLanguage)elt);
      } else if(elt instanceof TomTerm) {
        term = `TomTermToInstruction((TomTerm)elt);
          //System.out.println("term   = " + term);
      } else if(elt instanceof Instruction) {
        term = (Instruction)elt;
      } else {
        /* XXX: is this an error ? if yes, it should not be that silent */
        System.out.println("elt   = " + elt);
        term = (Instruction) elt;
      }
      list = `concInstruction(term,list*);
    }
    return list;
  }

  public static OptionList makeOptionList(List argumentList) {
    OptionList list = `concOption();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      Option term;
      if(elt instanceof TomName) {
        term = `TomNameToOption((TomName)elt);
      } else if(elt instanceof Declaration) {
        term = `DeclarationToOption((Declaration)elt);
      } else if(elt instanceof TomTerm) {
        term = `TomTermToOption((TomTerm)elt);
      } else {
        term = (Option) elt;
      }
      list = `concOption(term,list*);
    }
    return list;
  }

  public static ConstraintList makeConstraintList(List argumentList) {
    ConstraintList list = `concConstraint();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      Constraint term;
      term = (Constraint)elt;
      list = `concConstraint(term,list*);
    }
    return list;
  }

  public static TomNameList makeNameList(List argumentList) {
    TomNameList list = `concTomName();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      TomName term = (TomName) elt;
      list = `concTomName(term,list*);
    }
    return list;
  }

  public static SlotList makeSlotList(List argumentList) {
    SlotList list = `concSlot();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      Slot term = (Slot) elt;
      list = `concSlot(term,list*);
    }
    return list;
  }

  public static PairNameDeclList makePairNameDeclList(List argumentList) {
    PairNameDeclList list = `concPairNameDecl();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      PairNameDecl term = (PairNameDecl) elt;
      list = `concPairNameDecl(term,list*);
    }
    return list;
  }

  public static PatternInstructionList makePatternInstructionList(List argumentList) {
    PatternInstructionList list = `concPatternInstruction();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      PatternInstruction term;
      term = (PatternInstruction)elt;
      list = `concPatternInstruction(term,list*);
    }
    return list;
  }

  public static TomVisitList makeTomVisitList(List argumentList) {
    TomVisitList list = `concTomVisit();
    for(int i=argumentList.size()-1; i>=0 ; i--) {
      Object elt = argumentList.get(i);
      TomVisit term;
      term = (TomVisit)elt;
      list = `concTomVisit(term,list*);
    }
    return list;
  }
   
  public static TomSymbol makeSymbol(String symbolName, TomType resultType, TomTypeList typeList,
      PairNameDeclList pairNameDeclList, List optionList) {
    return `Symbol(Name(symbolName),TypesToType(typeList,resultType),pairNameDeclList,makeOptionList(optionList));
  }

  public static OptionList makeOption(Option arg) {
    OptionList list = `concOption();
    if(arg!= null) {
      list = `concOption(arg,list*);
    }
    return list;
  }

  public static ConstraintList makeConstraint(Constraint arg) {
    ConstraintList list = `concConstraint();
    if(arg!= null) {
      list = `concConstraint(arg,list*);
    }
    return list;
  }

  public static Constraint makeAssignTo(TomName name,int line, String fileName) {
    return `AssignTo(Variable(makeOption(makeOriginTracking(name.getString(), line , fileName)),
          name,
          TomTypeAlone("unknown type"),
          concConstraint()));
  }
  
  public static Constraint makeStorePosition(TomName name,int line, String fileName) {
    return `AssignPositionTo(Variable(makeOption(makeOriginTracking(name.getString(), line , fileName)),
          name,
          TomTypeAlone("unknown type"),
          concConstraint()));
  }

  public static OptionList makeOption(Option arg, Option info) {
    OptionList list = `concOption();
    if(arg!= null) {
      list = `concOption(arg,list*);
    }
    list = `concOption(info,list*);
    return list;
  }

  
  private static Option makeOriginTracking(String name, int line , String fileName) {
    return `OriginTracking(Name(name), line, fileName);
  }

  
  protected static TomType makeType(String typeNameTom, String typeNametGL) {
    TomType typeTom = `ASTTomType(typeNameTom);
    TomType sortTL  = `TLType(ITL(typeNametGL));
    return `Type(typeTom,sortTL);
  }
  
    /*
     * create an <sort> symbol
     * where <sort> could be int. double or String  
     */
  private static void makeSortSymbol(SymbolTable symbolTable,
                             String sort,
                             String value, List optionList) {
    TomTypeList typeList = `concTomType();
    PairNameDeclList pairSlotDeclList = `concPairNameDecl();
    TomSymbol astSymbol = makeSymbol(value,`TomTypeAlone(sort),typeList,pairSlotDeclList,optionList);
    symbolTable.putSymbol(value,astSymbol);
  }
  
    /*
     * create an integer symbol
     */
  public static void makeIntegerSymbol(SymbolTable symbolTable,
                                String value, List optionList) {
    String sort = "int";
    makeSortSymbol(symbolTable, sort, value, optionList);
  }

    /*
     * create a long symbol
     */
  public static void makeLongSymbol(SymbolTable symbolTable,
                             String value, List optionList) {
    String sort = "long";
    makeSortSymbol(symbolTable, sort, value, optionList);
  }

    /*
     * create a char symbol
     */
  public static void makeCharSymbol(SymbolTable symbolTable,
                             String value, List optionList) {
    String sort = "char";
    makeSortSymbol(symbolTable, sort, value, optionList);
  }
    /*
     * create a double symbol
     */
  public static void makeDoubleSymbol(SymbolTable symbolTable,
                               String value, List optionList) {
    String sort = "double";
    makeSortSymbol(symbolTable, sort, value, optionList);
  }

    /*
     * create a string symbol
     */
  public static void makeStringSymbol(SymbolTable symbolTable,
                               String value, List optionList) {
    String sort = "String";
    makeSortSymbol(symbolTable, sort, value, optionList);
  } 
  
    /*
     * update the root of lhs: it becomes a defined symbol
     */
  public static TomSymbol updateDefinedSymbol(SymbolTable symbolTable, TomTerm term) {
    if(term.isTermAppl() || term.isRecordAppl()) {
      String key = term.getNameList().getHeadconcTomName().getString();
      TomSymbol symbol = symbolTable.getSymbolFromName(key);
      if (symbol != null) {
        OptionList optionList = symbol.getOption();
        optionList = `concOption(optionList*,DefinedSymbol());
        symbolTable.putSymbol(key,symbol.setOption(optionList));
        return symbol;
      }
    }
    return null;
  }

  public static TargetLanguage reworkTLCode(TargetLanguage code, boolean pretty) {
    if(!pretty){
      String newTlCode = code.getCode();      
      newTlCode = newTlCode.replace('\n', ' ');
      newTlCode = newTlCode.replace('\t', ' ');
      newTlCode = newTlCode.replace('\r', ' ');
        //System.out.println("reworking"+newTlCode);
      return code.setCode(newTlCode);
    } else
      return code;
  }

  public static TomName makeName(String slotName) {
    if(slotName.length()>0)
      return `Name(slotName);
    else
      return `EmptyName();
  }

  public static String encodeXMLString(SymbolTable symbolTable, String name) {
    name = "\"" + name + "\"";
    makeStringSymbol(symbolTable,name, new LinkedList());
    return name;
  }

  public static String makeTomVariableName(String name) {
    return "tom_" + name;
  }

  public static TomList metaEncodeTermList(SymbolTable symbolTable,TomList list) {
    %match(TomList list) {
      concTomTerm() -> { return `concTomTerm();}
      concTomTerm(head,tail*) -> {
        TomList tl = metaEncodeTermList(symbolTable,`tail);
        return `concTomTerm(metaEncodeXMLAppl(symbolTable,head),tl*);
      }
    }
    return list;
  }

  public static TomTerm encodeXMLAppl(SymbolTable symbolTable, TomTerm term) {
      /*
       * encode a String into a quoted-string
       * Appl(...,Name("string"),...) becomes
       * Appl(...,Name("\"string\""),...)
       */
    TomNameList newNameList = `concTomName();
    %match(TomTerm term) {
      RecordAppl[NameList=(_*,Name(name),_*)] -> {
        newNameList = `concTomName(newNameList*,Name(encodeXMLString(symbolTable,name)));
      }
    }
    term = term.setNameList(newNameList);
      //System.out.println("encodeXMLAppl = " + term);
    return term;
  }

  public static TomTerm metaEncodeXMLAppl(SymbolTable symbolTable, TomTerm term) {
      /*
       * meta-encode a String into a TextNode
       * Appl(...,Name("\"string\""),...) becomes
       * Appl(...,Name("TextNode"),[Appl(...,Name("\"string\""),...)],...)
       */
      //System.out.println("metaEncode: " + term);
    %match(TomTerm term) {
      RecordAppl[NameList=(Name(tomName))] -> {
          //System.out.println("tomName = " + tomName);
        TomSymbol tomSymbol = symbolTable.getSymbolFromName(`tomName);
        if(tomSymbol != null) {
          if(symbolTable.isStringType(TomBase.getTomType(TomBase.getSymbolCodomain(tomSymbol)))) {
            Option info = `OriginTracking(Name(Constants.TEXT_NODE),-1,"unknown filename");
            term = `RecordAppl(ASTFactory.makeOption(info),
                               concTomName(Name(Constants.TEXT_NODE)),concSlot(PairSlotAppl(Name(Constants.SLOT_DATA),term)),
                          concConstraint());
              //System.out.println("metaEncodeXmlAppl = " + term);
          }
        }
      }
    }
    return term;
  }

  public static boolean isExplicitTermList(LinkedList childs) {
    if(childs.size() == 1) {
      TomTerm term = (TomTerm) childs.getFirst();
      //System.out.println("isExplicitTermList: " + term);
      %match(TomTerm term) {
        RecordAppl[NameList=(Name(""))] -> { 
          return true;
        }
        TermAppl[NameList=(Name(""))] -> { 
          return true;
        }
      }
    }
    return false;
  }
  
  public static LinkedList metaEncodeExplicitTermList(SymbolTable symbolTable, TomTerm term) {
    LinkedList list = new LinkedList();
    %match(TomTerm term) {
      RecordAppl[NameList=(Name("")),Slots=args] -> {
        while(!`args.isEmptyconcSlot()) {
          list.add(metaEncodeXMLAppl(symbolTable,`args.getHeadconcSlot().getAppl()));
          `args = `args.getTailconcSlot();
        }
        return list;
      }

      TermAppl[NameList=(Name("")),Args=args] -> {
        while(!`args.isEmptyconcTomTerm()) {
          list.add(metaEncodeXMLAppl(symbolTable,`args.getHeadconcTomTerm()));
          `args = `args.getTailconcTomTerm();
        }
        return list;
      }
    }
    //System.out.println("metaEncodeExplicitTermList: strange case: " + term);
    list.add(term);
    return list;
  }

  public static TomTerm buildList(TomName name,TomList args, SymbolTable symbolTable) {
    //if(!args.isEmptyconcTomTerm()) System.out.println("buildList: " + args.getHeadconcTomTerm());
    TomSymbol topListSymbol = symbolTable.getSymbolFromName(name.getString());
    String topDomain = TomBase.getTomType(TomBase.getSymbolDomain(topListSymbol).getHeadconcTomType());
    String topCodomain = TomBase.getTomType(TomBase.getSymbolCodomain(topListSymbol));

    //System.out.println("topDomain = " + topDomain);
    //System.out.println("topCodomain = " + topCodomain);

    %match(TomList args) {
      concTomTerm() -> {
        return `BuildEmptyList(name);
      }

      concTomTerm(head@VariableStar[],tail*) -> {
        TomTerm subList = buildList(name,`tail,symbolTable);
        if(topDomain != topCodomain) {
          return `BuildAppendList(name,head,subList);
        }
        return `BuildConsList(name,head,subList);
      }

      concTomTerm(Composite(concTomTerm(head@VariableStar[],_*)),tail*) -> {
        TomTerm subList = buildList(name,`tail,symbolTable);
        if(topDomain != topCodomain) {
          return `BuildAppendList(name,head,subList);
        }
        return `BuildConsList(name,head,subList);
      }

      concTomTerm(Composite(concTomTerm(head@BuildConsList[AstName=opName],_*)),tail*) -> {
        /* Flatten nested lists, unless domain and codomain are equals */
        TomTerm subList = buildList(name,`tail,symbolTable);
        if(topDomain != topCodomain) {
          if(name==`opName) {
            return `BuildAppendList(name,head,subList);
          }
        }
        return `BuildConsList(name,head,subList);
      }

      concTomTerm(Composite(concTomTerm(head@BuildTerm[AstName=Name(tomName),ModuleName=module],_*)),tail*) -> {
        TomTerm subList = buildList(name,`tail,symbolTable);
        if(topDomain != topCodomain) {
        /*
         * compare the codomain of tomName with topDomain
         * if the codomain of the inserted element is equal to the codomain
         * of the list operator, a BuildAppendList is performed 
         */
          TomSymbol symbol = symbolTable.getSymbolFromName(`tomName);
          String codomain = TomBase.getTomType(TomBase.getSymbolCodomain(symbol));
          if(codomain == topCodomain) {
            return `BuildAppendList(name,head,subList);
          } 
        }
        return `BuildConsList(name,head,subList);
      }

      concTomTerm(head@(BuildTerm|BuildConstant|Variable|Composite)[],tail*) -> {
        TomTerm subList = buildList(name,`tail,symbolTable);
        return `BuildConsList(name,head,subList);
      }

      concTomTerm(TargetLanguageToTomTerm[],tail*) -> {
        TomTerm subList = buildList(name,`tail,symbolTable);
        return subList;
      }

    }

    throw new TomRuntimeException("buildList strange term: " + args);
  }

  public static TomTerm buildArray(TomName name,TomList args) {
    return buildArray(name,reverse(args),0);
  }

  private static TomTerm buildArray(TomName name,TomList args, int size) {
    %match(TomList args) {
      concTomTerm() -> {
        return `BuildEmptyArray(name,size);
      }

      concTomTerm(head@VariableStar[],tail*) -> {
          /*System.out.println("head = " + head);*/
        TomTerm subList = buildArray(name,`tail,size+1);
        return `BuildAppendArray(name,head,subList);
      }

      concTomTerm(Composite(concTomTerm(_*,head@VariableStar[],_*)),tail*) -> {
          /*System.out.println("head = " + head);*/
        TomTerm subList = buildArray(name,`tail,size+1);
        return `BuildAppendArray(name,head,subList);
      }

      concTomTerm(head@(BuildTerm|BuildConstant|Variable|Composite)[],tail*) -> {
        TomTerm subList = buildArray(name,`tail,size+1);
        return `BuildConsArray(name,head,subList);
      }

      concTomTerm(TargetLanguageToTomTerm[],tail*) -> {
        TomTerm subList = buildArray(name,`tail,size);
        return subList;
      }

    }

    throw new TomRuntimeException("buildArray strange term: " + args);
     
  }

}
