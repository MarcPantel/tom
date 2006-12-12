/* Generated by TOM (version 2.5alpha): Do not edit this file */package tom.library.sl;

import java.util.LinkedList;
import java.util.Iterator;

public class Mu extends AbstractStrategy {
  public final static int VAR = 0;
  public final static int V = 1;

  private MuStrategyTopDown muStrategyTopDown;
  private boolean expanded =false;
  public Mu(Strategy var, Strategy v) {
    initSubterm(var, v);
    muStrategyTopDown = new MuStrategyTopDown();
  }

  public final jjtraveler.Visitable visit(jjtraveler.Visitable any) throws jjtraveler.VisitFailure {
    if(!expanded)
      muExpand();
    return visitors[V].visit(any);
  }

  public void visit() {
    if(!expanded)
      muExpand();
    visitors[V].visit();
  }

  private boolean isExpanded() {
    return ((MuVar)visitors[VAR]).isExpanded();
  }

  public void muExpand() {
    try {
      muStrategyTopDown.init();
      muStrategyTopDown.visit(this);
      expanded = true;
    } catch (jjtraveler.VisitFailure e) {
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
  private static boolean tom_terms_equal_MuStrategy(Object t1, Object t2) {  return 

t1.equals(t2) ;}private static boolean tom_is_fun_sym_Mu( tom.library.sl.Strategy  t) {  return 




 (t instanceof tom.library.sl.Mu)  ;}private static  tom.library.sl.Strategy  tom_get_slot_Mu_s1( tom.library.sl.Strategy  t) {  return 

 (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Mu.VAR)  ;}private static  tom.library.sl.Strategy  tom_get_slot_Mu_s2( tom.library.sl.Strategy  t) {  return 
 (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Mu.V)  ;}private static boolean tom_terms_equal_MuStrategyString(Object t1, Object t2) {  return 




t1.equals(t2) ;}private static boolean tom_is_fun_sym_MuVar( tom.library.sl.Strategy  t) {  return 



 (t instanceof tom.library.sl.MuVar)  ;}private static  String  tom_get_slot_MuVar_var( tom.library.sl.Strategy  t) {  return 

 ((tom.library.sl.MuVar)t).getName()  ;}


  private LinkedList stack;

  public MuStrategyTopDown() {
    stack = new LinkedList();
  }
  public void init() {
    stack.clear();
  }

  public void visit(jjtraveler.Visitable any) throws jjtraveler.VisitFailure {
     if(any instanceof  tom.library.sl.Strategy ) { { tom.library.sl.Strategy  tom_match1_1=(( tom.library.sl.Strategy )any); if ( ( tom_is_fun_sym_Mu(tom_match1_1) ||  false  ) ) { { tom.library.sl.Strategy  tom_match1_1_s1=tom_get_slot_Mu_s1(tom_match1_1); { tom.library.sl.Strategy  tom_match1_1_s2=tom_get_slot_Mu_s2(tom_match1_1); if ( ( tom_is_fun_sym_MuVar(tom_match1_1_s1) ||  false  ) ) { { String  tom_match1_1_s1_var=tom_get_slot_MuVar_var(tom_match1_1_s1); { tom.library.sl.Strategy  tom_var=tom_match1_1_s1; { tom.library.sl.Strategy  tom_v=tom_match1_1_s2; {boolean tom_match1_tom_anti_constraints_status= true ; { tom.library.sl.Strategy  tom_m=tom_match1_1; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {

        stack.addFirst(tom_m);
        visit(tom_v);
        visit(tom_var);
        stack.removeFirst();
        return;
       } } } } } } } } } } } if ( ( tom_is_fun_sym_MuVar(tom_match1_1) ||  false  ) ) { { String  tom_match1_1_var=tom_get_slot_MuVar_var(tom_match1_1); { String  tom_n=tom_match1_1_var; {boolean tom_match1_tom_anti_constraints_status= true ; { tom.library.sl.Strategy  tom_var=tom_match1_1; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {


        MuVar muvar = (MuVar)tom_var;
        if(!muvar.isExpanded()) {
          Iterator it = stack.iterator();
          while(it.hasNext()) {
            Mu m = (Mu)it.next();
            if(((MuVar)m.visitors[Mu.VAR]).getName().equals(tom_n)) {
              //System.out.println("MuVar: setInstance " + `n );
              muvar.setInstance(m);
              return;
            }
          }
          //System.out.println("MuVar: " + `n + " not found");
          throw new jjtraveler.VisitFailure();
        }
       } } } } } } } } }


    int childCount = any.getChildCount();
    for(int i = 0; i < childCount; i++) {
      visit(any.getChildAt(i));
    }
  }
}
