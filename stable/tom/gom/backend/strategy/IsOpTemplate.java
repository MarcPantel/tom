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

package tom.gom.backend.strategy;

import java.io.*;
import java.util.logging.*;
import tom.gom.backend.TemplateClass;
import tom.gom.tools.GomEnvironment;
import tom.gom.tools.error.GomRuntimeException;
import tom.gom.adt.objects.types.*;

public class IsOpTemplate extends TemplateClass {
  ClassName operator;
  SlotFieldList slotList;

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_SlotFieldList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SlotFieldList(Object t) { return  t instanceof tom.gom.adt.objects.types.SlotFieldList ;}private static boolean tom_equal_term_GomClass(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomClass(Object t) { return  t instanceof tom.gom.adt.objects.types.GomClass ;}private static boolean tom_equal_term_ClassName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ClassName(Object t) { return  t instanceof tom.gom.adt.objects.types.ClassName ;}private static boolean tom_equal_term_HookList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_HookList(Object t) { return  t instanceof tom.gom.adt.objects.types.HookList ;}private static boolean tom_is_fun_sym_OperatorClass( tom.gom.adt.objects.types.GomClass  t) { return  t instanceof tom.gom.adt.objects.types.gomclass.OperatorClass ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_ClassName( tom.gom.adt.objects.types.GomClass  t) { return  t.getClassName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_AbstractType( tom.gom.adt.objects.types.GomClass  t) { return  t.getAbstractType() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_ExtendsType( tom.gom.adt.objects.types.GomClass  t) { return  t.getExtendsType() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_Mapping( tom.gom.adt.objects.types.GomClass  t) { return  t.getMapping() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_SortName( tom.gom.adt.objects.types.GomClass  t) { return  t.getSortName() ;}private static  tom.gom.adt.objects.types.ClassName  tom_get_slot_OperatorClass_Visitor( tom.gom.adt.objects.types.GomClass  t) { return  t.getVisitor() ;}private static  tom.gom.adt.objects.types.SlotFieldList  tom_get_slot_OperatorClass_Slots( tom.gom.adt.objects.types.GomClass  t) { return  t.getSlots() ;}private static  tom.gom.adt.objects.types.HookList  tom_get_slot_OperatorClass_Hooks( tom.gom.adt.objects.types.GomClass  t) { return  t.getHooks() ;}private static boolean tom_is_fun_sym_ClassName( tom.gom.adt.objects.types.ClassName  t) { return  t instanceof tom.gom.adt.objects.types.classname.ClassName ;}private static  tom.gom.adt.objects.types.ClassName  tom_make_ClassName( String  t0,  String  t1) { return  tom.gom.adt.objects.types.classname.ClassName.make(t0, t1) ; }private static  String  tom_get_slot_ClassName_Pkg( tom.gom.adt.objects.types.ClassName  t) { return  t.getPkg() ;}private static  String  tom_get_slot_ClassName_Name( tom.gom.adt.objects.types.ClassName  t) { return  t.getName() ;} 

  /*
   * The argument is an operator class, and this template generates the
   * assotiated _Op strategy
   */
  public IsOpTemplate(GomClass gomClass) {
    super(gomClass);
    ClassName clsName = this.className;
    if (tom_is_sort_ClassName(clsName)) {{  tom.gom.adt.objects.types.ClassName  tomMatch375NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.ClassName )clsName);if (tom_is_fun_sym_ClassName(tomMatch375NameNumberfreshSubject_1)) {{  String  tomMatch375NameNumber_freshVar_0=tom_get_slot_ClassName_Pkg(tomMatch375NameNumberfreshSubject_1);{  String  tomMatch375NameNumber_freshVar_1=tom_get_slot_ClassName_Name(tomMatch375NameNumberfreshSubject_1);if ( true ) {

        String newpkg = tomMatch375NameNumber_freshVar_0.replaceFirst(".types.",".strategy.");
        String newname = "Is_"+tomMatch375NameNumber_freshVar_1;
        this.className = tom_make_ClassName(newpkg,newname);
      }}}}}}if (tom_is_sort_GomClass(gomClass)) {{  tom.gom.adt.objects.types.GomClass  tomMatch376NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClass )gomClass);if (tom_is_fun_sym_OperatorClass(tomMatch376NameNumberfreshSubject_1)) {{  tom.gom.adt.objects.types.ClassName  tomMatch376NameNumber_freshVar_0=tom_get_slot_OperatorClass_ClassName(tomMatch376NameNumberfreshSubject_1);{  tom.gom.adt.objects.types.SlotFieldList  tomMatch376NameNumber_freshVar_1=tom_get_slot_OperatorClass_Slots(tomMatch376NameNumberfreshSubject_1);if ( true ) {



        this.operator = tomMatch376NameNumber_freshVar_0;
        this.slotList = tomMatch376NameNumber_freshVar_1;
        return;
      }}}}}}

    throw new GomRuntimeException(
        "Wrong argument for IsOpTemplate: " + gomClass);
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
writer.write("\npackage "/* Generated by TOM (version 2.5): Do not edit this file */+getPackage()+";\n\npublic class "/* Generated by TOM (version 2.5): Do not edit this file */+className()+" extends tom.library.sl.AbstractStrategy {\n  private static final String msg = \"Not an "/* Generated by TOM (version 2.5): Do not edit this file */+className(operator)+"\";\n\n  public "/* Generated by TOM (version 2.5): Do not edit this file */+className()+"() {\n    initSubterm();\n  }\n\n  public tom.library.sl.Visitable visitLight(tom.library.sl.Visitable any) throws tom.library.sl.VisitFailure {\n    if(any instanceof "/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(operator)+") {\n     return any;\n    } else {\n      throw new tom.library.sl.VisitFailure(msg);\n    }\n  }\n\n  public int visit() {\n    tom.library.sl.Visitable any = environment.getSubject();\n    if(any instanceof "/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName(operator)+") {\n     return tom.library.sl.Environment.SUCCESS;\n    } else {\n      return tom.library.sl.Environment.FAILURE;\n    }\n  }\n}\n"


























);
}


public String generateMapping() {

  String whenName = className().replaceFirst("Is_","When_");
  return "\n  %op Strategy "/* Generated by TOM (version 2.5): Do not edit this file */+whenName+"(s:Strategy) {\n    make(s) { `Sequence("/* Generated by TOM (version 2.5): Do not edit this file */+className()+"(),s) }\n  }\n\n  %op Strategy "/* Generated by TOM (version 2.5): Do not edit this file */+className()+"() {\n    make() { new "/* Generated by TOM (version 2.5): Do not edit this file */+fullClassName()+"()}\n  }\n  "







;
}

/** the class logger instance*/
private Logger getLogger() {
  return Logger.getLogger(getClass().getName());
}
}
