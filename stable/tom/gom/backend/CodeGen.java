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

package tom.gom.backend;

import java.io.Writer;
import java.io.StringWriter;
import java.util.logging.Logger;
import java.util.logging.Level;
import tom.gom.tools.error.GomRuntimeException;
import tom.gom.adt.code.types.*;
import tom.gom.adt.objects.types.*;

public class CodeGen {

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_ClassName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassName(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassName ;}private static boolean tom_equal_term_Code(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Code(Object t) { return  t instanceof tom.gom.adt.code.types.Code ;}private static boolean tom_equal_term_TypedProduction(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TypedProduction(Object t) { return  t instanceof tom.gom.adt.gom.types.TypedProduction ;}private static boolean tom_equal_term_OperatorDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_OperatorDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.OperatorDecl ;}private static boolean tom_equal_term_SortDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SortDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.SortDecl ;}private static boolean tom_equal_term_ModuleDecl(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ModuleDecl(Object t) { return  t instanceof tom.gom.adt.gom.types.ModuleDecl ;}private static  tom.gom.adt.objects.types.ClassName  tom_make_ClassName( String  t0,  String  t1) { return  tom.gom.adt.objects.types.classname.ClassName.make(t0, t1) ; }private static boolean tom_is_fun_sym_Code( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.Code ;}private static  String  tom_get_slot_Code_prog( tom.gom.adt.code.types.Code  t) { return  t.getprog() ;}private static boolean tom_is_fun_sym_IsEmpty( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.IsEmpty ;}private static  String  tom_get_slot_IsEmpty_Var( tom.gom.adt.code.types.Code  t) { return  t.getVar() ;}private static  tom.gom.adt.gom.types.OperatorDecl  tom_get_slot_IsEmpty_Operator( tom.gom.adt.code.types.Code  t) { return  t.getOperator() ;}private static boolean tom_is_fun_sym_IsCons( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.IsCons ;}private static  String  tom_get_slot_IsCons_Var( tom.gom.adt.code.types.Code  t) { return  t.getVar() ;}private static  tom.gom.adt.gom.types.OperatorDecl  tom_get_slot_IsCons_Operator( tom.gom.adt.code.types.Code  t) { return  t.getOperator() ;}private static boolean tom_is_fun_sym_Empty( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.Empty ;}private static  tom.gom.adt.gom.types.OperatorDecl  tom_get_slot_Empty_Operator( tom.gom.adt.code.types.Code  t) { return  t.getOperator() ;}private static boolean tom_is_fun_sym_Cons( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.Cons ;}private static  tom.gom.adt.gom.types.OperatorDecl  tom_get_slot_Cons_Operator( tom.gom.adt.code.types.Code  t) { return  t.getOperator() ;}private static boolean tom_is_fun_sym_FullSortClass( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.FullSortClass ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_FullSortClass_Sort( tom.gom.adt.code.types.Code  t) { return  t.getSort() ;}private static boolean tom_is_fun_sym_FullOperatorClass( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.FullOperatorClass ;}private static  tom.gom.adt.gom.types.OperatorDecl  tom_get_slot_FullOperatorClass_Operator( tom.gom.adt.code.types.Code  t) { return  t.getOperator() ;}private static boolean tom_is_fun_sym_Compare( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.Compare ;}private static  tom.gom.adt.code.types.Code  tom_get_slot_Compare_LCode( tom.gom.adt.code.types.Code  t) { return  t.getLCode() ;}private static  tom.gom.adt.code.types.Code  tom_get_slot_Compare_RCode( tom.gom.adt.code.types.Code  t) { return  t.getRCode() ;}private static boolean tom_is_fun_sym_Variadic( tom.gom.adt.gom.types.TypedProduction  t) { return  t instanceof tom.gom.adt.gom.types.typedproduction.Variadic ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_Variadic_Sort( tom.gom.adt.gom.types.TypedProduction  t) { return  t.getSort() ;}private static boolean tom_is_fun_sym_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  t) { return  t instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl ;}private static  String  tom_get_slot_OperatorDecl_Name( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getName() ;}private static  tom.gom.adt.gom.types.SortDecl  tom_get_slot_OperatorDecl_Sort( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getSort() ;}private static  tom.gom.adt.gom.types.TypedProduction  tom_get_slot_OperatorDecl_Prod( tom.gom.adt.gom.types.OperatorDecl  t) { return  t.getProd() ;}private static boolean tom_is_fun_sym_SortDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t instanceof tom.gom.adt.gom.types.sortdecl.SortDecl ;}private static  String  tom_get_slot_SortDecl_Name( tom.gom.adt.gom.types.SortDecl  t) { return  t.getName() ;}private static  tom.gom.adt.gom.types.ModuleDecl  tom_get_slot_SortDecl_ModuleDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t.getModuleDecl() ;}private static boolean tom_is_fun_sym_BuiltinSortDecl( tom.gom.adt.gom.types.SortDecl  t) { return  t instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl ;}private static  String  tom_get_slot_BuiltinSortDecl_Name( tom.gom.adt.gom.types.SortDecl  t) { return  t.getName() ;}private static boolean tom_is_fun_sym_CodeList( tom.gom.adt.code.types.Code  t) { return  t instanceof tom.gom.adt.code.types.code.ConsCodeList || t instanceof tom.gom.adt.code.types.code.EmptyCodeList ;}private static  tom.gom.adt.code.types.Code  tom_empty_list_CodeList() { return  tom.gom.adt.code.types.code.EmptyCodeList.make() ; }private static  tom.gom.adt.code.types.Code  tom_cons_list_CodeList( tom.gom.adt.code.types.Code  e,  tom.gom.adt.code.types.Code  l) { return  tom.gom.adt.code.types.code.ConsCodeList.make(e,l) ; }private static  tom.gom.adt.code.types.Code  tom_get_head_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.getHeadCodeList() ;}private static  tom.gom.adt.code.types.Code  tom_get_tail_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.getTailCodeList() ;}private static boolean tom_is_empty_CodeList_Code( tom.gom.adt.code.types.Code  l) { return  l.isEmptyCodeList() ;}   private static   tom.gom.adt.code.types.Code  tom_append_list_CodeList( tom.gom.adt.code.types.Code l1,  tom.gom.adt.code.types.Code  l2) {     if(tom_is_empty_CodeList_Code(l1)) {       return l2;     } else if(tom_is_empty_CodeList_Code(l2)) {       return l1;     } else if(tom_is_fun_sym_CodeList(l1)) {       if(tom_is_empty_CodeList_Code(((tom_is_fun_sym_CodeList(l1))?tom_get_tail_CodeList_Code(l1):tom_empty_list_CodeList()))) {         return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_head_CodeList_Code(l1):l1),l2);       } else {         return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_head_CodeList_Code(l1):l1),tom_append_list_CodeList(((tom_is_fun_sym_CodeList(l1))?tom_get_tail_CodeList_Code(l1):tom_empty_list_CodeList()),l2));       }     } else {       return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(l1, l2);     }   }   private static   tom.gom.adt.code.types.Code  tom_get_slice_CodeList( tom.gom.adt.code.types.Code  begin,  tom.gom.adt.code.types.Code  end, tom.gom.adt.code.types.Code  tail) {     if(tom_equal_term_Code(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.code.types.Code )tom_cons_list_CodeList(((tom_is_fun_sym_CodeList(begin))?tom_get_head_CodeList_Code(begin):begin),( tom.gom.adt.code.types.Code )tom_get_slice_CodeList(((tom_is_fun_sym_CodeList(begin))?tom_get_tail_CodeList_Code(begin):tom_empty_list_CodeList()),end,tail));     }   }    

  private CodeGen() {
    /* Prevent instantiation */
  }
  
  /**
   * Generate code in a String.
   * 
   * @params code the Code to generate
   * @return the generated code
   */
  public static String generateCode(Code code) {
    StringWriter writer = new StringWriter();
    try {
      generateCode(code,writer);
    } catch (java.io.IOException e) {
      Logger.getLogger("CodeGen").log(
          Level.SEVERE,"Failed to generate code for " + code);
      
    }
    return writer.toString();
  }

  /**
   * Generate code in a writer.
   * 
   * @params code the Code to generate
   * @params writer where to generate
   */
  public static void generateCode(Code code, Writer writer)
    throws java.io.IOException {
    if (tom_is_sort_Code(code)) {{  tom.gom.adt.code.types.Code  tomMatch328NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if (tom_is_fun_sym_Code(tomMatch328NameNumberfreshSubject_1)) {{  String  tomMatch328NameNumber_freshVar_0=tom_get_slot_Code_prog(tomMatch328NameNumberfreshSubject_1);if ( true ) {

        writer.write(tomMatch328NameNumber_freshVar_0);
        return;
      }}}{ boolean tomMatch328NameNumber_freshVar_2= false ;{  tom.gom.adt.gom.types.OperatorDecl  tomMatch328NameNumber_freshVar_1= null ;if (tom_is_fun_sym_Empty(tomMatch328NameNumberfreshSubject_1)) {{tomMatch328NameNumber_freshVar_2= true ;tomMatch328NameNumber_freshVar_1=tom_get_slot_Empty_Operator(tomMatch328NameNumberfreshSubject_1);}} else {if (tom_is_fun_sym_Cons(tomMatch328NameNumberfreshSubject_1)) {{tomMatch328NameNumber_freshVar_2= true ;tomMatch328NameNumber_freshVar_1=tom_get_slot_Cons_Operator(tomMatch328NameNumberfreshSubject_1);}}}if ((tomMatch328NameNumber_freshVar_2 ==  true )) {{  tom.gom.adt.gom.types.OperatorDecl  tom_opdecl=tomMatch328NameNumber_freshVar_1;if ( true ) {if (tom_is_sort_OperatorDecl(tom_opdecl)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch325NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom_opdecl);if (tom_is_fun_sym_OperatorDecl(tomMatch325NameNumberfreshSubject_1)) {{  String  tomMatch325NameNumber_freshVar_0=tom_get_slot_OperatorDecl_Name(tomMatch325NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SortDecl  tomMatch325NameNumber_freshVar_1=tom_get_slot_OperatorDecl_Sort(tomMatch325NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.TypedProduction  tomMatch325NameNumber_freshVar_2=tom_get_slot_OperatorDecl_Prod(tomMatch325NameNumberfreshSubject_1);{  String  tom_opName=tomMatch325NameNumber_freshVar_0;if (tom_is_fun_sym_SortDecl(tomMatch325NameNumber_freshVar_1)) {{  String  tomMatch325NameNumber_freshVar_3=tom_get_slot_SortDecl_Name(tomMatch325NameNumber_freshVar_1);{  tom.gom.adt.gom.types.ModuleDecl  tomMatch325NameNumber_freshVar_4=tom_get_slot_SortDecl_ModuleDecl(tomMatch325NameNumber_freshVar_1);if (tom_is_fun_sym_Variadic(tomMatch325NameNumber_freshVar_2)) {if ( true ) {





            String tName = tom_opName;
            if (tom_is_sort_Code(code)) {{  tom.gom.adt.code.types.Code  tomMatch324NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if (tom_is_fun_sym_Empty(tomMatch324NameNumberfreshSubject_1)) {if ( true ) {

                tName = "Empty" + tom_opName;
              }}if (tom_is_fun_sym_Cons(tomMatch324NameNumberfreshSubject_1)) {if ( true ) {

                tName = "Cons" + tom_opName;
              }}}}

            String sortNamePackage = tomMatch325NameNumber_freshVar_3.toLowerCase();
            ClassName className = tom_make_ClassName(tom.gom.compiler.Compiler.packagePrefix(tomMatch325NameNumber_freshVar_4)+".types."+sortNamePackage,tName)

;
            writer.write(tom.gom.backend.TemplateClass.fullClassName(className));        
            return;
          }}}}}}}}}}}}

        Logger.getLogger("CodeGen").log(
            Level.SEVERE,"{Empty,Cons}: expecting varidic, but got {0}",
            new Object[] { (tom_opdecl) });
        return;
      }}}}}{ boolean tomMatch328NameNumber_freshVar_5= false ;{  tom.gom.adt.gom.types.OperatorDecl  tomMatch328NameNumber_freshVar_4= null ;{  String  tomMatch328NameNumber_freshVar_3= "" ;if (tom_is_fun_sym_IsEmpty(tomMatch328NameNumberfreshSubject_1)) {{tomMatch328NameNumber_freshVar_5= true ;tomMatch328NameNumber_freshVar_3=tom_get_slot_IsEmpty_Var(tomMatch328NameNumberfreshSubject_1);tomMatch328NameNumber_freshVar_4=tom_get_slot_IsEmpty_Operator(tomMatch328NameNumberfreshSubject_1);}} else {if (tom_is_fun_sym_IsCons(tomMatch328NameNumberfreshSubject_1)) {{tomMatch328NameNumber_freshVar_5= true ;tomMatch328NameNumber_freshVar_3=tom_get_slot_IsCons_Var(tomMatch328NameNumberfreshSubject_1);tomMatch328NameNumber_freshVar_4=tom_get_slot_IsCons_Operator(tomMatch328NameNumberfreshSubject_1);}}}if ((tomMatch328NameNumber_freshVar_5 ==  true )) {{  tom.gom.adt.gom.types.OperatorDecl  tom_opdecl=tomMatch328NameNumber_freshVar_4;if ( true ) {if (tom_is_sort_OperatorDecl(tom_opdecl)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch327NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom_opdecl);if (tom_is_fun_sym_OperatorDecl(tomMatch327NameNumberfreshSubject_1)) {{  String  tomMatch327NameNumber_freshVar_0=tom_get_slot_OperatorDecl_Name(tomMatch327NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.TypedProduction  tomMatch327NameNumber_freshVar_1=tom_get_slot_OperatorDecl_Prod(tomMatch327NameNumberfreshSubject_1);if (tom_is_fun_sym_Variadic(tomMatch327NameNumber_freshVar_1)) {if ( true ) {



            writer.write(tomMatch328NameNumber_freshVar_3);
            if (tom_is_sort_Code(code)) {{  tom.gom.adt.code.types.Code  tomMatch326NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if (tom_is_fun_sym_IsEmpty(tomMatch326NameNumberfreshSubject_1)) {if ( true ) {

                writer.write(".isEmpty");
              }}if (tom_is_fun_sym_IsCons(tomMatch326NameNumberfreshSubject_1)) {if ( true ) {

                writer.write(".isCons");
              }}}}

            writer.write(tomMatch327NameNumber_freshVar_0);
            writer.write("()");
            return;
          }}}}}}}

        Logger.getLogger("CodeGen").log(
            Level.SEVERE,"Is{Empty,Cons}: expecting varidic, but got {0}",
            new Object[] { (tom_opdecl) });
        return;
      }}}}}}if (tom_is_fun_sym_FullOperatorClass(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch328NameNumber_freshVar_6=tom_get_slot_FullOperatorClass_Operator(tomMatch328NameNumberfreshSubject_1);if (tom_is_fun_sym_OperatorDecl(tomMatch328NameNumber_freshVar_6)) {{  String  tomMatch328NameNumber_freshVar_7=tom_get_slot_OperatorDecl_Name(tomMatch328NameNumber_freshVar_6);{  tom.gom.adt.gom.types.SortDecl  tomMatch328NameNumber_freshVar_8=tom_get_slot_OperatorDecl_Sort(tomMatch328NameNumber_freshVar_6);if (tom_is_fun_sym_SortDecl(tomMatch328NameNumber_freshVar_8)) {{  String  tomMatch328NameNumber_freshVar_9=tom_get_slot_SortDecl_Name(tomMatch328NameNumber_freshVar_8);{  tom.gom.adt.gom.types.ModuleDecl  tomMatch328NameNumber_freshVar_10=tom_get_slot_SortDecl_ModuleDecl(tomMatch328NameNumber_freshVar_8);if ( true ) {






        String sortNamePackage = tomMatch328NameNumber_freshVar_9.toLowerCase();
        ClassName className = tom_make_ClassName(tom.gom.compiler.Compiler.packagePrefix(tomMatch328NameNumber_freshVar_10)+".types."+sortNamePackage,tomMatch328NameNumber_freshVar_7)

;
        writer.write(tom.gom.backend.TemplateClass.fullClassName(className));        
        return;
      }}}}}}}}}if (tom_is_fun_sym_FullSortClass(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.SortDecl  tomMatch328NameNumber_freshVar_11=tom_get_slot_FullSortClass_Sort(tomMatch328NameNumberfreshSubject_1);if (tom_is_fun_sym_SortDecl(tomMatch328NameNumber_freshVar_11)) {{  String  tomMatch328NameNumber_freshVar_12=tom_get_slot_SortDecl_Name(tomMatch328NameNumber_freshVar_11);{  tom.gom.adt.gom.types.ModuleDecl  tomMatch328NameNumber_freshVar_13=tom_get_slot_SortDecl_ModuleDecl(tomMatch328NameNumber_freshVar_11);if ( true ) {

        ClassName sortClassName = tom_make_ClassName(tom.gom.compiler.Compiler.packagePrefix(tomMatch328NameNumber_freshVar_13)+".types",tomMatch328NameNumber_freshVar_12)
;
        writer.write(tom.gom.backend.TemplateClass.fullClassName(sortClassName));        
        return;
      }}}}}}if (tom_is_fun_sym_FullSortClass(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.SortDecl  tomMatch328NameNumber_freshVar_14=tom_get_slot_FullSortClass_Sort(tomMatch328NameNumberfreshSubject_1);if (tom_is_fun_sym_BuiltinSortDecl(tomMatch328NameNumber_freshVar_14)) {{  String  tomMatch328NameNumber_freshVar_15=tom_get_slot_BuiltinSortDecl_Name(tomMatch328NameNumber_freshVar_14);if ( true ) {

        writer.write(tomMatch328NameNumber_freshVar_15);        
        return;
      }}}}}if (tom_is_fun_sym_Compare(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.code.types.Code  tomMatch328NameNumber_freshVar_16=tom_get_slot_Compare_LCode(tomMatch328NameNumberfreshSubject_1);{  tom.gom.adt.code.types.Code  tomMatch328NameNumber_freshVar_17=tom_get_slot_Compare_RCode(tomMatch328NameNumberfreshSubject_1);if ( true ) {

        generateCode(tomMatch328NameNumber_freshVar_16, writer);
        writer.write(".compareTo(");
        generateCode(tomMatch328NameNumber_freshVar_17, writer);
        writer.write(")");
        return;
      }}}}if (tom_is_fun_sym_CodeList(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.code.types.Code  tomMatch328NameNumber_freshVar_18=tomMatch328NameNumberfreshSubject_1;if ( ( tom_is_empty_CodeList_Code(tomMatch328NameNumber_freshVar_18) || tom_equal_term_Code(tomMatch328NameNumber_freshVar_18, tom_empty_list_CodeList()) ) ) {if ( true ) {
 return ; }}}}if (tom_is_fun_sym_CodeList(tomMatch328NameNumberfreshSubject_1)) {{  tom.gom.adt.code.types.Code  tomMatch328NameNumber_freshVar_19=tomMatch328NameNumberfreshSubject_1;if (!( ( tom_is_empty_CodeList_Code(tomMatch328NameNumber_freshVar_19) || tom_equal_term_Code(tomMatch328NameNumber_freshVar_19, tom_empty_list_CodeList()) ) )) {{  tom.gom.adt.code.types.Code  tomMatch328NameNumber_freshVar_20=((tom_is_fun_sym_CodeList(tomMatch328NameNumber_freshVar_19))?(tom_get_tail_CodeList_Code(tomMatch328NameNumber_freshVar_19)):(tom_empty_list_CodeList()));if ( true ) {

        generateCode(((tom_is_fun_sym_CodeList(tomMatch328NameNumber_freshVar_19))?(tom_get_head_CodeList_Code(tomMatch328NameNumber_freshVar_19)):(tomMatch328NameNumber_freshVar_19)),writer);
        generateCode(tomMatch328NameNumber_freshVar_20,writer);
        return;
      }}}}}}}

    throw new GomRuntimeException("Can't generate code for " + code);
  }
}
