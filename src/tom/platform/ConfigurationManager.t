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
 * Gregory Andrien
 *
 **/

package tom.platform;

import java.util.*;
import java.util.logging.*;
import java.io.*;


import aterm.*;
import aterm.pure.*;
import jtom.*;

import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;
import tom.library.xml.*;
import tom.platform.adt.platformoption.*;
import tom.platform.adt.platformoption.types.*;

/**
 * This class is a wrapper for the platform XML configuration files.
 * it extract the plugins information and create an ordered list of
 * of instances. it extract the Option Management information and based
 * on it create and initialize the corresponding OptionManager.
 * The creation of the class is not sfficient since it need to be
 * initialized with an execution commandLine.
 *
 */
public class ConfigurationManager {
  
  /** Used to analyse xml configuration file*/
  %include{ adt/TNode.tom }
  
  /**
   * Accessor method necessary when including adt/TNode.tom 
   * @return a TNodeFactory
   */
  private TNodeFactory getTNodeFactory() {
    return TNodeFactory.getInstance(SingletonFactory.getInstance());
  }

  %include{ adt/PlatformOption.tom }
  /**
   * Accessor method necessary to include adt/PlatformOption.tom
   * @return a PlatformOptionFactory
   */
  private PlatformOptionFactory getPlatformOptionFactory() {
    return PlatformOptionFactory.getInstance(SingletonFactory.getInstance());
  }
  
  /** configuration file name */
  private String xmlConfigurationFileName;

  /** The plugins instance list*/
  private List pluginsList;

  /** The global options */    
  private PlatformOptionList globalOptions;

  /** The OptionManager */
  private OptionManager optionManager;
  
  /**
   * Basic Constructor
   * @return a configurationManager that needs to be initialized
   */
  public ConfigurationManager(String xmlConfigurationFileName) {
    this.xmlConfigurationFileName = xmlConfigurationFileName;
    this.pluginsList = new ArrayList();
  }
  
  /**
   * initialize analyse the XML file and extract plugins and option management
   *
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  public int initialize(String[] commandLine) {
    XmlTools xtools = new XmlTools();
    TNode configurationNode = (TNode)xtools.convertXMLToATerm(xmlConfigurationFileName);
    if(configurationNode == null) {
      System.out.println("bad XML");
      getLogger().log(Level.SEVERE, "ConfigFileNotXML", xmlConfigurationFileName);
      return 1;
    }
    if(createPlugins(configurationNode.getDocElem())==1) {
      return 1;
    }
    if(createOptionManager(configurationNode.getDocElem()) == 1) {
      return 1;
    }
    return optionManager.initialize(this, commandLine);
  }

  /** Accessor method */
  public List getPluginsList() {
    return pluginsList;
  }

  /** Accessor method */
  public OptionManager  getOptionManager() {
    return optionManager;
  }
  
  /** Accessor method */
  public PlatformOptionList getGlobalOptionList() {
    return globalOptions;
  }
  
  /** 
   * Initialize the plugins list based on information extracted
   * from the XML conf file converted in TNode
   *
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  private int createPlugins(TNode configurationNode) {
    List pluginsClassList = extractClassPaths(configurationNode);
    if(pluginsClassList.isEmpty()) {
      System.out.println("NoPluginFound");
      getLogger().log(Level.SEVERE, "NoPluginFound", xmlConfigurationFileName);
      pluginsList = null;
      return 1;
    }
    // creates an instance of each plugin
    Iterator classPathIt = pluginsClassList.iterator();
    while(classPathIt.hasNext()) {
      String pluginClass = (String)classPathIt.next();
      try { 
        Object pluginInstance = Class.forName(pluginClass).newInstance();
        if(pluginInstance instanceof Plugin) {
          pluginsList.add(pluginInstance);
        } else {
          getLogger().log(Level.SEVERE, "ClassNotAPlugin", pluginClass);
          pluginsList = null;
          return 1;
        }
      } catch(ClassNotFoundException cnfe) {
        getLogger().log(Level.WARNING, "ClassNotFound", pluginClass);
      } catch(Exception e) {
        //e.printStackTrace();
        getLogger().log(Level.SEVERE, "InstantiationError", pluginClass);
        pluginsList = null;
        return 1;
      }
    }
    return 0;
  }
  
  /**
   * Extracts the plugins' class paths from the XML configuration file.
   * 
   * @param node the node containing the XML document
   * @return the List of plugins class path
   */
  private List extractClassPaths(TNode node) {
    List res = new ArrayList();
    %match(TNode node) {
      <platform><plugins><plugin [class=cp]/></plugins></platform> -> {
         res.add(cp);
         getLogger().log(Level.FINER, "ClassPathRead", cp);
       }
    }
    return res;
  }
 
   /**
   * Initialize the option manager based on information extracted
   * from the XML conf file converted in TNode
   * 
   * @param node the node containing the XML file
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  private int createOptionManager(TNode node) {
    %match(TNode node) {
      <platform><optionmanager class=omclass>opt@<options></options></optionmanager></platform> -> {
        globalOptions = OptionParser.xmlNodeToOptionList(`opt);
        try {
          Object omInstance = Class.forName(omclass).newInstance();
          if(omInstance instanceof OptionManager) {
            optionManager = (OptionManager)omInstance;
          } else {
            getLogger().log(Level.SEVERE, "ClassNotOptionManager", `omclass);
            return 1;
          }
        } catch(ClassNotFoundException cnfe) {
          getLogger().log(Level.SEVERE, "ClassNotFound", `omclass);
          optionManager = null;
          return 1;
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println(e.getMessage());
          getLogger().log(Level.SEVERE, "InstantiationError", `omclass);
          optionManager = null;
          return 1;
        }
        return 0;
      }
    }
    return 1;
  }

  /** logger accessor in case of logging needs*/
  private Logger getLogger() {
    return Logger.getLogger(getClass().getName());
  }

} //class ConfigurationManager
