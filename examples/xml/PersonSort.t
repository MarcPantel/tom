/*
 * Copyright (c) 2004-2005, Pierre-Etienne Moreau
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

import tom.library.xml.*;
import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;
import aterm.*;

public class PersonSort {
  %include{ adt/tnode/TNode.tom }

  private XmlTools xtools;
  private TNodeFactory getTNodeFactory() {
      return xtools.getTNodeFactory();
  }
  
  public static void main (String args[]) {
    PersonSort person = new PersonSort();
    person.run("person.xml");
  } 

 private void run(String filename){
    xtools = new XmlTools();
    TNode term = (TNode)xtools.convertXMLToATerm(filename);
    
    searchJu(term.getDocElem());
    term = sort(term.getDocElem());
    xtools.printXMLFromATerm(term);

  }
   
  private TNode sort(TNode subject) {
    %match(TNode subject) {
     <Persons>(X1*,p1,X2*,p2,X3*)</Persons> -> {
        if(`compare(p1,p2) > 0) {
          return sort(`xml(<Persons>X1* p2 X2* p1 X3*</Persons>));
        }	
      }
      
      _ -> { return subject; }     
    } 
  }
  	 
  private int compare(TNode t1, TNode t2) {
    %match(TNode t1, TNode t2) {
      <Person Age=a1><FirstName>#TEXT(n1)</FirstName></Person>,
      <Person Age=a2><FirstName>#TEXT(n2)</FirstName></Person>
      -> { return `a1.compareTo(`a2); }
    }
    return 0;
  }

  private void searchJu(TNode subject) {
    %match(TNode subject) {
      <Persons><Person><FirstName>#TEXT((_*,'Ju',X*))</FirstName></Person></Persons> -> {
        System.out.println("Hello Mr Ju" + `X); 
      }
    }
  }
}

