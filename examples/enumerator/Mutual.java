package enumerator;

import java.math.BigInteger;
import tom.library.enumerator.*;

import enumerator.mutual.types.*;

public class Mutual {

  public static void main(String args[]) {
    /**
     * this example use the Gom generator for the signature
     * A = a() | foo(b:B) | hoo(a:A, b:B)
     * B = b() | grr(a:A)
     * 
     * just call A.getEnumeration() to enumerate sort A
     */

    System.out.println("START");
    int n = 5;

    testEnumerator(Combinators.makeInt(), "int", 5);
    //testEnumerator(Combinators.makeString(), "String", 5);
    testEnumerator(A.getEnumeration(), "A", 5);
    testEnumerator(B.getEnumeration(), "B", 5);
   
    Enumeration<A> enumA = A.getEnumeration(); 
    System.out.println("#trees of size 500 = card(parts[500]) = " + enumA.parts().index(BigInteger.valueOf(500)).getCard());
    Enumeration<B> enumB = B.getEnumeration();
    System.out.println("#trees of size 500 = card(parts[500]) = " + enumB.parts().index(BigInteger.valueOf(500)).getCard());

    
/*
    LazyList<Finite<A>> parts = enumA.parts();
    for (int i = 0; i < 5 && !parts.isEmpty(); i++) {
      System.out.println(i + " --> " + parts.head());
      parts = parts.tail();
    }
    for (int p = 0; p < 10000; p = p + 100) {
      BigInteger i = java.math.BigInteger.TEN.pow(p);
      System.out.println("10^" + p + " --> " + enumA.get(i).toString().length());
    }
*/

  }
  
  private static void testEnumerator(Enumeration<?> e,String title, int n) {
	  System.out.println("Enumerator for " + title);
	    for (int i = 0; i < n; i++) {
	      System.out.println("Get " + i + "th term: " + e.get(BigInteger.valueOf(i)));
	    }
  }

}
