/*
 * Copyright (c) 2004, INRIA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 * 	- Redistributions of source code must retain the above copyright
 * 	notice, this list of conditions and the following disclaimer.  
 * 	- Redistributions in binary form must reproduce the above copyright
 * 	notice, this list of conditions and the following disclaimer in the
 * 	documentation and/or other materials provided with the distribution.
 * 	- Neither the name of the INRIA nor the names of its
 * 	contributors may be used to endorse or promote products derived from
 * 	this software without specific prior written permission.
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
 */

package xml;

import jtom.runtime.xml.*;
import jtom.adt.tnode.*;
import jtom.adt.tnode.types.*;
import aterm.*;
import jtom.runtime.*;
 
public class Action {
  
  %include{ TNode.tom }
    
  private XmlTools xtools;
  private GenericTraversal traversal = new GenericTraversal();
  private Factory getTNodeFactory() {
      return xtools.getTNodeFactory();
  }

  public static void main (String args[]) {
    Action action = new Action();
    action.run("action.xml");
  }

  private void run(String filename){
    xtools = new XmlTools();
    ATerm term = xtools.convertXMLToATerm(filename);
    test1(term);
    test2(term);
  }
    
  private void test1(ATerm subject) {
    TNode t = (TNode) subject;
    t = t.getDocElem();
    %match(TNode t) {
      <Actions><Action>
         comp@<Comp Label="busy" Type=type Index=i />
         </Action></Actions>
         -> {
           System.out.println("Action 1 Localisee ! " + type + "  " + i);
           System.out.println("Comp: " + comp);
         }
      
      _ -> {
          //System.out.println("do not match: " + t);
      }
    } // match
    
  }

  private void test2(ATerm subject) {
    TNode t = (TNode) subject;
    t = t.getDocElem();
    %match(TNode t) {
      <Actions>a</Actions> -> {
        %match(TNode a,TNode a) {
          <Action><Comp Index=i2 Label=l Type="Wait"/></Action>,
          <Action><Comp Index=i1 Label=l Type="Send"/></Action> -> {
            System.out.println("Synchronisation sur le label "+l +" entre "+i1+"(!) et "+i2+"(?)");				
          }	
        }
      }
    } // match
    
  }

  
  
}

