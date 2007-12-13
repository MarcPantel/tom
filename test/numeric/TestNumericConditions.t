package numeric;

/*
 * Copyright (c) 2004-2007, INRIA
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

import junit.framework.TestCase;
import junit.framework.TestSuite;

import numeric.testnumericconditions.m.types.*;

public class TestNumericConditions extends TestCase {
  %gom {
    module M
      imports int
      abstract syntax
      Term = 
      | a()
      | b()
      | c()
      | list( Term* )
      | f(t1:Term,t2:int)
      | h( l:TermList)
      | g(t:Term)

      TermList = termList(Term*)
  }
  
  public void test1() {
    %match(f(a(),6)) {
      f(_x,y) && y > 5 -> {        
        return;
      }
    }
    fail();
  }
  
  public void test2() {
    %match(f(a(),6)) {
      f(_x,y) && y >= 6 -> {        
        return;
      }
    }
    fail();
  }
  
  public void test3() {
    %match(f(a(),3)) {
      f(_x,y) && y < 6 -> {        
        return;
      }
    }
    fail();
  }
  
  public void test4() {
    %match(f(a(),4)) {
      f(_x,y) && y <= 3 -> {        
        fail();
      }
    }
    return;
  }

  public void test5() {
    %match(f(a(),4)) {
      f(_x,y) && y != 4 -> {        
        fail();
      }
    }
    return;
  }
  
  public void test6() {
    %match(f(a(),4)) {
      f(_x,y) && y == 4 -> {        
        return;
      }
    }
    fail();
  }
  
  public void test7() {
    %match(f(a(),4)) {
      f(_x,y) && y > 2 && y < 5 -> {        
        return;
      }
    }
    fail();
  }
  
  public void test8() {
    %match(f(a(),6)) {
      f(_x,y) && y > 2 && y < 5 -> {        
        fail();
      }
    }
    return;
  }
  
  public void test9() {
    %match(f(a(),6)) {
      f(_x,y) && ( y > 2 || y < 5 ) -> {        
        return;
      }
    }
    fail();
  }  

  public void test10() {
    %match(f(a(),6)) {
      f(_x,y) && ( y !=6 || y < 5 ) -> {        
        fail();
      }
    }
    return;
  }
  
  public void test11() {
    %match(f(a(),6)) {
      f(_x,y) && ( y ==6 || y < 5 ) -> {        
        return;
      }
    }
    fail();
  }
  
  public void test12() {
    %match(f(a(),6)) {
      f(_x,y) && ( y !=6 || y >= 5 ) -> {        
        return;
      }
    }
    fail();
  }
  
  public void test13() {
    %match(f(a(),6)) {
      f(_x,y) && y > int getIntegerValue() -> {        
        return;
      }
    }
    fail();
  }
  
  public void test14() {
    %match(f(a(),6)) {
      f(_x,y) && y == int getIntegerValue() -> {        
        fail();
      }
    }
    return;
  } 
  
  public void test15() {
    int m = 6;
    %match(f(a(),6)) {
      f(_x,y) && y == int m -> {        
        return;
      }
    }
    fail();
  } 

  
  private int getIntegerValue(){
    return 5;
  }


  public static void main(String[] args) {
    junit.textui.TestRunner.run(new TestSuite(TestNumericConditions.class));
  }

}
