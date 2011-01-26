/*
 *
 * Copyright (c) 2000-2011, INPL, INRIA
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
 *
 **/
package testgen;

import java.util.*;
import tom.library.sl.*;

/**
 * <p>
 * Basic strategy combinator used to build a fix-point strategy
 * Visiting such a combinator fire the mu-expansion which instantiate
 * the MuVar variables and removed the Mu combinator
 * the fix-point depends on the environment
 * When using the visitLight method, the MuFixPoint behaves like Mu
 * cannot extend directly Mu because visit methods are declared final
 **/ 
public class MuFixPoint extends AbstractStrategy {

  public final static int VAR = 0;
  public final static int V = 1;

  private boolean expanded = false;
  private String identifier;

  // the last environment is shared by all the instances of FixPoint with the same name
  // if the MuFixPoint is used in a %op, even if this strategy is constructed several times,
  // the last environment information is not lost
  public static Map<String,Environment> lastEnvironments = new HashMap();

  public MuFixPoint(String identifier, Strategy var, Strategy v) {
    initSubterm(var, v);
    this.identifier = identifier;
  }

  public final Object visitLight(Object any, Introspector i) throws VisitFailure {
    if(!expanded) { 
      expand(this); 
      expanded = true;
    }
    return visitors[V].visitLight(any,i);
  }

  public int visit(Introspector i) {
    if(environment.equals(lastEnvironments.get(identifier))) {
      return environment.getStatus();
    } else {
      try {
        lastEnvironments.put(identifier,(Environment) environment.clone());
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException("Unexpected CloneNotSupportedException");
      }
      if(!expanded) { 
        expand(this); 
        expanded = true;
      }
      return visitors[V].visit(i);
    }
  }

  private boolean isExpanded() {
    return ((MuVar)visitors[VAR]).isExpanded();
  }

  public static void expand(Strategy s) {
    expand(s,null,0,new HashSet(),new LinkedList());
  }

  /**
   * @param any the node we visit
   * @param parent the node we come from
   * @param childNumber the n-th subterm of parent
   * @param set of already visited parent
   */
  private static void expand(Strategy any, Strategy parent, int childNumber, HashSet set, LinkedList stack) {
    /* check that the current element has not already been expanded */
    if(set.contains(any)) {
      return;
    } else {
      set.add(parent);
    }

    if(any instanceof Mu) {
      MuVar var = (MuVar) any.getChildAt(VAR);
      Strategy v = (Strategy) any.getChildAt(V);
      stack.addFirst(any);
      expand(v,(Mu)any,0,set,stack);
      expand(var,null,0,set,stack);
      stack.removeFirst();
      return;
    } else {
      if (any instanceof MuVar) {
        String n = ((MuVar)any).getName();
        MuVar muvar = (MuVar) any;
        if(!muvar.isExpanded()) {
          Iterator it = stack.iterator();
          while(it.hasNext()) {
            Mu m = (Mu)it.next();
            if(((MuVar)m.getVisitor(Mu.VAR)).getName().equals(n)) {
              //System.out.println("MuVar: setInstance " + n );
              muvar.setInstance(m);
              if(parent!=null) {
                /* shortcut: the MuVar is replaced by a pointer 
                 * to the 2nd child of Mu
                 * after expansion there is no more Mu or MuVar
                 */
                //System.out.println("parent: " + parent);
                //System.out.println("childNumber: " + childNumber);
                //System.out.println("V: " + m.visitors[Mu.V]);
                parent.setChildAt(childNumber,m.getVisitor(Mu.V));
              } else {
                //System.out.println("strange: " + muvar);
              }
              return;
            }
          }
          //System.out.println("MuVar: " + n + " not found");
        }
      }
    }

    int childCount = any.getChildCount();
    for(int i = 0; i < childCount; i++) {
      expand((Strategy)any.getChildAt(i),any,i,set,stack);
    }
  }


}
