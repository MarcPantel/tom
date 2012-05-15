/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2012, INPL, INRIA
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


/**
 * Provides access to the TomSignatureFactory and helper methods.
 */
public final class TomBase {

        private static   tom.engine.adt.tomterm.types.TomList  tom_append_list_concTomTerm( tom.engine.adt.tomterm.types.TomList l1,  tom.engine.adt.tomterm.types.TomList  l2) {     if( l1.isEmptyconcTomTerm() ) {       return l2;     } else if( l2.isEmptyconcTomTerm() ) {       return l1;     } else if(  l1.getTailconcTomTerm() .isEmptyconcTomTerm() ) {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,l2) ;     } else {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,tom_append_list_concTomTerm( l1.getTailconcTomTerm() ,l2)) ;     }   }   private static   tom.engine.adt.tomterm.types.TomList  tom_get_slice_concTomTerm( tom.engine.adt.tomterm.types.TomList  begin,  tom.engine.adt.tomterm.types.TomList  end, tom.engine.adt.tomterm.types.TomList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomTerm()  ||  (end== tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( begin.getHeadconcTomTerm() ,( tom.engine.adt.tomterm.types.TomList )tom_get_slice_concTomTerm( begin.getTailconcTomTerm() ,end,tail)) ;   }      private static   tom.engine.adt.tomtype.types.TomTypeList  tom_append_list_concTomType( tom.engine.adt.tomtype.types.TomTypeList l1,  tom.engine.adt.tomtype.types.TomTypeList  l2) {     if( l1.isEmptyconcTomType() ) {       return l2;     } else if( l2.isEmptyconcTomType() ) {       return l1;     } else if(  l1.getTailconcTomType() .isEmptyconcTomType() ) {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,l2) ;     } else {       return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,tom_append_list_concTomType( l1.getTailconcTomType() ,l2)) ;     }   }   private static   tom.engine.adt.tomtype.types.TomTypeList  tom_get_slice_concTomType( tom.engine.adt.tomtype.types.TomTypeList  begin,  tom.engine.adt.tomtype.types.TomTypeList  end, tom.engine.adt.tomtype.types.TomTypeList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomType()  ||  (end== tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( begin.getHeadconcTomType() ,( tom.engine.adt.tomtype.types.TomTypeList )tom_get_slice_concTomType( begin.getTailconcTomType() ,end,tail)) ;   }      private static   tom.engine.adt.code.types.BQTermList  tom_append_list_concBQTerm( tom.engine.adt.code.types.BQTermList l1,  tom.engine.adt.code.types.BQTermList  l2) {     if( l1.isEmptyconcBQTerm() ) {       return l2;     } else if( l2.isEmptyconcBQTerm() ) {       return l1;     } else if(  l1.getTailconcBQTerm() .isEmptyconcBQTerm() ) {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,l2) ;     } else {       return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,tom_append_list_concBQTerm( l1.getTailconcBQTerm() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.BQTermList  tom_get_slice_concBQTerm( tom.engine.adt.code.types.BQTermList  begin,  tom.engine.adt.code.types.BQTermList  end, tom.engine.adt.code.types.BQTermList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcBQTerm()  ||  (end== tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( begin.getHeadconcBQTerm() ,( tom.engine.adt.code.types.BQTermList )tom_get_slice_concBQTerm( begin.getTailconcBQTerm() ,end,tail)) ;   }      private static   tom.engine.adt.tomname.types.TomNameList  tom_append_list_concTomName( tom.engine.adt.tomname.types.TomNameList l1,  tom.engine.adt.tomname.types.TomNameList  l2) {     if( l1.isEmptyconcTomName() ) {       return l2;     } else if( l2.isEmptyconcTomName() ) {       return l1;     } else if(  l1.getTailconcTomName() .isEmptyconcTomName() ) {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( l1.getHeadconcTomName() ,tom_append_list_concTomName( l1.getTailconcTomName() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNameList  tom_get_slice_concTomName( tom.engine.adt.tomname.types.TomNameList  begin,  tom.engine.adt.tomname.types.TomNameList  end, tom.engine.adt.tomname.types.TomNameList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomName()  ||  (end== tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName.make( begin.getHeadconcTomName() ,( tom.engine.adt.tomname.types.TomNameList )tom_get_slice_concTomName( begin.getTailconcTomName() ,end,tail)) ;   }      private static   tom.engine.adt.tomname.types.TomNumberList  tom_append_list_concTomNumber( tom.engine.adt.tomname.types.TomNumberList l1,  tom.engine.adt.tomname.types.TomNumberList  l2) {     if( l1.isEmptyconcTomNumber() ) {       return l2;     } else if( l2.isEmptyconcTomNumber() ) {       return l1;     } else if(  l1.getTailconcTomNumber() .isEmptyconcTomNumber() ) {       return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( l1.getHeadconcTomNumber() ,l2) ;     } else {       return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( l1.getHeadconcTomNumber() ,tom_append_list_concTomNumber( l1.getTailconcTomNumber() ,l2)) ;     }   }   private static   tom.engine.adt.tomname.types.TomNumberList  tom_get_slice_concTomNumber( tom.engine.adt.tomname.types.TomNumberList  begin,  tom.engine.adt.tomname.types.TomNumberList  end, tom.engine.adt.tomname.types.TomNumberList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomNumber()  ||  (end== tom.engine.adt.tomname.types.tomnumberlist.EmptyconcTomNumber.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( begin.getHeadconcTomNumber() ,( tom.engine.adt.tomname.types.TomNumberList )tom_get_slice_concTomNumber( begin.getTailconcTomNumber() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.SlotList  tom_append_list_concSlot( tom.engine.adt.tomslot.types.SlotList l1,  tom.engine.adt.tomslot.types.SlotList  l2) {     if( l1.isEmptyconcSlot() ) {       return l2;     } else if( l2.isEmptyconcSlot() ) {       return l1;     } else if(  l1.getTailconcSlot() .isEmptyconcSlot() ) {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( l1.getHeadconcSlot() ,tom_append_list_concSlot( l1.getTailconcSlot() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.SlotList  tom_get_slice_concSlot( tom.engine.adt.tomslot.types.SlotList  begin,  tom.engine.adt.tomslot.types.SlotList  end, tom.engine.adt.tomslot.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcSlot()  ||  (end== tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( begin.getHeadconcSlot() ,( tom.engine.adt.tomslot.types.SlotList )tom_get_slice_concSlot( begin.getTailconcSlot() ,end,tail)) ;   }      private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_append_list_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList l1,  tom.engine.adt.tomslot.types.PairNameDeclList  l2) {     if( l1.isEmptyconcPairNameDecl() ) {       return l2;     } else if( l2.isEmptyconcPairNameDecl() ) {       return l1;     } else if(  l1.getTailconcPairNameDecl() .isEmptyconcPairNameDecl() ) {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,l2) ;     } else {       return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( l1.getHeadconcPairNameDecl() ,tom_append_list_concPairNameDecl( l1.getTailconcPairNameDecl() ,l2)) ;     }   }   private static   tom.engine.adt.tomslot.types.PairNameDeclList  tom_get_slice_concPairNameDecl( tom.engine.adt.tomslot.types.PairNameDeclList  begin,  tom.engine.adt.tomslot.types.PairNameDeclList  end, tom.engine.adt.tomslot.types.PairNameDeclList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcPairNameDecl()  ||  (end== tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl.make( begin.getHeadconcPairNameDecl() ,( tom.engine.adt.tomslot.types.PairNameDeclList )tom_get_slice_concPairNameDecl( begin.getTailconcPairNameDecl() ,end,tail)) ;   }      private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {     if( l1.isEmptyconcOption() ) {       return l2;     } else if( l2.isEmptyconcOption() ) {       return l1;     } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;     } else {       return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;     }   }   private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end== tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;   }      private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_append_list_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList l1,  tom.engine.adt.tomconstraint.types.ConstraintList  l2) {     if( l1.isEmptyconcConstraint() ) {       return l2;     } else if( l2.isEmptyconcConstraint() ) {       return l1;     } else if(  l1.getTailconcConstraint() .isEmptyconcConstraint() ) {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,l2) ;     } else {       return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( l1.getHeadconcConstraint() ,tom_append_list_concConstraint( l1.getTailconcConstraint() ,l2)) ;     }   }   private static   tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slice_concConstraint( tom.engine.adt.tomconstraint.types.ConstraintList  begin,  tom.engine.adt.tomconstraint.types.ConstraintList  end, tom.engine.adt.tomconstraint.types.ConstraintList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcConstraint()  ||  (end== tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint.make( begin.getHeadconcConstraint() ,( tom.engine.adt.tomconstraint.types.ConstraintList )tom_get_slice_concConstraint( begin.getTailconcConstraint() ,end,tail)) ;   }      private static   tom.engine.adt.theory.types.Theory  tom_append_list_concElementaryTheory( tom.engine.adt.theory.types.Theory l1,  tom.engine.adt.theory.types.Theory  l2) {     if( l1.isEmptyconcElementaryTheory() ) {       return l2;     } else if( l2.isEmptyconcElementaryTheory() ) {       return l1;     } else if(  l1.getTailconcElementaryTheory() .isEmptyconcElementaryTheory() ) {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,l2) ;     } else {       return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( l1.getHeadconcElementaryTheory() ,tom_append_list_concElementaryTheory( l1.getTailconcElementaryTheory() ,l2)) ;     }   }   private static   tom.engine.adt.theory.types.Theory  tom_get_slice_concElementaryTheory( tom.engine.adt.theory.types.Theory  begin,  tom.engine.adt.theory.types.Theory  end, tom.engine.adt.theory.types.Theory  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcElementaryTheory()  ||  (end== tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( begin.getHeadconcElementaryTheory() ,( tom.engine.adt.theory.types.Theory )tom_get_slice_concElementaryTheory( begin.getTailconcElementaryTheory() ,end,tail)) ;   }         private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Sequence )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;       } else {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Sequence.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Choice )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;       } else {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Choice.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.SequenceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.SequenceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.ChoiceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.ChoiceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;   }      private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ));} private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { return (  tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) )  );}private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDownCollect( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),tom_make_Try( tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) )) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}     









  public final static String DEFAULT_MODULE_NAME = "default";
  //size of cache
  private final static int LRUCACHE_SIZE = 5000;

  private static Logger logger = Logger.getLogger("tom.engine.TomBase");

  /** shortcut */

  /**
   * Returns the name of a <code>TomType</code>
   */
  public static String getTomType(TomType type) {
    {{if ( (((Object)type) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )((Object)type)) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )(( tom.engine.adt.tomtype.types.TomType )((Object)type))) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 return  (( tom.engine.adt.tomtype.types.TomType )((Object)type)).getTomType() ; }}}}{if ( (((Object)type) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )((Object)type)) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )(( tom.engine.adt.tomtype.types.TomType )((Object)type))) instanceof tom.engine.adt.tomtype.types.tomtype.EmptyType) ) {
 return null; }}}if ( (((Object)type) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )((Object)type)) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )(( tom.engine.adt.tomtype.types.TomType )((Object)type))) instanceof tom.engine.adt.tomtype.types.tomtype.TypeVar) ) { return null; }}}}}

    throw new TomRuntimeException("getTomType error on term: " + type);
  }

  /**
   * Returns the implementation-type of a <code>TomType</code>
   */
  public static String getTLType(TomType type) {
    {{if ( (((Object)type) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )((Object)type)) instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )(( tom.engine.adt.tomtype.types.TomType )((Object)type))) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 return getTLCode( (( tom.engine.adt.tomtype.types.TomType )((Object)type)).getTlType() ); }}}}}

    throw new TomRuntimeException("getTLType error on term: " + type);
  }

  /**
   * Returns the implementation-type of a <code>TLType</code>
   */
  public static String getTLCode(TargetLanguageType type) {
    {{if ( (((Object)type) instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ) {if ( ((( tom.engine.adt.tomtype.types.TargetLanguageType )((Object)type)) instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ) {if ( ((( tom.engine.adt.tomtype.types.TargetLanguageType )(( tom.engine.adt.tomtype.types.TargetLanguageType )((Object)type))) instanceof tom.engine.adt.tomtype.types.targetlanguagetype.TLType) ) {
 return  (( tom.engine.adt.tomtype.types.TargetLanguageType )((Object)type)).getString() ; }}}}}

    throw new TomRuntimeException("getTLCode error on term: " + type);
  }

  /**
   * Returns the codomain of a given symbol
   */
  public static TomType getSymbolCodomain(TomSymbol symbol) {
    if(symbol!=null) {
      return symbol.getTypesToType().getCodomain();
    } else {
      return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
    }
  }

  /**
   * Returns the domain of a given symbol
   */
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
        {{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.Position) ) {

            buf.append("Position");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.MatchNumber) ) {

            buf.append("Match");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.PatternNumber) ) {

            buf.append("Pattern");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.ListNumber) ) {

            buf.append("List");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.IndexNumber) ) {

            buf.append("Index");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.Begin) ) {

            buf.append("Begin");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.End) ) {

            buf.append("End");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.Save) ) {

            buf.append("Save");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.AbsVar) ) {

            buf.append("AbsVar");
            buf.append(Integer.toString( (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getInteger() ));
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.RenamedVar) ) { tom.engine.adt.tomname.types.TomName  tom_tomName= (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getAstName() ;

            String identifier = "Empty";
            {{if ( (((Object)tom_tomName) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )(( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName))) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

                identifier =  (( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)).getString() ;
              }}}}{if ( (((Object)tom_tomName) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )(( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName))) instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

                identifier = tomNumberListToString( (( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)).getNumberList() );
              }}}}}

            buf.append("RenamedVar");
            buf.append(identifier);
          }}}}{if ( (((Object)number) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )((Object)number)) instanceof tom.engine.adt.tomname.types.TomNumber) ) {if ( ((( tom.engine.adt.tomname.types.TomNumber )(( tom.engine.adt.tomname.types.TomNumber )((Object)number))) instanceof tom.engine.adt.tomname.types.tomnumber.NameNumber) ) { tom.engine.adt.tomname.types.TomName  tom_tomName= (( tom.engine.adt.tomname.types.TomNumber )((Object)number)).getAstName() ;

            String identifier = "Empty";
            {{if ( (((Object)tom_tomName) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )(( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName))) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

                identifier =  (( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)).getString() ;
              }}}}{if ( (((Object)tom_tomName) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)) instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )(( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName))) instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

                identifier = tomNumberListToString( (( tom.engine.adt.tomname.types.TomName )((Object)tom_tomName)).getNumberList() );
              }}}}}

            //buf.append("NameNumber");
            buf.append(identifier);
          }}}}}

      }
      result = buf.toString();
      tomNumberListToStringMap.put(key,result);
    }
    return result;
  }

  /**
   * Returns <code>true</code> if the symbol corresponds to a %oplist
   */
  public static boolean isListOperator(TomSymbol symbol) {
    if(symbol==null) {
      return false;
    }
    {{if ( (((Object)symbol) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        OptionList optionList =  (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)).getOptions() ;
        while(!optionList.isEmptyconcOption()) {
          Option opt = optionList.getHeadconcOption();
          {{if ( (((Object)opt) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )((Object)opt)) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )(( tom.engine.adt.tomoption.types.Option )((Object)opt))) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch25_1= (( tom.engine.adt.tomoption.types.Option )((Object)opt)).getAstDeclaration() ;if ( (tomMatch25_1 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch25_1) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyList) ) {
 return true; }}}}}}{if ( (((Object)opt) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )((Object)opt)) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )(( tom.engine.adt.tomoption.types.Option )((Object)opt))) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch25_7= (( tom.engine.adt.tomoption.types.Option )((Object)opt)).getAstDeclaration() ;if ( (tomMatch25_7 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch25_7) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddList) ) {
 return true; }}}}}}}

          optionList = optionList.getTailconcOption();
        }
        return false;
      }}}}}

    throw new TomRuntimeException("isListOperator -- strange case: '" + symbol + "'");
  }

  /**
   * Returns <code>true</code> if the symbol corresponds to a %oparray
   */
  public static boolean isArrayOperator(TomSymbol symbol) {
    if(symbol==null) {
      return false;
    }
    {{if ( (((Object)symbol) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        OptionList optionList =  (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)).getOptions() ;
        while(!optionList.isEmptyconcOption()) {
          Option opt = optionList.getHeadconcOption();
          {{if ( (((Object)opt) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )((Object)opt)) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )(( tom.engine.adt.tomoption.types.Option )((Object)opt))) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch27_1= (( tom.engine.adt.tomoption.types.Option )((Object)opt)).getAstDeclaration() ;if ( (tomMatch27_1 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch27_1) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyArray) ) {
 return true; }}}}}}{if ( (((Object)opt) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )((Object)opt)) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )(( tom.engine.adt.tomoption.types.Option )((Object)opt))) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch27_7= (( tom.engine.adt.tomoption.types.Option )((Object)opt)).getAstDeclaration() ;if ( (tomMatch27_7 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch27_7) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddArray) ) {
 return true; }}}}}}}

          optionList = optionList.getTailconcOption();
        }
        return false;
      }}}}}

    throw new TomRuntimeException("isArrayOperator -- strange case: '" + symbol + "'");
  }

  /**
   * Returns <code>true</code> if the symbol corresponds to a %op
   */
  public static boolean isSyntacticOperator(TomSymbol subject) {
    return (!(isListOperator(subject) || isArrayOperator(subject)));
  }

  // ------------------------------------------------------------
  /**
   * Collects the variables that appears in a term
   * @param collection the bag which collect the results
   * @param subject the term to traverse
   */
  public static void collectVariable(Collection<TomTerm> collection, 
          tom.library.sl.Visitable subject, boolean considerBQVars) {
    try {
      //TODO: replace TopDownCollect by continuations
      tom_make_TopDownCollect(tom_make_collectVariable(collection,considerBQVars)).visitLight(subject);
    } catch(VisitFailure e) {
      throw new TomRuntimeException("Should not be there");
    }
  }

  public static class collectVariable extends tom.library.sl.AbstractStrategyBasic {private  java.util.Collection  collection;private  boolean  considerBQVars;public collectVariable( java.util.Collection  collection,  boolean  considerBQVars) {super(( new tom.library.sl.Identity() ));this.collection=collection;this.considerBQVars=considerBQVars;}public  java.util.Collection  getcollection() {return collection;}public  boolean  getconsiderBQVars() {return considerBQVars;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.engine.adt.code.types.BQTerm) ) {return ((T)visit_BQTerm((( tom.engine.adt.code.types.BQTerm )v),introspector));}if ( (v instanceof tom.engine.adt.tomterm.types.TomTerm) ) {return ((T)visit_TomTerm((( tom.engine.adt.tomterm.types.TomTerm )v),introspector));}if (!(  null ==environment )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomterm.types.TomTerm  _visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.engine.adt.tomterm.types.TomTerm )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.code.types.BQTerm  _visit_BQTerm( tom.engine.adt.code.types.BQTerm  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.engine.adt.code.types.BQTerm )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (((Object)tom__arg) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch28_5= false ; tom.engine.adt.tomconstraint.types.ConstraintList  tomMatch28_1= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch28_4= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch28_3= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg))) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch28_5= true ;tomMatch28_3=(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg));tomMatch28_1= tomMatch28_3.getConstraints() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg))) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch28_5= true ;tomMatch28_4=(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg));tomMatch28_1= tomMatch28_4.getConstraints() ;}}}}}if (tomMatch28_5) { tom.engine.adt.tomterm.types.TomTerm  tom_v=(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg));








        collection.add(tom_v);
        TomTerm annotedVariable = getAliasToVariable(tomMatch28_1);
        if(annotedVariable!=null) {
          collection.add(annotedVariable);
        }
        ( new tom.library.sl.Fail() ).visitLight(tom_v);
      }}}{if ( (((Object)tom__arg) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg))) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {



        collectVariable(collection, (( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)).getSlots() ,considerBQVars);
        TomTerm annotedVariable = getAliasToVariable( (( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)).getConstraints() );
        if(annotedVariable!=null) {
          collection.add(annotedVariable);
        }
        ( new tom.library.sl.Fail() ).visitLight((( tom.engine.adt.tomterm.types.TomTerm )((Object)tom__arg)));
      }}}}}return _visit_TomTerm(tom__arg,introspector);}@SuppressWarnings("unchecked")public  tom.engine.adt.code.types.BQTerm  visit_BQTerm( tom.engine.adt.code.types.BQTerm  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (((Object)tom__arg) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)tom__arg)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)tom__arg))) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {           if (considerBQVars) { collection.add(convertFromBQVarToVar((( tom.engine.adt.code.types.BQTerm )((Object)tom__arg)))); }          }}}}}return _visit_BQTerm(tom__arg,introspector);}}private static  tom.library.sl.Strategy  tom_make_collectVariable( java.util.Collection  t0,  boolean  t1) { return new collectVariable(t0,t1);}




  /**
   * Returns a Map which associates an integer to each variable name
   */
  public static Map<TomName,Integer> collectMultiplicity(tom.library.sl.Visitable subject) {
    // collect variables
    Collection<TomTerm> variableList = new HashSet<TomTerm>();
    collectVariable(variableList,subject,true);
    // compute multiplicities
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
    {{if ( (((Object)constraintList) instanceof tom.engine.adt.tomconstraint.types.ConstraintList) ) {if ( (((( tom.engine.adt.tomconstraint.types.ConstraintList )(( tom.engine.adt.tomconstraint.types.ConstraintList )((Object)constraintList))) instanceof tom.engine.adt.tomconstraint.types.constraintlist.ConsconcConstraint) || ((( tom.engine.adt.tomconstraint.types.ConstraintList )(( tom.engine.adt.tomconstraint.types.ConstraintList )((Object)constraintList))) instanceof tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint)) ) { tom.engine.adt.tomconstraint.types.ConstraintList  tomMatch30__end__4=(( tom.engine.adt.tomconstraint.types.ConstraintList )((Object)constraintList));do {{if (!( tomMatch30__end__4.isEmptyconcConstraint() )) { tom.engine.adt.tomconstraint.types.Constraint  tomMatch30_8= tomMatch30__end__4.getHeadconcConstraint() ;if ( (tomMatch30_8 instanceof tom.engine.adt.tomconstraint.types.Constraint) ) {if ( ((( tom.engine.adt.tomconstraint.types.Constraint )tomMatch30_8) instanceof tom.engine.adt.tomconstraint.types.constraint.AliasTo) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch30_7= tomMatch30_8.getVar() ;if ( (tomMatch30_7 instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )tomMatch30_7) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {
 return tomMatch30_7; }}}}}if ( tomMatch30__end__4.isEmptyconcConstraint() ) {tomMatch30__end__4=(( tom.engine.adt.tomconstraint.types.ConstraintList )((Object)constraintList));} else {tomMatch30__end__4= tomMatch30__end__4.getTailconcConstraint() ;}}} while(!( (tomMatch30__end__4==(( tom.engine.adt.tomconstraint.types.ConstraintList )((Object)constraintList))) ));}}}}

    return null;
  }

  public static boolean hasTheory(TomTerm term, ElementaryTheory elementaryTheory) {
    return hasTheory(getTheory(term),elementaryTheory);
  }

  public static boolean hasTheory(Theory theory, ElementaryTheory elementaryTheory) {
    {{if ( (((Object)theory) instanceof tom.engine.adt.theory.types.Theory) ) {if ( (((( tom.engine.adt.theory.types.Theory )(( tom.engine.adt.theory.types.Theory )((Object)theory))) instanceof tom.engine.adt.theory.types.theory.ConsconcElementaryTheory) || ((( tom.engine.adt.theory.types.Theory )(( tom.engine.adt.theory.types.Theory )((Object)theory))) instanceof tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory)) ) { tom.engine.adt.theory.types.Theory  tomMatch31__end__4=(( tom.engine.adt.theory.types.Theory )((Object)theory));do {{if (!( tomMatch31__end__4.isEmptyconcElementaryTheory() )) {
 if( tomMatch31__end__4.getHeadconcElementaryTheory() ==elementaryTheory) { return true; } }if ( tomMatch31__end__4.isEmptyconcElementaryTheory() ) {tomMatch31__end__4=(( tom.engine.adt.theory.types.Theory )((Object)theory));} else {tomMatch31__end__4= tomMatch31__end__4.getTailconcElementaryTheory() ;}}} while(!( (tomMatch31__end__4==(( tom.engine.adt.theory.types.Theory )((Object)theory))) ));}}}}

    return false;
  }

  public static Theory getTheory(TomTerm term) {
    {{if ( (((Object)term) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)term)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)term))) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch32_1= (( tom.engine.adt.tomterm.types.TomTerm )((Object)term)).getOptions() ;if ( (((( tom.engine.adt.tomoption.types.OptionList )tomMatch32_1) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )tomMatch32_1) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch32__end__7=tomMatch32_1;do {{if (!( tomMatch32__end__7.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch32_11= tomMatch32__end__7.getHeadconcOption() ;if ( (tomMatch32_11 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch32_11) instanceof tom.engine.adt.tomoption.types.option.MatchingTheory) ) {
 return  tomMatch32_11.getTheory() ; }}}if ( tomMatch32__end__7.isEmptyconcOption() ) {tomMatch32__end__7=tomMatch32_1;} else {tomMatch32__end__7= tomMatch32__end__7.getTailconcOption() ;}}} while(!( (tomMatch32__end__7==tomMatch32_1) ));}}}}}}

    return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( tom.engine.adt.theory.types.elementarytheory.Syntactic.make() , tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) ;
  }

  public static Theory getTheory(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch33__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch33__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch33_8= tomMatch33__end__4.getHeadconcOption() ;if ( (tomMatch33_8 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch33_8) instanceof tom.engine.adt.tomoption.types.option.MatchingTheory) ) {
 return  tomMatch33_8.getTheory() ; }}}if ( tomMatch33__end__4.isEmptyconcOption() ) {tomMatch33__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch33__end__4= tomMatch33__end__4.getTailconcOption() ;}}} while(!( (tomMatch33__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

    return  tom.engine.adt.theory.types.theory.ConsconcElementaryTheory.make( tom.engine.adt.theory.types.elementarytheory.Syntactic.make() , tom.engine.adt.theory.types.theory.EmptyconcElementaryTheory.make() ) ;
  }

  public static Declaration getIsFsymDecl(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch34__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch34__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch34_8= tomMatch34__end__4.getHeadconcOption() ;if ( (tomMatch34_8 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch34_8) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch34_7= tomMatch34_8.getAstDeclaration() ;if ( (tomMatch34_7 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch34_7) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl) ) {
 return tomMatch34_7; }}}}}if ( tomMatch34__end__4.isEmptyconcOption() ) {tomMatch34__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch34__end__4= tomMatch34__end__4.getTailconcOption() ;}}} while(!( (tomMatch34__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

    return null;
  }

  public static boolean hasIsFsymDecl(TomSymbol tomSymbol) {
    {{if ( (((Object)tomSymbol) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)tomSymbol)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)tomSymbol))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch35_1= (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)tomSymbol)).getOptions() ;if ( (((( tom.engine.adt.tomoption.types.OptionList )tomMatch35_1) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )tomMatch35_1) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch35__end__7=tomMatch35_1;do {{if (!( tomMatch35__end__7.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch35_11= tomMatch35__end__7.getHeadconcOption() ;if ( (tomMatch35_11 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch35_11) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) { tom.engine.adt.tomdeclaration.types.Declaration  tomMatch35_10= tomMatch35_11.getAstDeclaration() ;if ( (tomMatch35_10 instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tomMatch35_10) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl) ) {

        return true;
      }}}}}if ( tomMatch35__end__7.isEmptyconcOption() ) {tomMatch35__end__7=tomMatch35_1;} else {tomMatch35__end__7= tomMatch35__end__7.getTailconcOption() ;}}} while(!( (tomMatch35__end__7==tomMatch35_1) ));}}}}}}

    return false;
  }

  public static String getModuleName(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch36__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch36__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch36_8= tomMatch36__end__4.getHeadconcOption() ;if ( (tomMatch36_8 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch36_8) instanceof tom.engine.adt.tomoption.types.option.ModuleName) ) {
 return  tomMatch36_8.getString() ; }}}if ( tomMatch36__end__4.isEmptyconcOption() ) {tomMatch36__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch36__end__4= tomMatch36__end__4.getTailconcOption() ;}}} while(!( (tomMatch36__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

    return null;
  }

  /*
  public static boolean hasConstant(OptionList optionList) {
    %match(optionList) {
      concOption(_*,Constant[],_*) -> { return true; }
    }
    return false;
  }
*/

  public static boolean hasDefinedSymbol(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch37__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch37__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch37_7= tomMatch37__end__4.getHeadconcOption() ;if ( (tomMatch37_7 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch37_7) instanceof tom.engine.adt.tomoption.types.option.DefinedSymbol) ) {
 return true; }}}if ( tomMatch37__end__4.isEmptyconcOption() ) {tomMatch37__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch37__end__4= tomMatch37__end__4.getTailconcOption() ;}}} while(!( (tomMatch37__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

    return false;
  }

  public static boolean hasImplicitXMLAttribut(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch38__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch38__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch38_7= tomMatch38__end__4.getHeadconcOption() ;if ( (tomMatch38_7 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch38_7) instanceof tom.engine.adt.tomoption.types.option.ImplicitXMLAttribut) ) {
 return true; }}}if ( tomMatch38__end__4.isEmptyconcOption() ) {tomMatch38__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch38__end__4= tomMatch38__end__4.getTailconcOption() ;}}} while(!( (tomMatch38__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

    return false;
  }

  public static boolean hasImplicitXMLChild(OptionList optionList) {
    {{if ( (((Object)optionList) instanceof tom.engine.adt.tomoption.types.OptionList) ) {if ( (((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) { tom.engine.adt.tomoption.types.OptionList  tomMatch39__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));do {{if (!( tomMatch39__end__4.isEmptyconcOption() )) { tom.engine.adt.tomoption.types.Option  tomMatch39_7= tomMatch39__end__4.getHeadconcOption() ;if ( (tomMatch39_7 instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )tomMatch39_7) instanceof tom.engine.adt.tomoption.types.option.ImplicitXMLChild) ) {
 return true; }}}if ( tomMatch39__end__4.isEmptyconcOption() ) {tomMatch39__end__4=(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList));} else {tomMatch39__end__4= tomMatch39__end__4.getTailconcOption() ;}}} while(!( (tomMatch39__end__4==(( tom.engine.adt.tomoption.types.OptionList )((Object)optionList))) ));}}}}

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
    {{if ( (((Object)decl) instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )((Object)decl)) instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )(( tom.engine.adt.tomdeclaration.types.Declaration )((Object)decl))) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetSlotDecl) ) {
 return  (( tom.engine.adt.tomdeclaration.types.Declaration )((Object)decl)).getSlotName() ; }}}}}


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
    {{if ( (((Object)symbol) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomtype.types.TomType  tomMatch41_1= (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)).getTypesToType() ;if ( (tomMatch41_1 instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )tomMatch41_1) instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {

        int index = getSlotIndex(symbol,slotName);
        return elementAt( tomMatch41_1.getDomain() ,index);
      }}}}}}}

    return null;
    //throw new TomRuntimeException("getSlotType: bad slotName error: " + slotName);
  }

  public static boolean isDefinedSymbol(TomSymbol subject) {
    /*This test seems to be useless
       if(subject==null) {
      System.out.println("isDefinedSymbol: subject == null");
      return false;
    }*/
    {{if ( (((Object)subject) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)subject)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)subject))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {

        return hasDefinedSymbol( (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)subject)).getOptions() );
      }}}}}

    return false;
  }

  public static boolean isDefinedGetSlot(TomSymbol symbol, TomName slotName) {
    if(symbol==null) {
      //System.out.println("isDefinedSymbol: symbol == null");
      return false;
    }
    {{if ( (((Object)symbol) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)) instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )(( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol))) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) { tom.engine.adt.tomslot.types.PairNameDeclList  tomMatch43_1= (( tom.engine.adt.tomsignature.types.TomSymbol )((Object)symbol)).getPairNameDeclList() ;if ( (((( tom.engine.adt.tomslot.types.PairNameDeclList )tomMatch43_1) instanceof tom.engine.adt.tomslot.types.pairnamedecllist.ConsconcPairNameDecl) || ((( tom.engine.adt.tomslot.types.PairNameDeclList )tomMatch43_1) instanceof tom.engine.adt.tomslot.types.pairnamedecllist.EmptyconcPairNameDecl)) ) { tom.engine.adt.tomslot.types.PairNameDeclList  tomMatch43__end__7=tomMatch43_1;do {{if (!( tomMatch43__end__7.isEmptyconcPairNameDecl() )) { tom.engine.adt.tomslot.types.PairNameDecl  tomMatch43_12= tomMatch43__end__7.getHeadconcPairNameDecl() ;if ( (tomMatch43_12 instanceof tom.engine.adt.tomslot.types.PairNameDecl) ) {if ( ((( tom.engine.adt.tomslot.types.PairNameDecl )tomMatch43_12) instanceof tom.engine.adt.tomslot.types.pairnamedecl.PairNameDecl) ) {

        if( tomMatch43_12.getSlotName() ==slotName &&  tomMatch43_12.getSlotDecl() != tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration.make() ) {
          return true;
        }
      }}}if ( tomMatch43__end__7.isEmptyconcPairNameDecl() ) {tomMatch43__end__7=tomMatch43_1;} else {tomMatch43__end__7= tomMatch43__end__7.getTailconcPairNameDecl() ;}}} while(!( (tomMatch43__end__7==tomMatch43_1) ));}}}}}}

    return false;
  }


  /**
   * Return the option containing OriginTracking information
   */
  public static Option findOriginTracking(OptionList optionList) {
    if(optionList.isEmptyconcOption()) {
      return  tom.engine.adt.tomoption.types.option.noOption.make() ;
    }
    while(!optionList.isEmptyconcOption()) {
      Option subject = optionList.getHeadconcOption();
      {{if ( (((Object)subject) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )((Object)subject)) instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )(( tom.engine.adt.tomoption.types.Option )((Object)subject))) instanceof tom.engine.adt.tomoption.types.option.OriginTracking) ) {

          return (( tom.engine.adt.tomoption.types.Option )((Object)subject));
        }}}}}

      optionList = optionList.getTailconcOption();
    }
    throw new TomRuntimeException("findOriginTracking:  not found: " + optionList);
  }

  public static TomSymbol getSymbolFromName(String tomName, SymbolTable symbolTable) {
    return symbolTable.getSymbolFromName(tomName);
  }

  public static TomType getTermType(TomTerm t, SymbolTable symbolTable) {
    {{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch45_8= false ; tom.engine.adt.tomname.types.TomNameList  tomMatch45_1= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch45_3= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch45_4= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {{tomMatch45_8= true ;tomMatch45_3=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch45_1= tomMatch45_3.getNameList() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {{tomMatch45_8= true ;tomMatch45_4=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch45_1= tomMatch45_4.getNameList() ;}}}}}if (tomMatch45_8) {if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch45_1) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch45_1) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch45_1.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tom_headName= tomMatch45_1.getHeadconcTomName() ;

        String tomName = null;
        if((tom_headName) instanceof AntiName) {
          tomName = ((AntiName)tom_headName).getName().getString();
        } else {
          tomName = ((TomName)tom_headName).getString();
        }
        TomSymbol tomSymbol = symbolTable.getSymbolFromName(tomName);
        if(tomSymbol!=null) {
          return tomSymbol.getTypesToType().getCodomain();
        } else {
          return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
        }
      }}}}}{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch45_14= false ; tom.engine.adt.tomtype.types.TomType  tomMatch45_10= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch45_12= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch45_13= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch45_14= true ;tomMatch45_12=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch45_10= tomMatch45_12.getAstType() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch45_14= true ;tomMatch45_13=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch45_10= tomMatch45_13.getAstType() ;}}}}}if (tomMatch45_14) {


        return tomMatch45_10;
      }}}{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.AntiTerm) ) {

 return getTermType( (( tom.engine.adt.tomterm.types.TomTerm )((Object)t)).getTomTerm() ,symbolTable);}}}}}


    //System.out.println("getTermType error on term: " + t);
    //throw new TomRuntimeException("getTermType error on term: " + t);
    return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
  }

  public static TomType getTermType(BQTerm t, SymbolTable symbolTable) {
    {{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch46_5= false ; tom.engine.adt.tomtype.types.TomType  tomMatch46_1= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_3= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_4= null ;if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {{tomMatch46_5= true ;tomMatch46_3=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_1= tomMatch46_3.getAstType() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {{tomMatch46_5= true ;tomMatch46_4=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_1= tomMatch46_4.getAstType() ;}}}}}if (tomMatch46_5) {

        return tomMatch46_1;
      }}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.FunctionCall) ) {

 return  (( tom.engine.adt.code.types.BQTerm )((Object)t)).getAstType() ; }}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.ExpressionToBQTerm) ) {

 return getTermType( (( tom.engine.adt.code.types.BQTerm )((Object)t)).getExp() ,symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.ListHead) ) {

 return  (( tom.engine.adt.code.types.BQTerm )((Object)t)).getCodomain() ; }}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.ListTail) ) {

 return getTermType( (( tom.engine.adt.code.types.BQTerm )((Object)t)).getVariable() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.Subterm) ) { tom.engine.adt.tomname.types.TomName  tomMatch46_23= (( tom.engine.adt.code.types.BQTerm )((Object)t)).getAstName() ;if ( (tomMatch46_23 instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch46_23) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        TomSymbol tomSymbol = symbolTable.getSymbolFromName( tomMatch46_23.getString() );
        return getSlotType(tomSymbol,  (( tom.engine.adt.code.types.BQTerm )((Object)t)).getSlotName() );
      }}}}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch46_44= false ; tom.engine.adt.code.types.BQTerm  tomMatch46_33= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_37= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_35= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_39= null ; tom.engine.adt.tomname.types.TomName  tomMatch46_31= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_40= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_38= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_34= null ; tom.engine.adt.code.types.BQTerm  tomMatch46_36= null ;if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildConstant) ) {{tomMatch46_44= true ;tomMatch46_33=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_33.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildTerm) ) {{tomMatch46_44= true ;tomMatch46_34=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_34.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildEmptyList) ) {{tomMatch46_44= true ;tomMatch46_35=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_35.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildConsList) ) {{tomMatch46_44= true ;tomMatch46_36=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_36.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildAppendList) ) {{tomMatch46_44= true ;tomMatch46_37=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_37.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildEmptyArray) ) {{tomMatch46_44= true ;tomMatch46_38=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_38.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildConsArray) ) {{tomMatch46_44= true ;tomMatch46_39=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_39.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BuildAppendArray) ) {{tomMatch46_44= true ;tomMatch46_40=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch46_31= tomMatch46_40.getAstName() ;}}}}}}}}}}}}}}}}}if (tomMatch46_44) {if ( (tomMatch46_31 instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch46_31) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {



          TomSymbol tomSymbol = symbolTable.getSymbolFromName( tomMatch46_31.getString() ); 
          if(tomSymbol!=null) {
            return tomSymbol.getTypesToType().getCodomain();
          } else {
            return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
          }
        }}}}}}

    //System.out.println("getTermType error on term: " + t);
    //throw new TomRuntimeException("getTermType error on term: " + t);
    return  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ;
  }

  public static TomType getTermType(Expression t, SymbolTable symbolTable) {
    {{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {boolean tomMatch47_6= false ; tom.engine.adt.tomexpression.types.Expression  tomMatch47_3= null ; tom.engine.adt.tomexpression.types.Expression  tomMatch47_4= null ; tom.engine.adt.tomtype.types.TomType  tomMatch47_1= null ; tom.engine.adt.tomexpression.types.Expression  tomMatch47_5= null ;if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetHead) ) {{tomMatch47_6= true ;tomMatch47_3=(( tom.engine.adt.tomexpression.types.Expression )((Object)t));tomMatch47_1= tomMatch47_3.getCodomain() ;}} else {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetSlot) ) {{tomMatch47_6= true ;tomMatch47_4=(( tom.engine.adt.tomexpression.types.Expression )((Object)t));tomMatch47_1= tomMatch47_4.getCodomain() ;}} else {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetElement) ) {{tomMatch47_6= true ;tomMatch47_5=(( tom.engine.adt.tomexpression.types.Expression )((Object)t));tomMatch47_1= tomMatch47_5.getCodomain() ;}}}}}}}if (tomMatch47_6) {
 return tomMatch47_1; }}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.BQTermToExpression) ) {

 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getAstTerm() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.Conditional) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getCond() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.IsFsym) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getVariable() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetTail) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getVariable() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceList) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getVariableBeginAST() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceArray) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getSubjectListName() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.Cast) ) {
 return  (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getAstType() ; }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.AddOne) ) {
 return getTermType( (( tom.engine.adt.tomexpression.types.Expression )((Object)t)).getVariable() , symbolTable); }}}}{if ( (((Object)t) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )((Object)t)) instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )(( tom.engine.adt.tomexpression.types.Expression )((Object)t))) instanceof tom.engine.adt.tomexpression.types.expression.Integer) ) {
 return symbolTable.getType("int"); }}}}}

    System.out.println("getTermType error on term: " + t);
    throw new TomRuntimeException("getTermType error on term: " + t);
  }

  public static TomSymbol getSymbolFromTerm(TomTerm t, SymbolTable symbolTable) {
    {{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch48_8= false ; tom.engine.adt.tomterm.types.TomTerm  tomMatch48_3= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch48_4= null ; tom.engine.adt.tomname.types.TomNameList  tomMatch48_1= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {{tomMatch48_8= true ;tomMatch48_3=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch48_1= tomMatch48_3.getNameList() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {{tomMatch48_8= true ;tomMatch48_4=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch48_1= tomMatch48_4.getNameList() ;}}}}}if (tomMatch48_8) {if ( (((( tom.engine.adt.tomname.types.TomNameList )tomMatch48_1) instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || ((( tom.engine.adt.tomname.types.TomNameList )tomMatch48_1) instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {if (!( tomMatch48_1.isEmptyconcTomName() )) { tom.engine.adt.tomname.types.TomName  tom_headName= tomMatch48_1.getHeadconcTomName() ;

        String tomName = null;
        if((tom_headName) instanceof AntiName) {
          tomName = ((AntiName)tom_headName).getName().getString();
        } else {
          tomName = ((TomName)tom_headName).getString();
        }
        return symbolTable.getSymbolFromName(tomName);
      }}}}}{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch48_17= false ; tom.engine.adt.tomname.types.TomName  tomMatch48_10= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch48_13= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch48_12= null ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch48_17= true ;tomMatch48_12=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch48_10= tomMatch48_12.getAstName() ;}} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch48_17= true ;tomMatch48_13=(( tom.engine.adt.tomterm.types.TomTerm )((Object)t));tomMatch48_10= tomMatch48_13.getAstName() ;}}}}}if (tomMatch48_17) {if ( (tomMatch48_10 instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch48_10) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        return symbolTable.getSymbolFromName( tomMatch48_10.getString() );
      }}}}}{if ( (((Object)t) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)t)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)t))) instanceof tom.engine.adt.tomterm.types.tomterm.AntiTerm) ) {

 return getSymbolFromTerm( (( tom.engine.adt.tomterm.types.TomTerm )((Object)t)).getTomTerm() ,symbolTable);}}}}}

    return null;
  }

  public static TomSymbol getSymbolFromTerm(BQTerm t, SymbolTable symbolTable) {
    {{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {boolean tomMatch49_8= false ; tom.engine.adt.code.types.BQTerm  tomMatch49_4= null ; tom.engine.adt.tomname.types.TomName  tomMatch49_1= null ; tom.engine.adt.code.types.BQTerm  tomMatch49_3= null ;if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {{tomMatch49_8= true ;tomMatch49_3=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch49_1= tomMatch49_3.getAstName() ;}} else {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {{tomMatch49_8= true ;tomMatch49_4=(( tom.engine.adt.code.types.BQTerm )((Object)t));tomMatch49_1= tomMatch49_4.getAstName() ;}}}}}if (tomMatch49_8) {if ( (tomMatch49_1 instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch49_1) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

        return symbolTable.getSymbolFromName( tomMatch49_1.getString() );
      }}}}}{if ( (((Object)t) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)t)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)t))) instanceof tom.engine.adt.code.types.bqterm.FunctionCall) ) { tom.engine.adt.tomname.types.TomName  tomMatch49_10= (( tom.engine.adt.code.types.BQTerm )((Object)t)).getAstName() ;if ( (tomMatch49_10 instanceof tom.engine.adt.tomname.types.TomName) ) {if ( ((( tom.engine.adt.tomname.types.TomName )tomMatch49_10) instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

 return symbolTable.getSymbolFromName( tomMatch49_10.getString() ); }}}}}}}

    return null;
  }


  public static SlotList tomListToSlotList(TomList tomList) {
    return tomListToSlotList(tomList,1);
  }

  public static SlotList tomListToSlotList(TomList tomList, int index) {
    {{if ( (((Object)tomList) instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if ( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).isEmptyconcTomTerm() ) {
 return  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ; }}}}{if ( (((Object)tomList) instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if (!( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).isEmptyconcTomTerm() )) {

        TomName slotName =  tom.engine.adt.tomname.types.tomname.PositionName.make( tom.engine.adt.tomname.types.tomnumberlist.ConsconcTomNumber.make( tom.engine.adt.tomname.types.tomnumber.Position.make(index) , tom.engine.adt.tomname.types.tomnumberlist.EmptyconcTomNumber.make() ) ) ;
        SlotList sl = tomListToSlotList( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).getTailconcTomTerm() ,index+1);
        return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( tom.engine.adt.tomslot.types.slot.PairSlotAppl.make(slotName,  (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).getHeadconcTomTerm() ) ,tom_append_list_concSlot(sl, tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() )) ;
      }}}}}

    throw new TomRuntimeException("tomListToSlotList: " + tomList);
  }

  public static SlotList mergeTomListWithSlotList(TomList tomList, SlotList slotList) {
    {{if ( (((Object)tomList) instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if ( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).isEmptyconcTomTerm() ) {if ( (((Object)slotList) instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)slotList))) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)slotList))) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )((Object)slotList)).isEmptyconcSlot() ) {

        return  tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() ;
      }}}}}}}{if ( (((Object)tomList) instanceof tom.engine.adt.tomterm.types.TomList) ) {if ( (((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm) || ((( tom.engine.adt.tomterm.types.TomList )(( tom.engine.adt.tomterm.types.TomList )((Object)tomList))) instanceof tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm)) ) {if (!( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).isEmptyconcTomTerm() )) {if ( (((Object)slotList) instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)slotList))) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)slotList))) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if (!( (( tom.engine.adt.tomslot.types.SlotList )((Object)slotList)).isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch51_13= (( tom.engine.adt.tomslot.types.SlotList )((Object)slotList)).getHeadconcSlot() ;if ( (tomMatch51_13 instanceof tom.engine.adt.tomslot.types.Slot) ) {if ( ((( tom.engine.adt.tomslot.types.Slot )tomMatch51_13) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

        SlotList sl = mergeTomListWithSlotList( (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).getTailconcTomTerm() , (( tom.engine.adt.tomslot.types.SlotList )((Object)slotList)).getTailconcSlot() );
        return  tom.engine.adt.tomslot.types.slotlist.ConsconcSlot.make( tom.engine.adt.tomslot.types.slot.PairSlotAppl.make( tomMatch51_13.getSlotName() ,  (( tom.engine.adt.tomterm.types.TomList )((Object)tomList)).getHeadconcTomTerm() ) ,tom_append_list_concSlot(sl, tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot.make() )) ;
      }}}}}}}}}}

    throw new TomRuntimeException("mergeTomListWithSlotList: " + tomList + " and " + slotList);
  }

  public static BQTermList slotListToBQTermList(SlotList tomList) {
    {{if ( (((Object)tomList) instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)tomList))) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)tomList))) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if ( (( tom.engine.adt.tomslot.types.SlotList )((Object)tomList)).isEmptyconcSlot() ) {
 return  tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ; }}}}{if ( (((Object)tomList) instanceof tom.engine.adt.tomslot.types.SlotList) ) {if ( (((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)tomList))) instanceof tom.engine.adt.tomslot.types.slotlist.ConsconcSlot) || ((( tom.engine.adt.tomslot.types.SlotList )(( tom.engine.adt.tomslot.types.SlotList )((Object)tomList))) instanceof tom.engine.adt.tomslot.types.slotlist.EmptyconcSlot)) ) {if (!( (( tom.engine.adt.tomslot.types.SlotList )((Object)tomList)).isEmptyconcSlot() )) { tom.engine.adt.tomslot.types.Slot  tomMatch52_7= (( tom.engine.adt.tomslot.types.SlotList )((Object)tomList)).getHeadconcSlot() ;if ( (tomMatch52_7 instanceof tom.engine.adt.tomslot.types.Slot) ) {if ( ((( tom.engine.adt.tomslot.types.Slot )tomMatch52_7) instanceof tom.engine.adt.tomslot.types.slot.PairSlotAppl) ) {

        BQTermList tl = slotListToBQTermList( (( tom.engine.adt.tomslot.types.SlotList )((Object)tomList)).getTailconcSlot() );
        return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(convertFromVarToBQVar( tomMatch52_7.getAppl() ),tom_append_list_concBQTerm(tl, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() )) ;
      }}}}}}}

    throw new TomRuntimeException("slotListToTomList: " + tomList);
  }

  public static int getArity(TomSymbol symbol) {
    if (isListOperator(symbol) || isArrayOperator(symbol)) {
      return 2;
    } else {
      return ((Collection) symbol.getPairNameDeclList()).size();
    }
  }

  /**
   * builds a BQVariable from a TomTerm Variable
   */
  public static BQTerm convertFromVarToBQVar(TomTerm variable) {
    {{if ( (((Object)variable) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)variable))) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {

        return  tom.engine.adt.code.types.bqterm.BQVariable.make( (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getOptions() ,  (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getAstName() ,  (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getAstType() ) ;
      }}}}{if ( (((Object)variable) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)) instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )(( tom.engine.adt.tomterm.types.TomTerm )((Object)variable))) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {

        return  tom.engine.adt.code.types.bqterm.BQVariableStar.make( (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getOptions() ,  (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getAstName() ,  (( tom.engine.adt.tomterm.types.TomTerm )((Object)variable)).getAstType() ) ;
      }}}}}

    throw new TomRuntimeException("cannot convert into a bq variable the term "+variable);
  }

  /**
   * builds a Variable from a BQVariable
   */
  public static TomTerm convertFromBQVarToVar(BQTerm variable) {
    {{if ( (((Object)variable) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)variable)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)variable))) instanceof tom.engine.adt.code.types.bqterm.BQVariable) ) {

        return  tom.engine.adt.tomterm.types.tomterm.Variable.make( (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getOptions() ,  (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getAstName() ,  (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getAstType() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ;
      }}}}{if ( (((Object)variable) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )((Object)variable)) instanceof tom.engine.adt.code.types.BQTerm) ) {if ( ((( tom.engine.adt.code.types.BQTerm )(( tom.engine.adt.code.types.BQTerm )((Object)variable))) instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {

        return  tom.engine.adt.tomterm.types.tomterm.VariableStar.make( (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getOptions() ,  (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getAstName() ,  (( tom.engine.adt.code.types.BQTerm )((Object)variable)).getAstType() ,  tom.engine.adt.tomconstraint.types.constraintlist.EmptyconcConstraint.make() ) ;
      }}}}}

    throw new TomRuntimeException("cannot convert into a variable the term " + variable);
  }

} // class TomBase
