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

package tom.gom.backend.shared;

import tom.gom.backend.TemplateClass;
import tom.gom.adt.objects.*;
import tom.gom.adt.objects.types.*;

public class BasicStrategyTemplate extends TemplateClass {
  ClassName fwd;

  public BasicStrategyTemplate(ClassName className, ClassName fwd) {
    super(className);
    this.fwd = fwd;
  }

  public String generate() {
    StringBuffer out = new StringBuffer();

    out.append(%[
package @getPackage()@;
import tom.library.strategy.mutraveler.Position;
import tom.library.strategy.mutraveler.MuStrategy;
    
  public class @className()@ extends @className(fwd)@ implements MuStrategy {
  private Position position;

  public void setPosition(Position pos) {
    this.position = pos;
  }

  public Position getPosition() {
    if(hasPosition()) {
      return (Position) position.clone();
    } else {
      throw new RuntimeException("position not initialized");
    }
  }

  public boolean hasPosition() {
    return position!=null;
  }

    
  public int getChildCount() {
    return 1;
  }
    
  public jjtraveler.Visitable getChildAt(int i) {
    switch (i) {
      case 0: return (jjtraveler.Visitable) any;
      default: throw new IndexOutOfBoundsException();
    }
  }
    
  public jjtraveler.Visitable setChildAt(int i, jjtraveler.Visitable child) {
    switch (i) {
      case 0: any = (jjtraveler.reflective.VisitableVisitor) child; return this;
      default: throw new IndexOutOfBoundsException();
    }
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
    
  public @className()@(jjtraveler.reflective.VisitableVisitor any) {
    super(any);
  }
}
]%);

    return out.toString();
  }
}
