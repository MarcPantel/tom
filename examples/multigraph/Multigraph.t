/*
 * Copyright (c) 2004-2007, INRIA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 *	- Redistributions of source code must retain the above copyright
 *	notice, this conc of conditions and the following disclaimer.  
 *	- Redistributions in binary form must reproduce the above copyright
 *	notice, this conc of conditions and the following disclaimer in the
 *	documentation and/or other materials provided with the distribution.
 *	- Neither the name of the INRIA nor the names of its
 *	contributors may be used to endorse or promote products derived from
 *	this software without specific prior written permission.
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

package multigraph;

import tom.library.sl.*;

import jjtraveler.reflective.VisitableVisitor;
import jjtraveler.Visitable;
import jjtraveler.VisitFailure;
import java.util.*;

import multigraph.term.*;
import multigraph.term.types.*;

public class Multigraph {

  %include {sl.tom }
  %include {util/HashMap.tom}
  %include {term/term.tom}
  %include {term/_term.tom}

  public static void main(String[] args){
    String name= args[0];
    try{
      java.io.InputStream stream = new java.io.FileInputStream(name);
      RuleList rules = RuleList.fromStream(stream);
      System.out.println(prettyPrint(rules));
    } catch(java.io.IOException e) {}
  }

  %strategy Generate(output:StringWrapper) extends Identity(){
    visit Rule{
      rule(lhs,rhs) -> {
        output.buffer += "\n"+prettyPrint(`lhs)+ "->" +"{ return `"+prettyPrint(`rhs)+";}";
      }
    }
  }

  %typeterm StringWrapper{
      implement {StringWrapper}
  }


  public static class StringWrapper{
    public String buffer;
  }

  private static String prettyPrint(Nodes nodes) {
    String output = nodes.toString();
    %match (String output) {
      concString(X*,'VarNode("',N*,'")',Y*) -> {
        %match(String N) {
          !concString(_*,'"',_*) -> {
            output = `concString(X*,N*,Y*); }
        }
      }
    }
    %match (String output) {
      concString(X*,'VarNodeName("',N*,'")',Y*) -> {
        %match(String N) {
          !concString(_*,'"',_*) -> {
            output = `concString(X*,N*,Y*); }
        }
      }
    }
    return output;
  }

  private static String prettyPrint(RuleList rules) {
    StringWrapper output = new StringWrapper();
    output.buffer = %[
      %strategy MyRule() extends Identity() {
        visit Nodes {
      ]%;
    `TopDown(Generate(output)).fire(rules);
    output.buffer += "\n}\n}";
    return output.buffer;
  }

}
