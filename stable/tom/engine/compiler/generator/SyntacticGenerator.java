/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
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
import tom.engine.adt.theory.types.*;
import tom.engine.compiler.Compiler;

/**
 * Syntactic Generator
 */
public class SyntacticGenerator implements IBaseGenerator {

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file */    private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */   private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )) == null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else {       return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file */ /* Generated by TOM (version 2.6alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ) ); }   
	

  public Expression generate(Expression expression) throws VisitFailure {
    return  (Expression)tom_make_TopDown(tom_make_Generator()).visit(expression);
  }

  // If we find ConstraintToExpression it means that this constraint was not processed	
  private static class Generator extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy  {public Generator() { super(( new tom.library.sl.Identity() ));}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.engine.adt.tomexpression.types.Expression  visit_Expression( tom.engine.adt.tomexpression.types.Expression  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{if ( (tom__arg instanceof tom.engine.adt.tomexpression.types.Expression) ) {{  tom.engine.adt.tomexpression.types.Expression  tomMatch160NameNumberfreshSubject_1=(( tom.engine.adt.tomexpression.types.Expression )tom__arg);if ( (tomMatch160NameNumberfreshSubject_1 instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression) ) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch160NameNumber_freshVar_0= tomMatch160NameNumberfreshSubject_1.getcons() ;if ( (tomMatch160NameNumber_freshVar_0 instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_1= tomMatch160NameNumber_freshVar_0.getpattern() ;{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_2= tomMatch160NameNumber_freshVar_0.getsubject() ;if ( (tomMatch160NameNumber_freshVar_1 instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {{  tom.engine.adt.tomname.types.TomNameList  tomMatch160NameNumber_freshVar_3= tomMatch160NameNumber_freshVar_1.getNameList() ;if ( ((tomMatch160NameNumber_freshVar_3 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch160NameNumber_freshVar_3 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {{  tom.engine.adt.tomname.types.TomNameList  tomMatch160NameNumber_freshVar_5=tomMatch160NameNumber_freshVar_3;if (!( tomMatch160NameNumber_freshVar_5.isEmptyconcTomName() )) {{  tom.engine.adt.tomname.types.TomName  tom_name= tomMatch160NameNumber_freshVar_5.getHeadconcTomName() ;{  tom.engine.adt.tomname.types.TomNameList  tomMatch160NameNumber_freshVar_6= tomMatch160NameNumber_freshVar_5.getTailconcTomName() ;if ( tomMatch160NameNumber_freshVar_6.isEmptyconcTomName() ) {if ( (tomMatch160NameNumber_freshVar_2 instanceof tom.engine.adt.tomterm.types.tomterm.SymbolOf) ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_4= tomMatch160NameNumber_freshVar_2.getGroundTerm() ;if ( true ) {



        TomType termType = Compiler.getTermTypeFromName(tom_name);        
        Expression check = buildEqualFunctionSymbol(termType,tomMatch160NameNumber_freshVar_4,tom_name,TomBase.getTheory(tomMatch160NameNumber_freshVar_1));
        return check;
      }}}}}}}}}}}}}}}}}}if ( (tom__arg instanceof tom.engine.adt.tomexpression.types.Expression) ) {{  tom.engine.adt.tomexpression.types.Expression  tomMatch160NameNumberfreshSubject_1=(( tom.engine.adt.tomexpression.types.Expression )tom__arg);if ( (tomMatch160NameNumberfreshSubject_1 instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression) ) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch160NameNumber_freshVar_7= tomMatch160NameNumberfreshSubject_1.getcons() ;if ( (tomMatch160NameNumber_freshVar_7 instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_8= tomMatch160NameNumber_freshVar_7.getpattern() ;{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_9= tomMatch160NameNumber_freshVar_7.getsubject() ;if ( (tomMatch160NameNumber_freshVar_8 instanceof tom.engine.adt.tomterm.types.tomterm.Subterm) ) {{  tom.engine.adt.tomterm.types.TomTerm  tom_t=tomMatch160NameNumber_freshVar_8;if ( true ) {


        return  tom.engine.adt.tomexpression.types.expression.EqualTerm.make(Compiler.getTermTypeFromTerm(tom_t), tom_t, tomMatch160NameNumber_freshVar_9) ;
      }}}}}}}}}}if ( (tom__arg instanceof tom.engine.adt.tomexpression.types.Expression) ) {{  tom.engine.adt.tomexpression.types.Expression  tomMatch160NameNumberfreshSubject_1=(( tom.engine.adt.tomexpression.types.Expression )tom__arg);if ( (tomMatch160NameNumberfreshSubject_1 instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression) ) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch160NameNumber_freshVar_10= tomMatch160NameNumberfreshSubject_1.getcons() ;if ( (tomMatch160NameNumber_freshVar_10 instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_11= tomMatch160NameNumber_freshVar_10.getpattern() ;{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_12= tomMatch160NameNumber_freshVar_10.getsubject() ;if ( (tomMatch160NameNumber_freshVar_11 instanceof tom.engine.adt.tomterm.types.tomterm.TestVar) ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch160NameNumber_freshVar_13= tomMatch160NameNumber_freshVar_11.getVariable() ;{ boolean tomMatch160NameNumber_freshVar_15= false ;{  tom.engine.adt.tomtype.types.TomType  tomMatch160NameNumber_freshVar_14= null ;if ( (tomMatch160NameNumber_freshVar_13 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch160NameNumber_freshVar_15= true ;tomMatch160NameNumber_freshVar_14= tomMatch160NameNumber_freshVar_13.getAstType() ;}} else {if ( (tomMatch160NameNumber_freshVar_13 instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch160NameNumber_freshVar_15= true ;tomMatch160NameNumber_freshVar_14= tomMatch160NameNumber_freshVar_13.getAstType() ;}}}if ((tomMatch160NameNumber_freshVar_15 ==  true )) {if ( true ) {


        return  tom.engine.adt.tomexpression.types.expression.EqualTerm.make(tomMatch160NameNumber_freshVar_14, tomMatch160NameNumber_freshVar_13, tomMatch160NameNumber_freshVar_12) ;
      }}}}}}}}}}}}}}return super.visit_Expression(tom__arg,introspector); }}private static  tom.library.sl.Strategy  tom_make_Generator() { return new Generator(); }

 // end strategy	
  
  private static Expression buildEqualFunctionSymbol(TomType type, TomTerm subject,  TomName name, Theory theory) {    
    TomSymbol tomSymbol = Compiler.getSymbolTable().getSymbolFromName(name.getString());
    if(Compiler.getSymbolTable().isBuiltinType(TomBase.getTomType(type))) {
      if(TomBase.isListOperator(tomSymbol) || TomBase.isArrayOperator(tomSymbol) || TomBase.hasIsFsymDecl(tomSymbol)) {
        return  tom.engine.adt.tomexpression.types.expression.IsFsym.make(name, subject) ;
      } else {
        return  tom.engine.adt.tomexpression.types.expression.EqualTerm.make(type,  tom.engine.adt.tomterm.types.tomterm.BuildConstant.make(name) , subject) ;
      }
    } else if(TomBase.hasTheory(theory,  tom.engine.adt.theory.types.elementarytheory.TrueAU.make() )) {
      return  tom.engine.adt.tomexpression.types.expression.IsSort.make(type, subject) ;
    } 
    return  tom.engine.adt.tomexpression.types.expression.IsFsym.make(name, subject) ;
  }
}
