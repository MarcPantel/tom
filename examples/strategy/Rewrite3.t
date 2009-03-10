/*
 * Copyright (c) 2004-2009, INRIA
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

package strategy;

import strategy.term.*;
import strategy.term.types.*;

import java.util.*;
import tom.library.sl.*;

public class Rewrite3 {

  %include { term/term.tom }
  %include { sl.tom }
  %include { java/util/types/Collection.tom }

  public final static void main(String[] args) {
    try {
      Collection collection = new HashSet();
      Strategy rule = `RewriteSystem(collection);
      Term subject = `f(g(g(a(),b()),g(a(),b())));
      `Try(BottomUp(rule)).visitLight(subject);
      System.out.println("collect : " + collection);
      
      collection.clear();
      subject = `f(g(g(a(),b()),g(c(),b())));
      `Try(BottomUp(rule)).visitLight(subject);
      System.out.println("collect : " + collection);
    } catch (VisitFailure e) {
      System.out.println("reduction failed");
    }

    System.out.println("occursTerm: " + occursTerm(`g(c(),c()), `f(g(g(a(),b()),g(a(),b()))) ));
    System.out.println("occursTerm: " + occursTerm(`g(c(),c()), `f(g(g(a(),b()),g(c(),c()))) ));
  }
  
  %strategy RewriteSystem(collection:Collection) extends `Fail() {
    visit Term {
      g(x,b()) -> { collection.add(`x); }
    }
  }

  /*
   * Library
   */
  %strategy findTerm(groundTerm:Term) extends Identity() {
    visit Term {
      subject -> { 
        if(`groundTerm == `subject) {
          throw new VisitFailure();
        }
      }
    }
  }
  
  private static boolean occursTerm(final Term groundTerm, Term subject) {
    try {
      `BottomUp(findTerm(groundTerm)).visitLight(subject);
      return false;
    } catch(VisitFailure e) {
      return true;
    }
  }
 
}