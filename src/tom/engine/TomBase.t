/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2006, INRIA
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

package tom.engine;

import java.util.*;

import aterm.*;

import tom.engine.tools.*;
import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.exception.TomRuntimeException;

import tom.platform.adt.platformoption.*;

import tom.library.strategy.mutraveler.MuTraveler;
import jjtraveler.reflective.VisitableVisitor;
import jjtraveler.VisitFailure;


/**
 * Base class for most tom files in the compiler.
 * Provides access to the TomSignatureFactory and helper methods.
 */
public class TomBase {

  %include { adt/tomsignature/TomSignature.tom }
  %include { mutraveler.tom }
	%typeterm Collection {
		implement { java.util.Collection }
	}

 public final static String DEFAULT_MODULE_NAME = "default"; 
  
  /** shortcut */
  protected static TomSignatureFactory tsf() {
		return tom.engine.adt.tomsignature.TomSignatureFactory.getInstance(aterm.pure.SingletonFactory.getInstance());
  }
  
  %include { adt/platformoption/PlatformOption.tom }
  
  private static TomList empty;
  
  public TomBase() {
    empty = tsf().makeTomList();
  }

  
  protected static TomNumber makeNumber(int n) {
    return tsf().makeTomNumber_Number(n);
  }
  
  protected static OptionList emptyOption() {
    return ASTFactory.makeOption();
  }

  protected static TomList empty() {
    return empty;
  }

  protected static TomList cons(TomTerm t, TomList l) {
    if(t!=null) {
      return tsf().makeTomList(t,l);
    } else {
      System.out.println("cons: Warning t == null");
      return l;
    }
  }

  protected static TomNumberList appendNumber(int n, TomNumberList path) {
    return (TomNumberList) path.append(makeNumber(n));
  }
    
  protected static TomList append(TomTerm t, TomList l) {
    if(l.isEmptyconcTomTerm()) {
      return cons(t,l);
    } else {
      return cons(l.getHeadconcTomTerm(), append(t,l.getTailconcTomTerm()));
    }
  }

  protected static TomList concat(TomList l1, TomList l2) {
    if(l1.isEmptyconcTomTerm()) {
      return l2;
    } else {
      return cons(l1.getHeadconcTomTerm(), concat(l1.getTailconcTomTerm(),l2));
    }
  }

  protected static TomList reverse(TomList l) {
    TomList reverse = empty();
    while(!l.isEmptyconcTomTerm()){
      reverse = cons(l.getHeadconcTomTerm(),reverse);
      l = l.getTailconcTomTerm();
    }
    return reverse;
  }

  protected static int length(TomList l) {
    if(l.isEmptyconcTomTerm()) {
      return 0;
    } else {
      return 1 + length(l.getTailconcTomTerm());
    }
  }

  public static String getTomType(TomType type) {
    %match(TomType type) {
      ASTTomType(s) -> {return `s;}
      TomTypeAlone(s) -> {return `s;}
      Type(ASTTomType(s),_) -> {return `s;}
      EmptyType() -> {return null;}
    }
		System.out.println("getTomType error on term: " + type);
		throw new TomRuntimeException("getTomType error on term: " + type);
  }

  protected static String getTLType(TomType type) {
    %match(TomType type) {
      TLType[]  -> { return getTLCode(type); }
      Type[tlType=tlType] -> { return getTLCode(`tlType); }
    }
		throw new TomRuntimeException("getTLType error on term: " + type);
  }

  protected static String getTLCode(TomType type) {
    %match(TomType type) {
      TLType(TL[code=tlType])  -> { return `tlType; }
      TLType(ITL[code=tlType]) -> { return `tlType; }
    }
		System.out.println("getTLCode error on term: " + type);
		throw new TomRuntimeException("getTLCode error on term: " + type);
  }

  public static TomType getSymbolCodomain(TomSymbol symbol) {
    if(symbol!=null) {
      return symbol.getTypesToType().getCodomain();
    } else {
      //System.out.println("getSymbolCodomain: symbol = " + symbol);
      return `EmptyType();
    }
  }   

  protected static TomTypeList getSymbolDomain(TomSymbol symbol) {
    if(symbol!=null) {
      return symbol.getTypesToType().getDomain();
    } else {
      //System.out.println("getSymbolDomain: symbol = " + symbol);
      return tsf().makeTomTypeList();
    }
  }

  private static HashMap numberListToIdentifierMap = new HashMap();

  private static String elementToIdentifier(TomNumber subject) {
    %match(TomNumber subject) {
      Begin(Number(i)) -> { return "_begin" + `i; }
      End(Number(i)) -> { return "_end" + `i; }
      MatchNumber(Number(i)) -> { return "_match" + `i; }
      PatternNumber(Number(i)) -> { return "_pattern" + `i; }
      ListNumber(Number(i)) -> { return "_list" + `i; }
      IndexNumber(Number(i)) -> { return "_index" + `i; }
      AbsVar(Number(i)) -> { return "_absvar" + `i; }
      RenamedVar(Name(name)) -> { return "_renamedvar_" + `name; }
      NameNumber(Name(name)) -> { return "_" + `name; }
      NameNumber(PositionName(numberList)) -> { return `numberListToIdentifier(numberList); }
      RuleVar() -> { return "_rulevar"; }
      Number(i) -> { return "_" + `i; }
    }
		return subject.toString(); 
  }

  protected static String numberListToIdentifier(TomNumberList l) {
    String res = (String)numberListToIdentifierMap.get(l);
    if(res == null) {
      TomNumberList key = l;
      StringBuffer buf = new StringBuffer(30);
      while(!l.isEmptyconcTomNumber()) {
        TomNumber elt = l.getHeadconcTomNumber();
        //buf.append("_");
        buf.append(elementToIdentifier(elt));
        l = l.getTailconcTomNumber();
      }
      res = buf.toString();
      numberListToIdentifierMap.put(key,res);
    }
    return res;
  }

  protected static boolean isListOperator(TomSymbol subject) {
    if(subject==null) {
      return false;
    }
    %match(TomSymbol subject) {
      Symbol[option=l] -> {
        OptionList optionList = `l;
        while(!optionList.isEmptyconcOption()) {
          Option opt = optionList.getHeadconcOption();
          %match(Option opt) {
            DeclarationToOption(MakeEmptyList[]) -> { return true; }
            DeclarationToOption(MakeAddList[])   -> { return true; }
          }
          optionList = optionList.getTailconcOption();
        }
        return false;
      }
    }
		System.out.println("isListOperator: strange case: '" + subject + "'");
		throw new TomRuntimeException("isListOperator: strange case: '" + subject + "'");
  }

  protected static boolean isArrayOperator(TomSymbol subject) {
    //%variable
    if(subject==null) {
      return false;
    }
    %match(TomSymbol subject) {
      Symbol[option=l] -> {
        OptionList optionList = `l;
        while(!optionList.isEmptyconcOption()) {
          Option opt = optionList.getHeadconcOption();
          %match(Option opt) {
            DeclarationToOption(MakeEmptyArray[]) -> { return true; }
            DeclarationToOption(MakeAddArray[])   -> { return true; }
          }
          optionList = optionList.getTailconcOption();
        }
        return false;
      }
    }
		System.out.println("isArrayOperator: strange case: '" + subject + "'");
		throw new TomRuntimeException("isArrayOperator: strange case: '" + subject + "'");
  }

  // ------------------------------------------------------------
	%strategy collectVariable(collection:Collection) extends `Identity() {
		visit TomTerm {
			v@(Variable|VariableStar)[constraints=constraintList] -> {
				collection.add(`v);
				TomTerm annotedVariable = getAssignToVariable(`constraintList);
				if(annotedVariable!=null) {
					collection.add(annotedVariable);
				}
				`Fail().visit(`v);
			}

			v@(UnamedVariable|UnamedVariableStar)[constraints=constraintList] -> {
				TomTerm annotedVariable = getAssignToVariable(`constraintList);
				if(annotedVariable!=null) {
					collection.add(annotedVariable);
				}
				`Fail().visit(`v);
			}

			// to collect annoted nodes but avoid collect variables in optionSymbol
			t@RecordAppl[slots=subterms, constraints=constraintList] -> {
				collectVariable(collection,`subterms);
				TomTerm annotedVariable = getAssignToVariable(`constraintList);
				if(annotedVariable!=null) {
					collection.add(annotedVariable);
				}
				`Fail().visit(`t);
			}

		}
	}

  protected static void collectVariable(final Collection collection, ATerm subject) {
		try {
			MuTraveler.init(`mu(MuVar("x"),Try(Sequence(collectVariable(collection),All(MuVar("x")))))).visit(subject);
		} catch(jjtraveler.VisitFailure e) {
			System.out.println("strategy failed");
		}
  }

  public static Map collectMultiplicity(ATerm subject) {
    // collect variables
    ArrayList variableList = new ArrayList();
    collectVariable(variableList,subject);
    // compute multiplicities
    HashMap multiplicityMap = new HashMap();
    Iterator it = variableList.iterator();
    while(it.hasNext()) {
      TomTerm variable = (TomTerm)it.next();
      TomName name = variable.getAstName();
      if(multiplicityMap.containsKey(name)) {
        Integer value = (Integer)multiplicityMap.get(name);
        multiplicityMap.put(name, new Integer(1+value.intValue()));
      } else {
        multiplicityMap.put(name, new Integer(1));
      }
    }
    return multiplicityMap;
  }

  protected boolean isAnnotedVariable(TomTerm t) {
    %match(TomTerm t) {
      (RecordAppl|Variable|VariableStar|UnamedVariable|UnamedVariableStar)[constraints=constraintList] -> {
        return getAssignToVariable(`constraintList)!=null;
      }
    }
    return false;
  }

  public static TomTerm getAssignToVariable(ConstraintList constraintList) {
    %match(ConstraintList constraintList) {
      concConstraint(_*,AssignTo(var@Variable[]),_*) -> { return `var; }
    }
    return null;
  }

  protected static Declaration getIsFsymDecl(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,DeclarationToOption(decl@IsFsymDecl[]),_*) -> { return `decl; }
    }
    return null;
  }
  
	protected static String getModuleName(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,ModuleName(moduleName),_*) -> { return `moduleName; }
    }
    return null;
  }

  protected static String getDebug(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,Debug(Name(str)),_*) -> { return `str; }
    }
    return null;
  }

  protected static boolean hasGeneratedMatch(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,GeneratedMatch(),_*) -> { return true; }
    }
    return false;
  }

  protected static boolean hasConstant(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,Constant[],_*) -> { return true; }
    }
    return false;
  }

  protected static boolean hasDefinedSymbol(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,DefinedSymbol(),_*) -> { return true; }
    }
    return false;
  }

  protected static boolean hasImplicitXMLAttribut(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,ImplicitXMLAttribut(),_*) -> { return true; }
    }
    return false;
  }

  protected static boolean hasImplicitXMLChild(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,ImplicitXMLChild(),_*) -> { return true; }
    }
    return false;
  } 

  /*
  protected boolean hasGetHead(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,DeclarationToOption(GetHeadDecl[]),_*) -> { return true; }
    }
    return false;
  } 

  protected boolean hasGetTail(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,DeclarationToOption(GetTailDecl[]),_*) -> { return true; }
    }
    return false;
  } 

  protected boolean hasIsEmpty(OptionList optionList) {
    %match(OptionList optionList) {
      concOption(_*,DeclarationToOption(IsEmptyDecl[]),_*) -> { return true; }
    }
    return false;
  } 
*/

  protected static TomName getSlotName(TomSymbol symbol, int number) {
    PairNameDeclList pairNameDeclList = symbol.getPairNameDeclList();
    for(int index = 0; !pairNameDeclList.isEmptyconcPairNameDecl() && index<number ; index++) {
      pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
    }
    if(pairNameDeclList.isEmptyconcPairNameDecl()) {
      System.out.println("getSlotName: bad index error");
      throw new TomRuntimeException("getSlotName: bad index error");
    }
    PairNameDecl pairNameDecl = pairNameDeclList.getHeadconcPairNameDecl();

    Declaration decl = pairNameDecl.getSlotDecl();
    %match(Declaration decl) {
      GetSlotDecl[slotName=name] -> { return `name; }
    }

    return pairNameDecl.getSlotName();
  }

  protected static int getSlotIndex(TomSymbol tomSymbol, TomName slotName) {
    int index = 0;
    PairNameDeclList pairNameDeclList = tomSymbol.getPairNameDeclList();
    while(!pairNameDeclList.isEmptyconcPairNameDecl()) {
      TomName name = pairNameDeclList.getHeadconcPairNameDecl().getSlotName();
      // System.out.println("index = " + index + " name = " + name);
      if(slotName.equals(name)) {
        return index; 
      }
      pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
      index++;
    }
    return -1;
  }

  protected static TomType getSlotType(TomSymbol symbol, TomName slotName) {
    %match(TomSymbol symbol) {
      Symbol[typesToType=TypesToType(typeList,codomain)] -> {
        int index = getSlotIndex(symbol,slotName);
        return (TomType)`typeList.elementAt(index);
      }
    }
    throw new TomRuntimeException("getSlotType: bad slotName error");
  }

  protected static boolean isDefinedSymbol(TomSymbol subject) {
    if(subject==null) {
      System.out.println("isDefinedSymbol: subject == null");
      return false;
    }
    %match(TomSymbol subject) {
      Symbol[option=optionList] -> {
        return hasDefinedSymbol(`optionList);
      }
    }
    return false;
  }

  protected static boolean isDefinedGetSlot(TomSymbol symbol, TomName slotName) {
    if(symbol==null) {
      System.out.println("isDefinedSymbol: symbol == null");
      return false;
    }
    %match(TomSymbol symbol) {
      Symbol[pairNameDeclList=concPairNameDecl(_*,PairNameDecl[slotName=name,slotDecl=decl],_*)] -> {
        if(`name==slotName && `decl!=`EmptyDeclaration()) {
          return true;
        }
      }
    }
    return false;
  }


  // findOriginTracking(_) return the option containing OriginTracking information
  protected static Option findOriginTracking(OptionList optionList) {
    if(optionList.isEmptyconcOption()) {
      return `noOption();
    }
    while(!optionList.isEmptyconcOption()) {
      Option subject = optionList.getHeadconcOption();
      %match(Option subject) {
        orgTrack@OriginTracking[] -> {
          return `orgTrack;
        }
      }
      optionList = optionList.getTailconcOption();
    }
    System.out.println("findOriginTracking:  not found" + optionList);
    throw new TomRuntimeException("findOriginTracking:  not found" + optionList);
  }

  protected static TomSymbol getSymbolFromName(String tomName, SymbolTable symbolTable) {
    return symbolTable.getSymbolFromName(tomName);
  }
  
  protected static TomSymbol getSymbolFromType(TomType tomType, SymbolTable symbolTable) {
    SymbolList list = symbolTable.getSymbolFromType(tomType);
    SymbolList filteredList = `emptySymbolList();
    // Not necessary since checker ensure the uniqueness of the symbol
    while(!list.isEmptyconcSymbol()) {
      TomSymbol head = list.getHeadconcSymbol();
      if(isArrayOperator(head) || isListOperator(head)) {
        filteredList = `concSymbol(head,filteredList*);
      }
      list = list.getTailconcSymbol();
    }
    return filteredList.getHeadconcSymbol();
  }

  protected static TomType getTermType(TomTerm t, SymbolTable symbolTable){
    %match(TomTerm t) {
      RecordAppl[nameList=(Name(tomName),_*)] -> {
        TomSymbol tomSymbol = symbolTable.getSymbolFromName(`tomName);
        return tomSymbol.getTypesToType().getCodomain();
      }

      (Variable|VariableStar|UnamedVariable|UnamedVariableStar)[astType=type] -> { 
        return `type; 
      }

      Ref(term) -> { return getTermType(`term, symbolTable); }

      TargetLanguageToTomTerm[tl=(TL|ITL)[]] -> { return `EmptyType(); }

      FunctionCall[] -> { return `EmptyType(); }
    }
		System.out.println("getTermType error on term: " + t);
		throw new TomRuntimeException("getTermType error on term: " + t);
  }
  
  protected static TomType getTermType(Expression t, SymbolTable symbolTable){
    %match(Expression t) {
      (GetSubterm|GetHead|GetSlot|GetElement)[codomain=type] -> { return `type; }

      TomTermToExpression(term) -> { return getTermType(`term, symbolTable); }
      GetTail[variable=term] -> { return getTermType(`term, symbolTable); }
      GetSliceList[variableBeginAST=term] -> { return getTermType(`term, symbolTable); }
      GetSliceArray[subjectListName=term] -> { return getTermType(`term, symbolTable); }
    }
		System.out.println("getTermType error on term: " + t);
		throw new TomRuntimeException("getTermType error on term: " + t);
  }

  protected static SlotList tomListToSlotList(TomList tomList) {
    return tomListToSlotList(tomList,1);
  }

  protected static SlotList tomListToSlotList(TomList tomList, int index) {
    %match(TomList tomList) {
      concTomTerm() -> { return `concSlot(); }
      concTomTerm(head,tail*) -> { 
        TomName slotName = `PositionName(concTomNumber(Number(index)));
        SlotList sl = tomListToSlotList(tail,index+1);
        return `concSlot(PairSlotAppl(slotName,head),sl*); 
      }
    }
    throw new TomRuntimeException("tomListToSlotList: " + tomList);
  }

  protected static SlotList mergeTomListWithSlotList(TomList tomList, SlotList slotList) {
    %match(TomList tomList, SlotList slotList) {
      concTomTerm(), concSlot() -> { 
        return `concSlot(); 
      }
      concTomTerm(head,tail*), concSlot(PairSlotAppl[slotName=slotName],tailSlotList*) -> { 
        SlotList sl = mergeTomListWithSlotList(tail,tailSlotList);
        return `concSlot(PairSlotAppl(slotName,head),sl*); 
      }
    }
    throw new TomRuntimeException("mergeTomListWithSlotList: " + tomList + " and " + slotList);
  }

  protected static TomList slotListToTomList(SlotList tomList) {
    %match(SlotList tomList) {
      concSlot() -> { return `concTomTerm(); }
      concSlot(PairSlotAppl[appl=head],tail*) -> {
        TomList tl = slotListToTomList(tail);
        return `concTomTerm(head,tl*);
      }
    }
    throw new TomRuntimeException("slotListToTomList: " + tomList);
  }

} // class TomBase
