
























package tom.engine;



import java.util.*;
import java.util.logging.Logger;



import aterm.*;



import tom.engine.tools.*;



import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomname.types.tomname.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.theory.types.*;
import tom.engine.adt.code.types.*;



import tom.engine.exception.TomRuntimeException;
import tom.engine.TomMessage;



import tom.platform.adt.platformoption.*;



import tom.library.sl.*;
import tom.library.sl.VisitFailure;








public final class TomBase {

     private static   tom.engine.adt.tomterm.types.TomList  tom_append_list_concTomTerm( tom.engine.adt.tomterm.types.TomList l1,  tom.engine.adt.tomterm.types.TomList  l2) {     if( l1.isEmptyconcTomTerm() ) {       return l2;     } else if( l2.isEmptyconcTomTerm() ) {       return l1;     } else if(  l1.getTailconcTomTerm() .isEmptyconcTomTerm() ) {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,l2) ;     } else {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,tom_append_list_concTomTerm( l1.getTailconcTomTerm() ,l2)) ;     }   }   private static   tom.engine.adt.tomterm.types.TomList  tom_get_slice_concTomTerm( tom.engine.adt.tomterm.types.TomList  begin,  tom.engine.adt.tomterm.types.TomList  end, tom.engine.adt.tomterm.types.TomList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomTerm()  ||  (end== tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( begin.getHeadconcTomTerm() ,( tom.engine.adt.tomterm.types.TomList )tom_get_slice_concTomTerm( begin.getTailconcTomTerm() ,end,tail)) ;   }      private static   tom.engine.adt.tomtype.types.TomTypeList  tom_append_list_concTomType( tom.engine.adt.tomtype.types.TomTypeList l1,  tom.engine.adt.tomtype.types.TomTypeList  l2) {     if( l1.isEmptyconcTomType() ) {       return l2;     } else if( l2.isEmptyconcTomType() ) {       return l1;     } else if(  l1.getTailconcTomType() .isEmptyconcTomType() ) {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,l2) ;     } else {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,tom_append_list_concTomType( l1.getTailconcTomType() ,l2)) ;     }   }   private static   tom.engine.adt.tomtype.types.TomTypeList  tom_get_slice_concTomType( tom.engine.adt.tomtype.types.TomTypeList  begin,  tom.engine.adt.tomtype.types.TomTypeList  end, tom.engine.adt.tomtype.types.TomTypeList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomType()  ||  (end== tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( begin.getHeadconcTomType() ,( tom.engine.adt.tomtype.types.TomTypeList )tom_get_slice_concTomType( begin.getTailconcTomType() ,end,tail)) ;   }      private static   tom.engine.adt.code.types.BQTermList  tom_append_list_concBQTerm( tom.engine.adt.code.types.BQTermList l1,  tom.engine.adt.code.types.BQTermList  l2) {     if( l1.isEmptyconcBQTerm() ) {       return l2;     } else if( l2.isEmptyconcBQTerm() ) {       return l1;     } else if(  l1.getTailconcBQTerm() .isEmptyconcBQTerm() ) {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,tom_append_list_concBQTerm( l1.getTailconcBQTerm() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTermList  tom_get_slice_concBQTerm( tom.engine.adt.code.types.BQTermList  begin,  tom.engine.adt.code.types.BQTermList  end, tom.engine.adt.code.types.BQTermList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcBQTerm()  ||  (end== tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( begin.getHeadconcBQTerm() ,( tom.engine.adt.code.types.BQTermList )tom_get_slice_concBQTerm( begin.getTailconcBQTerm() ,end,tail)) ;   }      private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }      private static   tom.engine.adt.tomname.types.TomNumberList  tom_append_list_concTomNumber( tom.engine.adt.tomname.types.TomNumberList l1,  tom.engine.adt.tomname.types.TomNumberList  l2) {     if( l1.isEmptyconcTomNumber() ) {       return l2;     } else if( l2.isEmptyconcTomNumber() ) {       return l1;     } else if(  l1.getTailconcTomNumber() .isEmptyconcTomNumber() ) {       return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( l1.getHeadconcTomNumber() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( l1.getHeadconcTomNumber() ,tom_append_list_concTomNumber( l1.getTailconcTomNumber() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNumberList  tom_get_slice_concTomNumber( tom.engine.adt.tomname.types.TomNumberList  begin,  tom.engine.adt.tomname.types.TomNumberList  end, tom.engine.adt.tomname.types.TomNumberList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomNumber()  ||  (end== tom.engine.adt.tomname.types.tomnumberlist.EmptyconcTomNumber.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( begin.getHeadconcTomNumber() ,( tom.engine.adt.tomname.types.TomNumberList )tom_get_slice_concTomNumber( begin.getTailconcTomNumber() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_append_list_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList l1,  tom.engine.adt.tomslot.types.PairNameDeclList  l2) {     if( l1.isEmptyconcPairNameDecl() ) {       return l2;     } else if( l2.isEmptyconcPairNameDecl() ) {       return l1;     } else if(  l1.getTailconcPairNameDecl() .isEmptyconcPairNameDecl() ) {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,tom_append_list_concPairNameDecl( l1.getTailconcPairNameDecl() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_get_slice_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList  begin,  tom.engine.adt.tomslot.types.PairNameDeclList  end, tom.engine.adt.tomslot.types.PairNameDeclList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcPairNameDecl()  ||  (end== tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( begin.getHeadconcPairNameDecl() ,( tom.engine.adt.tomslot.types.PairNameDeclList )tom_get_slice_concPairNameDecl( begin.getTailconcPairNameDecl() ,end,tail)) ;   }      private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {     if( l1.isEmptyconcOption() ) {       return l2;     } else if( l2.isEmptyconcOption() ) {       return l1;     } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;     } else {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end== tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {     if( l1.isEmptyconcConstraint() ) {       return l2;     } else if( l2.isEmptyconcConstraint() ) {       return l1;     } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;     } else {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;     }   }   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;   }      private static   tom.engine.adt.theory.types.Theory  tom_append_list_concElementaryTheory( tom.engine.adt.theory.types.Theory l1,  tom.engine.adt.theory.types.Theory  l2) {     if( l1.isEmptyconcElementaryTheory() ) {       return l2;     } else if( l2.isEmptyconcElementaryTheory() ) {       return l1;     } else if(  l1.getTailconcElementaryTheory() .isEmptyconcElementaryTheory() ) {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,l2) ;     } else {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,tom_append_list_concElementaryTheory( l1.getTailconcElementaryTheory() ,l2)) ;     }   }   private static   tom.engine.adt.theory.types.Theory  tom_get_slice_concElementaryTheory( tom.engine.adt.theory.types.Theory  begin,  tom.engine.adt.theory.types.Theory  end, tom.engine.adt.theory.types.Theory  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcElementaryTheory()  ||  (end== tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( begin.getHeadconcElementaryTheory() ,( tom.engine.adt.theory.types.Theory )tom_get_slice_concElementaryTheory( begin.getTailconcElementaryTheory() ,end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Sequence )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;       } else {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Sequence.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Choice )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;       } else {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Choice.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.SequenceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.SequenceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.ChoiceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.ChoiceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;   }   private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ));}private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { return (  tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) )  );}private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDownCollect( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),tom_make_Try( tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) )) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}









  public final static String DEFAULT_MODULE_NAME = "default";
  
  private final static int LRUCACHE_SIZE = 5000;

  private static Logger logger = Logger.getLogger("tom.engine.TomBase");

  

  
  public static String getTomType(TomType type) {
    { /* unamed block */{ /* unamed block */if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 return  (( tom.engine.adt.tomtype.types.TomType )type).getTomType() ; }}}{ /* unamed block */if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.EmptyType) ) {
 return null; }}if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.TypeVar) ) { return null; }}}}

    throw new TomRuntimeException("getTomType error on term: " + type);
  }

  
  public static String getTLType(TomType type) {
    { /* unamed block */{ /* unamed block */if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 return getTLCode( (( tom.engine.adt.tomtype.types.TomType )type).getTlType() ); }}}}

    throw new TomRuntimeException("getTLType error on term: " + type);
  }

  
  public static String getTLCode(TargetLanguageType type) {
    { /* unamed block */{ /* unamed block */if ( (type instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ) {if ( ((( tom.engine.adt.tomtype.types.TargetLanguageType )type) instanceof tom.engine.adt.tomtype.types.targetlanguagetype.TLType) ) {
 return  (( tom.engine.adt.tomtype.types.TargetLanguageType )type).getString() ; }}}}

    throw new TomRuntimeException("getTLCode error on term: " + type);
  }

  
  public static TomType getSymbolCodomain(TomSymbol symbol) {
    if(symbol!=null) {
      return symbol.getTypesToType().getCodomain();
    } else {
      return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
    }
  }

  
  public static TomTypeList getSymbolDomain(TomSymbol symbol) {
    if(symbol!=null) {
      return symbol.getTypesToType().getDomain();
    } else {
      return  tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType.make() ;
    }
  }

  private static LRUCache<TomNumberList,String> tomNumberListToStringMap =
    new LRUCache<TomNumberList,String>(LRUCACHE_SIZE);
  public static String tomNumberListToString(TomNumberList numberList) {
    String result = tomNumberListToStringMap.get(numberList);
    if(result == null) {
      TomNumberList key = numberList;
      StringBuilder buf = new StringBuilder(30);
      while(!numberList.isEmptyconcTomNumber()) {
        TomNumber number = numberList.getHeadconcTomNumber();
        numberList = numberList.getTailconcTomNumber();
        { /* unamed block */{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.Position) ) {

            buf.append("Position");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.MatchNumber) ) {

            buf.append("Match");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.PatternNumber) ) {

            buf.append("Pattern");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.ListNumber) ) {

            buf.append("List");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.IndexNumber) ) {

            buf.append("Index");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.Begin) ) {

            buf.append("Begin");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.End) ) {

            buf.append("End");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.Save) ) {

            buf.append("Save");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.AbsVar) ) {

            buf.append("AbsVar");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )number).getInteger() ));
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.RenamedVar) ) { tom.engine.adt.tomname.types.TomName  tom___tomName= (( tom.engine.adt.tomname.types.TomNumber )number).getAstName() ;

            String identifier = "Empty";
            { /* unamed block */{ /* unamed block */if ( (tom___tomName instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tom___tomName) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

                identifier =  (( tom.engine.adt.tomname.types.TomName )tom___tomName).getString() ;
              }}}{ /* unamed block */if ( (tom___tomName instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tom___tomName) instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

                identifier = tomNumberListToString( (( tom.engine.adt.tomname.types.TomName )tom___tomName).getNumberList() );
              }}}}

            buf.append("RenamedVar");
            buf.append(identifier);
          }}}{ /* unamed block */if ( (number instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )number) instanceof tom.engine.adt.tomname.types.tomnumber.NameNumber) ) { tom.engine.adt.tomname.types.TomName  tom___tomName= (( tom.engine.adt.tomname.types.TomNumber )number).getAstName() ;

            String identifier = "Empty";
            { /* unamed block */{ /* unamed block */if ( (tom___tomName instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tom___tomName) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

                identifier =  (( tom.engine.adt.tomname.types.TomName )tom___tomName).getString() ;
              }}}{ /* unamed block */if ( (tom___tomName instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tom___tomName) instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

                identifier = tomNumberListToString( (( tom.engine.adt.tomname.types.TomName )tom___tomName).getNumberList() );
              }}}}

            
            buf.append(identifier);
          }}}}

      }
      result = buf.toString();
      tomNumberListToStringMap.put(key,result);
    }
    return result;
  }

  
  public static boolean isListOperator(TomSymbol symbol) {
    if(symbol==null) {
      return false;
    }
    { /* unamed block */{ /* unamed block */if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        OptionList optionList =  (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getOptions() ;
        while(!optionList.isEmptyconcOption()) { /* unamed block */
          Option opt = optionList.getHeadconcOption();
          { /* unamed block */{ /* unamed block */if ( (opt instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )opt) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration ) (( tom.engine.adt.tomoption.types.Option )opt).getAstDeclaration() ) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyList) ) {
 return true; }}}}{ /* unamed block */if ( (opt instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )opt) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration ) (( tom.engine.adt.tomoption.types.Option )opt).getAstDeclaration() ) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddList) ) {
 return true; }}}}}

          optionList = optionList.getTailconcOption();
        }
        return false;
      }}}}

    throw new TomRuntimeException("isListOperator -- strange case: '" + symbol + "'");
  }

  
  public static boolean isArrayOperator(TomSymbol symbol) {
    if(symbol==null) {
      return false;
    }
    { /* unamed block */{ /* unamed block */if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        OptionList optionList =  (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getOptions() ;
        while(!optionList.isEmptyconcOption()) { /* unamed block */
          Option opt = optionList.getHeadconcOption();
          { /* unamed block */{ /* unamed block */if ( (opt instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )opt) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration ) (( tom.engine.adt.tomoption.types.Option )opt).getAstDeclaration() ) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyArray) ) {
 return true; }}}}{ /* unamed block */if ( (opt instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )opt) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration ) (( tom.engine.adt.tomoption.types.Option )opt).getAstDeclaration() ) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddArray) ) {
 return true; }}}}}

          optionList = optionList.getTailconcOption();
        }
        return false;
      }}}}

    throw new TomRuntimeException("isArrayOperator -- strange case: '" + symbol + "'");
  }

  
  public static boolean isSyntacticOperator(TomSymbol subject) {
    return (!(isListOperator(subject) || isArrayOperator(subject)));
  }

  
  
  public static void collectVariable(Collection<TomTerm> collection, 
          tom.library.sl.Visitable subject, boolean considerBQVars) {
    try {
      
      tom_make_TopDownCollect( new collectVariable(collection,considerBQVars) ).visitLight(subject);
    } catch(VisitFailure e) {
      throw new TomRuntimeException("Should not be there");
    }
  }

  public static class collectVariable extends tom.library.sl.AbstractStrategyBasic {private  java.util.Collection  collection;private  boolean  considerBQVars;public collectVariable( java.util.Collection  collection,  boolean  considerBQVars) {super(( new tom.library.sl.Identity() ));this.collection=collection;this.considerBQVars=considerBQVars;}public  java.util.Collection  getcollection() {return collection;}public  boolean  getconsiderBQVars() {return considerBQVars;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChildren = new tom.library.sl.Visitable[getChildCount()];stratChildren[0] = super.getChildAt(0);return stratChildren;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.engine.adt.code.types.BQTerm) ) {return ((T)visit_BQTerm((( tom.engine.adt.code.types.BQTerm )v),introspector));}if ( (v instanceof tom.engine.adt.tomterm.types.TomTerm) ) {return ((T)visit_TomTerm((( tom.engine.adt.tomterm.types.TomTerm )v),introspector));}if (!(  null ==environment )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomterm.types.TomTerm  _visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.engine.adt.tomterm.types.TomTerm )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.code.types.BQTerm  _visit_BQTerm( tom.engine.adt.code.types.BQTerm  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.engine.adt.code.types.BQTerm )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{ /* unamed block */{ /* unamed block */if ( (tom__arg instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch28_5= false ; tom.engine.adt.tomconstraint.types.ConstraintList  tomMatch28_1= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch28_4= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch28_3= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom__arg) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{ /* unamed block */tomMatch28_5= true ;tomMatch28_3=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg);tomMatch28_1= tomMatch28_3.getConstraints() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom__arg) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{ /* unamed block */tomMatch28_5= true ;tomMatch28_4=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg);tomMatch28_1= tomMatch28_4.getConstraints() ;}}}if (tomMatch28_5) { tom.engine.adt.tomterm.types.TomTerm  tom___v=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg);








        collection.add(tom___v);
        TomTerm annotedVariable = getAliasToVariable(tomMatch28_1);
        if(annotedVariable!=null) { /* unamed block */
          collection.add(annotedVariable);
        }( new tom.library.sl.Fail() )
.visitLight(tom___v);
      }}}{ /* unamed block */if ( (tom__arg instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom__arg) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {



        collectVariable(collection, (( tom.engine.adt.tomterm.types.TomTerm )tom__arg).getSlots() ,considerBQVars);
        TomTerm annotedVariable = getAliasToVariable( (( tom.engine.adt.tomterm.types.TomTerm )tom__arg).getConstraints() );
        if(annotedVariable!=null) { /* unamed block */
          collection.add(annotedVariable);
        }( new tom.library.sl.Fail() )
.visitLight((( tom.engine.adt.tomterm.types.TomTerm )tom__arg));
      }}}}return _visit_TomTerm(tom__arg,introspector);}@SuppressWarnings("unchecked")public  tom.engine.adt.code.types.BQTerm  visit_BQTerm( tom.engine.adt.code.types.BQTerm  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{ /* unamed block */{ /* unamed block */if ( (tom__arg instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )tom__arg) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {           if (considerBQVars) { /* unamed block */ collection.add(convertFromBQVarToVar((( tom.engine.adt.code.types.BQTerm )tom__arg))); }}}}}return _visit_BQTerm(tom__arg,introspector);}}




  
  public static Map<TomName,Integer> collectMultiplicity(tom.library.sl.Visitable subject) {
    
    Collection<TomTerm> variableList = new HashSet<TomTerm>();
    collectVariable(variableList,subject,true);
    
    HashMap<TomName,Integer> multiplicityMap = new HashMap<TomName,Integer>();
    for(TomTerm variable:variableList) {
      TomName name = variable.getAstName();
      if(multiplicityMap.containsKey(name)) {
        int value = multiplicityMap.get(name);
        multiplicityMap.put(name, 1+value);
      } else {
        multiplicityMap.put(name, 1);
      }
    }
    return multiplicityMap;
  }

  private static TomTerm getAliasToVariable(ConstraintList constraintList) {
    { /* unamed block */{ /* unamed block */if ( (constraintList instanceof tom.engine.adt.tomconstraint.types.ConstraintList) ) {if ( (((( tom.engine.adt.tomconstraint.types.ConstraintList )constraintList) instanceof tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint) || ((( tom.engine.adt.tomconstraint.types.ConstraintList )constraintList) instanceof tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint)) ) { tom.engine.adt.tomconstraint.types.ConstraintList  tomMatch30_end_4=(( tom.engine.adt.tomconstraint.types.ConstraintList )constraintList);do {{ /* unamed block */if (!( tomMatch30_end_4.isEmptyconcConstraint() )) { tom.engine.adt.tomconstraint.types.Constraint  tomMatch30_8= tomMatch30_end_4.getHeadconcConstraint() ;if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tomMatch30_8) instanceof tom.engine.adt.tomconstraint.types.constraint.AliasTo) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch30_7= tomMatch30_8.getVar() ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )tomMatch30_7) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {
 return tomMatch30_7; }}}if ( tomMatch30_end_4.isEmptyconcConstraint() ) {tomMatch30_end_4=(( tom.engine.adt.tomconstraint.types.ConstraintList )constraintList);} else {tomMatch30_end_4= tomMatch30_end_4.getTailconcConstraint() ;}}} while(!( (tomMatch30_end_4==(( tom.engine.adt.tomconstraint.types.ConstraintList )constraintList)) ));}}}}

    return null;
  }

  public static boolean hasTheory(TomTerm term, ElementaryTheory elementaryTheory) {
    return hasTheory(getTheory(term),elementaryTheory);
  }

  public static boolean hasTheory(Theory theory, ElementaryTheory elementaryTheory) {
    { /* unamed block */{ /* unamed block */if ( (theory instanceof tom.engine.adt.theory.types.Theory) ) {if ( (((( tom.engine.adt.theory.types.Theory )theory) instanceof tom.engine.adt.theory.types.theory.ConsconcElementaryTheory) || ((( tom.engine.adt.theory.types.Theory )theory) instanceof tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory)) ) { tom.engine.adt.theory.types.Theory  tomMatch31_end_4=(( tom.engine.adt.theory.types.Theory )theory);do {{ /* unamed block */if (!( tomMatch31_end_4.isEmptyconcElementaryTheory() )) {
 if( tomMatch31_end_4.getHeadconcElementaryTheory() ==elementaryTheory) { /* unamed block */ return true; }}if ( tomMatch31_end_4.isEmptyconcElementaryTheory() ) {tomMatch31_end_4=(( tom.engine.adt.theory.types.Theory )theory);} else {tomMatch31_end_4= tomMatch31_end_4.getTailconcElementaryTheory() ;}}} while(!( (tomMatch31_end_4==(( tom.engine.adt.theory.types.Theory )theory)) ));}}}}

    return false;
  }

  public static Theory getTheory(TomTerm term) {
    { /* unamed block */{ /* unamed block */if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch32_1= (( tom.engine.adt.tomterm.types.TomTerm )term).getOptions() ;if ( (((( tom.engine.adt.tomoption.types.OptionList )tomMatch32_1) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )tomMatch32_1) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch32_end_7=tomMatch32_1;do {{ /* unamed block */if (!( tomMatch32_end_7.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch32_11= tomMatch32_end_7.getHeadconcOption() ;if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch32_11) instanceof tom.engine.adt.tomoption.types.option.MatchingTheory) ) {
 return  tomMatch32_11.getTheory() ; }}if ( tomMatch32_end_7.isEmptyconcOption() ) {tomMatch32_end_7=tomMatch32_1;} else {tomMatch32_end_7= tomMatch32_end_7.getTailconcOption() ;}}} while(!( (tomMatch32_end_7==tomMatch32_1) ));}}}}}

    return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( tom.engine.adt.theory.types.elementarytheory.Syntactic.make() , tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) ;
  }

  public static Theory getTheory(OptionList optionList) {
    { /* unamed block */{ /* unamed block */if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch33_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);do {{ /* unamed block */if (!( tomMatch33_end_4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch33_8= tomMatch33_end_4.getHeadconcOption() ;if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch33_8) instanceof tom.engine.adt.tomoption.types.option.MatchingTheory) ) {
 return  tomMatch33_8.getTheory() ; }}if ( tomMatch33_end_4.isEmptyconcOption() ) {tomMatch33_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);} else {tomMatch33_end_4= tomMatch33_end_4.getTailconcOption() ;}}} while(!( (tomMatch33_end_4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));}}}}

    return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( tom.engine.adt.theory.types.elementarytheory.Syntactic.make() , tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) ;
  }

  public static Declaration getIsFsymDecl(OptionList optionList) {
    { /* unamed block */{ /* unamed block */if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch34_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);do {{ /* unamed block */if (!( tomMatch34_end_4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch34_8= tomMatch34_end_4.getHeadconcOption() ;if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch34_8) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch34_7= tomMatch34_8.getAstDeclaration() ;if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch34_7) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl) ) {
 return tomMatch34_7; }}}if ( tomMatch34_end_4.isEmptyconcOption() ) {tomMatch34_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);} else {tomMatch34_end_4= tomMatch34_end_4.getTailconcOption() ;}}} while(!( (tomMatch34_end_4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));}}}}

    return null;
  }

  public static boolean hasIsFsymDecl(TomSymbol tomSymbol) {
    { /* unamed block */{ /* unamed block */if ( (tomSymbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )tomSymbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch35_1= (( tom.engine.adt.tomsignature.types.TomSymbol )tomSymbol).getOptions() ;if ( (((( tom.engine.adt.tomoption.types.OptionList )tomMatch35_1) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )tomMatch35_1) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch35_end_7=tomMatch35_1;do {{ /* unamed block */if (!( tomMatch35_end_7.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch35_11= tomMatch35_end_7.getHeadconcOption() ;if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch35_11) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration ) tomMatch35_11.getAstDeclaration() ) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl) ) {

        return true;
      }}}if ( tomMatch35_end_7.isEmptyconcOption() ) {tomMatch35_end_7=tomMatch35_1;} else {tomMatch35_end_7= tomMatch35_end_7.getTailconcOption() ;}}} while(!( (tomMatch35_end_7==tomMatch35_1) ));}}}}}

    return false;
  }

  public static String getModuleName(OptionList optionList) {
    { /* unamed block */{ /* unamed block */if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch36_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);do {{ /* unamed block */if (!( tomMatch36_end_4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch36_8= tomMatch36_end_4.getHeadconcOption() ;if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch36_8) instanceof tom.engine.adt.tomoption.types.option.ModuleName) ) {
 return  tomMatch36_8.getString() ; }}if ( tomMatch36_end_4.isEmptyconcOption() ) {tomMatch36_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);} else {tomMatch36_end_4= tomMatch36_end_4.getTailconcOption() ;}}} while(!( (tomMatch36_end_4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));}}}}

    return null;
  }

  

  public static boolean hasDefinedSymbol(OptionList optionList) {
    { /* unamed block */{ /* unamed block */if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch37_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);do {{ /* unamed block */if (!( tomMatch37_end_4.isEmptyconcOption() )) {if ( ((( tom.engine.adt.tomoption.types.Option ) tomMatch37_end_4.getHeadconcOption() ) instanceof tom.engine.adt.tomoption.types.option.DefinedSymbol) ) {
 return true; }}if ( tomMatch37_end_4.isEmptyconcOption() ) {tomMatch37_end_4=(( tom.engine.adt.tomoption.types.OptionList )optionList);} else {tomMatch37_end_4= tomMatch37_end_4.getTailconcOption() ;}}} while(!( (tomMatch37_end_4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));}}}}

    return false;
  }

  public static TomName getSlotName(TomSymbol symbol, int number) {
    PairNameDeclList pairNameDeclList = symbol.getPairNameDeclList();
    for(int index = 0; !pairNameDeclList.isEmptyconcPairNameDecl() && index<number ; index++) {
      pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
    }
    if(pairNameDeclList.isEmptyconcPairNameDecl()) {
      System.out.println("getSlotName: bad index error");
      throw new TomRuntimeException("getSlotName: bad index error");
    }
    PairNameDecl pairNameDecl = pairNameDeclList.getHeadconcPairNameDecl();

    Declaration decl = pairNameDecl.getSlotDecl();
    { /* unamed block */{ /* unamed block */if ( (decl instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )decl) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetSlotDecl) ) {
 return  (( tom.engine.adt.tomdeclaration.types.Declaration )decl).getSlotName() ; }}}}


    return pairNameDecl.getSlotName();
  }

  public static int getSlotIndex(TomSymbol tomSymbol, TomName slotName) {
    int index = 0;
    PairNameDeclList pairNameDeclList = tomSymbol.getPairNameDeclList();
    ArrayList<String> nameList = new ArrayList<String>();
    while(!pairNameDeclList.isEmptyconcPairNameDecl()) {
      TomName name = pairNameDeclList.getHeadconcPairNameDecl().getSlotName();
      if(slotName.equals(name)) {
        return index;
      }
      nameList.add(name.getString());
      pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
      index++;
    }
    return -1;
  }

  public static TomType elementAt(TomTypeList l, int index) {
    if (0 > index || index > l.length()) {
      throw new IllegalArgumentException("illegal list index: " + index);
    }
    for (int i = 0; i < index; i++) {
      l = l.getTailconcTomType();
    }
    return l.getHeadconcTomType();
  }

  public static TomType getSlotType(TomSymbol symbol, TomName slotName) {
    { /* unamed block */{ /* unamed block */if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomtype.types.TomType  tomMatch39_1= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getTypesToType() ;if ( ((( tom.engine.adt.tomtype.types.TomType )tomMatch39_1) instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {

        int index = getSlotIndex(symbol,slotName);
        return elementAt( tomMatch39_1.getDomain() ,index);
      }}}}}

    return null;
    
  }

  public static boolean isDefinedSymbol(TomSymbol subject) {
    
    { /* unamed block */{ /* unamed block */if ( (subject instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )subject) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        return hasDefinedSymbol( (( tom.engine.adt.tomsignature.types.TomSymbol )subject).getOptions() );
      }}}}

    return false;
  }

  public static boolean isDefinedGetSlot(TomSymbol symbol, TomName slotName) {
    if(symbol==null) {
      
      return false;
    }
    { /* unamed block */{ /* unamed block */if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomslot.types.PairNameDeclList  tomMatch41_1= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getPairNameDeclList() ;if ( (((( tom.engine.adt.tomslot.types.PairNameDeclList )tomMatch41_1) instanceof tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl) || ((( tom.engine.adt.tomslot.types.PairNameDeclList )tomMatch41_1) instanceof tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl)) ) { tom.engine.adt.tomslot.types.PairNameDeclList  tomMatch41_end_7=tomMatch41_1;do {{ /* unamed block */if (!( tomMatch41_end_7.isEmptyconcPairNameDecl() )) { tom.engine.adt.tomslot.types.PairNameDecl  tomMatch41_12= tomMatch41_end_7.getHeadconcPairNameDecl() ;if ( ((( tom.engine.adt.tomslot.types.PairNameDecl )tomMatch41_12) instanceof tom.engine.adt.tomslot.types.pairnamedecl.PairNameDecl) ) {

        if( tomMatch41_12.getSlotName() ==slotName &&  tomMatch41_12.getSlotDecl() != tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration.make() ) { /* unamed block */
          return true;
        }}}if ( tomMatch41_end_7.isEmptyconcPairNameDecl() ) {tomMatch41_end_7=tomMatch41_1;} else {tomMatch41_end_7= tomMatch41_end_7.getTailconcPairNameDecl() ;}}} while(!( (tomMatch41_end_7==tomMatch41_1) ));}}}}}


    return false;
  }


  
  public static Option findOriginTracking(OptionList optionList) {
    if(optionList.isEmptyconcOption()) {
      return  tom.engine.adt.tomoption.types.option.noOption.make() ;
    }
    while(!optionList.isEmptyconcOption()) {
      Option subject = optionList.getHeadconcOption();
      { /* unamed block */{ /* unamed block */if ( (subject instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )subject) instanceof tom.engine.adt.tomoption.types.option.OriginTracking) ) {

          return (( tom.engine.adt.tomoption.types.Option )subject);
        }}}}

      optionList = optionList.getTailconcOption();
    }
    throw new TomRuntimeException("findOriginTracking:  not found: " + optionList);
  }

  public static TomSymbol getSymbolFromName(String tomName, SymbolTable symbolTable) {
    return symbolTable.getSymbolFromName(tomName);
  }

  public static TomType getTermType(TomTerm t, SymbolTable symbolTable) {
    { /* unamed block */{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch43_8= false ; tom.engine.adt.tomname.types.TomNameList  tomMatch43_1= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch43_3= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch43_4= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {{ /* unamed block */tomMatch43_8= true ;tomMatch43_3=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch43_1= tomMatch43_3.getNameList() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {{ /* unamed block */tomMatch43_8= true ;tomMatch43_4=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch43_1= tomMatch43_4.getNameList() ;}}}if (tomMatch43_8) {if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch43_1) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch43_1) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch43_1.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tom___headName= tomMatch43_1.getHeadconcTomName() ;

        String tomName = null;
        if((tom___headName) instanceof AntiName) { /* unamed block */
          tomName = ((AntiName)tom___headName).getName().getString();
        } else { /* unamed block */
          tomName = ((TomName)tom___headName).getString();
        }
        TomSymbol tomSymbol = symbolTable.getSymbolFromName(tomName);
        if(tomSymbol!=null) { /* unamed block */
          return tomSymbol.getTypesToType().getCodomain();
        } else { /* unamed block */
          return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
        }}}}}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch43_14= false ; tom.engine.adt.tomtype.types.TomType  tomMatch43_10= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch43_12= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch43_13= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{ /* unamed block */tomMatch43_14= true ;tomMatch43_12=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch43_10= tomMatch43_12.getAstType() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{ /* unamed block */tomMatch43_14= true ;tomMatch43_13=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch43_10= tomMatch43_13.getAstType() ;}}}if (tomMatch43_14) {



        return tomMatch43_10;
      }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.AntiTerm) ) {

 return getTermType( (( tom.engine.adt.tomterm.types.TomTerm )t).getTomTerm() ,symbolTable);}}}}


    
    throw new TomRuntimeException("getTermType error on term: " + t);
    
  }

  public static TomType getTermType(BQTerm t, SymbolTable symbolTable) {
    { /* unamed block */{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch44_5= false ; tom.engine.adt.code.types.BQTerm  tomMatch44_3= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_4= null ; tom.engine.adt.tomtype.types.TomType  tomMatch44_1= null ;if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {{ /* unamed block */tomMatch44_5= true ;tomMatch44_3=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_1= tomMatch44_3.getAstType() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {{ /* unamed block */tomMatch44_5= true ;tomMatch44_4=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_1= tomMatch44_4.getAstType() ;}}}if (tomMatch44_5) {

        return tomMatch44_1;
      }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.FunctionCall) ) {

 return  (( tom.engine.adt.code.types.BQTerm )t).getAstType() ; }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.ExpressionToBQTerm) ) {

 return getTermType( (( tom.engine.adt.code.types.BQTerm )t).getExp() ,symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.ListHead) ) {

 return  (( tom.engine.adt.code.types.BQTerm )t).getCodomain() ; }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.ListTail) ) {

 return getTermType( (( tom.engine.adt.code.types.BQTerm )t).getVariable() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.Subterm) ) { tom.engine.adt.tomname.types.TomName  tomMatch44_23= (( tom.engine.adt.code.types.BQTerm )t).getAstName() ;if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch44_23) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        TomSymbol tomSymbol = symbolTable.getSymbolFromName( tomMatch44_23.getString() );
        return getSlotType(tomSymbol,  (( tom.engine.adt.code.types.BQTerm )t).getSlotName() );
      }}}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch44_45= false ; tom.engine.adt.code.types.BQTerm  tomMatch44_35= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_39= null ; tom.engine.adt.tomname.types.TomName  tomMatch44_31= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_37= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_41= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_40= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_33= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_36= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_34= null ; tom.engine.adt.code.types.BQTerm  tomMatch44_38= null ;if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BQAppl) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_33=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_33.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildConstant) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_34=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_34.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildTerm) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_35=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_35.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildEmptyList) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_36=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_36.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildConsList) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_37=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_37.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildAppendList) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_38=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_38.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildEmptyArray) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_39=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_39.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildConsArray) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_40=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_40.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BuildAppendArray) ) {{ /* unamed block */tomMatch44_45= true ;tomMatch44_41=(( tom.engine.adt.code.types.BQTerm )t);tomMatch44_31= tomMatch44_41.getAstName() ;}}}}}}}}}}if (tomMatch44_45) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch44_31) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {



          TomSymbol tomSymbol = symbolTable.getSymbolFromName( tomMatch44_31.getString() ); 
          if(tomSymbol!=null) { /* unamed block */
            return tomSymbol.getTypesToType().getCodomain();
          } else { /* unamed block */
            return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
          }}}}}}


    
    throw new TomRuntimeException("getTermType error on term: " + t);
    
  }

  public static TomType getTermType(Expression t, SymbolTable symbolTable) {
    { /* unamed block */{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {boolean tomMatch45_6= false ; tom.engine.adt.tomexpression.types.Expression  tomMatch45_5= null ; tom.engine.adt.tomexpression.types.Expression  tomMatch45_3= null ; tom.engine.adt.tomtype.types.TomType  tomMatch45_1= null ; tom.engine.adt.tomexpression.types.Expression  tomMatch45_4= null ;if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetHead) ) {{ /* unamed block */tomMatch45_6= true ;tomMatch45_3=(( tom.engine.adt.tomexpression.types.Expression )t);tomMatch45_1= tomMatch45_3.getCodomain() ;}} else {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetSlot) ) {{ /* unamed block */tomMatch45_6= true ;tomMatch45_4=(( tom.engine.adt.tomexpression.types.Expression )t);tomMatch45_1= tomMatch45_4.getCodomain() ;}} else {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetElement) ) {{ /* unamed block */tomMatch45_6= true ;tomMatch45_5=(( tom.engine.adt.tomexpression.types.Expression )t);tomMatch45_1= tomMatch45_5.getCodomain() ;}}}}if (tomMatch45_6) {
 return tomMatch45_1; }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.BQTermToExpression) ) {

 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getAstTerm() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.Conditional) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getCond() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.IsFsym) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getVariable() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetTail) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getVariable() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceList) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getVariableBeginAST() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceArray) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getSubjectListName() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.Cast) ) {
 return  (( tom.engine.adt.tomexpression.types.Expression )t).getAstType() ; }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.AddOne) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )t).getVariable() , symbolTable); }}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )t) instanceof tom.engine.adt.tomexpression.types.expression.Integer) ) {
 return symbolTable.getType("int"); }}}}

    System.out.println("getTermType error on term: " + t);
    throw new TomRuntimeException("getTermType error on term: " + t);
  }

  public static TomSymbol getSymbolFromTerm(TomTerm t, SymbolTable symbolTable) {
    { /* unamed block */{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch46_8= false ; tom.engine.adt.tomterm.types.TomTerm  tomMatch46_4= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch46_3= null ; tom.engine.adt.tomname.types.TomNameList  tomMatch46_1= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {{ /* unamed block */tomMatch46_8= true ;tomMatch46_3=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch46_1= tomMatch46_3.getNameList() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {{ /* unamed block */tomMatch46_8= true ;tomMatch46_4=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch46_1= tomMatch46_4.getNameList() ;}}}if (tomMatch46_8) {if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch46_1) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch46_1) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch46_1.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tom___headName= tomMatch46_1.getHeadconcTomName() ;

        String tomName = null;
        if((tom___headName) instanceof AntiName) { /* unamed block */
          tomName = ((AntiName)tom___headName).getName().getString();
        } else { /* unamed block */
          tomName = ((TomName)tom___headName).getString();
        }
        return symbolTable.getSymbolFromName(tomName);
      }}}}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch46_17= false ; tom.engine.adt.tomterm.types.TomTerm  tomMatch46_12= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch46_13= null ; tom.engine.adt.tomname.types.TomName  tomMatch46_10= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{ /* unamed block */tomMatch46_17= true ;tomMatch46_12=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch46_10= tomMatch46_12.getAstName() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{ /* unamed block */tomMatch46_17= true ;tomMatch46_13=(( tom.engine.adt.tomterm.types.TomTerm )t);tomMatch46_10= tomMatch46_13.getAstName() ;}}}if (tomMatch46_17) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch46_10) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        return symbolTable.getSymbolFromName( tomMatch46_10.getString() );
      }}}}{ /* unamed block */if ( (t instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )t) instanceof tom.engine.adt.tomterm.types.tomterm.AntiTerm) ) {

 return getSymbolFromTerm( (( tom.engine.adt.tomterm.types.TomTerm )t).getTomTerm() ,symbolTable);}}}}

    return null;
  }

  public static TomSymbol getSymbolFromTerm(BQTerm t, SymbolTable symbolTable) {
    { /* unamed block */{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch47_8= false ; tom.engine.adt.code.types.BQTerm  tomMatch47_4= null ; tom.engine.adt.code.types.BQTerm  tomMatch47_3= null ; tom.engine.adt.tomname.types.TomName  tomMatch47_1= null ;if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {{ /* unamed block */tomMatch47_8= true ;tomMatch47_3=(( tom.engine.adt.code.types.BQTerm )t);tomMatch47_1= tomMatch47_3.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {{ /* unamed block */tomMatch47_8= true ;tomMatch47_4=(( tom.engine.adt.code.types.BQTerm )t);tomMatch47_1= tomMatch47_4.getAstName() ;}}}if (tomMatch47_8) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch47_1) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

        return symbolTable.getSymbolFromName( tomMatch47_1.getString() );
      }}}}{ /* unamed block */if ( (t instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )t) instanceof tom.engine.adt.code.types.bqterm.FunctionCall) ) { tom.engine.adt.tomname.types.TomName  tomMatch47_10= (( tom.engine.adt.code.types.BQTerm )t).getAstName() ;if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch47_10) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

 return symbolTable.getSymbolFromName( tomMatch47_10.getString() ); }}}}}

    return null;
  }


  public static SlotList tomListToSlotList(TomList tomList) {
    return tomListToSlotList(tomList,1);
  }

  public static SlotList tomListToSlotList(TomList tomList, int index) {
    { /* unamed block */{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if ( (( tom.engine.adt.tomterm.types.TomList )tomList).isEmptyconcTomTerm() ) {
 return  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ; }}}}{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if (!( (( tom.engine.adt.tomterm.types.TomList )tomList).isEmptyconcTomTerm() )) {

        TomName slotName =  tom.engine.adt.tomname.types.tomname.PositionName.make( tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( tom.engine.adt.tomname.types.tomnumber.Position.make(index) , tom.engine.adt.tomname.types.tomnumberlist.EmptyconcTomNumber.make() ) ) ;
        SlotList sl = tomListToSlotList( (( tom.engine.adt.tomterm.types.TomList )tomList).getTailconcTomTerm() ,index+1);
        return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( tom.engine.adt.tomslot.types.slot.PairSlotAppl.make(slotName,  (( tom.engine.adt.tomterm.types.TomList )tomList).getHeadconcTomTerm() ) ,tom_append_list_concSlot(sl, tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() )) ;
      }}}}}

    throw new TomRuntimeException("tomListToSlotList: " + tomList);
  }

  public static SlotList mergeTomListWithSlotList(TomList tomList, SlotList slotList) {
    { /* unamed block */{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if ( (( tom.engine.adt.tomterm.types.TomList )tomList).isEmptyconcTomTerm() ) {if ( (slotList instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )slotList) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )slotList) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )slotList).isEmptyconcSlot() ) {

        return  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ;
      }}}}}}}{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )tomList) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if (!( (( tom.engine.adt.tomterm.types.TomList )tomList).isEmptyconcTomTerm() )) {if ( (slotList instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )slotList) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )slotList) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if (!( (( tom.engine.adt.tomslot.types.SlotList )slotList).isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch49_13= (( tom.engine.adt.tomslot.types.SlotList )slotList).getHeadconcSlot() ;if ( ((( tom.engine.adt.tomslot.types.Slot )tomMatch49_13) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

        SlotList sl = mergeTomListWithSlotList( (( tom.engine.adt.tomterm.types.TomList )tomList).getTailconcTomTerm() , (( tom.engine.adt.tomslot.types.SlotList )slotList).getTailconcSlot() );
        return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( tom.engine.adt.tomslot.types.slot.PairSlotAppl.make( tomMatch49_13.getSlotName() ,  (( tom.engine.adt.tomterm.types.TomList )tomList).getHeadconcTomTerm() ) ,tom_append_list_concSlot(sl, tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() )) ;
      }}}}}}}}}

    throw new TomRuntimeException("mergeTomListWithSlotList: " + tomList + " and " + slotList);
  }

  public static BQTermList slotListToBQTermList(SlotList tomList) {
    { /* unamed block */{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tomList) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tomList) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )tomList).isEmptyconcSlot() ) {
 return  tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ; }}}}{ /* unamed block */if ( (tomList instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )tomList) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )tomList) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if (!( (( tom.engine.adt.tomslot.types.SlotList )tomList).isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch50_7= (( tom.engine.adt.tomslot.types.SlotList )tomList).getHeadconcSlot() ;if ( ((( tom.engine.adt.tomslot.types.Slot )tomMatch50_7) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

        BQTermList tl = slotListToBQTermList( (( tom.engine.adt.tomslot.types.SlotList )tomList).getTailconcSlot() );
        return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(convertFromVarToBQVar( tomMatch50_7.getAppl() ),tom_append_list_concBQTerm(tl, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() )) ;
      }}}}}}

    throw new TomRuntimeException("slotListToTomList: " + tomList);
  }

  public static int getArity(TomSymbol symbol) {
    if (isListOperator(symbol) || isArrayOperator(symbol)) {
      return 2;
    } else {
      return ((Collection) symbol.getPairNameDeclList()).size();
    }
  }

  
  public static BQTerm convertFromVarToBQVar(TomTerm variable) {
    { /* unamed block */{ /* unamed block */if ( (variable instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )variable) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {

        return  tom.engine.adt.code.types.bqterm.BQVariable.make( (( tom.engine.adt.tomterm.types.TomTerm )variable).getOptions() ,  (( tom.engine.adt.tomterm.types.TomTerm )variable).getAstName() ,  (( tom.engine.adt.tomterm.types.TomTerm )variable).getAstType() ) ;
      }}}{ /* unamed block */if ( (variable instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )variable) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {

        return  tom.engine.adt.code.types.bqterm.BQVariableStar.make( (( tom.engine.adt.tomterm.types.TomTerm )variable).getOptions() ,  (( tom.engine.adt.tomterm.types.TomTerm )variable).getAstName() ,  (( tom.engine.adt.tomterm.types.TomTerm )variable).getAstType() ) ;
      }}}}

    throw new TomRuntimeException("cannot convert into a bq variable the term "+variable);
  }

  
  public static TomTerm convertFromBQVarToVar(BQTerm variable) {
    { /* unamed block */{ /* unamed block */if ( (variable instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )variable) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {

        return  tom.engine.adt.tomterm.types.tomterm.Variable.make( (( tom.engine.adt.code.types.BQTerm )variable).getOptions() ,  (( tom.engine.adt.code.types.BQTerm )variable).getAstName() ,  (( tom.engine.adt.code.types.BQTerm )variable).getAstType() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ;
      }}}{ /* unamed block */if ( (variable instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )variable) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {

        return  tom.engine.adt.tomterm.types.tomterm.VariableStar.make( (( tom.engine.adt.code.types.BQTerm )variable).getOptions() ,  (( tom.engine.adt.code.types.BQTerm )variable).getAstName() ,  (( tom.engine.adt.code.types.BQTerm )variable).getAstType() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ;
      }}}}

    throw new TomRuntimeException("cannot convert into a variable the term " + variable);
  }



} 
