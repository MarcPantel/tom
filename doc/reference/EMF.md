---
title: Documentation:EMF
permalink: /Documentation:EMF/
---

Overview
========

The goal of this extension is to offer support for manipulating data-structures generated by EMF. For this, we provide:

-   a mapping generator which starts from the Java generated code and provides a mapping that can be directly used via the `%match` construct
-   a Java library to make strategies applicable on an EMF data-structure

The -EMF part of the project is composed of

-   two generators (*emf-generate-mappings* and *emf-generate-xmi*),
-   a specialized introspector distributed as a library (`tom.library.emf` package) and the ecore.tom mapping (package).

Mappings and XMI generators
---------------------------

The mapping generator takes a package name (which is the generated interface that extends from EPackage) as argument and produces Tom mappings. The XMI generator has also a package name as argument and generates the corresponding XMI file.

Ecore Containment Introspector
------------------------------

The EcoreContainmentIntrospector is an EMF-specialized introspector . It implements the Introspector interface and has to be used in `visit` method when working with strategies. They can be used as usually, however it is important to know that only composition associations are followed. Indeed, this allows not to have cycles during visiting.

The application of a strategy on a model can be done as follows:

``` tom
strategy.visit(model, new EcoreContainmentIntrospector());
```

Generated mappings
==================

In this section, we detail how mappings are generated and what is generated. The generator uses reflexivity in order to generates the mappings.

It is able to find all EClassifier (EClass and EDataType -therefore EEnum-) and, for each one, it generates the corresponding typeterm and operator, as in the following example:

``` tom
%typeterm EObject {
  implement { org.eclipse.emf.ecore.EObject }
  is_sort(t) { $t instanceof org.eclipse.emf.ecore.EObject }
  equals(l1,l2) { $l1.equals($l2) }
}
%op EObject EObject() {
  is_fsym(t) { $t instanceof org.eclipse.emf.ecore.EObject }
  make() { construct((org.eclipse.emf.ecore.EObject)org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.create((EClass)org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getEClassifier("EObject")),new Object[]{}) }
}
```

At the same way, each EStructuralFeature -if there are more than one value that may appear in a valid instance- leads to the generation of a mapping and a list operator as follow :

``` tom
%typeterm EObjectEList {
  implement { EList<org.eclipse.emf.ecore.EObject> }
  is_sort(t) { $t instanceof EList<?> && (((EList<org.eclipse.emf.ecore.EObject>)$t).size() == 0 || (((EList<org.eclipse.emf.ecore.EObject>)$t).size()>0 && ((EList<org.eclipse.emf.ecore.EObject>)$t).get(0) instanceof org.eclipse.emf.ecore.EObject)) }
  equals(l1,l2) { $l1.equals($l2) }
}
%oparray EObjectEList EObjectEList(EObject*) {
  is_fsym(t) { $t instanceof EList<?> && ($t.size() == 0 || ($t.size()>0 && $t.get(0) instanceof org.eclipse.emf.ecore.EObject)) }
  make_empty(n) { new BasicEList<org.eclipse.emf.ecore.EObject>($n) }
  make_append(e,l) { append($e,$l) }
  get_element(l,n) { $l.get($n) }
  get_size(l)      { $l.size() }
}
```

The Ecore mappings
==================

We distribute Tom with few libraries and mappings. One of them is the *ecore.tom* mapping which is the mapping of the meta-model Ecore itself and which we generated with the *emf-generate-mappings* program. You can obviously generate it again by running the following command:

`emf-generate-mappings org.eclipse.emf.ecore.EcorePackage`

We used this mapping in the *emf-generate-xmi* program (source available in src/tom/emf/EcoreMappingToXMI.t) which can be a good example if you plan to use it for your own application. You will find a table which gives the implemented Java type for each Tom typeterm. You should be careful, since there are some confusing mappings (EObject / Entry / Entry1 /…):

| **Tom**            | !width=“280pt”|**Ecore**                   |     | !width=“140pt”|**Tom**  | !width=“280pt”|**Ecore**                              |     | !width=“140pt”|**Tom** | !width=“280pt”|**Ecore**                          |
|--------------------|--------------------------------------------|-----|-------------------------|-------------------------------------------------------|-----|------------------------|---------------------------------------------------|
| EAttribute         | org.eclipse.emf.ecore.EAttribute           |     | EAttributeEList         | EList&lt;org.eclipse.emf.ecore.EAttribute&gt;         |     | ResourceSet            | org.eclipse.emf.ecore.resource.ResourceSet        |
| EAnnotation        | org.eclipse.emf.ecore.EAnnotation          |     | EAnnotationEList        | EList&lt;org.eclipse.emf.ecore.EAnnotation&gt;        |     | Entry1                 | org.eclipse.emf.ecore.util.FeatureMap.Entry       |
| EStructuralFeature | org.eclipse.emf.ecore.EStructuralFeature   |     | EStructuralFeatureEList | EList&lt;org.eclipse.emf.ecore.EStructuralFeature&gt; |     | FeatureMap             | org.eclipse.emf.ecore.util.FeatureMap             |
| Entry              | EObject                                    |     | EntryEList              | EList<EObject>                                        |     | TreeIterator           | org.eclipse.emf.common.util.TreeIterator&lt;?&gt; |
| EDataType          | org.eclipse.emf.ecore.EDataType            |     | EEnum                   | org.eclipse.emf.ecore.EEnum                           |     | DiagnosticChain        | org.eclipse.emf.common.util.DiagnosticChain       |
| EModelElement      | org.eclipse.emf.ecore.EModelElement        |     | Enumerator              | org.eclipse.emf.common.util.Enumerator                |     | Resource               | org.eclipse.emf.ecore.resource.Resource           |
| EObject            | org.eclipse.emf.ecore.EObject              |     | EObjectEList            | EList&lt;org.eclipse.emf.ecore.EObject&gt;            |     | bytearray              | byte\[\]                                          |
| EEnumLiteral       | org.eclipse.emf.ecore.EEnumLiteral         |     | EEnumLiteralEList       | EList&lt;org.eclipse.emf.ecore.EEnumLiteral&gt;       |     | byte                   | byte                                              |
| EClassifier        | org.eclipse.emf.ecore.EClassifier          |     | EClassifierEList        | EList&lt;org.eclipse.emf.ecore.EClassifier&gt;        |     | Byte                   | java.lang.Byte                                    |
| ETypeParameter     | org.eclipse.emf.ecore.ETypeParameter       |     | ETypeParameterEList     | EList&lt;org.eclipse.emf.ecore.ETypeParameter&gt;     |     | Character              | java.lang.Character                               |
| EGenericType       | org.eclipse.emf.ecore.EGenericType         |     | EGenericTypeEList       | EList&lt;org.eclipse.emf.ecore.EGenericType&gt;       |     | Date                   | java.util.Date                                    |
| EReference         | org.eclipse.emf.ecore.EReference           |     | ENamedElement           | org.eclipse.emf.ecore.ENamedElement                   |     | Double                 | java.lang.Double                                  |
| EPackage           | org.eclipse.emf.ecore.EPackage             |     | EPackageEList           | EList&lt;org.eclipse.emf.ecore.EPackage&gt;           |     | Float                  | java.lang.Float                                   |
| EClass             | org.eclipse.emf.ecore.EClass               |     | EClassEList             | EList&lt;org.eclipse.emf.ecore.EClass&gt;             |     | Integer                | java.lang.Integer                                 |
| EParameter         | org.eclipse.emf.ecore.EParameter           |     | EParameterEList         | EList&lt;org.eclipse.emf.ecore.EParameter&gt;         |     | Class                  | java.lang.Class&lt;?&gt;                          |
| EOperation         | org.eclipse.emf.ecore.EOperation           |     | EOperationEList         | EList&lt;org.eclipse.emf.ecore.EOperation&gt;         |     | Long                   | java.lang.Long                                    |
| EList              | org.eclipse.emf.common.util.EList&lt;?&gt; |     | BigInteger              | java.math.BigInteger                                  |     | short                  | short                                             |
| ETypedElement      | org.eclipse.emf.ecore.ETypedElement        |     | Boolean                 | java.lang.Boolean                                     |     | Short                  | java.lang.Short                                   |
| EFactory           | org.eclipse.emf.ecore.EFactory             |     | BigDecimal              | java.math.BigDecimal                                  |     |                        |                                                   |

