package polygraphesnat;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import polygraphesnat.types.*;
import java.io.*;
import tom.library.sl.*;


public class polygraphicXML {
  private static Document dom;
  %include{ dom.tom }
  %include { polygraphesnat/PolygraphesNat.tom }
  %include { sl.tom }

  public static void main (String args[]) {
 try {

	 dom = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder().parse("/Users/aurelien/polygraphWorkspace/PolygraphesApp/polygraphes/src/XMLinput.xml");
      Element e = dom.getDocumentElement();
      
      //System.out.println(makeThreeCell(e));
      //System.out.println(twoPath2XML(PolygraphesNat.test(makeTwoPath(e))));
      
save(twoPath2XML(PolygraphesNat.test(makeTwoPath(e))),new File("/Users/aurelien/polygraphWorkspace/PolygraphesApp/polygraphes/src/XMLoutput.xml"));

      //save(twoPath2XML(PolygraphesNat.test(`TwoC1(TwoC0(TwoC1(TwoC0(TwoC1(TwoC1(TwoC1(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoC1(TwoC1(TwoC1(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()))),TwoCell("multiplication",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function())),TwoC1(TwoC1(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()))),TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function())))),new File("/Users/aurelien/polygraphWorkspace/PolygraphesApp/polygraphes/src/XMLoutput.xml"));
	
	/* dom = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder().parse("/Users/aurelien/polygraphWorkspace/PolygraphesApp/polygraphes/src/test.xml");
      Element e = dom.getDocumentElement();
	 System.out.println(makeRule(makeThreeCell(e)));*/

      //ThreePath test=`ThreeCell("test",TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("zero",OneCell("nat"),OneCell("nat"),Constructor()),Function());
      //System.out.println(threePath2XML(test));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


public static OnePath makeOnePath(Node node){
String nodeName =node.getNodeName();
		// System.out.println(nodeName);
		if(nodeName.equals("OnePath")){
			NodeList nodeChilds=node.getChildNodes();
			for (int i = 0; i < nodeChilds.getLength(); i++) {
				Node nodeChild=nodeChilds.item(i);
				if(!nodeChild.getNodeName().equals("#text")){return makeOnePath(nodeChild);}
			}
		}
		if(nodeName.equals("OneCell")){
			NamedNodeMap attributes=node.getAttributes();
				String name=attributes.getNamedItem("Name").getNodeValue();
				return `OneCell(name);
		}
		/*
		 * if(nodeName.equals("Id")){ return `Id(); }
		 */// useless
		if(nodeName.equals("OneC0")){
			NodeList oneC0s=node.getChildNodes();
			OnePath res=`Id();
			for (int j = 0; j < oneC0s.getLength(); j++) {
				Node oneC0Element = oneC0s.item(j);
				if(!oneC0Element.getNodeName().contains("#text")){
					res=`OneC0(res,makeOnePath(oneC0Element));
				}				
			}		
			return res;
		}
NodeList childs=node.getChildNodes();
for (int i = 0; i < childs.getLength(); i++) {
	Node child = childs.item(i);
		if(!child.getNodeName().equals("#text")){
			return makeOnePath(child);}
}
return `Id();
}

public static TwoPath makeTwoPath(Node node){
String nodeName =node.getNodeName();
		if(nodeName.equals("TwoPath")){// en prevision des 3-cellules
			NodeList nodeChilds=node.getChildNodes();
			for (int i = 0; i < nodeChilds.getLength(); i++) {
				Node nodeChild=nodeChilds.item(i);
				if(!nodeChild.getNodeName().equals("#text")){return makeTwoPath(nodeChild);}
			}
		}
		if(nodeName.equals("TwoId")){
			NodeList nodeChilds=node.getChildNodes();
			for (int i = 0; i < nodeChilds.getLength(); i++) {
				Node nodeChild=nodeChilds.item(i);
				if(!nodeChild.getNodeName().equals("#text")){return `TwoId(makeOnePath(nodeChild));}
			}
		}
		if(nodeName.equals("TwoCell")){
				NamedNodeMap attributes=node.getAttributes();
				String name=attributes.getNamedItem("Name").getNodeValue();
				String type=attributes.getNamedItem("Type").getNodeValue();
		CellType celltype=null;
		if(type.equals("Function")){celltype=`Function();}
		if(type.equals("Constructor")){celltype=`Constructor();}
		NodeList io=node.getChildNodes();
		OnePath source=`Id();
		OnePath target=`Id();
		for (int j = 0; j < io.getLength(); j++) {
			Node ioChild=io.item(j);
			String ioName =ioChild.getNodeName();
			if(ioName.equals("Source")){source=makeOnePath(ioChild);}
			if(ioName.equals("Target")){target=makeOnePath(ioChild);}
		}
		return `TwoCell(name,source,target,celltype);
		}
		if(nodeName.equals("TwoC0")){
			NodeList twoC0s=node.getChildNodes();
			TwoPath res=`TwoId(Id());
			for (int j = 0; j < twoC0s.getLength(); j++) {
				Node twoC0Element = twoC0s.item(j);
				if(!twoC0Element.getNodeName().contains("#text")){
					// System.out.println(twoC0Element.getNodeName());
					res=`TwoC0(res,makeTwoPath(twoC0Element));
				}				
			}
			// System.out.println(res);
			return res;
		}
		if(nodeName.equals("TwoC1")){
			NodeList twoC1s=node.getChildNodes();
			TwoPath res=`TwoId(Id());
			for (int j = 0; j < twoC1s.getLength(); j++) {
				Node twoC1Element = twoC1s.item(j);
				if(!twoC1Element.getNodeName().contains("#text")){
					if(res==`TwoId(Id())){res=`makeTwoPath(twoC1Element);}
					else{
					res=`TwoC1(res,makeTwoPath(twoC1Element));
					}
				}				
			}		
			return res;
		}
NodeList childs=node.getChildNodes();
for (int i = 0; i < childs.getLength(); i++) {
	Node child = childs.item(i);
	if(!child.getNodeName().equals("#text")){
		return makeTwoPath(child);
		}
}
return `TwoId(Id());
}


public static ThreePath makeThreeCell(Node node){
		NamedNodeMap attributes=node.getAttributes();
		String name=attributes.getNamedItem("Name").getNodeValue();
		String type=attributes.getNamedItem("Type").getNodeValue();
		CellType celltype=null;
		if(type.equals("Function")){celltype=`Function();}
		if(type.equals("Constructor")){celltype=`Constructor();}
		NodeList io=node.getChildNodes();
		TwoPath source=`TwoId(Id());
		TwoPath target=`TwoId(Id());
		for (int j = 0; j < io.getLength(); j++) {
			Node ioChild=io.item(j);
			String ioName =ioChild.getNodeName();
			if(ioName.equals("Source")){source=makeTwoPath(ioChild);}
			if(ioName.equals("Target")){target=makeTwoPath(ioChild);}
		}
		return `ThreeCell(name,source,target,celltype);
}

public static String twoPath2XML(TwoPath path){
%match (TwoPath path){
			TwoId(onepath) -> {return "<TwoPath>\n<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n</TwoPath>\n";}
			TwoCell(name,source,target,type) -> { return "<TwoPath>\n<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n</TwoPath>\n"; }
			TwoC0(head,tail*) -> {return "<TwoPath>\n<TwoC0>\n"+twoC02XML(`head)+twoC02XML(`tail)+"</TwoC0>\n</TwoPath>\n";}
			TwoC1(head,tail*) -> {return "<TwoPath>\n<TwoC1>\n"+twoC12XML(`head)+twoC12XML(`tail)+"</TwoC1>\n</TwoPath>\n";}
}
return "";
}
public static String twoC02XML(TwoPath path){
%match (TwoPath path){
			TwoId(onepath) -> {return "<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n";}
			TwoCell(name,source,target,type) -> { return "<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n"; }
			TwoC0(head,tail*) -> {return twoC02XML(`head)+twoC02XML(`tail);}
			TwoC1(head,tail*) -> {return "<TwoC1>\n"+twoC12XML(`head)+twoC12XML(`tail)+"</TwoC1>\n";}
}
return "";
}
public static String twoC12XML(TwoPath path){
%match (TwoPath path){
			TwoId(onepath) -> {return "<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n";}
			TwoCell(name,source,target,type) -> { return "<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n"; }
			TwoC0(head,tail*) -> {return "<TwoC0>\n"+twoC02XML(`head)+twoC02XML(`tail)+"</TwoC0>\n";}
			TwoC1(head,tail*) -> {return twoC12XML(`head)+twoC12XML(`tail);}
}
return "";
}
public static String onePath2XML(OnePath path){
%match (OnePath path){
			Id() -> {return "<OnePath>\n<Id></Id>\n</OnePath>\n";}
			OneCell(name) -> { return "<OnePath>\n<OneCell Name=\""+`name+"\"></OneCell>\n</OnePath>\n"; }
			OneC0(head,tail*)->{ return "<OnePath>\n<OneC0>\n"+oneC02XML(`head)+oneC02XML(`tail)+"</OneC0>\n</OnePath>\n";}
}
return "";
}
public static String oneC02XML(OnePath path){
%match (OnePath path){
			Id() -> {return "<Id></Id>\n";}
			OneCell(name) -> { return "<OneCell Name=\""+`name+"\"></OneCell>\n"; }
			OneC0(head,tail*)->{ return oneC02XML(`head)+oneC02XML(`tail);}
}
return "";
}

public static String threePath2XML(ThreePath path){
if(path instanceof ThreePath){
return "<ThreeCell Name=\""+path.getName()+"\" Type=\""+path.getType().toString().replace("()","")+"\">\n<Source>\n"+twoPath2XML(path.getSource())+"</Source>\n<Target>\n"+twoPath2XML(path.getTarget())+"</Target>\n</ThreeCell>\n";
}
else {System.out.println("this is not a ThreeCell !"+path);return null;} 
}


public static void save(String fileContent,File file) throws IOException {

		PrintWriter printWriter = new PrintWriter(new FileOutputStream(
				file));
		printWriter.print(fileContent);
		printWriter.flush();
		printWriter.close();
	}
/*%strategy NormalizeRule() extends Identity(){ 
  	visit TwoPath {
  	  TwoC0(left*,X,right*) -> {if(`X.sourcesize()>0){ return `TwoC0(left*,TwoC1(TwoId(X.source()),X),right);} }
  	  TwoId(OneC0(head,tail*)) -> {return `TwoC0(TwoId(head),TwoId(tail*)); } 
  	  TwoC1(head*,t@TwoId(_),TwoId(_),tail*) -> { if(`head!=`TwoId(Id())){return `TwoC1(head,t,tail);}else{return `TwoC1(t,tail);}}
 	 } 
}*///useless


public static String makeRule(ThreePath rule){
TwoPath source=rule.getSource();
TwoPath target=rule.getTarget();
source=`TwoC1(TwoId(source.source()),source);
source=PolygraphesNat.formatRule(source);
target=`TwoC1(TwoId(target.source()),target);
target=PolygraphesNat.formatRule(target);
String sourceString=source.toString();
String targetString=target.toString();
int i=0;
while(sourceString.contains("TwoId")){
sourceString=sourceString.replaceFirst("TwoId[^\\)]+\\)\\)", "X"+i);
targetString=targetString.replaceFirst("TwoId[^\\)]+\\)\\)", "X"+i);
i++;}
sourceString=sourceString.subSequence(0, sourceString.length()-1)+",Y*)";
targetString=targetString.subSequence(0, targetString.length()-1)+",Y*)";
return sourceString+ "-> {return "+targetString+";}";
}
}