/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (C) 2000-2004 INRIA
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

package tom.platform;

import java.util.*;
import tom.library.adt.tnode.types.TNode;
import tom.platform.adt.platformoption.types.*;

/**
 * Manage the list of option of different plugin but also global option
 * declared in xml configfile
 *
 * @author Gr&eacute;gory ANDRIEN
 */

public interface OptionManager {

  /**
   * An optionManager can be initialized with the intermediate of a
   * ConfigurationManager and a commandLine string
   *
   * @return an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  public int initialize(ConfigurationManager confManager,String[] commandLine);
  
    
  /**
   *
   * The Option manager is the only able to extract the input file name list 
   * in the list of argument. The computation is done during initialization
   *
   */
  public List getInputFileList(); 

  /**
   * Set the option 'name' to the corresponding value 
   *
   * @param name the option's name
   * @param value the option's value
   */
  public void setOptionValue(String name, Object value);
  
  /**
   * Get the option 'name' valus 
   *
   * @param optionName the option's name
   * @return the option's value as an Object
   */
  public Object getOptionValue(String optionName);
  
}
