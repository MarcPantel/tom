/*
*
* GOM
*
* Copyright (c) 2006-2010, INPL, INRIA
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

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.gom.GomMessage;
import tom.gom.GomStreamManager;
import tom.gom.tools.GomEnvironment;
import tom.gom.adt.gom.*;
import tom.gom.adt.gom.types.*;
import tom.gom.adt.gom.types.gommodulelist.ConcGomModule;
import tom.gom.tools.error.GomRuntimeException;

public class TypeExpander {



  private static   tom.gom.adt.gom.types.AlternativeList  tom_append_list_ConcAlternative( tom.gom.adt.gom.types.AlternativeList l1,  tom.gom.adt.gom.types.AlternativeList  l2) {
    if( l1.isEmptyConcAlternative() ) {
      return l2;
    } else if( l2.isEmptyConcAlternative() ) {
      return l1;
    } else if(  l1.getTailConcAlternative() .isEmptyConcAlternative() ) {
      return  tom.gom.adt.gom.types.alternativelist.ConsConcAlternative.make( l1.getHeadConcAlternative() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.alternativelist.ConsConcAlternative.make( l1.getHeadConcAlternative() ,tom_append_list_ConcAlternative( l1.getTailConcAlternative() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.AlternativeList  tom_get_slice_ConcAlternative( tom.gom.adt.gom.types.AlternativeList  begin,  tom.gom.adt.gom.types.AlternativeList  end, tom.gom.adt.gom.types.AlternativeList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcAlternative()  ||  (end== tom.gom.adt.gom.types.alternativelist.EmptyConcAlternative.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.alternativelist.ConsConcAlternative.make( begin.getHeadConcAlternative() ,( tom.gom.adt.gom.types.AlternativeList )tom_get_slice_ConcAlternative( begin.getTailConcAlternative() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.ModuleList  tom_append_list_ConcModule( tom.gom.adt.gom.types.ModuleList l1,  tom.gom.adt.gom.types.ModuleList  l2) {
    if( l1.isEmptyConcModule() ) {
      return l2;
    } else if( l2.isEmptyConcModule() ) {
      return l1;
    } else if(  l1.getTailConcModule() .isEmptyConcModule() ) {
      return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( l1.getHeadConcModule() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( l1.getHeadConcModule() ,tom_append_list_ConcModule( l1.getTailConcModule() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.ModuleList  tom_get_slice_ConcModule( tom.gom.adt.gom.types.ModuleList  begin,  tom.gom.adt.gom.types.ModuleList  end, tom.gom.adt.gom.types.ModuleList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcModule()  ||  (end== tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.modulelist.ConsConcModule.make( begin.getHeadConcModule() ,( tom.gom.adt.gom.types.ModuleList )tom_get_slice_ConcModule( begin.getTailConcModule() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.ImportList  tom_append_list_ConcImportedModule( tom.gom.adt.gom.types.ImportList l1,  tom.gom.adt.gom.types.ImportList  l2) {
    if( l1.isEmptyConcImportedModule() ) {
      return l2;
    } else if( l2.isEmptyConcImportedModule() ) {
      return l1;
    } else if(  l1.getTailConcImportedModule() .isEmptyConcImportedModule() ) {
      return  tom.gom.adt.gom.types.importlist.ConsConcImportedModule.make( l1.getHeadConcImportedModule() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.importlist.ConsConcImportedModule.make( l1.getHeadConcImportedModule() ,tom_append_list_ConcImportedModule( l1.getTailConcImportedModule() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.ImportList  tom_get_slice_ConcImportedModule( tom.gom.adt.gom.types.ImportList  begin,  tom.gom.adt.gom.types.ImportList  end, tom.gom.adt.gom.types.ImportList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcImportedModule()  ||  (end== tom.gom.adt.gom.types.importlist.EmptyConcImportedModule.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.importlist.ConsConcImportedModule.make( begin.getHeadConcImportedModule() ,( tom.gom.adt.gom.types.ImportList )tom_get_slice_ConcImportedModule( begin.getTailConcImportedModule() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.ProductionList  tom_append_list_ConcProduction( tom.gom.adt.gom.types.ProductionList l1,  tom.gom.adt.gom.types.ProductionList  l2) {
    if( l1.isEmptyConcProduction() ) {
      return l2;
    } else if( l2.isEmptyConcProduction() ) {
      return l1;
    } else if(  l1.getTailConcProduction() .isEmptyConcProduction() ) {
      return  tom.gom.adt.gom.types.productionlist.ConsConcProduction.make( l1.getHeadConcProduction() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.productionlist.ConsConcProduction.make( l1.getHeadConcProduction() ,tom_append_list_ConcProduction( l1.getTailConcProduction() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.ProductionList  tom_get_slice_ConcProduction( tom.gom.adt.gom.types.ProductionList  begin,  tom.gom.adt.gom.types.ProductionList  end, tom.gom.adt.gom.types.ProductionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcProduction()  ||  (end== tom.gom.adt.gom.types.productionlist.EmptyConcProduction.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.productionlist.ConsConcProduction.make( begin.getHeadConcProduction() ,( tom.gom.adt.gom.types.ProductionList )tom_get_slice_ConcProduction( begin.getTailConcProduction() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.GomModuleList  tom_append_list_ConcGomModule( tom.gom.adt.gom.types.GomModuleList l1,  tom.gom.adt.gom.types.GomModuleList  l2) {
    if( l1.isEmptyConcGomModule() ) {
      return l2;
    } else if( l2.isEmptyConcGomModule() ) {
      return l1;
    } else if(  l1.getTailConcGomModule() .isEmptyConcGomModule() ) {
      return  tom.gom.adt.gom.types.gommodulelist.ConsConcGomModule.make( l1.getHeadConcGomModule() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.gommodulelist.ConsConcGomModule.make( l1.getHeadConcGomModule() ,tom_append_list_ConcGomModule( l1.getTailConcGomModule() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.GomModuleList  tom_get_slice_ConcGomModule( tom.gom.adt.gom.types.GomModuleList  begin,  tom.gom.adt.gom.types.GomModuleList  end, tom.gom.adt.gom.types.GomModuleList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcGomModule()  ||  (end== tom.gom.adt.gom.types.gommodulelist.EmptyConcGomModule.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.gommodulelist.ConsConcGomModule.make( begin.getHeadConcGomModule() ,( tom.gom.adt.gom.types.GomModuleList )tom_get_slice_ConcGomModule( begin.getTailConcGomModule() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.SectionList  tom_append_list_ConcSection( tom.gom.adt.gom.types.SectionList l1,  tom.gom.adt.gom.types.SectionList  l2) {
    if( l1.isEmptyConcSection() ) {
      return l2;
    } else if( l2.isEmptyConcSection() ) {
      return l1;
    } else if(  l1.getTailConcSection() .isEmptyConcSection() ) {
      return  tom.gom.adt.gom.types.sectionlist.ConsConcSection.make( l1.getHeadConcSection() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.sectionlist.ConsConcSection.make( l1.getHeadConcSection() ,tom_append_list_ConcSection( l1.getTailConcSection() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.SectionList  tom_get_slice_ConcSection( tom.gom.adt.gom.types.SectionList  begin,  tom.gom.adt.gom.types.SectionList  end, tom.gom.adt.gom.types.SectionList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcSection()  ||  (end== tom.gom.adt.gom.types.sectionlist.EmptyConcSection.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.sectionlist.ConsConcSection.make( begin.getHeadConcSection() ,( tom.gom.adt.gom.types.SectionList )tom_get_slice_ConcSection( begin.getTailConcSection() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.SlotList  tom_append_list_ConcSlot( tom.gom.adt.gom.types.SlotList l1,  tom.gom.adt.gom.types.SlotList  l2) {
    if( l1.isEmptyConcSlot() ) {
      return l2;
    } else if( l2.isEmptyConcSlot() ) {
      return l1;
    } else if(  l1.getTailConcSlot() .isEmptyConcSlot() ) {
      return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( l1.getHeadConcSlot() ,tom_append_list_ConcSlot( l1.getTailConcSlot() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.SlotList  tom_get_slice_ConcSlot( tom.gom.adt.gom.types.SlotList  begin,  tom.gom.adt.gom.types.SlotList  end, tom.gom.adt.gom.types.SlotList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcSlot()  ||  (end== tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( begin.getHeadConcSlot() ,( tom.gom.adt.gom.types.SlotList )tom_get_slice_ConcSlot( begin.getTailConcSlot() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.SortDeclList  tom_append_list_ConcSortDecl( tom.gom.adt.gom.types.SortDeclList l1,  tom.gom.adt.gom.types.SortDeclList  l2) {
    if( l1.isEmptyConcSortDecl() ) {
      return l2;
    } else if( l2.isEmptyConcSortDecl() ) {
      return l1;
    } else if(  l1.getTailConcSortDecl() .isEmptyConcSortDecl() ) {
      return  tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl.make( l1.getHeadConcSortDecl() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl.make( l1.getHeadConcSortDecl() ,tom_append_list_ConcSortDecl( l1.getTailConcSortDecl() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.SortDeclList  tom_get_slice_ConcSortDecl( tom.gom.adt.gom.types.SortDeclList  begin,  tom.gom.adt.gom.types.SortDeclList  end, tom.gom.adt.gom.types.SortDeclList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcSortDecl()  ||  (end== tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl.make( begin.getHeadConcSortDecl() ,( tom.gom.adt.gom.types.SortDeclList )tom_get_slice_ConcSortDecl( begin.getTailConcSortDecl() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.ModuleDeclList  tom_append_list_ConcModuleDecl( tom.gom.adt.gom.types.ModuleDeclList l1,  tom.gom.adt.gom.types.ModuleDeclList  l2) {
    if( l1.isEmptyConcModuleDecl() ) {
      return l2;
    } else if( l2.isEmptyConcModuleDecl() ) {
      return l1;
    } else if(  l1.getTailConcModuleDecl() .isEmptyConcModuleDecl() ) {
      return  tom.gom.adt.gom.types.moduledecllist.ConsConcModuleDecl.make( l1.getHeadConcModuleDecl() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.moduledecllist.ConsConcModuleDecl.make( l1.getHeadConcModuleDecl() ,tom_append_list_ConcModuleDecl( l1.getTailConcModuleDecl() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.ModuleDeclList  tom_get_slice_ConcModuleDecl( tom.gom.adt.gom.types.ModuleDeclList  begin,  tom.gom.adt.gom.types.ModuleDeclList  end, tom.gom.adt.gom.types.ModuleDeclList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcModuleDecl()  ||  (end== tom.gom.adt.gom.types.moduledecllist.EmptyConcModuleDecl.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.moduledecllist.ConsConcModuleDecl.make( begin.getHeadConcModuleDecl() ,( tom.gom.adt.gom.types.ModuleDeclList )tom_get_slice_ConcModuleDecl( begin.getTailConcModuleDecl() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.OperatorDeclList  tom_append_list_ConcOperator( tom.gom.adt.gom.types.OperatorDeclList l1,  tom.gom.adt.gom.types.OperatorDeclList  l2) {
    if( l1.isEmptyConcOperator() ) {
      return l2;
    } else if( l2.isEmptyConcOperator() ) {
      return l1;
    } else if(  l1.getTailConcOperator() .isEmptyConcOperator() ) {
      return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( l1.getHeadConcOperator() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( l1.getHeadConcOperator() ,tom_append_list_ConcOperator( l1.getTailConcOperator() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.OperatorDeclList  tom_get_slice_ConcOperator( tom.gom.adt.gom.types.OperatorDeclList  begin,  tom.gom.adt.gom.types.OperatorDeclList  end, tom.gom.adt.gom.types.OperatorDeclList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcOperator()  ||  (end== tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make( begin.getHeadConcOperator() ,( tom.gom.adt.gom.types.OperatorDeclList )tom_get_slice_ConcOperator( begin.getTailConcOperator() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.FieldList  tom_append_list_ConcField( tom.gom.adt.gom.types.FieldList l1,  tom.gom.adt.gom.types.FieldList  l2) {
    if( l1.isEmptyConcField() ) {
      return l2;
    } else if( l2.isEmptyConcField() ) {
      return l1;
    } else if(  l1.getTailConcField() .isEmptyConcField() ) {
      return  tom.gom.adt.gom.types.fieldlist.ConsConcField.make( l1.getHeadConcField() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.fieldlist.ConsConcField.make( l1.getHeadConcField() ,tom_append_list_ConcField( l1.getTailConcField() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.FieldList  tom_get_slice_ConcField( tom.gom.adt.gom.types.FieldList  begin,  tom.gom.adt.gom.types.FieldList  end, tom.gom.adt.gom.types.FieldList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcField()  ||  (end== tom.gom.adt.gom.types.fieldlist.EmptyConcField.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.fieldlist.ConsConcField.make( begin.getHeadConcField() ,( tom.gom.adt.gom.types.FieldList )tom_get_slice_ConcField( begin.getTailConcField() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.GrammarList  tom_append_list_ConcGrammar( tom.gom.adt.gom.types.GrammarList l1,  tom.gom.adt.gom.types.GrammarList  l2) {
    if( l1.isEmptyConcGrammar() ) {
      return l2;
    } else if( l2.isEmptyConcGrammar() ) {
      return l1;
    } else if(  l1.getTailConcGrammar() .isEmptyConcGrammar() ) {
      return  tom.gom.adt.gom.types.grammarlist.ConsConcGrammar.make( l1.getHeadConcGrammar() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.grammarlist.ConsConcGrammar.make( l1.getHeadConcGrammar() ,tom_append_list_ConcGrammar( l1.getTailConcGrammar() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.GrammarList  tom_get_slice_ConcGrammar( tom.gom.adt.gom.types.GrammarList  begin,  tom.gom.adt.gom.types.GrammarList  end, tom.gom.adt.gom.types.GrammarList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcGrammar()  ||  (end== tom.gom.adt.gom.types.grammarlist.EmptyConcGrammar.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.grammarlist.ConsConcGrammar.make( begin.getHeadConcGrammar() ,( tom.gom.adt.gom.types.GrammarList )tom_get_slice_ConcGrammar( begin.getTailConcGrammar() ,end,tail)) ;
  }
  
  private static   tom.gom.adt.gom.types.SortList  tom_append_list_ConcSort( tom.gom.adt.gom.types.SortList l1,  tom.gom.adt.gom.types.SortList  l2) {
    if( l1.isEmptyConcSort() ) {
      return l2;
    } else if( l2.isEmptyConcSort() ) {
      return l1;
    } else if(  l1.getTailConcSort() .isEmptyConcSort() ) {
      return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( l1.getHeadConcSort() ,l2) ;
    } else {
      return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( l1.getHeadConcSort() ,tom_append_list_ConcSort( l1.getTailConcSort() ,l2)) ;
    }
  }
  private static   tom.gom.adt.gom.types.SortList  tom_get_slice_ConcSort( tom.gom.adt.gom.types.SortList  begin,  tom.gom.adt.gom.types.SortList  end, tom.gom.adt.gom.types.SortList  tail) {
    if( (begin==end) ) {
      return tail;
    } else if( (end==tail)  && ( end.isEmptyConcSort()  ||  (end== tom.gom.adt.gom.types.sortlist.EmptyConcSort.make() ) )) {
      /* code to avoid a call to make, and thus to avoid looping during list-matching */
      return begin;
    }
    return  tom.gom.adt.gom.types.sortlist.ConsConcSort.make( begin.getHeadConcSort() ,( tom.gom.adt.gom.types.SortList )tom_get_slice_ConcSort( begin.getTailConcSort() ,end,tail)) ;
  }
  

private GomEnvironment gomEnvironment;

public TypeExpander(GomEnvironment gomEnvironment) {
this.gomEnvironment = gomEnvironment;
}

public GomEnvironment getGomEnvironment() {
return this.gomEnvironment;
}

public GomStreamManager getStreamManager() {
return this.gomEnvironment.getStreamManager();
}

/**
* We try here to get full sort definitions for each constructs
* Once the structure is correctly build, we can attach the hooks
*/
public ModuleList expand(GomModuleList gomModuleList) {
if (!(gomModuleList instanceof ConcGomModule)) {
throw new RuntimeException("A GomModuleList should be a list");
}
ConcGomModule moduleList = (ConcGomModule) gomModuleList;

/* put a map giving all imported modules for each module in the path */
buildDependencyMap(moduleList);

/* collect all sort declarations */
SortDeclList sortDeclList = 
 tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl.make() ;
/* The sorts declared in each module */
Map<ModuleDecl,SortDeclList> sortsForModule =
new HashMap<ModuleDecl,SortDeclList>();
for (GomModule module : moduleList) {
Collection<SortDecl> decls = getSortDeclarations(module);

Collection<SortDecl> implicitdecls = getSortDeclarationInCodomain(module);

/* Check that there are no implicit sort declarations
* Also, check that declared sorts have at least an operator
*/
if(!decls.containsAll(implicitdecls)) {
// whine about non declared sorts
Collection<SortDecl> undeclaredSorts = new HashSet<SortDecl>();
undeclaredSorts.addAll(implicitdecls);
undeclaredSorts.removeAll(decls);
GomMessage.warning(getLogger(),null,0,
GomMessage.undeclaredSorts,
new Object[]{showSortList(undeclaredSorts)});
}
if(!implicitdecls.containsAll(decls)) {
// whine about sorts without operators: this is a real error
Collection<SortDecl> emptySorts = new HashSet<SortDecl>();
emptySorts.addAll(decls);
emptySorts.removeAll(implicitdecls);
GomMessage.error(getLogger(),null,0,
GomMessage.emptySorts,
new Object[]{showSortList(emptySorts)});
return 
 tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() ;
}
for (SortDecl decl : implicitdecls) {
sortDeclList = 
 tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl.make(decl,tom_append_list_ConcSortDecl(sortDeclList, tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl.make() )) ;
}
/* Fills sortsForModule */
SortDeclList declaredSorts = 
 tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl.make() ;
for (SortDecl decl : decls) {
declaredSorts = 
 tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl.make(decl,tom_append_list_ConcSortDecl(declaredSorts, tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl.make() )) ;
}
GomModuleName moduleName = module.getModuleName();
ModuleDecl mdecl = 
 tom.gom.adt.gom.types.moduledecl.ModuleDecl.make(moduleName, getStreamManager().getPackagePath(moduleName.getName())) ;
sortsForModule.put(mdecl,declaredSorts);
}

/* now get all operators for each sort */
Map<SortDecl,OperatorDeclList> operatorsForSort =
new HashMap<SortDecl,OperatorDeclList>();
for (GomModule module : moduleList) {
// iterate through the productions

{
{
if ( (module instanceof tom.gom.adt.gom.types.GomModule) ) {
if ( ((( tom.gom.adt.gom.types.GomModule )module) instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch527_2= (( tom.gom.adt.gom.types.GomModule )module).getSectionList() ;
if ( ((tomMatch527_2 instanceof tom.gom.adt.gom.types.sectionlist.ConsConcSection) || (tomMatch527_2 instanceof tom.gom.adt.gom.types.sectionlist.EmptyConcSection)) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch527__end__7=tomMatch527_2;
do {
{
if (!( tomMatch527__end__7.isEmptyConcSection() )) {
 tom.gom.adt.gom.types.Section  tomMatch527_11= tomMatch527__end__7.getHeadConcSection() ;
if ( (tomMatch527_11 instanceof tom.gom.adt.gom.types.section.Public) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch527_10= tomMatch527_11.getGrammarList() ;
if ( ((tomMatch527_10 instanceof tom.gom.adt.gom.types.grammarlist.ConsConcGrammar) || (tomMatch527_10 instanceof tom.gom.adt.gom.types.grammarlist.EmptyConcGrammar)) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch527__end__15=tomMatch527_10;
do {
{
if (!( tomMatch527__end__15.isEmptyConcGrammar() )) {
 tom.gom.adt.gom.types.Grammar  tomMatch527_19= tomMatch527__end__15.getHeadConcGrammar() ;
if ( (tomMatch527_19 instanceof tom.gom.adt.gom.types.grammar.Grammar) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch527_18= tomMatch527_19.getProductionList() ;
if ( ((tomMatch527_18 instanceof tom.gom.adt.gom.types.productionlist.ConsConcProduction) || (tomMatch527_18 instanceof tom.gom.adt.gom.types.productionlist.EmptyConcProduction)) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch527__end__23=tomMatch527_18;
do {
{
if (!( tomMatch527__end__23.isEmptyConcProduction() )) {
 tom.gom.adt.gom.types.Production  tomMatch527_27= tomMatch527__end__23.getHeadConcProduction() ;
if ( (tomMatch527_27 instanceof tom.gom.adt.gom.types.production.SortType) ) {
 tom.gom.adt.gom.types.AlternativeList  tomMatch527_26= tomMatch527_27.getAlternativeList() ;
if ( ((tomMatch527_26 instanceof tom.gom.adt.gom.types.alternativelist.ConsConcAlternative) || (tomMatch527_26 instanceof tom.gom.adt.gom.types.alternativelist.EmptyConcAlternative)) ) {
 tom.gom.adt.gom.types.AlternativeList  tomMatch527__end__31=tomMatch527_26;
do {
{
if (!( tomMatch527__end__31.isEmptyConcAlternative() )) {
if ( ( tomMatch527__end__31.getHeadConcAlternative()  instanceof tom.gom.adt.gom.types.alternative.Alternative) ) {

// we may want to pass moduleName to help resolve ambiguities with modules
getOperatorDecl(
 tomMatch527__end__31.getHeadConcAlternative() ,sortDeclList,operatorsForSort);



}
}
if ( tomMatch527__end__31.isEmptyConcAlternative() ) {
tomMatch527__end__31=tomMatch527_26;
} else {
tomMatch527__end__31= tomMatch527__end__31.getTailConcAlternative() ;
}

}
} while(!( (tomMatch527__end__31==tomMatch527_26) ));
}
}
}
if ( tomMatch527__end__23.isEmptyConcProduction() ) {
tomMatch527__end__23=tomMatch527_18;
} else {
tomMatch527__end__23= tomMatch527__end__23.getTailConcProduction() ;
}

}
} while(!( (tomMatch527__end__23==tomMatch527_18) ));
}
}
}
if ( tomMatch527__end__15.isEmptyConcGrammar() ) {
tomMatch527__end__15=tomMatch527_10;
} else {
tomMatch527__end__15= tomMatch527__end__15.getTailConcGrammar() ;
}

}
} while(!( (tomMatch527__end__15==tomMatch527_10) ));
}
}
}
if ( tomMatch527__end__7.isEmptyConcSection() ) {
tomMatch527__end__7=tomMatch527_2;
} else {
tomMatch527__end__7= tomMatch527__end__7.getTailConcSection() ;
}

}
} while(!( (tomMatch527__end__7==tomMatch527_2) ));
}
}
}

}

}

}

/*
* build the module list using the map
* since we already checked that the declared and used sorts do match, we
* can use the map alone
*/
ModuleList resultModuleList = 
 tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() ;
for (Map.Entry<ModuleDecl,SortDeclList> entry : sortsForModule.entrySet()) {
ModuleDecl mdecl = entry.getKey();
SortDeclList sdeclList = entry.getValue();
SortList sortList = 
 tom.gom.adt.gom.types.sortlist.EmptyConcSort.make() ;

{
{
if ( (sdeclList instanceof tom.gom.adt.gom.types.SortDeclList) ) {
if ( (((( tom.gom.adt.gom.types.SortDeclList )sdeclList) instanceof tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl) || ((( tom.gom.adt.gom.types.SortDeclList )sdeclList) instanceof tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl)) ) {
 tom.gom.adt.gom.types.SortDeclList  tomMatch528__end__4=(( tom.gom.adt.gom.types.SortDeclList )sdeclList);
do {
{
if (!( tomMatch528__end__4.isEmptyConcSortDecl() )) {
 tom.gom.adt.gom.types.SortDecl  tom_sdecl= tomMatch528__end__4.getHeadConcSortDecl() ;

OperatorDeclList opdecl = operatorsForSort.get(
tom_sdecl);
Sort fullSort = 
 tom.gom.adt.gom.types.sort.Sort.make(tom_sdecl, opdecl) ;
if(checkSortValidity(fullSort)) {
sortList = 
 tom.gom.adt.gom.types.sortlist.ConsConcSort.make(fullSort,tom_append_list_ConcSort(sortList, tom.gom.adt.gom.types.sortlist.EmptyConcSort.make() )) ;
}


}
if ( tomMatch528__end__4.isEmptyConcSortDecl() ) {
tomMatch528__end__4=(( tom.gom.adt.gom.types.SortDeclList )sdeclList);
} else {
tomMatch528__end__4= tomMatch528__end__4.getTailConcSortDecl() ;
}

}
} while(!( (tomMatch528__end__4==(( tom.gom.adt.gom.types.SortDeclList )sdeclList)) ));
}
}

}

}

resultModuleList = 
 tom.gom.adt.gom.types.modulelist.ConsConcModule.make( tom.gom.adt.gom.types.module.Module.make(mdecl, sortList) ,tom_append_list_ConcModule(resultModuleList, tom.gom.adt.gom.types.modulelist.EmptyConcModule.make() )) ;
}
return resultModuleList;
}

/*
* Get an OperatorDecl from an Alternative, using the list of sort declarations
* XXX: There is huge room for efficiency improvement, as we could use a map
* sortName -> sortDeclList instead of a simple list
*/
private OperatorDecl getOperatorDecl(Alternative alt,
SortDeclList sortDeclList,
Map<SortDecl,OperatorDeclList> operatorsForSort) {


{
{
if ( (alt instanceof tom.gom.adt.gom.types.Alternative) ) {
if ( ((( tom.gom.adt.gom.types.Alternative )alt) instanceof tom.gom.adt.gom.types.alternative.Alternative) ) {
 tom.gom.adt.gom.types.GomType  tomMatch529_3= (( tom.gom.adt.gom.types.Alternative )alt).getCodomain() ;
if ( (tomMatch529_3 instanceof tom.gom.adt.gom.types.gomtype.GomType) ) {

SortDecl codomainSort = declFromTypename(
 tomMatch529_3.getName() ,sortDeclList);
TypedProduction domainSorts = typedProduction(
 (( tom.gom.adt.gom.types.Alternative )alt).getDomainList() ,sortDeclList);

OperatorDecl decl = 
 tom.gom.adt.gom.types.operatordecl.OperatorDecl.make( (( tom.gom.adt.gom.types.Alternative )alt).getName() , codomainSort, domainSorts,  (( tom.gom.adt.gom.types.Alternative )alt).getOption() ) ;
if (operatorsForSort.containsKey(codomainSort)) {
OperatorDeclList list = operatorsForSort.get(codomainSort);
operatorsForSort.put(codomainSort,
 tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(decl,tom_append_list_ConcOperator(list, tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator.make() )) );
} else {
operatorsForSort.put(codomainSort,
 tom.gom.adt.gom.types.operatordecllist.ConsConcOperator.make(decl, tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator.make() ) );
}
return decl;


}
}
}

}

}

throw new GomRuntimeException(
"TypeExpander::getOperatorDecl: wrong Alternative?");
}

private SortDecl declFromTypename(String typename,
SortDeclList sortDeclList) {
if (getGomEnvironment().isBuiltinSort(typename)) {
return getGomEnvironment().builtinSort(typename);
}

{
{
if ( (sortDeclList instanceof tom.gom.adt.gom.types.SortDeclList) ) {
if ( (((( tom.gom.adt.gom.types.SortDeclList )sortDeclList) instanceof tom.gom.adt.gom.types.sortdecllist.ConsConcSortDecl) || ((( tom.gom.adt.gom.types.SortDeclList )sortDeclList) instanceof tom.gom.adt.gom.types.sortdecllist.EmptyConcSortDecl)) ) {
 tom.gom.adt.gom.types.SortDeclList  tomMatch530__end__4=(( tom.gom.adt.gom.types.SortDeclList )sortDeclList);
do {
{
if (!( tomMatch530__end__4.isEmptyConcSortDecl() )) {
 tom.gom.adt.gom.types.SortDecl  tomMatch530_8= tomMatch530__end__4.getHeadConcSortDecl() ;
if ( (tomMatch530_8 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {

if (typename.equals(
 tomMatch530_8.getName() )) {
return 
 tomMatch530__end__4.getHeadConcSortDecl() ;
}


}
}
if ( tomMatch530__end__4.isEmptyConcSortDecl() ) {
tomMatch530__end__4=(( tom.gom.adt.gom.types.SortDeclList )sortDeclList);
} else {
tomMatch530__end__4= tomMatch530__end__4.getTailConcSortDecl() ;
}

}
} while(!( (tomMatch530__end__4==(( tom.gom.adt.gom.types.SortDeclList )sortDeclList)) ));
}
}

}

}


GomMessage.error(getLogger(),null,0,
GomMessage.unknownSort,
new Object[]{typename});
/* If the sort is not known, assume it is a builtin */
return 
 tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl.make(typename) ;
}

private TypedProduction typedProduction(FieldList domain, SortDeclList sortDeclList) {

{
{
if ( (domain instanceof tom.gom.adt.gom.types.FieldList) ) {
if ( (((( tom.gom.adt.gom.types.FieldList )domain) instanceof tom.gom.adt.gom.types.fieldlist.ConsConcField) || ((( tom.gom.adt.gom.types.FieldList )domain) instanceof tom.gom.adt.gom.types.fieldlist.EmptyConcField)) ) {
if (!( (( tom.gom.adt.gom.types.FieldList )domain).isEmptyConcField() )) {
 tom.gom.adt.gom.types.Field  tomMatch531_5= (( tom.gom.adt.gom.types.FieldList )domain).getHeadConcField() ;
if ( (tomMatch531_5 instanceof tom.gom.adt.gom.types.field.StarredField) ) {
 tom.gom.adt.gom.types.GomType  tomMatch531_3= tomMatch531_5.getFieldType() ;
if ( (tomMatch531_3 instanceof tom.gom.adt.gom.types.gomtype.GomType) ) {
if (  (( tom.gom.adt.gom.types.FieldList )domain).getTailConcField() .isEmptyConcField() ) {

return 
 tom.gom.adt.gom.types.typedproduction.Variadic.make(declFromTypename( tomMatch531_3.getName() ,sortDeclList)) ;


}
}
}
}
}
}

}
{
if ( (domain instanceof tom.gom.adt.gom.types.FieldList) ) {
if ( (((( tom.gom.adt.gom.types.FieldList )domain) instanceof tom.gom.adt.gom.types.fieldlist.ConsConcField) || ((( tom.gom.adt.gom.types.FieldList )domain) instanceof tom.gom.adt.gom.types.fieldlist.EmptyConcField)) ) {

return 
 tom.gom.adt.gom.types.typedproduction.Slots.make(typedSlotList((( tom.gom.adt.gom.types.FieldList )domain),sortDeclList)) ;


}
}

}


}

throw new GomRuntimeException("TypeExpander::typedProduction: illformed Alternative");
}

private SlotList typedSlotList(FieldList fields, SortDeclList sortDeclList) {

{
{
if ( (fields instanceof tom.gom.adt.gom.types.FieldList) ) {
if ( (((( tom.gom.adt.gom.types.FieldList )fields) instanceof tom.gom.adt.gom.types.fieldlist.ConsConcField) || ((( tom.gom.adt.gom.types.FieldList )fields) instanceof tom.gom.adt.gom.types.fieldlist.EmptyConcField)) ) {
if ( (( tom.gom.adt.gom.types.FieldList )fields).isEmptyConcField() ) {

return 
 tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ;


}
}
}

}
{
if ( (fields instanceof tom.gom.adt.gom.types.FieldList) ) {
if ( (((( tom.gom.adt.gom.types.FieldList )fields) instanceof tom.gom.adt.gom.types.fieldlist.ConsConcField) || ((( tom.gom.adt.gom.types.FieldList )fields) instanceof tom.gom.adt.gom.types.fieldlist.EmptyConcField)) ) {
if (!( (( tom.gom.adt.gom.types.FieldList )fields).isEmptyConcField() )) {
 tom.gom.adt.gom.types.Field  tomMatch532_9= (( tom.gom.adt.gom.types.FieldList )fields).getHeadConcField() ;
if ( (tomMatch532_9 instanceof tom.gom.adt.gom.types.field.NamedField) ) {
 tom.gom.adt.gom.types.GomType  tomMatch532_7= tomMatch532_9.getFieldType() ;
if ( (tomMatch532_7 instanceof tom.gom.adt.gom.types.gomtype.GomType) ) {

SlotList newtail = typedSlotList(
 (( tom.gom.adt.gom.types.FieldList )fields).getTailConcField() ,sortDeclList);
return 
 tom.gom.adt.gom.types.slotlist.ConsConcSlot.make( tom.gom.adt.gom.types.slot.Slot.make( tomMatch532_9.getName() , declFromTypename( tomMatch532_7.getName() ,sortDeclList)) ,tom_append_list_ConcSlot(newtail, tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() )) ;


}
}
}
}
}

}


}

GomMessage.error(getLogger(),null,0,
GomMessage.malformedProduction,
new Object[]{fields.toString()});
return 
 tom.gom.adt.gom.types.slotlist.EmptyConcSlot.make() ;
}

/*
* Get all sort declarations in a module
*/
private Collection<SortDecl> getSortDeclarations(GomModule module) {
Collection<SortDecl> result = new HashSet<SortDecl>();

{
{
if ( (module instanceof tom.gom.adt.gom.types.GomModule) ) {
if ( ((( tom.gom.adt.gom.types.GomModule )module) instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch533_2= (( tom.gom.adt.gom.types.GomModule )module).getSectionList() ;
 tom.gom.adt.gom.types.GomModuleName  tom_moduleName= (( tom.gom.adt.gom.types.GomModule )module).getModuleName() ;
if ( ((tomMatch533_2 instanceof tom.gom.adt.gom.types.sectionlist.ConsConcSection) || (tomMatch533_2 instanceof tom.gom.adt.gom.types.sectionlist.EmptyConcSection)) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch533__end__7=tomMatch533_2;
do {
{
if (!( tomMatch533__end__7.isEmptyConcSection() )) {
 tom.gom.adt.gom.types.Section  tomMatch533_11= tomMatch533__end__7.getHeadConcSection() ;
if ( (tomMatch533_11 instanceof tom.gom.adt.gom.types.section.Public) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch533_10= tomMatch533_11.getGrammarList() ;
if ( ((tomMatch533_10 instanceof tom.gom.adt.gom.types.grammarlist.ConsConcGrammar) || (tomMatch533_10 instanceof tom.gom.adt.gom.types.grammarlist.EmptyConcGrammar)) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch533__end__15=tomMatch533_10;
do {
{
if (!( tomMatch533__end__15.isEmptyConcGrammar() )) {
 tom.gom.adt.gom.types.Grammar  tomMatch533_19= tomMatch533__end__15.getHeadConcGrammar() ;
if ( (tomMatch533_19 instanceof tom.gom.adt.gom.types.grammar.Grammar) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch533_18= tomMatch533_19.getProductionList() ;
if ( ((tomMatch533_18 instanceof tom.gom.adt.gom.types.productionlist.ConsConcProduction) || (tomMatch533_18 instanceof tom.gom.adt.gom.types.productionlist.EmptyConcProduction)) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch533__end__23=tomMatch533_18;
do {
{
if (!( tomMatch533__end__23.isEmptyConcProduction() )) {
 tom.gom.adt.gom.types.Production  tomMatch533_27= tomMatch533__end__23.getHeadConcProduction() ;
if ( (tomMatch533_27 instanceof tom.gom.adt.gom.types.production.SortType) ) {
 tom.gom.adt.gom.types.GomType  tomMatch533_26= tomMatch533_27.getType() ;
if ( (tomMatch533_26 instanceof tom.gom.adt.gom.types.gomtype.GomType) ) {
 String  tom_typeName= tomMatch533_26.getName() ;

if (getGomEnvironment().isBuiltinSort(
tom_typeName)) {
GomMessage.error(getLogger(),null,0,
GomMessage.operatorOnBuiltin,
new Object[]{(
tom_typeName)});
result.add(getGomEnvironment().builtinSort(
tom_typeName));
} else {
result.add(
 tom.gom.adt.gom.types.sortdecl.SortDecl.make(tom_typeName,  tom.gom.adt.gom.types.moduledecl.ModuleDecl.make(tom_moduleName, getStreamManager().getPackagePath(tom_moduleName.getName())) ) );
}


}
}
}
if ( tomMatch533__end__23.isEmptyConcProduction() ) {
tomMatch533__end__23=tomMatch533_18;
} else {
tomMatch533__end__23= tomMatch533__end__23.getTailConcProduction() ;
}

}
} while(!( (tomMatch533__end__23==tomMatch533_18) ));
}
}
}
if ( tomMatch533__end__15.isEmptyConcGrammar() ) {
tomMatch533__end__15=tomMatch533_10;
} else {
tomMatch533__end__15= tomMatch533__end__15.getTailConcGrammar() ;
}

}
} while(!( (tomMatch533__end__15==tomMatch533_10) ));
}
}
}
if ( tomMatch533__end__7.isEmptyConcSection() ) {
tomMatch533__end__7=tomMatch533_2;
} else {
tomMatch533__end__7= tomMatch533__end__7.getTailConcSection() ;
}

}
} while(!( (tomMatch533__end__7==tomMatch533_2) ));
}
}
}

}

}

return result;
}

/*
* Get all sort uses in a module (as codomain of an operator)
*/
private Collection<SortDecl> getSortDeclarationInCodomain(GomModule module) {
Collection<SortDecl> result = new HashSet<SortDecl>();

{
{
if ( (module instanceof tom.gom.adt.gom.types.GomModule) ) {
if ( ((( tom.gom.adt.gom.types.GomModule )module) instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch534_2= (( tom.gom.adt.gom.types.GomModule )module).getSectionList() ;
 tom.gom.adt.gom.types.GomModuleName  tom_moduleName= (( tom.gom.adt.gom.types.GomModule )module).getModuleName() ;
if ( ((tomMatch534_2 instanceof tom.gom.adt.gom.types.sectionlist.ConsConcSection) || (tomMatch534_2 instanceof tom.gom.adt.gom.types.sectionlist.EmptyConcSection)) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch534__end__7=tomMatch534_2;
do {
{
if (!( tomMatch534__end__7.isEmptyConcSection() )) {
 tom.gom.adt.gom.types.Section  tomMatch534_11= tomMatch534__end__7.getHeadConcSection() ;
if ( (tomMatch534_11 instanceof tom.gom.adt.gom.types.section.Public) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch534_10= tomMatch534_11.getGrammarList() ;
if ( ((tomMatch534_10 instanceof tom.gom.adt.gom.types.grammarlist.ConsConcGrammar) || (tomMatch534_10 instanceof tom.gom.adt.gom.types.grammarlist.EmptyConcGrammar)) ) {
 tom.gom.adt.gom.types.GrammarList  tomMatch534__end__15=tomMatch534_10;
do {
{
if (!( tomMatch534__end__15.isEmptyConcGrammar() )) {
 tom.gom.adt.gom.types.Grammar  tomMatch534_19= tomMatch534__end__15.getHeadConcGrammar() ;
if ( (tomMatch534_19 instanceof tom.gom.adt.gom.types.grammar.Grammar) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch534_18= tomMatch534_19.getProductionList() ;
if ( ((tomMatch534_18 instanceof tom.gom.adt.gom.types.productionlist.ConsConcProduction) || (tomMatch534_18 instanceof tom.gom.adt.gom.types.productionlist.EmptyConcProduction)) ) {
 tom.gom.adt.gom.types.ProductionList  tomMatch534__end__23=tomMatch534_18;
do {
{
if (!( tomMatch534__end__23.isEmptyConcProduction() )) {
 tom.gom.adt.gom.types.Production  tomMatch534_27= tomMatch534__end__23.getHeadConcProduction() ;
if ( (tomMatch534_27 instanceof tom.gom.adt.gom.types.production.SortType) ) {
 tom.gom.adt.gom.types.AlternativeList  tomMatch534_26= tomMatch534_27.getAlternativeList() ;
if ( ((tomMatch534_26 instanceof tom.gom.adt.gom.types.alternativelist.ConsConcAlternative) || (tomMatch534_26 instanceof tom.gom.adt.gom.types.alternativelist.EmptyConcAlternative)) ) {
 tom.gom.adt.gom.types.AlternativeList  tomMatch534__end__31=tomMatch534_26;
do {
{
if (!( tomMatch534__end__31.isEmptyConcAlternative() )) {
 tom.gom.adt.gom.types.Alternative  tomMatch534_38= tomMatch534__end__31.getHeadConcAlternative() ;
if ( (tomMatch534_38 instanceof tom.gom.adt.gom.types.alternative.Alternative) ) {
 tom.gom.adt.gom.types.GomType  tomMatch534_36= tomMatch534_38.getCodomain() ;
if ( (tomMatch534_36 instanceof tom.gom.adt.gom.types.gomtype.GomType) ) {
 String  tom_typeName= tomMatch534_36.getName() ;

if (getGomEnvironment().isBuiltinSort(
tom_typeName)) {
result.add(getGomEnvironment().builtinSort(
tom_typeName));
} else {
result.add(
 tom.gom.adt.gom.types.sortdecl.SortDecl.make(tom_typeName,  tom.gom.adt.gom.types.moduledecl.ModuleDecl.make(tom_moduleName, getStreamManager().getPackagePath(tom_moduleName.getName())) ) );
}


}
}
}
if ( tomMatch534__end__31.isEmptyConcAlternative() ) {
tomMatch534__end__31=tomMatch534_26;
} else {
tomMatch534__end__31= tomMatch534__end__31.getTailConcAlternative() ;
}

}
} while(!( (tomMatch534__end__31==tomMatch534_26) ));
}
}
}
if ( tomMatch534__end__23.isEmptyConcProduction() ) {
tomMatch534__end__23=tomMatch534_18;
} else {
tomMatch534__end__23= tomMatch534__end__23.getTailConcProduction() ;
}

}
} while(!( (tomMatch534__end__23==tomMatch534_18) ));
}
}
}
if ( tomMatch534__end__15.isEmptyConcGrammar() ) {
tomMatch534__end__15=tomMatch534_10;
} else {
tomMatch534__end__15= tomMatch534__end__15.getTailConcGrammar() ;
}

}
} while(!( (tomMatch534__end__15==tomMatch534_10) ));
}
}
}
if ( tomMatch534__end__7.isEmptyConcSection() ) {
tomMatch534__end__7=tomMatch534_2;
} else {
tomMatch534__end__7= tomMatch534__end__7.getTailConcSection() ;
}

}
} while(!( (tomMatch534__end__7==tomMatch534_2) ));
}
}
}

}

}

return result;
}

/**
* Get directly imported modules. Skip builtins
*
* @param module the main module with imports
* @return the Collection of imported GomModuleName
*/
private Collection<GomModuleName> getImportedModules(GomModule module) {
Set<GomModuleName> imports = new HashSet<GomModuleName>();

{
{
if ( (module instanceof tom.gom.adt.gom.types.GomModule) ) {
if ( ((( tom.gom.adt.gom.types.GomModule )module) instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {
 tom.gom.adt.gom.types.SectionList  tom_sectionList= (( tom.gom.adt.gom.types.GomModule )module).getSectionList() ;

imports.add(
 (( tom.gom.adt.gom.types.GomModule )module).getModuleName() );

{
{
if ( (tom_sectionList instanceof tom.gom.adt.gom.types.SectionList) ) {
if ( (((( tom.gom.adt.gom.types.SectionList )tom_sectionList) instanceof tom.gom.adt.gom.types.sectionlist.ConsConcSection) || ((( tom.gom.adt.gom.types.SectionList )tom_sectionList) instanceof tom.gom.adt.gom.types.sectionlist.EmptyConcSection)) ) {
 tom.gom.adt.gom.types.SectionList  tomMatch536__end__4=(( tom.gom.adt.gom.types.SectionList )tom_sectionList);
do {
{
if (!( tomMatch536__end__4.isEmptyConcSection() )) {
 tom.gom.adt.gom.types.Section  tomMatch536_8= tomMatch536__end__4.getHeadConcSection() ;
if ( (tomMatch536_8 instanceof tom.gom.adt.gom.types.section.Imports) ) {
 tom.gom.adt.gom.types.ImportList  tomMatch536_7= tomMatch536_8.getImportList() ;
if ( ((tomMatch536_7 instanceof tom.gom.adt.gom.types.importlist.ConsConcImportedModule) || (tomMatch536_7 instanceof tom.gom.adt.gom.types.importlist.EmptyConcImportedModule)) ) {
 tom.gom.adt.gom.types.ImportList  tomMatch536__end__12=tomMatch536_7;
do {
{
if (!( tomMatch536__end__12.isEmptyConcImportedModule() )) {
 tom.gom.adt.gom.types.ImportedModule  tomMatch536_16= tomMatch536__end__12.getHeadConcImportedModule() ;
if ( (tomMatch536_16 instanceof tom.gom.adt.gom.types.importedmodule.Import) ) {
 tom.gom.adt.gom.types.GomModuleName  tomMatch536_15= tomMatch536_16.getModuleName() ;
if ( (tomMatch536_15 instanceof tom.gom.adt.gom.types.gommodulename.GomModuleName) ) {

if (!getGomEnvironment().isBuiltin(
 tomMatch536_15.getName() )) {
imports.add(
tomMatch536_15);
}


}
}
}
if ( tomMatch536__end__12.isEmptyConcImportedModule() ) {
tomMatch536__end__12=tomMatch536_7;
} else {
tomMatch536__end__12= tomMatch536__end__12.getTailConcImportedModule() ;
}

}
} while(!( (tomMatch536__end__12==tomMatch536_7) ));
}
}
}
if ( tomMatch536__end__4.isEmptyConcSection() ) {
tomMatch536__end__4=(( tom.gom.adt.gom.types.SectionList )tom_sectionList);
} else {
tomMatch536__end__4= tomMatch536__end__4.getTailConcSection() ;
}

}
} while(!( (tomMatch536__end__4==(( tom.gom.adt.gom.types.SectionList )tom_sectionList)) ));
}
}

}

}



}
}

}

}

return imports;
}

private GomModule getModule(GomModuleName modname, GomModuleList list) {

{
{
if ( (list instanceof tom.gom.adt.gom.types.GomModuleList) ) {
if ( (((( tom.gom.adt.gom.types.GomModuleList )list) instanceof tom.gom.adt.gom.types.gommodulelist.ConsConcGomModule) || ((( tom.gom.adt.gom.types.GomModuleList )list) instanceof tom.gom.adt.gom.types.gommodulelist.EmptyConcGomModule)) ) {
 tom.gom.adt.gom.types.GomModuleList  tomMatch537__end__4=(( tom.gom.adt.gom.types.GomModuleList )list);
do {
{
if (!( tomMatch537__end__4.isEmptyConcGomModule() )) {
 tom.gom.adt.gom.types.GomModule  tomMatch537_8= tomMatch537__end__4.getHeadConcGomModule() ;
if ( (tomMatch537_8 instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {

if (
 tomMatch537_8.getModuleName() .equals(modname)) {
return 
 tomMatch537__end__4.getHeadConcGomModule() ;
}


}
}
if ( tomMatch537__end__4.isEmptyConcGomModule() ) {
tomMatch537__end__4=(( tom.gom.adt.gom.types.GomModuleList )list);
} else {
tomMatch537__end__4= tomMatch537__end__4.getTailConcGomModule() ;
}

}
} while(!( (tomMatch537__end__4==(( tom.gom.adt.gom.types.GomModuleList )list)) ));
}
}

}

}

throw new GomRuntimeException("Module "+ modname +" not present");
}

private Collection<GomModuleName> getTransitiveClosureImports(
GomModule module,
GomModuleList moduleList) {
Set<GomModuleName> imported = new HashSet<GomModuleName>();
imported.addAll(getImportedModules(module));

Set<GomModuleName> newSet = new HashSet<GomModuleName>();
while (!newSet.equals(imported)) {
newSet.addAll(imported);
imported.addAll(newSet);
for (GomModuleName modname : imported) {
newSet.addAll(getImportedModules(getModule(modname,moduleList)));
}
}
return newSet;
}

private void buildDependencyMap(GomModuleList moduleList) {

{
{
if ( (moduleList instanceof tom.gom.adt.gom.types.GomModuleList) ) {
if ( (((( tom.gom.adt.gom.types.GomModuleList )moduleList) instanceof tom.gom.adt.gom.types.gommodulelist.ConsConcGomModule) || ((( tom.gom.adt.gom.types.GomModuleList )moduleList) instanceof tom.gom.adt.gom.types.gommodulelist.EmptyConcGomModule)) ) {
 tom.gom.adt.gom.types.GomModuleList  tomMatch538__end__4=(( tom.gom.adt.gom.types.GomModuleList )moduleList);
do {
{
if (!( tomMatch538__end__4.isEmptyConcGomModule() )) {
 tom.gom.adt.gom.types.GomModule  tomMatch538_8= tomMatch538__end__4.getHeadConcGomModule() ;
if ( (tomMatch538_8 instanceof tom.gom.adt.gom.types.gommodule.GomModule) ) {
 tom.gom.adt.gom.types.GomModuleName  tom_moduleName= tomMatch538_8.getModuleName() ;

ModuleDeclList importsModuleDeclList = 
 tom.gom.adt.gom.types.moduledecllist.EmptyConcModuleDecl.make() ;
for (GomModuleName importedModuleName :
getTransitiveClosureImports(
 tomMatch538__end__4.getHeadConcGomModule() ,moduleList)) {
importsModuleDeclList =

 tom.gom.adt.gom.types.moduledecllist.ConsConcModuleDecl.make( tom.gom.adt.gom.types.moduledecl.ModuleDecl.make(importedModuleName, getStreamManager().getPackagePath(importedModuleName.getName())) ,tom_append_list_ConcModuleDecl(importsModuleDeclList, tom.gom.adt.gom.types.moduledecllist.EmptyConcModuleDecl.make() )) ;
}
getGomEnvironment().addModuleDependency(

 tom.gom.adt.gom.types.moduledecl.ModuleDecl.make(tom_moduleName, getStreamManager().getPackagePath(tom_moduleName.getName())) ,
importsModuleDeclList);


}
}
if ( tomMatch538__end__4.isEmptyConcGomModule() ) {
tomMatch538__end__4=(( tom.gom.adt.gom.types.GomModuleList )moduleList);
} else {
tomMatch538__end__4= tomMatch538__end__4.getTailConcGomModule() ;
}

}
} while(!( (tomMatch538__end__4==(( tom.gom.adt.gom.types.GomModuleList )moduleList)) ));
}
}

}

}

}

private boolean checkSortValidity(Sort sort) {
boolean valid = true;
// check if the same slot name is used with different types
Map<String,SortDecl> mapNameType = new HashMap<String,SortDecl>();

{
{
if ( (sort instanceof tom.gom.adt.gom.types.Sort) ) {
if ( ((( tom.gom.adt.gom.types.Sort )sort) instanceof tom.gom.adt.gom.types.sort.Sort) ) {
 tom.gom.adt.gom.types.SortDecl  tomMatch539_1= (( tom.gom.adt.gom.types.Sort )sort).getDecl() ;
 tom.gom.adt.gom.types.OperatorDeclList  tomMatch539_2= (( tom.gom.adt.gom.types.Sort )sort).getOperatorDecls() ;
boolean tomMatch539_25= false ;
 String  tomMatch539_4= "" ;
if ( (tomMatch539_1 instanceof tom.gom.adt.gom.types.sortdecl.SortDecl) ) {
{
tomMatch539_25= true ;
tomMatch539_4= tomMatch539_1.getName() ;

}
} else {
if ( (tomMatch539_1 instanceof tom.gom.adt.gom.types.sortdecl.BuiltinSortDecl) ) {
{
tomMatch539_25= true ;
tomMatch539_4= tomMatch539_1.getName() ;

}
}
}
if (tomMatch539_25) {
if ( ((tomMatch539_2 instanceof tom.gom.adt.gom.types.operatordecllist.ConsConcOperator) || (tomMatch539_2 instanceof tom.gom.adt.gom.types.operatordecllist.EmptyConcOperator)) ) {
 tom.gom.adt.gom.types.OperatorDeclList  tomMatch539__end__9=tomMatch539_2;
do {
{
if (!( tomMatch539__end__9.isEmptyConcOperator() )) {
 tom.gom.adt.gom.types.OperatorDecl  tomMatch539_13= tomMatch539__end__9.getHeadConcOperator() ;
if ( (tomMatch539_13 instanceof tom.gom.adt.gom.types.operatordecl.OperatorDecl) ) {
 tom.gom.adt.gom.types.TypedProduction  tomMatch539_12= tomMatch539_13.getProd() ;
if ( (tomMatch539_12 instanceof tom.gom.adt.gom.types.typedproduction.Slots) ) {
 tom.gom.adt.gom.types.SlotList  tomMatch539_14= tomMatch539_12.getSlots() ;
if ( ((tomMatch539_14 instanceof tom.gom.adt.gom.types.slotlist.ConsConcSlot) || (tomMatch539_14 instanceof tom.gom.adt.gom.types.slotlist.EmptyConcSlot)) ) {
 tom.gom.adt.gom.types.SlotList  tomMatch539__end__19=tomMatch539_14;
do {
{
if (!( tomMatch539__end__19.isEmptyConcSlot() )) {
 tom.gom.adt.gom.types.Slot  tomMatch539_24= tomMatch539__end__19.getHeadConcSlot() ;
if ( (tomMatch539_24 instanceof tom.gom.adt.gom.types.slot.Slot) ) {
 String  tom_slotName= tomMatch539_24.getName() ;
 tom.gom.adt.gom.types.SortDecl  tom_slotSort= tomMatch539_24.getSort() ;

if (!mapNameType.containsKey(
tom_slotName)) {
mapNameType.put(
tom_slotName,
tom_slotSort);
} else {
SortDecl prevSort = mapNameType.get(
tom_slotName);
if (!prevSort.equals(
tom_slotSort)) {
GomMessage.error(getLogger(),null,0,
GomMessage.slotIncompatibleTypes,
new Object[]{
tomMatch539_4,
tom_slotName,prevSort.getName(),

(tom_slotSort).getName()});
valid = false;
}
}


}
}
if ( tomMatch539__end__19.isEmptyConcSlot() ) {
tomMatch539__end__19=tomMatch539_14;
} else {
tomMatch539__end__19= tomMatch539__end__19.getTailConcSlot() ;
}

}
} while(!( (tomMatch539__end__19==tomMatch539_14) ));
}
}
}
}
if ( tomMatch539__end__9.isEmptyConcOperator() ) {
tomMatch539__end__9=tomMatch539_2;
} else {
tomMatch539__end__9= tomMatch539__end__9.getTailConcOperator() ;
}

}
} while(!( (tomMatch539__end__9==tomMatch539_2) ));
}
}

}
}

}

}

return valid;
}

private String showSortList(Collection<SortDecl> decls) {
StringBuilder sorts = new StringBuilder();
for (SortDecl decl : decls) {
if (0==sorts.length()) {
sorts.append(", ");
}
sorts.append(decl.getName());
}
return sorts.toString();
}

private Logger getLogger() {
return Logger.getLogger(getClass().getName());
}
}
