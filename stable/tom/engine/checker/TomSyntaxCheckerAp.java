/* Generated by TOM (version 2.4alpha): Do not edit this file *//*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2006, INRIA
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
 * Julien Guyon
 *
 **/

package tom.engine.checker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import tom.engine.TomMessage;
import tom.engine.exception.TomRuntimeException;

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

import tom.engine.xml.Constants;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import aterm.ATerm;
import tom.engine.tools.ASTFactory;

import tom.library.strategy.mutraveler.MuTraveler;
import jjtraveler.reflective.VisitableVisitor;
import jjtraveler.VisitFailure;

/**
 * The TomSyntaxChecker plugin - justs adds anti-pattern facilities to the TomSyntaxChecker.
 */
public class TomSyntaxCheckerAp extends TomSyntaxChecker {

  /* Generated by TOM (version 2.4alpha): Do not edit this file *//* Generated by TOM (version 2.4alpha): Do not edit this file *//* Generated by TOM (version 2.4alpha): Do not edit this file */   /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ /* Generated by TOM (version 2.4alpha): Do not edit this file */ private static boolean tom_terms_equal_TomType(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomName(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomNameList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomTerm(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_TomList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_OptionList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_ConstraintList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_is_fun_sym_AntiTerm( tom.engine.adt.tomterm.types.TomTerm  t) {  return  (t!=null) && t.isAntiTerm()  ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_slot_AntiTerm_TomTerm( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getTomTerm()  ;}private static boolean tom_is_fun_sym_Variable( tom.engine.adt.tomterm.types.TomTerm  t) {  return  (t!=null) && t.isVariable()  ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_Variable_Option( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getOption()  ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_Variable_AstName( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getAstName()  ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_Variable_AstType( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getAstType()  ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_Variable_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getConstraints()  ;}private static boolean tom_is_fun_sym_TermAppl( tom.engine.adt.tomterm.types.TomTerm  t) {  return  (t!=null) && t.isTermAppl()  ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_TermAppl_Option( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getOption()  ;}private static  tom.engine.adt.tomname.types.TomNameList  tom_get_slot_TermAppl_NameList( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getNameList()  ;}private static  tom.engine.adt.tomterm.types.TomList  tom_get_slot_TermAppl_Args( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getArgs()  ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_TermAppl_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) {  return  t.getConstraints()  ;} 

  /**
   * Basicaly ingnores the anti-symbol
   */
  public  TermDescription validateTerm(TomTerm term, TomType expectedType, boolean listSymbol, boolean topLevel, boolean permissive) {
    
	   if(term instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match1_1=(( tom.engine.adt.tomterm.types.TomTerm )term); if (tom_is_fun_sym_AntiTerm(tom_match1_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match1_1_TomTerm=tom_get_slot_AntiTerm_TomTerm(tom_match1_1); { tom.engine.adt.tomterm.types.TomTerm  tom_t=tom_match1_1_TomTerm; if ( true ) {



    		return super.validateTerm(tom_t, expectedType, listSymbol, topLevel, permissive);
    	 } } } } } }

	  
	  return super.validateTerm(term, expectedType, listSymbol, topLevel, permissive);
  }

  public  TermDescription analyseTerm(TomTerm term) {
   
       if(term instanceof  tom.engine.adt.tomterm.types.TomTerm ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match2_1=(( tom.engine.adt.tomterm.types.TomTerm )term); if (tom_is_fun_sym_AntiTerm(tom_match2_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match2_1_TomTerm=tom_get_slot_AntiTerm_TomTerm(tom_match2_1); if (tom_is_fun_sym_TermAppl(tom_match2_1_TomTerm) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_t=tom_match2_1_TomTerm; { tom.engine.adt.tomoption.types.OptionList  tom_match2_1_TomTerm_Option=tom_get_slot_TermAppl_Option(tom_match2_1_TomTerm); { tom.engine.adt.tomname.types.TomNameList  tom_match2_1_TomTerm_NameList=tom_get_slot_TermAppl_NameList(tom_match2_1_TomTerm); { tom.engine.adt.tomterm.types.TomList  tom_match2_1_TomTerm_Args=tom_get_slot_TermAppl_Args(tom_match2_1_TomTerm); { tom.engine.adt.tomconstraint.types.ConstraintList  tom_match2_1_TomTerm_Constraints=tom_get_slot_TermAppl_Constraints(tom_match2_1_TomTerm); if ( true ) {



    		return super.analyseTerm(tom_t);
    	 } } } } } } } } } if (tom_is_fun_sym_AntiTerm(tom_match2_1) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_match2_1_TomTerm=tom_get_slot_AntiTerm_TomTerm(tom_match2_1); if (tom_is_fun_sym_Variable(tom_match2_1_TomTerm) ||  false ) { { tom.engine.adt.tomterm.types.TomTerm  tom_v=tom_match2_1_TomTerm; { tom.engine.adt.tomoption.types.OptionList  tom_match2_1_TomTerm_Option=tom_get_slot_Variable_Option(tom_match2_1_TomTerm); { tom.engine.adt.tomname.types.TomName  tom_match2_1_TomTerm_AstName=tom_get_slot_Variable_AstName(tom_match2_1_TomTerm); { tom.engine.adt.tomtype.types.TomType  tom_match2_1_TomTerm_AstType=tom_get_slot_Variable_AstType(tom_match2_1_TomTerm); { tom.engine.adt.tomconstraint.types.ConstraintList  tom_match2_1_TomTerm_Constraints=tom_get_slot_Variable_Constraints(tom_match2_1_TomTerm); if ( true ) {

    		return super.analyseTerm(tom_v);
    	 } } } } } } } } } } }

      
      return super.analyseTerm(term);
  }
}
