/*
* Copyright (c) 2000-2011, INPL, INRIA
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are
* met: 
*	- Redistributions of source code must retain the above copyright
*	notice, this list of conditions and the following disclaimer.  
*	- Redistributions in binary form must reproduce the above copyright
*	notice, this list of conditions and the following disclaimer in the
*	documentation and/or other materials provided with the distribution.
*	- Neither the name of the INRIA nor the names of its
*	contributors may be used to endorse or promote products derived from
*	this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
* OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
* THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package tom.library.bytecode;

import java.util.Map;
import java.util.HashMap;
import java.io.Writer;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassAdapter;

import tom.library.sl.*;

import tom.library.adt.bytecode.*;
import tom.library.adt.bytecode.types.*;

/**
* A dot control flow graph exporter.
* This class generates a control flow graph for each method of a class.
*/
public class CFGViewer {



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

  private static   tom.library.adt.bytecode.types.LabelNodeList  tom_append_list_LabelNodeList( tom.library.adt.bytecode.types.LabelNodeList l1,  tom.library.adt.bytecode.types.LabelNodeList  l2) {
    if( l1.isEmptyLabelNodeList() ) {
      return l2;
    } else if( l2.isEmptyLabelNodeList() ) {
      return l1;
    } else if(  l1.getTailLabelNodeList() .isEmptyLabelNodeList() ) {
      return  tom.library.adt.bytecode.types.labelnodelist.ConsLabelNodeList.make( l1.getHeadLabelNodeList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.labelnodelist.ConsLabelNodeList.make( l1.getHeadLabelNodeList() ,tom_append_list_LabelNodeList( l1.getTailLabelNodeList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.LabelNodeList  tom_get_slice_LabelNodeList( tom.library.adt.bytecode.types.LabelNodeList  begin,  tom.library.adt.bytecode.types.LabelNodeList  end, tom.library.adt.bytecode.types.LabelNodeList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyLabelNodeList()  ||  (end== tom.library.adt.bytecode.types.labelnodelist.EmptyLabelNodeList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.labelnodelist.ConsLabelNodeList.make( begin.getHeadLabelNodeList() ,( tom.library.adt.bytecode.types.LabelNodeList )tom_get_slice_LabelNodeList( begin.getTailLabelNodeList() ,end,tail)) ;
  }
  
  private static   tom.library.adt.bytecode.types.MethodList  tom_append_list_MethodList( tom.library.adt.bytecode.types.MethodList l1,  tom.library.adt.bytecode.types.MethodList  l2) {
    if( l1.isEmptyMethodList() ) {
      return l2;
    } else if( l2.isEmptyMethodList() ) {
      return l1;
    } else if(  l1.getTailMethodList() .isEmptyMethodList() ) {
      return  tom.library.adt.bytecode.types.methodlist.ConsMethodList.make( l1.getHeadMethodList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.methodlist.ConsMethodList.make( l1.getHeadMethodList() ,tom_append_list_MethodList( l1.getTailMethodList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.MethodList  tom_get_slice_MethodList( tom.library.adt.bytecode.types.MethodList  begin,  tom.library.adt.bytecode.types.MethodList  end, tom.library.adt.bytecode.types.MethodList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyMethodList()  ||  (end== tom.library.adt.bytecode.types.methodlist.EmptyMethodList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.methodlist.ConsMethodList.make( begin.getHeadMethodList() ,( tom.library.adt.bytecode.types.MethodList )tom_get_slice_MethodList( begin.getTailMethodList() ,end,tail)) ;
  }
  
  private static   tom.library.adt.bytecode.types.LocalVariableList  tom_append_list_LocalVariableList( tom.library.adt.bytecode.types.LocalVariableList l1,  tom.library.adt.bytecode.types.LocalVariableList  l2) {
    if( l1.isEmptyLocalVariableList() ) {
      return l2;
    } else if( l2.isEmptyLocalVariableList() ) {
      return l1;
    } else if(  l1.getTailLocalVariableList() .isEmptyLocalVariableList() ) {
      return  tom.library.adt.bytecode.types.localvariablelist.ConsLocalVariableList.make( l1.getHeadLocalVariableList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.localvariablelist.ConsLocalVariableList.make( l1.getHeadLocalVariableList() ,tom_append_list_LocalVariableList( l1.getTailLocalVariableList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.LocalVariableList  tom_get_slice_LocalVariableList( tom.library.adt.bytecode.types.LocalVariableList  begin,  tom.library.adt.bytecode.types.LocalVariableList  end, tom.library.adt.bytecode.types.LocalVariableList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyLocalVariableList()  ||  (end== tom.library.adt.bytecode.types.localvariablelist.EmptyLocalVariableList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.localvariablelist.ConsLocalVariableList.make( begin.getHeadLocalVariableList() ,( tom.library.adt.bytecode.types.LocalVariableList )tom_get_slice_LocalVariableList( begin.getTailLocalVariableList() ,end,tail)) ;
  }
  
  private static   tom.library.adt.bytecode.types.InstructionList  tom_append_list_InstructionList( tom.library.adt.bytecode.types.InstructionList l1,  tom.library.adt.bytecode.types.InstructionList  l2) {
    if( l1.isEmptyInstructionList() ) {
      return l2;
    } else if( l2.isEmptyInstructionList() ) {
      return l1;
    } else if(  l1.getTailInstructionList() .isEmptyInstructionList() ) {
      return  tom.library.adt.bytecode.types.instructionlist.ConsInstructionList.make( l1.getHeadInstructionList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.instructionlist.ConsInstructionList.make( l1.getHeadInstructionList() ,tom_append_list_InstructionList( l1.getTailInstructionList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.InstructionList  tom_get_slice_InstructionList( tom.library.adt.bytecode.types.InstructionList  begin,  tom.library.adt.bytecode.types.InstructionList  end, tom.library.adt.bytecode.types.InstructionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyInstructionList()  ||  (end== tom.library.adt.bytecode.types.instructionlist.EmptyInstructionList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.instructionlist.ConsInstructionList.make( begin.getHeadInstructionList() ,( tom.library.adt.bytecode.types.InstructionList )tom_get_slice_InstructionList( begin.getTailInstructionList() ,end,tail)) ;
  }
  
  private static   tom.library.adt.bytecode.types.IntList  tom_append_list_IntList( tom.library.adt.bytecode.types.IntList l1,  tom.library.adt.bytecode.types.IntList  l2) {
    if( l1.isEmptyIntList() ) {
      return l2;
    } else if( l2.isEmptyIntList() ) {
      return l1;
    } else if(  l1.getTailIntList() .isEmptyIntList() ) {
      return  tom.library.adt.bytecode.types.intlist.ConsIntList.make( l1.getHeadIntList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.intlist.ConsIntList.make( l1.getHeadIntList() ,tom_append_list_IntList( l1.getTailIntList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.IntList  tom_get_slice_IntList( tom.library.adt.bytecode.types.IntList  begin,  tom.library.adt.bytecode.types.IntList  end, tom.library.adt.bytecode.types.IntList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyIntList()  ||  (end== tom.library.adt.bytecode.types.intlist.EmptyIntList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.intlist.ConsIntList.make( begin.getHeadIntList() ,( tom.library.adt.bytecode.types.IntList )tom_get_slice_IntList( begin.getTailIntList() ,end,tail)) ;
  }
  
  private static   tom.library.adt.bytecode.types.TryCatchBlockList  tom_append_list_TryCatchBlockList( tom.library.adt.bytecode.types.TryCatchBlockList l1,  tom.library.adt.bytecode.types.TryCatchBlockList  l2) {
    if( l1.isEmptyTryCatchBlockList() ) {
      return l2;
    } else if( l2.isEmptyTryCatchBlockList() ) {
      return l1;
    } else if(  l1.getTailTryCatchBlockList() .isEmptyTryCatchBlockList() ) {
      return  tom.library.adt.bytecode.types.trycatchblocklist.ConsTryCatchBlockList.make( l1.getHeadTryCatchBlockList() ,l2) ;
    } else {
      return  tom.library.adt.bytecode.types.trycatchblocklist.ConsTryCatchBlockList.make( l1.getHeadTryCatchBlockList() ,tom_append_list_TryCatchBlockList( l1.getTailTryCatchBlockList() ,l2)) ;
    }
  }
  private static   tom.library.adt.bytecode.types.TryCatchBlockList  tom_get_slice_TryCatchBlockList( tom.library.adt.bytecode.types.TryCatchBlockList  begin,  tom.library.adt.bytecode.types.TryCatchBlockList  end, tom.library.adt.bytecode.types.TryCatchBlockList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyTryCatchBlockList()  ||  (end== tom.library.adt.bytecode.types.trycatchblocklist.EmptyTryCatchBlockList.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.library.adt.bytecode.types.trycatchblocklist.ConsTryCatchBlockList.make( begin.getHeadTryCatchBlockList() ,( tom.library.adt.bytecode.types.TryCatchBlockList )tom_get_slice_TryCatchBlockList( begin.getTailTryCatchBlockList() ,end,tail)) ;
  }
  public static class BuildLabelMap extends tom.library.sl.AbstractStrategyBasic {
private  java.util.Map  m;
public BuildLabelMap( java.util.Map  m) {
super(( new tom.library.sl.Identity() ));
this.m=m;
}
public  java.util.Map  getm() {
return m;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch641_1= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
if ( (tomMatch641_1 instanceof tom.library.adt.bytecode.types.instruction.Anchor) ) {

m.put(
 tomMatch641_1.getlabel() , getEnvironment().getPosition());


}
}
}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_BuildLabelMap( java.util.Map  t0) { 
return new BuildLabelMap(t0);
}
public static class OneCfg extends tom.library.sl.AbstractStrategyBasic {
private  tom.library.sl.Strategy  s;
private  java.util.Map  m;
public OneCfg( tom.library.sl.Strategy  s,  java.util.Map  m) {
super(( new tom.library.sl.Identity() ));
this.s=s;
this.m=m;
}
public  tom.library.sl.Strategy  gets() {
return s;
}
public  java.util.Map  getm() {
return m;
}
public tom.library.sl.Visitable[] getChildren() {
tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];
stratChilds[0] = super.getChildAt(0);
stratChilds[1] = gets();
return stratChilds;}
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
case 1: s = ( tom.library.sl.Strategy )child; return this;
default: throw new IndexOutOfBoundsException();
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch642_1= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
if ( (tomMatch642_1 instanceof tom.library.adt.bytecode.types.instruction.Goto) ) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
 tomMatch642_1.getlabel() ));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
} else {
getEnvironment().followPathLocal(current.sub(p));
}
return (InstructionList) getEnvironment().getSubject();


}
}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch642_7= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
boolean tomMatch642_12= false ;
 tom.library.adt.bytecode.types.LabelNode  tomMatch642_10= null ;
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifeq) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifne) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Iflt) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifge) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifgt) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifle) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpeq) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpne) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmplt) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpge) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpgt) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmple) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_acmpeq) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.If_acmpne) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Jsr) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifnull) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

}
} else {
if ( (tomMatch642_7 instanceof tom.library.adt.bytecode.types.instruction.Ifnonnull) ) {
{
tomMatch642_12= true ;
tomMatch642_10= tomMatch642_7.getlabel() ;

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
}
}
}
}
}
}
}
}
if (tomMatch642_12) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
tomMatch642_10));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
return (InstructionList) getEnvironment().getSubject();
} else {
getEnvironment().followPathLocal(current.sub(p));
}


}

}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch642_14= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
boolean tomMatch642_20= false ;
 tom.library.adt.bytecode.types.LabelNode  tomMatch642_17= null ;
 tom.library.adt.bytecode.types.LabelNodeList  tomMatch642_18= null ;
if ( (tomMatch642_14 instanceof tom.library.adt.bytecode.types.instruction.Tableswitch) ) {
{
tomMatch642_20= true ;
tomMatch642_17= tomMatch642_14.getdflt() ;
tomMatch642_18= tomMatch642_14.getlabels() ;

}
} else {
if ( (tomMatch642_14 instanceof tom.library.adt.bytecode.types.instruction.Lookupswitch) ) {
{
tomMatch642_20= true ;
tomMatch642_17= tomMatch642_14.getdflt() ;
tomMatch642_18= tomMatch642_14.getlabels() ;

}
}
}
if (tomMatch642_20) {

LabelNodeList labelList = 
tomMatch642_18;

{
{
if ( (labelList instanceof tom.library.adt.bytecode.types.LabelNodeList) ) {
if ( (((( tom.library.adt.bytecode.types.LabelNodeList )labelList) instanceof tom.library.adt.bytecode.types.labelnodelist.ConsLabelNodeList) || ((( tom.library.adt.bytecode.types.LabelNodeList )labelList) instanceof tom.library.adt.bytecode.types.labelnodelist.EmptyLabelNodeList)) ) {
 tom.library.adt.bytecode.types.LabelNodeList  tomMatch643__end__4=(( tom.library.adt.bytecode.types.LabelNodeList )labelList);
do {
{
if (!( tomMatch643__end__4.isEmptyLabelNodeList() )) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
 tomMatch643__end__4.getHeadLabelNodeList() ));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
return (InstructionList) getEnvironment().getSubject();
} else {
getEnvironment().followPathLocal(current.sub(p));
}


}
if ( tomMatch643__end__4.isEmptyLabelNodeList() ) {
tomMatch643__end__4=(( tom.library.adt.bytecode.types.LabelNodeList )labelList);
} else {
tomMatch643__end__4= tomMatch643__end__4.getTailLabelNodeList() ;
}

}
} while(!( (tomMatch643__end__4==(( tom.library.adt.bytecode.types.LabelNodeList )labelList)) ));
}
}

}

}

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
tomMatch642_17));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
return (InstructionList) getEnvironment().getSubject();
} else {
getEnvironment().followPathLocal(current.sub(p));
}



}

}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.InstructionList  tomMatch642_23= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getTailInstructionList() ;
boolean tomMatch642_26= false ;
if ( ((tomMatch642_23 instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) || (tomMatch642_23 instanceof tom.library.adt.bytecode.types.instructionlist.EmptyInstructionList)) ) {
if ( tomMatch642_23.isEmptyInstructionList() ) {
tomMatch642_26= true ;
}
}
if (!(tomMatch642_26)) {

getEnvironment().down(2);
s.visit(getEnvironment());
getEnvironment().up();

}

}
}

}


}
return _visit_InstructionList(tom__arg,introspector);

}
}
public static class AllCfg extends tom.library.sl.AbstractStrategyBasic {
private  tom.library.sl.Strategy  s;
private  java.util.Map  m;
public AllCfg( tom.library.sl.Strategy  s,  java.util.Map  m) {
super(( new tom.library.sl.Identity() ));
this.s=s;
this.m=m;
}
public  tom.library.sl.Strategy  gets() {
return s;
}
public  java.util.Map  getm() {
return m;
}
public tom.library.sl.Visitable[] getChildren() {
tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];
stratChilds[0] = super.getChildAt(0);
stratChilds[1] = gets();
return stratChilds;}
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
case 1: s = ( tom.library.sl.Strategy )child; return this;
default: throw new IndexOutOfBoundsException();
}
}
@SuppressWarnings("unchecked")
public <T> T visitLight(T v, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch644_1= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
if ( (tomMatch644_1 instanceof tom.library.adt.bytecode.types.instruction.Goto) ) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
 tomMatch644_1.getlabel() ));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
} else {
getEnvironment().followPathLocal(current.sub(p));
}         
return (InstructionList) getEnvironment().getSubject();


}
}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch644_7= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
boolean tomMatch644_12= false ;
 tom.library.adt.bytecode.types.LabelNode  tomMatch644_10= null ;
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifeq) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifne) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Iflt) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifge) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifgt) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifle) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpeq) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpne) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmplt) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpge) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmpgt) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_icmple) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_acmpeq) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.If_acmpne) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Jsr) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifnull) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

}
} else {
if ( (tomMatch644_7 instanceof tom.library.adt.bytecode.types.instruction.Ifnonnull) ) {
{
tomMatch644_12= true ;
tomMatch644_10= tomMatch644_7.getlabel() ;

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
}
}
}
}
}
}
}
}
if (tomMatch644_12) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
tomMatch644_10));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
} else {
getEnvironment().followPathLocal(current.sub(p));
}         


}

}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.Instruction  tomMatch644_14= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() ;
boolean tomMatch644_20= false ;
 tom.library.adt.bytecode.types.LabelNodeList  tomMatch644_18= null ;
 tom.library.adt.bytecode.types.LabelNode  tomMatch644_17= null ;
if ( (tomMatch644_14 instanceof tom.library.adt.bytecode.types.instruction.Tableswitch) ) {
{
tomMatch644_20= true ;
tomMatch644_17= tomMatch644_14.getdflt() ;
tomMatch644_18= tomMatch644_14.getlabels() ;

}
} else {
if ( (tomMatch644_14 instanceof tom.library.adt.bytecode.types.instruction.Lookupswitch) ) {
{
tomMatch644_20= true ;
tomMatch644_17= tomMatch644_14.getdflt() ;
tomMatch644_18= tomMatch644_14.getlabels() ;

}
}
}
if (tomMatch644_20) {
 tom.library.adt.bytecode.types.LabelNodeList  tom_labels=tomMatch644_18;

LabelNodeList labels = 
tom_labels;

{
{
if ( (tom_labels instanceof tom.library.adt.bytecode.types.LabelNodeList) ) {
if ( (((( tom.library.adt.bytecode.types.LabelNodeList )tom_labels) instanceof tom.library.adt.bytecode.types.labelnodelist.ConsLabelNodeList) || ((( tom.library.adt.bytecode.types.LabelNodeList )tom_labels) instanceof tom.library.adt.bytecode.types.labelnodelist.EmptyLabelNodeList)) ) {
 tom.library.adt.bytecode.types.LabelNodeList  tomMatch645__end__4=(( tom.library.adt.bytecode.types.LabelNodeList )tom_labels);
do {
{
if (!( tomMatch645__end__4.isEmptyLabelNodeList() )) {

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
 tomMatch645__end__4.getHeadLabelNodeList() ));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(current.sub(p));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
} else {
getEnvironment().followPathLocal(current.sub(p));
}     


}
if ( tomMatch645__end__4.isEmptyLabelNodeList() ) {
tomMatch645__end__4=(( tom.library.adt.bytecode.types.LabelNodeList )tom_labels);
} else {
tomMatch645__end__4= tomMatch645__end__4.getTailLabelNodeList() ;
}

}
} while(!( (tomMatch645__end__4==(( tom.library.adt.bytecode.types.LabelNodeList )tom_labels)) ));
}
}

}

}

tom.library.sl.Position p = (tom.library.sl.Position) (m.get(
tomMatch644_17));
tom.library.sl.Position current = getEnvironment().getPosition();
getEnvironment().followPath(p.sub(current));
s.visit(getEnvironment());
if(getEnvironment().getStatus() == Environment.SUCCESS) {
getEnvironment().followPath(current.sub(p));
} else {
getEnvironment().followPathLocal(current.sub(p));
}


}

}
}

}
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {
 tom.library.adt.bytecode.types.InstructionList  tomMatch644_23= (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getTailInstructionList() ;
boolean tomMatch644_26= false ;
if ( ((tomMatch644_23 instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) || (tomMatch644_23 instanceof tom.library.adt.bytecode.types.instructionlist.EmptyInstructionList)) ) {
if ( tomMatch644_23.isEmptyInstructionList() ) {
tomMatch644_26= true ;
}
}
if (!(tomMatch644_26)) {

getEnvironment().down(2);
s.visit(getEnvironment());
getEnvironment().up();

}

}
}

}


}
return _visit_InstructionList(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_AllCfg( tom.library.sl.Strategy  t0,  java.util.Map  t1) { 
return new AllCfg(t0,t1);
}
public static class Mark extends tom.library.sl.AbstractStrategyBasic {
private  java.util.Map  map;
public Mark( java.util.Map  map) {
super(( new tom.library.sl.Identity() ));
this.map=map;
}
public  java.util.Map  getmap() {
return map;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
 tom.library.adt.bytecode.types.InstructionList  tom_c=(( tom.library.adt.bytecode.types.InstructionList )tom__arg);

Object o = map.get(
tom_c);
int value = 1;
if(o != null)
value = ((Integer)o).intValue() + 1;
map.put(
tom_c, Integer.valueOf(value));


}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
public static class UnMark extends tom.library.sl.AbstractStrategyBasic {
private  java.util.Map  map;
public UnMark( java.util.Map  map) {
super(( new tom.library.sl.Identity() ));
this.map=map;
}
public  java.util.Map  getmap() {
return map;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
 tom.library.adt.bytecode.types.InstructionList  tom_c=(( tom.library.adt.bytecode.types.InstructionList )tom__arg);

Object o = map.get(
tom_c);
if(o == null) {
throw new tom.library.sl.VisitFailure();
}
int value = ((Integer)o).intValue() - 1;
map.put(
tom_c, Integer.valueOf(value));


}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
public static class IsMarked extends tom.library.sl.AbstractStrategyBasic {
private  java.util.Map  map;
public IsMarked( java.util.Map  map) {
super(( new tom.library.sl.Identity() ));
this.map=map;
}
public  java.util.Map  getmap() {
return map;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {

Object o = map.get(
(( tom.library.adt.bytecode.types.InstructionList )tom__arg));
if(o == null || ((Integer)o).intValue() <= 0)
throw new tom.library.sl.VisitFailure();


}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}


/**
* Returns the dot node id of the given InstructionList.
* @param ins the instruction.
* @return the id.
*/
private static String getDotId(InstructionList ins) {
return ("insid" + ins.hashCode()).replace('-', 'm');
}

/**
* Returns the dot node id of the given TryCatchBlock.
* @param bl the try/catch block.
* @return the id.
*/
private static String getDotId(TryCatchBlock bl) {
return ("blockid" + bl.hashCode()).replace('-', 'm');
}

/**
* Returns the dot node id of the given LocalVariable.
* @param the local variable.
* @return the id.
*/
private static String getDotId(LocalVariable var) {
return ("varid" + var.hashCode()).replace('-', 'm');
}

/**
* Cleans the given string to prevent dot compilation problems.
* (ex: replace the character '"' with the string "\"").
* @param s the string to be cleaned.
* @return the cleaned string.
*/
private static String clean(String s) {
return s.replaceAll("\\\"", "\\\\\\\"");
}

/**
* Prints the current instruction node with a suitable label.
* @param out the writer to be used for the dot output.
*/

public static class PrintDotNode extends tom.library.sl.AbstractStrategyBasic {
private java.io.Writer out;
public PrintDotNode(java.io.Writer out) {
super(( new tom.library.sl.Identity() ));
this.out=out;
}
public java.io.Writer getout() {
return out;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {

String id = getDotId(
(( tom.library.adt.bytecode.types.InstructionList )tom__arg));
printDotInstruction(
 (( tom.library.adt.bytecode.types.InstructionList )tom__arg).getHeadInstructionList() , id, out);


}
}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_PrintDotNode(java.io.Writer t0) { 
return new PrintDotNode(t0);
}


/**
* Prints the given instruction with a suitable label and id.
* @param ins the instruction to be printed.
* @param id the id of the dot node.
* @param out the writer to be used for the dot output.
*/
private static void printDotInstruction(Instruction ins, String id, Writer out) {
try {

{
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
boolean tomMatch650_3= false ;
 int  tomMatch650_1= 0 ;
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Bipush) ) {
{
tomMatch650_3= true ;
tomMatch650_1= (( tom.library.adt.bytecode.types.Instruction )ins).getoperand() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Sipush) ) {
{
tomMatch650_3= true ;
tomMatch650_1= (( tom.library.adt.bytecode.types.Instruction )ins).getoperand() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Newarray) ) {
{
tomMatch650_3= true ;
tomMatch650_1= (( tom.library.adt.bytecode.types.Instruction )ins).getoperand() ;

}
}
}
}
if (tomMatch650_3) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\noperand : "+Integer.toString(
tomMatch650_1)+
"\"];\n              ");
return;


}

}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Multianewarray) ) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\ntypeDesc : "+
 (( tom.library.adt.bytecode.types.Instruction )ins).gettypeDesc() +
"\\ndims : "+Integer.toString(
 (( tom.library.adt.bytecode.types.Instruction )ins).getdims() )+
"\"];\n              ");
return;


}
}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Ldc) ) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\ncst : "+clean(
 (( tom.library.adt.bytecode.types.Instruction )ins).getcst() .toString())+
"\"];\n              ");
return;


}
}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
boolean tomMatch650_14= false ;
 int  tomMatch650_12= 0 ;
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Iload) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Lload) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Fload) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Dload) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Aload) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Istore) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Lstore) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Fstore) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Dstore) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Astore) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Ret) ) {
{
tomMatch650_14= true ;
tomMatch650_12= (( tom.library.adt.bytecode.types.Instruction )ins).getvar() ;

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
}
}
if (tomMatch650_14) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nvar : "+Integer.toString(
tomMatch650_12)+
"\"];\n              ");
return;


}

}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Iinc) ) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nincr : "+Integer.toString(
 (( tom.library.adt.bytecode.types.Instruction )ins).getincr() )+
"\\nvar : "+Integer.toString(
 (( tom.library.adt.bytecode.types.Instruction )ins).getvar() )+
"\"];\n              ");
return;


}
}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Tableswitch) ) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nmin : "+
 (( tom.library.adt.bytecode.types.Instruction )ins).getmin() +
"\\nmax : "+
 (( tom.library.adt.bytecode.types.Instruction )ins).getmax() +
"\"];\n              ");
return;


}
}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Lookupswitch) ) {
 tom.library.adt.bytecode.types.IntList  tom_keys= (( tom.library.adt.bytecode.types.Instruction )ins).getkeys() ;

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nkeys : ");
IntList keys = 
tom_keys;

{
{
if ( (tom_keys instanceof tom.library.adt.bytecode.types.IntList) ) {
if ( (((( tom.library.adt.bytecode.types.IntList )tom_keys) instanceof tom.library.adt.bytecode.types.intlist.ConsIntList) || ((( tom.library.adt.bytecode.types.IntList )tom_keys) instanceof tom.library.adt.bytecode.types.intlist.EmptyIntList)) ) {
 tom.library.adt.bytecode.types.IntList  tomMatch651__end__4=(( tom.library.adt.bytecode.types.IntList )tom_keys);
do {
{
if (!( tomMatch651__end__4.isEmptyIntList() )) {
 tom.library.adt.bytecode.types.IntList  tomMatch651_5= tomMatch651__end__4.getTailIntList() ;
 tom.library.adt.bytecode.types.IntList  tomMatch651__end__8=tomMatch651_5;
do {
{
if (!( tomMatch651__end__8.isEmptyIntList() )) {
if (  tomMatch651__end__8.getTailIntList() .isEmptyIntList() ) {

out.write(
""+Integer.toString(
 tomMatch651__end__4.getHeadIntList() )+
", ");


}
}
if ( tomMatch651__end__8.isEmptyIntList() ) {
tomMatch651__end__8=tomMatch651_5;
} else {
tomMatch651__end__8= tomMatch651__end__8.getTailIntList() ;
}

}
} while(!( (tomMatch651__end__8==tomMatch651_5) ));
}
if ( tomMatch651__end__4.isEmptyIntList() ) {
tomMatch651__end__4=(( tom.library.adt.bytecode.types.IntList )tom_keys);
} else {
tomMatch651__end__4= tomMatch651__end__4.getTailIntList() ;
}

}
} while(!( (tomMatch651__end__4==(( tom.library.adt.bytecode.types.IntList )tom_keys)) ));
}
}

}
{
if ( (tom_keys instanceof tom.library.adt.bytecode.types.IntList) ) {
if ( (((( tom.library.adt.bytecode.types.IntList )tom_keys) instanceof tom.library.adt.bytecode.types.intlist.ConsIntList) || ((( tom.library.adt.bytecode.types.IntList )tom_keys) instanceof tom.library.adt.bytecode.types.intlist.EmptyIntList)) ) {
 tom.library.adt.bytecode.types.IntList  tomMatch651__end__14=(( tom.library.adt.bytecode.types.IntList )tom_keys);
do {
{
if (!( tomMatch651__end__14.isEmptyIntList() )) {
if (  tomMatch651__end__14.getTailIntList() .isEmptyIntList() ) {

out.write(Integer.toString(
 tomMatch651__end__14.getHeadIntList() ));


}
}
if ( tomMatch651__end__14.isEmptyIntList() ) {
tomMatch651__end__14=(( tom.library.adt.bytecode.types.IntList )tom_keys);
} else {
tomMatch651__end__14= tomMatch651__end__14.getTailIntList() ;
}

}
} while(!( (tomMatch651__end__14==(( tom.library.adt.bytecode.types.IntList )tom_keys)) ));
}
}

}


}

out.write(
"\"];");
return;


}
}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
boolean tomMatch650_31= false ;
 tom.library.adt.bytecode.types.FieldDescriptor  tomMatch650_29= null ;
 String  tomMatch650_28= "" ;
 String  tomMatch650_27= "" ;
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Getstatic) ) {
{
tomMatch650_31= true ;
tomMatch650_27= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_28= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_29= (( tom.library.adt.bytecode.types.Instruction )ins).getfieldDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Putstatic) ) {
{
tomMatch650_31= true ;
tomMatch650_27= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_28= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_29= (( tom.library.adt.bytecode.types.Instruction )ins).getfieldDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Getfield) ) {
{
tomMatch650_31= true ;
tomMatch650_27= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_28= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_29= (( tom.library.adt.bytecode.types.Instruction )ins).getfieldDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Putfield) ) {
{
tomMatch650_31= true ;
tomMatch650_27= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_28= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_29= (( tom.library.adt.bytecode.types.Instruction )ins).getfieldDesc() ;

}
}
}
}
}
if (tomMatch650_31) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nowner : "+
tomMatch650_27+
"\\nname : "+
tomMatch650_28+
"\\ndescriptor : "+ToolBox.buildDescriptor(
tomMatch650_29)+
"\"];\n              ");
return;


}

}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
boolean tomMatch650_37= false ;
 String  tomMatch650_34= "" ;
 String  tomMatch650_33= "" ;
 tom.library.adt.bytecode.types.MethodDescriptor  tomMatch650_35= null ;
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Invokevirtual) ) {
{
tomMatch650_37= true ;
tomMatch650_33= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_34= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_35= (( tom.library.adt.bytecode.types.Instruction )ins).getmethodDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Invokespecial) ) {
{
tomMatch650_37= true ;
tomMatch650_33= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_34= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_35= (( tom.library.adt.bytecode.types.Instruction )ins).getmethodDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Invokestatic) ) {
{
tomMatch650_37= true ;
tomMatch650_33= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_34= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_35= (( tom.library.adt.bytecode.types.Instruction )ins).getmethodDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Invokeinterface) ) {
{
tomMatch650_37= true ;
tomMatch650_33= (( tom.library.adt.bytecode.types.Instruction )ins).getowner() ;
tomMatch650_34= (( tom.library.adt.bytecode.types.Instruction )ins).getname() ;
tomMatch650_35= (( tom.library.adt.bytecode.types.Instruction )ins).getmethodDesc() ;

}
}
}
}
}
if (tomMatch650_37) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\nowner : "+
tomMatch650_33+
"\\nname : "+
tomMatch650_34+
"\\ndescriptor : "+ToolBox.buildDescriptor(
tomMatch650_35)+
"\"];\n              ");
return;


}

}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {
boolean tomMatch650_41= false ;
 String  tomMatch650_39= "" ;
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.New) ) {
{
tomMatch650_41= true ;
tomMatch650_39= (( tom.library.adt.bytecode.types.Instruction )ins).gettypeDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Anewarray) ) {
{
tomMatch650_41= true ;
tomMatch650_39= (( tom.library.adt.bytecode.types.Instruction )ins).gettypeDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Checkcast) ) {
{
tomMatch650_41= true ;
tomMatch650_39= (( tom.library.adt.bytecode.types.Instruction )ins).gettypeDesc() ;

}
} else {
if ( ((( tom.library.adt.bytecode.types.Instruction )ins) instanceof tom.library.adt.bytecode.types.instruction.Instanceof) ) {
{
tomMatch650_41= true ;
tomMatch650_39= (( tom.library.adt.bytecode.types.Instruction )ins).gettypeDesc() ;

}
}
}
}
}
if (tomMatch650_41) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\\ndescriptor : "+
tomMatch650_39+
"\"];\n              ");
return;


}

}

}
{
if ( (ins instanceof tom.library.adt.bytecode.types.Instruction) ) {

out.write(
"\n              "+id+
" [label=\""+ins.symbolName()+
"\"];\n              ");


}

}


}

} catch(IOException e) {
e.printStackTrace();
}
}

/**
* Prints a link from the `parent' instruction to the current node instruction.
* @param out the writer to be used for the dot output.
*/

public static class PrintDotLink extends tom.library.sl.AbstractStrategyBasic {
private java.io.Writer out;
private  InsWrapper  parent;
public PrintDotLink(java.io.Writer out,  InsWrapper  parent) {
super(( new tom.library.sl.Identity() ));
this.out=out;
this.parent=parent;
}
public java.io.Writer getout() {
return out;
}
public  InsWrapper  getparent() {
return parent;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
if ( ((( tom.library.adt.bytecode.types.InstructionList )tom__arg) instanceof tom.library.adt.bytecode.types.instructionlist.ConsInstructionList) ) {

try {
out.write(
""+getDotId(parent.ins)+
" -> "+getDotId(
(( tom.library.adt.bytecode.types.InstructionList )tom__arg))+
";\n              ");
} catch(IOException e) {
e.printStackTrace();
}


}
}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_PrintDotLink(java.io.Writer t0,  InsWrapper  t1) { 
return new PrintDotLink(t0,t1);
}


/**
* Prints all the try/catch/finally informations of the given block list.
* @param list the try/catch/finally blocks to be printed.
* @param labelMap the label map (see the BuildLabelMap strategy).
* @param out the writer to be used for the dot output.
* @param inst the global list of instructions.
*/
private static void printTryCatchBlocks(TryCatchBlockList list, Map labelMap, Writer out,InstructionList inst) throws VisitFailure{

{
{
if ( (list instanceof tom.library.adt.bytecode.types.TryCatchBlockList) ) {
if ( (((( tom.library.adt.bytecode.types.TryCatchBlockList )list) instanceof tom.library.adt.bytecode.types.trycatchblocklist.ConsTryCatchBlockList) || ((( tom.library.adt.bytecode.types.TryCatchBlockList )list) instanceof tom.library.adt.bytecode.types.trycatchblocklist.EmptyTryCatchBlockList)) ) {
 tom.library.adt.bytecode.types.TryCatchBlockList  tomMatch653__end__4=(( tom.library.adt.bytecode.types.TryCatchBlockList )list);
do {
{
if (!( tomMatch653__end__4.isEmptyTryCatchBlockList() )) {

try {
TryCatchBlock block = 
 tomMatch653__end__4.getHeadTryCatchBlockList() ;
Handler handler = block.gethandler();
String id = getDotId(block);


{
{
if ( (handler instanceof tom.library.adt.bytecode.types.Handler) ) {
if ( ((( tom.library.adt.bytecode.types.Handler )handler) instanceof tom.library.adt.bytecode.types.handler.CatchHandler) ) {

Position labelPosition = (Position) labelMap.get(
 (( tom.library.adt.bytecode.types.Handler )handler).gethandler() );
InstructionList labelInst = (InstructionList) labelPosition.getSubterm().visit(inst);
out.write(
"\n                  "+id+
" [label=\"Catch\\ntype : "+
 (( tom.library.adt.bytecode.types.Handler )handler).gettype() +
"\" shape=box];\n                  "+id+
" -> "+getDotId(labelInst)+
" [label=\"handler\" style=dotted];\n                  ");


}
}

}
{
if ( (handler instanceof tom.library.adt.bytecode.types.Handler) ) {
if ( ((( tom.library.adt.bytecode.types.Handler )handler) instanceof tom.library.adt.bytecode.types.handler.FinallyHandler) ) {

Position labelPosition = (Position) labelMap.get(
 (( tom.library.adt.bytecode.types.Handler )handler).gethandler() );
InstructionList labelInst = (InstructionList) labelPosition.getSubterm().visit(inst);
out.write(
"\n                  "+id+
" [label=\"Finally\" shape=box];\n                  "+id+
" -> "+getDotId(labelInst)+
" [label=\"handler\" style=dotted];\n                  ");


}
}

}


}


Position startPosition = (Position) labelMap.get(block.getstart());
InstructionList startInst = (InstructionList) startPosition.getSubterm().visit(inst);
Position endPosition = (Position) labelMap.get(block.getend());
InstructionList lastInst = (InstructionList) endPosition.getSubterm().visit(inst);
out.write(
"\n              "+id+
" -> "+getDotId(startInst)+
" [label=\"start\" style=dotted];\n              "+id+
" -> "+getDotId(lastInst)+
" [label=\"end\" style=dotted];\n              ");
} catch(IOException e) {
e.printStackTrace();
}


}
if ( tomMatch653__end__4.isEmptyTryCatchBlockList() ) {
tomMatch653__end__4=(( tom.library.adt.bytecode.types.TryCatchBlockList )list);
} else {
tomMatch653__end__4= tomMatch653__end__4.getTailTryCatchBlockList() ;
}

}
} while(!( (tomMatch653__end__4==(( tom.library.adt.bytecode.types.TryCatchBlockList )list)) ));
}
}

}

}

}

/**
* Prints all the local variables informations.
* @param list the local variables list to be printed.
* @param labelMap the label map (see the BuildLabelMap strategy).
* @param out the writer to be used for the dot output.
* @param inst the global list of instructions.
*/
private static void printLocalVariables(LocalVariableList list, Map labelMap, Writer out, InstructionList inst) throws VisitFailure {

{
{
if ( (list instanceof tom.library.adt.bytecode.types.LocalVariableList) ) {
if ( (((( tom.library.adt.bytecode.types.LocalVariableList )list) instanceof tom.library.adt.bytecode.types.localvariablelist.ConsLocalVariableList) || ((( tom.library.adt.bytecode.types.LocalVariableList )list) instanceof tom.library.adt.bytecode.types.localvariablelist.EmptyLocalVariableList)) ) {
 tom.library.adt.bytecode.types.LocalVariableList  tomMatch655__end__4=(( tom.library.adt.bytecode.types.LocalVariableList )list);
do {
{
if (!( tomMatch655__end__4.isEmptyLocalVariableList() )) {

try {
LocalVariable var = 
 tomMatch655__end__4.getHeadLocalVariableList() ;
String id = getDotId(var);
Position startPosition = (Position) labelMap.get(var.getstart());
InstructionList startInst = (InstructionList) startPosition.getSubterm().visit(inst);
Position endPosition = (Position) labelMap.get(var.getend());
InstructionList lastInst = (InstructionList) endPosition.getSubterm().visit(inst);

out.write(
"\n              "+id+
" [label=\"var : "+var.getname()+
"\\ndescriptor : "+var.gettypeDesc()+
"\\nindex : "+Integer.toString(var.getindex())+
"\" shape=box];\n              "+id+
" -> "+getDotId(startInst)+
" [label=\"start\" style=dotted];\n              "+id+
" -> "+getDotId(lastInst)+
" [label=\"end\" style=dotted];\n              ");
} catch(IOException e) {
e.printStackTrace();
}


}
if ( tomMatch655__end__4.isEmptyLocalVariableList() ) {
tomMatch655__end__4=(( tom.library.adt.bytecode.types.LocalVariableList )list);
} else {
tomMatch655__end__4= tomMatch655__end__4.getTailLocalVariableList() ;
}

}
} while(!( (tomMatch655__end__4==(( tom.library.adt.bytecode.types.LocalVariableList )list)) ));
}
}

}

}

}

/**
* Used to pass the stored instruction as a strategy parameter.
*/
private static class InsWrapper { public InstructionList ins; }



/**
* Assign the current instruction node to the given InsWrapper.
* @param ins the instruction wrapper.
*/

public static class Assign extends tom.library.sl.AbstractStrategyBasic {
private  InsWrapper  ins;
public Assign( InsWrapper  ins) {
super(( new tom.library.sl.Identity() ));
this.ins=ins;
}
public  InsWrapper  getins() {
return ins;
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
if ( (v instanceof tom.library.adt.bytecode.types.InstructionList) ) {
return ((T)visit_InstructionList((( tom.library.adt.bytecode.types.InstructionList )v),introspector));
}
if (!(( null  == environment))) {
return ((T)any.visit(environment,introspector));
} else {
return any.visitLight(v,introspector);
}

}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  _visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
if (!(( null  == environment))) {
return (( tom.library.adt.bytecode.types.InstructionList )any.visit(environment,introspector));
} else {
return any.visitLight(arg,introspector);
}
}
@SuppressWarnings("unchecked")
public  tom.library.adt.bytecode.types.InstructionList  visit_InstructionList( tom.library.adt.bytecode.types.InstructionList  tom__arg, tom.library.sl.Introspector introspector)
 throws tom.library.sl.VisitFailure {
{
{
if ( (tom__arg instanceof tom.library.adt.bytecode.types.InstructionList) ) {
ins.ins = 
(( tom.library.adt.bytecode.types.InstructionList )tom__arg); 

}

}

}
return _visit_InstructionList(tom__arg,introspector);

}
}
private static  tom.library.sl.Strategy  tom_make_Assign( InsWrapper  t0) { 
return new Assign(t0);
}


/**
* Generates a control flow graph for each method of the given class.
* @param ast the gom-term subject representing the class.
*/
public static void classToDot(ClassNode ast) throws VisitFailure {
Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
MethodList methods = ast.getmethods();

{
{
if ( (methods instanceof tom.library.adt.bytecode.types.MethodList) ) {
if ( (((( tom.library.adt.bytecode.types.MethodList )methods) instanceof tom.library.adt.bytecode.types.methodlist.ConsMethodList) || ((( tom.library.adt.bytecode.types.MethodList )methods) instanceof tom.library.adt.bytecode.types.methodlist.EmptyMethodList)) ) {
 tom.library.adt.bytecode.types.MethodList  tomMatch657__end__4=(( tom.library.adt.bytecode.types.MethodList )methods);
do {
{
if (!( tomMatch657__end__4.isEmptyMethodList() )) {
 tom.library.adt.bytecode.types.Method  tom_x= tomMatch657__end__4.getHeadMethodList() ;

try {
MethodInfo info = 
tom_x.getinfo();
w.write(
"digraph "+info.getname()+
" {\n              ");

// Print a root node with the method name and descriptor. Add a link to the first instruction if any.
w.write(
"method [label=\"method : "+info.getname()+
"\\ndescriptor : "+ToolBox.buildDescriptor(info.getdesc())+
"\" shape=box];\n              ");
if(!
tom_x.getcode().isEmptyCode()) {
InstructionList ins = 
tom_x.getcode().getinstructions();
if(!ins.isEmptyInstructionList()) {
w.write(
"method -> "+getDotId(ins)+
"\n                  ");
}

// Compute the label map to allow us to retrieve an instruction from a label.
HashMap labelMap = new HashMap();

tom_make_TopDown(tom_make_BuildLabelMap(labelMap)).visit(ins);

// Create a wrapper to pass a parent node to its children.
InsWrapper insWrapper = new InsWrapper();

// This strategy run through all node. For each of them, the node is printed.
// Links between the current node and its children are printed by passing the parent to each of them.
// AllCfg allows us to get all the children of the current node.
Strategy toDot = 
tom_make_TopDown(tom_make_Try( tom.library.sl.Sequence.make(tom_make_PrintDotNode(w), tom.library.sl.Sequence.make( tom.library.sl.Sequence.make(tom_make_Assign(insWrapper), tom.library.sl.Sequence.make(tom_make_AllCfg(tom_make_PrintDotLink(w,insWrapper),labelMap), null ) ) , null ) ) ));

toDot.visit(ins);

// Prints the try/catch/finally blocks.
printTryCatchBlocks(
tom_x.getcode().gettryCatchBlocks(), labelMap, w, ins);

// Prints the local variables informations.
printLocalVariables(
tom_x.getcode().getlocalVariables(), labelMap, w, ins);
}

w.write("}\n");
w.flush();
} catch(IOException e) {
e.printStackTrace();
}


}
if ( tomMatch657__end__4.isEmptyMethodList() ) {
tomMatch657__end__4=(( tom.library.adt.bytecode.types.MethodList )methods);
} else {
tomMatch657__end__4= tomMatch657__end__4.getTailMethodList() ;
}

}
} while(!( (tomMatch657__end__4==(( tom.library.adt.bytecode.types.MethodList )methods)) ));
}
}

}

}

}

/**
* Generates the dot control flow graphs for each method of the specified class.
* Usage : java bytecode.CFGViewer <class name>
* Ex: java bytecode.CFGViewer bytecode.Subject
* @param args args[0] : the class name
*/
public static void main(String[] args) {
if(args.length <= 0) {
System.out.println("Usage : java bytecode.CFGViewer <class name>\nEx: java bytecode.CFGViewer MyClass");
return;
}
BytecodeReader cg = new BytecodeReader(args[0]);
ClassNode c = cg.getAst();
try {
classToDot(c);
} catch (VisitFailure e) {
System.out.println("Unexpected failure in strategies");
}
}

}
