/* Generated by TOM: Do not edit this file */ /*
  
    TOM - To One Matching Compiler
    
    Copyright (C) 2000-2003 INRIA
                            Nancy, France.
    
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
    
    Pierre-Etienne Moreau	e-mail: Pierre-Etienne.Moreau@loria.fr
    Julien Guyon

*/

package jtom.runtime.set;

import java.util.*;

import aterm.*;
import aterm.pure.PureFactory;

import jtom.adt.set.*;
import jtom.adt.set.types.*;

import jtom.runtime.Replace1;
import jtom.runtime.Collect1;
 
public class SharedMultiSet extends ATermSet {

  /* Generated by TOM: Do not edit this file */                                      
  
  public SharedMultiSet(PureFactory pureFactory) {
    if (factory==null) {
      factory = new Factory(pureFactory);
    }
    emptyTree = getSetFactory().makeJGTreeSet_EmptySet();
    this.tree = makeEmptySet();
  }
  
  private SharedMultiSet(Factory fact, JGTreeSet tree, int count) {
    factory = fact;
    this.tree = tree;
    this.count = count;
  }
  
  public Object[] toArray() {
    final Collection res = new ArrayList();
    Collect1 collect = new Collect1() {
        public boolean apply(ATerm t) {
          if(t instanceof JGTreeSet) {
             { JGTreeSet tom_match1_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match1_1) ||  false ) {
 return false; } if(tom_is_fun_sym_pair(tom_match1_1) ||  false ) { { aterm.ATerm tom_match1_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match1_1); { aterm.ATerm x = ( aterm.ATerm) tom_match1_1_1;
 
                res.add(x);
                return false;
              }} }
 return true;}}
 
          } else {
            return true;
          }
        } // Apply
      }; //new
    
    ATermSet.traversal.genericCollect(tree, collect);
    ATerm[] result = new ATerm[res.size()];
    for(int i=0;i<res.size();i++) {
      result[i] = (ATerm) (((ArrayList)res).get(i));
    }
    return result;
  }
  
  public Object[] toArray(Object[] o) {
    throw new RuntimeException("Not Yet Implemented");
  }
  
  public SharedMultiSet getTail() {
    JGTreeSet set = remove(getHead(tree), tree);
    return new SharedMultiSet(getSetFactory(), set, count-1);
  }
  
  private JGTreeSet makePair(ATerm trm, int i) {
    return getSetFactory().makeJGTreeSet_Pair(trm, i);
  }
  
    // Low interface
  
  protected int size(JGTreeSet t) {
     { JGTreeSet tom_match2_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match2_1) ||  false ) {
  return 0;  } if(tom_is_fun_sym_pair(tom_match2_1) ||  false ) { { aterm.ATerm tom_match2_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match2_1); { aterm.ATerm x = ( aterm.ATerm) tom_match2_1_1;
  return 1; }} } if(tom_is_fun_sym_branch(tom_match2_1) ||  false ) { { JGTreeSet tom_match2_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match2_1); { JGTreeSet tom_match2_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match2_1); { JGTreeSet l = ( JGTreeSet) tom_match2_1_1; { JGTreeSet r = ( JGTreeSet) tom_match2_1_2;
 return size(l) + size(r);}}}} }}}
 
    return 0;
  }
  
  public int multiplicitySize() {
    return multiplicitySize(tree);
  }

  private int multiplicitySize(JGTreeSet t) {
     { JGTreeSet tom_match3_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match3_1) ||  false ) {
  return 0;  } if(tom_is_fun_sym_pair(tom_match3_1) ||  false ) { { int tom_match3_1_2 = ( int) tom_get_slot_pair_multiplicity(tom_match3_1); { int m = ( int) tom_match3_1_2;
 
        return m;
      }} } if(tom_is_fun_sym_branch(tom_match3_1) ||  false ) { { JGTreeSet tom_match3_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match3_1); { JGTreeSet tom_match3_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match3_1); { JGTreeSet l = ( JGTreeSet) tom_match3_1_1; { JGTreeSet r = ( JGTreeSet) tom_match3_1_2;
 return multiplicitySize(l) + multiplicitySize(r);}}}} }}}
 
    return 0;
  }
      // getHead return the first left inner element found
  protected ATerm getHead(JGTreeSet t) {
     { JGTreeSet tom_match4_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match4_1) ||  false ) {
 
        return null;
       } if(tom_is_fun_sym_pair(tom_match4_1) ||  false ) { { aterm.ATerm tom_match4_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match4_1); { aterm.ATerm x = ( aterm.ATerm) tom_match4_1_1;
 return x;}} } if(tom_is_fun_sym_branch(tom_match4_1) ||  false ) { { JGTreeSet tom_match4_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match4_1); { JGTreeSet tom_match4_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match4_1); { JGTreeSet l = ( JGTreeSet) tom_match4_1_1; { JGTreeSet r = ( JGTreeSet) tom_match4_1_2;
 
        ATerm left = getHead(l);
        if(left != null) {
          return left;
        }
        return getHead(r);
      }}}} }}}
 
    return null;
  }
  
  /* Simple binary operation skeleton
 private JGTreeSet f(JGTreeSet m1, JGTreeSet m2) {
   %match(JGTreeSet m1, JGTreeSet m2) {
      emptySet(), x -> {
        return f2(m2);
      }
      x, emptySet -> {
        return f1(m1);
      }
      singleton(y) , x -> {
        return g2(y, m2);
      }
      x, singleton(y) -> {
        return g1(y, m1)
      }
      branch(l1, r1), branch(l2, r2) -> {
        return `branch(f(l1, l2, level+1), f(r1, r2, level+1));
      }
    }
  }*/

  protected JGTreeSet reworkJGTreeSet(JGTreeSet t) {
    Replace1 replace = new Replace1() {
        public ATerm apply(ATerm t) {
           { JGTreeSet tom_match5_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match5_1) ||  false ) {
 return t; } if(tom_is_fun_sym_pair(tom_match5_1) ||  false ) {
 return t; } if(tom_is_fun_sym_branch(tom_match5_1) ||  false ) { { JGTreeSet tom_match5_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match5_1); { JGTreeSet tom_match5_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match5_1); if(tom_is_fun_sym_emptySet(tom_match5_1_1) ||  false ) { if(tom_is_fun_sym_pair(tom_match5_1_2) ||  false ) { { JGTreeSet p = ( JGTreeSet) tom_match5_1_2;
 return p;} } }}} } if(tom_is_fun_sym_branch(tom_match5_1) ||  false ) { { JGTreeSet tom_match5_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match5_1); { JGTreeSet tom_match5_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match5_1); if(tom_is_fun_sym_pair(tom_match5_1_1) ||  false ) { { JGTreeSet p = ( JGTreeSet) tom_match5_1_1; if(tom_is_fun_sym_emptySet(tom_match5_1_2) ||  false ) {
 return p; }} }}} } if(tom_is_fun_sym_branch(tom_match5_1) ||  false ) { { JGTreeSet tom_match5_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match5_1); { JGTreeSet tom_match5_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match5_1); if(tom_is_fun_sym_emptySet(tom_match5_1_1) ||  false ) { { JGTreeSet e = ( JGTreeSet) tom_match5_1_1; if(tom_is_fun_sym_emptySet(tom_match5_1_2) ||  false ) {
 return e; }} }}} } if(tom_is_fun_sym_branch(tom_match5_1) ||  false ) { { JGTreeSet tom_match5_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match5_1); { JGTreeSet tom_match5_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match5_1); { JGTreeSet l1 = ( JGTreeSet) tom_match5_1_1; { JGTreeSet l2 = ( JGTreeSet) tom_match5_1_2;
 return tom_make_branch(reworkJGTreeSet(l1),reworkJGTreeSet(l2)) ;}}}} }
  return traversal.genericTraversal(t,this); }}
 
        }
      };
    
    JGTreeSet res = (JGTreeSet)replace.apply(t);
    if(res != t) {
      res = reworkJGTreeSet(res);
    }
    return res;
  }
  
  protected JGTreeSet union(JGTreeSet m1, JGTreeSet m2, int level) {
     { JGTreeSet tom_match6_1 = ( JGTreeSet) m1;{ { JGTreeSet tom_match6_2 = ( JGTreeSet) m2;{ if(tom_is_fun_sym_emptySet(tom_match6_1) ||  false ) { { JGTreeSet x = ( JGTreeSet) tom_match6_2;
 
        return m2;
      } } { JGTreeSet x = ( JGTreeSet) tom_match6_1; if(tom_is_fun_sym_emptySet(tom_match6_2) ||  false ) {

 
        return m1;
       }} if(tom_is_fun_sym_pair(tom_match6_1) ||  false ) { { aterm.ATerm tom_match6_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match6_1); { int tom_match6_1_2 = ( int) tom_get_slot_pair_multiplicity(tom_match6_1); { aterm.ATerm elt = ( aterm.ATerm) tom_match6_1_1; { int mult = ( int) tom_match6_1_2; { JGTreeSet x = ( JGTreeSet) tom_match6_2;

 
        return override(elt, mult, x, level);
      }}}}} } { JGTreeSet x = ( JGTreeSet) tom_match6_1; if(tom_is_fun_sym_pair(tom_match6_2) ||  false ) { { aterm.ATerm tom_match6_2_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match6_2); { int tom_match6_2_2 = ( int) tom_get_slot_pair_multiplicity(tom_match6_2); { aterm.ATerm elt = ( aterm.ATerm) tom_match6_2_1; { int mult = ( int) tom_match6_2_2;

 
          // underride dont worry about multiplicity
        return underride(elt, x, level);
      }}}} }} if(tom_is_fun_sym_branch(tom_match6_1) ||  false ) { { JGTreeSet tom_match6_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match6_1); { JGTreeSet tom_match6_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match6_1); { JGTreeSet l1 = ( JGTreeSet) tom_match6_1_1; { JGTreeSet r1 = ( JGTreeSet) tom_match6_1_2; if(tom_is_fun_sym_branch(tom_match6_2) ||  false ) { { JGTreeSet tom_match6_2_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match6_2); { JGTreeSet tom_match6_2_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match6_2); { JGTreeSet l2 = ( JGTreeSet) tom_match6_2_1; { JGTreeSet r2 = ( JGTreeSet) tom_match6_2_2;

 
        int l = level+1;
        return tom_make_branch(union(l1,l2,l),union(r1,r2,l)) ;
      }}}} }}}}} }}}}}
 
    return null;
  }
  
  protected JGTreeSet intersection(JGTreeSet m1, JGTreeSet m2, int level) {
     { JGTreeSet tom_match7_1 = ( JGTreeSet) m1;{ { JGTreeSet tom_match7_2 = ( JGTreeSet) m2;{ if(tom_is_fun_sym_emptySet(tom_match7_1) ||  false ) { { JGTreeSet x = ( JGTreeSet) tom_match7_2;

  
        return tom_make_emptySet() ;
      } } { JGTreeSet x = ( JGTreeSet) tom_match7_1; if(tom_is_fun_sym_emptySet(tom_match7_2) ||  false ) {           return tom_make_emptySet() ;        }} if(tom_is_fun_sym_pair(tom_match7_1) ||  false ) { { JGTreeSet p = ( JGTreeSet) tom_match7_1; { aterm.ATerm tom_match7_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match7_1); { int tom_match7_1_2 = ( int) tom_get_slot_pair_multiplicity(tom_match7_1); { aterm.ATerm elt = ( aterm.ATerm) tom_match7_1_1; { int mult = ( int) tom_match7_1_2; { JGTreeSet x = ( JGTreeSet) tom_match7_2;


 
        if (contains(elt, x, level)) {
            /* Warning: we have to choose one of the 2 element: with the lowest multiplicity*/
          return p;
        } else {
          return tom_make_emptySet() ;
        }
      }}}}}} } { JGTreeSet x = ( JGTreeSet) tom_match7_1; if(tom_is_fun_sym_pair(tom_match7_2) ||  false ) { { JGTreeSet p = ( JGTreeSet) tom_match7_2; { aterm.ATerm tom_match7_2_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match7_2); { int tom_match7_2_2 = ( int) tom_get_slot_pair_multiplicity(tom_match7_2); { aterm.ATerm elt = ( aterm.ATerm) tom_match7_2_1; { int mult = ( int) tom_match7_2_2;          if (contains(elt, x, level)) {             /* Warning: we have to choose one of the 2 element: with the lowest multiplicity*/           return p;         } else {           return tom_make_emptySet() ;         }       }}}}} }} if(tom_is_fun_sym_branch(tom_match7_1) ||  false ) { { JGTreeSet tom_match7_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match7_1); { JGTreeSet tom_match7_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match7_1); { JGTreeSet l1 = ( JGTreeSet) tom_match7_1_1; { JGTreeSet r1 = ( JGTreeSet) tom_match7_1_2; if(tom_is_fun_sym_branch(tom_match7_2) ||  false ) { { JGTreeSet tom_match7_2_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match7_2); { JGTreeSet tom_match7_2_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match7_2); { JGTreeSet l2 = ( JGTreeSet) tom_match7_2_1; { JGTreeSet r2 = ( JGTreeSet) tom_match7_2_2;

 
        int l = level+1;
        return tom_make_branch(intersection(l1,l2,l),intersection(r1,r2,l)) ;        
      }}}} }}}}} }}}}}
 
    return null;
  }
  
  protected JGTreeSet restriction(JGTreeSet m1, JGTreeSet m2, int level) {
     { JGTreeSet tom_match8_1 = ( JGTreeSet) m1;{ { JGTreeSet tom_match8_2 = ( JGTreeSet) m2;{ if(tom_is_fun_sym_emptySet(tom_match8_1) ||  false ) { { JGTreeSet x = ( JGTreeSet) tom_match8_2;

  
        return tom_make_emptySet() ;
      } } { JGTreeSet x = ( JGTreeSet) tom_match8_1; if(tom_is_fun_sym_emptySet(tom_match8_2) ||  false ) {           return tom_make_emptySet() ;        }} if(tom_is_fun_sym_pair(tom_match8_1) ||  false ) { { aterm.ATerm tom_match8_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match8_1); { aterm.ATerm y = ( aterm.ATerm) tom_match8_1_1; { JGTreeSet x = ( JGTreeSet) tom_match8_2;

 
        return remove(y, x, level);
      }}} } { JGTreeSet x = ( JGTreeSet) tom_match8_1; if(tom_is_fun_sym_pair(tom_match8_2) ||  false ) { { aterm.ATerm tom_match8_2_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match8_2); { aterm.ATerm y = ( aterm.ATerm) tom_match8_2_1;

 
        if (contains(y, x)) {
            // Warning: we have to choose one of the 2 element: with the lowest multiplicity
          return m2;
        } else {
          return tom_make_emptySet() ;
        }
      }} }} if(tom_is_fun_sym_branch(tom_match8_1) ||  false ) { { JGTreeSet tom_match8_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match8_1); { JGTreeSet tom_match8_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match8_1); { JGTreeSet l1 = ( JGTreeSet) tom_match8_1_1; { JGTreeSet r1 = ( JGTreeSet) tom_match8_1_2; if(tom_is_fun_sym_branch(tom_match8_2) ||  false ) { { JGTreeSet tom_match8_2_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match8_2); { JGTreeSet tom_match8_2_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match8_2); { JGTreeSet l2 = ( JGTreeSet) tom_match8_2_1; { JGTreeSet r2 = ( JGTreeSet) tom_match8_2_2;

 
        int l = level+1;
        return tom_make_branch(restriction(l1,l2,l),restriction(r1,r2,l)) ;
      }}}} }}}}} }}}}}
 
    return null;
  }
  
  protected JGTreeSet remove(ATerm elt, JGTreeSet t, int level) {
     { JGTreeSet tom_match9_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match9_1) ||  false ) {
 return t; } if(tom_is_fun_sym_pair(tom_match9_1) ||  false ) { { aterm.ATerm tom_match9_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match9_1); { aterm.ATerm x = ( aterm.ATerm) tom_match9_1_1;

 
        if (x == elt) {return tom_make_emptySet() ;}
        else {return t;}
      }} } if(tom_is_fun_sym_branch(tom_match9_1) ||  false ) { { JGTreeSet tom_match9_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match9_1); { JGTreeSet tom_match9_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match9_1); { JGTreeSet l = ( JGTreeSet) tom_match9_1_1; { JGTreeSet r = ( JGTreeSet) tom_match9_1_2;

 
        JGTreeSet l1 = null, r1=null;
        if( isBitZero(elt, level) ) {
          l1 = remove(elt, l, level+1);
          r1 = r;
        } else {
          l1 = l;
          r1 = remove(elt, r, level+1);
        }
         { JGTreeSet tom_match10_1 = ( JGTreeSet) l1;{ { JGTreeSet tom_match10_2 = ( JGTreeSet) r1;{ if(tom_is_fun_sym_emptySet(tom_match10_1) ||  false ) { if(tom_is_fun_sym_pair(tom_match10_2) ||  false ) {
 return r1; } } if(tom_is_fun_sym_pair(tom_match10_1) ||  false ) { if(tom_is_fun_sym_emptySet(tom_match10_2) ||  false ) {
 return l1; } }
 return tom_make_branch(l1,r1) ;}}}}
 
      }}}} }}}
 
    return null;
  }

  protected boolean contains(ATerm elt, JGTreeSet t, int level) {
     { JGTreeSet tom_match11_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match11_1) ||  false ) {
 return false; } if(tom_is_fun_sym_pair(tom_match11_1) ||  false ) { { aterm.ATerm tom_match11_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match11_1); { aterm.ATerm x = ( aterm.ATerm) tom_match11_1_1;

 
        if(x == elt) return true;
      }} } if(tom_is_fun_sym_branch(tom_match11_1) ||  false ) { { JGTreeSet tom_match11_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match11_1); { JGTreeSet tom_match11_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match11_1); { JGTreeSet l = ( JGTreeSet) tom_match11_1_1; { JGTreeSet r = ( JGTreeSet) tom_match11_1_2;

 
        if(level == depth) {
          return (contains(elt, l, level) || contains(elt, r, level));
        }
        if( isBitZero(elt, level)) {
          return contains(elt, l, level+1);
        } else {
          return contains(elt, r, level+1);
        }
      }}}} }}}
 
    return false;
  }
  
  protected JGTreeSet override(ATerm elt, int multiplicity, JGTreeSet t, int level) {
    int lev = level+1;
     { JGTreeSet tom_match12_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match12_1) ||  false ) {
 
        return makePair(elt, multiplicity);
       } if(tom_is_fun_sym_pair(tom_match12_1) ||  false ) { { aterm.ATerm tom_match12_1_1 = ( aterm.ATerm) tom_get_slot_pair_value(tom_match12_1); { int tom_match12_1_2 = ( int) tom_get_slot_pair_multiplicity(tom_match12_1); { aterm.ATerm x = ( aterm.ATerm) tom_match12_1_1; { int mult = ( int) tom_match12_1_2;

 
        if(x == elt) {  return makePair(elt, mult+multiplicity);}
        else if( level >= depth ) {
          System.out.println("Collision!!!!!!!!");
          collisions++;
            // Create 1rst list of element as it was a branch
          return tom_make_branch(t,tom_make_singleton(elt)) ;
          
        }
        else if ( isBitZero(elt, level) && isBitZero(x, level) )  { return tom_make_branch(override(elt,multiplicity,t,lev),tom_make_emptySet()) ;}
        else if ( isBitOne(elt, level)  && isBitOne(x, level) )   { return tom_make_branch(tom_make_emptySet(),override(elt,multiplicity,t,lev)) ;}
        else if ( isBitZero(elt, level) && isBitOne(x, level) ) { return tom_make_branch(makePair(elt,multiplicity),t) ;}
        else if ( isBitOne(elt, level)  && isBitZero(x, level) ){ return tom_make_branch(t,makePair(elt,multiplicity)) ;}
      }}}} } if(tom_is_fun_sym_branch(tom_match12_1) ||  false ) { { JGTreeSet tom_match12_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match12_1); { JGTreeSet tom_match12_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match12_1); { JGTreeSet l = ( JGTreeSet) tom_match12_1_1; { JGTreeSet r = ( JGTreeSet) tom_match12_1_2;

 
        if(level >= depth) {
          System.out.println("Collision!!!!!!!!");
          collisions++;
            //continue list of element
          return tom_make_branch(t,tom_make_singleton(elt)) ;
        }
        if (isBitZero(elt, level)) {
          return tom_make_branch(override(elt,multiplicity,l,lev),r) ;
        } else {
          return tom_make_branch(l,override(elt,multiplicity,r,lev)) ;
        }
      }}}} }}}
 
    return null;
  }
  
  protected JGTreeSet underride(ATerm elt, JGTreeSet t, int level) {
    int lev = level+1;
     { JGTreeSet tom_match13_1 = ( JGTreeSet) t;{ if(tom_is_fun_sym_emptySet(tom_match13_1) ||  false ) {
 return tom_make_singleton(elt) ; } if(tom_is_fun_sym_singleton(tom_match13_1) ||  false ) { { aterm.ATerm tom_match13_1_1 = ( aterm.ATerm) tom_get_slot_singleton_value(tom_match13_1); { aterm.ATerm x = ( aterm.ATerm) tom_match13_1_1;

 
        if(x == elt) {  return t;}
        else if( level >= depth ) {
          System.out.println("Collision!!!!!!!!");
          collisions++;
            // Create 1rst list of element as it was a branch
          return tom_make_branch(t,tom_make_singleton(elt)) ;
          
        }
        else if ( isBitZero(elt, level) && isBitZero(x, level) )  { return tom_make_branch(underride(elt,t,lev),tom_make_emptySet()) ;}
        else if ( isBitOne(elt, level)  && isBitOne(x, level) )   { return tom_make_branch(tom_make_emptySet(),underride(elt,t,lev)) ;}
        else if ( isBitZero(elt, level) && isBitOne(x, level) ) { return tom_make_branch(tom_make_singleton(elt),t) ;}
        else if ( isBitOne(elt, level)  && isBitZero(x, level) ){ return tom_make_branch(t,tom_make_singleton(elt)) ;}
      }} } if(tom_is_fun_sym_branch(tom_match13_1) ||  false ) { { JGTreeSet tom_match13_1_1 = ( JGTreeSet) tom_get_slot_branch_left(tom_match13_1); { JGTreeSet tom_match13_1_2 = ( JGTreeSet) tom_get_slot_branch_right(tom_match13_1); { JGTreeSet l = ( JGTreeSet) tom_match13_1_1; { JGTreeSet r = ( JGTreeSet) tom_match13_1_2;

 
        if (isBitZero(elt, level)) {return tom_make_branch(underride(elt,l,lev),r) ;}
        else {return tom_make_branch(l,underride(elt,r,lev)) ;}
      }}}} }}}
 
    return null;
  }
  
} //Class SharedMultiSet
