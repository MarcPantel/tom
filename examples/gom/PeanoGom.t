/*
 * Copyright (c) 2004-2011, INPL, INRIA
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
package gom;

import gom.peanogom.peano.*;
import gom.peanogom.peano.types.*;

class PeanoGom {

  %gom {
    module Peano
    abstract syntax
    Nat = zero()
        | suc(pred:Nat)
   }

    // rule fib
  public static Nat fib(Nat t) {
    %match(t) {
      zero() -> { return `suc(zero()); }
      suc(zero()) -> { return `suc(zero()); }
      suc(suc(x)) -> { return `plus(fib(x),fib(suc(x))); }
    }
    return null;
  }

    // rule plus
  public static Nat plus(Nat t1, Nat t2) {
    %match(t2) {
      zero() -> { return t1; }
      suc(y)    -> { return `suc(plus(t1,y)); }
    } 
    return null;
  }
  
  public void run() {
    System.out.println("running...");
    System.out.println(fib(`suc(suc(suc(suc(suc(zero())))))));
  }
  
  public final static void main(String[] args) {
    PeanoGom test = new PeanoGom();
    test.run();
  }

  public Nat getFib(Nat n) {
    return fib(n);
  }
  
  public Nat getFive() {
    return `suc(suc(suc(suc(suc(zero())))));
  }

  public Nat getEight() {
    return `suc(suc(suc(suc(suc(suc(suc(suc(zero()))))))));
  }

}
