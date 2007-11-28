/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
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

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file */  

  /*
   * The argument is an operator class, and this template generates the
   * assotiated _Op strategy
   */
  public IsOpTemplate(GomClass gomClass) {
    super(gomClass);
    ClassName clsName = this.className;
    {if ( (clsName instanceof tom.gom.adt.objects.types.ClassName) ) {{  tom.gom.adt.objects.types.ClassName  tomMatch400NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.ClassName )clsName);if ( (tomMatch400NameNumberfreshSubject_1 instanceof tom.gom.adt.objects.types.classname.ClassName) ) {{  String  tomMatch400NameNumber_freshVar_0= tomMatch400NameNumberfreshSubject_1.getPkg() ;{  String  tomMatch400NameNumber_freshVar_1= tomMatch400NameNumberfreshSubject_1.getName() ;if ( true ) {

        String newpkg = tomMatch400NameNumber_freshVar_0.replaceFirst(".types.",".strategy.");
        String newname = "Is_"+tomMatch400NameNumber_freshVar_1;
        this.className =  tom.gom.adt.objects.types.classname.ClassName.make(newpkg, newname) ;
      }}}}}}}{if ( (gomClass instanceof tom.gom.adt.objects.types.GomClass) ) {{  tom.gom.adt.objects.types.GomClass  tomMatch401NameNumberfreshSubject_1=(( tom.gom.adt.objects.types.GomClass )gomClass);if ( (tomMatch401NameNumberfreshSubject_1 instanceof tom.gom.adt.objects.types.gomclass.OperatorClass) ) {{  tom.gom.adt.objects.types.ClassName  tomMatch401NameNumber_freshVar_0= tomMatch401NameNumberfreshSubject_1.getClassName() ;{  tom.gom.adt.objects.types.SlotFieldList  tomMatch401NameNumber_freshVar_1= tomMatch401NameNumberfreshSubject_1.getSlots() ;if ( true ) {



        this.operator = tomMatch401NameNumber_freshVar_0;
        this.slotList = tomMatch401NameNumber_freshVar_1;
        return;
      }}}}}}}

    throw new GomRuntimeException(
        "Wrong argument for IsOpTemplate: " + gomClass);
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
writer.write("\npackage "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getPackage()+";\n\npublic class "/* Generated by TOM (version 2.6alpha): Do not edit this file */+className()+" extends tom.library.sl.AbstractStrategy {\n  private static final String msg = \"Not an "/* Generated by TOM (version 2.6alpha): Do not edit this file */+className(operator)+"\";\n\n  public "/* Generated by TOM (version 2.6alpha): Do not edit this file */+className()+"() {\n    initSubterm();\n  }\n\n  public tom.library.sl.Visitable visit(tom.library.sl.Environment envt) throws tom.library.sl.VisitFailure {\n    return (tom.library.sl.Visitable) visit(envt,tom.library.sl.VisitableIntrospector.getInstance());\n  }\n\n  public tom.library.sl.Visitable visit(tom.library.sl.Visitable any) throws tom.library.sl.VisitFailure{\n    return (tom.library.sl.Visitable) visit(any,tom.library.sl.VisitableIntrospector.getInstance());\n  }\n\n  public tom.library.sl.Visitable visitLight(tom.library.sl.Visitable any) throws tom.library.sl.VisitFailure {\n    return (tom.library.sl.Visitable) visitLight(any,tom.library.sl.VisitableIntrospector.getInstance());\n  }\n\n\n  public Object visitLight(Object any, tom.library.sl.Introspector i) throws tom.library.sl.VisitFailure {\n    if(any instanceof "/* Generated by TOM (version 2.6alpha): Do not edit this file */+fullClassName(operator)+") {\n     return any;\n    } else {\n      throw new tom.library.sl.VisitFailure(msg);\n    }\n  }\n\n  public int visit(tom.library.sl.Introspector i) {\n    Object any = environment.getSubject();\n    if(any instanceof "/* Generated by TOM (version 2.6alpha): Do not edit this file */+fullClassName(operator)+") {\n     return tom.library.sl.Environment.SUCCESS;\n    } else {\n      return tom.library.sl.Environment.FAILURE;\n    }\n  }\n}\n"







































);
}


public String generateMapping() {

  String whenName = className().replaceFirst("Is_","When_");
  return "\n  %op Strategy "/* Generated by TOM (version 2.6alpha): Do not edit this file */+whenName+"(s:Strategy) {\n    make(s) { `Sequence("/* Generated by TOM (version 2.6alpha): Do not edit this file */+className()+"(),s) }\n  }\n\n  %op Strategy "/* Generated by TOM (version 2.6alpha): Do not edit this file */+className()+"() {\n    make() { new "/* Generated by TOM (version 2.6alpha): Do not edit this file */+fullClassName()+"()}\n  }\n  "







;
}

/** the class logger instance*/
private Logger getLogger() {
  return Logger.getLogger(getClass().getName());
}
}
