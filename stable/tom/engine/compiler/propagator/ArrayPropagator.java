
























package tom.engine.compiler.propagator;



import tom.engine.compiler.propagator.*;  



import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.code.types.*;
import tom.engine.adt.tomterm.types.tomterm.*;
import tom.library.sl.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.compiler.*;
import tom.engine.exception.TomRuntimeException;
import tom.engine.TomBase;
import tom.engine.compiler.Compiler;






public class ArrayPropagator implements IBasePropagator {

  
     private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.Constraint  tom_append_list_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  l1,  tom.engine.adt.tomconstraint.types.Constraint  l2) {     if( l1.isEmptyAndConstraint() ) {       return l2;     } else if( l2.isEmptyAndConstraint() ) {       return l1;     } else if( ((l1 instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (l1 instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) ) {       if(  l1.getTailAndConstraint() .isEmptyAndConstraint() ) {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,l2) ;       } else {         return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( l1.getHeadAndConstraint() ,tom_append_list_AndConstraint( l1.getTailAndConstraint() ,l2)) ;       }     } else {       return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(l1,l2) ;     }   }   private static   tom.engine.adt.tomconstraint.types.Constraint  tom_get_slice_AndConstraint( tom.engine.adt.tomconstraint.types.Constraint  begin,  tom.engine.adt.tomconstraint.types.Constraint  end, tom.engine.adt.tomconstraint.types.Constraint  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyAndConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getHeadAndConstraint() :begin),( tom.engine.adt.tomconstraint.types.Constraint )tom_get_slice_AndConstraint((( ((begin instanceof tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint) || (begin instanceof tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint)) )? begin.getTailAndConstraint() : tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ),end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {     if( l1.isEmptyconcConstraint() ) {       return l2;     } else if( l2.isEmptyconcConstraint() ) {       return l1;     } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;     } else {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;     }   }   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Sequence )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;       } else {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Sequence.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Choice )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;       } else {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Choice.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.SequenceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.SequenceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.ChoiceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.ChoiceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;   }   private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ));}private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { return (  tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) )  );}private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}  private static Strategy makeTopDownWhenConstraint(Strategy s) {   return ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( makeWhenConstraint( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) )) );  }    public static class WhenConstraint extends tom.library.sl.AbstractStrategyBasic {    private  tom.library.sl.Strategy  s;      public WhenConstraint( tom.library.sl.Strategy  s) {     super(( new tom.library.sl.Identity() ));     this.s=s;   }      public  tom.library.sl.Strategy  gets() {     return s;   }    public tom.library.sl.Visitable[] getChildren() {     tom.library.sl.Visitable[] stratChildren = new tom.library.sl.Visitable[getChildCount()];     stratChildren[0] = super.getChildAt(0);     stratChildren[1] = gets();     return stratChildren;   }    public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {     super.setChildAt(0, children[0]);     s = ( tom.library.sl.Strategy ) children[1];     return this;   }    public int getChildCount() {     return 2;   }    public tom.library.sl.Visitable getChildAt(int index) {     switch (index) {       case 0: return super.getChildAt(0);       case 1: return gets();       default: throw new IndexOutOfBoundsException();      }   }    public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {     switch (index) {       case 0: return super.setChildAt(0, child);       case 1: s = ( tom.library.sl.Strategy )child;               return this;       default: throw new IndexOutOfBoundsException();     }   }    public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {     if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {       return s.visitLight(v,introspector);     }     return any.visitLight(v,introspector);   }    }    private static  tom.library.sl.Strategy  makeWhenConstraint( tom.library.sl.Strategy  t0) { return new WhenConstraint(t0);}     














  private Compiler compiler;
  private ConstraintPropagator constraintPropagator; 
  private GeneralPurposePropagator generalPurposePropagator;  

  public ArrayPropagator(Compiler myCompiler, ConstraintPropagator myConstraintPropagator) {
    this.compiler = myCompiler;
    this.constraintPropagator = myConstraintPropagator; 
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
    return ( makeTopDownWhenConstraint( new ArrayPatternMatching(this) ) ).visitLight(constraint);		
  }	

  public static class ArrayPatternMatching extends tom.library.sl.AbstractStrategyBasic {private  ArrayPropagator  ap;public ArrayPatternMatching( ArrayPropagator  ap) {super(( new tom.library.sl.Identity() ));this.ap=ap;}public  ArrayPropagator  getap() {return ap;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChildren = new tom.library.sl.Visitable[getChildCount()];stratChildren[0] = super.getChildAt(0);return stratChildren;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {return ((T)visit_Constraint((( tom.engine.adt.tomconstraint.types.Constraint )v),introspector));}if (!(( null  == environment))) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomconstraint.types.Constraint  _visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(( null  == environment))) {return (( tom.engine.adt.tomconstraint.types.Constraint )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomconstraint.types.Constraint  visit_Constraint( tom.engine.adt.tomconstraint.types.Constraint  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{ /* unamed block */{ /* unamed block */if ( (tom__arg instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tom__arg) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch226_1= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getPattern() ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )tomMatch226_1) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomname.types.TomNameList  tomMatch226_4= tomMatch226_1.getNameList() ; tom.engine.adt.tomslot.types.SlotList  tomMatch226_5= tomMatch226_1.getSlots() ;if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch226_4) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch226_4) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch226_4.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tomMatch226_11= tomMatch226_4.getHeadconcTomName() ;if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch226_11) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if (  tomMatch226_4.getTailconcTomName() .isEmptyconcTomName() ) { tom.engine.adt.tomconstraint.types.Constraint  tom___m=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg);boolean tomMatch226_14= false ;if ( (((( tom.engine.adt.tomslot.types.SlotList )tomMatch226_5) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tomMatch226_5) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( tomMatch226_5.isEmptyconcSlot() ) {tomMatch226_14= true ;}}if (!(tomMatch226_14)) {











        if(TomBase.hasTheory(tomMatch226_1, tom.engine.adt.theory.types.elementarytheory.AC.make() )) { /* unamed block */
          return tom___m;
        }
        
        if(!TomBase.isArrayOperator(ap.getCompiler().getSymbolTable().
              getSymbolFromName( tomMatch226_11.getString() ))) { /* unamed block */ return tom___m; }
        Constraint detachedConstr = ap.getGeneralPurposePropagator().detachSublists(tom___m);
        
        if (detachedConstr != tom___m) { /* unamed block */ return detachedConstr; }}}}}}}}}}{ /* unamed block */if ( (tom__arg instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tom__arg) instanceof tom.engine.adt.tomconstraint.types.constraint.MatchConstraint) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch226_16= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getPattern() ; tom.engine.adt.code.types.BQTerm  tomMatch226_17= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getSubject() ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )tomMatch226_16) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomname.types.TomNameList  tomMatch226_22= tomMatch226_16.getNameList() ;if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch226_22) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch226_22) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch226_22.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tomMatch226_31= tomMatch226_22.getHeadconcTomName() ;if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch226_31) instanceof tom.engine.adt.tomname.types.tomname.Name) ) { tom.engine.adt.tomname.types.TomName  tom___name= tomMatch226_22.getHeadconcTomName() ; tom.engine.adt.tomslot.types.SlotList  tom___slots= tomMatch226_16.getSlots() ; tom.engine.adt.code.types.BQTerm  tom___g=tomMatch226_17; tom.engine.adt.tomtype.types.TomType  tom___aType= (( tom.engine.adt.tomconstraint.types.Constraint )tom__arg).getAstType() ; tom.engine.adt.tomconstraint.types.Constraint  tom___m=(( tom.engine.adt.tomconstraint.types.Constraint )tom__arg);boolean tomMatch226_36= false ;if ( ((( tom.engine.adt.code.types.BQTerm )tomMatch226_17) instanceof tom.engine.adt.code.types.bqterm.SymbolOf) ) {if ( (tom___g==tomMatch226_17) ) {tomMatch226_36= true ;}}if (!(tomMatch226_36)) {























      
        if(TomBase.hasTheory(tomMatch226_16, tom.engine.adt.theory.types.elementarytheory.AC.make() )) { /* unamed block */
          return tom___m;
        }
        TomSymbol symb = ap.getCompiler().getSymbolTable().getSymbolFromName( tomMatch226_31.getString() );

        
        if(!TomBase.isArrayOperator(symb)) { /* unamed block */return tom___m;}        

        TomType slotType =
          symb.getTypesToType().getDomain().getHeadconcTomType();
        
        
        
        BQTerm freshVariable = ap.getCompiler().getFreshVariableStar(tom___aType);
        
        Constraint freshVarDeclaration =
           tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(freshVariable), tom___g, tom___aType) ;

        
        BQTerm freshIndex = ap.getFreshIndex();				
        TomType freshIndexType = ap.getCompiler().getTermTypeFromTerm(freshIndex);
        Constraint freshIndexDeclaration =
           tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(freshIndex),  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Integer.make(0) ) , freshIndexType) 
;
        Constraint l =  tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ;
match:  { /* unamed block */{ /* unamed block */if ( (tom___slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tom___slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom___slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )tom___slots).isEmptyconcSlot() ) {

            l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom___name, freshVariable, freshIndex) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) );
          }}}}{ /* unamed block */if ( (tom___slots instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tom___slots) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tom___slots) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) { tom.engine.adt.tomslot.types.SlotList  tomMatch227_end_6=(( tom.engine.adt.tomslot.types.SlotList )tom___slots);do {{ /* unamed block */if (!( tomMatch227_end_6.isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch227_10= tomMatch227_end_6.getHeadconcSlot() ;if ( ((( tom.engine.adt.tomslot.types.Slot )tomMatch227_10) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) { tom.engine.adt.tomterm.types.TomTerm  tom___appl= tomMatch227_10.getAppl() ; tom.engine.adt.tomslot.types.SlotList  tom___X= tomMatch227_end_6.getTailconcSlot() ;

            BQTerm newFreshIndex = ap.getFreshIndex();                
mAppl:{ /* unamed block */{ /* unamed block */if ( (tom___appl instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom___appl) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {


          
          if(tom___X.length() == 0) { /* unamed block */
            
            l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom___appl,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetSliceArray.make(tom___name, freshVariable, freshIndex,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetSize.make(tom___name, freshVariable) ) ) ) , tom___aType) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) )
;
          } else { /* unamed block */
            BQTerm beginIndex = ap.getBeginIndex();
            BQTerm endIndex = ap.getEndIndex();
            l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(beginIndex), freshIndex, tom___aType) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(endIndex), freshIndex, tom___aType) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom___appl,  tom.engine.adt.code.types.bqterm.VariableHeadArray.make(tom___name, freshVariable, beginIndex, endIndex) , tom___aType) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(newFreshIndex), endIndex, tom___aType) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) ) ) ) )



;     
          }
          break mAppl;
        }}}{ /* unamed block */if ( (tom___appl instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
                    
          TomType applType = ap.getCompiler().getTermTypeFromTerm(tom___appl);
          l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.Negate.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom___name, freshVariable, freshIndex) ) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(tom___appl,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetElement.make(tom___name, slotType, freshVariable, freshIndex) ) , applType) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make(TomBase.convertFromBQVarToVar(newFreshIndex),  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.AddOne.make(freshIndex) ) , freshIndexType) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) ) ) )


;
          
          if(tom___X.length() == 0) { /* unamed block */                  
            l = tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.EmptyArrayConstraint.make(tom___name, freshVariable, newFreshIndex) , tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() ) );
          }}}}


      freshIndex = newFreshIndex;
          }}if ( tomMatch227_end_6.isEmptyconcSlot() ) {tomMatch227_end_6=(( tom.engine.adt.tomslot.types.SlotList )tom___slots);} else {tomMatch227_end_6= tomMatch227_end_6.getTailconcSlot() ;}}} while(!( (tomMatch227_end_6==(( tom.engine.adt.tomslot.types.SlotList )tom___slots)) ));}}}}

        
        l =
           tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(freshVarDeclaration, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make( tom.engine.adt.tomconstraint.types.constraint.MatchConstraint.make( tom.engine.adt.tomterm.types.tomterm.RecordAppl.make( tomMatch226_16.getOptions() , tomMatch226_22,  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ,  tom.engine.adt.code.types.bqterm.SymbolOf.make(freshVariable) , tom___aType) , tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(freshIndexDeclaration, tom.engine.adt.tomconstraint.types.constraint.ConsAndConstraint.make(ap.getConstraintPropagator().performDetach(tom___m),tom_append_list_AndConstraint(l, tom.engine.adt.tomconstraint.types.constraint.EmptyAndConstraint.make() )) ) ) ) 
;
        return l;
      }}}}}}}}}return _visit_Constraint(tom__arg,introspector);}}



  private BQTerm getBeginIndex() {
    return getCompiler().getBeginVariableStar(getCompiler().getSymbolTable().getIntType());
  }

  private BQTerm getEndIndex() {
    return getCompiler().getEndVariableStar(getCompiler().getSymbolTable().getIntType());
  }

  private BQTerm getFreshIndex() {
    return getCompiler().getFreshVariableStar(getCompiler().getSymbolTable().getIntType());    
  }
}
