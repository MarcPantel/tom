/*
 * Gom
 *
 * Copyright (c) 2006-2012, INPL, INRIA
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

import tom.gom.GomStreamManager;
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
  // Here, we use a GomEnvironment in spite of the fact that only a GomStreamManager is needed
  // But as other classes have a GomEnvironment, we keep the same model for the moment
  // It is better to stay with one single model whatever class is used
  private GomEnvironment gomEnvironment;
  private String grammarPkg = "";

  AdapterGenerator(File tomHomePath, GomEnvironment gomEnvironment) {
    this.tomHomePath = tomHomePath;
    this.gomEnvironment = gomEnvironment;
    this.grammarPkg = getStreamManager().getDefaultPackagePath();
  }

  private GomStreamManager getStreamManager() {
    return gomEnvironment.getStreamManager();
  }

         private static   tom.gom.adt.code.types.Code  tom_append_list_CodeList( tom.gom.adt.code.types.Code  l1,  tom.gom.adt.code.types.Code  l2) {     if( l1.isEmptyCodeList() ) {       return l2;     } else if( l2.isEmptyCodeList() ) {       return l1;     } else if( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) ) {       if(  l1.getTailCodeList() .isEmptyCodeList() ) {         return  tom.gom.adt.code.types.code.ConsCodeList.make( l1.getHeadCodeList() ,l2) ;       } else {         return  tom.gom.adt.code.types.code.ConsCodeList.make( l1.getHeadCodeList() ,tom_append_list_CodeList( l1.getTailCodeList() ,l2)) ;       }     } else {       return  tom.gom.adt.code.types.code.ConsCodeList.make(l1,l2) ;     }   }   private static   tom.gom.adt.code.types.Code  tom_get_slice_CodeList( tom.gom.adt.code.types.Code  begin,  tom.gom.adt.code.types.Code  end, tom.gom.adt.code.types.Code  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyCodeList()  ||  (end== tom.gom.adt.code.types.code.EmptyCodeList.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getHeadCodeList() :begin),( tom.gom.adt.code.types.Code )tom_get_slice_CodeList((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ),end,tail)) ;   }         private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Sequence )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;       } else {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Sequence.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Choice )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;       } else {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Choice.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.SequenceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.SequenceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.ChoiceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.ChoiceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;   }      private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ));} private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { return (  tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) )  );}private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}    






  public void generate(ModuleList moduleList, HookDeclList hookDecls) {
    final Map<OperatorDecl,Integer> operatormap = new HashMap<OperatorDecl,Integer>();
    IntRef intref = new IntRef(10);
    try {
      tom_make_TopDown(tom_make_CollectOperators(operatormap,intref)).visitLight(moduleList);
    } catch (VisitFailure f) {
      throw new GomRuntimeException("CollectOperators should not fail");
    }
    writeTokenFile(operatormap);
    writeAdapterFile(operatormap);
  }

  public int writeTokenFile(Map<OperatorDecl,Integer> operatormap) {
    try {
       File output = tokenFileToGenerate();
       // make sure the directory exists
       output.getParentFile().mkdirs();
       Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
       generateTokenFile(operatormap, writer);
       writer.flush();
       writer.close();
    } catch(Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

  public int writeAdapterFile(Map<OperatorDecl,Integer> operatormap) {
    try {
       File output = adaptorFileToGenerate();
       // make sure the directory exists
       output.getParentFile().mkdirs();
       Writer writer =
         new BufferedWriter(
             new OutputStreamWriter(
               new FileOutputStream(output)));
       generateAdapterFile(operatormap, writer);
       writer.flush();
       writer.close();
    } catch(Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

  private String adapterPkg() {
    String packagePrefix = getStreamManager().getDefaultPackagePath();
    return ((packagePrefix=="")?filename():packagePrefix+"."+filename()).toLowerCase();
  }

  public void generateAdapterFile(Map<OperatorDecl,Integer> operatormap, Writer writer)
    throws java.io.IOException {
    writer.write(
    "\npackage "+adapterPkg()+";\n\nimport org.antlr.runtime.Token;\nimport org.antlr.runtime.tree.Tree;\n"




);
    if (!"".equals(grammarPkg)) {
    writer.write("\n"
);
    }
    writer.write("\npublic class "+filename()+"Adaptor {\n  public static shared.SharedObject getTerm(Tree tree) {\n    shared.SharedObject res = null;\n    if (tree.isNil()) {\n      throw new RuntimeException(\"nil term\");\n    }\n    if (tree.getType()==Token.INVALID_TOKEN_TYPE) {\n      throw new RuntimeException(\"bad type\");\n    }\n\n    switch (tree.getType()) {\n"











);

    for (Map.Entry<OperatorDecl,Integer> entry : operatormap.entrySet()) {
      OperatorDecl opDecl = entry.getKey();
      String opkey = entry.getValue().toString();
      {{if ( (((Object)opDecl) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl)) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )(( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl))) instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) { tom.gom.adt.gom.types.TypedProduction  tomMatch492_1= (( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl)).getProd() ;if ( (tomMatch492_1 instanceof tom.gom.adt.gom.types.TypedProduction) ) {if ( ((( tom.gom.adt.gom.types.TypedProduction )tomMatch492_1) instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) { tom.gom.adt.gom.types.SortDecl  tom_domainSort= tomMatch492_1.getSort() ; tom.gom.adt.gom.types.OperatorDecl  tom_op=(( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl));


          Code cast = genGetTerm(tom_domainSort,"tree.getChild(i)");
          Code code =
             tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      case ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(opkey) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        {\n") , tom.gom.adt.code.types.code.ConsCodeList.make(/* createemptylist*/
                 tom.gom.adt.code.types.code.Code.make("          res = ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Empty.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(".make();\n") , tom.gom.adt.code.types.code.ConsCodeList.make(/* addelements*/
                 tom.gom.adt.code.types.code.Code.make("          for(int i = 0; i < tree.getChildCount(); i++) {\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(tom_domainSort) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" elem = ") ,tom_append_list_CodeList(cast, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(";\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" list = (") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(") res;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("res = list.append(elem);\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          }\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          break;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        }\n") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) ) ) ) )) ) ) ) ) ) ) ) ) ) ) 
























;
          CodeGen.generateCode(code,writer);
        }}}}}}{if ( (((Object)opDecl) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl)) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )(( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl))) instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) { tom.gom.adt.gom.types.TypedProduction  tomMatch492_8= (( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl)).getProd() ; tom.gom.adt.gom.types.TypedProduction  tom_prod=tomMatch492_8;boolean tomMatch492_14= false ;if ( (tomMatch492_8 instanceof tom.gom.adt.gom.types.TypedProduction) ) {if ( ((( tom.gom.adt.gom.types.TypedProduction )tomMatch492_8) instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {if ( (tom_prod==tomMatch492_8) ) {tomMatch492_14= true ;}}}if (!(tomMatch492_14)) {


        Code code =
           tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      case ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(opkey) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        {\n") 
              , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) 




;
        {{if ( (((Object)tom_prod) instanceof tom.gom.adt.gom.types.TypedProduction) ) {if ( ((( tom.gom.adt.gom.types.TypedProduction )((Object)tom_prod)) instanceof tom.gom.adt.gom.types.TypedProduction) ) {if ( ((( tom.gom.adt.gom.types.TypedProduction )(( tom.gom.adt.gom.types.TypedProduction )((Object)tom_prod))) instanceof tom.gom.adt.gom.types.typedproduction.Slots) ) { tom.gom.adt.gom.types.SlotList  tom_slotList= (( tom.gom.adt.gom.types.TypedProduction )((Object)tom_prod)).getSlots() ;

            int idx = 0;
            SlotList sList = tom_slotList;
            int length = sList.length();
            String sCode = "\n          if (tree.getChildCount()!="+length+") {\n            throw new RuntimeException(\"Node \" + tree + \": "+length+" child(s) expected, but \" + tree.getChildCount() + \" found\");\n          }\n"



;
            code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(sCode) , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ;

            while(sList.isConsConcSlot()) {
              Slot slot = sList.getHeadConcSlot();
              sList = sList.getTailConcSlot();
              Code cast = genGetTerm(slot.getSort(),"tree.getChild("+idx+")");
              code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(slot.getSort()) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" field" + idx+ " = ") ,tom_append_list_CodeList(cast, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(";\n") 
                  , tom.gom.adt.code.types.code.EmptyCodeList.make() ) )) ) ) ) 





;
              idx++;
            }
            code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          res = ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make((( tom.gom.adt.gom.types.OperatorDecl )((Object)opDecl))) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(".make(") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(genArgsList(tom_slotList)) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(");\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          break;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        }\n") 
                , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) 







;
              }}}}}


          CodeGen.generateCode(code,writer);
        }}}}}}


    }

    writer.write("\n    }\n    return res;\n  }\n}\n"




);
  }

  public void generateTokenFile(Map<OperatorDecl,Integer> operatormap, Writer writer)
    throws java.io.IOException {
    for (Map.Entry<OperatorDecl,Integer> entry : operatormap.entrySet()) {
      final OperatorDecl decl = entry.getKey();
      {{if ( (((Object)decl) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )((Object)decl)) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )(( tom.gom.adt.gom.types.OperatorDecl )((Object)decl))) instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {

          writer.write( (( tom.gom.adt.gom.types.OperatorDecl )((Object)decl)).getName() + "="+entry.getValue().intValue()+"\n");
        }}}}}

    }
  }

  static class IntRef {
    IntRef(int val) {
      intValue = val;
    }
    public int intValue;
  }
  public static class CollectOperators extends tom.library.sl.AbstractStrategyBasic {private  Map<OperatorDecl,Integer>  bag;private  IntRef  intref;public CollectOperators( Map<OperatorDecl,Integer>  bag,  IntRef  intref) {super(( new tom.library.sl.Identity() ));this.bag=bag;this.intref=intref;}public  Map<OperatorDecl,Integer>  getbag() {return bag;}public  IntRef  getintref() {return intref;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.gom.adt.gom.types.OperatorDecl) ) {return ((T)visit_OperatorDecl((( tom.gom.adt.gom.types.OperatorDecl )v),introspector));}if (!(  null ==environment )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.OperatorDecl  _visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.gom.adt.gom.types.OperatorDecl )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (((Object)tom__arg) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg)) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {if ( ((( tom.gom.adt.gom.types.OperatorDecl )(( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg))) instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {




        bag.put((( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg)),Integer.valueOf(intref.intValue));
        intref.intValue++;
      }}}}}return _visit_OperatorDecl(tom__arg,introspector);}}private static  tom.library.sl.Strategy  tom_make_CollectOperators( Map<OperatorDecl,Integer>  t0,  IntRef  t1) { return new CollectOperators(t0,t1);}



  protected Code genGetTerm(SortDecl sort, String tree) {
    Code code =  tom.gom.adt.code.types.code.EmptyCodeList.make() ;
    {{if ( (((Object)sort) instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )((Object)sort)) instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )(( tom.gom.adt.gom.types.SortDecl )((Object)sort))) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {

        code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("(") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(sort) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(")" + filename() + "Adaptor.getTerm(" + tree+ ")") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) 


;
      }}}}{if ( (((Object)sort) instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )((Object)sort)) instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )(( tom.gom.adt.gom.types.SortDecl )((Object)sort))) instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl) ) { String  tom_name= (( tom.gom.adt.gom.types.SortDecl )((Object)sort)).getName() ;

        if ("int".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("Integer.parseInt(" + tree+ ".getText())") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else if ("long".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("Long.parseLong(" + tree+ ".getText())") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else if ("String".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(tree+ ".getText()") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else if ("boolean".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("Boolean.valueOf(" + tree+ ".getText())") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else {
          throw new RuntimeException("Unsupported builtin "+tom_name);
        }
      }}}}}

    return code;
  }

  protected String genArgsList(SlotList slots) {
    String res = "";
    SlotList sList = slots;
    int idx = 0;
    while (sList.isConsConcSlot()) {
      Slot slot = sList.getHeadConcSlot();
      sList = sList.getTailConcSlot();
      res += "field" + idx;
      if (sList.isConsConcSlot()) {
        res += ", ";
      }
      idx++;
    }
    return res;
  }

  protected String fullFileName() {
    return (adapterPkg() + "." + filename()).replace('.',File.separatorChar);
  }

  protected String filename() {
    String filename = (new File(getStreamManager().getOutputFileName())).getName();
    int dotidx = filename.indexOf('.');
    if (-1 != dotidx) {
      filename = filename.substring(0,dotidx);
    }
    return filename;
  }

  protected File tokenFileToGenerate() {
    File output = new File(
        getStreamManager().getDestDir(),
        fullFileName()+"Tokens.tokens");
    return output;
  }

  protected File adaptorFileToGenerate() {
    File output = new File(
        getStreamManager().getDestDir(),
        fullFileName()+"Adaptor.java");
    return output;
  }
}
