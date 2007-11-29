/*
 * Copyright (c) 2004-2007, INRIA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *	- Redistributions of source code must retain the above copyright
 *	notice, this list of conditions and the following disclaimer.
 *	- Redistributions in binary form must reproduce the above copyright
 *	notice, this list of conditions and the following disclaimer in the
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
package analysis;
import analysis.node.*;
import analysis.node.types.*;
import tom.library.sl.*;


public class ControlFlowGraphBasicStrategy extends analysis.node.NodeBasicStrategy {

  public ControlFlowGraphBasicStrategy(Strategy v) {
    super(v);
  }

  public Object visitLight(Object v, Introspector i) throws VisitFailure {
    if (v instanceof ControlFlowGraph) {
      return this.visit_ControlFlowGraph((ControlFlowGraph)v,i);
    } else {
      return super.visitLight(v,i);
    }
  }


  public ControlFlowGraph visit_ControlFlowGraph(ControlFlowGraph arg, Introspector i) throws VisitFailure {
    Node root = arg.getRoot().getNode();
    Node result = (Node) visitLight(root);
    arg.getRoot().setNode(result);
    return arg;
  }
}
