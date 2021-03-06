package lemu2.util;

import lemu2.kernel.proofterms.types.*;
import lemu2.kernel.proofterms.types.rawnamelist.RawnameList;
import lemu2.kernel.proofterms.types.rawconamelist.RawconameList;
import lemu2.kernel.coc.types.*;
import fj.data.List;
import fj.F;

public class Pretty {

  %include { kernel/proofterms/proofterms.tom } 
  %include { kernel/coc/coc.tom } 

  private static String pr(Name var, Prop prop) {
    return churchStyle ? %[@var.gethint() + var.getn()@:@pretty(prop)@]% : %[@var@]%;
  }

  private static String pr(CoName var, Prop prop) {
    return churchStyle ? %[@var.gethint() + var.getn()@:@pretty(prop)@]% : %[@var@]%;
  }

  public static String pretty(LTerm t) {
    %match(t) {
      lvar(Name(i,n)) -> { return `n + `i; }
      lam(Lam(x,ty,u)) -> { return "\u03BB" + %[@`pr(x,ty)@.@`pretty(u)@]%; }
      flam(FLam(x,u)) -> { return "\u03BC" + %[@`x@.@`pretty(u)@]%; }
      activ(Act(x,ty,u)) -> { return "\u03BC" + %[@`pr(x,ty)@.@`pretty(u)@]%; }
      lapp(u@(lapp|fapp|lvar)[],v) -> { 
        return %[@`pretty(u)@ @`pretty(v)@]%; 
      }
      lapp(u,v) -> { return %[(@`pretty(u)@) @`pretty(v)@]%; }
      fapp(u@(lapp|fapp|lvar)[],v) -> { 
        return %[@`pretty(u)@ @`pretty(v)@]%; 
      }
      fapp(u,v) -> { return %[(@`pretty(u)@) @`pretty(v)@]%; }
      pair(u,v) -> { return %[(@`pretty(u)@,@`pretty(v)@)]%; }
      proj1(u) -> { return %[fst @`pretty(u)@]%; }
      proj2(u) -> { return %[snd @`pretty(u)@]%; }
      caseof(u,Alt(x,px,v),Alt(y,py,w)) -> { 
        return %[(case @`pretty(u)@ of (left @`pr(x,px)@) -> @`pretty(v)@ | (right @`pr(y,py)@) -> @`pretty(w)@)]%;
      }
      //letin(Letin(fx,x,px,u,v)) -> { 
      letin(Letin(fx,x,_,u,v)) -> { 
        return %[(let <@`fx@,@`x@> = @`pretty(u)@ in @`pretty(v)@)]%;
      }
      witness(ft,u,p) -> { return %[(<@`pretty(ft)@,@`pretty(u)@>:@`pretty(p)@)]%; }
      left(u,p) -> { return %[left{@`pretty(p)@} @`pretty(u)@]%; }
      right(u,p) -> { return %[right{@`pretty(p)@} @`pretty(u)@]%; }
      passiv(CoName(i,mv),lvar(Name(j,x))) -> { return %[[@`mv+`i@]@`x + `j@]%; }
      passiv(CoName(i,mv),u) -> { return %[[@`mv+`i@](@`pretty(u)@)]%; }
      unit() -> { return "()"; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  /* raw lambda-mu terms */

  private static String pr(String var, RawProp prop) {
    return churchStyle ? %[@var@:@pretty(prop)@]% : %[@var@]%;
  }

  public static String pretty(RawLTerm t) {
    %match(t) {
      Rawlvar(v) -> { return `v; }
      Rawlam(RawLam(x,ty,u)) -> { return "\u03BB" + %[@`pr(x,ty)@.@`pretty(u)@]%; }
      Rawflam(RawFLam(x,u)) -> { return "\u03BC" + %[@`x@.@`pretty(u)@]%; }
      Rawactiv(RawAct(x,ty,u)) -> { return "\u03BC" + %[@`pr(x,ty)@.@`pretty(u)@]%; }
      Rawlapp(u@(Rawlapp|Rawfapp|Rawlvar)[],v) -> { 
        return %[@`pretty(u)@ @`pretty(v)@]%; 
      }
      Rawlapp(u,v) -> { return %[(@`pretty(u)@) @`pretty(v)@]%; }
      Rawfapp(u@(Rawlapp|Rawfapp|Rawlvar)[],v) -> { 
        return %[@`pretty(u)@ @`pretty(v)@]%; 
      }
      Rawfapp(u,v) -> { return %[(@`pretty(u)@) @`pretty(v)@]%; }
      Rawpair(u,v) -> { return %[(@`pretty(u)@,@`pretty(v)@)]%; }
      Rawproj1(u) -> { return %[fst @`pretty(u)@]%; }
      Rawproj2(u) -> { return %[snd @`pretty(u)@]%; }
      Rawcaseof(u,RawAlt(x,px,v),RawAlt(y,py,w)) -> { 
        return %[(case @`pretty(u)@ of (left @`pr(x,px)@) -> @`pretty(v)@ | (right @`pr(y,py)@) -> @`pretty(w)@)]%;
      }
      //Rawletin(RawLetin(fx,x,px,u,v)) -> { 
      Rawletin(RawLetin(fx,x,_,u,v)) -> { 
        return %[(let <@`fx@,@`x@> = @`pretty(u)@ in @`pretty(v)@)]%;
      }
      Rawwitness(ft,u,p) -> { return %[(<@`pretty(ft)@,@`pretty(u)@>:@`pretty(p)@)]%; }
      Rawleft(u,p) -> { return %[left{@`pretty(p)@} @`pretty(u)@]%; }
      Rawright(u,p) -> { return %[right{@`pretty(p)@} @`pretty(u)@]%; }
      Rawpassiv(mv,Rawlvar(x)) -> { return %[[@`mv@]@`x@]%; }
      Rawpassiv(mv,u) -> { return %[[@`mv@](@`pretty(u)@)]%; }
      Rawunit() -> { return "()"; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  /* raw propositions */

  /* precedences :
       forall, exists : 0
       =>             : 1
       /\             : 2
       \/             : 3
       P,False,True   : 4 
  */
  private static String pretty(RawProp p, int prec) {
    %match(p) {
      Rawforall(RawFa(x,p1)) -> { if (prec <= 0) return %[forall @`x@, @`pretty(p1,0)@]%; }
      Rawexists(RawEx(x,p1)) -> { if (prec <= 0) return %[exists @`x@, @`pretty(p1,0)@]%; }
      Rawimplies(p1,p2)      -> { if (prec <= 1) return %[@`pretty(p1,2)@ => @`pretty(p2,1)@]% ; }
      Rawor(p1,p2)           -> { if (prec <= 2) return %[@`pretty(p1,2)@ \/ @`pretty(p2,3)@]%; }
      Rawand(p1,p2)          -> { if (prec <= 3) return %[@`pretty(p1,3)@ /\ @`pretty(p2,4)@]%; }
      Rawbottom()            -> { return "False"; }
      Rawtop()               -> { return "True"; }
      /*-- special cases --*/
      RawrelApp("eq",RawtermList(x,y))  -> { return %[(@`pretty(x)@ = @`pretty(y)@)]%; }
      RawrelApp("gt",RawtermList(x,y))  -> { return %[(@`pretty(x)@ > @`pretty(y)@)]%; }
      RawrelApp("lt",RawtermList(x,y))  -> { return %[(@`pretty(x)@ < @`pretty(y)@)]%; }
      RawrelApp("le",RawtermList(x,y))  -> { return %[(@`pretty(x)@ <= @`pretty(y)@)]%; }
      RawrelApp("in",RawtermList(x,y))  -> { return "(" + `pretty(x) + " \u2208 " + `pretty(y) + ")"; }
      /* -- regular case --*/
      RawrelApp(r,RawtermList())        -> { return `r; }
      RawrelApp(r,x)         -> { return %[@`r@(@`pretty(x)@)]%; }
      p1                     -> { return %[(@`pretty(p1,0)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawProp p) {
    return pretty(p,0);
  }

  /* raw first-order terms */

  public static String pretty(RawTermList tl) {
    %match(tl) {
      RawtermList() -> { return ""; }
      RawtermList(x) -> { return `pretty(x); }
      RawtermList(h,t*) -> { return %[@`pretty(h)@,@pretty(`t)@]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }
  public static String pretty(RawTerm t) {
    %match(t) {
      Rawvar(x) -> { return `x;}
      // arithmetic pretty print
      RawfunApp("z",RawtermList()) -> { return "0"; }
      i@RawfunApp("s",_) -> {
        try { return Integer.toString(peanoToInt(`i));}
        catch (ConversionError e) { }
      }
      RawfunApp("plus",RawtermList(t1,t2)) -> { return %[(@`pretty(t1)@ + @`pretty(t2)@)]%; }
      RawfunApp("mult",RawtermList(t1,t2)) -> { return %[(@`pretty(t1)@ * @`pretty(t2)@)]%; }
      RawfunApp("minus",RawtermList(t1,t2)) -> { return %[(@`pretty(t1)@ - @`pretty(t2)@)]%; }
      RawfunApp("div",RawtermList(t1,t2)) -> { return %[(@`pretty(t1)@ / @`pretty(t2)@)]%; }
      // finite 1st order theory of classes 
      RawfunApp("appl",RawtermList(p,x*)) -> { return %[@`pretty(p)@[@`pretty(x)@]]%; }
      RawfunApp("nil",RawtermList()) -> { return "nil"; }
      l@RawfunApp("cons",RawtermList(x,y)) -> {
        try { return %[<@`prettyList(l)@>]%; }
        catch(ConversionError e) { return %[@`pretty(x)@::@`pretty(y)@]%; }
      }
      // normal case
      RawfunApp(name,x) -> { return %[@`name@(@`pretty(x)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static class ConversionError extends Exception { }

  private static int peanoToInt(RawTerm t) throws ConversionError {
    %match(t) {
      RawfunApp("z",RawtermList()) -> { return 0; }
      RawfunApp("s",RawtermList(n)) -> { return 1+`peanoToInt(n); }
    }
    throw new ConversionError();
  }

  public static String prettyList(RawTerm t) throws ConversionError {
    %match(t) {
      RawfunApp("cons",RawtermList(x,RawfunApp("nil",RawtermList()))) -> { return `pretty(x); }
      RawfunApp("cons",RawtermList(x,y)) -> { return %[@`pretty(x)@,@`prettyList(y)@]%; }
    }
    throw new ConversionError();
  }

  /* -- raw proofterms -- */ 

  private static boolean churchStyle = true;

  public static void setChurchStyle(boolean b) {
    churchStyle = b;
  }

  private static String space(int sp) {
    String res = "";
    for(int i=0; i<sp; i++)
      res += "  ";
    return res;
  }

  private static String pr(String var, RawProp prop, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var@:@pretty(prop)@>]% : %[<@var@>]%);
  }

  private static String pr2(String var, RawProp prop, String fovar, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var@:@pretty(prop)@><@fovar@>]% : %[<@var@><@fovar@>]%);
  }

  private static String pr3(String var1, RawProp prop1, String var2, RawProp prop2, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var1@:@pretty(prop1)@><@var2@:@pretty(prop2)@>]% : %[<@var1@><@var2@>]%);
  }

  private static String pretty(RawProofTerm t, int sp) {
    %match(t) {
      Rawax(n,cn) -> {return %[ax(@`n@,@`cn@)]%; }
      RawfalseL(n) -> { return %[falseL(@`n@)]%; }
      RawtrueR(cn) -> { return %[trueR(@`cn@)]%; }
      Rawcut(RawCutPrem1(cn,pcn,M),RawCutPrem2(n,pn,N)) -> { return %[cut(@`pr(cn,pcn,sp)@ @`pretty(M,sp+1)@,@`pr(n,pn,sp)@ @`pretty(N,sp+1)@)]%; }
      RawfalseL(n) -> { return %[falseL(@`n@)]%; }
      RawfalseL(cn) -> { return %[falseL(@`cn@)]%; }
      RawandR(RawAndRPrem1(a,pa,M),RawAndRPrem2(b,pb,N),cn) -> { return %[andR(@`pr(a,pa,sp)@ @`pretty(M,sp+1)@,@`pr(b,pb,sp)@ @`pretty(N,sp+1)@,@`cn@)]%; }
      RawandL(RawAndLPrem1(x,px,y,py,M),n) -> { return %[andL(@`pr3(x,px,y,py,sp)@ @`pretty(M,sp+2)@,@`n@)]%; }
      RaworR(RawOrRPrem1(a,pa,b,pb,M),cn) -> { return %[orR(@`pr3(a,pa,b,pb,sp)@ @`pretty(M,sp+1)@,@`cn@)]%; }
      RaworL(RawOrLPrem1(x,px,M),RawOrLPrem2(y,py,N),n) -> { return %[orL(@`pr(x,px,sp)@ @`pretty(M,sp+1)@,@`pr(y,py,sp)@ @`pretty(N,sp+1)@,@`n@)]%; }
      RawimplyR(RawImplyRPrem1(x,px,a,pa,M),cn) -> { return %[implyR(@`pr3(x,px,a,pa,sp)@ @`pretty(M,sp+1)@,@`cn@)]%; }
      RawimplyL(RawImplyLPrem1(x,px,M),RawImplyLPrem2(a,pa,N),n) -> { return %[implyL(@`pr(x,px,sp)@ @`pretty(M,sp+1)@,@`pr(a,pa,sp)@ @`pretty(N,sp+1)@,@`n@)]%; }
      RawexistsR(RawExistsRPrem1(a,pa,M),term,cn) -> { return %[existsR(@`pr(a,pa,sp)@ @`pretty(M,sp+1)@,@`pretty(term)@,@`cn@)]%; }
      RawexistsL(RawExistsLPrem1(x,px,fx,M),n) -> { return %[existsL(@`pr2(x,px,fx,sp)@ @`pretty(M,sp+1)@,@`n@)]%; }
      RawforallR(RawForallRPrem1(a,pa,fx,M),cn) -> { return %[forallR(@`pr2(a,pa,fx,sp)@ @`pretty(M,sp+1)@,@`cn@)]%; }
      RawforallL(RawForallLPrem1(x,px,M),term,n) -> { return %[forallL(@`pr(x,px,sp)@ @`pretty(M,sp+1)@,@`pretty(term)@,@`n@)]%; }
      RawrootL(RawRootLPrem1(x,px,M)) -> { return %[@`pr(x,px,sp)@ @`pretty(M,sp+1)@]%; }
      RawrootR(RawRootRPrem1(a,pa,M)) -> { return %[@`pr(a,pa,sp)@ @`pretty(M,sp+1)@]%; }
      RawfoldL(id,RawFoldLPrem1(x,px,M),n) -> { return %[foldL[@`id@](@`pr(x,px,sp)@ @`pretty(M,sp+1)@,@`n@)]%; }
      RawfoldR(id,RawFoldRPrem1(a,pa,M),cn) -> { return %[foldR[@`id@](@`pr(a,pa,sp)@ @`pretty(M,sp+1)@,@`cn@)]%; }
      RawsuperR(id,prems,ts,cn) -> { return %[superR[@`id@](@`pr(prems,sp+1)@,(@`pretty(ts)@),@`cn@)]%; }
      RawsuperL(id,prems,ts,n) -> { return %[superL[@`id@](@`pr(prems,sp+1)@,(@`pretty(ts)@),@`n@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pr(RawSuperPrems prems,int sp) {
    %match(prems) {
      RawPremList()  -> { return ""; }
      RawPremList(x) -> { return `pr(x,sp); }
      RawPremList(x,xs*) -> { return %[@`pr(x,sp)@,@`pr(xs,sp)@]% ; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawGPrem p, int sp) {
    %match(p) {
      RawGPrem(xs,as,fxs,M) -> {
        return %[<@`pr(xs)@><@`pr(as)@><@`pr(fxs)@>@`pretty(M,sp)@]%;
      }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawTyNameList l) { 
    %match(l) {
      RawTyNameList()  -> { return ""; }
      RawTyNameList(x) -> { return `pr(x); }
      RawTyNameList(x,xs*) -> { return %[@`pr(x)@,@`pr(xs)@]% ; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawTyCoNameList l) { 
    %match(l) {
      RawTyCoNameList()  -> { return ""; }
      RawTyCoNameList(x) -> { return `pr(x); }
      RawTyCoNameList(x,xs*) -> { return %[@`pr(x)@,@`pr(xs)@]% ; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawBFoVarList l) { 
    %match(l) {
      RawBFoVarList()  -> { return ""; }
      RawBFoVarList(x) -> { return `x; }
      RawBFoVarList(x,xs*) -> { return %[@`x@,@`pr(xs)@]% ; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawTyName nty) { 
    %match(nty) {
      RawTyName(n,ty) -> { return %[@`n@:@`ty@]%; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pr(RawTyCoName cnty) { 
    %match(cnty) {
      RawTyCoName(cn,ty) -> { return %[@`cn@:@`ty@]%; }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pretty(RawProofTerm t) {
    return pretty(t,0);
  }

  /* -- rewrite rules --*/

  public static String pretty(RawFoBound tl) {
    %match(tl) {
      RawfoBound() -> { return ""; }
      RawfoBound(x) -> { return `x; }
      RawfoBound(h,t*) -> { return %[@`h@,@`pretty(t)@]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawTermRewriteRules tl) {
    %match(tl) {
      Rawtermrrules() -> { return ""; }
      Rawtermrrules(h,t*) -> { return `pretty(h) + "\n" + `pretty(t); }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawTermRewriteRule r) {
    %match(r) {
      Rawtermrrule(id,Rawtrule(b,lhs,rhs)) -> { 
        return %[@`id@ : [@`pretty(b)@] @`pretty(lhs)@ -> @`pretty(rhs)@]%; 
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawPropRewriteRules tl) {
    %match(tl) {
      Rawproprrules() -> { return ""; }
      Rawproprrules(h,t*) -> { return `pretty(h) + "\n" + `pretty(t); }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawPropRewriteRule r) {
    %match(r) {
      Rawproprrule(id,Rawprule(b,lhs,rhs)) -> { 
        return %[@`id@ : [@`pretty(b)@] @`pretty(lhs)@ -> @`pretty(rhs)@]%; 
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /* propositions */

  public static String pretty(Prop p) {
    %match(p) {
      implies(p1@relApp[],p2) -> { return %[@`pretty(p1)@ => @`pretty(p2)@]% ; }
      implies(p1,p2) -> { return %[(@`pretty(p1)@) => @`pretty(p2)@]% ; }
      or(p1@relApp[],p2) -> { return %[@`pretty(p1)@ \/ @`pretty(p2)@]%; }
      or(p1,p2) -> { return %[(@`pretty(p1)@) \/ @`pretty(p2)@]%; }
      and(p1@relApp[],p2) -> { return %[@`pretty(p1)@ /\ @`pretty(p2)@]%; }
      and(p1,p2) -> { return %[(@`pretty(p1)@) /\ @`pretty(p2)@]%; }
      forall(Fa(x,p1)) -> { return %[forall @`pretty(x)@, (@`pretty(p1)@)]%; }
      exists(Ex(x,p1)) -> { return %[exists @`pretty(x)@, (@`pretty(p1)@)]%; }
      relApp(r,termList()) -> { return `r; }
      relApp(r,x) -> { return %[@`r@(@`pretty(x)@)]%; }
      bottom() -> { return "False"; }
      top() -> { return "True"; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /* first-order terms */

  public static String pretty(TermList tl) {
    %match(tl) {
      termList() -> { return ""; }
      termList(x) -> { return `pretty(x); }
      termList(h,t*) -> { return %[@`pretty(h)@,@pretty(`t)@]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(Term t) {
    %match(t) {
      var(x) -> { return `pretty(x);}
      funApp(name,x) -> { return %[@`name@(@`pretty(x)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /*-- proofterms --*/

  private static String pr(String var, Prop prop, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var@:@pretty(prop)@>]% : %[<@var@>]%);
  }

  private static String pr2(String var, Prop prop, String fovar, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var@:@pretty(prop)@><@fovar@>]% : %[<@var@><@fovar@>]%);
  }

  private static String pr3(String var1, Prop prop1, String var2, Prop prop2, int sp) {
    return "\n" + space(sp) + (churchStyle ? %[<@var1@:@pretty(prop1)@><@var2@:@pretty(prop2)@>]% : %[<@var1@><@var2@>]%);
  }

  private static String pretty(Name n) {
    %match(n) { Name(p,i) -> { return `i + `p; } }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(CoName n) {
    %match(n) { CoName(p,i) -> { return `i + `p; } }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(FoVar n) {
    %match(n) { FoVar(p,i) -> { return `i + `p; } }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(ProofTerm t, int sp) {
    %match(t) {
      ax(n,cn) -> {return %[ax(@`pretty(n)@,@`pretty(cn)@)]%; }
      falseL(n) -> { return %[falseL(@`pretty(n)@)]%; }
      trueR(cn) -> { return %[trueR(@`pretty(cn)@)]%; }
      cut(CutPrem1(cn,pcn,M),CutPrem2(n,pn,N)) -> { return %[cut(@`pr(pretty(cn),pcn,sp)@ @`pretty(M,sp+1)@,@`pr(pretty(n),pn,sp)@ @`pretty(N,sp+1)@)]%; }
      falseL(n) -> { return %[falseL(@`pretty(n)@)]%; }
      falseL(cn) -> { return %[falseL(@`pretty(cn)@)]%; }
      andR(AndRPrem1(a,pa,M),AndRPrem2(b,pb,N),cn) -> { return %[andR(@`pr(pretty(a),pa,sp)@ @`pretty(M,sp+1)@,@`pr(pretty(b),pb,sp)@ @`pretty(N,sp+1)@,@`pretty(cn)@)]%; }
      andL(AndLPrem1(x,px,y,py,M),n) -> { return %[andL(@`pr3(pretty(x),px,pretty(y),py,sp)@ @`pretty(M,sp+2)@,@`pretty(n)@)]%; }
      orR(OrRPrem1(a,pa,b,pb,M),cn) -> { return %[orR(@`pr3(pretty(a),pa,pretty(b),pb,sp)@ @`pretty(M,sp+1)@,@`pretty(cn)@)]%; }
      orL(OrLPrem1(x,px,M),OrLPrem2(y,py,N),n) -> { return %[orL(@`pr(pretty(x),px,sp)@ @`pretty(M,sp+1)@,@`pr(pretty(y),py,sp)@ @`pretty(N,sp+1)@,@`pretty(n)@)]%; }
      implyR(ImplyRPrem1(x,px,a,pa,M),cn) -> { return %[implyR(@`pr3(pretty(x),px,pretty(a),pa,sp)@ @`pretty(M,sp+1)@,@`pretty(cn)@)]%; }
      implyL(ImplyLPrem1(x,px,M),ImplyLPrem2(a,pa,N),n) -> { return %[implyL(@`pr(pretty(x),px,sp)@ @`pretty(M,sp+1)@,@`pr(pretty(a),pa,sp)@ @`pretty(N,sp+1)@,@`pretty(n)@)]%; }
      existsR(ExistsRPrem1(a,pa,M),term,cn) -> { return %[existsR(@`pr(pretty(a),pa,sp)@ @`pretty(M,sp+1)@,@`pretty(term)@,@`pretty(cn)@)]%; }
      existsL(ExistsLPrem1(x,px,fx,M),n) -> { return %[existsL(@`pr2(pretty(x),px,pretty(fx),sp)@ @`pretty(M,sp+1)@,@`pretty(n)@)]%; }
      forallR(ForallRPrem1(a,pa,fx,M),cn) -> { return %[forallR(@`pr2(pretty(a),pa,pretty(fx),sp)@ @`pretty(M,sp+1)@,@`pretty(cn)@)]%; }
      forallL(ForallLPrem1(x,px,M),term,n) -> { return %[forallL(@`pr(pretty(x),px,sp)@ @`pretty(M,sp+1)@,@`pretty(term)@,@`pretty(n)@)]%; }
      rootL(RootLPrem1(x,px,M)) -> { return %[@`pr(pretty(x),px,sp)@ @`pretty(M,sp+1)@]%; }
      rootR(RootRPrem1(a,pa,M)) -> { return %[@`pr(pretty(a),pa,sp)@ @`pretty(M,sp+1)@]%; }
      foldL(id,FoldLPrem1(x,px,M),n) -> { return %[foldL[@`id@](@`pr(pretty(x),px,sp)@ @`pretty(M,sp+1)@,@`pretty(n)@)]%; }
      foldR(id,FoldRPrem1(a,pa,M),cn) -> { return %[foldR[@`id@](@`pr(pretty(a),pa,sp)@ @`pretty(M,sp+1)@,@`pretty(cn)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(ProofTerm t) {
    return pretty(t,0);
  }

  /* sequents */

  private static String pretty(NProp np) {
    %match(np) { nprop(n,p) -> { return %[@`pretty(n)@:@`pretty(p)@]%; }}
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(CNProp np) {
    %match(np) { cnprop(cn,p) -> { return %[@`pretty(cn)@:@`pretty(p)@]%; }}
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(LCtx ctx) {
    %match(ctx) {
      lctx() -> { return ""; }
      lctx(x) -> { return `pretty(x); }
      lctx(x,xs*) -> { return `pretty(x) + "\n" + `pretty(xs); }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(RCtx ctx) {
    %match(ctx) {
      rctx() -> { return ""; }
      rctx(x) -> { return `pretty(x); }
      rctx(x,xs*) -> { return `pretty(x) + "\n" + `pretty(xs); }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String pretty(FoVarList l) {
    %match(l) {
      fovarList() -> { return ""; }
      fovarList(x) -> { return `pretty(x); }
      fovarList(x,xs*) -> { return %[@`pretty(x)@,@`pretty(xs)@]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(Sequent s) {
    String res;
    %match(s) { 
      seq(fv,lctx,rctx) -> {
        res  = `pretty(lctx) + "\n";
        res += "----------------------------------[" + `pretty(fv) + "]\n";
        res += `pretty(rctx);
        return res;
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /* --------------- coc terms -------------- */

  private static List<String> freeVars(RawCoCTerm t) {
    return freeVars(List.<String>nil(),t);
  }

  private static List<String> freeVars(List<String> ctx, RawCoCTerm t) {
    %match(t) {
      RawcocVar(v) -> { return ctx.toCollection().`contains(v) ? ctx : ctx.`cons(v); }
      RawcocLam(RawCoCLam(x,_,u)) -> { return `freeVars(ctx.cons(x),u); }
      RawcocPi(RawCoCPi(x,_,u)) -> { return `freeVars(ctx.cons(x),u); }
      RawcocApp(u,v) -> { return `freeVars(ctx,u).append(`freeVars(ctx,v)); }
      RawcocConst(_) -> { return List.<String>nil();  }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pretty(CoCTerm t) {
    %match(t) {
      cocVar(CoCAtom(x,i)) -> { return `x + `i; }
      cocLam(CoCLam(CoCAtom(x,i),ty,u)) -> { 
        return %[(fun @`x + `i@:@`pretty(ty)@ => @`pretty(u)@)]%; }
      cocPi(CoCPi(CoCAtom(x,i),ty,u)) -> { 
        return %[(forall @`x + `i@:@`pretty(ty)@, @`pretty(u)@)]%; 
      }
      cocApp(u,v) -> { return %[(@`pretty(u)@ @`pretty(v)@)]%; }
      cocConst(c) -> { return `c; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String pretty(RawCoCTerm t) {
    %match(t) {
      RawcocVar(x) -> { return `x; }
      RawcocLam(RawCoCLam(x,ty,u)) -> { 
        return %[(fun @`x@:@`pretty(ty)@ => @`pretty(u)@)]%; 
      }
      RawcocPi(RawCoCPi(x,ty,u)) -> { 
        if (`freeVars(u).toCollection().`contains(x))
          return %[(forall @`x@:@`pretty(ty)@, @`pretty(u)@)]%; 
        else
          return %[(@`pretty(ty)@ -> @`pretty(u)@)]%;
      }
      RawcocApp(u,v) -> { return %[(@`pretty(u)@ @`pretty(v)@)]%; }
      RawcocConst(c) -> { return `c; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /* raw lambda-mu terms */

  public static String pretty(RawLMMTerm t) {
    %match(t) {
      RawlmmVar(v) -> { return `v; }
      RawlmmLam(RawLmmLam(x,ty,u)) -> { return "\u03BB" + %[@`pr(x,ty)@.@`pretty(u)@]%; }
      RawlmmFLam(RawLmmFLam(fx,u)) -> { return "\u03BB" + %[@`fx@.@`pretty(u)@]%; }
      RawlmmMu(RawLmmMu(a,ty,c)) -> { return "\u03BC" + %[@`pr(a,ty)@.@`pretty(c)@]%; }
      RawlmmFoldR(rule,u) -> { return %[(lfold[@`rule@] @`pretty(u)@)]%; } 
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pretty(RawLMMContext e) {
    %match(e) {
      RawlmmCoVar(v) -> { return `v; }
      RawlmmMuT(RawLmmMuT(x,ty,c)) -> { return "\u03BC\u0303" + %[@`pr(x,ty)@.@`pretty(c)@]%; }
      RawlmmDot(v,e1) -> { return `pretty(v) + " \u00B7 " + `pretty(e1);  }
      RawlmmFDot(ft,e1) -> { return `pretty(ft) + " \u00B7 " + `pretty(e1);  }
      RawlmmFoldL(rule,e1) -> { return %[(rfold[@`rule@] @`pretty(e1)@)]%; } 
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  public static String pretty(RawLMMCommand c) {
    //%match(c) { RawlmmCommand(v,e) -> { return "\u2329" + `pretty(v) + " | " + `pretty(e) + "\u232A"; } }
    %match(c) { RawlmmCommand(v,e) -> { return "<" + `pretty(v) + "|" + `pretty(e) + ">"; } }
    throw new RuntimeException("non exhaustive patterns");
  }

  /* latex */

  private static String lspace(int sp) {
    String res = "";
    for(int i=0; i<sp; i++)
      res += "~~";
    return res;
  }

  private static String lpr(String var, RawProp prop, int sp) {
    return "\\\\\n" + lspace(sp) + (churchStyle ? %[\langle @var@:@lpretty(prop)@ \rangle]% : %[\langle @var@ \rangle]%);
  }

  private static String lpr2(String var, RawProp prop, String fovar, int sp) {
    return "\\\\\n" + lspace(sp) + (churchStyle ? %[\langle @var@:@lpretty(prop)@ \rangle\langle \fo{@fovar@} \rangle]% : %[\langle @var@ \rangle\langle \fo{@fovar@} \rangle]%);
  }

  private static String lpr3(String var1, RawProp prop1, String var2, RawProp prop2, int sp) {
    return "\\\\\n" + lspace(sp) + (churchStyle ? %[\langle @var1@:@lpretty(prop1)@ \rangle\langle @var2@:@lpretty(prop2)@ \rangle]% : %[\langle @var1@ \rangle\langle @var2@ \rangle]%);
  }

  public static String lpretty(RawProofTerm t) {
    return lpretty(t,1);
  }

 
  private static String lpretty(RawProofTerm t, int sp) {
    %match(t) {
      Rawax(n,cn) -> {return %[\tAx(@`n@,@`cn@)]%; }
      RawfalseL(n) -> { return %[\tFalseL(@`n@)]%; }
      RawtrueR(cn) -> { return %[\tTrueR(@`cn@)]%; }
      Rawcut(RawCutPrem1(cn,pcn,M),RawCutPrem2(n,pn,N)) -> { return %[\tCut(@`lpr(cn,pcn,sp)@ @`lpretty(M,sp+1)@,@`lpr(n,pn,sp)@ @`lpretty(N,sp+1)@)]%; }
      RawandR(RawAndRPrem1(a,pa,M),RawAndRPrem2(b,pb,N),cn) -> { return %[\tAndR(@`lpr(a,pa,sp)@ @`lpretty(M,sp+1)@,@`lpr(b,pb,sp)@ @`lpretty(N,sp+1)@,@`cn@)]%; }
      RawandL(RawAndLPrem1(x,px,y,py,M),n) -> { return %[\tAndL(@`lpr3(x,px,y,py,sp)@ @`lpretty(M,sp+2)@,@`n@)]%; }
      RaworR(RawOrRPrem1(a,pa,b,pb,M),cn) -> { return %[\tOrR(@`lpr3(a,pa,b,pb,sp)@ @`lpretty(M,sp+1)@,@`cn@)]%; }
      RaworL(RawOrLPrem1(x,px,M),RawOrLPrem2(y,py,N),n) -> { return %[\tOrL(@`lpr(x,px,sp)@ @`lpretty(M,sp+1)@,@`lpr(y,py,sp)@ @`lpretty(N,sp+1)@,@`n@)]%; }
      RawimplyR(RawImplyRPrem1(x,px,a,pa,M),cn) -> { return %[\tImpR(@`lpr3(x,px,a,pa,sp)@ @`lpretty(M,sp+1)@,@`cn@)]%; }
      RawimplyL(RawImplyLPrem1(x,px,M),RawImplyLPrem2(a,pa,N),n) -> { return %[\tImpL(@`lpr(x,px,sp)@ @`lpretty(M,sp+1)@,@`lpr(a,pa,sp)@ @`lpretty(N,sp+1)@,@`n@)]%; }
      RawexistsR(RawExistsRPrem1(a,pa,M),term,cn) -> { return %[\tExistsR(@`lpr(a,pa,sp)@ @`lpretty(M,sp+1)@,@`lpretty(term)@,@`cn@)]%; }
      RawexistsL(RawExistsLPrem1(x,px,fx,M),n) -> { return %[\tExistsL(@`lpr2(x,px,fx,sp)@ @`lpretty(M,sp+1)@,@`n@)]%; }
      RawforallR(RawForallRPrem1(a,pa,fx,M),cn) -> { return %[\tForallR(@`lpr2(a,pa,fx,sp)@ @`lpretty(M,sp+1)@,@`cn@)]%; }
      RawforallL(RawForallLPrem1(x,px,M),term,n) -> { return %[\tForallL(@`lpr(x,px,sp)@ @`lpretty(M,sp+1)@,@`lpretty(term)@,@`n@)]%; }
      RawrootL(RawRootLPrem1(x,px,M)) -> { return %[@`lpr(x,px,sp)@ @`lpretty(M,sp+1)@]%; }
      RawrootR(RawRootRPrem1(a,pa,M)) -> { return %[@`lpr(a,pa,sp)@ @`lpretty(M,sp+1)@]%; }
      RawfoldL(id,RawFoldLPrem1(x,px,M),n) -> { return %[\tFoldL{\mathfrak{@`id@}}(@`lpr(x,px,sp)@ @`lpretty(M,sp+1)@,@`n@)]%; }
      RawfoldR(id,RawFoldRPrem1(a,pa,M),cn) -> { return %[\tFoldR{\mathfrak{@`id@}}(@`lpr(a,pa,sp)@ @`lpretty(M,sp+1)@,@`cn@)]%; }
      //RawsuperR(id,prems,ts,cn) -> { return %[\tSuperR[@`id@](@`lpr(prems,sp+1)@,(@`lpretty(ts)@),@`cn@)]%; }
      //RawsuperL(id,prems,ts,n) -> { return %[\tSuperL[@`id@](@`lpr(prems,sp+1)@,(@`lpretty(ts)@),@`n@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String lpretty(RawProp p, int prec) {
    %match(p) {
      Rawforall(RawFa(x,p1)) -> { if (prec <= 0) return %[\forall \fo{@`x@}, @`lpretty(p1,0)@]%; }
      Rawexists(RawEx(x,p1)) -> { if (prec <= 0) return %[\exists \fo{@`x@}, @`lpretty(p1,0)@]%; }
      Rawimplies(p1,p2)      -> { if (prec <= 1) return %[@`lpretty(p1,2)@ \implies @`lpretty(p2,1)@]% ; }
      Rawor(p1,p2)           -> { if (prec <= 2) return %[@`lpretty(p1,2)@ \lor @`lpretty(p2,3)@]%; }
      Rawand(p1,p2)          -> { if (prec <= 3) return %[@`lpretty(p1,3)@ \land @`lpretty(p2,4)@]%; }
      Rawbottom()            -> { return "\\bot"; }
      Rawtop()               -> { return "\\top"; }
      RawrelApp(r,RawtermList())        -> { return `r; }
      RawrelApp("Eq",RawtermList(x,y)) -> { return %[@lpretty(`x)@ \simeq @lpretty(`y)@]%; }
      RawrelApp("In",RawtermList(x,y)) -> { return %[@lpretty(`x)@ \in @lpretty(`y)@]%; }
      RawrelApp(r,x)         -> { return %[@`r@(@`lpretty(x)@)]%; }
      p1                     -> { return %[(@`lpretty(p1,0)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }
  public static String lpretty(RawProp p) {
    return lpretty(p,0);
  }
  public static String lpretty(RawTermList tl) {
    %match(tl) {
      RawtermList(x) -> { return `lpretty(x); }
      RawtermList(h,t*) -> { return %[@`lpretty(h)@,@lpretty(`t)@]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }
  public static String lpretty(RawTerm t) {
    %match(t) {
      Rawvar(x) -> { return %[\fo{@`x@}]%;}
      RawfunApp(name,RawtermList()) -> { return %[@`name@]%; }
      RawfunApp(name,x) -> { return %[@`name@(@`lpretty(x)@)]%; }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  /* tree pretty print */

  private static String lpretty(RawNProp np) {
    %match(np) { Rawnprop(n,p) -> { return %[@`n@:@`lpretty(p)@]%; }}
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String lpretty(RawCNProp np) {
    %match(np) { Rawcnprop(cn,p) -> { return %[@`cn@:@`lpretty(p)@]%; }}
    throw new RuntimeException("non exhaustive patterns"); 
  }


  private static String lpretty(RawLCtx ctx, RawnameList fns) {
    return lpretty(ctx,fns,false);
  }

  private static String lpretty(RawLCtx ctx, RawnameList fns, boolean comma) {
    %match(ctx) {
      Rawlctx() -> { return ""; }
      Rawlctx(x@Rawnprop(n,_),xs*) -> { 
        if (fns.contains(`n)) 
          return (comma ? "," : "") + `lpretty(x) + `lpretty(xs,fns,true);
        else
          return `lpretty(xs,fns,comma);
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static String lpretty(RawRCtx ctx, RawconameList fcns) {
    return lpretty(ctx,fcns,false);
  }

  private static String lpretty(RawRCtx ctx, RawconameList fcns, boolean comma) {
    %match(ctx) {
      Rawrctx() -> { return ""; }
      Rawrctx(x@Rawcnprop(cn,_),xs*) -> { 
        if (fcns.contains(`cn)) 
          return (comma ? "," : "") + `lpretty(x) + `lpretty(xs,fcns,true);
        else
          return `lpretty(xs,fcns,comma);
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

 public static String lpretty(RawSequent s, RawnameList fns, RawconameList fcns) {
    %match(s) { 
      Rawseq(_,lctx,rctx) -> {
        return `lpretty(lctx,fns) + " \\vdash " + `lpretty(rctx,fcns);
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  private static RawnameList getFreeNames(RawNameList ctx, RawProofTerm pt) {
    RawnameList c = (RawnameList) ctx;
    %match (pt) {
      Rawax(n,_) -> {
        return (RawnameList) (c.contains(`n) ? `RawnameList() : `RawnameList(n));
      }
      Rawcut(RawCutPrem1(_,_,M1),RawCutPrem2(x,_,M2)) -> {
        RawNameList M1names = `getFreeNames(c,M1);
        RawNameList M2names = `getFreeNames(RawnameList(x,c*),M2);
        return (RawnameList) `RawnameList(M1names*,M2names*); 
      }
      // left rules
      RawfalseL(n) -> {
        return (RawnameList) (c.contains(`n) ? `RawnameList() : `RawnameList(n)); 
      }
      RawandL(RawAndLPrem1(x,_,y,_,M),n) -> {
        RawNameList Mnames = `getFreeNames(RawnameList(x,y,c*),M);
        return (RawnameList) (c.contains(`n) ? Mnames : `RawnameList(n,Mnames*)); 
      }
      RaworL(RawOrLPrem1(x,_,M1),RawOrLPrem2(y,_,M2),n) -> {
        RawNameList M1names = `getFreeNames(RawnameList(x,c*),M1);
        RawNameList M2names = `getFreeNames(RawnameList(y,c*),M2);
        return (RawnameList) (c.contains(`n) ? `RawnameList(M1names*,M2names*) : `RawnameList(n,M1names*,M2names*));
      }
      RawimplyL(RawImplyLPrem1(x,_,M1),RawImplyLPrem2(_,_,M2),n) -> {
        RawNameList M1names = `getFreeNames(RawnameList(x,c*),M1);
        RawNameList M2names = `getFreeNames(c,M2);
        return (RawnameList) (c.contains(`n) ? `RawnameList(M1names*,M2names*) : `RawnameList(n,M1names*,M2names*)); 
      }
      RawforallL(RawForallLPrem1(x,_,M),_,n) -> {
        RawNameList Mnames = `getFreeNames(RawnameList(x,c*),M);
        return (RawnameList) (c.contains(`n) ? Mnames : `RawnameList(n,Mnames*)); 
      }
      RawexistsL(RawExistsLPrem1(x,_,_,M),n) -> {
        RawNameList Mnames = `getFreeNames(RawnameList(x,c*),M);
        return (RawnameList) (c.contains(`n) ? Mnames : `RawnameList(n,Mnames*)); 
      }
      RawrootL(RawRootLPrem1(x,_,M)) -> {
        return `getFreeNames(RawnameList(x,c*),M);
      }
      RawfoldL(_,RawFoldLPrem1(x,_,M),n) -> {
        RawNameList Mnames = `getFreeNames(RawnameList(x,c*),M);
        return (RawnameList) (c.contains(`n) ? Mnames : `RawnameList(n,Mnames*)); 
      }
      // right rules
      RawtrueR(_) -> {
        return (RawnameList) `RawnameList();
      }
      RaworR(RawOrRPrem1(_,_,_,_,M),_) -> {
        return `getFreeNames(c,M);
      }
      RawandR(RawAndRPrem1(_,_,M1),RawAndRPrem2(_,_,M2),_) -> {
        RawNameList M1names = `getFreeNames(c,M1);
        RawNameList M2names = `getFreeNames(c,M2);
        return (RawnameList) `RawnameList(M1names*,M2names*); 
      }
      RawimplyR(RawImplyRPrem1(x,_,_,_,M),_) -> {
        return `getFreeNames(RawnameList(x,c*),M);
      }
      RawexistsR(RawExistsRPrem1(_,_,M),_,_) -> {
        return `getFreeNames(c,M);
      }
      RawforallR(RawForallRPrem1(_,_,_,M),_) -> {
        return `getFreeNames(c,M);
      }
      RawrootR(RawRootRPrem1(_,_,M)) -> {
        return `getFreeNames(c,M);
      }
      RawfoldR(_,RawFoldRPrem1(_,_,M),_) -> {
        return `getFreeNames(c,M);
      }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  private static RawnameList getFreeNames(RawProofTerm pt) {
    return `getFreeNames(RawnameList(),pt);
  }

  private static RawconameList getFreeCoNames(RawCoNameList ctx, RawProofTerm pt) {
    RawconameList c = (RawconameList) ctx;
    %match (pt) {
      Rawax(_,cn) -> {
        return (RawconameList) (c.contains(`cn) ? `RawconameList() : `RawconameList(cn));
      }
      Rawcut(RawCutPrem1(a,_,M1),RawCutPrem2(_,_,M2)) -> {
        RawCoNameList M1Rawconames = `getFreeCoNames(RawconameList(a,c*),M1);
        RawCoNameList M2Rawconames = `getFreeCoNames(c,M2);
        return (RawconameList) `RawconameList(M1Rawconames*,M2Rawconames*); 
      }
      // left rules
      RawfalseL(_) -> {
        return (RawconameList) `RawconameList();
      }
      RawandL(RawAndLPrem1(_,_,_,_,M),_) -> {
        return `getFreeCoNames(c,M);
      }
      RaworL(RawOrLPrem1(_,_,M1),RawOrLPrem2(_,_,M2),_) -> {
        RawCoNameList M1Rawconames = `getFreeCoNames(c,M1);
        RawCoNameList M2Rawconames = `getFreeCoNames(c,M2);
        return (RawconameList) `RawconameList(M1Rawconames*,M2Rawconames*); 
      }
      RawimplyL(RawImplyLPrem1(_,_,M1),RawImplyLPrem2(a,_,M2),_) -> {
        RawCoNameList M1names = `getFreeCoNames(c,M1);
        RawCoNameList M2names = `getFreeCoNames(RawconameList(a,c*),M2);
        return (RawconameList) `RawconameList(M1names*,M2names*); 
      }
      RawforallL(RawForallLPrem1(_,_,M),_,_) -> {
        return `getFreeCoNames(c,M);
      }
      RawexistsL(RawExistsLPrem1(_,_,_,M),_) -> {
        return `getFreeCoNames(c,M);
      }
      RawrootL(RawRootLPrem1(_,_,M)) -> {
        return `getFreeCoNames(c,M);
      }
      RawfoldL(_,RawFoldLPrem1(_,_,M),_) -> {
        return `getFreeCoNames(c,M);
      }
      // right rules
      RawtrueR(cn) -> {
        return (RawconameList) (c.contains(`cn) ? `RawconameList() : `RawconameList(cn)); 
      }
      RaworR(RawOrRPrem1(a,_,b,_,M),cn) -> {
        RawCoNameList MRawconames = `getFreeCoNames(RawconameList(a,b,c*),M);
        return (RawconameList) (c.contains(`cn) ? MRawconames : `RawconameList(cn,MRawconames*)); 
      }
      RawandR(RawAndRPrem1(a,_,M1),RawAndRPrem2(b,_,M2),cn) -> {
        RawCoNameList M1Rawconames = `getFreeCoNames(RawconameList(a,c*),M1);
        RawCoNameList M2Rawconames = `getFreeCoNames(RawconameList(b,c*),M2);
        return (RawconameList) (c.contains(`cn)  ? `RawconameList(M1Rawconames*,M2Rawconames*) : `RawconameList(cn,M1Rawconames*,M2Rawconames*)); 
      }
      RawimplyR(RawImplyRPrem1(_,_,a,_,M),cn) -> {
        RawCoNameList MRawconames = `getFreeCoNames(RawconameList(a,c*),M);
        return (RawconameList) (c.contains(`cn) ? MRawconames : `RawconameList(cn,MRawconames*)); 
      }
      RawexistsR(RawExistsRPrem1(a,_,M),_,cn) -> {
        RawCoNameList MRawconames = `getFreeCoNames(RawconameList(a,c*),M);
        return (RawconameList) (c.contains(`cn) ? MRawconames : `RawconameList(cn,MRawconames*)); 
      }
      RawforallR(RawForallRPrem1(a,_,_,M),cn) -> {
        RawCoNameList MRawconames = `getFreeCoNames(RawconameList(a,c*),M);
        return (RawconameList) (c.contains(`cn) ? MRawconames : `RawconameList(cn,MRawconames*)); 
      }
      RawrootR(RawRootRPrem1(a,_,M)) -> {
        return `getFreeCoNames(RawconameList(a,c*),M);
      }
      RawfoldR(_,RawFoldRPrem1(a,_,M),cn) -> {
        RawCoNameList MRawconames = `getFreeCoNames(RawconameList(a,c*),M);
        return (RawconameList) (c.contains(`cn) ? MRawconames : `RawconameList(cn,MRawconames*)); 
      }
    }
    throw new RuntimeException("non exhaustive patterns");
  }

  private static RawconameList getFreeCoNames(RawProofTerm pt) {
    return `getFreeCoNames(RawconameList(),pt);
  }

  public static String lprettyTree(RawProofTerm t) {
    %match(t) {
      RawrootR(RawRootRPrem1(cn,pcn,M)) -> {
        return `lprettyTree(Rawseq(RawfovarList(),Rawlctx(),Rawrctx(Rawcnprop(cn,pcn))),M);
      }
      RawrootR(RawRootRPrem1(n,pn,M)) -> {
        return `lprettyTree(Rawseq(RawfovarList(),Rawlctx(Rawnprop(n,pn)),Rawrctx()),M);
      }
    }
    throw new RuntimeException("proof must begin with rootR or rootL");
  }

  public static String lprettyTree(RawSequent seq, RawProofTerm t) {
    %match(seq) {
      Rawseq(fvs,lctx,rctx) -> {
        %match(t) {
          Rawax(n,cn) -> { return %[\infer[\text{\textsc{ax}}]{@`lpretty(seq,RawnameList(n),RawconameList(cn))@}{}]%; }
          RawfalseL(n) -> { return %[\infer[\bot\text{\textsc{-l}}]{@`lpretty(seq,RawnameList(n),RawconameList())@}{}]%; }
          RawtrueR(cn) -> { return %[\infer[\top\text{\textsc{-r}}]{@`lpretty(seq,RawnameList(),RawconameList(cn))@}{}]%; }
          Rawcut(RawCutPrem1(cn,pcn,M),RawCutPrem2(n,pn,N)) -> { 
            RawnameList fns1 = `getFreeNames(M);
            RawnameList fns2 = `getFreeNames(N);
            RawnameList fns = (RawnameList) `RawnameList(fns1*,fns2*);
            RawconameList fcns1 = `getFreeCoNames(M);
            RawconameList fcns2 = `getFreeCoNames(N);
            RawconameList fcns = (RawconameList) `RawconameList(fcns1*,fcns2*);
            return %[\infer[\text{\textsc{cut}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,lctx,Rawrctx(Rawcnprop(cn,pcn),rctx*)),M)@ & 
               @`lprettyTree(Rawseq(fvs,Rawlctx(Rawnprop(n,pn),lctx*),rctx),N)@}]%; 
          }
          RawimplyR(RawImplyRPrem1(x,px,a,pa,M),cn) -> { 
            RawnameList fns = `getFreeNames(M);
            RawconameList fcns1 = `getFreeCoNames(M);
            RawconameList fcns = (RawconameList) `RawconameList(cn,fcns1*);
            return %[\infer[\Rightarrow\text{\textsc{-r}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,Rawlctx(Rawnprop(x,px),lctx*),Rawrctx(Rawcnprop(a,pa),rctx*)),M)@}]%; 
          }
          RawimplyL(RawImplyLPrem1(x,px,M),RawImplyLPrem2(a,pa,N),n) -> { 
            RawnameList fns1 = `getFreeNames(M);
            RawnameList fns2 = `getFreeNames(N);
            RawnameList fns = (RawnameList) `RawnameList(n,fns1*,fns2*);
            RawconameList fcns1 = `getFreeCoNames(M);
            RawconameList fcns2 = `getFreeCoNames(M);
            RawconameList fcns = (RawconameList) `RawconameList(fcns1*,fcns2*);
            return %[\infer[\Rightarrow\text{\textsc{-l}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,Rawlctx(Rawnprop(x,px),lctx*),rctx),M)@ &
               @`lprettyTree(Rawseq(fvs,lctx,Rawrctx(Rawcnprop(a,pa),rctx*)),N)@}]%; 
          }
          RawforallR(RawForallRPrem1(a,pa,fx,M),cn) -> { 
            RawnameList fns = `getFreeNames(M);
            RawconameList fcns1 = `getFreeCoNames(M);
            RawconameList fcns = (RawconameList) `RawconameList(cn,fcns1*);
            return %[\infer[\forall\text{\textsc{-r}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(RawfovarList(fx,fvs*),lctx,Rawrctx(Rawcnprop(a,pa),rctx*)),M)@}]%; 
          }
          RawforallL(RawForallLPrem1(x,px,M),_,n) -> { 
            RawnameList fns1 = `getFreeNames(M);
            RawnameList fns = (RawnameList) `RawnameList(n,fns1*);
            RawconameList fcns = `getFreeCoNames(M);
            return %[\infer[\forall\text{\textsc{-l}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,Rawlctx(Rawnprop(x,px),lctx*),rctx),M)@}]%; 
          }
          RawfoldL(id,RawFoldLPrem1(x,px,M),n) -> {
            RawnameList fns1 = `getFreeNames(M);
            RawnameList fns = (RawnameList) `RawnameList(n,fns1*);
            RawconameList fcns = `getFreeCoNames(M);
            return %[\infer[\text{\textsc{fold-l[$\mathfrak{@`id@}$]}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,Rawlctx(Rawnprop(x,px),lctx*),rctx),M)@}]%; 
          }
          RawfoldR(id,RawFoldRPrem1(a,pa,M),cn) -> { 
            RawnameList fns = `getFreeNames(M);
            RawconameList fcns1 = `getFreeCoNames(M);
            RawconameList fcns = (RawconameList) `RawconameList(cn,fcns1*);
            return %[\infer[\text{\textsc{fold-r[$\mathfrak{@`id@}$]}}]
              {@`lpretty(seq,fns,fcns)@}
              {@`lprettyTree(Rawseq(fvs,lctx,Rawrctx(Rawcnprop(a,pa),rctx*)),M)@}]%; 
          }
          /*
          RawandR(RawAndRPrem1(a,pa,M),RawAndRPrem2(b,pb,N),cn) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RawandL(RawAndLPrem1(x,px,y,py,M),n) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RaworR(RawOrRPrem1(a,pa,b,pb,M),cn) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RaworL(RawOrLPrem1(x,px,M),RawOrLPrem2(y,py,N),n) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RawexistsR(RawExistsRPrem1(a,pa,M),term,cn) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RawexistsL(RawExistsLPrem1(x,px,fx,M),n) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RawrootL(RawRootLPrem1(x,px,M)) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          RawrootR(RawRootRPrem1(a,pa,M)) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          */
          //RawsuperR(id,prems,ts,cn) -> { return %[infer{@`lpretty(seq)@}{}]%; }
          //RawsuperL(id,prems,ts,n) -> { }
        }
      }
    }
    throw new RuntimeException("non exhaustive patterns"); 
  }

  public static String toDoc(RawProofTerm pt) {
    return
      %[\documentclass{article}
        \newcommand{\tez}{\vdash}
        \newcommand{\fo}[1]{\mathsf{#1}}
        \usepackage{proof}
        \usepackage{amssymb}
        \usepackage{amsmath}
        \usepackage{euler}
        \renewcommand{\implies}{\Rightarrow}
        \begin{document}
        \pagestyle{empty}
        \[@lprettyTree(pt)@\]
        \end{document}]%;
  }

}
