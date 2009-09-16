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
import tom.engine.adt.code.types.*;
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

        private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }         private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) );       } else {         return ( (tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );   }            private static Strategy makeTopDownWhenExpression(Strategy s) {   return ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( makeWhenExpression(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?s:new tom.library.sl.Sequence(s,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) )) );  }  public static class WhenExpression extends tom.library.sl.AbstractStrategyBasic {    private  tom.library.sl.Strategy  s;      public WhenExpression( tom.library.sl.Strategy  s) {     super(( new tom.library.sl.Identity() ));     this.s=s;   }      public  tom.library.sl.Strategy  gets() {     return s;   }    public tom.library.sl.Visitable[] getChildren() {     tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];     stratChilds[0] = super.getChildAt(0);     stratChilds[1] = gets();     return stratChilds;   }    public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {     super.setChildAt(0, children[0]);     s = ( tom.library.sl.Strategy ) children[1];     return this;   }    public int getChildCount() {     return 2;   }    public tom.library.sl.Visitable getChildAt(int index) {     switch (index) {       case 0: return super.getChildAt(0);       case 1: return gets();       default: throw new IndexOutOfBoundsException();      }   }    public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {     switch (index) {       case 0: return super.setChildAt(0, child);       case 1: s = ( tom.library.sl.Strategy )child;               return this;       default: throw new IndexOutOfBoundsException();     }   }    public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {     if ( (v instanceof tom.engine.adt.tomexpression.types.Expression) ) {       return s.visitLight(v,introspector);     }     return any.visitLight(v,introspector);   }  }    private static  tom.library.sl.Strategy  makeWhenExpression( tom.library.sl.Strategy  t0) { return new WhenExpression(t0);}           private static Strategy makeTopDownWhenExprConstrOrTerm(Strategy s) {   return ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( makeWhenExprConstrOrTerm(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?s:new tom.library.sl.Sequence(s,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) )) );  }  public static class WhenExprConstrOrTerm extends tom.library.sl.AbstractStrategyBasic {    private  tom.library.sl.Strategy  s;      public WhenExprConstrOrTerm( tom.library.sl.Strategy  s) {     super(( new tom.library.sl.Identity() ));     this.s=s;   }      public  tom.library.sl.Strategy  gets() {     return s;   }    public tom.library.sl.Visitable[] getChildren() {     tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];     stratChilds[0] = super.getChildAt(0);     stratChilds[1] = gets();     return stratChilds;   }    public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {     super.setChildAt(0, children[0]);     s = ( tom.library.sl.Strategy ) children[1];     return this;   }    public int getChildCount() {     return 2;   }    public tom.library.sl.Visitable getChildAt(int index) {     switch (index) {       case 0: return super.getChildAt(0);       case 1: return gets();       default: throw new IndexOutOfBoundsException();      }   }    public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {     switch (index) {       case 0: return super.setChildAt(0, child);       case 1: s = ( tom.library.sl.Strategy )child;               return this;       default: throw new IndexOutOfBoundsException();     }   }    public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {     if (v instanceof tom.engine.adt.tomexpression.types.Expression ||          v instanceof tom.engine.adt.code.types.BQTerm ||          v instanceof tom.engine.adt.tomterm.types.TomTerm ||          v instanceof tom.engine.adt.tomconstraint.types.Constraint) {       return s.visitLight(v,introspector);     }     return any.visitLight(v,introspector);   }  }    private static  tom.library.sl.Strategy  makeWhenExprConstrOrTerm( tom.library.sl.Strategy  t0) { return new WhenExprConstrOrTerm(t0);}     








  private Compiler compiler; 
  private ConstraintGenerator constraintGenerator; // only present for "compatibility" : cf. ConstraintGenerator.t and look around ".newInstance(this.getCompiler(),this);" 
 
  public SyntacticGenerator(Compiler myCompiler, ConstraintGenerator myConstraintGenerator) {
    this.compiler = myCompiler;
    this.constraintGenerator = myConstraintGenerator; // only present for "compatibility" : cf. ConstraintGenerator.t and look around ".newInstance(this.getCompiler(),this);" 
  }

  public Compiler getCompiler() {
    return this.compiler;
  }
 
  public ConstraintGenerator getConstraintGenerator() {
    return this.constraintGenerator;
  }
 
  public Expression generate(Expression expression) throws VisitFailure {
    return  ( makeTopDownWhenExpression(tom_make_Generator(this)) ).visitLight(expression);
  }

  // If we find ConstraintToExpression it means that this constraint was not processed	
  public static class Generator extends tom.library.sl.AbstractStrategyBasic {private  SyntacticGenerator  sg;public Generator( SyntacticGenerator  sg) {super(( new tom.library.sl.Identity() ));this.sg=sg;}public  SyntacticGenerator  getsg() {return sg;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomexpression.types.Expression  visit_Expression( tom.engine.adt.tomexpression.types.Expression  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (tom__arg instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )tom__arg) instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression) ) { tom.engine.adt.tomconstraint.types.Constraint  tomMatch135NameNumber_freshVar_1= (( tom.engine.adt.tomexpression.types.Expression )tom__arg).getcons() ;if ( (tomMatch135NameNumber_freshVar_1 instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch135NameNumber_freshVar_3= tomMatch135NameNumber_freshVar_1.getPattern() ; tom.engine.adt.code.types.BQTerm  tomMatch135NameNumber_freshVar_4= tomMatch135NameNumber_freshVar_1.getSubject() ;if ( (tomMatch135NameNumber_freshVar_3 instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomname.types.TomNameList  tomMatch135NameNumber_freshVar_6= tomMatch135NameNumber_freshVar_3.getNameList() ;if ( ((tomMatch135NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch135NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch135NameNumber_freshVar_6.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tom_name= tomMatch135NameNumber_freshVar_6.getHeadconcTomName() ;if (  tomMatch135NameNumber_freshVar_6.getTailconcTomName() .isEmptyconcTomName() ) {if ( (tomMatch135NameNumber_freshVar_4 instanceof tom.engine.adt.code.types.bqterm.SymbolOf) ) {



        TomType termType = sg.getCompiler().getTermTypeFromName(tom_name);
        Expression check = sg.buildEqualFunctionSymbol(termType, tomMatch135NameNumber_freshVar_4.getGroundTerm() ,tom_name,TomBase.getTheory(tomMatch135NameNumber_freshVar_3));
        return check;
      }}}}}}}}}{if ( (tom__arg instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )tom__arg) instanceof tom.engine.adt.tomexpression.types.expression.ConstraintToExpression) ) { tom.engine.adt.tomconstraint.types.Constraint  tomMatch135NameNumber_freshVar_13= (( tom.engine.adt.tomexpression.types.Expression )tom__arg).getcons() ;if ( (tomMatch135NameNumber_freshVar_13 instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch135NameNumber_freshVar_15= tomMatch135NameNumber_freshVar_13.getPattern() ;if ( (tomMatch135NameNumber_freshVar_15 instanceof tom.engine.adt.tomterm.types.tomterm.TestVar) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch135NameNumber_freshVar_18= tomMatch135NameNumber_freshVar_15.getVariable() ;boolean tomMatch135NameNumber_freshVar_22= false ; tom.engine.adt.tomtype.types.TomType  tomMatch135NameNumber_freshVar_20= null ;if ( (tomMatch135NameNumber_freshVar_18 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch135NameNumber_freshVar_22= true ;tomMatch135NameNumber_freshVar_20= tomMatch135NameNumber_freshVar_18.getAstType() ;}} else {if ( (tomMatch135NameNumber_freshVar_18 instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch135NameNumber_freshVar_22= true ;tomMatch135NameNumber_freshVar_20= tomMatch135NameNumber_freshVar_18.getAstType() ;}}}if ((tomMatch135NameNumber_freshVar_22 ==  true )) {


        return  tom.engine.adt.tomexpression.types.expression.EqualBQTerm.make(tomMatch135NameNumber_freshVar_20,  tomMatch135NameNumber_freshVar_13.getSubject() , TomBase.convertFromVarToBQVar(tomMatch135NameNumber_freshVar_18)) ;
      }}}}}}}return _visit_Expression(tom__arg,introspector); }@SuppressWarnings("unchecked")public  tom.engine.adt.tomexpression.types.Expression  _visit_Expression( tom.engine.adt.tomexpression.types.Expression  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!((environment ==  null ))) {return (( tom.engine.adt.tomexpression.types.Expression )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);} }@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.engine.adt.tomexpression.types.Expression) ) {return ((T)visit_Expression((( tom.engine.adt.tomexpression.types.Expression )v),introspector));}if (!((environment ==  null ))) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);} }}private static  tom.library.sl.Strategy  tom_make_Generator( SyntacticGenerator  t0) { return new Generator(t0);}

 // end strategy	
  
  private Expression buildEqualFunctionSymbol(TomType type, BQTerm subject, TomName name, Theory theory) {
    TomSymbol tomSymbol = getCompiler().getSymbolTable().getSymbolFromName(name.getString());
    if(getCompiler().getSymbolTable().isBuiltinType(TomBase.getTomType(type))) {
      if(TomBase.isListOperator(tomSymbol) || TomBase.isArrayOperator(tomSymbol) || TomBase.hasIsFsymDecl(tomSymbol)) {
        return  tom.engine.adt.tomexpression.types.expression.IsFsym.make(name, subject) ;
      } else {
        return  tom.engine.adt.tomexpression.types.expression.EqualBQTerm.make(type,  tom.engine.adt.code.types.bqterm.BuildConstant.make(name) , subject) ;
      }
    } else if(TomBase.hasTheory(theory,  tom.engine.adt.theory.types.elementarytheory.AU.make() )) {
      return  tom.engine.adt.tomexpression.types.expression.IsSort.make(type, subject) ;
    }
    return  tom.engine.adt.tomexpression.types.expression.IsFsym.make(name, subject) ;
  }
}
