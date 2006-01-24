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

package prodrule;

import aterm.*;
import aterm.pure.*;
import prodrule.fib4.fib.*;
import prodrule.fib4.fib.types.*;
import java.util.*;

public class Fib4 {
  private fibFactory factory;

  %vas {
    // extension of adt syntax
    module fib
    imports
      
	    public
	    sorts Element
      
	    abstract syntax
	    Undef -> Element
	    Nat( value:Int ) -> Element
	    Fib(arg:Int, val:Element) -> Element
	    }

    
  %typeterm Space {
    implement { ArrayList }
    equals(l1,l2)      { l1.equals(l2) }
  }

  %oparray Space concElement( Element* ) {
    is_fsym(t) { t instanceof ArrayList }
    make_empty(n)   { myEmpty(n) }
    make_append(e,l) { myAdd(e,(ArrayList)l) }
    get_element(l,n)   { (Element)l.get(n) }
    get_size(l)        { l.size() }
  }
    

  private ArrayList myAdd(Object e,ArrayList l) {
    l.add(e);
    return l;
  }
  
  private ArrayList myEmpty(int n) {
    ArrayList res = new ArrayList(n);
    return res;
  }

  public Fib4(fibFactory factory) {
    this.factory = factory;
  } 

  public fibFactory getFibFactory() {
    return factory;
  }
  
  private static boolean opt = true;
  private static int fire=0;
  public int run(int n) {
    ArrayList WM = new ArrayList();
    long startChrono = System.currentTimeMillis();
    System.out.println("running...");
    WM.add(`Fib(0,Nat(1)));
    WM.add(`Fib(1,Nat(1)));
    WM.add(`Fib(n,Undef));
    loop(WM);
    System.out.println("fib(" + n + ") = " + result(WM,n) + " (in " + (System.currentTimeMillis()-startChrono)+ " ms)");
    System.out.println("fire = " + fire);
    return result(WM,n);
  } 
  
  public void loop(ArrayList WM) {
    boolean modified = true;
    while(modified) {
	    modified = modified && (rec(WM) || compute(WM));
	    //System.out.println("WM = "+ WM);
    } 
  }

  public final static void main(String[] args) {
    Fib4 test = new Fib4(fibFactory.getInstance(new PureFactory(16)));
    try {
      test.run(Integer.parseInt(args[0]));
    } catch (Exception e) {
      System.out.println("Usage: java Fib <nb>");
      return;
    }
  }

  public boolean rec(ArrayList WM) {
    %match(Space WM) {
	    concElement(_*, Fib[arg=n,val=Undef()], _*) -> {
        if(`n>2 && !`occursFib(WM,n-1)) {
          WM.add(0,`Fib(n-1,Undef));
          fire++;
          return (true);
        }
	    }
    }
    return false;
  }

  public boolean compute(ArrayList WM) {
    %match(Space WM) {
	    concElement(_*, f@Fib[arg=n,val=Undef()], _*) -> {
        %match(Space WM) {
          concElement(_*, f1@Fib[arg=n1,val=Nat(v1)], _*, f2@Fib[arg=n2,val=Nat(v2)], _*) -> {
            //if(`(f!=f1 && f!=f2)) {
            if(`(n1+1==n && n2+2==n) || `(n2+1==n && n1+2==n)) {
              int modulo = (`v1+`v2)%1000000;
              WM.remove(`f);
              WM.add(0,`Fib(n,Nat(modulo)));
              if(opt) {
                WM.remove(`(n2+2==n)?`f2:`f1);
              }
              fire++;
              return true;
            }
            //}

          }
        }
	    }
    }
    return false;
  }

  public boolean occursFib(ArrayList WM, int value) {
    %match(Space WM) {
	    concElement(_*, Fib[arg=n], _*) -> {
        if(`n == value) {
          return true;
        }
	    }
    }
    return false;
  }

  public int result(ArrayList WM, int value) {
    %match(Space WM) {
	    concElement(_*, Fib[arg=n, val=Nat(v)], _*) -> {
        if(`n == value) {
          return `v;
        }
	    }
    }
    return 0;
  }

}
