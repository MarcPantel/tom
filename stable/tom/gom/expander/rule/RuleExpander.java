/*
*
* GOM
*
* Copyright (c) 2007-2011, INPL, INRIA
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
* Antoine Reilles  e-mail: Antoine.Reilles@loria.fr
*
**/

package tom.gom.expander.rule;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.tree.Tree;
import tom.gom.adt.rule.RuleAdaptor;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import tom.gom.GomMessage;
import tom.gom.adt.gom.types.*;
import tom.gom.adt.rule.types.*;
import tom.gom.tools.error.GomRuntimeException;

public class RuleExpander {



  private static   tom.gom.adt.gom.types.HookDeclList  tom_append_list_ConcHookDecl( tom.gom.adt.gom.types.HookDeclList l1,  tom.gom.adt.gom.types.HookDeclList  l2) {
    if( l1.isEmptyConcHookDecl() ) {
      return l2;
    } else if( l2.isEmptyConcHookDecl() ) {
      return l1;
    } else if(  l1.getTailConcHookDecl() .isEmptyConcHookDecl() ) {
      return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( l1.getHeadConcHookDecl() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( l1.getHeadConcHookDecl() ,tom_append_list_ConcHookDecl( l1.getTailConcHookDecl() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.HookDeclList  tom_get_slice_ConcHookDecl( tom.gom.adt.gom.types.HookDeclList  begin,  tom.gom.adt.gom.types.HookDeclList  end, tom.gom.adt.gom.types.HookDeclList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcHookDecl()  ||  (end== tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( begin.getHeadConcHookDecl() ,( tom.gom.adt.gom.types.HookDeclList )tom_get_slice_ConcHookDecl( begin.getTailConcHookDecl() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.SlotList  tom_append_list_ConcSlot( tom.gom.adt.gom.types.SlotList l1,  tom.gom.adt.gom.types.SlotList  l2) {
    if( l1.isEmptyConcSlot() ) {
      return l2;
    } else if( l2.isEmptyConcSlot() ) {
      return l1;
    } else if(  l1.getTailConcSlot() .isEmptyConcSlot() ) {
      return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,tom_append_list_ConcSlot( l1.getTailConcSlot() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.SlotList  tom_get_slice_ConcSlot( tom.gom.adt.gom.types.SlotList  begin,  tom.gom.adt.gom.types.SlotList  end, tom.gom.adt.gom.types.SlotList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcSlot()  ||  (end== tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( begin.getHeadConcSlot() ,( tom.gom.adt.gom.types.SlotList )tom_get_slice_ConcSlot( begin.getTailConcSlot() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.rule.types.Condition  tom_append_list_CondAnd( tom.gom.adt.rule.types.Condition  l1,  tom.gom.adt.rule.types.Condition  l2) {
    if( l1.isEmptyCondAnd() ) {
      return l2;
    } else if( l2.isEmptyCondAnd() ) {
      return l1;
    } else if( ((l1 instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || (l1 instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) ) {
      if(  l1.getTailCondAnd() .isEmptyCondAnd() ) {
        return  tom.gom.adt.rule.types.condition.ConsCondAnd.make( l1.getHeadCondAnd() ,l2) ;
      } else {
        return  tom.gom.adt.rule.types.condition.ConsCondAnd.make( l1.getHeadCondAnd() ,tom_append_list_CondAnd( l1.getTailCondAnd() ,l2)) ;
      }
    } else {
      return  tom.gom.adt.rule.types.condition.ConsCondAnd.make(l1,l2) ;
    }
  }
  private static   tom.gom.adt.rule.types.Condition  tom_get_slice_CondAnd( tom.gom.adt.rule.types.Condition  begin,  tom.gom.adt.rule.types.Condition  end, tom.gom.adt.rule.types.Condition  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyCondAnd()  ||  (end== tom.gom.adt.rule.types.condition.EmptyCondAnd.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.rule.types.condition.ConsCondAnd.make((( ((begin instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || (begin instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) )? begin.getHeadCondAnd() :begin),( tom.gom.adt.rule.types.Condition )tom_get_slice_CondAnd((( ((begin instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || (begin instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) )? begin.getTailCondAnd() : tom.gom.adt.rule.types.condition.EmptyCondAnd.make() ),end,tail)) ;
  }
  
  private static   tom.gom.adt.rule.types.Condition  tom_append_list_CondOr( tom.gom.adt.rule.types.Condition  l1,  tom.gom.adt.rule.types.Condition  l2) {
    if( l1.isEmptyCondOr() ) {
      return l2;
    } else if( l2.isEmptyCondOr() ) {
      return l1;
    } else if( ((l1 instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || (l1 instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) ) {
      if(  l1.getTailCondOr() .isEmptyCondOr() ) {
        return  tom.gom.adt.rule.types.condition.ConsCondOr.make( l1.getHeadCondOr() ,l2) ;
      } else {
        return  tom.gom.adt.rule.types.condition.ConsCondOr.make( l1.getHeadCondOr() ,tom_append_list_CondOr( l1.getTailCondOr() ,l2)) ;
      }
    } else {
      return  tom.gom.adt.rule.types.condition.ConsCondOr.make(l1,l2) ;
    }
  }
  private static   tom.gom.adt.rule.types.Condition  tom_get_slice_CondOr( tom.gom.adt.rule.types.Condition  begin,  tom.gom.adt.rule.types.Condition  end, tom.gom.adt.rule.types.Condition  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyCondOr()  ||  (end== tom.gom.adt.rule.types.condition.EmptyCondOr.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.rule.types.condition.ConsCondOr.make((( ((begin instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || (begin instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) )? begin.getHeadCondOr() :begin),( tom.gom.adt.rule.types.Condition )tom_get_slice_CondOr((( ((begin instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || (begin instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) )? begin.getTailCondOr() : tom.gom.adt.rule.types.condition.EmptyCondOr.make() ),end,tail)) ;
  }
  
  private static   tom.gom.adt.rule.types.RuleList  tom_append_list_RuleList( tom.gom.adt.rule.types.RuleList l1,  tom.gom.adt.rule.types.RuleList  l2) {
    if( l1.isEmptyRuleList() ) {
      return l2;
    } else if( l2.isEmptyRuleList() ) {
      return l1;
    } else if(  l1.getTailRuleList() .isEmptyRuleList() ) {
      return  tom.gom.adt.rule.types.rulelist.ConsRuleList.make( l1.getHeadRuleList() ,l2) ;
    } else {
      return  tom.gom.adt.rule.types.rulelist.ConsRuleList.make( l1.getHeadRuleList() ,tom_append_list_RuleList( l1.getTailRuleList() ,l2)) ;
    }
  }
  private static   tom.gom.adt.rule.types.RuleList  tom_get_slice_RuleList( tom.gom.adt.rule.types.RuleList  begin,  tom.gom.adt.rule.types.RuleList  end, tom.gom.adt.rule.types.RuleList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyRuleList()  ||  (end== tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.rule.types.rulelist.ConsRuleList.make( begin.getHeadRuleList() ,( tom.gom.adt.rule.types.RuleList )tom_get_slice_RuleList( begin.getTailRuleList() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.rule.types.TermList  tom_append_list_TermList( tom.gom.adt.rule.types.TermList l1,  tom.gom.adt.rule.types.TermList  l2) {
    if( l1.isEmptyTermList() ) {
      return l2;
    } else if( l2.isEmptyTermList() ) {
      return l1;
    } else if(  l1.getTailTermList() .isEmptyTermList() ) {
      return  tom.gom.adt.rule.types.termlist.ConsTermList.make( l1.getHeadTermList() ,l2) ;
    } else {
      return  tom.gom.adt.rule.types.termlist.ConsTermList.make( l1.getHeadTermList() ,tom_append_list_TermList( l1.getTailTermList() ,l2)) ;
    }
  }
  private static   tom.gom.adt.rule.types.TermList  tom_get_slice_TermList( tom.gom.adt.rule.types.TermList  begin,  tom.gom.adt.rule.types.TermList  end, tom.gom.adt.rule.types.TermList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyTermList()  ||  (end== tom.gom.adt.rule.types.termlist.EmptyTermList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.rule.types.termlist.ConsTermList.make( begin.getHeadTermList() ,( tom.gom.adt.rule.types.TermList )tom_get_slice_TermList( begin.getTailTermList() ,end,tail)) ;
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


private ModuleList moduleList;

public RuleExpander(ModuleList data) {
this.moduleList = data;
}

public HookDeclList expandRules(String ruleCode) {
RuleLexer lexer = new RuleLexer(new ANTLRStringStream(ruleCode));
CommonTokenStream tokens = new CommonTokenStream(lexer);
RuleParser parser = new RuleParser(tokens);
RuleList rulelist = 
 tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ;
try {
Tree ast = (Tree) parser.ruleset().getTree();
rulelist = (RuleList) RuleAdaptor.getTerm(ast);
} catch (org.antlr.runtime.RecognitionException e) {
GomMessage.error(getLogger(), null, 0, GomMessage.rulesParsingFailure);
return 
 tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ;
}
return expand(rulelist);
}

protected HookDeclList expand(RuleList rulelist) {
HookDeclList hookList = 
 tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ;
/* collect all rules for a given symbol */
Map<OperatorDecl,RuleList> rulesForOperator =
new HashMap<OperatorDecl,RuleList>();

{
{
if ( (((Object)rulelist) instanceof tom.gom.adt.rule.types.RuleList) ) {
if ( (((( tom.gom.adt.rule.types.RuleList )(( tom.gom.adt.rule.types.RuleList )((Object)rulelist))) instanceof tom.gom.adt.rule.types.rulelist.ConsRuleList) || ((( tom.gom.adt.rule.types.RuleList )(( tom.gom.adt.rule.types.RuleList )((Object)rulelist))) instanceof tom.gom.adt.rule.types.rulelist.EmptyRuleList)) ) {
 tom.gom.adt.rule.types.RuleList  tomMatch638__end__4=(( tom.gom.adt.rule.types.RuleList )((Object)rulelist));
do {
{
if (!( tomMatch638__end__4.isEmptyRuleList() )) {
 tom.gom.adt.rule.types.Rule  tomMatch638_8= tomMatch638__end__4.getHeadRuleList() ;
boolean tomMatch638_14= false ;
 tom.gom.adt.rule.types.Rule  tomMatch638_10= null ;
 tom.gom.adt.rule.types.Rule  tomMatch638_9= null ;
 tom.gom.adt.rule.types.Term  tomMatch638_7= null ;
if ( (tomMatch638_8 instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )tomMatch638_8) instanceof tom.gom.adt.rule.types.rule.Rule) ) {
{
tomMatch638_14= true ;
tomMatch638_9=tomMatch638_8;
tomMatch638_7= tomMatch638_9.getlhs() ;

}
} else {
if ( (tomMatch638_8 instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )tomMatch638_8) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {
{
tomMatch638_14= true ;
tomMatch638_10=tomMatch638_8;
tomMatch638_7= tomMatch638_10.getlhs() ;

}
}
}
}
}
if (tomMatch638_14) {
if ( (tomMatch638_7 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch638_7) instanceof tom.gom.adt.rule.types.term.Appl) ) {
 tom.gom.adt.rule.types.Rule  tom_rl= tomMatch638__end__4.getHeadRuleList() ;

OperatorDecl decl = getOperatorDecl(
 tomMatch638_7.getsymbol() );
if (null != decl) {
RuleList rules = rulesForOperator.get(decl);
if (null == rules) {
rulesForOperator.put(decl,
 tom.gom.adt.rule.types.rulelist.ConsRuleList.make(tom_rl, tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ) );
} else {
rulesForOperator.put(decl,
tom_append_list_RuleList(rules, tom.gom.adt.rule.types.rulelist.ConsRuleList.make(tom_rl, tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ) ));
}
} else {
GomMessage.warning(getLogger(), null, 0, 
GomMessage.discardRuleWarning, /*XXX:prettyprint*/
(tom_rl));
}


}
}
}

}
if ( tomMatch638__end__4.isEmptyRuleList() ) {
tomMatch638__end__4=(( tom.gom.adt.rule.types.RuleList )((Object)rulelist));
} else {
tomMatch638__end__4= tomMatch638__end__4.getTailRuleList() ;
}

}
} while(!( (tomMatch638__end__4==(( tom.gom.adt.rule.types.RuleList )((Object)rulelist))) ));
}
}

}

}

/* Generate a construction hook for each constructor */
for (OperatorDecl opDecl : rulesForOperator.keySet()) {
TypedProduction prod = opDecl.getProd();

{
{
if ( (((Object)prod) instanceof tom.gom.adt.gom.types.TypedProduction) ) {
if ( ((( tom.gom.adt.gom.types.TypedProduction )((Object)prod)) instanceof tom.gom.adt.gom.types.TypedProduction) ) {
if ( ((( tom.gom.adt.gom.types.TypedProduction )(( tom.gom.adt.gom.types.TypedProduction )((Object)prod))) instanceof tom.gom.adt.gom.types.typedproduction.Slots) ) {

SlotList args = opArgs(
 (( tom.gom.adt.gom.types.TypedProduction )((Object)prod)).getSlots() ,1);
String hookCode =
generateHookCode(args, rulesForOperator.get(opDecl));
hookList =

tom_append_list_ConcHookDecl(hookList, tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.MakeHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) , args,  tom.gom.adt.code.types.code.Code.make(hookCode) ,  tom.gom.adt.gom.types.hookkind.HookKind.make("rules") ,  true ) , tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) );


}
}
}

}
{
if ( (((Object)prod) instanceof tom.gom.adt.gom.types.TypedProduction) ) {
if ( ((( tom.gom.adt.gom.types.TypedProduction )((Object)prod)) instanceof tom.gom.adt.gom.types.TypedProduction) ) {
if ( ((( tom.gom.adt.gom.types.TypedProduction )(( tom.gom.adt.gom.types.TypedProduction )((Object)prod))) instanceof tom.gom.adt.gom.types.typedproduction.Variadic) ) {

RuleList rules = rulesForOperator.get(opDecl);
/* Handle rules for empty: there should be at least one */
int count = 0;
RuleList nonEmptyRules = rules;

{
{
if ( (((Object)rules) instanceof tom.gom.adt.rule.types.RuleList) ) {
if ( (((( tom.gom.adt.rule.types.RuleList )(( tom.gom.adt.rule.types.RuleList )((Object)rules))) instanceof tom.gom.adt.rule.types.rulelist.ConsRuleList) || ((( tom.gom.adt.rule.types.RuleList )(( tom.gom.adt.rule.types.RuleList )((Object)rules))) instanceof tom.gom.adt.rule.types.rulelist.EmptyRuleList)) ) {
 tom.gom.adt.rule.types.RuleList  tomMatch640__end__4=(( tom.gom.adt.rule.types.RuleList )((Object)rules));
do {
{
if (!( tomMatch640__end__4.isEmptyRuleList() )) {
 tom.gom.adt.rule.types.Rule  tomMatch640_8= tomMatch640__end__4.getHeadRuleList() ;
boolean tomMatch640_15= false ;
 tom.gom.adt.rule.types.Rule  tomMatch640_9= null ;
 tom.gom.adt.rule.types.Rule  tomMatch640_10= null ;
 tom.gom.adt.rule.types.Term  tomMatch640_7= null ;
if ( (tomMatch640_8 instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )tomMatch640_8) instanceof tom.gom.adt.rule.types.rule.Rule) ) {
{
tomMatch640_15= true ;
tomMatch640_9=tomMatch640_8;
tomMatch640_7= tomMatch640_9.getlhs() ;

}
} else {
if ( (tomMatch640_8 instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )tomMatch640_8) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {
{
tomMatch640_15= true ;
tomMatch640_10=tomMatch640_8;
tomMatch640_7= tomMatch640_10.getlhs() ;

}
}
}
}
}
if (tomMatch640_15) {
if ( (tomMatch640_7 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch640_7) instanceof tom.gom.adt.rule.types.term.Appl) ) {
 tom.gom.adt.rule.types.TermList  tomMatch640_11= tomMatch640_7.getargs() ;
if ( (((( tom.gom.adt.rule.types.TermList )tomMatch640_11) instanceof tom.gom.adt.rule.types.termlist.ConsTermList) || ((( tom.gom.adt.rule.types.TermList )tomMatch640_11) instanceof tom.gom.adt.rule.types.termlist.EmptyTermList)) ) {
if ( tomMatch640_11.isEmptyTermList() ) {

count++;
nonEmptyRules = 
tom_append_list_RuleList(tom_get_slice_RuleList((( tom.gom.adt.rule.types.RuleList )((Object)rules)),tomMatch640__end__4, tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ),tom_append_list_RuleList( tomMatch640__end__4.getTailRuleList() , tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ));
String hookCode =
generateHookCode(
 tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ,
 tom.gom.adt.rule.types.rulelist.ConsRuleList.make( tomMatch640__end__4.getHeadRuleList() , tom.gom.adt.rule.types.rulelist.EmptyRuleList.make() ) );
hookList =

tom_append_list_ConcHookDecl(hookList, tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.MakeHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) ,  tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ,  tom.gom.adt.code.types.code.Code.make(hookCode) ,  tom.gom.adt.gom.types.hookkind.HookKind.make("rules") ,  true ) , tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) );


}
}
}
}
}

}
if ( tomMatch640__end__4.isEmptyRuleList() ) {
tomMatch640__end__4=(( tom.gom.adt.rule.types.RuleList )((Object)rules));
} else {
tomMatch640__end__4= tomMatch640__end__4.getTailRuleList() ;
}

}
} while(!( (tomMatch640__end__4==(( tom.gom.adt.rule.types.RuleList )((Object)rules))) ));
}
}

}

}

if (count>1) {
GomMessage.warning(getLogger(), null, 0, 
GomMessage.multipleRulesForEmpty, opDecl.getName());
}
/* Then handle rules for insert */
if (!nonEmptyRules.isEmptyRuleList()) {
SlotList args = 
 tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("head",  (( tom.gom.adt.gom.types.TypedProduction )((Object)prod)).getSort() ) , tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("tail", opDecl.getSort()) , tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) ) ;
String hookCode =
generateVariadicHookCode(args, nonEmptyRules);
hookList =

tom_append_list_ConcHookDecl(hookList, tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.MakeHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) , args,  tom.gom.adt.code.types.code.Code.make(hookCode) ,  tom.gom.adt.gom.types.hookkind.HookKind.make("rules") ,  true ) , tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) );
}


}
}
}

}


}

}
return hookList;
}

private String generateHookCode(SlotList slotList, RuleList ruleList) {
StringBuilder output = new StringBuilder();
if(slotList.isEmptyConcSlot()) {
while(!ruleList.isEmptyRuleList()) {
Rule rule = ruleList.getHeadRuleList();
ruleList = ruleList.getTailRuleList();

{
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.Rule) ) {
 tom.gom.adt.rule.types.Term  tomMatch641_1= (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ;
if ( (tomMatch641_1 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch641_1) instanceof tom.gom.adt.rule.types.term.Appl) ) {

output.append("    return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append(";\n");


}
}
}
}
}

}
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {
 tom.gom.adt.rule.types.Term  tomMatch641_8= (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ;
if ( (tomMatch641_8 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch641_8) instanceof tom.gom.adt.rule.types.term.Appl) ) {

output.append("    %match{\n");
genCondition(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getcond() ,output);
output.append(" -> { return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append("; }\n");
output.append("}\n");


}
}
}
}
}

}


}

}

} else {
output.append("    %match(");
matchArgs(slotList,output,1);
output.append(") {\n");
while(!ruleList.isEmptyRuleList()) {
Rule rule = ruleList.getHeadRuleList();
ruleList = ruleList.getTailRuleList();

{
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.Rule) ) {
 tom.gom.adt.rule.types.Term  tomMatch642_1= (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ;
if ( (tomMatch642_1 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch642_1) instanceof tom.gom.adt.rule.types.term.Appl) ) {

genTermList(
 tomMatch642_1.getargs() ,output);
output.append(" -> { return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append("; }\n");


}
}
}
}
}

}
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {
 tom.gom.adt.rule.types.Term  tomMatch642_9= (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ;
if ( (tomMatch642_9 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch642_9) instanceof tom.gom.adt.rule.types.term.Appl) ) {

genTermList(
 tomMatch642_9.getargs() ,output);
output.append(" && ");
genCondition(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getcond() ,output);
output.append(" -> { return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append("; }\n");


}
}
}
}
}

}


}

}
output.append("    }\n");
}
return output.toString();
}

private String generateVariadicHookCode(SlotList slotList, RuleList ruleList) {
StringBuilder output = new StringBuilder();
output.append("    %match(realMake(head,tail)) {\n");
while(!ruleList.isEmptyRuleList()) {
Rule rule = ruleList.getHeadRuleList();
ruleList = ruleList.getTailRuleList();

{
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
boolean tomMatch643_16= false ;
 tom.gom.adt.rule.types.Rule  tomMatch643_3= null ;
 tom.gom.adt.rule.types.Term  tomMatch643_1= null ;
 tom.gom.adt.rule.types.Rule  tomMatch643_4= null ;
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.Rule) ) {
{
tomMatch643_16= true ;
tomMatch643_3=(( tom.gom.adt.rule.types.Rule )((Object)rule));
tomMatch643_1= tomMatch643_3.getlhs() ;

}
} else {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {
{
tomMatch643_16= true ;
tomMatch643_4=(( tom.gom.adt.rule.types.Rule )((Object)rule));
tomMatch643_1= tomMatch643_4.getlhs() ;

}
}
}
}
}
if (tomMatch643_16) {
if ( (tomMatch643_1 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch643_1) instanceof tom.gom.adt.rule.types.term.Appl) ) {
 tom.gom.adt.rule.types.TermList  tomMatch643_6= tomMatch643_1.getargs() ;
if ( (((( tom.gom.adt.rule.types.TermList )tomMatch643_6) instanceof tom.gom.adt.rule.types.termlist.ConsTermList) || ((( tom.gom.adt.rule.types.TermList )tomMatch643_6) instanceof tom.gom.adt.rule.types.termlist.EmptyTermList)) ) {
if (!( tomMatch643_6.isEmptyTermList() )) {
 tom.gom.adt.rule.types.Term  tomMatch643_12= tomMatch643_6.getHeadTermList() ;
boolean tomMatch643_15= false ;
 tom.gom.adt.rule.types.Term  tomMatch643_14= null ;
 tom.gom.adt.rule.types.Term  tomMatch643_13= null ;
if ( (tomMatch643_12 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch643_12) instanceof tom.gom.adt.rule.types.term.UnnamedVarStar) ) {
{
tomMatch643_15= true ;
tomMatch643_13=tomMatch643_12;

}
} else {
if ( (tomMatch643_12 instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )tomMatch643_12) instanceof tom.gom.adt.rule.types.term.VarStar) ) {
{
tomMatch643_15= true ;
tomMatch643_14=tomMatch643_12;

}
}
}
}
}
if (tomMatch643_15) {
 tom.gom.adt.rule.types.Term  tom_var= tomMatch643_6.getHeadTermList() ;

String varname = "_";

{
{
if ( (((Object)tom_var) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)tom_var)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)tom_var))) instanceof tom.gom.adt.rule.types.term.VarStar) ) {
varname = 
 (( tom.gom.adt.rule.types.Term )((Object)tom_var)).getname() ; 

}
}
}

}

}

GomMessage.warning(getLogger(),null,0,
GomMessage.variadicRuleStartingWithStar,
new Object[]{
( tomMatch643_1.getsymbol() ),varname});


}

}
}
}
}
}

}

}

}
{
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.Rule) ) {

genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ,output);
output.append(" -> { return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append("; }\n");


}
}
}

}
{
if ( (((Object)rule) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )((Object)rule)) instanceof tom.gom.adt.rule.types.Rule) ) {
if ( ((( tom.gom.adt.rule.types.Rule )(( tom.gom.adt.rule.types.Rule )((Object)rule))) instanceof tom.gom.adt.rule.types.rule.ConditionalRule) ) {

genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getlhs() ,output);
output.append(" && ");
genCondition(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getcond() ,output);
output.append(" -> { return `");
genTerm(
 (( tom.gom.adt.rule.types.Rule )((Object)rule)).getrhs() ,output);
output.append("; }\n");


}
}
}

}


}

}
output.append("    }\n");
return output.toString();
}
private void genTermList(TermList list, StringBuilder output) {

{
{
if ( (((Object)list) instanceof tom.gom.adt.rule.types.TermList) ) {
if ( (((( tom.gom.adt.rule.types.TermList )(( tom.gom.adt.rule.types.TermList )((Object)list))) instanceof tom.gom.adt.rule.types.termlist.ConsTermList) || ((( tom.gom.adt.rule.types.TermList )(( tom.gom.adt.rule.types.TermList )((Object)list))) instanceof tom.gom.adt.rule.types.termlist.EmptyTermList)) ) {
if ( (( tom.gom.adt.rule.types.TermList )((Object)list)).isEmptyTermList() ) {
return; 
}
}
}

}
{
if ( (((Object)list) instanceof tom.gom.adt.rule.types.TermList) ) {
if ( (((( tom.gom.adt.rule.types.TermList )(( tom.gom.adt.rule.types.TermList )((Object)list))) instanceof tom.gom.adt.rule.types.termlist.ConsTermList) || ((( tom.gom.adt.rule.types.TermList )(( tom.gom.adt.rule.types.TermList )((Object)list))) instanceof tom.gom.adt.rule.types.termlist.EmptyTermList)) ) {
if (!( (( tom.gom.adt.rule.types.TermList )((Object)list)).isEmptyTermList() )) {
 tom.gom.adt.rule.types.TermList  tom_t= (( tom.gom.adt.rule.types.TermList )((Object)list)).getTailTermList() ;

genTerm(
 (( tom.gom.adt.rule.types.TermList )((Object)list)).getHeadTermList() ,output);
if (!
tom_t.isEmptyTermList()) {
output.append(", ");
}
genTermList(
tom_t,output);


}
}
}

}


}

}

private void genTerm(Term termArg, StringBuilder output) {

{
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.Appl) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getsymbol() );
output.append("(");
genTermList(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getargs() , output);
output.append(")");


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.At) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getname() );
output.append("@");
genTerm(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getterm() ,output);


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.Anti) ) {

output.append("!");
genTerm(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getterm() ,output);


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.UnnamedVar) ) {

output.append("_");

}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.UnnamedVarStar) ) {

output.append("_*");

}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.Var) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getname() );


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.VarStar) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).getname() );
output.append("*");


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.BuiltinInt) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).geti() );


}
}
}

}
{
if ( (((Object)termArg) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )((Object)termArg)) instanceof tom.gom.adt.rule.types.Term) ) {
if ( ((( tom.gom.adt.rule.types.Term )(( tom.gom.adt.rule.types.Term )((Object)termArg))) instanceof tom.gom.adt.rule.types.term.BuiltinString) ) {

output.append(
 (( tom.gom.adt.rule.types.Term )((Object)termArg)).gets() );


}
}
}

}


}

}

private void genCondition(Condition cond, StringBuilder output) {

{
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondEquals) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" == ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondNotEquals) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" != ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondLessEquals) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" <= ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondLessThan) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" < ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondGreaterEquals) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" >= ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondGreaterThan) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" > ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.CondMatch) ) {

genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett1() ,output);
output.append(" << ");
genTerm(
 (( tom.gom.adt.rule.types.Condition )((Object)cond)).gett2() ,output);


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( (((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) ) {
if (!( (  (( tom.gom.adt.rule.types.Condition )((Object)cond)).isEmptyCondAnd()  ||  ((( tom.gom.adt.rule.types.Condition )((Object)cond))== tom.gom.adt.rule.types.condition.EmptyCondAnd.make() )  ) )) {
 tom.gom.adt.rule.types.Condition  tom_head=(( (((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) )?( (( tom.gom.adt.rule.types.Condition )((Object)cond)).getHeadCondAnd() ):((( tom.gom.adt.rule.types.Condition )((Object)cond))));
 tom.gom.adt.rule.types.Condition  tom_tail=(( (((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.ConsCondAnd) || ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.EmptyCondAnd)) )?( (( tom.gom.adt.rule.types.Condition )((Object)cond)).getTailCondAnd() ):( tom.gom.adt.rule.types.condition.EmptyCondAnd.make() ));

if(
tom_tail!= 
 tom.gom.adt.rule.types.condition.EmptyCondAnd.make() ) {
output.append("(");
genCondition(
tom_head,output);
output.append(" && ");
genCondition(
tom_tail,output);
output.append(")");
} else {
genCondition(
tom_head,output);
}


}
}
}

}
{
if ( (((Object)cond) instanceof tom.gom.adt.rule.types.Condition) ) {
if ( (((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || ((( tom.gom.adt.rule.types.Condition )(( tom.gom.adt.rule.types.Condition )((Object)cond))) instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) ) {
if (!( (  (( tom.gom.adt.rule.types.Condition )((Object)cond)).isEmptyCondOr()  ||  ((( tom.gom.adt.rule.types.Condition )((Object)cond))== tom.gom.adt.rule.types.condition.EmptyCondOr.make() )  ) )) {
 tom.gom.adt.rule.types.Condition  tom_head=(( (((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) )?( (( tom.gom.adt.rule.types.Condition )((Object)cond)).getHeadCondOr() ):((( tom.gom.adt.rule.types.Condition )((Object)cond))));
 tom.gom.adt.rule.types.Condition  tom_tail=(( (((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.ConsCondOr) || ((( tom.gom.adt.rule.types.Condition )((Object)cond)) instanceof tom.gom.adt.rule.types.condition.EmptyCondOr)) )?( (( tom.gom.adt.rule.types.Condition )((Object)cond)).getTailCondOr() ):( tom.gom.adt.rule.types.condition.EmptyCondOr.make() ));

if(
tom_tail!= 
 tom.gom.adt.rule.types.condition.EmptyCondOr.make() ) {
output.append("(");
genCondition(
tom_head,output);
output.append(" || ");
genCondition(
tom_tail,output);
output.append(")");
} else {
genCondition(
tom_head,output);
}


}
}
}

}


}

}

private void matchArgs(SlotList sl, StringBuilder output, int count) {

{
{
if ( (((Object)sl) instanceof tom.gom.adt.gom.types.SlotList) ) {
if ( (((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)sl))) instanceof tom.gom.adt.gom.types.slotlist.ConsConcSlot) || ((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)sl))) instanceof tom.gom.adt.gom.types.slotlist.EmptyConcSlot)) ) {
if ( (( tom.gom.adt.gom.types.SlotList )((Object)sl)).isEmptyConcSlot() ) {
return; 
}
}
}

}
{
if ( (((Object)sl) instanceof tom.gom.adt.gom.types.SlotList) ) {
if ( (((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)sl))) instanceof tom.gom.adt.gom.types.slotlist.ConsConcSlot) || ((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)sl))) instanceof tom.gom.adt.gom.types.slotlist.EmptyConcSlot)) ) {
if (!( (( tom.gom.adt.gom.types.SlotList )((Object)sl)).isEmptyConcSlot() )) {
 tom.gom.adt.gom.types.Slot  tomMatch649_7= (( tom.gom.adt.gom.types.SlotList )((Object)sl)).getHeadConcSlot() ;
if ( (tomMatch649_7 instanceof tom.gom.adt.gom.types.Slot) ) {
if ( ((( tom.gom.adt.gom.types.Slot )tomMatch649_7) instanceof tom.gom.adt.gom.types.slot.Slot) ) {
 tom.gom.adt.gom.types.SortDecl  tom_sort= tomMatch649_7.getSort() ;
 tom.gom.adt.gom.types.SlotList  tom_t= (( tom.gom.adt.gom.types.SlotList )((Object)sl)).getTailConcSlot() ;
{
{
if ( (((Object)tom_sort) instanceof tom.gom.adt.gom.types.SortDecl) ) {
boolean tomMatch650_5= false ;
 String  tomMatch650_1= "" ;
 tom.gom.adt.gom.types.SortDecl  tomMatch650_4= null ;
 tom.gom.adt.gom.types.SortDecl  tomMatch650_3= null ;
if ( ((( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort)) instanceof tom.gom.adt.gom.types.SortDecl) ) {
if ( ((( tom.gom.adt.gom.types.SortDecl )(( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort))) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {
{
tomMatch650_5= true ;
tomMatch650_3=(( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort));
tomMatch650_1= tomMatch650_3.getName() ;

}
} else {
if ( ((( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort)) instanceof tom.gom.adt.gom.types.SortDecl) ) {
if ( ((( tom.gom.adt.gom.types.SortDecl )(( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort))) instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl) ) {
{
tomMatch650_5= true ;
tomMatch650_4=(( tom.gom.adt.gom.types.SortDecl )((Object)tom_sort));
tomMatch650_1= tomMatch650_4.getName() ;

}
}
}
}
}
if (tomMatch650_5) {

output.append(
tomMatch650_1);
output.append(" arg_"+count);


}

}

}

}

if (!
tom_t.isEmptyConcSlot()) {
output.append(", ");
}
matchArgs(
tom_t,output,count+1);


}
}
}
}
}

}


}

}

private SlotList opArgs(SlotList slots, int count) {

{
{
if ( (((Object)slots) instanceof tom.gom.adt.gom.types.SlotList) ) {
if ( (((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)slots))) instanceof tom.gom.adt.gom.types.slotlist.ConsConcSlot) || ((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)slots))) instanceof tom.gom.adt.gom.types.slotlist.EmptyConcSlot)) ) {
if ( (( tom.gom.adt.gom.types.SlotList )((Object)slots)).isEmptyConcSlot() ) {

return 
 tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ;


}
}
}

}
{
if ( (((Object)slots) instanceof tom.gom.adt.gom.types.SlotList) ) {
if ( (((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)slots))) instanceof tom.gom.adt.gom.types.slotlist.ConsConcSlot) || ((( tom.gom.adt.gom.types.SlotList )(( tom.gom.adt.gom.types.SlotList )((Object)slots))) instanceof tom.gom.adt.gom.types.slotlist.EmptyConcSlot)) ) {
if (!( (( tom.gom.adt.gom.types.SlotList )((Object)slots)).isEmptyConcSlot() )) {
 tom.gom.adt.gom.types.Slot  tomMatch651_7= (( tom.gom.adt.gom.types.SlotList )((Object)slots)).getHeadConcSlot() ;
if ( (tomMatch651_7 instanceof tom.gom.adt.gom.types.Slot) ) {
if ( ((( tom.gom.adt.gom.types.Slot )tomMatch651_7) instanceof tom.gom.adt.gom.types.slot.Slot) ) {

SlotList tail = opArgs(
 (( tom.gom.adt.gom.types.SlotList )((Object)slots)).getTailConcSlot() ,count+1);
return 
 tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("arg_"+count,  tomMatch651_7.getSort() ) ,tom_append_list_ConcSlot(tail, tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() )) ;


}
}
}
}
}

}


}

throw new GomRuntimeException("RuleExpander:opArgs failed "+slots);
}
protected OperatorDecl getOperatorDecl(String name) {
OperatorDecl decl = null;
OpRef ref = new OpRef();
try {

tom_make_TopDown(tom_make_GetOperatorDecl(ref,name)).visit(moduleList);
} catch (tom.library.sl.VisitFailure e) {
throw new GomRuntimeException("Unexpected strategy failure!");
}
if (ref.val == null) {
GomMessage.error(getLogger(), null, 0, GomMessage.unknownConstructor, name);
}
return ref.val;
}
static class OpRef { OperatorDecl val; }

public static class GetOperatorDecl extends tom.library.sl.AbstractStrategyBasic {
private  OpRef  opref;
private  String  opName;
public GetOperatorDecl( OpRef  opref,  String  opName) {
super(( new tom.library.sl.Identity() ));
this.opref=opref;
this.opName=opName;
}
public  OpRef  getopref() {
return opref;
}
public  String  getopName() {
return opName;
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
if ( (v instanceof tom.gom.adt.gom.types.OperatorDecl) ) {
return ((T)visit_OperatorDecl((( tom.gom.adt.gom.types.OperatorDecl )v),introspector));
}
if (!(  null ==environment )) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.gom.adt.gom.types.OperatorDecl  _visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(  null ==environment )) {
return (( tom.gom.adt.gom.types.OperatorDecl )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.gom.adt.gom.types.OperatorDecl  visit_OperatorDecl( tom.gom.adt.gom.types.OperatorDecl  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (((Object)tom__arg) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {
if ( ((( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg)) instanceof tom.gom.adt.gom.types.OperatorDecl) ) {
if ( ((( tom.gom.adt.gom.types.OperatorDecl )(( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg))) instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {

if (
 (( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg)).getName() .equals(opName)) {
opref.val = 
(( tom.gom.adt.gom.types.OperatorDecl )((Object)tom__arg));
}


}
}
}

}

}
return _visit_OperatorDecl(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_GetOperatorDecl( OpRef  t0,  String  t1) { 
return new GetOperatorDecl(t0,t1);
}


private Logger getLogger() {
return Logger.getLogger(getClass().getName());
}
}
