package lemu;

import lemu.sequents.*;
import lemu.sequents.types.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Map;
import java.util.Collection;

import tom.library.sl.*;

import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Utils {

  %include { sequents/sequents.tom }
  %include { sl.tom }
  %typeterm StringCollection { implement {Collection<String>} is_sort(t) { t instanceof Collection} }
  %typeterm Collection { implement {Collection} is_sort(t) { t instanceof Collection} }
  %typeterm StringTermMap{ implement { Map<String,Term> } is_sort(t) { t instanceof Map} }


  public static Prop forAllList(StringList l, Prop p) {
    Prop res = p;
    %match(StringList l) {
      (_*,v,_*) -> { res = `forAll(v,res); }
    }
    return res;
  }

  public static Prop existsList(StringList l, Prop p) {
    Prop res = p;
    %match(StringList l) {
      (_*,v,_*) -> { res = `exists(v,res); }
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
        forAll(n,p1) -> {
          if(old_term != `Var(n)) {
            if (new_term.getVars().contains(`n)) {
              Term fv = freshVar(`n,nonfresh);
              Prop np1 = `replaceFreeVars(p1,Var(n),fv,nonfresh);
              nonfresh.add(`n);
              Prop res = `forAll(fv.getname(),replaceFreeVars(np1,old_term,new_term,nonfresh));
              nonfresh.remove(`n);
              return res;
            } else {
              return `forAll(n,replaceFreeVars(p1,old_term,new_term,nonfresh));
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

  %strategy ReplaceFreeVars(old_term: Term, new_term: Term) extends `Identity() {
    // FIXME : encore utile ?? surement pour l'expansion
    visit RuleType {
      forAllRightInfo(t) -> {
        if (`t==old_term) return `forAllRightInfo(new_term);
      }
      forAllLeftInfo(t) -> {
        if (`t==old_term) return `forAllLeftInfo(new_term);
      }
      existsRightInfo(t) -> {
        if (`t==old_term) return `existsRightInfo(new_term);
      }
      existsLeftInfo(t) -> {
        if (`t==old_term) return `existsLeftInfo(new_term);
      }
    }

    visit Prop {
      p -> { return `replaceFreeVars(p,old_term,new_term); }
    }
  }

  %strategy CollectFreeVars(StringCollection res) extends `Fail() {
    visit Prop {
      p -> { res.addAll(`p.getFreeVars()); return `p; }
    }
  }

  public static Set<String> getFreeVars(sequentsAbstractType t) {
    Set<String> res = new HashSet();
    try { `mu(MuVar("x"),Choice(CollectFreeVars(res),All(MuVar("x")))).visit(t); }
    catch(VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }
    return res;
  }

  public static sequentsAbstractType 
    replaceFreeVars(sequentsAbstractType p, Term old_term, Term new_term) 
    {
      Strategy v = `TopDown(ReplaceFreeVars(old_term, new_term));
      try { p = (sequentsAbstractType) v.visit(`p); }
      catch (VisitFailure e) { e.printStackTrace(); throw new RuntimeException(); }

      return  p; 
    }

  %strategy VarCollector(vars:StringCollection) extends `Fail() {
    visit Term {
      t -> { vars.addAll(`t.getVars()); return `t; }
    }
  }

  public static Set<String> collectVars(sequentsAbstractType t) {
    HashSet set = new HashSet();
    Strategy v = `mu(MuVar("x"),Choice(VarCollector(set),All(MuVar("x"))));
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

  private static char[] append(char[] buf, int index, char c) {
    if (index >= buf.length) {
      char[] newbuf = new char[Math.max(index+1,buf.length*2)]; 
      System.arraycopy(buf, 0, newbuf, 0, buf.length);
      buf = newbuf;
    }
    buf[index] = c;
    return buf;
  }

  private static class InputRes {
    public InputRes(char[] buf, int size) {
      this.buf = buf; this.size = size;
    }
    public char[] buf = null;
    public int size = 0;
  }

  // handling user input
  private static InputRes getInput() throws IOException {
    char[] buf = new char[1024];
    int index = 0;
    char c = 0;
    char last = 0;

    while (c != '.' && c != -1) {
      last = c;
      c = (char) stream.read();
      append(buf,index++,c);
      if (last == '/' && c == '/') {
        while(c != '\n') {
          last = c;
          c = (char) stream.read();
          append(buf,index++,c);
        }
      } else if (last == '/' && c == '*') {
        while( ! (last == '*' && c == '/')) {
          last = c;
          c = (char) stream.read();
          append(buf,index++,c);
        }
      }
    }
    return new InputRes(buf,index);
  }

  // IO functions 

  private static InputStream stream = System.in;

  public static void setStream(InputStream newStream) {
    stream = newStream;
  }

  public static Prop getProp() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.start1_return root = parser.start1();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.pred();
  }

  public static Term getTerm() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.start2_return root = parser.start2();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.term();
  }

  public static Command getCommand() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.command_return root = parser.command();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.command();
  }

  public static ProofCommand getProofCommand() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.proofcommand_return root = parser.proofcommand();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.proofcommand();
  }

  // FIXME : get rid of "ident" in parser and use lexer directly
  public static String getIdent() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.ident_return root = parser.ident();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.ident();
  }

//C est nous
  public static Sig getSig() throws RecognitionException, IOException {
    InputRes input = getInput();
    CharStream cinput = new ANTLRStringStream(input.buf,input.size);
    SeqLexer lex = new SeqLexer(cinput);
    CommonTokenStream tokens = new CommonTokenStream(lex);
    SeqParser parser = new SeqParser(tokens);
    SeqParser.rule1_return root = parser.rule1();
    CommonTreeNodeStream nodes = new CommonTreeNodeStream((org.antlr.runtime.tree.Tree)root.tree);
    SeqWalker walker = new SeqWalker(nodes);
    return walker.rule1();
  }
//On a fini

}

