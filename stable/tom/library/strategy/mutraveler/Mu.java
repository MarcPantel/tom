/* Generated by TOM (version 2.4alpha): Do not edit this file */
package tom.library.strategy.mutraveler;

import tom.library.strategy.mutraveler.AbstractMuStrategy;
import jjtraveler.Visitable;
import jjtraveler.reflective.VisitableVisitor;
import jjtraveler.VisitFailure;

import java.util.LinkedList;
import java.util.ListIterator;

public class Mu extends AbstractMuStrategy {
  public final static int VAR = 0;
  public final static int V = 1;

  public Mu(VisitableVisitor var, VisitableVisitor v) {
    initSubterm(var, v);
  }

  public Visitable visit(Visitable any) throws VisitFailure {
    if(!isExpanded()) {
      expand();
    }
    return getArgument(V).visit(any);
  }

  private boolean isExpanded() {
    return ((MuVar)getArgument(VAR)).isExpanded();
  }

  private void expand() {
    try {
      new MuTopDown().visit(this);
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
 * visited and finally, the Mu node is poped.
 *
 * When a MuVar node is matched, then the stacked is browsed to find the
 * corresponding Mu (the last pushed with the same variable name). The MuVar is
 * expanded.
 *
 * When the current node is not a Mu or a MuVar, we visit all children of the
 * current node.
 */
class MuTopDown {
  /* Generated by TOM (version 2.4alpha): Do not edit this file */private static boolean tom_terms_equal_Strategy(Object t1, Object t2) {  return t1.equals(t2) ;}/* Generated by TOM (version 2.4alpha): Do not edit this file */private static boolean tom_is_fun_sym_Mu( tom.library.strategy.mutraveler.MuStrategy  t) {  return  (t instanceof tom.library.strategy.mutraveler.Mu)  ;}private static  tom.library.strategy.mutraveler.MuStrategy  tom_get_slot_Mu_s1( tom.library.strategy.mutraveler.MuStrategy  t) {  return  (tom.library.strategy.mutraveler.MuStrategy)t.getChildAt(tom.library.strategy.mutraveler.Mu.VAR)  ;}private static  tom.library.strategy.mutraveler.MuStrategy  tom_get_slot_Mu_s2( tom.library.strategy.mutraveler.MuStrategy  t) {  return  (tom.library.strategy.mutraveler.MuStrategy)t.getChildAt(tom.library.strategy.mutraveler.Mu.V)  ;}private static boolean tom_is_fun_sym_MuVar( tom.library.strategy.mutraveler.MuStrategy  t) {  return  (t instanceof tom.library.strategy.mutraveler.MuVar)  ;}private static String tom_get_slot_MuVar_var( tom.library.strategy.mutraveler.MuStrategy  t) {  return  ((tom.library.strategy.mutraveler.MuVar)t).getName()  ;}  

  private LinkedList stack;

  public MuTopDown() {
    stack = new LinkedList();
  }

  public void visit(Visitable any) throws VisitFailure {
     if(any instanceof  tom.library.strategy.mutraveler.MuStrategy ) { { tom.library.strategy.mutraveler.MuStrategy  tom_match1_1=(( tom.library.strategy.mutraveler.MuStrategy )any); if (tom_is_fun_sym_Mu(tom_match1_1) ||  false ) { { tom.library.strategy.mutraveler.MuStrategy  tom_m=tom_match1_1; { tom.library.strategy.mutraveler.MuStrategy  tom_match1_1_s1=tom_get_slot_Mu_s1(tom_match1_1); { tom.library.strategy.mutraveler.MuStrategy  tom_match1_1_s2=tom_get_slot_Mu_s2(tom_match1_1); if (tom_is_fun_sym_MuVar(tom_match1_1_s1) ||  false ) { { tom.library.strategy.mutraveler.MuStrategy  tom_var=tom_match1_1_s1; {String tom_match1_1_s1_var=tom_get_slot_MuVar_var(tom_match1_1_s1); { tom.library.strategy.mutraveler.MuStrategy  tom_v=tom_match1_1_s2; if ( true ) {

        stack.addFirst(tom_m);
        visit(tom_v);
        visit(tom_var);
        stack.removeFirst();
        return;
       } } } } } } } } } if (tom_is_fun_sym_MuVar(tom_match1_1) ||  false ) { { tom.library.strategy.mutraveler.MuStrategy  tom_var=tom_match1_1; {String tom_match1_1_var=tom_get_slot_MuVar_var(tom_match1_1); {String tom_n=tom_match1_1_var; if ( true ) {


        MuVar muvar = (MuVar)tom_var;
        if(!muvar.isExpanded()) {
          ListIterator it = stack.listIterator(0);
          while(it.hasNext()) {
            Mu m = (Mu)it.next();
            if(((MuVar)m.getArgument(Mu.VAR)).getName().equals(tom_n)) {
              muvar.setInstance(m);
              return;
            }
          }

          throw new VisitFailure();
        }
       } } } } } } }


    int childCount = any.getChildCount();
    for(int i = 0; i < childCount; i++) {
      visit(any.getChildAt(i));
    }
  }
}

