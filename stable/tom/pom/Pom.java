/* Generated by TOM (version 2.4alpha): Do not edit this file *//*
 * Pom
 * 
 * Copyright (c) 2005-2006, INRIA
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
 * Yoann Toussaint    e-mail: Yoann.Toussaint@loria.fr
 * 
 **/

package tom.pom;

import java.io.*;
import java.util.*;

public class Pom {


  private String fileName;
  private String packageName;

  /* Generated by TOM (version 2.4alpha): Do not edit this file *//*  *  * Copyright (c) 2004-2006, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/  /* Generated by TOM (version 2.4alpha): Do not edit this file *//*  *  * Copyright (c) 2004-2006, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/  private static  char  tom_set_stamp_char( char  c) {  return  c  ;}private static void tom_check_stamp_char( char  c) { ;}private static boolean tom_terms_equal_char( char  t1,  char  t2) {  return  (t1==t2)  ;}private static  Character  tom_set_stamp_Character( Character  c) {  return  c  ;}private static void tom_check_stamp_Character( Character  c) { ;}private static boolean tom_terms_equal_Character(Object t1, Object t2) {  return  (t1.equals(t2))  ;}private static boolean tom_is_fun_sym_Char( Character  t) {  return  (t!= null) && (t instanceof Character)  ;}private static  Character  tom_make_Char( char  c) { return  new Character(c) ; }private static  char  tom_get_slot_Char_c( Character  t) {  return  t.charValue()  ;} private static  String  tom_set_stamp_String( String  s) {  return  s  ;}private static void tom_check_stamp_String( String  s) { ;}private static boolean tom_terms_equal_String( String  t1,  String  t2) {  return  (t1.equals(t2))  ;}private static boolean tom_is_fun_sym_concString( String  t) {  return  (t!= null) && (t instanceof String)  ;}private static  String  tom_empty_list_concString() { return  "" ; }private static  String  tom_cons_list_concString( char  c,  String  s) { return  (c+s) ; }private static  char  tom_get_head_concString_String( String  s) {  return  s.charAt(0)  ;}private static  String  tom_get_tail_concString_String( String  s) {  return  s.substring(1)  ;}private static boolean tom_is_empty_concString_String( String  s) {  return  (s.length()==0)  ;}private static  String  tom_append_list_concString( String  l1,  String  l2) {    if(tom_is_empty_concString_String(l1)) {     return l2;    } else if(tom_is_empty_concString_String(l2)) {     return l1;    } else if(tom_is_empty_concString_String(( String )tom_get_tail_concString_String(l1))) {     return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(l1),l2);    } else {      return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(l1),tom_append_list_concString(( String )tom_get_tail_concString_String(l1),l2));    }   }  private static  String  tom_get_slice_concString( String  begin,  String  end) {    if(tom_terms_equal_String(begin,end)) {      return ( String )tom_empty_list_concString();    } else {      return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(begin),( String )tom_get_slice_concString(( String )tom_get_tail_concString_String(begin),end));    }   }    

  public static void main(String[] args) {
    exec(args);
  }
  public static int exec(String[] args) {
    Pom pom = new Pom(args[0],args[1]);
    pom.gen();
    return 0;//no errors
  }


  public Pom(String fileN,String packageN){
    this.packageName = packageN;
    this.fileName = fileN;
  }

  public void gen() {
    // use the first argument as input file
    Map tokenMap = new HashMap();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = "";
      while(line != null) {
        line = reader.readLine();
         { String  tom_match1_1=(( String )line); if (tom_is_fun_sym_concString(tom_match1_1) ||  false ) { { String  tom_match1_1_list1=tom_match1_1; if (!(tom_is_empty_concString_String(tom_match1_1_list1))) { { char  tom_match1_1_1=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if (tom_terms_equal_char('/', tom_match1_1_1) ||  false ) { if (!(tom_is_empty_concString_String(tom_match1_1_list1))) { { char  tom_match1_1_2=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if (tom_terms_equal_char('/', tom_match1_1_2) ||  false ) { if ( true ) {
 /* comment */ } } } } } } } } } if (tom_is_fun_sym_concString(tom_match1_1) ||  false ) { { String  tom_match1_1_list1=tom_match1_1; { String  tom_match1_1_begin1=tom_match1_1_list1; { String  tom_match1_1_end1=tom_match1_1_list1; { while (!(tom_is_empty_concString_String(tom_match1_1_end1))) {tom_match1_1_list1=tom_match1_1_end1; { { char  tom_match1_1_2=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if (tom_terms_equal_char('=', tom_match1_1_2) ||  false ) { if ( true ) {

            Integer val = Integer.valueOf(tom_match1_1_list1);
            tokenMap.put(val,tom_get_slice_concString(tom_match1_1_begin1,tom_match1_1_end1));
           } } }tom_match1_1_end1=tom_get_tail_concString_String(tom_match1_1_end1); } }tom_match1_1_list1=tom_match1_1_begin1; } } } } } }

      }
    } catch (Exception e) {
      System.out.println("Exception "+e);
    }
    System.out.println(tokenMap);
    generateTable(tokenMap);
    generateTomMapping(tokenMap);
  }

  void generateTable(Map tokenMap) {
    StringBuffer out = new StringBuffer();

    out.append("\npackage "/* Generated by TOM (version 2.4alpha): Do not edit this file */+packageName+";\n\npublic class TokenTable {\n  private static java.util.HashMap tokenMap = null;\n\n  private static java.util.HashMap initTokenMap() {\n    tokenMap = new java.util.HashMap();\n"/* Generated by TOM (version 2.4alpha): Do not edit this file */+initMap("tokenMap",tokenMap)+"\n    return tokenMap;\n  }\n  public static java.util.Map getTokenMap() {\n    if (tokenMap == null) {\n      tokenMap = initTokenMap();\n    }\n    return (java.util.Map)tokenMap.clone();\n  }\n\n}\n\n"



















);
    try {
      Writer writer = new BufferedWriter(
          new FileWriter("parsingtests/TokenTable.java"));
      writer.write(out.toString());
      writer.close();
    } catch (Exception e) {
      System.out.println("Write failed "+e);
      e.printStackTrace();
    }
  }

  private String initMap(String mapName, Map tokMap) {
    StringBuffer out = new StringBuffer();
    Iterator it = tokMap.keySet().iterator();
    while(it.hasNext()) {
      Integer key = (Integer)it.next();
      String value = (String)tokMap.get(key);
      out.append("\n    "/* Generated by TOM (version 2.4alpha): Do not edit this file */+mapName+".put(new Integer("/* Generated by TOM (version 2.4alpha): Do not edit this file */+key.intValue()+"),\""/* Generated by TOM (version 2.4alpha): Do not edit this file */+value+"\");"
);
    }
    return out.toString();
  }

  void generateTomMapping(Map tokMap) {
    StringBuffer out = new StringBuffer();

    out.append("\n  %include{ int.tom }\n  %include{ string.tom }\n  %include{ aterm.tom }\n  %include{ atermlist.tom }\n\n  %oplist ATermList concATerm (ATerm*){\n    is_fsym(t) { t instanceof ATermList }\n    make_empty() { aterm.pure.SingletonFactory.getInstance().makeList() }\n    make_insert(e,l) { l.insert(e) }\n    get_head(t) { t.getFirst() }\n    get_tail(t) { t.getNext() }\n    is_empty(t) { t.isEmpty() }\n  }\n\n  %op ATerm NodeInfo(text:String,line:int,column:int) {\n    is_fsym(t) { (t != null) && ((ATermAppl)t).getAFun() == SingletonFactory.getInstance().makeAFun(\"NodeInfo\",3,false) }\n    get_slot(text, t) { ((ATermAppl)((ATermAppl)t).getArgument(0)).getAFun().getName() }\n    get_slot(line, t) { ((ATermInt)((ATermAppl)t).getArgument(1)).getInt() }\n    get_slot(column, t) { ((ATermInt)((ATermAppl)t).getArgument(2)).getInt() }\n    make(t,l,c) { SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(\"NodeInfo\",3,false),SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(t,0,true)),SingletonFactory.getInstance().makeInt(l),SingletonFactory.getInstance().makeInt(c)) }\n  }\n\n  "






















);

    Iterator it = tokMap.keySet().iterator();
    while(it.hasNext()) {
      Integer key = (Integer)it.next();
      String value = (String)tokMap.get(key);
      out.append("\n  %op ATerm "/* Generated by TOM (version 2.4alpha): Do not edit this file */+value+"(info:ATerm,childs:ATermList) {\n    is_fsym(t) { (t != null) && ((ATermAppl)t).getAFun() == SingletonFactory.getInstance().makeAFun(\""/* Generated by TOM (version 2.4alpha): Do not edit this file */+value+"\",2,false) }\n    get_slot(info, t) { ((ATermAppl)t).getArgument(0) }\n    get_slot(childs, t) { (ATermList)((ATermAppl)t).getArgument(1) }\n    make(i,c) {SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(\""/* Generated by TOM (version 2.4alpha): Do not edit this file */+value+"\",2,false),i,c) }\n  }"





);
    }

    try {
      Writer writer = new BufferedWriter(
          new FileWriter("parsingtests/Mapping.tom"));
      writer.write(out.toString());
      writer.close();
    } catch (Exception e) {
      System.out.println("Write failed "+e);
      e.printStackTrace();
    }
  }
}
