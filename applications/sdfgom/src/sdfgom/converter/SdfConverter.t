/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2009, INRIA
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

package sdfgom.converter;

import java.lang.*;
import aterm.AFun;
import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermList;
import aterm.pure.PureFactory;
import aterm.pure.SingletonFactory;
import tom.library.utils.ATermConverter;

public class SdfConverter implements ATermConverter {

  private static PureFactory factory = SingletonFactory.getInstance();

  /**
   * Method from ATermConverter interface
   */
  public ATerm convert(ATerm at) {
    switch(at.getType()) {
      case ATerm.APPL:
        ATermAppl appl = renameAppl((ATermAppl) at);
        String name = appl.getName();
        at = appl; // renamed at by default

        if(name.equals("default")) { // default(x) -> x
          ATerm arg = appl.getArgument(0);
          at = arg;
        } else if(name.equals("single")) {
          ATerm arg = appl.getArgument(0);
          if(arg instanceof ATermAppl) {
            if(((ATermAppl)arg).getName().equals("char_class")) {
              ATerm new_at = factory.makeAppl(factory.makeAFun("look_char_class",1,false),((ATermAppl)arg).getArgumentArray());
              at = appl.setArgument(new_at,0);
            }
          }
        } 
        // default case: perform classical renaming
        return at;

      default:
        return at;
    }
  } //convert

  /** 
   * wrap the integers contained in an ATermList
   * @param l the ATermList that needs to be transformed
   * @param constructorName the constructor to introduce
   * @return the list where each integer is replaced by constructorName(int)
   */
  private ATermList encodeIntList(ATermList l,String constructorName) {
    if(l.isEmpty()) {
      return l;
    } else {
      ATerm elt = l.getFirst();
      if(elt.getType() == ATerm.INT) {
        elt = factory.makeAppl(factory.makeAFun(constructorName,1,false),elt);
      } 
      return factory.makeList(elt,encodeIntList(l.getNext(),constructorName));
    }
  }

  /**
    * rename into a valid Java identifier the name of an ATermAppl
    * @param the ATerm to rename
    * @return the renamed ATerm
    * '-' are replaced by '_'
    * the constant 'assoc' is renamed into 'my_assoc'
    * the constructor 'sort' is renamed into 'my_sort'
    */
  private ATermAppl renameAppl(ATermAppl appl) {
    AFun fun = appl.getAFun();
    if(!fun.isQuoted()) {
      String name = fun.getName().replaceAll("-","_");
      if(name.equals("sort")) {
        name = "my_sort";
      } else if(name.equals("short")) {
        name = "my_short";
      } else if(name.equals("assoc") && appl.getArity()==0) {
        name = "my_assoc";
      }
      appl = factory.makeAppl(factory.makeAFun(name,fun.getArity(),false),appl.getArgumentArray());
    }
    return appl; 
  }


}