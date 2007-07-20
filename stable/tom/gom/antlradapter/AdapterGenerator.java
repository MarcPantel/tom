/* Generated by TOM (version 2.5): Do not edit this file *//*
 * Gom
 *
 * Copyright (C) 2006-2007, INRIA
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
 * Antoine Reilles  e-mail: Antoine.Reilles@loria.fr
 *
 **/

package tom.gom.antlradapter;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.gom.GomMessage;
import tom.gom.tools.GomEnvironment;
import tom.gom.adt.gom.*;
import tom.gom.adt.gom.types.*;
import tom.gom.backend.CodeGen;
import tom.gom.tools.error.GomRuntimeException;

import tom.gom.adt.code.types.*;
import tom.gom.adt.objects.*;
import tom.gom.adt.objects.types.*;
import tom.library.sl.VisitFailure;
import tom.library.sl.Strategy;

public class AdapterGenerator {

  /* Attributes needed to call tom properly */
  private File tomHomePath;
  private List importList = null;
  private String grammarName = "";

  AdapterGenerator(File tomHomePath, List importList, String grammar) {
    this.tomHomePath = tomHomePath;
    this.importList = importList;
    this.grammarName = grammar;
  }

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_Code(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Code(Object t) { return  t instanceof tom.gom.adt.code.types.Code ;}private static boolean tom_equal_term_Slot(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Slot(Object t) { return  t instanceof tom.gom.adt.gom.types.Slot ;}private static boolean tom_equal_term_TypedProduction(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TypedProduction(Object t) { return  t instanceof tom.gom.adt.gom.types.TypedProduction ;}private static boolean tom_equal_term_OperatorDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_OperatorDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.OperatorDecl ;}private static boolean tom_equal_term_SortDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SortDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.SortDecl ;}private static boolean tom_equal_term_SlotList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SlotList(Object t) { return  t instanceof tom.gom.adt.gom.types.SlotList ;}private static boolean tom_equal_term_ModuleDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ModuleDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.ModuleDecl ;}private static  tom.gom.adt.code.types.Code  tom_make_Code( String  t0) { return  tom.gom.adt.code.types.code.Code.make(t0) ; }private static  tom.gom.adt.code.types.Code  tom_make_Empty( tom.gom.adt.gom.types.OperatorDecl  t0) { return  tom.gom.adt.code.types.code.Empty.make(t0) ; }private static  tom.gom.adt.code.types.Code  tom_make_FullSortClass( tom.gom.adt.gom.types.SortDecl  t0) { return  tom.gom.adt.code.types.code.FullSortClass.make(t0) ; }private static  tom.gom.adt.code.types.Code  tom_make_FullOperatorClass( tom.gom.adt.gom.types.OperatorDecl  t0) { return  tom.gom.adt.code.types.code.FullOperatorClass.make(t0) ; }private static boolean tom_is_fun_sym_Slot( tom.gom.adt.gom.types.Slot  t) { return  t instanceof tom.gom.adt.gom.types.slot.Slot ;}private static  String  tom_get_slot_Slot_Name( tom.gom.adt.gom.types.Slot  t) { return  t.getName() ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_Slot_Sort( tom.gom.adt.gom.types.Slot  t) { return  t.getSort() ;}private static boolean tom_is_fun_sym_Slots( tom.gom.adt.gom.types.TypedProduction  t) { return  t instanceof tom.gom.adt.gom.types.typedproduction.Slots ;}private static  tom.gom.adt.gom.types.SlotList  tom_get_slot_Slots_Slots( tom.gom.adt.gom.types.TypedProduction  t) { return  t.getSlots() ;}private static boolean tom_is_fun_sym_Variadic( tom.gom.adt.gom.types.TypedProduction  t) { return  t instanceof tom.gom.adt.gom.types.typedproduction.Variadic ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_Variadic_Sort( tom.gom.adt.gom.types.TypedProduction  t) { return  t.getSort() ;}private static boolean tom_is_fun_sym_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  t) { return  t instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl ;}private static  String  tom_get_slot_OperatorDecl_Name( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getName() ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_OperatorDecl_Sort( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getSort() ;}private static  tom.gom.adt.gom.types.TypedProduction  tom_get_slot_OperatorDecl_Prod( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getProd() ;}private static boolean tom_is_fun_sym_SortDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t instanceof tom.gom.adt.gom.types.sortdecl.SortDecl ;}private static  String  tom_get_slot_SortDecl_Name( tom.gom.adt.gom.types.SortDecl  t) { return  t.getName() ;}private static  tom.gom.adt.gom.types.ModuleDecl  tom_get_slot_SortDecl_ModuleDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t.getModuleDecl() ;}private static boolean tom_is_fun_sym_BuiltinSortDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl ;}private static  String  tom_get_slot_BuiltinSortDecl_Name( tom.gom.adt.gom.types.SortDecl  t) { return  t.getName() ;}private static boolean tom_is_fun_sym_CodeList( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.ConsCodeList || t instanceof tom.gom.adt.code.types.code.EmptyCodeList ;}private static  tom.gom.adt.code.types.Code  tom_empty_list_CodeList() { return  tom.gom.adt.code.types.code.EmptyCodeList.make() ; }private static  tom.gom.adt.code.types.Code  tom_cons_list_CodeList( tom.gom.adt.code.types.Code  e,  tom.gom.adt.code.types.Code  l) { return  tom.gom.adt.code.types.code.ConsCodeList.make(e,l) ; }private static  tom.gom.adt.code.types.Code  tom_get_head_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.getHeadCodeList() ;}private static  tom.gom.adt.code.types.Code  tom_get_tail_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.getTailCodeList() ;}private static boolean tom_is_empty_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.isEmptyCodeList() ;}   private static   tom.gom.adt.code.types.Code  tom_append_list_CodeList( tom.gom.adt.code.types.Code l1,  tom.gom.adt.code.types.Code  l2) {     if(tom_is_empty_CodeList_Code(l1)) {       return l2;     } else if(tom_is_empty_CodeList_Code(l2)) {       return l1;     } else if(tom_is_fun_sym_CodeList(l1)) {       if(tom_is_empty_CodeList_Code(((tom_is_fun_sym_CodeList(l1))?tom_get_tail_CodeList_Code(l1):tom_empty_list_CodeList()))) {         return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_head_CodeList_Code(l1):l1),l2);       } else {         return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_head_CodeList_Code(l1):l1),tom_append_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_tail_CodeList_Code(l1):tom_empty_list_CodeList()),l2));       }     } else {       return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(l1, l2);     }   }   private static   tom.gom.adt.code.types.Code  tom_get_slice_CodeList( tom.gom.adt.code.types.Code  begin,  tom.gom.adt.code.types.Code  end, tom.gom.adt.code.types.Code  tail) {     if(tom_equal_term_Code(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(begin))?tom_get_head_CodeList_Code(begin):begin),( tom.gom.adt.code.types.Code )tom_get_slice_CodeList(((tom_is_fun_sym_CodeList(begin))?tom_get_tail_CodeList_Code(begin):tom_empty_list_CodeList()),end,tail));     }   }    /* Generated by TOM (version 2.5): Do not edit this file */private static boolean tom_equal_term_Strategy(Object t1, Object t2) { return t1.equals(t2);}private static boolean tom_is_sort_Strategy(Object t) { return  t instanceof tom.library.sl.Strategy ;}/* Generated by TOM (version 2.5): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_mu( tom.library.sl.Strategy  var,  tom.library.sl.Strategy  v) { return  new tom.library.sl.Mu(var,v) ; }private static  tom.library.sl.Strategy  tom_make_MuVar( String  name) { return  new tom.library.sl.MuVar(name) ; }private static  tom.library.sl.Strategy  tom_make_Identity() { return  new tom.library.sl.Identity() ; }private static  tom.library.sl.Strategy  tom_make_All( tom.library.sl.Strategy  v) { return  new tom.library.sl.All(v) ; }private static boolean tom_is_fun_sym_Sequence( tom.library.sl.Strategy  t) { return  (t instanceof tom.library.sl.Sequence) ;}private static  tom.library.sl.Strategy  tom_empty_list_Sequence() { return  null ; }private static  tom.library.sl.Strategy  tom_cons_list_Sequence( tom.library.sl.Strategy  head,  tom.library.sl.Strategy  tail) { return  (tail==null)?head:new tom.library.sl.Sequence(head,tail) ; }private static  tom.library.sl.Strategy  tom_get_head_Sequence_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Sequence.FIRST) ;}private static  tom.library.sl.Strategy  tom_get_tail_Sequence_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Sequence.THEN) ;}private static boolean tom_is_empty_Sequence_Strategy( tom.library.sl.Strategy  t) { return  t == null ;}   private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy l1,  tom.library.sl.Strategy  l2) {     if(tom_is_empty_Sequence_Strategy(l1)) {       return l2;     } else if(tom_is_empty_Sequence_Strategy(l2)) {       return l1;     } else if(tom_is_fun_sym_Sequence(l1)) {       if(tom_is_empty_Sequence_Strategy(((tom_is_fun_sym_Sequence(l1))?tom_get_tail_Sequence_Strategy(l1):tom_empty_list_Sequence()))) {         return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_head_Sequence_Strategy(l1):l1),l2);       } else {         return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_head_Sequence_Strategy(l1):l1),tom_append_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_tail_Sequence_Strategy(l1):tom_empty_list_Sequence()),l2));       }     } else {       return ( tom.library.sl.Strategy )tom_cons_list_Sequence(l1, l2);     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if(tom_equal_term_Strategy(begin,end)) {       return tail;     } else {       return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(begin))?tom_get_head_Sequence_Strategy(begin):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((tom_is_fun_sym_Sequence(begin))?tom_get_tail_Sequence_Strategy(begin):tom_empty_list_Sequence()),end,tail));     }   }    /* Generated by TOM (version 2.5): Do not edit this file */ /* Generated by TOM (version 2.5): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return tom_make_mu(tom_make_MuVar("_x"),tom_cons_list_Sequence(v,tom_cons_list_Sequence(tom_make_All(tom_make_MuVar("_x")),tom_empty_list_Sequence()))) ; }   /* Generated by TOM (version 2.5): Do not edit this file */private static boolean tom_equal_term_Collection(Object l1, Object l2) { return  l1.equals(l2) ;}private static boolean tom_is_sort_Collection(Object t) { return  t instanceof java.util.Collection ;} private static boolean tom_is_sort_Writer(Object t) { return 




 t instanceof Writer ;}


  private GomEnvironment environment() {
    return GomEnvironment.getInstance();
  }

  public void generate(ModuleList moduleList, HookDeclList hookDecls) {
    writeTokenFile(moduleList);
    writeAdapterFile(moduleList);
    writeTreeFile(moduleList);
  }

  public int writeTokenFile(ModuleList moduleList) {
    try {
       File output = tokenFileToGenerate();
       // make sure the directory exists
       output.getParentFile().mkdirs();
       Writer writer =
         new BufferedWriter(
             new OutputStreamWriter(
               new FileOutputStream(output)));
       generateTokenFile(moduleList, writer);
       writer.flush();
       writer.close();
    } catch(Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

  public int writeTreeFile(ModuleList moduleList) {
    try {
       File output = treeFileToGenerate();
       // make sure the directory exists
       output.getParentFile().mkdirs();
       Writer writer =
         new BufferedWriter(
             new OutputStreamWriter(
               new FileOutputStream(output)));
       generateTreeFile(moduleList, writer);
       writer.flush();
       writer.close();
    } catch(Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

  public int writeAdapterFile(ModuleList moduleList) {
    try {
       File output = adaptorFileToGenerate();
       // make sure the directory exists
       output.getParentFile().mkdirs();
       Writer writer =
         new BufferedWriter(
             new OutputStreamWriter(
               new FileOutputStream(output)));
       generateAdapterFile(moduleList, writer);
       writer.flush();
       writer.close();
    } catch(Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

  private String adapterPkg() {
    String packagePrefix =
      environment()
        .getStreamManager()
          .getPackagePath().replace(File.separatorChar,'.');
    return
      (packagePrefix==""?filename():packagePrefix+"."+filename()).toLowerCase();
  }

  public void generateAdapterFile(ModuleList moduleList, Writer writer)
    throws java.io.IOException {
    writer.write(
    "\npackage "/* Generated by TOM (version 2.5): Do not edit this file */+adapterPkg()+";\n\nimport org.antlr.runtime.Token;\nimport org.antlr.runtime.tree.CommonTreeAdaptor;\n\npublic class "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Adaptor extends CommonTreeAdaptor {\n\n\tpublic Object create(Token payload) {\n\t\treturn new "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree(payload);\n\t}\n\n}\n"












);
  }

  public void generateTreeFile(ModuleList moduleList, Writer writer)
    throws java.io.IOException {
    String packagePrefix =
      environment()
        .getStreamManager()
          .getPackagePath().replace(File.separatorChar,'.');
    Collection operatorset = new HashSet();
    Collection slotset = new HashSet();
    try {
      tom_make_TopDown(tom_cons_list_Sequence(tom_make_CollectOperators(operatorset),tom_cons_list_Sequence(tom_make_CollectSlots(slotset),tom_empty_list_Sequence()))).visitLight(moduleList);
    } catch (VisitFailure f) {
      throw new GomRuntimeException("CollectOperators should not fail");
    }
    writer.write("\npackage "/* Generated by TOM (version 2.5): Do not edit this file */+adapterPkg()+";\n\nimport org.antlr.runtime.Token;\nimport org.antlr.runtime.tree.*;\nimport "/* Generated by TOM (version 2.5): Do not edit this file */+packagePrefix+"."/* Generated by TOM (version 2.5): Do not edit this file */+grammarName+"Parser;\n\npublic class "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree extends CommonTree {\n\n  private int termIndex = 0;\n  /* Use SharedObject as type, as most general type */\n  private shared.SharedObject inAstTerm = null;\n\n  public "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree(CommonTree node) {\n    super(node);\n    this.token = node.token;\n    initAstTerm(node.token);\n  }\n\n  public "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree(Token t) {\n    this.token = t;\n    initAstTerm(t);\n  }\n\n  private void initAstTerm(Token t) {\n    if(null==t) {\n      return;\n    }\n    switch (t.getType()) {\n"




























);
    Iterator it = operatorset.iterator();
    while(it.hasNext()) {
      OperatorDecl op = (OperatorDecl) it.next();
      if (tom_is_sort_OperatorDecl(op)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch314NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )op);if (tom_is_fun_sym_OperatorDecl(tomMatch314NameNumberfreshSubject_1)) {{  String  tomMatch314NameNumber_freshVar_0=tom_get_slot_OperatorDecl_Name(tomMatch314NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.TypedProduction  tomMatch314NameNumber_freshVar_1=tom_get_slot_OperatorDecl_Prod(tomMatch314NameNumberfreshSubject_1);if (tom_is_fun_sym_Variadic(tomMatch314NameNumber_freshVar_1)) {if ( true ) {

          Code code =
            tom_cons_list_CodeList(tom_make_Code("      case "+grammarName+"Parser."),tom_cons_list_CodeList(tom_make_Code(tomMatch314NameNumber_freshVar_0),tom_cons_list_CodeList(tom_make_Code(":\n"),tom_cons_list_CodeList(tom_make_Code("      {\n"),tom_cons_list_CodeList(tom_make_Code("        inAstTerm = "),tom_cons_list_CodeList(tom_make_Empty(tomMatch314NameNumberfreshSubject_1),tom_cons_list_CodeList(tom_make_Code(".make();\n"),tom_cons_list_CodeList(tom_make_Code("        break;\n"),tom_cons_list_CodeList(tom_make_Code("        }\n"),tom_empty_list_CodeList())))))))))








;
          CodeGen.generateCode(code,writer);
        }}}}}}}

    }
    writer.write("\n    }\n  }\n\n"



);
    /* Add fields for each slot : first for variadic operators, then constructor slots */
    it = operatorset.iterator();
    while(it.hasNext()) {
      OperatorDecl op = (OperatorDecl) it.next();
      try {
        tom_make_GenerateSlots(writer).visitLight(op);
      } catch (VisitFailure f) {
        throw new GomRuntimeException("GenerateSlots for variadic operators should not fail");
      }
    }
    it = slotset.iterator();
    while(it.hasNext()) {
      Slot slot = (Slot) it.next();
      try {
        tom_make_GenerateSlots(writer).visitLight(slot);
      } catch (VisitFailure f) {
        throw new GomRuntimeException("GenerateSlots for slots should not fail");
      }
    }

    writer.write("\n\n  public shared.SharedObject getTerm() {\n    return inAstTerm;\n  }\n\n  public void addChild(Tree t) {\n    super.addChild(t);\n    if (null==t) {\n      return;\n    }\n    "/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree tree = ("/* Generated by TOM (version 2.5): Do not edit this file */+filename()+"Tree) t;\n    if(this.token == null || tree.token == null) {\n      return;\n    }\n\n    /* Depending on the token number and the child count, fill the correct field */\n    switch (this.token.getType()) {\n"

















);

    it = operatorset.iterator();
    while(it.hasNext()) {
      OperatorDecl op = (OperatorDecl) it.next();
      generateAddChildCase(op, writer);
    }

    writer.write("\n      default: break;\n    }\n\n    termIndex++;\n    /* Instantiate the term if needed */\n  }\n"






);
    writer.write("\n}\n"

);
  }

  public void generateTokenFile(ModuleList moduleList, Writer writer)
    throws java.io.IOException {
    Collection bag = new HashSet();
    try {
      tom_make_TopDown(tom_make_CollectOperatorNames(bag)).visitLight(moduleList);
    } catch (VisitFailure f) {
      throw new GomRuntimeException("CollectOperatorNames should not fail");
    }
    Iterator it = bag.iterator();
    while(it.hasNext()) {
      String op = (String) it.next();
      writer.write(op + ";\n");
    }
  }

  private static class CollectOperatorNames extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectOperatorNames( java.util.Collection  bag) { super(tom_make_Identity());this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];for (int i = 0; i < getChildCount(); i++) {stratChilds[i]=getChildAt(i);}return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {for (int i = 0; i < getChildCount(); i++) {setChildAt(i,children[i]);}return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg) throws tom.library.sl.VisitFailure {if (tom_is_sort_OperatorDecl(tom__arg)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch315NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom__arg);if (tom_is_fun_sym_OperatorDecl(tomMatch315NameNumberfreshSubject_1)) {{  String  tomMatch315NameNumber_freshVar_0=tom_get_slot_OperatorDecl_Name(tomMatch315NameNumberfreshSubject_1);if ( true ) {


        bag.add(tomMatch315NameNumber_freshVar_0);
      }}}}}return super.visit_OperatorDecl(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectOperatorNames( java.util.Collection  t0) { return new CollectOperatorNames(t0); }private static class CollectOperators extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectOperators( java.util.Collection  bag) { super(tom_make_Identity());this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];for (int i = 0; i < getChildCount(); i++) {stratChilds[i]=getChildAt(i);}return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {for (int i = 0; i < getChildCount(); i++) {setChildAt(i,children[i]);}return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg) throws tom.library.sl.VisitFailure {if (tom_is_sort_OperatorDecl(tom__arg)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch316NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom__arg);if (tom_is_fun_sym_OperatorDecl(tomMatch316NameNumberfreshSubject_1)) {if ( true ) {






        bag.add(tomMatch316NameNumberfreshSubject_1);
      }}}}return super.visit_OperatorDecl(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectOperators( java.util.Collection  t0) { return new CollectOperators(t0); }private static class CollectSlots extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectSlots( java.util.Collection  bag) { super(tom_make_Identity());this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];for (int i = 0; i < getChildCount(); i++) {stratChilds[i]=getChildAt(i);}return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {for (int i = 0; i < getChildCount(); i++) {setChildAt(i,children[i]);}return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.Slot  visit_Slot( tom.gom.adt.gom.types.Slot  tom__arg) throws tom.library.sl.VisitFailure {if (tom_is_sort_Slot(tom__arg)) {{  tom.gom.adt.gom.types.Slot  tomMatch317NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.Slot )tom__arg);if (tom_is_fun_sym_Slot(tomMatch317NameNumberfreshSubject_1)) {if ( true ) {






        bag.add(tomMatch317NameNumberfreshSubject_1);
      }}}}return super.visit_Slot(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectSlots( java.util.Collection  t0) { return new CollectSlots(t0); }private static class GenerateSlots extends  tom.gom.adt.gom.GomBasicStrategy  {private  Writer  writer; public GenerateSlots( Writer  writer) { super(tom_make_Identity());this.writer=writer;}public  Writer  getwriter() { return writer;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];for (int i = 0; i < getChildCount(); i++) {stratChilds[i]=getChildAt(i);}return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {for (int i = 0; i < getChildCount(); i++) {setChildAt(i,children[i]);}return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.Slot  visit_Slot( tom.gom.adt.gom.types.Slot  tom__arg) throws tom.library.sl.VisitFailure {if (tom_is_sort_Slot(tom__arg)) {{  tom.gom.adt.gom.types.Slot  tomMatch318NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.Slot )tom__arg);if (tom_is_fun_sym_Slot(tomMatch318NameNumberfreshSubject_1)) {{  String  tomMatch318NameNumber_freshVar_0=tom_get_slot_Slot_Name(tomMatch318NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SortDecl  tomMatch318NameNumber_freshVar_1=tom_get_slot_Slot_Sort(tomMatch318NameNumberfreshSubject_1);if ( true ) {






        Code code =
          tom_cons_list_CodeList(tom_make_Code("  "),tom_cons_list_CodeList(tom_make_FullSortClass(tomMatch318NameNumber_freshVar_1),tom_cons_list_CodeList(tom_make_Code(" "),tom_cons_list_CodeList(tom_make_Code(tomMatch318NameNumber_freshVar_0),tom_cons_list_CodeList(tom_make_Code(";\n")
           ,tom_empty_list_CodeList())))))





;
        try {
          CodeGen.generateCode(code,writer);
        } catch (IOException e) {
          throw new VisitFailure("IOException " + e);
        }
      }}}}}}return super.visit_Slot(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_GenerateSlots( Writer  t0) { return new GenerateSlots(t0); }



  protected void generateAddChildCase(OperatorDecl op, Writer writer) throws IOException {
    if (tom_is_sort_OperatorDecl(op)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch320NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )op);if (tom_is_fun_sym_OperatorDecl(tomMatch320NameNumberfreshSubject_1)) {{  String  tomMatch320NameNumber_freshVar_0=tom_get_slot_OperatorDecl_Name(tomMatch320NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SortDecl  tomMatch320NameNumber_freshVar_1=tom_get_slot_OperatorDecl_Sort(tomMatch320NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.TypedProduction  tomMatch320NameNumber_freshVar_2=tom_get_slot_OperatorDecl_Prod(tomMatch320NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.TypedProduction  tom_prod=tomMatch320NameNumber_freshVar_2;{  tom.gom.adt.gom.types.OperatorDecl  tom_op=tomMatch320NameNumberfreshSubject_1;if ( true ) {

        Code code =
          tom_cons_list_CodeList(tom_make_Code("      case "+grammarName+"Parser."),tom_cons_list_CodeList(tom_make_Code(tomMatch320NameNumber_freshVar_0),tom_cons_list_CodeList(tom_make_Code(":\n"),tom_cons_list_CodeList(tom_make_Code("      {\n")
              ,tom_empty_list_CodeList()))))




;
        if (tom_is_sort_TypedProduction(tom_prod)) {{  tom.gom.adt.gom.types.TypedProduction  tomMatch319NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.TypedProduction )tom_prod);if (tom_is_fun_sym_Slots(tomMatch319NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.SlotList  tomMatch319NameNumber_freshVar_0=tom_get_slot_Slots_Slots(tomMatch319NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SlotList  tom_slotList=tomMatch319NameNumber_freshVar_0;if ( true ) {

            code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_FullSortClass(tomMatch320NameNumber_freshVar_1),tom_cons_list_CodeList(tom_make_Code(" term = ("),tom_cons_list_CodeList(tom_make_FullOperatorClass(tom_op),tom_cons_list_CodeList(tom_make_Code(") inAstTerm;\n"),tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_Code("switch(termIndex) {\n")
                ,tom_empty_list_CodeList()))))))))







;
            int idx = 0;
            SlotList sList = tom_slotList;
            while(sList.isConsconcSlot()) {
              Slot slot = sList.getHeadconcSlot();
              sList = sList.getTailconcSlot();
              Code cast = genGetSubterm(slot.getSort());
              code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("          "),tom_cons_list_CodeList(tom_make_Code("case "+idx+":\n"),tom_cons_list_CodeList(tom_make_Code("            "),tom_cons_list_CodeList(tom_make_Code(slot.getName() + " = "),tom_append_list_CodeList(cast,tom_cons_list_CodeList(tom_make_Code(";\n")
                  ,tom_empty_list_CodeList())))))))






;
              if(idx == tom_slotList.length() - 1) {
                code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("            inAstTerm = "),tom_cons_list_CodeList(tom_make_FullOperatorClass(tom_op),tom_cons_list_CodeList(tom_make_Code(".make("),tom_cons_list_CodeList(tom_make_Code(genArgsList(tom_slotList)),tom_cons_list_CodeList(tom_make_Code(");\n")
                    ,tom_empty_list_CodeList()))))))





;
              }
              code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("            break;\n")
                  ,tom_empty_list_CodeList()))

;
              idx++;
            }
            code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_Code("}\n")
                ,tom_empty_list_CodeList())))


;
          }}}}if (tom_is_fun_sym_Variadic(tomMatch319NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.SortDecl  tomMatch319NameNumber_freshVar_1=tom_get_slot_Variadic_Sort(tomMatch319NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SortDecl  tom_domainSort=tomMatch319NameNumber_freshVar_1;if ( true ) {

            Code cast = genGetSubterm(tom_domainSort);
            code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_FullSortClass(tom_domainSort),tom_cons_list_CodeList(tom_make_Code(" elem = "),tom_append_list_CodeList(cast,tom_cons_list_CodeList(tom_make_Code(";\n"),tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_FullOperatorClass(tom_op),tom_cons_list_CodeList(tom_make_Code(" list = ("),tom_cons_list_CodeList(tom_make_FullOperatorClass(tom_op),tom_cons_list_CodeList(tom_make_Code(") inAstTerm;\n"),tom_cons_list_CodeList(tom_make_Code("        "),tom_cons_list_CodeList(tom_make_Code("inAstTerm = list.add(elem);\n")
                ,tom_empty_list_CodeList())))))))))))))












;
          }}}}}}

        code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("        break;\n"),tom_cons_list_CodeList(tom_make_Code("        }\n"),tom_empty_list_CodeList())))

;
        CodeGen.generateCode(code,writer);
      }}}}}}}}}

  }

  protected Code genGetSubterm(SortDecl sort) {
    Code code = tom_empty_list_CodeList();;
    if (tom_is_sort_SortDecl(sort)) {{  tom.gom.adt.gom.types.SortDecl  tomMatch321NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.SortDecl )sort);if (tom_is_fun_sym_SortDecl(tomMatch321NameNumberfreshSubject_1)) {if ( true ) {

        code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("("),tom_cons_list_CodeList(tom_make_FullSortClass(sort),tom_cons_list_CodeList(tom_make_Code(") tree.getTerm()"),tom_empty_list_CodeList()))))


;
      }}if (tom_is_fun_sym_BuiltinSortDecl(tomMatch321NameNumberfreshSubject_1)) {{  String  tomMatch321NameNumber_freshVar_0=tom_get_slot_BuiltinSortDecl_Name(tomMatch321NameNumberfreshSubject_1);{  String  tom_name=tomMatch321NameNumber_freshVar_0;if ( true ) {

        if("int".equals(tom_name)) {
          code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("Integer.parseInt(t.getText())"),tom_empty_list_CodeList()))
;
        } else if ("String".equals(tom_name)) {
          code = tom_cons_list_CodeList(code,tom_cons_list_CodeList(tom_make_Code("t.getText()"),tom_empty_list_CodeList()))
;
        } else {
          throw new RuntimeException("Unsupported builtin");
        }
      }}}}}}

    return code;
  }

  protected String genArgsList(SlotList slots) {
    String res = "";
    SlotList sList = slots;
    while(sList.isConsconcSlot()) {
      Slot slot = sList.getHeadconcSlot();
      sList = sList.getTailconcSlot();
      res += slot.getName();
      if(sList.isConsconcSlot()) {
        res += ", ";
      }
    }
    return res;
  }

  protected String fullFileName() {

    return (adapterPkg() + "." + filename()).replace('.',File.separatorChar);
  }

  protected String filename() {
    String filename =
      (new File(environment().getStreamManager().getOutputFileName())).getName();
    int dotidx = filename.indexOf('.');
    if(-1 != dotidx) {
      filename = filename.substring(0,dotidx);
    }
    return filename;
  }

  protected File tokenFileToGenerate() {
    File output = new File(
        environment().getStreamManager().getDestDir(),
        fullFileName()+"TokenList.txt");
    return output;
  }

  protected File adaptorFileToGenerate() {
    File output = new File(
        environment().getStreamManager().getDestDir(),
        fullFileName()+"Adaptor.java");
    return output;
  }

  protected File treeFileToGenerate() {
    File output = new File(
        environment().getStreamManager().getDestDir(),
        fullFileName()+"Tree.java");
    return output;
  }
}
