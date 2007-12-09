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

package tom.gom.expander;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.gom.GomMessage;
import tom.gom.GomStreamManager;
import tom.gom.tools.GomEnvironment;
import tom.gom.adt.gom.*;
import tom.gom.adt.gom.types.*;
import tom.gom.parser.AST2Gom;
import tom.gom.parser.ANTLRMapperGomLexer;
import tom.gom.parser.ANTLRMapperGomParser;
import tom.antlrmapper.ATermAST;
import tom.platform.PlatformLogRecord;
import antlr.RecognitionException;
import antlr.TokenStreamException;

public class Expander {
  private GomStreamManager streamManager;

  %include { ../adt/gom/Gom.tom}

  public Expander(GomStreamManager streamManager) {
    this.streamManager = streamManager;
  }

  private GomEnvironment environment() {
    return GomEnvironment.getInstance();
  }

  /*
   * Compute the transitive closure of imported modules
   */
  public GomModuleList expand(GomModule module) {
    GomModuleList result = `ConcGomModule(module);
    Set alreadyParsedModule = new HashSet();
    alreadyParsedModule.add(module.getModuleName());
    Set moduleToAnalyse = generateModuleToAnalyseSet(module, alreadyParsedModule);
    getLogger().log(Level.FINER, "GomExpander:moduleToAnalyse {0}",
        new Object[]{moduleToAnalyse});

    while (!moduleToAnalyse.isEmpty()) {
      HashSet newModuleToAnalyse = new HashSet();
      Iterator it = moduleToAnalyse.iterator();

      while(it.hasNext()) {
        GomModuleName moduleNameName = (GomModuleName)it.next();
        String moduleName = moduleNameName.getName();

        if(!environment().isBuiltin(moduleName)) {
          if(!alreadyParsedModule.contains(moduleNameName)) {
            GomModule importedModule = parse(moduleName);
            if(importedModule == null) {
              return null;
            }
            result = `ConcGomModule(result*, importedModule);
            alreadyParsedModule.add(moduleNameName);
            newModuleToAnalyse.addAll(generateModuleToAnalyseSet(importedModule,alreadyParsedModule));
	  }
        } else {
          environment().markUsedBuiltin(moduleName); 
        }
      }
      moduleToAnalyse = newModuleToAnalyse;
    }
    return result;
  }

  /*
   * Compute immediate imported modules where already parsed modules are removed
   */
  private Set generateModuleToAnalyseSet(GomModule module, Set alreadyParsedModule) {
    HashSet moduleToAnalyse = new HashSet();
    ImportList importedModules = getImportList(module);
    while(!importedModules.isEmptyConcImportedModule()) {
      GomModuleName name = importedModules.getHeadConcImportedModule().getModuleName();
      if(!alreadyParsedModule.contains(name)) {
        moduleToAnalyse.add(name);
      }
      importedModules = importedModules.getTailConcImportedModule();
    }
    //System.out.println("*** generateModuleToAnalyseSet = " + moduleToAnalyse);
    return moduleToAnalyse;
  }

  private GomModule parse(String moduleName) {
    getLogger().log(Level.FINE, "Seeking for file {0}",
        new Object[]{moduleName});
    GomModule result = null;
    File importedModuleFile = findModuleFile(moduleName);
    if(importedModuleFile == null) {
      getLogger().log(Level.SEVERE,
          GomMessage.moduleNotFound.getMessage(),
          new Object[]{moduleName});
      return null;
    }
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(importedModuleFile);
    } catch (FileNotFoundException e) {
      getLogger().log(Level.SEVERE,
          GomMessage.fileNotFound.getMessage(),
          new Object[]{moduleName+".gom"});
      return null;
    }
    ANTLRMapperGomLexer lexer = new ANTLRMapperGomLexer(inputStream);
    ANTLRMapperGomParser parser = new ANTLRMapperGomParser(lexer,"GomIncludeParser");
    try {
      parser.setASTNodeClass("tom.antlrmapper.ATermAST");
      parser.module();
      ATermAST t = (ATermAST)parser.getAST();
      result = AST2Gom.getGomModule(t,streamManager);

    } catch (RecognitionException re) {
      getLogger().log(new PlatformLogRecord(Level.SEVERE,
            GomMessage.detailedParseException,
            re.getMessage(),moduleName+".gom", lexer.getLine()));
      return null;
    } catch(TokenStreamException tse) {
      getLogger().log(new PlatformLogRecord(Level.SEVERE,
            GomMessage.detailedParseException,
            tse.getMessage(),moduleName+".gom", lexer.getLine()));
      return null;
    }
    return result;
  }

  /**
   * find a module locally or thanks to the stream manager import list
   */
  private File findModuleFile(String moduleName) {
    String extendedModuleName = moduleName+".gom";
    File f = new File(extendedModuleName);
    if(f.exists()) {
      return f;
    }
    return streamManager.findModuleFile(extendedModuleName);
  }

  /** the class logger instance*/
  private Logger getLogger() {
    return Logger.getLogger(getClass().getName());
  }

  public ImportList getImportList(GomModule module) {
    ImportList imports = `ConcImportedModule();
    %match(GomModule module) {
      GomModule(_,ConcSection(_*,Imports(importList),_*)) -> {
        imports = `ConcImportedModule(importList*,imports*);
      }
    }
    return imports;
  }
}
