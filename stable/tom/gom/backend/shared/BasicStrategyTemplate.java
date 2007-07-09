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
  ClassName fwd;

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */   /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_GomClass(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClass(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClass ;}private static boolean tom_equal_term_ClassName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassName(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassName ;}private static boolean tom_equal_term_GomClassList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClassList(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClassList ;}private static boolean tom_equal_term_ClassNameList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassNameList(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassNameList ;}private static boolean tom_is_fun_sym_VisitableFwdClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.VisitableFwdClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_VisitableFwdClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.GomClass  tom_get_slot_VisitableFwdClass_Fwd( tom.gom.adt.objects.types.GomClass  t) { return  t.getFwd() ;}private static boolean tom_is_fun_sym_FwdClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.FwdClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_FwdClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_FwdClass_Visitor( tom.gom.adt.objects.types.GomClass  t) { return  t.getVisitor() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_FwdClass_ImportedVisitors( tom.gom.adt.objects.types.GomClass  t) { return  t.getImportedVisitors() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_FwdClass_AbstractType( tom.gom.adt.objects.types.GomClass  t) { return  t.getAbstractType() ;}private static  tom.gom.adt.objects.types.ClassNameList  tom_get_slot_FwdClass_ImportedAbstractTypes( tom.gom.adt.objects.types.GomClass  t) { return  t.getImportedAbstractTypes() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_FwdClass_SortClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getSortClasses() ;}private static  tom.gom.adt.objects.types.GomClassList  tom_get_slot_FwdClass_OperatorClasses( tom.gom.adt.objects.types.GomClass  t) { return  t.getOperatorClasses() ;} 

  public BasicStrategyTemplate(GomClass basic) {
    super(basic);
    if (tom_is_sort_GomClass(basic)) {{  tom.gom.adt.objects.types.GomClass  tomMatch340NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClass )basic);if (tom_is_fun_sym_VisitableFwdClass(tomMatch340NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.ClassName  tomMatch340NameNumber_freshVar_0=tom_get_slot_VisitableFwdClass_ClassName(tomMatch340NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.GomClass  tomMatch340NameNumber_freshVar_1=tom_get_slot_VisitableFwdClass_Fwd(tomMatch340NameNumberfreshSubject_1);if (tom_is_fun_sym_FwdClass(tomMatch340NameNumber_freshVar_1)) {{  tom.gom.adt.objects.types.ClassName  tomMatch340NameNumber_freshVar_2=tom_get_slot_FwdClass_ClassName(tomMatch340NameNumber_freshVar_1);if ( true ) {


        this.fwd = tomMatch340NameNumber_freshVar_2;
        return;
      }}}}}}}}

    throw new GomRuntimeException(
        "Wrong argument for BasicStrategyTemplate: " + basic);
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
    writer.write("\npackage "/* Generated by TOM (version 2.5): Do not edit this file */+getPackage()+";\nimport tom.library.sl.*;\n   \n  public class "/* Generated by TOM (version 2.5): Do not edit this file */+className()+" extends "/* Generated by TOM (version 2.5): Do not edit this file */+className(fwd)+" implements tom.library.sl.Strategy {\n  private tom.library.sl.Environment environment;\n      \n  public int getChildCount() {\n    return 1;\n  }\n    \n  public Visitable getChildAt(int i) {\n    switch (i) {\n      case 0: return (Visitable) any;\n      default: throw new IndexOutOfBoundsException();\n    }\n  }\n    \n  public Visitable setChildAt(int i, Visitable child) {\n    switch (i) {\n      case 0: any = (Strategy) child; return this;\n      default: throw new IndexOutOfBoundsException();\n    }\n  }\n\n  public Visitable[] getChildren() {\n    return new Visitable[]{(Visitable)any};\n  }\n\n  public Visitable setChildren(Visitable[] children) {\n    any = (Strategy)children[0];\n    return this;\n  }\n\n  public Visitable visit(Environment envt) throws VisitFailure {\n    setEnvironment(envt);\n    int status = visit();\n    if(status == Environment.SUCCESS) {\n      return environment.getRoot();\n    } else {\n      throw new VisitFailure();\n    }\n  }\n\n\n  public tom.library.sl.Visitable visit(tom.library.sl.Visitable any) throws VisitFailure {\n    tom.library.sl.AbstractStrategy.init(this,new tom.library.sl.Environment());\n    environment.setRoot(any);\n    int status = visit();\n    if(status == tom.library.sl.Environment.SUCCESS) {\n      return environment.getRoot();\n    } else {\n      throw new VisitFailure();\n    }\n  }\n\n  public int visit() {\n    try {\n      environment.setSubject((tom.library.sl.Visitable)this.visitLight(environment.getSubject()));\n      return tom.library.sl.Environment.SUCCESS;\n    } catch(VisitFailure f) {\n      return tom.library.sl.Environment.FAILURE;\n    }\n  }\n\n  public tom.library.sl.Strategy accept(tom.library.sl.reflective.StrategyFwd v) throws tom.library.sl.VisitFailure {\n    return v.visit_Strategy(this);\n  } \n\n  public tom.library.sl.Environment getEnvironment() {\n    if(environment!=null) {\n      return environment;\n    } else {\n      throw new java.lang.RuntimeException(\"environment not initialized\");\n    }\n  }\n\n  public void setEnvironment(tom.library.sl.Environment env) {\n    this.environment = env;\n  }\n\n   \n    \n  public "/* Generated by TOM (version 2.5): Do not edit this file */+className()+"(Strategy any) {\n    super(any);\n  }\n}\n"






















































































);
  }
}
