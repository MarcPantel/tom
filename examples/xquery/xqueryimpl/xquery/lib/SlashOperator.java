//Source file: C:\\document\\codegen\\xquery\\lib\\SlashOperator.java

package xquery.lib;

import xquery.lib.data.Sequence;
import xquery.lib.data.Item;

import org.w3c.dom.*;
import xquery.lib.data.type.*;

import java.util.*;

public class SlashOperator extends PathOperator 
{
   
  /**
   * @roseuid 4110D94E030A
   */
  public SlashOperator() 
  {
	super();
  }
   
  /**
   * @param nodetester
   * @param predicateList
   * @param nextOperator
   * @roseuid 4110D8F003CD
   */
  public SlashOperator(NodeTester nodetester, NodePredicateList predicateList) 
  {
	super(nodetester, predicateList);
  }
   
  /**
   * @param axe
   * @param nodetester
   * @param nodepredicateList
   * @param nextOperator
   * @roseuid 4110B632008F
   */
  public SlashOperator(PathAxe axe, NodeTester nodetester, NodePredicateList predicateList) 
  {
	super(axe, nodetester, predicateList);
  }
   
  /**
   * @param obj
   * @return xquery.lib.data.Sequence
   * @roseuid 410E10B60124
   */
  public Sequence run(Sequence input) throws XQueryTypeException 
  {
	int axeType = axe.getAxeType();
	
	switch (axeType) {
	case PathAxe.CHILD:
	  return runChild(input);
	case PathAxe.ATTRIBUTE:
	  return runAttribute(input);
	case PathAxe.PARENT:
	  return runParent(input);
	case PathAxe.SELF:
	  return runSelf(input);
	default:
	  return new Sequence();
	}
  }
   

  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runChild(Sequence input) throws XQueryTypeException 
  {
	//	System.out.println("SlashOperator: runChildsequence: input:" + input.size());
	Sequence result = new Sequence();
	Enumeration enum=input.elements(); 

	while (enum.hasMoreElements()) {
	  Object obj=enum.nextElement(); 
	  if (obj instanceof Node) {
		result.add(runChild((Node)obj));
	  }
	  else if (obj instanceof Item) {
		result.add(runChild((Item)obj));
	  }
	}
	
	//	System.out.println("SlashOperator: runChildsequence: result:" + result.size());
	return result;
  }


  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runChild(Item item) throws XQueryTypeException 
  {
	if (item.isNode()) {
	  return runChild(item.getNode());
	}
	else {
	  return new Sequence();
	}
  }


  
  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runChild(Node node) throws XQueryTypeException 
  {
	Sequence seq=new Sequence();
	if (node.hasChildNodes()) {
	  //	  System.out.println("deo hieu the nao, co child ro rang kia ma");

	  NodeList nodelist=node.getChildNodes();
	  for(int i=0;i < nodelist.getLength(); i++) {
		// System.out.println("i: " + i);		
		Node childnode = nodelist.item(i);
		if (doTest(childnode) && doFilter(childnode, i+1)) {  // i is current position in child list
		  //		  System.out.println("slashOper; runchildNode: test OK");
		  seq.add(childnode); // index 1
		}
// 		else {
// 		  System.out.println("slashOper; runchildNode: test failed");
		  
// 		}
		
	  }
	} 

	//	System.out.println("SlashOperator: runChildNode: result:" + seq.size());
	return seq;
  }



  /**
   * @param seq
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runParent(Sequence seq) throws XQueryTypeException 
  {
	Sequence result = new Sequence();
	Enumeration enum=seq.elements(); 

	while (enum.hasMoreElements()) {
	  Object obj=enum.nextElement(); 
	  if (obj instanceof Node) {
		result.add(runParent((Node)obj));
	  }
	  else if (obj instanceof Item) {
		result.add(runParent(((Item)obj).getNode()));
	  }
	}

	return result;
  }


  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  protected Sequence runParent(Node node) throws XQueryTypeException 
  {
	Node parent = node.getParentNode();
	Sequence result=new Sequence(); 
	
	if (parent ==null) {
	  return new Sequence();
	}
	else {
	  if (doTest(parent) && doFilter(parent, 1)) { // 1: one PARENT NODE only
		result.add(parent);
	  }
	  return result; 
	}
  }


  /**
   * @param seq
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runSelf(Sequence seq) throws XQueryTypeException 
  {
	Sequence result = new Sequence();
	Enumeration enum=seq.elements(); 

	while (enum.hasMoreElements()) {
	  Object obj=enum.nextElement(); 
	  if (obj instanceof Node) {
		result.add(runSelf((Node)obj));
	  }
	  else if (obj instanceof Item) {
		result.add(runSelf(((Item)obj).getNode()));
	  }
	}

	return result;
  }

  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  protected Sequence runSelf(Node node) throws XQueryTypeException 
  {
	//	System.out.println("runself - PathOperator");
	
	if (doTest(node) && doFilter(node, 1)) {  // 1: one SELF node only
	  //	  System.out.println("runSelf-PathOperator return true");
	  
	  Sequence result = new Sequence();
	  result.add(node);
	  //	  System.out.println(result.size());
	  return result;
	}
	else {
	  return new Sequence();
	}
  }


  /**
   * @param seq
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  public Sequence runAttribute(Sequence seq) throws XQueryTypeException 
  {
	Sequence result = new Sequence();
	Enumeration enum=seq.elements(); 

	while (enum.hasMoreElements()) {
	  Object obj=enum.nextElement(); 
	  if (obj instanceof Node) {
		result.add(runAttribute((Node)obj));
	  }
	  else if (obj instanceof Item) {
		result.add(runAttribute(((Item)obj).getNode()));
	  }
	}

	return result;
  }

  /**
   * @param node
   * @return xquery.lib.data.Sequence
   * @throws xquery.lib.data.type.XQueryTypeException
   */
  protected Sequence runAttribute(Node node) throws XQueryTypeException 
  {
	//	System.out.println("run attribute");
	Sequence seq=new Sequence();
	if (node.hasAttributes()) {
	  //	  System.out.println("deo hieu the nao, co attribute ro rang kia ma");
	  
	  NamedNodeMap nodelist=node.getAttributes();
	  for(int i=0;i < nodelist.getLength(); i++) {
		Node childnode = nodelist.item(i);
		if (doTest(childnode) && doFilter(childnode, i+1)) {
		  //		  System.out.println("runAttribute-PathOperator return true");
		  seq.add(childnode);
		}
	  }
	} 

	return seq;
  }

}
