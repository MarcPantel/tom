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
package tom.engine.compiler.propagator;

import tom.engine.compiler.propagator.*;  

import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomterm.types.tomterm.*;
import tom.library.sl.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.compiler.*;
import tom.engine.exception.TomRuntimeException;
import tom.engine.TomBase;
import tom.engine.compiler.Compiler;

/**
 * Array propagator
 */
public class ArrayPropagator implements IBasePropagator {

//--------------------------------------------------------	
        private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.Constraint  tom_append_list_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  l1,  tom.engine.adt.tomconstraint.types.Constraint  l2) {     if( l1.isEmptyAndConstraint() ) {       return l2;     } else if( l2.isEmptyAndConstraint() ) {       return l1;     } else if( ((l1 instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (l1 instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) ) {       if(  l1.getTailAndConstraint() .isEmptyAndConstraint() ) {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,l2) ;       } else {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,tom_append_list_AndConstraint( l1.getTailAndConstraint() ,l2)) ;       }     } else {       return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(l1,l2) ;     }   }   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_get_slice_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  begin,  tom.engine.adt.tomconstraint.types.Constraint  end, tom.engine.adt.tomconstraint.types.Constraint  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyAndConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getHeadAndConstraint() :begin),( tom.engine.adt.tomconstraint.types.Constraint )tom_get_slice_AndConstraint((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getTailAndConstraint() : tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ),end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {     if( l1.isEmptyconcConstraint() ) {       return l2;     } else if( l2.isEmptyconcConstraint() ) {       return l1;     } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;     } else {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;     }   }   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;   }         private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) );       } else {         return ( (tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );   }            private static Strategy makeTopDownWhenConstraint(Strategy s) {   return ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( makeWhenConstraint(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?s:new tom.library.sl.Sequence(s,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) )) );  }  public static class WhenConstraint extends tom.library.sl.AbstractStrategyBasic {    private  tom.library.sl.Strategy  s;      public WhenConstraint( tom.library.sl.Strategy  s) {     super(( new tom.library.sl.Identity() ));     this.s=s;   }      public  tom.library.sl.Strategy  gets() {     return s;   }    public tom.library.sl.Visitable[] getChildren() {     tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];     stratChilds[0] = super.getChildAt(0);     stratChilds[1] = gets();     return stratChilds;   }    public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {     super.setChildAt(0, children[0]);     s = ( tom.library.sl.Strategy ) children[1];     return this;   }    public int getChildCount() {     return 2;   }    public tom.library.sl.Visitable getChildAt(int index) {     switch (index) {       case 0: return super.getChildAt(0);       case 1: return gets();       default: throw new IndexOutOfBoundsException();      }   }    public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {     switch (index) {       case 0: return super.setChildAt(0, child);       case 1: s = ( tom.library.sl.Strategy )child;               return this;       default: throw new IndexOutOfBoundsException();     }   }    public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {     if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {       return s.visitLight(v,introspector);     }     return any.visitLight(v,introspector);   }  }    private static  tom.library.sl.Strategy  makeWhenConstraint( tom.library.sl.Strategy  t0) { return new WhenConstraint(t0);}     

	
//--------------------------------------------------------

  









  private Compiler compiler;
  private ConstraintPropagator constraintPropagator; // only present for compatibility 
  private GeneralPurposePropagator generalPurposePropagator;  
 
  public ArrayPropagator(Compiler myCompiler, ConstraintPropagator myConstraintPropagator) {
    this.compiler = myCompiler;
    this.constraintPropagator = myConstraintPropagator; // only present for compatibility 
    this.generalPurposePropagator = new GeneralPurposePropagator(this.compiler, this.constraintPropagator);
  }

  public Compiler getCompiler() {
    return this.compiler;
  }
 
  public GeneralPurposePropagator getGeneralPurposePropagator() {
    return this.generalPurposePropagator;
  }
 
  public ConstraintPropagator getConstraintPropagator() {
    return this.constraintPropagator;
  }
 
  public Constraint propagate(Constraint constraint) throws VisitFailure {
    return ( makeTopDownWhenConstraint(tom_make_ArrayPatternMatching(this)) ).visitLight(constraint);		
  }	

  public static class ArrayPatternMatching extends tom.library.sl.AbstractStrategyBasic {private  ArrayPropagator  ap;public ArrayPatternMatching( ArrayPropagator  ap) {super(( new tom.library.sl.Identity() ));this.ap=ap;}public  ArrayPropagator  getap() {return ap;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomconstraint.types.Constraint  visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (tom__arg instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tom__arg) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch177NameNumber_freshVar_1= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getPattern() ;if ( (tomMatch177NameNumber_freshVar_1 instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomname.types.TomNameList  tomMatch177NameNumber_freshVar_4= tomMatch177NameNumber_freshVar_1.getNameList() ; tom.engine.adt.tomslot.types.SlotList  tomMatch177NameNumber_freshVar_5= tomMatch177NameNumber_freshVar_1.getSlots() ;if ( ((tomMatch177NameNumber_freshVar_4 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch177NameNumber_freshVar_4 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch177NameNumber_freshVar_4.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tomMatch177NameNumber_freshVar_10= tomMatch177NameNumber_freshVar_4.getHeadconcTomName() ;if ( (tomMatch177NameNumber_freshVar_10 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if (  tomMatch177NameNumber_freshVar_4.getTailconcTomName() .isEmptyconcTomName() ) { tom.engine.adt.tomconstraint.types.Constraint  tom_m=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg);boolean tomMatch177NameNumber_freshVar_12= false ;if ( ((tomMatch177NameNumber_freshVar_5 instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || (tomMatch177NameNumber_freshVar_5 instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( tomMatch177NameNumber_freshVar_5.isEmptyconcSlot() ) {tomMatch177NameNumber_freshVar_12= true ;}}if ((tomMatch177NameNumber_freshVar_12 ==  false )) {











        if(TomBase.hasTheory(tomMatch177NameNumber_freshVar_1, tom.engine.adt.theory.types.elementarytheory.AC.make() )) {
          return tom_m;
        }
        // if this is not an array, nothing to do
        if(!TomBase.isArrayOperator(ap.getCompiler().getSymbolTable().
            getSymbolFromName( tomMatch177NameNumber_freshVar_10.getString() ))) { return tom_m; }
        Constraint detachedConstr = ap.getGeneralPurposePropagator().detachSublists(tom_m);
        // if something changed
        if (detachedConstr != tom_m) { return detachedConstr; }
      }}}}}}}}}{if ( (tom__arg instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tom__arg) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch177NameNumber_freshVar_14= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getPattern() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch177NameNumber_freshVar_15= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getSubject() ;if ( (tomMatch177NameNumber_freshVar_14 instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomname.types.TomNameList  tomMatch177NameNumber_freshVar_18= tomMatch177NameNumber_freshVar_14.getNameList() ;if ( ((tomMatch177NameNumber_freshVar_18 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch177NameNumber_freshVar_18 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch177NameNumber_freshVar_18.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tomMatch177NameNumber_freshVar_26= tomMatch177NameNumber_freshVar_18.getHeadconcTomName() ;if ( (tomMatch177NameNumber_freshVar_26 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { tom.engine.adt.tomname.types.TomName  tom_name= tomMatch177NameNumber_freshVar_18.getHeadconcTomName() ; tom.engine.adt.tomslot.types.SlotList  tom_slots= tomMatch177NameNumber_freshVar_14.getSlots() ; tom.engine.adt.tomterm.types.TomTerm  tom_g=tomMatch177NameNumber_freshVar_15; tom.engine.adt.tomconstraint.types.Constraint  tom_m=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg);boolean tomMatch177NameNumber_freshVar_29= false ;if ( (tomMatch177NameNumber_freshVar_15 instanceof tom.engine.adt.tomterm.types.tomterm.SymbolOf) ) {if ( (tomMatch177NameNumber_freshVar_15==tom_g) ) {tomMatch177NameNumber_freshVar_29= true ;}}if ((tomMatch177NameNumber_freshVar_29 ==  false )) {








      
        if(TomBase.hasTheory(tomMatch177NameNumber_freshVar_14, tom.engine.adt.theory.types.elementarytheory.AC.make() )) {
          return tom_m;
        }
            // if this is not an array, nothing to do
            if(!TomBase.isArrayOperator(ap.getCompiler().getSymbolTable().
                getSymbolFromName( tomMatch177NameNumber_freshVar_26.getString() ))) {return tom_m;}        
            // declare fresh variable            
            TomType termType = ap.getCompiler().getTermTypeFromTerm(tom_g);            
            TomTerm freshVariable = ap.getCompiler().getFreshVariableStar(termType);
            Constraint freshVarDeclaration =  tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(freshVariable, tom_g) ;
            
            // declare fresh index = 0            
            TomTerm freshIndex = ap.getFreshIndex();				
            Constraint freshIndexDeclaration =  tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(freshIndex,  tom.engine.adt.tomterm.types.tomterm.TargetLanguageToTomTerm.make( tom.engine.adt.tomsignature.types.targetlanguage.ITL.make("0") ) ) ;
            Constraint l =  tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ;
    match:  {{if ( (tom_slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )tom_slots).isEmptyconcSlot() ) {

                l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom_name, freshVariable, freshIndex) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) );
              }}}}{if ( (tom_slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) { tom.engine.adt.tomslot.types.SlotList  tomMatch178NameNumber_end_6=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);do {{if (!( tomMatch178NameNumber_end_6.isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch178NameNumber_freshVar_10= tomMatch178NameNumber_end_6.getHeadconcSlot() ;if ( (tomMatch178NameNumber_freshVar_10 instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) { tom.engine.adt.tomterm.types.TomTerm  tom_appl= tomMatch178NameNumber_freshVar_10.getAppl() ; tom.engine.adt.tomslot.types.SlotList  tom_X= tomMatch178NameNumber_end_6.getTailconcSlot() ;

                TomTerm newFreshIndex = ap.getFreshIndex();                
          mAppl:{{if ( (tom_appl instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch179NameNumber_freshVar_2= false ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom_appl) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {tomMatch179NameNumber_freshVar_2= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom_appl) instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariableStar) ) {tomMatch179NameNumber_freshVar_2= true ;}}if ((tomMatch179NameNumber_freshVar_2 ==  true )) {


                    // if it is the last element               
                    if(tom_X.length() == 0) {
                      // we should only assign it, without generating a loop
                      l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom_appl,  tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm.make( tom.engine.adt.tomexpression.types.expression.GetSliceArray.make(tom_name, freshVariable, freshIndex,  tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm.make( tom.engine.adt.tomexpression.types.expression.GetSize.make(tom_name, freshVariable) ) ) ) ) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) )
;
                    } else {
                      TomTerm beginIndex = ap.getBeginIndex();
                      TomTerm endIndex = ap.getEndIndex();
                      l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(beginIndex, freshIndex) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(endIndex, freshIndex) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom_appl,  tom.engine.adt.tomterm.types.tomterm.VariableHeadArray.make(tom_name, freshVariable, beginIndex, endIndex) ) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(newFreshIndex, endIndex) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) ) ) ) )



;     
                    }
                    break mAppl;
                  }}}{if ( (tom_appl instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
                    
                    l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.Negate.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom_name, freshVariable, freshIndex) ) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom_appl,  tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm.make( tom.engine.adt.tomexpression.types.expression.GetElement.make(tom_name, ap.getCompiler().getTermTypeFromTerm(tom_appl), freshVariable, freshIndex) ) ) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(newFreshIndex,  tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm.make( tom.engine.adt.tomexpression.types.expression.AddOne.make(freshIndex) ) ) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) ) ) )


;
                    // for the last element, we should also check that the list ends
                    if(tom_X.length() == 0) {                  
                      l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom_name, freshVariable, newFreshIndex) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) );
                    }
                  }}}
// end match
                freshIndex = newFreshIndex;
              }}if ( tomMatch178NameNumber_end_6.isEmptyconcSlot() ) {tomMatch178NameNumber_end_6=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);} else {tomMatch178NameNumber_end_6= tomMatch178NameNumber_end_6.getTailconcSlot() ;}}} while(!( (tomMatch178NameNumber_end_6==(( tom.engine.adt.tomslot.types.SlotList )tom_slots)) ));}}}}
// end match                        
            // add head equality condition + fresh var declaration + detached constraints
            l =  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(freshVarDeclaration, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make( tom.engine.adt.tomterm.types.tomterm.RecordAppl.make( tomMatch177NameNumber_freshVar_14.getOption() , tomMatch177NameNumber_freshVar_18,  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ,  tom.engine.adt.tomterm.types.tomterm.SymbolOf.make(freshVariable) ) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(freshIndexDeclaration, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(ap.getConstraintPropagator().performDetach(tom_m),tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() )) ) ) ) 
;
            return l;
        }}}}}}}}}return _visit_Constraint(tom__arg,introspector); }@SuppressWarnings("unchecked")public  tom.engine.adt.tomconstraint.types.Constraint  _visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!((environment ==  null ))) {return (( tom.engine.adt.tomconstraint.types.Constraint )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);} }@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {return ((T)visit_Constraint((( tom.engine.adt.tomconstraint.types.Constraint )v),introspector));}if (!((environment ==  null ))) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);} }}private static  tom.library.sl.Strategy  tom_make_ArrayPatternMatching( ArrayPropagator  t0) { return new ArrayPatternMatching(t0);}

// end %strategy

  private TomTerm getBeginIndex() {
    return getCompiler().getBeginVariableStar(getCompiler().getSymbolTable().getIntType());
  }

  private TomTerm getEndIndex() {
    return getCompiler().getEndVariableStar(getCompiler().getSymbolTable().getIntType());
  }

  private TomTerm getFreshIndex() {
    return getCompiler().getFreshVariableStar(getCompiler().getSymbolTable().getIntType());    
  }
}
