package library;

import org.w3c.dom.*;
import java.io.File;
import adt.polygraphicprogram.types.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//provides all the functions to transform terms in xml and vice-versa

public class XMLhandler {
  %include{ dom.tom }
  %include { ../adt/polygraphicprogram/PolygraphicProgram.tom }
  %include { sl.tom }

  // save a String in a new File
  public static void save(String fileContent,File file) throws IOException {
		PrintWriter printWriter = new PrintWriter(new FileOutputStream(
				file));
		printWriter.print(fileContent);
		printWriter.flush();
		printWriter.close();
	}

  //get the text from a text file
  public static String load(String filepath) throws IOException {
		FileChannel channel = new FileInputStream(new File(filepath)).getChannel();
		try {
			ByteBuffer b = ByteBuffer.allocate((int) channel.size());
			channel.read(b);
			return new String(b.array());
		} finally {
			channel.close();
		}
	}


  // make a 1-path term from its xml description
  public static OnePath makeOnePath(Node node){
	  String nodeName =node.getNodeName();
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
	  if(nodeName.equals("OneC0")){
		  NodeList oneC0s=node.getChildNodes();
		  OnePath res=`Id();
		  for (int j = oneC0s.getLength()-1; j > 0; j--) {
			  Node oneC0Element = oneC0s.item(j);
			  if(!oneC0Element.getNodeName().contains("#text")){
				  res=`OneC0(makeOnePath(oneC0Element),res);
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

  // make a 2-path term from its xml description
  public static TwoPath makeTwoPath(Node node){
	  String nodeName =node.getNodeName();
	  if(nodeName.equals("TwoPath")){
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
		  return `TwoCell(name,source,target,celltype,0);
	  }
	  if(nodeName.equals("TwoC0")){
		  NodeList twoC0s=node.getChildNodes();
		  TwoPath res=`TwoId(Id());
		  for (int j = twoC0s.getLength()-1; j >0; j--) {
			  Node twoC0Element = twoC0s.item(j);
			  if(!twoC0Element.getNodeName().contains("#text")){
				  res=`TwoC0(makeTwoPath(twoC0Element),res);
			  }				
		  }
		  return res;
	  }
	  if(nodeName.equals("TwoC1")){
		  NodeList twoC1s=node.getChildNodes();
		  TwoPath res=`TwoId(Id());
		  for (int j = twoC1s.getLength()-1; j >0; j--) {
			  Node twoC1Element = twoC1s.item(j);
			  if(!twoC1Element.getNodeName().contains("#text")){
				  if(res==`TwoId(Id())){res=`makeTwoPath(twoC1Element);}
				  else{
					  res=`TwoC1(makeTwoPath(twoC1Element),res);
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

  // make a 3-path term from its xml description
  public static ThreePath makeThreeCell(Node node){
	  NamedNodeMap attributes=node.getAttributes();
	  String name=attributes.getNamedItem("Name").getNodeValue();
	  String type=attributes.getNamedItem("Type").getNodeValue();
	  CellType celltype=null;
	  if(type.equals("Function")){celltype=`Function();}
	  if(type.equals("Constructor")){celltype=`Constructor();}//never used
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


	//Set of functions converting polygraphic terms in strings based on the xml format we chose
	//return the xml description of a 2-Path
	public static String twoPath2XML(TwoPath path){
		%match (TwoPath path){
			TwoId(onepath) -> {return "<TwoPath>\n<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n</TwoPath>\n";}
			TwoCell(name,source,target,type,id) -> { return "<TwoPath>\n<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n</TwoPath>\n"; }
			TwoC0(head,tail*) -> {return "<TwoPath>\n<TwoC0>\n"+twoC02XML(`head)+twoC02XML(`tail)+"</TwoC0>\n</TwoPath>\n";}
			TwoC1(head,tail*) -> {return "<TwoPath>\n<TwoC1>\n"+twoC12XML(`head)+twoC12XML(`tail)+"</TwoC1>\n</TwoPath>\n";}
		}
		return "";
	}
	//sub-function of the previous one to handle twoC0s
	public static String twoC02XML(TwoPath path){
	%match (TwoPath path){
					TwoId(onepath) -> {return "<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n";}
					TwoCell(name,source,target,type,id) -> { return "<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n"; }
					TwoC0(head,tail*) -> {return twoC02XML(`head)+twoC02XML(`tail);}
					TwoC1(head,tail*) -> {return "<TwoC1>\n"+twoC12XML(`head)+twoC12XML(`tail)+"</TwoC1>\n";}
		}
	return "";
	}
	//the same for twoC1s
	public static String twoC12XML(TwoPath path){
		%match (TwoPath path){
			TwoId(onepath) -> {return "<TwoId>\n"+onePath2XML(`onepath)+"</TwoId>\n";}
			TwoCell(name,source,target,type,id) -> { return "<TwoCell Name=\""+`name+"\" Type=\""+`type.toString().replace("()","")+"\">\n<Source>\n"+onePath2XML(`source)+"</Source>\n<Target>\n"+onePath2XML(`target)+"</Target>\n</TwoCell>\n"; }
			TwoC0(head,tail*) -> {return "<TwoC0>\n"+twoC02XML(`head)+twoC02XML(`tail)+"</TwoC0>\n";}
			TwoC1(head,tail*) -> {return twoC12XML(`head)+twoC12XML(`tail);}
		}
		return "";
	}
	//same idea with 1-paths
	public static String onePath2XML(OnePath path){
		%match (OnePath path){
			Id() -> {return "<OnePath>\n<Id></Id>\n</OnePath>\n";}
			OneCell(name) -> { return "<OnePath>\n<OneCell Name=\""+`name+"\"></OneCell>\n</OnePath>\n"; }
			OneC0(head,tail*)->{ return "<OnePath>\n<OneC0>\n"+oneC02XML(`head)+oneC02XML(`tail)+"</OneC0>\n</OnePath>\n";}
		}
		return "";
	}
	//and for OneC0s
	public static String oneC02XML(OnePath path){
		%match (OnePath path){
			Id() -> {return "<Id></Id>\n";}
			OneCell(name) -> { return "<OneCell Name=\""+`name+"\"></OneCell>\n"; }
			OneC0(head,tail*)->{ return oneC02XML(`head)+oneC02XML(`tail);}
		}
		return "";
	}
	//return the xml description of a 3-Cell
	public static String threePath2XML(ThreePath path){
		if(path instanceof ThreePath){
			return "<ThreeCell Name=\""+path.getName()+"\" Type=\""+path.getType().toString().replace("()","")+"\">\n<Source>\n"+twoPath2XML(path.getSource())+"</Source>\n<Target>\n"+twoPath2XML(path.getTarget())+"</Target>\n</ThreeCell>\n";
		}
		else {System.out.println("this is not a ThreeCell !"+path);return null;} 
	}
}