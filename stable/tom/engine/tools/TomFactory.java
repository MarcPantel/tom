/* Generated by TOM: Do not edit this file */ /*
  
    TOM - To One Matching Compiler

    Copyright (C) 2000-2004 INRIA
			    Nancy, France.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

    Pierre-Etienne Moreau	e-mail: Pierre-Etienne.Moreau@loria.fr

*/

package jtom.tools;

import aterm.*;
import jtom.*;
import java.util.*;
import jtom.adt.tomsignature.types.*;
import jtom.xml.*;

public class TomFactory extends TomBase {

// ------------------------------------------------------------
  /* Generated by TOM: Do not edit this file */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
// ------------------------------------------------------------

  public TomFactory(jtom.TomEnvironment environment) {
    super(environment);
  }
  
  public String encodeXMLString(SymbolTable symbolTable, String name) {
    name = "\"" + name + "\"";
    ast().makeStringSymbol(symbolTable,name, new LinkedList());
    return name;
  }

  public TomList metaEncodeTermList(SymbolTable symbolTable,TomList list) {
     { TomList tom_match1_1 =(( TomList)list);{ if(tom_is_fun_sym_emptyTomList(tom_match1_1) ||  false ) {
  return tom_make_emptyTomList() ; } if(tom_is_fun_sym_manyTomList(tom_match1_1) ||  false ) { { TomTerm tom_match1_1_1 =tom_get_slot_manyTomList_head(tom_match1_1); { TomList tom_match1_1_2 =tom_get_slot_manyTomList_tail(tom_match1_1); { TomTerm head =tom_match1_1_1; { TomList tail =tom_match1_1_2;
 
        return tom_make_manyTomList(metaEncodeXMLAppl(symbolTable,head),metaEncodeTermList(symbolTable,tail))
 ;
      }}}} }}}
 
    return list;
  }

  public TomTerm encodeXMLAppl(SymbolTable symbolTable, TomTerm term) {
      /*
       * encode a String into a quoted-string
       * Appl(...,Name("string"),...) becomes
       * Appl(...,Name("\"string\""),...)
       */
    NameList newNameList = tom_make_empty_concTomName() ;
     { TomTerm tom_match2_1 =(( TomTerm)term);{ if(tom_is_fun_sym_Appl(tom_match2_1) ||  false ) { { NameList tom_match2_1_2 =tom_get_slot_Appl_nameList(tom_match2_1); if(tom_is_fun_sym_concTomName(tom_match2_1_2) ||  false ) { { NameList tom_match2_1_2_list1 =tom_match2_1_2; { NameList tom_match2_1_2_begin1 =tom_match2_1_2_list1; { NameList tom_match2_1_2_end1 =tom_match2_1_2_list1; do {{ if(!(tom_is_empty_NameList(tom_match2_1_2_list1))) { { TomName tom_match2_1_2_2 =(( TomName)tom_get_head_NameList(tom_match2_1_2_list1));{ tom_match2_1_2_list1=tom_get_tail_NameList(tom_match2_1_2_list1); if(tom_is_fun_sym_Name(tom_match2_1_2_2) ||  false ) { { String tom_match2_1_2_2_1 =tom_get_slot_Name_string(tom_match2_1_2_2); { String name =tom_match2_1_2_2_1;
 
        newNameList = (NameList)newNameList.append(tom_make_Name(encodeXMLString(symbolTable,name)) );
      }} }}} } if(!(tom_is_empty_NameList(tom_match2_1_2_end1))) { tom_match2_1_2_end1=tom_get_tail_NameList(tom_match2_1_2_end1); } tom_match2_1_2_list1=tom_match2_1_2_end1;} } while(!(tom_is_empty_NameList(tom_match2_1_2_list1)));}}} }} }}}
 
    term = term.setNameList(newNameList);
      //System.out.println("encodeXMLAppl = " + term);
    return term;
  }

  public TomTerm metaEncodeXMLAppl(SymbolTable symbolTable, TomTerm term) {
      /*
       * meta-encode a String into a TextNode
       * Appl(...,Name("\"string\""),...) becomes
       * Appl(...,Name("TextNode"),[Appl(...,Name("\"string\""),...)],...)
       */
      //System.out.println("metaEncode: " + term);
     { TomTerm tom_match3_1 =(( TomTerm)term);{ if(tom_is_fun_sym_Appl(tom_match3_1) ||  false ) { { NameList tom_match3_1_2 =tom_get_slot_Appl_nameList(tom_match3_1); if(tom_is_fun_sym_concTomName(tom_match3_1_2) ||  false ) { { NameList tom_match3_1_2_list1 =tom_match3_1_2; if(!(tom_is_empty_NameList(tom_match3_1_2_list1))) { { TomName tom_match3_1_2_1 =(( TomName)tom_get_head_NameList(tom_match3_1_2_list1));{ tom_match3_1_2_list1=tom_get_tail_NameList(tom_match3_1_2_list1); if(tom_is_fun_sym_Name(tom_match3_1_2_1) ||  false ) { { String tom_match3_1_2_1_1 =tom_get_slot_Name_string(tom_match3_1_2_1); { String tomName =tom_match3_1_2_1_1; if(tom_is_empty_NameList(tom_match3_1_2_list1)) {
 
          //System.out.println("tomName = " + tomName);
        TomSymbol tomSymbol = symbolTable.getSymbol(tomName);
        if(tomSymbol != null) {
          if(isStringOperator(tomSymbol)) {
            Option info = ast().makeOriginTracking(Constants.TEXT_NODE,-1,"??");
            term = tom_make_Appl(ast() .makeOption(info),tom_make_insert_concTomName(tom_make_Name(Constants .TEXT_NODE),tom_make_empty_concTomName()),tom_make_insert_concTomTerm(term,tom_make_empty_concTomTerm()))
 ;
              //System.out.println("metaEncodeXmlAppl = " + term);
          }
        }
       }}} }}} }} }} }}}
 
    return term;
  }

  public boolean isExplicitTermList(LinkedList childs) {
    if(childs.size() == 1) {
      TomTerm term = (TomTerm) childs.getFirst();
       { TomTerm tom_match4_1 =(( TomTerm)term);{ if(tom_is_fun_sym_Appl(tom_match4_1) ||  false ) { { NameList tom_match4_1_2 =tom_get_slot_Appl_nameList(tom_match4_1); { TomList tom_match4_1_3 =tom_get_slot_Appl_args(tom_match4_1); if(tom_is_fun_sym_concTomName(tom_match4_1_2) ||  false ) { { NameList tom_match4_1_2_list1 =tom_match4_1_2; if(!(tom_is_empty_NameList(tom_match4_1_2_list1))) { { TomName tom_match4_1_2_1 =(( TomName)tom_get_head_NameList(tom_match4_1_2_list1));{ tom_match4_1_2_list1=tom_get_tail_NameList(tom_match4_1_2_list1); if(tom_is_fun_sym_Name(tom_match4_1_2_1) ||  false ) { { String tom_match4_1_2_1_1 =tom_get_slot_Name_string(tom_match4_1_2_1); if(tom_cmp_fun_sym_String(tom_get_fun_sym_String(tom_match4_1_2_1_1) , "") ||  false ) { if(tom_is_empty_NameList(tom_match4_1_2_list1)) { { TomList args =tom_match4_1_3;
 
          return true;
        } } }} }}} }} }}} }}}
 
    }
    return false;
  }
  
  public LinkedList metaEncodeExplicitTermList(SymbolTable symbolTable, TomTerm term) {
    LinkedList list = new LinkedList();
     { TomTerm tom_match5_1 =(( TomTerm)term);{ if(tom_is_fun_sym_Appl(tom_match5_1) ||  false ) { { NameList tom_match5_1_2 =tom_get_slot_Appl_nameList(tom_match5_1); { TomList tom_match5_1_3 =tom_get_slot_Appl_args(tom_match5_1); if(tom_is_fun_sym_concTomName(tom_match5_1_2) ||  false ) { { NameList tom_match5_1_2_list1 =tom_match5_1_2; if(!(tom_is_empty_NameList(tom_match5_1_2_list1))) { { TomName tom_match5_1_2_1 =(( TomName)tom_get_head_NameList(tom_match5_1_2_list1));{ tom_match5_1_2_list1=tom_get_tail_NameList(tom_match5_1_2_list1); if(tom_is_fun_sym_Name(tom_match5_1_2_1) ||  false ) { { String tom_match5_1_2_1_1 =tom_get_slot_Name_string(tom_match5_1_2_1); if(tom_cmp_fun_sym_String(tom_get_fun_sym_String(tom_match5_1_2_1_1) , "") ||  false ) { if(tom_is_empty_NameList(tom_match5_1_2_list1)) { { TomList args =tom_match5_1_3;
 
        while(!args.isEmpty()) {
          list.add(metaEncodeXMLAppl(symbolTable,args.getHead()));
          args = args.getTail();
        }
        return list;
      } } }} }}} }} }}} }

 
          //System.out.println("metaEncodeExplicitTermList: strange case: " + term);
        list.add(term);
        return list;
      }}
 
  }
  
}
