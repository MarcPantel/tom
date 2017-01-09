/*
 * Gom
 *
 * Copyright (c) 2006-2017, Universite de Lorraine, Inria
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

import tom.gom.GomStreamManager;
import tom.gom.tools.GomEnvironment;
import tom.gom.backend.MappingTemplateClass;
import java.io.*;
import tom.gom.adt.objects.types.*;
import tom.gom.tools.error.GomRuntimeException;

public class StratMappingTemplate extends MappingTemplateClass {
  GomClassList operatorClasses;
  int generateStratMapping = 0;

         private static   tom.gom.adt.objects.types.GomClassList  tom_append_list_ConcGomClass( tom.gom.adt.objects.types.GomClassList l1,  tom.gom.adt.objects.types.GomClassList  l2) {     if( l1.isEmptyConcGomClass() ) {       return l2;     } else if( l2.isEmptyConcGomClass() ) {       return l1;     } else if(  l1.getTailConcGomClass() .isEmptyConcGomClass() ) {       return  tom.gom.adt.objects.types.gomclasslist.ConsConcGomClass.make( l1.getHeadConcGomClass() ,l2) ;     } else {       return  tom.gom.adt.objects.types.gomclasslist.ConsConcGomClass.make( l1.getHeadConcGomClass() ,tom_append_list_ConcGomClass( l1.getTailConcGomClass() ,l2)) ;     }   }   private static   tom.gom.adt.objects.types.GomClassList  tom_get_slice_ConcGomClass( tom.gom.adt.objects.types.GomClassList  begin,  tom.gom.adt.objects.types.GomClassList  end, tom.gom.adt.objects.types.GomClassList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcGomClass()  ||  (end== tom.gom.adt.objects.types.gomclasslist.EmptyConcGomClass.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.objects.types.gomclasslist.ConsConcGomClass.make( begin.getHeadConcGomClass() ,( tom.gom.adt.objects.types.GomClassList )tom_get_slice_ConcGomClass( begin.getTailConcGomClass() ,end,tail)) ;   }    

  public StratMappingTemplate(GomClass gomClass, GomEnvironment gomEnvironment, int generateStratMapping) {
    super(gomClass,gomEnvironment);
    this.generateStratMapping = generateStratMapping;
    { /* unamed block */{ /* unamed block */if ( (gomClass instanceof tom.gom.adt.objects.types.GomClass) ) {if ( ((( tom.gom.adt.objects.types.GomClass )gomClass) instanceof tom.gom.adt.objects.types.gomclass.TomMapping) ) {

        this.operatorClasses =  (( tom.gom.adt.objects.types.GomClass )gomClass).getOperatorClasses() ;
        return;
      }}}}

    throw new GomRuntimeException(
        "Wrong argument for MappingTemplate: " + gomClass);
  }

  public void generateTomMapping(java.io.Writer writer) throws java.io.IOException {
    generate(writer);
  }

  /**
    * generate mappings for congruence strategies
    * in a _file.tom
    */
  public void generate(java.io.Writer writer) throws java.io.IOException {
    if(generateStratMapping == 1) {
      writer.write("  %include { sl.tom }");
    }
    { /* unamed block */{ /* unamed block */if ( (operatorClasses instanceof tom.gom.adt.objects.types.GomClassList) ) {if ( (((( tom.gom.adt.objects.types.GomClassList )operatorClasses) instanceof tom.gom.adt.objects.types.gomclasslist.ConsConcGomClass) || ((( tom.gom.adt.objects.types.GomClassList )operatorClasses) instanceof tom.gom.adt.objects.types.gomclasslist.EmptyConcGomClass)) ) { tom.gom.adt.objects.types.GomClassList  tomMatch631_end_4=(( tom.gom.adt.objects.types.GomClassList )operatorClasses);do {{ /* unamed block */if (!( tomMatch631_end_4.isEmptyConcGomClass() )) {if ( ((( tom.gom.adt.objects.types.GomClass ) tomMatch631_end_4.getHeadConcGomClass() ) instanceof tom.gom.adt.objects.types.gomclass.OperatorClass) ) { tom.gom.adt.objects.types.GomClass  tom___op= tomMatch631_end_4.getHeadConcGomClass() ;

        //TODO: change the generation for Empty and Cons constructors
        writer.write(
            (new tom.gom.backend.strategy.SOpTemplate(tom___op,getGomEnvironment())).generateMapping());
        writer.write(
            (new tom.gom.backend.strategy.IsOpTemplate(tom___op,getGomEnvironment())).generateMapping());
        writer.write(
            (new tom.gom.backend.strategy.MakeOpTemplate(tom___op,getGomEnvironment())).generateMapping());
      }}if ( tomMatch631_end_4.isEmptyConcGomClass() ) {tomMatch631_end_4=(( tom.gom.adt.objects.types.GomClassList )operatorClasses);} else {tomMatch631_end_4= tomMatch631_end_4.getTailConcGomClass() ;}}} while(!( (tomMatch631_end_4==(( tom.gom.adt.objects.types.GomClassList )operatorClasses)) ));}}}{ /* unamed block */if ( (operatorClasses instanceof tom.gom.adt.objects.types.GomClassList) ) {if ( (((( tom.gom.adt.objects.types.GomClassList )operatorClasses) instanceof tom.gom.adt.objects.types.gomclasslist.ConsConcGomClass) || ((( tom.gom.adt.objects.types.GomClassList )operatorClasses) instanceof tom.gom.adt.objects.types.gomclasslist.EmptyConcGomClass)) ) { tom.gom.adt.objects.types.GomClassList  tomMatch631_end_13=(( tom.gom.adt.objects.types.GomClassList )operatorClasses);do {{ /* unamed block */if (!( tomMatch631_end_13.isEmptyConcGomClass() )) { tom.gom.adt.objects.types.GomClass  tomMatch631_19= tomMatch631_end_13.getHeadConcGomClass() ;if ( ((( tom.gom.adt.objects.types.GomClass )tomMatch631_19) instanceof tom.gom.adt.objects.types.gomclass.VariadicOperatorClass) ) { tom.gom.adt.objects.types.GomClass  tomMatch631_17= tomMatch631_19.getEmpty() ; tom.gom.adt.objects.types.GomClass  tomMatch631_18= tomMatch631_19.getCons() ;if ( ((( tom.gom.adt.objects.types.GomClass )tomMatch631_17) instanceof tom.gom.adt.objects.types.gomclass.OperatorClass) ) {if ( ((( tom.gom.adt.objects.types.GomClass )tomMatch631_18) instanceof tom.gom.adt.objects.types.gomclass.OperatorClass) ) {





        writer.write("\n  %op Strategy _"+className( tomMatch631_19.getClassName() )+"(sub:Strategy) {\n    is_fsym(t) { false }\n    make(sub)  {\n    `Choice(When_"+className( tomMatch631_18.getClassName() )+"(All(sub)),When_"+className( tomMatch631_17.getClassName() )+"(Identity())) }\n  }\n  "





);
      }}}}if ( tomMatch631_end_13.isEmptyConcGomClass() ) {tomMatch631_end_13=(( tom.gom.adt.objects.types.GomClassList )operatorClasses);} else {tomMatch631_end_13= tomMatch631_end_13.getTailConcGomClass() ;}}} while(!( (tomMatch631_end_13==(( tom.gom.adt.objects.types.GomClassList )operatorClasses)) ));}}}}

      }

  protected String fileName() {
    return fullClassName().replace('.',File.separatorChar)+".tom";
  }

}
