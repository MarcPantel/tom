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

package addressbook;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import aterm.pure.*;
import addressbook.data.*;
import addressbook.data.types.*;

import java.util.Iterator;
import java.util.HashSet;

public class TestAddressBook extends TestCase {
  private AddressBook1 test;
  private HashSet book;

  public static void main(String[] args) {
    junit.textui.TestRunner.run(new TestSuite(TestAddressBook.class));
  }

  public void setUp() {
    test = new AddressBook1();
    book = new HashSet();
    test.generatePerson(book);
  }

  %include { data/Data.tom }

  public void testBirthdate() {
    Iterator it = book.iterator();
    while(it.hasNext()) {
      Person p = (Person) it.next();
      Date d = p.getbirthdate();
      %match(Person p, Date d) {
        person(_, _ ,date(_,month1,day1)), date(_,month2,day2) -> {
           assertTrue((`month1==`month2) && (`day1==`day2));
         }  			  
      }
    }
  }
    
}
