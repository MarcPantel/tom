package lemu;

import lemu.sequents.*;
import lemu.sequents.types.*;

import tom.library.sl.*;

public class ProofChecker {
  %include { sequents/sequents.tom }
  %include { sl.tom }

  %strategy ReplaceVar(var:Term, new_term:Term) extends `Fail() {
    visit Term {
      v@Var[] -> { if (`v == var) return new_term; }
    }
  }

  %strategy ReplaceFreeVars(var: Term, new_term: Term) extends `Fail() {
    visit Prop {
      relationAppl(r, tl) -> {
        TermList res = (TermList) `mu(MuVar("x"),Choice(ReplaceVar(var,new_term),All(MuVar("x")))).visit(`tl);
        return `relationAppl(r, res);
      }
      r@(forall|exists)(x,_) -> { 
        if  (`Var(x) == var)  return `r; 
      }
    }
  }

  %op Strategy replaceFreeVars(var:Term, new_term:Term) {
    make(var,new_term) {
      `mu(MuVar("x"),Choice(ReplaceFreeVars(var,new_term),All(MuVar("x"))))
    }
  }

  private static Visitable tryVisit(Strategy s, Visitable v) {
    Visitable res = null;
    try { res = s.visit(v); return res; }
    catch (VisitFailure e) { return v; }
  }

  public static boolean boundedInContext(Term var, Context ctxt) {
    return tryVisit(`replaceFreeVars(var,Var("@notavar@")),ctxt) == ctxt; 
  }

  public static boolean proofcheck(Tree term) {
    %match(Tree term) {
      /* rule(type, premisses, conclusion, focussed proposition) */

      // propositional fragment
      rule(axiomInfo[],_,sequent((_*,x,_*),(_*,x,_*)),_) -> {
        return true;
      }
      rule(topInfo[],_,sequent(_,(_*,top(),_*)),_) -> {
        return true;
      }
      rule(bottomInfo[],_,sequent((_*,bottom(),_*),_),_) -> {
        return true;
      }
      rule(
          andLeftInfo[],
          (p@rule(_,_,sequent((g1*,A,B,g2*), d),_)),
          sequent((g1*,a,g2*),d),
          a@and(A,B)
          )
        -> { return proofcheck(`p);}
      rule(
          andRightInfo[],
          (p1@rule(_,_,sequent(g,(d1*,A,d2*)),_),p2@rule(_,_,sequent(g,(d1*,B,d2*)),_)),
          sequent(g,(d1*,a,d2*)),
          a@and(A,B)
          )
        -> { return (proofcheck(`p1) && proofcheck(`p2)); }
      rule(
          orRightInfo[],
          (p@rule(_,_,sequent(g, (d1*,A,B,d2*)),_)),
          sequent(g, (d1*,a,d2*)),
          a@or(A,B)
          )
        -> { return proofcheck(`p);}
      rule(
          orLeftInfo[],
          (p1@rule(_,_,sequent((g1*,A,g2*), d),_),p2@rule(_,_,sequent((g1*,B,g2*), d),_)),
          sequent((g1*,a,g2*), d),
          a@or(A,B)
          )
        -> { return (proofcheck(`p1) && proofcheck(`p2)); }
      rule(
          impliesRightInfo[],
          (p@rule(_,_,sequent((g*,A), (d1*, B, d2*)),_)),
          sequent(g, (d1*,a,d2*)),
          a@implies(A,B)
          )
        -> { return proofcheck(`p);}
      rule(
          impliesLeftInfo[],
          (p1@rule(_,_,sequent((g1*,g2*),(A,d*)),_), p2@rule(_,_,sequent((g1*,B,g2*),d),_)), 
          sequent((g1*,a,g2*),d),
          a@implies(A,B)
          )
        -> { return (proofcheck(`p1) && proofcheck(`p2)); } 
      rule(
          contractionLeftInfo[],
          (p@rule(_,_,sequent((g1*,a,a,g2*), d),_)),
          sequent((g1*,a,g2*),d),
          a
          )
        -> { return proofcheck(`p);}
      rule(
          contractionRightInfo[],
          (p@rule(_,_,sequent(g,(d1*,a,a,d2*)),_)),
          sequent(g,(d1*,a,d2*)),
          a
          )
        -> { return proofcheck(`p); }
      rule(
          weakLeftInfo[],
          (p@rule(_,_,sequent((g1*,g2*), d),_)),
          sequent((g1*,a,g2*),d),
          a
          )
        -> { return proofcheck(`p);}
      rule(
          weakRightInfo[],
          (p@rule(_,_,sequent(g,(d1*,d2*)),_)),
          sequent(g,(d1*,a,d2*)),
          a
          )
        -> { return proofcheck(`p); }
      /* the syntax could be improved when the bug #3786 is fixed */ 
      rule(
          cutInfo(a),
          (p1@rule(_,_,sequent((g*),(d*,a)),_), p2@rule(_,_,sequent((g*,a),(d*)),_)),
          sequent((g*),(d*)),
          _
          )
        -> { return proofcheck(`p1) && proofcheck(`p2); }

      // first order logic
      rule(
          forallRightInfo(new_var),
          (p@rule(_,_,sequent(g,(d1*,B,d2*)),_)), 
          sequent(g,(d1*,a,d2*)),
          a@forall(v,A)
          )
        -> {
          if (`tryVisit(replaceFreeVars(Var(v),new_var),A) == `B && boundedInContext(`new_var,`context(d1*,d2*,g*)))  
            return proofcheck(`p); 
        }

      rule(
          forallLeftInfo(new_term),
          (p@rule(_,_,sequent((g1*,B,g2*),d),_)), 
          sequent((g1*,a,g2*),d),
          a@forall(v,A)
          )
        -> { if (`tryVisit(replaceFreeVars(Var(v),new_term),A) == `B) return proofcheck(`p); } 

      rule(
          existsRightInfo(new_term),
          (p@rule(_,_,sequent(g,(d1*,B,d2*)),_)), 
          sequent(g,(d1*,a,d2*)),
          a@exists(v,A)
          )
        -> { if (`tryVisit(replaceFreeVars(Var(v),new_term),A) == `B) return proofcheck(`p); 
        } 

      rule(
          existsLeftInfo(new_var),
          (p@rule(_,_,sequent((g1*,B,g2*),d),_)), 
          sequent((g1*,a,g2*),d),
          a@exists(v,A)
          )
        -> {
          if (`tryVisit(replaceFreeVars(Var(v),new_var),A) == `B && boundedInContext(`new_var,`context(g1*,g2*,d*)))
            return proofcheck(`p); 
        } 

      // error case
      x -> {
        System.out.println(`x);
        try {PrettyPrinter.display(`x);}
        catch (Exception e) {};
        return false;
      }
    }
    return false;
  }
}
