/*
 * Gom
 * 
 * Copyright (c) 2000-2010, INPL, INRIA
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
 * Antoine Reilles    e-mail: Antoine.Reilles@loria.fr
 * 
 **/

package tom.library.freshgom;

import java.util.Hashtable;
import java.util.Collection;

/* stack for exportation (term -> raw term) */

public class ConvertMap<T extends Atom> extends Hashtable<String,T> {

  public ConvertMap() {
    super();
  }

  public ConvertMap(ConvertMap<T> o) {
    super(o);
  }

  public ConvertMap<T> combine(ConvertMap<T> m) {
    ConvertMap<T> res = new ConvertMap<T>(this);
    res.putAll(m);
    return res;
  }
}


