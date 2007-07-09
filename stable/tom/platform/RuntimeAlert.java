/* Generated by TOM (version 2.5): Do not edit this file *//*
 * 
 * TOM - To One Matching Compiler
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
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/

package tom.platform;

import aterm.pure.*;
import java.util.logging.*;

import tom.platform.adt.platformalert.*;
import tom.platform.adt.platformalert.types.*;

public class RuntimeAlert {
  
  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5): Do not edit this file */private static boolean tom_equal_term_int(int t1, int t2) { return  (t1==t2) ;}private static boolean tom_is_sort_int(int t) { return  true ;} private static boolean tom_equal_term_AlertList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_AlertList(Object t) { return  t instanceof tom.platform.adt.platformalert.types.AlertList ;}private static boolean tom_equal_term_Alert(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Alert(Object t) { return  t instanceof tom.platform.adt.platformalert.types.Alert ;}private static  tom.platform.adt.platformalert.types.Alert  tom_make_Error( String  t0,  String  t1,  int  t2) { return  tom.platform.adt.platformalert.types.alert.Error.make(t0, t1, t2) ; }private static  tom.platform.adt.platformalert.types.Alert  tom_make_Warning( String  t0,  String  t1,  int  t2) { return  tom.platform.adt.platformalert.types.alert.Warning.make(t0, t1, t2) ; }private static boolean tom_is_fun_sym_concAlert( tom.platform.adt.platformalert.types.AlertList  t) { return  t instanceof tom.platform.adt.platformalert.types.alertlist.ConsconcAlert || t instanceof tom.platform.adt.platformalert.types.alertlist.EmptyconcAlert ;}private static  tom.platform.adt.platformalert.types.AlertList  tom_empty_list_concAlert() { return  tom.platform.adt.platformalert.types.alertlist.EmptyconcAlert.make() ; }private static  tom.platform.adt.platformalert.types.AlertList  tom_cons_list_concAlert( tom.platform.adt.platformalert.types.Alert  e,  tom.platform.adt.platformalert.types.AlertList  l) { return  tom.platform.adt.platformalert.types.alertlist.ConsconcAlert.make(e,l) ; }private static  tom.platform.adt.platformalert.types.Alert  tom_get_head_concAlert_AlertList( tom.platform.adt.platformalert.types.AlertList  l) { return  l.getHeadconcAlert() ;}private static  tom.platform.adt.platformalert.types.AlertList  tom_get_tail_concAlert_AlertList( tom.platform.adt.platformalert.types.AlertList  l) { return  l.getTailconcAlert() ;}private static boolean tom_is_empty_concAlert_AlertList( tom.platform.adt.platformalert.types.AlertList  l) { return  l.isEmptyconcAlert() ;}   private static   tom.platform.adt.platformalert.types.AlertList  tom_append_list_concAlert( tom.platform.adt.platformalert.types.AlertList l1,  tom.platform.adt.platformalert.types.AlertList  l2) {     if(tom_is_empty_concAlert_AlertList(l1)) {       return l2;     } else if(tom_is_empty_concAlert_AlertList(l2)) {       return l1;     } else if(tom_is_empty_concAlert_AlertList(tom_get_tail_concAlert_AlertList(l1))) {       return ( tom.platform.adt.platformalert.types.AlertList )tom_cons_list_concAlert(tom_get_head_concAlert_AlertList(l1),l2);     } else {       return ( tom.platform.adt.platformalert.types.AlertList )tom_cons_list_concAlert(tom_get_head_concAlert_AlertList(l1),tom_append_list_concAlert(tom_get_tail_concAlert_AlertList(l1),l2));     }   }   private static   tom.platform.adt.platformalert.types.AlertList  tom_get_slice_concAlert( tom.platform.adt.platformalert.types.AlertList  begin,  tom.platform.adt.platformalert.types.AlertList  end, tom.platform.adt.platformalert.types.AlertList  tail) {     if(tom_equal_term_AlertList(begin,end)) {       return tail;     } else {       return ( tom.platform.adt.platformalert.types.AlertList )tom_cons_list_concAlert(tom_get_head_concAlert_AlertList(begin),( tom.platform.adt.platformalert.types.AlertList )tom_get_slice_concAlert(tom_get_tail_concAlert_AlertList(begin),end,tail));     }   }    

  private AlertList errors;
  private AlertList warnings;
  private int nbErrors;
  private int nbWarnings;

  public RuntimeAlert() {
    errors = tom_empty_list_concAlert();
    warnings = tom_empty_list_concAlert();
    nbErrors = 0;
    nbWarnings = 0;
  }

  /**
   * Add the warning only if it is not already in the list 
   */
  public void addWarning(String message, String file, int line) {
    Alert entry = tom_make_Warning(message,file,line);
    if (tom_is_sort_Alert(entry)) {{  tom.platform.adt.platformalert.types.Alert  tomMatch516NameNumberfreshSubject_1=(( tom.platform.adt.platformalert.types.Alert )entry);if (tom_is_sort_AlertList(warnings)) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumberfreshSubject_2=(( tom.platform.adt.platformalert.types.AlertList )warnings);{ boolean tomMatch516NameNumber_freshVar_6= false ;if (tom_is_fun_sym_concAlert(tomMatch516NameNumberfreshSubject_2)) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumber_freshVar_0=tomMatch516NameNumberfreshSubject_2;{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumber_begin_2=tomMatch516NameNumber_freshVar_0;{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumber_end_3=tomMatch516NameNumber_freshVar_0;do {{{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumber_freshVar_1=tomMatch516NameNumber_end_3;if (!(tom_is_empty_concAlert_AlertList(tomMatch516NameNumber_freshVar_1))) {if (tom_equal_term_Alert(tomMatch516NameNumberfreshSubject_1, tom_get_head_concAlert_AlertList(tomMatch516NameNumber_freshVar_1))) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch516NameNumber_freshVar_4=tom_get_tail_concAlert_AlertList(tomMatch516NameNumber_freshVar_1);tomMatch516NameNumber_freshVar_6= true ;}}}}if (tom_is_empty_concAlert_AlertList(tomMatch516NameNumber_end_3)) {tomMatch516NameNumber_end_3=tomMatch516NameNumber_begin_2;} else {tomMatch516NameNumber_end_3=tom_get_tail_concAlert_AlertList(tomMatch516NameNumber_end_3);}}} while(!(tom_equal_term_AlertList(tomMatch516NameNumber_end_3, tomMatch516NameNumber_begin_2)));}}}}if ((tomMatch516NameNumber_freshVar_6 ==  false )) {if ( true ) {

        warnings = tom_cons_list_concAlert(entry,tom_append_list_concAlert(warnings,tom_empty_list_concAlert()));
        nbWarnings++;   
      }}}}}}}
    
  }
  
  /**
   * Add the error only if it is not already in the list 
   */
  public void addError(String message, String file, int line) {
    Alert entry = tom_make_Error(message,file,line);
    if (tom_is_sort_Alert(entry)) {{  tom.platform.adt.platformalert.types.Alert  tomMatch517NameNumberfreshSubject_1=(( tom.platform.adt.platformalert.types.Alert )entry);if (tom_is_sort_AlertList(errors)) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumberfreshSubject_2=(( tom.platform.adt.platformalert.types.AlertList )errors);{ boolean tomMatch517NameNumber_freshVar_6= false ;if (tom_is_fun_sym_concAlert(tomMatch517NameNumberfreshSubject_2)) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumber_freshVar_0=tomMatch517NameNumberfreshSubject_2;{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumber_begin_2=tomMatch517NameNumber_freshVar_0;{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumber_end_3=tomMatch517NameNumber_freshVar_0;do {{{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumber_freshVar_1=tomMatch517NameNumber_end_3;if (!(tom_is_empty_concAlert_AlertList(tomMatch517NameNumber_freshVar_1))) {if (tom_equal_term_Alert(tomMatch517NameNumberfreshSubject_1, tom_get_head_concAlert_AlertList(tomMatch517NameNumber_freshVar_1))) {{  tom.platform.adt.platformalert.types.AlertList  tomMatch517NameNumber_freshVar_4=tom_get_tail_concAlert_AlertList(tomMatch517NameNumber_freshVar_1);tomMatch517NameNumber_freshVar_6= true ;}}}}if (tom_is_empty_concAlert_AlertList(tomMatch517NameNumber_end_3)) {tomMatch517NameNumber_end_3=tomMatch517NameNumber_begin_2;} else {tomMatch517NameNumber_end_3=tom_get_tail_concAlert_AlertList(tomMatch517NameNumber_end_3);}}} while(!(tom_equal_term_AlertList(tomMatch517NameNumber_end_3, tomMatch517NameNumber_begin_2)));}}}}if ((tomMatch517NameNumber_freshVar_6 ==  false )) {if ( true ) {

        errors = tom_cons_list_concAlert(entry,tom_append_list_concAlert(errors,tom_empty_list_concAlert()));
        nbErrors++;    
      }}}}}}}
    
  }
  
  public int getNbErrors() {
    return nbErrors;
  }

  public int getNbWarnings() {
    return nbWarnings;
  }

  public boolean hasErrors() {
    return (nbErrors>0);
  }

  public boolean hasWarnings() {
    return (nbWarnings>0);
  }

  public AlertList getErrors() {
    return errors;
  }

  public AlertList getWarnings() {
    return warnings;
  }
  
  public void concat(RuntimeAlert newErrors) {
    if(newErrors.getErrors() != tom_empty_list_concAlert()) {
      AlertList newAlerts = newErrors.getErrors();
      errors = tom_append_list_concAlert(newAlerts,tom_append_list_concAlert(errors,tom_empty_list_concAlert()));
      nbErrors += newErrors.getNbErrors();      
    }
    if(newErrors.getWarnings() != tom_empty_list_concAlert()) {
      AlertList newAlerts = newErrors.getWarnings();
      warnings = tom_append_list_concAlert(newAlerts,tom_append_list_concAlert(warnings,tom_empty_list_concAlert()));
      nbWarnings += newErrors.getNbWarnings();
    }
  }

  /**
   * @param record
   */
  public void add(PlatformLogRecord record) {
    
	PlatformFormatter formatter = new PlatformFormatter();   
	  
	if(record.getLevel() == Level.SEVERE) {
      addError(formatter.formatMessage(record), record.getFilePath(), record.getLine());
    } else if(record.getLevel() == Level.WARNING) {
      addWarning(formatter.formatMessage(record), record.getFilePath(), record.getLine());
    }
  }

} //class RuntimeAlert
