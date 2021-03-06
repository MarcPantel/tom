import strategy.term.types.*;
import tom.library.sl.*;
import java.util.*;
class Strategy {
%include { sl.tom }
%include { java/util/types/Collection.tom }

  %gom {
    module Term
    abstract syntax

    T = | f(x1:T, x2:T) 
        | g(x1:T) 
        | a()
        | b()
        | c()
  }
 
  public final static void main(String[] args) {
    T t = `f(g(a()),f(g(b()),g(a())));
    Collection bag = new HashSet();

    try {
      T result = (T) `TopDown(Collect(bag)).visit(t);
      System.out.println("bag = " + bag);
      System.out.println("result = " + result);
    } catch(VisitFailure e) {
      System.out.println("failure on " + t);
    }
  }
  
  // Replace a by b
  // ReplaceAB : a -> b
  %strategy ReplaceAB() extends Fail() {
    visit T {
      a() -> { return `b(); }
    }
  }

  %strategy Collect(c:Collection) extends Identity() {
    visit T {
      g(x) -> { c.add(`x); }
    }
  }









  %strategy TraceAB() extends Identity() {
    visit T {
      x -> { System.out.println("Application de a->b: " + getEnvironment().getPosition());  }
    }
  }
 
}
