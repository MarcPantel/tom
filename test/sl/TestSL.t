/*
 * Copyright (c) 2004-2008, INRIA
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

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestSL extends TestCase {	  

  %include { testsl/testsl.tom }

  private NewBehaviour nb = new NewBehaviour();
  private OldBehaviour ob = new OldBehaviour();

  public static void main(String[] args) {
    junit.textui.TestRunner.run(new TestSuite(TestSL.class));
  }

  public void setUp() {

  }

  public void testSL1() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test1().equals(nb.test1()) );
  }
  public void testSL2() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test2().equals(nb.test2()) );
  }
  public void testSL3() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test3().equals(nb.test3()) );
  }
  public void testSL4() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test4().equals(nb.test4()) );
  }
  public void testSL5() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test5().equals(nb.test5()) );
  }
  public void testSLChoice() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testChoice().equals(nb.testChoice()) );
  }
  public void testSLChoiceSideEffect() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testChoiceSideEffect().equals(nb.testChoiceSideEffect()) );
  } 
  public void testSLNot() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testNot().equals(nb.testNot()) );
  }
  public void testSLNotSideEffect() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testNotSideEffect().equals(nb.testNotSideEffect()) );
  }
  public void testSL6() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test6().equals(nb.test6()) );
  }
  public void testSL7() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test7().equals(nb.test7()) );
  }
  public void testSL8() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test8().equals(nb.test8()) );
  }
  public void testSL9() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test9().equals(nb.test9()) );
  }
  public void testITESideEffect() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testITESideEffect().equals(nb.testITESideEffect()) );
  }
  public void testSL10() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test10().equals(nb.test10()) );
  }
  public void testSL11() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test11().equals(nb.test11()) );
  }
  public void testSL12() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test12().equals(nb.test12()) );
  }
  public void testSL13() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.test13().equals(nb.test13()) );
  }
  public void testSLWhen1() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testWhen1().equals(nb.testWhen1()) );
  }
  public void testSLCongruence1() throws tom.library.sl.VisitFailure {		
    assertTrue( ob.testCongruence1().equals(nb.testCongruence1()) );
  }
  public void testSLCongruence2() throws tom.library.sl.VisitFailure {	
    try {
      ob.testCongruence2();
      fail();
    } catch (RuntimeException e) {
      try {
        nb.testCongruence2();
        fail();
      } catch (tom.library.sl.VisitFailure ee) {/*success*/}
    }
  }
  public void testSLCongruenceList() throws tom.library.sl.VisitFailure {
    assertTrue( ob.testCongruenceList().equals(nb.testCongruenceList()) );
  }

}
