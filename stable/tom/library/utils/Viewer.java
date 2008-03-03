/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
 * Copyright (c) 2004-2008, INRIA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *  - Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  - Neither the name of the INRIA nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
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
package tom.library.utils;

import java.io.*;
import tom.library.sl.*;
import java.util.Stack;
import java.util.Vector;
import aterm.pure.PureFactory;
import att.grappa.*;
import javax.swing.*;
import java.awt.event.*;

public class Viewer {

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */ /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */     private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )) == null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Choice) )) {       if(( ((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):( null )) ==null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1):new tom.library.sl.Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):( null )),l2)==null)?((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1):new tom.library.sl.Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1),tom_append_list_Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):( null )),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Choice(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin):new tom.library.sl.Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)) );   }    /* Generated by TOM (version 2.6alpha): Do not edit this file */ /* Generated by TOM (version 2.6alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  v) { return ( ( (( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )==null)?v:new tom.library.sl.Choice(v,( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )) ) ); }private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ) ); }private static  tom.library.sl.Strategy  tom_make_TopDownCollect( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),tom_make_Try(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) ))) ) ); }   /* Generated by TOM (version 2.6alpha): Do not edit this file */  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */    private static   aterm.ATermList  tom_append_list_concATerm( aterm.ATermList l1,  aterm.ATermList  l2) {     if( l1.isEmpty() ) {       return l2;     } else if( l2.isEmpty() ) {       return l1;     } else if(  l1.getNext() .isEmpty() ) {       return  l2.insert( l1.getFirst() ) ;     } else {       return  tom_append_list_concATerm( l1.getNext() ,l2).insert( l1.getFirst() ) ;     }   }   private static   aterm.ATermList  tom_get_slice_concATerm( aterm.ATermList  begin,  aterm.ATermList  end, aterm.ATermList  tail) {     if( begin==end ) {       return tail;     } else if( end==tail  && ( end.isEmpty()  ||  end== aterm.pure.SingletonFactory.getInstance().makeList()  )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  ( aterm.ATermList )tom_get_slice_concATerm( begin.getNext() ,end,tail).insert( begin.getFirst() ) ;   }    



  /* -------- dot part --------- */
  public static void toDot(tom.library.sl.Visitable v, Writer w) 
    throws java.io.IOException {
      w.write("digraph visitable {\nordering=out;");
      try{
        Strategy print = new Print(w);
        tom_make_TopDown(print).visit(v);
      } catch (VisitFailure e) {throw new RuntimeException("unexcepted visit failure");}
      w.write("\n}");
      w.flush();
    }

  public static void toDot(tom.library.sl.Visitable v) {
    try {
      Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
      toDot(v,w);
      w.write('\n');
      w.flush();
    } catch(java.io.IOException e) {}
  }

  



  private static String getNodeFromPos(Position p) {
    int[] omega = p.toArray();
    StringBuilder r = new StringBuilder("p");
    for(int i=0 ; i<p.depth() ; i++) {
      r.append(omega[i]);
      if(i<p.depth()-1) {
        r.append("_");
      }
    }
    return r.toString();
  }

  //TODO: adapt to traverse any data-structure using newsl
  static class Print extends AbstractStrategy {

    protected Writer w;

    public Print(Writer w) {
      initSubterm();
      this.w=w;
    }

    public Object visitLight(Object any, Introspector i) throws VisitFailure {
      throw new VisitFailure();
    } 

    public int visit(Introspector introspector) {
      Visitable v = (Visitable) getEnvironment().getSubject();
      try {
        if (v instanceof Path) {
          Position current = getEnvironment().getPosition();
          Position father = current.up();
          w.write("\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+" [label=\"\"];\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(father)+" -> "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+"; "

);
          Position dest = (Position) current.add((Path)v).getCanonicalPath();
          w.write("\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+" -> "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(dest)+"; "
);
        } else {
          Position current = getEnvironment().getPosition();
          String term = v.toString();
          // in case of wrapped builtin return term in complete
          // else return the name of the constructor
          int end = term.indexOf("(");
          String name = term.substring(0,(end==-1)?term.length():end);
          w.write("\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+" [label=\""/* Generated by TOM (version 2.6alpha): Do not edit this file */+name+"\"]; "
);
          if(!current.equals(new Position(new int[]{}))) {
            Position father = current.up();
            w.write("\n                "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(father)+" -> "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+"; "
);
          }
        }
      } catch(IOException e) {}
      return Environment.SUCCESS;
    }
  }

  public static void display(Visitable vv) {
    final Visitable v = vv;
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame("Viewer");
    frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        synchronized(v){  v.notify(); }}});
    try {
      Runtime rt = Runtime.getRuntime();
      Process pr = rt.exec("dot");
      Writer out = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
      if(v instanceof Strategy) {        
        Viewer.toDot((Strategy)v, out); 
      } else {
        Viewer.toDot(v, out); 
      }
      out.close();
      Parser parser = new Parser(pr.getInputStream());
      parser.parse();
      Graph graph = parser.getGraph();
      GrappaPanel panel = new GrappaPanel(graph);
      panel.setScaleToFit(true);
      frame.getContentPane().add(panel, java.awt.BorderLayout.CENTER);
      frame.pack();
      frame.setVisible(true);
      synchronized(v){  
        v.wait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /* -------- pstree-like part --------- */
  public static void toTree(tom.library.sl.Visitable v) {
    try {
      Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
      toTree(v,w);
      w.write('\n');
      w.flush();
    } catch(java.io.IOException e) {}
  }


  public static void toTree(tom.library.sl.Visitable v, Writer w)
    throws java.io.IOException {
      aterm.ATermFactory atermFactory = new PureFactory();
      aterm.ATerm at = atermFactory.parse(v.toString());
      ATermToTree(at, w, new Stack<Integer>(), 0);
    }

  private static void writeContext(Writer w, Stack<Integer> context, int deep) 
    throws java.io.IOException {
      for(int i=0; i<deep; i++) {
        if (context.contains(i))
          w.write('\u2502');
        else
          w.write(' ');
      }
    }

  private static void ATermToTree(aterm.ATerm term, Writer w, Stack<Integer> context, int deep) 
    throws java.io.IOException {
      {if ( term instanceof aterm.ATerm ) {{  aterm.ATerm  tomMatch557NameNumberfreshSubject_1=(( aterm.ATerm )term);if ( tomMatch557NameNumberfreshSubject_1 instanceof aterm.ATermAppl ) {{  aterm.AFun  tomMatch557NameNumber_freshVar_0= ((aterm.ATermAppl)tomMatch557NameNumberfreshSubject_1).getAFun() ;{  aterm.ATermList  tomMatch557NameNumber_freshVar_1= ((aterm.ATermAppl)tomMatch557NameNumberfreshSubject_1).getArguments() ;if ( tomMatch557NameNumber_freshVar_0 instanceof aterm.AFun ) {{  String  tomMatch557NameNumber_freshVar_2= tomMatch557NameNumber_freshVar_0.getName() ;{  String  tom_name=tomMatch557NameNumber_freshVar_2;{  aterm.ATermList  tom_list=tomMatch557NameNumber_freshVar_1;if ( true ) {

          aterm.ATermAppl a = (aterm.ATermAppl) term;
          if (a.getArity() == 0) {  // no child
            w.write("\u2500"+tom_name);
            return;
          } else if (a.getArity() == 1) {  // only one child
            w.write('\u2500' + tom_name+ "\u2500\u2500");
            deep = deep + tom_name.length() + 3;
            ATermToTree(tom_list.getFirst(),w,context,deep);
            return;
          } else {
            int ndeep = deep + tom_name.length() + 3;
            {if ( tom_list instanceof aterm.ATermList ) {{  aterm.ATermList  tomMatch558NameNumberfreshSubject_1=(( aterm.ATermList )tom_list);if ( tomMatch558NameNumberfreshSubject_1 instanceof aterm.ATermList ) {{  aterm.ATermList  tomMatch558NameNumber_freshVar_0=tomMatch558NameNumberfreshSubject_1;if (!( tomMatch558NameNumber_freshVar_0.isEmpty() )) {{  aterm.ATermList  tomMatch558NameNumber_freshVar_1= tomMatch558NameNumber_freshVar_0.getNext() ;{  aterm.ATermList  tomMatch558NameNumber_begin_3=tomMatch558NameNumber_freshVar_1;{  aterm.ATermList  tomMatch558NameNumber_end_4=tomMatch558NameNumber_freshVar_1;do {{{  aterm.ATermList  tom_l=tom_get_slice_concATerm(tomMatch558NameNumber_begin_3,tomMatch558NameNumber_end_4, aterm.pure.SingletonFactory.getInstance().makeList() );{  aterm.ATermList  tomMatch558NameNumber_freshVar_2=tomMatch558NameNumber_end_4;if (!( tomMatch558NameNumber_freshVar_2.isEmpty() )) {{  aterm.ATermList  tomMatch558NameNumber_freshVar_5= tomMatch558NameNumber_freshVar_2.getNext() ;if ( tomMatch558NameNumber_freshVar_5.isEmpty() ) {if ( true ) {

                // first child
                w.write('\u2500' + tom_name+ "\u2500\u252C");
                context.push(ndeep-1); 
                ATermToTree( tomMatch558NameNumber_freshVar_0.getFirst() ,w,context,ndeep);
                context.pop();
                w.write('\n');

                // 2 ... n-1
                {if ( tom_l instanceof aterm.ATermList ) {{  aterm.ATermList  tomMatch559NameNumberfreshSubject_1=(( aterm.ATermList )tom_l);if ( tomMatch559NameNumberfreshSubject_1 instanceof aterm.ATermList ) {{  aterm.ATermList  tomMatch559NameNumber_freshVar_0=tomMatch559NameNumberfreshSubject_1;{  aterm.ATermList  tomMatch559NameNumber_begin_2=tomMatch559NameNumber_freshVar_0;{  aterm.ATermList  tomMatch559NameNumber_end_3=tomMatch559NameNumber_freshVar_0;do {{{  aterm.ATermList  tomMatch559NameNumber_freshVar_1=tomMatch559NameNumber_end_3;if (!( tomMatch559NameNumber_freshVar_1.isEmpty() )) {{  aterm.ATermList  tomMatch559NameNumber_freshVar_4= tomMatch559NameNumber_freshVar_1.getNext() ;if ( true ) {

                    writeContext(w,context,ndeep-1);
                    w.write("\u251C");
                    context.push(ndeep-1);
                    ATermToTree( tomMatch559NameNumber_freshVar_1.getFirst() ,w,context,ndeep);
                    context.pop();
                    w.write('\n');
                  }}}}if ( tomMatch559NameNumber_end_3.isEmpty() ) {tomMatch559NameNumber_end_3=tomMatch559NameNumber_begin_2;} else {tomMatch559NameNumber_end_3= tomMatch559NameNumber_end_3.getNext() ;}}} while(!( tomMatch559NameNumber_end_3==tomMatch559NameNumber_begin_2 ));}}}}}}}

                // last child
                writeContext(w,context,ndeep-1);
                w.write("\u2514");
                ATermToTree( tomMatch558NameNumber_freshVar_2.getFirst() ,w,context,ndeep);
              }}}}}}if ( tomMatch558NameNumber_end_4.isEmpty() ) {tomMatch558NameNumber_end_4=tomMatch558NameNumber_begin_3;} else {tomMatch558NameNumber_end_4= tomMatch558NameNumber_end_4.getNext() ;}}} while(!( tomMatch558NameNumber_end_4==tomMatch558NameNumber_begin_3 ));}}}}}}}}}

          }
        }}}}}}}}}}}

    }


  /* -------- strategy part --------- */
  private static int counter = 0;  

  static private String clean(String s) {
    s = s.replace('.','_');
    s = s.replace('$','_');
    s = s.replace('@','_');
    return s;
  }

  public static void 
    toDot(Strategy subj, Writer w) throws IOException {
      Mu.expand(subj);
      try{
        subj = (Strategy) tom_make_TopDownCollect(tom_make_RemoveMu()).visit(subj);
        w.write("digraph strategy {\nordering=out;");
        Strategy print = new PrintStrategy(w);
        tom_make_TopDownCollect(print).visit(subj);
      } catch (VisitFailure e) {throw new RuntimeException("unexcepted visit failure");}
      w.write("\n}");
      w.flush();
    }

  private static class RemoveMu extends tom.library.sl.BasicStrategy {public RemoveMu() { super(( new tom.library.sl.Identity() ));}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.library.sl.Strategy  visit_Strategy( tom.library.sl.Strategy  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{if ( (tom__arg instanceof tom.library.sl.Strategy) ) {{  tom.library.sl.Strategy  tomMatch560NameNumberfreshSubject_1=(( tom.library.sl.Strategy )tom__arg);if (( (tomMatch560NameNumberfreshSubject_1 instanceof tom.library.sl.Mu) )) {{  tom.library.sl.Strategy  tomMatch560NameNumber_freshVar_0=( (tom.library.sl.Strategy)tomMatch560NameNumberfreshSubject_1.getChildAt(tom.library.sl.Mu.V) );if ( true ) {


        return tomMatch560NameNumber_freshVar_0;
      }}}}}if ( (tom__arg instanceof tom.library.sl.Strategy) ) {{  tom.library.sl.Strategy  tomMatch560NameNumberfreshSubject_1=(( tom.library.sl.Strategy )tom__arg);if ( true ) {

        if (getEnvironment().getCurrentStack().contains(getEnvironment().getSubject())) {
          //corresponds to a pointer due to MuVar
          throw new VisitFailure();
        }
      }}}}return _visit_Strategy(tom__arg,introspector); }public  tom.library.sl.Strategy  _visit_Strategy( tom.library.sl.Strategy  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!( environment== null  )) {return (( tom.library.sl.Strategy )any.visit(environment,introspector));} else {return (( tom.library.sl.Strategy )any.visitLight(arg,introspector));} }public Object visitLight(Object v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.library.sl.Strategy) ) {return visit_Strategy((( tom.library.sl.Strategy )v),introspector);}if (!( environment== null  )) {return any.visit(environment,introspector);} else {return any.visitLight(v,introspector);} }}private static  tom.library.sl.Strategy  tom_make_RemoveMu() { return new RemoveMu(); }



  public static void toDot(tom.library.sl.Strategy s) {
    try {
      Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
      toDot(s,w);
      w.write('\n');
      w.flush();
    } catch(java.io.IOException e) {}
  }


  static class PrintStrategy extends AbstractStrategy {

    protected Writer w;

    //TODO: adapt with newsl to visit anu data-structures
    public PrintStrategy(Writer w) {
      initSubterm();
      this.w=w;
    }
    
    public Object visitLight(Object any, Introspector i) throws VisitFailure {
      throw new VisitFailure();
    }

    public int visit(Introspector introspector) {
      Visitable v = (Visitable) getEnvironment().getSubject();
      Position current = getEnvironment().getPosition();
      Vector<Object> stack = getEnvironment().getCurrentStack(); 
      try {
        //test if it is a pointer due to an expanded MuVar
        if (stack.contains(v)) {
          int index = stack.indexOf(v);
          Position dest = (Position) current.clone();
          for(int i=current.length();i>index;i--) {
            dest = dest.up();
          } 
          Position father = current.up();
          w.write("\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(father)+" -> "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(dest)+"; "
);
          //fails to prevent loops
          return Environment.FAILURE;
        }
        else {
          String[] tab = v.getClass().getName().split("\\.");
          String name = tab[tab.length-1];
          tab = name.split("\\$");
          name = tab[tab.length-1];
          w.write("\n              "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+" [label=\""/* Generated by TOM (version 2.6alpha): Do not edit this file */+name+"\"]; "
);
          if(stack.size()!=0) {
            Position father = current.up();
            w.write("\n                "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(father)+" -> "/* Generated by TOM (version 2.6alpha): Do not edit this file */+getNodeFromPos(current)+"; "
);
          }
        }
      } catch(java.io.IOException e) {
        return Environment.FAILURE;
      }
      return Environment.SUCCESS;
    }

  }

}

