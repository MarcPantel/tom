/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2017, Universite de Lorraine, Inria
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
import java.util.logging.*;

import tom.platform.adt.platformoption.*;
import tom.platform.adt.platformoption.types.*;
import tom.platform.adt.platformconfig.*;
import tom.platform.adt.platformconfig.types.*;

/**
 * This class is a wrapper for the platform configuration files.
 * It extracts the plugins information and create an ordered list of
 * of instances. Extracts the Option Management information and based
 * on it create and initialize the corresponding OptionManager.
 * The instantiation of a Configuration is not sufficient since it need to
 * be initialized with an execution commandLine.
 *
 */
public class ConfigurationManager {
  %include{ adt/platformconfig/PlatformConfig.tom }
  
  /** configuration file name */
  private String configurationFileName;

  /** The plugins instance list*/
  private List<Plugin> pluginsList;

  /** The OptionManager */
  private OptionManager optionManager;
  
  private static Logger logger = Logger.getLogger("tom.platform.ConfigurationManager");
  /**
   * Basic Constructor
   * constructing a configurationManager that needs to be initialized
   */
  public ConfigurationManager(String configurationFileName) {
    this.configurationFileName = configurationFileName;
    this.pluginsList = new ArrayList<Plugin>();
  }
  
  /**
   * initialize analyse the file and extract plugins and option management
   *
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  public int initialize(String[] commandLine) {    
    PlatformConfig platformConfig = null;
    try {
      //System.out.println("platformConfig filename = " + configurationFileName);
      java.io.InputStream stream = new java.io.FileInputStream(configurationFileName);
      platformConfig = PlatformConfig.fromStream(stream);
      //System.out.println("platformConfig = " + platformConfig);
    } catch(java.io.IOException e) {
      System.out.println("cannot read: " + configurationFileName);
    }

    if(platformConfig == null) {
      PluginPlatformMessage.error(logger, null, 0, 
          PluginPlatformMessage.configFileNotValid, configurationFileName);
      return 1;
    }

   if(createPlugins(platformConfig)==1) {
     return 1;
   }    

   if(createOptionManager(platformConfig) == 1) {     
     return 1;
   }


    return optionManager.initialize(this, commandLine);
  }

  /** Accessor method */
  public List<Plugin> getPluginsList() {
    return pluginsList;
  }

  /** Accessor method */
  public OptionManager getOptionManager() {
    return optionManager;
  }
  
  /** 
   * Initialize the plugins list based on information extracted
   * from the config file converted in TNode
   *
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  private int createPlugins(PlatformConfig configurationNode) {
    List<String> pluginsClassList = extractClassPaths(configurationNode);
    // if empty list this means there is a problem somewhere
    if(pluginsClassList.isEmpty()) {
      PluginPlatformMessage.error(logger, null, 0, 
          PluginPlatformMessage.noPluginFound, configurationFileName);
      pluginsList = null;
      return 1;
    }
    // creates an instance of each plugin
    for (String pluginClass : pluginsClassList) {
      try { 
        Object pluginInstance = Class.forName(pluginClass).newInstance();
        if(pluginInstance instanceof Plugin) {
          pluginsList.add((Plugin)pluginInstance);
        } else {
          PluginPlatformMessage.error(logger, null, 0, 
              PluginPlatformMessage.classNotAPlugin, pluginClass);
          pluginsList = null;
          return 1;
        }
      } catch(ClassNotFoundException cnfe) {
        PluginPlatformMessage.warning(logger, null, 0, 
            PluginPlatformMessage.classNotFound, pluginClass);
        return 1;
      } catch(Exception e) {
        // adds the error message. this is too cryptic otherwise
        e.printStackTrace();
        PluginPlatformMessage.error(logger, null, 0, 
            PluginPlatformMessage.instantiationError, pluginClass);
        pluginsList = null;
        return 1;
      }
    }
    return 0;
  }
  
  private List<String> extractClassPaths(PlatformConfig node) {
    List<String> res = new ArrayList<String>();
    %match(node) {
      PlatformConfig(concPlugin(_*,Plugin(name,cp),_*),optionmanager) -> {
         res.add(`cp);
         PluginPlatformMessage.finer(logger, null, 0, 
             PluginPlatformMessage.classPathRead, `cp);
       }
    }
    return res;
  }
 
   /**
   * Initialize the option manager based on information extracted
   * from the config file
   * 
   * @param node the node representing the config file
   * @return an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */

  private int createOptionManager(PlatformConfig node) {
    %match(node) {
      PlatformConfig(plugins,OptionManager(omclass,options)) -> {
        try {
          Object omInstance = Class.forName(`omclass).newInstance();
          if(omInstance instanceof OptionManager) {
            optionManager = (OptionManager)omInstance;
          } else {
            PluginPlatformMessage.error(logger, null, 0, 
                PluginPlatformMessage.classNotOptionManager, `omclass);
            return 1;
          }
        } catch(ClassNotFoundException cnfe) {
          PluginPlatformMessage.error(logger, null, 0, 
              PluginPlatformMessage.classNotFound, `omclass);
          optionManager = null;
          return 1;
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println(e.getMessage());
          PluginPlatformMessage.error(logger, null, 0, 
              PluginPlatformMessage.instantiationError, `omclass);
          optionManager = null;
          return 1;
        }

        PlatformOption optionX = `PluginOption("config","X", "Defines an alternate configuration file",
            StringValue(configurationFileName), "");
        PlatformOptionList globalOptions = `concPlatformOption(optionX, options*);
        optionManager.setGlobalOptionList(globalOptions);
        return 0;
      }
    }
    return 1;
  }

  /** logger accessor in case of logging needs*/
  private Logger getLogger() {
    return Logger.getLogger(getClass().getName());
  }

}
