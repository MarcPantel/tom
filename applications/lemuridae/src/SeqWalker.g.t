//header {
//package parsingtests;
//}

{
	import sequents.*;
	import sequents.types.*;
}

//{{{ class SeqTreeWalker extends TreeParser;
class SeqTreeParser extends TreeParser;

options {
	importVocab=SeqParser;
}

{
  %include { sequents/sequents.tom }
}

/*
seq returns [Sequent s]
{
  Context ctxt;
  Context concl;
  s = null;  
}
  : SEQ concl=list_pred { s = `sequent(context(),concl); }
  | ctxt=list_pred SEQ concl=list_pred { s = `sequent(ctxt,concl); }
  ;

list_pred returns [Context c]
{
	c = `context();
	Context left;
	Prop p;
}
  : p = pred { c = `context(p,c*); }
  | #(LIST left=list_pred p=pred) { c = `context(left*,p); }
  ; 
*/

pred returns [Prop p]
    { 
      Prop a,b;
      TermList l;
      p = null;
      Term t1,t2;
    }
      : i:ID { p = `relationAppl(i.getText(), concTerm()); } 
      | BOTTOM { p = `bottom(); }
      | TOP { p = `top(); }
      | #(EQUIV a=pred b=pred )  { p = `and(implies(a,b),implies(b,a)); }
      | #(IMPL a=pred b=pred )  { p = `implies(a,b); }
      | #(OR a=pred b=pred ) { p = `or(a,b); }
      | #(AND a=pred b=pred ) {  p = `and(a,b); }
      | #(NOT a=pred ) {p = `implies(a,bottom());}
      | #(LPAREN ap:ID l=term_list) { p = `relationAppl(ap.getText(), l); }
      | #(FORALL x:ID a=pred) { p = `forAll(x.getText(),a); }
      | #(EXISTS y:ID a=pred) { p = `exists(y.getText(),a); }
      ;

term_list returns [TermList l] 
    {
       l = `concTerm();
       TermList left;
       Term t;
    }
    : t = term { l = `concTerm(t,l*); }
    | #(COMMA left=term_list t=term) {  l = `concTerm(left*,t); }
    | VOIDLIST {}
    ;

term returns [Term t] 
    {
        t = null;
        TermList l;
        Term t1,t2;
    }
    : v:ID { t = `Var(v.getText());  }
    | i:NUMBER {
        try { t = PrettyPrinter.intToPeano(Integer.parseInt(i.getText())); }
        catch (NumberFormatException e) { e.printStackTrace(); } 
      }
    | #(TIMES t1=term t2=term) { t = `funAppl("mult",concTerm(t1,t2)); }
    | #(PLUS t1=term t2=term) { t = `funAppl("plus",concTerm(t1,t2)); }
    | #(DIV t1=term t2=term) { t = `funAppl("div",concTerm(t1,t2)); }
    | #(MINUS t1=term t2=term) { t = `funAppl("minus",concTerm(t1,t2)); }
    | #(LPAREN f:ID l=term_list) { t = `funAppl(f.getText(),l); }
    ;


command returns [Command c] 
  {
    c = null;
    Prop r,l;
    Term lhs,rhs;
  }
  : #(PROOF i1:ID l=pred) { c = `proof(i1.getText(),l); }
  | #(RRULE l=pred r=pred) { c = `rewritesuper(l,r); }
  | #(PRULE l=pred r=pred) { c = `rewriteprop(l,r); }
  | #(PROP l=pred) { c = `normalizeProp(l); }
  | #(TERM lhs=term) { c = `normalizeTerm(lhs); }
  | #(TRULE lhs=term rhs=term) { c = `rewriteterm(lhs,rhs); }
  | #(DISPLAY i2:ID) { c = `display(i2.getText()); }
  | #(PROOFTERM i7:ID) { c = `proofterm(i7.getText()); }
  | QUIT { c = `quit(); }
  | REINIT { c = `reinit(); }
  | #(PROOFCHECK i4:ID) { c = `proofcheck(i4.getText()); }
  | #(PRINT i3:ID) { c = `print(i3.getText()); }
  | #(RESUME i5:ID) { c = `resume(i5.getText()); }
  | GIBBER { c = `gibber(); }
  | #(IMPORT i6:PATH) { c = `importfile(i6.getText()); }
  | EOF { c = `endoffile(); }
  ;

proofcommand returns [ProofCommand c]
  {
    c = null;
    Prop p;
  }
  : i:ID { c = `proofCommand(i.getText()); }
  | #(FOCUS v:ID) {c = `focusCommand(v.getText()); }
  | #(RRULE n:NUMBER) {c = `ruleCommand(Integer.parseInt(n.getText())); }
  | RULEALONE { c = `ruleCommand(-1);}
  | #(CUT p=pred) { c = `cutCommand(p); }
  | #(THEOREM name:ID) { c = `theoremCommand(name.getText()); }
  | NORMALIZE { c = `normalizeSequent(); }
  | DISPLAY { c = `proofCommand("display"); }
  | ASKRULES { c = `askrulesCommand(); }
  | QUIT { c = `proofquit(); }
  | REINIT { c = `proofreinit(); }
  | ABORT { c = `abort(); }
  | EOF { c = `proofendoffile(); }
  ;

ident returns [String s]
  {
    s = null;
  }
  : i:ID { s = i.getText(); }
  ;

