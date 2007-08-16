/* Generated by TOM (version 2.5): Do not edit this file */ /* Gom
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
import tom.gom.adt.objects.*;
import tom.gom.adt.objects.types.*;
import tom.gom.tools.error.GomRuntimeException;

public class BasicStrategyTemplate extends TemplateClass {
  ClassName visitor;
  ClassNameList importedVisitors;
  ClassName abstractType;
  ClassNameList importedAbstractTypes;
  GomClassList sortClasses;
  GomClassList operatorClasses;


  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */   /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_SlotFieldList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SlotFieldList(Object t) { return  t instanceof tom.gom.adt.objects.types.SlotFieldList ;}private static boolean tom_equal_term_GomClass(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClass(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClass ;}private static boolean tom_equal_term_ClassName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassName(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassName ;}private static boolean tom_equal_term_GomClassList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClassList(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClassList ;}private static boolean tom_equal_term_ClassNameList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassNameList(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassNameList ;}private static boolean tom_equal_term_HookList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_HookList(Object t) { return  t instanceof tom.gom.adt.objects.types.HookList ;}private static boolean tom_is_fun_sym_SortClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.SortClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_AbstractType( tom.gom.adt.objects.types.GomClass  t) { return  t.getAbstractType() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Mapping( tom.gom.adt.objects.types.GomClass  t) { return  t.getMapping() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Visitor( tom.gom.adt.objects.types.GomClass  t) { return  t.getVisitor() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_SortClass_Forward( tom.gom.adt.objects.types.GomClass  t) { return  t.getForward() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_SortClass_Operators( tom.gom.adt.objects.types.GomClass  t) { return  t.getOperators() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_SortClass_VariadicOperators( tom.gom.adt.objects.types.GomClass  t) { return  t.getVariadicOperators() ;}private static  tom.gom.adt.objects.types.SlotFieldList  tom_get_slot_SortClass_Slots( tom.gom.adt.objects.types.GomClass  t) { return  t.getSlots() ;}private static  tom.gom.adt.objects.types.HookList  tom_get_slot_SortClass_Hooks( tom.gom.adt.objects.types.GomClass  t) { return  t.getHooks() ;}private static boolean tom_is_fun_sym_VisitableFwdClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.VisitableFwdClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_VisitableFwdClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_VisitableFwdClass_Visitor( tom.gom.adt.objects.types.GomClass  t) { return  t.getVisitor() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_VisitableFwdClass_ImportedVisitors( tom.gom.adt.objects.types.GomClass  t) { return  t.getImportedVisitors() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_VisitableFwdClass_AbstractType( tom.gom.adt.objects.types.GomClass  t) { return  t.getAbstractType() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_VisitableFwdClass_ImportedAbstractTypes( tom.gom.adt.objects.types.GomClass  t) { return  t.getImportedAbstractTypes() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_VisitableFwdClass_SortClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getSortClasses() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_VisitableFwdClass_OperatorClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getOperatorClasses() ;}private static boolean tom_is_fun_sym_concGomClass( tom.gom.adt.objects.types.GomClassList  t) { return  t instanceof tom.gom.adt.objects.types.gomclasslist.ConsconcGomClass || t instanceof tom.gom.adt.objects.types.gomclasslist.EmptyconcGomClass ;}private static  tom.gom.adt.objects.types.GomClassList  tom_empty_list_concGomClass() { return  tom.gom.adt.objects.types.gomclasslist.EmptyconcGomClass.make() ; }private static  tom.gom.adt.objects.types.GomClassList  tom_cons_list_concGomClass( tom.gom.adt.objects.types.GomClass  e,  tom.gom.adt.objects.types.GomClassList  l) { return  tom.gom.adt.objects.types.gomclasslist.ConsconcGomClass.make(e,l) ; }private static  tom.gom.adt.objects.types.GomClass  tom_get_head_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.getHeadconcGomClass() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_tail_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.getTailconcGomClass() ;}private static boolean tom_is_empty_concGomClass_GomClassList( tom.gom.adt.objects.types.GomClassList  l) { return  l.isEmptyconcGomClass() ;}   private static   tom.gom.adt.objects.types.GomClassList  tom_append_list_concGomClass( tom.gom.adt.objects.types.GomClassList l1,  tom.gom.adt.objects.types.GomClassList  l2) {     if(tom_is_empty_concGomClass_GomClassList(l1)) {       return l2;     } else if(tom_is_empty_concGomClass_GomClassList(l2)) {       return l1;     } else if(tom_is_empty_concGomClass_GomClassList(tom_get_tail_concGomClass_GomClassList(l1))) {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(l1),l2);     } else {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(l1),tom_append_list_concGomClass(tom_get_tail_concGomClass_GomClassList(l1),l2));     }   }   private static   tom.gom.adt.objects.types.GomClassList  tom_get_slice_concGomClass( tom.gom.adt.objects.types.GomClassList  begin,  tom.gom.adt.objects.types.GomClassList  end, tom.gom.adt.objects.types.GomClassList  tail) {     if(tom_equal_term_GomClassList(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.objects.types.GomClassList )tom_cons_list_concGomClass(tom_get_head_concGomClass_GomClassList(begin),( tom.gom.adt.objects.types.GomClassList )tom_get_slice_concGomClass(tom_get_tail_concGomClass_GomClassList(begin),end,tail));     }   }    

  public BasicStrategyTemplate(GomClass basic) {
    super(basic);
    if (tom_is_sort_GomClass(basic)) {{  tom.gom.adt.objects.types.GomClass  tomMatch354NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClass )basic);if (tom_is_fun_sym_VisitableFwdClass(tomMatch354NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.ClassName  tomMatch354NameNumber_freshVar_0=tom_get_slot_VisitableFwdClass_Visitor(tomMatch354NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.ClassNameList  tomMatch354NameNumber_freshVar_1=tom_get_slot_VisitableFwdClass_ImportedVisitors(tomMatch354NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.ClassName  tomMatch354NameNumber_freshVar_2=tom_get_slot_VisitableFwdClass_AbstractType(tomMatch354NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.ClassNameList  tomMatch354NameNumber_freshVar_3=tom_get_slot_VisitableFwdClass_ImportedAbstractTypes(tomMatch354NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.GomClassList  tomMatch354NameNumber_freshVar_4=tom_get_slot_VisitableFwdClass_SortClasses(tomMatch354NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.GomClassList  tomMatch354NameNumber_freshVar_5=tom_get_slot_VisitableFwdClass_OperatorClasses(tomMatch354NameNumberfreshSubject_1);if ( true ) {






        this.visitor = tomMatch354NameNumber_freshVar_0;
        this.importedVisitors = tomMatch354NameNumber_freshVar_1;
        this.abstractType = tomMatch354NameNumber_freshVar_2;
        this.importedAbstractTypes = tomMatch354NameNumber_freshVar_3;
        this.sortClasses = tomMatch354NameNumber_freshVar_4;
        this.operatorClasses = tomMatch354NameNumber_freshVar_5;
        return;
      }}}}}}}}}}

    throw new GomRuntimeException(
        "Wrong argument for BasicStrategyTemplate: " + basic);
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
    writer.write("\npackage "/* Generated by TOM (version 2.5): Do not edit this file */+getPackage()+";\nimport tom.library.sl.*;\n   \n  public class "/* Generated by TOM (version 2.5): Do not edit this file */+className()+" implements tom.library.sl.Strategy,"/* Generated by TOM (version 2.5): Do not edit this file */+ className(visitor)+importedVisitorList(importedVisitors) +" {\n  private tom.library.sl.Environment environment;\n  protected Strategy any;\n  \n  public "/* Generated by TOM (version 2.5): Do not edit this file */+className()+"(Strategy v) {\n    this.any = v;\n  }\n    \n  public int getChildCount() {\n    return 1;\n  }\n    \n  public Visitable getChildAt(int i) {\n    switch (i) {\n      case 0: return (Visitable) any;\n      default: throw new IndexOutOfBoundsException();\n    }\n  }\n    \n  public Visitable setChildAt(int i, Visitable child) {\n    switch (i) {\n      case 0: any = (Strategy) child; return this;\n      default: throw new IndexOutOfBoundsException();\n    }\n  }\n\n  public Visitable[] getChildren() {\n    return new Visitable[]{(Visitable)any};\n  }\n\n  public Visitable setChildren(Visitable[] children) {\n    any = (Strategy)children[0];\n    return this;\n  }\n\n  public tom.library.sl.Strategy accept(tom.library.sl.reflective.StrategyFwd v) throws tom.library.sl.VisitFailure {\n    return v.visit_Strategy(this);\n  } \n\n  public tom.library.sl.Environment getEnvironment() {\n    if(environment!=null) {\n      return environment;\n    } else {\n      throw new java.lang.RuntimeException(\"environment not initialized\");\n    }\n  }\n\n  public void setEnvironment(tom.library.sl.Environment env) {\n    this.environment = env;\n  }\n \n  public Visitable visit(Environment envt) throws VisitFailure {\n    setEnvironment(envt);\n    int status = visit();\n    if(status == Environment.SUCCESS) {\n      return environment.getSubject();\n    } else {\n      throw new VisitFailure();\n    }\n  }\n\n\n  public tom.library.sl.Visitable visit(tom.library.sl.Visitable any) throws VisitFailure {\n    tom.library.sl.AbstractStrategy.init(this,new tom.library.sl.Environment());\n    environment.setRoot(any);\n    int status = visit();\n    if(status == tom.library.sl.Environment.SUCCESS) {\n      return environment.getRoot();\n    } else {\n      throw new VisitFailure();\n    }\n  }\n\n  public int visit() {\n    try {\n      environment.setSubject((tom.library.sl.Visitable)this.visitLight(environment.getSubject()));\n      return tom.library.sl.Environment.SUCCESS;\n    } catch(VisitFailure f) {\n      return tom.library.sl.Environment.FAILURE;\n    }\n  }\n\n  public Visitable visitLight(Visitable v) throws VisitFailure {\n    if (v instanceof "/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(abstractType)+") {\n      return (("/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(abstractType)+") v).accept(this);\n    }\n"

























































































);
generateDispatch(writer,importedAbstractTypes);
writer.write("\n    else {\n      return any.visitLight(v);\n    }\n  }\n"




);
generateVisitMethods(writer);
writer.write("\n}\n"

);
}

  private void generateVisitMethods(java.io.Writer writer) throws java.io.IOException {
    // generate a visit for each sort
    if (tom_is_sort_GomClassList(sortClasses)) {{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClassList )sortClasses);if (tom_is_fun_sym_concGomClass(tomMatch355NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumber_freshVar_0=tomMatch355NameNumberfreshSubject_1;{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumber_begin_2=tomMatch355NameNumber_freshVar_0;{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumber_end_3=tomMatch355NameNumber_freshVar_0;do {{{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumber_freshVar_1=tomMatch355NameNumber_end_3;if (!(tom_is_empty_concGomClass_GomClassList(tomMatch355NameNumber_freshVar_1))) {if (tom_is_fun_sym_SortClass(tom_get_head_concGomClass_GomClassList(tomMatch355NameNumber_freshVar_1))) {{  tom.gom.adt.objects.types.ClassName  tomMatch355NameNumber_freshVar_6=tom_get_slot_SortClass_ClassName(tom_get_head_concGomClass_GomClassList(tomMatch355NameNumber_freshVar_1));{  tom.gom.adt.objects.types.ClassName  tom_sortName=tomMatch355NameNumber_freshVar_6;{  tom.gom.adt.objects.types.GomClassList  tomMatch355NameNumber_freshVar_4=tom_get_tail_concGomClass_GomClassList(tomMatch355NameNumber_freshVar_1);if ( true ) {


        writer.write("\n  public "/* Generated by TOM (version 2.5): Do not edit this file */+ fullClassName(tom_sortName) +" "/* Generated by TOM (version 2.5): Do not edit this file */+visitMethod(tom_sortName)+"("/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(tom_sortName)+" arg) throws VisitFailure {\n   if(environment!=null) {\n      //TODO: must be removed\n      assert(arg.equals(environment.getSubject()));\n   return ("/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(tom_sortName)+") any.visit(environment);\n   } else {\n    return ("/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(tom_sortName)+") any.visitLight(arg);\n   }\n }\n"









);
      }}}}}}}if (tom_is_empty_concGomClass_GomClassList(tomMatch355NameNumber_end_3)) {tomMatch355NameNumber_end_3=tomMatch355NameNumber_begin_2;} else {tomMatch355NameNumber_end_3=tom_get_tail_concGomClass_GomClassList(tomMatch355NameNumber_end_3);}}} while(!(tom_equal_term_GomClassList(tomMatch355NameNumber_end_3, tomMatch355NameNumber_begin_2)));}}}}}}

  }

  private void generateDispatch(java.io.Writer writer, ClassNameList types) throws java.io.IOException {
    while(!types.isEmptyconcClassName()) {
      writer.write("    else if (v instanceof "/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(types.getHeadconcClassName())+") {\n      return (("/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(types.getHeadconcClassName())+") v).accept(this);\n    }"

);
      types = types.getTailconcClassName();
    }
  }
  
  private String importedVisitorList(ClassNameList list) {
    StringBuffer out = new StringBuffer();
    while(!list.isEmptyconcClassName()) {
      out.append(", "+fullClassName(list.getHeadconcClassName()));
      list = list.getTailconcClassName();
    }
    return out.toString();
  }

}
