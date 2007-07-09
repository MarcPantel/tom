/* Generated by TOM (version 2.5): Do not edit this file *//*
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
  private String packagePrefix;

  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */   /* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_GomModuleList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomModuleList(Object t) { return  t instanceof tom.gom.adt.gom.types.GomModuleList ;}private static boolean tom_equal_term_SectionList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_SectionList(Object t) { return  t instanceof tom.gom.adt.gom.types.SectionList ;}private static boolean tom_equal_term_GomModule(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomModule(Object t) { return  t instanceof tom.gom.adt.gom.types.GomModule ;}private static boolean tom_equal_term_Section(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Section(Object t) { return  t instanceof tom.gom.adt.gom.types.Section ;}private static boolean tom_equal_term_ImportList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ImportList(Object t) { return  t instanceof tom.gom.adt.gom.types.ImportList ;}private static boolean tom_equal_term_GomModuleName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_GomModuleName(Object t) { return  t instanceof tom.gom.adt.gom.types.GomModuleName ;}private static boolean tom_equal_term_ImportedModule(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ImportedModule(Object t) { return  t instanceof tom.gom.adt.gom.types.ImportedModule ;}private static boolean tom_is_fun_sym_GomModule( tom.gom.adt.gom.types.GomModule  t) { return  t instanceof tom.gom.adt.gom.types.gommodule.GomModule ;}private static  tom.gom.adt.gom.types.GomModuleName  tom_get_slot_GomModule_ModuleName( tom.gom.adt.gom.types.GomModule  t) { return  t.getModuleName() ;}private static  tom.gom.adt.gom.types.SectionList  tom_get_slot_GomModule_SectionList( tom.gom.adt.gom.types.GomModule  t) { return  t.getSectionList() ;}private static boolean tom_is_fun_sym_Imports( tom.gom.adt.gom.types.Section  t) { return  t instanceof tom.gom.adt.gom.types.section.Imports ;}private static  tom.gom.adt.gom.types.ImportList  tom_get_slot_Imports_ImportList( tom.gom.adt.gom.types.Section  t) { return  t.getImportList() ;}private static boolean tom_is_fun_sym_concGomModule( tom.gom.adt.gom.types.GomModuleList  t) { return  t instanceof tom.gom.adt.gom.types.gommodulelist.ConsconcGomModule || t instanceof tom.gom.adt.gom.types.gommodulelist.EmptyconcGomModule ;}private static  tom.gom.adt.gom.types.GomModuleList  tom_empty_list_concGomModule() { return  tom.gom.adt.gom.types.gommodulelist.EmptyconcGomModule.make() ; }private static  tom.gom.adt.gom.types.GomModuleList  tom_cons_list_concGomModule( tom.gom.adt.gom.types.GomModule  e,  tom.gom.adt.gom.types.GomModuleList  l) { return  tom.gom.adt.gom.types.gommodulelist.ConsconcGomModule.make(e,l) ; }private static  tom.gom.adt.gom.types.GomModule  tom_get_head_concGomModule_GomModuleList( tom.gom.adt.gom.types.GomModuleList  l) { return  l.getHeadconcGomModule() ;}private static  tom.gom.adt.gom.types.GomModuleList  tom_get_tail_concGomModule_GomModuleList( tom.gom.adt.gom.types.GomModuleList  l) { return  l.getTailconcGomModule() ;}private static boolean tom_is_empty_concGomModule_GomModuleList( tom.gom.adt.gom.types.GomModuleList  l) { return  l.isEmptyconcGomModule() ;}   private static   tom.gom.adt.gom.types.GomModuleList  tom_append_list_concGomModule( tom.gom.adt.gom.types.GomModuleList l1,  tom.gom.adt.gom.types.GomModuleList  l2) {     if(tom_is_empty_concGomModule_GomModuleList(l1)) {       return l2;     } else if(tom_is_empty_concGomModule_GomModuleList(l2)) {       return l1;     } else if(tom_is_empty_concGomModule_GomModuleList(tom_get_tail_concGomModule_GomModuleList(l1))) {       return ( tom.gom.adt.gom.types.GomModuleList )tom_cons_list_concGomModule(tom_get_head_concGomModule_GomModuleList(l1),l2);     } else {       return ( tom.gom.adt.gom.types.GomModuleList )tom_cons_list_concGomModule(tom_get_head_concGomModule_GomModuleList(l1),tom_append_list_concGomModule(tom_get_tail_concGomModule_GomModuleList(l1),l2));     }   }   private static   tom.gom.adt.gom.types.GomModuleList  tom_get_slice_concGomModule( tom.gom.adt.gom.types.GomModuleList  begin,  tom.gom.adt.gom.types.GomModuleList  end, tom.gom.adt.gom.types.GomModuleList  tail) {     if(tom_equal_term_GomModuleList(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.gom.types.GomModuleList )tom_cons_list_concGomModule(tom_get_head_concGomModule_GomModuleList(begin),( tom.gom.adt.gom.types.GomModuleList )tom_get_slice_concGomModule(tom_get_tail_concGomModule_GomModuleList(begin),end,tail));     }   }   private static boolean tom_is_fun_sym_concSection( tom.gom.adt.gom.types.SectionList  t) { return  t instanceof tom.gom.adt.gom.types.sectionlist.ConsconcSection || t instanceof tom.gom.adt.gom.types.sectionlist.EmptyconcSection ;}private static  tom.gom.adt.gom.types.SectionList  tom_empty_list_concSection() { return  tom.gom.adt.gom.types.sectionlist.EmptyconcSection.make() ; }private static  tom.gom.adt.gom.types.SectionList  tom_cons_list_concSection( tom.gom.adt.gom.types.Section  e,  tom.gom.adt.gom.types.SectionList  l) { return  tom.gom.adt.gom.types.sectionlist.ConsconcSection.make(e,l) ; }private static  tom.gom.adt.gom.types.Section  tom_get_head_concSection_SectionList( tom.gom.adt.gom.types.SectionList  l) { return  l.getHeadconcSection() ;}private static  tom.gom.adt.gom.types.SectionList  tom_get_tail_concSection_SectionList( tom.gom.adt.gom.types.SectionList  l) { return  l.getTailconcSection() ;}private static boolean tom_is_empty_concSection_SectionList( tom.gom.adt.gom.types.SectionList  l) { return  l.isEmptyconcSection() ;}   private static   tom.gom.adt.gom.types.SectionList  tom_append_list_concSection( tom.gom.adt.gom.types.SectionList l1,  tom.gom.adt.gom.types.SectionList  l2) {     if(tom_is_empty_concSection_SectionList(l1)) {       return l2;     } else if(tom_is_empty_concSection_SectionList(l2)) {       return l1;     } else if(tom_is_empty_concSection_SectionList(tom_get_tail_concSection_SectionList(l1))) {       return ( tom.gom.adt.gom.types.SectionList )tom_cons_list_concSection(tom_get_head_concSection_SectionList(l1),l2);     } else {       return ( tom.gom.adt.gom.types.SectionList )tom_cons_list_concSection(tom_get_head_concSection_SectionList(l1),tom_append_list_concSection(tom_get_tail_concSection_SectionList(l1),l2));     }   }   private static   tom.gom.adt.gom.types.SectionList  tom_get_slice_concSection( tom.gom.adt.gom.types.SectionList  begin,  tom.gom.adt.gom.types.SectionList  end, tom.gom.adt.gom.types.SectionList  tail) {     if(tom_equal_term_SectionList(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.gom.types.SectionList )tom_cons_list_concSection(tom_get_head_concSection_SectionList(begin),( tom.gom.adt.gom.types.SectionList )tom_get_slice_concSection(tom_get_tail_concSection_SectionList(begin),end,tail));     }   }   private static boolean tom_is_fun_sym_concImportedModule( tom.gom.adt.gom.types.ImportList  t) { return  t instanceof tom.gom.adt.gom.types.importlist.ConsconcImportedModule || t instanceof tom.gom.adt.gom.types.importlist.EmptyconcImportedModule ;}private static  tom.gom.adt.gom.types.ImportList  tom_empty_list_concImportedModule() { return  tom.gom.adt.gom.types.importlist.EmptyconcImportedModule.make() ; }private static  tom.gom.adt.gom.types.ImportList  tom_cons_list_concImportedModule( tom.gom.adt.gom.types.ImportedModule  e,  tom.gom.adt.gom.types.ImportList  l) { return  tom.gom.adt.gom.types.importlist.ConsconcImportedModule.make(e,l) ; }private static  tom.gom.adt.gom.types.ImportedModule  tom_get_head_concImportedModule_ImportList( tom.gom.adt.gom.types.ImportList  l) { return  l.getHeadconcImportedModule() ;}private static  tom.gom.adt.gom.types.ImportList  tom_get_tail_concImportedModule_ImportList( tom.gom.adt.gom.types.ImportList  l) { return  l.getTailconcImportedModule() ;}private static boolean tom_is_empty_concImportedModule_ImportList( tom.gom.adt.gom.types.ImportList  l) { return  l.isEmptyconcImportedModule() ;}   private static   tom.gom.adt.gom.types.ImportList  tom_append_list_concImportedModule( tom.gom.adt.gom.types.ImportList l1,  tom.gom.adt.gom.types.ImportList  l2) {     if(tom_is_empty_concImportedModule_ImportList(l1)) {       return l2;     } else if(tom_is_empty_concImportedModule_ImportList(l2)) {       return l1;     } else if(tom_is_empty_concImportedModule_ImportList(tom_get_tail_concImportedModule_ImportList(l1))) {       return ( tom.gom.adt.gom.types.ImportList )tom_cons_list_concImportedModule(tom_get_head_concImportedModule_ImportList(l1),l2);     } else {       return ( tom.gom.adt.gom.types.ImportList )tom_cons_list_concImportedModule(tom_get_head_concImportedModule_ImportList(l1),tom_append_list_concImportedModule(tom_get_tail_concImportedModule_ImportList(l1),l2));     }   }   private static   tom.gom.adt.gom.types.ImportList  tom_get_slice_concImportedModule( tom.gom.adt.gom.types.ImportList  begin,  tom.gom.adt.gom.types.ImportList  end, tom.gom.adt.gom.types.ImportList  tail) {     if(tom_equal_term_ImportList(begin,end)) {       return tail;     } else {       return ( tom.gom.adt.gom.types.ImportList )tom_cons_list_concImportedModule(tom_get_head_concImportedModule_ImportList(begin),( tom.gom.adt.gom.types.ImportList )tom_get_slice_concImportedModule(tom_get_tail_concImportedModule_ImportList(begin),end,tail));     }   }    

  public Expander(GomStreamManager streamManager) {
    this.streamManager = streamManager;
    packagePrefix= streamManager.getPackagePath().replace(File.separatorChar,'.');
  }

  private GomEnvironment environment() {
    return GomEnvironment.getInstance();
  }

  /*
   * Compute the transitive closure of imported modules
   */
  public GomModuleList expand(GomModule module) {
    GomModuleList result = tom_cons_list_concGomModule(module,tom_empty_list_concGomModule());
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
            result = tom_append_list_concGomModule(result,tom_cons_list_concGomModule(importedModule,tom_empty_list_concGomModule()));
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
    while(!importedModules.isEmptyconcImportedModule()) {
      GomModuleName name = importedModules.getHeadconcImportedModule().getModuleName();
      if(!alreadyParsedModule.contains(name)) {
        moduleToAnalyse.add(name);
      }
      importedModules = importedModules.getTailconcImportedModule();
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
    ImportList imports = tom_empty_list_concImportedModule();
    if (tom_is_sort_GomModule(module)) {{  tom.gom.adt.gom.types.GomModule  tomMatch418NameNumberfreshSubject_1=(( tom.gom.adt.gom.types.GomModule )module);if (tom_is_fun_sym_GomModule(tomMatch418NameNumberfreshSubject_1)) {{  tom.gom.adt.gom.types.GomModuleName  tomMatch418NameNumber_freshVar_0=tom_get_slot_GomModule_ModuleName(tomMatch418NameNumberfreshSubject_1);{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_freshVar_1=tom_get_slot_GomModule_SectionList(tomMatch418NameNumberfreshSubject_1);if (tom_is_fun_sym_concSection(tomMatch418NameNumber_freshVar_1)) {{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_freshVar_2=tomMatch418NameNumber_freshVar_1;{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_begin_4=tomMatch418NameNumber_freshVar_2;{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_end_5=tomMatch418NameNumber_freshVar_2;do {{{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_freshVar_3=tomMatch418NameNumber_end_5;if (!(tom_is_empty_concSection_SectionList(tomMatch418NameNumber_freshVar_3))) {if (tom_is_fun_sym_Imports(tom_get_head_concSection_SectionList(tomMatch418NameNumber_freshVar_3))) {{  tom.gom.adt.gom.types.ImportList  tomMatch418NameNumber_freshVar_8=tom_get_slot_Imports_ImportList(tom_get_head_concSection_SectionList(tomMatch418NameNumber_freshVar_3));{  tom.gom.adt.gom.types.SectionList  tomMatch418NameNumber_freshVar_6=tom_get_tail_concSection_SectionList(tomMatch418NameNumber_freshVar_3);if ( true ) {

        imports = tom_append_list_concImportedModule(tomMatch418NameNumber_freshVar_8,tom_append_list_concImportedModule(imports,tom_empty_list_concImportedModule()));
      }}}}}}if (tom_is_empty_concSection_SectionList(tomMatch418NameNumber_end_5)) {tomMatch418NameNumber_end_5=tomMatch418NameNumber_begin_4;} else {tomMatch418NameNumber_end_5=tom_get_tail_concSection_SectionList(tomMatch418NameNumber_end_5);}}} while(!(tom_equal_term_SectionList(tomMatch418NameNumber_end_5, tomMatch418NameNumber_begin_4)));}}}}}}}}}

    return imports;
  }
}
