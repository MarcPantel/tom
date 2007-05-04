/* Generated by TOM (version 2.5alpha): Do not edit this file */package tom.library.sl;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;

public class Mu extends AbstractStrategy {
  public final static int VAR = 0;
  public final static int V = 1;

  private MuStrategyTopDown muStrategyTopDown;
  private boolean expanded = false;
  public Mu(Strategy var, Strategy v) {
    initSubterm(var, v);
    muStrategyTopDown = new MuStrategyTopDown();
  }

  public final Visitable visitLight(Visitable any) throws VisitFailure {
    if(!expanded) { muExpand(); }
    return visitors[V].visitLight(any);
  }

  public int visit() {
    if(!expanded) { muExpand(); }
    return visitors[V].visit();
  }

  private boolean isExpanded() {
    return ((MuVar)visitors[VAR]).isExpanded();
  }

  public void muExpand() {
    try {
      muStrategyTopDown.init();
      muStrategyTopDown.visitLight(this);
      expanded = true;
    } catch (VisitFailure e) {
      System.out.println("mu reduction failed");
    }
  }
  
}

/**
 * Custom TopDown strategy which realizes the mu expansion.
 * The visit method seeks all Mu and MuVar nodes.
 *
 * When a Mu node is matched, it is pushed on a stack. Then child nodes are
 * visited and finally, the Mu node is popped.
 *
 * When a MuVar node is matched, then the stack is browsed to find the
 * corresponding Mu (the last pushed with the same variable name). The MuVar is
 * then expanded.
 *
 * When the current node is not a Mu or a MuVar, we visit all children of the
 * current node.
 */
class MuStrategyTopDown {
  private static boolean tom_equal_term_MuStrategy(Object t1, Object t2) { return 


t1.equals(t2);}private static boolean tom_is_sort_MuStrategy(Object t) { return  t instanceof tom.library.sl.Strategy ;}private static boolean tom_is_fun_sym_Mu( tom.library.sl.Strategy  t) { return 




 (t instanceof tom.library.sl.Mu) ;}private static  tom.library.sl.Strategy  tom_get_slot_Mu_s1( tom.library.sl.Strategy  t) { return 

 (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Mu.VAR) ;}private static  tom.library.sl.Strategy  tom_get_slot_Mu_s2( tom.library.sl.Strategy  t) { return 
 (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Mu.V) ;}private static boolean tom_equal_term_MuStrategyString(Object t1, Object t2) { return 





t1.equals(t2);}private static boolean tom_is_sort_MuStrategyString(Object t) { return  t instanceof String ;}private static boolean tom_is_fun_sym_MuVar( tom.library.sl.Strategy  t) { return 



 (t instanceof tom.library.sl.MuVar) ;}private static  String  tom_get_slot_MuVar_var( tom.library.sl.Strategy  t) { return 

 ((tom.library.sl.MuVar)t).getName() ;}


  private LinkedList stack;

  public MuStrategyTopDown() {
    stack = new LinkedList();
  }

  public void init() {
    stack.clear();
  }

  public void visitLight(Visitable any) throws VisitFailure {
    visitLight(any,null,0,new HashSet());
  }

  /**
   * @param any the node we visit
   * @param parent the node we come from
   * @param childNumber the n-th subtemr of parent
   * @param set of already visited parent
   */
  private void visitLight(Visitable any, Visitable parent, int childNumber, HashSet set) throws VisitFailure {
    /* check that the current element has not already been expanded */
    if(set.contains(any)) {
      return;
    } else {
      set.add(parent);
    }

    if (tom_is_sort_MuStrategy(any)) {{  tom.library.sl.Strategy  tomMatch264NameNumberfreshSubject_1=(( tom.library.sl.Strategy )any);{  tom.library.sl.Strategy  tomMatch264NameNumber_freshVar_0=tomMatch264NameNumberfreshSubject_1;if (tom_is_fun_sym_Mu(tomMatch264NameNumber_freshVar_0)) {{  tom.library.sl.Strategy  tomMatch264NameNumber_freshVar_2=tom_get_slot_Mu_s1(tomMatch264NameNumber_freshVar_0);{  tom.library.sl.Strategy  tomMatch264NameNumber_freshVar_3=tom_get_slot_Mu_s2(tomMatch264NameNumber_freshVar_0);{  tom.library.sl.Strategy  tomMatch264NameNumber_freshVar_1=tomMatch264NameNumber_freshVar_2;if (tom_is_fun_sym_MuVar(tomMatch264NameNumber_freshVar_1)) {{  tom.library.sl.Strategy  tom_m=tomMatch264NameNumber_freshVar_0;if ( true ) {

        stack.addFirst(tom_m);
        visitLight(tomMatch264NameNumber_freshVar_3,tom_m,0,set);
        visitLight(tomMatch264NameNumber_freshVar_1,null,0,set);
        stack.removeFirst();
        return;
      }}}}}}}}{  tom.library.sl.Strategy  tomMatch264NameNumber_freshVar_4=tomMatch264NameNumberfreshSubject_1;if (tom_is_fun_sym_MuVar(tomMatch264NameNumber_freshVar_4)) {{  String  tomMatch264NameNumber_freshVar_5=tom_get_slot_MuVar_var(tomMatch264NameNumber_freshVar_4);if ( true ) {


        MuVar muvar = (MuVar)tomMatch264NameNumber_freshVar_4;
        if(!muvar.isExpanded()) {
          Iterator it = stack.iterator();
          while(it.hasNext()) {
            Mu m = (Mu)it.next();
            if(((MuVar)m.visitors[Mu.VAR]).getName().equals(tomMatch264NameNumber_freshVar_5)) {
              //System.out.println("MuVar: setInstance " + `n );
              muvar.setInstance(m);
              if(parent!=null) {
                /* shortcut: the MuVar is replaced by a pointer 
                 * to the 2nd child of Mu
                 * after expansion there is no more Mu or MuVar
                 */
                //System.out.println("parent: " + parent);
                //System.out.println("childNumber: " + childNumber);
                //System.out.println("V: " + m.visitors[Mu.V]);
                parent.setChildAt(childNumber,m.visitors[Mu.V]);
              } else {
                //System.out.println("strange: " + `var);
              }
              return;
            }
          }
          //System.out.println("MuVar: " + `n + " not found");
          throw new VisitFailure();
        }
      }}}}}}


    int childCount = any.getChildCount();
    for(int i = 0; i < childCount; i++) {
      visitLight(any.getChildAt(i),any,i,set);
    }
  }
}
