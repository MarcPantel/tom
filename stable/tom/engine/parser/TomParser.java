// $ANTLR 2.7.7 (20060906): "TomLanguage.g" -> "TomParser.java"$

/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2011, INPL, INRIA
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

import java.io.StringReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.exception.TomException;

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

import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;
import tom.engine.xml.Constants;
import tom.platform.OptionManager;
import tom.platform.PlatformLogRecord;
import aterm.*;
import antlr.TokenStreamSelector;

public class TomParser extends antlr.LLkParser       implements TomParserTokenTypes
 {

    //--------------------------
    private static boolean tom_equal_term_char(char t1, char t2) {return  t1==t2 ;}private static boolean tom_is_sort_char(char t) {return  true ;} private static boolean tom_equal_term_String(String t1, String t2) {return  t1.equals(t2) ;}private static boolean tom_is_sort_String(String t) {return  t instanceof String ;} private static boolean tom_equal_term_int(int t1, int t2) {return  t1==t2 ;}private static boolean tom_is_sort_int(int t) {return  true ;} private static boolean tom_equal_term_TomSymbolTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolTable) ;}private static boolean tom_equal_term_TomSymbol(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbol(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbol) ;}private static boolean tom_equal_term_TomStructureTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomStructureTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomStructureTable) ;}private static boolean tom_equal_term_TomEntryList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntryList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntryList) ;}private static boolean tom_equal_term_TomEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntry) ;}private static boolean tom_equal_term_TomVisitList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisitList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisitList) ;}private static boolean tom_equal_term_TomSymbolList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolList) ;}private static boolean tom_equal_term_TextPosition(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TextPosition(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TextPosition) ;}private static boolean tom_equal_term_TomVisit(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisit(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisit) ;}private static boolean tom_equal_term_KeyEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_KeyEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.KeyEntry) ;}private static boolean tom_equal_term_TypeConstraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraint(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraint) ;}private static boolean tom_equal_term_TypeConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraintList(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraintList) ;}private static boolean tom_equal_term_Info(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Info(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.Info) ;}private static boolean tom_equal_term_Expression(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Expression(Object t) {return  (t instanceof tom.engine.adt.tomexpression.types.Expression) ;}private static boolean tom_equal_term_TomList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomList(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomList) ;}private static boolean tom_equal_term_TomTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTerm(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomTerm) ;}private static boolean tom_equal_term_Declaration(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Declaration(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.Declaration) ;}private static boolean tom_equal_term_DeclarationList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_DeclarationList(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.DeclarationList) ;}private static boolean tom_equal_term_TypeOption(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeOption(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TypeOption) ;}private static boolean tom_equal_term_TomType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomType) ;}private static boolean tom_equal_term_TomTypeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTypeList(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomTypeList) ;}private static boolean tom_equal_term_TypeOptionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeOptionList(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TypeOptionList) ;}private static boolean tom_equal_term_TargetLanguageType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguageType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ;}private static boolean tom_equal_term_CodeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CodeList(Object t) {return  (t instanceof tom.engine.adt.code.types.CodeList) ;}private static boolean tom_equal_term_CompositeMember(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CompositeMember(Object t) {return  (t instanceof tom.engine.adt.code.types.CompositeMember) ;}private static boolean tom_equal_term_BQTermList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTermList(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTermList) ;}private static boolean tom_equal_term_BQTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTerm(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTerm) ;}private static boolean tom_equal_term_TargetLanguage(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguage(Object t) {return  (t instanceof tom.engine.adt.code.types.TargetLanguage) ;}private static boolean tom_equal_term_Code(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Code(Object t) {return  (t instanceof tom.engine.adt.code.types.Code) ;}private static boolean tom_equal_term_ConstraintInstruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstruction) ;}private static boolean tom_equal_term_Instruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Instruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.Instruction) ;}private static boolean tom_equal_term_InstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_InstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.InstructionList) ;}private static boolean tom_equal_term_ConstraintInstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstructionList) ;}private static boolean tom_equal_term_TomNumber(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumber(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumber) ;}private static boolean tom_equal_term_TomNameList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNameList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNameList) ;}private static boolean tom_equal_term_TomNumberList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumberList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumberList) ;}private static boolean tom_equal_term_TomName(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomName(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomName) ;}private static boolean tom_equal_term_Slot(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Slot(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.Slot) ;}private static boolean tom_equal_term_BQSlotList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQSlotList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.BQSlotList) ;}private static boolean tom_equal_term_SlotList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_SlotList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.SlotList) ;}private static boolean tom_equal_term_BQSlot(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQSlot(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.BQSlot) ;}private static boolean tom_equal_term_PairNameDecl(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDecl(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDecl) ;}private static boolean tom_equal_term_PairNameDeclList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDeclList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDeclList) ;}private static boolean tom_equal_term_OptionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_OptionList(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.OptionList) ;}private static boolean tom_equal_term_Option(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Option(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.Option) ;}private static boolean tom_equal_term_NumericConstraintType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_NumericConstraintType(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ;}private static boolean tom_equal_term_Constraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Constraint(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.Constraint) ;}private static boolean tom_equal_term_ConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintList(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.ConstraintList) ;}private static boolean tom_equal_term_Theory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Theory(Object t) {return  (t instanceof tom.engine.adt.theory.types.Theory) ;}private static boolean tom_equal_term_ElementaryTheory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ElementaryTheory(Object t) {return  (t instanceof tom.engine.adt.theory.types.ElementaryTheory) ;}private static  tom.engine.adt.tomsignature.types.TomVisit  tom_make_VisitTerm( tom.engine.adt.tomtype.types.TomType  t0,  tom.engine.adt.tominstruction.types.ConstraintInstructionList  t1,  tom.engine.adt.tomoption.types.OptionList  t2) { return  tom.engine.adt.tomsignature.types.tomvisit.VisitTerm.make(t0, t1, t2) ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_TomInstructionToExpression( tom.engine.adt.tominstruction.types.Instruction  t0) { return  tom.engine.adt.tomexpression.types.expression.TomInstructionToExpression.make(t0) ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_TrueTL() { return  tom.engine.adt.tomexpression.types.expression.TrueTL.make() ;}private static  tom.engine.adt.tomexpression.types.Expression  tom_make_Code( String  t0) { return  tom.engine.adt.tomexpression.types.expression.Code.make(t0) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_TermAppl( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomNameList  t1,  tom.engine.adt.tomterm.types.TomList  t2,  tom.engine.adt.tomconstraint.types.ConstraintList  t3) { return  tom.engine.adt.tomterm.types.tomterm.TermAppl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_RecordAppl( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomNameList  t1,  tom.engine.adt.tomslot.types.SlotList  t2,  tom.engine.adt.tomconstraint.types.ConstraintList  t3) { return  tom.engine.adt.tomterm.types.tomterm.RecordAppl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_XMLAppl( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomNameList  t1,  tom.engine.adt.tomterm.types.TomList  t2,  tom.engine.adt.tomterm.types.TomList  t3,  tom.engine.adt.tomconstraint.types.ConstraintList  t4) { return  tom.engine.adt.tomterm.types.tomterm.XMLAppl.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_Variable( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2,  tom.engine.adt.tomconstraint.types.ConstraintList  t3) { return  tom.engine.adt.tomterm.types.tomterm.Variable.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_VariableStar( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2,  tom.engine.adt.tomconstraint.types.ConstraintList  t3) { return  tom.engine.adt.tomterm.types.tomterm.VariableStar.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_make_AntiTerm( tom.engine.adt.tomterm.types.TomTerm  t0) { return  tom.engine.adt.tomterm.types.tomterm.AntiTerm.make(t0) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_TypeTermDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomdeclaration.types.DeclarationList  t1,  tom.engine.adt.tomoption.types.Option  t2) { return  tom.engine.adt.tomdeclaration.types.declaration.TypeTermDecl.make(t0, t1, t2) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetImplementationDecl( tom.engine.adt.code.types.BQTerm  t0,  tom.engine.adt.tominstruction.types.Instruction  t1,  tom.engine.adt.tomoption.types.Option  t2) { return  tom.engine.adt.tomdeclaration.types.declaration.GetImplementationDecl.make(t0, t1, t2) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_IsFsymDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetSlotDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.code.types.BQTerm  t2,  tom.engine.adt.tomexpression.types.Expression  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.GetSlotDecl.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetDefaultDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.GetDefaultDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_EqualTermDecl( tom.engine.adt.code.types.BQTerm  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.EqualTermDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_IsSortDecl( tom.engine.adt.code.types.BQTerm  t0,  tom.engine.adt.tomexpression.types.Expression  t1,  tom.engine.adt.tomoption.types.Option  t2) { return  tom.engine.adt.tomdeclaration.types.declaration.IsSortDecl.make(t0, t1, t2) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetHeadDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomtype.types.TomType  t1,  tom.engine.adt.code.types.BQTerm  t2,  tom.engine.adt.tomexpression.types.Expression  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.GetHeadDecl.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetTailDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.GetTailDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_IsEmptyDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.IsEmptyDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_MakeEmptyList( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tominstruction.types.Instruction  t1,  tom.engine.adt.tomoption.types.Option  t2) { return  tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyList.make(t0, t1, t2) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_MakeAddList( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.code.types.BQTerm  t2,  tom.engine.adt.tominstruction.types.Instruction  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.MakeAddList.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetElementDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.code.types.BQTerm  t2,  tom.engine.adt.tomexpression.types.Expression  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.GetElementDecl.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_GetSizeDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomexpression.types.Expression  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.GetSizeDecl.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_MakeEmptyArray( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tominstruction.types.Instruction  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyArray.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_MakeAddArray( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.code.types.BQTerm  t2,  tom.engine.adt.tominstruction.types.Instruction  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.MakeAddArray.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_MakeDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomtype.types.TomType  t1,  tom.engine.adt.code.types.BQTermList  t2,  tom.engine.adt.tominstruction.types.Instruction  t3,  tom.engine.adt.tomoption.types.Option  t4) { return  tom.engine.adt.tomdeclaration.types.declaration.MakeDecl.make(t0, t1, t2, t3, t4) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_Strategy( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomsignature.types.TomVisitList  t2,  tom.engine.adt.tomoption.types.Option  t3) { return  tom.engine.adt.tomdeclaration.types.declaration.Strategy.make(t0, t1, t2, t3) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_SymbolDecl( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomdeclaration.types.declaration.SymbolDecl.make(t0) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_ListSymbolDecl( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomdeclaration.types.declaration.ListSymbolDecl.make(t0) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_ArraySymbolDecl( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomdeclaration.types.declaration.ArraySymbolDecl.make(t0) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_EmptyDeclaration() { return  tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration.make() ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_make_AbstractDecl( tom.engine.adt.tomdeclaration.types.DeclarationList  t0) { return  tom.engine.adt.tomdeclaration.types.declaration.AbstractDecl.make(t0) ;}private static  tom.engine.adt.tomtype.types.TypeOption  tom_make_SubtypeDecl( String  t0) { return  tom.engine.adt.tomtype.types.typeoption.SubtypeDecl.make(t0) ;}private static  tom.engine.adt.tomtype.types.TomType  tom_make_Type( tom.engine.adt.tomtype.types.TypeOptionList  t0,  String  t1,  tom.engine.adt.tomtype.types.TargetLanguageType  t2) { return  tom.engine.adt.tomtype.types.tomtype.Type.make(t0, t1, t2) ;}private static  tom.engine.adt.tomtype.types.TomType  tom_make_EmptyType() { return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;}private static  tom.engine.adt.tomtype.types.TargetLanguageType  tom_make_TLType( String  t0) { return  tom.engine.adt.tomtype.types.targetlanguagetype.TLType.make(t0) ;}private static  tom.engine.adt.tomtype.types.TargetLanguageType  tom_make_EmptyTargetLanguageType() { return  tom.engine.adt.tomtype.types.targetlanguagetype.EmptyTargetLanguageType.make() ;}private static  tom.engine.adt.code.types.CompositeMember  tom_make_CompositeTL( tom.engine.adt.code.types.TargetLanguage  t0) { return  tom.engine.adt.code.types.compositemember.CompositeTL.make(t0) ;}private static boolean tom_is_fun_sym_BQAppl( tom.engine.adt.code.types.BQTerm  t) {return  (t instanceof tom.engine.adt.code.types.bqterm.BQAppl) ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQAppl( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.code.types.BQTermList  t2) { return  tom.engine.adt.code.types.bqterm.BQAppl.make(t0, t1, t2) ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_BQAppl_Options( tom.engine.adt.code.types.BQTerm  t) {return  t.getOptions() ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_BQAppl_AstName( tom.engine.adt.code.types.BQTerm  t) {return  t.getAstName() ;}private static  tom.engine.adt.code.types.BQTermList  tom_get_slot_BQAppl_Args( tom.engine.adt.code.types.BQTerm  t) {return  t.getArgs() ;}private static boolean tom_is_fun_sym_BQVariable( tom.engine.adt.code.types.BQTerm  t) {return  (t instanceof tom.engine.adt.code.types.bqterm.BQVariable) ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQVariable( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2) { return  tom.engine.adt.code.types.bqterm.BQVariable.make(t0, t1, t2) ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_BQVariable_Options( tom.engine.adt.code.types.BQTerm  t) {return  t.getOptions() ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_BQVariable_AstName( tom.engine.adt.code.types.BQTerm  t) {return  t.getAstName() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_BQVariable_AstType( tom.engine.adt.code.types.BQTerm  t) {return  t.getAstType() ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BQVariableStar( tom.engine.adt.tomoption.types.OptionList  t0,  tom.engine.adt.tomname.types.TomName  t1,  tom.engine.adt.tomtype.types.TomType  t2) { return  tom.engine.adt.code.types.bqterm.BQVariableStar.make(t0, t1, t2) ;}private static boolean tom_is_fun_sym_BuildConstant( tom.engine.adt.code.types.BQTerm  t) {return  (t instanceof tom.engine.adt.code.types.bqterm.BuildConstant) ;}private static  tom.engine.adt.code.types.BQTerm  tom_make_BuildConstant( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.code.types.bqterm.BuildConstant.make(t0) ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_BuildConstant_AstName( tom.engine.adt.code.types.BQTerm  t) {return  t.getAstName() ;}private static  tom.engine.adt.code.types.TargetLanguage  tom_make_ITL( String  t0) { return  tom.engine.adt.code.types.targetlanguage.ITL.make(t0) ;}private static  tom.engine.adt.code.types.Code  tom_make_TargetLanguageToCode( tom.engine.adt.code.types.TargetLanguage  t0) { return  tom.engine.adt.code.types.code.TargetLanguageToCode.make(t0) ;}private static  tom.engine.adt.tominstruction.types.ConstraintInstruction  tom_make_ConstraintInstruction( tom.engine.adt.tomconstraint.types.Constraint  t0,  tom.engine.adt.tominstruction.types.Instruction  t1,  tom.engine.adt.tomoption.types.OptionList  t2) { return  tom.engine.adt.tominstruction.types.constraintinstruction.ConstraintInstruction.make(t0, t1, t2) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_ExpressionToInstruction( tom.engine.adt.tomexpression.types.Expression  t0) { return  tom.engine.adt.tominstruction.types.instruction.ExpressionToInstruction.make(t0) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_CodeToInstruction( tom.engine.adt.code.types.Code  t0) { return  tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make(t0) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_If( tom.engine.adt.tomexpression.types.Expression  t0,  tom.engine.adt.tominstruction.types.Instruction  t1,  tom.engine.adt.tominstruction.types.Instruction  t2) { return  tom.engine.adt.tominstruction.types.instruction.If.make(t0, t1, t2) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_Return( tom.engine.adt.code.types.BQTerm  t0) { return  tom.engine.adt.tominstruction.types.instruction.Return.make(t0) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_Nop() { return  tom.engine.adt.tominstruction.types.instruction.Nop.make() ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_AbstractBlock( tom.engine.adt.tominstruction.types.InstructionList  t0) { return  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(t0) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_Match( tom.engine.adt.tominstruction.types.ConstraintInstructionList  t0,  tom.engine.adt.tomoption.types.OptionList  t1) { return  tom.engine.adt.tominstruction.types.instruction.Match.make(t0, t1) ;}private static  tom.engine.adt.tominstruction.types.Instruction  tom_make_RawAction( tom.engine.adt.tominstruction.types.Instruction  t0) { return  tom.engine.adt.tominstruction.types.instruction.RawAction.make(t0) ;}private static  tom.engine.adt.tomname.types.TomName  tom_make_Name( String  t0) { return  tom.engine.adt.tomname.types.tomname.Name.make(t0) ;}private static  tom.engine.adt.tomname.types.TomName  tom_make_EmptyName() { return  tom.engine.adt.tomname.types.tomname.EmptyName.make() ;}private static  tom.engine.adt.tomname.types.TomName  tom_make_AntiName( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomname.types.tomname.AntiName.make(t0) ;}private static  tom.engine.adt.tomslot.types.Slot  tom_make_PairSlotAppl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomterm.types.TomTerm  t1) { return  tom.engine.adt.tomslot.types.slot.PairSlotAppl.make(t0, t1) ;}private static boolean tom_is_fun_sym_PairNameDecl( tom.engine.adt.tomslot.types.PairNameDecl  t) {return  (t instanceof tom.engine.adt.tomslot.types.pairnamedecl.PairNameDecl) ;}private static  tom.engine.adt.tomslot.types.PairNameDecl  tom_make_PairNameDecl( tom.engine.adt.tomname.types.TomName  t0,  tom.engine.adt.tomdeclaration.types.Declaration  t1) { return  tom.engine.adt.tomslot.types.pairnamedecl.PairNameDecl.make(t0, t1) ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_PairNameDecl_SlotName( tom.engine.adt.tomslot.types.PairNameDecl  t) {return  t.getSlotName() ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_get_slot_PairNameDecl_SlotDecl( tom.engine.adt.tomslot.types.PairNameDecl  t) {return  t.getSlotDecl() ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_DeclarationToOption( tom.engine.adt.tomdeclaration.types.Declaration  t0) { return  tom.engine.adt.tomoption.types.option.DeclarationToOption.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_OriginTracking( tom.engine.adt.tomname.types.TomName  t0,  int  t1,  String  t2) { return  tom.engine.adt.tomoption.types.option.OriginTracking.make(t0, t1, t2) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_OriginalText( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomoption.types.option.OriginalText.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_Constant() { return  tom.engine.adt.tomoption.types.option.Constant.make() ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_MatchingTheory( tom.engine.adt.theory.types.Theory  t0) { return  tom.engine.adt.tomoption.types.option.MatchingTheory.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_Label( tom.engine.adt.tomname.types.TomName  t0) { return  tom.engine.adt.tomoption.types.option.Label.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_ModuleName( String  t0) { return  tom.engine.adt.tomoption.types.option.ModuleName.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_ImplicitXMLAttribut() { return  tom.engine.adt.tomoption.types.option.ImplicitXMLAttribut.make() ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_ImplicitXMLChild() { return  tom.engine.adt.tomoption.types.option.ImplicitXMLChild.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumLessThan() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumLessThan.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumLessOrEqualThan() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumLessOrEqualThan.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumGreaterThan() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumGreaterThan.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumGreaterOrEqualThan() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumGreaterOrEqualThan.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumDifferent() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumDifferent.make() ;}private static  tom.engine.adt.tomconstraint.types.NumericConstraintType  tom_make_NumEqual() { return  tom.engine.adt.tomconstraint.types.numericconstrainttype.NumEqual.make() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_TrueConstraint() { return  tom.engine.adt.tomconstraint.types.constraint.TrueConstraint.make() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_MatchConstraint( tom.engine.adt.tomterm.types.TomTerm  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomtype.types.TomType  t2) { return  tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(t0, t1, t2) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_make_NumericConstraint( tom.engine.adt.code.types.BQTerm  t0,  tom.engine.adt.code.types.BQTerm  t1,  tom.engine.adt.tomconstraint.types.NumericConstraintType  t2) { return  tom.engine.adt.tomconstraint.types.constraint.NumericConstraint.make(t0, t1, t2) ;}private static  tom.engine.adt.theory.types.ElementaryTheory  tom_make_AC() { return  tom.engine.adt.theory.types.elementarytheory.AC.make() ;}private static  tom.engine.adt.theory.types.ElementaryTheory  tom_make_AU() { return  tom.engine.adt.theory.types.elementarytheory.AU.make() ;}private static boolean tom_is_fun_sym_concTomVisit( tom.engine.adt.tomsignature.types.TomVisitList  t) {return  ((t instanceof tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit) || (t instanceof tom.engine.adt.tomsignature.types.tomvisitlist.EmptyconcTomVisit)) ;}private static  tom.engine.adt.tomsignature.types.TomVisitList  tom_empty_list_concTomVisit() { return  tom.engine.adt.tomsignature.types.tomvisitlist.EmptyconcTomVisit.make() ;}private static  tom.engine.adt.tomsignature.types.TomVisitList  tom_cons_list_concTomVisit( tom.engine.adt.tomsignature.types.TomVisit  e,  tom.engine.adt.tomsignature.types.TomVisitList  l) { return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make(e,l) ;}private static  tom.engine.adt.tomsignature.types.TomVisit  tom_get_head_concTomVisit_TomVisitList( tom.engine.adt.tomsignature.types.TomVisitList  l) {return  l.getHeadconcTomVisit() ;}private static  tom.engine.adt.tomsignature.types.TomVisitList  tom_get_tail_concTomVisit_TomVisitList( tom.engine.adt.tomsignature.types.TomVisitList  l) {return  l.getTailconcTomVisit() ;}private static boolean tom_is_empty_concTomVisit_TomVisitList( tom.engine.adt.tomsignature.types.TomVisitList  l) {return  l.isEmptyconcTomVisit() ;}   private static   tom.engine.adt.tomsignature.types.TomVisitList  tom_append_list_concTomVisit( tom.engine.adt.tomsignature.types.TomVisitList l1,  tom.engine.adt.tomsignature.types.TomVisitList  l2) {     if( l1.isEmptyconcTomVisit() ) {       return l2;     } else if( l2.isEmptyconcTomVisit() ) {       return l1;     } else if(  l1.getTailconcTomVisit() .isEmptyconcTomVisit() ) {       return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( l1.getHeadconcTomVisit() ,l2) ;     } else {       return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( l1.getHeadconcTomVisit() ,tom_append_list_concTomVisit( l1.getTailconcTomVisit() ,l2)) ;     }   }   private static   tom.engine.adt.tomsignature.types.TomVisitList  tom_get_slice_concTomVisit( tom.engine.adt.tomsignature.types.TomVisitList  begin,  tom.engine.adt.tomsignature.types.TomVisitList  end, tom.engine.adt.tomsignature.types.TomVisitList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomVisit()  ||  (end==tom_empty_list_concTomVisit()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( begin.getHeadconcTomVisit() ,( tom.engine.adt.tomsignature.types.TomVisitList )tom_get_slice_concTomVisit( begin.getTailconcTomVisit() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concTomTerm( tom.engine.adt.tomterm.types.TomList  t) {return  ((t instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || (t instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ;}private static  tom.engine.adt.tomterm.types.TomList  tom_empty_list_concTomTerm() { return  tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ;}private static  tom.engine.adt.tomterm.types.TomList  tom_cons_list_concTomTerm( tom.engine.adt.tomterm.types.TomTerm  e,  tom.engine.adt.tomterm.types.TomList  l) { return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(e,l) ;}private static  tom.engine.adt.tomterm.types.TomTerm  tom_get_head_concTomTerm_TomList( tom.engine.adt.tomterm.types.TomList  l) {return  l.getHeadconcTomTerm() ;}private static  tom.engine.adt.tomterm.types.TomList  tom_get_tail_concTomTerm_TomList( tom.engine.adt.tomterm.types.TomList  l) {return  l.getTailconcTomTerm() ;}private static boolean tom_is_empty_concTomTerm_TomList( tom.engine.adt.tomterm.types.TomList  l) {return  l.isEmptyconcTomTerm() ;}   private static   tom.engine.adt.tomterm.types.TomList  tom_append_list_concTomTerm( tom.engine.adt.tomterm.types.TomList l1,  tom.engine.adt.tomterm.types.TomList  l2) {     if( l1.isEmptyconcTomTerm() ) {       return l2;     } else if( l2.isEmptyconcTomTerm() ) {       return l1;     } else if(  l1.getTailconcTomTerm() .isEmptyconcTomTerm() ) {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,l2) ;     } else {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,tom_append_list_concTomTerm( l1.getTailconcTomTerm() ,l2)) ;     }   }   private static   tom.engine.adt.tomterm.types.TomList  tom_get_slice_concTomTerm( tom.engine.adt.tomterm.types.TomList  begin,  tom.engine.adt.tomterm.types.TomList  end, tom.engine.adt.tomterm.types.TomList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomTerm()  ||  (end==tom_empty_list_concTomTerm()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( begin.getHeadconcTomTerm() ,( tom.engine.adt.tomterm.types.TomList )tom_get_slice_concTomTerm( begin.getTailconcTomTerm() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concDeclaration( tom.engine.adt.tomdeclaration.types.DeclarationList  t) {return  ((t instanceof tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration) || (t instanceof tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration)) ;}private static  tom.engine.adt.tomdeclaration.types.DeclarationList  tom_empty_list_concDeclaration() { return  tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() ;}private static  tom.engine.adt.tomdeclaration.types.DeclarationList  tom_cons_list_concDeclaration( tom.engine.adt.tomdeclaration.types.Declaration  e,  tom.engine.adt.tomdeclaration.types.DeclarationList  l) { return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make(e,l) ;}private static  tom.engine.adt.tomdeclaration.types.Declaration  tom_get_head_concDeclaration_DeclarationList( tom.engine.adt.tomdeclaration.types.DeclarationList  l) {return  l.getHeadconcDeclaration() ;}private static  tom.engine.adt.tomdeclaration.types.DeclarationList  tom_get_tail_concDeclaration_DeclarationList( tom.engine.adt.tomdeclaration.types.DeclarationList  l) {return  l.getTailconcDeclaration() ;}private static boolean tom_is_empty_concDeclaration_DeclarationList( tom.engine.adt.tomdeclaration.types.DeclarationList  l) {return  l.isEmptyconcDeclaration() ;}   private static   tom.engine.adt.tomdeclaration.types.DeclarationList  tom_append_list_concDeclaration( tom.engine.adt.tomdeclaration.types.DeclarationList l1,  tom.engine.adt.tomdeclaration.types.DeclarationList  l2) {     if( l1.isEmptyconcDeclaration() ) {       return l2;     } else if( l2.isEmptyconcDeclaration() ) {       return l1;     } else if(  l1.getTailconcDeclaration() .isEmptyconcDeclaration() ) {       return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( l1.getHeadconcDeclaration() ,l2) ;     } else {       return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( l1.getHeadconcDeclaration() ,tom_append_list_concDeclaration( l1.getTailconcDeclaration() ,l2)) ;     }   }   private static   tom.engine.adt.tomdeclaration.types.DeclarationList  tom_get_slice_concDeclaration( tom.engine.adt.tomdeclaration.types.DeclarationList  begin,  tom.engine.adt.tomdeclaration.types.DeclarationList  end, tom.engine.adt.tomdeclaration.types.DeclarationList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcDeclaration()  ||  (end==tom_empty_list_concDeclaration()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( begin.getHeadconcDeclaration() ,( tom.engine.adt.tomdeclaration.types.DeclarationList )tom_get_slice_concDeclaration( begin.getTailconcDeclaration() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concTomType( tom.engine.adt.tomtype.types.TomTypeList  t) {return  ((t instanceof tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType) || (t instanceof tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType)) ;}private static  tom.engine.adt.tomtype.types.TomTypeList  tom_empty_list_concTomType() { return  tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType.make() ;}private static  tom.engine.adt.tomtype.types.TomTypeList  tom_cons_list_concTomType( tom.engine.adt.tomtype.types.TomType  e,  tom.engine.adt.tomtype.types.TomTypeList  l) { return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make(e,l) ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_head_concTomType_TomTypeList( tom.engine.adt.tomtype.types.TomTypeList  l) {return  l.getHeadconcTomType() ;}private static  tom.engine.adt.tomtype.types.TomTypeList  tom_get_tail_concTomType_TomTypeList( tom.engine.adt.tomtype.types.TomTypeList  l) {return  l.getTailconcTomType() ;}private static boolean tom_is_empty_concTomType_TomTypeList( tom.engine.adt.tomtype.types.TomTypeList  l) {return  l.isEmptyconcTomType() ;}   private static   tom.engine.adt.tomtype.types.TomTypeList  tom_append_list_concTomType( tom.engine.adt.tomtype.types.TomTypeList l1,  tom.engine.adt.tomtype.types.TomTypeList  l2) {     if( l1.isEmptyconcTomType() ) {       return l2;     } else if( l2.isEmptyconcTomType() ) {       return l1;     } else if(  l1.getTailconcTomType() .isEmptyconcTomType() ) {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,l2) ;     } else {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,tom_append_list_concTomType( l1.getTailconcTomType() ,l2)) ;     }   }   private static   tom.engine.adt.tomtype.types.TomTypeList  tom_get_slice_concTomType( tom.engine.adt.tomtype.types.TomTypeList  begin,  tom.engine.adt.tomtype.types.TomTypeList  end, tom.engine.adt.tomtype.types.TomTypeList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomType()  ||  (end==tom_empty_list_concTomType()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( begin.getHeadconcTomType() ,( tom.engine.adt.tomtype.types.TomTypeList )tom_get_slice_concTomType( begin.getTailconcTomType() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concTypeOption( tom.engine.adt.tomtype.types.TypeOptionList  t) {return  ((t instanceof tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption) || (t instanceof tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption)) ;}private static  tom.engine.adt.tomtype.types.TypeOptionList  tom_empty_list_concTypeOption() { return  tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ;}private static  tom.engine.adt.tomtype.types.TypeOptionList  tom_cons_list_concTypeOption( tom.engine.adt.tomtype.types.TypeOption  e,  tom.engine.adt.tomtype.types.TypeOptionList  l) { return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make(e,l) ;}private static  tom.engine.adt.tomtype.types.TypeOption  tom_get_head_concTypeOption_TypeOptionList( tom.engine.adt.tomtype.types.TypeOptionList  l) {return  l.getHeadconcTypeOption() ;}private static  tom.engine.adt.tomtype.types.TypeOptionList  tom_get_tail_concTypeOption_TypeOptionList( tom.engine.adt.tomtype.types.TypeOptionList  l) {return  l.getTailconcTypeOption() ;}private static boolean tom_is_empty_concTypeOption_TypeOptionList( tom.engine.adt.tomtype.types.TypeOptionList  l) {return  l.isEmptyconcTypeOption() ;}   private static   tom.engine.adt.tomtype.types.TypeOptionList  tom_append_list_concTypeOption( tom.engine.adt.tomtype.types.TypeOptionList l1,  tom.engine.adt.tomtype.types.TypeOptionList  l2) {     if( l1.isEmptyconcTypeOption() ) {       return l2;     } else if( l2.isEmptyconcTypeOption() ) {       return l1;     } else if(  l1.getTailconcTypeOption() .isEmptyconcTypeOption() ) {       return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( l1.getHeadconcTypeOption() ,l2) ;     } else {       return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( l1.getHeadconcTypeOption() ,tom_append_list_concTypeOption( l1.getTailconcTypeOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomtype.types.TypeOptionList  tom_get_slice_concTypeOption( tom.engine.adt.tomtype.types.TypeOptionList  begin,  tom.engine.adt.tomtype.types.TypeOptionList  end, tom.engine.adt.tomtype.types.TypeOptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTypeOption()  ||  (end==tom_empty_list_concTypeOption()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( begin.getHeadconcTypeOption() ,( tom.engine.adt.tomtype.types.TypeOptionList )tom_get_slice_concTypeOption( begin.getTailconcTypeOption() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concBQTerm( tom.engine.adt.code.types.BQTermList  t) {return  ((t instanceof tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm) || (t instanceof tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm)) ;}private static  tom.engine.adt.code.types.BQTermList  tom_empty_list_concBQTerm() { return  tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ;}private static  tom.engine.adt.code.types.BQTermList  tom_cons_list_concBQTerm( tom.engine.adt.code.types.BQTerm  e,  tom.engine.adt.code.types.BQTermList  l) { return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(e,l) ;}private static  tom.engine.adt.code.types.BQTerm  tom_get_head_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.getHeadconcBQTerm() ;}private static  tom.engine.adt.code.types.BQTermList  tom_get_tail_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.getTailconcBQTerm() ;}private static boolean tom_is_empty_concBQTerm_BQTermList( tom.engine.adt.code.types.BQTermList  l) {return  l.isEmptyconcBQTerm() ;}   private static   tom.engine.adt.code.types.BQTermList  tom_append_list_concBQTerm( tom.engine.adt.code.types.BQTermList l1,  tom.engine.adt.code.types.BQTermList  l2) {     if( l1.isEmptyconcBQTerm() ) {       return l2;     } else if( l2.isEmptyconcBQTerm() ) {       return l1;     } else if(  l1.getTailconcBQTerm() .isEmptyconcBQTerm() ) {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,tom_append_list_concBQTerm( l1.getTailconcBQTerm() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTermList  tom_get_slice_concBQTerm( tom.engine.adt.code.types.BQTermList  begin,  tom.engine.adt.code.types.BQTermList  end, tom.engine.adt.code.types.BQTermList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcBQTerm()  ||  (end==tom_empty_list_concBQTerm()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( begin.getHeadconcBQTerm() ,( tom.engine.adt.code.types.BQTermList )tom_get_slice_concBQTerm( begin.getTailconcBQTerm() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_Composite( tom.engine.adt.code.types.BQTerm  t) {return  ((t instanceof tom.engine.adt.code.types.bqterm.ConsComposite) || (t instanceof tom.engine.adt.code.types.bqterm.EmptyComposite)) ;}private static  tom.engine.adt.code.types.BQTerm  tom_empty_list_Composite() { return  tom.engine.adt.code.types.bqterm.EmptyComposite.make() ;}private static  tom.engine.adt.code.types.BQTerm  tom_cons_list_Composite( tom.engine.adt.code.types.CompositeMember  e,  tom.engine.adt.code.types.BQTerm  l) { return  tom.engine.adt.code.types.bqterm.ConsComposite.make(e,l) ;}private static  tom.engine.adt.code.types.CompositeMember  tom_get_head_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.getHeadComposite() ;}private static  tom.engine.adt.code.types.BQTerm  tom_get_tail_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.getTailComposite() ;}private static boolean tom_is_empty_Composite_BQTerm( tom.engine.adt.code.types.BQTerm  l) {return  l.isEmptyComposite() ;}   private static   tom.engine.adt.code.types.BQTerm  tom_append_list_Composite( tom.engine.adt.code.types.BQTerm l1,  tom.engine.adt.code.types.BQTerm  l2) {     if( l1.isEmptyComposite() ) {       return l2;     } else if( l2.isEmptyComposite() ) {       return l1;     } else if(  l1.getTailComposite() .isEmptyComposite() ) {       return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,tom_append_list_Composite( l1.getTailComposite() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTerm  tom_get_slice_Composite( tom.engine.adt.code.types.BQTerm  begin,  tom.engine.adt.code.types.BQTerm  end, tom.engine.adt.code.types.BQTerm  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyComposite()  ||  (end==tom_empty_list_Composite()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqterm.ConsComposite.make( begin.getHeadComposite() ,( tom.engine.adt.code.types.BQTerm )tom_get_slice_Composite( begin.getTailComposite() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concTomName( tom.engine.adt.tomname.types.TomNameList  t) {return  ((t instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (t instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ;}private static  tom.engine.adt.tomname.types.TomNameList  tom_empty_list_concTomName() { return  tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ;}private static  tom.engine.adt.tomname.types.TomNameList  tom_cons_list_concTomName( tom.engine.adt.tomname.types.TomName  e,  tom.engine.adt.tomname.types.TomNameList  l) { return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make(e,l) ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_head_concTomName_TomNameList( tom.engine.adt.tomname.types.TomNameList  l) {return  l.getHeadconcTomName() ;}private static  tom.engine.adt.tomname.types.TomNameList  tom_get_tail_concTomName_TomNameList( tom.engine.adt.tomname.types.TomNameList  l) {return  l.getTailconcTomName() ;}private static boolean tom_is_empty_concTomName_TomNameList( tom.engine.adt.tomname.types.TomNameList  l) {return  l.isEmptyconcTomName() ;}   private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end==tom_empty_list_concTomName()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList  t) {return  ((t instanceof tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl) || (t instanceof tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl)) ;}private static  tom.engine.adt.tomslot.types.PairNameDeclList  tom_empty_list_concPairNameDecl() { return  tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl.make() ;}private static  tom.engine.adt.tomslot.types.PairNameDeclList  tom_cons_list_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDecl  e,  tom.engine.adt.tomslot.types.PairNameDeclList  l) { return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make(e,l) ;}private static  tom.engine.adt.tomslot.types.PairNameDecl  tom_get_head_concPairNameDecl_PairNameDeclList( tom.engine.adt.tomslot.types.PairNameDeclList  l) {return  l.getHeadconcPairNameDecl() ;}private static  tom.engine.adt.tomslot.types.PairNameDeclList  tom_get_tail_concPairNameDecl_PairNameDeclList( tom.engine.adt.tomslot.types.PairNameDeclList  l) {return  l.getTailconcPairNameDecl() ;}private static boolean tom_is_empty_concPairNameDecl_PairNameDeclList( tom.engine.adt.tomslot.types.PairNameDeclList  l) {return  l.isEmptyconcPairNameDecl() ;}   private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_append_list_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList l1,  tom.engine.adt.tomslot.types.PairNameDeclList  l2) {     if( l1.isEmptyconcPairNameDecl() ) {       return l2;     } else if( l2.isEmptyconcPairNameDecl() ) {       return l1;     } else if(  l1.getTailconcPairNameDecl() .isEmptyconcPairNameDecl() ) {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,tom_append_list_concPairNameDecl( l1.getTailconcPairNameDecl() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_get_slice_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList  begin,  tom.engine.adt.tomslot.types.PairNameDeclList  end, tom.engine.adt.tomslot.types.PairNameDeclList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcPairNameDecl()  ||  (end==tom_empty_list_concPairNameDecl()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( begin.getHeadconcPairNameDecl() ,( tom.engine.adt.tomslot.types.PairNameDeclList )tom_get_slice_concPairNameDecl( begin.getTailconcPairNameDecl() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concOption( tom.engine.adt.tomoption.types.OptionList  t) {return  ((t instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || (t instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_empty_list_concOption() { return  tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_cons_list_concOption( tom.engine.adt.tomoption.types.Option  e,  tom.engine.adt.tomoption.types.OptionList  l) { return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(e,l) ;}private static  tom.engine.adt.tomoption.types.Option  tom_get_head_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.getHeadconcOption() ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_tail_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.getTailconcOption() ;}private static boolean tom_is_empty_concOption_OptionList( tom.engine.adt.tomoption.types.OptionList  l) {return  l.isEmptyconcOption() ;}   private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {     if( l1.isEmptyconcOption() ) {       return l2;     } else if( l2.isEmptyconcOption() ) {       return l1;     } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;     } else {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end==tom_empty_list_concOption()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {return  ((t instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (t instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_empty_list_AndConstraint() { return  tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_cons_list_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.Constraint  l) { return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(e,l) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_AndConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.getHeadAndConstraint() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_tail_AndConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.getTailAndConstraint() ;}private static boolean tom_is_empty_AndConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.isEmptyAndConstraint() ;}   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_append_list_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  l1,  tom.engine.adt.tomconstraint.types.Constraint  l2) {     if( l1.isEmptyAndConstraint() ) {       return l2;     } else if( l2.isEmptyAndConstraint() ) {       return l1;     } else if( ((l1 instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (l1 instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) ) {       if(  l1.getTailAndConstraint() .isEmptyAndConstraint() ) {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,l2) ;       } else {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,tom_append_list_AndConstraint( l1.getTailAndConstraint() ,l2)) ;       }     } else {       return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(l1,l2) ;     }   }   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_get_slice_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  begin,  tom.engine.adt.tomconstraint.types.Constraint  end, tom.engine.adt.tomconstraint.types.Constraint  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyAndConstraint()  ||  (end==tom_empty_list_AndConstraint()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getHeadAndConstraint() :begin),( tom.engine.adt.tomconstraint.types.Constraint )tom_get_slice_AndConstraint((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getTailAndConstraint() :tom_empty_list_AndConstraint()),end,tail)) ;   }   private static boolean tom_is_fun_sym_OrConstraint( tom.engine.adt.tomconstraint.types.Constraint  t) {return  ((t instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint) || (t instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraint)) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_empty_list_OrConstraint() { return  tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraint.make() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_cons_list_OrConstraint( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.Constraint  l) { return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint.make(e,l) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_OrConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.getHeadOrConstraint() ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_tail_OrConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.getTailOrConstraint() ;}private static boolean tom_is_empty_OrConstraint_Constraint( tom.engine.adt.tomconstraint.types.Constraint  l) {return  l.isEmptyOrConstraint() ;}   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_append_list_OrConstraint( tom.engine.adt.tomconstraint.types.Constraint  l1,  tom.engine.adt.tomconstraint.types.Constraint  l2) {     if( l1.isEmptyOrConstraint() ) {       return l2;     } else if( l2.isEmptyOrConstraint() ) {       return l1;     } else if( ((l1 instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint) || (l1 instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraint)) ) {       if(  l1.getTailOrConstraint() .isEmptyOrConstraint() ) {         return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint.make( l1.getHeadOrConstraint() ,l2) ;       } else {         return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint.make( l1.getHeadOrConstraint() ,tom_append_list_OrConstraint( l1.getTailOrConstraint() ,l2)) ;       }     } else {       return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint.make(l1,l2) ;     }   }   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_get_slice_OrConstraint( tom.engine.adt.tomconstraint.types.Constraint  begin,  tom.engine.adt.tomconstraint.types.Constraint  end, tom.engine.adt.tomconstraint.types.Constraint  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyOrConstraint()  ||  (end==tom_empty_list_OrConstraint()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint.make((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraint)) )? begin.getHeadOrConstraint() :begin),( tom.engine.adt.tomconstraint.types.Constraint )tom_get_slice_OrConstraint((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraint)) )? begin.getTailOrConstraint() :tom_empty_list_OrConstraint()),end,tail)) ;   }   private static boolean tom_is_fun_sym_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  t) {return  ((t instanceof tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint) || (t instanceof tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint)) ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_empty_list_concConstraint() { return  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_cons_list_concConstraint( tom.engine.adt.tomconstraint.types.Constraint  e,  tom.engine.adt.tomconstraint.types.ConstraintList  l) { return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make(e,l) ;}private static  tom.engine.adt.tomconstraint.types.Constraint  tom_get_head_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {return  l.getHeadconcConstraint() ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_tail_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {return  l.getTailconcConstraint() ;}private static boolean tom_is_empty_concConstraint_ConstraintList( tom.engine.adt.tomconstraint.types.ConstraintList  l) {return  l.isEmptyconcConstraint() ;}   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {     if( l1.isEmptyconcConstraint() ) {       return l2;     } else if( l2.isEmptyconcConstraint() ) {       return l1;     } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;     } else {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;     }   }   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end==tom_empty_list_concConstraint()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;   }   private static boolean tom_is_fun_sym_concElementaryTheory( tom.engine.adt.theory.types.Theory  t) {return  ((t instanceof tom.engine.adt.theory.types.theory.ConsconcElementaryTheory) || (t instanceof tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory)) ;}private static  tom.engine.adt.theory.types.Theory  tom_empty_list_concElementaryTheory() { return  tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ;}private static  tom.engine.adt.theory.types.Theory  tom_cons_list_concElementaryTheory( tom.engine.adt.theory.types.ElementaryTheory  e,  tom.engine.adt.theory.types.Theory  l) { return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make(e,l) ;}private static  tom.engine.adt.theory.types.ElementaryTheory  tom_get_head_concElementaryTheory_Theory( tom.engine.adt.theory.types.Theory  l) {return  l.getHeadconcElementaryTheory() ;}private static  tom.engine.adt.theory.types.Theory  tom_get_tail_concElementaryTheory_Theory( tom.engine.adt.theory.types.Theory  l) {return  l.getTailconcElementaryTheory() ;}private static boolean tom_is_empty_concElementaryTheory_Theory( tom.engine.adt.theory.types.Theory  l) {return  l.isEmptyconcElementaryTheory() ;}   private static   tom.engine.adt.theory.types.Theory  tom_append_list_concElementaryTheory( tom.engine.adt.theory.types.Theory l1,  tom.engine.adt.theory.types.Theory  l2) {     if( l1.isEmptyconcElementaryTheory() ) {       return l2;     } else if( l2.isEmptyconcElementaryTheory() ) {       return l1;     } else if(  l1.getTailconcElementaryTheory() .isEmptyconcElementaryTheory() ) {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,l2) ;     } else {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,tom_append_list_concElementaryTheory( l1.getTailconcElementaryTheory() ,l2)) ;     }   }   private static   tom.engine.adt.theory.types.Theory  tom_get_slice_concElementaryTheory( tom.engine.adt.theory.types.Theory  begin,  tom.engine.adt.theory.types.Theory  end, tom.engine.adt.theory.types.Theory  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcElementaryTheory()  ||  (end==tom_empty_list_concElementaryTheory()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( begin.getHeadconcElementaryTheory() ,( tom.engine.adt.theory.types.Theory )tom_get_slice_concElementaryTheory( begin.getTailconcElementaryTheory() ,end,tail)) ;   }    
    //--------------------------

    public String currentFile() {
        return targetparser.getCurrentFile();
    }

    // the default-mode parser
    private HostParser targetparser;
    protected BackQuoteParser bqparser;
    private TomLexer tomlexer;

    //store information for the OriginalText contained in the OptionList
    private StringBuilder text = new StringBuilder();

    private int lastLine;

    private SymbolTable symbolTable;

    private OptionManager optionManager;

    private HashMap<String,String> usedSlots = new HashMap<String,String>();

    public TomParser(ParserSharedInputState state, HostParser target,
                     OptionManager optionManager) {
        this(state);
        this.targetparser = target;
        this.tomlexer = (TomLexer) selector().getStream("tomlexer");
        this.symbolTable = target.getSymbolTable();
        this.bqparser = new BackQuoteParser(state,this);
        this.optionManager = optionManager;
    }

    /**
     * Returns the value of a boolean option.
     * 
     * @param optionName the name of the option whose value is seeked
     * @return a boolean that is the option's value
     */
    public boolean getOptionBooleanValue(String optionName) {
      return ((Boolean)optionManager.getOptionValue(optionName)).booleanValue();
    }

    private void putSlot(String sName, String sType) {
      usedSlots.put(sName,sType);
    }

    private String getSlotType(String sName) {
      return usedSlots.get(sName);
    }

    private void putType(String name, TomType type) {
        symbolTable.putType(name,type);
    }

    private TomType getType(String name) {
        return symbolTable.getType(name);
    }

    private void putSymbol(String name, TomSymbol symbol) {
        symbolTable.putSymbol(name,symbol);
    }

    private int getLine() {
        return tomlexer.getLine();
    }

    public void updatePosition(int i, int j) {
        targetparser.updatePosition(i,j);
    }

    public void addTargetCode(Token t) {
        targetparser.addTargetCode(t);
    }

    private void setLastLine(int line) {
        lastLine = line;
    }

    private void clearText() {
        text.delete(0,text.length());
    }

    protected TokenStreamSelector selector() {
        return targetparser.getSelector();
    }

    private Logger getLogger() {
      return Logger.getLogger(getClass().getName());
    }


protected TomParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public TomParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected TomParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public TomParser(TokenStream lexer) {
  this(lexer,1);
}

public TomParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final Instruction  matchConstruct(
		Option ot
	) throws RecognitionException, TokenStreamException, TomException {
		Instruction result;
		
		Token  t1 = null;
		Token  t2 = null;
		
		result = null;
		OptionList optionList = tom_cons_list_concOption(ot,tom_cons_list_concOption(tom_make_ModuleName(TomBase.DEFAULT_MODULE_NAME),tom_empty_list_concOption()));
		List<BQTerm> argumentList = new LinkedList<BQTerm>();
		List<TomType> subjectTypeList = new LinkedList<TomType>();
		List<ConstraintInstruction> constraintInstructionList = new LinkedList<ConstraintInstruction>();
		BQTermList subjectList = null;
		TomType patternType = SymbolTable.TYPE_UNKNOWN;
		
		
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			matchArguments(argumentList,subjectTypeList);
			match(RPAREN);
			match(LBRACE);
			if ( inputState.guessing==0 ) {
				
				subjectList = ASTFactory.makeBQTermList(argumentList); 
				
			}
			{
			_loop4:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					patternInstruction(subjectList,subjectTypeList,constraintInstructionList,patternType);
				}
				else {
					break _loop4;
				}
				
			} while (true);
			}
			t1 = LT(1);
			match(RBRACE);
			if ( inputState.guessing==0 ) {
				
				result = tom_make_Match(ASTFactory.makeConstraintInstructionList(constraintInstructionList),optionList);
				// update for new target block...
				updatePosition(t1.getLine(),t1.getColumn());
				// Match is finished : pop the tomlexer and return in the target parser.
				selector().pop();
				
			}
			break;
		}
		case LBRACE:
		{
			match(LBRACE);
			if ( inputState.guessing==0 ) {
				subjectList = ASTFactory.makeBQTermList(argumentList);
			}
			{
			_loop6:
			do {
				if ((_tokenSet_1.member(LA(1)))) {
					constraintInstruction(constraintInstructionList,patternType);
				}
				else {
					break _loop6;
				}
				
			} while (true);
			}
			t2 = LT(1);
			match(RBRACE);
			if ( inputState.guessing==0 ) {
				
				result = tom_make_Match(ASTFactory.makeConstraintInstructionList(constraintInstructionList),optionList);
				// update for new target block...
				updatePosition(t2.getLine(),t2.getColumn());
				// Match is finished : pop the tomlexer and return in the target parser.
				selector().pop();
				
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
	
	public final void matchArguments(
		List<BQTerm> list, List<TomType> typeList
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		{
		matchArgument(list,typeList);
		{
		_loop10:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				matchArgument(list,typeList);
			}
			else {
				break _loop10;
			}
			
		} while (true);
		}
		}
	}
	
	public final void patternInstruction(
		BQTermList subjectList, List<TomType> subjectTypeList, List<ConstraintInstruction> list, TomType rhsType
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  label = null;
		
		List<Option> optionListLinked = new LinkedList<Option>();
		List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
		
		Constraint constraint = tom_make_TrueConstraint();
		Constraint constr = null;
		OptionList optionList = null;
		Option option = null;
		
		boolean isAnd = false;
		
		clearText();
		
		
		{
		{
		boolean synPredMatched18 = false;
		if (((LA(1)==ALL_ID))) {
			int _m18 = mark();
			synPredMatched18 = true;
			inputState.guessing++;
			try {
				{
				match(ALL_ID);
				match(COLON);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched18 = false;
			}
			rewind(_m18);
inputState.guessing--;
		}
		if ( synPredMatched18 ) {
			label = LT(1);
			match(ALL_ID);
			match(COLON);
		}
		else if ((_tokenSet_0.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		option=matchPattern(matchPatternList,true);
		if ( inputState.guessing==0 ) {
			
			if(matchPatternList.size() != subjectList.length()) {
			TomMessage.error(getLogger(),currentFile(), getLine(),
			TomMessage.badMatchNumberArgument, 
			Integer.valueOf(subjectList.length()), Integer.valueOf(matchPatternList.size()));
			return;
			}
			
			int counter = 0;
			{{if (tom_is_sort_BQTermList(subjectList)) {if (tom_is_fun_sym_concBQTerm((( tom.engine.adt.code.types.BQTermList )subjectList))) { tom.engine.adt.code.types.BQTermList  tomMatch2__end__4=(( tom.engine.adt.code.types.BQTermList )subjectList);do {{if (!(tom_is_empty_concBQTerm_BQTermList(tomMatch2__end__4))) {
			
			constraint =
			tom_cons_list_AndConstraint(constraint,tom_cons_list_AndConstraint(tom_make_MatchConstraint(matchPatternList.get(counter),tom_get_head_concBQTerm_BQTermList(tomMatch2__end__4),subjectTypeList.get(counter)),tom_empty_list_AndConstraint()));
			counter++;
			}if (tom_is_empty_concBQTerm_BQTermList(tomMatch2__end__4)) {tomMatch2__end__4=(( tom.engine.adt.code.types.BQTermList )subjectList);} else {tomMatch2__end__4=tom_get_tail_concBQTerm_BQTermList(tomMatch2__end__4);}}} while(!(tom_equal_term_BQTermList(tomMatch2__end__4, (( tom.engine.adt.code.types.BQTermList )subjectList))));}}}}
			
			
			optionList = tom_cons_list_concOption(option,tom_cons_list_concOption(tom_make_OriginalText(tom_make_Name(text.toString())),tom_empty_list_concOption()));
			
			matchPatternList.clear();
			clearText();
			
		}
		{
		switch ( LA(1)) {
		case AND_CONNECTOR:
		case OR_CONNECTOR:
		{
			{
			switch ( LA(1)) {
			case AND_CONNECTOR:
			{
				match(AND_CONNECTOR);
				if ( inputState.guessing==0 ) {
					isAnd = true;
				}
				break;
			}
			case OR_CONNECTOR:
			{
				match(OR_CONNECTOR);
				if ( inputState.guessing==0 ) {
					isAnd = false;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			constr=matchOrConstraint(optionListLinked);
			if ( inputState.guessing==0 ) {
				
				constraint = isAnd ? tom_cons_list_AndConstraint(constraint,tom_cons_list_AndConstraint(constr,tom_empty_list_AndConstraint())) : tom_cons_list_OrConstraint(constraint,tom_cons_list_OrConstraint(constr,tom_empty_list_OrConstraint()));
				
			}
			break;
		}
		case ARROW:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		arrowAndAction(list,optionList,optionListLinked,label,rhsType,constraint);
		}
	}
	
	public final void constraintInstruction(
		List<ConstraintInstruction> list, TomType rhsType
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		List<Option> optionListLinked = new LinkedList<Option>();
		Constraint constraint = tom_make_TrueConstraint();
		clearText();
		
		
		{
		constraint=matchOrConstraint(optionListLinked);
		arrowAndAction(list,null,optionListLinked,null,rhsType,constraint);
		}
	}
	
	public final void matchArgument(
		List<BQTerm> list, List<TomType> typeList
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		BQTerm subject1 = null;
		BQTerm subject2 = null;
		
		String s1 = null;
		String s2 = null;
		
		
		subject1=plainBQTerm();
		if ( inputState.guessing==0 ) {
			
			s1 = text.toString();
			text.delete(0, text.length());
			
		}
		{
		switch ( LA(1)) {
		case BACKQUOTE:
		{
			match(BACKQUOTE);
			if ( inputState.guessing==0 ) {
				text.delete(0, text.length());
			}
			break;
		}
		case RPAREN:
		case COMMA:
		case ALL_ID:
		case AND_CONNECTOR:
		case OR_CONNECTOR:
		case ARROW:
		case NUM_INT:
		case STRING:
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
		switch ( LA(1)) {
		case ALL_ID:
		case NUM_INT:
		case STRING:
		{
			subject2=plainBQTerm();
			if ( inputState.guessing==0 ) {
				s2 = text.toString();
			}
			break;
		}
		case RPAREN:
		case COMMA:
		case AND_CONNECTOR:
		case OR_CONNECTOR:
		case ARROW:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			
			if(subject2==null) {
			list.add(subject1);
			typeList.add(SymbolTable.TYPE_UNKNOWN);
			} else {
			if(subject1.isBQVariable()) {
			String typeName = subject1.getAstName().getString();
			{{if (tom_is_sort_BQTerm(subject2)) {boolean tomMatch1_2= false ;if (tom_is_fun_sym_BQVariable((( tom.engine.adt.code.types.BQTerm )subject2))) {tomMatch1_2= true ;} else {if (tom_is_fun_sym_BQAppl((( tom.engine.adt.code.types.BQTerm )subject2))) {tomMatch1_2= true ;} else {if (tom_is_fun_sym_BuildConstant((( tom.engine.adt.code.types.BQTerm )subject2))) {tomMatch1_2= true ;}}}if (tomMatch1_2) {
			
			list.add(subject2);
			typeList.add(tom_make_Type(tom_empty_list_concTypeOption(),typeName,tom_make_EmptyTargetLanguageType()));
			return;
			}}}}
			
			}
			throw new TomException(TomMessage.invalidMatchSubject, new Object[]{subject1, subject2});
			}
			
		}
	}
	
	public final BQTerm  plainBQTerm() throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  star = null;
		Token  args = null;
		Token  number = null;
		Token  string = null;
		
		TomName name = null;
		result = null;
		List<Option> optionList = new LinkedList<Option>();
		BQTerm tmp;
		BQTermList l = tom_empty_list_concBQTerm();
		
		
		switch ( LA(1)) {
		case ALL_ID:
		{
			name=headSymbol(optionList);
			{
			if ((LA(1)==STAR)) {
				star = LT(1);
				match(STAR);
			}
			else if ((LA(1)==LPAREN)) {
				{
				args = LT(1);
				match(LPAREN);
				{
				switch ( LA(1)) {
				case ALL_ID:
				case NUM_INT:
				case STRING:
				{
					tmp=plainBQTerm();
					if ( inputState.guessing==0 ) {
						l = tom_append_list_concBQTerm(l,tom_cons_list_concBQTerm(tmp,tom_empty_list_concBQTerm()));
					}
					break;
				}
				case RPAREN:
				case COMMA:
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
				_loop90:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						tmp=plainBQTerm();
						if ( inputState.guessing==0 ) {
							l = tom_append_list_concBQTerm(l,tom_cons_list_concBQTerm(tmp,tom_empty_list_concBQTerm()));
						}
					}
					else {
						break _loop90;
					}
					
				} while (true);
				}
				match(RPAREN);
				}
			}
			else if ((_tokenSet_2.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			if ( inputState.guessing==0 ) {
				
				if (args==null) {
				if (star == null) {
				result = tom_make_BQVariable(ASTFactory.makeOptionList(optionList),name,SymbolTable.TYPE_UNKNOWN);
				} else {
				result = tom_make_BQVariableStar(ASTFactory.makeOptionList(optionList),name,SymbolTable.TYPE_UNKNOWN);
				}
				} else {
				result = tom_make_BQAppl(ASTFactory.makeOptionList(optionList),name,l);
				}
				
			}
			break;
		}
		case NUM_INT:
		{
			number = LT(1);
			match(NUM_INT);
			if ( inputState.guessing==0 ) {
				
				String val = number.getText();
				ASTFactory.makeIntegerSymbol(symbolTable,val,optionList);
				result = tom_make_BuildConstant(tom_make_Name(val));
				
			}
			break;
		}
		case STRING:
		{
			string = LT(1);
			match(STRING);
			if ( inputState.guessing==0 ) {
				
				String val = string.getText();
				ASTFactory.makeStringSymbol(symbolTable,val,optionList);
				result = tom_make_BuildConstant(tom_make_Name(val));
				
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
	
	public final Option  matchPattern(
		List<TomTerm> list, boolean allowImplicit
	) throws RecognitionException, TokenStreamException, TomException {
		Option result;
		
		
		result = null;
		TomTerm term = null;
		
		
		{
		term=annotatedTerm(allowImplicit);
		if ( inputState.guessing==0 ) {
			
			list.add(term);
			result = tom_make_OriginTracking(tom_make_Name("Pattern"),lastLine,currentFile());
			
		}
		{
		_loop53:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				if ( inputState.guessing==0 ) {
					text.append('\n');
				}
				term=annotatedTerm(allowImplicit);
				if ( inputState.guessing==0 ) {
					list.add(term);
				}
			}
			else {
				break _loop53;
			}
			
		} while (true);
		}
		}
		return result;
	}
	
	public final Constraint  matchOrConstraint(
		List<Option> optionListLinked
	) throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		result = null;
		Constraint constr = null;
		
		
		result=matchAndConstraint(optionListLinked);
		{
		_loop34:
		do {
			if ((LA(1)==OR_CONNECTOR)) {
				match(OR_CONNECTOR);
				constr=matchAndConstraint(optionListLinked);
				if ( inputState.guessing==0 ) {
					
					result = tom_cons_list_OrConstraint(result,tom_cons_list_OrConstraint(constr,tom_empty_list_OrConstraint()));
					
				}
			}
			else {
				break _loop34;
			}
			
		} while (true);
		}
		return result;
	}
	
	public final void arrowAndAction(
		List<ConstraintInstruction> list, OptionList optionList, List<Option> optionListLinked, Token label, TomType rhsType, Constraint constraint
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		List<Code> blockList = new LinkedList<Code>();
		BQTerm rhsTerm = null;
		
		
		match(ARROW);
		if ( inputState.guessing==0 ) {
			
			optionList = tom_empty_list_concOption();
			for(Option op:optionListLinked) {
			optionList = tom_append_list_concOption(optionList,tom_cons_list_concOption(op,tom_empty_list_concOption()));
			}
			optionList = tom_append_list_concOption(optionList,tom_cons_list_concOption(tom_make_OriginalText(tom_make_Name(text.toString())),tom_empty_list_concOption()));
			if(label != null) {
			optionList = tom_cons_list_concOption(tom_make_Label(tom_make_Name(label.getText())),tom_append_list_concOption(optionList,tom_empty_list_concOption()));
			}
			
		}
		t = LT(1);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			// update for new target block
			updatePosition(t.getLine(),t.getColumn());
			// actions in target language : call the target lexer and
			// call the target parser
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			// target parser finished : pop the target lexer
			selector().pop();
			blockList.add(tom_make_TargetLanguageToCode(tlCode));
			list.add(tom_make_ConstraintInstruction(constraint,tom_make_RawAction(tom_make_If(tom_make_TrueTL(),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),tom_make_Nop())),optionList)
			
			
			
			);
			
		}
	}
	
	public final void visitInstruction(
		List<ConstraintInstruction> list, TomType rhsType
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  label = null;
		Token  t = null;
		
		List<Option> optionListLinked = new LinkedList<Option>();
		List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
		
		Constraint constraint = tom_make_TrueConstraint();
		Constraint constr = null;
		OptionList optionList = null;
		Option option = null;
		
		boolean isAnd = false;
		
		List<Code> blockList = new LinkedList<Code>();
		BQTerm rhsTerm = null;
		
		clearText();
		
		
		{
		{
		boolean synPredMatched25 = false;
		if (((LA(1)==ALL_ID))) {
			int _m25 = mark();
			synPredMatched25 = true;
			inputState.guessing++;
			try {
				{
				match(ALL_ID);
				match(COLON);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched25 = false;
			}
			rewind(_m25);
inputState.guessing--;
		}
		if ( synPredMatched25 ) {
			label = LT(1);
			match(ALL_ID);
			match(COLON);
		}
		else if ((_tokenSet_0.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		option=matchPattern(matchPatternList,true);
		if ( inputState.guessing==0 ) {
			
			int subjectListLength = 1;
			if(matchPatternList.size() != subjectListLength) {
			TomMessage.error(getLogger(),currentFile(), getLine(),
			TomMessage.badMatchNumberArgument,
			subjectListLength, Integer.valueOf(matchPatternList.size()));
			return;
			}
			
			BQTerm subject = tom_make_BQVariable(tom_empty_list_concOption(),tom_make_Name("tom__arg"),rhsType);
			TomType matchType = (getOptionBooleanValue("newtyper")?SymbolTable.TYPE_UNKNOWN:rhsType);
			constraint =
			tom_cons_list_AndConstraint(constraint,tom_cons_list_AndConstraint(tom_make_MatchConstraint(matchPatternList.get(0),subject,matchType),tom_empty_list_AndConstraint()));
			
			//optionList = `concOption(option, OriginalText(Name(text.toString())));
			
			matchPatternList.clear();
			clearText();
			
		}
		{
		switch ( LA(1)) {
		case AND_CONNECTOR:
		case OR_CONNECTOR:
		{
			{
			switch ( LA(1)) {
			case AND_CONNECTOR:
			{
				match(AND_CONNECTOR);
				if ( inputState.guessing==0 ) {
					isAnd = true;
				}
				break;
			}
			case OR_CONNECTOR:
			{
				match(OR_CONNECTOR);
				if ( inputState.guessing==0 ) {
					isAnd = false;
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			constr=matchOrConstraint(optionListLinked);
			if ( inputState.guessing==0 ) {
				
				constraint = isAnd ? tom_cons_list_AndConstraint(constraint,tom_cons_list_AndConstraint(constr,tom_empty_list_AndConstraint())) : tom_cons_list_OrConstraint(constraint,tom_cons_list_OrConstraint(constr,tom_empty_list_OrConstraint()));
				
			}
			break;
		}
		case ARROW:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(ARROW);
		if ( inputState.guessing==0 ) {
			
			optionList = tom_empty_list_concOption();
			for(Option op:optionListLinked) {
			optionList = tom_append_list_concOption(optionList,tom_cons_list_concOption(op,tom_empty_list_concOption()));
			}
			optionList = tom_append_list_concOption(optionList,tom_cons_list_concOption(tom_make_OriginalText(tom_make_Name(text.toString())),tom_empty_list_concOption()));
			if (label != null) {
			optionList = tom_cons_list_concOption(tom_make_Label(tom_make_Name(label.getText())),tom_append_list_concOption(optionList,tom_empty_list_concOption()));
			}
			
			
		}
		{
		switch ( LA(1)) {
		case LBRACE:
		{
			t = LT(1);
			match(LBRACE);
			if ( inputState.guessing==0 ) {
				
				// update for new target block
				updatePosition(t.getLine(),t.getColumn());
				// actions in target language : call the target lexer and
				// call the target parser
				selector().push("targetlexer");
				TargetLanguage tlCode = targetparser.targetLanguage(blockList);
				// target parser finished : pop the target lexer
				selector().pop();
				blockList.add(tom_make_TargetLanguageToCode(tlCode));
				list.add(tom_make_ConstraintInstruction(constraint,tom_make_RawAction(tom_make_If(tom_make_TrueTL(),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),tom_make_Nop())),optionList)
				
				
				
				);
				
			}
			break;
		}
		case ALL_ID:
		case NUM_INT:
		case STRING:
		{
			rhsTerm=plainBQTerm();
			if ( inputState.guessing==0 ) {
				
				// case where the rhs of a rule is an algebraic term
				list.add(tom_make_ConstraintInstruction(constraint,tom_make_Return(rhsTerm),optionList)
				
				
				
				);
				
				
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
	}
	
	public final Constraint  matchAndConstraint(
		List<Option> optionListLinked
	) throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		result = null;
		Constraint constr = null;
		
		
		result=matchParanthesedConstraint(optionListLinked);
		{
		_loop37:
		do {
			if ((LA(1)==AND_CONNECTOR)) {
				match(AND_CONNECTOR);
				constr=matchParanthesedConstraint(optionListLinked);
				if ( inputState.guessing==0 ) {
					
					result = tom_cons_list_AndConstraint(result,tom_cons_list_AndConstraint(constr,tom_empty_list_AndConstraint()));
					
				}
			}
			else {
				break _loop37;
			}
			
		} while (true);
		}
		return result;
	}
	
	public final Constraint  matchParanthesedConstraint(
		List<Option> optionListLinked
	) throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		result = null;
		List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
		
		
		boolean synPredMatched40 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m40 = mark();
			synPredMatched40 = true;
			inputState.guessing++;
			try {
				{
				matchPattern(matchPatternList,true);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched40 = false;
			}
			rewind(_m40);
inputState.guessing--;
		}
		if ( synPredMatched40 ) {
			result=constraint(optionListLinked);
		}
		else if ((LA(1)==LPAREN)) {
			match(LPAREN);
			result=matchOrConstraint(optionListLinked);
			match(RPAREN);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		return result;
	}
	
	public final Constraint  constraint(
		List<Option> optionListLinked
	) throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		boolean synPredMatched43 = false;
		if (((_tokenSet_0.member(LA(1))))) {
			int _m43 = mark();
			synPredMatched43 = true;
			inputState.guessing++;
			try {
				{
				matchPattern(null,true);
				match(MATCH_CONSTRAINT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched43 = false;
			}
			rewind(_m43);
inputState.guessing--;
		}
		if ( synPredMatched43 ) {
			result=matchConstraint(optionListLinked);
		}
		else if ((_tokenSet_3.member(LA(1)))) {
			result=numericConstraint();
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		return result;
	}
	
	public final Constraint  matchConstraint(
		List<Option> optionListLinked
	) throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
		List<BQTerm> matchSubjectList = new LinkedList<BQTerm>();
		List<TomType> matchSubjectTypeList = new LinkedList<TomType>();
		Option option = null;
		result = null;
		int consType = -1;
		
		
		option=matchPattern(matchPatternList,true);
		match(MATCH_CONSTRAINT);
		matchArgument(matchSubjectList,matchSubjectTypeList);
		if ( inputState.guessing==0 ) {
			
			optionListLinked.add(option);
			TomTerm left  = matchPatternList.get(0);
			BQTerm right = matchSubjectList.get(0);
			TomType type = matchSubjectTypeList.get(0);
			return tom_make_MatchConstraint(left,right,type);
			
		}
		return result;
	}
	
	public final Constraint  numericConstraint() throws RecognitionException, TokenStreamException, TomException {
		Constraint result;
		
		
		//TODO could be simplified without using matchArgument rule
		// we are sure to have only one argument in each case
		List<BQTerm> matchLhsList = new LinkedList<BQTerm>();
		List<BQTerm> matchRhsList = new LinkedList<BQTerm>();
		result = null;
		int consType = -1;
		BQTerm subject1 = null;
		BQTerm subject2 = null;
		
		
		{
		switch ( LA(1)) {
		case BACKQUOTE:
		{
			match(BACKQUOTE);
			if ( inputState.guessing==0 ) {
				text.delete(0, text.length());
			}
			break;
		}
		case ALL_ID:
		case NUM_INT:
		case STRING:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		subject1=plainBQTerm();
		if ( inputState.guessing==0 ) {
			
			text.delete(0, text.length());
			matchLhsList.add(subject1);
			
		}
		consType=numconstraintType();
		{
		switch ( LA(1)) {
		case BACKQUOTE:
		{
			match(BACKQUOTE);
			if ( inputState.guessing==0 ) {
				text.delete(0, text.length());
			}
			break;
		}
		case ALL_ID:
		case NUM_INT:
		case STRING:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		subject2=plainBQTerm();
		if ( inputState.guessing==0 ) {
			
			text.delete(0, text.length());
			matchRhsList.add(subject2);
			
		}
		if ( inputState.guessing==0 ) {
			
			BQTerm left  = matchLhsList.get(0);
			BQTerm right = matchRhsList.get(0);
			
			switch(consType) {
			case XML_START : {
			return tom_make_NumericConstraint(left,right,tom_make_NumLessThan());
			}
			case LESSOREQUAL_CONSTRAINT : {
			return tom_make_NumericConstraint(left,right,tom_make_NumLessOrEqualThan());
			}
			case XML_CLOSE : {
			return tom_make_NumericConstraint(left,right,tom_make_NumGreaterThan());
			}
			case GREATEROREQUAL_CONSTRAINT : {
			return tom_make_NumericConstraint(left,right,tom_make_NumGreaterOrEqualThan());
			}
			case DIFFERENT_CONSTRAINT : {
			return tom_make_NumericConstraint(left,right,tom_make_NumDifferent());
			}
			case DOUBLEEQ : {
			return tom_make_NumericConstraint(left,right,tom_make_NumEqual());
			}
			}
			// should never reach this statement because of the parsing error that should occur before
			throw new TomException(TomMessage.invalidConstraintType);
			
		}
		return result;
	}
	
	public final int  numconstraintType() throws RecognitionException, TokenStreamException {
		int result;
		
		
		result = -1;
		
		
		{
		switch ( LA(1)) {
		case XML_START:
		{
			match(XML_START);
			if ( inputState.guessing==0 ) {
				result = XML_START;
			}
			break;
		}
		case LESSOREQUAL_CONSTRAINT:
		{
			match(LESSOREQUAL_CONSTRAINT);
			if ( inputState.guessing==0 ) {
				result = LESSOREQUAL_CONSTRAINT;
			}
			break;
		}
		case XML_CLOSE:
		{
			match(XML_CLOSE);
			if ( inputState.guessing==0 ) {
				result = XML_CLOSE;
			}
			break;
		}
		case GREATEROREQUAL_CONSTRAINT:
		{
			match(GREATEROREQUAL_CONSTRAINT);
			if ( inputState.guessing==0 ) {
				result = GREATEROREQUAL_CONSTRAINT;
			}
			break;
		}
		case DOUBLEEQ:
		{
			match(DOUBLEEQ);
			if ( inputState.guessing==0 ) {
				result = DOUBLEEQ;
			}
			break;
		}
		case DIFFERENT_CONSTRAINT:
		{
			match(DIFFERENT_CONSTRAINT);
			if ( inputState.guessing==0 ) {
				result = DIFFERENT_CONSTRAINT;
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
	
	public final TomTerm  annotatedTerm(
		boolean allowImplicit
	) throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  lname = null;
		Token  name = null;
		
		result = null;
		TomName labeledName = null;
		TomName annotatedName = null;
		int line = 0;
		boolean anti = false;
		
		
		{
		{
		boolean synPredMatched74 = false;
		if (((LA(1)==ALL_ID))) {
			int _m74 = mark();
			synPredMatched74 = true;
			inputState.guessing++;
			try {
				{
				match(ALL_ID);
				match(COLON);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched74 = false;
			}
			rewind(_m74);
inputState.guessing--;
		}
		if ( synPredMatched74 ) {
			lname = LT(1);
			match(ALL_ID);
			match(COLON);
			if ( inputState.guessing==0 ) {
				
				text.append(lname.getText());
				text.append(':');
				labeledName = tom_make_Name(lname.getText());
				line = lname.getLine();
				
			}
		}
		else if ((_tokenSet_0.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		{
		boolean synPredMatched77 = false;
		if (((LA(1)==ALL_ID))) {
			int _m77 = mark();
			synPredMatched77 = true;
			inputState.guessing++;
			try {
				{
				match(ALL_ID);
				match(AT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched77 = false;
			}
			rewind(_m77);
inputState.guessing--;
		}
		if ( synPredMatched77 ) {
			name = LT(1);
			match(ALL_ID);
			match(AT);
			if ( inputState.guessing==0 ) {
				
				text.append(name.getText());
				text.append('@');
				annotatedName = tom_make_Name(name.getText());
				line = name.getLine();
				
			}
		}
		else if ((_tokenSet_0.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		result=plainTerm(labeledName,annotatedName,line);
		}
		return result;
	}
	
	public final Declaration  strategyConstruct(
		Option orgTrack
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  name = null;
		Token  firstSlot1 = null;
		Token  colon = null;
		Token  secondSlot1 = null;
		Token  firstSlot2 = null;
		Token  colon2 = null;
		Token  secondSlot2 = null;
		Token  t = null;
		
		result = null;
		BQTerm extendsBQTerm = null;
		List<TomVisit> visitList = new LinkedList<TomVisit>();
		TomVisitList astVisitList = tom_empty_list_concTomVisit();
		TomName orgText = null;
		TomTypeList types = tom_empty_list_concTomType();
		List<Option> options = new LinkedList<Option>();
		List<TomName> slotNameList = new LinkedList<TomName>();
		List<PairNameDecl> pairNameDeclList = new LinkedList<PairNameDecl>();
		String stringSlotName = null;
		String stringTypeArg = null;
		
		clearText();
		
		
		{
		name = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
			Option ot = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			options.add(ot);
			if(symbolTable.getSymbolFromName(name.getText()) != null) {
			throw new TomException(TomMessage.invalidStrategyName, new Object[]{name.getText()});
			}
			
		}
		{
		match(LPAREN);
		{
		switch ( LA(1)) {
		case ALL_ID:
		{
			firstSlot1 = LT(1);
			match(ALL_ID);
			{
			switch ( LA(1)) {
			case COLON:
			{
				colon = LT(1);
				match(COLON);
				break;
			}
			case ALL_ID:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			secondSlot1 = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				if(colon != null) {
				stringSlotName = firstSlot1.getText();
				stringTypeArg = secondSlot1.getText();
				} else {
				stringSlotName = secondSlot1.getText();
				stringTypeArg = firstSlot1.getText();
				}
				TomName astName = tom_make_Name(stringSlotName);
				slotNameList.add(astName);
				
				TomType strategyType = tom_make_Type(tom_empty_list_concTypeOption(),"Strategy",tom_make_EmptyTargetLanguageType());
				
				// Define get<slot> method.
				Option slotOption = tom_make_OriginTracking(tom_make_Name(stringSlotName),firstSlot1.getLine(),currentFile());
				String varname = "t";
				BQTerm slotVar = tom_make_BQVariable(tom_cons_list_concOption(slotOption,tom_empty_list_concOption()),tom_make_Name(varname),strategyType);
				String code = ASTFactory.abstractCode("((" + name.getText() + ")$"+varname+").get" + stringSlotName + "()",varname);
				Declaration slotDecl = tom_make_GetSlotDecl(tom_make_Name(name.getText()),tom_make_Name(stringSlotName),slotVar,tom_make_Code(code),slotOption);
				
				pairNameDeclList.add(tom_make_PairNameDecl(astName,slotDecl));
				types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),stringTypeArg,tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
				
			}
			{
			_loop61:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					firstSlot2 = LT(1);
					match(ALL_ID);
					{
					switch ( LA(1)) {
					case COLON:
					{
						colon2 = LT(1);
						match(COLON);
						break;
					}
					case ALL_ID:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					secondSlot2 = LT(1);
					match(ALL_ID);
					if ( inputState.guessing==0 ) {
						
						if(colon != null) {
						stringSlotName = firstSlot2.getText();
						stringTypeArg = secondSlot2.getText();
						} else {
						stringSlotName = secondSlot2.getText();
						stringTypeArg = firstSlot2.getText();
						}
						TomName astName = ASTFactory.makeName(stringSlotName);
						if(slotNameList.indexOf(astName) != -1) {
						TomMessage.error(getLogger(),currentFile(), getLine(),
						TomMessage.repeatedSlotName,
						stringSlotName);
						}
						slotNameList.add(astName);
						
						TomType strategyType = tom_make_Type(tom_empty_list_concTypeOption(),"Strategy",tom_make_EmptyTargetLanguageType());
						// Define get<slot> method.
						Option slotOption = tom_make_OriginTracking(tom_make_Name(stringSlotName),firstSlot2.getLine(),currentFile());
						String varname = "t";
						BQTerm slotVar = tom_make_BQVariable(tom_cons_list_concOption(slotOption,tom_empty_list_concOption()),tom_make_Name(varname),strategyType);
						String code = ASTFactory.abstractCode("((" + name.getText() + ")$"+varname+").get" + stringSlotName + "()",varname);
						Declaration slotDecl = tom_make_GetSlotDecl(tom_make_Name(name.getText()),tom_make_Name(stringSlotName),slotVar,tom_make_Code(code),slotOption);
						
						pairNameDeclList.add(tom_make_PairNameDecl(tom_make_Name(stringSlotName),slotDecl));
						types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),stringTypeArg,tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
						
					}
				}
				else {
					break _loop61;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		}
		match(EXTENDS);
		{
		switch ( LA(1)) {
		case BACKQUOTE:
		{
			match(BACKQUOTE);
			break;
		}
		case ALL_ID:
		case NUM_INT:
		case STRING:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		extendsBQTerm=plainBQTerm();
		match(LBRACE);
		strategyVisitList(visitList);
		if ( inputState.guessing==0 ) {
			astVisitList = ASTFactory.makeTomVisitList(visitList);
		}
		t = LT(1);
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			
			//initialize arrayList with argument names
							 BQTermList makeArgs = tom_empty_list_concBQTerm();
			int index = 0;
			TomTypeList makeTypes = types;//keep a copy of types
							 String makeTlCode = "new " + name.getText() + "(";
			while(!makeTypes.isEmptyconcTomType()) {
								 String argName = "t"+index;
			if (index>0) {//if many parameters
			makeTlCode = makeTlCode.concat(",");
			}
								 makeTlCode += argName;
			
			BQTerm arg = tom_make_BQVariable(tom_empty_list_concOption(),tom_make_Name(argName),makeTypes.getHeadconcTomType());
			makeArgs = tom_append_list_concBQTerm(makeArgs,tom_cons_list_concBQTerm(arg,tom_empty_list_concBQTerm()));
			
								 makeTypes = makeTypes.getTailconcTomType();
			index++;
			}
							 makeTlCode += ")";
			
			TomType strategyType = tom_make_Type(tom_empty_list_concTypeOption(),"Strategy",tom_make_EmptyTargetLanguageType());
							 Option makeOption = tom_make_OriginTracking(tom_make_Name(name.getText()),t.getLine(),currentFile());
							 Declaration makeDecl = tom_make_MakeDecl(tom_make_Name(name.getText()),strategyType,makeArgs,tom_make_CodeToInstruction(tom_make_TargetLanguageToCode(tom_make_ITL(makeTlCode))),makeOption);
			options.add(tom_make_DeclarationToOption(makeDecl));
			
			// Define the is_fsym method.
			Option fsymOption = tom_make_OriginTracking(tom_make_Name(name.getText()),t.getLine(),currentFile());
			String varname = "t";
			BQTerm fsymVar = tom_make_BQVariable(tom_cons_list_concOption(fsymOption,tom_empty_list_concOption()),tom_make_Name(varname),strategyType);
			String code = ASTFactory.abstractCode("($"+varname+" instanceof " + name.getText() + ")",varname);
			Declaration fsymDecl = tom_make_IsFsymDecl(tom_make_Name(name.getText()),fsymVar,tom_make_Code(code),fsymOption);
			options.add(tom_make_DeclarationToOption(fsymDecl));
			
			TomSymbol astSymbol = ASTFactory.makeSymbol(name.getText(), strategyType, types, ASTFactory.makePairNameDeclList(pairNameDeclList), options);
			putSymbol(name.getText(),astSymbol);
			// update for new target block...
			updatePosition(t.getLine(),t.getColumn());
			
			result = tom_make_AbstractDecl(tom_cons_list_concDeclaration(tom_make_Strategy(tom_make_Name(name.getText()),extendsBQTerm,astVisitList,orgTrack),tom_cons_list_concDeclaration(tom_make_SymbolDecl(tom_make_Name(name.getText())),tom_empty_list_concDeclaration())));
			
			// %strat finished: go back in target parser.
			selector().pop();
			
		}
		}
		return result;
	}
	
	public final void strategyVisitList(
		List<TomVisit> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		{
		_loop65:
		do {
			if ((LA(1)==LITERAL_visit)) {
				strategyVisit(list);
			}
			else {
				break _loop65;
			}
			
		} while (true);
		}
	}
	
	public final void strategyVisit(
		List<TomVisit> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  type = null;
		
		List<ConstraintInstruction> constraintInstructionList = new LinkedList<ConstraintInstruction>();
		TomType vType = null;
		clearText();
		
		
		{
		match(LITERAL_visit);
		type = LT(1);
		match(ALL_ID);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			vType = tom_make_Type(tom_empty_list_concTypeOption(),type.getText(),tom_make_EmptyTargetLanguageType());
		}
		{
		_loop69:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				visitInstruction(constraintInstructionList,vType);
			}
			else {
				break _loop69;
			}
			
		} while (true);
		}
		match(RBRACE);
		}
		if ( inputState.guessing==0 ) {
			
			List<Option> optionList = new LinkedList<Option>();
			optionList.add(tom_make_OriginTracking(tom_make_Name(type.getText()),type.getLine(),currentFile()));
			OptionList options = ASTFactory.makeOptionList(optionList);
			list.add(tom_make_VisitTerm(vType,ASTFactory.makeConstraintInstructionList(constraintInstructionList),options));
			
		}
	}
	
	public final TomTerm  plainTerm(
		TomName astLabeledName, TomName astAnnotedName, int line
	) throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  a = null;
		Token  st = null;
		Token  qm = null;
		
		List list = new LinkedList();
		List<Option> secondOptionList = new LinkedList<Option>();
		List<Option> optionList = new LinkedList<Option>();
		List<Constraint> constraintList = new LinkedList<Constraint>();
		result = null;
		TomNameList nameList = tom_empty_list_concTomName();
		TomName name = null;
		boolean implicit = false;
		boolean anti = false;
		
		if(astLabeledName != null) {
		constraintList.add(ASTFactory.makeStorePosition(astLabeledName, line, currentFile()));
		}
		if(astAnnotedName != null) {
		constraintList.add(ASTFactory.makeAliasTo(astAnnotedName, line, currentFile()));
		}
		
		
		{
		_loop80:
		do {
			if ((LA(1)==ANTI_SYM)) {
				a = LT(1);
				match(ANTI_SYM);
				if ( inputState.guessing==0 ) {
					anti = !anti;
				}
			}
			else {
				break _loop80;
			}
			
		} while (true);
		}
		{
		if ((_tokenSet_4.member(LA(1)))) {
			result=xmlTerm(optionList, constraintList);
			if ( inputState.guessing==0 ) {
				
				if(anti) { result = tom_make_AntiTerm(result); }
				
			}
		}
		else {
			boolean synPredMatched83 = false;
			if ((((LA(1)==ALL_ID||LA(1)==UNDERSCORE))&&(!anti))) {
				int _m83 = mark();
				synPredMatched83 = true;
				inputState.guessing++;
				try {
					{
					variableStar(null,null);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched83 = false;
				}
				rewind(_m83);
inputState.guessing--;
			}
			if ( synPredMatched83 ) {
				result=variableStar(optionList,constraintList);
			}
			else if (((LA(1)==UNDERSCORE))&&(!anti)) {
				result=unamedVariable(optionList,constraintList);
			}
			else if (((LA(1)==ALL_ID))&&(LA(2) != LPAREN && LA(2) != LBRACKET && LA(2) != QMARK && LA(2) != QQMARK)) {
				name=headSymbol(optionList);
				if ( inputState.guessing==0 ) {
					
					result = tom_make_Variable(ASTFactory.makeOptionList(optionList),name,SymbolTable.TYPE_UNKNOWN,ASTFactory.makeConstraintList(constraintList))
					;
					if(anti) { result = tom_make_AntiTerm(result); }
					
				}
			}
			else if (((_tokenSet_5.member(LA(1))))&&(LA(2) != LPAREN && LA(2) != LBRACKET && LA(2) != QMARK && LA(2) != QQMARK)) {
				nameList=headConstantList(optionList);
				if ( inputState.guessing==0 ) {
					
					optionList.add(tom_make_Constant());
					result = tom_make_TermAppl(ASTFactory.makeOptionList(optionList),nameList,ASTFactory.makeTomList((List<TomTerm>)list),ASTFactory.makeConstraintList(constraintList))
					
					
					
					;
					if(anti) { result = tom_make_AntiTerm(result); }
					
				}
			}
			else if ((LA(1)==ALL_ID)) {
				name=headSymbol(optionList);
				{
				switch ( LA(1)) {
				case QQMARK:
				{
					st = LT(1);
					match(QQMARK);
					if ( inputState.guessing==0 ) {
						
						if(st!=null) {
						optionList.add(tom_make_MatchingTheory(tom_cons_list_concElementaryTheory(tom_make_AC(),tom_empty_list_concElementaryTheory())));
						}
						
					}
					break;
				}
				case QMARK:
				{
					qm = LT(1);
					match(QMARK);
					if ( inputState.guessing==0 ) {
						
						if(qm!=null) {
						optionList.add(tom_make_MatchingTheory(tom_cons_list_concElementaryTheory(tom_make_AU(),tom_empty_list_concElementaryTheory())));
						}
						
					}
					break;
				}
				case LPAREN:
				case LBRACKET:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				implicit=args(list,secondOptionList);
				if ( inputState.guessing==0 ) {
					
					nameList = tom_append_list_concTomName(nameList,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
					if(implicit) {
					result = tom_make_RecordAppl(ASTFactory.makeOptionList(optionList),nameList,ASTFactory.makeSlotList((List<Slot>)list),ASTFactory.makeConstraintList(constraintList)
					)
					
					
					
					
					;
					} else {
					result = tom_make_TermAppl(ASTFactory.makeOptionList(optionList),nameList,ASTFactory.makeTomList((List<TomTerm>)list),ASTFactory.makeConstraintList(constraintList)
					)
					
					
					
					
					;
					}
					if(anti) { result = tom_make_AntiTerm(result); }
					
				}
			}
			else if (((LA(1)==LPAREN))&&(LA(3) == ALTERNATIVE || LA(4) == ALTERNATIVE)) {
				nameList=headSymbolList(optionList);
				implicit=args(list, secondOptionList);
				if ( inputState.guessing==0 ) {
					
					if(implicit) {
					result = tom_make_RecordAppl(ASTFactory.makeOptionList(optionList),nameList,ASTFactory.makeSlotList((List<Slot>)list),ASTFactory.makeConstraintList(constraintList)
					)
					
					
					
					
					;
					} else {
					result = tom_make_TermAppl(ASTFactory.makeOptionList(optionList),nameList,ASTFactory.makeTomList((List<TomTerm>)list),ASTFactory.makeConstraintList(constraintList)
					)
					
					
					
					
					;
					}
					if(anti) { result = tom_make_AntiTerm(result); }
					
				}
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			return result;
		}
		
	public final TomTerm  xmlTerm(
		List<Option> optionList, List<Constraint> constraintList
	) throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  t = null;
		
		result = null;
		TomTerm arg1, arg2;
		List<Slot> pairSlotList = new LinkedList<Slot>();
		List attributeList = new LinkedList();
		List childs = new LinkedList();
		String keyword = null;
		boolean implicit;
		TomNameList nameList, closingNameList;
		OptionList option = null;
		ConstraintList constraint;
		
		
		{
		switch ( LA(1)) {
		case XML_START:
		{
			match(XML_START);
			if ( inputState.guessing==0 ) {
				text.append("<");
			}
			nameList=xmlNameList(optionList, true);
			implicit=xmlAttributeList(attributeList);
			if ( inputState.guessing==0 ) {
				
				if(implicit) { optionList.add(tom_make_ImplicitXMLAttribut()); }
				
			}
			{
			switch ( LA(1)) {
			case XML_CLOSE_SINGLETON:
			{
				match(XML_CLOSE_SINGLETON);
				if ( inputState.guessing==0 ) {
					
					text.append("\\>");
					option =  ASTFactory.makeOptionList(optionList);
					
				}
				break;
			}
			case XML_CLOSE:
			{
				match(XML_CLOSE);
				if ( inputState.guessing==0 ) {
					text.append(">");
				}
				implicit=xmlChilds(childs);
				match(XML_START_ENDING);
				if ( inputState.guessing==0 ) {
					text.append("</");
				}
				closingNameList=xmlNameList(optionList, false);
				t = LT(1);
				match(XML_CLOSE);
				if ( inputState.guessing==0 ) {
					text.append(">");
				}
				if ( inputState.guessing==0 ) {
					
					if(!nameList.equals(closingNameList)) {
					StringBuilder found = new StringBuilder();
					StringBuilder expected = new StringBuilder();
					while(!nameList.isEmptyconcTomName()) {
					expected.append("|"+nameList.getHeadconcTomName().getString());
					nameList = nameList.getTailconcTomName();
					}
					while(!closingNameList.isEmptyconcTomName()) {
					found.append("|"+closingNameList.getHeadconcTomName().getString());
					closingNameList = closingNameList.getTailconcTomName();
					}
					// TODO find the orgTrack of the match
					throw new TomException(TomMessage.malformedXMLTerm,
					new Object[]{currentFile(), Integer.valueOf(getLine()),
					"match", expected.substring(1), found.substring(1)});
					}
					if(implicit) {
					//System.out.println("implicit");
					//System.out.println("childs = " + childs);
					optionList.add(tom_make_ImplicitXMLChild());
					} else {
					//System.out.println("explicit");
					//System.out.println("childs1 = " + childs);
					childs = ASTFactory.metaEncodeExplicitTermList(symbolTable, childs);
					//System.out.println("childs2 = " + childs);
					}
					option = ASTFactory.makeOptionList(optionList);
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				
				result = tom_make_XMLAppl(option,nameList,ASTFactory.makeTomList((List<TomTerm>)attributeList),ASTFactory.makeTomList((List<TomTerm>)childs),ASTFactory.makeConstraintList(constraintList))
				
				
				
				
				;
				
			}
			break;
		}
		case XML_TEXT:
		{
			match(XML_TEXT);
			match(LPAREN);
			arg1=annotatedTerm(true);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				keyword = Constants.TEXT_NODE;
				pairSlotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_DATA),arg1));
				
				optionList.add(tom_make_OriginTracking(tom_make_Name(keyword),getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				constraint = ASTFactory.makeConstraintList(constraintList);
				nameList = tom_cons_list_concTomName(tom_make_Name(keyword),tom_empty_list_concTomName());
				result = tom_make_RecordAppl(option,nameList,ASTFactory.makeSlotList(pairSlotList),constraint)
				
				
				;
				
			}
			break;
		}
		case XML_COMMENT:
		{
			match(XML_COMMENT);
			match(LPAREN);
			arg1=termStringIdentifier(null);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				keyword = Constants.COMMENT_NODE;
				pairSlotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_DATA),arg1));
				
				optionList.add(tom_make_OriginTracking(tom_make_Name(keyword),getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				constraint = ASTFactory.makeConstraintList(constraintList);
				nameList = tom_cons_list_concTomName(tom_make_Name(keyword),tom_empty_list_concTomName());
				result = tom_make_RecordAppl(option,nameList,ASTFactory.makeSlotList(pairSlotList),constraint)
				
				
				;
				
			}
			break;
		}
		case XML_PROC:
		{
			match(XML_PROC);
			match(LPAREN);
			arg1=termStringIdentifier(null);
			match(COMMA);
			arg2=termStringIdentifier(null);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				keyword = Constants.PROCESSING_INSTRUCTION_NODE;
				pairSlotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_TARGET),arg1));
				pairSlotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_DATA),arg2));
				
				optionList.add(tom_make_OriginTracking(tom_make_Name(keyword),getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				constraint = ASTFactory.makeConstraintList(constraintList);
				nameList = tom_cons_list_concTomName(tom_make_Name(keyword),tom_empty_list_concTomName());
				result = tom_make_RecordAppl(option,nameList,ASTFactory.makeSlotList(pairSlotList),constraint)
				
				
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
	
	public final TomTerm  variableStar(
		List<Option> optionList, List<Constraint> constraintList
	) throws RecognitionException, TokenStreamException {
		TomTerm result;
		
		Token  name1 = null;
		Token  name2 = null;
		Token  t = null;
		
		result = null;
		String name = null;
		int line = 0;
		OptionList options = null;
		ConstraintList constraints = null;
		
		
		{
		{
		switch ( LA(1)) {
		case ALL_ID:
		{
			name1 = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				name = name1.getText();
				line = name1.getLine();
				
			}
			break;
		}
		case UNDERSCORE:
		{
			name2 = LT(1);
			match(UNDERSCORE);
			if ( inputState.guessing==0 ) {
				
				name = name2.getText();
				line = name2.getLine();
				
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		t = LT(1);
		match(STAR);
		if ( inputState.guessing==0 ) {
			
			text.append(name);
			text.append(t.getText());
			
			// setting line number for origin tracking
			// in %rule construct
			setLastLine(t.getLine());
			
			optionList.add(tom_make_OriginTracking(tom_make_Name(name),line,currentFile()));
			options = ASTFactory.makeOptionList(optionList);
			constraints = ASTFactory.makeConstraintList(constraintList);
			if(name1 == null) {
			result = tom_make_VariableStar(options,tom_make_EmptyName(),SymbolTable.TYPE_UNKNOWN,constraints)
			
			
			
			
			;
			} else {
			result = tom_make_VariableStar(options,tom_make_Name(name),SymbolTable.TYPE_UNKNOWN,constraints)
			
			
			
			
			;
			}
			
		}
		}
		return result;
	}
	
	public final TomTerm  unamedVariable(
		List<Option> optionList, List<Constraint> constraintList
	) throws RecognitionException, TokenStreamException {
		TomTerm result;
		
		Token  t = null;
		
		result = null;
		OptionList options = null;
		ConstraintList constraints = null;
		
		
		{
		t = LT(1);
		match(UNDERSCORE);
		if ( inputState.guessing==0 ) {
			
			text.append(t.getText());
			setLastLine(t.getLine());
			
			optionList.add(tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile()));
			options = ASTFactory.makeOptionList(optionList);
			constraints = ASTFactory.makeConstraintList(constraintList);
			result = tom_make_Variable(options,tom_make_EmptyName(),SymbolTable.TYPE_UNKNOWN,constraints);
			
		}
		}
		return result;
	}
	
	public final TomName  headSymbol(
		List<Option> optionList
	) throws RecognitionException, TokenStreamException {
		TomName result;
		
		Token  i = null;
		
		result = null;
		
		
		{
		i = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
			String name = i.getText();
					int line = i.getLine();
					text.append(name);
					setLastLine(line);
					result = tom_make_Name(name);
					optionList.add(tom_make_OriginTracking(result,line,currentFile()));
				
		}
		}
		return result;
	}
	
	public final TomNameList  headConstantList(
		List<Option> optionList
	) throws RecognitionException, TokenStreamException {
		TomNameList result;
		
		
		result = tom_empty_list_concTomName();
		TomName name = null;
		
		
		name=headConstant(optionList);
		if ( inputState.guessing==0 ) {
			result = tom_append_list_concTomName(result,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
		}
		{
		_loop163:
		do {
			if ((LA(1)==ALTERNATIVE)) {
				match(ALTERNATIVE);
				if ( inputState.guessing==0 ) {
					text.append('|');
				}
				name=headConstant(optionList);
				if ( inputState.guessing==0 ) {
					result = tom_append_list_concTomName(result,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
				}
			}
			else {
				break _loop163;
			}
			
		} while (true);
		}
		return result;
	}
	
	public final boolean  args(
		List list, List<Option> optionList
	) throws RecognitionException, TokenStreamException, TomException {
		boolean result;
		
		Token  t1 = null;
		Token  t2 = null;
		Token  t3 = null;
		Token  t4 = null;
		
		result = false;
		
		
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			t1 = LT(1);
			match(LPAREN);
			if ( inputState.guessing==0 ) {
				text.append('(');
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			case ALL_ID:
			case XML_START:
			case ANTI_SYM:
			case NUM_INT:
			case STRING:
			case XML_TEXT:
			case XML_COMMENT:
			case XML_PROC:
			case UNDERSCORE:
			case CHARACTER:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				termList(list);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			t2 = LT(1);
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				// setting line number for origin tracking
				// in %rule construct
				setLastLine(t2.getLine());
				text.append(t2.getText());
				result = false;
				optionList.add(tom_make_OriginTracking(tom_make_Name(""),t1.getLine(),currentFile()));
				
			}
			break;
		}
		case LBRACKET:
		{
			t3 = LT(1);
			match(LBRACKET);
			if ( inputState.guessing==0 ) {
				text.append('[');
			}
			{
			switch ( LA(1)) {
			case ALL_ID:
			{
				pairList(list);
				break;
			}
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			t4 = LT(1);
			match(RBRACKET);
			if ( inputState.guessing==0 ) {
				
				// setting line number for origin tracking
				// in %rule construct
				setLastLine(t4.getLine());
				text.append(t4.getText());
				result = true;
				optionList.add(tom_make_OriginTracking(tom_make_Name(""),t3.getLine(),currentFile()));
				
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
	
	public final TomNameList  headSymbolList(
		List<Option> optionList
	) throws RecognitionException, TokenStreamException {
		TomNameList result;
		
		Token  t = null;
		
		result = tom_empty_list_concTomName();
		TomName name = null;
		
		
		{
		match(LPAREN);
		if ( inputState.guessing==0 ) {
			text.append('(');
		}
		name=headSymbolOrConstant(optionList);
		if ( inputState.guessing==0 ) {
			
			result = tom_append_list_concTomName(result,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
			
		}
		match(ALTERNATIVE);
		if ( inputState.guessing==0 ) {
			text.append('|');
		}
		name=headSymbolOrConstant(optionList);
		if ( inputState.guessing==0 ) {
			
			result = tom_append_list_concTomName(result,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
			
		}
		{
		_loop156:
		do {
			if ((LA(1)==ALTERNATIVE)) {
				match(ALTERNATIVE);
				if ( inputState.guessing==0 ) {
					text.append('|');
				}
				name=headSymbolOrConstant(optionList);
				if ( inputState.guessing==0 ) {
					
					result = tom_append_list_concTomName(result,tom_cons_list_concTomName(name,tom_empty_list_concTomName()));
					
				}
			}
			else {
				break _loop156;
			}
			
		} while (true);
		}
		t = LT(1);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			text.append(t.getText());
			setLastLine(t.getLine());
			
		}
		}
		return result;
	}
	
	public final TomNameList  xmlNameList(
		List<Option> optionList, boolean needOrgTrack
	) throws RecognitionException, TokenStreamException, TomException {
		TomNameList result;
		
		Token  a = null;
		Token  name = null;
		Token  name2 = null;
		Token  b = null;
		Token  name3 = null;
		Token  c = null;
		Token  name4 = null;
		
		result = tom_empty_list_concTomName();
		StringBuilder xmlName = new StringBuilder();
		int decLine = 0;
		boolean anti = false;
		
		
		{
		switch ( LA(1)) {
		case ALL_ID:
		case ANTI_SYM:
		{
			{
			_loop116:
			do {
				if ((LA(1)==ANTI_SYM)) {
					a = LT(1);
					match(ANTI_SYM);
					if ( inputState.guessing==0 ) {
						anti = !anti;
					}
				}
				else {
					break _loop116;
				}
				
			} while (true);
			}
			name = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				text.append(name.getText());
				xmlName.append(name.getText());
				decLine = name.getLine();
				if (anti) {
				result =  tom_cons_list_concTomName(tom_make_AntiName(tom_make_Name(name.getText())),tom_empty_list_concTomName());
				} else {
				result = tom_cons_list_concTomName(tom_make_Name(name.getText()),tom_empty_list_concTomName());
				}
				
			}
			break;
		}
		case UNDERSCORE:
		{
			name2 = LT(1);
			match(UNDERSCORE);
			if ( inputState.guessing==0 ) {
				
				text.append(name2.getText());
				xmlName.append(name2.getText());
				decLine = name2.getLine();
				result = tom_cons_list_concTomName(tom_make_Name(name2.getText()),tom_empty_list_concTomName());
				
			}
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			{
			_loop118:
			do {
				if ((LA(1)==ANTI_SYM)) {
					b = LT(1);
					match(ANTI_SYM);
					if ( inputState.guessing==0 ) {
						anti = !anti;
					}
				}
				else {
					break _loop118;
				}
				
			} while (true);
			}
			name3 = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				text.append(name3.getText());
				xmlName.append(name3.getText());
				decLine = name3.getLine();
				if (anti) {
				result =  tom_cons_list_concTomName(tom_make_AntiName(tom_make_Name(name3.getText())),tom_empty_list_concTomName());
				} else {
				result = tom_cons_list_concTomName(tom_make_Name(name3.getText()),tom_empty_list_concTomName());
				}
				
				
			}
			{
			_loop122:
			do {
				if ((LA(1)==ALTERNATIVE)) {
					match(ALTERNATIVE);
					{
					_loop121:
					do {
						if ((LA(1)==ANTI_SYM)) {
							c = LT(1);
							match(ANTI_SYM);
							if ( inputState.guessing==0 ) {
								anti = !anti;
							}
						}
						else {
							break _loop121;
						}
						
					} while (true);
					}
					name4 = LT(1);
					match(ALL_ID);
					if ( inputState.guessing==0 ) {
						
						text.append("|"+name4.getText());
						xmlName.append("|"+name4.getText());
						if (anti) {
						result = tom_append_list_concTomName(result,tom_cons_list_concTomName(tom_make_AntiName(tom_make_Name(name4.getText())),tom_empty_list_concTomName()));
						} else {
						result = tom_append_list_concTomName(result,tom_cons_list_concTomName(tom_make_Name(name4.getText()),tom_empty_list_concTomName()));
						}
						
					}
				}
				else {
					break _loop122;
				}
				
			} while (true);
			}
			match(RPAREN);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			
			if(needOrgTrack) {
			optionList.add(tom_make_OriginTracking(tom_make_Name(xmlName.toString()),decLine,currentFile()));
			}
			
		}
		return result;
	}
	
	public final boolean  xmlAttributeList(
		List<TomTerm> list
	) throws RecognitionException, TokenStreamException, TomException {
		boolean result;
		
		
		result = false;
		TomTerm term;
		
		
		{
		switch ( LA(1)) {
		case LBRACKET:
		{
			match(LBRACKET);
			if ( inputState.guessing==0 ) {
				text.append("[");
			}
			{
			switch ( LA(1)) {
			case ALL_ID:
			case UNDERSCORE:
			{
				term=xmlAttribute();
				if ( inputState.guessing==0 ) {
					list.add(term);
				}
				{
				_loop98:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						if ( inputState.guessing==0 ) {
							text.append("(");
						}
						term=xmlAttribute();
						if ( inputState.guessing==0 ) {
							list.add(term);
						}
					}
					else {
						break _loop98;
					}
					
				} while (true);
				}
				break;
			}
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RBRACKET);
			if ( inputState.guessing==0 ) {
				
				text.append("]");
				result = true;
				
			}
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			if ( inputState.guessing==0 ) {
				text.append("(");
			}
			{
			switch ( LA(1)) {
			case ALL_ID:
			case UNDERSCORE:
			{
				term=xmlAttribute();
				if ( inputState.guessing==0 ) {
					list.add(term);
				}
				{
				_loop101:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						if ( inputState.guessing==0 ) {
							text.append(",");
						}
						term=xmlAttribute();
						if ( inputState.guessing==0 ) {
							list.add(term);
						}
					}
					else {
						break _loop101;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				text.append(")");
				result = false;
				
			}
			break;
		}
		case ALL_ID:
		case XML_CLOSE:
		case XML_CLOSE_SINGLETON:
		case UNDERSCORE:
		{
			{
			_loop103:
			do {
				if (((LA(1)==ALL_ID||LA(1)==UNDERSCORE))&&(LA(1) != XML_CLOSE)) {
					term=xmlAttribute();
					if ( inputState.guessing==0 ) {
						list.add(term);
					}
				}
				else {
					break _loop103;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				result = true;
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
	
	public final boolean  xmlChilds(
		List<TomTerm> list
	) throws RecognitionException, TokenStreamException, TomException {
		boolean result;
		
		
		result = false;
		TomTerm term;
		
		
		{
		if ((LA(1)==LBRACKET)) {
			match(LBRACKET);
			if ( inputState.guessing==0 ) {
				text.append("[");
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			case ALL_ID:
			case XML_START:
			case ANTI_SYM:
			case NUM_INT:
			case STRING:
			case XML_TEXT:
			case XML_COMMENT:
			case XML_PROC:
			case UNDERSCORE:
			case CHARACTER:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				termList(list);
				break;
			}
			case RBRACKET:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RBRACKET);
			if ( inputState.guessing==0 ) {
				
				text.append("]");
				result=true;
				
			}
		}
		else if ((LA(1)==LPAREN)) {
			match(LPAREN);
			if ( inputState.guessing==0 ) {
				text.append("(");
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			case ALL_ID:
			case XML_START:
			case ANTI_SYM:
			case NUM_INT:
			case STRING:
			case XML_TEXT:
			case XML_COMMENT:
			case XML_PROC:
			case UNDERSCORE:
			case CHARACTER:
			case NUM_FLOAT:
			case NUM_LONG:
			case NUM_DOUBLE:
			{
				termList(list);
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			if ( inputState.guessing==0 ) {
				
				text.append(")");
				result=false;
				
			}
		}
		else if ((_tokenSet_6.member(LA(1)))) {
			{
			_loop132:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					term=annotatedTerm(true);
					if ( inputState.guessing==0 ) {
						list.add(ASTFactory.metaEncodeXMLAppl(symbolTable,term));
					}
				}
				else {
					break _loop132;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				
				result = true;
				
			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		return result;
	}
	
	public final TomTerm  termStringIdentifier(
		List<Option> options
	) throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  nameID = null;
		Token  nameString = null;
		
		result = null;
		List<Option> optionList = (options==null)?new LinkedList<Option>():options;
		OptionList option = null;
		TomNameList nameList = null;
		
		
		{
		switch ( LA(1)) {
		case ALL_ID:
		{
			nameID = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				text.append(nameID.getText());
				optionList.add(tom_make_OriginTracking(tom_make_Name(nameID.getText()),nameID.getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				result = tom_make_Variable(option,tom_make_Name(nameID.getText()),SymbolTable.TYPE_UNKNOWN,tom_empty_list_concConstraint());
				
			}
			break;
		}
		case STRING:
		{
			nameString = LT(1);
			match(STRING);
			if ( inputState.guessing==0 ) {
				
				text.append(nameString.getText());
				optionList.add(tom_make_OriginTracking(tom_make_Name(nameString.getText()),nameString.getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				ASTFactory.makeStringSymbol(symbolTable,nameString.getText(),optionList);
				nameList = tom_cons_list_concTomName(tom_make_Name(nameString.getText()),tom_empty_list_concTomName());
				result = tom_make_TermAppl(option,nameList,tom_empty_list_concTomTerm(),tom_empty_list_concConstraint());
				
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
	
	public final TomTerm  xmlAttribute() throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  id = null;
		Token  anno2 = null;
		Token  a = null;
		Token  anno1 = null;
		Token  e = null;
		Token  anno3 = null;
		Token  b = null;
		
		result = null;
		List<Slot> slotList = new LinkedList<Slot>();
		TomTerm term = null;
		TomTerm termName = null;
		String name;
		OptionList option = null;
		ConstraintList constraint;
		List<Option> optionList = new LinkedList<Option>();
		List<Constraint> constraintList = new LinkedList<Constraint>();
		List anno1ConstraintList = new LinkedList();
		List anno2ConstraintList = new LinkedList();
		List<Option> optionListAnno2 = new LinkedList<Option>();
		TomNameList nameList;
		boolean varStar = false;
		boolean anti = false;
		
		
		{
		if (((LA(1)==ALL_ID||LA(1)==UNDERSCORE))&&(LA(2) == STAR)) {
			result=variableStar(optionList,constraintList);
			if ( inputState.guessing==0 ) {
				varStar = true;
			}
		}
		else if (((LA(1)==ALL_ID))&&(LA(2) == EQUAL)) {
			id = LT(1);
			match(ALL_ID);
			match(EQUAL);
			if ( inputState.guessing==0 ) {
				text.append(id.getText()+"=");
			}
			{
			if (((LA(1)==ALL_ID))&&(LA(2) == AT)) {
				anno2 = LT(1);
				match(ALL_ID);
				match(AT);
				if ( inputState.guessing==0 ) {
					
					text.append(anno2.getText()+"@");
					anno2ConstraintList.add(ASTFactory.makeAliasTo(tom_make_Name(anno2.getText()), getLine(), currentFile()));
					
				}
			}
			else if ((_tokenSet_7.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			_loop108:
			do {
				if ((LA(1)==ANTI_SYM)) {
					a = LT(1);
					match(ANTI_SYM);
					if ( inputState.guessing==0 ) {
						anti = !anti;
					}
				}
				else {
					break _loop108;
				}
				
			} while (true);
			}
			term=unamedVariableOrTermStringIdentifier(optionListAnno2,anno2ConstraintList);
			if ( inputState.guessing==0 ) {
				
				name = ASTFactory.encodeXMLString(symbolTable,id.getText());
				nameList = tom_cons_list_concTomName(tom_make_Name(name),tom_empty_list_concTomName());
				termName = tom_make_TermAppl(tom_empty_list_concOption(),nameList,tom_empty_list_concTomTerm(),tom_empty_list_concConstraint());
				
			}
		}
		else if ((LA(1)==ALL_ID||LA(1)==UNDERSCORE)) {
			{
			switch ( LA(1)) {
			case ALL_ID:
			{
				anno1 = LT(1);
				match(ALL_ID);
				match(AT);
				if ( inputState.guessing==0 ) {
					
					text.append(anno1.getText()+"@");
					anno1ConstraintList.add(ASTFactory.makeAliasTo(tom_make_Name(anno1.getText()), getLine(), currentFile()));
					
				}
				break;
			}
			case UNDERSCORE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			termName=unamedVariable(optionList,anno1ConstraintList);
			e = LT(1);
			match(EQUAL);
			if ( inputState.guessing==0 ) {
				text.append("=");
			}
			{
			if (((LA(1)==ALL_ID))&&(LA(2) == AT)) {
				anno3 = LT(1);
				match(ALL_ID);
				match(AT);
				if ( inputState.guessing==0 ) {
					
					text.append(anno3.getText()+"@");
					anno2ConstraintList.add(ASTFactory.makeAliasTo(tom_make_Name(anno3.getText()), getLine(), currentFile()));
					
				}
			}
			else if ((_tokenSet_7.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			{
			_loop112:
			do {
				if ((LA(1)==ANTI_SYM)) {
					b = LT(1);
					match(ANTI_SYM);
					if ( inputState.guessing==0 ) {
						anti = !anti;
					}
				}
				else {
					break _loop112;
				}
				
			} while (true);
			}
			term=unamedVariableOrTermStringIdentifier(optionListAnno2,anno2ConstraintList);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		if ( inputState.guessing==0 ) {
			
			if (!varStar) {
			if (anti) {
			term = tom_make_AntiTerm(term);
			}
			
			slotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_NAME),termName));
			// we add the specif value : _
			optionList.add(tom_make_OriginTracking(tom_make_Name("_"),getLine(),currentFile()));
			option = ASTFactory.makeOptionList(optionList);
			constraint = ASTFactory.makeConstraintList(constraintList);
			slotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_SPECIFIED),tom_make_Variable(option,tom_make_EmptyName(),SymbolTable.TYPE_UNKNOWN,constraint)));
			// no longer necessary ot metaEncode Strings in attributes
			slotList.add(tom_make_PairSlotAppl(tom_make_Name(Constants.SLOT_VALUE),term));
			optionList.add(tom_make_OriginTracking(tom_make_Name(Constants.ATTRIBUTE_NODE),getLine(),currentFile()));
			option = ASTFactory.makeOptionList(optionList);
			constraint = ASTFactory.makeConstraintList(constraintList);
			
			nameList = tom_cons_list_concTomName(tom_make_Name(Constants.ATTRIBUTE_NODE),tom_empty_list_concTomName());
			result = tom_make_RecordAppl(option,nameList,ASTFactory.makeSlotList(slotList),constraint)
			
			
			;
			}
			
		}
		return result;
	}
	
	public final TomTerm  unamedVariableOrTermStringIdentifier(
		List<Option> options, List<Constraint> constraintList
	) throws RecognitionException, TokenStreamException, TomException {
		TomTerm result;
		
		Token  nameID = null;
		Token  nameString = null;
		
		result = null;
		List<Option> optionList = (options==null)?new LinkedList<Option>():options;
		OptionList option = null;
		TomNameList nameList = null;
		ConstraintList constraints = null;
		
		
		{
		switch ( LA(1)) {
		case UNDERSCORE:
		{
			result=unamedVariable(optionList, constraintList);
			break;
		}
		case ALL_ID:
		{
			nameID = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				text.append(nameID.getText());
				optionList.add(tom_make_OriginTracking(tom_make_Name(nameID.getText()),nameID.getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				constraints = ASTFactory.makeConstraintList(constraintList);
				result = tom_make_Variable(option,tom_make_Name(nameID.getText()),SymbolTable.TYPE_UNKNOWN,constraints);
				
			}
			break;
		}
		case STRING:
		{
			nameString = LT(1);
			match(STRING);
			if ( inputState.guessing==0 ) {
				
				text.append(nameString.getText());
				optionList.add(tom_make_OriginTracking(tom_make_Name(nameString.getText()),nameString.getLine(),currentFile()));
				option = ASTFactory.makeOptionList(optionList);
				ASTFactory.makeStringSymbol(symbolTable,nameString.getText(),optionList);
				nameList = tom_cons_list_concTomName(tom_make_Name(nameString.getText()),tom_empty_list_concTomName());
				constraints = ASTFactory.makeConstraintList(constraintList);
				result = tom_make_TermAppl(option,nameList,tom_empty_list_concTomTerm(),constraints);
				
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
		List<TomTerm> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		TomTerm term = null;
		
		
		{
		term=annotatedTerm(true);
		if ( inputState.guessing==0 ) {
			list.add(term);
		}
		{
		_loop140:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				if ( inputState.guessing==0 ) {
					text.append(',');
				}
				term=annotatedTerm(true);
				if ( inputState.guessing==0 ) {
					list.add(term);
				}
			}
			else {
				break _loop140;
			}
			
		} while (true);
		}
		}
	}
	
	public final void pairList(
		List<Slot> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  name = null;
		Token  name2 = null;
		
		TomTerm term = null;
		
		
		{
		name = LT(1);
		match(ALL_ID);
		match(EQUAL);
		if ( inputState.guessing==0 ) {
			
			text.append(name.getText());
			text.append('=');
			
		}
		term=annotatedTerm(true);
		if ( inputState.guessing==0 ) {
			list.add(tom_make_PairSlotAppl(tom_make_Name(name.getText()),term));
		}
		{
		_loop144:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				if ( inputState.guessing==0 ) {
					text.append(',');
				}
				name2 = LT(1);
				match(ALL_ID);
				match(EQUAL);
				if ( inputState.guessing==0 ) {
					
					text.append(name2.getText());
					text.append('=');
					
				}
				term=annotatedTerm(true);
				if ( inputState.guessing==0 ) {
					list.add(tom_make_PairSlotAppl(tom_make_Name(name2.getText()),term));
				}
			}
			else {
				break _loop144;
			}
			
		} while (true);
		}
		}
	}
	
	public final BQTerm  bqVariableStar(
		List<Option> optionList, List<Constraint> constraintList
	) throws RecognitionException, TokenStreamException {
		BQTerm result;
		
		Token  name1 = null;
		Token  t = null;
		
		result = null;
		String name = null;
		int line = 0;
		OptionList options = null;
		ConstraintList constraints = null;
		
		
		{
		{
		name1 = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
			name = name1.getText();
			line = name1.getLine();
			
		}
		}
		t = LT(1);
		match(STAR);
		if ( inputState.guessing==0 ) {
			
			text.append(name);
			text.append(t.getText());
			
			// setting line number for origin tracking
			// in %rule construct
			setLastLine(t.getLine());
			
			optionList.add(tom_make_OriginTracking(tom_make_Name(name),line,currentFile()));
			options = ASTFactory.makeOptionList(optionList);
			constraints = ASTFactory.makeConstraintList(constraintList);
			result = tom_make_BQVariableStar(options,tom_make_Name(name),SymbolTable.TYPE_UNKNOWN)
			
			
			
			;
			
			
		}
		}
		return result;
	}
	
	public final TomName  headSymbolOrConstant(
		List<Option> optionList
	) throws RecognitionException, TokenStreamException {
		TomName result;
		
		
		result = null;
		
		
		{
		switch ( LA(1)) {
		case ALL_ID:
		{
			result=headSymbol(optionList);
			break;
		}
		case NUM_INT:
		case STRING:
		case CHARACTER:
		case NUM_FLOAT:
		case NUM_LONG:
		case NUM_DOUBLE:
		{
			result=headConstant(optionList);
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
	
	public final TomName  headConstant(
		List<Option> optionList
	) throws RecognitionException, TokenStreamException {
		TomName result;
		
		
		result = null;
		Token t;
		
		
		t=constant();
		if ( inputState.guessing==0 ) {
			
				String name = t.getText();
				int line = t.getLine();
				text.append(name);
				setLastLine(line);
				result = tom_make_Name(name);
				optionList.add(tom_make_OriginTracking(result,line,currentFile()));
			
				switch(t.getType()) {
					case NUM_INT:
						ASTFactory.makeIntegerSymbol(symbolTable,name,optionList);
						break;
					case NUM_LONG:
						ASTFactory.makeLongSymbol(symbolTable,name,optionList);
						break;
					case CHARACTER:
						ASTFactory.makeCharSymbol(symbolTable,name,optionList);
						break;
					case NUM_DOUBLE:
						ASTFactory.makeDoubleSymbol(symbolTable,name,optionList);
						break;
					case STRING:
						ASTFactory.makeStringSymbol(symbolTable,name,optionList);
						break;
					default:
				}
			
		}
		return result;
	}
	
	public final Token  constant() throws RecognitionException, TokenStreamException {
		Token result;
		
		Token  t1 = null;
		Token  t2 = null;
		Token  t3 = null;
		Token  t4 = null;
		Token  t5 = null;
		Token  t6 = null;
		
		result = null;
		
		
		{
		switch ( LA(1)) {
		case NUM_INT:
		{
			t1 = LT(1);
			match(NUM_INT);
			if ( inputState.guessing==0 ) {
				result = t1;
			}
			break;
		}
		case CHARACTER:
		{
			t2 = LT(1);
			match(CHARACTER);
			if ( inputState.guessing==0 ) {
				result = t2;
			}
			break;
		}
		case STRING:
		{
			t3 = LT(1);
			match(STRING);
			if ( inputState.guessing==0 ) {
				result = t3;
			}
			break;
		}
		case NUM_FLOAT:
		{
			t4 = LT(1);
			match(NUM_FLOAT);
			if ( inputState.guessing==0 ) {
				result = t4;
			}
			break;
		}
		case NUM_LONG:
		{
			t5 = LT(1);
			match(NUM_LONG);
			if ( inputState.guessing==0 ) {
				result = t5;
			}
			break;
		}
		case NUM_DOUBLE:
		{
			t6 = LT(1);
			match(NUM_DOUBLE);
			if ( inputState.guessing==0 ) {
				result = t6;
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
	
	public final Declaration  operator() throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  type = null;
		Token  name = null;
		Token  slotName = null;
		Token  typeArg = null;
		Token  slotName2 = null;
		Token  typeArg2 = null;
		Token  t = null;
		
		result=null;
		Option ot = null;
		TomTypeList types = tom_empty_list_concTomType();
		List<Option> options = new LinkedList<Option>();
		List<TomName> slotNameList = new LinkedList<TomName>();
		List<PairNameDecl> pairNameDeclList = new LinkedList<PairNameDecl>();
		TomName astName = null;
		String stringSlotName = null;
		Declaration attribute;
		
		
		type = LT(1);
		match(ALL_ID);
		name = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
			ot = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			options.add(ot);
			
		}
		{
		match(LPAREN);
		{
		switch ( LA(1)) {
		case ALL_ID:
		{
			slotName = LT(1);
			match(ALL_ID);
			match(COLON);
			typeArg = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				stringSlotName = slotName.getText();
				astName = ASTFactory.makeName(stringSlotName);
				slotNameList.add(astName);
				pairNameDeclList.add(tom_make_PairNameDecl(astName,tom_make_EmptyDeclaration()));
				types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),typeArg.getText(),tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
				String typeOfSlot = getSlotType(stringSlotName);
				String typeOfArg= typeArg.getText();
				if (typeOfSlot != null && !typeOfSlot.equals(typeOfArg)) {
				TomMessage.warning(getLogger(),currentFile(), getLine(),
				TomMessage.slotIncompatibleTypes,stringSlotName,typeOfArg,typeOfSlot);
				} else {
				putSlot(stringSlotName,typeOfArg);
				}
				
			}
			{
			_loop171:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					slotName2 = LT(1);
					match(ALL_ID);
					match(COLON);
					typeArg2 = LT(1);
					match(ALL_ID);
					if ( inputState.guessing==0 ) {
						
						stringSlotName = slotName2.getText();
						astName = ASTFactory.makeName(stringSlotName);
						if(slotNameList.indexOf(astName) != -1) {
						TomMessage.error(getLogger(),currentFile(), getLine(),
						TomMessage.repeatedSlotName,
						stringSlotName);
						}
						slotNameList.add(astName);
						pairNameDeclList.add(tom_make_PairNameDecl(tom_make_Name(stringSlotName),tom_make_EmptyDeclaration()));
						types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),typeArg2.getText(),tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
						String typeOfSlot = getSlotType(stringSlotName);
						String typeOfArg= typeArg2.getText();
						if (typeOfSlot != null && !typeOfSlot.equals(typeOfArg)) {
						TomMessage.warning(getLogger(),currentFile(), getLine(),
						TomMessage.slotIncompatibleTypes,stringSlotName,typeOfArg,typeOfSlot);
						} else {
						putSlot(stringSlotName,typeOfArg);
						}
						
					}
				}
				else {
					break _loop171;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		}
		{
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			astName = tom_make_Name(name.getText());
			
		}
		{
		_loop174:
		do {
			switch ( LA(1)) {
			case MAKE:
			{
				attribute=keywordMake(name.getText(),tom_make_Type(tom_empty_list_concTypeOption(),type.getText(),tom_make_EmptyTargetLanguageType()),types);
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			case GET_SLOT:
			{
				attribute=keywordGetSlot(astName,type.getText());
				if ( inputState.guessing==0 ) {
					
					TomName sName = attribute.getSlotName();
					int index = slotNameList.indexOf(sName);
					/*
					* ensure that sName appears in slotNameList, only once
					* ensure that sName has not already been generated
					*/
					TomMessage msg = null;
					if(index == -1) {
					msg = TomMessage.errorIncompatibleSlotDecl;
					} else {
					PairNameDecl pair = pairNameDeclList.get(index);
					{{if (tom_is_sort_PairNameDecl(pair)) {if (tom_is_fun_sym_PairNameDecl((( tom.engine.adt.tomslot.types.PairNameDecl )pair))) {
					
					if(tom_get_slot_PairNameDecl_SlotDecl((( tom.engine.adt.tomslot.types.PairNameDecl )pair))!= tom_make_EmptyDeclaration()) {
					msg = TomMessage.errorTwoSameSlotDecl;
					}
					}}}}
					
					}
					if(msg != null) {
					TomMessage.error(getLogger(),currentFile(), getLine(),
					msg,
					currentFile(), Integer.valueOf(attribute.getOrgTrack().getLine()),
					"%op "+type.getText(), Integer.valueOf(ot.getLine()), sName.getString());
					} else {
					pairNameDeclList.set(index,tom_make_PairNameDecl(sName,attribute));
					}
					
				}
				break;
			}
			case GET_DEFAULT:
			{
				attribute=keywordGetDefault(astName,type.getText());
				if ( inputState.guessing==0 ) {
					
					TomName sName = attribute.getSlotName();
					/*
					* ensure that sName appears in slotNameList
					*/
					TomMessage msg = null;
					int index = slotNameList.indexOf(sName);
					if(index == -1) {
					msg = TomMessage.errorIncompatibleSlotDecl;
					} 
					if(msg != null) {
					TomMessage.error(getLogger(),currentFile(), getLine(),
					msg,
					currentFile(), Integer.valueOf(attribute.getOrgTrack().getLine()),
					"%op "+type.getText(), Integer.valueOf(ot.getLine()), sName.getString());
					}
					options.add(tom_make_DeclarationToOption(attribute));
					
				}
				break;
			}
			case IS_FSYM:
			{
				attribute=keywordIsFsym(astName,type.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			default:
			{
				break _loop174;
			}
			}
		} while (true);
		}
		t = LT(1);
		match(RBRACE);
		}
		if ( inputState.guessing==0 ) {
			
			TomSymbol astSymbol = ASTFactory.makeSymbol(name.getText(), tom_make_Type(tom_empty_list_concTypeOption(),type.getText(),tom_make_EmptyTargetLanguageType()), types, ASTFactory.makePairNameDeclList(pairNameDeclList), options);
			putSymbol(name.getText(),astSymbol);
			result = tom_make_SymbolDecl(astName);
			updatePosition(t.getLine(),t.getColumn());
			selector().pop();
			
		}
		return result;
	}
	
	public final Declaration  keywordMake(
		String opname, TomType returnType, TomTypeList types
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  nameArg = null;
		Token  nameArg2 = null;
		Token  l = null;
		
		result = null;
		Option ot = null;
		BQTermList args = tom_empty_list_concBQTerm();
		ArrayList<String> varnameList = new ArrayList<String>();
		int index = 0;
		TomType type;
		int nbTypes = types.length();
		
		
		{
		t = LT(1);
		match(MAKE);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			{
			switch ( LA(1)) {
			case ALL_ID:
			{
				nameArg = LT(1);
				match(ALL_ID);
				if ( inputState.guessing==0 ) {
					
					if( !(nbTypes > 0) ) {
					type = tom_make_EmptyType();
					} else {
					type = TomBase.elementAt(types,index++);
					}
					Option info1 = tom_make_OriginTracking(tom_make_Name(nameArg.getText()),nameArg.getLine(),currentFile());
					OptionList option1 = tom_cons_list_concOption(info1,tom_empty_list_concOption());
					
					args = tom_append_list_concBQTerm(args,tom_cons_list_concBQTerm(tom_make_BQVariable(option1,tom_make_Name(nameArg.getText()),type),tom_empty_list_concBQTerm()))
					
					
					
					;
					varnameList.add(nameArg.getText());
					
				}
				{
				_loop213:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						nameArg2 = LT(1);
						match(ALL_ID);
						if ( inputState.guessing==0 ) {
							
							if( index >= nbTypes ) {
							type = tom_make_EmptyType();
							} else {
							type = TomBase.elementAt(types,index++);
							}
							Option info2 = tom_make_OriginTracking(tom_make_Name(nameArg2.getText()),nameArg2.getLine(),currentFile());
							OptionList option2 = tom_cons_list_concOption(info2,tom_empty_list_concOption());
							
							args = tom_append_list_concBQTerm(args,tom_cons_list_concBQTerm(tom_make_BQVariable(option2,tom_make_Name(nameArg2.getText()),type),tom_empty_list_concBQTerm()))
							
							
							
							;
							varnameList.add(nameArg2.getText());
							
						}
					}
					else {
						break _loop213;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
			break;
		}
		case LBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		l = LT(1);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			updatePosition(t.getLine(),t.getColumn());
			selector().push("targetlexer");
			List<Code> blockList = new LinkedList<Code>();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tom_make_TargetLanguageToCode(tlCode));
			if(blockList.size()==1) {
			String[] vars = new String[varnameList.size()];
			String code = ASTFactory.abstractCode(tlCode.getCode(),varnameList.toArray(vars));
			result = tom_make_MakeDecl(tom_make_Name(opname),returnType,args,tom_make_ExpressionToInstruction(tom_make_Code(code)),ot);
			} else {
			result = tom_make_MakeDecl(tom_make_Name(opname),returnType,args,tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
			}
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordGetSlot(
		TomName astName, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  slotName = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_SLOT);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		slotName = LT(1);
		match(ALL_ID);
		match(COMMA);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
			result = tom_make_GetSlotDecl(astName,tom_make_Name(slotName.getText()),tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(code),ot)
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordGetDefault(
		TomName astName, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  slotName = null;
		Token  l = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_DEFAULT);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		slotName = LT(1);
		match(ALL_ID);
		match(RPAREN);
		l = LT(1);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			updatePosition(t.getLine(),t.getColumn());
			selector().push("targetlexer");
			List<Code> blockList = new LinkedList<Code>();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tom_make_TargetLanguageToCode(tlCode));
			if(blockList.size()==1) {
			String code = tlCode.getCode();
			//System.out.println("keywordGetDefault: " + code);
			result = tom_make_GetDefaultDecl(astName,tom_make_Name(slotName.getText()),tom_make_Code(code),ot);
			} else {
			result = tom_make_GetDefaultDecl(astName,tom_make_Name(slotName.getText()),tom_make_TomInstructionToExpression(tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList))),ot);
			//System.out.println("keywordGetDefault result = " + result);
			}
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordIsFsym(
		TomName astName, String typeString
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(IS_FSYM);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
			result = tom_make_IsFsymDecl(astName,tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),typeString,tom_make_EmptyTargetLanguageType())),tom_make_Code(code),ot)
			
			;
			
		}
		return result;
	}
	
	public final Declaration  operatorList() throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  type = null;
		Token  name = null;
		Token  typeArg = null;
		Token  t = null;
		
		result = null;
		TomTypeList types = tom_empty_list_concTomType();
		List options = new LinkedList();
		Declaration attribute = null;
		String opName = null;
		
		
		type = LT(1);
		match(ALL_ID);
		name = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
				  opName = name.getText();
				  Option ot = tom_make_OriginTracking(tom_make_Name(opName),name.getLine(),currentFile());
				  options.add(ot);
			
		}
		match(LPAREN);
		typeArg = LT(1);
		match(ALL_ID);
		match(STAR);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),typeArg.getText(),tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
			
		}
		match(LBRACE);
		{
		_loop177:
		do {
			switch ( LA(1)) {
			case MAKE_EMPTY:
			{
				attribute=keywordMakeEmptyList(opName);
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			case MAKE_INSERT:
			{
				attribute=keywordMakeAddList(opName,type.getText(),typeArg.getText());
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			case IS_FSYM:
			{
				attribute=keywordIsFsym(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			case GET_HEAD:
			{
				attribute=keywordGetHead(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			case GET_TAIL:
			{
				attribute=keywordGetTail(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			case IS_EMPTY:
			{
				attribute=keywordIsEmpty(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(attribute);
				}
				break;
			}
			default:
			{
				break _loop177;
			}
			}
		} while (true);
		}
		t = LT(1);
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			
			PairNameDeclList pairNameDeclList = tom_cons_list_concPairNameDecl(tom_make_PairNameDecl(tom_make_EmptyName(),tom_make_EmptyDeclaration()),tom_empty_list_concPairNameDecl());
			TomSymbol astSymbol = ASTFactory.makeSymbol(opName, tom_make_Type(tom_empty_list_concTypeOption(),type.getText(),tom_make_EmptyTargetLanguageType()), types, pairNameDeclList, options);
			putSymbol(opName,astSymbol);
			result = tom_make_ListSymbolDecl(tom_make_Name(opName));
			updatePosition(t.getLine(),t.getColumn());
			selector().pop();
			
		}
		return result;
	}
	
	public final Declaration  keywordMakeEmptyList(
		String name
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(MAKE_EMPTY);
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			match(RPAREN);
			break;
		}
		case LBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			selector().push("targetlexer");
			List blockList = new LinkedList();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tlCode);
			if(blockList.size()==1) {
			result = tom_make_MakeEmptyList(tom_make_Name(name),tom_make_ExpressionToInstruction(tom_make_Code(tlCode.getCode())),ot);
			} else {
			result = tom_make_MakeEmptyList(tom_make_Name(name),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
			}
			
		}
		return result;
	}
	
	public final Declaration  keywordMakeAddList(
		String name, String listType, String elementType
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  elementName = null;
		Token  listName = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(MAKE_INSERT);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		elementName = LT(1);
		match(ALL_ID);
		match(COMMA);
		listName = LT(1);
		match(ALL_ID);
		match(RPAREN);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			Option listInfo = tom_make_OriginTracking(tom_make_Name(listName.getText()),listName.getLine(),currentFile());
			Option elementInfo = tom_make_OriginTracking(tom_make_Name(elementName.getText()),elementName.getLine(),currentFile());
			OptionList listOption = tom_cons_list_concOption(listInfo,tom_empty_list_concOption());
			OptionList elementOption = tom_cons_list_concOption(elementInfo,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			List blockList = new LinkedList();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tlCode);
			if(blockList.size()==1) {
			String code = ASTFactory.abstractCode(tlCode.getCode(),elementName.getText(),listName.getText());
			result = tom_make_MakeAddList(tom_make_Name(name),tom_make_BQVariable(elementOption,tom_make_Name(elementName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),elementType,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_ExpressionToInstruction(tom_make_Code(code)),ot)
			
			
			;
			} else {
			result = tom_make_MakeAddList(tom_make_Name(name),tom_make_BQVariable(elementOption,tom_make_Name(elementName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),elementType,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot)
			
			
			;
			}
			
		}
		return result;
	}
	
	public final Declaration  keywordGetHead(
		TomName opname, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_HEAD);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_GetHeadDecl(opname,symbolTable.getUniversalType(),tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),ot)
			
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordGetTail(
		TomName opname, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_TAIL);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_GetTailDecl(opname,tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),ot)
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordIsEmpty(
		TomName opname, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(IS_EMPTY);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage  tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_IsEmptyDecl(opname,tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),ot)
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  operatorArray() throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  type = null;
		Token  name = null;
		Token  typeArg = null;
		Token  t = null;
		
		result = null;
		TomTypeList types = tom_empty_list_concTomType();
		List<Option> options = new LinkedList<Option>();
		Declaration attribute = null;
		String opName = null;
		
		
		type = LT(1);
		match(ALL_ID);
		name = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
				  opName = name.getText();
				  Option ot = tom_make_OriginTracking(tom_make_Name(opName),name.getLine(),currentFile());
				  options.add(ot);
			
		}
		match(LPAREN);
		typeArg = LT(1);
		match(ALL_ID);
		match(STAR);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			types = tom_append_list_concTomType(types,tom_cons_list_concTomType(tom_make_Type(tom_empty_list_concTypeOption(),typeArg.getText(),tom_make_EmptyTargetLanguageType()),tom_empty_list_concTomType()));
			
		}
		match(LBRACE);
		{
		_loop180:
		do {
			switch ( LA(1)) {
			case MAKE_EMPTY:
			{
				attribute=keywordMakeEmptyArray(opName,type.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			case MAKE_APPEND:
			{
				attribute=keywordMakeAddArray(opName,type.getText(),typeArg.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			case IS_FSYM:
			{
				attribute=keywordIsFsym(tom_make_Name(opName),type.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			case GET_ELEMENT:
			{
				attribute=keywordGetElement(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			case GET_SIZE:
			{
				attribute=keywordGetSize(tom_make_Name(opName), type.getText());
				if ( inputState.guessing==0 ) {
					options.add(tom_make_DeclarationToOption(attribute));
				}
				break;
			}
			default:
			{
				break _loop180;
			}
			}
		} while (true);
		}
		t = LT(1);
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			
			PairNameDeclList pairNameDeclList = tom_cons_list_concPairNameDecl(tom_make_PairNameDecl(tom_make_EmptyName(),tom_make_EmptyDeclaration()),tom_empty_list_concPairNameDecl());
			TomSymbol astSymbol = ASTFactory.makeSymbol(opName, tom_make_Type(tom_empty_list_concTypeOption(),type.getText(),tom_make_EmptyTargetLanguageType()), types, pairNameDeclList, options);
			putSymbol(opName,astSymbol);
			
			result = tom_make_ArraySymbolDecl(tom_make_Name(opName));
			
			updatePosition(t.getLine(),t.getColumn());
			
			selector().pop();
			
		}
		return result;
	}
	
	public final Declaration  keywordMakeEmptyArray(
		String name, String listType
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  listName = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(MAKE_EMPTY);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		listName = LT(1);
		match(ALL_ID);
		match(RPAREN);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			Option listInfo = tom_make_OriginTracking(tom_make_Name(listName.getText()),listName.getLine(),currentFile());
			OptionList listOption = tom_cons_list_concOption(listInfo,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			List blockList = new LinkedList();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tlCode);
			if(blockList.size()==1) {
			String code = ASTFactory.abstractCode(tlCode.getCode(),listName.getText());
			result = tom_make_MakeEmptyArray(tom_make_Name(name),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_ExpressionToInstruction(tom_make_Code(code)),ot)
			;
			} else {
			result = tom_make_MakeEmptyArray(tom_make_Name(name),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot)
			
			;
			}
			
		}
		return result;
	}
	
	public final Declaration  keywordMakeAddArray(
		String name, String listType, String elementType
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  elementName = null;
		Token  listName = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(MAKE_APPEND);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		elementName = LT(1);
		match(ALL_ID);
		match(COMMA);
		listName = LT(1);
		match(ALL_ID);
		match(RPAREN);
		match(LBRACE);
		if ( inputState.guessing==0 ) {
			
			selector().push("targetlexer");
			List blockList = new LinkedList();
			TargetLanguage tlCode = targetparser.targetLanguage(blockList);
			selector().pop();
			blockList.add(tlCode);
			
			Option listInfo = tom_make_OriginTracking(tom_make_Name(listName.getText()),listName.getLine(),currentFile());
			Option elementInfo = tom_make_OriginTracking(tom_make_Name(elementName.getText()),elementName.getLine(),currentFile());
			OptionList listOption = tom_cons_list_concOption(listInfo,tom_empty_list_concOption());
			OptionList elementOption = tom_cons_list_concOption(elementInfo,tom_empty_list_concOption());
			if(blockList.size()==1) {
			String code = ASTFactory.abstractCode(tlCode.getCode(),elementName.getText(),listName.getText());
			result = tom_make_MakeAddArray(tom_make_Name(name),tom_make_BQVariable(elementOption,tom_make_Name(elementName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),elementType,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_ExpressionToInstruction(tom_make_Code(code)),ot)
			
			
			;
			} else {
			result = tom_make_MakeAddArray(tom_make_Name(name),tom_make_BQVariable(elementOption,tom_make_Name(elementName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),elementType,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(listOption,tom_make_Name(listName.getText()),tom_make_Type(tom_empty_list_concTypeOption(),listType,tom_make_EmptyTargetLanguageType())),tom_make_AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot)
			
			
			;
			}
			
		}
		return result;
	}
	
	public final Declaration  keywordGetElement(
		TomName opname, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name1 = null;
		Token  name2 = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_ELEMENT);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name1 = LT(1);
		match(ALL_ID);
		match(COMMA);
		name2 = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info1 = tom_make_OriginTracking(tom_make_Name(name1.getText()),name1.getLine(),currentFile());
			Option info2 = tom_make_OriginTracking(tom_make_Name(name2.getText()),name2.getLine(),currentFile());
			OptionList option1 = tom_cons_list_concOption(info1,tom_empty_list_concOption());
			OptionList option2 = tom_cons_list_concOption(info2,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_GetElementDecl(opname,tom_make_BQVariable(option1,tom_make_Name(name1.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(option2,tom_make_Name(name2.getText()),tom_make_Type(tom_empty_list_concTypeOption(),"int",tom_make_EmptyTargetLanguageType())),tom_make_Code(ASTFactory.abstractCode(tlCode.getCode(),name1.getText(),name2.getText())),ot)
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordGetSize(
		TomName opname, String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(GET_SIZE);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_GetSizeDecl(opname,tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),ot)
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  typeTerm() throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  type = null;
		Token  supertype = null;
		Token  t = null;
		
		result = null;
		Option ot = null;
		Declaration attribute = null;
		TargetLanguage implement = null;
		DeclarationList declarationList = tom_empty_list_concDeclaration();
		String s;
		
		TypeOptionList typeoptionList = tom_empty_list_concTypeOption();
		int currentLine = -1;
		String supertypeName = null;
		String currentTypeName = null;
		
		
		{
		type = LT(1);
		match(ALL_ID);
		if ( inputState.guessing==0 ) {
			
			currentLine = type.getLine();
			currentTypeName = type.getText();
			ot = tom_make_OriginTracking(tom_make_Name(currentTypeName),currentLine,currentFile());
			
		}
		{
		switch ( LA(1)) {
		case EXTENDS:
		{
			match(EXTENDS);
			supertype = LT(1);
			match(ALL_ID);
			if ( inputState.guessing==0 ) {
				
				supertypeName = supertype.getText();
				typeoptionList = tom_cons_list_concTypeOption(tom_make_SubtypeDecl(supertypeName),tom_empty_list_concTypeOption());
				
			}
			break;
		}
		case LBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(LBRACE);
		implement=keywordImplement();
		{
		_loop185:
		do {
			switch ( LA(1)) {
			case EQUALS:
			{
				attribute=keywordEquals(currentTypeName);
				if ( inputState.guessing==0 ) {
					declarationList = tom_cons_list_concDeclaration(attribute,tom_append_list_concDeclaration(declarationList,tom_empty_list_concDeclaration()));
				}
				break;
			}
			case IS_SORT:
			{
				attribute=keywordIsSort(currentTypeName);
				if ( inputState.guessing==0 ) {
					declarationList = tom_cons_list_concDeclaration(attribute,tom_append_list_concDeclaration(declarationList,tom_empty_list_concDeclaration()));
				}
				break;
			}
			case GET_IMPLEMENTATION:
			{
				attribute=keywordGetImplementation(currentTypeName);
				if ( inputState.guessing==0 ) {
					declarationList = tom_cons_list_concDeclaration(attribute,tom_append_list_concDeclaration(declarationList,tom_empty_list_concDeclaration()));
				}
				break;
			}
			default:
			{
				break _loop185;
			}
			}
		} while (true);
		}
		t = LT(1);
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			
			TomType astType = tom_make_Type(typeoptionList,currentTypeName,tom_make_TLType(implement.getCode()));
			putType(currentTypeName, astType);
			result = tom_make_TypeTermDecl(tom_make_Name(currentTypeName),declarationList,ot);
			updatePosition(t.getLine(),t.getColumn());
			selector().pop();
			
		}
		}
		return result;
	}
	
	public final TargetLanguage  keywordImplement() throws RecognitionException, TokenStreamException, TomException {
		TargetLanguage tlCode;
		
		
		tlCode = null;
		
		
		{
		match(IMPLEMENT);
		if ( inputState.guessing==0 ) {
			
			selector().push("targetlexer");
			tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
		}
		}
		return tlCode;
	}
	
	public final Declaration  keywordEquals(
		String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name1 = null;
		Token  name2 = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(EQUALS);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name1 = LT(1);
		match(ALL_ID);
		match(COMMA);
		name2 = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info1 = tom_make_OriginTracking(tom_make_Name(name1.getText()),name1.getLine(),currentFile());
			Option info2 = tom_make_OriginTracking(tom_make_Name(name2.getText()),name2.getLine(),currentFile());
			OptionList option1 = tom_cons_list_concOption(info1,tom_empty_list_concOption());
			OptionList option2 = tom_cons_list_concOption(info2,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			String code = ASTFactory.abstractCode(tlCode.getCode(),name1.getText(),name2.getText());
			result = tom_make_EqualTermDecl(tom_make_BQVariable(option1,tom_make_Name(name1.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_BQVariable(option2,tom_make_Name(name2.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(code),ot)
			
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordIsSort(
		String type
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		{
		t = LT(1);
		match(IS_SORT);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
			result = tom_make_IsSortDecl(tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),type,tom_make_EmptyTargetLanguageType())),tom_make_Code(code),ot)
			
			;
			
		}
		}
		return result;
	}
	
	public final Declaration  keywordGetImplementation(
		String typeString
	) throws RecognitionException, TokenStreamException, TomException {
		Declaration result;
		
		Token  t = null;
		Token  name = null;
		
		result = null;
		Option ot = null;
		
		
		t = LT(1);
		match(GET_IMPLEMENTATION);
		if ( inputState.guessing==0 ) {
			ot = tom_make_OriginTracking(tom_make_Name(t.getText()),t.getLine(),currentFile());
		}
		match(LPAREN);
		name = LT(1);
		match(ALL_ID);
		match(RPAREN);
		if ( inputState.guessing==0 ) {
			
			Option info = tom_make_OriginTracking(tom_make_Name(name.getText()),name.getLine(),currentFile());
			OptionList option = tom_cons_list_concOption(info,tom_empty_list_concOption());
			
			selector().push("targetlexer");
			TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList<Code>());
			selector().pop();
			
			result = tom_make_GetImplementationDecl(tom_make_BQVariable(option,tom_make_Name(name.getText()),tom_make_Type(tom_empty_list_concTypeOption(),typeString,tom_make_EmptyTargetLanguageType())),tom_make_Return(tom_cons_list_Composite(tom_make_CompositeTL(tlCode),tom_empty_list_Composite())),ot)
			;
			
		}
		return result;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"LPAREN",
		"RPAREN",
		"LBRACE",
		"RBRACE",
		"COMMA",
		"BACKQUOTE",
		"ALL_ID",
		"COLON",
		"AND_CONNECTOR",
		"OR_CONNECTOR",
		"ARROW",
		"MATCH_CONSTRAINT",
		"XML_START",
		"LESSOREQUAL_CONSTRAINT",
		"XML_CLOSE",
		"GREATEROREQUAL_CONSTRAINT",
		"DOUBLEEQ",
		"DIFFERENT_CONSTRAINT",
		"\"extends\"",
		"\"visit\"",
		"AT",
		"ANTI_SYM",
		"QQMARK",
		"QMARK",
		"STAR",
		"NUM_INT",
		"STRING",
		"XML_CLOSE_SINGLETON",
		"XML_START_ENDING",
		"XML_TEXT",
		"XML_COMMENT",
		"XML_PROC",
		"LBRACKET",
		"RBRACKET",
		"EQUAL",
		"UNDERSCORE",
		"ALTERNATIVE",
		"CHARACTER",
		"NUM_FLOAT",
		"NUM_LONG",
		"NUM_DOUBLE",
		"\"implement\"",
		"\"equals\"",
		"\"is_sort\"",
		"\"get_head\"",
		"\"get_tail\"",
		"\"is_empty\"",
		"\"get_element\"",
		"\"get_size\"",
		"\"is_fsym\"",
		"\"get_implementation\"",
		"\"get_slot\"",
		"\"get_default\"",
		"\"make\"",
		"\"make_empty\"",
		"\"make_insert\"",
		"\"make_append\"",
		"\"where\"",
		"\"if\"",
		"\"when\"",
		"DOULEARROW",
		"AFFECT",
		"DOUBLE_QUOTE",
		"WS",
		"SLCOMMENT",
		"ML_COMMENT",
		"CONSTRAINT_GROUP_START",
		"CONSTRAINT_GROUP_END",
		"ESC",
		"HEX_DIGIT",
		"LETTER",
		"DIGIT",
		"ID",
		"ID_MINUS",
		"MINUS",
		"PLUS",
		"EXPONENT",
		"DOT",
		"FLOAT_SUFFIX"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 33596878423056L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 33596878423568L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 33596882515952L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 1610614272L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 60129607680L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 32986959446016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 33601173390352L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 550863111168L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	
	}
