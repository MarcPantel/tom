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

package tom.gom.backend.shared;

import tom.gom.backend.TemplateClass;
import tom.gom.adt.objects.types.*;
import tom.gom.tools.error.GomRuntimeException;

public class VisitorTemplate extends TemplateClass {
  GomClassList sortClasses;
  GomClassList operatorClasses;

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */   /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_SlotFieldList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SlotFieldList(Object t) { return  t instanceof tom.gom.adt.objects.types.SlotFieldList ;}private static boolean tom_equal_term_GomClass(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClass(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClass ;}private static boolean tom_equal_term_ClassName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassName(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassName ;}private static boolean tom_equal_term_GomClassList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClassList(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClassList ;}private static boolean tom_equal_term_ClassNameList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassNameList(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassNameList ;}private static boolean tom_equal_term_HookList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_HookList(Object t) { return  t instanceof tom.gom.adt.objects.types.HookList ;}private static boolean tom_is_fun_sym_SortClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.SortClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_AbstractType( tom.gom.adt.objects.types.GomClass  t) { return  t.getAbstractType() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Mapping( tom.gom.adt.objects.types.GomClass  t) { return  t.getMapping() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Visitor( tom.gom.adt.objects.types.GomClass  t) { return  t.getVisitor() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Forward( tom.gom.adt.objects.types.GomClass  t) { return  t.getForward() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_SortClass_Operators( tom.gom.adt.objects.types.GomClass  t) { return  t.getOperators() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_SortClass_VariadicOperators( tom.gom.adt.objects.types.GomClass  t) { return  t.getVariadicOperators() ;}private static  tom.gom.adt.objects.types.SlotFieldList  tom_get_slot_SortClass_Slots( tom.gom.adt.objects.types.GomClass  t) { return  t.getSlots() ;}private static  tom.gom.adt.objects.types.HookList  tom_get_slot_SortClass_Hooks( tom.gom.adt.objects.types.GomClass  t) { return  t.getHooks() ;}private static boolean tom_is_fun_sym_VisitorClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.VisitorClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_VisitorClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_VisitorClass_SortClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getSortClasses() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_VisitorClass_OperatorClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getOperatorClasses() ;}private static boolean tom_is_fun_sym_concGomClass( tom.gom.adt.objects.types.GomClassList  t) { return  t instanceof tom.gom.adt.objects.types.gomclasslist.ConsconcGomClass || t instanceof tom.gom.adt.objects.types.gomclasslist.EmptyconcGomClass ;}private static  tom.gom.adt.objects.types.GomClassList  tom_empty_list_concGomClass() { return  tom.gom.adt.objects.types.gomclasslist.EmptyconcGomClass.make() ; }private static  tom.gom.adt.objects.types.GomClassList  tom_cons_list_concGomClass( tom.gom.adt.objects.types.GomClass  e,  tom.gom.adt.objects.types.GomClassList  l) { return  tom.gom.adt.objects.types.gomclasslist.ConsconcGomClass.make(e,l) ; }private static  tom.gom.adt.objects.types.GomClass  tom_get_head_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.getHeadconcGomClass() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_tail_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.getTailconcGomClass() ;}private static boolean tom_is_empty_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.isEmptyconcGomClass() ;}   private static   tom.gom.adt.objects.types.GomClassList  tom_append_list_concGomClass( tom.gom.adt.objects.types.GomClassList l1,  tom.gom.adt.objects.types.GomClassList  l2) {     if(tom_is_empty_concGomClass_GomClassList(l1)) {       return l2;     } else if(tom_is_empty_concGomClass_GomClassList(l2)) {       return l1;     } else if(tom_is_empty_concGomClass_GomClassList(tom_get_tail_concGomClass_GomClassList(l1))) {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(l1),l2);     } else {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(l1),tom_append_list_concGomClass(tom_get_tail_concGomClass_GomClassList(l1),l2));     }   }   private static   tom.gom.adt.objects.types.GomClassList  tom_get_slice_concGomClass( tom.gom.adt.objects.types.GomClassList  begin,  tom.gom.adt.objects.types.GomClassList  end, tom.gom.adt.objects.types.GomClassList  tail) {     if(tom_equal_term_GomClassList(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(begin),( tom.gom.adt.objects.types.GomClassList )tom_get_slice_concGomClass(tom_get_tail_concGomClass_GomClassList(begin),end,tail));     }   }    

  public VisitorTemplate(GomClass gomClass) {
    super(gomClass);
    if (tom_is_sort_GomClass(gomClass)) {{  tom.gom.adt.objects.types.GomClass  tomMatch379NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClass )gomClass);if (tom_is_fun_sym_VisitorClass(tomMatch379NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.GomClassList  tomMatch379NameNumber_freshVar_0=tom_get_slot_VisitorClass_SortClasses(tomMatch379NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.GomClassList  tomMatch379NameNumber_freshVar_1=tom_get_slot_VisitorClass_OperatorClasses(tomMatch379NameNumberfreshSubject_1);if ( true ) {


        this.sortClasses = tomMatch379NameNumber_freshVar_0;
        this.operatorClasses = tomMatch379NameNumber_freshVar_1;
        return;
      }}}}}}

    throw new GomRuntimeException(
        "Bad argument for VisitorTemplate: " + gomClass);
  }

  /* We may want to return the stringbuffer itself in the future, or directly write to a Stream */
  public void generate(java.io.Writer writer) throws java.io.IOException {
    writer.write("package "+getPackage()+";\n");
    writer.write("\n");
    writer.write("public interface "+className()+" {\n");
    writer.write("\n");

    // generate a visit for each sort
    if (tom_is_sort_GomClassList(sortClasses)) {{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClassList )sortClasses);if (tom_is_fun_sym_concGomClass(tomMatch380NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumber_freshVar_0=tomMatch380NameNumberfreshSubject_1;{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumber_begin_2=tomMatch380NameNumber_freshVar_0;{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumber_end_3=tomMatch380NameNumber_freshVar_0;do {{{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumber_freshVar_1=tomMatch380NameNumber_end_3;if (!(tom_is_empty_concGomClass_GomClassList(tomMatch380NameNumber_freshVar_1))) {if (tom_is_fun_sym_SortClass(tom_get_head_concGomClass_GomClassList(tomMatch380NameNumber_freshVar_1))) {{  tom.gom.adt.objects.types.ClassName  tomMatch380NameNumber_freshVar_6=tom_get_slot_SortClass_ClassName(tom_get_head_concGomClass_GomClassList(tomMatch380NameNumber_freshVar_1));{  tom.gom.adt.objects.types.ClassName  tom_sortName=tomMatch380NameNumber_freshVar_6;{  tom.gom.adt.objects.types.GomClassList  tomMatch380NameNumber_freshVar_4=tom_get_tail_concGomClass_GomClassList(tomMatch380NameNumber_freshVar_1);if ( true ) {

        writer.write("\tpublic "+fullClassName(tom_sortName)+" "+visitMethod(tom_sortName)+"("+fullClassName(tom_sortName)+" arg) throws tom.library.sl.VisitFailure;\n");
      }}}}}}}if (tom_is_empty_concGomClass_GomClassList(tomMatch380NameNumber_end_3)) {tomMatch380NameNumber_end_3=tomMatch380NameNumber_begin_2;} else {tomMatch380NameNumber_end_3=tom_get_tail_concGomClass_GomClassList(tomMatch380NameNumber_end_3);}}} while(!(tom_equal_term_GomClassList(tomMatch380NameNumber_end_3, tomMatch380NameNumber_begin_2)));}}}}}}


    writer.write("\n");
    writer.write("}\n");
  }

}
