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

import tom.library.sl.*;

public class TomConstraintPrettyPrinter {

  %include{ ../adt/tomconstraint/TomConstraint.tom }


  public static String prettyPrint(tom.library.sl.Visitable subject) {
    
    %match(subject) {

      AssignTo(Variable) -> {
        return "AssignTo("+prettyPrint(`Variable)+")";
      }

      AssignPositionTo(Variable) -> {
        return "AssignPositionTo("+prettyPrint(`Variable)+")";
      }

      TrueConstraint() -> {
        return "true";
      }
      
      FalseConstraint()	-> {
        return "false";
      }

      Negate(Constraint) -> {
        return "!"+prettyPrint(`Constraint); 
      }

      IsSortConstraint(AstType, TomTerm) -> {
        return "IsSort("+prettyPrint(`AstType)+","+prettyPrint(`TomTerm)+")";      
      }
      
      ConsAndConstraint(head,tail) -> {
        return prettyPrint(`head)+" &\n"+prettyPrint(`tail);
      }

      ConsOrConstraint(head,tail) -> {
        return prettyPrint(`head)+" ||\n"+prettyPrint(`tail);
      }

      ConsOrConstraintDisjunction(head,tail) -> {
        return "("+prettyPrint(`head)+" | "+prettyPrint(`tail)+")";
      }

      MatchConstraint(Pattern, Subject)-> {
        return prettyPrint(`Pattern)+" << "+prettyPrint(`Subject);
      }

      ACMatchConstraint(Pattern, Subject)-> {
        return prettyPrint(`Pattern)+" <<_ac "+prettyPrint(`Subject);
      }

      AntiMatchConstraint(Constraint)-> {
        return "Anti("+prettyPrint(`Constraint)+")";
      }

      NumericConstraint(Pattern, Subject, Type)-> {
        return prettyPrint(`Pattern)+prettyPrint(`Type)+prettyPrint(`Subject);
      }

      EmptyListConstraint(Opname, Variable)-> {
        return "IsEmptyList("+prettyPrint(`Opname)+","+prettyPrint(`Variable)+")";
      }

      EmptyArrayConstraint(Opname, Variable, Index)-> {
        return "IsEmptyArray("+prettyPrint(`Opname)+","+prettyPrint(`Variable)+","+prettyPrint(`Index)+")";
      }

    }

    %match(subject) { 
      NumLessThan() -> {
        return "<";
      }
      NumLessOrEqualThan()-> {
        return "<=";
      }
      NumGreaterThan()-> {
        return ">";
      }
      NumGreaterOrEqualThan()-> {
        return ">=";
      }
      NumDifferent()-> {
        return "<>";
      }
      NumEqual()-> {
        return "==";
      }
    }

    %match(subject) {
      ExpressionToTomTerm(term) -> {
        return prettyPrint(`term);
      }

      Variable(_,name,_,_) -> {
        return prettyPrint(`name);
      }

      UnamedVariable[] -> {
        return "_";
      }

      UnamedVariableStar[] -> {
        return "_";
      }

      VariableStar(_,name,_,_) -> {
        return prettyPrint(`name);
      }

      RecordAppl[NameList=nameList,Slots=slots] ->{
        return prettyPrint(`nameList)+"("+prettyPrint(`slots)+")"; 
      }

      ListHead[Variable=Variable] -> {
        return "ListHead("+prettyPrint(`Variable)+")";
      }

      ListTail[Variable=Variable] -> {
        return "ListTail("+prettyPrint(`Variable)+")";
      }

      SymbolOf(GroundTerm) -> {
        return "SymbolOf("+prettyPrint(`GroundTerm)+")";
      }

      Subterm(AstName, SlotName, GroundTerm) -> {
        return "Subterm("+prettyPrint(`AstName)+","+prettyPrint(`SlotName)+","+prettyPrint(`GroundTerm)+")";
      }

      VariableHeadList(Opname,Begin,End) -> {
        return "VariableHeadList("+prettyPrint(`Opname)+","+prettyPrint(`Begin)+","+prettyPrint(`End)+")";
      }

      VariableHeadArray(Opname,Subject,BeginIndex,EndIndex) -> {
        return "VariableHeadArray("+prettyPrint(`Opname)+","+prettyPrint(`Subject)+","+prettyPrint(`BeginIndex)+","+prettyPrint(`EndIndex)+")";
      }

    }

    %match(subject) {
      Cast(AstType,Source) -> {
        return "("+prettyPrint(`AstType)+") "+ prettyPrint(`Source); 
      }

      GetSliceList(AstName, VariableBeginAST, VariableEndAST, Tail) -> {
        return "GetSliceList("+prettyPrint(`AstName)+","+prettyPrint(`VariableBeginAST)+","+prettyPrint(`VariableEndAST)+","+prettyPrint(`Tail)+")";
      }

      GetSliceArray(_, SubjectListName, VariableBeginAST, VariableEndAST) -> {
        return "GetSliceArray("+prettyPrint(`SubjectListName)+","+prettyPrint(`VariableBeginAST)+","+prettyPrint(`VariableEndAST)+")";
      }

      GetSize(_, Variable) -> {
        return "GetSize("+prettyPrint(`Variable)+")";
      }

      GetElement[Variable=Variable,Index=Index] -> {
        return "GetElement("+prettyPrint(`Variable)+","+prettyPrint(`Index)+")";
      }

      TomTermToExpression(AstTerm) -> {
        return prettyPrint(`AstTerm);
      }

    }

    %match(subject) {
      PositionName(number_list) -> {
        return "t"+ TomBase.tomNumberListToString(`number_list);
      }
      Name(string) -> {
        return `string;
      }

    }

    %match(subject) {
      concTomName(Name) -> {
        return prettyPrint(`Name);
      }

      ConsconcTomName(head,tail) -> {
        return prettyPrint(`head)+"."+prettyPrint(`tail);
      }
    }

    %match(subject) {
      Type[TomType = name] -> { return `name; }
    }

    %match(subject) {
      Position(i) -> {
        return "" + `i;
      }

      NameNumber(name) -> {
        return prettyPrint(`name);
      }

      ListNumber(number) -> {
        return "listNumber"+`number;
      }

      Begin(number) -> {
        return "begin"+`number;
      }

      End(number) -> {
        return "end"+`number;
      }
    }

    %match(subject) {
      PairSlotAppl(_,Appl) -> {
        return prettyPrint(`Appl);
      }
    }

    String s = "";
    %match(subject) {
      concSlot(_*, t, _*) -> {
        s += prettyPrint(`t)+",";
      }
    }
    if (! s.equals("")) return s.substring(0,s.length()-1);

    return subject.toString();
  }

}
