/*
 * Gom
 *
 * Copyright (C) 2006 INRIA
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

package tom.gom.backend.strategy;

import java.io.*;
import java.util.logging.*;
import tom.gom.backend.TemplateClass;
import tom.gom.tools.GomEnvironment;
import tom.gom.tools.error.GomRuntimeException;
import tom.gom.adt.objects.types.*;

public class SOpTemplate extends TemplateClass {
  ClassName operator;
  SlotFieldList slotList;

  %include { ../../adt/objects/Objects.tom}

  /*
   * The argument is an operator class, and this template generates the
   * assotiated _Op strategy
   */
  public SOpTemplate(ClassName className,
                     SlotFieldList slots) {
    super(className);
    %match(ClassName className) {
      ClassName(pkg,name) -> {
        String newpkg = `pkg.replaceFirst(".types.",".strategy.");
        String newname = "_"+`name;
        this.className = `ClassName(newpkg,newname);
      }
    }
    this.operator = className;
    this.slotList = slots;
  }

  public String generate() {

    String classBody = %[
package @getPackage()@;

import tom.library.strategy.mutraveler.Position;
import tom.library.strategy.mutraveler.MuVisitable;
import tom.library.strategy.mutraveler.MuStrategy;
import tom.library.strategy.mutraveler.reflective.StrategyVisitorFwd;

public class @className()@ implements tom.library.strategy.mutraveler.MuStrategy {
  private static final String msg = "Not an @className(operator)@";
  /* Manage an internal position */
  private Position position;

  public void setPosition(Position pos) {
    this.position = pos;
  }

  public Position getPosition() {
    if(hasPosition()) {
      return position;
    } else {
      throw new RuntimeException("position not initialized");
    }
  }

  public boolean hasPosition() {
    return position!=null;
  }

  private jjtraveler.reflective.VisitableVisitor[] args;

  public jjtraveler.reflective.VisitableVisitor getArgument(int i) {
    return args[i];
  }
  public void setArgument(int i, jjtraveler.reflective.VisitableVisitor child) {
    args[i]= child;
  }
  public int getChildCount() {
    return args.length;
  }
  public jjtraveler.Visitable getChildAt(int i) {
      return args[i];
  }
  public jjtraveler.Visitable setChildAt(int i, jjtraveler.Visitable child) {
    args[i]= (jjtraveler.reflective.VisitableVisitor) child;
    return this;
  }
  /*
   * Apply the strategy, and returns the subject in case of VisitFailure
   */
  public jjtraveler.Visitable apply(jjtraveler.Visitable any) {
    try {
      return tom.library.strategy.mutraveler.MuTraveler.init(this).visit(any);
    } catch (jjtraveler.VisitFailure f) {
      return any;
    }
  }

  public MuStrategy accept(StrategyVisitorFwd v) throws jjtraveler.VisitFailure {
    return v.visit_Strategy(this);
  }

  private static boolean[] nonbuiltin = new boolean[]{@genNonBuiltin()@};
  public @className()@(@genConstrArgs(slotList.length(),"jjtraveler.reflective.VisitableVisitor arg")@) {
    args = new jjtraveler.reflective.VisitableVisitor[]{@genConstrArgs(slotList.length(),"arg")@};
  }

  public jjtraveler.Visitable visit(jjtraveler.Visitable any) throws jjtraveler.VisitFailure {
    if(any instanceof @fullClassName(operator)@) {
      jjtraveler.Visitable result = any;
      if (any instanceof MuVisitable) {
        boolean updated = false;
        jjtraveler.Visitable[] childs = null;

        if(!hasPosition()) {
          for (int i = 0, nbi = 0; i < @slotList.length()@; i++) {
            if (nonbuiltin[i]) {
              jjtraveler.Visitable oldChild = any.getChildAt(nbi);
              jjtraveler.Visitable newChild = args[i].visit(oldChild);
              if (updated || (newChild != oldChild)) {
                if (!updated) { // this is the first change
                  updated = true;
                  // allocate the array, and fill it
                  childs = new jjtraveler.Visitable[@nonBuiltinChildCount()@];
                  for (int j = 0 ; j<nbi ; j++) {
                    childs[j] = any.getChildAt(j);
                  }
                }
                childs[nbi] = newChild;
              }
              nbi++;
            }
          }
        } else {
          try {
            for (int i = 0, nbi = 0; i < @slotList.length()@; i++) {
              if (nonbuiltin[i]) {
                jjtraveler.Visitable oldChild = any.getChildAt(nbi);
                getPosition().down(i+1);
                jjtraveler.Visitable newChild = args[i].visit(oldChild);
                getPosition().up();
                if (updated || (newChild != oldChild)) {
                  if (!updated) {
                    updated = true;
                    // allocate the array, and fill it
                    childs = new jjtraveler.Visitable[@nonBuiltinChildCount()@];
                    for (int j = 0 ; j<nbi ; j++) {
                      childs[j] = any.getChildAt(j);
                    }
                  }
                  childs[nbi] = newChild;
                }
                nbi++;
              }
            }
          } catch(jjtraveler.VisitFailure f) {
            getPosition().up();
            throw new jjtraveler.VisitFailure();
          }
        }
        if (updated) {
          result = ((MuVisitable) any).setChilds(childs);
        }
      } else {
        if(!hasPosition()) {
          for (int i = 0, nbi = 0; i < @slotList.length()@; i++) {
            if (nonbuiltin[i]) {
              jjtraveler.Visitable newChild = args[i].visit(result.getChildAt(nbi));
              result = result.setChildAt(nbi, newChild);
              nbi++;
            }
          }
        } else {
          try {
            for (int i = 0, nbi = 0; i < @slotList.length()@; i++) {
              if (nonbuiltin[i]) {
                getPosition().down(i+1);
                jjtraveler.Visitable newChild = args[i].visit(result.getChildAt(nbi));
                getPosition().up();
                result = result.setChildAt(nbi, newChild);
                nbi++;
              }
            }
          } catch(jjtraveler.VisitFailure f) {
            getPosition().up();
            throw new jjtraveler.VisitFailure();
          }
        }
      }
      return result;
    } else {
      throw new jjtraveler.VisitFailure(msg);
    }
  }
}
]%;

    return classBody;
  }

  private String genConstrArgs(int count, String arg) {
    String args = "";
    for(int i = 0; i < count; ++i) {
      args += (i==0?"":", ")+arg+i;
    }
    return args;
  }

  public String generateMapping() {
    return %[
%op Strategy @className()@(@genStratArgs(slotList.length(),"arg")@) {
  is_fsym(t) { (t!=null) && t instanceof (@fullClassName()@)}
@genGetSlot(slotList.length(),"arg")@
  make(@genConstrArgs(slotList.length(),"arg")@) { new @fullClassName()@(@genConstrArgs(slotList.length(),"arg")@) }
}
]%;
  }

  private String genGetSlot(int count, String arg) {
    String out = "";
    for (int i = 0; i < count; ++i) {
      out += %[
  get_slot(@arg+i@, t) { t.getArgument(@i@) }]%;
    }
    return out;
  }
  private String genStratArgs(int count, String arg) {
    String args = "";
    for(int i = 0; i < count; ++i) {
      args += (i==0?"":", ")+arg+i+":Strategy";
    }
    return args;
  }

  private String genNonBuiltin() {
    String out = "";
    %match(SlotFieldList slotList) {
      concSlotField(_*,SlotField[name=fieldName,domain=domain],_*) -> {
        if (!GomEnvironment.getInstance().isBuiltinClass(`domain)) {
          out += "true, ";
        } else {
          out += "false, ";
        }
      }
    }
    if (out.length()!=0) {
      return out.substring(0,out.length()-2);
    } else {
      return out;
    }
  }

  private int nonBuiltinChildCount() {
    int count = 0;
    %match(SlotFieldList slotList) {
      concSlotField(_*,SlotField[domain=domain],_*) -> {
        if (!GomEnvironment.getInstance().isBuiltinClass(`domain)) {
          count++;
        }
      }
    }
    return count;
  }

  /** the class logger instance*/
  private Logger getLogger() {
    return Logger.getLogger(getClass().getName());
  }
}
