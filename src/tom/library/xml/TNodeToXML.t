/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2007, INRIA
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

import java.io.*;
import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;

public class TNodeToXML {

  %include{ adt/tnode/TNode.tom }

  private OutputStream out = System.out;
  private Writer writer = null;

  public void setOutputStream(OutputStream out){
    this.out = out;
  }

  public void setWriter(Writer writer) {
    this.writer = writer;
  }

  /*
public void convert(String filename) {
    try {
      convert(SingletonFactory.getInstance().readFromFile(filename));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
*/

  public String xml(TNode n) {
		StringWriter str_res = new StringWriter();
		setWriter(str_res);
		tnodeToXML(n);
		return str_res.toString();
  }

  public void tnodeToXML(TNode n) {
    %match(TNode n) {
      DocumentNode(docType,docElem) -> {
        write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        tnodeToXML(`docType);
        tnodeToXML(`docElem);
        write("\n");
        return;
      }
      DocumentTypeNode(name,publicId,systemId,internalSubset,_,_) -> {
        write("<!DOCTYPE "+`name);
        if (!`publicId.equals("UNDEF") && !`systemId.equals("UNDEF"))
          write(" PUBLIC \""+`publicId+"\" \"");
        else if (!`systemId.equals("UNDEF") && `publicId.equals("UNDEF"))
          write(" SYSTEM \"");
        else {
          System.out.println("Problem in DocumentTypeNode");
          throw new RuntimeException("Problem in DocumentTypeNode");
        }
        write(`systemId+"\"");
        if (!`internalSubset.equals("UNDEF"))
          write(" ["+`internalSubset+"]");
          write(">\n");
          return;
      }
      ElementNode(name,attrList,concTNode()) -> {
        write("<"+`name);
        tnodeListToXML(`attrList);
        write("/>");
        return;
      }
      ElementNode(name,attrList,childList) -> {
        write("<"+`name);
        tnodeListToXML(`attrList);
        write(">");
        tnodeListToXML(`childList);
        write("</"+`name+">");
        return;
      }
      AttributeNode(name,specified,child) -> {
        if (`specified.equals("true")) {
          write(" " + `name + "=\"" + `child + "\"");
        }
        return;
      }
      TextNode(data) -> {
        write(`data);
        return;
      }
      CommentNode(data) -> {
        write("<!-- "+`data+" -->");
        return;
      }
      CDATASectionNode(data) -> {
        write("<![CDATA["+`data+"]]>");
        return;
      }
      ProcessingInstructionNode(target,data) -> {
        write("<?"+`target+" "+`data+"?>");
        return;
      }
      EntityReferenceNode(name,_) -> {
        write("&"+`name+";");
        return;
      }
      EmptyNode() -> {
        return;
      }
      _ -> {
        System.out.println("Unknown type of TNode : "+n);
      }
    }
    write("\n");
  }

  private void tnodeListToXML(TNodeList list) {
    if(list.isEmptyconcTNode()) {
      return;
    }
    TNode t = list.getHeadconcTNode();
    TNodeList l = list.getTailconcTNode();
    tnodeToXML(t);
    tnodeListToXML(l);
  }

  private void write(String s) {
    try {
      if (out != null) {
    out.write(s.getBytes("UTF-8"));
    }
      if (writer != null) {
    writer.write(s);
    }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }

}
