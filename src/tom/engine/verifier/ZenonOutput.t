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
import aterm.pure.*;
import java.util.*;
import tom.library.traversal.*;
import jtom.adt.tomsignature.types.*;
import jtom.verifier.il.*;
import jtom.verifier.il.types.*;
import jtom.verifier.zenon.*;
import jtom.verifier.zenon.types.*;


public class ZenonOutput {

  // ------------------------------------------------------------
  %include { il/Il.tom }
  %include { zenon/Zenon.tom }
  // ------------------------------------------------------------

  protected jtom.verifier.il.IlFactory factory;
  protected jtom.verifier.zenon.ZenonFactory zfactory;
  private GenericTraversal traversal;
  private Verifier verifier;
  private TomIlTools tomiltools;

  public ZenonOutput(Verifier verifier) {
    factory = IlFactory.getInstance(SingletonFactory.getInstance());
    zfactory = ZenonFactory.getInstance(SingletonFactory.getInstance());
    this.traversal = new GenericTraversal();
    this.verifier = verifier;
    this.tomiltools = new TomIlTools(verifier);
  }

  public GenericTraversal traversal() {
    return this.traversal;
  }
  
  protected final IlFactory getIlFactory() {
    return factory;
  }

  protected final ZenonFactory getZenonFactory() {
    return zfactory;
  }

  public Collection zspecSetFromDerivationTreeSet(Collection derivationSet) {
    Collection resset = new HashSet();
    Iterator it = derivationSet.iterator();
    while(it.hasNext()) {
      DerivTree tree = (DerivTree) it.next();
      ZSpec spec = zspecFromDerivationTree(tree);
      resset.add(spec);
    }
    return resset;
  }

  public ZSpec zspecFromDerivationTree(DerivTree tree) {
    
    Map variableset = new HashMap();
    tree = collectProgramVariables(tree,variableset);

    // Use a TreeMap to have the conditions sorted
    Map conditions = new TreeMap();
    collectConstraints(tree,conditions);            
    Map conds = new TreeMap();

    List subjectList = new LinkedList();
    ZExpr pattern = null;
    ZExpr negpattern = null;
    // theorem to prove
    %match(DerivTree tree) {
      derivrule(_,ebs(_,env(subsList,acc@accept(positive,negative))),_,_) -> {
        Map variableMap = ztermVariableMapFromSubstitutionList(subsList, new HashMap());
        pattern = tomiltools.patternToZExpr((Pattern)positive,variableMap);
        tomiltools.getZTermSubjectListFromPattern((Pattern)positive,subjectList,variableMap);
        negpattern = tomiltools.patternToZExpr((PatternList)negative,variableMap);
      }
    }
    
    ZExpr constraints = `ztrue();
    // we consider only the interesting conditions : dedexpr
    Iterator it = conditions.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      Seq value = (Seq) entry.getValue();
      if (value.isDedexpr()) {
        conds.put(((String) entry.getKey()),
                  build_zenon_from_Seq(cleanSeq(value)));
      }
    }
    it = conds.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      ZExpr value = (ZExpr) entry.getValue();
      constraints = `zand(constraints,value);
    }
    ZExpr theorem = null;
    if (pattern != null && constraints != null) {
      if(verifier.isCamlSemantics() && negpattern != null) {
        theorem = `zequiv(zand(pattern,znot(negpattern)),constraints);
      } else {
        theorem = `zequiv(pattern,constraints);
      }
    }

    // now we have to to build the axion list, starting from the
    // signature. Again, the TomIlTools will be useful, it has access
    // to TomSignature and Zenon signature
    
    // collects symbols in pattern
    Collection symbols = tomiltools.collectSymbols(pattern);
    // generates the axioms for this set of symbols
    ZAxiomList symbolsAxioms = tomiltools.symbolsDefinition(symbols);
    // generates axioms for all subterm operations
    ZAxiomList subtermAxioms = tomiltools.subtermsDefinition(symbols);

    Iterator iter = subjectList.iterator();
    while(iter.hasNext()) {
      ZTerm input = (ZTerm)iter.next();
      theorem = `zforall(input,ztype("T"),theorem);  
    }
    ZSpec spec = `zthm(theorem,zby(symbolsAxioms*,subtermAxioms*));

    return spec;
  }


  /**
   * collects all variable names in the DerivTree, and give a name to _'s
   */
  DerivTree collectProgramVariables(DerivTree tree, Map variables) {
    Replace2 programVariablesCollector = new Replace2() {
      public ATerm apply(ATerm subject, Object astore) {
        Map store = (Map) astore;

        if (subject instanceof Variable) {
          %match(Variable subject) {
            var(name) -> {
              String newname = name;
              if (store.containsKey(name)){
                newname = (String) store.get(name);
              } else {
                if (name.startsWith("[") && name.endsWith("]")) {
                  newname = "X_" + store.size();
                }
                store.put(name,newname);
              }
              return `var(newname);
            }
          }
        }
        return traversal().genericTraversal(subject,this,astore);
      }
    };
    return (DerivTree) programVariablesCollector.apply(tree,variables);
  }

  ZTerm ztermFromTerm(Term term) {
    %match(Term term) {
      tau(absTerm) -> {
        return ztermFromAbsTerm(`absTerm);
      }
      repr(name) -> {
        return `zvar("Error in ztermFromTerm repr");
      }
      subterm(s,t,index) -> {
        return `zvar("Error in ztermFromTerm subterm");
      }
      slot(s,t,name) -> {
        return `zvar("Error in ztermFromTerm slot");
      }
      appSubsT(subst,t) -> {
        // probleme: la substitution devrait etre appliquee
        return `zvar("Error in ztermFromTerm appsubsT ");
      }
    }
    return `zvar("match vide dans ztermFromTerm");
  }

  ZExpr zexprFromExpr(Expr expr) {
    %match(Expr expr) {
      true() -> { return `ztrue();}
      false() -> { return `zfalse();}
      isfsym(t,s) -> {
        // this should not occur
        return `zisfsym(zvar("Error in zexprFromExpr"),zsymbol("isfsym"));
      }
      eq(lt,rt) -> {
        // this should not occur
        return `zeq(zvar("Error in zexprFromExpr"),zvar("eq"));
      }
      tisfsym(absterm,s) -> {
        return `zisfsym(ztermFromAbsTerm(absterm),build_zenon_from_symbol(s));
      }
      teq(absterml,abstermr) -> {
        return `zeq(ztermFromAbsTerm(absterml),ztermFromAbsTerm(abstermr));
      }
      appSubsE(subslist,e) -> {
        // this should not occur
        return `zeq(zvar("Error in zexprFromExpr"),zvar("appSubsE"));
      }
    }
    return `zeq(zvar("Error in zexprFromExpr"),zvar("end"));
  }

  ZSymbol build_zenon_from_symbol(Symbol symb) {
    String n = "random";
    %match(Symbol symb) {
      fsymbol(name) -> {
        n = name;
      }
    }
    return `zsymbol(n);
  }

  ZExpr build_zenon_from_Seq(Seq seq) {
    ZExpr result = `ztrue();
    %match(Seq seq) {
      seq() -> { /* nothing */ }
      dedterm(termlist) -> {
        %match(TermList termlist) {
          concTerm(X*,tl,tr) -> {
              result = `zeq(ztermFromTerm(tl),ztermFromTerm(tr));
          }
        }
      }
      dedexpr(exprlist) -> {
        %match(ExprList exprlist) {
          concExpr(X*,t,true()) -> {
              result = zexprFromExpr(`t);
          }
        }
      }
      dedexpr(exprlist) -> {
        %match(ExprList exprlist) {
          concExpr(X*,t,false()) -> {
              result = `znot(zexprFromExpr(t));
          }
        }
      }
    }
    return result;
  }

  ZTerm ztermFromAbsTerm(AbsTerm absterm) {
    %match(AbsTerm absterm) {
      absvar(var(name)) -> {
        return `zvar(name);
      }
      st(s,t,index) -> {
        return `zst(ztermFromAbsTerm(t),index);
      }
      sl(s,t,name) -> {
        return `zsl(ztermFromAbsTerm(t),name);
      }
    }
    return `zvar("Error in ztermFromAbsTerm");
  }

  Seq cleanSeq(Seq seq) {
    %match(Seq seq) {
      seq() -> { return seq; }
      dedterm(concTerm(_*,t,v)) -> {
          return `dedterm(concTerm(t,v));
      }
      /* What happen in the "false" case ? */
      dedexpr(concExpr(_*,t,v)) -> {
        return `dedexpr(concExpr(t,v));
      }
    }
    return seq;
  }

  private Map ztermVariableMapFromSubstitutionList(SubstitutionList sublist, Map map) {
    %match(SubstitutionList sublist) {
      ()                -> { return map; }
      (undefsubs(),t*)  -> { return ztermVariableMapFromSubstitutionList(`t,map);}
      (is(var(name),term),t*)   -> { 
        map.put(`name,ztermFromTerm(`term));
        return ztermVariableMapFromSubstitutionList(`t,map);
      }
      _ -> { return null; }
    }
  }

  public void collectConstraints(DerivTree tree, Map conditions) {
    %match(DerivTree tree) {
      derivrule(name,post,pre,condition) -> {
        String condname = "" + (conditions.size()+1) + "";
        conditions.put(condname,condition);
        collectConstraints(pre,conditions);
      }
      derivrule2(name,post,pre,pre2,condition) -> {
        String condname = "" + (conditions.size()+1) + "";
        conditions.put(condname,condition);
        collectConstraints(pre,conditions);
        collectConstraints(pre2,conditions);
      }
    }
  }

}
