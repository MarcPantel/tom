/* Generated by TOM (version 2.5alpha): Do not edit this file *//*
 * ANTLR Mapper
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
 * Yoann Toussaint    e-mail: Yoann.Toussaint@loria.fr
 *
 **/

package tom.antlrmapper;

import java.io.*;
import java.util.*;

public class ANTLRMapper {

  private String fileName;
  private String destDir;
  private String packagePrefix;
  private String packagePath;

  /* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file */private static boolean tom_terms_equal_char(char t1, char t2) {  return  (t1==t2)  ;} private static boolean tom_terms_equal_String(String t1, String t2) {  return  (t1.equals(t2))  ;}private static boolean tom_is_fun_sym_concString( String  t) {  return  (t!= null) && (t instanceof String)  ;}private static  String  tom_empty_list_concString() { return  "" ; }private static  String  tom_cons_list_concString( char  c,  String  s) { return  (c+s) ; }private static  char  tom_get_head_concString_String( String  s) {  return  s.charAt(0)  ;}private static  String  tom_get_tail_concString_String( String  s) {  return  s.substring(1)  ;}private static boolean tom_is_empty_concString_String( String  s) {  return  (s.length()==0)  ;}private static  String  tom_append_list_concString( String  l1,  String  l2) {    if(tom_is_empty_concString_String(l1)) {     return l2;    } else if(tom_is_empty_concString_String(l2)) {     return l1;    } else if(tom_is_empty_concString_String(( String )tom_get_tail_concString_String(l1))) {     return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(l1),l2);    } else {      return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(l1),tom_append_list_concString(( String )tom_get_tail_concString_String(l1),l2));    }   }  private static  String  tom_get_slice_concString( String  begin,  String  end) {    if(tom_terms_equal_String(begin,end)) {      return ( String )tom_empty_list_concString();    } else {      return ( String )tom_cons_list_concString(( char )tom_get_head_concString_String(begin),( String )tom_get_slice_concString(( String )tom_get_tail_concString_String(begin),end));    }   }    

  public static void main(String[] args) {
    int errno = exec(args);
    System.exit(errno);
  }

  public static int exec(String[] args) {
    String srcfile = "";
    String destdir = ".";
    String pack = "";

    for (int i=0; i<args.length; i++) {
      if (args[i].equals("--srcfile")) {
	srcfile = args[++i];
      }
      if (args[i].equals("--destdir")) {
	destdir = args[++i];
      }
      if (args[i].equals("--package")) {
	pack = args[++i];
      }
    }
    ANTLRMapper antlrMapper = new ANTLRMapper(srcfile,destdir,pack);
    return antlrMapper.gen();
  }

  public ANTLRMapper(String fileN, String destD, String pack) {
    this.fileName = fileN;
    this.destDir = destD;
    this.packagePrefix = pack;
    this.packagePath = pack.replace('.',File.separatorChar);
  }

  public int gen() {
    // use the first argument as input file
    Map tokenMap = new HashMap();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = "";
      while(line != null) {
	line = reader.readLine();
matchBlock: {
	       { String  tom_match1_1=(( String )line); if ( ( tom_is_fun_sym_concString(tom_match1_1) ||  false  ) ) { { String  tom_match1_1_list1=tom_match1_1; if (!(tom_is_empty_concString_String(tom_match1_1_list1))) { { char  tom_match1_1_1=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if ( ( tom_terms_equal_char('/', tom_match1_1_1) ||  false  ) ) { if (!(tom_is_empty_concString_String(tom_match1_1_list1))) { { char  tom_match1_1_2=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if ( ( tom_terms_equal_char('/', tom_match1_1_2) ||  false  ) ) { {boolean tom_match1_tom_anti_constraints_status= true ; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {
 /* comment */
		  break matchBlock;
	         } } } } } } } } } } } if ( ( tom_is_fun_sym_concString(tom_match1_1) ||  false  ) ) { { String  tom_match1_1_list1=tom_match1_1; { String  tom_match1_1_begin1=tom_match1_1_list1; { String  tom_match1_1_end1=tom_match1_1_list1; { while (!(tom_is_empty_concString_String(tom_match1_1_end1))) {tom_match1_1_list1=tom_match1_1_end1; { { String  tom_name=tom_get_slice_concString(tom_match1_1_begin1,tom_match1_1_end1); { char  tom_match1_1_2=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if ( ( tom_terms_equal_char('=', tom_match1_1_2) ||  false  ) ) { { String  tom_match1_1_begin3=tom_match1_1_list1; { String  tom_match1_1_end3=tom_match1_1_list1; { while (!(tom_is_empty_concString_String(tom_match1_1_end3))) {tom_match1_1_list1=tom_match1_1_end3; { { char  tom_match1_1_4=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if ( ( tom_terms_equal_char('=', tom_match1_1_4) ||  false  ) ) { { String  tom_value=tom_match1_1_list1; {boolean tom_match1_tom_anti_constraints_status= true ; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {
//token
		  Integer val = Integer.valueOf(tom_value);
		  tokenMap.put(val,tom_name);
		  break matchBlock;
		 } } } } } }tom_match1_1_end3=tom_get_tail_concString_String(tom_match1_1_end3); } }tom_match1_1_list1=tom_match1_1_begin3; } } } } } }tom_match1_1_end1=tom_get_tail_concString_String(tom_match1_1_end1); } }tom_match1_1_list1=tom_match1_1_begin1; } } } } } if ( ( tom_is_fun_sym_concString(tom_match1_1) ||  false  ) ) { { String  tom_match1_1_list1=tom_match1_1; { String  tom_match1_1_begin1=tom_match1_1_list1; { String  tom_match1_1_end1=tom_match1_1_list1; { while (!(tom_is_empty_concString_String(tom_match1_1_end1))) {tom_match1_1_list1=tom_match1_1_end1; { { String  tom_name=tom_get_slice_concString(tom_match1_1_begin1,tom_match1_1_end1); { char  tom_match1_1_2=tom_get_head_concString_String(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concString_String(tom_match1_1_list1); if ( ( tom_terms_equal_char('=', tom_match1_1_2) ||  false  ) ) { { String  tom_value=tom_match1_1_list1; {boolean tom_match1_tom_anti_constraints_status= true ; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {

		  Integer val = Integer.valueOf(tom_value);
		  tokenMap.put(val,tom_name);
		  break matchBlock;
		 } } } } } } }tom_match1_1_end1=tom_get_tail_concString_String(tom_match1_1_end1); } }tom_match1_1_list1=tom_match1_1_begin1; } } } } } }

	    }
      }
    } catch (Exception e) {
      System.err.println("Exception: "+e);
      return 1;
    }
    System.out.println(tokenMap);
    generateTable(tokenMap);
    generateTomMapping(tokenMap);
    return 0;//no errors
  }

  private void generateTable(Map tokenMap) {
    StringBuffer out = new StringBuffer();
    if(packagePrefix.length()>0) {
      out.append("package "/* Generated by TOM (version 2.5alpha): Do not edit this file */+packagePrefix+";\n\t  "
);
    }
    out.append("\n\tpublic class TokenTable {\n\tprivate static java.util.HashMap tokenMap = null;\n\n\tprivate static java.util.HashMap initTokenMap() {\n\ttokenMap = new java.util.HashMap();\n\t"/* Generated by TOM (version 2.5alpha): Do not edit this file */+initMap("tokenMap",tokenMap)+"\n\treturn tokenMap;\n\t}\n\tpublic static java.util.Map getTokenMap() {\n\tif (tokenMap == null) {\n\ttokenMap = initTokenMap();\n\t}\n\treturn (java.util.Map)tokenMap.clone();\n\t}\n\n\t}\n\n\t"

















);
    try {
      Writer writer = new BufferedWriter(
	  new FileWriter(destDir + File.separator +
	    packagePath + File.separator + "TokenTable.java"));
      writer.write(out.toString());
      writer.close();
    } catch (IOException e) {
      System.err.println(e.getClass() + ": " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Write failed "+e);
      e.printStackTrace();
    }
  }

  private String initMap(String mapName, Map tokMap) {
    StringBuffer out = new StringBuffer();
    Iterator it = tokMap.keySet().iterator();
    while(it.hasNext()) {
      Integer key = (Integer)it.next();
      String value = (String)tokMap.get(key);
      out.append("\n\t  "/* Generated by TOM (version 2.5alpha): Do not edit this file */+mapName+".put(new Integer("/* Generated by TOM (version 2.5alpha): Do not edit this file */+key.intValue()+"),\""/* Generated by TOM (version 2.5alpha): Do not edit this file */+value+"\");"
);
    }
    return out.toString();
  }

  void generateTomMapping(Map tokMap) {
    StringBuffer out = new StringBuffer();

    out.append("\n\t%include{ int.tom }\n\t%include{ string.tom }\n\t%include{ aterm.tom }\n\t%include{ atermlist.tom }\n\n\t%oplist ATermList concATerm (ATerm*){\n\tis_fsym(t) { t instanceof ATermList }\n\tmake_empty() { aterm.pure.SingletonFactory.getInstance().makeList() }\n\tmake_insert(e,l) { l.insert(e) }\n\tget_head(t) { t.getFirst() }\n\tget_tail(t) { t.getNext() }\n\tis_empty(t) { t.isEmpty() }\n\t}\n\n\t%op ATerm NodeInfo(text:String,line:int,column:int) {\n\tis_fsym(t) { (t != null) && ((ATermAppl)t).getAFun() == SingletonFactory.getInstance().makeAFun(\"NodeInfo\",3,false) }\n\tget_slot(text, t) { ((ATermAppl)((ATermAppl)t).getArgument(0)).getAFun().getName() }\n\tget_slot(line, t) { ((ATermInt)((ATermAppl)t).getArgument(1)).getInt() }\n\tget_slot(column, t) { ((ATermInt)((ATermAppl)t).getArgument(2)).getInt() }\n\tmake(t,l,c) { SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(\"NodeInfo\",3,false),SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(t,0,true)),SingletonFactory.getInstance().makeInt(l),SingletonFactory.getInstance().makeInt(c)) }\n\t}\n\n\t"






















);

	Iterator it = tokMap.keySet().iterator();
	while(it.hasNext()) {
	  Integer key = (Integer)it.next();
	  String value = (String)tokMap.get(key);
	  out.append("\n\t      %op ATerm "/* Generated by TOM (version 2.5alpha): Do not edit this file */+value+"(info:ATerm,childs:ATermList) {\n\t      is_fsym(t) { (t != null) && ((ATermAppl)t).getAFun() == SingletonFactory.getInstance().makeAFun(\""/* Generated by TOM (version 2.5alpha): Do not edit this file */+value+"\",2,false) }\n\t      get_slot(info, t) { ((ATermAppl)t).getArgument(0) }\n\t      get_slot(childs, t) { (ATermList)((ATermAppl)t).getArgument(1) }\n\t      make(i,c) {SingletonFactory.getInstance().makeAppl(SingletonFactory.getInstance().makeAFun(\""/* Generated by TOM (version 2.5alpha): Do not edit this file */+value+"\",2,false),i,c) }\n\t      }"





);
	}

	try {
	  Writer writer = new BufferedWriter(
	      new FileWriter(destDir + File.separator +
		packagePath + File.separator + "Mapping.tom"));
	  writer.write(out.toString());
	  writer.close();
	} catch (IOException e) {
	  System.err.println(e.getClass() + ": " + e.getMessage());
	} catch (Exception e) {
	  System.err.println("Write failed "+e);
	  e.printStackTrace();
	}
  }
}
