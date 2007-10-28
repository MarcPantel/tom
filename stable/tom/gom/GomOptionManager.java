/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
 * Gom
 *
 * Copyright (c) 2000-2007, INRIA
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
 * Antoine Reilles       e-mail: Antoine.Reilles@loria.fr
 *
 **/

package tom.gom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.platform.ConfigurationManager;
import tom.platform.OptionManager;
import tom.platform.OptionOwner;
import tom.platform.adt.platformoption.types.*;
import aterm.ATerm;
import aterm.ATermAppl;
import aterm.ATermList;

public class GomOptionManager implements OptionManager, OptionOwner {

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */private static boolean tom_is_sort_char(char t) { return  true ;}  /* Generated by TOM (version 2.6alpha): Do not edit this file */private static boolean tom_is_sort_int(int t) { return  true ;} private static  tom.platform.adt.platformoption.types.PlatformBoolean  tom_make_True() { return  tom.platform.adt.platformoption.types.platformboolean.True.make() ; }private static  tom.platform.adt.platformoption.types.PlatformBoolean  tom_make_False() { return  tom.platform.adt.platformoption.types.platformboolean.False.make() ; }private static  tom.platform.adt.platformoption.types.PlatformOptionList  tom_empty_list_concPlatformOption() { return  tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ; }   private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_append_list_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList l1,  tom.platform.adt.platformoption.types.PlatformOptionList  l2) {     if( l1.isEmptyconcPlatformOption() ) {       return l2;     } else if( l2.isEmptyconcPlatformOption() ) {       return l1;     } else if(  l1.getTailconcPlatformOption() .isEmptyconcPlatformOption() ) {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,l2) ;     } else {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,tom_append_list_concPlatformOption( l1.getTailconcPlatformOption() ,l2)) ;     }   }   private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_get_slice_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList  begin,  tom.platform.adt.platformoption.types.PlatformOptionList  end, tom.platform.adt.platformoption.types.PlatformOptionList  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( begin.getHeadconcPlatformOption() ,( tom.platform.adt.platformoption.types.PlatformOptionList )tom_get_slice_concPlatformOption( begin.getTailconcPlatformOption() ,end,tail)) ;     }   }    

  /** The global options */
  private PlatformOptionList globalOptions;

  /**  map the name of an option to the plugin which defines this option */
  private Map mapNameToOptionOwner;

  /** map the name of an option to the option itself */
  private Map mapNameToOption;

  /** map a shortname of an option to its full name */
  private Map mapShortNameToName;

  /** the list of input files extract from the commandLine */
  private List inputFileList;

  /**
   * Basic Constructor
   * @return a configurationManager that needs to be initialized
   */
  public GomOptionManager() {
    mapNameToOptionOwner = new HashMap();
    mapNameToOption = new HashMap();
    mapShortNameToName = new HashMap();
    inputFileList = new ArrayList();
    globalOptions = tom_empty_list_concPlatformOption();
  }

  /**
   * initialize the GomOptionManager
   *
   * @return  an error code :
   * <ul>
   * <li>0 if no error was encountered</li>
   * <li>1 if something went wrong</li>
   * </ul>
   */
  public int initialize(
      ConfigurationManager confManager,
      String[] commandLine) {
    List pluginList = confManager.getPluginsList();
    List optionOwnerList = new ArrayList(pluginList);
    optionOwnerList.add(this);
    collectOptions(optionOwnerList, pluginList);
    this.inputFileList = processArguments(commandLine);
    if(this.inputFileList == null) {
      return 1;
    }
    return checkAllOptionsDepedencies(optionOwnerList);
  }

  /**
   * Inherited from OptionManager interface
   */
  public void setGlobalOptionList(PlatformOptionList globalOptions) {
    this.globalOptions = globalOptions;
  }

  /** Accessor Method */
  public List getInputToCompileList() {
    return inputFileList;
  }

  /**
   * An option has changed
   *
   * @param optionName the option's name
   * @param optionValue the option's desired value
   */
  public void optionChanged(String optionName, Object optionValue) {
    String canonicalOptionName = getCanonicalName(optionName);
    if(canonicalOptionName.equals("verbose")) {
      if( ((Boolean)optionValue).booleanValue() ) {
        Gom.changeLogLevel(Level.INFO);
      }
    } else if(canonicalOptionName.equals("wall")) {
      if( ((Boolean)optionValue).booleanValue() ) {
        Gom.changeLogLevel(Level.WARNING);
      }
    } else if(canonicalOptionName.equals("debug")) {
      if( ((Boolean)optionValue).booleanValue() ) {
        Gom.changeLogLevel(Level.FINE);
      }
    } else if(canonicalOptionName.equals("verbosedebug")) {
      if( ((Boolean)optionValue).booleanValue() ) {
        Gom.changeLogLevel(Level.FINER);
      }
    }
  }

  /**
   * Sets an option to the desired value.
   */
  public void setOptionValue(String optionName, Object optionValue) {
    PlatformBoolean bool = null;
    if(optionValue instanceof Boolean) {
      bool = ((Boolean)optionValue).booleanValue()?tom_make_True():tom_make_False();
      setOptionPlatformValue(optionName,  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make(bool) );
    } else if(optionValue instanceof Integer) {
      Integer v = (Integer) optionValue;
      setOptionPlatformValue(optionName,  tom.platform.adt.platformoption.types.platformvalue.IntegerValue.make(v.intValue()) );
    } else if(optionValue instanceof String) {
      String v = (String) optionValue;
      setOptionPlatformValue(optionName,  tom.platform.adt.platformoption.types.platformvalue.StringValue.make(v) );
    } else {
      throw new RuntimeException("unknown optionValue type: " + optionValue);
    }
    // alert the owner of the change
    OptionOwner owner = getOptionOwnerFromName(optionName);
    owner.optionChanged(optionName, optionValue);
  }

  /**
   * Returns the value of an option. Returns an Object which is a Boolean,
   * a String or an Integer depending on what the option type is.
   *
   * @param optionName the name of the option whose value is seeked
   * @return an Object containing the option's value
   */
  public Object getOptionValue(String name) {
    PlatformOption option = getOptionFromName(name);
    if(option != null) {
      if ( option instanceof tom.platform.adt.platformoption.types.PlatformOption ) {{  tom.platform.adt.platformoption.types.PlatformOption  tomMatch330NameNumberfreshSubject_1=(( tom.platform.adt.platformoption.types.PlatformOption )option);if ( (tomMatch330NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch330NameNumber_freshVar_0= tomMatch330NameNumberfreshSubject_1.getValue() ;if ( (tomMatch330NameNumber_freshVar_0 instanceof tom.platform.adt.platformoption.types.platformvalue.BooleanValue) ) {{  tom.platform.adt.platformoption.types.PlatformBoolean  tomMatch330NameNumber_freshVar_1= tomMatch330NameNumber_freshVar_0.getBooleanValue() ;if ( (tomMatch330NameNumber_freshVar_1 instanceof tom.platform.adt.platformoption.types.platformboolean.True) ) {if ( true ) {
 return Boolean.valueOf(true); }}}}}}if ( (tomMatch330NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch330NameNumber_freshVar_2= tomMatch330NameNumberfreshSubject_1.getValue() ;if ( (tomMatch330NameNumber_freshVar_2 instanceof tom.platform.adt.platformoption.types.platformvalue.BooleanValue) ) {{  tom.platform.adt.platformoption.types.PlatformBoolean  tomMatch330NameNumber_freshVar_3= tomMatch330NameNumber_freshVar_2.getBooleanValue() ;if ( (tomMatch330NameNumber_freshVar_3 instanceof tom.platform.adt.platformoption.types.platformboolean.False) ) {if ( true ) {
 return Boolean.valueOf(false); }}}}}}if ( (tomMatch330NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch330NameNumber_freshVar_4= tomMatch330NameNumberfreshSubject_1.getValue() ;if ( (tomMatch330NameNumber_freshVar_4 instanceof tom.platform.adt.platformoption.types.platformvalue.IntegerValue) ) {{  int  tomMatch330NameNumber_freshVar_5= tomMatch330NameNumber_freshVar_4.getIntegerValue() ;if ( true ) {
 return new Integer(tomMatch330NameNumber_freshVar_5); }}}}}if ( (tomMatch330NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch330NameNumber_freshVar_6= tomMatch330NameNumberfreshSubject_1.getValue() ;if ( (tomMatch330NameNumber_freshVar_6 instanceof tom.platform.adt.platformoption.types.platformvalue.StringValue) ) {{  String  tomMatch330NameNumber_freshVar_7= tomMatch330NameNumber_freshVar_6.getStringValue() ;if ( true ) {
 return tomMatch330NameNumber_freshVar_7; }}}}}}}

    } else {
      getLogger().log(Level.SEVERE,GomMessage.optionNotFound.getMessage(),name);
      throw new RuntimeException();
    }
    return null;
  }

  /**
   * From OptionOwner Interface
   * @return the global options
   */
  public PlatformOptionList getDeclaredOptionList() {
    return globalOptions;
  }

  /**
   * From OptionOwner Interface
   * @return the prerequisites
   */
  public PlatformOptionList getRequiredOptionList() {
    PlatformOptionList prerequisites = tom_empty_list_concPlatformOption();
    // for now, there is no incompatibilities or requirements on options
    return prerequisites;
  }

  public void setOptionManager(OptionManager om) {}

  /**
   * collects the options/services provided by each plugin
   */
  private void collectOptions(List optionOwnerList, List plugins) {
    Iterator owners = optionOwnerList.iterator();
    while(owners.hasNext()) {
      OptionOwner owner = (OptionOwner)owners.next();
      PlatformOptionList list = owner.getDeclaredOptionList();
      owner.setOptionManager((OptionManager)this);
      while(!list.isEmptyconcPlatformOption()) {
        PlatformOption option = list.getHeadconcPlatformOption();
        if ( option instanceof tom.platform.adt.platformoption.types.PlatformOption ) {{  tom.platform.adt.platformoption.types.PlatformOption  tomMatch331NameNumberfreshSubject_1=(( tom.platform.adt.platformoption.types.PlatformOption )option);if ( (tomMatch331NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  String  tomMatch331NameNumber_freshVar_0= tomMatch331NameNumberfreshSubject_1.getName() ;{  String  tomMatch331NameNumber_freshVar_1= tomMatch331NameNumberfreshSubject_1.getAltName() ;{  String  tom_name=tomMatch331NameNumber_freshVar_0;{  String  tom_altName=tomMatch331NameNumber_freshVar_1;if ( true ) {

            setOptionOwnerFromName(tom_name, owner);
            setOptionFromName(tom_name, option);
            if(tom_altName.length() > 0) {
              mapShortNameToName.put(tom_altName,tom_name);
            }
          }}}}}}}}

        list = list.getTailconcPlatformOption();
      }
    }
  }

  /**
   * Checks if every plugin's needs are fulfilled
   */
  private int checkAllOptionsDepedencies(List optionOwnerList) {
    Iterator owners = optionOwnerList.iterator();
    while(owners.hasNext()) {
      OptionOwner plugin = (OptionOwner)owners.next();
      if(!checkOptionDependency(plugin.getRequiredOptionList())) {
        getLogger().log(Level.SEVERE, GomMessage.prerequisitesIssue.getMessage(), plugin.getClass().getName());
        return 1;
      }
    }
    return 0;
  }

  private String getCanonicalName(String name) {
    if(mapShortNameToName.containsKey(name)) {
      return (String)mapShortNameToName.get(name);
    }
    return name;
  }

  private PlatformOption getOptionFromName(String name) {
    PlatformOption option = (PlatformOption)mapNameToOption.get(getCanonicalName(name));
    if(option == null) {
      getLogger().log(Level.WARNING,GomMessage.optionNotFound.getMessage(),getCanonicalName(name));
    }
    return option;
  }

  private PlatformOption setOptionFromName(String name, PlatformOption option) {
    return (PlatformOption)mapNameToOption.put(getCanonicalName(name),option);
  }

  private OptionOwner getOptionOwnerFromName(String name) {
    OptionOwner plugin = (OptionOwner)mapNameToOptionOwner.get(getCanonicalName(name));
    if(plugin == null) {
      getLogger().log(Level.WARNING,GomMessage.optionNotFound.getMessage(),getCanonicalName(name));
    }
    return plugin;
  }

  private void setOptionOwnerFromName(String name, OptionOwner plugin) {
    mapNameToOptionOwner.put(getCanonicalName(name),plugin);
  }

  private void setOptionPlatformValue(String name, PlatformValue value) {
    PlatformOption option = getOptionFromName(name);
    if(option != null) {
      PlatformOption newOption = option.setValue(value);
      Object replaced = setOptionFromName(name, newOption);
      getLogger().log(Level.FINER,GomMessage.setValue.getMessage(),new Object[]{name,value,replaced});
    } else {
      throw new RuntimeException();
    }
  }

  /**
   * Self-explanatory. Displays an help message indicating how to use the compiler.
   */
  private void displayHelp() {
    String beginning = "usage :"
      + "\n\tgom [options] file [... file_n]"
      + "\noptions :";
    StringBuffer buffer = new StringBuffer(beginning);

    buffer.append("\n\t-X <file>:\tDefines an alternate XML configuration file\n");

    for(Iterator it = mapNameToOption.values().iterator(); it.hasNext() ; ) {
      PlatformOption h = (PlatformOption)it.next();
      if ( h instanceof tom.platform.adt.platformoption.types.PlatformOption ) {{  tom.platform.adt.platformoption.types.PlatformOption  tomMatch332NameNumberfreshSubject_1=(( tom.platform.adt.platformoption.types.PlatformOption )h);if ( (tomMatch332NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  String  tomMatch332NameNumber_freshVar_0= tomMatch332NameNumberfreshSubject_1.getName() ;{  String  tomMatch332NameNumber_freshVar_1= tomMatch332NameNumberfreshSubject_1.getAltName() ;{  String  tomMatch332NameNumber_freshVar_2= tomMatch332NameNumberfreshSubject_1.getDescription() ;{  String  tomMatch332NameNumber_freshVar_3= tomMatch332NameNumberfreshSubject_1.getAttrName() ;{  String  tom_altName=tomMatch332NameNumber_freshVar_1;{  String  tom_attrName=tomMatch332NameNumber_freshVar_3;if ( true ) {

          buffer.append("\t--" + tomMatch332NameNumber_freshVar_0);
          if(tom_attrName.length() > 0) {
            buffer.append(" <" + tom_attrName+ ">");
          }
          if(tom_altName.length() > 0) {
            buffer.append(" | -" + tom_altName);
          }
          buffer.append(":\t" + tomMatch332NameNumber_freshVar_2);
          buffer.append("\n");
        }}}}}}}}}}

    }

    System.out.println(buffer.toString());
  }

  /**
   * Self-explanatory. Displays the current version of the Gom compiler.
   */
  public void displayVersion() {
    System.out.println("Gom " + Gom.VERSION + "\n\n"
                       + "Copyright (c) 2000-2007, INRIA, Nancy, France.\n");
  }

  /**
   * Checks if all the options a plugin needs are here.
   *
   * @param list a list of options that must be found with the right value
   * @return true if every option was found with the right value
   */
  private boolean checkOptionDependency(PlatformOptionList requiredOptions) {
    if ( requiredOptions instanceof tom.platform.adt.platformoption.types.PlatformOptionList ) {{  tom.platform.adt.platformoption.types.PlatformOptionList  tomMatch333NameNumberfreshSubject_1=(( tom.platform.adt.platformoption.types.PlatformOptionList )requiredOptions);if ( ((tomMatch333NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption) || (tomMatch333NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption)) ) {{  tom.platform.adt.platformoption.types.PlatformOptionList  tomMatch333NameNumber_freshVar_0=tomMatch333NameNumberfreshSubject_1;if ( tomMatch333NameNumber_freshVar_0.isEmptyconcPlatformOption() ) {if ( true ) {

        return true;
      }}}}if ( ((tomMatch333NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption) || (tomMatch333NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption)) ) {{  tom.platform.adt.platformoption.types.PlatformOptionList  tomMatch333NameNumber_freshVar_1=tomMatch333NameNumberfreshSubject_1;if (!( tomMatch333NameNumber_freshVar_1.isEmptyconcPlatformOption() )) {if ( ( tomMatch333NameNumber_freshVar_1.getHeadconcPlatformOption()  instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  String  tomMatch333NameNumber_freshVar_5=  tomMatch333NameNumber_freshVar_1.getHeadconcPlatformOption() .getName() ;{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch333NameNumber_freshVar_6=  tomMatch333NameNumber_freshVar_1.getHeadconcPlatformOption() .getValue() ;{  String  tom_name=tomMatch333NameNumber_freshVar_5;{  tom.platform.adt.platformoption.types.PlatformValue  tom_value=tomMatch333NameNumber_freshVar_6;{  tom.platform.adt.platformoption.types.PlatformOptionList  tomMatch333NameNumber_freshVar_2= tomMatch333NameNumber_freshVar_1.getTailconcPlatformOption() ;if ( true ) {


        PlatformOption option = getOptionFromName(tom_name);
        if(option !=null) {
          PlatformValue localValue = option.getValue();
          if(tom_value!= localValue) {
            getLogger().log(Level.SEVERE,
                GomMessage.incorrectOptionValue.getMessage(),
                new Object[]{tom_name,tom_value,getOptionValue(tom_name)});
            return false;
          } else {
            return checkOptionDependency(tomMatch333NameNumber_freshVar_2);
          }
        } else {
          getLogger().log(Level.SEVERE,
              GomMessage.incorrectOptionValue.getMessage(),
              new Object[]{tom_name,tom_value,getOptionValue(tom_name)});
          return false;
        }
      }}}}}}}}}}}}

    return false;
  }

  /** logger accessor in case of logging needs*/
  private Logger getLogger() {
    return Logger.getLogger(getClass().getName());
  }

    /**
   * This method takes the arguments given by the user and deduces the options
   * to set, then sets them.
   *
   * @param argumentList
   * @return an array containing the name of the input files
   */
  private List processArguments(String[] argumentList) {
    List inputFiles = new ArrayList();
    StringBuffer imports = new StringBuffer();
    boolean outputEncountered = false;
    boolean destdirEncountered = false;
    int i = 0;
    String argument="";
    try {
      for(; i < argumentList.length; i++) {
        argument = argumentList[i];
        getLogger().log(Level.FINER, "GomOptionManager: processing argument "+i+" \""+argument+"\"");
        if(!argument.startsWith("-") || (argument.equals("-")) ) {
          // input file name, should never start with '-' (except for System.in)
          inputFiles.add(argument);
        } else { // s does start with '-', thus is -or at least should be- an option
          argument = argument.substring(1); // crops the '-'
          if(argument.startsWith("-")) { // if there's another one
            argument = argument.substring(1); // crops the second '-'
          }

          if(argument.equals("X")) {
            // if we're here, the PluginPlatform has already handled the "-X" option
            // and all errors that might occur
            // just skip it,along with its argument
            i++;
            continue;
          }

          if(argument.equals("help") || argument.equals("h")) {
            displayHelp();
            return null;
          }
          if(argument.equals("version") || argument.equals("V")) {
            displayVersion();
            return null;
          }
          if(argument.equals("import") || argument.equals("I")) {
            i++;
            imports.append(argumentList[i] + ":");
          }
          if(argument.equals("destdir") || argument.equals("d")) {
            if(destdirEncountered) {
              getLogger().log(Level.SEVERE, GomMessage.destdirTwice.getMessage());
              return null;
            } else {
              destdirEncountered = true;
            }
          }

          OptionOwner plugin = getOptionOwnerFromName(argument);
          PlatformOption option = getOptionFromName(argument);

          if(option == null || plugin == null) {// option not found
            getLogger().log(Level.SEVERE, GomMessage.invalidOption.getMessage(), argument);
            displayHelp();
            return null;
          } else {
            if ( option instanceof tom.platform.adt.platformoption.types.PlatformOption ) {{  tom.platform.adt.platformoption.types.PlatformOption  tomMatch334NameNumberfreshSubject_1=(( tom.platform.adt.platformoption.types.PlatformOption )option);if ( (tomMatch334NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch334NameNumber_freshVar_0= tomMatch334NameNumberfreshSubject_1.getValue() ;if ( (tomMatch334NameNumber_freshVar_0 instanceof tom.platform.adt.platformoption.types.platformvalue.BooleanValue) ) {if ( true ) {

                setOptionValue(argument, Boolean.TRUE);
              }}}}if ( (tomMatch334NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch334NameNumber_freshVar_1= tomMatch334NameNumberfreshSubject_1.getValue() ;if ( (tomMatch334NameNumber_freshVar_1 instanceof tom.platform.adt.platformoption.types.platformvalue.IntegerValue) ) {if ( true ) {


                String t = argumentList[++i];
                setOptionValue(argument, new Integer(t));
              }}}}if ( (tomMatch334NameNumberfreshSubject_1 instanceof tom.platform.adt.platformoption.types.platformoption.PluginOption) ) {{  tom.platform.adt.platformoption.types.PlatformValue  tomMatch334NameNumber_freshVar_2= tomMatch334NameNumberfreshSubject_1.getValue() ;if ( (tomMatch334NameNumber_freshVar_2 instanceof tom.platform.adt.platformoption.types.platformvalue.StringValue) ) {if ( true ) {


                if ( !( argument.equals("import") || argument.equals("I") ) ) {
                  // "import" is handled in the end
                  String t = argumentList[++i];
                  setOptionValue(argument, t);
                }
              }}}}}}

          }
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      getLogger().log(Level.SEVERE, GomMessage.incompleteOption.getMessage(), argument);
      displayHelp();
      return null;
    }

    setOptionValue("import",imports.toString());

    if(inputFiles.isEmpty()) {
      getLogger().log(Level.SEVERE, GomMessage.noFileToCompile.getMessage());
      displayHelp();
      return null;
    }
    return inputFiles;
  }
}
