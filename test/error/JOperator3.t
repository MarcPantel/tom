%typeterm type1 {
  implement { String }
  get_fun_sym(t) { t }
  cmp_fun_sym(s1,s2) { s1.equals(s2) }
  get_subterm(t,n) { null }
  equals(t1,t2) { t1.equals(t2) }
}

%op type1 op(type:type1, type1) {
  fsym { fzero }
  make(t1, t2, t3) { factory.makeAppl(fzero) }// Bad number of arguments in make(...) macro-function: 3 arguments found but 2 expected in symbol definition (line11)
  is_fsym(t) { ((((ATermAppl)t).getAFun()) == fzero)  }
  get_slot(type,t) { t.getType() }
}
