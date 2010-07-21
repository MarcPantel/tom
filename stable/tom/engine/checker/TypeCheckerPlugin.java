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
* Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
* Jean-Christophe Bach e-mail: Jeanchristophe.Bach@loria.fr
* Julien Guyon
*
**/

package tom.engine.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.tools.TomGenericPlugin;
import tom.engine.exception.TomRuntimeException;
import tom.platform.OptionParser;
import tom.platform.PlatformLogRecord;

import aterm.ATerm;
import tom.library.sl.*;
import tom.platform.adt.platformoption.types.PlatformOptionList;

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

/**
* The TomTypeChecker plugin.
*/
public class TypeCheckerPlugin extends TomGenericPlugin {



  private static   tom.engine.adt.tomsignature.types.TomVisitList  tom_append_list_concTomVisit( tom.engine.adt.tomsignature.types.TomVisitList l1,  tom.engine.adt.tomsignature.types.TomVisitList  l2) {
    if( l1.isEmptyconcTomVisit() ) {
      return l2;
    } else if( l2.isEmptyconcTomVisit() ) {
      return l1;
    } else if(  l1.getTailconcTomVisit() .isEmptyconcTomVisit() ) {
      return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( l1.getHeadconcTomVisit() ,l2) ;
    } else {
      return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( l1.getHeadconcTomVisit() ,tom_append_list_concTomVisit( l1.getTailconcTomVisit() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomsignature.types.TomVisitList  tom_get_slice_concTomVisit( tom.engine.adt.tomsignature.types.TomVisitList  begin,  tom.engine.adt.tomsignature.types.TomVisitList  end, tom.engine.adt.tomsignature.types.TomVisitList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcTomVisit()  ||  (end== tom.engine.adt.tomsignature.types.tomvisitlist.EmptyconcTomVisit.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit.make( begin.getHeadconcTomVisit() ,( tom.engine.adt.tomsignature.types.TomVisitList )tom_get_slice_concTomVisit( begin.getTailconcTomVisit() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tominstruction.types.ConstraintInstructionList  tom_append_list_concConstraintInstruction( tom.engine.adt.tominstruction.types.ConstraintInstructionList l1,  tom.engine.adt.tominstruction.types.ConstraintInstructionList  l2) {
    if( l1.isEmptyconcConstraintInstruction() ) {
      return l2;
    } else if( l2.isEmptyconcConstraintInstruction() ) {
      return l1;
    } else if(  l1.getTailconcConstraintInstruction() .isEmptyconcConstraintInstruction() ) {
      return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( l1.getHeadconcConstraintInstruction() ,l2) ;
    } else {
      return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( l1.getHeadconcConstraintInstruction() ,tom_append_list_concConstraintInstruction( l1.getTailconcConstraintInstruction() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tominstruction.types.ConstraintInstructionList  tom_get_slice_concConstraintInstruction( tom.engine.adt.tominstruction.types.ConstraintInstructionList  begin,  tom.engine.adt.tominstruction.types.ConstraintInstructionList  end, tom.engine.adt.tominstruction.types.ConstraintInstructionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcConstraintInstruction()  ||  (end== tom.engine.adt.tominstruction.types.constraintinstructionlist.EmptyconcConstraintInstruction.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( begin.getHeadconcConstraintInstruction() ,( tom.engine.adt.tominstruction.types.ConstraintInstructionList )tom_get_slice_concConstraintInstruction( begin.getTailconcConstraintInstruction() ,end,tail)) ;
  }
  
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
  
  private static   tom.engine.adt.tomoption.types.OptionList  tom_append_list_concOption( tom.engine.adt.tomoption.types.OptionList l1,  tom.engine.adt.tomoption.types.OptionList  l2) {
    if( l1.isEmptyconcOption() ) {
      return l2;
    } else if( l2.isEmptyconcOption() ) {
      return l1;
    } else if(  l1.getTailconcOption() .isEmptyconcOption() ) {
      return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,l2) ;
    } else {
      return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( l1.getHeadconcOption() ,tom_append_list_concOption( l1.getTailconcOption() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomoption.types.OptionList  tom_get_slice_concOption( tom.engine.adt.tomoption.types.OptionList  begin,  tom.engine.adt.tomoption.types.OptionList  end, tom.engine.adt.tomoption.types.OptionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcOption()  ||  (end== tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make( begin.getHeadconcOption() ,( tom.engine.adt.tomoption.types.OptionList )tom_get_slice_concOption( begin.getTailconcOption() ,end,tail)) ;
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 == null )) {
      return l2;
    } else if(( l2 == null )) {
      return l1;
    } else if(( (l1 instanceof tom.library.sl.Sequence) )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {
        return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) );
      } else {
        return ( (tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) );
      }
    } else {
      return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 ==null )) {
      return l2;
    } else if(( l2 ==null )) {
      return l1;
    } else if(( (l1 instanceof tom.library.sl.Choice) )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {
        return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):new tom.library.sl.Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) );
      } else {
        return ( (tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):new tom.library.sl.Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) );
      }
    } else {
      return ( (l2==null)?l1:new tom.library.sl.Choice(l1,l2) );
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals(( null ))) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return ( (( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin):new tom.library.sl.Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)) );
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 == null )) {
      return l2;
    } else if(( l2 == null )) {
      return l1;
    } else if(( (l1 instanceof tom.library.sl.SequenceId) )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {
        return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ):new tom.library.sl.SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) );
      } else {
        return ( (tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ):new tom.library.sl.SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) );
      }
    } else {
      return ( (l2==null)?l1:new tom.library.sl.SequenceId(l1,l2) );
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return ( (( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( (begin instanceof tom.library.sl.SequenceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.SequenceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin):new tom.library.sl.SequenceId(((( (begin instanceof tom.library.sl.SequenceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( (begin instanceof tom.library.sl.SequenceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ):( null )),end,tail)) );
  }
  
  private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {
    if(( l1 ==null )) {
      return l2;
    } else if(( l2 ==null )) {
      return l1;
    } else if(( (l1 instanceof tom.library.sl.ChoiceId) )) {
      if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {
        return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ):new tom.library.sl.ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) );
      } else {
        return ( (tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ):new tom.library.sl.ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) );
      }
    } else {
      return ( (l2==null)?l1:new tom.library.sl.ChoiceId(l1,l2) );
    }
  }
  private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {
    if( (begin.equals(end)) ) {
      return tail;
    } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals(( null ))) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return ( (( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( (begin instanceof tom.library.sl.ChoiceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.ChoiceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin):new tom.library.sl.ChoiceId(((( (begin instanceof tom.library.sl.ChoiceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( (begin instanceof tom.library.sl.ChoiceId) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ):( null )),end,tail)) );
  }
  private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ),( (( (( null )==null)?( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )==null)?( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Sequence(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )) ):new tom.library.sl.Choice(( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )==null)?( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Sequence(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )) ),( null )) )==null)?s2:new tom.library.sl.Choice(s2,( (( null )==null)?( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )==null)?( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Sequence(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )) ):new tom.library.sl.Choice(( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )==null)?( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Sequence(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.Identity() )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ),( null )) )) ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ),( (( (( null )==null)?( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Choice(( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( null )) )==null)?s2:new tom.library.sl.Choice(s2,( (( null )==null)?( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )) ):new tom.library.sl.Choice(( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )==null)?s1:new tom.library.sl.Sequence(s1,( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ),( null )) )) ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { 
return ( 
( (( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )==null)?s:new tom.library.sl.Choice(s,( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )==null)?( (( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.Sequence(( new tom.library.sl.MuVar("_x") ),( null )) )==null)?s:new tom.library.sl.Sequence(s,( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.Sequence(( new tom.library.sl.MuVar("_x") ),( null )) )) ):new tom.library.sl.Choice(( (( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.Sequence(( new tom.library.sl.MuVar("_x") ),( null )) )==null)?s:new tom.library.sl.Sequence(s,( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.Sequence(( new tom.library.sl.MuVar("_x") ),( null )) )) ),( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_TopDownCollect( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),tom_make_Try(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) ))) ))

;
}
private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Choice(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Choice(v,( (( null )==null)?( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Choice(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.SequenceId(( new tom.library.sl.MuVar("_x") ),( null )) )==null)?v:new tom.library.sl.SequenceId(v,( (( null )==null)?( new tom.library.sl.MuVar("_x") ):new tom.library.sl.SequenceId(( new tom.library.sl.MuVar("_x") ),( null )) )) )) ))

;
}
private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { 
return ( 
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.ChoiceId(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.ChoiceId(v,( (( null )==null)?( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.ChoiceId(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ))

;
}


// Different kind of structures
protected final static int TERM_APPL               = 0;
protected final static int UNAMED_APPL             = 1;
protected final static int APPL_DISJUNCTION        = 2;
protected final static int RECORD_APPL             = 3;
protected final static int RECORD_APPL_DISJUNCTION = 4;
protected final static int XML_APPL                = 5;
protected final static int VARIABLE_STAR           = 6;
protected final static int UNAMED_VARIABLE_STAR    = 7;
protected final static int UNAMED_VARIABLE         = 8;
protected final static int VARIABLE                = 9;

protected Option currentTomStructureOrgTrack;

/** the declared options string */
public static final String DECLARED_OPTIONS = 
"<options>" +
"<boolean name='noTypeCheck' altName='' description='Do not perform type checking' value='false'/>" +
"</options>";

/** Constructor */
public TypeCheckerPlugin() {
super("TypeCheckerPlugin");
}

/**
* inherited from OptionOwner interface (plugin) 
*/
public PlatformOptionList getDeclaredOptionList() {
return OptionParser.xmlToOptionList(TypeCheckerPlugin.DECLARED_OPTIONS);
}

protected void reinit() {
currentTomStructureOrgTrack = null;
}

public int getClass(TomTerm term) {

{
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch106_1= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch106_1 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch106_1 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch106_1.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tomMatch106_6= tomMatch106_1.getHeadconcTomName() ;
if ( (tomMatch106_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if ( "".equals( tomMatch106_6.getString() ) ) {
if (  tomMatch106_1.getTailconcTomName() .isEmptyconcTomName() ) {
return UNAMED_APPL;
}
}
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch106_9= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch106_9 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch106_9 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch106_9.isEmptyconcTomName() )) {
if ( ( tomMatch106_9.getHeadconcTomName()  instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if (  tomMatch106_9.getTailconcTomName() .isEmptyconcTomName() ) {
return TERM_APPL;
}
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch106_16= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch106_16 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch106_16 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch106_16.isEmptyconcTomName() )) {
if ( ( tomMatch106_16.getHeadconcTomName()  instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
return APPL_DISJUNCTION;
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch106_24= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch106_24 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch106_24 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch106_24.isEmptyconcTomName() )) {
if ( ( tomMatch106_24.getHeadconcTomName()  instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if (  tomMatch106_24.getTailconcTomName() .isEmptyconcTomName() ) {
return RECORD_APPL;
}
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch106_31= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch106_31 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch106_31 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch106_31.isEmptyconcTomName() )) {
if ( ( tomMatch106_31.getHeadconcTomName()  instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
return RECORD_APPL_DISJUNCTION;
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.XMLAppl) ) {
return XML_APPL;
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {
return VARIABLE_STAR;
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {
return VARIABLE;
}
}

}


}

throw new TomRuntimeException("Invalid Term");
}

public String getName(TomTerm term) {
String dijunctionName = "";

{
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch107_1= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch107_1 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch107_1 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch107_1.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tomMatch107_6= tomMatch107_1.getHeadconcTomName() ;
if ( (tomMatch107_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if (  tomMatch107_1.getTailconcTomName() .isEmptyconcTomName() ) {
return 
 tomMatch107_6.getString() ;

}
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tom_nameList= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;

String head;
dijunctionName = 
tom_nameList.getHeadconcTomName().getString();
while(!
tom_nameList.isEmptyconcTomName()) {
head = 
tom_nameList.getHeadconcTomName().getString();
dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;

tom_nameList= 
tom_nameList.getTailconcTomName();
}
return dijunctionName;


}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch107_11= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch107_11 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch107_11 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch107_11.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tomMatch107_16= tomMatch107_11.getHeadconcTomName() ;
if ( (tomMatch107_16 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if (  tomMatch107_11.getTailconcTomName() .isEmptyconcTomName() ) {
return 
 tomMatch107_16.getString() ;

}
}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.RecordAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tom_nameList= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;

String head;
dijunctionName = 
tom_nameList.getHeadconcTomName().getString();
while(!
tom_nameList.isEmptyconcTomName()) {
head = 
tom_nameList.getHeadconcTomName().getString();
dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;

tom_nameList= 
tom_nameList.getTailconcTomName();
}
return dijunctionName;


}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.XMLAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tomMatch107_21= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;
if ( ((tomMatch107_21 instanceof tom.engine.adt.tomname.types.tomnamelist.ConsconcTomName) || (tomMatch107_21 instanceof tom.engine.adt.tomname.types.tomnamelist.EmptyconcTomName)) ) {
if (!( tomMatch107_21.isEmptyconcTomName() )) {
 tom.engine.adt.tomname.types.TomName  tomMatch107_27= tomMatch107_21.getHeadconcTomName() ;
if ( (tomMatch107_27 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
return 
 tomMatch107_27.getString() ;

}
}
}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.XMLAppl) ) {
 tom.engine.adt.tomname.types.TomNameList  tom_nameList= (( tom.engine.adt.tomterm.types.TomTerm )term).getNameList() ;

String head;
dijunctionName = 
tom_nameList.getHeadconcTomName().getString();
while(!
tom_nameList.isEmptyconcTomName()) {
head = 
tom_nameList.getHeadconcTomName().getString();
dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;

tom_nameList= 
tom_nameList.getTailconcTomName();
}
return dijunctionName;


}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {
 tom.engine.adt.tomname.types.TomName  tomMatch107_32= (( tom.engine.adt.tomterm.types.TomTerm )term).getAstName() ;
if ( (tomMatch107_32 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
return 
 tomMatch107_32.getString() ;

}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {
 tom.engine.adt.tomname.types.TomName  tomMatch107_37= (( tom.engine.adt.tomterm.types.TomTerm )term).getAstName() ;
if ( (tomMatch107_37 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
return 
 tomMatch107_37.getString() +"*";

}
}
}

}
{
if ( (term instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )term) instanceof tom.engine.adt.tomterm.types.tomterm.AntiTerm) ) {
return getName(
 (( tom.engine.adt.tomterm.types.TomTerm )term).getTomTerm() ); 

}
}

}


}

throw new TomRuntimeException("Invalid Term:" + term);
}

/**
* Shared Functions 
*/
protected String extractType(TomSymbol symbol) {
TomType type = TomBase.getSymbolCodomain(symbol);
return TomBase.getTomType(type);
}

protected String findOriginTrackingFileName(OptionList optionList) {

{
{
if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {
if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) {
 tom.engine.adt.tomoption.types.OptionList  tomMatch108__end__4=(( tom.engine.adt.tomoption.types.OptionList )optionList);
do {
{
if (!( tomMatch108__end__4.isEmptyconcOption() )) {
 tom.engine.adt.tomoption.types.Option  tomMatch108_8= tomMatch108__end__4.getHeadconcOption() ;
if ( (tomMatch108_8 instanceof tom.engine.adt.tomoption.types.option.OriginTracking) ) {
return 
 tomMatch108_8.getFileName() ; 

}
}
if ( tomMatch108__end__4.isEmptyconcOption() ) {
tomMatch108__end__4=(( tom.engine.adt.tomoption.types.OptionList )optionList);
} else {
tomMatch108__end__4= tomMatch108__end__4.getTailconcOption() ;
}

}
} while(!( (tomMatch108__end__4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));
}
}

}

}

return "unknown filename";
}

protected int findOriginTrackingLine(OptionList optionList) {

{
{
if ( (optionList instanceof tom.engine.adt.tomoption.types.OptionList) ) {
if ( (((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.ConsconcOption) || ((( tom.engine.adt.tomoption.types.OptionList )optionList) instanceof tom.engine.adt.tomoption.types.optionlist.EmptyconcOption)) ) {
 tom.engine.adt.tomoption.types.OptionList  tomMatch109__end__4=(( tom.engine.adt.tomoption.types.OptionList )optionList);
do {
{
if (!( tomMatch109__end__4.isEmptyconcOption() )) {
 tom.engine.adt.tomoption.types.Option  tomMatch109_8= tomMatch109__end__4.getHeadconcOption() ;
if ( (tomMatch109_8 instanceof tom.engine.adt.tomoption.types.option.OriginTracking) ) {
return 
 tomMatch109_8.getLine() ; 

}
}
if ( tomMatch109__end__4.isEmptyconcOption() ) {
tomMatch109__end__4=(( tom.engine.adt.tomoption.types.OptionList )optionList);
} else {
tomMatch109__end__4= tomMatch109__end__4.getTailconcOption() ;
}

}
} while(!( (tomMatch109__end__4==(( tom.engine.adt.tomoption.types.OptionList )optionList)) ));
}
}

}

}

return -1;
}

protected void ensureOriginTrackingLine(int line) {
if(line < 0) {
TomMessage.error(getLogger(),
getStreamManager().getInputFileName(), 0,
TomMessage.findOTL);
//System.out.println("findOriginTrackingLine: not found ");
}
}

public void run(Map informationTracker) {
if(isActivated()) {
long startChrono = System.currentTimeMillis();
try {
// clean up internals
reinit();
// perform analyse
try {
Code subject = (Code) getWorkingTerm();

tom_make_TopDownCollect(tom_make_checkTypeInference(this)).visitLight(subject);
} catch(tom.library.sl.VisitFailure e) {
System.out.println("strategy failed");
}
// verbose
TomMessage.info(getLogger(),null,0,TomMessage.tomTypeCheckingPhase,
Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
} catch (Exception e) {
TomMessage.error(getLogger(), 
getStreamManager().getInputFileName(), 0,
TomMessage.exceptionMessage,
getClass().getName(), 
getStreamManager().getInputFileName(),
e.getMessage() );
e.printStackTrace();
}
} else {
// type checker desactivated    
TomMessage.info(getLogger(),null,0,TomMessage.typeCheckerInactivated);
}
}

private boolean isActivated() {
return !getOptionBooleanValue("noTypeCheck");
}

/**
* Main type checking entry point:
* We check all Match
*/


public static class checkTypeInference extends tom.library.sl.AbstractStrategyBasic {
private  TypeCheckerPlugin  tcp;
public checkTypeInference( TypeCheckerPlugin  tcp) {
super(( new tom.library.sl.Identity() ));
this.tcp=tcp;
}
public  TypeCheckerPlugin  gettcp() {
return tcp;
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
public  tom.engine.adt.tominstruction.types.Instruction  visit_Instruction( tom.engine.adt.tominstruction.types.Instruction  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.tominstruction.types.Instruction) ) {
if ( ((( tom.engine.adt.tominstruction.types.Instruction )tom__arg) instanceof tom.engine.adt.tominstruction.types.instruction.Match) ) {

tcp.currentTomStructureOrgTrack = TomBase.findOriginTracking(
 (( tom.engine.adt.tominstruction.types.Instruction )tom__arg).getOptions() );
tcp.verifyMatchVariable(
 (( tom.engine.adt.tominstruction.types.Instruction )tom__arg).getConstraintInstructionList() );
throw new tom.library.sl.VisitFailure();// to stop the top-downd


}
}

}

}
return _visit_Instruction(tom__arg,introspector);

}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tomdeclaration.types.Declaration  visit_Declaration( tom.engine.adt.tomdeclaration.types.Declaration  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg) instanceof tom.engine.adt.tomdeclaration.types.declaration.Strategy) ) {

tcp.currentTomStructureOrgTrack = 
 (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getOrgTrack() ;
tcp.verifyStrategyVariable(
 (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getVisitList() );
throw new tom.library.sl.VisitFailure();// to stop the top-downd


}
}

}

}
return _visit_Declaration(tom__arg,introspector);

}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tominstruction.types.Instruction  _visit_Instruction( tom.engine.adt.tominstruction.types.Instruction  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.engine.adt.tominstruction.types.Instruction )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tomdeclaration.types.Declaration  _visit_Declaration( tom.engine.adt.tomdeclaration.types.Declaration  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.engine.adt.tomdeclaration.types.Declaration )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.engine.adt.tominstruction.types.Instruction) ) {
return ((T)visit_Instruction((( tom.engine.adt.tominstruction.types.Instruction )v),introspector));
}
if ( (v instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
return ((T)visit_Declaration((( tom.engine.adt.tomdeclaration.types.Declaration )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
}
private static  tom.library.sl.Strategy  tom_make_checkTypeInference( TypeCheckerPlugin  t0) { 
return new checkTypeInference(t0);
}


/* 
* Collect unknown (not in symbol table) appls without ()
*/

public static class collectUnknownAppls extends tom.library.sl.AbstractStrategyBasic {
private  TypeCheckerPlugin  tcp;
public collectUnknownAppls( TypeCheckerPlugin  tcp) {
super(( new tom.library.sl.Identity() ));
this.tcp=tcp;
}
public  TypeCheckerPlugin  gettcp() {
return tcp;
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
public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
if ( ((( tom.engine.adt.tomterm.types.TomTerm )tom__arg) instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {
 tom.engine.adt.tomterm.types.TomTerm  tom_app=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg);

if(tcp.getSymbolTable().getSymbolFromName(tcp.getName(
tom_app))==null) {
TomMessage.error(tcp.getLogger(),
tcp.findOriginTrackingFileName(
tom_app.getOptions()),
tcp.findOriginTrackingLine(
tom_app.getOptions()),
TomMessage.unknownVariableInWhen,
tcp.getName(
tom_app));
}
// else, it's actually app()
// else, it's a unknown (ie : java) function


}
}

}

}
return _visit_TomTerm(tom__arg,introspector);

}
@SuppressWarnings("unchecked")
public  tom.engine.adt.tomterm.types.TomTerm  _visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.engine.adt.tomterm.types.TomTerm )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.engine.adt.tomterm.types.TomTerm) ) {
return ((T)visit_TomTerm((( tom.engine.adt.tomterm.types.TomTerm )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
}


private void verifyMatchVariable(ConstraintInstructionList constraintInstructionList) throws VisitFailure {

{
{
if ( (constraintInstructionList instanceof tom.engine.adt.tominstruction.types.ConstraintInstructionList) ) {
if ( (((( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList) instanceof tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction) || ((( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList) instanceof tom.engine.adt.tominstruction.types.constraintinstructionlist.EmptyconcConstraintInstruction)) ) {
 tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch113__end__4=(( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList);
do {
{
if (!( tomMatch113__end__4.isEmptyconcConstraintInstruction() )) {
 tom.engine.adt.tominstruction.types.ConstraintInstruction  tomMatch113_9= tomMatch113__end__4.getHeadconcConstraintInstruction() ;
if ( (tomMatch113_9 instanceof tom.engine.adt.tominstruction.types.constraintinstruction.ConstraintInstruction) ) {

// collect variables
ArrayList<TomTerm> variableList = new ArrayList<TomTerm>();
TomBase.collectVariable(variableList, 
 tomMatch113_9.getConstraint() , false); 
verifyVariableTypeListCoherence(variableList);        

// TODO: check in the action that a VariableStar is under the right symbol
verifyListVariableInAction(
 tomMatch113_9.getAction() );


}
}
if ( tomMatch113__end__4.isEmptyconcConstraintInstruction() ) {
tomMatch113__end__4=(( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList);
} else {
tomMatch113__end__4= tomMatch113__end__4.getTailconcConstraintInstruction() ;
}

}
} while(!( (tomMatch113__end__4==(( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList)) ));
}
}

}

}

}

/**
* check that variables that appear more than once have coherent types
* @param list the list of variables
*/
private void verifyVariableTypeListCoherence(ArrayList<TomTerm> list) {
// compute multiplicities
HashMap<TomName,TomTerm> map = new HashMap<TomName,TomTerm>();
for(TomTerm variable:list) {
TomName name = variable.getAstName();
if(map.containsKey(name)) {
TomTerm var = map.get(name);
TomType type1 = var.getAstType();
TomType type2 = variable.getAstType();
// we use getTomType because type1 may be a TypeWithSymbol and type2 a TomType
if(!TomBase.getTomType(type1).equals(TomBase.getTomType(type2))) {
TomMessage.error(getLogger(),
findOriginTrackingFileName(variable.getOptions()),
findOriginTrackingLine(variable.getOptions()),
TomMessage.incoherentVariable,
name.getString(),
TomBase.getTomType(type1),
TomBase.getTomType(type2));
}
} else {
map.put(name,variable);
}
}
}

/*
* forbid to use a X* under the wrong symbol
* like in f(X*) -> g(X*)
*/
private void verifyListVariableInAction(Instruction action) {
try {

tom_make_TopDown(tom_make_checkVariableStar(this)).visitLight(
action);
} catch(VisitFailure e) {
System.out.println("strategy failed");
}
}


public static class checkVariableStar extends tom.library.sl.AbstractStrategyBasic {
private  TypeCheckerPlugin  tcp;
public checkVariableStar( TypeCheckerPlugin  tcp) {
super(( new tom.library.sl.Identity() ));
this.tcp=tcp;
}
public  TypeCheckerPlugin  gettcp() {
return tcp;
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
public  tom.engine.adt.code.types.BQTerm  visit_BQTerm( tom.engine.adt.code.types.BQTerm  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.code.types.BQTerm) ) {
boolean tomMatch114_16= false ;
 tom.engine.adt.tomname.types.TomName  tomMatch114_1= null ;
 tom.engine.adt.code.types.BQTerm  tomMatch114_2= null ;
if ( ((( tom.engine.adt.code.types.BQTerm )tom__arg) instanceof tom.engine.adt.code.types.bqterm.BuildAppendList) ) {
{
tomMatch114_16= true ;
tomMatch114_1= (( tom.engine.adt.code.types.BQTerm )tom__arg).getAstName() ;
tomMatch114_2= (( tom.engine.adt.code.types.BQTerm )tom__arg).getHeadTerm() ;

}
} else {
if ( ((( tom.engine.adt.code.types.BQTerm )tom__arg) instanceof tom.engine.adt.code.types.bqterm.BuildAppendArray) ) {
{
tomMatch114_16= true ;
tomMatch114_1= (( tom.engine.adt.code.types.BQTerm )tom__arg).getAstName() ;
tomMatch114_2= (( tom.engine.adt.code.types.BQTerm )tom__arg).getHeadTerm() ;

}
}
}
if (tomMatch114_16) {
if ( (tomMatch114_1 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
 String  tom_listName= tomMatch114_1.getString() ;
if ( (tomMatch114_2 instanceof tom.engine.adt.code.types.bqterm.BQVariableStar) ) {
 tom.engine.adt.tomname.types.TomName  tomMatch114_7= tomMatch114_2.getAstName() ;
 tom.engine.adt.tomtype.types.TomType  tomMatch114_8= tomMatch114_2.getAstType() ;
 tom.engine.adt.tomoption.types.OptionList  tom_optionList= tomMatch114_2.getOptions() ;
if ( (tomMatch114_7 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
if ( (tomMatch114_8 instanceof tom.engine.adt.tomtype.types.tomtype.TypeWithSymbol) ) {
 tom.engine.adt.tomname.types.TomName  tomMatch114_12= tomMatch114_8.getRootSymbolName() ;
if ( (tomMatch114_12 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {
 String  tom_rootName= tomMatch114_12.getString() ;

if(!
tom_listName.equals(
tom_rootName)) {
TomMessage.error(tcp.getLogger(),
tcp.findOriginTrackingFileName(
tom_optionList),
tcp.findOriginTrackingLine(
tom_optionList),
TomMessage.incoherentVariableStar,

 tomMatch114_7.getString() , 
tom_rootName, 
tom_listName);
}


}
}
}
}
}
}

}

}

}
return _visit_BQTerm(tom__arg,introspector);

}
@SuppressWarnings("unchecked")
public  tom.engine.adt.code.types.BQTerm  _visit_BQTerm( tom.engine.adt.code.types.BQTerm  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.engine.adt.code.types.BQTerm )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.engine.adt.code.types.BQTerm) ) {
return ((T)visit_BQTerm((( tom.engine.adt.code.types.BQTerm )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
}
private static  tom.library.sl.Strategy  tom_make_checkVariableStar( TypeCheckerPlugin  t0) { 
return new checkVariableStar(t0);
}


private void verifyStrategyVariable(TomVisitList list) {
/* %strategy : error if there is no visit */
if(
list.isEmptyconcTomVisit()) {
TomMessage.error(getLogger(),null,0,TomMessage.emptyStrategy);
}

{
{
if ( (list instanceof tom.engine.adt.tomsignature.types.TomVisitList) ) {
if ( (((( tom.engine.adt.tomsignature.types.TomVisitList )list) instanceof tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit) || ((( tom.engine.adt.tomsignature.types.TomVisitList )list) instanceof tom.engine.adt.tomsignature.types.tomvisitlist.EmptyconcTomVisit)) ) {
 tom.engine.adt.tomsignature.types.TomVisitList  tomMatch115__end__4=(( tom.engine.adt.tomsignature.types.TomVisitList )list);
do {
{
if (!( tomMatch115__end__4.isEmptyconcTomVisit() )) {
 tom.engine.adt.tomsignature.types.TomVisit  tomMatch115_10= tomMatch115__end__4.getHeadconcTomVisit() ;
if ( (tomMatch115_10 instanceof tom.engine.adt.tomsignature.types.tomvisit.VisitTerm) ) {
 tom.engine.adt.tomtype.types.TomType  tomMatch115_7= tomMatch115_10.getVNode() ;
if ( (tomMatch115_7 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
if ( ( tomMatch115_7.getTlType()  instanceof tom.engine.adt.tomtype.types.targetlanguagetype.EmptyTargetLanguageType) ) {
 tom.engine.adt.tomoption.types.OptionList  tom_optionList= tomMatch115_10.getOptions() ;

TomMessage.error(getLogger(),
findOriginTrackingFileName(
tom_optionList),
findOriginTrackingLine(
tom_optionList),
TomMessage.unknownVisitedType,

 tomMatch115_7.getTomType() );


}
}
}
}
if ( tomMatch115__end__4.isEmptyconcTomVisit() ) {
tomMatch115__end__4=(( tom.engine.adt.tomsignature.types.TomVisitList )list);
} else {
tomMatch115__end__4= tomMatch115__end__4.getTailconcTomVisit() ;
}

}
} while(!( (tomMatch115__end__4==(( tom.engine.adt.tomsignature.types.TomVisitList )list)) ));
}
}

}

}

}

}
