// $ANTLR 2.7.7 (20090417): "BackQuoteLanguage.g" -> "BackQuoteParser.java"$
/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2010, INPL, INRIA
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
 *
 **/

package tom.engine.parser;
  

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

import java.util.LinkedList;

import tom.engine.TomBase;
import tom.engine.xml.Constants;

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
import tom.engine.adt.code.types.*;
import tom.engine.adt.code.types.bqterm.Composite;

import tom.engine.tools.ASTFactory;
import tom.engine.tools.SymbolTable;
import antlr.TokenStreamSelector;
import aterm.*;

public class BackQuoteParser extends antlr.LLkParser       implements BackQuoteParserTokenTypes
 {

	private final static String DEFAULT_MODULE_NAME = "default";
	private final static String TNODE_MODULE_NAME = "tnode";
    private static boolean tom_equal_term_char(char t1, char t2) {return  t1==t2 ;}private static boolean tom_is_sort_char(char t) {return  true ;} private static boolean tom_equal_term_String(String t1, String t2) {return  t1.equals(t2) ;}private static boolean tom_is_sort_String(String t) {return  t instanceof String ;} private static boolean tom_equal_term_int(int t1, int t2) {return  t1==t2 ;}private static boolean tom_is_sort_int(int t) {return  true ;} private static boolean tom_equal_term_TomSymbolTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolTable) ;}private static boolean tom_equal_term_TomSymbol(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbol(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbol) ;}private static boolean tom_equal_term_TomStructureTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomStructureTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomStructureTable) ;}private static boolean tom_equal_term_TargetLanguage(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguage(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ;}private static boolean tom_equal_term_TomEntryList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntryList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntryList) ;}private static boolean tom_equal_term_TomEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntry) ;}private static boolean tom_equal_term_TomVisitList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisitList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisitList) ;}private static boolean tom_equal_term_TomSymbolList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolList) ;}private static boolean tom_equal_term_TextPosition(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TextPosition(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TextPosition) ;}private static boolean tom_equal_term_TomVisit(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisit(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisit) ;}private static boolean tom_equal_term_KeyEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_KeyEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.KeyEntry) ;}private static boolean tom_equal_term_TypeConstraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraint(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraint) ;}private static boolean tom_equal_term_TypeConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraintList(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraintList) ;}private static boolean tom_equal_term_Info(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Info(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.Info) ;}private static boolean tom_equal_term_Expression(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Expression(Object t) {return  (t instanceof tom.engine.adt.tomexpression.types.Expression) ;}private static boolean tom_equal_term_TomList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomList(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomList) ;}private static boolean tom_equal_term_TomTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTerm(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomTerm) ;}private static boolean tom_equal_term_Declaration(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Declaration(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.Declaration) ;}private static boolean tom_equal_term_DeclarationList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_DeclarationList(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.DeclarationList) ;}private static boolean tom_equal_term_TypeOption(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeOption(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TypeOption) ;}private static boolean tom_equal_term_TomType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomType) ;}private static boolean tom_equal_term_TomTypeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTypeList(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomTypeList) ;}private static boolean tom_equal_term_TypeOptionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeOptionList(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TypeOptionList) ;}private static boolean tom_equal_term_TargetLanguageType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguageType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ;}private static boolean tom_equal_term_CodeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CodeList(Object t) {return  (t instanceof tom.engine.adt.code.types.CodeList) ;}private static boolean tom_equal_term_CompositeMember(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CompositeMember(Object t) {return  (t instanceof tom.engine.adt.code.types.CompositeMember) ;}private static boolean tom_equal_term_BQTermList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTermList(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTermList) ;}private static boolean tom_equal_term_BQTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTerm(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTerm) ;}private static boolean tom_equal_term_Code(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Code(Object t) {return  (t instanceof tom.engine.adt.code.types.Code) ;}private static boolean tom_equal_term_ConstraintInstruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstruction) ;}private static boolean tom_equal_term_Instruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Instruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.Instruction) ;}private static boolean tom_equal_term_InstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_InstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.InstructionList) ;}private static boolean tom_equal_term_ConstraintInstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstructionList) ;}private static boolean tom_equal_term_TomNumber(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumber(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumber) ;}private static boolean tom_equal_term_TomNameList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNameList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNameList) ;}private static boolean tom_equal_term_TomNumberList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumberList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumberList) ;}private static boolean tom_equal_term_TomName(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomName(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomName) ;}private static boolean tom_equal_term_Slot(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Slot(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.Slot) ;}private static boolean tom_equal_term_SlotList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_SlotList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.SlotList) ;}private static boolean tom_equal_term_PairNameDecl(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDecl(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDecl) ;}private static boolean tom_equal_term_PairNameDeclList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDeclList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDeclList) ;}private static boolean tom_equal_term_OptionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_OptionList(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.OptionList) ;}private static boolean tom_equal_term_Option(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Option(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.Option) ;}private static boolean tom_equal_term_NumericConstraintType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_NumericConstraintType(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ;}private static boolean tom_equal_term_Constraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Constraint(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.Constraint) ;}private static boolean tom_equal_term_ConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintList(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.ConstraintList) ;}private static boolean tom_equal_term_Theory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Theory(Object t) {return  (t instanceof tom.engine.adt.theory.types.Theory) ;}private static boolean tom_equal_term_ElementaryTheory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ElementaryTheory(Object t) {return  (t instanceof tom.engine.adt.theory.types.ElementaryTheory) ;}private static  tom.engine.adt.tomsignature.types.TargetLanguage  tom_make_ITL( String  t0) { return  tom.engine.adt.tomsignature.types.targetlanguage.ITL.make(t0) ;}private static  tom.engine.adt.code.types.CompositeMember  tom_make_CompositeBQTerm( tom.engine.adt.code.types.BQTerm  t0) { return  tom.engine.adt.code.types.compositemember.CompositeBQTerm.make(t0) ;}private static  tom.engine.adt.code.types.CompositeMember  tom_make_CompositeTL( tom.engine.adt.tomsignature.types.TargetLanguage  t0) { return  tom.engine.adt.code.types.compositemember.CompositeTL.make(t0) ;}private static boolean tom_is_fun_sym_BQAppl( tom.engine.adt.code.types.BQTerm  t) {return  (t instanceof tom.engine.adt.code.types.bqterm.BQAppl) ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQAppl( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.code.types.BQTermList  t2) { return  tom.engine.adt.code.types.bqterm.BQAppl.make(t0, t1, t2) ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_BQAppl_Options( tom.engine.adt.code.types.BQTerm  t) {return  t.getOptions() ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_BQAppl_AstName( tom.engine.adt.code.types.BQTerm  t) {return  t.getAstName() ;}private static  tom.engine.adt.code.types.BQTermList  tom_get_slot_BQAppl_Args( tom.engine.adt.code.types.BQTerm  t) {return  t.getArgs() ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQVariable( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2) { return  tom.engine.adt.code.types.bqterm.BQVariable.make(t0, t1, t2) ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQVariableStar( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2) { return  tom.engine.adt.code.types.bqterm.BQVariableStar.make(t0, t1, t2) ;}private static boolean tom_is_fun_sym_Name( tom.engine.adt.tomname.types.TomName  t) {return  (t instanceof tom.engine.adt.tomname.types.tomname.Name) ;}private static  tom.engine.adt.tomname.types.TomName  tom_make_Name( String  t0) { return  tom.engine.adt.tomname.types.tomname.Name.make(t0) ;}private static  String  tom_get_slot_Name_String( tom.engine.adt.tomname.types.TomName  t) {return  t.getString() ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_OriginTracking( tom.engine.adt.tomname.types.TomName  t0,  int  t1,  String  t2) { return  tom.engine.adt.tomoption.types.option.OriginTracking.make(t0, t1, t2) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_Constant() { return  tom.engine.adt.tomoption.types.option.Constant.make() ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_ModuleName( String  t0) { return  tom.engine.adt.tomoption.types.option.ModuleName.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_TypeForVariable( String  t0) { return  tom.engine.adt.tomoption.types.option.TypeForVariable.make(t0) ;}private static boolean tom_is_fun_sym_concBQTerm( tom.engine.adt.code.types.BQTermList  t) {return  ((t instanceof tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm) || (t instanceof tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm)) ;}private static  tom.engine.adt.code.types.BQTermList  tom_empty_list_concBQTerm() { return  tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ;}private static  tom.engine.adt.code.types.BQTermList  tom_cons_list_concBQTerm( tom.engine.adt.code.types.BQTerm  e,  tom.engine.adt.code.types.BQTermList  l) { return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(e,l) ;}private static  tom.engine.adt.code.types.BQTerm  tom_get_head_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.getHeadconcBQTerm() ;}private static  tom.engine.adt.code.types.BQTermList  tom_get_tail_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.getTailconcBQTerm() ;}private static boolean tom_is_empty_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.isEmptyconcBQTerm() ;}   private static   tom.engine.adt.code.types.BQTermList  tom_append_list_concBQTerm( tom.engine.adt.code.types.BQTermList l1,  tom.engine.adt.code.types.BQTermList  l2) {     if( l1.isEmptyconcBQTerm() ) {       return l2;     } else if( l2.isEmptyconcBQTerm() ) {       return l1;     } else if(  l1.getTailconcBQTerm() .isEmptyconcBQTerm() ) {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,tom_append_list_concBQTerm( l1.getTailconcBQTerm() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTermList  tom_get_slice_concBQTerm( tom.engine.adt.code.types.BQTermList  begin,  tom.engine.adt.code.types.BQTermList  end, tom.engine.adt.code.types.BQTermList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcBQTerm()  ||  (end==tom_empty_list_concBQTerm()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( begin.getHeadconcBQTerm() ,( tom.engine.adt.code.types.BQTermList )tom_get_slice_concBQTerm( begin.getTailconcBQTerm() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_Composite( tom.engine.adt.code.types.BQTerm  t) {return  ((t instanceof tom.engine.adt.code.types.bqterm.ConsComposite) || (t instanceof tom.engine.adt.code.types.bqterm.EmptyComposite)) ;}private static  tom.engine.adt.code.types.BQTerm  tom_empty_list_Composite() { return  tom.engine.adt.code.types.bqterm.EmptyComposite.make() ;}private static  tom.engine.adt.code.types.BQTerm  tom_cons_list_Composite( tom.engine.adt.code.types.CompositeMember  e,  tom.engine.adt.code.types.BQTerm  l) { return  tom.engine.adt.code.types.bqterm.ConsComposite.make(e,l) ;}private static  tom.engine.adt.code.types.CompositeMember  tom_get_head_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.getHeadComposite() ;}private static  tom.engine.adt.code.types.BQTerm  tom_get_tail_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.getTailComposite() ;}private static boolean tom_is_empty_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.isEmptyComposite() ;}   private static   tom.engine.adt.code.types.BQTerm  tom_append_list_Composite( tom.engine.adt.code.types.BQTerm l1,  tom.engine.adt.code.types.BQTerm  l2) {     if( l1.isEmptyComposite() ) {       return l2;     } else if( l2.isEmptyComposite() ) {       return l1;     } else if(  l1.getTailComposite() .isEmptyComposite() ) {       return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,tom_append_list_Composite( l1.getTailComposite() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTerm  tom_get_slice_Composite( tom.engine.adt.code.types.BQTerm  begin,  tom.engine.adt.code.types.BQTerm  end, tom.engine.adt.code.types.BQTerm  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyComposite()  ||  (end==tom_empty_list_Composite()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqterm.ConsComposite.make( begin.getHeadComposite() ,( tom.engine.adt.code.types.BQTerm )tom_get_slice_Composite( begin.getTailComposite() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concOption( tom.engine.adt.tomoption.types.OptionList  t) {return  ((t instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || (t instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_empty_list_concOption() { return  tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_cons_list_concOption( tom.engine.adt.tomoption.types.Option  e,  tom.engine.adt.tomoption.types.OptionList  l) { return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(e,l) ;}private static  tom.engine.adt.tomoption.types.Option  tom_get_head_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.getHeadconcOption() ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_tail_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.getTailconcOption() ;}private static boolean tom_is_empty_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.isEmptyconcOption() ;}   private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {     if( l1.isEmptyconcOption() ) {       return l2;     } else if( l2.isEmptyconcOption() ) {       return l1;     } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;     } else {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end==tom_empty_list_concOption()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;   }    
    
    // the lexer for backquote language
    BackQuoteLexer bqlexer = null;

    // the parser for tom language
    TomParser tomparser = null;

    // the current file's name
    String currentFile(){
      return tomparser.currentFile();
    }

    //constructor
    public BackQuoteParser(ParserSharedInputState state, TomParser tomparser){
      this(state);
      this.tomparser = tomparser;
      bqlexer = (BackQuoteLexer) selector().getStream("bqlexer");
    }

    // add token t to the buffer containing the target code
    private void addTargetCode(Token t){
      tomparser.addTargetCode(t);
    }

    // returns the selector
    private TokenStreamSelector selector(){
      return tomparser.selector();
    }
    
   private BQTerm buildBqAppl(Token id, LinkedList<BQTerm> blockList, BQTerm term, boolean composite) {
     OptionList option = tom_cons_list_concOption(tom_make_OriginTracking(tom_make_Name(id.getText()),id.getLine(),currentFile()),tom_cons_list_concOption(tom_make_ModuleName(DEFAULT_MODULE_NAME),tom_empty_list_concOption()));
     Composite target = (Composite) ((term==null)?
       tom_empty_list_Composite():
       tom_cons_list_Composite(tom_make_CompositeTL(tom_make_ITL(".")),tom_cons_list_Composite(tom_make_CompositeBQTerm(term),tom_empty_list_Composite())));

     if(composite) {
			 BQTermList list = ASTFactory.makeBQTermList(blockList);
			 return tom_cons_list_Composite(tom_make_CompositeBQTerm(tom_make_BQAppl(option,tom_make_Name(id.getText()),list)),tom_append_list_Composite(target,tom_empty_list_Composite()));
     } else {
			 return tom_cons_list_Composite(tom_make_CompositeBQTerm(tom_make_BQVariable(option,tom_make_Name(id.getText()),SymbolTable.TYPE_UNKNOWN)),tom_append_list_Composite(target,tom_empty_list_Composite()));
		 }

   }
 
   /*
    * add a term to a list of term
    * when newComposite is true, this means that a ',' has been read before the term
    */
    private void addTerm(LinkedList<BQTerm> list, BQTerm term, boolean newComposite) {
      // if the list is empty put an empty composite in it to simplify the code
      if(list.isEmpty()) {
        list.add(tom_empty_list_Composite());
      }
      BQTerm lastElement = (BQTerm) list.getLast();
      /*
       * when newComposite is true, we add the term, eventually wrapped by a Composite 
       * otherwise, the term is inserted (eventually unwrapped) into the last Composite of the list
       */
      if(newComposite) {
        {{if (tom_is_sort_BQTerm(lastElement)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )lastElement))) {if (tom_is_sort_BQTerm(term)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )term))) {
 
            list.add((( tom.engine.adt.code.types.BQTerm )term)); 
            return; 
          }}}}}{if (tom_is_sort_BQTerm(lastElement)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )lastElement))) {if (tom_is_sort_BQTerm(term)) {
 
            list.add(tom_cons_list_Composite(tom_make_CompositeBQTerm((( tom.engine.adt.code.types.BQTerm )term)),tom_empty_list_Composite())); 
            return; 
          }}}}}

      } else {
        {{if (tom_is_sort_BQTerm(lastElement)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )lastElement))) {if (tom_is_sort_BQTerm(term)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )term))) {
 
            list.set(list.size()-1,tom_append_list_Composite((( tom.engine.adt.code.types.BQTerm )lastElement),tom_append_list_Composite((( tom.engine.adt.code.types.BQTerm )term),tom_empty_list_Composite()))); 
            return;
          }}}}}{if (tom_is_sort_BQTerm(lastElement)) {if (tom_is_fun_sym_Composite((( tom.engine.adt.code.types.BQTerm )lastElement))) {if (tom_is_sort_BQTerm(term)) {
 
            list.set(list.size()-1,tom_append_list_Composite((( tom.engine.adt.code.types.BQTerm )lastElement),tom_cons_list_Composite(tom_make_CompositeBQTerm((( tom.engine.adt.code.types.BQTerm )term)),tom_empty_list_Composite()))); 
            return;
          }}}}}

      }
    }

    // sorts attributes of xml term with lexicographical order
    private BQTermList sortAttributeList(BQTermList list){
      {{if (tom_is_sort_BQTermList(list)) {if (tom_is_fun_sym_concBQTerm((( tom.engine.adt.code.types.BQTermList )list))) {if (tom_is_empty_concBQTerm_BQTermList((( tom.engine.adt.code.types.BQTermList )list))) {
 return list; }}}}{if (tom_is_sort_BQTermList(list)) {if (tom_is_fun_sym_concBQTerm((( tom.engine.adt.code.types.BQTermList )list))) { tom.engine.adt.code.types.BQTermList  tomMatch3__end__6=(( tom.engine.adt.code.types.BQTermList )list);do {{if (!(tom_is_empty_concBQTerm_BQTermList(tomMatch3__end__6))) { tom.engine.adt.code.types.BQTerm  tom_e1=tom_get_head_concBQTerm_BQTermList(tomMatch3__end__6); tom.engine.adt.code.types.BQTermList  tomMatch3_7=tom_get_tail_concBQTerm_BQTermList(tomMatch3__end__6); tom.engine.adt.code.types.BQTermList  tomMatch3__end__10=tomMatch3_7;do {{if (!(tom_is_empty_concBQTerm_BQTermList(tomMatch3__end__10))) { tom.engine.adt.code.types.BQTerm  tom_e2=tom_get_head_concBQTerm_BQTermList(tomMatch3__end__10);{{if (tom_is_sort_BQTerm(tom_e1)) {if (tom_is_fun_sym_BQAppl((( tom.engine.adt.code.types.BQTerm )tom_e1))) { tom.engine.adt.code.types.BQTermList  tomMatch4_2=tom_get_slot_BQAppl_Args((( tom.engine.adt.code.types.BQTerm )tom_e1));if (tom_is_fun_sym_concBQTerm(tomMatch4_2)) {if (!(tom_is_empty_concBQTerm_BQTermList(tomMatch4_2))) { tom.engine.adt.code.types.BQTerm  tomMatch4_13=tom_get_head_concBQTerm_BQTermList(tomMatch4_2);if (tom_is_fun_sym_BQAppl(tomMatch4_13)) { tom.engine.adt.tomname.types.TomName  tomMatch4_12=tom_get_slot_BQAppl_AstName(tomMatch4_13);if (tom_is_fun_sym_Name(tomMatch4_12)) {if (tom_is_sort_BQTerm(tom_e2)) {if (tom_is_fun_sym_BQAppl((( tom.engine.adt.code.types.BQTerm )tom_e2))) { tom.engine.adt.code.types.BQTermList  tomMatch4_4=tom_get_slot_BQAppl_Args((( tom.engine.adt.code.types.BQTerm )tom_e2));if (tom_is_fun_sym_concBQTerm(tomMatch4_4)) {if (!(tom_is_empty_concBQTerm_BQTermList(tomMatch4_4))) { tom.engine.adt.code.types.BQTerm  tomMatch4_17=tom_get_head_concBQTerm_BQTermList(tomMatch4_4);if (tom_is_fun_sym_BQAppl(tomMatch4_17)) { tom.engine.adt.tomname.types.TomName  tomMatch4_16=tom_get_slot_BQAppl_AstName(tomMatch4_17);if (tom_is_fun_sym_Name(tomMatch4_16)) {




              if(tom_get_slot_Name_String(tomMatch4_12).compareTo(tom_get_slot_Name_String(tomMatch4_16)) > 0) {
                return sortAttributeList(tom_append_list_concBQTerm(tom_get_slice_concBQTerm((( tom.engine.adt.code.types.BQTermList )list),tomMatch3__end__6,tom_empty_list_concBQTerm()),tom_cons_list_concBQTerm(tom_e2,tom_append_list_concBQTerm(tom_get_slice_concBQTerm(tomMatch3_7,tomMatch3__end__10,tom_empty_list_concBQTerm()),tom_cons_list_concBQTerm(tom_e1,tom_append_list_concBQTerm(tom_get_tail_concBQTerm_BQTermList(tomMatch3__end__10),tom_empty_list_concBQTerm()))))));
              }
            }}}}}}}}}}}}}}

        }if (tom_is_empty_concBQTerm_BQTermList(tomMatch3__end__10)) {tomMatch3__end__10=tomMatch3_7;} else {tomMatch3__end__10=tom_get_tail_concBQTerm_BQTermList(tomMatch3__end__10);}}} while(!(tom_equal_term_BQTermList(tomMatch3__end__10, tomMatch3_7)));}if (tom_is_empty_concBQTerm_BQTermList(tomMatch3__end__6)) {tomMatch3__end__6=(( tom.engine.adt.code.types.BQTermList )list);} else {tomMatch3__end__6=tom_get_tail_concBQTerm_BQTermList(tomMatch3__end__6);}}} while(!(tom_equal_term_BQTermList(tomMatch3__end__6, (( tom.engine.adt.code.types.BQTermList )list))));}}}}

      return list;
    }
    
    // built a sorted BQTermList from a LinkedList
    private BQTermList buildAttributeList(LinkedList<BQTerm> list) {
      return sortAttributeList(ASTFactory.makeBQTermList(list));
    }
    
    // add double quotes around a string
    private String encodeName(String name) {
      return "\"" + name + "\"";
    }


protected BackQuoteParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public BackQuoteParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected BackQuoteParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public BackQuoteParser(TokenStream lexer) {
  this(lexer,1);
}

public BackQuoteParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final BQTerm  beginBackquote() throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		
		result = null; 
		BQTermList context = tom_empty_list_concBQTerm();
		
		
		ws();
		{
		switch ( LA(1)) {
		case BQ_BACKQUOTE:
		{
			match(BQ_BACKQUOTE);
			break;
		}
		case BQ_ID:
		case BQ_LPAREN:
		case XML:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		result=mainBqTerm(context);
		}
		if ( inputState.guessing==0 ) {
			selector().pop();
		}
		return result;
	}
	
	public final void ws() throws RecognitionException, TokenStreamException {
		
		
		{
		_loop33:
		do {
			if ((LA(1)==BQ_WS)) {
				match(BQ_WS);
			}
			else {
				break _loop33;
			}
			
		} while (true);
		}
	}
	
	public final BQTerm  mainBqTerm(
		BQTermList context
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  id = null;
		Token  type = null;
		
		result = null;
		BQTerm term = null;
		BQTermList list = tom_empty_list_concBQTerm();
		
		Token t = null;
		LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
		
		
		{
		switch ( LA(1)) {
		case BQ_LPAREN:
		case XML:
		{
			result=basicTerm(list);
			break;
		}
		case BQ_ID:
		{
			id = LT(1);
			match(BQ_ID);
			{
			if (((LA(1)==BQ_STAR))&&(LA(1) == BQ_STAR)) {
				match(BQ_STAR);
				if ( inputState.guessing==0 ) {
					
					String name = id.getText();
					Option ot = tom_make_OriginTracking(tom_make_Name(name),id.getLine(),currentFile());
					result = tom_make_BQVariableStar(tom_cons_list_concOption(ot,tom_empty_list_concOption()),tom_make_Name(name),SymbolTable.TYPE_UNKNOWN);  
					
				}
			}
			else if (((LA(1)==EOF))&&(LA(1) == BQ_RBRACE)) {
				if ( inputState.guessing==0 ) {
					
					// generate an ERROR when a '}' is encoutered
					//System.out.println("ERROR");
					
				}
			}
			else if ((_tokenSet_0.member(LA(1)))) {
				ws();
				{
				if (((LA(1)==BQ_LPAREN))&&(LA(1) == BQ_LPAREN)) {
					match(BQ_LPAREN);
					ws();
					{
					switch ( LA(1)) {
					case BQ_ID:
					case BQ_STAR:
					case BQ_LPAREN:
					case BQ_DOT:
					case XML:
					case BQ_STRING:
					case BQ_WS:
					case BQ_INTEGER:
					case BQ_MINUS:
					case DOUBLE_QUOTE:
					case XML_START:
					case XML_EQUAL:
					case XML_CLOSE:
					case ANY:
					case XML_TEXT:
					{
						termList(blockList,list);
						break;
					}
					case BQ_RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(BQ_RPAREN);
					if ( inputState.guessing==0 ) {
						
						result = buildBqAppl(id,blockList,term,true);
						
					}
				}
				else if (((LA(1)==BQ_LBRACE))&&(LA(1) == BQ_LBRACE)) {
					match(BQ_LBRACE);
					type = LT(1);
					match(BQ_ID);
					match(BQ_RBRACE);
					if ( inputState.guessing==0 ) {
						
						String name = id.getText();
						OptionList ol = tom_cons_list_concOption(tom_make_TypeForVariable(type.getText()),tom_cons_list_concOption(tom_make_OriginTracking(tom_make_Name(name),id.getLine(),currentFile()),tom_cons_list_concOption(tom_make_ModuleName(DEFAULT_MODULE_NAME),tom_empty_list_concOption())));
						result = tom_make_BQVariable(ol,tom_make_Name(name),SymbolTable.TYPE_UNKNOWN);
						
					}
				}
				else if ((_tokenSet_1.member(LA(1)))) {
					t=targetCode();
					if ( inputState.guessing==0 ) {
						
						//System.out.println("targetCode = " + t);
						addTargetCode(t);
						String name = id.getText();
						OptionList ol = tom_cons_list_concOption(tom_make_OriginTracking(tom_make_Name(name),id.getLine(),currentFile()),tom_cons_list_concOption(tom_make_ModuleName(DEFAULT_MODULE_NAME),tom_empty_list_concOption()));
						//result = `BQAppl(ol,Name(name),concBQTerm());
						result = tom_make_BQVariable(ol,tom_make_Name(name),SymbolTable.TYPE_UNKNOWN);
						
					}
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return result;
	}
	
	public final BQTerm  basicTerm(
		BQTermList context
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		
		result = null;
		BQTerm term = null;
		BQTermList localContext = tom_empty_list_concBQTerm();
		
		LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
		
		
		{
		switch ( LA(1)) {
		case XML:
		{
			match(XML);
			ws();
			match(BQ_LPAREN);
			ws();
			{
			_loop23:
			do {
				boolean synPredMatched22 = false;
				if (((_tokenSet_2.member(LA(1))))) {
					int _m22 = mark();
					synPredMatched22 = true;
					inputState.guessing++;
					try {
						{
						bqTerm(null);
						match(BQ_COMMA);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched22 = false;
					}
					rewind(_m22);
inputState.guessing--;
				}
				if ( synPredMatched22 ) {
					term=bqTerm(context);
					match(BQ_COMMA);
					ws();
					if ( inputState.guessing==0 ) {
						blockList.add(term);
					}
				}
				else {
					break _loop23;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				localContext = ASTFactory.makeBQTermList(blockList);
			}
			result=bqTerm(localContext);
			match(BQ_RPAREN);
			break;
		}
		case BQ_LPAREN:
		{
			match(BQ_LPAREN);
			ws();
			{
			switch ( LA(1)) {
			case BQ_ID:
			case BQ_STAR:
			case BQ_LPAREN:
			case BQ_DOT:
			case XML:
			case BQ_STRING:
			case BQ_WS:
			case BQ_INTEGER:
			case BQ_MINUS:
			case DOUBLE_QUOTE:
			case XML_START:
			case XML_EQUAL:
			case XML_CLOSE:
			case ANY:
			case XML_TEXT:
			{
				termList(blockList,context);
				break;
			}
			case BQ_RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(BQ_RPAREN);
			if ( inputState.guessing==0 ) {
				
				Composite compositeList = ASTFactory.makeComposite(blockList);
				result = tom_cons_list_Composite(tom_make_CompositeTL(tom_make_ITL("(")),tom_append_list_Composite(compositeList,tom_cons_list_Composite(tom_make_CompositeTL(tom_make_ITL(")"))
				,tom_empty_list_Composite())))
				
				
				
				;
				
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return result;
	}
	
	public final void termList(
		LinkedList<BQTerm> list,BQTermList context
	) throws RecognitionException, TokenStreamException {
		
		Token  c = null;
		
		BQTerm term = null;
		
		
		term=bqTerm(context);
		if ( inputState.guessing==0 ) {
			addTerm(list,term,false);
		}
		{
		_loop28:
		do {
			if ((_tokenSet_3.member(LA(1)))) {
				{
				switch ( LA(1)) {
				case BQ_COMMA:
				{
					c = LT(1);
					match(BQ_COMMA);
					ws();
					break;
				}
				case BQ_ID:
				case BQ_STAR:
				case BQ_LPAREN:
				case BQ_DOT:
				case XML:
				case BQ_STRING:
				case BQ_WS:
				case BQ_INTEGER:
				case BQ_MINUS:
				case DOUBLE_QUOTE:
				case XML_START:
				case XML_EQUAL:
				case XML_CLOSE:
				case ANY:
				case XML_TEXT:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				term=bqTerm(context);
				if ( inputState.guessing==0 ) {
					addTerm(list,term, (c!=null)); c = null;
				}
			}
			else {
				break _loop28;
			}
			
		} while (true);
		}
	}
	
	public final Token  targetCode() throws RecognitionException, TokenStreamException {
		Token result;
		
		Token  c = null;
		Token  i = null;
		Token  r = null;
		Token  t = null;
		Token  xcs = null;
		Token  xt = null;
		Token  xc = null;
		Token  xp = null;
		
		result = null;
		
		
		switch ( LA(1)) {
		case BQ_STAR:
		case BQ_DOT:
		case BQ_STRING:
		case BQ_WS:
		case BQ_INTEGER:
		case BQ_MINUS:
		case DOUBLE_QUOTE:
		case XML_START:
		case XML_EQUAL:
		case XML_CLOSE:
		case ANY:
		{
			result=target();
			break;
		}
		case BQ_COMMA:
		{
			c = LT(1);
			match(BQ_COMMA);
			if ( inputState.guessing==0 ) {
				result = c;
			}
			break;
		}
		case BQ_ID:
		{
			i = LT(1);
			match(BQ_ID);
			if ( inputState.guessing==0 ) {
				result = i;
			}
			break;
		}
		case BQ_RPAREN:
		{
			r = LT(1);
			match(BQ_RPAREN);
			if ( inputState.guessing==0 ) {
				result = r;
			}
			break;
		}
		case XML_START_ENDING:
		{
			t = LT(1);
			match(XML_START_ENDING);
			if ( inputState.guessing==0 ) {
				result = t;
			}
			break;
		}
		case XML_CLOSE_SINGLETON:
		{
			xcs = LT(1);
			match(XML_CLOSE_SINGLETON);
			if ( inputState.guessing==0 ) {
				result = xcs;
			}
			break;
		}
		case XML_TEXT:
		{
			xt = LT(1);
			match(XML_TEXT);
			if ( inputState.guessing==0 ) {
				result = xt;
			}
			break;
		}
		case XML_COMMENT:
		{
			xc = LT(1);
			match(XML_COMMENT);
			if ( inputState.guessing==0 ) {
				result = xc;
			}
			break;
		}
		case XML_PROC:
		{
			xp = LT(1);
			match(XML_PROC);
			if ( inputState.guessing==0 ) {
				result = xp;
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return result;
	}
	
	public final BQTerm  bqTerm(
		BQTermList context
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  id = null;
		
		result = null;
		BQTerm term = null;
		BQTermList xmlTermList = tom_empty_list_concBQTerm();
		
		Token t = null;
		LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
		boolean arguments = false;
		
		
		switch ( LA(1)) {
		case BQ_LPAREN:
		case XML:
		{
			result=basicTerm(context);
			break;
		}
		case BQ_ID:
		{
			id = LT(1);
			match(BQ_ID);
			{
			if (((LA(1)==BQ_STAR))&&(LA(1) == BQ_STAR)) {
				match(BQ_STAR);
				if ( inputState.guessing==0 ) {
					
					String name = id.getText();
					Option ot = tom_make_OriginTracking(tom_make_Name(name),id.getLine(),currentFile());
					result = tom_make_BQVariableStar(tom_cons_list_concOption(ot,tom_empty_list_concOption()),tom_make_Name(name),SymbolTable.TYPE_UNKNOWN);      
					
				}
			}
			else if ((_tokenSet_4.member(LA(1)))) {
				ws();
				{
				if (((LA(1)==BQ_LPAREN))&&(LA(1) == BQ_LPAREN)) {
					match(BQ_LPAREN);
					if ( inputState.guessing==0 ) {
						arguments = true;
					}
					ws();
					{
					switch ( LA(1)) {
					case BQ_ID:
					case BQ_STAR:
					case BQ_LPAREN:
					case BQ_DOT:
					case XML:
					case BQ_STRING:
					case BQ_WS:
					case BQ_INTEGER:
					case BQ_MINUS:
					case DOUBLE_QUOTE:
					case XML_START:
					case XML_EQUAL:
					case XML_CLOSE:
					case ANY:
					case XML_TEXT:
					{
						termList(blockList,context);
						break;
					}
					case BQ_RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(BQ_RPAREN);
				}
				else if ((_tokenSet_4.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				{
				boolean synPredMatched15 = false;
				if (((LA(1)==BQ_DOT))) {
					int _m15 = mark();
					synPredMatched15 = true;
					inputState.guessing++;
					try {
						{
						match(BQ_DOT);
						term=bqTerm(null);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched15 = false;
					}
					rewind(_m15);
inputState.guessing--;
				}
				if ( synPredMatched15 ) {
					match(BQ_DOT);
					term=bqTerm(context);
				}
				else if ((_tokenSet_4.member(LA(1)))) {
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				
				}
				if ( inputState.guessing==0 ) {
					
					result = buildBqAppl(id,blockList,term,arguments);
					
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			break;
		}
		default:
			boolean synPredMatched17 = false;
			if (((LA(1)==XML_START||LA(1)==XML_TEXT))) {
				int _m17 = mark();
				synPredMatched17 = true;
				inputState.guessing++;
				try {
					{
					xmlTerm(null);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched17 = false;
				}
				rewind(_m17);
inputState.guessing--;
			}
			if ( synPredMatched17 ) {
				result=xmlTerm(context);
			}
			else if ((_tokenSet_5.member(LA(1)))) {
				t=target();
				if ( inputState.guessing==0 ) {
					
					//System.out.println("target = " + t);
					result = tom_cons_list_Composite(tom_make_CompositeTL(tom_make_ITL(t.getText())),tom_empty_list_Composite());
					
				}
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return result;
	}
	
	public final BQTerm  xmlTerm(
		BQTermList context
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  id = null;
		
		result = null;
		BQTermList attributeBQTermList = tom_empty_list_concBQTerm();
		BQTermList childrenBQTermList = tom_empty_list_concBQTerm();
		BQTerm term = null;
		
		LinkedList<BQTerm> attributes = new LinkedList<BQTerm>();
		LinkedList<BQTerm> children = new LinkedList<BQTerm>();
		
		
		{
		switch ( LA(1)) {
		case XML_START:
		{
			match(XML_START);
			ws();
			id = LT(1);
			match(BQ_ID);
			ws();
			xmlAttributeList(attributes,context);
			if ( inputState.guessing==0 ) {
				
				attributeBQTermList = buildAttributeList(attributes);
				
			}
			{
			switch ( LA(1)) {
			case XML_CLOSE_SINGLETON:
			{
				match(XML_CLOSE_SINGLETON);
				ws();
				break;
			}
			case XML_CLOSE:
			{
				match(XML_CLOSE);
				ws();
				xmlChildren(children,context);
				if ( inputState.guessing==0 ) {
					
					childrenBQTermList = ASTFactory.makeBQTermList(children);
					
				}
				match(XML_START_ENDING);
				ws();
				match(BQ_ID);
				ws();
				match(XML_CLOSE);
				ws();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				BQTermList args = tom_cons_list_concBQTerm(tom_make_BQAppl(tom_cons_list_concOption(tom_make_Constant(),tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption())),tom_make_Name(encodeName(id.getText())),tom_empty_list_concBQTerm()
				),tom_cons_list_concBQTerm(tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.CONC_TNODE),attributeBQTermList),tom_cons_list_concBQTerm(tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.CONC_TNODE),childrenBQTermList)
				,tom_empty_list_concBQTerm())))
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				;
				
				if(context == null){
				result = tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.ELEMENT_NODE),args)
				
				
				
				;
				} else {
															result = tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.ELEMENT_NODE),tom_append_list_concBQTerm(context,tom_append_list_concBQTerm(args,tom_empty_list_concBQTerm()))
				)
				
				
				
				
				
				;
				}
				
				
			}
			break;
		}
		case XML_TEXT:
		{
			match(XML_TEXT);
			match(BQ_LPAREN);
			term=bqTerm(context);
			match(BQ_RPAREN);
			if ( inputState.guessing==0 ) {
				
				result = tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.TEXT_NODE),tom_append_list_concBQTerm(context,tom_cons_list_concBQTerm(term,tom_empty_list_concBQTerm()))
				)
				
				
				
				
				
				
				;
				
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return result;
	}
	
	public final Token  target() throws RecognitionException, TokenStreamException {
		Token result;
		
		Token  in = null;
		Token  str = null;
		Token  m = null;
		Token  s = null;
		Token  w = null;
		Token  d = null;
		Token  dq = null;
		Token  xs = null;
		Token  xe = null;
		Token  xc = null;
		Token  a = null;
		
		result = null;
		
		
		switch ( LA(1)) {
		case BQ_INTEGER:
		{
			in = LT(1);
			match(BQ_INTEGER);
			if ( inputState.guessing==0 ) {
				result = in;
			}
			break;
		}
		case BQ_STRING:
		{
			str = LT(1);
			match(BQ_STRING);
			if ( inputState.guessing==0 ) {
				result = str;
			}
			break;
		}
		case BQ_MINUS:
		{
			m = LT(1);
			match(BQ_MINUS);
			if ( inputState.guessing==0 ) {
				result = m;
			}
			break;
		}
		case BQ_STAR:
		{
			s = LT(1);
			match(BQ_STAR);
			if ( inputState.guessing==0 ) {
				result = s;
			}
			break;
		}
		case BQ_WS:
		{
			w = LT(1);
			match(BQ_WS);
			if ( inputState.guessing==0 ) {
				result = w;
			}
			break;
		}
		case BQ_DOT:
		{
			d = LT(1);
			match(BQ_DOT);
			if ( inputState.guessing==0 ) {
				result = d;
			}
			break;
		}
		case DOUBLE_QUOTE:
		{
			dq = LT(1);
			match(DOUBLE_QUOTE);
			if ( inputState.guessing==0 ) {
				result = dq;
			}
			break;
		}
		case XML_START:
		{
			xs = LT(1);
			match(XML_START);
			if ( inputState.guessing==0 ) {
				result = xs;
			}
			break;
		}
		case XML_EQUAL:
		{
			xe = LT(1);
			match(XML_EQUAL);
			if ( inputState.guessing==0 ) {
				result = xe;
			}
			break;
		}
		case XML_CLOSE:
		{
			xc = LT(1);
			match(XML_CLOSE);
			if ( inputState.guessing==0 ) {
				result = xc;
			}
			break;
		}
		case ANY:
		{
			a = LT(1);
			match(ANY);
			if ( inputState.guessing==0 ) {
				result = a;
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return result;
	}
	
	public final BQTerm  xmlAttributeStringOrBQVariable() throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  id = null;
		Token  string = null;
		result = null;
		
		{
		switch ( LA(1)) {
		case BQ_ID:
		{
			id = LT(1);
			match(BQ_ID);
			if ( inputState.guessing==0 ) {
				
				String name = id.getText();
				OptionList ol = tom_cons_list_concOption(tom_make_OriginTracking(tom_make_Name(name),id.getLine(),currentFile()),tom_cons_list_concOption(tom_make_ModuleName(DEFAULT_MODULE_NAME),tom_empty_list_concOption()));
				result = tom_make_BQVariable(ol,tom_make_Name(name),SymbolTable.TYPE_UNKNOWN);
						   //result = `Composite(CompositeTL(ITL(id.getText()))); 
						
			}
			break;
		}
		case BQ_STRING:
		{
			string = LT(1);
			match(BQ_STRING);
			if ( inputState.guessing==0 ) {
				
						   result = tom_cons_list_Composite(tom_make_CompositeTL(tom_make_ITL(string.getText())),tom_empty_list_Composite());
						
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return result;
	}
	
	public final BQTerm  xmlAttribute(
		BQTermList context
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  id = null;
		
		result = null;
		BQTerm value = null;
		
		
		{
		id = LT(1);
		match(BQ_ID);
		{
		switch ( LA(1)) {
		case BQ_WS:
		case XML_EQUAL:
		{
			ws();
			match(XML_EQUAL);
			ws();
			value=xmlAttributeStringOrBQVariable();
			if ( inputState.guessing==0 ) {
				
				BQTermList args = tom_cons_list_concBQTerm(tom_make_BQAppl(tom_cons_list_concOption(tom_make_Constant(),tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption())),tom_make_Name(encodeName(id.getText())),tom_empty_list_concBQTerm()
				),tom_cons_list_concBQTerm(tom_make_BQAppl(tom_cons_list_concOption(tom_make_Constant(),tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption())),tom_make_Name("\"true\""),tom_empty_list_concBQTerm()
				),tom_cons_list_concBQTerm(value,tom_empty_list_concBQTerm())))
				
				
				
				
				
				
				
				
				
				
				
				;
						    if(context != null) {
						    args = tom_append_list_concBQTerm(context,tom_append_list_concBQTerm(args,tom_empty_list_concBQTerm()));
						    }
						    result = tom_make_BQAppl(tom_cons_list_concOption(tom_make_ModuleName(TNODE_MODULE_NAME),tom_empty_list_concOption()),tom_make_Name(Constants.ATTRIBUTE_NODE),args)
				
				
				;
				
			}
			break;
		}
		case BQ_STAR:
		{
			match(BQ_STAR);
			if ( inputState.guessing==0 ) {
				
						result = tom_make_BQVariableStar(tom_empty_list_concOption(),tom_make_Name(id.getText()),SymbolTable.TYPE_UNKNOWN)
				
				
				;
				
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		}
		return result;
	}
	
	public final void xmlAttributeList(
		LinkedList<BQTerm> attributeList, BQTermList context
	) throws RecognitionException, TokenStreamException {
		
		
		BQTerm term = null;
		
		
		{
		_loop41:
		do {
			if ((LA(1)==BQ_ID)) {
				term=xmlAttribute(context);
				ws();
				if ( inputState.guessing==0 ) {
					
					attributeList.add(term);
					
				}
			}
			else {
				break _loop41;
			}
			
		} while (true);
		}
	}
	
	public final void xmlChildren(
		LinkedList<BQTerm> children, BQTermList context
	) throws RecognitionException, TokenStreamException {
		
		
		BQTerm term = null;
		
		
		{
		_loop44:
		do {
			if (((_tokenSet_2.member(LA(1))))&&(LA(1) != XML_START_ENDING && LA(1) != XML_CLOSE)) {
				term=bqTerm(context);
				if ( inputState.guessing==0 ) {
					children.add(term);
				}
			}
			else {
				break _loop44;
			}
			
		} while (true);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"BQ_BACKQUOTE",
		"BQ_ID",
		"BQ_STAR",
		"BQ_LPAREN",
		"BQ_RPAREN",
		"BQ_LBRACE",
		"BQ_RBRACE",
		"BQ_DOT",
		"\"xml\"",
		"BQ_COMMA",
		"BQ_STRING",
		"BQ_WS",
		"BQ_INTEGER",
		"BQ_MINUS",
		"DOUBLE_QUOTE",
		"XML_START",
		"XML_EQUAL",
		"XML_CLOSE",
		"ANY",
		"XML_START_ENDING",
		"XML_CLOSE_SINGLETON",
		"XML_TEXT",
		"XML_COMMENT",
		"XML_PROC",
		"XML_SKIP",
		"BQ_SIMPLE_ID",
		"BQ_MINUS_ID",
		"BQ_MINUS_ID_PART",
		"BQ_DIGIT",
		"BQ_UNDERSCORE",
		"BQ_ESC",
		"BQ_HEX_DIGIT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 268430304L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 268429664L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 41933024L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 41941216L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 50330080L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 8374336L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	
	}
