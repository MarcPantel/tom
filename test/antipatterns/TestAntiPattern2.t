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

package antipatterns;

import static org.junit.Assert.*;
import org.junit.Test;

import antipatterns.testantipattern2.m.types.*;

public class TestAntiPattern2 {
  %gom {
    module M
      abstract syntax
      TwoPath = 
      | id()
      | g()
      | c0( TwoPath* )
      | c1( TwoPath* )
  }

  @Test
  public void test1() {
    TwoPath res = `c1(c0(g(),id()),c0(id(),g()));
    %match(res) {
      c1(c0(_M*,_f@!id[],_N*), c0(_P*,g@!id[],_Q*), _tail*) -> {
        if(`g.isid()) {
          //fail();
        }
      }
    }
  }

  @Test
  public void test2() {
    TwoPath t = `g();
    %match(t) {
      !id() -> { return; /* OK */ }
    }
    fail();
  }

  @Test
  public void test3() {
    TwoPath p1 = `c0(g(),id());
    TwoPath p2 = `c0(id(),g());
    %match(p1,p2) {
      c0(_M*,_f@!id[],_N*), c0(_P*,g@!id[],_Q*) -> {
        if(`g.isid()) {
          //fail();
        }
      }
    }
  }

  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main(TestAntiPattern2.class.getName());
  }

}