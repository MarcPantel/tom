/*
 * Copyright (c) 2004-2006, INRIA
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

package sl;

import sl.testenvironment.term.types.*;
import tom.library.sl.Environment;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestEnvironment extends TestCase {

  %gom {
    module term
    abstract syntax
    Term = f(lhs:Term,rhs:Term)
         | a() | b() | c()
  }

	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(TestEnvironment.class));
	}

  public void testEqual(){
    Environment e1 = new Environment();
    Environment e2 = new Environment();
    assertEquals(e1,e2);
    Term subject = `f(a(),b());
    e1.setSubject(subject);
    e2.setSubject(subject);
    assertEquals(e1,e2);
    e1.down(2);
    e2.down(2);
    assertEquals(e1,e2);
    e1.up();
    e2.up();
    e1.down(1);
    e2.down(1);
    assertEquals(e1,e2);
  }


  public void testUpAndDown(){
    Environment e1 = new Environment();
    Environment e2 = new Environment();
    Term subject = `f(a(),b());
    e1.setSubject(subject);
    e2.setSubject(subject);
    e1.down(2);
    e2.down(2);
    //test of down
    assertEquals(e1.getRoot(),`f(a(),b()));
    assertEquals(e1.getOmega().length,1);
    assertEquals(e1.getOmega()[0],2);
    assertEquals(e1.getSubject(),`b());
    e1.up();
    e2.up();
    //test of up
    assertEquals(e1.getRoot(),`f(a(),b()));
    assertEquals(e1.getOmega().length,0);
    assertEquals(e1.getSubject(),`f(a(),b()));
    e1.down(1);
    e2.down(1);
    //test of down
    assertEquals(e1.getRoot(),`f(a(),b()));
    assertEquals(e1.getOmega().length,1);
    assertEquals(e1.getOmega()[0],1);
    assertEquals(e1.getSubject(),`a());
  }

}
