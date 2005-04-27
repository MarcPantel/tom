/* Generated by TOM (version 2.2rc1): Do not edit this file *//*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2005, INRIA
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
 * **/

package jtom.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import jtom.TomBase;
import jtom.adt.tomsignature.*;
import jtom.adt.tomsignature.types.*;
import jtom.tools.TomGenericPlugin;
import tom.library.traversal.Collect2;
import tom.library.traversal.Replace1;
import aterm.ATerm;
import aterm.ATermList;

public class PILFactory extends TomBase {
  
  /* Generated by TOM (version 2.2rc1): Do not edit this file *//* Generated by TOM (version 2.2rc1): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/  /* Generated by TOM (version 2.2rc1): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/     /* Generated by TOM (version 2.2rc1): Do not edit this file *//*  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */   /* Generated by TOM (version 2.2rc1): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/   /* Generated by TOM (version 2.2rc1): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/   /* Generated by TOM (version 2.2rc1): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/     

  /**
   * level specifies the level of details of the output
   * 0 is identity
   * 1 removes options
   */
  private int level = 0;
  
  public PILFactory() {
    super();
    init(1);
  }

  void init (int level) {
    this.level = level;
  }

  public TomTerm reduce(TomTerm subject) {
    TomTerm res = subject;
    res = remove(res);

    return res;
  }

  TomTerm remove(TomTerm subject) {
    return (TomTerm) replace_remove.apply(subject);
  }

 public ATerm remove(ATerm subject) {
    return replace_remove.apply(subject);
  }

  Replace1 replace_remove = new Replace1() {
      public ATerm apply(ATerm subject) {
        // removes options
        if (subject instanceof OptionList) {
          return tom_empty_list_concOption();
        } else if (subject instanceof Option) {
          return tom_make_noOption();
        } else if (subject instanceof TargetLanguage) {
          // removes TargetLanguage
          return tom_make_noTL();
        } else if (subject instanceof TomType) {
          // removes Type
           { jtom.adt.tomsignature.types.TomType tom_match1_1=(( jtom.adt.tomsignature.types.TomType)subject); if(tom_is_fun_sym_Type(tom_match1_1) ||  false ) {
 return tom_make_EmptyType(); }}

        } else if (subject instanceof Expression) {
        // clean Expressions
           { jtom.adt.tomsignature.types.Expression tom_match2_1=(( jtom.adt.tomsignature.types.Expression)subject); if(tom_is_fun_sym_Cast(tom_match2_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match2_1_source=tom_get_slot_Cast_source(tom_match2_1); { jtom.adt.tomsignature.types.Expression e=tom_match2_1_source;
 return this.apply(e); }} } if(tom_is_fun_sym_Or(tom_match2_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match2_1_arg1=tom_get_slot_Or_arg1(tom_match2_1); { jtom.adt.tomsignature.types.Expression tom_match2_1_arg2=tom_get_slot_Or_arg2(tom_match2_1); { jtom.adt.tomsignature.types.Expression e=tom_match2_1_arg1; if(tom_is_fun_sym_FalseTL(tom_match2_1_arg2) ||  false ) {
 return this.apply(e);  }}}} } if(tom_is_fun_sym_EqualFunctionSymbol(tom_match2_1) ||  false ) { { jtom.adt.tomsignature.types.TomType tom_match2_1_astType=tom_get_slot_EqualFunctionSymbol_astType(tom_match2_1); { jtom.adt.tomsignature.types.TomTerm tom_match2_1_exp1=tom_get_slot_EqualFunctionSymbol_exp1(tom_match2_1); { jtom.adt.tomsignature.types.TomTerm tom_match2_1_exp2=tom_get_slot_EqualFunctionSymbol_exp2(tom_match2_1); { jtom.adt.tomsignature.types.TomType type=tom_match2_1_astType; { jtom.adt.tomsignature.types.TomTerm t1=tom_match2_1_exp1; if(tom_is_fun_sym_RecordAppl(tom_match2_1_exp2) ||  false ) { { jtom.adt.tomsignature.types.TomTerm appl=tom_match2_1_exp2; { jtom.adt.tomsignature.types.SlotList tom_match2_1_exp2_slots=tom_get_slot_RecordAppl_slots(tom_match2_1_exp2); if(tom_is_fun_sym_concSlot(tom_match2_1_exp2_slots) ||  false ) { { jtom.adt.tomsignature.types.SlotList tom_match2_1_exp2_slots_list1=tom_match2_1_exp2_slots; if(!(tom_is_empty_concSlot_SlotList(tom_match2_1_exp2_slots_list1))) { { jtom.adt.tomsignature.types.Slot x=tom_get_head_concSlot_SlotList(tom_match2_1_exp2_slots_list1);tom_match2_1_exp2_slots_list1=tom_get_tail_concSlot_SlotList(tom_match2_1_exp2_slots_list1);

              return this.apply(tom_make_EqualFunctionSymbol(type,t1,appl.setArgs(tom_empty_list_concTomTerm())));
            } }} }}} }}}}}} }}

        }


        /* Default case : Traversal */
        return traversal().genericTraversal(subject,this);
      }
    };

  public String prettyPrintCompiledMatch(TomTerm subject) {
    String res = "";
    Collection matches = collectMatch(subject);
    Iterator it = matches.iterator();
    while(it.hasNext()) {
      Instruction cm = (Instruction) it.next();
      res += prettyPrint(cm);
      res += "\n";
    }
    return res;
  }
  public String prettyPrint(ATerm subject) {
    if (subject instanceof Instruction) {
       { jtom.adt.tomsignature.types.Instruction tom_match3_1=(( jtom.adt.tomsignature.types.Instruction)subject); if(tom_is_fun_sym_CompiledMatch(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match3_1_automataInst=tom_get_slot_CompiledMatch_automataInst(tom_match3_1); { jtom.adt.tomsignature.types.Instruction automata=tom_match3_1_automataInst;
 
          return prettyPrint(automata); 
        }} } if(tom_is_fun_sym_Let(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match3_1_variable=tom_get_slot_Let_variable(tom_match3_1); { jtom.adt.tomsignature.types.Expression tom_match3_1_source=tom_get_slot_Let_source(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_astInstruction=tom_get_slot_Let_astInstruction(tom_match3_1); { jtom.adt.tomsignature.types.TomTerm variable=tom_match3_1_variable; { jtom.adt.tomsignature.types.Expression src=tom_match3_1_source; { jtom.adt.tomsignature.types.Instruction body=tom_match3_1_astInstruction;



          return "let " + prettyPrint(variable) + " = " + prettyPrint(src) + " in\n\t" + prettyPrint(body).replaceAll("\n","\n\t");
        }}}}}} } if(tom_is_fun_sym_LetRef(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match3_1_variable=tom_get_slot_LetRef_variable(tom_match3_1); { jtom.adt.tomsignature.types.Expression tom_match3_1_source=tom_get_slot_LetRef_source(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_astInstruction=tom_get_slot_LetRef_astInstruction(tom_match3_1); { jtom.adt.tomsignature.types.TomTerm variable=tom_match3_1_variable; { jtom.adt.tomsignature.types.Expression src=tom_match3_1_source; { jtom.adt.tomsignature.types.Instruction body=tom_match3_1_astInstruction;


          return "letRef " + prettyPrint(variable) + " = " + prettyPrint(src) + " in\n\t" + prettyPrint(body).replaceAll("\n","\n\t");
        }}}}}} } if(tom_is_fun_sym_LetAssign(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match3_1_variable=tom_get_slot_LetAssign_variable(tom_match3_1); { jtom.adt.tomsignature.types.Expression tom_match3_1_source=tom_get_slot_LetAssign_source(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_astInstruction=tom_get_slot_LetAssign_astInstruction(tom_match3_1); { jtom.adt.tomsignature.types.TomTerm variable=tom_match3_1_variable; { jtom.adt.tomsignature.types.Expression src=tom_match3_1_source; { jtom.adt.tomsignature.types.Instruction body=tom_match3_1_astInstruction;


          return "letAssign " + prettyPrint(variable) + " = " + prettyPrint(src) + " in\n\t" + prettyPrint(body).replaceAll("\n","\n\t");
        }}}}}} } if(tom_is_fun_sym_DoWhile(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match3_1_doInst=tom_get_slot_DoWhile_doInst(tom_match3_1); { jtom.adt.tomsignature.types.Expression tom_match3_1_condition=tom_get_slot_DoWhile_condition(tom_match3_1); { jtom.adt.tomsignature.types.Instruction doInst=tom_match3_1_doInst; { jtom.adt.tomsignature.types.Expression condition=tom_match3_1_condition;



          return "do\n\t " + prettyPrint(doInst).replaceAll("\n","\n\t") +"while "+ prettyPrint(condition);
        }}}} } if(tom_is_fun_sym_WhileDo(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match3_1_condition=tom_get_slot_WhileDo_condition(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_doInst=tom_get_slot_WhileDo_doInst(tom_match3_1); { jtom.adt.tomsignature.types.Expression condition=tom_match3_1_condition; { jtom.adt.tomsignature.types.Instruction doInst=tom_match3_1_doInst;


          return "while "+ prettyPrint(condition)+"do\n\t " + prettyPrint(doInst).replaceAll("\n","\n\t");
        }}}} } if(tom_is_fun_sym_If(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match3_1_condition=tom_get_slot_If_condition(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_succesInst=tom_get_slot_If_succesInst(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_failureInst=tom_get_slot_If_failureInst(tom_match3_1); { jtom.adt.tomsignature.types.Expression cond=tom_match3_1_condition; { jtom.adt.tomsignature.types.Instruction success=tom_match3_1_succesInst; if(tom_is_fun_sym_Nop(tom_match3_1_failureInst) ||  false ) {



          return  "if " + prettyPrint(cond) + " then \n\t" + prettyPrint(success).replaceAll("\n","\n\t"); 
         }}}}}} } if(tom_is_fun_sym_If(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match3_1_condition=tom_get_slot_If_condition(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_succesInst=tom_get_slot_If_succesInst(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_failureInst=tom_get_slot_If_failureInst(tom_match3_1); { jtom.adt.tomsignature.types.Expression cond=tom_match3_1_condition; { jtom.adt.tomsignature.types.Instruction success=tom_match3_1_succesInst; { jtom.adt.tomsignature.types.Instruction failure=tom_match3_1_failureInst;


          return "if " + prettyPrint(cond) + " then \n\t" + prettyPrint(success).replaceAll("\n","\n\t") + "\n\telse " + prettyPrint(failure).replaceAll("\n","\n\t")+"\n";
        }}}}}} } if(tom_is_fun_sym_AbstractBlock(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList=tom_get_slot_AbstractBlock_instList(tom_match3_1); if(tom_is_fun_sym_concInstruction(tom_match3_1_instList) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList_list1=tom_match3_1_instList; { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList_begin1=tom_match3_1_instList_list1; { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList_end1=tom_match3_1_instList_list1;{ while (!(tom_is_empty_concInstruction_InstructionList(tom_match3_1_instList_end1))) {tom_match3_1_instList_list1=tom_match3_1_instList_end1;{ { jtom.adt.tomsignature.types.InstructionList x=tom_get_slice_concInstruction(tom_match3_1_instList_begin1,tom_match3_1_instList_end1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_instList_2=tom_get_head_concInstruction_InstructionList(tom_match3_1_instList_list1);tom_match3_1_instList_list1=tom_get_tail_concInstruction_InstructionList(tom_match3_1_instList_list1); if(tom_is_fun_sym_Nop(tom_match3_1_instList_2) ||  false ) { { jtom.adt.tomsignature.types.InstructionList y=tom_match3_1_instList_list1;



          return prettyPrint(tom_make_AbstractBlock(tom_append_list_concInstruction(x,tom_append_list_concInstruction(y,tom_empty_list_concInstruction()))));
        } }}}tom_match3_1_instList_end1=tom_get_tail_concInstruction_InstructionList(tom_match3_1_instList_end1);} }tom_match3_1_instList_list1=tom_match3_1_instList_begin1;}}}} }} } if(tom_is_fun_sym_AbstractBlock(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList=tom_get_slot_AbstractBlock_instList(tom_match3_1); { jtom.adt.tomsignature.types.InstructionList instList=tom_match3_1_instList;


          return prettyPrint(instList);
        }} } if(tom_is_fun_sym_UnamedBlock(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList=tom_get_slot_UnamedBlock_instList(tom_match3_1); { jtom.adt.tomsignature.types.InstructionList instList=tom_match3_1_instList;


          return prettyPrint(instList);
        }} } if(tom_is_fun_sym_TypedAction(tom_match3_1) ||  false ) {



         return "targetLanguageInstructions";
         } if(tom_is_fun_sym_CompiledPattern(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match3_1_automataInst=tom_get_slot_CompiledPattern_automataInst(tom_match3_1); { jtom.adt.tomsignature.types.Instruction automata=tom_match3_1_automataInst;

 
          return prettyPrint(automata); 
        }} } if(tom_is_fun_sym_CheckStamp(tom_match3_1) ||  false ) {


          return "";
         }}




    } else if (subject instanceof Expression) {
       { jtom.adt.tomsignature.types.Expression tom_match4_1=(( jtom.adt.tomsignature.types.Expression)subject); if(tom_is_fun_sym_TomTermToExpression(tom_match4_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match4_1_astTerm=tom_get_slot_TomTermToExpression_astTerm(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm astTerm=tom_match4_1_astTerm;

          return prettyPrint(astTerm);
        }} } if(tom_is_fun_sym_EqualFunctionSymbol(tom_match4_1) ||  false ) { { jtom.adt.tomsignature.types.TomType tom_match4_1_astType=tom_get_slot_EqualFunctionSymbol_astType(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm tom_match4_1_exp1=tom_get_slot_EqualFunctionSymbol_exp1(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm tom_match4_1_exp2=tom_get_slot_EqualFunctionSymbol_exp2(tom_match4_1); { jtom.adt.tomsignature.types.TomType astType=tom_match4_1_astType; { jtom.adt.tomsignature.types.TomTerm exp1=tom_match4_1_exp1; { jtom.adt.tomsignature.types.TomTerm exp2=tom_match4_1_exp2;


          return "is_fun_sym("+prettyPrint(exp1)+","+prettyPrint(exp2)+")";
        }}}}}} } if(tom_is_fun_sym_IsEmptyList(tom_match4_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match4_1_variable=tom_get_slot_IsEmptyList_variable(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm kid1=tom_match4_1_variable;


          return "is_empty("+prettyPrint(kid1)+")";
        }} } if(tom_is_fun_sym_GetHead(tom_match4_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match4_1_variable=tom_get_slot_GetHead_variable(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm variable=tom_match4_1_variable;


          return "getHead("+prettyPrint(variable)+")";
        }} } if(tom_is_fun_sym_GetSlot(tom_match4_1) ||  false ) { { jtom.adt.tomsignature.types.TomType tom_match4_1_codomain=tom_get_slot_GetSlot_codomain(tom_match4_1); { jtom.adt.tomsignature.types.TomName tom_match4_1_astName=tom_get_slot_GetSlot_astName(tom_match4_1); { String  tom_match4_1_slotNameString=tom_get_slot_GetSlot_slotNameString(tom_match4_1); { jtom.adt.tomsignature.types.TomTerm tom_match4_1_variable=tom_get_slot_GetSlot_variable(tom_match4_1); { jtom.adt.tomsignature.types.TomType codomain=tom_match4_1_codomain; { jtom.adt.tomsignature.types.TomName astName=tom_match4_1_astName; { String  slotNameString=tom_match4_1_slotNameString; { jtom.adt.tomsignature.types.TomTerm variable=tom_match4_1_variable;


          return "get_slot_"+prettyPrint(astName)+"_"+slotNameString+"("+prettyPrint(variable)+")";
        }}}}}}}} }}


    } else if (subject instanceof TomTerm) {
       { jtom.adt.tomsignature.types.TomTerm tom_match5_1=(( jtom.adt.tomsignature.types.TomTerm)subject); if(tom_is_fun_sym_Variable(tom_match5_1) ||  false ) { { jtom.adt.tomsignature.types.TomName tom_match5_1_astName=tom_get_slot_Variable_astName(tom_match5_1); { jtom.adt.tomsignature.types.TomName name=tom_match5_1_astName;

          return prettyPrint(name);
        }} } if(tom_is_fun_sym_VariableStar(tom_match5_1) ||  false ) { { jtom.adt.tomsignature.types.TomName tom_match5_1_astName=tom_get_slot_VariableStar_astName(tom_match5_1); { jtom.adt.tomsignature.types.TomName name=tom_match5_1_astName;


          return prettyPrint(name);
        }} } if(tom_is_fun_sym_Ref(tom_match5_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match5_1_tomTerm=tom_get_slot_Ref_tomTerm(tom_match5_1); { jtom.adt.tomsignature.types.TomTerm term=tom_match5_1_tomTerm;


          return prettyPrint(term);
        }} } if(tom_is_fun_sym_RecordAppl(tom_match5_1) ||  false ) { { jtom.adt.tomsignature.types.OptionList tom_match5_1_option=tom_get_slot_RecordAppl_option(tom_match5_1); { jtom.adt.tomsignature.types.NameList tom_match5_1_nameList=tom_get_slot_RecordAppl_nameList(tom_match5_1); { jtom.adt.tomsignature.types.SlotList tom_match5_1_slots=tom_get_slot_RecordAppl_slots(tom_match5_1); { jtom.adt.tomsignature.types.ConstraintList tom_match5_1_constraints=tom_get_slot_RecordAppl_constraints(tom_match5_1); { jtom.adt.tomsignature.types.OptionList optionList=tom_match5_1_option; { jtom.adt.tomsignature.types.NameList nameList=tom_match5_1_nameList; { jtom.adt.tomsignature.types.SlotList args=tom_match5_1_slots; { jtom.adt.tomsignature.types.ConstraintList constraints=tom_match5_1_constraints;


          return prettyPrint(nameList); 
        }}}}}}}} }}

    } else if (subject instanceof TomName) {
       { jtom.adt.tomsignature.types.TomName tom_match6_1=(( jtom.adt.tomsignature.types.TomName)subject); if(tom_is_fun_sym_PositionName(tom_match6_1) ||  false ) { { jtom.adt.tomsignature.types.TomNumberList tom_match6_1_numberList=tom_get_slot_PositionName_numberList(tom_match6_1); { jtom.adt.tomsignature.types.TomNumberList number_list=tom_match6_1_numberList;

          return "t"+prettyPrint(number_list);
        }} } if(tom_is_fun_sym_Name(tom_match6_1) ||  false ) { { String  tom_match6_1_string=tom_get_slot_Name_string(tom_match6_1); { String  string=tom_match6_1_string;

          return string;
        }} }}

    } else if (subject instanceof TomNumber) {
       { jtom.adt.tomsignature.types.TomNumber tom_match7_1=(( jtom.adt.tomsignature.types.TomNumber)subject); if(tom_is_fun_sym_Number(tom_match7_1) ||  false ) { { int  tom_match7_1_integer=tom_get_slot_Number_integer(tom_match7_1); { int  n=tom_match7_1_integer;

          return "_"+n;
        }} } if(tom_is_fun_sym_MatchNumber(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.TomNumber tom_match7_1_number=tom_get_slot_MatchNumber_number(tom_match7_1); { jtom.adt.tomsignature.types.TomNumber number=tom_match7_1_number;


          return prettyPrint(number);
        }} } if(tom_is_fun_sym_ListNumber(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.TomNumber tom_match7_1_number=tom_get_slot_ListNumber_number(tom_match7_1); { jtom.adt.tomsignature.types.TomNumber number=tom_match7_1_number;


          return "listNumber"+prettyPrint(number);
        }} } if(tom_is_fun_sym_Begin(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.TomNumber tom_match7_1_number=tom_get_slot_Begin_number(tom_match7_1); { jtom.adt.tomsignature.types.TomNumber number=tom_match7_1_number;


          return "begin"+prettyPrint(number);
        }} } if(tom_is_fun_sym_End(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.TomNumber tom_match7_1_number=tom_get_slot_End_number(tom_match7_1); { jtom.adt.tomsignature.types.TomNumber number=tom_match7_1_number;


          return "end"+prettyPrint(number);
        }} }}


      
    } else if(subject instanceof InstructionList) {
      ATermList list = (ATermList)subject;
      if(list.isEmpty()) {
        return "";
      } else {
        return prettyPrint(list.getFirst()) + "\n" + prettyPrint(list.getNext());
      }
    }  else if(subject instanceof TomNumberList) {
      ATermList list = (ATermList)subject;
      if(list.isEmpty()) {
        return "";
      } else {
        return prettyPrint(list.getFirst()) + prettyPrint(list.getNext());
      }
    }
    else if(subject instanceof ATermList) {
      ATermList list = (ATermList)subject;
      if(list.isEmpty()) {
        return "";
      } else {
        return prettyPrint(list.getFirst()) + " " + prettyPrint(list.getNext());
      }
    }

    return subject.toString();

  }

  private Collect2 collect_match = new Collect2() {
      public boolean apply(ATerm subject, Object astore) {
        Collection store = (Collection)astore;
        if (subject instanceof Instruction) {
           { jtom.adt.tomsignature.types.Instruction tom_match8_1=(( jtom.adt.tomsignature.types.Instruction)subject); if(tom_is_fun_sym_CompiledMatch(tom_match8_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match8_1_automataInst=tom_get_slot_CompiledMatch_automataInst(tom_match8_1); { jtom.adt.tomsignature.types.Instruction automata=tom_match8_1_automataInst;

              store.add(subject);
            }} }
 return true; }

        } else { return true; }
      }
    };
  
  public Collection collectMatch(TomTerm subject) {
    Collection result = new HashSet();
    traversal().genericCollect(subject,collect_match,result);
    return result;
  }


}
