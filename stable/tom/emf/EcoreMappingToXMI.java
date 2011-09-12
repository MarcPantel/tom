/*
*
* TOM - To One Matching Compiler
*
* Copyright (c) 2009-2011, INPL, INRIA
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
* Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
*
**/

package tom.emf;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
* Transforms an EcorePackage to XMI thanks an EcoreMapping (ecoremapping.tom)
* 
* @author Nicolas HENRY
* 
*/

public class EcoreMappingToXMI {



private static <O> EList<O> append(O e,EList<O> l) {
l.add(e);
return l;
}

public static <O extends EObject> O construct(O o, Object[] objs){
int i=0;
EList<EStructuralFeature> sfes = o.eClass().getEAllStructuralFeatures();
for(EStructuralFeature esf : sfes) {
if(esf.isChangeable()){
o.eSet(esf, objs[i]);
i++;
}
}
return o;
}

  private static   EList<org.eclipse.emf.ecore.EObject>  tom_get_slice_EObjectEList( EList<org.eclipse.emf.ecore.EObject>  subject, int begin, int end) {
     EList<org.eclipse.emf.ecore.EObject>  result =  new BasicEList<org.eclipse.emf.ecore.EObject>(end-begin) ;
    while(begin!=end) {
      result =  append( subject.get(begin) ,result) ;
      begin++;
    }
    return result;
  }

  private static   EList<org.eclipse.emf.ecore.EObject>  tom_append_array_EObjectEList( EList<org.eclipse.emf.ecore.EObject>  l2,  EList<org.eclipse.emf.ecore.EObject>  l1) {
    int size1 =  l1.size() ;
    int size2 =  l2.size() ;
    int index;
     EList<org.eclipse.emf.ecore.EObject>  result =  new BasicEList<org.eclipse.emf.ecore.EObject>(size1+size2) ;
    index=size1;
    while(index >0) {
      result =  append( l1.get(size1-index) ,result) ;
      index--;
    }

    index=size2;
    while(index > 0) {
      result =  append( l2.get(size2-index) ,result) ;
      index--;
    }
    return result;
  }
  private static   EList<org.eclipse.emf.ecore.EClass>  tom_get_slice_EClassEList( EList<org.eclipse.emf.ecore.EClass>  subject, int begin, int end) {
     EList<org.eclipse.emf.ecore.EClass>  result =  new BasicEList<org.eclipse.emf.ecore.EClass>(end-begin) ;
    while(begin!=end) {
      result =  append( subject.get(begin) ,result) ;
      begin++;
    }
    return result;
  }

  private static   EList<org.eclipse.emf.ecore.EClass>  tom_append_array_EClassEList( EList<org.eclipse.emf.ecore.EClass>  l2,  EList<org.eclipse.emf.ecore.EClass>  l1) {
    int size1 =  l1.size() ;
    int size2 =  l2.size() ;
    int index;
     EList<org.eclipse.emf.ecore.EClass>  result =  new BasicEList<org.eclipse.emf.ecore.EClass>(size1+size2) ;
    index=size1;
    while(index >0) {
      result =  append( l1.get(size1-index) ,result) ;
      index--;
    }

    index=size2;
    while(index > 0) {
      result =  append( l2.get(size2-index) ,result) ;
      index--;
    }
    return result;
  }
  private static   EList<org.eclipse.emf.ecore.EClassifier>  tom_get_slice_EClassifierEList( EList<org.eclipse.emf.ecore.EClassifier>  subject, int begin, int end) {
     EList<org.eclipse.emf.ecore.EClassifier>  result =  new BasicEList<org.eclipse.emf.ecore.EClassifier>(end-begin) ;
    while(begin!=end) {
      result =  append( subject.get(begin) ,result) ;
      begin++;
    }
    return result;
  }

  private static   EList<org.eclipse.emf.ecore.EClassifier>  tom_append_array_EClassifierEList( EList<org.eclipse.emf.ecore.EClassifier>  l2,  EList<org.eclipse.emf.ecore.EClassifier>  l1) {
    int size1 =  l1.size() ;
    int size2 =  l2.size() ;
    int index;
     EList<org.eclipse.emf.ecore.EClassifier>  result =  new BasicEList<org.eclipse.emf.ecore.EClassifier>(size1+size2) ;
    index=size1;
    while(index >0) {
      result =  append( l1.get(size1-index) ,result) ;
      index--;
    }

    index=size2;
    while(index > 0) {
      result =  append( l2.get(size2-index) ,result) ;
      index--;
    }
    return result;
  }
  private static   EList<org.eclipse.emf.ecore.EAttribute>  tom_get_slice_EAttributeEList( EList<org.eclipse.emf.ecore.EAttribute>  subject, int begin, int end) {
     EList<org.eclipse.emf.ecore.EAttribute>  result =  new BasicEList<org.eclipse.emf.ecore.EAttribute>(end-begin) ;
    while(begin!=end) {
      result =  append( subject.get(begin) ,result) ;
      begin++;
    }
    return result;
  }

  private static   EList<org.eclipse.emf.ecore.EAttribute>  tom_append_array_EAttributeEList( EList<org.eclipse.emf.ecore.EAttribute>  l2,  EList<org.eclipse.emf.ecore.EAttribute>  l1) {
    int size1 =  l1.size() ;
    int size2 =  l2.size() ;
    int index;
     EList<org.eclipse.emf.ecore.EAttribute>  result =  new BasicEList<org.eclipse.emf.ecore.EAttribute>(size1+size2) ;
    index=size1;
    while(index >0) {
      result =  append( l1.get(size1-index) ,result) ;
      index--;
    }

    index=size2;
    while(index > 0) {
      result =  append( l2.get(size2-index) ,result) ;
      index--;
    }
    return result;
  }

/**
* Return XML string matching the object
* @param eo EPackage to match
* @param subpackage to set if eo is a subpackage
* @return XML string matching the object
*/

public static String parse(EPackage eo, boolean subpackage) {
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EPackage ) {
if ( (( org.eclipse.emf.ecore.EPackage )((Object)eo)) instanceof org.eclipse.emf.ecore.EPackage ) {
if ( (( org.eclipse.emf.ecore.EPackage )(( org.eclipse.emf.ecore.EPackage )((Object)eo))) instanceof org.eclipse.emf.ecore.EPackage ) {

sb.append("<"+(subpackage?"eSubpackages":"ecore:EPackage " +
"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
"xmlns:ecore=\"http://www.eclipse.org/emf/2002/Ecore\" " +
"xsi:schemaLocation=\"http://www.eclipse.org/emf/2002/Ecore \" ") +
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " +
"nsURI=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("nsURI")) + "\" " +
"nsPrefix=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("nsPrefix")) + "\" " +
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EClassifier>)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("eClassifiers")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EPackage>)(( org.eclipse.emf.ecore.EPackage )((Object)eo)).eGet((( org.eclipse.emf.ecore.EPackage )((Object)eo)).eClass().getEStructuralFeature("eSubpackages")) ));
sb.append("</ecore:EPackage>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EClass to match
* @return XML string matching the object
*/

public static String parse(EClass eo) {
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EClass ) {
if ( (( org.eclipse.emf.ecore.EClass )((Object)eo)) instanceof org.eclipse.emf.ecore.EClass ) {
if ( (( org.eclipse.emf.ecore.EClass )(( org.eclipse.emf.ecore.EClass )((Object)eo))) instanceof org.eclipse.emf.ecore.EClass ) {
 String  tom_instanceClassName= (java.lang.String)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("instanceClassName")) ;
 String  tom_instanceTypeName= (java.lang.String)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("instanceTypeName")) ;
 boolean  tom__abstract= (java.lang.Boolean)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("abstract")) ;
 boolean  tom__interface= (java.lang.Boolean)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("interface")) ;
 EList<org.eclipse.emf.ecore.EClass>  tom_eSuperTypes= (EList<org.eclipse.emf.ecore.EClass>)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("eSuperTypes")) ;
 org.eclipse.emf.ecore.EClass  tom_c=(( org.eclipse.emf.ecore.EClass )((Object)eo));

StringBuffer sb2=new StringBuffer();

{
{
if ( ((Object)tom_eSuperTypes) instanceof EList<?> && (((EList<org.eclipse.emf.ecore.EClass>)((Object)tom_eSuperTypes)).size() == 0 || (((EList<org.eclipse.emf.ecore.EClass>)((Object)tom_eSuperTypes)).size()>0 && ((EList<org.eclipse.emf.ecore.EClass>)((Object)tom_eSuperTypes)).get(0) instanceof org.eclipse.emf.ecore.EClass)) ) {
if ( (( EList<org.eclipse.emf.ecore.EClass> )(( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes))) instanceof EList<?> && ((( EList<org.eclipse.emf.ecore.EClass> )(( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes))).size() == 0 || ((( EList<org.eclipse.emf.ecore.EClass> )(( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes))).size()>0 && (( EList<org.eclipse.emf.ecore.EClass> )(( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes))).get(0) instanceof org.eclipse.emf.ecore.EClass)) ) {
int tomMatch3__end__5=0;
do {
{
if (!(tomMatch3__end__5 >=  (( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes)).size() )) {
 org.eclipse.emf.ecore.EClass  tomMatch3_19= (( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes)).get(tomMatch3__end__5) ;
if ( tomMatch3_19 instanceof org.eclipse.emf.ecore.EClass ) {
if ( (( org.eclipse.emf.ecore.EClass )tomMatch3_19) instanceof org.eclipse.emf.ecore.EClass ) {
sb2.append("#//" + 
 (java.lang.String)tomMatch3_19.eGet(tomMatch3_19.eClass().getEStructuralFeature("name")) + " "); 

}
}
}
tomMatch3__end__5=tomMatch3__end__5 + 1;

}
} while(!(tomMatch3__end__5 >  (( EList<org.eclipse.emf.ecore.EClass> )((Object)tom_eSuperTypes)).size() ));
}
}

}

}

sb.append("<eClassifiers " +
"xsi:type=\"ecore:EClass\" " +
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " + 
(! EObject.class.isAssignableFrom(
tom_c.getInstanceClass()) ? "instanceClassName=\"" + 
tom_instanceClassName+ "\" " : "") + 
(!
tom_instanceClassName.equals(
tom_instanceTypeName) ? "instanceTypeName=\"" + 
tom_instanceTypeName+ "\" " : "") + 
((Boolean)
tom_c.eClass().getEStructuralFeature("abstract").getDefaultValue() != 
tom__abstract? "abstract=\"" + 
tom__abstract+ "\" " : "") + 
((Boolean)
tom_c.eClass().getEStructuralFeature("interface").getDefaultValue() != 
tom__interface? "interface=\"" + 
tom__interface+ "\" " : "") + 
(sb2.length() > 0 ? "eSuperTypes=\"" + sb2 + "\" " : "") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.ETypeParameter>)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("eTypeParameters")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EOperation>)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("eOperations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EStructuralFeature>)(( org.eclipse.emf.ecore.EClass )((Object)eo)).eGet((( org.eclipse.emf.ecore.EClass )((Object)eo)).eClass().getEStructuralFeature("eStructuralFeatures")) ));
sb.append("</eClassifiers>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EDataType to match
* @return XML string matching the object
*/

public static String parse(EDataType eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EDataType ) {
if ( (( org.eclipse.emf.ecore.EDataType )((Object)eo)) instanceof org.eclipse.emf.ecore.EDataType ) {
if ( (( org.eclipse.emf.ecore.EDataType )(( org.eclipse.emf.ecore.EDataType )((Object)eo))) instanceof org.eclipse.emf.ecore.EDataType ) {
 String  tom_instanceClassName= (java.lang.String)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("instanceClassName")) ;
 String  tom_instanceTypeName= (java.lang.String)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("instanceTypeName")) ;
 boolean  tom_serializable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("serializable")) ;

sb.append("<eClassifiers " +
"xsi:type=\"ecore:EDataType\" " +
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " +
"instanceClassName=\""+
tom_instanceClassName+"\" " +
(!
tom_instanceClassName.equals(
tom_instanceTypeName)?"instanceTypeName=\"" + 
tom_instanceTypeName+ "\" ":"") + 
((Boolean)
(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("serializable").getDefaultValue() != 
tom_serializable?"serializable=\"" + 
tom_serializable+ "\" ":"") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.ETypeParameter>)(( org.eclipse.emf.ecore.EDataType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EDataType )((Object)eo)).eClass().getEStructuralFeature("eTypeParameters")) ));
sb.append("</eClassifiers>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EEnum to match
* @return XML string matching the object
*/

public static String parse(EEnum eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EEnum ) {
if ( (( org.eclipse.emf.ecore.EEnum )((Object)eo)) instanceof org.eclipse.emf.ecore.EEnum ) {
if ( (( org.eclipse.emf.ecore.EEnum )(( org.eclipse.emf.ecore.EEnum )((Object)eo))) instanceof org.eclipse.emf.ecore.EEnum ) {
 String  tom_instanceClassName= (java.lang.String)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("instanceClassName")) ;
 String  tom_instanceTypeName= (java.lang.String)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("instanceTypeName")) ;
 boolean  tom_serializable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("serializable")) ;

sb.append("<eClassifiers " +
"xsi:type=\"ecore:EEnum\" " +
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " +
"instanceClassName=\""+
tom_instanceClassName+"\" " +
(!
tom_instanceClassName.equals(
tom_instanceTypeName)?"instanceTypeName=\"" + 
tom_instanceTypeName+ "\" ":"") + 
((Boolean)
(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("serializable").getDefaultValue() != 
tom_serializable?"serializable=\"" + 
tom_serializable+ "\" ":"") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.ETypeParameter>)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("eTypeParameters")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EEnumLiteral>)(( org.eclipse.emf.ecore.EEnum )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnum )((Object)eo)).eClass().getEStructuralFeature("eLiterals")) ));
sb.append("</eClassifiers>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EAnnotation to match
* @return XML string matching the object
*/

public static String parse(EAnnotation eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EAnnotation ) {
if ( (( org.eclipse.emf.ecore.EAnnotation )((Object)eo)) instanceof org.eclipse.emf.ecore.EAnnotation ) {
if ( (( org.eclipse.emf.ecore.EAnnotation )(( org.eclipse.emf.ecore.EAnnotation )((Object)eo))) instanceof org.eclipse.emf.ecore.EAnnotation ) {
 String  tom_source= (java.lang.String)(( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eClass().getEStructuralFeature("source")) ;

sb.append("<eAnnotations" + (
tom_source!= null?" source=\""+
tom_source+"\"":"") + ">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<EObject>)(( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAnnotation )((Object)eo)).eClass().getEStructuralFeature("details")) ));
sb.append("</eAnnotations>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EAttribute to match
* @return XML string matching the object
*/

public static String parse(EAttribute eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EAttribute ) {
if ( (( org.eclipse.emf.ecore.EAttribute )((Object)eo)) instanceof org.eclipse.emf.ecore.EAttribute ) {
if ( (( org.eclipse.emf.ecore.EAttribute )(( org.eclipse.emf.ecore.EAttribute )((Object)eo))) instanceof org.eclipse.emf.ecore.EAttribute ) {
 boolean  tom_ordered= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("ordered")) ;
 boolean  tom_unique= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("unique")) ;
 int  tom_lowerBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("lowerBound")) ;
 int  tom_upperBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("upperBound")) ;
 org.eclipse.emf.ecore.EClassifier  tom_eType= (org.eclipse.emf.ecore.EClassifier)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("eType")) ;
 org.eclipse.emf.ecore.EGenericType  tom_eGenericType= (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("eGenericType")) ;
 boolean  tom_changeable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("changeable")) ;
 boolean  tom__volatile= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("volatile")) ;
 boolean  tom__transient= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("transient")) ;
 String  tom_defaultValueLiteral= (java.lang.String)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("defaultValueLiteral")) ;
 boolean  tom_unsettable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("unsettable")) ;
 boolean  tom_derived= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("derived")) ;
 boolean  tom_iD= (java.lang.Boolean)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("iD")) ;
 org.eclipse.emf.ecore.EAttribute  tom_a=(( org.eclipse.emf.ecore.EAttribute )((Object)eo));

sb.append("<eStructuralFeatures xsi:type=\"ecore:EAttribute\" " + 
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " + 
((Boolean)
tom_a.eClass().getEStructuralFeature("ordered").getDefaultValue() != 
tom_ordered?"ordered=\"" + 
tom_ordered+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("unique").getDefaultValue() != 
tom_unique?"unique=\"" + 
tom_unique+ "\" ":"") + 
((Integer)
tom_a.eClass().getEStructuralFeature("lowerBound").getDefaultValue() != 
tom_lowerBound?"lowerBound=\"" + 
tom_lowerBound+ "\" ":"") + 
((Integer)
tom_a.eClass().getEStructuralFeature("upperBound").getDefaultValue() != 
tom_upperBound?"upperBound=\"" + 
tom_upperBound+ "\" ":"") + 
(
tom_eType!= null&&
tom_eType.getETypeParameters().size() == 0?"eType=\"#//" + 
tom_eType.getName() + "\" ":"") +
((Boolean)
tom_a.eClass().getEStructuralFeature("changeable").getDefaultValue() != 
tom_changeable?"changeable=\"" + 
tom_changeable+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("volatile").getDefaultValue() != 
tom__volatile?"volatile=\"" + 
tom__volatile+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("transient").getDefaultValue() != 
tom__transient?"transient=\"" + 
tom__transient+ "\" ":"") + 
(
tom_defaultValueLiteral!= null?"defaultValueLiteral=\"" + 
tom_defaultValueLiteral+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("unsettable").getDefaultValue() != 
tom_unsettable?"unsettable=\"" + 
tom_unsettable+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("derived").getDefaultValue() != 
tom_derived?"derived=\"" + 
tom_derived+ "\" ":"") + 
((Boolean)
tom_a.eClass().getEStructuralFeature("iD").getDefaultValue() != 
tom_iD?"iD=\"" + 
tom_iD+ "\" ":"") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eGet((( org.eclipse.emf.ecore.EAttribute )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
if(
tom_eGenericType!= null && (
tom_eGenericType.getEClassifier() != 
tom_eType|| 
tom_eType.getETypeParameters().size() >0)){
sb.append(parse(
tom_eGenericType, false));
}
sb.append("</eStructuralFeatures>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EReference to match
* @return XML string matching the object
*/

public static String parse(EReference eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EReference ) {
if ( (( org.eclipse.emf.ecore.EReference )((Object)eo)) instanceof org.eclipse.emf.ecore.EReference ) {
if ( (( org.eclipse.emf.ecore.EReference )(( org.eclipse.emf.ecore.EReference )((Object)eo))) instanceof org.eclipse.emf.ecore.EReference ) {
 boolean  tom_ordered= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("ordered")) ;
 boolean  tom_unique= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("unique")) ;
 int  tom_lowerBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("lowerBound")) ;
 int  tom_upperBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("upperBound")) ;
 org.eclipse.emf.ecore.EClassifier  tom_eType= (org.eclipse.emf.ecore.EClassifier)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("eType")) ;
 org.eclipse.emf.ecore.EGenericType  tom_eGenericType= (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("eGenericType")) ;
 boolean  tom_changeable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("changeable")) ;
 boolean  tom__volatile= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("volatile")) ;
 boolean  tom__transient= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("transient")) ;
 String  tom_defaultValueLiteral= (java.lang.String)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("defaultValueLiteral")) ;
 boolean  tom_unsettable= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("unsettable")) ;
 boolean  tom_derived= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("derived")) ;
 boolean  tom_containment= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("containment")) ;
 boolean  tom_resolveProxies= (java.lang.Boolean)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("resolveProxies")) ;
 org.eclipse.emf.ecore.EReference  tom_eOpposite= (org.eclipse.emf.ecore.EReference)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("eOpposite")) ;
 EList<org.eclipse.emf.ecore.EAttribute>  tom_eKeys= (EList<org.eclipse.emf.ecore.EAttribute>)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("eKeys")) ;
 org.eclipse.emf.ecore.EReference  tom_r=(( org.eclipse.emf.ecore.EReference )((Object)eo));

StringBuffer sb2=new StringBuffer();

{
{
if ( ((Object)tom_eKeys) instanceof EList<?> && (((EList<org.eclipse.emf.ecore.EAttribute>)((Object)tom_eKeys)).size() == 0 || (((EList<org.eclipse.emf.ecore.EAttribute>)((Object)tom_eKeys)).size()>0 && ((EList<org.eclipse.emf.ecore.EAttribute>)((Object)tom_eKeys)).get(0) instanceof org.eclipse.emf.ecore.EAttribute)) ) {
if ( (( EList<org.eclipse.emf.ecore.EAttribute> )(( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys))) instanceof EList<?> && ((( EList<org.eclipse.emf.ecore.EAttribute> )(( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys))).size() == 0 || ((( EList<org.eclipse.emf.ecore.EAttribute> )(( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys))).size()>0 && (( EList<org.eclipse.emf.ecore.EAttribute> )(( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys))).get(0) instanceof org.eclipse.emf.ecore.EAttribute)) ) {
int tomMatch9__end__5=0;
do {
{
if (!(tomMatch9__end__5 >=  (( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys)).size() )) {
 org.eclipse.emf.ecore.EAttribute  tomMatch9_23= (( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys)).get(tomMatch9__end__5) ;
if ( tomMatch9_23 instanceof org.eclipse.emf.ecore.EAttribute ) {
if ( (( org.eclipse.emf.ecore.EAttribute )tomMatch9_23) instanceof org.eclipse.emf.ecore.EAttribute ) {

sb2.append("#//" + (
 (org.eclipse.emf.ecore.EClassifier)tomMatch9_23.eGet(tomMatch9_23.eClass().getEStructuralFeature("eType")) ).getName() + "/" + 
 (java.lang.String)tomMatch9_23.eGet(tomMatch9_23.eClass().getEStructuralFeature("name")) + " ");


}
}
}
tomMatch9__end__5=tomMatch9__end__5 + 1;

}
} while(!(tomMatch9__end__5 >  (( EList<org.eclipse.emf.ecore.EAttribute> )((Object)tom_eKeys)).size() ));
}
}

}

}

sb.append("<eStructuralFeatures xsi:type=\"ecore:EReference\" " + 
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " + 
((Boolean)
tom_r.eClass().getEStructuralFeature("ordered").getDefaultValue() != 
tom_ordered?"ordered=\"" + 
tom_ordered+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("unique").getDefaultValue() != 
tom_unique?"unique=\"" + 
tom_unique+ "\" ":"") + 
((Integer)
tom_r.eClass().getEStructuralFeature("lowerBound").getDefaultValue() != 
tom_lowerBound?"lowerBound=\"" + 
tom_lowerBound+ "\" ":"") + 
((Integer)
tom_r.eClass().getEStructuralFeature("upperBound").getDefaultValue() != 
tom_upperBound?"upperBound=\"" + 
tom_upperBound+ "\" ":"") + 
(
tom_eType!= null&&
tom_eType.getETypeParameters().size() == 0?"eType=\"#//" + 
tom_eType.getName() + "\" ":"") +
((Boolean)
tom_r.eClass().getEStructuralFeature("changeable").getDefaultValue() != 
tom_changeable?"changeable=\"" + 
tom_changeable+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("volatile").getDefaultValue() != 
tom__volatile?"volatile=\"" + 
tom__volatile+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("transient").getDefaultValue() != 
tom__transient?"transient=\"" + 
tom__transient+ "\" ":"") + 
(
tom_defaultValueLiteral!= null?"defaultValueLiteral=\""+
tom_defaultValueLiteral+ "\" ":"") +
((Boolean)
tom_r.eClass().getEStructuralFeature("unsettable").getDefaultValue() != 
tom_unsettable?"unsettable=\"" + 
tom_unsettable+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("derived").getDefaultValue() != 
tom_derived?"derived=\"" + 
tom_derived+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("containment").getDefaultValue() != 
tom_containment?"containment=\"" + 
tom_containment+ "\" ":"") + 
((Boolean)
tom_r.eClass().getEStructuralFeature("resolveProxies").getDefaultValue() != 
tom_resolveProxies?"resolveProxies=\"" + 
tom_resolveProxies+ "\" ":"") + 
(
tom_eOpposite!= null?"eOpposite=\"#//" + 
tom_eType.getName() + "/" + 
tom_eOpposite.getName() + "\" ":"") + 
(sb2.length()>0?"eKeys=\"" + sb2 + "\" ":"") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EReference )((Object)eo)).eGet((( org.eclipse.emf.ecore.EReference )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
if(
tom_eGenericType!= null && (
tom_eGenericType.getEClassifier() != 
tom_eType|| 
tom_eType.getETypeParameters().size() >0)){
sb.append(parse(
tom_eGenericType, false));
}
sb.append("</eStructuralFeatures>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EGenericType to match
* @param argument to set if eo is an argument
* @return XML string matching the object
*/

public static String parse(EGenericType eo, boolean argument){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EGenericType ) {
if ( (( org.eclipse.emf.ecore.EGenericType )((Object)eo)) instanceof org.eclipse.emf.ecore.EGenericType ) {
if ( (( org.eclipse.emf.ecore.EGenericType )(( org.eclipse.emf.ecore.EGenericType )((Object)eo))) instanceof org.eclipse.emf.ecore.EGenericType ) {
 org.eclipse.emf.ecore.EClassifier  tom_eClassifier= (org.eclipse.emf.ecore.EClassifier)(( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eClass().getEStructuralFeature("eClassifier")) ;

sb.append((!argument?"<eGenericType ":"<eTypeArguments ") + 
(
tom_eClassifier!= null?"eClassifier=\"#//" + 
tom_eClassifier.getName() + "\"":"") + 
">");
sb.append(parse(
 (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eClass().getEStructuralFeature("eUpperBound")) , false));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EGenericType>)(( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eClass().getEStructuralFeature("eTypeArguments")) ));
sb.append(parse(
 (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eClass().getEStructuralFeature("eLowerBound")) , false));
sb.append(parse(
 (org.eclipse.emf.ecore.ETypeParameter)(( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eGet((( org.eclipse.emf.ecore.EGenericType )((Object)eo)).eClass().getEStructuralFeature("eTypeParameter")) ));
sb.append(!argument?"</eGenericType>":"</eTypeArguments>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EOperation to match
* @return XML string matching the object
*/

public static String parse(EOperation eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EOperation ) {
if ( (( org.eclipse.emf.ecore.EOperation )((Object)eo)) instanceof org.eclipse.emf.ecore.EOperation ) {
if ( (( org.eclipse.emf.ecore.EOperation )(( org.eclipse.emf.ecore.EOperation )((Object)eo))) instanceof org.eclipse.emf.ecore.EOperation ) {
 boolean  tom_ordered= (java.lang.Boolean)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("ordered")) ;
 boolean  tom_unique= (java.lang.Boolean)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("unique")) ;
 int  tom_lowerBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("lowerBound")) ;
 int  tom_upperBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("upperBound")) ;
 org.eclipse.emf.ecore.EClassifier  tom_eType= (org.eclipse.emf.ecore.EClassifier)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eType")) ;
 org.eclipse.emf.ecore.EGenericType  tom_eGenericType= (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eGenericType")) ;
 EList<org.eclipse.emf.ecore.EClassifier>  tom_eExceptions= (EList<org.eclipse.emf.ecore.EClassifier>)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eExceptions")) ;
 org.eclipse.emf.ecore.EOperation  tom_o=(( org.eclipse.emf.ecore.EOperation )((Object)eo));

StringBuffer sb2 = new StringBuffer();

{
{
if ( ((Object)tom_eExceptions) instanceof EList<?> && (((EList<org.eclipse.emf.ecore.EClassifier>)((Object)tom_eExceptions)).size() == 0 || (((EList<org.eclipse.emf.ecore.EClassifier>)((Object)tom_eExceptions)).size()>0 && ((EList<org.eclipse.emf.ecore.EClassifier>)((Object)tom_eExceptions)).get(0) instanceof org.eclipse.emf.ecore.EClassifier)) ) {
if ( (( EList<org.eclipse.emf.ecore.EClassifier> )(( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions))) instanceof EList<?> && ((( EList<org.eclipse.emf.ecore.EClassifier> )(( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions))).size() == 0 || ((( EList<org.eclipse.emf.ecore.EClassifier> )(( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions))).size()>0 && (( EList<org.eclipse.emf.ecore.EClassifier> )(( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions))).get(0) instanceof org.eclipse.emf.ecore.EClassifier)) ) {
int tomMatch12__end__5=0;
do {
{
if (!(tomMatch12__end__5 >=  (( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions)).size() )) {

sb2.append("#//" + 
 (( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions)).get(tomMatch12__end__5) .getName() + " ");


}
tomMatch12__end__5=tomMatch12__end__5 + 1;

}
} while(!(tomMatch12__end__5 >  (( EList<org.eclipse.emf.ecore.EClassifier> )((Object)tom_eExceptions)).size() ));
}
}

}

}

sb.append("<eOperations " +
"name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" " +
((Boolean)
tom_o.eClass().getEStructuralFeature("ordered").getDefaultValue() != 
tom_ordered?"ordered=\"" + 
tom_ordered+ "\" ":"") + 
((Boolean)
tom_o.eClass().getEStructuralFeature("unique").getDefaultValue() != 
tom_unique?"unique=\"" + 
tom_unique+ "\" ":"") + 
((Integer)
tom_o.eClass().getEStructuralFeature("lowerBound").getDefaultValue() != 
tom_lowerBound?"lowerBound=\"" + 
tom_lowerBound+ "\" ":"") + 
((Integer)
tom_o.eClass().getEStructuralFeature("upperBound").getDefaultValue() != 
tom_upperBound?"upperBound=\"" + 
tom_upperBound+ "\" ":"") + 
(
tom_eType!= null&&
tom_eType.getETypeParameters().size() == 0?"eType=\"#//" + 
tom_eType.getName() + "\" ":"") +
(sb2.length() > 0?"eExceptions=\"" + sb2 + "\" ":"") + 
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
if(
tom_eGenericType!= null && (
tom_eGenericType.getEClassifier() != 
tom_eType|| 
tom_eType.getETypeParameters().size() >0)){
sb.append(parse(
tom_eGenericType, false));
}
sb.append(parse(
 (EList<org.eclipse.emf.ecore.ETypeParameter>)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eTypeParameters")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EParameter>)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eParameters")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EGenericType>)(( org.eclipse.emf.ecore.EOperation )((Object)eo)).eGet((( org.eclipse.emf.ecore.EOperation )((Object)eo)).eClass().getEStructuralFeature("eGenericExceptions")) ));
sb.append("</eOperations>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EParameter to match
* @return XML string matching the object
*/

public static String parse(EParameter eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EParameter ) {
if ( (( org.eclipse.emf.ecore.EParameter )((Object)eo)) instanceof org.eclipse.emf.ecore.EParameter ) {
if ( (( org.eclipse.emf.ecore.EParameter )(( org.eclipse.emf.ecore.EParameter )((Object)eo))) instanceof org.eclipse.emf.ecore.EParameter ) {
 boolean  tom_ordered= (java.lang.Boolean)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("ordered")) ;
 boolean  tom_unique= (java.lang.Boolean)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("unique")) ;
 int  tom_lowerBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("lowerBound")) ;
 int  tom_upperBound= (java.lang.Integer)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("upperBound")) ;
 org.eclipse.emf.ecore.EClassifier  tom_eType= (org.eclipse.emf.ecore.EClassifier)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("eType")) ;
 org.eclipse.emf.ecore.EGenericType  tom_eGenericType= (org.eclipse.emf.ecore.EGenericType)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("eGenericType")) ;
 org.eclipse.emf.ecore.EParameter  tom_p=(( org.eclipse.emf.ecore.EParameter )((Object)eo));

sb.append("<eParameters name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("name")) + "\" "+
((Boolean)
tom_p.eClass().getEStructuralFeature("ordered").getDefaultValue() != 
tom_ordered?"ordered=\"" + 
tom_ordered+ "\" ":"") + 
((Boolean)
tom_p.eClass().getEStructuralFeature("unique").getDefaultValue() != 
tom_unique?"unique=\"" + 
tom_unique+ "\" ":"") + 
((Integer)
tom_p.eClass().getEStructuralFeature("lowerBound").getDefaultValue() != 
tom_lowerBound?"lowerBound=\"" + 
tom_lowerBound+ "\" ":"") + 
((Integer)
tom_p.eClass().getEStructuralFeature("upperBound").getDefaultValue() != 
tom_upperBound?"upperBound=\"" + 
tom_upperBound+ "\" ":"") + 
(
tom_eType!= null&&
tom_eType.getETypeParameters().size() == 0?"eType=\"#//" + 
tom_eType.getName() + "\" ":"") +
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.EParameter )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
if(
tom_eGenericType!= null && (
tom_eGenericType.getEClassifier() != 
tom_eType|| 
tom_eType.getETypeParameters().size() >0)){
sb.append(parse(
tom_eGenericType, false));
}
sb.append("</eParameters>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo ETypeParameter to match
* @return XML string matching the object
*/

public static String parse(ETypeParameter eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.ETypeParameter ) {
if ( (( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)) instanceof org.eclipse.emf.ecore.ETypeParameter ) {
if ( (( org.eclipse.emf.ecore.ETypeParameter )(( org.eclipse.emf.ecore.ETypeParameter )((Object)eo))) instanceof org.eclipse.emf.ecore.ETypeParameter ) {

sb.append("<eTypeParameters name=\"" + 
 (java.lang.String)(( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eClass().getEStructuralFeature("name")) + "\">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EGenericType>)(( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eGet((( org.eclipse.emf.ecore.ETypeParameter )((Object)eo)).eClass().getEStructuralFeature("eBounds")) ));
sb.append("</eTypeParameters>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EEnumLiteral to match
* @return XML string matching the object
*/

public static String parse(EEnumLiteral eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof org.eclipse.emf.ecore.EEnumLiteral ) {
if ( (( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)) instanceof org.eclipse.emf.ecore.EEnumLiteral ) {
if ( (( org.eclipse.emf.ecore.EEnumLiteral )(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo))) instanceof org.eclipse.emf.ecore.EEnumLiteral ) {
 String  tom_name= (java.lang.String)(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eClass().getEStructuralFeature("name")) ;
 int  tom_value= (java.lang.Integer)(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eClass().getEStructuralFeature("value")) ;
 String  tom_literal= (java.lang.String)(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eClass().getEStructuralFeature("literal")) ;

sb.append("<eLiterals " +
"name=\"" + 
tom_name+ "\" " +
((Integer)
(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eClass().getEStructuralFeature("value").getDefaultValue() != 
tom_value?"value=\"" + 
tom_value+ "\" " : "") +
(!
tom_literal.equals(
tom_name) ? "literal=\"" + 
tom_literal+ "\" " : "") +
">");
sb.append(parse(
 (EList<org.eclipse.emf.ecore.EAnnotation>)(( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eGet((( org.eclipse.emf.ecore.EEnumLiteral )((Object)eo)).eClass().getEStructuralFeature("eAnnotations")) ));
sb.append("</eLiterals>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo Entry to match
* @return XML string matching the object
*/

public static String parse(java.util.Map.Entry<String, String> eo){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)eo) instanceof java.util.Map.Entry<?, ?> ) {
if ( (( EObject )((Object)eo)) instanceof java.util.Map.Entry<?, ?> ) {
if ( (( EObject )(( EObject )((Object)eo))) instanceof java.util.Map.Entry<?, ?> ) {

sb.append("<details key=\"" + 
 (java.lang.String)(( EObject )((Object)eo)).eGet((( EObject )((Object)eo)).eClass().getEStructuralFeature("key")) + "\" value=\"" + 
 (java.lang.String)(( EObject )((Object)eo)).eGet((( EObject )((Object)eo)).eClass().getEStructuralFeature("value")) + "\"/>");


}
}
}

}

}

return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EClassifier to match
* @return XML string matching the object
*/

public static String parse(EClassifier eo){
StringBuffer sb=new StringBuffer();
if(eo instanceof EClass){
sb.append(parse((EClass)eo));
}else if(eo instanceof EEnum){
sb.append(parse((EEnum)eo));
}else if(eo instanceof EDataType){
sb.append(parse((EDataType)eo));
}
return sb.toString();
}

/**
* Return XML string matching the object
* @param eo EList to match
* @return XML string matching the object
*/

public static String parse(EList<?> el){
StringBuffer sb=new StringBuffer();

{
{
if ( ((Object)el) instanceof EList<?> && (((EList<org.eclipse.emf.ecore.EObject>)((Object)el)).size() == 0 || (((EList<org.eclipse.emf.ecore.EObject>)((Object)el)).size()>0 && ((EList<org.eclipse.emf.ecore.EObject>)((Object)el)).get(0) instanceof org.eclipse.emf.ecore.EObject)) ) {
if ( (( EList<org.eclipse.emf.ecore.EObject> )(( EList<org.eclipse.emf.ecore.EObject> )((Object)el))) instanceof EList<?> && ((( EList<org.eclipse.emf.ecore.EObject> )(( EList<org.eclipse.emf.ecore.EObject> )((Object)el))).size() == 0 || ((( EList<org.eclipse.emf.ecore.EObject> )(( EList<org.eclipse.emf.ecore.EObject> )((Object)el))).size()>0 && (( EList<org.eclipse.emf.ecore.EObject> )(( EList<org.eclipse.emf.ecore.EObject> )((Object)el))).get(0) instanceof org.eclipse.emf.ecore.EObject)) ) {
int tomMatch17_2=0;
if (!(tomMatch17_2 >=  (( EList<org.eclipse.emf.ecore.EObject> )((Object)el)).size() )) {
 org.eclipse.emf.ecore.EObject  tom_o= (( EList<org.eclipse.emf.ecore.EObject> )((Object)el)).get(tomMatch17_2) ;

if((
tom_o) instanceof EClassifier){
sb.append(parse((EClassifier)
tom_o));
}else if((
tom_o) instanceof EAnnotation){
sb.append(parse((EAnnotation)
tom_o));
}else if((
tom_o) instanceof EAttribute){
sb.append(parse((EAttribute)
tom_o));
}else if((
tom_o) instanceof EReference){
sb.append(parse((EReference)
tom_o));
}else if((
tom_o) instanceof EOperation){
sb.append(parse((EOperation)
tom_o));
}else if((
tom_o) instanceof EParameter){
sb.append(parse((EParameter)
tom_o));
}else if((
tom_o) instanceof EGenericType){
sb.append(parse((EGenericType)
tom_o, true));
}else if((
tom_o) instanceof java.util.Map.Entry<?,?>){
sb.append(parse((java.util.Map.Entry<String,String>)
tom_o));
}else if((
tom_o) instanceof ETypeParameter){
sb.append(parse((ETypeParameter)
tom_o));
}else if((
tom_o) instanceof EPackage){
sb.append(parse((EPackage)
tom_o,true));
}else if((
tom_o) instanceof EEnumLiteral){
sb.append(parse((EEnumLiteral)
tom_o));
}else{
throw new IllegalArgumentException("Unknown list element type : \n"+
tom_o.toString());
}
sb.append(parse(
tom_get_slice_EObjectEList((( EList<org.eclipse.emf.ecore.EObject> )((Object)el)),tomMatch17_2 + 1, (( EList<org.eclipse.emf.ecore.EObject> )((Object)el)).size() )));


}
}
}

}

}

return sb.toString();
}

/**
* An error manager showing possible XML errors
*/

public static class ErrorManager implements ErrorHandler{
public void warning(SAXParseException exception) throws SAXException{
throw exception;
}
public void error(SAXParseException exception) throws SAXException{
throw exception;
}
public void fatalError(SAXParseException exception) throws SAXException{
throw exception;
}
}

public static void main(String [] args) {
if(args.length != 1) {
System.out.println("usage : java TomMappingFromEcore <EPackageClassName>");
System.exit(1);
}
String xml ="";
try {
EPackage p2 = (EPackage) Class.forName(args[0]).getField("eINSTANCE").get(
null);

xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + parse(p2, false);

DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
DocumentBuilder constructeur = fabrique.newDocumentBuilder();
constructeur.setErrorHandler(new ErrorManager());

Document document = constructeur.parse(new InputSource(new StringReader(xml)));

Transformer transformer = TransformerFactory.newInstance().newTransformer();
transformer.setOutputProperty(OutputKeys.INDENT, "yes");

StreamResult result = new StreamResult(new StringWriter());
DOMSource source = new DOMSource(document);
transformer.transform(source, result);

String xmlString = result.getWriter().toString();
System.out.println(xmlString);
} catch(Exception e) {
e.printStackTrace();
System.err.println(xml.replaceAll(">", ">\n"));
}

}
}
