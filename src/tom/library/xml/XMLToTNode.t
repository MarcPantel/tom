/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2012, INPL, INRIA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the INRIA nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * INRIA, Nancy, France
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/

package tom.library.xml;

import java.util.*;

import java.io.*;
import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLToTNode {

  %include{ adt/tnode/TNode.tom }

  public static final String XMLTOTNODE = "XMLToTNode";
  public static final String UNDEF = "UNDEF";

  private TNode nodeTerm = null;
  private boolean deleteWhiteSpaceNodes = false;
  private Hashtable<TNode,Collection<Element> > ht_Nodes =
    new Hashtable<TNode,Collection<Element> >();

  protected Collection<Element> getNodes(TNode key) {
    return ht_Nodes.get(key);
  }

  public void setDeletingWhiteSpaceNodes(boolean b_d) {
    deleteWhiteSpaceNodes=b_d;
  }

  public XMLToTNode() { /* Beware ! nodeTerm is null */ }

  public XMLToTNode(InputStream is) {
    convert(is);
  }

  public XMLToTNode(String filename) {
    convert(filename);
  }

  /**
   * Get the nodeTerm attribute
   *
   * @return nodeTerm
   */
  public TNode getTNode() {
    return nodeTerm;
  }

  /**
   * Set up nodeTerm by providing a filename
   *
   * @param filename the filename
   */
  public void convert(String filename) {
    nodeTerm = xmlToTNode(convertToNode(filename));
  }

  /**
   * Set up nodeTerm by providing an InputStream
   *
   * @param is the InputStream
   */
  public void convert(InputStream is) {
    nodeTerm = xmlToTNode(convertToNode(is));
  }

  /**
   * Set up nodeTerm by providing a Node
   *
   * @param node the Node
   */
  public void convert(Node node) {
    nodeTerm = xmlToTNode(node);
  }

  /**
   * Returns an XML file to a Node representation
   *
   * @param filename the file to convert
   * @return a Node representation of the XML file
   */
  public Node convertToNode(String filename) {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      //documentFactory.setValidating(true);
      DocumentBuilder db = documentFactory.newDocumentBuilder();
      return db.parse(filename);
    } catch (SAXException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (IOException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (ParserConfigurationException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns a Node by providing an input stream
   *
   * @param is the input stream
   * @return a Node
   */
  public Node convertToNode(InputStream is) {
    try {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      return db.parse(is);
    } catch (SAXException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (IOException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (ParserConfigurationException e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      System.err.println(XMLToTNode.XMLTOTNODE + ": "+ e.getClass() + ": " + e.getMessage());
      //e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns an ATerm representation of a NodeList
   *
   * @param list the NodeList
   * @return an ATerm representation of the NodeList
   */
  public TNodeList nodeListToAterm(NodeList list) {
    TNodeList res = `concTNode();
    for(int i=list.getLength()-1 ; i>=0 ; i--) {
      TNode elt = xmlToTNode(list.item(i));
      if(elt != null) {
        res = `concTNode(elt,res*);
      }
    }
    return res;
  }

  /**
   * Returns an ATerm representation of a NamedNodeMap
   *
   * @param list the NamedNodeMap
   * @return an ATerm representation of the NamedNodeMap
   */
  public TNodeList namedNodeMapToAterm(NamedNodeMap list) {
    TNodeList res = `concTNode();
    for(int i=list.getLength()-1 ; i>=0 ; i--) {
      TNode elt = xmlToTNode(list.item(i));
      if(elt != null) {
        res = `concTNode(elt,res*);
      }
    }
    return res;
  }

  /**
   * Returns a TNode by providing a Node
   *
   * @param node the Node to normalize and convert
   * @return a TNode obtained from the Node
   */
  public TNode xmlToTNode(Node node) {
    if ( node == null ) { // Nothing to do
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
      System.err.println("We shouldn't find DocumentFragment in a freshly-parsed document");
      throw new RuntimeException("We shouldn't find DocumentFragment in a freshly-parsed document");
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
      throw new RuntimeException("The type of Node is unknown");
    } // switch
  }

  private TNode makeDocumentNode (Document doc){
    TNode doctype = xmlToTNode(doc.getDoctype());
    if (doctype == null) {
      doctype = `TextNode("");
    }
    TNode elem = xmlToTNode(doc.getDocumentElement());
    return `DocumentNode(doctype,elem);
  }

  private TNode makeDocumentTypeNode (DocumentType doctype) {
    String name=doctype.getName();
    name = (name == null ? XMLToTNode.UNDEF : name);
    String publicId = doctype.getPublicId();
    publicId = (publicId == null ? XMLToTNode.UNDEF : publicId);
    String systemId = doctype.getSystemId();
    systemId = (systemId == null ? XMLToTNode.UNDEF : systemId);
    String internalSubset = doctype.getInternalSubset();
    internalSubset = (internalSubset == null ? XMLToTNode.UNDEF : internalSubset);
    TNodeList entitiesList = namedNodeMapToAterm(doctype.getEntities());
    TNodeList notationsList = namedNodeMapToAterm(doctype.getNotations());
    return `DocumentTypeNode(name,publicId,systemId,
           internalSubset,entitiesList,notationsList);
  }

  private TNode makeElementNode(Element elem) {
    TNodeList attrList  = namedNodeMapToAterm(elem.getAttributes());
      //System.out.println("attrList = " + attrList);
    attrList = sortAttributeList(attrList);
      //System.out.println("sorted attrList = " + attrList);
    TNodeList childList = nodeListToAterm(elem.getChildNodes());
    TNode result = `ElementNode(elem.getNodeName(),attrList,childList);
    Collection<Element> curCol = ht_Nodes.get(elem);
    if (curCol==null) {
      curCol = new ArrayList<Element>();
    }
    curCol.add(elem);
    ht_Nodes.put(result,curCol);
    return result;
  }

  public TNodeList sortAttributeList(TNodeList attrList) {
    TNodeList res = `concTNode();
    while(!attrList.isEmptyconcTNode()) {
      res = insertSortedAttribute(attrList.getHeadconcTNode(),res);
      attrList = attrList.getTailconcTNode();
    }
    return res;
  }

  private TNodeList insertSortedAttribute(TNode elt, TNodeList list) {
    %match(elt, list) {
      AttributeNode[Name=_], concTNode() -> {
        return `concTNode(elt,list*);
      }

      AttributeNode[Name=name1], concTNode(head@AttributeNode[Name=name2],tail*) -> {
        if(`name1.compareTo(`name2) >= 0) {
          TNodeList tl = insertSortedAttribute(elt,`tail*);
          return `concTNode(head,tl*);
        } else {
          return `concTNode(elt,list*);
        }
      }
    }
    System.err.println("insertSortedAttribute: Strange case");
    return list;
  }

  private TNode makeAttributeNode(Attr attr) {
    String specif = (attr.getSpecified() ? "true" : "false");
    return `AttributeNode(attr.getNodeName(),specif,attr.getNodeValue());
  }

  private TNode makeTextNode(Text text) {
    if (!deleteWhiteSpaceNodes) {
      return `TextNode(text.getData());
    }
    if (!text.getData().trim().equals("")) {
      return `TextNode(text.getData());
    }
    return null;
  }

  private TNode makeEntityNode(Entity e) {
    String nn= e.getNotationName();
    nn = (nn == null ? XMLToTNode.UNDEF : nn);
    String publicId = e.getPublicId();
    publicId = (publicId == null ? XMLToTNode.UNDEF : publicId);
    String systemId = e.getSystemId();
    systemId = (systemId == null ? XMLToTNode.UNDEF : systemId);
    return `EntityNode(nn,publicId,systemId);
  }

  private TNode makeEntityReferenceNode(EntityReference er) {
    TNodeList list = nodeListToAterm(er.getChildNodes());
    return `EntityReferenceNode(er.getNodeName(),list);
  }

  private TNode makeNotationNode(Notation notation) {
    String publicId = notation.getPublicId();
    publicId = (publicId == null ? XMLToTNode.UNDEF : publicId);
    String systemId = notation.getSystemId();
    systemId = (systemId == null ? XMLToTNode.UNDEF : systemId);
    return `NotationNode(publicId,systemId);
  }

  private TNode makeCommentNode(Comment comment) {
    return `CommentNode(comment.getData());
  }

  private TNode makeCDATASectionNode(CDATASection cdata) {
    return `CDATASectionNode(cdata.getData());
  }

  private TNode makeProcessingInstructionNode(ProcessingInstruction instr) {
    return `ProcessingInstructionNode(instr.getTarget(),instr.getData());
  }

}
