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

package tom.gom.backend;

import java.io.Writer;
import java.io.StringWriter;
import java.util.logging.Logger;
import java.util.logging.Level;
import tom.gom.tools.error.GomRuntimeException;
import tom.gom.adt.code.types.*;
import tom.gom.adt.objects.types.*;

public class CodeGen {

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file */    private static   tom.gom.adt.code.types.Code  tom_append_list_CodeList( tom.gom.adt.code.types.Code  l1,  tom.gom.adt.code.types.Code  l2) {     if( l1.isEmptyCodeList() ) {       return l2;     } else if( l2.isEmptyCodeList() ) {       return l1;     } else if( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) ) {       if( (( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ).isEmptyCodeList() ) {         return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getHeadCodeList() :l1),l2) ;       } else {         return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getHeadCodeList() :l1),tom_append_list_CodeList((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ),l2)) ;       }     } else {       return  tom.gom.adt.code.types.code.ConsCodeList.make(l1,l2) ;     }   }   private static   tom.gom.adt.code.types.Code  tom_get_slice_CodeList( tom.gom.adt.code.types.Code  begin,  tom.gom.adt.code.types.Code  end, tom.gom.adt.code.types.Code  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getHeadCodeList() :begin),( tom.gom.adt.code.types.Code )tom_get_slice_CodeList((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ),end,tail)) ;     }   }    

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
    {if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Code) ) {{  String  tomMatch347NameNumber_freshVar_0= tomMatch347NameNumberfreshSubject_1.getprog() ;if ( true ) {

        writer.write(tomMatch347NameNumber_freshVar_0);
        return;
      }}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);{ boolean tomMatch347NameNumber_freshVar_2= false ;{  tom.gom.adt.gom.types.OperatorDecl  tomMatch347NameNumber_freshVar_1= null ;if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Empty) ) {{tomMatch347NameNumber_freshVar_2= true ;tomMatch347NameNumber_freshVar_1= tomMatch347NameNumberfreshSubject_1.getOperator() ;}} else {if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Cons) ) {{tomMatch347NameNumber_freshVar_2= true ;tomMatch347NameNumber_freshVar_1= tomMatch347NameNumberfreshSubject_1.getOperator() ;}}}if ((tomMatch347NameNumber_freshVar_2 ==  true )) {{  tom.gom.adt.gom.types.OperatorDecl  tom_opdecl=tomMatch347NameNumber_freshVar_1;if ( true ) {{if ( (tom_opdecl instanceof tom.gom.adt.gom.types.OperatorDecl) ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch348NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom_opdecl);if ( (tomMatch348NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch348NameNumber_freshVar_0= tomMatch348NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.SortDecl  tomMatch348NameNumber_freshVar_1= tomMatch348NameNumberfreshSubject_1.getSort() ;{  tom.gom.adt.gom.types.TypedProduction  tomMatch348NameNumber_freshVar_2= tomMatch348NameNumberfreshSubject_1.getProd() ;{  String  tom_opName=tomMatch348NameNumber_freshVar_0;if ( (tomMatch348NameNumber_freshVar_1 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {{  String  tomMatch348NameNumber_freshVar_3= tomMatch348NameNumber_freshVar_1.getName() ;{  tom.gom.adt.gom.types.ModuleDecl  tomMatch348NameNumber_freshVar_4= tomMatch348NameNumber_freshVar_1.getModuleDecl() ;if ( (tomMatch348NameNumber_freshVar_2 instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {if ( true ) {





            String tName = tom_opName;
            {if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch349NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch349NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Empty) ) {if ( true ) {

                tName = "Empty" + tom_opName;
              }}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch349NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch349NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Cons) ) {if ( true ) {

                tName = "Cons" + tom_opName;
              }}}}}

            String sortNamePackage = tomMatch348NameNumber_freshVar_3.toLowerCase();
            ClassName className =  tom.gom.adt.objects.types.classname.ClassName.make(tom.gom.compiler.Compiler.packagePrefix(tomMatch348NameNumber_freshVar_4)+".types."+sortNamePackage, tName) 

;
            writer.write(tom.gom.backend.TemplateClass.fullClassName(className));        
            return;
          }}}}}}}}}}}}}

        Logger.getLogger("CodeGen").log(
            Level.SEVERE,"{Empty,Cons}: expecting varidic, but got {0}",
            new Object[] { (tom_opdecl) });
        return;
      }}}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);{ boolean tomMatch347NameNumber_freshVar_5= false ;{  tom.gom.adt.gom.types.OperatorDecl  tomMatch347NameNumber_freshVar_4= null ;{  String  tomMatch347NameNumber_freshVar_3= "" ;if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.IsEmpty) ) {{tomMatch347NameNumber_freshVar_5= true ;tomMatch347NameNumber_freshVar_3= tomMatch347NameNumberfreshSubject_1.getVar() ;tomMatch347NameNumber_freshVar_4= tomMatch347NameNumberfreshSubject_1.getOperator() ;}} else {if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.IsCons) ) {{tomMatch347NameNumber_freshVar_5= true ;tomMatch347NameNumber_freshVar_3= tomMatch347NameNumberfreshSubject_1.getVar() ;tomMatch347NameNumber_freshVar_4= tomMatch347NameNumberfreshSubject_1.getOperator() ;}}}if ((tomMatch347NameNumber_freshVar_5 ==  true )) {{  tom.gom.adt.gom.types.OperatorDecl  tom_opdecl=tomMatch347NameNumber_freshVar_4;if ( true ) {{if ( (tom_opdecl instanceof tom.gom.adt.gom.types.OperatorDecl) ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch350NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom_opdecl);if ( (tomMatch350NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch350NameNumber_freshVar_0= tomMatch350NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.TypedProduction  tomMatch350NameNumber_freshVar_1= tomMatch350NameNumberfreshSubject_1.getProd() ;if ( (tomMatch350NameNumber_freshVar_1 instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {if ( true ) {



            writer.write(tomMatch347NameNumber_freshVar_3);
            {if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch351NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch351NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.IsEmpty) ) {if ( true ) {

                writer.write(".isEmpty");
              }}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch351NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch351NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.IsCons) ) {if ( true ) {

                writer.write(".isCons");
              }}}}}

            writer.write(tomMatch350NameNumber_freshVar_0);
            writer.write("()");
            return;
          }}}}}}}}

        Logger.getLogger("CodeGen").log(
            Level.SEVERE,"Is{Empty,Cons}: expecting varidic, but got {0}",
            new Object[] { (tom_opdecl) });
        return;
      }}}}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.FullOperatorClass) ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch347NameNumber_freshVar_6= tomMatch347NameNumberfreshSubject_1.getOperator() ;if ( (tomMatch347NameNumber_freshVar_6 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch347NameNumber_freshVar_7= tomMatch347NameNumber_freshVar_6.getName() ;{  tom.gom.adt.gom.types.SortDecl  tomMatch347NameNumber_freshVar_8= tomMatch347NameNumber_freshVar_6.getSort() ;if ( (tomMatch347NameNumber_freshVar_8 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {{  String  tomMatch347NameNumber_freshVar_9= tomMatch347NameNumber_freshVar_8.getName() ;{  tom.gom.adt.gom.types.ModuleDecl  tomMatch347NameNumber_freshVar_10= tomMatch347NameNumber_freshVar_8.getModuleDecl() ;if ( true ) {






        String sortNamePackage = tomMatch347NameNumber_freshVar_9.toLowerCase();
        ClassName className =  tom.gom.adt.objects.types.classname.ClassName.make(tom.gom.compiler.Compiler.packagePrefix(tomMatch347NameNumber_freshVar_10)+".types."+sortNamePackage, tomMatch347NameNumber_freshVar_7) 

;
        writer.write(tom.gom.backend.TemplateClass.fullClassName(className));        
        return;
      }}}}}}}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.FullSortClass) ) {{  tom.gom.adt.gom.types.SortDecl  tomMatch347NameNumber_freshVar_11= tomMatch347NameNumberfreshSubject_1.getSort() ;if ( (tomMatch347NameNumber_freshVar_11 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {{  String  tomMatch347NameNumber_freshVar_12= tomMatch347NameNumber_freshVar_11.getName() ;{  tom.gom.adt.gom.types.ModuleDecl  tomMatch347NameNumber_freshVar_13= tomMatch347NameNumber_freshVar_11.getModuleDecl() ;if ( true ) {

        ClassName sortClassName =  tom.gom.adt.objects.types.classname.ClassName.make(tom.gom.compiler.Compiler.packagePrefix(tomMatch347NameNumber_freshVar_13)+".types", tomMatch347NameNumber_freshVar_12) 
;
        writer.write(tom.gom.backend.TemplateClass.fullClassName(sortClassName));        
        return;
      }}}}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.FullSortClass) ) {{  tom.gom.adt.gom.types.SortDecl  tomMatch347NameNumber_freshVar_14= tomMatch347NameNumberfreshSubject_1.getSort() ;if ( (tomMatch347NameNumber_freshVar_14 instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl) ) {{  String  tomMatch347NameNumber_freshVar_15= tomMatch347NameNumber_freshVar_14.getName() ;if ( true ) {

        writer.write(tomMatch347NameNumber_freshVar_15);        
        return;
      }}}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.Compare) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumber_freshVar_16= tomMatch347NameNumberfreshSubject_1.getLCode() ;{  tom.gom.adt.code.types.Code  tomMatch347NameNumber_freshVar_17= tomMatch347NameNumberfreshSubject_1.getRCode() ;if ( true ) {

        generateCode(tomMatch347NameNumber_freshVar_16, writer);
        writer.write(".compareTo(");
        generateCode(tomMatch347NameNumber_freshVar_17, writer);
        writer.write(")");
        return;
      }}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( ((tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumber_freshVar_18=tomMatch347NameNumberfreshSubject_1;if ( (  tomMatch347NameNumber_freshVar_18.isEmptyCodeList()  ||  tomMatch347NameNumber_freshVar_18.equals( tom.gom.adt.code.types.code.EmptyCodeList.make() )  ) ) {if ( true ) {
 return ; }}}}}}if ( (code instanceof tom.gom.adt.code.types.Code) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumberfreshSubject_1=(( tom.gom.adt.code.types.Code )code);if ( ((tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (tomMatch347NameNumberfreshSubject_1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) ) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumber_freshVar_19=tomMatch347NameNumberfreshSubject_1;if (!( (  tomMatch347NameNumber_freshVar_19.isEmptyCodeList()  ||  tomMatch347NameNumber_freshVar_19.equals( tom.gom.adt.code.types.code.EmptyCodeList.make() )  ) )) {{  tom.gom.adt.code.types.Code  tomMatch347NameNumber_freshVar_20=(( ((tomMatch347NameNumber_freshVar_19 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (tomMatch347NameNumber_freshVar_19 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )?( tomMatch347NameNumber_freshVar_19.getTailCodeList() ):( tom.gom.adt.code.types.code.EmptyCodeList.make() ));if ( true ) {

        generateCode((( ((tomMatch347NameNumber_freshVar_19 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (tomMatch347NameNumber_freshVar_19 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )?( tomMatch347NameNumber_freshVar_19.getHeadCodeList() ):(tomMatch347NameNumber_freshVar_19)),writer);
        generateCode(tomMatch347NameNumber_freshVar_20,writer);
        return;
      }}}}}}}}

    throw new GomRuntimeException("Can't generate code for " + code);
  }
}
