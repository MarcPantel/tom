/* Generated by TOM (version 2.5alpha): Do not edit this file *//*
 * Gom
 *
 * Copyright (C) 2006 INRIA
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

package tom.gom.backend;

import tom.gom.adt.objects.*;
import tom.gom.adt.objects.types.*;

public abstract class TemplateHookedClass extends TemplateClass {
  protected HookList hooks;

  public TemplateHookedClass(ClassName className,HookList hooks) {
    super(className);
    this.hooks = hooks;
  }

  /* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file *//* Generated by TOM (version 2.5alpha): Do not edit this file */ private static boolean tom_terms_equal_String(String t1, String t2) {  return  (t1.equals(t2))  ;}  private static boolean tom_terms_equal_HookList(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_terms_equal_Hook(Object t1, Object t2) {  return  t1.equals(t2)  ;}private static boolean tom_is_fun_sym_BlockHook( tom.gom.adt.objects.types.Hook  t) {  return  t instanceof tom.gom.adt.objects.types.hook.BlockHook  ;}private static  String  tom_get_slot_BlockHook_Code( tom.gom.adt.objects.types.Hook  t) {  return  t.getCode()  ;}private static boolean tom_is_fun_sym_InterfaceHook( tom.gom.adt.objects.types.Hook  t) {  return  t instanceof tom.gom.adt.objects.types.hook.InterfaceHook  ;}private static  String  tom_get_slot_InterfaceHook_Code( tom.gom.adt.objects.types.Hook  t) {  return  t.getCode()  ;}private static boolean tom_is_fun_sym_ImportHook( tom.gom.adt.objects.types.Hook  t) {  return  t instanceof tom.gom.adt.objects.types.hook.ImportHook  ;}private static  String  tom_get_slot_ImportHook_Code( tom.gom.adt.objects.types.Hook  t) {  return  t.getCode()  ;}private static boolean tom_is_fun_sym_concHook( tom.gom.adt.objects.types.HookList  t) {  return  t instanceof tom.gom.adt.objects.types.hooklist.ConsconcHook || t instanceof tom.gom.adt.objects.types.hooklist.EmptyconcHook  ;}private static  tom.gom.adt.objects.types.HookList  tom_empty_list_concHook() { return  tom.gom.adt.objects.types.hooklist.EmptyconcHook.make() ; }private static  tom.gom.adt.objects.types.HookList  tom_cons_list_concHook( tom.gom.adt.objects.types.Hook  e,  tom.gom.adt.objects.types.HookList  l) { return  tom.gom.adt.objects.types.hooklist.ConsconcHook.make(e,l) ; }private static  tom.gom.adt.objects.types.Hook  tom_get_head_concHook_HookList( tom.gom.adt.objects.types.HookList  l) {  return  l.getHeadconcHook()  ;}private static  tom.gom.adt.objects.types.HookList  tom_get_tail_concHook_HookList( tom.gom.adt.objects.types.HookList  l) {  return  l.getTailconcHook()  ;}private static boolean tom_is_empty_concHook_HookList( tom.gom.adt.objects.types.HookList  l) {  return  l.isEmptyconcHook()  ;}private static  tom.gom.adt.objects.types.HookList  tom_append_list_concHook( tom.gom.adt.objects.types.HookList  l1,  tom.gom.adt.objects.types.HookList  l2) {    if(tom_is_empty_concHook_HookList(l1)) {     return l2;    } else if(tom_is_empty_concHook_HookList(l2)) {     return l1;    } else if(tom_is_empty_concHook_HookList(( tom.gom.adt.objects.types.HookList )tom_get_tail_concHook_HookList(l1))) {     return ( tom.gom.adt.objects.types.HookList )tom_cons_list_concHook(( tom.gom.adt.objects.types.Hook )tom_get_head_concHook_HookList(l1),l2);    } else {      return ( tom.gom.adt.objects.types.HookList )tom_cons_list_concHook(( tom.gom.adt.objects.types.Hook )tom_get_head_concHook_HookList(l1),tom_append_list_concHook(( tom.gom.adt.objects.types.HookList )tom_get_tail_concHook_HookList(l1),l2));    }   }  private static  tom.gom.adt.objects.types.HookList  tom_get_slice_concHook( tom.gom.adt.objects.types.HookList  begin,  tom.gom.adt.objects.types.HookList  end) {    if(tom_terms_equal_HookList(begin,end)) {      return ( tom.gom.adt.objects.types.HookList )tom_empty_list_concHook();    } else {      return ( tom.gom.adt.objects.types.HookList )tom_cons_list_concHook(( tom.gom.adt.objects.types.Hook )tom_get_head_concHook_HookList(begin),( tom.gom.adt.objects.types.HookList )tom_get_slice_concHook(( tom.gom.adt.objects.types.HookList )tom_get_tail_concHook_HookList(begin),end));    }   }   

  protected String generateBlock() {
    StringBuffer res = new StringBuffer();
     if(hooks instanceof  tom.gom.adt.objects.types.HookList ) { { tom.gom.adt.objects.types.HookList  tom_match1_1=(( tom.gom.adt.objects.types.HookList )hooks); if ( ( tom_is_fun_sym_concHook(tom_match1_1) ||  false  ) ) { { tom.gom.adt.objects.types.HookList  tom_match1_1_list1=tom_match1_1; { tom.gom.adt.objects.types.HookList  tom_match1_1_begin1=tom_match1_1_list1; { tom.gom.adt.objects.types.HookList  tom_match1_1_end1=tom_match1_1_list1; { while (!(tom_is_empty_concHook_HookList(tom_match1_1_end1))) {tom_match1_1_list1=tom_match1_1_end1; { { tom.gom.adt.objects.types.HookList  tom_L1=tom_get_slice_concHook(tom_match1_1_begin1,tom_match1_1_end1); { tom.gom.adt.objects.types.Hook  tom_match1_1_2=tom_get_head_concHook_HookList(tom_match1_1_list1);tom_match1_1_list1=tom_get_tail_concHook_HookList(tom_match1_1_list1); if ( ( tom_is_fun_sym_BlockHook(tom_match1_1_2) ||  false  ) ) { { String  tom_match1_1_2_Code=tom_get_slot_BlockHook_Code(tom_match1_1_2); { String  tom_code=tom_match1_1_2_Code; { tom.gom.adt.objects.types.HookList  tom_L2=tom_match1_1_list1; {boolean tom_match1_tom_anti_constraints_status= true ; if ((tom_match1_tom_anti_constraints_status ==  true )) { if ( true ) {

        //remove brackets
        res.append(tom_code.substring(1,tom_code.length()-1)+"\n");
        hooks = tom_append_list_concHook(tom_L1,tom_append_list_concHook(tom_L2,tom_empty_list_concHook()));
       } } } } } } } } }tom_match1_1_end1=tom_get_tail_concHook_HookList(tom_match1_1_end1); } }tom_match1_1_list1=tom_match1_1_begin1; } } } } } } }

    return res.toString();
  }

  protected String generateImport() {
    StringBuffer res = new StringBuffer();
     if(hooks instanceof  tom.gom.adt.objects.types.HookList ) { { tom.gom.adt.objects.types.HookList  tom_match2_1=(( tom.gom.adt.objects.types.HookList )hooks); if ( ( tom_is_fun_sym_concHook(tom_match2_1) ||  false  ) ) { { tom.gom.adt.objects.types.HookList  tom_match2_1_list1=tom_match2_1; { tom.gom.adt.objects.types.HookList  tom_match2_1_begin1=tom_match2_1_list1; { tom.gom.adt.objects.types.HookList  tom_match2_1_end1=tom_match2_1_list1; { while (!(tom_is_empty_concHook_HookList(tom_match2_1_end1))) {tom_match2_1_list1=tom_match2_1_end1; { { tom.gom.adt.objects.types.HookList  tom_L1=tom_get_slice_concHook(tom_match2_1_begin1,tom_match2_1_end1); { tom.gom.adt.objects.types.Hook  tom_match2_1_2=tom_get_head_concHook_HookList(tom_match2_1_list1);tom_match2_1_list1=tom_get_tail_concHook_HookList(tom_match2_1_list1); if ( ( tom_is_fun_sym_ImportHook(tom_match2_1_2) ||  false  ) ) { { String  tom_match2_1_2_Code=tom_get_slot_ImportHook_Code(tom_match2_1_2); { String  tom_code=tom_match2_1_2_Code; { tom.gom.adt.objects.types.HookList  tom_L2=tom_match2_1_list1; {boolean tom_match2_tom_anti_constraints_status= true ; if ((tom_match2_tom_anti_constraints_status ==  true )) { if ( true ) {

        //remove brackets
        res.append(tom_code.substring(1,tom_code.length()-1)+"\n");
        hooks = tom_append_list_concHook(tom_L1,tom_append_list_concHook(tom_L2,tom_empty_list_concHook()));
       } } } } } } } } }tom_match2_1_end1=tom_get_tail_concHook_HookList(tom_match2_1_end1); } }tom_match2_1_list1=tom_match2_1_begin1; } } } } } } }

    return res.toString();
  }

  protected String generateInterface() {
    StringBuffer res = new StringBuffer();
     if(hooks instanceof  tom.gom.adt.objects.types.HookList ) { { tom.gom.adt.objects.types.HookList  tom_match3_1=(( tom.gom.adt.objects.types.HookList )hooks); if ( ( tom_is_fun_sym_concHook(tom_match3_1) ||  false  ) ) { { tom.gom.adt.objects.types.HookList  tom_match3_1_list1=tom_match3_1; { tom.gom.adt.objects.types.HookList  tom_match3_1_begin1=tom_match3_1_list1; { tom.gom.adt.objects.types.HookList  tom_match3_1_end1=tom_match3_1_list1; { while (!(tom_is_empty_concHook_HookList(tom_match3_1_end1))) {tom_match3_1_list1=tom_match3_1_end1; { { tom.gom.adt.objects.types.HookList  tom_L1=tom_get_slice_concHook(tom_match3_1_begin1,tom_match3_1_end1); { tom.gom.adt.objects.types.Hook  tom_match3_1_2=tom_get_head_concHook_HookList(tom_match3_1_list1);tom_match3_1_list1=tom_get_tail_concHook_HookList(tom_match3_1_list1); if ( ( tom_is_fun_sym_InterfaceHook(tom_match3_1_2) ||  false  ) ) { { String  tom_match3_1_2_Code=tom_get_slot_InterfaceHook_Code(tom_match3_1_2); { String  tom_code=tom_match3_1_2_Code; { tom.gom.adt.objects.types.HookList  tom_L2=tom_match3_1_list1; {boolean tom_match3_tom_anti_constraints_status= true ; if ((tom_match3_tom_anti_constraints_status ==  true )) { if ( true ) {

        //remove brackets
        res.append(","+tom_code.substring(1,tom_code.length()-1).replaceAll("\n",""));
        hooks = tom_append_list_concHook(tom_L1,tom_append_list_concHook(tom_L2,tom_empty_list_concHook()));
       } } } } } } } } }tom_match3_1_end1=tom_get_tail_concHook_HookList(tom_match3_1_end1); } }tom_match3_1_list1=tom_match3_1_begin1; } } } } } } }

    return res.toString();
  }
}
