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
  private String grammarPkg = "";
  private String grammarName = "";

  AdapterGenerator(File tomHomePath, List importList, String grammar) {
    this.tomHomePath = tomHomePath;
    this.importList = importList;
    int lastDot = grammar.lastIndexOf('.');
    if (-1 != lastDot) {
      // the grammar is in a package different from the gom file
      this.grammarPkg = grammar.substring(0,lastDot);
      this.grammarName = grammar.substring(lastDot+1,grammar.length());
    } else {
      String packagePrefix =
        environment()
        .getStreamManager()
        .getPackagePath().replace(File.separatorChar,'.');
      this.grammarName = grammar;
      this.grammarPkg = packagePrefix;
    }
  }

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file */    private static   tom.gom.adt.code.types.Code  tom_append_list_CodeList( tom.gom.adt.code.types.Code  l1,  tom.gom.adt.code.types.Code  l2) {     if( l1.isEmptyCodeList() ) {       return l2;     } else if( l2.isEmptyCodeList() ) {       return l1;     } else if( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) ) {       if( (( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ).isEmptyCodeList() ) {         return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getHeadCodeList() :l1),l2) ;       } else {         return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getHeadCodeList() :l1),tom_append_list_CodeList((( ((l1 instanceof tom.gom.adt.code.types.code.ConsCodeList) || (l1 instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? l1.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ),l2)) ;       }     } else {       return  tom.gom.adt.code.types.code.ConsCodeList.make(l1,l2) ;     }   }   private static   tom.gom.adt.code.types.Code  tom_get_slice_CodeList( tom.gom.adt.code.types.Code  begin,  tom.gom.adt.code.types.Code  end, tom.gom.adt.code.types.Code  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.gom.adt.code.types.code.ConsCodeList.make((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getHeadCodeList() :begin),( tom.gom.adt.code.types.Code )tom_get_slice_CodeList((( ((begin instanceof tom.gom.adt.code.types.code.ConsCodeList) || (begin instanceof tom.gom.adt.code.types.code.EmptyCodeList)) )? begin.getTailCodeList() : tom.gom.adt.code.types.code.EmptyCodeList.make() ),end,tail)) ;     }   }      private static   tom.gom.adt.gom.types.SlotList  tom_append_list_concSlot( tom.gom.adt.gom.types.SlotList l1,  tom.gom.adt.gom.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.gom.adt.gom.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.gom.adt.gom.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.SlotList  tom_get_slice_concSlot( tom.gom.adt.gom.types.SlotList  begin,  tom.gom.adt.gom.types.SlotList  end, tom.gom.adt.gom.types.SlotList  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.gom.adt.gom.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.gom.adt.gom.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */   private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )) == null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else {       return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file */ /* Generated by TOM (version 2.6alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ) ); }   /* Generated by TOM (version 2.6alpha): Do not edit this file */ 







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
    "\npackage "/* Generated by TOM (version 2.6alpha): Do not edit this file */+adapterPkg()+";\n\nimport org.antlr.runtime.Token;\nimport org.antlr.runtime.tree.CommonTreeAdaptor;\n\npublic class "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Adaptor extends CommonTreeAdaptor {\n\n\tpublic Object create(Token payload) {\n\t\treturn new "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree(payload);\n\t}\n\n}\n"












);
  }

  public void generateTreeFile(ModuleList moduleList, Writer writer)
    throws java.io.IOException {
    Collection operatorset = new HashSet();
    Collection slotset = new HashSet();
    try {
      tom_make_TopDown(( (( (( null )==null)?tom_make_CollectSlots(slotset):new tom.library.sl.Sequence(tom_make_CollectSlots(slotset),( null )) )==null)?tom_make_CollectOperators(operatorset):new tom.library.sl.Sequence(tom_make_CollectOperators(operatorset),( (( null )==null)?tom_make_CollectSlots(slotset):new tom.library.sl.Sequence(tom_make_CollectSlots(slotset),( null )) )) )).visitLight(moduleList);
    } catch (VisitFailure f) {
      throw new GomRuntimeException("CollectOperators should not fail");
    }
    writer.write("\npackage "/* Generated by TOM (version 2.6alpha): Do not edit this file */+adapterPkg()+";\n\nimport org.antlr.runtime.Token;\nimport org.antlr.runtime.tree.*;\n"




);
    if (!"".equals(grammarPkg)) {
    writer.write("\nimport "/* Generated by TOM (version 2.6alpha): Do not edit this file */+grammarPkg+"."/* Generated by TOM (version 2.6alpha): Do not edit this file */+grammarName+"Parser;\n"

);
    }
    writer.write("\n\npublic class "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree extends CommonTree {\n\n  private int termIndex = 0;\n  /* Use SharedObject as type, as most general type */\n  private shared.SharedObject inAstTerm = null;\n\n  public "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree(CommonTree node) {\n    super(node);\n    this.token = node.token;\n    initAstTerm(node.token);\n  }\n\n  public "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree(Token t) {\n    this.token = t;\n    initAstTerm(t);\n  }\n\n  private void initAstTerm(Token t) {\n    if(null==t) {\n      return;\n    }\n    switch (t.getType()) {\n"























);
    Iterator it = operatorset.iterator();
    while(it.hasNext()) {
      OperatorDecl opDecl = (OperatorDecl) it.next();
      {if ( opDecl instanceof tom.gom.adt.gom.types.OperatorDecl ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch60NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )opDecl);if ( (tomMatch60NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch60NameNumber_freshVar_0= tomMatch60NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.TypedProduction  tomMatch60NameNumber_freshVar_1= tomMatch60NameNumberfreshSubject_1.getProd() ;if ( (tomMatch60NameNumber_freshVar_1 instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {if ( true ) {

          Code code =
             tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      case "+grammarName+"Parser.") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(tomMatch60NameNumber_freshVar_0) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      {\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        inAstTerm = ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Empty.make(tomMatch60NameNumberfreshSubject_1) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(".make();\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        break;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        }\n") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) ) 








;
          CodeGen.generateCode(code,writer);
        }}}}}}}if ( opDecl instanceof tom.gom.adt.gom.types.OperatorDecl ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch60NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )opDecl);if ( (tomMatch60NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch60NameNumber_freshVar_2= tomMatch60NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.TypedProduction  tomMatch60NameNumber_freshVar_3= tomMatch60NameNumberfreshSubject_1.getProd() ;if ( (tomMatch60NameNumber_freshVar_3 instanceof tom.gom.adt.gom.types.typedproduction.Slots) ) {{  tom.gom.adt.gom.types.SlotList  tomMatch60NameNumber_freshVar_4= tomMatch60NameNumber_freshVar_3.getSlots() ;if ( ((tomMatch60NameNumber_freshVar_4 instanceof tom.gom.adt.gom.types.slotlist.ConsconcSlot) || (tomMatch60NameNumber_freshVar_4 instanceof tom.gom.adt.gom.types.slotlist.EmptyconcSlot)) ) {{  tom.gom.adt.gom.types.SlotList  tomMatch60NameNumber_freshVar_5=tomMatch60NameNumber_freshVar_4;if ( tomMatch60NameNumber_freshVar_5.isEmptyconcSlot() ) {if ( true ) {

          /* Initialise constants */
          Code code =
             tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      case "+grammarName+"Parser.") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(tomMatch60NameNumber_freshVar_2) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      {\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        inAstTerm = ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tomMatch60NameNumberfreshSubject_1) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(".make();\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        break;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        }\n") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) ) 








;
          CodeGen.generateCode(code,writer);
        }}}}}}}}}}}}

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

    writer.write("\n\n  public shared.SharedObject getTerm() {\n    return inAstTerm;\n  }\n\n  public void addChild(Tree t) {\n    super.addChild(t);\n    if (null==t) {\n      return;\n    }\n    "/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree tree = ("/* Generated by TOM (version 2.6alpha): Do not edit this file */+filename()+"Tree) t;\n    if(this.token == null || tree.token == null) {\n      return;\n    }\n\n    /* Depending on the token number and the child count, fill the correct field */\n    switch (this.token.getType()) {\n"

















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

  private static class CollectOperatorNames extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectOperatorNames( java.util.Collection  bag) { super(( new tom.library.sl.Identity() ));this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg) throws tom.library.sl.VisitFailure {{if ( tom__arg instanceof tom.gom.adt.gom.types.OperatorDecl ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch61NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom__arg);if ( (tomMatch61NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch61NameNumber_freshVar_0= tomMatch61NameNumberfreshSubject_1.getName() ;if ( true ) {


        bag.add(tomMatch61NameNumber_freshVar_0);
      }}}}}}return super.visit_OperatorDecl(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectOperatorNames( java.util.Collection  t0) { return new CollectOperatorNames(t0); }private static class CollectOperators extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectOperators( java.util.Collection  bag) { super(( new tom.library.sl.Identity() ));this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg) throws tom.library.sl.VisitFailure {{if ( tom__arg instanceof tom.gom.adt.gom.types.OperatorDecl ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch62NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )tom__arg);if ( (tomMatch62NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {if ( true ) {






        bag.add(tomMatch62NameNumberfreshSubject_1);
      }}}}}return super.visit_OperatorDecl(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectOperators( java.util.Collection  t0) { return new CollectOperators(t0); }private static class CollectSlots extends  tom.gom.adt.gom.GomBasicStrategy  {private  java.util.Collection  bag; public CollectSlots( java.util.Collection  bag) { super(( new tom.library.sl.Identity() ));this.bag=bag;}public  java.util.Collection  getbag() { return bag;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.Slot  visit_Slot( tom.gom.adt.gom.types.Slot  tom__arg) throws tom.library.sl.VisitFailure {{if ( tom__arg instanceof tom.gom.adt.gom.types.Slot ) {{  tom.gom.adt.gom.types.Slot  tomMatch63NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.Slot )tom__arg);if ( (tomMatch63NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.slot.Slot) ) {if ( true ) {






        bag.add(tomMatch63NameNumberfreshSubject_1);
      }}}}}return super.visit_Slot(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_CollectSlots( java.util.Collection  t0) { return new CollectSlots(t0); }private static class GenerateSlots extends  tom.gom.adt.gom.GomBasicStrategy  {private  Writer  writer; public GenerateSlots( Writer  writer) { super(( new tom.library.sl.Identity() ));this.writer=writer;}public  Writer  getwriter() { return writer;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.gom.adt.gom.types.Slot  visit_Slot( tom.gom.adt.gom.types.Slot  tom__arg) throws tom.library.sl.VisitFailure {{if ( tom__arg instanceof tom.gom.adt.gom.types.Slot ) {{  tom.gom.adt.gom.types.Slot  tomMatch64NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.Slot )tom__arg);if ( (tomMatch64NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.slot.Slot) ) {{  String  tomMatch64NameNumber_freshVar_0= tomMatch64NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.SortDecl  tomMatch64NameNumber_freshVar_1= tomMatch64NameNumberfreshSubject_1.getSort() ;if ( true ) {






        Code code =
           tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("  ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(tomMatch64NameNumber_freshVar_1) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" field") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(tomMatch64NameNumber_freshVar_0) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(";\n") 
           , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) 





;
        try {
          CodeGen.generateCode(code,writer);
        } catch (IOException e) {
          throw new VisitFailure("IOException " + e);
        }
      }}}}}}}return super.visit_Slot(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_GenerateSlots( Writer  t0) { return new GenerateSlots(t0); }



  protected void generateAddChildCase(OperatorDecl opDecl, Writer writer) throws IOException {
    {if ( opDecl instanceof tom.gom.adt.gom.types.OperatorDecl ) {{  tom.gom.adt.gom.types.OperatorDecl  tomMatch65NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.OperatorDecl )opDecl);if ( (tomMatch65NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {{  String  tomMatch65NameNumber_freshVar_0= tomMatch65NameNumberfreshSubject_1.getName() ;{  tom.gom.adt.gom.types.SortDecl  tomMatch65NameNumber_freshVar_1= tomMatch65NameNumberfreshSubject_1.getSort() ;{  tom.gom.adt.gom.types.TypedProduction  tomMatch65NameNumber_freshVar_2= tomMatch65NameNumberfreshSubject_1.getProd() ;{  tom.gom.adt.gom.types.TypedProduction  tom_prod=tomMatch65NameNumber_freshVar_2;{  tom.gom.adt.gom.types.OperatorDecl  tom_op=tomMatch65NameNumberfreshSubject_1;if ( true ) {

        Code code =
           tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      case "+grammarName+"Parser.") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(tomMatch65NameNumber_freshVar_0) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("      {\n") 
              , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) 




;
        {if ( tom_prod instanceof tom.gom.adt.gom.types.TypedProduction ) {{  tom.gom.adt.gom.types.TypedProduction  tomMatch66NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.TypedProduction )tom_prod);if ( (tomMatch66NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.typedproduction.Slots) ) {{  tom.gom.adt.gom.types.SlotList  tomMatch66NameNumber_freshVar_0= tomMatch66NameNumberfreshSubject_1.getSlots() ;{  tom.gom.adt.gom.types.SlotList  tom_slotList=tomMatch66NameNumber_freshVar_0;if ( true ) {

            code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(tomMatch65NameNumber_freshVar_1) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" term = (") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(") inAstTerm;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("switch(termIndex) {\n") 
                , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) 







;
            int idx = 0;
            SlotList sList = tom_slotList;
            while(sList.isConsconcSlot()) {
              Slot slot = sList.getHeadconcSlot();
              sList = sList.getTailconcSlot();
              Code cast = genGetSubterm(slot.getSort());
              code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("          ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("case "+idx+":\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            field") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(slot.getName() + " = ") ,tom_append_list_CodeList(cast, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(";\n") 
                  , tom.gom.adt.code.types.code.EmptyCodeList.make() ) )) ) ) ) ) 






;
              if(idx == tom_slotList.length() - 1) {
                code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            inAstTerm = ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(".make(") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(genArgsList(tom_slotList)) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(");\n") 
                    , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) 





;
              }
              code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("            break;\n") 
                  , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 

;
              idx++;
            }
            code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("}\n") 
                , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) 


;
          }}}}}}if ( tom_prod instanceof tom.gom.adt.gom.types.TypedProduction ) {{  tom.gom.adt.gom.types.TypedProduction  tomMatch66NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.TypedProduction )tom_prod);if ( (tomMatch66NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {{  tom.gom.adt.gom.types.SortDecl  tomMatch66NameNumber_freshVar_1= tomMatch66NameNumberfreshSubject_1.getSort() ;{  tom.gom.adt.gom.types.SortDecl  tom_domainSort=tomMatch66NameNumber_freshVar_1;if ( true ) {

            Code cast = genGetSubterm(tom_domainSort);
            code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(tom_domainSort) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" elem = ") ,tom_append_list_CodeList(cast, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(";\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(" list = (") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullOperatorClass.make(tom_op) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(") inAstTerm;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        ") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("inAstTerm = list.append(elem);\n") 
                , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) ) ) ) ) )) ) ) ) 












;
          }}}}}}}

        code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        break;\n") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("        }\n") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) 

;
        CodeGen.generateCode(code,writer);
      }}}}}}}}}}

  }

  protected Code genGetSubterm(SortDecl sort) {
    Code code =  tom.gom.adt.code.types.code.EmptyCodeList.make() ;;
    {if ( sort instanceof tom.gom.adt.gom.types.SortDecl ) {{  tom.gom.adt.gom.types.SortDecl  tomMatch67NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.SortDecl )sort);if ( (tomMatch67NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {if ( true ) {

        code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("(") , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.FullSortClass.make(sort) , tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make(") tree.getTerm()") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) ) ) 


;
      }}}}if ( sort instanceof tom.gom.adt.gom.types.SortDecl ) {{  tom.gom.adt.gom.types.SortDecl  tomMatch67NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.SortDecl )sort);if ( (tomMatch67NameNumberfreshSubject_1 instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl) ) {{  String  tomMatch67NameNumber_freshVar_0= tomMatch67NameNumberfreshSubject_1.getName() ;{  String  tom_name=tomMatch67NameNumber_freshVar_0;if ( true ) {

        if("int".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("Integer.parseInt(t.getText())") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else if ("String".equals(tom_name)) {
          code =  tom.gom.adt.code.types.code.ConsCodeList.make(code, tom.gom.adt.code.types.code.ConsCodeList.make( tom.gom.adt.code.types.code.Code.make("t.getText()") , tom.gom.adt.code.types.code.EmptyCodeList.make() ) ) 
;
        } else {
          throw new RuntimeException("Unsupported builtin");
        }
      }}}}}}}

    return code;
  }

  protected String genArgsList(SlotList slots) {
    String res = "";
    SlotList sList = slots;
    while(sList.isConsconcSlot()) {
      Slot slot = sList.getHeadconcSlot();
      sList = sList.getTailconcSlot();
      res += "field" + slot.getName();
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
