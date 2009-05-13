/*
 * Copyright (c) 2004-2009, INRIA
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
import java.util.List;
import aterm.pure.PureFactory;
import att.grappa.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Provide tools to view terms, with a graphical browser or with GraphViz.
 */
public class Viewer {

          private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ) == null )) {         return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),l2) );       } else {         return ( (tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):new tom.library.sl.Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ),tom_append_list_Sequence(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end == null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):( null )),end,tail)) );   }      private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Choice) )) {       if(( ( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ) ==null )) {         return ( (l2==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):new tom.library.sl.Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),l2) );       } else {         return ( (tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)==null)?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):new tom.library.sl.Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ),tom_append_list_Choice(( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Choice(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else if( (end.equals(tail))  && (( end ==null ) ||  (end.equals(( null ))) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return ( (( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)==null)?((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin):new tom.library.sl.Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):( null )),end,tail)) );   }     private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  v) { return ( ( (( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )==null)?v:new tom.library.sl.Choice(v,( (( null )==null)?( new tom.library.sl.Identity() ):new tom.library.sl.Choice(( new tom.library.sl.Identity() ),( null )) )) ) );}private static  tom.library.sl.Strategy  tom_make_TopDown( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) )) ) );}private static  tom.library.sl.Strategy  tom_make_TopDownCollect( tom.library.sl.Strategy  v) { return ( ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("_x") ),tom_make_Try(( (( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )==null)?v:new tom.library.sl.Sequence(v,( (( null )==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("_x") )) ),( null )) )) ))) ) );}         private static   aterm.ATermList  tom_append_list_concATerm( aterm.ATermList l1,  aterm.ATermList  l2) {     if( l1.isEmpty() ) {       return l2;     } else if( l2.isEmpty() ) {       return l1;     } else if(  l1.getNext() .isEmpty() ) {       return  l2.insert( l1.getFirst() ) ;     } else {       return  tom_append_list_concATerm( l1.getNext() ,l2).insert( l1.getFirst() ) ;     }   }   private static   aterm.ATermList  tom_get_slice_concATerm( aterm.ATermList  begin,  aterm.ATermList  end, aterm.ATermList  tail) {     if( begin==end ) {       return tail;     } else if( end==tail  && ( end.isEmpty()  ||  end== aterm.pure.SingletonFactory.getInstance().makeList()  )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  ( aterm.ATermList )tom_get_slice_concATerm( begin.getNext() ,end,tail).insert( begin.getFirst() ) ;   }    



  /* -------- dot part --------- */
  /** 
   * Give a dot representation of a visitable term on the writer stream
   * 
   * @param v the visitable term to visualize
   * @param w the writer stream
   * @throws RuntimeException in case of visit failure
   */
  public static void toDot(tom.library.sl.Visitable v, Writer w) 
    throws java.io.IOException {
      if ( v instanceof tom.library.sl.Strategy ) {
        Strategy subj = (tom.library.sl.Strategy) v;
        Mu.expand(subj);
        try{
          subj = (Strategy) tom_make_TopDownCollect(tom_make_RemoveMu()).visit(subj);
          w.write("digraph strategy {\nordering=out;");
          Strategy print = new PrintStrategy(w);
          tom_make_TopDownCollect(print).visit(subj);
        } catch (VisitFailure e) {throw new RuntimeException("unexcepted visit failure");}
        w.write("\n}");
        w.flush();
      } else {
        w.write("digraph visitable {\nordering=out;");
        try{
          Strategy print = new Print(w);
          tom_make_TopDown(print).visit(v);
        } catch (VisitFailure e) {throw new RuntimeException("unexcepted visit failure");}
        w.write("\n}");
        w.flush();
      }
    }

 /** 
  * Give a dot representation of a visitable term on the standard output stream
  * 
  * @param v the visitable term to visualize
  * @throws RuntimeException in case of visit failure
  */
 public static void toDot(tom.library.sl.Visitable v) {
    try {
      Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
      toDot(v,w);
      w.write('\n');
      w.flush();
    } catch(java.io.IOException e) {}
  }

  



  /** 
   * Return a term node from a specified position
   * 
   * @param p position of the node to return
   * @return a string representation of the node at the specified position
   */
  private static String getNodeFromPos(Position p) {
    int[] omega = p.toIntArray();
    StringBuilder r = new StringBuilder("p");
    for(int i=0 ; i<p.length() ; i++) {
      r.append(omega[i]);
      if(i<p.length()-1) {
        r.append("_");
      }
    }
    return r.toString();
  }

  //TODO: adapt to traverse any data-structure using newsl
  static private class Print extends AbstractBasicStrategy {

    protected Writer w;

    public Print(Writer w) {
      super(( new tom.library.sl.Identity() ));
      this.w=w;
    }

    /** 
     * Visits the subject any in a light way by providing the introspector 
     *
     * @param any the subject to visit
     * @param i the introspector
     * @throws VisitFailure if visitLight fails
     */
    public Object visitLight(Object any, Introspector i) throws VisitFailure {
      throw new VisitFailure();
    } 

    /** 
     * Visit the subject by providing an introspector
     * 
     * @param introspector the introspector
     */
    public int visit(Introspector introspector) {
      Visitable v = (Visitable) getEnvironment().getSubject();
      try {
        if (v instanceof Path) {
          Position current = getEnvironment().getPosition();
          Position father = current.up();
          w.write("\n              "+getNodeFromPos(current)+" [label=\"\"];\n              "+getNodeFromPos(father)+" -> "+getNodeFromPos(current)+"; "

);
          Position dest = (Position) current.add((Path)v).getCanonicalPath();
          w.write("\n              "+getNodeFromPos(current)+" -> "+getNodeFromPos(dest)+"; "
);
        } else {
          Position current = getEnvironment().getPosition();
          String term = v.toString();
          // in case of wrapped builtin return term in complete
          // else return the name of the constructor
          int end = term.indexOf("(");
          String name = term.substring(0,(end==-1)?term.length():end);
          w.write("\n              "+getNodeFromPos(current)+" [label=\""+name+"\"]; "
);
          if(!current.equals(Position.make())) {
            Position father = current.up();
            w.write("\n                "+getNodeFromPos(father)+" -> "+getNodeFromPos(current)+"; "
);
          }
        }
      } catch(IOException e) {}
      return Environment.SUCCESS;
    }
  }

  /** 
   * Give a GUI display to visualize a visitable term
   * 
   * @param vv the visitable term to visualize
   */
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
      Viewer.toDot(v, out); 
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
  /** 
   * Give a pstree-like representation of a visitable term on the standard output stream
   * 
   * @param v the visitable term to visualize
   * @throws RuntimeException in case of visit failure
   */
  public static void toTree(tom.library.sl.Visitable v) {
    try {
      Writer w = new BufferedWriter(new OutputStreamWriter(System.out)); 
      toTree(v,w);
      w.write('\n');
      w.flush();
    } catch(java.io.IOException e) {}
  }

  /** 
   * Give a pstree-like representation of a visitable term on the writer stream
   * 
   * @param v the visitable term to visualize
   * @param w the writer stream
   * @throws RuntimeException in case of visit failure
   */
  public static void toTree(tom.library.sl.Visitable v, Writer w)
    throws java.io.IOException {
      aterm.ATermFactory atermFactory = new PureFactory();
      aterm.ATerm at = atermFactory.parse(v.toString());
      ATermToTree(at, w, new Stack<Integer>(), 0);
    }

  private static void writeContext(Writer w, Stack<Integer> context, int deep) 
    throws java.io.IOException {
      for(int i=0; i<deep; i++) {
        if (context.contains(i)) {
          w.write("│");
        } else {
          w.write(' ');
        }
      }
    }

  /** 
   * Return a tree representation of an ATerm, by providing a writer, a context and depth
   * 
   * @param term the ATerm to represent as a tree
   * @param w the writer
   * @param context the context
   * @param deep the depth
   */
  private static void ATermToTree(aterm.ATerm term, Writer w, Stack<Integer> context, int deep) 
    throws java.io.IOException {
      {{if ( term instanceof aterm.ATerm ) {if ( (( aterm.ATerm )term) instanceof aterm.ATermAppl ) { aterm.AFun  tomMatch630NameNumber_freshVar_1= ((aterm.ATermAppl)(( aterm.ATerm )term)).getAFun() ;if ( tomMatch630NameNumber_freshVar_1 instanceof aterm.AFun ) { String  tom_name= tomMatch630NameNumber_freshVar_1.getName() ; aterm.ATermList  tom_list= ((aterm.ATermAppl)(( aterm.ATerm )term)).getArguments() ;

          aterm.ATermAppl a = (aterm.ATermAppl) term;
          if (a.getArity() == 0) {  // no child
            w.write("─"+tom_name);
            return;
          } else if (a.getArity() == 1) {  // only one child
            w.write("─" + tom_name+ "──");
            deep = deep + tom_name.length() + 3;
            ATermToTree(tom_list.getFirst(),w,context,deep);
            return;
          } else {
            int ndeep = deep + tom_name.length() + 3;
            {{if ( tom_list instanceof aterm.ATermList ) {if ( (( aterm.ATermList )tom_list) instanceof aterm.ATermList ) {if (!( (( aterm.ATermList )tom_list).isEmpty() )) { aterm.ATermList  tomMatch631NameNumber_freshVar_2= (( aterm.ATermList )tom_list).getNext() ; aterm.ATermList  tomMatch631NameNumber_end_5=tomMatch631NameNumber_freshVar_2;do {{ aterm.ATermList  tom_l=tom_get_slice_concATerm(tomMatch631NameNumber_freshVar_2,tomMatch631NameNumber_end_5, aterm.pure.SingletonFactory.getInstance().makeList() );if (!( tomMatch631NameNumber_end_5.isEmpty() )) {if (  tomMatch631NameNumber_end_5.getNext() .isEmpty() ) {

                // first child
                w.write("─" + tom_name+ "─┬");
                context.push(ndeep-1); 
                ATermToTree( (( aterm.ATermList )tom_list).getFirst() ,w,context,ndeep);
                context.pop();
                w.write('\n');

                // 2 ... n-1
                {{if ( tom_l instanceof aterm.ATermList ) {if ( (( aterm.ATermList )tom_l) instanceof aterm.ATermList ) { aterm.ATermList  tomMatch632NameNumber_end_4=(( aterm.ATermList )tom_l);do {{if (!( tomMatch632NameNumber_end_4.isEmpty() )) {

                    writeContext(w,context,ndeep-1);
                    w.write("├");
                    context.push(ndeep-1);
                    ATermToTree( tomMatch632NameNumber_end_4.getFirst() ,w,context,ndeep);
                    context.pop();
                    w.write('\n');
                  }if ( tomMatch632NameNumber_end_4.isEmpty() ) {tomMatch632NameNumber_end_4=(( aterm.ATermList )tom_l);} else {tomMatch632NameNumber_end_4= tomMatch632NameNumber_end_4.getNext() ;}}} while(!( tomMatch632NameNumber_end_4==(( aterm.ATermList )tom_l) ));}}}}

                // last child
                writeContext(w,context,ndeep-1);
                w.write("└");
                ATermToTree( tomMatch631NameNumber_end_5.getFirst() ,w,context,ndeep);
              }}if ( tomMatch631NameNumber_end_5.isEmpty() ) {tomMatch631NameNumber_end_5=tomMatch631NameNumber_freshVar_2;} else {tomMatch631NameNumber_end_5= tomMatch631NameNumber_end_5.getNext() ;}}} while(!( tomMatch631NameNumber_end_5==tomMatch631NameNumber_freshVar_2 ));}}}}}

          }
        }}}}}

    }


  /* -------- strategy part --------- */
  private static int counter = 0;  

  /** 
   * Replaces characters '.' '$' and '@' by '_' in a string
   * 
   * @param s the string to process
   * @return a string where '.' '$' and '@' by '_' have been replaced
   */
  static private String clean(String s) {
    s = s.replace('.','_');
    s = s.replace('$','_');
    s = s.replace('@','_');
    return s;
  }

  public static class RemoveMu extends tom.library.sl.AbstractBasicStrategy {public RemoveMu() {super(( new tom.library.sl.Identity() ));}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() {return 1;}public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}@SuppressWarnings("unchecked")public  tom.library.sl.Strategy  visit_Strategy( tom.library.sl.Strategy  tom__arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {{{if ( (tom__arg instanceof tom.library.sl.Strategy) ) {if (( ((( tom.library.sl.Strategy )tom__arg) instanceof tom.library.sl.Mu) )) {


        return ( (tom.library.sl.Strategy)(( tom.library.sl.Strategy )tom__arg).getChildAt(tom.library.sl.Mu.V) );
      }}}{if ( (tom__arg instanceof tom.library.sl.Strategy) ) {

        if (getEnvironment().getCurrentStack().contains(getEnvironment().getSubject())) {
          //corresponds to a pointer due to MuVar
          throw new VisitFailure();
        }
      }}}return _visit_Strategy(tom__arg,introspector); }@SuppressWarnings("unchecked")public  tom.library.sl.Strategy  _visit_Strategy( tom.library.sl.Strategy  arg, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if (!( environment== null  )) {return (( tom.library.sl.Strategy )any.visit(environment,introspector));} else {return any.visitLight(arg,introspector);} }@SuppressWarnings("unchecked")public <T> T visitLight(T v, tom.library.sl.Introspector introspector) throws tom.library.sl.VisitFailure {if ( (v instanceof tom.library.sl.Strategy) ) {return ((T)visit_Strategy((( tom.library.sl.Strategy )v),introspector));}if (!( environment== null  )) {return ((T)any.visit(environment,introspector));} else {return any.visitLight(v,introspector);} }}private static  tom.library.sl.Strategy  tom_make_RemoveMu() { return new RemoveMu();}



  static private class PrintStrategy extends AbstractBasicStrategy {

    protected Writer w;

    //TODO: adapt with newsl to visit anu data-structures
    public PrintStrategy(Writer w) {
      super(( new tom.library.sl.Identity() ));
      this.w=w;
    }

    /** 
     * Visits the subject any in a light way by providing the introspector 
     *
     * @param any the subject to visit
     * @param i the introspector
     * @throws VisitFailure if visitLight fails
     */
    public Object visitLight(Object any, Introspector i) throws VisitFailure {
      throw new VisitFailure();
    }

    /** 
     * Visit the subject by providing an introspector
     * 
     * @param introspector the introspector
     */
    public int visit(Introspector introspector) {
      Visitable v = (Visitable) getEnvironment().getSubject();
      Position current = getEnvironment().getPosition();
      List<Object> stack = getEnvironment().getCurrentStack(); 
      try {
        //test if it is a pointer due to an expanded MuVar
        if (stack.contains(v)) {
          int index = stack.indexOf(v);
          Position dest = (Position) current.clone();
          for(int i=current.length();i>index;i--) {
            dest = dest.up();
          } 
          Position father = current.up();
          w.write("\n              "+getNodeFromPos(father)+" -> "+getNodeFromPos(dest)+"; "
);
          //fails to prevent loops
          return Environment.FAILURE;
        }
        else {
          String[] tab = v.getClass().getName().split("\\.");
          String name = tab[tab.length-1];
          tab = name.split("\\$");
          name = tab[tab.length-1];
          w.write("\n              "+getNodeFromPos(current)+" [label=\""+name+"\"]; "
);
          if(stack.size()!=0) {
            Position father = current.up();
            w.write("\n                "+getNodeFromPos(father)+" -> "+getNodeFromPos(current)+"; "
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
