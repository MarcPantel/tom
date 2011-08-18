/*
* 
* TOM - To One Matching Expander
* 
* Copyright (c) 2000-2011, INPL, INRIA
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

package tom.engine.expander;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.engine.exception.TomRuntimeException;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomsignature.types.tomsymbollist.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.code.types.*;

import tom.engine.adt.tominstruction.types.constraintinstructionlist.concConstraintInstruction;
import tom.engine.adt.tomslot.types.slotlist.concSlot;
import tom.engine.adt.tomsignature.types.tomvisitlist.concTomVisit;
import tom.engine.adt.tomdeclaration.types.declaration.IntrospectorClass;
import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;
import tom.engine.tools.TomGenericPlugin;
import tom.engine.tools.Tools;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;

import tom.library.sl.*;

/**
* The Expander plugin.
*/
public class ExpanderPlugin extends TomGenericPlugin {



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
  
  private static   tom.engine.adt.tomsignature.types.TomSymbolList  tom_append_list_concTomSymbol( tom.engine.adt.tomsignature.types.TomSymbolList l1,  tom.engine.adt.tomsignature.types.TomSymbolList  l2) {
    if( l1.isEmptyconcTomSymbol() ) {
      return l2;
    } else if( l2.isEmptyconcTomSymbol() ) {
      return l1;
    } else if(  l1.getTailconcTomSymbol() .isEmptyconcTomSymbol() ) {
      return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( l1.getHeadconcTomSymbol() ,l2) ;
    } else {
      return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( l1.getHeadconcTomSymbol() ,tom_append_list_concTomSymbol( l1.getTailconcTomSymbol() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomsignature.types.TomSymbolList  tom_get_slice_concTomSymbol( tom.engine.adt.tomsignature.types.TomSymbolList  begin,  tom.engine.adt.tomsignature.types.TomSymbolList  end, tom.engine.adt.tomsignature.types.TomSymbolList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcTomSymbol()  ||  (end== tom.engine.adt.tomsignature.types.tomsymbollist.EmptyconcTomSymbol.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol.make( begin.getHeadconcTomSymbol() ,( tom.engine.adt.tomsignature.types.TomSymbolList )tom_get_slice_concTomSymbol( begin.getTailconcTomSymbol() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tomdeclaration.types.DeclarationList  tom_append_list_concDeclaration( tom.engine.adt.tomdeclaration.types.DeclarationList l1,  tom.engine.adt.tomdeclaration.types.DeclarationList  l2) {
    if( l1.isEmptyconcDeclaration() ) {
      return l2;
    } else if( l2.isEmptyconcDeclaration() ) {
      return l1;
    } else if(  l1.getTailconcDeclaration() .isEmptyconcDeclaration() ) {
      return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( l1.getHeadconcDeclaration() ,l2) ;
    } else {
      return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( l1.getHeadconcDeclaration() ,tom_append_list_concDeclaration( l1.getTailconcDeclaration() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomdeclaration.types.DeclarationList  tom_get_slice_concDeclaration( tom.engine.adt.tomdeclaration.types.DeclarationList  begin,  tom.engine.adt.tomdeclaration.types.DeclarationList  end, tom.engine.adt.tomdeclaration.types.DeclarationList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcDeclaration()  ||  (end== tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( begin.getHeadconcDeclaration() ,( tom.engine.adt.tomdeclaration.types.DeclarationList )tom_get_slice_concDeclaration( begin.getTailconcDeclaration() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tomtype.types.TomTypeList  tom_append_list_concTomType( tom.engine.adt.tomtype.types.TomTypeList l1,  tom.engine.adt.tomtype.types.TomTypeList  l2) {
    if( l1.isEmptyconcTomType() ) {
      return l2;
    } else if( l2.isEmptyconcTomType() ) {
      return l1;
    } else if(  l1.getTailconcTomType() .isEmptyconcTomType() ) {
      return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,l2) ;
    } else {
      return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( l1.getHeadconcTomType() ,tom_append_list_concTomType( l1.getTailconcTomType() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomtype.types.TomTypeList  tom_get_slice_concTomType( tom.engine.adt.tomtype.types.TomTypeList  begin,  tom.engine.adt.tomtype.types.TomTypeList  end, tom.engine.adt.tomtype.types.TomTypeList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcTomType()  ||  (end== tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType.make( begin.getHeadconcTomType() ,( tom.engine.adt.tomtype.types.TomTypeList )tom_get_slice_concTomType( begin.getTailconcTomType() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tomtype.types.TypeOptionList  tom_append_list_concTypeOption( tom.engine.adt.tomtype.types.TypeOptionList l1,  tom.engine.adt.tomtype.types.TypeOptionList  l2) {
    if( l1.isEmptyconcTypeOption() ) {
      return l2;
    } else if( l2.isEmptyconcTypeOption() ) {
      return l1;
    } else if(  l1.getTailconcTypeOption() .isEmptyconcTypeOption() ) {
      return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( l1.getHeadconcTypeOption() ,l2) ;
    } else {
      return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( l1.getHeadconcTypeOption() ,tom_append_list_concTypeOption( l1.getTailconcTypeOption() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tomtype.types.TypeOptionList  tom_get_slice_concTypeOption( tom.engine.adt.tomtype.types.TypeOptionList  begin,  tom.engine.adt.tomtype.types.TypeOptionList  end, tom.engine.adt.tomtype.types.TypeOptionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcTypeOption()  ||  (end== tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tomtype.types.typeoptionlist.ConsconcTypeOption.make( begin.getHeadconcTypeOption() ,( tom.engine.adt.tomtype.types.TypeOptionList )tom_get_slice_concTypeOption( begin.getTailconcTypeOption() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.code.types.CodeList  tom_append_list_concCode( tom.engine.adt.code.types.CodeList l1,  tom.engine.adt.code.types.CodeList  l2) {
    if( l1.isEmptyconcCode() ) {
      return l2;
    } else if( l2.isEmptyconcCode() ) {
      return l1;
    } else if(  l1.getTailconcCode() .isEmptyconcCode() ) {
      return  tom.engine.adt.code.types.codelist.ConsconcCode.make( l1.getHeadconcCode() ,l2) ;
    } else {
      return  tom.engine.adt.code.types.codelist.ConsconcCode.make( l1.getHeadconcCode() ,tom_append_list_concCode( l1.getTailconcCode() ,l2)) ;
    }
  }
  private static   tom.engine.adt.code.types.CodeList  tom_get_slice_concCode( tom.engine.adt.code.types.CodeList  begin,  tom.engine.adt.code.types.CodeList  end, tom.engine.adt.code.types.CodeList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcCode()  ||  (end== tom.engine.adt.code.types.codelist.EmptyconcCode.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.code.types.codelist.ConsconcCode.make( begin.getHeadconcCode() ,( tom.engine.adt.code.types.CodeList )tom_get_slice_concCode( begin.getTailconcCode() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.code.types.BQTermList  tom_append_list_concBQTerm( tom.engine.adt.code.types.BQTermList l1,  tom.engine.adt.code.types.BQTermList  l2) {
    if( l1.isEmptyconcBQTerm() ) {
      return l2;
    } else if( l2.isEmptyconcBQTerm() ) {
      return l1;
    } else if(  l1.getTailconcBQTerm() .isEmptyconcBQTerm() ) {
      return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,l2) ;
    } else {
      return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( l1.getHeadconcBQTerm() ,tom_append_list_concBQTerm( l1.getTailconcBQTerm() ,l2)) ;
    }
  }
  private static   tom.engine.adt.code.types.BQTermList  tom_get_slice_concBQTerm( tom.engine.adt.code.types.BQTermList  begin,  tom.engine.adt.code.types.BQTermList  end, tom.engine.adt.code.types.BQTermList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcBQTerm()  ||  (end== tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( begin.getHeadconcBQTerm() ,( tom.engine.adt.code.types.BQTermList )tom_get_slice_concBQTerm( begin.getTailconcBQTerm() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.code.types.BQTerm  tom_append_list_Composite( tom.engine.adt.code.types.BQTerm l1,  tom.engine.adt.code.types.BQTerm  l2) {
    if( l1.isEmptyComposite() ) {
      return l2;
    } else if( l2.isEmptyComposite() ) {
      return l1;
    } else if(  l1.getTailComposite() .isEmptyComposite() ) {
      return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,l2) ;
    } else {
      return  tom.engine.adt.code.types.bqterm.ConsComposite.make( l1.getHeadComposite() ,tom_append_list_Composite( l1.getTailComposite() ,l2)) ;
    }
  }
  private static   tom.engine.adt.code.types.BQTerm  tom_get_slice_Composite( tom.engine.adt.code.types.BQTerm  begin,  tom.engine.adt.code.types.BQTerm  end, tom.engine.adt.code.types.BQTerm  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyComposite()  ||  (end== tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.code.types.bqterm.ConsComposite.make( begin.getHeadComposite() ,( tom.engine.adt.code.types.BQTerm )tom_get_slice_Composite( begin.getTailComposite() ,end,tail)) ;
  }
  
  private static   tom.engine.adt.tominstruction.types.InstructionList  tom_append_list_concInstruction( tom.engine.adt.tominstruction.types.InstructionList l1,  tom.engine.adt.tominstruction.types.InstructionList  l2) {
    if( l1.isEmptyconcInstruction() ) {
      return l2;
    } else if( l2.isEmptyconcInstruction() ) {
      return l1;
    } else if(  l1.getTailconcInstruction() .isEmptyconcInstruction() ) {
      return  tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( l1.getHeadconcInstruction() ,l2) ;
    } else {
      return  tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( l1.getHeadconcInstruction() ,tom_append_list_concInstruction( l1.getTailconcInstruction() ,l2)) ;
    }
  }
  private static   tom.engine.adt.tominstruction.types.InstructionList  tom_get_slice_concInstruction( tom.engine.adt.tominstruction.types.InstructionList  begin,  tom.engine.adt.tominstruction.types.InstructionList  end, tom.engine.adt.tominstruction.types.InstructionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyconcInstruction()  ||  (end== tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( begin.getHeadconcInstruction() ,( tom.engine.adt.tominstruction.types.InstructionList )tom_get_slice_concInstruction( begin.getTailconcInstruction() ,end,tail)) ;
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
private static  tom.library.sl.Strategy  tom_make_TopDownIdStopOnSuccess( tom.library.sl.Strategy  v) { 
return (
( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) ) )) 

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


private static Logger logger = Logger.getLogger("tom.engine.expander.ExpanderPlugin");
/** some output suffixes */
public static final String EXPANDED_SUFFIX = ".tfix.expanded";

/** the declared options string */
public static final String DECLARED_OPTIONS = 
"<options>" +
"<boolean name='expand' altName='' description='Expander (activated by default)' value='true'/>" +
"<boolean name='genIntrospector' altName='gi' description='Generate a class that implements Introspector to apply strategies on non visitable terms' value='false'/>" +
"</options>";

private static final TomType objectType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","Object");
private static final TomType genericType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","T");
private static final TomType methodparameterType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","<T> T");
private static final TomType objectArrayType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","Object[]");
private static final TomType intType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"int","int");

private static final TomType basicStratType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","tom.library.sl.AbstractStrategyBasic");
private static final TomType introspectorType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","tom.library.sl.Introspector");
private static final TomType visitfailureType = ASTFactory.makeType(
 tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() ,"undefined","tom.library.sl.VisitFailure");
// introspector argument of visitLight
private static final BQTerm introspectorVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("introspector") , introspectorType) ;
private static final BQTerm objectVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("o") , objectType) ;
private static final BQTerm childVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("child") , objectType) ;
private static final BQTerm intVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("i") , intType) ;
private static final BQTerm objectArrayVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("children") , objectArrayType) ;

/** if the flag is true, a class that implements Introspector is generated */
private boolean genIntrospector = false;
private boolean generatedIntrospector = false;

public boolean getGenIntrospector() {
return genIntrospector;
}

private void setGenIntrospector(boolean genIntrospector) {
this.genIntrospector = genIntrospector;
}

public boolean getGeneratedIntrospector() {
return generatedIntrospector;
}

private void setGeneratedIntrospector(boolean generatedIntrospector) {
this.generatedIntrospector = generatedIntrospector;
}

/** Constructor */
public ExpanderPlugin() {
super("ExpanderPlugin");
}

public void run(Map informationTracker) {
long startChrono = System.currentTimeMillis();
boolean intermediate = getOptionBooleanValue("intermediate");    
setGenIntrospector(getOptionBooleanValue("genIntrospector"));
//System.out.println("(debug) I'm in the Tom expander : TSM"+getStreamManager().toString());
try {
//reinit the variable for intropsector generation
setGeneratedIntrospector(false);
Code expandedTerm = (Code) this.expand((Code)getWorkingTerm());
// verbose
TomMessage.info(logger,null,0,TomMessage.tomExpandingPhase, Integer.valueOf((int)(System.currentTimeMillis()-startChrono)) );
setWorkingTerm(expandedTerm);
if(intermediate) {
Tools.generateOutput(getStreamManager().getOutputFileName() + EXPANDED_SUFFIX, (Code)getWorkingTerm());
}
} catch(Exception e) {
TomMessage.error(logger,getStreamManager().getInputFileName(),0,TomMessage.exceptionMessage, e.getMessage());
e.printStackTrace();
}
}

public PlatformOptionList getDeclaredOptionList() {
return OptionParser.xmlToOptionList(ExpanderPlugin.DECLARED_OPTIONS);
}

private tom.library.sl.Visitable expand(tom.library.sl.Visitable subject) {
try {
return 
tom_make_TopDownIdStopOnSuccess(tom_make_Expand_once(this)).visitLight(subject);
} catch(VisitFailure e) {
throw new TomRuntimeException("ExpanderPlugin.expand: fail on " + subject);
}
}

/*
* Expand_once:
* compiles %strategy
* - generate instrospectors if -gi is activated
* - generate visitLight and visit
*/


public static class Expand_once extends tom.library.sl.AbstractStrategyBasic {
private  ExpanderPlugin  expander;
public Expand_once( ExpanderPlugin  expander) {
super(( new tom.library.sl.Identity() ));
this.expander=expander;
}
public  ExpanderPlugin  getexpander() {
return expander;
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
if ( (v instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
return ((T)visit_Declaration((( tom.engine.adt.tomdeclaration.types.Declaration )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
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
public  tom.engine.adt.tomdeclaration.types.Declaration  visit_Declaration( tom.engine.adt.tomdeclaration.types.Declaration  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg) instanceof tom.engine.adt.tomdeclaration.types.declaration.Strategy) ) {
 tom.engine.adt.tomsignature.types.TomVisitList  tom_visitList= (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getVisitList() ;
 tom.engine.adt.tomoption.types.Option  tom_orgTrack= (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getOrgTrack() ;

//Generate only one Introspector for a class if at least one  %strategy is found
Declaration introspectorClass = 
 tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration.make() ;
if(expander.getGenIntrospector() && !expander.getGeneratedIntrospector()) {
expander.setGeneratedIntrospector(true);
DeclarationList l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() ;
//generate the code for every method of Instrospector interface

SymbolTable symbolTable = expander.getSymbolTable();
Collection<TomType> types = symbolTable.getUsedTypes();

/**
* generate code for:
* public int getChildCount(Object o);
*/
String funcName = "getChildCount";//function name
//manage null children: return 0
InstructionList instructions = 
 tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("o==null") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) ;
for (TomType type:types) {
InstructionList instructionsForSort = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ;

{
{
if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {
if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 String  tom_typeName= (( tom.engine.adt.tomtype.types.TomType )type).getTomType() ;

if(!(symbolTable.isBuiltinType(
tom_typeName))) {
BQTerm var = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("v_"+tom_typeName) , type) ;
TomSymbolList list = symbolTable.getSymbolFromType(type);

{
{
if ( (list instanceof tom.engine.adt.tomsignature.types.TomSymbolList) ) {
if ( (((( tom.engine.adt.tomsignature.types.TomSymbolList )list) instanceof tom.engine.adt.tomsignature.types.tomsymbollist.ConsconcTomSymbol) || ((( tom.engine.adt.tomsignature.types.TomSymbolList )list) instanceof tom.engine.adt.tomsignature.types.tomsymbollist.EmptyconcTomSymbol)) ) {
 tom.engine.adt.tomsignature.types.TomSymbolList  tomMatch239__end__4=(( tom.engine.adt.tomsignature.types.TomSymbolList )list);
do {
{
if (!( tomMatch239__end__4.isEmptyconcTomSymbol() )) {
 tom.engine.adt.tomsignature.types.TomSymbol  tomMatch239_8= tomMatch239__end__4.getHeadconcTomSymbol() ;
if ( (tomMatch239_8 instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {
 tom.engine.adt.tomname.types.TomName  tom_opName= tomMatch239_8.getAstName() ;
 tom.engine.adt.tomsignature.types.TomSymbol  tom_symbol= tomMatch239__end__4.getHeadconcTomSymbol() ;

Instruction inst = 
 tom.engine.adt.tominstruction.types.instruction.Nop.make() ;
if ( TomBase.isListOperator(
tom_symbol) ) {
// manage empty lists and arrays
inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_opName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsEmptyList.make(tom_opName, var) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("2") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
} else if ( TomBase.isArrayOperator(
tom_symbol) ) {
inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_opName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsEmptyArray.make(tom_opName, var,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Integer.make(0) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("2") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
} else {
inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_opName, var) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make(""+TomBase.getArity(tom_symbol)) ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
} 
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );


}
}
if ( tomMatch239__end__4.isEmptyconcTomSymbol() ) {
tomMatch239__end__4=(( tom.engine.adt.tomsignature.types.TomSymbolList )list);
} else {
tomMatch239__end__4= tomMatch239__end__4.getTailconcTomSymbol() ;
}

}
} while(!( (tomMatch239__end__4==(( tom.engine.adt.tomsignature.types.TomSymbolList )list)) ));
}
}

}

}

instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsSort.make(type, objectVar) ,  tom.engine.adt.tominstruction.types.instruction.Let.make(var,  tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make(objectVar) ) ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructionsForSort) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
}


}
}

}

}

}
//default case (for builtins too): return 0
instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) , intType,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructions) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;
/**
* generate code for:
* public Object[] getChildren(Object o);
*/
funcName = "getChildren";//function name
instructions = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ;
for (TomType type:types) {
InstructionList instructionsForSort = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ;
//cast in concTomSymbol to use the for statement

{
{
if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {
if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 String  tom_typeName= (( tom.engine.adt.tomtype.types.TomType )type).getTomType() ;

if (! symbolTable.isBuiltinType(
tom_typeName)) {
BQTerm var = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("v_"+tom_typeName) , type) ;
concTomSymbol list = (concTomSymbol) symbolTable.getSymbolFromType(type);
for (TomSymbol symbol:list) {

{
{
if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {
if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {
 tom.engine.adt.tomtype.types.TomType  tomMatch241_2= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getTypesToType() ;
 tom.engine.adt.tomname.types.TomName  tom_symbolName= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getAstName() ;
if ( (tomMatch241_2 instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {
 tom.engine.adt.tomtype.types.TomType  tom_codomain= tomMatch241_2.getCodomain() ;

if(TomBase.isListOperator(symbol)) {
Instruction return_array = 
 tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.BQTermToCode.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("return new Object[]{") ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetHead.make(tom_symbolName,  tomMatch241_2.getDomain() .getHeadconcTomType(), var) ) ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make(",") ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetTail.make(tom_symbolName, var) ) ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("};") ) 
                                  , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ) ) ) ;
//default case (used for builtins too)                     
Instruction return_emptyArray = 
 tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.TargetLanguageToCode.make( tom.engine.adt.code.types.targetlanguage.ITL.make("return new Object[]{};") ) ) ;
Instruction inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsEmptyList.make(tom_symbolName, var) , return_emptyArray, return_array) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
} else if (TomBase.isArrayOperator(symbol)) {
//TODO
// we consider that the children of the array are the first element and the tail
//default case (used for builtins too)                     
BQTerm emptyArray = 
 tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("new Object[]{}") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ;
//`CodeToInstruction(TargetLanguageToCode(ITL("new Object[]{}")));
BQTerm tail = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ,  tom.engine.adt.tomname.types.tomname.Name.make("tail") , tom_codomain) ;

CodeList result = 
 tom.engine.adt.code.types.codelist.ConsconcCode.make( tom.engine.adt.code.types.code.BQTermToCode.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("new Object[]{") ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetElement.make(tom_symbolName, tom_codomain, var,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Integer.make(0) ) ) ) ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make(",") ) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeBQTerm.make(tail) , tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("}") ) 
                                  , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ) ) , tom.engine.adt.code.types.codelist.EmptyconcCode.make() ) ;

//Return(ExpressionToBQTerm(TomInstructionToExpression(emptyArray))),
Instruction inst = 

 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsEmptyArray.make(tom_symbolName, var,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Integer.make(0) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make(emptyArray) ,  tom.engine.adt.tominstruction.types.instruction.LetRef.make(intVar,  tom.engine.adt.tomexpression.types.expression.Integer.make(1) ,  tom.engine.adt.tominstruction.types.instruction.LetRef.make(tail,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.BuildEmptyArray.make(tom_symbolName,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.SubstractOne.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetSize.make(tom_symbolName, var) ) ) ) ) 
                                        ) ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make( tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.WhileDo.make( tom.engine.adt.tomexpression.types.expression.LessThan.make( tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make(intVar) ,  tom.engine.adt.tomexpression.types.expression.GetSize.make(tom_symbolName, var) ) ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make( tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Assign.make(tail,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.BuildConsArray.make(tom_symbolName,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetElement.make(tom_symbolName, tom_codomain, var, intVar) ) , tail) ) ) , tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Assign.make(intVar,  tom.engine.adt.tomexpression.types.expression.AddOne.make(intVar) ) 
                                                , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) ) ) 
                                            ) , tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.TomInstructionToExpression.make( tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.Tom.make(result) ) ) ) ) 
                                          , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) ) ) ) ) 
                                    ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );

} else {
int arity = TomBase.getArity(symbol);
BQTerm composite = 
 tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("return new Object[]{") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ;
PairNameDeclList pairNameDeclList = symbol.getPairNameDeclList();
for(int i=0; i< arity; i++) {
PairNameDecl pairNameDecl = pairNameDeclList.getHeadconcPairNameDecl();
Declaration decl = pairNameDecl.getSlotDecl();

{
{
if ( (decl instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )decl) instanceof tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration) ) {

// case of undefined getSlot
// return null (to be improved)
composite =  
tom_append_list_Composite(composite, tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("null") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) );
if(i < arity-1) {
composite =  
tom_append_list_Composite(composite, tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make(",") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) );
}


}
}

}
{
if ( (decl instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {
if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )decl) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetSlotDecl) ) {
 tom.engine.adt.tomname.types.TomName  tom_SlotName= (( tom.engine.adt.tomdeclaration.types.Declaration )decl).getSlotName() ;

composite = 
tom_append_list_Composite(composite, tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.GetSlot.make(TomBase.getSlotType(symbol,tom_SlotName),  (( tom.engine.adt.tomdeclaration.types.Declaration )decl).getAstName() , tom_SlotName.getString(), var) ) ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) );
if(i < arity-1) {
composite = 
tom_append_list_Composite(composite, tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make(",") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) );
}


}
}

}


}

pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
}
composite = 
tom_append_list_Composite(composite, tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("};") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) );
Instruction inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.BQTermToCode.make(composite) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
}


}
}
}

}

}

}
instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsSort.make(type, objectVar) ,  tom.engine.adt.tominstruction.types.instruction.Let.make(var,  tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make(objectVar) ) ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructionsForSort) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
} 


}
}

}

}

}
//default case: return null
instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("null") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) , objectArrayType,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructions) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;

/**
* generate code for:
*  public Object setChildren(Object o, Object[] children);
*/
funcName = "setChildren";//function name
instructions = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ;
for (TomType type:types) {
InstructionList instructionsForSort = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ;
//cast in concTomSymbol to use the for statement

{
{
if ( (type instanceof tom.engine.adt.tomtype.types.TomType) ) {
if ( ((( tom.engine.adt.tomtype.types.TomType )type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 String  tom_typeName= (( tom.engine.adt.tomtype.types.TomType )type).getTomType() ;

if(! symbolTable.isBuiltinType(
tom_typeName)) {
BQTerm var = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("v_"+tom_typeName) , type) ;
concTomSymbol list = (concTomSymbol) symbolTable.getSymbolFromType(type);
for (TomSymbol symbol:list) {

{
{
if ( (symbol instanceof tom.engine.adt.tomsignature.types.TomSymbol) ) {
if ( ((( tom.engine.adt.tomsignature.types.TomSymbol )symbol) instanceof tom.engine.adt.tomsignature.types.tomsymbol.Symbol) ) {
 tom.engine.adt.tomname.types.TomName  tom_symbolName= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getAstName() ;
 tom.engine.adt.tomtype.types.TomType  tom_TypesToType= (( tom.engine.adt.tomsignature.types.TomSymbol )symbol).getTypesToType() ;

if (TomBase.isListOperator(symbol)) {

{
{
if ( (tom_TypesToType instanceof tom.engine.adt.tomtype.types.TomType) ) {
if ( ((( tom.engine.adt.tomtype.types.TomType )tom_TypesToType) instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {
 tom.engine.adt.tomtype.types.TomTypeList  tomMatch245_1= (( tom.engine.adt.tomtype.types.TomType )tom_TypesToType).getDomain() ;
if ( ((tomMatch245_1 instanceof tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType) || (tomMatch245_1 instanceof tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType)) ) {
if (!( tomMatch245_1.isEmptyconcTomType() )) {
if (  tomMatch245_1.getTailconcTomType() .isEmptyconcTomType() ) {

Instruction inst = 

 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children.length==0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.BuildEmptyList.make(tom_symbolName) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.BuildConsList.make(tom_symbolName,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make( tomMatch245_1.getHeadconcTomType() ,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children[0]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children[1]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ) ) 
                                      ) 
                                    ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );


}
}
}
}
}

}

}

} else if (TomBase.isArrayOperator(symbol)) {
//TODO 

{
{
if ( (tom_TypesToType instanceof tom.engine.adt.tomtype.types.TomType) ) {
if ( ((( tom.engine.adt.tomtype.types.TomType )tom_TypesToType) instanceof tom.engine.adt.tomtype.types.tomtype.TypesToType) ) {
 tom.engine.adt.tomtype.types.TomTypeList  tomMatch246_1= (( tom.engine.adt.tomtype.types.TomType )tom_TypesToType).getDomain() ;
if ( ((tomMatch246_1 instanceof tom.engine.adt.tomtype.types.tomtypelist.ConsconcTomType) || (tomMatch246_1 instanceof tom.engine.adt.tomtype.types.tomtypelist.EmptyconcTomType)) ) {
if (!( tomMatch246_1.isEmptyconcTomType() )) {
if (  tomMatch246_1.getTailconcTomType() .isEmptyconcTomType() ) {

Instruction inst = 

 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children.length==0") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.BuildEmptyArray.make(tom_symbolName,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Integer.make(0) ) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.BuildConsArray.make(tom_symbolName,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make( tomMatch246_1.getHeadconcTomType() ,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children[0]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ,  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children[1]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) 
                                          ) ) 
                                      ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );


}
}
}
}
}

}

}


} else {
int arity = TomBase.getArity(symbol);
BQTermList slots = 
 tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ;
PairNameDeclList pairNameDeclList = symbol.getPairNameDeclList();
for(int i=0; i<arity; i++) {
PairNameDecl pairNameDecl = pairNameDeclList.getHeadconcPairNameDecl();
Declaration decl = pairNameDecl.getSlotDecl();
TomType slotType = TomBase.getSlotType(symbol,TomBase.getSlotName(symbol,i));
String slotTypeName = slotType.getTomType();
// manage builtin slots
if(symbolTable.isBuiltinType(slotTypeName)) {
slots = 
tom_append_list_concBQTerm(slots, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("("+symbolTable.builtinToWrapper(slotTypeName)+")children["+i+"]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) , tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) );
} else {
slots = 
tom_append_list_concBQTerm(slots, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(slotType,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("children["+i+"]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) , tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) );
}
pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
}
Instruction inst = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsFsym.make(tom_symbolName, var) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.BuildTerm.make(tom_symbolName, slots, "default") ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
instructionsForSort = 
tom_append_list_concInstruction(instructionsForSort, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(inst, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
}


}
}

}

}

} 

instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsSort.make(type, objectVar) ,  tom.engine.adt.tominstruction.types.instruction.Let.make(var,  tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make(objectVar) ) ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructionsForSort) ) ,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
}


}
}

}

}

}
//default case: return o
instructions = 
tom_append_list_concInstruction(instructions, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.Return.make(objectVar) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectVar, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectArrayVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) , objectType,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructions) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;

/**
* generate code for:
* public Object getChildAt(Object o, int i);
*/
funcName = "getChildAt";//function name
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectVar, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(intVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) , objectType,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("getChildren(o)[i]") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;

/**
* generate code for:
* public Object setChildAt( Object o, int i, Object child);
*/
funcName = "setChildAt";//function name
String code = 
"\n            Object[] newChildren = getChildren(o);\n            newChildren[i] = child;\n            return setChildren(o, newChildren);\n          ";
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(objectVar, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(intVar, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(childVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) ) , objectType,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ,  tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.TargetLanguageToCode.make( tom.engine.adt.code.types.targetlanguage.ITL.make(code) ) ) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;
introspectorClass = 
 tom.engine.adt.tomdeclaration.types.declaration.IntrospectorClass.make( tom.engine.adt.tomname.types.tomname.Name.make("LocalIntrospector") ,  tom.engine.adt.tomdeclaration.types.declaration.AbstractDecl.make(l) ) ;
}

/*
* generate code for a %strategy
*/
DeclarationList l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() ; // represents compiled Strategy
HashMap<TomType,String> dispatchInfo = new HashMap<TomType,String>(); // contains info needed for dispatch

{
{
if ( (tom_visitList instanceof tom.engine.adt.tomsignature.types.TomVisitList) ) {
if ( (((( tom.engine.adt.tomsignature.types.TomVisitList )tom_visitList) instanceof tom.engine.adt.tomsignature.types.tomvisitlist.ConsconcTomVisit) || ((( tom.engine.adt.tomsignature.types.TomVisitList )tom_visitList) instanceof tom.engine.adt.tomsignature.types.tomvisitlist.EmptyconcTomVisit)) ) {
 tom.engine.adt.tomsignature.types.TomVisitList  tomMatch247__end__4=(( tom.engine.adt.tomsignature.types.TomVisitList )tom_visitList);
do {
{
if (!( tomMatch247__end__4.isEmptyconcTomVisit() )) {
 tom.engine.adt.tomsignature.types.TomVisit  tomMatch247_10= tomMatch247__end__4.getHeadconcTomVisit() ;
if ( (tomMatch247_10 instanceof tom.engine.adt.tomsignature.types.tomvisit.VisitTerm) ) {
 tom.engine.adt.tomtype.types.TomType  tomMatch247_7= tomMatch247_10.getVNode() ;
if ( (tomMatch247_7 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {
 tom.engine.adt.tomtype.types.TomType  tom_vType=tomMatch247_7;

BQTerm arg = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("tom__arg") , tom_vType) ;//arg subjectList
String funcName = "visit_" + 
 tomMatch247_7.getTomType() ; // function name
BQTermList subjectListAST = 
 tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(arg, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(introspectorVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) ;
//return default strategy.visitLight(arg)
// FIXME: put superclass keyword in backend, in c# 'super' is 'base'
Instruction returnStatement = 
 tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.FunctionCall.make( tom.engine.adt.tomname.types.tomname.Name.make("_" + funcName) , tom_vType, subjectListAST) ) ;
Instruction matchStatement = 
 tom.engine.adt.tominstruction.types.instruction.Match.make( tomMatch247_10.getAstConstraintInstructionList() ,  tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ) ;
InstructionList instructions = 
 tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(matchStatement, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(returnStatement, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) ) ;
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make(funcName) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(arg, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(introspectorVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) , tom_vType, visitfailureType,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(instructions) ) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;
dispatchInfo.put(
tom_vType,funcName);


}
}
}
if ( tomMatch247__end__4.isEmptyconcTomVisit() ) {
tomMatch247__end__4=(( tom.engine.adt.tomsignature.types.TomVisitList )tom_visitList);
} else {
tomMatch247__end__4= tomMatch247__end__4.getTailconcTomVisit() ;
}

}
} while(!( (tomMatch247__end__4==(( tom.engine.adt.tomsignature.types.TomVisitList )tom_visitList)) ));
}
}

}

}


/*
* Generates the following dispatch mechanism:
*           
* public Visitable visitLight(Visitable v) throws VisitFailure {
*   if(is_sort(v, Term1))
*     return this.visit_Term1((Term1) v);
*   ...
*   if(is_sort(v, Termn))
*     return this.visit_Termn((Termn) v);               
*   return any.visitLight(v);
* }
*
* public Term1 _visit_Term1(Term1 arg) throws VisitFailure {
*   if(environment != null) {
*     return (Term1) any.visit(environment);
*   } else {
*     return (Term1) any.visitLight(arg);
*   }
* }
* ...
* public Termn _visit_Termn(Termn arg) throws VisitFailure {
*   if(environment != null) {
*     return (Termn) any.visit(environment);
*   } else {
*     return (Termn) any.visitLight(arg);
*   }
* }
*
*/        
BQTerm vVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("v") , genericType) ;// v argument of visitLight
InstructionList ifList = 
 tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ; // the list of ifs in visitLight
Expression testEnvNotNull = null;
// generate the visitLight
for(TomType type:dispatchInfo.keySet()) {
BQTermList funcArg = 
 tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make(vVar) ) ) , tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(introspectorVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) ;            
Instruction returnStatement = 
 tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(genericType,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.FunctionCall.make( tom.engine.adt.tomname.types.tomname.Name.make(dispatchInfo.get(type)) , type, funcArg) ) ) ) ) ;
Instruction ifInstr = 
 tom.engine.adt.tominstruction.types.instruction.If.make( tom.engine.adt.tomexpression.types.expression.IsSort.make(type, vVar) , returnStatement,  tom.engine.adt.tominstruction.types.instruction.Nop.make() ) ;
ifList = 
tom_append_list_concInstruction(ifList, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make(ifInstr, tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
// generate the _visit_Term
BQTerm arg = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("arg") , type) ;
BQTerm environmentVar = 
 tom.engine.adt.code.types.bqterm.BQVariable.make( tom.engine.adt.tomoption.types.optionlist.ConsconcOption.make(tom_orgTrack, tom.engine.adt.tomoption.types.optionlist.EmptyconcOption.make() ) ,  tom.engine.adt.tomname.types.tomname.Name.make("environment") ,  tom.engine.adt.tomtype.types.tomtype.EmptyType.make() ) ;
Instruction return1 = 
 tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(type,  tom.engine.adt.tomexpression.types.expression.TomInstructionToExpression.make( tom.engine.adt.tominstruction.types.instruction.CodeToInstruction.make( tom.engine.adt.code.types.code.TargetLanguageToCode.make( tom.engine.adt.code.types.targetlanguage.ITL.make("any.visit(environment,introspector)") ) ) ) ) ) ) ;
Instruction return2 = 
 tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("any.visitLight(arg,introspector)") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ;
testEnvNotNull = 
 tom.engine.adt.tomexpression.types.expression.Negation.make( tom.engine.adt.tomexpression.types.expression.EqualTerm.make(expander.getStreamManager().getSymbolTable().getBooleanType(),  tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Bottom.make( tom.engine.adt.tomtype.types.tomtype.Type.make( tom.engine.adt.tomtype.types.typeoptionlist.EmptyconcTypeOption.make() , "Object",  tom.engine.adt.tomtype.types.targetlanguagetype.EmptyTargetLanguageType.make() ) ) ) , TomBase.convertFromBQVarToVar(environmentVar)) ) ;
Instruction ifThenElse = 
 tom.engine.adt.tominstruction.types.instruction.If.make(testEnvNotNull, return1, return2) ;
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make("_" + dispatchInfo.get(type)) ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(arg, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(introspectorVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) , type, visitfailureType, ifThenElse) ,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;
}
ifList = 
tom_append_list_concInstruction(ifList, tom.engine.adt.tominstruction.types.instructionlist.ConsconcInstruction.make( tom.engine.adt.tominstruction.types.instruction.If.make(testEnvNotNull,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ExpressionToBQTerm.make( tom.engine.adt.tomexpression.types.expression.Cast.make(genericType,  tom.engine.adt.tomexpression.types.expression.BQTermToExpression.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("any.visit(environment,introspector)") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) ) ) ,  tom.engine.adt.tominstruction.types.instruction.Return.make( tom.engine.adt.code.types.bqterm.ConsComposite.make( tom.engine.adt.code.types.compositemember.CompositeTL.make( tom.engine.adt.code.types.targetlanguage.ITL.make("any.visitLight(v,introspector)") ) , tom.engine.adt.code.types.bqterm.EmptyComposite.make() ) ) ) , tom.engine.adt.tominstruction.types.instructionlist.EmptyconcInstruction.make() ) );
Declaration visitLightDeclaration = 
 tom.engine.adt.tomdeclaration.types.declaration.MethodDef.make( tom.engine.adt.tomname.types.tomname.Name.make("visitLight") ,  tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(vVar, tom.engine.adt.code.types.bqtermlist.ConsconcBQTerm.make(introspectorVar, tom.engine.adt.code.types.bqtermlist.EmptyconcBQTerm.make() ) ) , methodparameterType, visitfailureType,  tom.engine.adt.tominstruction.types.instruction.AbstractBlock.make(ifList) ) ;
l = 
 tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make(visitLightDeclaration,tom_append_list_concDeclaration(l, tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() )) ;
return (Declaration) expander.expand(
 tom.engine.adt.tomdeclaration.types.declaration.AbstractDecl.make( tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make(introspectorClass, tom.engine.adt.tomdeclaration.types.declarationlist.ConsconcDeclaration.make( tom.engine.adt.tomdeclaration.types.declaration.Class.make( (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getSName() , basicStratType,  (( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg).getExtendsTerm() ,  tom.engine.adt.tomdeclaration.types.declaration.AbstractDecl.make(l) ) , tom.engine.adt.tomdeclaration.types.declarationlist.EmptyconcDeclaration.make() ) ) ) );


}
}

}

}
return _visit_Declaration(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_Expand_once( ExpanderPlugin  t0) { 
return new Expand_once(t0);
}
// end strategy

}
