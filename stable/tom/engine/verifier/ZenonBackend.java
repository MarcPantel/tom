/* Generated by TOM (version 2.5alpha): Do not edit this file *//*
 *   
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2007, INRIA
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
 * Antoine Reilles        e-mail: Antoine.Reilles@loria.fr
 *
 **/

package tom.engine.verifier;

import tom.engine.*;
import aterm.*;
import aterm.pure.*;
import java.util.*;
import tom.engine.adt.zenon.*;
import tom.engine.adt.zenon.types.*;

public class ZenonBackend {

  // ------------------------------------------------------------
  /* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5alpha): Do not edit this file */private static boolean tom_equal_term_int(int t1, int t2) { return  (t1==t2) ;}private static boolean tom_is_sort_int(int t) { return  true ;} private static boolean tom_equal_term_ZTermList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZTermList(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZTermList ;}private static boolean tom_equal_term_ZType(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZType(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZType ;}private static boolean tom_equal_term_ZTerm(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZTerm(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZTerm ;}private static boolean tom_equal_term_ZAxiom(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZAxiom(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZAxiom ;}private static boolean tom_equal_term_ZSpec(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZSpec(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZSpec ;}private static boolean tom_equal_term_ZSymbol(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZSymbol(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZSymbol ;}private static boolean tom_equal_term_ZExpr(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZExpr(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZExpr ;}private static boolean tom_equal_term_ZAxiomList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ZAxiomList(Object t) { return  t instanceof tom.engine.adt.zenon.types.ZAxiomList ;}private static boolean tom_is_fun_sym_ztype( tom.engine.adt.zenon.types.ZType  t) { return  t instanceof tom.engine.adt.zenon.types.ztype.ztype ;}private static  String  tom_get_slot_ztype_Tname( tom.engine.adt.zenon.types.ZType  t) { return  t.getTname() ;}private static boolean tom_is_fun_sym_zvar( tom.engine.adt.zenon.types.ZTerm  t) { return  t instanceof tom.engine.adt.zenon.types.zterm.zvar ;}private static  String  tom_get_slot_zvar_Varname( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getVarname() ;}private static boolean tom_is_fun_sym_zappl( tom.engine.adt.zenon.types.ZTerm  t) { return  t instanceof tom.engine.adt.zenon.types.zterm.zappl ;}private static  tom.engine.adt.zenon.types.ZSymbol  tom_get_slot_zappl_Zsymb( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getZsymb() ;}private static  tom.engine.adt.zenon.types.ZTermList  tom_get_slot_zappl_Termlist( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getTermlist() ;}private static boolean tom_is_fun_sym_zst( tom.engine.adt.zenon.types.ZTerm  t) { return  t instanceof tom.engine.adt.zenon.types.zterm.zst ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zst_Abst( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getAbst() ;}private static  int  tom_get_slot_zst_Index( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getIndex() ;}private static boolean tom_is_fun_sym_zsl( tom.engine.adt.zenon.types.ZTerm  t) { return  t instanceof tom.engine.adt.zenon.types.zterm.zsl ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zsl_Abst( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getAbst() ;}private static  String  tom_get_slot_zsl_Name( tom.engine.adt.zenon.types.ZTerm  t) { return  t.getName() ;}private static boolean tom_is_fun_sym_zaxiom( tom.engine.adt.zenon.types.ZAxiom  t) { return  t instanceof tom.engine.adt.zenon.types.zaxiom.zaxiom ;}private static  String  tom_get_slot_zaxiom_Name( tom.engine.adt.zenon.types.ZAxiom  t) { return  t.getName() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zaxiom_Ax( tom.engine.adt.zenon.types.ZAxiom  t) { return  t.getAx() ;}private static boolean tom_is_fun_sym_zthm( tom.engine.adt.zenon.types.ZSpec  t) { return  t instanceof tom.engine.adt.zenon.types.zspec.zthm ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zthm_Thm( tom.engine.adt.zenon.types.ZSpec  t) { return  t.getThm() ;}private static  tom.engine.adt.zenon.types.ZAxiomList  tom_get_slot_zthm_By( tom.engine.adt.zenon.types.ZSpec  t) { return  t.getBy() ;}private static boolean tom_is_fun_sym_zsymbol( tom.engine.adt.zenon.types.ZSymbol  t) { return  t instanceof tom.engine.adt.zenon.types.zsymbol.zsymbol ;}private static  tom.engine.adt.zenon.types.ZSymbol  tom_make_zsymbol( String  t0) { return  tom.engine.adt.zenon.types.zsymbol.zsymbol.make(t0) ; }private static  String  tom_get_slot_zsymbol_Name( tom.engine.adt.zenon.types.ZSymbol  t) { return  t.getName() ;}private static boolean tom_is_fun_sym_ztrue( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.ztrue ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_make_ztrue() { return  tom.engine.adt.zenon.types.zexpr.ztrue.make() ; }private static boolean tom_is_fun_sym_zfalse( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zfalse ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_make_zfalse() { return  tom.engine.adt.zenon.types.zexpr.zfalse.make() ; }private static boolean tom_is_fun_sym_zisfsym( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zisfsym ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zisfsym_T( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getT() ;}private static  tom.engine.adt.zenon.types.ZSymbol  tom_get_slot_zisfsym_Symbol( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getSymbol() ;}private static boolean tom_is_fun_sym_zeq( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zeq ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zeq_Lt( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getLt() ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zeq_Rt( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getRt() ;}private static boolean tom_is_fun_sym_zforall( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zforall ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zforall_Var( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getVar() ;}private static  tom.engine.adt.zenon.types.ZType  tom_get_slot_zforall_Aztype( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getAztype() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zforall_Expr( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getExpr() ;}private static boolean tom_is_fun_sym_zexists( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zexists ;}private static  tom.engine.adt.zenon.types.ZTerm  tom_get_slot_zexists_Var( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getVar() ;}private static  tom.engine.adt.zenon.types.ZType  tom_get_slot_zexists_Aztype( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getAztype() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zexists_Expr( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getExpr() ;}private static boolean tom_is_fun_sym_zand( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zand ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zand_Lte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getLte() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zand_Rte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getRte() ;}private static boolean tom_is_fun_sym_zor( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zor ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zor_Lte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getLte() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zor_Rte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getRte() ;}private static boolean tom_is_fun_sym_znot( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.znot ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_znot_Nex( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getNex() ;}private static boolean tom_is_fun_sym_zequiv( tom.engine.adt.zenon.types.ZExpr  t) { return  t instanceof tom.engine.adt.zenon.types.zexpr.zequiv ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zequiv_Lte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getLte() ;}private static  tom.engine.adt.zenon.types.ZExpr  tom_get_slot_zequiv_Rte( tom.engine.adt.zenon.types.ZExpr  t) { return  t.getRte() ;}private static boolean tom_is_fun_sym_zby( tom.engine.adt.zenon.types.ZAxiomList  t) { return  t instanceof tom.engine.adt.zenon.types.zaxiomlist.Conszby || t instanceof tom.engine.adt.zenon.types.zaxiomlist.Emptyzby ;}private static  tom.engine.adt.zenon.types.ZAxiomList  tom_empty_list_zby() { return  tom.engine.adt.zenon.types.zaxiomlist.Emptyzby.make() ; }private static  tom.engine.adt.zenon.types.ZAxiomList  tom_cons_list_zby( tom.engine.adt.zenon.types.ZAxiom  e,  tom.engine.adt.zenon.types.ZAxiomList  l) { return  tom.engine.adt.zenon.types.zaxiomlist.Conszby.make(e,l) ; }private static  tom.engine.adt.zenon.types.ZAxiom  tom_get_head_zby_ZAxiomList( tom.engine.adt.zenon.types.ZAxiomList  l) { return  l.getHeadzby() ;}private static  tom.engine.adt.zenon.types.ZAxiomList  tom_get_tail_zby_ZAxiomList( tom.engine.adt.zenon.types.ZAxiomList  l) { return  l.getTailzby() ;}private static boolean tom_is_empty_zby_ZAxiomList( tom.engine.adt.zenon.types.ZAxiomList  l) { return  l.isEmptyzby() ;}   private static   tom.engine.adt.zenon.types.ZAxiomList  tom_append_list_zby( tom.engine.adt.zenon.types.ZAxiomList l1,  tom.engine.adt.zenon.types.ZAxiomList  l2) {     if(tom_is_empty_zby_ZAxiomList(l1)) {       return l2;     } else if(tom_is_empty_zby_ZAxiomList(l2)) {       return l1;     } else if(tom_is_empty_zby_ZAxiomList(tom_get_tail_zby_ZAxiomList(l1))) {       return ( tom.engine.adt.zenon.types.ZAxiomList )tom_cons_list_zby(tom_get_head_zby_ZAxiomList(l1),l2);     } else {       return ( tom.engine.adt.zenon.types.ZAxiomList )tom_cons_list_zby(tom_get_head_zby_ZAxiomList(l1),tom_append_list_zby(tom_get_tail_zby_ZAxiomList(l1),l2));     }   }   private static   tom.engine.adt.zenon.types.ZAxiomList  tom_get_slice_zby( tom.engine.adt.zenon.types.ZAxiomList  begin,  tom.engine.adt.zenon.types.ZAxiomList  end, tom.engine.adt.zenon.types.ZAxiomList  tail) {     if(tom_equal_term_ZAxiomList(begin,end)) {       return tail;     } else {       return ( tom.engine.adt.zenon.types.ZAxiomList )tom_cons_list_zby(tom_get_head_zby_ZAxiomList(begin),( tom.engine.adt.zenon.types.ZAxiomList )tom_get_slice_zby(tom_get_tail_zby_ZAxiomList(begin),end,tail));     }   }    
  // ------------------------------------------------------------

  private Verifier verifier; // is it useful ?
  private TomIlTools tomiltools;

  public ZenonBackend(Verifier verifier) {
    this.verifier = verifier;
    this.tomiltools = new TomIlTools(verifier);
  }

  public String genZSymbol(ZSymbol symbol) {
    if (tom_is_sort_ZSymbol(symbol)) {{  tom.engine.adt.zenon.types.ZSymbol  tomMatch98NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZSymbol )symbol);if (tom_is_fun_sym_zsymbol(tomMatch98NameNumberfreshSubject_1)) {{  String  tomMatch98NameNumber_freshVar_0=tom_get_slot_zsymbol_Name(tomMatch98NameNumberfreshSubject_1);if ( true ) {

        // manage builtins
        String symbolName = tomiltools.replaceNumbersByString(tomMatch98NameNumber_freshVar_0);
        return symbolName+ "_";
      }}}}}

    return "errorZSymbol";
  }

  public String genZType(ZType type) {
    if (tom_is_sort_ZType(type)) {{  tom.engine.adt.zenon.types.ZType  tomMatch99NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZType )type);if (tom_is_fun_sym_ztype(tomMatch99NameNumberfreshSubject_1)) {{  String  tomMatch99NameNumber_freshVar_0=tom_get_slot_ztype_Tname(tomMatch99NameNumberfreshSubject_1);if ( true ) {

        return tomMatch99NameNumber_freshVar_0;
      }}}}}

    return "errorZType";
  }

  public String genZTerm(ZTerm term) {
    if (tom_is_sort_ZTerm(term)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch100NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZTerm )term);if (tom_is_fun_sym_zvar(tomMatch100NameNumberfreshSubject_1)) {{  String  tomMatch100NameNumber_freshVar_0=tom_get_slot_zvar_Varname(tomMatch100NameNumberfreshSubject_1);if ( true ) {
 return tomMatch100NameNumber_freshVar_0; }}}if (tom_is_fun_sym_zappl(tomMatch100NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZSymbol  tomMatch100NameNumber_freshVar_1=tom_get_slot_zappl_Zsymb(tomMatch100NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZTermList  tomMatch100NameNumber_freshVar_2=tom_get_slot_zappl_Termlist(tomMatch100NameNumberfreshSubject_1);if (tom_is_fun_sym_zsymbol(tomMatch100NameNumber_freshVar_1)) {{  String  tomMatch100NameNumber_freshVar_3=tom_get_slot_zsymbol_Name(tomMatch100NameNumber_freshVar_1);if ( true ) {
 
        // manage builtins
        String realName = tomiltools.replaceNumbersByString(tomMatch100NameNumber_freshVar_3);
        return "(" + realName +" "+genZTermList(tomMatch100NameNumber_freshVar_2)+")"; 
      }}}}}}if (tom_is_fun_sym_zst(tomMatch100NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch100NameNumber_freshVar_4=tom_get_slot_zst_Abst(tomMatch100NameNumberfreshSubject_1);{  int  tomMatch100NameNumber_freshVar_5=tom_get_slot_zst_Index(tomMatch100NameNumberfreshSubject_1);if ( true ) {
 
        return "(_"+tomMatch100NameNumber_freshVar_5+" "+genZTerm(tomMatch100NameNumber_freshVar_4)+")";
      }}}}if (tom_is_fun_sym_zsl(tomMatch100NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch100NameNumber_freshVar_6=tom_get_slot_zsl_Abst(tomMatch100NameNumberfreshSubject_1);{  String  tomMatch100NameNumber_freshVar_7=tom_get_slot_zsl_Name(tomMatch100NameNumberfreshSubject_1);if ( true ) {
 
        return "(_"+tomMatch100NameNumber_freshVar_7+" "+genZTerm(tomMatch100NameNumber_freshVar_6)+")";
      }}}}}}

    return "errorZTerm";
  }

  public String genZTermList(ZTermList tl) {
    StringBuffer res = new StringBuffer();
    while (!tl.isEmptyconcZTerm()) {
      res.append(" "+genZTerm(tl.getHeadconcZTerm()));
      tl = tl.getTailconcZTerm();
    }
    return res.toString();
  }

  public String genZExpr(ZExpr expr) {
    if (tom_is_sort_ZExpr(expr)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZExpr )expr);if (tom_is_fun_sym_ztrue(tomMatch101NameNumberfreshSubject_1)) {if ( true ) {
 return "True";}}if (tom_is_fun_sym_zfalse(tomMatch101NameNumberfreshSubject_1)) {if ( true ) {
 return "False";}}if (tom_is_fun_sym_zisfsym(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch101NameNumber_freshVar_0=tom_get_slot_zisfsym_T(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZSymbol  tomMatch101NameNumber_freshVar_1=tom_get_slot_zisfsym_Symbol(tomMatch101NameNumberfreshSubject_1);if ( true ) {

        return "((symb "+genZTerm(tomMatch101NameNumber_freshVar_0)+") = "+genZSymbol(tomMatch101NameNumber_freshVar_1)+")";
      }}}}if (tom_is_fun_sym_zeq(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch101NameNumber_freshVar_2=tom_get_slot_zeq_Lt(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZTerm  tomMatch101NameNumber_freshVar_3=tom_get_slot_zeq_Rt(tomMatch101NameNumberfreshSubject_1);if ( true ) {

        return "("+genZTerm(tomMatch101NameNumber_freshVar_2)+" = "+genZTerm(tomMatch101NameNumber_freshVar_3)+")";
      }}}}if (tom_is_fun_sym_zforall(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch101NameNumber_freshVar_4=tom_get_slot_zforall_Var(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZType  tomMatch101NameNumber_freshVar_5=tom_get_slot_zforall_Aztype(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_6=tom_get_slot_zforall_Expr(tomMatch101NameNumberfreshSubject_1);if ( true ) {

        return "forall "+genZTerm(tomMatch101NameNumber_freshVar_4)+" : "+genZType(tomMatch101NameNumber_freshVar_5)+",\n "+genZExpr(tomMatch101NameNumber_freshVar_6);
      }}}}}if (tom_is_fun_sym_zexists(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZTerm  tomMatch101NameNumber_freshVar_7=tom_get_slot_zexists_Var(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZType  tomMatch101NameNumber_freshVar_8=tom_get_slot_zexists_Aztype(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_9=tom_get_slot_zexists_Expr(tomMatch101NameNumberfreshSubject_1);if ( true ) {

        return "exists "+genZTerm(tomMatch101NameNumber_freshVar_7)+" : "+genZType(tomMatch101NameNumber_freshVar_8)+", "+genZExpr(tomMatch101NameNumber_freshVar_9);
      }}}}}if (tom_is_fun_sym_zand(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_10=tom_get_slot_zand_Lte(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_11=tom_get_slot_zand_Rte(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tom_l=tomMatch101NameNumber_freshVar_10;{  tom.engine.adt.zenon.types.ZExpr  tom_r=tomMatch101NameNumber_freshVar_11;if ( true ) {

        if(tom_l== tom_make_ztrue()) {
          return "("+ genZExpr(tom_r) +")";
        }
        else if (tom_r== tom_make_ztrue()) {
          return "("+ genZExpr(tom_l) +")";
        }
        return "("+genZExpr(tom_l)+") /\\ ("+genZExpr(tom_r)+")";
      }}}}}}if (tom_is_fun_sym_zor(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_12=tom_get_slot_zor_Lte(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_13=tom_get_slot_zor_Rte(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tom_l=tomMatch101NameNumber_freshVar_12;{  tom.engine.adt.zenon.types.ZExpr  tom_r=tomMatch101NameNumber_freshVar_13;if ( true ) {

        if(tom_l== tom_make_zfalse()) {
          return "("+ genZExpr(tom_r) +")";
        }
        else if (tom_r== tom_make_zfalse()) {
          return "("+ genZExpr(tom_l) +")";
        }
        return "("+genZExpr(tom_l)+") \\/ ("+genZExpr(tom_r)+")";
      }}}}}}if (tom_is_fun_sym_znot(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_14=tom_get_slot_znot_Nex(tomMatch101NameNumberfreshSubject_1);if ( true ) {
 return "~("+genZExpr(tomMatch101NameNumber_freshVar_14)+")"; }}}if (tom_is_fun_sym_zequiv(tomMatch101NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_15=tom_get_slot_zequiv_Lte(tomMatch101NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch101NameNumber_freshVar_16=tom_get_slot_zequiv_Rte(tomMatch101NameNumberfreshSubject_1);if ( true ) {

        return "("+genZExpr(tomMatch101NameNumber_freshVar_15)+") <-> ("+genZExpr(tomMatch101NameNumber_freshVar_16)+")";
      }}}}}}

    return "errorZExpr";
  }

  public String genZAxiom(ZAxiom axiom) {
    if (tom_is_sort_ZAxiom(axiom)) {{  tom.engine.adt.zenon.types.ZAxiom  tomMatch102NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZAxiom )axiom);if (tom_is_fun_sym_zaxiom(tomMatch102NameNumberfreshSubject_1)) {{  String  tomMatch102NameNumber_freshVar_0=tom_get_slot_zaxiom_Name(tomMatch102NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZExpr  tomMatch102NameNumber_freshVar_1=tom_get_slot_zaxiom_Ax(tomMatch102NameNumberfreshSubject_1);if ( true ) {

        // manage builtins
        String realName = tomiltools.replaceNumbersByString(tomMatch102NameNumber_freshVar_0);
        return "Parameter " + realName +" :\n    " + genZExpr(tomMatch102NameNumber_freshVar_1) + ".\n";
      }}}}}}

    return "errorZAxiom";
  }

  public String genZAxiomList(ZAxiomList axlist) {
    StringBuffer res = new StringBuffer();
    while (!axlist.isEmptyzby()) {
      res.append(genZAxiom(axlist.getHeadzby()));
      axlist = axlist.getTailzby();
    }
    return res.toString();
  }

  public String genZSpec(ZSpec spec) {
    if (tom_is_sort_ZSpec(spec)) {{  tom.engine.adt.zenon.types.ZSpec  tomMatch103NameNumberfreshSubject_1=(( tom.engine.adt.zenon.types.ZSpec )spec);if (tom_is_fun_sym_zthm(tomMatch103NameNumberfreshSubject_1)) {{  tom.engine.adt.zenon.types.ZExpr  tomMatch103NameNumber_freshVar_0=tom_get_slot_zthm_Thm(tomMatch103NameNumberfreshSubject_1);{  tom.engine.adt.zenon.types.ZAxiomList  tomMatch103NameNumber_freshVar_1=tom_get_slot_zthm_By(tomMatch103NameNumberfreshSubject_1);if ( true ) {

        return "\n" 
          + genZExpr(tomMatch103NameNumber_freshVar_0) 
          + "\n" 
          + genZAxiomList(tomMatch103NameNumber_freshVar_1)+"\n";
      }}}}}}

    return "errorZSpec";
  }

  public String genFunctionSymbolDeclaration(String symbolName) {
    StringBuffer res = new StringBuffer();
    res.append("Parameter ");
    res.append(tomiltools.replaceNumbersByString(symbolName)+" :");
    // take care of the arity
    List names = tomiltools.subtermList(symbolName);
    for(int i = 0; i<names.size();i++) {
      res.append(" T ->");
    }
    res.append(" T.\n");
    return res.toString();
  }

  public String genZSpecCollection(Collection collection) {
    StringBuffer out = new StringBuffer();

    out.append("\nRequire Import zenon.\n");
    out.append("\nParameter T S : Set.\n");

    // collects all used symbols
    Collection symbols = new HashSet();
    Iterator it = collection.iterator();
    while(it.hasNext()) {
      ZSpec local = (ZSpec) it.next();
      symbols.addAll(tomiltools.collectSymbolsFromZSpec(local));
    }

    // Generates types for symbol functions
    it = symbols.iterator();
    while(it.hasNext()) {
      String symbolName = (String) it.next();
      out.append(genFunctionSymbolDeclaration(symbolName));
      // declares the subterm functions if necessary
      List names = tomiltools.subtermList(symbolName);
      if(names.size() != 0) {
        out.append("Parameter ");
        Iterator nameIt = names.iterator();
        while(nameIt.hasNext()) {
          String localName = (String) nameIt.next();
          out.append("_" + localName + " ");
        }
        out.append(": T -> T.\n");
      }
    }


    out.append("Parameter symb : T -> S.\n");
    // XXX: define True
    out.append("Parameter true_is_true : True.\n");
    // Generates types for symbols
    it = symbols.iterator();
    out.append("Parameter ");
    while(it.hasNext()) {
      String symbolName = (String) it.next();
      out.append(genZSymbol(tom_make_zsymbol(symbolName)) +" ");
    }
    out.append(": S.\n");


    // Generates the axioms for coq
    ZAxiomList axiomsDef = tomiltools.symbolsDefinition(symbols);
    ZAxiomList axiomsSub = tomiltools.subtermsDefinition(symbols);
    ZAxiomList axioms = tom_append_list_zby(axiomsDef,tom_append_list_zby(axiomsSub,tom_empty_list_zby()));
    while (!axioms.isEmptyzby()) {
      out.append(genZAxiom(axioms.getHeadzby()));
      axioms = axioms.getTailzby();
    }

    // Generates the different proof obligations
    int number=1;
    it = collection.iterator();
    while (it.hasNext()) {
      out.append("\n%%begin-auto-proof\n");
      //out.append("%%location: []\n");
      out.append("%%name: theorem"+number+"\n");
      //out.append("%%syntax: tom\n");
      //out.append("%%statement\n");
      out.append(genZSpec((ZSpec)it.next()));

      // XXX: Outputs the axiom for True (Newer versions of zenon may remove this need)
      out.append("Parameter true_is_true : True.\n");

      // Generates types for symbol functions
      // (otherwise, zenon can not know T is not empty)
      // also adds a Parameter fake : T. to make sure zenon knows T is
      // not empty
      Iterator symbIt = symbols.iterator();
      while(symbIt.hasNext()) {
        String symbolName = (String) symbIt.next();
        out.append(genFunctionSymbolDeclaration(symbolName));
      }
      out.append("Parameter tom_fake : T.\n");
    
      out.append("%%end-auto-proof\n");
      number++;
    }
    return out.toString();
  }
}
