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
 * Antoine Reilles      Antoine.Reilles@loria.fr
 *
 **/

package tom.gom.starter;

import java.util.logging.Level;
import java.util.Map;

import tom.gom.GomMessage;
import tom.gom.GomStreamManager;
import tom.gom.tools.GomGenericPlugin;
import tom.gom.tools.GomEnvironment;


/**
 * The Starter "plugin". Only here to initialize the GomStreamManager
 * and to initalize the plugin platform set/getargs process with it
 */
public class Starter extends GomGenericPlugin {

  /** The args to set during run and to return */
  private Object[] argToRelay;
  /** Saved information during setArgs */
  private String fileName;

  public Starter() {
    super("GomStarter");
  }

  public GomEnvironment getGomEnvironment() {
    return this.gomEnvironment;
  }

  public void setGomEnvironment(GomEnvironment gomEnvironment) {
    this.gomEnvironment = gomEnvironment;
  }

  /**
   * inherited from plugin interface
   * arg[0] should contain the input file name
   */
  public void setArgs(Object[] arg) {
    if (arg[0] instanceof String) {
      fileName = (String)arg[0];
    } else {
      getLogger().log(Level.SEVERE,
          GomMessage.invalidPluginArgument.getMessage(),
          new Object[]{"GomStarter", "[String]",
            getArgumentArrayString(arg)});
    }
  }

  /**
   * inherited from plugin interface
   * Create the GomStreamManager as input for next plugin
   */
  public void run(Map<String,String> informationTracker) {
    getStreamManager().initializeFromOptionManager(getOptionManager());
    getStreamManager().prepareForInputFile(fileName);
    argToRelay = new Object[]{ getGomEnvironment() };
    informationTracker.put(KEY_LAST_GEN_MAPPING,getGomEnvironment().getLastGeneratedMapping());
  }

  /**
   * inherited from plugin interface
   * returns argTorelay initialized during run call
   */
  public Object[] getArgs() {
    return argToRelay;
  }
}
