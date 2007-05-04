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
 * Radu Kopetz e-mail: Radu.Kopetz@loria.fr
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/
package tom.engine.compiler.generator;

import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tomexpression.types.expression.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomname.types.tomname.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.tomterm.*;
import tom.library.sl.*;
import tom.engine.tools.SymbolTable;
import tom.engine.exception.TomRuntimeException;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.TomBase;

import tom.engine.compiler.*;
/**
 * Array Generator
 */
public class ArrayGenerator implements IBaseGenerator{

  /* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5alpha): Do not edit this file */ private static boolean tom_equal_term_TomType(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomType(Object t) { return  t instanceof tom.engine.adt.tomtype.types.TomType ;}private static boolean tom_equal_term_TomName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomName(Object t) { return  t instanceof tom.engine.adt.tomname.types.TomName ;}private static boolean tom_equal_term_Expression(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Expression(Object t) { return  t instanceof tom.engine.adt.tomexpression.types.Expression ;}private static boolean tom_equal_term_TomTerm(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomTerm(Object t) { return  t instanceof tom.engine.adt.tomterm.types.TomTerm ;}private static boolean tom_equal_term_OptionList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_OptionList(Object t) { return  t instanceof tom.engine.adt.tomoption.types.OptionList ;}private static boolean tom_equal_term_Constraint(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Constraint(Object t) { return  t instanceof tom.engine.adt.tomconstraint.types.Constraint ;}private static boolean tom_equal_term_ConstraintList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ConstraintList(Object t) { return  t instanceof tom.engine.adt.tomconstraint.types.ConstraintList ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_TomTermToExpression( tom.engine.adt.tomterm.types.TomTerm  t0) { return  tom.engine.adt.tomexpression.types.expression.TomTermToExpression.make(t0) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_Negation( tom.engine.adt.tomexpression.types.Expression  t0) { return  tom.engine.adt.tomexpression.types.expression.Negation.make(t0) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_And( tom.engine.adt.tomexpression.types.Expression  t0,  tom.engine.adt.tomexpression.types.Expression  t1) { return  tom.engine.adt.tomexpression.types.expression.And.make(t0, t1) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_GreaterThan( tom.engine.adt.tomexpression.types.Expression  t0,  tom.engine.adt.tomexpression.types.Expression  t1) { return  tom.engine.adt.tomexpression.types.expression.GreaterThan.make(t0, t1) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_EqualTerm( tom.engine.adt.tomtype.types.TomType  t0,  tom.engine.adt.tomterm.types.TomTerm  t1,  tom.engine.adt.tomterm.types.TomTerm  t2) { return  tom.engine.adt.tomexpression.types.expression.EqualTerm.make(t0, t1, t2) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_AddOne( tom.engine.adt.tomterm.types.TomTerm  t0) { return  tom.engine.adt.tomexpression.types.expression.AddOne.make(t0) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_make_GetSize( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return  tom.engine.adt.tomexpression.types.expression.GetSize.make(t0, t1) ; }private static boolean tom_is_fun_sym_GetElement( tom.engine.adt.tomexpression.types.Expression  t) { return  t instanceof tom.engine.adt.tomexpression.types.expression.GetElement ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_GetElement_Opname( tom.engine.adt.tomexpression.types.Expression  t) { return  t.getOpname() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_GetElement_Codomain( tom.engine.adt.tomexpression.types.Expression  t) { return  t.getCodomain() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_GetElement_Kid1( tom.engine.adt.tomexpression.types.Expression  t) { return  t.getKid1() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_GetElement_Kid2( tom.engine.adt.tomexpression.types.Expression  t) { return  t.getKid2() ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_GetSliceArray( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomterm.types.TomTerm  t1,  tom.engine.adt.tomterm.types.TomTerm  t2,  tom.engine.adt.tomterm.types.TomTerm  t3) { return  tom.engine.adt.tomexpression.types.expression.GetSliceArray.make(t0, t1, t2, t3) ; }private static boolean tom_is_fun_sym_ConstraintToExpression( tom.engine.adt.tomexpression.types.Expression  t) { return  t instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_ConstraintToExpression( tom.engine.adt.tomconstraint.types.Constraint  t0) { return  tom.engine.adt.tomexpression.types.expression.ConstraintToExpression.make(t0) ; }private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_slot_ConstraintToExpression_cons( tom.engine.adt.tomexpression.types.Expression  t) { return  t.getcons() ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_DoWhileExpression( tom.engine.adt.tomexpression.types.Expression  t0,  tom.engine.adt.tomexpression.types.Expression  t1) { return  tom.engine.adt.tomexpression.types.expression.DoWhileExpression.make(t0, t1) ; }private static boolean tom_is_fun_sym_VariableStar( tom.engine.adt.tomterm.types.TomTerm  t) { return  t instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_VariableStar_Option( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getOption() ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_VariableStar_AstName( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstName() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_VariableStar_AstType( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstType() ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_VariableStar_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getConstraints() ;}private static boolean tom_is_fun_sym_UnamedVariableStar( tom.engine.adt.tomterm.types.TomTerm  t) { return  t instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariableStar ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_UnamedVariableStar_Option( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getOption() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_UnamedVariableStar_AstType( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstType() ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_UnamedVariableStar_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getConstraints() ;}private static boolean tom_is_fun_sym_VariableHeadArray( tom.engine.adt.tomterm.types.TomTerm  t) { return  t instanceof tom.engine.adt.tomterm.types.tomterm.VariableHeadArray ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_VariableHeadArray_Opname( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getOpname() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_VariableHeadArray_Subject( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getSubject() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_VariableHeadArray_BeginIndex( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getBeginIndex() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_VariableHeadArray_EndIndex( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getEndIndex() ;}private static boolean tom_is_fun_sym_ExpressionToTomTerm( tom.engine.adt.tomterm.types.TomTerm  t) { return  t instanceof tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_ExpressionToTomTerm( tom.engine.adt.tomexpression.types.Expression  t0) { return  tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm.make(t0) ; }private static  tom.engine.adt.tomexpression.types.Expression  tom_get_slot_ExpressionToTomTerm_AstExpression( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstExpression() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_Ref( tom.engine.adt.tomterm.types.TomTerm  t0) { return  tom.engine.adt.tomterm.types.tomterm.Ref.make(t0) ; }private static boolean tom_is_fun_sym_MatchConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) { return  t instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_MatchConstraint( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return  tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(t0, t1) ; }private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_MatchConstraint_pattern( tom.engine.adt.tomconstraint.types.Constraint  t) { return  t.getpattern() ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_MatchConstraint_subject( tom.engine.adt.tomconstraint.types.Constraint  t) { return  t.getsubject() ;} /* Generated by TOM (version 2.5alpha): Do not edit this file */private static boolean tom_equal_term_Strategy(Object t1, Object t2) { return t1.equals(t2);}private static boolean tom_is_sort_Strategy(Object t) { return  t instanceof tom.library.sl.Strategy ;}/* Generated by TOM (version 2.5alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_mu( tom.library.sl.Strategy  var,  tom.library.sl.Strategy  v) { return  new tom.library.sl.Mu(var,v) ; }private static  tom.library.sl.Strategy  tom_make_MuVar( String  name) { return  new tom.library.sl.MuVar(name) ; }private static  tom.library.sl.Strategy  tom_make_Identity() { return  new tom.library.sl.Identity() ; }private static  tom.library.sl.Strategy  tom_make_All( tom.library.sl.Strategy  v) { return  new tom.library.sl.All(v) ; }private static boolean tom_is_fun_sym_Sequence( tom.library.sl.Strategy  t) { return  (t instanceof tom.library.sl.Sequence) ;}private static  tom.library.sl.Strategy  tom_empty_list_Sequence() { return  new tom.library.sl.Identity() ; }private static  tom.library.sl.Strategy  tom_cons_list_Sequence( tom.library.sl.Strategy  head,  tom.library.sl.Strategy  tail) { return  new tom.library.sl.Sequence(head,tail) ; }private static  tom.library.sl.Strategy  tom_get_head_Sequence_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Sequence.FIRST) ;}private static  tom.library.sl.Strategy  tom_get_tail_Sequence_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.Sequence.THEN) ;}private static boolean tom_is_empty_Sequence_Strategy( tom.library.sl.Strategy  t) { return  t instanceof tom.library.sl.Identity ;}   private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy l1,  tom.library.sl.Strategy  l2) {     if(tom_is_empty_Sequence_Strategy(l1)) {       return l2;     } else if(tom_is_empty_Sequence_Strategy(l2)) {       return l1;     } else if(tom_is_fun_sym_Sequence(l1)) {       if(tom_is_empty_Sequence_Strategy(((tom_is_fun_sym_Sequence(l1))?tom_get_tail_Sequence_Strategy(l1):tom_empty_list_Sequence()))) {         return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_head_Sequence_Strategy(l1):l1),l2);       } else {         return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_head_Sequence_Strategy(l1):l1),tom_append_list_Sequence(((tom_is_fun_sym_Sequence(l1))?tom_get_tail_Sequence_Strategy(l1):tom_empty_list_Sequence()),l2));       }     } else {       return ( tom.library.sl.Strategy )tom_cons_list_Sequence(l1, l2);     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if(tom_equal_term_Strategy(begin,end)) {       return tail;     } else {       return ( tom.library.sl.Strategy )tom_cons_list_Sequence(((tom_is_fun_sym_Sequence(begin))?tom_get_head_Sequence_Strategy(begin):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((tom_is_fun_sym_Sequence(begin))?tom_get_tail_Sequence_Strategy(begin):tom_empty_list_Sequence()),end,tail));     }   }   private static boolean tom_is_fun_sym_SequenceId( tom.library.sl.Strategy  t) { return  (t instanceof tom.library.sl.SequenceId) ;}private static  tom.library.sl.Strategy  tom_empty_list_SequenceId() { return  new tom.library.sl.Identity() ; }private static  tom.library.sl.Strategy  tom_cons_list_SequenceId( tom.library.sl.Strategy  head,  tom.library.sl.Strategy  tail) { return  new tom.library.sl.SequenceId(head,tail) ; }private static  tom.library.sl.Strategy  tom_get_head_SequenceId_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.SequenceId.FIRST) ;}private static  tom.library.sl.Strategy  tom_get_tail_SequenceId_Strategy( tom.library.sl.Strategy  t) { return  (tom.library.sl.Strategy)t.getChildAt(tom.library.sl.SequenceId.THEN) ;}private static boolean tom_is_empty_SequenceId_Strategy( tom.library.sl.Strategy  t) { return  t instanceof tom.library.sl.Identity ;}   private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy l1,  tom.library.sl.Strategy  l2) {     if(tom_is_empty_SequenceId_Strategy(l1)) {       return l2;     } else if(tom_is_empty_SequenceId_Strategy(l2)) {       return l1;     } else if(tom_is_fun_sym_SequenceId(l1)) {       if(tom_is_empty_SequenceId_Strategy(((tom_is_fun_sym_SequenceId(l1))?tom_get_tail_SequenceId_Strategy(l1):tom_empty_list_SequenceId()))) {         return ( tom.library.sl.Strategy )tom_cons_list_SequenceId(((tom_is_fun_sym_SequenceId(l1))?tom_get_head_SequenceId_Strategy(l1):l1),l2);       } else {         return ( tom.library.sl.Strategy )tom_cons_list_SequenceId(((tom_is_fun_sym_SequenceId(l1))?tom_get_head_SequenceId_Strategy(l1):l1),tom_append_list_SequenceId(((tom_is_fun_sym_SequenceId(l1))?tom_get_tail_SequenceId_Strategy(l1):tom_empty_list_SequenceId()),l2));       }     } else {       return ( tom.library.sl.Strategy )tom_cons_list_SequenceId(l1, l2);     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if(tom_equal_term_Strategy(begin,end)) {       return tail;     } else {       return ( tom.library.sl.Strategy )tom_cons_list_SequenceId(((tom_is_fun_sym_SequenceId(begin))?tom_get_head_SequenceId_Strategy(begin):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((tom_is_fun_sym_SequenceId(begin))?tom_get_tail_SequenceId_Strategy(begin):tom_empty_list_SequenceId()),end,tail));     }   }    /* Generated by TOM (version 2.5alpha): Do not edit this file */ /* Generated by TOM (version 2.5alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_InnermostId( tom.library.sl.Strategy  v) { return tom_make_mu(tom_make_MuVar("_x"),tom_cons_list_Sequence(tom_make_All(tom_make_MuVar("_x")),tom_cons_list_Sequence(tom_cons_list_SequenceId(v,tom_cons_list_SequenceId(tom_make_MuVar("_x"),tom_empty_list_SequenceId())),tom_empty_list_Sequence()))) ; }   
	

  public Expression generate(Expression expression) {
    try {
      return (Expression)tom_make_InnermostId(tom_make_Generator()).visit(expression);		
    } catch (tom.library.sl.VisitFailure e) {
      throw new TomRuntimeException("Unexpected strategy failure!");
    }
  }

  // If we find ConstraintToExpression it means that this constraint was not processed	
  private static class Generator extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy  {public Generator() { super(tom_make_Identity());}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];for (int i = 0; i < getChildCount(); i++) {stratChilds[i]=getChildAt(i);}return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {for (int i = 0; i < getChildCount(); i++) {setChildAt(i,children[i]);}return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.engine.adt.tomexpression.types.Expression  visit_Expression( tom.engine.adt.tomexpression.types.Expression  tom__arg) throws tom.library.sl.VisitFailure {if (tom_is_sort_Expression(tom__arg)) {{  tom.engine.adt.tomexpression.types.Expression  tomMatch103NameNumberfreshSubject_1=(( tom.engine.adt.tomexpression.types.Expression )tom__arg);if (tom_is_fun_sym_ConstraintToExpression(tomMatch103NameNumberfreshSubject_1)) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch103NameNumber_freshVar_1=tom_get_slot_ConstraintToExpression_cons(tomMatch103NameNumberfreshSubject_1);if (tom_is_fun_sym_MatchConstraint(tomMatch103NameNumber_freshVar_1)) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_2=tom_get_slot_MatchConstraint_pattern(tomMatch103NameNumber_freshVar_1);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_3=tom_get_slot_MatchConstraint_subject(tomMatch103NameNumber_freshVar_1);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_0=tomMatch103NameNumber_freshVar_2;if (tom_is_fun_sym_VariableHeadArray(tomMatch103NameNumber_freshVar_3)) {{  tom.engine.adt.tomname.types.TomName  tomMatch103NameNumber_freshVar_4=tom_get_slot_VariableHeadArray_Opname(tomMatch103NameNumber_freshVar_3);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_5=tom_get_slot_VariableHeadArray_Subject(tomMatch103NameNumber_freshVar_3);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_6=tom_get_slot_VariableHeadArray_BeginIndex(tomMatch103NameNumber_freshVar_3);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_7=tom_get_slot_VariableHeadArray_EndIndex(tomMatch103NameNumber_freshVar_3);{  tom.engine.adt.tomname.types.TomName  tom_opName=tomMatch103NameNumber_freshVar_4;{  tom.engine.adt.tomterm.types.TomTerm  tom_subject=tomMatch103NameNumber_freshVar_5;{  tom.engine.adt.tomterm.types.TomTerm  tom_end=tomMatch103NameNumber_freshVar_7;{ boolean tomMatch103NameNumber_freshVar_9= false ;{  tom.engine.adt.tomtype.types.TomType  tomMatch103NameNumber_freshVar_8= null ;if (tom_is_fun_sym_VariableStar(tomMatch103NameNumber_freshVar_0)) {{tomMatch103NameNumber_freshVar_9= true ;tomMatch103NameNumber_freshVar_8=tom_get_slot_VariableStar_AstType(tomMatch103NameNumber_freshVar_0);}} else {if (tom_is_fun_sym_UnamedVariableStar(tomMatch103NameNumber_freshVar_0)) {{tomMatch103NameNumber_freshVar_9= true ;tomMatch103NameNumber_freshVar_8=tom_get_slot_UnamedVariableStar_AstType(tomMatch103NameNumber_freshVar_0);}}}if ((tomMatch103NameNumber_freshVar_9 ==  true )) {{  tom.engine.adt.tomterm.types.TomTerm  tom_v=tomMatch103NameNumber_freshVar_0;if ( true ) {











        Expression doWhileTest = tom_make_Negation(tom_make_GreaterThan(tom_make_TomTermToExpression(tom_make_Ref(tom_end)),tom_make_GetSize(tom_opName,tom_make_Ref(tom_subject))));
        // expression at the end of the loop 
        Expression endExpression = tom_make_ConstraintToExpression(tom_make_MatchConstraint(tom_end,tom_make_ExpressionToTomTerm(tom_make_AddOne(tom_make_Ref(tom_end)))));        
        // if we have a varStar, then add its declaration also
        if (tom_v.isVariableStar()) {
          Expression varDeclaration = tom_make_ConstraintToExpression(tom_make_MatchConstraint(tom_v,tom_make_ExpressionToTomTerm(tom_make_GetSliceArray(tom_opName,tom_make_Ref(tom_subject),tomMatch103NameNumber_freshVar_6,tom_make_Ref(tom_end)))))
;
          return tom_make_And(tom_make_DoWhileExpression(endExpression,doWhileTest),varDeclaration);
        }
        return tom_make_DoWhileExpression(endExpression,doWhileTest);		        		      
      }}}}}}}}}}}}}}}}}}}if (tom_is_fun_sym_ConstraintToExpression(tomMatch103NameNumberfreshSubject_1)) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch103NameNumber_freshVar_11=tom_get_slot_ConstraintToExpression_cons(tomMatch103NameNumberfreshSubject_1);if (tom_is_fun_sym_MatchConstraint(tomMatch103NameNumber_freshVar_11)) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_12=tom_get_slot_MatchConstraint_pattern(tomMatch103NameNumber_freshVar_11);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_13=tom_get_slot_MatchConstraint_subject(tomMatch103NameNumber_freshVar_11);{  tom.engine.adt.tomterm.types.TomTerm  tomMatch103NameNumber_freshVar_10=tomMatch103NameNumber_freshVar_12;if (tom_is_fun_sym_ExpressionToTomTerm(tomMatch103NameNumber_freshVar_10)) {{  tom.engine.adt.tomexpression.types.Expression  tomMatch103NameNumber_freshVar_14=tom_get_slot_ExpressionToTomTerm_AstExpression(tomMatch103NameNumber_freshVar_10);if (tom_is_fun_sym_GetElement(tomMatch103NameNumber_freshVar_14)) {{  tom.engine.adt.tomtype.types.TomType  tomMatch103NameNumber_freshVar_15=tom_get_slot_GetElement_Codomain(tomMatch103NameNumber_freshVar_14);if ( true ) {


        return tom_make_EqualTerm(tomMatch103NameNumber_freshVar_15,tomMatch103NameNumber_freshVar_10,tomMatch103NameNumber_freshVar_13);
      }}}}}}}}}}}}}return super.visit_Expression(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_Generator() { return new Generator(); }



 // end strategy	
}
