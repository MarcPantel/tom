 /*
  
    TOM - To One Matching Compiler

    Copyright (C) 2000-2003  LORIA (CNRST, INPL, INRIA, UHP, U-Nancy 2)
			     Nancy, France.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

    Pierre-Etienne Moreau	e-mail: Pierre-Etienne.Moreau@loria.fr

*/

package jtom.runtime.xml;

import java.io.*;
import jtom.runtime.xml.adt.*;
import aterm.*;
import aterm.pure.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLToATerm {
  
  public Object tom_get_fun_sym_String( String t) { return t; }public boolean tom_cmp_fun_sym_String(Object s1, Object s2) { return  s1.equals(s2); }public Object tom_get_subterm_String( String t,  int n) { return null; }public boolean tom_terms_equal_String(Object t1, Object t2) { return t1.equals(t2); }public  int tom_get_fun_sym_int( int t) { return t; }public boolean tom_cmp_fun_sym_int( int s1,  int s2) { return  (s1 == s2); }public Object tom_get_subterm_int( int t,  int n) { return null; }public boolean tom_terms_equal_int( int t1,  int t2) { return (t1 == t2); }public  double tom_get_fun_sym_double( double t) { return t; }public boolean tom_cmp_fun_sym_double( double s1,  double s2) { return  (s1 == s2); }public Object tom_get_subterm_double( double t,  int n) { return null; }public boolean tom_terms_equal_double( double t1,  double t2) { return (t1 == t2); }public Object tom_get_fun_sym_ATerm( aterm.ATerm t) { return ((t instanceof ATermAppl)?((ATermAppl)t).getAFun():null); }public boolean tom_cmp_fun_sym_ATerm(Object s1, Object s2) { return  s1==s2; }public Object tom_get_subterm_ATerm( aterm.ATerm t,  int n) { return (((ATermAppl)t).getArgument(n)); }public boolean tom_terms_equal_ATerm(Object t1, Object t2) { return t1.equals(t2); }public Object tom_get_fun_sym_ATermList( aterm.ATermList t) { return ((t instanceof ATermList)?getTNodeFactory().getPureFactory().makeAFun("conc",1,false):null); }public boolean tom_cmp_fun_sym_ATermList(Object s1, Object s2) { return  s1==s2; }public boolean tom_terms_equal_ATermList(Object t1, Object t2) { return t1.equals(t2); }public Object tom_get_head_ATermList( aterm.ATermList l) { return l.getFirst(); }public  aterm.ATermList tom_get_tail_ATermList( aterm.ATermList l) { return l.getNext(); }public boolean tom_is_empty_ATermList( aterm.ATermList l) { return l.isEmpty(); }public Object tom_get_fun_sym_TNode( TNode t) { return null; }public boolean tom_cmp_fun_sym_TNode(Object s1, Object s2) { return  false; }public Object tom_get_subterm_TNode( TNode t,  int n) { return null; }public boolean tom_terms_equal_TNode(Object t1, Object t2) { return t1.equals(t2); }public boolean tom_is_fun_sym_CommentNode( TNode t) { return  (t!= null) && t.isCommentNode(); }public  TNode tom_make_CommentNode( String t0) { return  getTNodeFactory().makeTNode_CommentNode(t0); }public  String tom_get_slot_CommentNode_data( TNode t) { return  t.getData(); }public boolean tom_is_fun_sym_ProcessingInstructionNode( TNode t) { return  (t!= null) && t.isProcessingInstructionNode(); }public  TNode tom_make_ProcessingInstructionNode( String t0,  String t1) { return  getTNodeFactory().makeTNode_ProcessingInstructionNode(t0, t1); }public  String tom_get_slot_ProcessingInstructionNode_target( TNode t) { return  t.getTarget(); }public  String tom_get_slot_ProcessingInstructionNode_data( TNode t) { return  t.getData(); }public boolean tom_is_fun_sym_TextNode( TNode t) { return  (t!= null) && t.isTextNode(); }public  TNode tom_make_TextNode( String t0) { return  getTNodeFactory().makeTNode_TextNode(t0); }public  String tom_get_slot_TextNode_data( TNode t) { return  t.getData(); }public boolean tom_is_fun_sym_CDATASectionNode( TNode t) { return  (t!= null) && t.isCDATASectionNode(); }public  TNode tom_make_CDATASectionNode( String t0) { return  getTNodeFactory().makeTNode_CDATASectionNode(t0); }public  String tom_get_slot_CDATASectionNode_data( TNode t) { return  t.getData(); }public boolean tom_is_fun_sym_DocumentNode( TNode t) { return  (t!= null) && t.isDocumentNode(); }public  TNode tom_make_DocumentNode( TNode t0,  TNode t1) { return  getTNodeFactory().makeTNode_DocumentNode(t0, t1); }public  TNode tom_get_slot_DocumentNode_docType( TNode t) { return  t.getDocType(); }public  TNode tom_get_slot_DocumentNode_docElem( TNode t) { return  t.getDocElem(); }public boolean tom_is_fun_sym_ElementNode( TNode t) { return  (t!= null) && t.isElementNode(); }public  TNode tom_make_ElementNode( String t0,  TNodeList t1,  TNodeList t2) { return  getTNodeFactory().makeTNode_ElementNode(t0, t1, t2); }public  String tom_get_slot_ElementNode_name( TNode t) { return  t.getName(); }public  TNodeList tom_get_slot_ElementNode_attrList( TNode t) { return  t.getAttrList(); }public  TNodeList tom_get_slot_ElementNode_childList( TNode t) { return  t.getChildList(); }public boolean tom_is_fun_sym_AttributeNode( TNode t) { return  (t!= null) && t.isAttributeNode(); }public  TNode tom_make_AttributeNode( String t0,  String t1,  TNode t2) { return  getTNodeFactory().makeTNode_AttributeNode(t0, t1, t2); }public  String tom_get_slot_AttributeNode_name( TNode t) { return  t.getName(); }public  String tom_get_slot_AttributeNode_specified( TNode t) { return  t.getSpecified(); }public  TNode tom_get_slot_AttributeNode_child( TNode t) { return  t.getChild(); }public boolean tom_is_fun_sym_DocumentTypeNode( TNode t) { return  (t!= null) && t.isDocumentTypeNode(); }public  TNode tom_make_DocumentTypeNode( String t0,  String t1,  String t2,  String t3,  TNodeList t4,  TNodeList t5) { return  getTNodeFactory().makeTNode_DocumentTypeNode(t0, t1, t2, t3, t4, t5); }public  String tom_get_slot_DocumentTypeNode_name( TNode t) { return  t.getName(); }public  String tom_get_slot_DocumentTypeNode_publicId( TNode t) { return  t.getPublicId(); }public  String tom_get_slot_DocumentTypeNode_systemId( TNode t) { return  t.getSystemId(); }public  String tom_get_slot_DocumentTypeNode_internalSubset( TNode t) { return  t.getInternalSubset(); }public  TNodeList tom_get_slot_DocumentTypeNode_entities( TNode t) { return  t.getEntities(); }public  TNodeList tom_get_slot_DocumentTypeNode_notations( TNode t) { return  t.getNotations(); }public boolean tom_is_fun_sym_EntityReferenceNode( TNode t) { return  (t!= null) && t.isEntityReferenceNode(); }public  TNode tom_make_EntityReferenceNode( String t0,  TNodeList t1) { return  getTNodeFactory().makeTNode_EntityReferenceNode(t0, t1); }public  String tom_get_slot_EntityReferenceNode_name( TNode t) { return  t.getName(); }public  TNodeList tom_get_slot_EntityReferenceNode_childList( TNode t) { return  t.getChildList(); }public boolean tom_is_fun_sym_EntityNode( TNode t) { return  (t!= null) && t.isEntityNode(); }public  TNode tom_make_EntityNode( String t0,  String t1,  String t2) { return  getTNodeFactory().makeTNode_EntityNode(t0, t1, t2); }public  String tom_get_slot_EntityNode_notationName( TNode t) { return  t.getNotationName(); }public  String tom_get_slot_EntityNode_publicId( TNode t) { return  t.getPublicId(); }public  String tom_get_slot_EntityNode_systemId( TNode t) { return  t.getSystemId(); }public boolean tom_is_fun_sym_NotationNode( TNode t) { return  (t!= null) && t.isNotationNode(); }public  TNode tom_make_NotationNode( String t0,  String t1) { return  getTNodeFactory().makeTNode_NotationNode(t0, t1); }public  String tom_get_slot_NotationNode_publicId( TNode t) { return  t.getPublicId(); }public  String tom_get_slot_NotationNode_systemId( TNode t) { return  t.getSystemId(); }public Object tom_get_fun_sym_TNodeList( TNodeList t) { return null; }public boolean tom_cmp_fun_sym_TNodeList(Object s1, Object s2) { return  false; }public boolean tom_terms_equal_TNodeList(Object t1, Object t2) { return t1.equals(t2); }public Object tom_get_head_TNodeList( TNodeList l) { return l.getHead(); }public  TNodeList tom_get_tail_TNodeList( TNodeList l) { return l.getTail(); }public boolean tom_is_empty_TNodeList( TNodeList l) { return l.isEmpty(); }public boolean tom_is_fun_sym_concTNode( TNodeList t) { return (t!= null) && t.isSortTNodeList(); }public Object tom_make_empty_concTNode() { return getTNodeFactory().makeTNodeList(); }public  TNodeList tom_make_insert_concTNode( TNode e,  TNodeList l) { return getTNodeFactory().makeTNodeList(e,l); } public  TNodeList tom_reverse_concTNode( TNodeList l) {     TNodeList result = ( TNodeList)tom_make_empty_concTNode();     while(!tom_is_empty_TNodeList(l) ) {       result = ( TNodeList)tom_make_insert_concTNode(( TNode)tom_get_head_TNodeList(l),result);       l = ( TNodeList)tom_get_tail_TNodeList(l);     }     return result;   }  public  TNodeList tom_insert_list_concTNode( TNodeList l1,  TNodeList l2) {    if(tom_is_empty_TNodeList(l1)) {     return l2;    } else if(tom_is_empty_TNodeList(l2)) {     return l1;    } else if(tom_is_empty_TNodeList(( TNodeList)tom_get_tail_TNodeList(l1))) {     return ( TNodeList)tom_make_insert_concTNode(( TNode)tom_get_head_TNodeList(l1),l2);    } else {      return ( TNodeList)tom_make_insert_concTNode(( TNode)tom_get_head_TNodeList(l1),tom_insert_list_concTNode(( TNodeList)tom_get_tail_TNodeList(l1),l2));    }   }  public  TNodeList tom_get_slice_concTNode( TNodeList begin,  TNodeList end) {     TNodeList result = ( TNodeList)tom_make_empty_concTNode();     while(!tom_terms_equal_TNodeList(begin,end)) {       result = ( TNodeList)tom_make_insert_concTNode(( TNode)tom_get_head_TNodeList(begin),result);       begin = ( TNodeList)tom_get_tail_TNodeList(begin);      }     result = ( TNodeList)tom_reverse_concTNode(result);     return result;   } public boolean tom_is_fun_sym_emptyTNodeList( TNodeList t) { return  (t!= null) && t.isEmpty(); }public  TNodeList tom_make_emptyTNodeList() { return getTNodeFactory().makeTNodeList(); }public boolean tom_is_fun_sym_manyTNodeList( TNodeList t) { return  (t!= null) && t.isMany(); }public  TNodeList tom_make_manyTNodeList( TNode e,  TNodeList l) { return getTNodeFactory().makeTNodeList(e,l); }public  TNode tom_get_slot_manyTNodeList_head( TNodeList t) { return  t.getHead(); }public  TNodeList tom_get_slot_manyTNodeList_tail( TNodeList t) { return  t.getTail(); }  
	
  private TNodeFactory nodesFactory = null;
  private TNode nodeTerm = null;
  private boolean deleteWhiteSpaceNodes = false;
  
  public void setDeletingWhiteSpaceNodes(boolean b_d) {
    deleteWhiteSpaceNodes=b_d;
  }

  private TNodeFactory getTNodeFactory() {
    return nodesFactory;
  }

  public XMLToATerm () {
    nodesFactory = new TNodeFactory(new PureFactory());
  }

  public XMLToATerm(TNodeFactory factory) {
    nodesFactory = factory;
  }

  public XMLToATerm(String filename) {
    this();
    convert(filename);
  }

  public XMLToATerm(TNodeFactory factory,String filename) {
    this(factory);
    convert(filename);
  }

  public ATerm getATerm() {
    return nodeTerm;
  }

  public void convert(String filename) {
    try {
      DocumentBuilder db = DocumentBuilderFactory.newInstance(
	  					 ).newDocumentBuilder();
      nodeTerm = xmlToATerm(db.parse(filename));
    } catch (SAXException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  } 
 
  public void convert(Node node) {
    nodeTerm = xmlToATerm(node);
  }
  
  public Node convertToNode(String filename) {
    try {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      return db.parse(filename);
    } catch (SAXException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    System.out.println("We shouldn't be there...");
    return null;
  }

  public TNodeList nodeListToAterm(NodeList n) {
    TNodeList nt_result = tom_make_emptyTNodeList() ;
    for (int it=0;it<n.getLength();it++)
      nt_result = tom_make_insert_concTNode(xmlToATerm(n .item(it)),tom_insert_list_concTNode(nt_result,( TNodeList) tom_make_empty_concTNode())) ;
    return nt_result;
  }
	
  public TNode xmlToATerm(Node node) {
    if ( node == null ) { // Nothing to do
      //System.out.println("\n\nFound a null node\n\n");
      return null;
    }
    node.normalize();
    int type = node.getNodeType();
    switch (type) {
    case Node.ATTRIBUTE_NODE:
      //The node is an Attr.
      return makeAttributeNode((Attr) node);
      //break;
    case Node.CDATA_SECTION_NODE:
      //The node is a CDATASection.
      return makeCDATASectionNode((CDATASection) node);
      //break;
    case Node.COMMENT_NODE:
      //The node is a Comment.
      return makeCommentNode((Comment) node);
      //break;
    case Node.DOCUMENT_FRAGMENT_NODE:
      //The node is a DocumentFragment.
      System.out.println("We shouldn't find DocumentFragment in a freshly-parsed document");
      //break;
    case Node.DOCUMENT_NODE:
      //The node is a Document.
      return makeDocumentNode((Document) node);
      //break;
    case Node.DOCUMENT_TYPE_NODE:
      //The node is a DocumentType.
      return makeDocumentTypeNode((DocumentType) node);
      //break;
    case Node.ELEMENT_NODE:
      //The node is an Element.
      return makeElementNode((Element) node);
      //break;
    case Node.ENTITY_NODE:
      //The node is an Entity.
      return makeEntityNode((Entity) node);
      //break;
    case Node.ENTITY_REFERENCE_NODE:
      return makeEntityReferenceNode((EntityReference) node);
      //The node is an EntityReference.
      //break;
    case Node.NOTATION_NODE:
      //The node is a Notation.
      return makeNotationNode((Notation) node);
      //break;
    case Node.PROCESSING_INSTRUCTION_NODE:
      //The node is a ProcessingInstruction.
      return makeProcessingInstructionNode((ProcessingInstruction) node);
      //break;
    case Node.TEXT_NODE:
      return makeTextNode((Text) node);
      //break;
    default : 
      System.err.println("The type of Node is unknown");
      System.exit(1);
    } // switch
    System.err.println("We should not be here");
    return null;
  }

  private TNode makeDocumentNode (Document doc){
    TNode doctype = xmlToATerm(doc.getDoctype());
    if (doctype == null) {
      doctype = tom_make_TextNode( "") ;
    }
    TNode elem = xmlToATerm(doc.getDocumentElement());
    return tom_make_DocumentNode(doctype,elem) ;
  }
    
  private TNode makeDocumentTypeNode (DocumentType doctype) {
    String name=doctype.getName();
    name = (name == null ? "UNDEF" : name);
    String publicId = doctype.getPublicId();
    publicId = (publicId == null ? "UNDEF" : publicId);
    String systemId = doctype.getSystemId();
    systemId = (systemId == null ? "UNDEF" : systemId);
    String internalSubset = doctype.getInternalSubset();
    internalSubset = (internalSubset == null ? "UNDEF" : internalSubset);

    TNodeList entitiesList= ( TNodeList) tom_make_empty_concTNode() ;
    NamedNodeMap entities = doctype.getEntities();
    for(int i=0; i < entities.getLength(); i++)
      entitiesList = tom_insert_list_concTNode(entitiesList,tom_make_insert_concTNode(xmlToATerm(entities .item(i)),( TNodeList) tom_make_empty_concTNode())) ;

    TNodeList notationsList = ( TNodeList) tom_make_empty_concTNode() ;
    NamedNodeMap notations = doctype.getNotations();
    for(int i=0; i < notations.getLength(); i++) 
      notationsList = tom_insert_list_concTNode(notationsList,tom_make_insert_concTNode(xmlToATerm(notations .item(i)),( TNodeList) tom_make_empty_concTNode())) ;

    return tom_make_DocumentTypeNode(name,publicId,systemId,internalSubset,entitiesList,notationsList)
 ;
  }
    
  private TNode makeElementNode(Element elem) {
    TNodeList attrList=( TNodeList) tom_make_empty_concTNode() ;
    TNode n;
    NamedNodeMap attrs = elem.getAttributes();
    for(int i=0; i < attrs.getLength(); i++) {
      n = xmlToATerm(attrs.item(i));
      if (n!=null) attrList = tom_insert_list_concTNode(attrList,tom_make_insert_concTNode(n,( TNodeList) tom_make_empty_concTNode())) ;
    }
    TNodeList childList=( TNodeList) tom_make_empty_concTNode() ;
    NodeList nodes = elem.getChildNodes();
    for(int i = 0; i < nodes.getLength(); i++) {
      n = xmlToATerm(nodes.item(i));
      if (n!=null) childList = tom_insert_list_concTNode(childList,tom_make_insert_concTNode(n,( TNodeList) tom_make_empty_concTNode())) ;
    }
    return tom_make_ElementNode(elem .getNodeName(),attrList,childList) ;
  }

  private TNode makeAttributeNode(Attr attr) {
    String specif = (attr.getSpecified() ? "true" : "false");
    return tom_make_AttributeNode(attr .getNodeName(),specif,tom_make_TextNode(attr .getNodeValue()))

 ;
  }

  private TNode makeTextNode(Text text) {
    if (!deleteWhiteSpaceNodes)
      return tom_make_TextNode(text .getData()) ;
    if (!text.getData().trim().equals(""))
      return tom_make_TextNode(text .getData()) ;
    return null;
  }
  
  private TNode makeEntityNode(Entity e) {
    String nn= e.getNotationName();
    nn = (nn == null ? "UNDEF" : nn);
    String publicId = e.getPublicId();
    publicId = (publicId == null ? "UNDEF" : publicId);
    String systemId = e.getSystemId();
    systemId = (systemId == null ? "UNDEF" : systemId);
    return tom_make_EntityNode(nn,publicId,systemId) ;
  }

  private TNode makeEntityReferenceNode(EntityReference er) {
    TNodeList list=( TNodeList) tom_make_empty_concTNode() ;
    TNode n;
    NodeList nodes = er.getChildNodes();
    for(int i = 0; i < nodes.getLength(); i++) {
      n = xmlToATerm(nodes.item(i));
      if (n!=null) list = tom_insert_list_concTNode(list,tom_make_insert_concTNode(n,( TNodeList) tom_make_empty_concTNode())) ;
    }
    return tom_make_EntityReferenceNode(er .getNodeName(),list) ;
  }

  private TNode makeNotationNode(Notation notation) {
    String publicId = notation.getPublicId();
    publicId = (publicId == null ? "UNDEF" : publicId);
    String systemId = notation.getSystemId();
    systemId = (systemId == null ? "UNDEF" : systemId);
    return tom_make_NotationNode(publicId,systemId) ;
  }

  private TNode makeCommentNode(Comment comment) {
    return tom_make_CommentNode(comment .getData()) ;
  }
  
  private TNode makeCDATASectionNode(CDATASection cdata) {
    return tom_make_CDATASectionNode(cdata .getData()) ;
  }

  private TNode makeProcessingInstructionNode(ProcessingInstruction instr) {
    return tom_make_ProcessingInstructionNode(instr .getTarget(),instr .getData()) ;
  }

}
