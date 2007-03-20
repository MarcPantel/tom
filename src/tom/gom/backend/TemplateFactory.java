/*
 * Gom
 *
 * Copyright (C) 2006-2007, INRIA
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
package tom.gom.backend;

import tom.gom.tools.error.GomRuntimeException;
import tom.gom.adt.objects.types.*;

public abstract class TemplateFactory {

  public static TemplateFactory getFactory(String mode) {
    if (mode.equals("shared")) {
      return new SharedTemplateFactory();
    } else {
      throw new GomRuntimeException("Output mode "+mode+" not supported");
    }
  }

  public abstract MappingTemplateClass makeTomMappingTemplate(
      GomClass gomClass,
      TemplateClass strategyMapping);
  public abstract TemplateClass makeForwardTemplate(GomClass gomClass);
  public abstract TemplateClass makeVisitableForwardTemplate(GomClass gomClass);
  public abstract TemplateClass makeVisitorTemplate(GomClass gomClass);
  public abstract TemplateClass makeAbstractTypeTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping);
  public abstract TemplateClass makeSortTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping);
  public abstract TemplateClass makeOperatorTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping);
  public abstract TemplateClass makeVariadicOperatorTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping);
}

class SharedTemplateFactory extends TemplateFactory {

  public MappingTemplateClass makeTomMappingTemplate(
      GomClass gomClass,
      TemplateClass strategyMapping) {
    return
      new tom.gom.backend.shared.MappingTemplate(gomClass,strategyMapping);
  }
  public TemplateClass makeForwardTemplate(GomClass gomClass) {
    return new tom.gom.backend.shared.ForwardTemplate(gomClass);
  }
  public TemplateClass makeVisitableForwardTemplate(GomClass gomClass) {
    return new tom.gom.backend.shared.BasicStrategyTemplate(gomClass);
  }
  public TemplateClass makeVisitorTemplate(GomClass gomClass) {
    return new tom.gom.backend.shared.VisitorTemplate(gomClass);
  }
  public TemplateClass makeAbstractTypeTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping) {
    return new tom.gom.backend.shared.AbstractTypeTemplate(tomHomePath, importList, gomClass,mapping);
  }
  public TemplateClass makeSortTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping) {
    return new tom.gom.backend.shared.SortTemplate(tomHomePath, importList, gomClass,mapping);
  }
  public TemplateClass makeOperatorTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping) {
    return new tom.gom.backend.shared.OperatorTemplate(tomHomePath, importList, gomClass,mapping);
  }
  public TemplateClass makeVariadicOperatorTemplate(java.io.File tomHomePath, java.util.List importList, GomClass gomClass, TemplateClass mapping) {
    return new tom.gom.backend.shared.VariadicOperatorTemplate(tomHomePath, importList, gomClass,mapping);
  }
}
