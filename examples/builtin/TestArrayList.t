/*
 * Copyright (c) 2006-2009, INRIA
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
package builtin;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestArrayList extends TestCase {

  public final static void main(String[] args) {
    junit.textui.TestRunner.run(new TestSuite(TestArrayList.class));
  }

  %include { java/util/ArrayList.tom }

  public void testMake() {
    java.util.ArrayList list = `concArrayList("one","two","three","four");

    assertEquals(list.get(0),"one");
    assertEquals(list.get(1),"two");
    assertEquals(list.get(2),"three");
    assertEquals(list.get(3),"four");
  }

  public void testIter() {
    java.util.Set set = new java.util.HashSet();
    java.util.ArrayList list = `concArrayList("one","two","three","four");
    %match(ArrayList list) {
      (_*,x,_*)   -> { set.add(`x+"_"+`x); }
    }
    assertEquals(set.size(),4);
    assertTrue(set.contains("one_one"));
    assertTrue(set.contains("two_two"));
    assertTrue(set.contains("three_three"));
    assertTrue(set.contains("four_four"));
  }
}
