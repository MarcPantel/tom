/* Generated by TOM (version 2.4alpha): Do not edit this file *//*
 * Copyright (c) 2005-2006, INRIA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.  
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the INRIA nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package tom.engine.compiler.antipattern;

import java.io.*;
import java.util.*;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;

import tom.engine.exception.*;

import tom.library.strategy.mutraveler.MuTraveler;

import jjtraveler.reflective.VisitableVisitor;
import tom.library.strategy.mutraveler.MuStrategy;
import jjtraveler.VisitFailure;

/**
 * Contains methods for transforming an anti-pattern problem into a disunification one
 */
public class TomAntiPatternTransform {	

//	------------------------------------------------------------
//	%include { adt/tomconstraint/TomConstraint.tom }
  /* Generated by TOM (version 2.4alpha): Do not edit this file *//* Generated by TOM (version 2.4alpha): Do not edit this file *//* Generated by TOM (version 2.4alpha): Do not edit this file */ private static boolean tom_terms_equal_String( String  t1,  String  t2) {  return  (t1.equals(t2))  ;}  /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ private static boolean tom_terms_equal_TomType(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomName(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomTerm(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_Option(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_OptionList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_OConstraintList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_AConstraintList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_Constraint(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_ConstraintList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static  tom.engine.adt.tomtype.types.TomType  tom_make_EmptyType() { return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make(); }private static  tom.engine.adt.tomname.types.TomName  tom_make_Name( String  t0) { return  tom.engine.adt.tomname.types.tomname.Name.make(t0); }private static boolean tom_is_fun_sym_AntiTerm( tom.engine.adt.tomterm.types.TomTerm  t) {  return  (t!=null) && t.isAntiTerm()  ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_AntiTerm_TomTerm( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getTomTerm()  ;}private static boolean tom_is_fun_sym_Variable( tom.engine.adt.tomterm.types.TomTerm  t) {  return  (t!=null) && t.isVariable()  ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_Variable( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2,  tom.engine.adt.tomconstraint.types.ConstraintList  t3) { return  tom.engine.adt.tomterm.types.tomterm.Variable.make(t0, t1, t2, t3); }private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_Variable_Option( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getOption()  ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_Variable_AstName( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getAstName()  ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_Variable_AstType( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getAstType()  ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_Variable_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getConstraints()  ;}private static boolean tom_is_fun_sym_ForAll( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isForAll()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_ForAll( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.tomconstraint.types.Constraint  t1) { return  tom.engine.adt.tomconstraint.types.constraint.ForAll.make(t0, t1); }private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_ForAll_var( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getvar()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_slot_ForAll_cons( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getcons()  ;}private static boolean tom_is_fun_sym_Exists( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isExists()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_Exists( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.tomconstraint.types.Constraint  t1) { return  tom.engine.adt.tomconstraint.types.constraint.Exists.make(t0, t1); }private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_Exists_var( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getvar()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_slot_Exists_cons( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getcons()  ;}private static boolean tom_is_fun_sym_NEqualConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isNEqualConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_NEqualConstraint( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return  tom.engine.adt.tomconstraint.types.constraint.NEqualConstraint.make(t0, t1); }private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_NEqualConstraint_pattern( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getpattern()  ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_NEqualConstraint_genTerm( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getgenTerm()  ;}private static boolean tom_is_fun_sym_EqualConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isEqualConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_EqualConstraint( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return  tom.engine.adt.tomconstraint.types.constraint.EqualConstraint.make(t0, t1); }private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_EqualConstraint_pattern( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getpattern()  ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_EqualConstraint_genTerm( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getgenTerm()  ;}private static boolean tom_is_fun_sym_OrConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isOrConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_OrConstraint( tom.engine.adt.tomconstraint.types.OConstraintList  t0) { return  tom.engine.adt.tomconstraint.types.constraint.OrConstraint.make(t0); }private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_get_slot_OrConstraint_clo( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getclo()  ;}private static boolean tom_is_fun_sym_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isAndConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_AndConstraint( tom.engine.adt.tomconstraint.types.AConstraintList  t0) { return  tom.engine.adt.tomconstraint.types.constraint.AndConstraint.make(t0); }private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_get_slot_AndConstraint_cla( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getcla()  ;}private static boolean tom_is_fun_sym_Neg( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  (t!=null) && t.isNeg()  ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_Neg( tom.engine.adt.tomconstraint.types.Constraint  t0) { return  tom.engine.adt.tomconstraint.types.constraint.Neg.make(t0); }private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_slot_Neg_c( tom.engine.adt.tomconstraint.types.Constraint  t) {  return  t.getc()  ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_empty_list_concOption() { return  tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ; }private static  tom.engine.adt.tomoption.types.OptionList  tom_cons_list_concOption( tom.engine.adt.tomoption.types.Option  e,  tom.engine.adt.tomoption.types.OptionList  l) { return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(e,l) ; }private static  tom.engine.adt.tomoption.types.Option  tom_get_head_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {  return  l.getHeadconcOption()  ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_tail_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {  return  l.getTailconcOption()  ;}private static boolean tom_is_empty_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {  return  l.isEmptyconcOption()  ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList  l1,  tom.engine.adt.tomoption.types.OptionList  l2) {    if(tom_is_empty_concOption_OptionList(l1)) {     return l2;    } else if(tom_is_empty_concOption_OptionList(l2)) {     return l1;    } else if(tom_is_empty_concOption_OptionList(( tom.engine.adt.tomoption.types.OptionList )tom_get_tail_concOption_OptionList(l1))) {     return ( tom.engine.adt.tomoption.types.OptionList )tom_cons_list_concOption(( tom.engine.adt.tomoption.types.Option )tom_get_head_concOption_OptionList(l1),l2);    } else {      return ( tom.engine.adt.tomoption.types.OptionList )tom_cons_list_concOption(( tom.engine.adt.tomoption.types.Option )tom_get_head_concOption_OptionList(l1),tom_append_list_concOption(( tom.engine.adt.tomoption.types.OptionList )tom_get_tail_concOption_OptionList(l1),l2));    }   }  private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end) {    if(tom_terms_equal_OptionList(begin,end)) {      return ( tom.engine.adt.tomoption.types.OptionList )tom_empty_list_concOption();    } else {      return ( tom.engine.adt.tomoption.types.OptionList )tom_cons_list_concOption(( tom.engine.adt.tomoption.types.Option )tom_get_head_concOption_OptionList(begin),( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption(( tom.engine.adt.tomoption.types.OptionList )tom_get_tail_concOption_OptionList(begin),end));    }   }  private static boolean tom_is_fun_sym_concOr( tom.engine.adt.tomconstraint.types.OConstraintList  t) {  return  t instanceof tom.engine.adt.tomconstraint.types.oconstraintlist.ConsconcOr || t instanceof tom.engine.adt.tomconstraint.types.oconstraintlist.EmptyconcOr  ;}private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_empty_list_concOr() { return  tom.engine.adt.tomconstraint.types.oconstraintlist.EmptyconcOr.make() ; }private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_cons_list_concOr( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.OConstraintList  l) { return  tom.engine.adt.tomconstraint.types.oconstraintlist.ConsconcOr.make(e,l) ; }private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_concOr_OConstraintList( tom.engine.adt.tomconstraint.types.OConstraintList  l) {  return  l.getHeadconcOr()  ;}private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_get_tail_concOr_OConstraintList( tom.engine.adt.tomconstraint.types.OConstraintList  l) {  return  l.getTailconcOr()  ;}private static boolean tom_is_empty_concOr_OConstraintList( tom.engine.adt.tomconstraint.types.OConstraintList  l) {  return  l.isEmptyconcOr()  ;}private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_append_list_concOr( tom.engine.adt.tomconstraint.types.OConstraintList  l1,  tom.engine.adt.tomconstraint.types.OConstraintList  l2) {    if(tom_is_empty_concOr_OConstraintList(l1)) {     return l2;    } else if(tom_is_empty_concOr_OConstraintList(l2)) {     return l1;    } else if(tom_is_empty_concOr_OConstraintList(( tom.engine.adt.tomconstraint.types.OConstraintList )tom_get_tail_concOr_OConstraintList(l1))) {     return ( tom.engine.adt.tomconstraint.types.OConstraintList )tom_cons_list_concOr(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concOr_OConstraintList(l1),l2);    } else {      return ( tom.engine.adt.tomconstraint.types.OConstraintList )tom_cons_list_concOr(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concOr_OConstraintList(l1),tom_append_list_concOr(( tom.engine.adt.tomconstraint.types.OConstraintList )tom_get_tail_concOr_OConstraintList(l1),l2));    }   }  private static  tom.engine.adt.tomconstraint.types.OConstraintList  tom_get_slice_concOr( tom.engine.adt.tomconstraint.types.OConstraintList  begin,  tom.engine.adt.tomconstraint.types.OConstraintList  end) {    if(tom_terms_equal_OConstraintList(begin,end)) {      return ( tom.engine.adt.tomconstraint.types.OConstraintList )tom_empty_list_concOr();    } else {      return ( tom.engine.adt.tomconstraint.types.OConstraintList )tom_cons_list_concOr(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concOr_OConstraintList(begin),( tom.engine.adt.tomconstraint.types.OConstraintList )tom_get_slice_concOr(( tom.engine.adt.tomconstraint.types.OConstraintList )tom_get_tail_concOr_OConstraintList(begin),end));    }   }  private static boolean tom_is_fun_sym_concAnd( tom.engine.adt.tomconstraint.types.AConstraintList  t) {  return  t instanceof tom.engine.adt.tomconstraint.types.aconstraintlist.ConsconcAnd || t instanceof tom.engine.adt.tomconstraint.types.aconstraintlist.EmptyconcAnd  ;}private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_empty_list_concAnd() { return  tom.engine.adt.tomconstraint.types.aconstraintlist.EmptyconcAnd.make() ; }private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_cons_list_concAnd( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.AConstraintList  l) { return  tom.engine.adt.tomconstraint.types.aconstraintlist.ConsconcAnd.make(e,l) ; }private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_concAnd_AConstraintList( tom.engine.adt.tomconstraint.types.AConstraintList  l) {  return  l.getHeadconcAnd()  ;}private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_get_tail_concAnd_AConstraintList( tom.engine.adt.tomconstraint.types.AConstraintList  l) {  return  l.getTailconcAnd()  ;}private static boolean tom_is_empty_concAnd_AConstraintList( tom.engine.adt.tomconstraint.types.AConstraintList  l) {  return  l.isEmptyconcAnd()  ;}private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_append_list_concAnd( tom.engine.adt.tomconstraint.types.AConstraintList  l1,  tom.engine.adt.tomconstraint.types.AConstraintList  l2) {    if(tom_is_empty_concAnd_AConstraintList(l1)) {     return l2;    } else if(tom_is_empty_concAnd_AConstraintList(l2)) {     return l1;    } else if(tom_is_empty_concAnd_AConstraintList(( tom.engine.adt.tomconstraint.types.AConstraintList )tom_get_tail_concAnd_AConstraintList(l1))) {     return ( tom.engine.adt.tomconstraint.types.AConstraintList )tom_cons_list_concAnd(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concAnd_AConstraintList(l1),l2);    } else {      return ( tom.engine.adt.tomconstraint.types.AConstraintList )tom_cons_list_concAnd(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concAnd_AConstraintList(l1),tom_append_list_concAnd(( tom.engine.adt.tomconstraint.types.AConstraintList )tom_get_tail_concAnd_AConstraintList(l1),l2));    }   }  private static  tom.engine.adt.tomconstraint.types.AConstraintList  tom_get_slice_concAnd( tom.engine.adt.tomconstraint.types.AConstraintList  begin,  tom.engine.adt.tomconstraint.types.AConstraintList  end) {    if(tom_terms_equal_AConstraintList(begin,end)) {      return ( tom.engine.adt.tomconstraint.types.AConstraintList )tom_empty_list_concAnd();    } else {      return ( tom.engine.adt.tomconstraint.types.AConstraintList )tom_cons_list_concAnd(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concAnd_AConstraintList(begin),( tom.engine.adt.tomconstraint.types.AConstraintList )tom_get_slice_concAnd(( tom.engine.adt.tomconstraint.types.AConstraintList )tom_get_tail_concAnd_AConstraintList(begin),end));    }   }  private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_empty_list_concConstraint() { return  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ; }private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_cons_list_concConstraint( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.ConstraintList  l) { return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make(e,l) ; }private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {  return  l.getHeadconcConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_tail_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {  return  l.getTailconcConstraint()  ;}private static boolean tom_is_empty_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {  return  l.isEmptyconcConstraint()  ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {    if(tom_is_empty_concConstraint_ConstraintList(l1)) {     return l2;    } else if(tom_is_empty_concConstraint_ConstraintList(l2)) {     return l1;    } else if(tom_is_empty_concConstraint_ConstraintList(( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_tail_concConstraint_ConstraintList(l1))) {     return ( tom.engine.adt.tomconstraint.types.ConstraintList )tom_cons_list_concConstraint(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concConstraint_ConstraintList(l1),l2);    } else {      return ( tom.engine.adt.tomconstraint.types.ConstraintList )tom_cons_list_concConstraint(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concConstraint_ConstraintList(l1),tom_append_list_concConstraint(( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_tail_concConstraint_ConstraintList(l1),l2));    }   }  private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end) {    if(tom_terms_equal_ConstraintList(begin,end)) {      return ( tom.engine.adt.tomconstraint.types.ConstraintList )tom_empty_list_concConstraint();    } else {      return ( tom.engine.adt.tomconstraint.types.ConstraintList )tom_cons_list_concConstraint(( tom.engine.adt.tomconstraint.types.Constraint )tom_get_head_concConstraint_ConstraintList(begin),( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint(( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_tail_concConstraint_ConstraintList(begin),end));    }   }   /* Generated by TOM (version 2.4alpha): Do not edit this file */private static boolean tom_terms_equal_Strategy(Object t1, Object t2) {  return t1.equals(t2) ;}private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_mu( tom.library.strategy.mutraveler.MuStrategy  var,  tom.library.strategy.mutraveler.MuStrategy  v) { return  new tom.library.strategy.mutraveler.Mu(var,v) ; }/* Generated by TOM (version 2.4alpha): Do not edit this file */private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_Identity() { return  new tom.library.strategy.mutraveler.Identity() ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_Sequence( tom.library.strategy.mutraveler.MuStrategy  first,  tom.library.strategy.mutraveler.MuStrategy  then) { return  new tom.library.strategy.mutraveler.Sequence(first,then) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_All( tom.library.strategy.mutraveler.MuStrategy  v) { return  new tom.library.strategy.mutraveler.All(v) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_MuVar( String  name) { return  new tom.library.strategy.mutraveler.MuVar(name) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_OneId( tom.library.strategy.mutraveler.MuStrategy  v) { return  new tom.library.strategy.mutraveler.OneId(v) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_SequenceId( tom.library.strategy.mutraveler.MuStrategy  first,  tom.library.strategy.mutraveler.MuStrategy  then) { return  new tom.library.strategy.mutraveler.SequenceId(first,then) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_ChoiceId( tom.library.strategy.mutraveler.MuStrategy  first,  tom.library.strategy.mutraveler.MuStrategy  then) { return  new tom.library.strategy.mutraveler.ChoiceId(first,then) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_TopDown( tom.library.strategy.mutraveler.MuStrategy  v) { return tom_make_mu(tom_make_MuVar("x"),tom_make_Sequence(v,tom_make_All(tom_make_MuVar("x")))) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_OnceTopDownId( tom.library.strategy.mutraveler.MuStrategy  v) { return tom_make_mu(tom_make_MuVar("x"),tom_make_ChoiceId(v,tom_make_OneId(tom_make_MuVar("x")))) ; }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_InnermostId( tom.library.strategy.mutraveler.MuStrategy  v) { return tom_make_mu(tom_make_MuVar("x"),tom_make_Sequence(tom_make_All(tom_make_MuVar("x")),tom_make_SequenceId(v,tom_make_MuVar("x")))) ; }  /* Generated by TOM (version 2.4alpha): Do not edit this file */private static boolean tom_terms_equal_ArrayList(Object l1, Object l2) {  return  l1.equals(l2)  ;} 

	
//	------------------------------------------------------------
	
	private static int varCounter = 0;	
	
	/**
	 * transforms the anti-pattern problem received into a disunification one
	 * @param c the anti-pattern problem to transform 
	 * @return corresponding disunification problem 
	 */
	public static Constraint transform(Constraint c) {	
		//varCounter = 0;		
		try {
		// eliminate anti
			Constraint noAnti = applyMainRule(c);
		// transform the problem into a disunification one		
			return (Constraint) MuTraveler.init(tom_make_InnermostId(tom_make_TransformIntoDisunification())).visit(noAnti);			
		} catch(VisitFailure e) {
			throw new TomRuntimeException("Reduction failed on: " + c + "\nException:" + e.getMessage());			
		}
    // System.out.println("Result after main rule: " + TomAntiPatternUtils.formatConstraint(noAnti));
    // System.out.println("Disunification problem: " + TomAntiPatternUtils.formatConstraint(disunifProblem));
	}	
	
	/**
   * applies the main rule that transforms ap problems
   * into dis-unification ones
   */
  private static Constraint applyMainRule(Constraint c) throws VisitFailure {
    // first get the constraint without the anti
    Constraint cNoAnti = (Constraint) tom_make_OnceTopDownId(tom_make_ElimAnti()).apply(c);
    // if nothing changed, time to exit
    if(cNoAnti == c) {
      return c;
    } 
    cNoAnti = tom_make_Neg(applyMainRule(cNoAnti));

    ArrayList quantifiedVarList = new ArrayList();
    // TODO: use a congruence here and remove ApplyStrategy
    MuTraveler.init(tom_make_OnceTopDownId(tom_make_ApplyStrategy(quantifiedVarList))).visit(c);
    //System.out.println("quantifiedVarList = " + quantifiedVarList);
    Iterator it = quantifiedVarList.iterator();
    while(it.hasNext()) {
      TomTerm t = (TomTerm)it.next();
      cNoAnti = tom_make_ForAll(t,cNoAnti);
    }

    // get the constraint with a variable instead of anti
    String varName = "v" + (varCounter++);
    TomTerm abstractVariable = tom_make_Variable(tom_empty_list_concOption(),tom_make_Name(varName),tom_make_EmptyType(),tom_empty_list_concConstraint());
    Constraint cAntiReplaced = (Constraint) tom_make_OnceTopDownId(tom_make_SequenceId(tom_make_ElimAnti(),tom_make_AbstractTerm(abstractVariable))).apply(c);
    cAntiReplaced = applyMainRule(cAntiReplaced);

    /*
     * to improve the efficiency, we should
     * first, abstract anti symbols, and get cAntiReplaced
     * store the tuple (abstractedTerm, variable) during the abstraction
     * then, re-instantiated the abstractedVariables to deduce cNoAnti
     * this would avoid the double recrusive traversal
     */
    return tom_make_AndConstraint(tom_cons_list_concAnd(tom_make_Exists(abstractVariable,cAntiReplaced),tom_cons_list_concAnd(cNoAnti,tom_empty_list_concAnd())));
  }
	
	// the strategy that handles the variables inside an anti
	// symbol for beeing quantified 
	 private static class ApplyStrategy  extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy   { private  java.util.ArrayList  quantifiedVarList;  public ApplyStrategy(  java.util.ArrayList  quantifiedVarList ) { super(tom_make_Identity() ); this.quantifiedVarList=quantifiedVarList; } public  java.util.ArrayList  getquantifiedVarList() { return quantifiedVarList;} public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm(  tom.engine.adt.tomterm.types.TomTerm  tom__arg )  throws jjtraveler.VisitFailure { if(tom__arg instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match1_1=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg); if (tom_is_fun_sym_AntiTerm(tom_match1_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match1_1_TomTerm=tom_get_slot_AntiTerm_TomTerm(tom_match1_1); { tom.engine.adt.tomterm.types.TomTerm  tom_p=tom_match1_1_TomTerm; if ( true ) {



				MuTraveler.init(tom_make_TopDown(tom_make_AnalyzeTerm(quantifiedVarList,tom_p))).visit(tom_p);				
				// now it has to stop
				return tom_p;
			 } } } } } } return super.visit_TomTerm(tom__arg) ;  } }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_ApplyStrategy( java.util.ArrayList  t0) { return new ApplyStrategy(t0); }


	
	// quantifies the variables in positive positions
	 private static class AnalyzeTerm  extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy   { private  java.util.ArrayList  quantifiedVarList;  private  tom.engine.adt.tomterm.types.TomTerm  subject;  public AnalyzeTerm(  java.util.ArrayList  quantifiedVarList ,   tom.engine.adt.tomterm.types.TomTerm  subject ) { super(tom_make_Identity() ); this.quantifiedVarList=quantifiedVarList; this.subject=subject; } public  java.util.ArrayList  getquantifiedVarList() { return quantifiedVarList;} public  tom.engine.adt.tomterm.types.TomTerm  getsubject() { return subject;} public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm(  tom.engine.adt.tomterm.types.TomTerm  tom__arg )  throws jjtraveler.VisitFailure { if(tom__arg instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match2_1=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg); if (tom_is_fun_sym_Variable(tom_match2_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_v=tom_match2_1; if ( true ) {


				//System.out.println("Analyzing " + `v + " position=" + getPosition() );
        MuStrategy useOmegaPath = (MuStrategy)getPosition().getOmegaPath(tom_make_ElimAnti());				
        TomTerm res = (TomTerm) useOmegaPath.visit(subject);
				// if no anti-symbol found, then the variable can be quantified
				if(res == subject) { // there was no anti symbol
          quantifiedVarList.add(tom_v);
				}
			 } } } } } return super.visit_TomTerm(tom__arg) ;  } }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_AnalyzeTerm( java.util.ArrayList  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return new AnalyzeTerm(t0,t1); }


	
	// returns a term without the first negation that it finds
	 private static class ElimAnti  extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy   { public ElimAnti( ) { super(tom_make_Identity() ); } public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm(  tom.engine.adt.tomterm.types.TomTerm  tom__arg )  throws jjtraveler.VisitFailure { if(tom__arg instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match3_1=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg); if (tom_is_fun_sym_AntiTerm(tom_match3_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match3_1_TomTerm=tom_get_slot_AntiTerm_TomTerm(tom_match3_1); { tom.engine.adt.tomterm.types.TomTerm  tom_p=tom_match3_1_TomTerm; if ( true ) {

 return tom_p;  } } } } } } return super.visit_TomTerm(tom__arg) ;  } }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_ElimAnti() { return new ElimAnti(); }


	
	// returns a term with the first negation that it finds
	// replaced by a fresh variable
	 private static class AbstractTerm  extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy   { private  tom.engine.adt.tomterm.types.TomTerm  variable;  public AbstractTerm(  tom.engine.adt.tomterm.types.TomTerm  variable ) { super(tom_make_Identity() ); this.variable=variable; } public  tom.engine.adt.tomterm.types.TomTerm  getvariable() { return variable;} public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm(  tom.engine.adt.tomterm.types.TomTerm  tom__arg )  throws jjtraveler.VisitFailure { if(tom__arg instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match4_1=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg); if ( true ) {

 return variable;  } } } return super.visit_TomTerm(tom__arg) ;  } }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_AbstractTerm( tom.engine.adt.tomterm.types.TomTerm  t0) { return new AbstractTerm(t0); }


	
	// applies symple logic rules for eliminating 
	// the not and thus creating a real disunification problem
	 private static class TransformIntoDisunification  extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy   { public TransformIntoDisunification( ) { super(tom_make_Identity() ); } public  tom.engine.adt.tomconstraint.types.Constraint  visit_Constraint(  tom.engine.adt.tomconstraint.types.Constraint  tom__arg )  throws jjtraveler.VisitFailure { if(tom__arg instanceof  tom.engine.adt.tomconstraint.types.Constraint ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg); if (tom_is_fun_sym_Exists(tom_match5_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_var=tom_get_slot_Exists_var(tom_match5_1); { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_cons=tom_get_slot_Exists_cons(tom_match5_1); { tom.engine.adt.tomterm.types.TomTerm  tom_a=tom_match5_1_var; if (tom_is_fun_sym_Exists(tom_match5_1_cons) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_e=tom_match5_1_cons; { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_cons_var=tom_get_slot_Exists_var(tom_match5_1_cons); { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_cons_cons=tom_get_slot_Exists_cons(tom_match5_1_cons); { tom.engine.adt.tomterm.types.TomTerm  tom_renamedvar_a_1=tom_match5_1_cons_var; if (tom_terms_equal_TomTerm(tom_a, tom_renamedvar_a_1)) { if ( true ) {


 return tom_e;  } } } } } } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_AndConstraint(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomconstraint.types.AConstraintList  tom_match5_1_c_cla=tom_get_slot_AndConstraint_cla(tom_match5_1_c); { tom.engine.adt.tomconstraint.types.AConstraintList  tom_a=tom_match5_1_c_cla; if ( true ) {



				AConstraintList l = tom_a;
				OConstraintList result = tom_empty_list_concOr();			
				while(!l.isEmptyconcAnd()){
					result = tom_cons_list_concOr(tom_make_Neg(l.getHeadconcAnd()),tom_append_list_concOr(result,tom_empty_list_concOr()));
					l = l.getTailconcAnd();
				}				
				result = result.reverse();
				return tom_make_OrConstraint(result);
			 } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_OrConstraint(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomconstraint.types.OConstraintList  tom_match5_1_c_clo=tom_get_slot_OrConstraint_clo(tom_match5_1_c); { tom.engine.adt.tomconstraint.types.OConstraintList  tom_a=tom_match5_1_c_clo; if ( true ) {



				OConstraintList l = tom_a;
				AConstraintList result = tom_empty_list_concAnd();
				while(!l.isEmptyconcOr()){
					result = tom_cons_list_concAnd(tom_make_Neg(l.getHeadconcOr()),tom_append_list_concAnd(result,tom_empty_list_concAnd()));
					l = l.getTailconcOr();
				}
				result = result.reverse();
				return tom_make_AndConstraint(result);
			 } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_Neg(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c_c=tom_get_slot_Neg_c(tom_match5_1_c); { tom.engine.adt.tomconstraint.types.Constraint  tom_x=tom_match5_1_c_c; if ( true ) {


 return tom_x;  } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_EqualConstraint(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_pattern=tom_get_slot_EqualConstraint_pattern(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_genTerm=tom_get_slot_EqualConstraint_genTerm(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_a=tom_match5_1_c_pattern; { tom.engine.adt.tomterm.types.TomTerm  tom_b=tom_match5_1_c_genTerm; if ( true ) {


 return tom_make_NEqualConstraint(tom_a,tom_b); } } } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_NEqualConstraint(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_pattern=tom_get_slot_NEqualConstraint_pattern(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_genTerm=tom_get_slot_NEqualConstraint_genTerm(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_a=tom_match5_1_c_pattern; { tom.engine.adt.tomterm.types.TomTerm  tom_b=tom_match5_1_c_genTerm; if ( true ) {


 return tom_make_EqualConstraint(tom_a,tom_b); } } } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_Exists(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_var=tom_get_slot_Exists_var(tom_match5_1_c); { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c_cons=tom_get_slot_Exists_cons(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_v=tom_match5_1_c_var; { tom.engine.adt.tomconstraint.types.Constraint  tom_c=tom_match5_1_c_cons; if ( true ) {


 return tom_make_ForAll(tom_v,tom_make_Neg(tom_c));  } } } } } } } } if (tom_is_fun_sym_Neg(tom_match5_1) ||  false ) { { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c=tom_get_slot_Neg_c(tom_match5_1); if (tom_is_fun_sym_ForAll(tom_match5_1_c) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match5_1_c_var=tom_get_slot_ForAll_var(tom_match5_1_c); { tom.engine.adt.tomconstraint.types.Constraint  tom_match5_1_c_cons=tom_get_slot_ForAll_cons(tom_match5_1_c); { tom.engine.adt.tomterm.types.TomTerm  tom_v=tom_match5_1_c_var; { tom.engine.adt.tomconstraint.types.Constraint  tom_c=tom_match5_1_c_cons; if ( true ) {


 return tom_make_Exists(tom_v,tom_make_Neg(tom_c));  } } } } } } } } } } return super.visit_Constraint(tom__arg) ;  } }private static  tom.library.strategy.mutraveler.MuStrategy  tom_make_TransformIntoDisunification() { return new TransformIntoDisunification(); }

		
} // end class
