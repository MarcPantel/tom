package lemu;

import lemu.sequents.*;
import lemu.sequents.types.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Map;
import java.util.Collection;

import tom.library.sl.*;

public class Utils {

  %include { sequents/sequents.tom }
  %include { sl.tom }
  %typeterm StringCollection { implement {Collection<String>} }
  %typeterm Collection { implement {Collection} }
  %typeterm StringTermMap{ implement { Map<String,Term> } }

  /** 
   * for convenience 
   * forallList((x,y),p) -> forall(x,forall(y,p)) 
   */
  public static Prop forallList(StringList l, Prop p) {
    Prop res = p;
    %match(StringList l) {
      strlist(_*,v,_*) -> { res = `forall(v,res); }
    }
    return res;
  }

  /** 
   * for convenience 
   * existsList((x,y),p) -> exists(x,exists(y,p)) 
   */
  public static Prop existsList(StringList l, Prop p) {
    Prop res = p;
    %match(StringList l) {
      strlist(_*,v,_*) -> { res = `exists(v,res); }
    }
    return res;
  }

  %strategy ReplaceTerm(old_term: Term, new_term: Term) extends `Identity() {
    visit Term {
      x -> { if (`x == old_term) return new_term; }
    }
  }

  public static sequentsAbstractType replaceTerm(sequentsAbstractType subject, 
      Term old_term, Term new_term) 
  {
    Strategy v = `ReplaceTerm(old_term, new_term);
    sequentsAbstractType res = null;
    try { res = (sequentsAbstractType) `TopDown(v).visit(subject); }
    catch (VisitFailure e ) { e.printStackTrace(); throw new RuntimeException(); }
    return res;
  }


  // several vars in one pass  
  %strategy ReplaceVars(map:StringTermMap) extends `Identity() {
    visit Term {
      Var(x) -> { 
        Term res = map.get(`x);
        if (res != null) {
          return res;
        }
      }
    }
  }

  public static sequentsAbstractType 
    replaceVars(sequentsAbstractType subject, Map<String,Term> map) 
    {
      Strategy v = `ReplaceVars(map);
      sequentsAbstractType res = null;
      try { res = (sequentsAbstractType) `TopDown(v).visit(subject); }
      catch (VisitFailure e ) { e.printStackTrace(); throw new RuntimeException(); }
      return res;
    }

  private static Prop 
    replaceFreeVars(Prop p, Term old_term, Term new_term, Set<String> nonfresh) {
      %match(Prop p) {
        forall(n,p1) -> {
          if(old_term != `Var(n)) {
            if (new_term.getVars().contains(`n)) {
              Term fv = freshVar(`n,nonfresh);
              Prop np1 = `replaceFreeVars(p1,Var(n),fv,nonfresh);
              nonfresh.add(`n);
              Prop res = `forall(fv.getname(),replaceFreeVars(np1,old_term,new_term,nonfresh));
              nonfresh.remove(`n);
              return res;
            } else {
              return `forall(n,replaceFreeVars(p1,old_term,new_term,nonfresh));
            }
          } else  return p;
        }
        exists(n,p1) -> {
          if(old_term != `Var(n)) {
            if (new_term.getVars().contains(`n)) {
              Term fv = freshVar(`n,nonfresh);
              Prop np1 = `replaceFreeVars(p1,Var(n),fv,nonfresh);
              nonfresh.add(`n);
              Prop res = `exists(fv.getname(), replaceFreeVars(np1,old_term,new_term,nonfresh));
              nonfresh.remove(`n);
              return res;
            } else {
              return `exists(n,replaceFreeVars(p1,old_term,new_term,nonfresh));
            }
          } else return p;
        }
        relationAppl(r,tl) -> {
          TermList res = (TermList) replaceTerm(`tl, old_term, new_term);
          return `relationAppl(r, res);
        }
        and(p1,p2) -> {
          return `and(replaceFreeVars(p1,old_term,new_term,nonfresh), 
              replaceFreeVars(p2,old_term,new_term,nonfresh));
        }
        or(p1,p2) -> {
          return `or(replaceFreeVars(p1,old_term,new_term,nonfresh), 
              replaceFreeVars(p2,old_term,new_term,nonfresh));
        }
        implies(p1,p2) -> {
          return `implies(replaceFreeVars(p1,old_term,new_term,nonfresh), 
              replaceFreeVars(p2,old_term,new_term,nonfresh));
        }
      }
      return p; 
    }

  public static Prop 
    replaceFreeVars(Prop p, Term old_term, Term new_term) {
      Set<String> nonfresh = p.getFreeVars();
      nonfresh.addAll(new_term.getVars());
      return replaceFreeVars(p, old_term, new_term, nonfresh);
    }

  %strategy ReplaceFreeVars(defaul:Strategy,old_term: Term, new_term: Term) extends defaul {
    visit Prop {
      p -> { return `replaceFreeVars(p,old_term,new_term); }
    }
  }

  %strategy CollectFreeVars(defaultcase:Strategy, res:StringCollection) extends defaultcase {
    visit Prop {
      p -> { res.addAll(`p.getFreeVars()); return `p; }
    }
  }

  public static Set<String> getFreeVars(sequentsAbstractType t) {
    Set<String> res = new HashSet();
    try { `mu(MuVar("x"),CollectFreeVars(All(MuVar("x")),res)).visit(t); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    return res;
  }

  public static sequentsAbstractType 
    replaceFreeVars(sequentsAbstractType p, Term old_term, Term new_term) 
    {
      Strategy v = `mu(MuVar("x"),ReplaceFreeVars(All(MuVar("x")),old_term,new_term)); 
      try { p = (sequentsAbstractType) v.visit(`p); }
      catch (VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
      return  p; 
    }

  %strategy VarCollector(defaultcase:Strategy, vars:StringCollection) extends defaultcase {
    visit Term {
      t -> { vars.addAll(`t.getVars()); }
    }
  }

  public static Set<String> collectVars(sequentsAbstractType t) {
    HashSet set = new HashSet();
    Strategy v = `mu(MuVar("x"),VarCollector(All(MuVar("x")),set));
    try { v.visit(t); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    return set;
  }


  public static Term freshVar(String x, sequentsAbstractType term) {
    Set<String> set = collectVars(term);
    return freshVar(x,set);
  }

  public static Term freshVar(String x, Set<String> set) {
    int i = 0;
    while(true) {
      String s = x + i;
      if (!set.contains(s))
        return `Var(s);
      else
        i++;
    }
  }

  %strategy CollectConstraints(set: Collection) extends `Identity() {
    visit Term {
      x@FreshVar[] -> { set.add(`x); }
    }
  }

  public static HashSet getSideConstraints(sequentsAbstractType list) {
    HashSet set = new HashSet();
    try {
      `TopDown(CollectConstraints(set)).visit(list);
    } catch (VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    return set;
  }

  %strategy CollectNewVars(set: Collection) extends `Identity() {
    visit Term {
      x@NewVar[] -> { set.add(`x); }
    }
  }

  public static HashSet getNewVars(sequentsAbstractType seq) {
    HashSet set = new HashSet();
    try {
      `TopDown(CollectNewVars(set)).visit(seq);
    } catch (VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    return set;
  }

  public static boolean isClosed(sequentsAbstractType t) {
    return getFreeVars(t).isEmpty();
  }

  private static PropRule theoremToPropRewriteRuleRec(Prop th) throws Exception {
    %match (th) {
      forall(x,p) -> { return `theoremToPropRewriteRuleRec(p); }
      and(implies(a@relationAppl[],b),implies(b,a@relationAppl[]))  -> {
        return `proprule(a,b); 
      }
      and(implies(a,b@relationAppl[]),implies(b@relationAppl[],a))  -> {
        return `proprule(b,a); 
      }
    }
    throw new Exception("Theorem has not the shape of a proposition rewrite rule");
  }


  /** 
   * Converts a theorem into a proposition rewrite rule
   * 
   * @param th Prop of the shape 'forall x1..xn, Atom(x1,..,xn) &lt;&eq;&gt; Phi(x1,..,xn)'
   * @throws Exception if the theorem has a wrong shape
   * @return the rewrite rule is NOT prepared to avoid captures (no @ in front of variables)
   */
  public static PropRule theoremToPropRewriteRule(Prop th) throws Exception {
    if(! isClosed(th)) throw new Exception("The theorem contains free variables");
    else return theoremToPropRewriteRuleRec(th);
  }

  private static TermRule theoremToTermRewriteRuleRec(Prop th) throws Exception {
    %match (th) {
      forall(x,p) -> { return `theoremToTermRewriteRuleRec(p); }
      relationAppl("eq",concTerm(x,y))  -> {
        return `termrule(x,y); 
      }
    }
    throw new Exception("Theorem has not the shape of a term rewrite rule");
  }


  /** 
   * Converts a theorem into a term rewrite rule
   * 
   * @param th Prop of the shape 'forall x1..xn, f(x1,..,xn) &eq; g(x1,..,xn)'
   * @throws Exception if the theorem has a wrong shape
   * @return the rewrite rule is NOT prepared to avoid captures (no @ in front of variables)
   */
  public static TermRule theoremToTermRewriteRule(Prop th) throws Exception {
    if(! isClosed(th)) throw new Exception("The theorem contains free variables");
    else return theoremToTermRewriteRuleRec(th);
  }

}

