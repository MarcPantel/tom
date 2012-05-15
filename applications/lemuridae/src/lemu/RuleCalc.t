package lemu;

import lemu.sequents.*;
import lemu.sequents.types.*;

//import tom.library.strategy.mutraveler.MuTraveler;
//import tom.library.strategy.mutraveler.MuStrategy;
//import jjtraveler.VisitFailure;
//import jjtraveler.reflective.VisitableVisitor;
import tom.library.sl.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import java.io.*;
import antlr.*;
import antlr.collections.*;

//import tom.library.strategy.mutraveler.Position;

/**
 * usage : RuleCalc rc = new RuleCalc(rulelist);
 *         Prop permutability_problem = rc.getProblem();
 *         while((permutability_problem = rc.getProblem()) != null) {
 *            // ask user for a name
 *            rc.run(name);
 *         }
 *         RuleList res = rc.getResult();
 **/
public class RuleCalc {

  %include { sequents/sequents.tom }
  %include { sl.tom }
  %include { util/types/Collection.tom }


  private RuleList result = `rlist();
  private PropRuleList pruleList = null;
  private Position lastPos = null; // innermost problematic forall/exists
  private Prop lastProp = null;
  private PropRule lastRule = null;

  public RuleCalc(Prop atom, Prop p) {
    pruleList = `proprulelist(proprule(atom,p));
    run();
  }

  %strategy ReplaceProp(old_prop: Prop, new_prop: Prop) extends `Identity() {
    visit Prop {
      x -> {
        if (`x == old_prop) return new_prop;
      }
    }
  }

  public void run() {
    lastProp = null;
    lastPos = null;
    lastRule = null;

    // looks for permutability problems
    %match(PropRuleList pruleList) {
      proprulelist(r1*, r@proprule(_,prop) ,r2*) -> {
        lastPos = forallNeg(`prop, 0, 1, Position.makeFromArray(new int[0]));
        if(lastPos != null) {
          // removing lastRule from the rules
          pruleList = `proprulelist(r1*,r2*);
          // removing lastProp from the rule
          lastRule = `r;
          lastProp = `prop;
          return;
        }
      }
    }
  }

  public void run(String name) {
    if (lastProp == null)
      return;

    Prop problematicProp = null, cleanedProp = null;

    try { problematicProp = (Prop) lastPos.getSubterm().visit(lastProp); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    Set<String> freevars = problematicProp.getFreeVars();
    TermList tl = `concTerm();
    for(String var: freevars) {	tl = `concTerm(tl*,Var(var)); }
    Prop newPred = `relationAppl(name,tl);
    try { cleanedProp = (Prop) lastPos.getReplace(newPred).visit(lastProp); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }

    // adding new prop to lastrule
    lastRule = `proprule(lastRule.getlhs(), cleanedProp);
    pruleList = `proprulelist(pruleList*, lastRule, proprule(newPred,problematicProp));

    run();
  }

  public Prop getProblem() {
    if(lastPos != null) {
      try { return (Prop) lastPos.getSubterm().visit(lastProp); }
      catch (VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    }
    return null;
  }

  // to avoid problems with calling getResult twice
  private boolean generated = false;

  public RuleList getResult() {
    if (generated) return result;

    Sequent seq = null;
    Tree partialTree = null;
    SeqList prems = null;
    %match(PropRuleList pruleList) {
      proprulelist(_*,proprule(lhs,rhs),_*) -> {
        // right super rule
        seq = `sequent(context(), context(rhs));
        partialTree = buildTree(`seq);
        prems = collectPremises(partialTree);
        result = `rlist(result*,ruledesc(1,lhs,prems,partialTree));
        // left super rule
        seq = `sequent(context(rhs), context());
        partialTree = buildTree(`seq);
        prems = collectPremises(partialTree);
        result = `rlist(result*,ruledesc(0,lhs,prems,partialTree));
      }
    }
    generated = true;
    return result;
  }


  %strategy CollectPremises(set:Collection) extends `Identity() {
    visit Tree {
      rule(openInfo[],_,concl,_) -> {
        set.add(`concl);
      }
    }
  }

  private static SeqList collectPremises(Tree t) {
    LinkedList<Sequent> list = new LinkedList<Sequent>();
    try { `TopDown(CollectPremises(list)).visit(t); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    SeqList result = `concSeq();
    for(Sequent seq: list) {
      result = `concSeq(result*,seq);
    }
    return result;
  }

  private static Tree buildTree(Sequent seq) {
    Set<String> set = Utils.collectVars(seq);
    return buildTree(seq,set);
  }

  private static Tree buildTree(Sequent seq, Set<String> freevars) {
    %match(seq) {
      // axiom
      s@sequent(context(_*,p,_*),context(_*,p,_*)) -> {
        return `rule(axiomInfo(), premisses(), s, p) ;
      }
      // bottom
      s@sequent(context(_*,b@bottom(),_*),_) -> {
        return `rule(bottomInfo(), premisses(), s, b);
      }
      // top
      s@sequent(_,context(_*,t@top(),_*)) -> {
        return `rule(topInfo(), premisses(), s, t);
      }

      // and R
      s@sequent(g,context(u*,x@and(a,b),v*)) -> {
        Tree t1 = buildTree(`sequent(g,context(u*,a,v*)),freevars);
        Tree t2 = buildTree(`sequent(g,context(u*,b,v*)),freevars);
        return `rule(andRightInfo(),premisses(t1,t2),s,x);
      }

      // or R
      s@sequent(g,context(u*,x@or(a,b),v*)) -> {
        Tree t = buildTree(`sequent(g,context(u*,a,b,v*)),freevars);
        return `rule(orRightInfo(),premisses(t),s,x);
      }

      // => R
      s@sequent(g,context(u*,x@implies(a,b),v*)) -> {
        Tree t = buildTree(`sequent(context(g*,a),context(u*,b,v*)),freevars);
        return `rule(impliesRightInfo(),premisses(t),s,x);
      }

      // and L
      s@sequent(context(u*,x@and(a,b),v*),d) -> {
        Tree t = buildTree(`sequent(context(u*,a,b,v*),d),freevars);
        return `rule(andLeftInfo(),premisses(t),s,x);
      }

      // => L
      s@sequent(context(u*,x@implies(a,b),v*),d) -> {
        Tree t1 = buildTree(`sequent(context(u*,v*),context(a,d*)),freevars);
        Tree t2 = buildTree(`sequent(context(u*,b,v*),d),freevars);
        return `rule(impliesLeftInfo(),premisses(t1,t2),s,x);
      }

      // or L
      s@sequent(context(u*,x@or(a,b),v*),d) -> {
        Tree t1 = buildTree(`sequent(context(u*,a,v*),d),freevars);
        Tree t2 = buildTree(`sequent(context(u*,b,v*),d),freevars);
        return `rule(orLeftInfo(),premisses(t1,t2),s,x);
      }

      // and L
      s@sequent(context(u*,x@and(a,b),v*),d) -> {
        Tree t = buildTree(`sequent(context(u*,a,b,v*),d),freevars);
        return `rule(andLeftInfo(),premisses(t),s,x);
      }

      // forall R
      s@sequent(g,context(u*,x@forall(n,a),v*)) -> {
        String new_n = Utils.freshVar(`n,freevars).getname();
        Term fresh_v = `FreshVar(new_n,n);
        freevars.add(fresh_v.getname());
        Prop new_a = (Prop) Utils.replaceFreeVars(`a,`Var(n), fresh_v);
        Tree t = buildTree(`sequent(g,context(u*,new_a,v*)),freevars);
        return `rule(forallRightInfo(fresh_v),premisses(t),s,x);
      }

      // exists R
      s@sequent(g,context(u*,x@exists(n,a),v*)) -> {
        String new_n = Utils.freshVar(`n,freevars).getname();
        Term new_v = `NewVar(new_n,n);
        freevars.add(new_v.getname());
        Prop new_a = (Prop) Utils.replaceFreeVars(`a,`Var(n), new_v);
        Tree t = buildTree(`sequent(g,context(u*,new_a,v*)),freevars);
        return `rule(existsRightInfo(new_v),premisses(t),s,x);
      }

      // forall L
      s@sequent(context(u*,x@forall(n,a),v*),d) -> {
        String new_n = Utils.freshVar(`n,freevars).getname();
        Term new_v = `NewVar(new_n,n);
        freevars.add(new_v.getname());
        Prop new_a = (Prop) Utils.replaceFreeVars(`a,`Var(n), new_v);
        Tree t = buildTree(`sequent(context(u*,new_a,v*),d),freevars);
        return `rule(forallLeftInfo(new_v),premisses(t),s,x);
      }

      // exists L
      s@sequent(context(u*,x@exists(n,a),v*),d) -> {
        String new_n = Utils.freshVar(`n,freevars).getname();
        Term fresh_v = `FreshVar(new_n,n);
        freevars.add(new_n);
        Prop new_a = (Prop) Utils.replaceFreeVars(`a,`Var(n), fresh_v);
        Tree t = buildTree(`sequent(context(u*,new_a,v*),d),freevars);
        return `rule(existsLeftInfo(fresh_v),premisses(t),s,x);
      }

      // open leaves
      s -> {
        return `rule(openInfo(),premisses(),s,nullProp());
      }
    }
    return null;
  }


  // last : 0 = undefined, -1 = negative, 1 = positive
  private static Position forallNeg(Prop p, int last, int current, Position pos) {
    %match(Prop p) {
      (relationAppl | top | bottom) [] -> { return null; }

      (and | or) (p1,p2) -> {
        pos = pos.down(1);
        Position pos1 = forallNeg(`p1, last, current, pos);
        if (pos1 != null) return pos1;
        pos = pos.up();
        pos = pos.down(2);
        pos1 = forallNeg(`p2, last, current, pos);
        if (pos1 != null) return pos1;
        pos = pos.up();
      }

      implies(p1,p2) -> {
        pos = pos.down(1);
        Position pos1 = forallNeg(`p1, last, -current, pos);
        if (pos1 != null) return pos1;
        pos = pos.up();
        pos = pos.down(2);
        pos1 = forallNeg(`p2, last, current, pos);
        if (pos1 != null) return pos1;
        pos = pos.up();
      }

      forall(_,p1) -> {
        if(current*last >= 0) {
          pos = pos.down(2);
          return forallNeg(`p1,current,current,pos);
        }
        else return pos;
      }

      exists(_,p1) -> { 
        if(current*last > 0)
          return pos;
        else {
          pos = pos.down(2);
          return forallNeg(`p1,current,current,pos); 
        }
      }
    }
    return null;
  }
}

