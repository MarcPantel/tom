/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2010, INPL, INRIA
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

import java.util.*;

import tom.engine.exception.TomRuntimeException;

import tom.engine.TomBase;
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

import tom.platform.OptionManager;

public class SymbolTable {

  %include { ../adt/tomsignature/TomSignature.tom }

  private final static String TYPE_INT       = "int";
  private final static String TYPE_LONG      = "long";
  private final static String TYPE_FLOAT     = "float";
  private final static String TYPE_CHAR      = "char";
  private final static String TYPE_DOUBLE    = "double";
  private final static String TYPE_STRING    = "String";
  private final static String TYPE_BOOLEAN   = "boolean";
  private final static String TYPE_UNIVERSAL = "universal";
  private final static String TYPE_VOID      = "void";
  private final static String TYPE_INT_ARRAY = "intarray";
  private final static String INT_ARRAY_OP   = "concInt";

  public final static TomType TYPE_UNKNOWN   = `Type("unknown type",EmptyType());
  public final static String TYPENAME_UNKNOWN   = "unknown type";

  /** associate a symbol to a name */
  private Map<String,TomSymbol> mapSymbolName = null;
  /** associate a type to a name */
  private Map<String,TomType> mapTypeName = null;
  /** store symbols and types that are used */ 
  private Set<KeyEntry> usedKeyEntry = null;

  private boolean cCode = false;
  private boolean jCode = false;
  private boolean camlCode = false;
  private boolean pCode = false;

  public void init(OptionManager optionManager) {
    mapSymbolName = new HashMap<String,TomSymbol>();
    mapTypeName = new HashMap<String,TomType>();
    usedKeyEntry = new HashSet<KeyEntry>();
    mapInliner = new HashMap<String,String>();

    if( ((Boolean)optionManager.getOptionValue("cCode")).booleanValue() ) {
      cCode = true;
    } else if( ((Boolean)optionManager.getOptionValue("jCode")).booleanValue() ) {
      jCode = true;
    } else if( ((Boolean)optionManager.getOptionValue("camlCode")).booleanValue() ) {
      camlCode = true;
    } else if( ((Boolean)optionManager.getOptionValue("pCode")).booleanValue() ) {
      pCode = true;
    }

  }

  public void regenerateFromTerm(TomSymbolTable symbTable) {
    TomEntryList list =  symbTable.getEntryList();
    while(!list.isEmptyconcTomEntry()) {
      TomEntry symb = list.getHeadconcTomEntry();
      putSymbol(symb.getStrName(), symb.getAstSymbol());
      list = list.getTailconcTomEntry();
    }
  }

  public void putSymbol(String name, TomSymbol astSymbol) {
    TomSymbol result = mapSymbolName.put(name,astSymbol);
  }

  public TomSymbol getSymbolFromName(String name) {
    return mapSymbolName.get(name);
    
  }

  public TomSymbolList getSymbolFromType(TomType type) {
    TomSymbolList res = `concTomSymbol();
    Iterator<TomSymbol> it = mapSymbolName.values().iterator();
    while(it.hasNext()) {
      TomSymbol symbol = it.next();
      if(symbol.getTypesToType().getCodomain() == type) {
        res = `concTomSymbol(symbol,res*);
      }
    }
    return res;
  }

  public void putType(String name, TomType astType) {
    mapTypeName.put(name,astType);
  }

  public Collection<TomType> getUsedTypes() {
    return mapTypeName.values();
  }

  public TomType getType(String name) {
    return mapTypeName.get(name);
  }

  public boolean isUsedSymbolConstructor(TomSymbol symbol) {    
    return usedKeyEntry.contains(`UsedSymbolConstructor(symbol));
  }

  public boolean isUsedSymbolDestructor(TomSymbol symbol) {
    return usedKeyEntry.contains(`UsedSymbolDestructor(symbol));
  }

  public boolean isUsedSymbolAC(TomSymbol symbol) {
    return usedKeyEntry.contains(`UsedSymbolAC(symbol));
  }

  public boolean isUsedType(TomType type) {
    return usedKeyEntry.contains(`UsedType(type));
  }

  public void setUsedSymbolConstructor(TomSymbol symbol) {
    usedKeyEntry.add(`UsedSymbolConstructor(symbol));
  }

  public void setUsedSymbolDestructor(TomSymbol symbol) {
    usedKeyEntry.add(`UsedSymbolDestructor(symbol));
  }

  public void setUsedSymbolAC(TomSymbol symbol) {
    usedKeyEntry.add(`UsedSymbolAC(symbol));
  }

  public void setUsedType(TomType type) {
    usedKeyEntry.add(`UsedType(type));
  }

  public void setUsedSymbolConstructor(String name) {
    TomSymbol symbol = getSymbolFromName(name);
    if(symbol!=null) {
      setUsedSymbolConstructor(symbol);
    }
  }

  public void setUsedSymbolDestructor(String name) {
    TomSymbol symbol = getSymbolFromName(name);
    if(symbol!=null) {
      setUsedSymbolDestructor(symbol);
    }
  }

  public void setUsedSymbolAC(String name) {
    TomSymbol symbol = getSymbolFromName(name);
    if(symbol!=null) {
      setUsedSymbolAC(symbol);
    }
  }

  public void setUsedType(String name) {
    TomType type = getType(name);
    if(type!=null) {
      setUsedType(type);
    }
  }

  public boolean isUsedSymbolConstructor(String name) {
    TomSymbol symbol = getSymbolFromName(name);
    if(symbol!=null) {
      return isUsedSymbolConstructor(symbol);
    }
    return false;
  }

  public boolean isUsedSymbolDestructor(String name) {
    TomSymbol symbol = getSymbolFromName(name);
    if(symbol!=null) {
      return isUsedSymbolDestructor(symbol);
    }
    return false;
  }

  public boolean isUsedType(String name) {
    TomType type = getType(name);
    if(type!=null) {
      return isUsedType(type);
    }
    return false;
  }

  public TomType getIntType() {
    return ASTFactory.makeType(TYPE_INT,"int");
  }

  public TomType getIntArrayType() {
    return ASTFactory.makeType(TYPE_INT_ARRAY,"int[]");
  }

  public TomType getLongType() {
    return ASTFactory.makeType(TYPE_LONG,"long");
  }

  public TomType getFloatType() {
    return ASTFactory.makeType(TYPE_FLOAT,"float");
  }

  public TomType getCharType() {
    String type = "char";
    if(pCode) {
      type = "str";
    }
    return ASTFactory.makeType(TYPE_CHAR,type);
  }

  public TomType getDoubleType() {
    String type = "double";
    if(pCode) {
      type = "float";
    }
    return ASTFactory.makeType(TYPE_DOUBLE,type);
  }

  public TomType getBooleanType() {
    String type = "boolean";
    if(cCode) {
      type = "int";
    } else if(camlCode) {
      type = "bool";
    } else if(pCode) {
      type = "bool";
    } 
    return ASTFactory.makeType(TYPE_BOOLEAN,type);
  }

  public TomType getStringType() {
    String type = "String";
    if(cCode) {
      type = "char*";
    } else if(pCode) {
      type = "str";
    } 
    return ASTFactory.makeType(TYPE_STRING,type);
  }

  public TomType getUniversalType() {
    String type = "Object";
    if(cCode) {
      type = "void*";
    } else if(camlCode) {
      type = "None";
    } else if(pCode) {
      type = "None";
    }
    return ASTFactory.makeType(TYPE_UNIVERSAL,type);
  }

  public TomType getVoidType() {
    String type = "void";
    if(camlCode) {
      type = "unit";
    } else if(pCode) {
      type = "function";
    }
    return ASTFactory.makeType(TYPE_VOID,type);
  }

  //public TomType getUnknownType() {
  //  return `Type(TYPE_UNKNOWN,EmptyType());
  //}

  public boolean isIntType(String type) {
    return type.equals(TYPE_INT);
  }

  public boolean isIntArrayType(String type) {
    return type.equals(TYPE_INT_ARRAY);
  }

  public boolean isLongType(String type) {
    return type.equals(TYPE_LONG);
  }

  public boolean isFloatType(String type) {
    return type.equals(TYPE_FLOAT);
  }

  public boolean isCharType(String type) {
    return type.equals(TYPE_CHAR);
  }

  public boolean isStringType(String type) {
    return type.equals(TYPE_STRING);
  }

  public boolean isBooleanType(String type) {
    return type.equals(TYPE_BOOLEAN);
  }

  public boolean isDoubleType(String type) {
    return type.equals(TYPE_DOUBLE);
  }

  public boolean isVoidType(String type) {
    return type.equals(TYPE_VOID);
  }
  
  public boolean isUnknownType(String type) {
    return `Type(type,EmptyType()).equals(TYPE_UNKNOWN);
  }
 
  public boolean isUnknownTypeName(String type) {
    return type.equals(TYPENAME_UNKNOWN);
  }

  public String builtinToWrapper(String type) {
    %match(type) {
      "byte" -> { return "java.lang.Byte"; }
      "short" -> { return "java.lang.Short"; }
      "int" -> { return "java.lang.Integer"; }
      "long" -> { return "java.lang.Long"; }
      "float" -> { return "java.lang.Float"; }
      "double" -> { return "java.lang.Double"; }
      "boolean" -> { return "java.lang.Boolean"; }
      "char" -> { return "java.lang.Character"; }
    }
    return type;
  }

  public boolean isBuiltinType(String type) {
    return isIntType(type) || isLongType(type) || isCharType(type) ||
      isStringType(type) || isBooleanType(type) || isDoubleType(type) ||
      isFloatType(type);
  }

  public boolean isNumericType(String type) {
    return isIntType(type) || isLongType(type) || isDoubleType(type) || isFloatType(type);
  }

  public boolean isNumericType(TomType type) {    
    %match(type){
      Type(str,EmptyType()) -> {
        return isNumericType(`str);
      }
    }
    if (type.equals(getIntType()) 
        || type.equals(getLongType()) 
        || type.equals(getFloatType())  
        || type.equals(getDoubleType())) {
      return true;
    }
    return false;    
  }

  public TomType getBuiltinType(String type) {
    if(isIntType(type)) {
      return getIntType();
    } else if(isLongType(type)) {
      return getLongType();
    } else if(isCharType(type)) {
      return getCharType();
    } else if(isStringType(type)) {
      return getStringType();
    } else if(isBooleanType(type)) {
      return getBooleanType();
    } else if(isDoubleType(type)) {
      return getDoubleType();
    } else if(isFloatType(type)) {
      return getFloatType();
    }
    System.out.println("Not a builtin type: " + type);
    throw new TomRuntimeException("getBuiltinType error on term: " + type);
  }

  public Iterator<String> keySymbolIterator() {
    Set<String> keys = mapSymbolName.keySet();
    return keys.iterator();
  }

  public void fromTerm(TomSymbolTable table) {
    TomEntryList list = table.getEntryList();
    while(!list.isEmptyconcTomEntry()) {
      TomEntry entry = list.getHeadconcTomEntry();
      putSymbol(entry.getStrName(),entry.getAstSymbol());
      list = list.getTailconcTomEntry();
    }
  }

  public TomSymbolTable toTerm() {
    TomEntryList list = `concTomEntry();
    for(String name:mapSymbolName.keySet()) {
      TomSymbol symbol = getSymbolFromName(name);
      TomEntry entry = `Entry(name,symbol);
      list = `concTomEntry(entry,list*);
    }
    return `Table(list);
  }

  public TomSymbol updateConstrainedSymbolCodomain(TomSymbol symbol, SymbolTable symbolTable) {
    %match(symbol) {
      Symbol(name,TypesToType(domain,Codomain(opName)),slots,options) -> {
        //System.out.println("depend from : " + `opName);
        TomSymbol dependSymbol = symbolTable.getSymbolFromName(`opName);
        //System.out.println("1st depend codomain: " + TomBase.getSymbolCodomain(dependSymbol));
        dependSymbol = updateConstrainedSymbolCodomain(dependSymbol,symbolTable);
        TomType codomain = TomBase.getSymbolCodomain(dependSymbol);
        //System.out.println("2nd depend codomain: " + TomBase.getSymbolCodomain(dependSymbol));
        OptionList newOptions = `options;
        %match(options) {
          concOption(O1*,DeclarationToOption(m@MakeDecl[AstType=Codomain[]]),O2*) -> {
            Declaration newMake = `m.setAstType(codomain);
            //System.out.println("newMake: " + newMake);
            newOptions = `concOption(O1*,O2*,DeclarationToOption(newMake));
          }
        }
        TomSymbol newSymbol = `Symbol(name,TypesToType(domain,codomain),slots,newOptions);
        //System.out.println("newSymbol: " + newSymbol);
        symbolTable.putSymbol(`name.getString(),newSymbol);
        return newSymbol;
      }
    }
    return symbol;
  }

  /*
   * Inlining
   */

  /** associate an inliner to a name */
  private Map<String,String> mapInliner = null;

  private final static String prefixIsFsym = "is_fsym_";
  private final static String prefixGetSlot = "get_slot_";
  private final static String prefixGetHead = "get_head_";
  private final static String prefixGetTail = "get_tail_";
  private final static String prefixGetElementArray = "get_element_array_";
  private final static String prefixGetSizeArray = "get_size_array_";
  private final static String prefixIsEmptyList = "is_empty_list_";
  private final static String prefixIsSort = "is_sort_";
  private final static String prefixMake = "make_";
  private final static String prefixMakeEmptyArray = "make_empty_array_";
  private final static String prefixMakeEmptyList = "make_empty_list_";
  private final static String prefixMakeAddArray = "make_append_";
  private final static String prefixMakeAddList = "make_insert_";
  private final static String prefixEqualTerm = "equal_";

  private void putInliner(String prefix, String opname, String code) {
    mapInliner.put(prefix+opname,code);
  }

  private String getInliner(String prefix, String opname) {
    return mapInliner.get(prefix+opname);
  }

  public void putIsFsym(String opname, String code) {
    //System.out.println("putIsFsym: " + opname + " -> " + code);
    putInliner(prefixIsFsym,opname,code);
  }
  public String getIsFsym(String opname) {
    return getInliner(prefixIsFsym,opname);
  }

  public void putIsSort(String type, String code) {
    //System.out.println("putIsSort: " + type + " -> " + code);
    putInliner(prefixIsSort,type,code);
  }
  public String getIsSort(String type) {
    return getInliner(prefixIsSort,type);
  }

  public void putGetSlot(String opname, String slotname, String code) {
    putInliner(prefixGetSlot,opname+slotname,code);
  }
  public String getGetSlot(String opname, String slotname) {
    return getInliner(prefixGetSlot,opname+slotname);
  }

  public void putGetHead(String opname, String code) {
    putInliner(prefixGetHead,opname,code);
  }
  public String getGetHead(String opname) {
    return getInliner(prefixGetHead,opname);
  }

  public void putGetTail(String opname, String code) {
    putInliner(prefixGetTail,opname,code);
  }
  public String getGetTail(String opname) {
    return getInliner(prefixGetTail,opname);
  }

  public void putGetElementArray(String opname, String code) {
    putInliner(prefixGetElementArray,opname,code);
  }
  public String getGetElementArray(String opname) {
    return getInliner(prefixGetElementArray,opname);
  }

  public void putGetSizeArray(String opname, String code) {
    //System.out.println("put: " + opname + " -> " + code);
    putInliner(prefixGetSizeArray,opname,code);
  }
  public String getGetSizeArray(String opname) {
    //System.out.println("get: " + opname);
    return getInliner(prefixGetSizeArray,opname);
  }

  public void putIsEmptyList(String opname, String code) {
    putInliner(prefixIsEmptyList,opname,code);
  }
  public String getIsEmptyList(String opname) {
    return getInliner(prefixIsEmptyList,opname);
  }

  public void putMake(String opname, String code) {
    putInliner(prefixMake,opname,code);
  }
  public String getMake(String opname) {
    return getInliner(prefixMake,opname);
  }

  public void putMakeEmptyArray(String opname, String code) {
    putInliner(prefixMakeEmptyArray,opname,code);
  }
  public String getMakeEmptyArray(String opname) {
    return getInliner(prefixMakeEmptyArray,opname);
  }

  public void putMakeEmptyList(String opname, String code) {
    putInliner(prefixMakeEmptyList,opname,code);
  }
  public String getMakeEmptyList(String opname) {
    return getInliner(prefixMakeEmptyList,opname);
  }

  public void putMakeAddArray(String opname, String code) {
    putInliner(prefixMakeAddArray,opname,code);
  }
  public String getMakeAddArray(String opname) {
    return getInliner(prefixMakeAddArray,opname);
  }

  public void putMakeAddList(String opname, String code) {
    putInliner(prefixMakeAddList,opname,code);
  }
  public String getMakeAddList(String opname) {
    return getInliner(prefixMakeAddList,opname);
  }

  public void putEqualTerm(String type, String code) {
    putInliner(prefixEqualTerm,type,code);
  }
  public String getEqualTerm(String type) {
    return getInliner(prefixEqualTerm,type);
  }
  /*
     private static class Inliner {
     public String isfsym;
     public String getslot;
     }
   */
  public String getIntArrayOp() {
    return INT_ARRAY_OP;
  }

}
