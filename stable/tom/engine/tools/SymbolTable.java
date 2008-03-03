/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2008, INRIA
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

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file */    private static   tom.engine.adt.tomsignature.types.TomSymbolList  tom_append_list_concTomSymbol( tom.engine.adt.tomsignature.types.TomSymbolList l1,  tom.engine.adt.tomsignature.types.TomSymbolList  l2) {     if( l1.isEmptyconcTomSymbol() ) {       return l2;     } else if( l2.isEmptyconcTomSymbol() ) {       return l1;     } else if(  l1.getTailconcTomSymbol() .isEmptyconcTomSymbol() ) {       return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( l1.getHeadconcTomSymbol() ,l2) ;     } else {       return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( l1.getHeadconcTomSymbol() ,tom_append_list_concTomSymbol( l1.getTailconcTomSymbol() ,l2)) ;     }   }   private static   tom.engine.adt.tomsignature.types.TomSymbolList  tom_get_slice_concTomSymbol( tom.engine.adt.tomsignature.types.TomSymbolList  begin,  tom.engine.adt.tomsignature.types.TomSymbolList  end, tom.engine.adt.tomsignature.types.TomSymbolList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomSymbol()  ||  (end== tom.engine.adt.tomsignature.types.tomsymbollist.EmptyconcTomSymbol.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( begin.getHeadconcTomSymbol() ,( tom.engine.adt.tomsignature.types.TomSymbolList )tom_get_slice_concTomSymbol( begin.getTailconcTomSymbol() ,end,tail)) ;   }      private static   tom.engine.adt.tomsignature.types.TomEntryList  tom_append_list_concTomEntry( tom.engine.adt.tomsignature.types.TomEntryList l1,  tom.engine.adt.tomsignature.types.TomEntryList  l2) {     if( l1.isEmptyconcTomEntry() ) {       return l2;     } else if( l2.isEmptyconcTomEntry() ) {       return l1;     } else if(  l1.getTailconcTomEntry() .isEmptyconcTomEntry() ) {       return  tom.engine.adt.tomsignature.types.tomentrylist.ConsconcTomEntry.make( l1.getHeadconcTomEntry() ,l2) ;     } else {       return  tom.engine.adt.tomsignature.types.tomentrylist.ConsconcTomEntry.make( l1.getHeadconcTomEntry() ,tom_append_list_concTomEntry( l1.getTailconcTomEntry() ,l2)) ;     }   }   private static   tom.engine.adt.tomsignature.types.TomEntryList  tom_get_slice_concTomEntry( tom.engine.adt.tomsignature.types.TomEntryList  begin,  tom.engine.adt.tomsignature.types.TomEntryList  end, tom.engine.adt.tomsignature.types.TomEntryList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomEntry()  ||  (end== tom.engine.adt.tomsignature.types.tomentrylist.EmptyconcTomEntry.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomsignature.types.tomentrylist.ConsconcTomEntry.make( begin.getHeadconcTomEntry() ,( tom.engine.adt.tomsignature.types.TomEntryList )tom_get_slice_concTomEntry( begin.getTailconcTomEntry() ,end,tail)) ;   }      private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {     if( l1.isEmptyconcOption() ) {       return l2;     } else if( l2.isEmptyconcOption() ) {       return l1;     } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;     } else {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end== tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;   }    

  private final static String TYPE_INT       = "int";
  private final static String TYPE_LONG      = "long";
  private final static String TYPE_FLOAT     = "float";
  private final static String TYPE_CHAR      = "char";
  private final static String TYPE_DOUBLE    = "double";
  private final static String TYPE_STRING    = "String";
  private final static String TYPE_BOOLEAN   = "boolean";
  private final static String TYPE_UNIVERSAL = "universal";
  private final static String TYPE_VOID      = "void";

  /** associate a symbol to a name */
  private Map<String,TomSymbol> mapSymbolName = null;
  /** associate a type to a name */
  private Map<String,TomTypeDefinition> mapTypeName = null;
  /** store symbols and types that are used */ 
  private Set<KeyEntry> usedKeyEntry = null;

  private boolean cCode = false;
  private boolean jCode = false;
  private boolean camlCode = false;
  private boolean pCode = false;

  public void init(OptionManager optionManager) {
    mapSymbolName = new HashMap<String,TomSymbol>();
    mapTypeName = new HashMap<String,TomTypeDefinition>();
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
    TomSymbol res = mapSymbolName.get(name);
    return res;
  }

  public TomSymbolList getSymbolFromType(TomType type) {
    TomSymbolList res =  tom.engine.adt.tomsignature.types.tomsymbollist.EmptyconcTomSymbol.make() ;
    Iterator<TomSymbol> it = mapSymbolName.values().iterator();
    while(it.hasNext()) {
      TomSymbol symbol = it.next();
      if(symbol.getTypesToType().getCodomain() == type) {
        res =  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make(symbol,tom_append_list_concTomSymbol(res, tom.engine.adt.tomsignature.types.tomsymbollist.EmptyconcTomSymbol.make() )) ;
      }
    }
    return res;
  }

  public void putTypeDefinition(String name, TomType astType, TomForwardType fwdType) {
    TomTypeDefinition typeDef =  tom.engine.adt.tomtype.types.tomtypedefinition.TypeDefinition.make(astType, fwdType) ;
    mapTypeName.put(name,typeDef);
  }

  public Collection<TomTypeDefinition> getUsedTypes() {
    return mapTypeName.values();
  }

  public TomTypeDefinition getTypeDefinition(String name) {
    TomTypeDefinition def = mapTypeName.get(name);
    return def;
  }

  public TomType getType(String name) {
    TomTypeDefinition def = getTypeDefinition(name);
    if (def != null) {
      TomType result = def.getTomType();
      return result;
    } else {
      return null;
    } 
  }

  public TomForwardType getForwardType(String name) {
    TomTypeDefinition def = getTypeDefinition(name);
    if (def != null) {
      TomForwardType result = def.getForward();
      return result;
    } else { 
      return null;
    } 
  }

  public boolean isUsedSymbolConstructor(TomSymbol symbol) {
    return usedKeyEntry.contains( tom.engine.adt.tomsignature.types.keyentry.UsedSymbolConstructor.make(symbol) );
  }

  public boolean isUsedSymbolDestructor(TomSymbol symbol) {
    return usedKeyEntry.contains( tom.engine.adt.tomsignature.types.keyentry.UsedSymbolDestructor.make(symbol) );
  }

  public boolean isUsedTypeDefinition(TomTypeDefinition type) {
    return usedKeyEntry.contains( tom.engine.adt.tomsignature.types.keyentry.UsedTypeDefinition.make(type) );
  }

  public void setUsedSymbolConstructor(TomSymbol symbol) {
    usedKeyEntry.add( tom.engine.adt.tomsignature.types.keyentry.UsedSymbolConstructor.make(symbol) );
  }

  public void setUsedSymbolDestructor(TomSymbol symbol) {
    usedKeyEntry.add( tom.engine.adt.tomsignature.types.keyentry.UsedSymbolDestructor.make(symbol) );
  }

  public void setUsedTypeDefinition(TomTypeDefinition type) {
    usedKeyEntry.add( tom.engine.adt.tomsignature.types.keyentry.UsedTypeDefinition.make(type) );
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

  public void setUsedTypeDefinition(String name) {
    TomTypeDefinition type = getTypeDefinition(name);
    if(type!=null) {
      setUsedTypeDefinition(type);
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

  public boolean isUsedTypeDefinition(String name) {
    TomTypeDefinition type = getTypeDefinition(name);
    if(type!=null) {
      return isUsedTypeDefinition(type);
    }
    return false;
  }

  public TomType getIntType() {
    return ASTFactory.makeType(TYPE_INT,"int");
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
    if(pCode) {
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

  public boolean isIntType(String type) {
    return type.equals(TYPE_INT);
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

  public String builtinToWrapper(String type) {
    {if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "byte".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Byte"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "short".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Short"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "int".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Integer"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "long".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Long"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "float".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Float"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "double".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Double"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "boolean".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Boolean"; }}}}if ( true ) {{  String  tomMatch227NameNumberfreshSubject_1=(( String )type);if ( "char".equals(tomMatch227NameNumberfreshSubject_1) ) {if ( true ) {
 return "java.lang.Character"; }}}}}

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
    {if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {{  tom.engine.adt.tomtype.types.TomType  tomMatch228NameNumberfreshSubject_1=(( tom.engine.adt.tomtype.types.TomType )type);if ( (tomMatch228NameNumberfreshSubject_1 instanceof tom.engine.adt.tomtype.types.tomtype.TomTypeAlone) ) {{  String  tomMatch228NameNumber_freshVar_0= tomMatch228NameNumberfreshSubject_1.getString() ;if ( true ) {

        return isNumericType(tomMatch228NameNumber_freshVar_0);
      }}}}}}

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
    Iterator<String> it = keys.iterator();
    return it;
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
    TomEntryList list =  tom.engine.adt.tomsignature.types.tomentrylist.EmptyconcTomEntry.make() ;
    for(String name:mapSymbolName.keySet()) {
      TomSymbol symbol = getSymbolFromName(name);
      TomEntry entry =  tom.engine.adt.tomsignature.types.tomentry.Entry.make(name, symbol) ;
      list =  tom.engine.adt.tomsignature.types.tomentrylist.ConsconcTomEntry.make(entry,tom_append_list_concTomEntry(list, tom.engine.adt.tomsignature.types.tomentrylist.EmptyconcTomEntry.make() )) ;
    }
    return  tom.engine.adt.tomsignature.types.tomsymboltable.Table.make(list) ;
  }

  public TomSymbol updateConstrainedSymbolCodomain(TomSymbol symbol, SymbolTable symbolTable) {
    {if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {{  tom.engine.adt.tomsignature.types.TomSymbol  tomMatch229NameNumberfreshSubject_1=(( tom.engine.adt.tomsignature.types.TomSymbol )symbol);if ( (tomMatch229NameNumberfreshSubject_1 instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {{  tom.engine.adt.tomname.types.TomName  tomMatch229NameNumber_freshVar_0= tomMatch229NameNumberfreshSubject_1.getAstName() ;{  tom.engine.adt.tomtype.types.TomType  tomMatch229NameNumber_freshVar_1= tomMatch229NameNumberfreshSubject_1.getTypesToType() ;{  tom.engine.adt.tomslot.types.PairNameDeclList  tomMatch229NameNumber_freshVar_2= tomMatch229NameNumberfreshSubject_1.getPairNameDeclList() ;{  tom.engine.adt.tomoption.types.OptionList  tomMatch229NameNumber_freshVar_3= tomMatch229NameNumberfreshSubject_1.getOption() ;{  tom.engine.adt.tomname.types.TomName  tom_name=tomMatch229NameNumber_freshVar_0;if ( (tomMatch229NameNumber_freshVar_1 instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {{  tom.engine.adt.tomtype.types.TomTypeList  tomMatch229NameNumber_freshVar_4= tomMatch229NameNumber_freshVar_1.getDomain() ;{  tom.engine.adt.tomtype.types.TomType  tomMatch229NameNumber_freshVar_5= tomMatch229NameNumber_freshVar_1.getCodomain() ;if ( (tomMatch229NameNumber_freshVar_5 instanceof tom.engine.adt.tomtype.types.tomtype.Codomain) ) {{  tom.engine.adt.tomname.types.TomName  tomMatch229NameNumber_freshVar_6= tomMatch229NameNumber_freshVar_5.getAstName() ;if ( (tomMatch229NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {{  String  tomMatch229NameNumber_freshVar_7= tomMatch229NameNumber_freshVar_6.getString() ;{  tom.engine.adt.tomoption.types.OptionList  tom_options=tomMatch229NameNumber_freshVar_3;if ( true ) {

        //System.out.println("update codomain: " + `name);
        //System.out.println("depend from : " + `opName);
        TomSymbol dependSymbol = symbolTable.getSymbolFromName(tomMatch229NameNumber_freshVar_7);
        //System.out.println("1st depend codomain: " + TomBase.getSymbolCodomain(dependSymbol));
        dependSymbol = updateConstrainedSymbolCodomain(dependSymbol,symbolTable);
        TomType codomain = TomBase.getSymbolCodomain(dependSymbol);
        //System.out.println("2nd depend codomain: " + TomBase.getSymbolCodomain(dependSymbol));
        OptionList newOptions = tom_options;
        {if ( (tom_options instanceof tom.engine.adt.tomoption.types.OptionList) ) {{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumberfreshSubject_1=(( tom.engine.adt.tomoption.types.OptionList )tom_options);if ( ((tomMatch230NameNumberfreshSubject_1 instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || (tomMatch230NameNumberfreshSubject_1 instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) {{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumber_freshVar_0=tomMatch230NameNumberfreshSubject_1;{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumber_begin_2=tomMatch230NameNumber_freshVar_0;{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumber_end_3=tomMatch230NameNumber_freshVar_0;do {{{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumber_freshVar_1=tomMatch230NameNumber_end_3;if (!( tomMatch230NameNumber_freshVar_1.isEmptyconcOption() )) {if ( ( tomMatch230NameNumber_freshVar_1.getHeadconcOption()  instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {{  tom.engine.adt.tomdeclaration.types.Declaration  tomMatch230NameNumber_freshVar_6=  tomMatch230NameNumber_freshVar_1.getHeadconcOption() .getAstDeclaration() ;if ( (tomMatch230NameNumber_freshVar_6 instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeDecl) ) {{  tom.engine.adt.tomtype.types.TomType  tomMatch230NameNumber_freshVar_7= tomMatch230NameNumber_freshVar_6.getAstType() ;if ( (tomMatch230NameNumber_freshVar_7 instanceof tom.engine.adt.tomtype.types.tomtype.Codomain) ) {{  tom.engine.adt.tomoption.types.OptionList  tomMatch230NameNumber_freshVar_4= tomMatch230NameNumber_freshVar_1.getTailconcOption() ;if ( true ) {

            Declaration newMake = tomMatch230NameNumber_freshVar_6.setAstType(codomain);
            //System.out.println("newMake: " + newMake);
            newOptions = tom_append_list_concOption(tom_get_slice_concOption(tomMatch230NameNumber_begin_2,tomMatch230NameNumber_end_3, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ),tom_append_list_concOption(tomMatch230NameNumber_freshVar_4, tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( tom.engine.adt.tomoption.types.option.DeclarationToOption.make(newMake) , tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ));
          }}}}}}}}}if ( tomMatch230NameNumber_end_3.isEmptyconcOption() ) {tomMatch230NameNumber_end_3=tomMatch230NameNumber_begin_2;} else {tomMatch230NameNumber_end_3= tomMatch230NameNumber_end_3.getTailconcOption() ;}}} while(!( (tomMatch230NameNumber_end_3==tomMatch230NameNumber_begin_2) ));}}}}}}}

        TomSymbol newSymbol =  tom.engine.adt.tomsignature.types.tomsymbol.Symbol.make(tom_name,  tom.engine.adt.tomtype.types.tomtype.TypesToType.make(tomMatch229NameNumber_freshVar_4, codomain) , tomMatch229NameNumber_freshVar_2, newOptions) ;
        //System.out.println("newSymbol: " + newSymbol);
        symbolTable.putSymbol(tom_name.getString(),newSymbol);
        return newSymbol;
      }}}}}}}}}}}}}}}}}}

    return symbol;
  }

  /*
   * Inlining
   */

  /** associate an inliner to a name */
  private Map<String,String> mapInliner = null;

  private static String prefixIsFsym = "is_fsym_";
  private static String prefixGetSlot = "get_slot_";
  private static String prefixGetHead = "get_head_";
  private static String prefixGetTail = "get_tail_";
  private static String prefixGetElementArray = "get_element_array_";
  private static String prefixGetSizeArray = "get_size_array_";
  private static String prefixIsEmptyList = "is_empty_list_";
  private static String prefixIsSort = "is_sort_";
  private static String prefixMake = "make_";
  private static String prefixMakeEmptyArray = "make_empty_array_";
  private static String prefixMakeEmptyList = "make_empty_list_";
  private static String prefixMakeAddArray = "make_append_";
  private static String prefixMakeAddList = "make_insert_";
  private static String prefixEqualTerm = "equal_";

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

}
