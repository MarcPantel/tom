/*
 * Gom
 *
 * Copyright (c) 2006-2009, INRIA
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

import java.io.*;
import java.util.*;

import tom.gom.backend.TemplateClass;
import tom.gom.backend.TemplateHookedClass;
import tom.gom.adt.objects.types.*;
import tom.gom.tools.error.GomRuntimeException;
import tom.gom.tools.GomEnvironment;
import tom.platform.OptionManager;

public class SortTemplate extends TemplateHookedClass {
  ClassName abstractType;
  ClassNameList operatorList;
  ClassNameList variadicOperatorList;
  SlotFieldList slotList;
  boolean maximalsharing;

         private static   tom.gom.adt.objects.types.ClassNameList  tom_append_list_ConcClassName( tom.gom.adt.objects.types.ClassNameList l1,  tom.gom.adt.objects.types.ClassNameList  l2) {     if( l1.isEmptyConcClassName() ) {       return l2;     } else if( l2.isEmptyConcClassName() ) {       return l1;     } else if(  l1.getTailConcClassName() .isEmptyConcClassName() ) {       return  tom.gom.adt.objects.types.classnamelist.ConsConcClassName.make( l1.getHeadConcClassName() ,l2) ;     } else {       return  tom.gom.adt.objects.types.classnamelist.ConsConcClassName.make( l1.getHeadConcClassName() ,tom_append_list_ConcClassName( l1.getTailConcClassName() ,l2)) ;     }   }   private static   tom.gom.adt.objects.types.ClassNameList  tom_get_slice_ConcClassName( tom.gom.adt.objects.types.ClassNameList  begin,  tom.gom.adt.objects.types.ClassNameList  end, tom.gom.adt.objects.types.ClassNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcClassName()  ||  (end== tom.gom.adt.objects.types.classnamelist.EmptyConcClassName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.objects.types.classnamelist.ConsConcClassName.make( begin.getHeadConcClassName() ,( tom.gom.adt.objects.types.ClassNameList )tom_get_slice_ConcClassName( begin.getTailConcClassName() ,end,tail)) ;   }    

  public SortTemplate(File tomHomePath,
                      OptionManager manager,
                      boolean maximalsharing,
                      List importList, 	
                      GomClass gomClass,
                      TemplateClass mapping,
                      GomEnvironment gomEnvironment) {
    super(gomClass,manager,tomHomePath,importList,mapping,gomEnvironment);
    this.maximalsharing = maximalsharing;
    {{if ( (gomClass instanceof tom.gom.adt.objects.types.GomClass) ) {if ( ((( tom.gom.adt.objects.types.GomClass )gomClass) instanceof tom.gom.adt.objects.types.gomclass.SortClass) ) {




        this.abstractType =  (( tom.gom.adt.objects.types.GomClass )gomClass).getAbstractType() ;
        this.operatorList =  (( tom.gom.adt.objects.types.GomClass )gomClass).getOperators() ;
        this.variadicOperatorList =  (( tom.gom.adt.objects.types.GomClass )gomClass).getVariadicOperators() ;
        this.slotList =  (( tom.gom.adt.objects.types.GomClass )gomClass).getSlotFields() ;
        return;
      }}}}

    throw new GomRuntimeException(
        "Bad argument for SortTemplate: " + gomClass);
  }

  public GomEnvironment getGomEnvironment() {
    return this.gomEnvironment;
  }

  protected String generateInterface() {
    String interfaces =  super.generateInterface();
    if (interfaces.equals("")) return "";
    else return "implements "+interfaces.substring(1)+"";
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
    writer.write("\npackage "+getPackage()+";\n"+generateImport()+"\n\npublic abstract class "+className()+" extends "+fullClassName(abstractType)+" "+generateInterface()+" {\n  /**\n   * Sole constructor.  (For invocation by subclass\n   * constructors, typically implicit.)\n   */\n  protected "+className()+"() {}\n\n"+generateBlock()+"\n"











);
generateBody(writer);
writer.write("\n}\n"

);
  }

  public void generateBody(java.io.Writer writer) throws java.io.IOException {
    // methods for each operator
    ClassNameList consum = operatorList;
    while (!consum.isEmptyConcClassName()) {
      ClassName operatorName = consum.getHeadConcClassName();
      consum = consum.getTailConcClassName();

      writer.write("\n  /**\n   * Returns true if the term is rooted by the symbol "+operatorName.getName()+"\n   *\n   * @return true if the term is rooted by the symbol "+operatorName.getName()+"\n   */\n  public boolean "+isOperatorMethod(operatorName)+"() {\n    return false;\n  }\n"








);
    }
    // methods for each slot
    SlotFieldList sl = slotList;
    while (!sl.isEmptyConcSlotField()) {
      SlotField slot = sl.getHeadConcSlotField();
      sl = sl.getTailConcSlotField();

      writer.write("\n  /**\n   * Returns the subterm corresponding to the slot "+slot.getName()+"\n   *\n   * @return the subterm corresponding to the slot "+slot.getName()+"\n   */\n  public "+slotDomain(slot)+" "+getMethod(slot)+"() {\n    throw new UnsupportedOperationException(\"This "+className()+" has no "+slot.getName()+"\");\n  }\n\n  /**\n   * Returns a new term where the subterm corresponding to the slot "+slot.getName()+"\n   * is replaced by the term given in argument.\n   * Note that there is no side-effect: a new term is returned and the original term is left unchanged\n   *\n   * @param _arg the value of the new subterm\n   * @return a new term where the subterm corresponding to the slot "+slot.getName()+" is replaced by _arg\n   */\n  public "+className()+" "+setMethod(slot)+"("+slotDomain(slot)+" _arg) {\n    throw new UnsupportedOperationException(\"This "+className()+" has no "+slot.getName()+"\");\n  }\n"




















);

    }

    /* fromTerm method, dispatching to operator classes */
    writer.write("\n  protected static tom.library.utils.IdConverter idConv = new tom.library.utils.IdConverter();\n\n  /**\n   * Returns an ATerm representation of this term.\n   *\n   * @return null to indicate to sub-classes that they have to work\n   */\n  public aterm.ATerm toATerm() {\n    // returns null to indicate sub-classes that they have to work\n    return null;\n  }\n\n  /**\n   * Returns a "+fullClassName()+" from an ATerm without any conversion\n   *\n   * @param trm ATerm to handle to retrieve a Gom term\n   * @return the term from the ATerm\n   */\n  public static "+fullClassName()+" fromTerm(aterm.ATerm trm) {\n    return fromTerm(trm,idConv);\n  }\n\n  /**\n   * Returns a "+fullClassName()+" from a String without any conversion\n   *\n   * @param s String containing the ATerm\n   * @return the term from the String\n   */\n  public static "+fullClassName()+" fromString(String s) {\n    return fromTerm(atermFactory.parse(s),idConv);\n  }\n\n  /**\n   * Returns a "+fullClassName()+" from a Stream without any conversion\n   *\n   * @param stream stream containing the ATerm\n   * @return the term from the Stream\n   * @throws java.io.IOException if a problem occurs with the stream\n   */\n  public static "+fullClassName()+" fromStream(java.io.InputStream stream) throws java.io.IOException {\n    return fromTerm(atermFactory.readFromFile(stream),idConv);\n  }\n\n  /**\n   * Apply a conversion on the ATerm and returns a "+fullClassName()+"\n   *\n   * @param trm ATerm to convert into a Gom term\n   * @param atConv ATermConverter used to convert the ATerm\n   * @return the Gom term\n   * @throws IllegalArgumentException\n   */\n  public static "+fullClassName()+" fromTerm(aterm.ATerm trm, tom.library.utils.ATermConverter atConv) {\n    aterm.ATerm convertedTerm = atConv.convert(trm);\n    "+fullClassName()+" tmp;\n    java.util.ArrayList<"+fullClassName()+"> results = new java.util.ArrayList<"+fullClassName()+">();\n"























































);
    ClassNameList constructor = tom_append_list_ConcClassName(operatorList,tom_append_list_ConcClassName(variadicOperatorList, tom.gom.adt.objects.types.classnamelist.EmptyConcClassName.make() ));
    while(!constructor.isEmptyConcClassName()) {
      ClassName operatorName = constructor.getHeadConcClassName();
      constructor = constructor.getTailConcClassName();
      writer.write("\n    tmp = "+fullClassName(operatorName)+".fromTerm(convertedTerm,atConv);\n    if(tmp!=null) {\n      results.add(tmp);\n    }"



);
    }

    writer.write("\n    switch(results.size()) {\n      case 0:\n        throw new IllegalArgumentException(trm + \" is not a "+className()+"\");\n      case 1:\n        return results.get(0);\n      default:\n        java.util.logging.Logger.getLogger(\""+className()+"\").log(java.util.logging.Level.WARNING,\"There were many possibilities ({0}) in {1} but the first one was chosen: {2}\",new Object[] {results.toString(), \""+fullClassName()+"\", results.get(0).toString()});\n        return results.get(0);\n    }\n  }\n\n  /**\n   * Apply a conversion on the ATerm contained in the String and returns a "+fullClassName()+" from it\n   *\n   * @param s String containing the ATerm\n   * @param atConv ATerm Converter used to convert the ATerm\n   * @return the Gom term\n   */\n  public static "+fullClassName()+" fromString(String s, tom.library.utils.ATermConverter atConv) {\n    return fromTerm(atermFactory.parse(s),atConv);\n  }\n\n  /**\n   * Apply a conversion on the ATerm contained in the Stream and returns a "+fullClassName()+" from it\n   *\n   * @param stream stream containing the ATerm\n   * @param atConv ATerm Converter used to convert the ATerm\n   * @return the Gom term\n   */\n  public static "+fullClassName()+" fromStream(java.io.InputStream stream, tom.library.utils.ATermConverter atConv) throws java.io.IOException {\n    return fromTerm(atermFactory.readFromFile(stream),atConv);\n  }\n"
































);

    /* abstract method to compare two terms represented by objects without maximal sharing */
    /* used in the mapping */
    if(!maximalsharing) {
      writer.write("\n  /**\n   * Abstract method to compare two terms represented by objects without maximal sharing\n   *\n   * @param o Object used to compare\n   * @return true if the two objects are equal\n   */\n  public abstract boolean deepEquals(Object o);\n"







);
    }

    /* length and reverse prototypes, only usable on lists */
    writer.write("\n  /**\n   * Returns the length of the list\n   *\n   * @return the length of the list\n   * @throws IllegalArgumentException if the term is not a list\n   */\n  public int length() {\n    throw new IllegalArgumentException(\n      \"This \"+this.getClass().getName()+\" is not a list\");\n  }\n\n  /**\n   * Returns an inverted term\n   *\n   * @return the inverted list\n   * @throws IllegalArgumentException if the term is not a list\n   */\n  public "+fullClassName()+" reverse() {\n    throw new IllegalArgumentException(\n      \"This \"+this.getClass().getName()+\" is not a list\");\n  }\n  "





















);

    /*
     * generate a getCollection<OpName>() method for all variadic operators
     */
    ClassNameList varopList = variadicOperatorList;
    while (!varopList.isEmptyConcClassName()) {
      ClassName operatorName = varopList.getHeadConcClassName();
      varopList = varopList.getTailConcClassName();

      String varopName = operatorName.getName();
      SlotFieldList tmpsl = slotList;
      while (!tmpsl.isEmptyConcSlotField()) {
        SlotField slot = tmpsl.getHeadConcSlotField();
        tmpsl = tmpsl.getTailConcSlotField();
        if(slot.getName().equals("Head" + varopName)) {
          String domainClassName = fullClassName(slot.getDomain());
          writer.write("\n  /**\n   * Returns a Collection extracted from the term\n   *\n   * @return the collection\n   * @throws UnsupportedOperationException if the term is not a list\n   */\n  public java.util.Collection<"+primitiveToReferenceType(domainClassName)+"> getCollection"+varopName+"() {\n    throw new UnsupportedOperationException(\"This "+className()+" cannot be converted into a Collection\");\n  }\n          "









);
        }
      }
    }

  /*
    // methods for each variadic operator
    consum = variadicOperatorList;
    while(!consum.isEmptyConcClassName()) {
      ClassName operatorName = consum.getHeadConcClassName();
      consum = consum.getTailConcClassName();
      // look for the corresponding domain
matchblock: {
      %match(slotList) {
        ConcSlotField(_*,slot@SlotField[Name=opname,Domain=domain],_*) -> {
          if(`opname.equals("Head"+operatorName.getName())) {
      writer.write(%[
  public java.util.Collection<@primitiveToReferenceType(slotDomain(`slot))@> @getCollectionMethod(operatorName)@() {
    throw new IllegalArgumentException(
      "This "+this.getClass().getName()+" is not a list");
  }
]%);
      break matchblock;
          }
        }
      }
      }
    }
  */
    if(hooks.containsTomCode()) {
      mapping.generate(writer);
    }
  }

  public void generateTomMapping(Writer writer) throws java.io.IOException {
    writer.write("\n%typeterm "+className()+" {\n  implement { "+fullClassName()+" }\n  is_sort(t) { ($t instanceof "+fullClassName()+") }\n"



);
    if(maximalsharing) {
      writer.write("\n  equals(t1,t2) { ($t1==$t2) }\n"

);
    } else {
      writer.write("\n  equals(t1,t2) { ((("+fullClassName()+")$t1).deepEquals($t2)) }\n"

);
    }
    writer.write("\n}\n"

);
 }
}
