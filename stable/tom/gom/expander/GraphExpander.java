/*
 *
 * GOM
 *
 * Copyright (c) 2006-2011, INPL, INRIA
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
 * Emilie Balland  e-mail: Emilie.Balland@loria.fr
 *
 **/

package tom.gom.expander;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.library.sl.*;
import tom.gom.backend.CodeGen;
import tom.gom.SymbolTable;
import tom.gom.exception.*;
import tom.gom.GomStreamManager;
import tom.gom.tools.GomEnvironment;
import tom.gom.adt.gom.*;
import tom.gom.adt.gom.types.*;
import tom.gom.adt.objects.types.ClassName;
import tom.gom.tools.error.GomRuntimeException;

public class GraphExpander {

     static class MapEntry {   private Object key;   private Object val;   public Object getKey() { return key; }   public Object getVal() { return val; }   public MapEntry(Object key, Object val) {     this.key = key;     this.val = val;   } }       @SuppressWarnings("unchecked") private static java.util.HashMap hashMapAppend(MapEntry e, java.util.HashMap m) {   java.util.HashMap res = (java.util.HashMap) m.clone();   res.put(e.getKey(), e.getVal());   return res; }  @SuppressWarnings("unchecked") private static MapEntry hashMapGetHead(java.util.HashMap m) {   java.util.Set es = m.entrySet();   java.util.Iterator it = es.iterator();   java.util.Map.Entry e = (java.util.Map.Entry) it.next();   return new MapEntry(e.getKey(), e.getValue()); }  @SuppressWarnings("unchecked") private static java.util.HashMap hashMapGetTail(java.util.HashMap m) {   java.util.HashMap res = (java.util.HashMap) m.clone();   java.util.Set es = m.entrySet();   java.util.Iterator it = es.iterator();   java.util.Map.Entry e = (java.util.Map.Entry) it.next();   res.remove(e.getKey());   return res; }      @SuppressWarnings("unchecked") private static java.util.ArrayList concArrayListAppend(Object o, java.util.ArrayList l) {   java.util.ArrayList res = (java.util.ArrayList)l.clone();   res.add(o);   return res; }         private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Sequence )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) ;       } else {         return  tom.library.sl.Sequence.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Sequence.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Sequence.make(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( begin instanceof tom.library.sl.Sequence ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.Choice )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) ;       } else {         return  tom.library.sl.Choice.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.Choice.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.Choice.make(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( begin instanceof tom.library.sl.Choice ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_SequenceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.SequenceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ) == null )) {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.SequenceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.FIRST) ),tom_append_list_SequenceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.SequenceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.SequenceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_SequenceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.SequenceId.make(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_SequenceId(((( begin instanceof tom.library.sl.SequenceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.SequenceId.THEN) ): null ),end,tail)) ;   }      private static   tom.library.sl.Strategy  tom_append_list_ChoiceId( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( l1 instanceof tom.library.sl.ChoiceId )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ) ==null )) {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),l2) ;       } else {         return  tom.library.sl.ChoiceId.make(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.FIRST) ),tom_append_list_ChoiceId(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.ChoiceId.THEN) ),l2)) ;       }     } else {       return  tom.library.sl.ChoiceId.make(l1,l2) ;     }   }   private static   tom.library.sl.Strategy  tom_get_slice_ChoiceId( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals( null )) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.sl.ChoiceId.make(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_ChoiceId(((( begin instanceof tom.library.sl.ChoiceId ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.ChoiceId.THEN) ): null ),end,tail)) ;   }      private static  tom.library.sl.Strategy  tom_make_AUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ), null ) ) , tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.Identity() )) ), null ) ) , null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_EUCtl( tom.library.sl.Strategy  s1,  tom.library.sl.Strategy  s2) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ), tom.library.sl.Choice.make(s2, tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s1, tom.library.sl.Sequence.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("x") )) ), null ) ) , null ) ) ) ));} private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  s) { return (  tom.library.sl.Choice.make(s, tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) )  );}private static  tom.library.sl.Strategy  tom_make_Repeat( tom.library.sl.Strategy  s) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make( tom.library.sl.Sequence.make(s, tom.library.sl.Sequence.make(( new tom.library.sl.MuVar("_x") ), null ) ) , tom.library.sl.Choice.make(( new tom.library.sl.Identity() ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Sequence.make(v, tom.library.sl.Sequence.make(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.Choice.make(v, tom.library.sl.Choice.make(( new tom.library.sl.One(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_RepeatId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.SequenceId.make(v, tom.library.sl.SequenceId.make(( new tom.library.sl.MuVar("_x") ), null ) ) ) ) );}private static  tom.library.sl.Strategy  tom_make_OnceTopDownId( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ), tom.library.sl.ChoiceId.make(v, tom.library.sl.ChoiceId.make(( new tom.library.sl.OneId(( new tom.library.sl.MuVar("_x") )) ), null ) ) ) ) );}       private static   tom.gom.adt.gom.types.ModuleList  tom_append_list_ConcModule( tom.gom.adt.gom.types.ModuleList l1,  tom.gom.adt.gom.types.ModuleList  l2) {     if( l1.isEmptyConcModule() ) {       return l2;     } else if( l2.isEmptyConcModule() ) {       return l1;     } else if(  l1.getTailConcModule() .isEmptyConcModule() ) {       return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( l1.getHeadConcModule() ,l2) ;     } else {       return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( l1.getHeadConcModule() ,tom_append_list_ConcModule( l1.getTailConcModule() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.ModuleList  tom_get_slice_ConcModule( tom.gom.adt.gom.types.ModuleList  begin,  tom.gom.adt.gom.types.ModuleList  end, tom.gom.adt.gom.types.ModuleList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcModule()  ||  (end== tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( begin.getHeadConcModule() ,( tom.gom.adt.gom.types.ModuleList )tom_get_slice_ConcModule( begin.getTailConcModule() ,end,tail)) ;   }      private static   tom.gom.adt.gom.types.HookDeclList  tom_append_list_ConcHookDecl( tom.gom.adt.gom.types.HookDeclList l1,  tom.gom.adt.gom.types.HookDeclList  l2) {     if( l1.isEmptyConcHookDecl() ) {       return l2;     } else if( l2.isEmptyConcHookDecl() ) {       return l1;     } else if(  l1.getTailConcHookDecl() .isEmptyConcHookDecl() ) {       return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( l1.getHeadConcHookDecl() ,l2) ;     } else {       return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( l1.getHeadConcHookDecl() ,tom_append_list_ConcHookDecl( l1.getTailConcHookDecl() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.HookDeclList  tom_get_slice_ConcHookDecl( tom.gom.adt.gom.types.HookDeclList  begin,  tom.gom.adt.gom.types.HookDeclList  end, tom.gom.adt.gom.types.HookDeclList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcHookDecl()  ||  (end== tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( begin.getHeadConcHookDecl() ,( tom.gom.adt.gom.types.HookDeclList )tom_get_slice_ConcHookDecl( begin.getTailConcHookDecl() ,end,tail)) ;   }      private static   tom.gom.adt.gom.types.SlotList  tom_append_list_ConcSlot( tom.gom.adt.gom.types.SlotList l1,  tom.gom.adt.gom.types.SlotList  l2) {     if( l1.isEmptyConcSlot() ) {       return l2;     } else if( l2.isEmptyConcSlot() ) {       return l1;     } else if(  l1.getTailConcSlot() .isEmptyConcSlot() ) {       return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,l2) ;     } else {       return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,tom_append_list_ConcSlot( l1.getTailConcSlot() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.SlotList  tom_get_slice_ConcSlot( tom.gom.adt.gom.types.SlotList  begin,  tom.gom.adt.gom.types.SlotList  end, tom.gom.adt.gom.types.SlotList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcSlot()  ||  (end== tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( begin.getHeadConcSlot() ,( tom.gom.adt.gom.types.SlotList )tom_get_slice_ConcSlot( begin.getTailConcSlot() ,end,tail)) ;   }      private static   tom.gom.adt.gom.types.OperatorDeclList  tom_append_list_ConcOperator( tom.gom.adt.gom.types.OperatorDeclList l1,  tom.gom.adt.gom.types.OperatorDeclList  l2) {     if( l1.isEmptyConcOperator() ) {       return l2;     } else if( l2.isEmptyConcOperator() ) {       return l1;     } else if(  l1.getTailConcOperator() .isEmptyConcOperator() ) {       return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( l1.getHeadConcOperator() ,l2) ;     } else {       return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( l1.getHeadConcOperator() ,tom_append_list_ConcOperator( l1.getTailConcOperator() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.OperatorDeclList  tom_get_slice_ConcOperator( tom.gom.adt.gom.types.OperatorDeclList  begin,  tom.gom.adt.gom.types.OperatorDeclList  end, tom.gom.adt.gom.types.OperatorDeclList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcOperator()  ||  (end== tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( begin.getHeadConcOperator() ,( tom.gom.adt.gom.types.OperatorDeclList )tom_get_slice_ConcOperator( begin.getTailConcOperator() ,end,tail)) ;   }      private static   tom.gom.adt.gom.types.SortList  tom_append_list_ConcSort( tom.gom.adt.gom.types.SortList l1,  tom.gom.adt.gom.types.SortList  l2) {     if( l1.isEmptyConcSort() ) {       return l2;     } else if( l2.isEmptyConcSort() ) {       return l1;     } else if(  l1.getTailConcSort() .isEmptyConcSort() ) {       return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( l1.getHeadConcSort() ,l2) ;     } else {       return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( l1.getHeadConcSort() ,tom_append_list_ConcSort( l1.getTailConcSort() ,l2)) ;     }   }   private static   tom.gom.adt.gom.types.SortList  tom_get_slice_ConcSort( tom.gom.adt.gom.types.SortList  begin,  tom.gom.adt.gom.types.SortList  end, tom.gom.adt.gom.types.SortList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyConcSort()  ||  (end== tom.gom.adt.gom.types.sortlist.EmptyConcSort.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( begin.getHeadConcSort() ,( tom.gom.adt.gom.types.SortList )tom_get_slice_ConcSort( begin.getTailConcSort() ,end,tail)) ;   }       private static   tom.gom.adt.symboltable.types.FieldDescriptionList  tom_append_list_concFieldDescription( tom.gom.adt.symboltable.types.FieldDescriptionList l1,  tom.gom.adt.symboltable.types.FieldDescriptionList  l2) {     if( l1.isEmptyconcFieldDescription() ) {       return l2;     } else if( l2.isEmptyconcFieldDescription() ) {       return l1;     } else if(  l1.getTailconcFieldDescription() .isEmptyconcFieldDescription() ) {       return  tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( l1.getHeadconcFieldDescription() ,l2) ;     } else {       return  tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( l1.getHeadconcFieldDescription() ,tom_append_list_concFieldDescription( l1.getTailconcFieldDescription() ,l2)) ;     }   }   private static   tom.gom.adt.symboltable.types.FieldDescriptionList  tom_get_slice_concFieldDescription( tom.gom.adt.symboltable.types.FieldDescriptionList  begin,  tom.gom.adt.symboltable.types.FieldDescriptionList  end, tom.gom.adt.symboltable.types.FieldDescriptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcFieldDescription()  ||  (end== tom.gom.adt.symboltable.types.fielddescriptionlist.EmptyconcFieldDescription.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( begin.getHeadconcFieldDescription() ,( tom.gom.adt.symboltable.types.FieldDescriptionList )tom_get_slice_concFieldDescription( begin.getTailconcFieldDescription() ,end,tail)) ;   }    







  private SortDecl stringSortDecl;
  private SortDecl intSortDecl;
  // indicates if the expand method must include normalization phase
  // specific to termgraphs
  private boolean forTermgraph;
  private GomEnvironment gomEnvironment;
  private SymbolTable st;

  public GraphExpander(boolean forTermgraph,GomEnvironment gomEnvironment) {
    this.forTermgraph = forTermgraph;
    this.gomEnvironment = gomEnvironment;
    st = gomEnvironment.getSymbolTable();
    stringSortDecl = gomEnvironment.builtinSort("String");
    intSortDecl = gomEnvironment.builtinSort("int");
    gomEnvironment.markUsedBuiltin("String");
    gomEnvironment.markUsedBuiltin("int");
  }

  public GomStreamManager getStreamManager() {
    return this.gomEnvironment.getStreamManager();
  }

  public SortDecl getStringSortDecl() {
    return stringSortDecl;
  }

  public SortDecl getIntSortDecl() {
    return intSortDecl;
  }

  public boolean getForTermgraph() {
    return forTermgraph;
  }

  public void setForTermgraph(boolean forTermgraph) {
    this.forTermgraph = forTermgraph;
  }

  public GomEnvironment getGomEnvironment() {
    return gomEnvironment;
  }

  public SymbolTable getSymbolTable() {
    return st;
  }

  public Pair expand(ModuleList list, HookDeclList hooks) {
    ModuleList expandedList =  tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() ;
    ArrayList hookList = new ArrayList();
    try {
      expandedList = tom_make_TopDown(tom_make_ExpandSort(hookList,this)).visit(list);
    } catch(tom.library.sl.VisitFailure e) {
      throw new tom.gom.tools.error.GomRuntimeException("Unexpected strategy failure!");
    }
    //add a global expand method in every ModuleDecl contained in the SortList
    try {
      tom_make_TopDown(tom_make_ExpandModule(getForTermgraph(),hookList,this))
.visit(expandedList);
    } catch (tom.library.sl.VisitFailure e) {
      throw new tom.gom.tools.error.GomRuntimeException("Unexpected strategy failure!");
    }
    Iterator it = hookList.iterator();
    while(it.hasNext()) {
      HookDeclList hList = (HookDeclList) it.next();
      hooks = tom_append_list_ConcHookDecl(hList,tom_append_list_ConcHookDecl(hooks, tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ));
    }
    return  tom.gom.adt.gom.types.pair.ModHookPair.make(expandedList, hooks) ;
  }

  public static class ExpandModule extends tom.library.sl.AbstractStrategyBasic {private  boolean  forTermgraph;private  java.util.ArrayList  hookList;private  GraphExpander  ge;public ExpandModule( boolean  forTermgraph,  java.util.ArrayList  hookList,  GraphExpander  ge) {super(( new tom.library.sl.Identity() ));this.forTermgraph=forTermgraph;this.hookList=hookList;this.ge=ge;}public  boolean  getforTermgraph() {return forTermgraph;}public  java.util.ArrayList  gethookList() {return hookList;}public  GraphExpander  getge() {return ge;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.gom.adt.gom.types.Module) ) {return ((T)visit_Module((( tom.gom.adt.gom.types.Module )v),introspector));}if (!(  null ==environment )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.Module  _visit_Module( tom.gom.adt.gom.types.Module  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.gom.adt.gom.types.Module )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.Module  visit_Module( tom.gom.adt.gom.types.Module  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (((Object)tom__arg) instanceof tom.gom.adt.gom.types.Module) ) {if ( ((( tom.gom.adt.gom.types.Module )((Object)tom__arg)) instanceof tom.gom.adt.gom.types.Module) ) {if ( ((( tom.gom.adt.gom.types.Module )(( tom.gom.adt.gom.types.Module )((Object)tom__arg))) instanceof tom.gom.adt.gom.types.module.Module) ) { tom.gom.adt.gom.types.ModuleDecl  tomMatch603_1= (( tom.gom.adt.gom.types.Module )((Object)tom__arg)).getMDecl() ;if ( (tomMatch603_1 instanceof tom.gom.adt.gom.types.ModuleDecl) ) {if ( ((( tom.gom.adt.gom.types.ModuleDecl )tomMatch603_1) instanceof tom.gom.adt.gom.types.moduledecl.ModuleDecl) ) {






        hookList.add(ge.expHooksModule( tomMatch603_1.getModuleName() .getName(), (( tom.gom.adt.gom.types.Module )((Object)tom__arg)).getSorts() ,tomMatch603_1,ge.getForTermgraph()));
      }}}}}}}return _visit_Module(tom__arg,introspector);}}private static  tom.library.sl.Strategy  tom_make_ExpandModule( boolean  t0,  java.util.ArrayList  t1,  GraphExpander  t2) { return new ExpandModule(t0,t1,t2);}public static class ExpandSort extends tom.library.sl.AbstractStrategyBasic {private  java.util.ArrayList  hookList;private  GraphExpander  ge;public ExpandSort( java.util.ArrayList  hookList,  GraphExpander  ge) {super(( new tom.library.sl.Identity() ));this.hookList=hookList;this.ge=ge;}public  java.util.ArrayList  gethookList() {return hookList;}public  GraphExpander  getge() {return ge;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.gom.adt.gom.types.Sort) ) {return ((T)visit_Sort((( tom.gom.adt.gom.types.Sort )v),introspector));}if (!(  null ==environment )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.Sort  _visit_Sort( tom.gom.adt.gom.types.Sort  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!(  null ==environment )) {return (( tom.gom.adt.gom.types.Sort )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);}}@SuppressWarnings("unchecked")public  tom.gom.adt.gom.types.Sort  visit_Sort( tom.gom.adt.gom.types.Sort  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (((Object)tom__arg) instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )((Object)tom__arg)) instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )(( tom.gom.adt.gom.types.Sort )((Object)tom__arg))) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch604_1= (( tom.gom.adt.gom.types.Sort )((Object)tom__arg)).getDecl() ;if ( (tomMatch604_1 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch604_1) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) { String  tom_sortname= tomMatch604_1.getName() ; tom.gom.adt.gom.types.SortDecl  tom_sortdecl=tomMatch604_1;







        //We add 4 new operators Lab<Sort>,Ref<Sort>,Path<Sort>,Var<Sort>
        //the last one is only used to implement the termgraph rewriting step
        // for now, we need also to fill the symbol table
        OperatorDecl labOp =  tom.gom.adt.gom.types.operatordecl.OperatorDecl.make("Lab"+tom_sortname, tom_sortdecl,  tom.gom.adt.gom.types.typedproduction.Slots.make( tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("label"+tom_sortname, ge.getStringSortDecl()) , tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("term"+tom_sortname, tom_sortdecl) , tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) ) ) ,  tom.gom.adt.gom.types.option.Details.make("/** labOp */") ) ;
        ge.getSymbolTable().addConstructor("Lab"+tom_sortname,tom_sortname, tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( tom.gom.adt.symboltable.types.fielddescription.FieldDescription.make("label"+tom_sortname, "String",  tom.gom.adt.symboltable.types.status.SNone.make() ) , tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( tom.gom.adt.symboltable.types.fielddescription.FieldDescription.make("term"+tom_sortname, tom_sortname,  tom.gom.adt.symboltable.types.status.SNone.make() ) , tom.gom.adt.symboltable.types.fielddescriptionlist.EmptyconcFieldDescription.make() ) ) );
        OperatorDecl refOp =  tom.gom.adt.gom.types.operatordecl.OperatorDecl.make("Ref"+tom_sortname, tom_sortdecl,  tom.gom.adt.gom.types.typedproduction.Slots.make( tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("label"+tom_sortname, ge.getStringSortDecl()) , tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) ) ,  tom.gom.adt.gom.types.option.Details.make("/** refOp */") ) ;
        ge.getSymbolTable().addConstructor("Ref"+tom_sortname,tom_sortname, tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( tom.gom.adt.symboltable.types.fielddescription.FieldDescription.make("label"+tom_sortname, "String",  tom.gom.adt.symboltable.types.status.SNone.make() ) , tom.gom.adt.symboltable.types.fielddescriptionlist.EmptyconcFieldDescription.make() ) );
        OperatorDecl pathOp =  tom.gom.adt.gom.types.operatordecl.OperatorDecl.make("Path"+tom_sortname, tom_sortdecl,  tom.gom.adt.gom.types.typedproduction.Variadic.make(ge.getIntSortDecl()) ,  tom.gom.adt.gom.types.option.Details.make("/** pathOp */") ) ;
        ge.getSymbolTable().addVariadicConstructor("Path"+tom_sortname,"int",tom_sortname);
        OperatorDecl varOp =  tom.gom.adt.gom.types.operatordecl.OperatorDecl.make("Var"+tom_sortname, tom_sortdecl,  tom.gom.adt.gom.types.typedproduction.Slots.make( tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make("label"+tom_sortname, ge.getStringSortDecl()) , tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) ) ,  tom.gom.adt.gom.types.option.Details.make("/** varOp */") ) ;
        ge.getSymbolTable().addConstructor("Var"+tom_sortname,tom_sortname, tom.gom.adt.symboltable.types.fielddescriptionlist.ConsconcFieldDescription.make( tom.gom.adt.symboltable.types.fielddescription.FieldDescription.make("label"+tom_sortname, "String",  tom.gom.adt.symboltable.types.status.SNone.make() ) , tom.gom.adt.symboltable.types.fielddescriptionlist.EmptyconcFieldDescription.make() ) );
        hookList.add(ge.pathHooks(pathOp,tom_sortdecl));
        return (( tom.gom.adt.gom.types.Sort )((Object)tom__arg)).setOperatorDecls(tom_append_list_ConcOperator( (( tom.gom.adt.gom.types.Sort )((Object)tom__arg)).getOperatorDecls() , tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(labOp, tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(refOp, tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(pathOp, tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(varOp, tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator.make() ) ) ) ) ));

      }}}}}}}return _visit_Sort(tom__arg,introspector);}}private static  tom.library.sl.Strategy  tom_make_ExpandSort( java.util.ArrayList  t0,  GraphExpander  t1) { return new ExpandSort(t0,t1);}



  private HookDeclList pathHooks(OperatorDecl opDecl, SortDecl sort){
    String moduleName = sort.getModuleDecl().getModuleName().getName();
    String sortName = sort.getName();
    String prefixPkg = getStreamManager().getPackagePath(moduleName);
    String codeImport ="\n    import "+((prefixPkg=="")?"":prefixPkg+".")+moduleName.toLowerCase()+".types.*;\n    import tom.library.sl.*;\n    "


;

    String codeBlock ="\n\n    public Path add(Path p) {\n        Position pp = Position.makeFromPath(this);\n        return make(pp.add(p));\n    }\n\n    public Path inverse() {\n      Position pp = Position.makeFromPath(this);\n      return make(pp.inverse());\n    }\n\n    public Path sub(Path p) {\n      Position pp = Position.makeFromPath(this);\n      return make(pp.sub(p));\n    }\n\n    public int getHead() {\n      return getHeadPath"+sortName+"();\n    }\n\n    public Path getTail() {\n      return (Path) getTailPath"+sortName+"();\n    }\n\n    public Path getCanonicalPath() {\n      %match(this) {\n        Path"+sortName+"(X*,x,y,Y*) -> {\n          if (`x==-`y) {\n            return ((Path)`Path"+sortName+"(X*,Y*)).getCanonicalPath();\n          }\n        }\n      }\n      return this;\n    }\n\n    public Path conc(int i) {\n      Path"+sortName+" current = this;\n      return (Path) `Path"+sortName+"(i,current*);\n    }\n\n    public int[] toIntArray() {\n      int[] array = new int[length()];\n      Path p = this;\n      for(int i=0; i<length(); i++) {\n        array[i] = p.getHead();\n        p = p.getTail();\n      }\n      return array;\n    }\n\n    public static Path"+sortName+" make(Path path) {\n      "+CodeGen.generateCode( tom.gom.adt.code.types.code.FullSortClass.make(sort) )+" ref = `Path"+sortName+"();\n      Path pp = path.getCanonicalPath();\n      int size = pp.length();\n      for(int i=0;i<size;i++){\n        ref = `Path"+sortName+"(ref*,pp.getHead());\n        pp = pp.getTail();\n      }\n      return (Path"+sortName+") ref;\n    }\n\n    public int compare(Path p) {\n      Position p1 = Position.makeFromPath(this);\n      Position p2 = Position.makeFromPath(p);\n      return p1.compare(p2);\n    }\n    "


































































;

    return
       tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.ImportHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) ,  tom.gom.adt.code.types.code.Code.make(codeImport) ) , tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.InterfaceHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) ,  tom.gom.adt.code.types.code.Code.make("tom.library.sl.Path") ) , tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.BlockHookDecl.make( tom.gom.adt.gom.types.decl.CutOperator.make(opDecl) ,  tom.gom.adt.code.types.code.Code.make(codeBlock) ,  true ) , tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) ) ) 



;
  }

  private HookDeclList expHooksModule(String gomModuleName,
      SortList sorts,
      ModuleDecl mDecl,
      boolean forTermgraph) {
    String fullAbstractTypeClassName = st.getFullAbstractTypeClassName(gomModuleName);
    String fullModuleName = st.getFullModuleName(gomModuleName);

    String codeImport ="\n    import "+fullModuleName+".types.*;\n    import "+fullModuleName+".*;\n    import tom.library.sl.*;\n    "



;

    String codeStrategies = getStrategies(sorts);

    {{if ( (((Object)sorts) instanceof tom.gom.adt.gom.types.SortList) ) {if ( (((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.ConsConcSort) || ((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.EmptyConcSort)) ) { tom.gom.adt.gom.types.SortList  tomMatch605__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));do {{if (!( tomMatch605__end__4.isEmptyConcSort() )) { tom.gom.adt.gom.types.Sort  tomMatch605_8= tomMatch605__end__4.getHeadConcSort() ;if ( (tomMatch605_8 instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )tomMatch605_8) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch605_7= tomMatch605_8.getDecl() ;if ( (tomMatch605_7 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch605_7) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {

        codeImport += "\n          import "+st.getFullConstructorClassName("Path"+ tomMatch605_7.getName() )+";\n        "

;
      }}}}}if ( tomMatch605__end__4.isEmptyConcSort() ) {tomMatch605__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));} else {tomMatch605__end__4= tomMatch605__end__4.getTailConcSort() ;}}} while(!( (tomMatch605__end__4==(( tom.gom.adt.gom.types.SortList )((Object)sorts))) ));}}}}


    String codeBlockCommon ="\n    %include{java/util/HashMap.tom}\n    %include{java/util/ArrayList.tom}\n    %include{sl.tom}\n\n    static int freshlabel =0; //to unexpand termgraphs\n\n    %typeterm tom_Info {\n      implement { Info }\n      is_sort(t) { ($t instanceof Info) }\n    }\n\n\n    public static class Info {\n      public String label;\n      public Path path;\n      public "+fullAbstractTypeClassName+" term;\n    }\n\n    public "+fullAbstractTypeClassName+" unexpand() {\n       java.util.HashMap<String,Position> map = getMapFromPositionToLabel();\n       try {\n         return `Sequence(TopDown(CollectRef(map)),BottomUp(AddLabel(map))).visit(this);\n       } catch (tom.library.sl.VisitFailure e) {\n         throw new RuntimeException(\"Unexpected strategy failure!\");\n       }\n    }\n\n    protected java.util.HashMap<String,Position> getMapFromPositionToLabel() {\n      java.util.HashMap<String,Position> map = new java.util.HashMap<String,Position>();\n      try {\n        `TopDown(CollectPositionsOfLabels(map)).visit(this);\n        return map;\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    "





































;


    String codeBlockTermWithPointers ="\n\n      public "+fullAbstractTypeClassName+" expand() {\n        java.util.HashMap<Position,String> map = new java.util.HashMap<Position,String>();\n        Strategy label2path = `Sequence(RepeatId(OnceTopDownId(CollectAndRemoveLabels(map))),TopDown(Label2Path(map)));\n        try {\n          return `label2path.visit(this);\n        } catch (tom.library.sl.VisitFailure e) {\n          throw new RuntimeException(\"Unexpected strategy failure!\");\n        }}\n        "









;

    String codeBlockTermGraph ="\n\n   public "+fullAbstractTypeClassName+" expand() {\n       java.util.HashMap<Position,String> map = new java.util.HashMap<Position,String>();\n       try {\n         return `InnermostIdSeq(NormalizeLabel(map)).visit(this.unexpand()).label2path();\n       } catch (tom.library.sl.VisitFailure e) {\n         throw new RuntimeException(\"Unexpected strategy failure!\");\n       }\n     }\n\n    public "+fullAbstractTypeClassName+" normalizeWithLabels() {\n      java.util.HashMap<Position,String> map = new java.util.HashMap<Position,String>();\n      try {\n        return `InnermostIdSeq(NormalizeLabel(map)).visit(this);\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public "+fullAbstractTypeClassName+" label2path() {\n      java.util.HashMap<Position,String> map = new java.util.HashMap<Position,String>();\n      Strategy label2path = `Sequence(RepeatId(OnceTopDownId(CollectAndRemoveLabels(map))),TopDown(Label2Path(map)));\n      try {\n        return label2path.visit(this);\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public java.util.HashMap<String,Position> getMapFromLabelToPositionAndRemoveLabels() {\n      java.util.HashMap<String,Position> map = new java.util.HashMap<String,Position>();\n      try {\n        `TopDown(CollectAndRemoveLabels(map)).visit(this);\n        return map;\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public java.util.HashMap<String,Position> getMapFromLabelToPosition() {\n      java.util.HashMap<String,Position> map = new java.util.HashMap<String,Position>();\n      try {\n        `TopDown(CollectLabels(map)).visit(this);\n        return map;\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public "+fullAbstractTypeClassName+" normalize() {\n      try {\n        return `InnermostIdSeq(Normalize()).visit(this);\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public "+fullAbstractTypeClassName+" applyGlobalRedirection(Position p1,Position p2) {\n      try {\n         return globalRedirection(p1,p2).visit(this);\n      } catch (tom.library.sl.VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n    public static Strategy globalRedirection(Position p1,Position p2) {\n        return `TopDown(GlobalRedirection(p1,p2));\n    }\n\n    public "+fullAbstractTypeClassName+" swap(Position p1, Position p2) {\n      try {\n        "+fullAbstractTypeClassName+" updatedSubject =  `TopDown(Sequence(UpdatePos(p1,p2))).visit(this);\n        "+fullAbstractTypeClassName+" subterm_p1 = p1.getSubterm().visit(updatedSubject);\n        "+fullAbstractTypeClassName+" subterm_p2 = p2.getSubterm().visit(updatedSubject);\n        return `Sequence(p2.getReplace(subterm_p1),p1.getReplace(subterm_p2)).visit(updatedSubject);\n      } catch (VisitFailure e) {\n        throw new RuntimeException(\"Unexpected strategy failure!\");\n      }\n    }\n\n   %strategy Normalize() extends Identity() {\n"

















































































;

  {{if ( (((Object)sorts) instanceof tom.gom.adt.gom.types.SortList) ) {if ( (((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.ConsConcSort) || ((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.EmptyConcSort)) ) { tom.gom.adt.gom.types.SortList  tomMatch606__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));do {{if (!( tomMatch606__end__4.isEmptyConcSort() )) { tom.gom.adt.gom.types.Sort  tomMatch606_8= tomMatch606__end__4.getHeadConcSort() ;if ( (tomMatch606_8 instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )tomMatch606_8) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch606_7= tomMatch606_8.getDecl() ;if ( (tomMatch606_7 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch606_7) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) { String  tom_sortname= tomMatch606_7.getName() ;

 codeBlockTermGraph += "\n        visit "+tom_sortname+" {\n          p@Path"+tom_sortname+"(_*) -> {\n            Position current = getEnvironment().getPosition();\n            Position dest = (Position) current.add((Path)`p).getCanonicalPath();\n            if(current.compare(dest)== -1) {\n                getEnvironment().followPath((Path)`p);\n                Position realDest = getEnvironment().getPosition();\n            if(!realDest.equals(dest)) {\n                /* the subterm pointed was a pos (in case of previous switch) */\n                /* and we must only update the relative position */\n                getEnvironment().followPath(current.sub(getEnvironment().getPosition()));\n                return Path"+tom_sortname+".make(realDest.sub(current));\n            }  else {\n                /* switch the rel position and the pointed subterm */\n\n                /* 1. construct the new relative position */\n                "+tom_sortname+" relref = Path"+tom_sortname+".make(current.sub(dest));\n\n                /* 2. update the part to change */\n                `TopDown(UpdatePos(dest,current)).visit(getEnvironment());\n\n                /* 3. save the subterm updated */\n                "+tom_sortname+" subterm = ("+tom_sortname+") getEnvironment().getSubject();\n\n                /* 4. replace at dest the subterm by the new relative pos */\n                getEnvironment().setSubject(relref);\n                getEnvironment().followPath(current.sub(getEnvironment().getPosition()));\n                return subterm;\n            }\n          }\n        }\n      }\n"
































;
      }}}}}if ( tomMatch606__end__4.isEmptyConcSort() ) {tomMatch606__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));} else {tomMatch606__end__4= tomMatch606__end__4.getTailConcSort() ;}}} while(!( (tomMatch606__end__4==(( tom.gom.adt.gom.types.SortList )((Object)sorts))) ));}}}}


  codeBlockTermGraph += "\n    }\n\n   %strategy UpdatePos(source:Position,target:Position) extends Identity() {\n  "



;


   {{if ( (((Object)sorts) instanceof tom.gom.adt.gom.types.SortList) ) {if ( (((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.ConsConcSort) || ((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.EmptyConcSort)) ) { tom.gom.adt.gom.types.SortList  tomMatch607__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));do {{if (!( tomMatch607__end__4.isEmptyConcSort() )) { tom.gom.adt.gom.types.Sort  tomMatch607_8= tomMatch607__end__4.getHeadConcSort() ;if ( (tomMatch607_8 instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )tomMatch607_8) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch607_7= tomMatch607_8.getDecl() ;if ( (tomMatch607_7 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch607_7) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) { String  tom_sortname= tomMatch607_7.getName() ;

        codeBlockTermGraph += "\n      visit "+tom_sortname+" {\n            p@Path"+tom_sortname+"(_*) -> {\n              Position current = getEnvironment().getPosition();\n              Position dest = (Position) current.add((Path)`p).getCanonicalPath();\n              /* relative pos from the source to the external */\n              if(current.hasPrefix(source) && !dest.hasPrefix(target) && !dest.hasPrefix(source)){\n                current = current.changePrefix(source,target);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n              /* relative pos from the external to the source */\n              if (dest.hasPrefix(source) && !current.hasPrefix(target) && !current.hasPrefix(source)){\n                dest = dest.changePrefix(source,target);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n              /* relative pos from the target to the external */\n              if(current.hasPrefix(target) && !dest.hasPrefix(source) && !dest.hasPrefix(target)){\n                current = current.changePrefix(target,source);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n              /* relative pos from the external to the target */\n              if (dest.hasPrefix(target) && !current.hasPrefix(source) && !current.hasPrefix(target)){\n                dest = dest.changePrefix(target,source);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n              /* relative pos from the source to the target */\n              if(current.hasPrefix(source) && dest.hasPrefix(target)){\n                current = current.changePrefix(source,target);\n                dest = dest.changePrefix(target,source);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n              /* relative pos from the target to the source */\n              if(current.hasPrefix(target) && dest.hasPrefix(source)){\n                current = current.changePrefix(target,source);\n                dest = dest.changePrefix(source,target);\n                return Path"+tom_sortname+".make(dest.sub(current));\n              }\n\n            }\n          }\n    "












































;
      }}}}}if ( tomMatch607__end__4.isEmptyConcSort() ) {tomMatch607__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));} else {tomMatch607__end__4= tomMatch607__end__4.getTailConcSort() ;}}} while(!( (tomMatch607__end__4==(( tom.gom.adt.gom.types.SortList )((Object)sorts))) ));}}}}


   codeBlockTermGraph += "\n   }\n\n   %strategy GlobalRedirection(source:Position,target:Position) extends Identity() {\n  "



;

   {{if ( (((Object)sorts) instanceof tom.gom.adt.gom.types.SortList) ) {if ( (((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.ConsConcSort) || ((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.EmptyConcSort)) ) { tom.gom.adt.gom.types.SortList  tomMatch608__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));do {{if (!( tomMatch608__end__4.isEmptyConcSort() )) { tom.gom.adt.gom.types.Sort  tomMatch608_8= tomMatch608__end__4.getHeadConcSort() ;if ( (tomMatch608_8 instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )tomMatch608_8) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch608_7= tomMatch608_8.getDecl() ;if ( (tomMatch608_7 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch608_7) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) { String  tom_sortname= tomMatch608_7.getName() ;

        codeBlockTermGraph += "\n      visit "+tom_sortname+" {\n            p@Path"+tom_sortname+"(_*) -> {\n              Position current = getEnvironment().getPosition();\n              Position dest = (Position) current.add((Path)`p).getCanonicalPath();\n              if(dest.equals(source)) {\n                return Path"+tom_sortname+".make(target.sub(current));\n              }\n            }\n          }\n    "









;
      }}}}}if ( tomMatch608__end__4.isEmptyConcSort() ) {tomMatch608__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));} else {tomMatch608__end__4= tomMatch608__end__4.getTailConcSort() ;}}} while(!( (tomMatch608__end__4==(( tom.gom.adt.gom.types.SortList )((Object)sorts))) ));}}}}


   codeBlockTermGraph += "\n   }\n   "

;

    String codeBlock = codeBlockCommon + codeStrategies + (getForTermgraph()?codeBlockTermGraph:codeBlockTermWithPointers);

    return  tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.ImportHookDecl.make( tom.gom.adt.gom.types.decl.CutModule.make(mDecl) ,  tom.gom.adt.code.types.code.Code.make(codeImport) ) , tom.gom.adt.gom.types.hookdecllist.ConsConcHookDecl.make( tom.gom.adt.gom.types.hookdecl.BlockHookDecl.make( tom.gom.adt.gom.types.decl.CutModule.make(mDecl) ,  tom.gom.adt.code.types.code.Code.make(codeBlock) ,  true ) , tom.gom.adt.gom.types.hookdecllist.EmptyConcHookDecl.make() ) ) 

;
  }

  private String getStrategies(SortList sorts) {
    StringBuilder strategiesCode = new StringBuilder();
    // for the CollectLabels strategy
    StringBuilder CollectLabelsCode = new StringBuilder();
    CollectLabelsCode.append("\n    /**\n     * Collect labels and their corresponding positions in a\n     * map. The keys are the labels.\n     * @param map the map to collect tuples <label,position>\n     */\n\n    %strategy CollectLabels(map:HashMap) extends Identity() {\n      "







);

    // for the CollectAndRemoveLabels strategy
    StringBuilder CollectAndRemoveLabelsCode = new StringBuilder();
    CollectAndRemoveLabelsCode.append("\n    /**\n     * Collect labels and their corresponding positions in a\n     * map. The keys are the labels. At the same time, replace the labelled\n     * term by the term itself.\n     * @param map the map to collect tuples <label,position>\n     */\n\n    %strategy CollectAndRemoveLabels(map:HashMap) extends Identity() {\n      "








);

    // for the CollectPositionsOfLabels strategy
    StringBuilder CollectPositionsOfLabelsCode = new StringBuilder();
    CollectPositionsOfLabelsCode.append("\n     /**\n     * Collect labels of sort and their corresponding positions in a\n     * map and at the same time replace the labelled term by the term itself.\n     * The keys are the positions.\n     * @param map the map to collect tuples <position,label>\n     */\n\n    %strategy CollectPositionsOfLabels(map:HashMap) extends Identity() {\n    "








);

    // for the Label2Path strategy
    StringBuilder Label2PathCode = new StringBuilder();
    Label2PathCode.append("\n    %strategy Label2Path(map:HashMap) extends Identity() {\n    "

);

    // for the CollectRef strategy
    StringBuilder CollectRefCode = new StringBuilder();
    CollectRefCode.append("\n    %strategy CollectRef(map:HashMap) extends Identity() {\n    "

);

    // for the AddLabel strategy
    StringBuilder AddLabelCode = new StringBuilder();
    AddLabelCode.append("\n    %strategy AddLabel(map:HashMap) extends Identity() {\n    "

);

    // for the NormalizeLabel strategy
    StringBuilder NormalizeLabelCode = new StringBuilder();
    NormalizeLabelCode.append("\n    %strategy NormalizeLabel(map:HashMap) extends Identity() {\n    "

);

    {{if ( (((Object)sorts) instanceof tom.gom.adt.gom.types.SortList) ) {if ( (((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.ConsConcSort) || ((( tom.gom.adt.gom.types.SortList )(( tom.gom.adt.gom.types.SortList )((Object)sorts))) instanceof tom.gom.adt.gom.types.sortlist.EmptyConcSort)) ) { tom.gom.adt.gom.types.SortList  tomMatch609__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));do {{if (!( tomMatch609__end__4.isEmptyConcSort() )) { tom.gom.adt.gom.types.Sort  tomMatch609_8= tomMatch609__end__4.getHeadConcSort() ;if ( (tomMatch609_8 instanceof tom.gom.adt.gom.types.Sort) ) {if ( ((( tom.gom.adt.gom.types.Sort )tomMatch609_8) instanceof tom.gom.adt.gom.types.sort.Sort) ) { tom.gom.adt.gom.types.SortDecl  tomMatch609_7= tomMatch609_8.getDecl() ;if ( (tomMatch609_7 instanceof tom.gom.adt.gom.types.SortDecl) ) {if ( ((( tom.gom.adt.gom.types.SortDecl )tomMatch609_7) instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) { String  tom_sortName= tomMatch609_7.getName() ; tom.gom.adt.gom.types.SortDecl  tom_sDecl=tomMatch609_7;


        strategiesCode.append("\n\n    %typeterm tom_Info"+tom_sortName+" {\n        implement { Info"+tom_sortName+" }\n        is_sort(t) { ($t instanceof Info"+tom_sortName+") }\n    }\n\n    static class Info"+tom_sortName+"{\n      public Position omegaRef;\n      public "+CodeGen.generateCode( tom.gom.adt.code.types.code.FullSortClass.make(tom_sDecl) )+" sharedTerm;\n    }\n\n    "











);

        // generate the code for the CollectLabels strategy
        CollectLabelsCode.append("\n      visit "+tom_sortName+"{\n        Lab"+tom_sortName+"[label"+tom_sortName+"=label]-> {\n          map.put(`label,getEnvironment().getPosition());\n          return ("+tom_sortName+") getEnvironment().getSubject();\n        }\n      }\n        "






);

        // generate the code for the CollectAndRemoveLabels strategy
        CollectAndRemoveLabelsCode.append("\n      visit "+tom_sortName+"{\n        Lab"+tom_sortName+"[label"+tom_sortName+"=label,term"+tom_sortName+"=term]-> {\n          map.put(`label,getEnvironment().getPosition());\n          return `term;\n        }\n      }\n      "






);

        // generate the code for the CollectPositionsOfLabels strategy
        CollectPositionsOfLabelsCode.append("\n      visit "+tom_sortName+"{\n        Lab"+tom_sortName+"[label"+tom_sortName+"=label]-> {\n          map.put(getEnvironment().getPosition().toString(),`label);\n          return ("+tom_sortName+") getEnvironment().getSubject();\n        }\n      }\n      "






);

       // generate the code for the Label2Path strategy
        Label2PathCode.append("\n      visit "+tom_sortName+" {\n        Ref"+tom_sortName+"[label"+tom_sortName+"=label] -> {\n          if (map.containsKey(`label)) {\n            Position target = (Position) map.get(`label);\n            /*"+CodeGen.generateCode( tom.gom.adt.code.types.code.FullSortClass.make(tom_sDecl) )+" ref = */ return ("+CodeGen.generateCode( tom.gom.adt.code.types.code.FullSortClass.make(tom_sDecl) )+") (Path"+tom_sortName+".make(target.sub(getEnvironment().getPosition())).getCanonicalPath());\n          }\n        }\n      }\n      "








);

        // generate the code for the CollectRef strategy
        CollectRefCode.append("\n      visit "+tom_sortName+" {\n        p@Path"+tom_sortName+"(_*) -> {\n          /* \n           * use String instead of Position because containskey method does\n           * not use the method equals to compare values\n           */\n          String target =\n            getEnvironment().getPosition().add((Path)`p).getCanonicalPath().toString();\n          if (map.containsKey(target)){\n            String label = (String) map.get(target);\n            return `Ref"+tom_sortName+"(label);\n          }\n          else{\n            freshlabel++;\n            String label = \"tom_label\"+freshlabel;\n            map.put(target,label);\n            return `Ref"+tom_sortName+"(label);\n          }\n        }\n      }\n   "




















);

        // generate the code for the AddLabel strategy
        AddLabelCode.append("\n    visit "+tom_sortName+" {\n      t@!Lab"+tom_sortName+"[] -> {\n        if (map.containsKey(getEnvironment().getPosition().toString())) {\n          String label = (String) map.get(getEnvironment().getPosition().toString());\n          return `Lab"+tom_sortName+"(label,t);\n        }\n      }\n    }\n   "








);
        // generate the code for the NormalizeLabel strategy
        NormalizeLabelCode.append("\n      visit "+tom_sortName+" {\n        Ref"+tom_sortName+"[label"+tom_sortName+"=label] -> {\n          if (! map.containsKey(`label)){\n            Position old = getEnvironment().getPosition();\n            Position rootpos = Position.make();\n            Info"+tom_sortName+" info = new Info"+tom_sortName+"();\n            info.omegaRef = old;\n            getEnvironment().followPath(rootpos.sub(getEnvironment().getPosition()));\n            `OnceTopDown(CollectSubterm"+tom_sortName+"(label,info)).visit(getEnvironment());\n            getEnvironment().followPath(old.sub(getEnvironment().getPosition()));\n            /* test if it is not a ref to a cycle */\n            if (info.sharedTerm!=null) {\n              map.put(`label,old);\n              return `Lab"+tom_sortName+"(label,info.sharedTerm);\n            }\n          }\n        }\n        Lab"+tom_sortName+"[label"+tom_sortName+"=label] -> {\n          map.put(`label,getEnvironment().getPosition());\n        }\n      }\n    "





















);

    // generate each strategy CollectSubterm<sortname>
    strategiesCode.append("\n    %strategy CollectSubterm"+tom_sortName+"(label:String,info:tom_Info"+tom_sortName+") extends Fail() {\n      visit "+tom_sortName+" {\n        term@Lab"+tom_sortName+"[label"+tom_sortName+"=label,term"+tom_sortName+"=subterm] -> {\n          Position current = getEnvironment().getPosition();\n          if (label.equals(`label)) {\n            /* test if it is not a cycle */\n            if (!info.omegaRef.hasPrefix(current)) {\n              /* return a ref */\n              info.sharedTerm = `subterm;\n              return `Ref"+tom_sortName+"(label);\n            }\n            else {\n              /* do not return a ref and stop to collect */\n              return `term;\n            }\n          }\n        }\n      }\n    }\n    "



















);
      }}}}}if ( tomMatch609__end__4.isEmptyConcSort() ) {tomMatch609__end__4=(( tom.gom.adt.gom.types.SortList )((Object)sorts));} else {tomMatch609__end__4= tomMatch609__end__4.getTailConcSort() ;}}} while(!( (tomMatch609__end__4==(( tom.gom.adt.gom.types.SortList )((Object)sorts))) ));}}}}



    CollectLabelsCode.append("\n  }\n  "

);
    CollectAndRemoveLabelsCode.append("\n  }\n  "

);
    CollectPositionsOfLabelsCode.append("\n  }\n  "

);
    Label2PathCode.append("\n  }\n  "

);

    CollectRefCode.append("\n  }\n  "

);
    AddLabelCode.append("\n  }\n  "

);
    NormalizeLabelCode.append("\n  }\n  "

);
    strategiesCode.append(CollectLabelsCode);
    strategiesCode.append(CollectAndRemoveLabelsCode);
    strategiesCode.append(CollectPositionsOfLabelsCode);
    strategiesCode.append(Label2PathCode);
    strategiesCode.append(CollectRefCode);
    strategiesCode.append(AddLabelCode);
    strategiesCode.append(NormalizeLabelCode);
    return strategiesCode.toString();
  }
}
