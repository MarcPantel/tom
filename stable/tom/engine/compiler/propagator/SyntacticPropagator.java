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
* Radu Kopetz e-mail: Radu.Kopetz@loria.fr
* Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
*
**/
package tom.engine.compiler.propagator;

import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.code.types.*;
import tom.library.sl.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;
import tom.engine.compiler.*;
import tom.engine.TomBase;
import java.util.*;
import tom.engine.exception.TomRuntimeException;
import tom.engine.compiler.Compiler;

/**
* Syntactic propagator
*/
public class SyntacticPropagator implements IBasePropagator {

//--------------------------------------------------------


  private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {
    if( l1.isEmptyconcTomName() ) {
      return l2;
    } else if( l2.isEmptyconcTomName() ) {
      return l1;
    } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {
      return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;
    } else {
      return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {
    if( l1.isEmptyconcSlot() ) {
      return l2;
    } else if( l2.isEmptyconcSlot() ) {
      return l1;
    } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {
      return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;
    } else {
      return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tomconstraint.types.Constraint  tom_append_list_OrConstraintDisjunction( tom.engine.adt.tomconstraint.types.Constraint  l1,  tom.engine.adt.tomconstraint.types.Constraint  l2) {
    if( l1.isEmptyOrConstraintDisjunction() ) {
      return l2;
    } else if( l2.isEmptyOrConstraintDisjunction() ) {
      return l1;
    } else if( ((l1 instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction) || (l1 instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction)) ) {
      if(  l1.getTailOrConstraintDisjunction() .isEmptyOrConstraintDisjunction() ) {
        return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction.make( l1.getHeadOrConstraintDisjunction() ,l2) ;
      } else {
        return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction.make( l1.getHeadOrConstraintDisjunction() ,tom_append_list_OrConstraintDisjunction( l1.getTailOrConstraintDisjunction() ,l2)) ;
      }
    } else {
      return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction.make(l1,l2) ;
    }
  }
  private static   tom.engine.adt.tomconstraint.types.Constraint  tom_get_slice_OrConstraintDisjunction( tom.engine.adt.tomconstraint.types.Constraint  begin,  tom.engine.adt.tomconstraint.types.Constraint  end, tom.engine.adt.tomconstraint.types.Constraint  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyOrConstraintDisjunction()  ||  (end== tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction.make((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction)) )? begin.getHeadOrConstraintDisjunction() :begin),( tom.engine.adt.tomconstraint.types.Constraint )tom_get_slice_OrConstraintDisjunction((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction)) )? begin.getTailOrConstraintDisjunction() : tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction.make() ),end,tail)) ;
  }
  
  private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {
    if( l1.isEmptyconcConstraint() ) {
      return l2;
    } else if( l2.isEmptyconcConstraint() ) {
      return l1;
    } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {
      return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;
    } else {
      return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 == null )) {
      return l2;
    } else if(( l2 == null )) {
      return l1;
    } else if(( l1 instanceof tom.library.sl.Sequence )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {
        return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;
      } else {
        return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;
      }
    } else {
      return  tom.library.sl.Sequence.make(l1,l2) ;
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 ==null )) {
      return l2;
    } else if(( l2 ==null )) {
      return l1;
    } else if(( l1 instanceof tom.library.sl.Choice )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {
        return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;
      } else {
        return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;
      }
    } else {
      return  tom.library.sl.Choice.make(l1,l2) ;
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 == null )) {
      return l2;
    } else if(( l2 == null )) {
      return l1;
    } else if(( l1 instanceof tom.library.sl.SequenceId )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {
        return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;
      } else {
        return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;
      }
    } else {
      return  tom.library.sl.SequenceId.make(l1,l2) ;
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 ==null )) {
      return l2;
    } else if(( l2 ==null )) {
      return l1;
    } else if(( l1 instanceof tom.library.sl.ChoiceId )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {
        return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;
      } else {
        return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;
      }
    } else {
      return  tom.library.sl.ChoiceId.make(l1,l2) ;
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;
  }
  private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { 
return ( 
 tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) )

;
}
private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ))

;
}
private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ))

;
}


private static Strategy makeTopDownWhenConstraint(Strategy s) {
return 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( makeWhenConstraint( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) )) ); 
}

public static class WhenConstraint extends tom.library.sl.AbstractStrategyBasic {

private  tom.library.sl.Strategy  s;

public WhenConstraint( tom.library.sl.Strategy  s) {
super(( new tom.library.sl.Identity() ));
this.s=s;
}

public  tom.library.sl.Strategy  gets() {
return s;
}

public tom.library.sl.Visitable[] getChildren() {
tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];
stratChilds[0] = super.getChildAt(0);
stratChilds[1] = gets();
return stratChilds;
}

public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {
super.setChildAt(0, children[0]);
s = ( tom.library.sl.Strategy ) children[1];
return this;
}

public int getChildCount() {
return 2;
}

public tom.library.sl.Visitable getChildAt(int index) {
switch (index) {
case 0: return super.getChildAt(0);
case 1: return gets();
default: throw new IndexOutOfBoundsException();

}
}

public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {
switch (index) {
case 0: return super.setChildAt(0, child);
case 1: s = ( tom.library.sl.Strategy )child;
return this;
default: throw new IndexOutOfBoundsException();
}
}

public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {
return s.visitLight(v,introspector);
}
return any.visitLight(v,introspector);
}

}


private static  tom.library.sl.Strategy  makeWhenConstraint( tom.library.sl.Strategy  t0) { return new WhenConstraint(t0);}    

//--------------------------------------------------------




private Compiler compiler; 
private ConstraintPropagator constraintPropagator;

public SyntacticPropagator(Compiler myCompiler, ConstraintPropagator myConstraintPropagator) {
this.compiler = myCompiler;
this.constraintPropagator = myConstraintPropagator;
}

public Compiler getCompiler() {
return this.compiler;
}

public ConstraintPropagator getConstraintPropagator() {
return this.constraintPropagator;
}


public Constraint propagate(Constraint constraint) throws VisitFailure {   
return  
( makeTopDownWhenConstraint(tom_make_SyntacticPatternMatching(this)) ).visitLight(constraint);
}	


public static class SyntacticPatternMatching extends tom.library.sl.AbstractStrategyBasic {
private  SyntacticPropagator  sp;
public SyntacticPatternMatching( SyntacticPropagator  sp) {
super(( new tom.library.sl.Identity() ));
this.sp=sp;
}
public  SyntacticPropagator  getsp() {
return sp;
}
public tom.library.sl.Visitable[] getChildren() {
tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];
stratChilds[0] = super.getChildAt(0);
return stratChilds;}
public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {
super.setChildAt(0, children[0]);
return this;
}
public int getChildCount() {
return 1;
}
public tom.library.sl.Visitable getChildAt(int index) {
switch (index) {
case 0: return super.getChildAt(0);
default: throw new IndexOutOfBoundsException();
}
}
public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {
switch (index) {
case 0: return super.setChildAt(0, child);
default: throw new IndexOutOfBoundsException();
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {
return ((T)visit_Constraint((( tom.engine.adt.tomconstraint.types.Constraint )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tomconstraint.types.Constraint  _visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.engine.adt.tomconstraint.types.Constraint )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tomconstraint.types.Constraint  visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {
if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tom__arg) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) {
 tom.engine.adt.tomterm.types.TomTerm  tomMatch218_1= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getPattern() ;
 tom.engine.adt.code.types.BQTerm  tomMatch218_2= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getSubject() ;
if ( (tomMatch218_1 instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch218_6= tomMatch218_1.getNameList() ;
if ( ((tomMatch218_6 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch218_6 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
 tom.engine.adt.tomname.types.TomNameList  tom_nameList=tomMatch218_6;
if (!( tomMatch218_6.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tomMatch218_14= tomMatch218_6.getHeadconcTomName() ;
if ( (tomMatch218_14 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
 tom.engine.adt.tomslot.types.SlotList  tom_slots= tomMatch218_1.getSlots() ;
 tom.engine.adt.code.types.BQTerm  tom_g=tomMatch218_2;
 tom.engine.adt.tomtype.types.TomType  tom_aType= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getAstType() ;
 tom.engine.adt.tomconstraint.types.Constraint  tom_m=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg);
boolean tomMatch218_17= false ;
if ( (tomMatch218_2 instanceof tom.engine.adt.code.types.bqterm.SymbolOf) ) {
if ( (tom_g==tomMatch218_2) ) {
tomMatch218_17= true ;
}
}
if (!(tomMatch218_17)) {

// if this a list or array, nothing to do
if(!TomBase.isSyntacticOperator(
sp.getCompiler().getSymbolTable().getSymbolFromName(
 tomMatch218_14.getString() ))) { return 
tom_m; }

//System.out.println("m = " + `m);
List<Constraint> lastPart = new ArrayList<Constraint>();
ArrayList<BQTerm> freshVarList = new ArrayList<BQTerm>();
// we build the last part only once, and we store the fresh variables we generate

{
{
if ( (tom_slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {
if ( (((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {
 tom.engine.adt.tomslot.types.SlotList  tomMatch219__end__4=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);
do {
{
if (!( tomMatch219__end__4.isEmptyconcSlot() )) {
 tom.engine.adt.tomslot.types.Slot  tomMatch219_9= tomMatch219__end__4.getHeadconcSlot() ;
if ( (tomMatch219_9 instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

BQTerm freshVar = sp.getCompiler().getFreshVariable(sp.getCompiler().getSlotType(
 tomMatch218_6.getHeadconcTomName() ,
 tomMatch219_9.getSlotName() ));
// store the fresh variable
freshVarList.add(freshVar);
// build the last part
lastPart.add(
 tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make( tomMatch219_9.getAppl() , freshVar, tom_aType) );              


}
}
if ( tomMatch219__end__4.isEmptyconcSlot() ) {
tomMatch219__end__4=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);
} else {
tomMatch219__end__4= tomMatch219__end__4.getTailconcSlot() ;
}

}
} while(!( (tomMatch219__end__4==(( tom.engine.adt.tomslot.types.SlotList )tom_slots)) ));
}
}

}

}

BQTerm freshSubject = sp.getCompiler().getFreshVariable(sp.getCompiler().getTermTypeFromTerm(
tom_g));
// take each symbol and build the disjunction (OrConstraintDisjunction)
Constraint l = 
 tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction.make() ;

{
{
if ( (tom_nameList instanceof tom.engine.adt.tomname.types.TomNameList) ) {
if ( (((( tom.engine.adt.tomname.types.TomNameList )tom_nameList) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tom_nameList) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch220__end__4=(( tom.engine.adt.tomname.types.TomNameList )tom_nameList);
do {
{
if (!( tomMatch220__end__4.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tom_name= tomMatch220__end__4.getHeadconcTomName() ;

// the 'and' conjunction for each name
List<Constraint> andForName = new ArrayList<Constraint>();
// add condition for symbolOf
andForName.add(
 tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make( tom.engine.adt.tomterm.types.tomterm.RecordAppl.make( tomMatch218_1.getOptions() ,  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make(tom_name, tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) ,  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ,  tom.engine.adt.code.types.bqterm.SymbolOf.make(freshSubject) , tom_aType) );
int counter = 0;          
// for each slot

{
{
if ( (tom_slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {
if ( (((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom_slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {
 tom.engine.adt.tomslot.types.SlotList  tomMatch221__end__4=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);
do {
{
if (!( tomMatch221__end__4.isEmptyconcSlot() )) {
 tom.engine.adt.tomslot.types.Slot  tomMatch221_9= tomMatch221__end__4.getHeadconcSlot() ;
if ( (tomMatch221_9 instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

BQTerm freshVar = freshVarList.get(counter);          
andForName.add(
 tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(freshVar),  tom.engine.adt.code.types.bqterm.Subterm.make(tom_name,  tomMatch221_9.getSlotName() , freshSubject) , tom_aType) );
counter++;


}
}
if ( tomMatch221__end__4.isEmptyconcSlot() ) {
tomMatch221__end__4=(( tom.engine.adt.tomslot.types.SlotList )tom_slots);
} else {
tomMatch221__end__4= tomMatch221__end__4.getTailconcSlot() ;
}

}
} while(!( (tomMatch221__end__4==(( tom.engine.adt.tomslot.types.SlotList )tom_slots)) ));
}
}

}

}
// match slots
l = 
tom_append_list_OrConstraintDisjunction(l, tom.engine.adt.tomconstraint.types.constraint.ConsOrConstraintDisjunction.make(ASTFactory.makeAndConstraint(andForName), tom.engine.adt.tomconstraint.types.constraint.EmptyOrConstraintDisjunction.make() ) );


}
if ( tomMatch220__end__4.isEmptyconcTomName() ) {
tomMatch220__end__4=(( tom.engine.adt.tomname.types.TomNameList )tom_nameList);
} else {
tomMatch220__end__4= tomMatch220__end__4.getTailconcTomName() ;
}

}
} while(!( (tomMatch220__end__4==(( tom.engine.adt.tomname.types.TomNameList )tom_nameList)) ));
}
}

}

}

lastPart.add(0,l);
lastPart.add(0,
 tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(freshSubject), tom_g, tom_aType) );
lastPart.add(sp.getConstraintPropagator().performDetach(
tom_m));
return ASTFactory.makeAndConstraint(lastPart);
//return `AndConstraint(MatchConstraint(freshSubject,g,aType),l,lastPart*,sp.getConstraintPropagator().performDetach(m));


}

}
}
}
}
}
}

}

}
return _visit_Constraint(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_SyntacticPatternMatching( SyntacticPropagator  t0) { 
return new SyntacticPatternMatching(t0);
}
// end %strategy
}
