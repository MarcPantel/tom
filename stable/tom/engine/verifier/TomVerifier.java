/* Generated by TOM (version 2.3rc0): Do not edit this file *//*
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
 * Antoine Reilles        e-mail: Antoine.Reilles@loria.fr
 **/

package jtom.verifier;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.*;
import java.util.logging.Level;

import jtom.adt.tomsignature.*;
import jtom.adt.tomsignature.types.*;
import jtom.TomMessage;
import jtom.tools.TomGenericPlugin;
import tom.library.traversal.Collect2;
import tom.library.traversal.Replace1;
import tom.platform.OptionParser;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import aterm.ATerm;
import jtom.adt.il.types.*;
import jtom.adt.zenon.types.*;
/**
 * The TomVerifier plugin.
 */
public class TomVerifier extends TomGenericPlugin {
  
  /* Generated by TOM (version 2.3rc0): Do not edit this file *//* Generated by TOM (version 2.3rc0): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/  /* Generated by TOM (version 2.3rc0): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/     /* Generated by TOM (version 2.3rc0): Do not edit this file *//*  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */   /* Generated by TOM (version 2.3rc0): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/   /* Generated by TOM (version 2.3rc0): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/   /* Generated by TOM (version 2.3rc0): Do not edit this file *//*  *  * Copyright (c) 2004-2005, Pierre-Etienne Moreau  * All rights reserved.  *   * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are  * met:   *  - Redistributions of source code must retain the above copyright  *  notice, this list of conditions and the following disclaimer.    *  - Redistributions in binary form must reproduce the above copyright  *  notice, this list of conditions and the following disclaimer in the  *  documentation and/or other materials provided with the distribution.  *  - Neither the name of the INRIA nor the names of its  *  contributors may be used to endorse or promote products derived from  *  this software without specific prior written permission.  *   * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  *   **/     
  
  public static final String DECLARED_OPTIONS = 
    "<options>" +
    "<boolean name='verify' altName='' description='Verify correctness of match compilation' value='false'/>" +
    "<boolean name='noReduce' altName='' description='Do not simplify extracted constraints (depends on --verify)' value='false'/>" +
    "<boolean name='camlSemantics' altName='' description='Verify with caml semantics for match' value='false'/>" +
    "</options>";

  public static final String ZENON_SUFFIX = ".zv";
  
  protected Verifier verif;
  protected ZenonOutput zenon;

  public TomVerifier() {
    super("TomVerifier");
  }

  public void run() {
    boolean camlsemantics = getOptionBooleanValue("camlSemantics");
    verif = new Verifier(camlsemantics);
    verif.setSymbolTable(this.symbolTable());
    // delay the zenonoutput creation, as it needs the verifiers
    // symboltable to be properly set
    if(isActivated()) {
      zenon = new ZenonOutput(verif);
      long startChrono = System.currentTimeMillis();
      try {

        Collection matchingCode = getMatchingCode();

        // Collection derivations = getDerivations(matchingCode);
        // System.out.println("Derivations : " + derivations);

        Map rawConstraints = getRawConstraints(matchingCode);
        //System.out.println(rawConstraints);

        // reduce constraints
        verif.mappingReduce(rawConstraints);
        if (!getOptionBooleanValue("noReduce")) {
          verif.booleanReduce(rawConstraints);
        }

        Collection zspecSet = zenon.zspecSetFromConstraintMap(rawConstraints);

        // the latex output stuff
        // LatexOutput output;
        // output = new LatexOutput(this);
        // String latex = output.build_latex(derivations);
        // System.out.println(latex);

        // the zenon output stuff
        // Collection zen = zenon.zspecSetFromDerivationTreeSet(derivations);

        ZenonBackend back = new ZenonBackend(verif);
        //System.out.println(back.genZSpecCollection(zen));
        String output = back.genZSpecCollection(zspecSet);
        
        try {
          Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                    getStreamManager().getOutputFileNameWithoutSuffix() + ZENON_SUFFIX
                    ))));
          writer.write(output);
          writer.close();
        } catch (IOException e) {
          getLogger().log( Level.SEVERE, TomMessage.backendIOException.getMessage(),
              new Object[]{getStreamManager().getOutputFile().getName(), e.getMessage()} );
          return;
        }

        // The stats output stuff
        // StatOutput stats;
        // stats = new StatOutput(this);
        // String statistics = stats.build_stats(derivations);
        // System.out.println(statistics);

        // verbose
        getLogger().log(Level.INFO, TomMessage.tomVerificationPhase.getMessage(),
                        new Integer((int)(System.currentTimeMillis()-startChrono)));
        
      } catch (Exception e) {
        getLogger().log(Level.SEVERE, TomMessage.exceptionMessage.getMessage(),
                         new Object[]{getClass().getName(),
                                      getStreamManager().getInputFile().getName(),
                                      e.getMessage()} );
        e.printStackTrace();
      }
    } else {      
      // Not active plugin
      getLogger().log(Level.INFO, TomMessage.verifierInactivated.getMessage());
    }
  }

  protected Collection getMatchingCode() {
        // here the extraction stuff
        Collection matchSet = collectMatch((TomTerm)getWorkingTerm());

        Collection purified = purify(matchSet);
        // System.out.println("Purified : " + purified);        

        // removes all associative patterns
        filterAssociative(purified);

        return purified;
  }
  
  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(TomVerifier.DECLARED_OPTIONS);
  }

  private boolean isActivated() {
    return getOptionBooleanValue("verify");
  }
  
  private Collect2 matchCollector = new Collect2() {
      public boolean apply(ATerm subject, Object astore) {
        Collection store = (Collection)astore;
        if (subject instanceof Instruction) {
           { jtom.adt.tomsignature.types.Instruction tom_match1_1=(( jtom.adt.tomsignature.types.Instruction)subject); if(tom_is_fun_sym_CompiledMatch(tom_match1_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match1_1_automataInst=tom_get_slot_CompiledMatch_automataInst(tom_match1_1);

              store.add(tom_match1_1_automataInst);
            } }



              return true;
            }
//end match
        } else { 
          return true;
        }
      }//end apply
    }; //end new
  
  public Collection collectMatch(TomTerm subject) {
    Collection result = new HashSet();
    traversal().genericCollect(subject,matchCollector,result);
    return result;
  }

  public Collection purify(Collection subject) {
    Collection purified = new HashSet();
    Iterator it = subject.iterator();
    while (it.hasNext()) {
      Instruction cm = (Instruction)it.next();
      // simplify the IL automata
      purified.add((simplifyIl(cm)));
    }
    return purified;
  }
  
  Replace1 ilSimplifier = new Replace1() {
      public ATerm apply(ATerm subject) {
        if (subject instanceof Expression) {
           { jtom.adt.tomsignature.types.Expression tom_match2_1=(( jtom.adt.tomsignature.types.Expression)subject); if(tom_is_fun_sym_Or(tom_match2_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match2_1_arg1=tom_get_slot_Or_arg1(tom_match2_1); { jtom.adt.tomsignature.types.Expression tom_match2_1_arg2=tom_get_slot_Or_arg2(tom_match2_1); if(tom_is_fun_sym_FalseTL(tom_match2_1_arg2) ||  false ) {

              return traversal().genericTraversal(tom_match2_1_arg1,this);
             }}} }}

        } // end instanceof Expression
        else if (subject instanceof Instruction) {
          // the checkstamp should be managed another way 
           { jtom.adt.tomsignature.types.Instruction tom_match3_1=(( jtom.adt.tomsignature.types.Instruction)subject); if(tom_is_fun_sym_If(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Expression tom_match3_1_condition=tom_get_slot_If_condition(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_succesInst=tom_get_slot_If_succesInst(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_failureInst=tom_get_slot_If_failureInst(tom_match3_1); if(tom_is_fun_sym_TrueTL(tom_match3_1_condition) ||  false ) { if(tom_is_fun_sym_Nop(tom_match3_1_failureInst) ||  false ) {

              return traversal().genericTraversal(tom_match3_1_succesInst,this);
             } }}}} } if(tom_is_fun_sym_AbstractBlock(tom_match3_1) || tom_is_fun_sym_UnamedBlock(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList=tom_get_slot_UnamedBlock_instList(tom_match3_1); if(tom_is_fun_sym_concInstruction(tom_match3_1_instList) ||  false ) { { jtom.adt.tomsignature.types.InstructionList tom_match3_1_instList_list1=tom_match3_1_instList; if(!(tom_is_empty_concInstruction_InstructionList(tom_match3_1_instList_list1))) { { jtom.adt.tomsignature.types.Instruction tom_match3_1_instList_1=tom_get_head_concInstruction_InstructionList(tom_match3_1_instList_list1);tom_match3_1_instList_list1=tom_get_tail_concInstruction_InstructionList(tom_match3_1_instList_list1); if(tom_is_fun_sym_CheckStamp(tom_match3_1_instList_1) ||  false ) { if(!(tom_is_empty_concInstruction_InstructionList(tom_match3_1_instList_list1))) { { jtom.adt.tomsignature.types.Instruction tom_inst=tom_get_head_concInstruction_InstructionList(tom_match3_1_instList_list1);tom_match3_1_instList_list1=tom_get_tail_concInstruction_InstructionList(tom_match3_1_instList_list1); if(tom_is_empty_concInstruction_InstructionList(tom_match3_1_instList_list1)) {

              return traversal().genericTraversal(tom_inst,this);
             }} } }} }} }} } if(tom_is_fun_sym_LetAssign(tom_match3_1) || tom_is_fun_sym_LetRef(tom_match3_1) || tom_is_fun_sym_Let(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.TomTerm tom_match3_1_variable=tom_get_slot_Let_variable(tom_match3_1); { jtom.adt.tomsignature.types.Instruction tom_match3_1_astInstruction=tom_get_slot_Let_astInstruction(tom_match3_1); if(tom_is_fun_sym_UnamedVariableStar(tom_match3_1_variable) || tom_is_fun_sym_UnamedVariable(tom_match3_1_variable) ||  false ) {

              return traversal().genericTraversal(tom_match3_1_astInstruction,this);
             }}} } if(tom_is_fun_sym_CompiledPattern(tom_match3_1) ||  false ) { { jtom.adt.tomsignature.types.Instruction tom_match3_1_automataInst=tom_get_slot_CompiledPattern_automataInst(tom_match3_1);


              return traversal().genericTraversal(tom_match3_1_automataInst,this);
            } }}


        } // end instanceof Instruction
        /*
         * Default case : Traversal
         */
        return traversal().genericTraversal(subject,this);
      }//end apply
    };//end new Replace1 ilSimplifier
  
  private Instruction simplifyIl(Instruction subject) {
    return (Instruction) ilSimplifier.apply(subject);
  }
  
  void filterAssociative(Collection c) {
    for (Iterator i = c.iterator(); i.hasNext(); )
      if (containsAssociativeOperator((Instruction) i.next()))
        i.remove();
  }

  boolean containsAssociativeOperator(Instruction subject) {
    Collection result = new HashSet();
    traversal().genericCollect(subject,associativeOperatorCollector,result);
    return !result.isEmpty();   
  }

  private Collect2 associativeOperatorCollector = new Collect2() {
      public boolean apply(ATerm subject, Object astore) {
        Collection store = (Collection)astore;
        if (subject instanceof Instruction) {
           { jtom.adt.tomsignature.types.Instruction tom_match4_1=(( jtom.adt.tomsignature.types.Instruction)subject); if(tom_is_fun_sym_LetRef(tom_match4_1) ||  false ) {

              store.add(subject);
             } if(tom_is_fun_sym_WhileDo(tom_match4_1) ||  false ) {

              store.add(subject);
             } if(tom_is_fun_sym_DoWhile(tom_match4_1) ||  false ) {

              store.add(subject);
             }}
//end match
        } 
        else if (subject instanceof Expression) {
           { jtom.adt.tomsignature.types.Expression tom_match5_1=(( jtom.adt.tomsignature.types.Expression)subject); if(tom_is_fun_sym_Or(tom_match5_1) ||  false ) {


              store.add(subject);
             }}

        }
        return true;
      }//end apply
    }; //end new  

  public Collection getDerivations(Collection subject) {
    Collection derivations = new HashSet();
    Iterator it = subject.iterator();
    
    while (it.hasNext()) {
      Instruction automata = (Instruction) it.next();
      Collection trees = verif.build_tree(automata);
      derivations.addAll(trees);
    }
    return derivations;
  }

  public Map getRawConstraints(Collection subject) {
    Map rawConstraints = new HashMap();
    Iterator it = subject.iterator();
    
    while (it.hasNext()) {
      Instruction automata = (Instruction) it.next();
      Map trees = verif.getConstraints(automata);
      rawConstraints.putAll(trees);
    }
    return rawConstraints;
  }
  
  public String patternToString(ATerm patternList) {
    return patternToString((PatternList) patternList);
  }

  public String patternToString(PatternList patternList) {
    StringBuffer result = new StringBuffer();
    Pattern h = null;
    PatternList tail = patternList;
    if(!tail.isEmpty()) {
      h = tail.getHead();
      tail = tail.getTail();
      result.append(patternToString(h));
    }

    while(!tail.isEmpty()) {
      h = tail.getHead();
      result.append("," + patternToString(h));
      tail = tail.getTail();
    }
    return result.toString();
  }

  public String patternToString(Pattern pattern) {
    String result = "";
     { jtom.adt.tomsignature.types.Pattern tom_match6_1=(( jtom.adt.tomsignature.types.Pattern)pattern); if(tom_is_fun_sym_Pattern(tom_match6_1) ||  false ) { { jtom.adt.tomsignature.types.TomList tom_match6_1_tomList=tom_get_slot_Pattern_tomList(tom_match6_1);

        return patternToString(tom_match6_1_tomList);
      } }}

    return result;
  }

  public String patternToString(TomList tomList) {
    StringBuffer result = new StringBuffer();
    TomTerm h = null;
    TomList tail = tomList;
    if(!tail.isEmpty()) {
      h = tail.getHead();
      tail = tail.getTail();
      result.append(patternToString(h));
    }

    while(!tail.isEmpty()) {
      h = tail.getHead();
      result.append("," + patternToString(h));
      tail = tail.getTail();
    }
    return result.toString();
  }
  public String patternToString(TomTerm tomTerm) {
     { jtom.adt.tomsignature.types.TomTerm tom_match7_1=(( jtom.adt.tomsignature.types.TomTerm)tomTerm); if(tom_is_fun_sym_TermAppl(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.NameList tom_match7_1_nameList=tom_get_slot_TermAppl_nameList(tom_match7_1); { jtom.adt.tomsignature.types.TomList tom_match7_1_args=tom_get_slot_TermAppl_args(tom_match7_1); if(tom_is_fun_sym_concTomName(tom_match7_1_nameList) ||  false ) { { jtom.adt.tomsignature.types.NameList tom_match7_1_nameList_list1=tom_match7_1_nameList; if(!(tom_is_empty_concTomName_NameList(tom_match7_1_nameList_list1))) { { jtom.adt.tomsignature.types.TomName tom_match7_1_nameList_1=tom_get_head_concTomName_NameList(tom_match7_1_nameList_list1);tom_match7_1_nameList_list1=tom_get_tail_concTomName_NameList(tom_match7_1_nameList_list1); if(tom_is_fun_sym_Name(tom_match7_1_nameList_1) ||  false ) { { String  tom_match7_1_nameList_1_string=tom_get_slot_Name_string(tom_match7_1_nameList_1); { String  tom_name=tom_match7_1_nameList_1_string; { jtom.adt.tomsignature.types.TomList tom_childrens=tom_match7_1_args;

        if (tom_childrens.isEmpty()) {
          return tom_name;
        } else {
          tom_name= tom_name+ "(";
          TomTerm head = tom_childrens.getHead();
          tom_name+= patternToString(head);
          TomList tail = tom_childrens.getTail();
          while(!tail.isEmpty()) {
            head = tail.getHead();
            tom_name+= "," + patternToString(head);
            tail = tail.getTail();
          }
          tom_name+= ")";
          return tom_name;
        }
      }}} }} }} }}} } if(tom_is_fun_sym_Variable(tom_match7_1) ||  false ) { { jtom.adt.tomsignature.types.TomName tom_match7_1_astName=tom_get_slot_Variable_astName(tom_match7_1); if(tom_is_fun_sym_Name(tom_match7_1_astName) ||  false ) { { String  tom_match7_1_astName_string=tom_get_slot_Name_string(tom_match7_1_astName);

        return tom_match7_1_astName_string;
      } }} } if(tom_is_fun_sym_UnamedVariable(tom_match7_1) ||  false ) {

        return "\\_";
       }}

    return "StrangePattern" + tomTerm;
  }

} // class TomVerifier
