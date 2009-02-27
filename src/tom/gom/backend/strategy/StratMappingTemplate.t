/*
 * Gom
 *
 * Copyright (c) 2006-2009, INRIA
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

import tom.gom.GomStreamManager;
import tom.gom.tools.GomEnvironment;
import tom.gom.backend.MappingTemplateClass;
import java.io.*;
import tom.gom.adt.objects.types.*;
import tom.gom.tools.error.GomRuntimeException;

public class StratMappingTemplate extends MappingTemplateClass {
  GomClassList operatorClasses;

  %include { ../../adt/objects/Objects.tom }

  public StratMappingTemplate(GomClass gomClass, GomEnvironment gomEnvironment) {
    super(gomClass,gomEnvironment);
    %match(gomClass) {
      TomMapping[OperatorClasses=ops] -> {
        this.operatorClasses = `ops;
        return;
      }
    }
    throw new GomRuntimeException(
        "Wrong argument for MappingTemplate: " + gomClass);
  }
  
  public GomEnvironment getGomEnvironment() {
    return this.gomEnvironment;
  }

  public void generateTomMapping(java.io.Writer writer) throws java.io.IOException {
    generate(writer);
  }

  public void generate(java.io.Writer writer) throws java.io.IOException {
    %match(GomClassList operatorClasses) {
      ConcGomClass(_*,op@OperatorClass[],_*) -> {
        writer.write(
            (new tom.gom.backend.strategy.SOpTemplate(`op,getGomEnvironment())).generateMapping());
        writer.write(
            (new tom.gom.backend.strategy.IsOpTemplate(`op,getGomEnvironment())).generateMapping());
        writer.write(
            (new tom.gom.backend.strategy.MakeOpTemplate(`op,getGomEnvironment())).generateMapping());
      }
      ConcGomClass(_*,
          VariadicOperatorClass[ClassName=vopName,
          Empty=OperatorClass[ClassName=empty],
          Cons=OperatorClass[ClassName=cons]],
          _*)-> {
        writer.write(%[
            %op Strategy _@className(`vopName)@(sub:Strategy) {
            is_fsym(t) { false }
            make(sub)  { `mu(MuVar("x_@className(`vopName)@"),Choice(_@className(`cons)@(sub,MuVar("x_@className(`vopName)@")),_@className(`empty)@())) }
            }
            ]%);
      }
    }
      }

  protected String fileName() {
    return fullClassName().replace('.',File.separatorChar)+".tom";
  }

}
