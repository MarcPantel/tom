/*
 *   
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2005, INRIA
 * Nancy, France.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 * 
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 * Antoine Reilles
 *
 **/

package jtom.verifier;

import jtom.*;
import aterm.*;
import java.util.*;
import tom.library.traversal.*;
import jtom.tools.SymbolTable;
import jtom.adt.tomsignature.types.*;
import jtom.verifier.il.*;
import jtom.verifier.il.types.*;

public class Verifier extends TomBase {

  // ------------------------------------------------------------
  %include { adt/tomsignature/TomSignature.tom }
  %include { il/il.tom }
  // ------------------------------------------------------------


  protected jtom.verifier.il.ilFactory factory;
  private SymbolTable symbolTable;
  private boolean camlsemantics = false;

  public Verifier() {
    super();
    factory = ilFactory.getInstance(getTomSignatureFactory().getPureFactory());
  }

  public void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  public boolean isCamlSemantics() {
    return camlsemantics;
  }

  protected final ilFactory getIlFactory() {
    return factory;
  }

  public Term build_TermFromTomTerm(TomTerm tomterm) {
    %match(TomTerm tomterm) {
      ExpressionToTomTerm(expr) -> {
        return `build_TermFromExpression(expr);
      }
      Variable(options,name,type,constraints) -> {
        return `build_Term_from_TomName(name);
      }
      _ -> {
        System.out.println("build_TermFromTomTerm don't know how to handle this: " + tomterm);
        return `repr("foirade");
      }
    }
  }

  Variable build_Variable_from_TomName(TomName name) {
    %match(TomName name) {
      Name(stringname) -> {
        return `var(stringname);
      }
      PositionName(numberlist) -> {
        return `var(numberlist.toString());
      }
      EmptyName() -> {
        return `var("emptyName");
      }
    }
    return `var("error while building variable name");
  }

  Term build_Term_from_TomName(TomName name) {
    return `tau(absvar(build_Variable_from_TomName(name)));
  }

  public Term build_TermFromExpression(Expression expression) {
    %match(Expression expression) {
      GetSubterm(codomain,Variable[astName=name], Number(index)) -> {
        // we will need to find the head symbol
        Term term = build_Term_from_TomName(name);
        return `subterm(fsymbol("empty"),term,index);
      }
      GetSlot(codomain,Name(symbolName),slotName,Variable[astName=name]) -> {
        Term term = build_Term_from_TomName(name);
        return `slot(fsymbol(symbolName),term,slotName);
      }
      TomTermToExpression(Variable[astName=name]) -> {
        Term term = build_Term_from_TomName(name);
        return `term;
      }
      Cast(type,expr) -> {
        return build_TermFromExpression(expr);
      }
      _ -> {
        System.out.println("build_TermFromExpression don't know how to handle this: " + expression);
        return `repr("autre foirade avec " + expression);
      }
    }
  }
  
  public String extract_Name(NameList nl) {
    %match(NameList nl) {
      (Name(name)) -> {
        return `name;
      }
    }
    return nl.toString();
  }

  public Expr build_ExprFromExpression(Expression expression) {
    %match(Expression expression) {
      TrueTL()  -> { return `true(); }
      FalseTL() -> { return `false(); }
      EqualFunctionSymbol(type,Variable[astName=name],Appl[nameList=symbolName]) -> {
        Term term = build_Term_from_TomName(name);
        return `isfsym(term,fsymbol(extract_Name(symbolName)));
      }
      EqualFunctionSymbol(type,term1,Appl[nameList=symbolName]) -> {
        return `isfsym(build_TermFromTomTerm(term1),fsymbol(extract_Name(symbolName)));
      }
      EqualTerm(type,t1,t2) -> {
        return `eq(build_TermFromTomTerm(t1),build_TermFromTomTerm(t2));
      }
      _ -> {
        System.out.println("build_ExprFromExpression don't know how to handle this: " + expression);
        return `false();
      }
    }
  }

  public Instr build_InstrFromInstructionList(InstructionList instrlist) {
    InstrList list = `semicolon();
    while (!instrlist.isEmpty()) {
      Instruction i = (Instruction) instrlist.getHead();
      instrlist = instrlist.getTail();
      if (!i.isCheckStamp()) {
        list = `semicolon(list*,build_InstrFromAutomata(i));
      }
    }
    return `sequence(list);
  }

  public Instr build_InstrFromAutomata(Instruction automata) {
    %match(Instruction automata) {
      TypedAction(action,positivePatterns,negativePatterns) -> {
        return `accept(positivePatterns,negativePatterns);
      }

      If(cond,ift,iff) -> {
        return `ITE(build_ExprFromExpression(cond),
                    build_InstrFromAutomata(ift),
                    build_InstrFromAutomata(iff));
      }
      Let(Variable[astName=avar],expr,body) -> {
        Variable thevar = build_Variable_from_TomName(avar);
        return `ILLet(thevar,
                      build_TermFromExpression(expr),
                      build_InstrFromAutomata(body));
      }
      LetAssign(Variable[astName=avar],expr,body) -> {
        Variable thevar = build_Variable_from_TomName(avar);
        return `ILLet(thevar,
                      build_TermFromExpression(expr),
                      build_InstrFromAutomata(body));
      }
      (Let|LetAssign)(UnamedVariable[],expr,body) -> {
        return build_InstrFromAutomata(`body);
      }
      CompiledPattern(patterns,instr) -> {
        return build_InstrFromAutomata(`instr);
      }
      AbstractBlock(concInstruction(CheckStamp[],instr)) -> {
        return build_InstrFromAutomata(`instr);
      }
      AbstractBlock(concInstruction(instrlist*)) -> {
        return build_InstrFromInstructionList(`instrlist);
      }
      Nop() -> {
        // tom uses nop in the iffalse part of ITE
        return `refuse();
      }
      _ -> {
        System.out.println("build_InstrFromAutomata don't know how to handle this : " + automata);
        return `refuse();
      }
    }
  }

  public Collection build_tree(Instruction automata) {
    // DerivTree tree = null;
    // System.out.println("Build derivation tree for: " + automata);

    Environment startingenv = `env(subs(),
                                 build_InstrFromAutomata(automata));
    Collection localAccepts = collect_accept(automata);

    Iterator iter = localAccepts.iterator();
    Collection tree_list = new HashSet();
    while(iter.hasNext()) {
        Instr localAccept = (Instr) iter.next();

        Deriv startingderiv = `ebs(startingenv,
                                   env(subs(undefsubs()),localAccept));

        // System.out.println("The derivation: " + startingderiv);

        Collection tree_list_pre = apply_sem_rules(startingderiv);
        // replace substitutions in trees
        Iterator it = tree_list_pre.iterator();
        while(it.hasNext()) {
            DerivTree tree = (DerivTree) it.next();
            SubstitutionList outputsubst = collect_subst(tree);
            tree = replaceUndefSubst(tree,outputsubst);
            tree_list.add(tree);
        }
    }

    return tree_list;
  }
  
  private Collect2 collect_subst = new Collect2() {
      public boolean apply(ATerm subject, Object astore) {
        SubstRef outsubst = (SubstRef) astore;
        if (subject instanceof Deriv) {
          %match(Deriv subject) {
            ebs(env(e,accept[]),env(subs(undefsubs()),accept[])) -> {
              outsubst.set(`e);
            }
            
            // default rule
            _ -> {
              return true;
            }
          }//end match
        } else { 
          return true;
        }
      }//end apply
    }; //end new
  
  public SubstitutionList collect_subst(DerivTree subject) {
    SubstRef output = new SubstRef();
    traversal().genericCollect(subject,collect_subst,output);
    return output.get();
  }

  private Collect2 collect_accept = new Collect2() {
      public boolean apply(ATerm subject, Object astore) {
        Collection store = (Collection)astore;
        if (subject instanceof Instruction) {
          %match(Instruction subject) {
            TypedAction(action,positive,negative)  -> {
              store.add(`accept(positive,negative));
            }
            
            // default rule
            _ -> {
              return true;
            }
          }//end match
        } else { 
          return true;
        }
      }//end apply
    }; //end new
  
  public Collection collect_accept(Instruction subject) {
    Collection result = new HashSet();
    traversal().genericCollect(subject,collect_accept,result);
    return result;
  }
  

/**
 * The axioms the mapping has to verify
 */
  
  protected Seq build_dedterm(Term sp) {
    // TODO : implement the \mapequiv relation
    TermList ded = `concTerm(sp);
    %match(Term sp) {
      appSubsT[] -> { 
        TermList follow = apply_termRules(replaceVarsInTerm(sp));
        ded = `concTerm(ded*,follow*); 
      }
    }

    // System.out.println("dedterm gives : " + ded);
    return `dedterm(concTerm(ded*));
  }

  protected ExprList build_dedexpr(Expr sp) {
    ExprList ded = `concExpr(sp);
    %match(Expr sp) {
      appSubsE[] -> { 
        ExprList follow = apply_exprRules(replaceVarsInExpr(sp));
        ded = `concExpr(ded*,follow*); 
      }
    }

    // System.out.println("dedexpr gives: " + ded);
    return `ded;
  }

  // need to be reworked : this IS a BAD way to do it !
  protected TermList apply_termRules(Term trm) {
    %match(Term trm) {
      tau[] -> {
        return `concTerm(trm);
      }
      subterm(s,t@subterm[],index) -> {
        // first reduce the argument
        TermList reduced = apply_termRules(`t);
        TermList res = `concTerm(trm);
        while(!reduced.isEmpty()) {
          Term head = reduced.getHead();
          if (head.isTau()) {
            TermList hl = apply_termRules(head);
            while(!hl.isEmpty()) {
              Term h = hl.getHead();
              res = `concTerm(res*,subterm(s,h,index));           
              hl = hl.getTail();
            }
          } else {
            res = `concTerm(res*,subterm(s,head,index));
          }
          reduced = reduced.getTail();
        }
        return `concTerm(res*);
      }
      slot(s,t@slot[],slotName) -> {
        // first reduce the argument
        TermList reduced = apply_termRules(`t);
        TermList res = `concTerm(trm);
        while(!reduced.isEmpty()) {
          Term head = reduced.getHead();
          if (head.isTau()) {
            TermList hl = apply_termRules(head);
            while(!hl.isEmpty()) {
              Term h = hl.getHead();
              res = `concTerm(res*,slot(s,h,slotName));           
              hl = hl.getTail();
            }
          } else {
            res = `concTerm(res*,slot(s,head,slotName));
          }
          reduced = reduced.getTail();
        }
        return `concTerm(res*);
      }
      subterm(s,tau(t),index) -> {
        // we shall test if term t has symbol s 
        AbsTerm term = `st(s,t,index);
        return `concTerm(trm,tau(term));
      }
      slot(s,tau(t),slotName) -> {
        // we shall test if term t has symbol s 
        AbsTerm term = `sl(s,t,slotName);
        return `concTerm(trm,tau(term));
      }
      _ -> { 
        System.out.println("apply TermRules : nothing applies to:" + trm);
        return `concTerm(trm); }
    }
  }

  protected ExprList apply_exprRules(Expr ex) {
    %match(Expr ex) {
      eq(tau(tl),tau(tr)) -> {
        return `concExpr(ex,teq(tl,tr));
      }
      isfsym(tau(t),symbol) -> {
        return `concExpr(ex,tisfsym(t,symbol));
      }
      eq(lt,rt) -> {
        // first reduce the argument
        Term reducedl = ((TermList)apply_termRules(`lt).reverse()).getHead();
        Term reducedr = ((TermList)apply_termRules(`rt).reverse()).getHead();

        ExprList taill = `apply_exprRules(eq(reducedl,reducedr));
        ExprList res = `concExpr(ex,taill*);
      }
      isfsym(t,symbol) -> {
        // first reduce the argument
        TermList reduced = apply_termRules(`t);
        ExprList res = `concExpr(ex);
        while(!reduced.isEmpty()) {
          Term head = reduced.getHead();
          res = `concExpr(res*,isfsym(head,symbol));
          reduced = reduced.getTail();
        }
        %match(ExprList res) {
          concExpr(hl*,tail) -> {
            ExprList taill = `apply_exprRules(tail);
            return `concExpr(hl*,taill*);
          }
        }
      }
      true() | false() -> {
        return `concExpr(ex);
      }
      _ -> { 
        System.out.println("apply ExprRules : nothing applies to:" + ex);
        return `concExpr(ex); }
    }
  }

  protected Collection apply_sem_rules(Deriv post) {
    Collection c = new HashSet();
    %match(Deriv post) {
      ebs(env(e,sequence(semicolon(h,t*))),env(subs(undefsubs()),ip)) -> {
        if(instruction_contains(`h,ip)) {
          // continue the derivation
          Deriv up = `ebs(env(e,h),env(subs(undefsubs()),ip));
          Collection pre_list = apply_sem_rules(up);

          Iterator it = pre_list.iterator();
          while(it.hasNext()) {
            DerivTree pre = (DerivTree) it.next();
            c.add(`derivrule("seqa",post,pre,seq()));
          }
        } else {
          // continue the derivation with t
          Deriv up = `ebs(env(e,sequence(t*)),env(subs(undefsubs()),ip));
          Collection post_list = apply_sem_rules(up);

          if(camlsemantics) {
            up = `ebs(env(e,h),env(subs(undefsubs()),refuse()));
            Collection pre_list = apply_sem_rules(up);
            Iterator it = pre_list.iterator();
            while(it.hasNext()) {
              DerivTree pre = (DerivTree) it.next();
              Iterator it2 = post_list.iterator();
              while(it2.hasNext()) {
                DerivTree pre2 = (DerivTree) it2.next();
                c.add(`derivrule2("seqb",post,pre,pre2,seq()));
              }
            }
          } else {
            Iterator it = post_list.iterator();
            while(it.hasNext()) {
              DerivTree pre = (DerivTree) it.next();
              c.add(`derivrule2("seqb",post,endderiv,pre,seq()));
            }
          }
        }
      }
      // let rule
      ebs(env(e,ILLet(x,u,i)),env(subs(undefsubs()),ip)) -> {
        // build condition
        Seq cond = build_dedterm(`appSubsT(e,u));
        // find "t"
        Term t = null;
        %match(Seq cond) {
          dedterm(concTerm(_*,r)) -> { t = `r; }
            _ -> { if (t == null) { 
              System.out.println("build_dedterm has a problem with " + cond);
            }
          }
        }
        Deriv up = `ebs(
            env(subs(e*,is(x,t)),i),
            env(subs(undefsubs()),ip)
            );
        Collection pre_list = apply_sem_rules(up);
        Iterator it = pre_list.iterator();
        while(it.hasNext()) {
          DerivTree pre = (DerivTree) it.next();
          c.add(`derivrule("let",post,pre,cond));
        }
      }
      // iftrue/iffalse rule
      ebs(env(e,ITE(exp,ift,iff)),env(subs(undefsubs()),ip)) -> {
        // build condition
        ExprList cond = build_dedexpr(`appSubsE(e,exp));

        Deriv up = `ebs(env(e,ift),env(subs(undefsubs()),ip));
        String rulename = "iftrue";
        
        Collection pre_list = apply_sem_rules(up);
        Iterator it = pre_list.iterator();
        while(it.hasNext()) {
          DerivTree pre = (DerivTree) it.next();
          c.add(`derivrule(rulename,post,pre,dedexpr(concExpr(cond*,true()))));
        }

        up = `ebs(env(e,iff),env(subs(undefsubs()),ip));
        rulename = "iffalse";
        
        pre_list = apply_sem_rules(up);
        it = pre_list.iterator();
        while(it.hasNext()) {
          DerivTree pre = (DerivTree) it.next();
          c.add(`derivrule(rulename,post,pre,dedexpr(concExpr(cond*,false()))));
        }
      }
      // axioms !
      ebs(env(e,accept[]),env(subs(undefsubs()),accept[])) -> {
        c.add(`derivrule("axiom_accept",post,endderiv(),seq()));
      }
      ebs(env(e,refuse[]),env(subs(undefsubs()),refuse[])) -> {
        c.add(`derivrule("axiom_refuse",post,endderiv(),seq()));
      }
      _ -> { 
        if (c.isEmpty()) {
            //System.out.println("Ratai ! " + post);
        }
      }
    }
    return c;
  }

  protected boolean instruction_contains(Instr i, Instr goal) {
    Collect3 collect_find = new Collect3() {
      public boolean apply(ATerm subject, Object astore, Object arg) {
        Collection c = (Collection) astore;
        Instr lgoal = (Instr) arg;
        if (subject instanceof Instr) {
          if (subject == lgoal) {
            c.add(lgoal);
            return false;
          }
          return true;
        } else { 
          return true;
        }
      }//end apply
    };
    Collection collect = new HashSet();
    traversal().genericCollect(i,collect_find,collect,goal);
    return !collect.isEmpty();
  }

/**
 * To replace undefsubst in tree by the computed value
 * which leads to axiom
 */

  Replace2 replace_undefsubs = new Replace2() {
      public ATerm apply(ATerm subject, Object arg1) {
        if (subject instanceof SubstitutionList) {
          %match(SubstitutionList subject) {
            (undefsubs()) -> {
              return (SubstitutionList)arg1;
            }
          }
        }
        /* Default case : Traversal */
        return traversal().genericTraversal(subject,this,arg1);
      } // end apply
    };

  private DerivTree replaceUndefSubst(DerivTree subject, 
                                      SubstitutionList subs) {
    return (DerivTree) replace_undefsubs.apply(subject,subs);
  }

  private class SubstRef {
    private SubstitutionList sublist;
    public SubstRef() {
      sublist = null;
    }
    public void set(SubstitutionList ssublist) {
      this.sublist = ssublist;
    }
    public SubstitutionList get() {
      return sublist;
    }
  }

/**
 * These functions deals with substitution application
 */

  Replace2 replace_VarbyTerm = new Replace2() {
      public ATerm apply(ATerm subject, Object arg1) {
        if (subject instanceof Term) {
          %match(Term subject) {
            tau(absvar(v@var(name))) -> {
              Map map = (Map) arg1;
              if (map.containsKey(v)) {
                return (Term)map.get(v);
              }
              return (Term)subject;
            }
          }
        } 
        /* Default case : Traversal */
        return traversal().genericTraversal(subject,this,arg1);
      } // end apply
    };

  public Term replaceVarsInTerm(Term subject) {
    %match(Term subject) {
      appSubsT(sublist,term) -> {
        Map map = build_varmap(sublist, new HashMap());
        return (Term) replace_VarbyTerm.apply(term,map);
      }
    }
    return subject;
  }

  public Expr replaceVarsInExpr(Expr subject) {
    %match(Expr subject) {
      appSubsE(sublist,term) -> {
        Map map = build_varmap(sublist, new HashMap());
        return (Expr) replace_VarbyTerm.apply(term,map);
      }
    }
    return subject;
  }

  private Map build_varmap(SubstitutionList sublist, Map map) {
    %match(SubstitutionList sublist) {
      ()                -> { return map; }
      (undefsubs(),t*)  -> { return build_varmap(`t,map);}
      (is(v,term),t*)   -> { 
        map.put(`v,`term);
        return build_varmap(`t,map);
      }
      _ -> { return null; }
    }
  }

  public String pattern_to_string(ATerm patternList, Map map) {
    return pattern_to_string((PatternList) patternList, map);
  }

  public String pattern_to_string(PatternList patternList, Map map) {
    String result = "";
    Pattern h = null;
    PatternList tail = patternList;
    if(!tail.isEmpty()) {
      h = tail.getHead();
      tail = tail.getTail();
      result = pattern_to_string(h,map);
    }

    while(!tail.isEmpty()) {
      h = tail.getHead();
      result = "," + pattern_to_string(h,map);
      tail = tail.getTail();
    }
    return result;
  }

  public String pattern_to_string(Pattern pattern, Map map) {
    String result = "";
    %match(Pattern pattern) {
      Pattern(tomList,guards) -> {
          return pattern_to_string(tomList, map);
      }
    }
    return result;
  }
    public String pattern_to_string(TomList tomList, Map map) {
    String result = "";
    TomTerm h = null;
    TomList tail = tomList;
    if(!tail.isEmpty()) {
      h = tail.getHead();
      tail = tail.getTail();
      result = pattern_to_string(h,map);
    }

    while(!tail.isEmpty()) {
      h = tail.getHead();
      result = "," + pattern_to_string(h,map);
      tail = tail.getTail();
    }
    return result;
  }
  
  public String pattern_to_string(TomTerm tomTerm, Map map) {
    %match(TomTerm tomTerm) {
      Appl(_,concTomName(Name(name),_*),childrens,_) -> {
        if (childrens.isEmpty()) {
          return name;
        } else {
          name = name + "(";
          TomTerm head = childrens.getHead();
          name += pattern_to_string(head,map);
          TomList tail = childrens.getTail();
          while(!tail.isEmpty()) {
            head = tail.getHead();
            name += "," + pattern_to_string(head,map);
            tail = tail.getTail();
          }
          name += ")";
          return name;
        }
      }
      Variable(_,Name(name),_,_) -> {
        if (map.containsKey(`var(name))) {
          System.out.println("In map: "+ map.containsKey(`var(name)));
          return (String) map.get(`var(name));
        } else {
          return name;
        }
      }
      UnamedVariable[] -> {
        return "\\_";
      }
    }
    return "StrangePattern" + tomTerm;
  }

}
