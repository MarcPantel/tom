/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2009, INRIA
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
 * Emilie Balland e-mail: Emilie.Balland@loria.fr
 *
 **/

package tom.engine.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import tom.engine.TomBase;
import tom.engine.adt.tomsignature.*;
import tom.engine.tools.TomGenericPlugin;

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

import tom.library.sl.*;

public class TomConstraintPrettyPrinter {

        private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;   }    


  public static String prettyPrint(Constraint subject) {
    
    {{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.AliasTo) ) {


        return "AliasTo("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getVar() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.AssignPositionTo) ) {


        return "AssignPositionTo("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getVariable() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.TrueConstraint) ) {


        return "true";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.FalseConstraint) ) {


        return "false";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.Negate) ) {


        return "!"+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getc() ); 
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.IsSortConstraint) ) {


        return "IsSort("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getAstType() )+","+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getBQTerm() )+")";      
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) ) {


        return prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getHeadAndConstraint() )+" &\n"+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getTailAndConstraint() );
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraint) ) {


        return prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getHeadOrConstraint() )+" ||\n"+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getTailOrConstraint() );
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction) ) {


        return "("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getHeadOrConstraintDisjunction() )+" | "+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getTailOrConstraintDisjunction() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tom_Pattern= (( tom.engine.adt.tomconstraint.types.Constraint )subject).getPattern() ; tom.engine.adt.code.types.BQTerm  tom_Subject= (( tom.engine.adt.tomconstraint.types.Constraint )subject).getSubject() ;


        if(TomBase.hasTheory(tom_Pattern, tom.engine.adt.theory.types.elementarytheory.AC.make() )) {
          return prettyPrint(tom_Pattern)+" <<_AC "+prettyPrint(tom_Subject);
        } else {
          return prettyPrint(tom_Pattern)+" << "+prettyPrint(tom_Subject);
        }
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.AntiMatchConstraint) ) {


        return "Anti("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getConstraint() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.NumericConstraint) ) {


        return prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getLeft() )+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getType() )+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getRight() );
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyListConstraint) ) {


        return "IsEmptyList("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getOpname() )+","+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getVariable() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )subject) instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint) ) {


        return "IsEmptyArray("+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getOpname() )+","+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getVariable() )+","+prettyPrint( (( tom.engine.adt.tomconstraint.types.Constraint )subject).getIndex() )+")";
      }}}}
 
    return subject.toString();
  }


  public static String prettyPrint(NumericConstraintType subject) {
    {{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumLessThan) ) {

        return "<";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumLessOrEqualThan) ) {

        return "<=";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumGreaterThan) ) {

        return ">";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumGreaterOrEqualThan) ) {

        return ">=";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumDifferent) ) {

        return "<>";
      }}}{if ( (subject instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ) {if ( ((( tom.engine.adt.tomconstraint.types.NumericConstraintType )subject) instanceof tom.engine.adt.tomconstraint.types.numericconstrainttype.NumEqual) ) {

        return "==";
      }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(TomTerm subject) {
    {{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {

        return prettyPrint( (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() );
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariable) ) {


        return "_";
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariableStar) ) {


        return "_";
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {


        return prettyPrint( (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() );
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {


        return prettyPrint( (( tom.engine.adt.tomterm.types.TomTerm )subject).getNameList() )+"("+prettyPrint( (( tom.engine.adt.tomterm.types.TomTerm )subject).getSlots() )+")"; 
      }}}}
 
    return subject.toString();
  }


  public static String prettyPrint(Expression subject) {
    {{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Cast) ) {

        return "("+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getAstType() )+") "+ prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getSource() ); 
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceList) ) {


        return "GetSliceList("+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableBeginAST() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableEndAST() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getTail() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceArray) ) {


        return "GetSliceArray("+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getSubjectListName() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableBeginAST() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableEndAST() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSize) ) {


        return "GetSize("+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetElement) ) {


        return "GetElement("+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() )+","+prettyPrint( (( tom.engine.adt.tomexpression.types.Expression )subject).getIndex() )+")";
      }}}}
 
    return subject.toString();
  }


  public static String prettyPrint(TomName subject) {
    {{if ( (subject instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )subject) instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

        return "t"+ TomBase.tomNumberListToString( (( tom.engine.adt.tomname.types.TomName )subject).getNumberList() );
      }}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )subject) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

        return  (( tom.engine.adt.tomname.types.TomName )subject).getString() ;
      }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(TomNameList subject) {
    {{if ( (subject instanceof tom.engine.adt.tomname.types.TomNameList) ) {if ( (((( tom.engine.adt.tomname.types.TomNameList )subject) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )subject) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( (( tom.engine.adt.tomname.types.TomNameList )subject).isEmptyconcTomName() )) {if (  (( tom.engine.adt.tomname.types.TomNameList )subject).getTailconcTomName() .isEmptyconcTomName() ) {

        return prettyPrint( (( tom.engine.adt.tomname.types.TomNameList )subject).getHeadconcTomName() );
      }}}}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomNameList) ) {if ( ((( tom.engine.adt.tomname.types.TomNameList )subject) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) ) {


        return prettyPrint( (( tom.engine.adt.tomname.types.TomNameList )subject).getHeadconcTomName() )+"."+prettyPrint( (( tom.engine.adt.tomname.types.TomNameList )subject).getTailconcTomName() );
      }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(TomType subject) {
    {{if ( (subject instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )subject) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 return  (( tom.engine.adt.tomtype.types.TomType )subject).getTomType() ; }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(TomNumber subject) {
    {{if ( (subject instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )subject) instanceof tom.engine.adt.tomname.types.tomnumber.Position) ) {

        return "" +  (( tom.engine.adt.tomname.types.TomNumber )subject).getInteger() ;
      }}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )subject) instanceof tom.engine.adt.tomname.types.tomnumber.NameNumber) ) {


        return prettyPrint( (( tom.engine.adt.tomname.types.TomNumber )subject).getAstName() );
      }}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )subject) instanceof tom.engine.adt.tomname.types.tomnumber.ListNumber) ) {


        return "listNumber"+ (( tom.engine.adt.tomname.types.TomNumber )subject).getInteger() ;
      }}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )subject) instanceof tom.engine.adt.tomname.types.tomnumber.Begin) ) {


        return "begin"+ (( tom.engine.adt.tomname.types.TomNumber )subject).getInteger() ;
      }}}{if ( (subject instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )subject) instanceof tom.engine.adt.tomname.types.tomnumber.End) ) {


        return "end"+ (( tom.engine.adt.tomname.types.TomNumber )subject).getInteger() ;
      }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(Slot subject) {
    {{if ( (subject instanceof tom.engine.adt.tomslot.types.Slot) ) {if ( ((( tom.engine.adt.tomslot.types.Slot )subject) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

        return prettyPrint( (( tom.engine.adt.tomslot.types.Slot )subject).getAppl() );
      }}}}
 
    return subject.toString();
  }

  public static String prettyPrint(SlotList subject) {
    String s = "";
    {{if ( (subject instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )subject) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )subject) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) { tom.engine.adt.tomslot.types.SlotList  tomMatch233NameNumber_end_4=(( tom.engine.adt.tomslot.types.SlotList )subject);do {{if (!( tomMatch233NameNumber_end_4.isEmptyconcSlot() )) {

        s += prettyPrint( tomMatch233NameNumber_end_4.getHeadconcSlot() )+",";
      }if ( tomMatch233NameNumber_end_4.isEmptyconcSlot() ) {tomMatch233NameNumber_end_4=(( tom.engine.adt.tomslot.types.SlotList )subject);} else {tomMatch233NameNumber_end_4= tomMatch233NameNumber_end_4.getTailconcSlot() ;}}} while(!( (tomMatch233NameNumber_end_4==(( tom.engine.adt.tomslot.types.SlotList )subject)) ));}}}}

    if (! s.equals("")) return s.substring(0,s.length()-1);

    return subject.toString();
  }

  public static String prettyPrint(BQTerm subject) {
    {{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.ExpressionToBQTerm) ) {

        return prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getExp() );
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.ListHead) ) {


        return "ListHead("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getVariable() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.ListTail) ) {


        return "ListTail("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getVariable() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.SymbolOf) ) {


        return "SymbolOf("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getGroundTerm() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.Subterm) ) {


        return "Subterm("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getAstName() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getSlotName() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getGroundTerm() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.VariableHeadList) ) {


        return "VariableHeadList("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getOpname() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getBegin() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getEnd() )+")";
      }}}{if ( (subject instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )subject) instanceof tom.engine.adt.code.types.bqterm.VariableHeadArray) ) {


        return "VariableHeadArray("+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getOpname() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getSubject() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getBeginIndex() )+","+prettyPrint( (( tom.engine.adt.code.types.BQTerm )subject).getEndIndex() )+")";
      }}}}

    return subject.toString();
  }
}
