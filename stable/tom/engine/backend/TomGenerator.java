 /*
  
    TOM - To One Matching Compiler

    Copyright (C) 2000-2003  LORIA (CNRS, INPL, INRIA, UHP, U-Nancy 2)
			     Nancy, France.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

    Pierre-Etienne Moreau	e-mail: Pierre-Etienne.Moreau@loria.fr

*/

package jtom.backend;
  
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.HashMap;

import jtom.TomBase;
import jtom.adt.Declaration;
import jtom.adt.Expression;
import jtom.adt.Instruction;
import jtom.adt.Option;
import jtom.adt.OptionList;
import jtom.adt.Position;
import jtom.adt.SlotList;
import jtom.adt.TargetLanguage;
import jtom.adt.TomList;
import jtom.adt.TomName;
import jtom.adt.TomNumber;
import jtom.adt.TomNumberList;
import jtom.adt.TomSymbol;
import jtom.adt.TomTerm;
import jtom.adt.TomType;
import jtom.adt.TomTypeList;
import jtom.tools.TomTask;
import jtom.tools.OutputCode;
import jtom.tools.SingleLineOutputCode;
import jtom.tools.TomTaskInput;


public class TomGenerator extends TomBase implements TomTask {
  
  private TomTask nextTask;
  private String debugKey = null;
  private boolean cCode = false, eCode = false, jCode = false, supportedGoto = false, 
  	supportedBlock = false, debugMode = false, strictType = false, staticFunction = false, 
  	genDecl = false, pretty = false;
  
  public TomGenerator(jtom.TomEnvironment environment) {
    super(environment);
  }

// ------------------------------------------------------------
    
// ------------------------------------------------------------

 public void addTask(TomTask task) {
  	this.nextTask = task;
  }
  public void process(TomTaskInput input) {
    try {
      cCode = input.isCCode();
      eCode = input.isECode();
      jCode = input.isJCode();
      supportedGoto = input.isSupportedGoto(); 
      supportedBlock = input.isSupportedBlock();
      debugMode = input.isDebugMode();
      strictType = input.isStrictType();
      staticFunction = input.isStaticFunction();
      genDecl = input.isGenDecl();
      pretty = input.isPretty();
      long startChrono = 0;
      boolean verbose = input.isVerbose();
      if(verbose) {
        startChrono = System.currentTimeMillis();
      }
      int defaultDeep = 2;
      Writer writer = new BufferedWriter(new OutputStreamWriter(
                                           new FileOutputStream(input.getOutputFileName())));
      OutputCode out = new OutputCode(writer, cCode, pretty);
      generate(out,defaultDeep,input.getTerm());
      writer.close();
      if(verbose) {
        System.out.println("TOM generation phase (" + (System.currentTimeMillis()-startChrono)+ " ms)");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    if(nextTask != null) {
      nextTask.process(input);
    }
  }
  
  public TomTask getTask() {
  	return nextTask;
  }
    /*
     * Generate the goal language
     */
  
  private void generate(jtom.tools.OutputCode out, int deep, TomTerm subject)
    throws IOException {
      //System.out.println("Generate: " + subject);
      //%variable


     {  TomTerm tom_match1_1 = null; tom_match1_1 = ( TomTerm) subject;matchlab_match1_pattern1: {  TomList l = null; if(tom_is_fun_sym_Tom(tom_match1_1)) {  TomList tom_match1_1_1 = null; tom_match1_1_1 = ( TomList) tom_get_slot_Tom_tomList(tom_match1_1); l = ( TomList) tom_match1_1_1;

 
        generateList(out,deep,l);
        return;
       }}matchlab_match1_pattern2: {  TomList l = null; if(tom_is_fun_sym_TomInclude(tom_match1_1)) {  TomList tom_match1_1_1 = null; tom_match1_1_1 = ( TomList) tom_get_slot_TomInclude_tomList(tom_match1_1); l = ( TomList) tom_match1_1_1;

 
        if (jCode) {
          OutputCode singleLineOutput = new SingleLineOutputCode(out.getFile(), cCode, pretty);
          generateList(singleLineOutput,deep,l);
        } else {
          generateList(out,deep,l);
        }
        return;
       }}matchlab_match1_pattern3: {  String name = null; if(tom_is_fun_sym_BuildVariable(tom_match1_1)) {  TomName tom_match1_1_1 = null; tom_match1_1_1 = ( TomName) tom_get_slot_BuildVariable_astName(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); name = ( String) tom_match1_1_1_1;

 
        out.write(name);
        return;
       } }}matchlab_match1_pattern4: {  TomNumberList l1 = null; if(tom_is_fun_sym_BuildVariable(tom_match1_1)) {  TomName tom_match1_1_1 = null; tom_match1_1_1 = ( TomName) tom_get_slot_BuildVariable_astName(tom_match1_1); if(tom_is_fun_sym_PositionName(tom_match1_1_1)) {  TomNumberList tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match1_1_1); l1 = ( TomNumberList) tom_match1_1_1_1;

 
        out.write("tom" + numberListToIdentifier(l1));
          //out.write("tom");
          //numberListToIdentifier(out,l1);
        return;
       } }}matchlab_match1_pattern5: {  TomList argList = null;  String name = null; if(tom_is_fun_sym_BuildTerm(tom_match1_1)) {  TomName tom_match1_1_1 = null;  TomList tom_match1_1_2 = null; tom_match1_1_1 = ( TomName) tom_get_slot_BuildTerm_astName(tom_match1_1); tom_match1_1_2 = ( TomList) tom_get_slot_BuildTerm_args(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); name = ( String) tom_match1_1_1_1; argList = ( TomList) tom_match1_1_2;






 
        out.write("tom_make_");
        out.write(name);
        if(eCode && argList.isEmpty()) {
        } else {
          out.writeOpenBrace();
          while(!argList.isEmpty()) {
            generate(out,deep,argList.getHead());
            argList = argList.getTail();
            if(!argList.isEmpty()) {
              out.writeComa();
            }
          }
          out.writeCloseBrace();
        }
        return;
       } }}matchlab_match1_pattern6: {  String name = null;  TomList argList = null; if(tom_is_fun_sym_BuildList(tom_match1_1)) {  TomName tom_match1_1_1 = null;  TomList tom_match1_1_2 = null; tom_match1_1_1 = ( TomName) tom_get_slot_BuildList_astName(tom_match1_1); tom_match1_1_2 = ( TomList) tom_get_slot_BuildList_args(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); name = ( String) tom_match1_1_1_1; argList = ( TomList) tom_match1_1_2;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(name);
        String listType = getTLType(getSymbolCodomain(tomSymbol));
        int size = 0;
        while(!argList.isEmpty()) {
          TomTerm elt = argList.getHead();

          matchBlock: {
             {  TomTerm tom_match2_1 = null; tom_match2_1 = ( TomTerm) elt;matchlab_match2_pattern1: {  TomTerm tom_absvar1 = null; if(tom_is_fun_sym_Composite(tom_match2_1)) {  TomList tom_match2_1_1 = null; tom_match2_1_1 = ( TomList) tom_get_slot_Composite_args(tom_match2_1); if(tom_is_fun_sym_concTomTerm(tom_match2_1_1)) {  TomList tom_match2_1_1_list1 = null; tom_match2_1_1_list1 = ( TomList) tom_match2_1_1; if(!(tom_is_empty_TomList(tom_match2_1_1_list1))) { tom_absvar1 = ( TomTerm) tom_get_head_TomList(tom_match2_1_1_list1); tom_match2_1_1_list1 = ( TomList) tom_get_tail_TomList(tom_match2_1_1_list1); if(tom_is_empty_TomList(tom_match2_1_1_list1)) { {  TomTerm tom_match3_1 = null; tom_match3_1 = ( TomTerm) tom_absvar1;matchlab_match3_pattern1: { if(tom_is_fun_sym_VariableStar(tom_match3_1)) {  Option tom_match3_1_1 = null;  TomName tom_match3_1_2 = null;  TomType tom_match3_1_3 = null; tom_match3_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match3_1); tom_match3_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match3_1); tom_match3_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match3_1);
 
                out.write("tom_insert_list_" + name + "(");
                generate(out,deep,elt);
                out.write(",");
                break matchBlock;
               }} } } } } }}matchlab_match2_pattern2: { if(tom_is_fun_sym_VariableStar(tom_match2_1)) {  Option tom_match2_1_1 = null;  TomName tom_match2_1_2 = null;  TomType tom_match2_1_3 = null; tom_match2_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match2_1); tom_match2_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match2_1); tom_match2_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match2_1);                  out.write("tom_insert_list_" + name + "(");                 generate(out,deep,elt);                 out.write(",");                 break matchBlock;                }}matchlab_match2_pattern3: {

 
                out.write("tom_make_insert_" + name + "(");
                generate(out,deep,elt);
                out.write(",");
                break matchBlock;
              } }
 
          } // end matchBlock
          
          argList = argList.getTail();
          size++;
        }
        out.write("(" + listType + ") tom_make_empty_" + name + "()");
        for(int i=0; i<size; i++) {
          out.write(")");
        }
        return;
       } }}matchlab_match1_pattern7: {  TomList argList = null;  String name = null; if(tom_is_fun_sym_BuildArray(tom_match1_1)) {  TomName tom_match1_1_1 = null;  TomList tom_match1_1_2 = null; tom_match1_1_1 = ( TomName) tom_get_slot_BuildArray_astName(tom_match1_1); tom_match1_1_2 = ( TomList) tom_get_slot_BuildArray_args(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); name = ( String) tom_match1_1_1_1; argList = ( TomList) tom_match1_1_2;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(name);
        String listType = getTLType(getSymbolCodomain(tomSymbol));
        int size = 0;
        TomList reverse = reverse(argList);
        while(!reverse.isEmpty()) {
          TomTerm elt = reverse.getHead();

          matchBlock: {
             {  TomTerm tom_match4_1 = null; tom_match4_1 = ( TomTerm) elt;matchlab_match4_pattern1: {  TomTerm tom_absvar2 = null; if(tom_is_fun_sym_Composite(tom_match4_1)) {  TomList tom_match4_1_1 = null; tom_match4_1_1 = ( TomList) tom_get_slot_Composite_args(tom_match4_1); if(tom_is_fun_sym_concTomTerm(tom_match4_1_1)) {  TomList tom_match4_1_1_list1 = null; tom_match4_1_1_list1 = ( TomList) tom_match4_1_1; if(!(tom_is_empty_TomList(tom_match4_1_1_list1))) { tom_absvar2 = ( TomTerm) tom_get_head_TomList(tom_match4_1_1_list1); tom_match4_1_1_list1 = ( TomList) tom_get_tail_TomList(tom_match4_1_1_list1); if(tom_is_empty_TomList(tom_match4_1_1_list1)) { {  TomTerm tom_match5_1 = null; tom_match5_1 = ( TomTerm) tom_absvar2;matchlab_match5_pattern1: { if(tom_is_fun_sym_VariableStar(tom_match5_1)) {  Option tom_match5_1_1 = null;  TomName tom_match5_1_2 = null;  TomType tom_match5_1_3 = null; tom_match5_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match5_1); tom_match5_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match5_1); tom_match5_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match5_1);
 
                out.write("tom_append_array_" + name + "(");
                generate(out,deep,elt);
                out.write(",");
                break matchBlock;
               }} } } } } }}matchlab_match4_pattern2: { if(tom_is_fun_sym_VariableStar(tom_match4_1)) {  Option tom_match4_1_1 = null;  TomName tom_match4_1_2 = null;  TomType tom_match4_1_3 = null; tom_match4_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match4_1); tom_match4_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match4_1); tom_match4_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match4_1);                  out.write("tom_append_array_" + name + "(");                 generate(out,deep,elt);                 out.write(",");                 break matchBlock;                }}matchlab_match4_pattern3: {

 
                out.write("tom_make_append_" + name + "(");
                generate(out,deep,elt);
                out.write(",");
                break matchBlock;
              } }
 
          } // end matchBlock
          
          reverse = reverse.getTail();
          size++;
        }
        out.write("(" + listType + ") tom_make_empty_" + name + "(" + size + ")"); 
        for(int i=0; i<size; i++) { 
            //out.write("," + i + ")");
          out.write(")"); 
        }
        return;
       } }}matchlab_match1_pattern8: {  String name = null;  TomList argList = null; if(tom_is_fun_sym_FunctionCall(tom_match1_1)) {  TomName tom_match1_1_1 = null;  TomList tom_match1_1_2 = null; tom_match1_1_1 = ( TomName) tom_get_slot_FunctionCall_astName(tom_match1_1); tom_match1_1_2 = ( TomList) tom_get_slot_FunctionCall_args(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); name = ( String) tom_match1_1_1_1; argList = ( TomList) tom_match1_1_2;

 
        out.write(name + "(");
        while(!argList.isEmpty()) {
          generate(out,deep,argList.getHead());
          argList = argList.getTail();
          if(!argList.isEmpty()) {
            out.write(", ");
          }
        }
        out.write(")");
        return;
       } }}matchlab_match1_pattern9: {  TomList argList = null; if(tom_is_fun_sym_Composite(tom_match1_1)) {  TomList tom_match1_1_1 = null; tom_match1_1_1 = ( TomList) tom_get_slot_Composite_args(tom_match1_1); argList = ( TomList) tom_match1_1_1;

 
        while(!argList.isEmpty()) {
          generate(out,deep,argList.getHead());
          argList = argList.getTail();
        }
        return;
       }}matchlab_match1_pattern10: {  TomList namedBlockList = null;  TomList matchDeclarationList = null;  OptionList list = null; if(tom_is_fun_sym_CompiledMatch(tom_match1_1)) {  TomList tom_match1_1_1 = null;  TomList tom_match1_1_2 = null;  Option tom_match1_1_3 = null; tom_match1_1_1 = ( TomList) tom_get_slot_CompiledMatch_decls(tom_match1_1); tom_match1_1_2 = ( TomList) tom_get_slot_CompiledMatch_automataList(tom_match1_1); tom_match1_1_3 = ( Option) tom_get_slot_CompiledMatch_option(tom_match1_1); matchDeclarationList = ( TomList) tom_match1_1_1; namedBlockList = ( TomList) tom_match1_1_2; if(tom_is_fun_sym_Option(tom_match1_1_3)) {  OptionList tom_match1_1_3_1 = null; tom_match1_1_3_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match1_1_3); list = ( OptionList) tom_match1_1_3_1;

 
        boolean generated = hasGeneratedMatch(list);
        boolean defaultPattern = hasDefaultProd(list);
        Option orgTrack = null;
        if(supportedBlock) {
          generateInstruction(out,deep,tom_make_OpenBlock() );
        }
        if(debugMode && !generated) {
          orgTrack = findOriginTracking(list);
          debugKey = orgTrack.getFileName().getString() + orgTrack.getLine();
          out.write("jtom.debug.TomDebugger.debugger.enteringStructure(\""+debugKey+"\");\n");
        }
        generateList(out,deep+1,matchDeclarationList);
        generateList(out,deep+1,namedBlockList);
        if(debugMode && !generated && !defaultPattern) {
          out.write("jtom.debug.TomDebugger.debugger.leavingStructure(\""+debugKey+"\");\n");
        }
        if(supportedBlock) {
          generateInstruction(out,deep,tom_make_CloseBlock() );
        }
        return;
       } }}matchlab_match1_pattern11: {  TomList instList = null; if(tom_is_fun_sym_CompiledPattern(tom_match1_1)) {  TomList tom_match1_1_1 = null; tom_match1_1_1 = ( TomList) tom_get_slot_CompiledPattern_instList(tom_match1_1); instList = ( TomList) tom_match1_1_1;

 
        generateList(out,deep,instList);
        return;
       }}matchlab_match1_pattern12: {  Option option1 = null;  TomType type1 = null;  TomNumberList l1 = null; if(tom_is_fun_sym_Variable(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomType tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match1_1); tom_match1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match1_1); option1 = ( Option) tom_match1_1_1; if(tom_is_fun_sym_PositionName(tom_match1_1_2)) {  TomNumberList tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match1_1_2); l1 = ( TomNumberList) tom_match1_1_2_1; type1 = ( TomType) tom_match1_1_3;

 
          //System.out.println("Variable(option1,PositionName(l1),type1)");
          //System.out.println("subject = " + subject);
          //System.out.println("l1      = " + l1);

          /*
           * sans type: re-definition lorsque %variable est utilise
           * avec type: probleme en cas de filtrage dynamique
           */
        out.write("tom" + numberListToIdentifier(l1));
        return;
       } }}matchlab_match1_pattern13: {  Option option1 = null;  String name1 = null;  TomType type1 = null; if(tom_is_fun_sym_Variable(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomType tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match1_1); tom_match1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match1_1); option1 = ( Option) tom_match1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); name1 = ( String) tom_match1_1_2_1; type1 = ( TomType) tom_match1_1_3;

 
        out.write(name1);
        return;
       } }}matchlab_match1_pattern14: {  TomNumberList l1 = null;  TomType type1 = null;  Option option1 = null; if(tom_is_fun_sym_VariableStar(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomType tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match1_1); tom_match1_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match1_1); option1 = ( Option) tom_match1_1_1; if(tom_is_fun_sym_PositionName(tom_match1_1_2)) {  TomNumberList tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match1_1_2); l1 = ( TomNumberList) tom_match1_1_2_1; type1 = ( TomType) tom_match1_1_3;

 
        out.write("tom" + numberListToIdentifier(l1));
        return;  
       } }}matchlab_match1_pattern15: {  TomType type1 = null;  Option option1 = null;  String name1 = null; if(tom_is_fun_sym_VariableStar(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomType tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match1_1); tom_match1_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match1_1); option1 = ( Option) tom_match1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); name1 = ( String) tom_match1_1_2_1; type1 = ( TomType) tom_match1_1_3;

 
        out.write(name1);
        return;
       } }}matchlab_match1_pattern16: {  TomType tlType = null;  String type = null;  TomTerm var = null;  Option option1 = null;  TomName name1 = null; if(tom_is_fun_sym_Declaration(tom_match1_1)) {  TomTerm tom_match1_1_1 = null; tom_match1_1_1 = ( TomTerm) tom_get_slot_Declaration_kid1(tom_match1_1); if(tom_is_fun_sym_Variable(tom_match1_1_1)) {  Option tom_match1_1_1_1 = null;  TomName tom_match1_1_1_2 = null;  TomType tom_match1_1_1_3 = null; tom_match1_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match1_1_1); tom_match1_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match1_1_1); tom_match1_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match1_1_1); var = ( TomTerm) tom_match1_1_1; option1 = ( Option) tom_match1_1_1_1; name1 = ( TomName) tom_match1_1_1_2; if(tom_is_fun_sym_Type(tom_match1_1_1_3)) {  TomType tom_match1_1_1_3_1 = null;  TomType tom_match1_1_1_3_2 = null; tom_match1_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match1_1_1_3); tom_match1_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match1_1_1_3); if(tom_is_fun_sym_TomType(tom_match1_1_1_3_1)) {  String tom_match1_1_1_3_1_1 = null; tom_match1_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match1_1_1_3_1); type = ( String) tom_match1_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match1_1_1_3_2)) {  TargetLanguage tom_match1_1_1_3_2_1 = null; tom_match1_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match1_1_1_3_2); tlType = ( TomType) tom_match1_1_1_3_2;


 
        if(cCode || jCode) {
          out.write(deep,getTLCode(tlType) + " ");
          generate(out,deep,var);
        } else if(eCode) {
          generate(out,deep,var);
          out.write(deep,": " + getTLCode(tlType));
        }
        
        if(jCode &&
           !isBoolType(type) &&
           !isIntType(type) &&
           !isDoubleType(type)) {
          out.writeln(" = null;");
        } else {
          out.writeln(";");
        }
        return;
       } } } } }}matchlab_match1_pattern17: {  String type = null;  TomName name1 = null;  Option option1 = null;  TomTerm var = null;  TomType tlType = null; if(tom_is_fun_sym_Declaration(tom_match1_1)) {  TomTerm tom_match1_1_1 = null; tom_match1_1_1 = ( TomTerm) tom_get_slot_Declaration_kid1(tom_match1_1); if(tom_is_fun_sym_VariableStar(tom_match1_1_1)) {  Option tom_match1_1_1_1 = null;  TomName tom_match1_1_1_2 = null;  TomType tom_match1_1_1_3 = null; tom_match1_1_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match1_1_1); tom_match1_1_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match1_1_1); tom_match1_1_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match1_1_1); var = ( TomTerm) tom_match1_1_1; option1 = ( Option) tom_match1_1_1_1; name1 = ( TomName) tom_match1_1_1_2; if(tom_is_fun_sym_Type(tom_match1_1_1_3)) {  TomType tom_match1_1_1_3_1 = null;  TomType tom_match1_1_1_3_2 = null; tom_match1_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match1_1_1_3); tom_match1_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match1_1_1_3); if(tom_is_fun_sym_TomType(tom_match1_1_1_3_1)) {  String tom_match1_1_1_3_1_1 = null; tom_match1_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match1_1_1_3_1); type = ( String) tom_match1_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match1_1_1_3_2)) {  TargetLanguage tom_match1_1_1_3_2_1 = null; tom_match1_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match1_1_1_3_2); tlType = ( TomType) tom_match1_1_1_3_2;


 
        if(cCode || jCode) {
          out.write(deep,getTLCode(tlType) + " ");
          generate(out,deep,var);
        } else if(eCode) {
          generate(out,deep,var);
          out.write(deep,": " + getTLCode(tlType));
        }
        out.writeln(";");
        return;
       } } } } }}matchlab_match1_pattern18: {  String tomName = null;  TomList varList = null; if(tom_is_fun_sym_MakeFunctionBegin(tom_match1_1)) {  TomName tom_match1_1_1 = null;  TomTerm tom_match1_1_2 = null; tom_match1_1_1 = ( TomName) tom_get_slot_MakeFunctionBegin_astName(tom_match1_1); tom_match1_1_2 = ( TomTerm) tom_get_slot_MakeFunctionBegin_subjectListAST(tom_match1_1); if(tom_is_fun_sym_Name(tom_match1_1_1)) {  String tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( String) tom_get_slot_Name_string(tom_match1_1_1); tomName = ( String) tom_match1_1_1_1; if(tom_is_fun_sym_SubjectList(tom_match1_1_2)) {  TomList tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( TomList) tom_get_slot_SubjectList_tomList(tom_match1_1_2); varList = ( TomList) tom_match1_1_2_1;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        String glType = getTLType(getSymbolCodomain(tomSymbol));
        String name = tomSymbol.getAstName().getString();
        
        if(cCode || jCode) {
          out.write(deep,glType + " " + name + "(");
        } else if(eCode) {
          out.write(deep,name + "(");
        }
        while(!varList.isEmpty()) {
          TomTerm localVar = varList.getHead();
          matchBlock: {
             {  TomTerm tom_match6_1 = null; tom_match6_1 = ( TomTerm) localVar;matchlab_match6_pattern1: {  Option option2 = null;  TomType type2 = null;  TomName name2 = null;  TomTerm v = null; if(tom_is_fun_sym_Variable(tom_match6_1)) {  Option tom_match6_1_1 = null;  TomName tom_match6_1_2 = null;  TomType tom_match6_1_3 = null; tom_match6_1_1 = ( Option) tom_get_slot_Variable_option(tom_match6_1); tom_match6_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match6_1); tom_match6_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match6_1); v = ( TomTerm) tom_match6_1; option2 = ( Option) tom_match6_1_1; name2 = ( TomName) tom_match6_1_2; type2 = ( TomType) tom_match6_1_3;
 
                if(cCode || jCode) {
                  out.write(deep,getTLType(type2) + " ");
                  generate(out,deep,v);
                } else if(eCode) {
                  generate(out,deep,v);
                  out.write(deep,": " + getTLType(type2));
                }
                break matchBlock;
               }}matchlab_match6_pattern2: {
 
                System.out.println("MakeFunction: strange term: " + localVar);
                System.exit(1);
              } }
 
          }
          varList = varList.getTail();
          if(!varList.isEmpty()) {
            if(cCode || jCode) {
              out.write(deep,", ");
            } else if(eCode) {
              out.write(deep,"; ");
            }
          }
        }
        if(cCode || jCode) {
          out.writeln(deep,") {");
        } else if(eCode) {
          out.writeln(deep,"): " + glType + " is");
          out.writeln(deep,"local ");
        }
          //out.writeln(deep,"return null;");
        return;
       } } }}matchlab_match1_pattern19: { if(tom_is_fun_sym_MakeFunctionEnd(tom_match1_1)) {

 
        if(cCode || jCode) {
          out.writeln(deep,"}");
        } else if(eCode) {
          out.writeln(deep,"end;");
        }
        return;
       }}matchlab_match1_pattern20: { if(tom_is_fun_sym_EndLocalVariable(tom_match1_1)) {

  out.writeln(deep,"do"); return;  }}matchlab_match1_pattern21: {  TargetLanguage t = null; if(tom_is_fun_sym_TargetLanguageToTomTerm(tom_match1_1)) {  TargetLanguage tom_match1_1_1 = null; tom_match1_1_1 = ( TargetLanguage) tom_get_slot_TargetLanguageToTomTerm_tl(tom_match1_1); t = ( TargetLanguage) tom_match1_1_1;

 
        generateTargetLanguage(out,deep,t);
        return;
       }}matchlab_match1_pattern22: {  Declaration t = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match1_1)) {  Declaration tom_match1_1_1 = null; tom_match1_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match1_1); t = ( Declaration) tom_match1_1_1;

 
        generateDeclaration(out,deep,t);
        return;
       }}matchlab_match1_pattern23: {  Expression t = null; if(tom_is_fun_sym_ExpressionToTomTerm(tom_match1_1)) {  Expression tom_match1_1_1 = null; tom_match1_1_1 = ( Expression) tom_get_slot_ExpressionToTomTerm_astExpression(tom_match1_1); t = ( Expression) tom_match1_1_1;

 
        generateExpression(out,deep,t);
        return;
       }}matchlab_match1_pattern24: {  Instruction t = null; if(tom_is_fun_sym_InstructionToTomTerm(tom_match1_1)) {  Instruction tom_match1_1_1 = null; tom_match1_1_1 = ( Instruction) tom_get_slot_InstructionToTomTerm_astInstruction(tom_match1_1); t = ( Instruction) tom_match1_1_1;

 
        generateInstruction(out,deep,t);
        return;
       }}matchlab_match1_pattern25: {  TomTerm t = null; t = ( TomTerm) tom_match1_1;

 
        System.out.println("Cannot generate code for: " + t);
        System.exit(1);
      } }
 
  }

  public void generateExpression(jtom.tools.OutputCode out, int deep, Expression subject)
    throws IOException {
    if(subject==null) { return; }
    
     {  Expression tom_match7_1 = null; tom_match7_1 = ( Expression) subject;matchlab_match7_pattern1: {  Expression exp = null; if(tom_is_fun_sym_Not(tom_match7_1)) {  Expression tom_match7_1_1 = null; tom_match7_1_1 = ( Expression) tom_get_slot_Not_arg(tom_match7_1); exp = ( Expression) tom_match7_1_1;
 
        if(cCode || jCode) {
          out.write("!(");
          generateExpression(out,deep,exp);
          out.write(")");
        } else if(eCode) {
          out.write("not ");
          generateExpression(out,deep,exp);
        }
        return;
       }}matchlab_match7_pattern2: {  Expression exp1 = null;  Expression exp2 = null; if(tom_is_fun_sym_And(tom_match7_1)) {  Expression tom_match7_1_1 = null;  Expression tom_match7_1_2 = null; tom_match7_1_1 = ( Expression) tom_get_slot_And_arg1(tom_match7_1); tom_match7_1_2 = ( Expression) tom_get_slot_And_arg2(tom_match7_1); exp1 = ( Expression) tom_match7_1_1; exp2 = ( Expression) tom_match7_1_2;

 
        generateExpression(out,deep,exp1);
        out.write(" && ");
        generateExpression(out,deep,exp2);
        return;
       }}matchlab_match7_pattern3: { if(tom_is_fun_sym_TrueTL(tom_match7_1)) {

 
        if(cCode) {
          out.write(" 1 ");
        } else if(jCode) {
          out.write(" true ");
        } else if(eCode) {
          out.write(" true ");
        }
        return;
       }}matchlab_match7_pattern4: { if(tom_is_fun_sym_FalseTL(tom_match7_1)) {

 
        if(cCode) {
          out.write(" 0 ");
        } else if(jCode) {
          out.write(" false ");
        } else if(eCode) {
          out.write(" false ");
        }
        return;
       }}matchlab_match7_pattern5: {  TomType type1 = null;  TomTerm var = null; if(tom_is_fun_sym_IsEmptyList(tom_match7_1)) {  TomTerm tom_match7_1_1 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_IsEmptyList_kid1(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3;

 
        out.write("tom_is_empty_" + getTomType(type1) + "(");
        generate(out,deep,var);
        out.write(")");
        return;
       } }}matchlab_match7_pattern6: {  TomTerm varIndex = null;  TomTerm varArray = null;  TomType type1 = null; if(tom_is_fun_sym_IsEmptyArray(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_IsEmptyArray_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_IsEmptyArray_kid2(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); varArray = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3; if(tom_is_fun_sym_Variable(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomType tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_2); varIndex = ( TomTerm) tom_match7_1_2;

 
        generate(out,deep,varIndex);
        out.write(" >= ");
        out.write("tom_get_size_" + getTomType(type1) + "(");
        generate(out,deep,varArray);
        out.write(")");
        return;
       } } }}matchlab_match7_pattern7: {  TomList l = null;  Option option = null;  TomTerm var = null;  String tomName = null;  TomType type1 = null; if(tom_is_fun_sym_EqualFunctionSymbol(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_EqualFunctionSymbol_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_EqualFunctionSymbol_kid2(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3; if(tom_is_fun_sym_Appl(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomList tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Appl_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Appl_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomList) tom_get_slot_Appl_args(tom_match7_1_2); option = ( Option) tom_match7_1_2_1; if(tom_is_fun_sym_Name(tom_match7_1_2_2)) {  String tom_match7_1_2_2_1 = null; tom_match7_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match7_1_2_2); tomName = ( String) tom_match7_1_2_2_1; l = ( TomList) tom_match7_1_2_3;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        TomName termNameAST = tomSymbol.getAstName();
        OptionList termOptionList = tomSymbol.getOption().getOptionList();
        
        Declaration isFsymDecl = getIsFsymDecl(termOptionList);
        if(isFsymDecl != null) {
          generateExpression(out,deep,tom_make_IsFsym(termNameAST,var) );
        } else {
          String s = (String)getFunSymMap.get(type1);
          if(s == null) {
            s = "tom_cmp_fun_sym_" + getTomType(type1) + "(tom_get_fun_sym_" + getTomType(type1) + "(";
            getFunSymMap.put(type1,s);
          }
          out.write(s);
          generate(out,deep,var);
          out.write(") , " + getSymbolCode(tomSymbol) + ")");
        }
        return;
       } } } }}matchlab_match7_pattern8: {  TomTerm var2 = null;  TomType type1 = null;  TomTerm var1 = null; if(tom_is_fun_sym_EqualFunctionSymbol(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_EqualFunctionSymbol_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_EqualFunctionSymbol_kid2(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var1 = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3; var2 = ( TomTerm) tom_match7_1_2;

 
          //System.out.println("EqualFunctionSymbol(...," + var2 + ")");
        out.write("tom_cmp_fun_sym_" + getTomType(type1) + "(");
        out.write("tom_get_fun_sym_" + getTomType(type1) + "(");
        generate(out,deep,var1);
        out.write(") , " + var2 + ")");
        return;
       } }}matchlab_match7_pattern9: {  TomTerm var2 = null;  TomType type1 = null;  TomTerm var1 = null; if(tom_is_fun_sym_EqualTerm(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_EqualTerm_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_EqualTerm_kid2(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var1 = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3; var2 = ( TomTerm) tom_match7_1_2;

 
        out.write("tom_terms_equal_" + getTomType(type1) + "(");
        generate(out,deep,var1);
        out.write(", ");
        generate(out,deep,var2);
        out.write(")");
        return;
       } }}matchlab_match7_pattern10: {  TomTerm var1 = null;  TomType type1 = null;  TomTerm var2 = null; if(tom_is_fun_sym_EqualTerm(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_EqualTerm_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_EqualTerm_kid2(tom_match7_1); if(tom_is_fun_sym_VariableStar(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match7_1_1); var1 = ( TomTerm) tom_match7_1_1; type1 = ( TomType) tom_match7_1_1_3; var2 = ( TomTerm) tom_match7_1_2;

 
        out.write("tom_terms_equal_" + getTomType(type1) + "(");
        generate(out,deep,var1);
        out.write(", ");
        generate(out,deep,var2);
        out.write(")");
        return;
       } }}matchlab_match7_pattern11: {  Option option1 = null;  TomNumberList l1 = null;  String opname = null;  TomTerm var = null;  TomType type1 = null; if(tom_is_fun_sym_IsFsym(tom_match7_1)) {  TomName tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomName) tom_get_slot_IsFsym_astName(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_IsFsym_variable(tom_match7_1); if(tom_is_fun_sym_Name(tom_match7_1_1)) {  String tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( String) tom_get_slot_Name_string(tom_match7_1_1); opname = ( String) tom_match7_1_1_1; if(tom_is_fun_sym_Variable(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomType tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_2); var = ( TomTerm) tom_match7_1_2; option1 = ( Option) tom_match7_1_2_1; if(tom_is_fun_sym_PositionName(tom_match7_1_2_2)) {  TomNumberList tom_match7_1_2_2_1 = null; tom_match7_1_2_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_2_2); l1 = ( TomNumberList) tom_match7_1_2_2_1; type1 = ( TomType) tom_match7_1_2_3;

 
        String s = (String)isFsymMap.get(opname);
        if(s == null) {
          s = "tom_is_fun_sym_" + opname + "(";
          isFsymMap.put(opname,s);
        } 
        out.write(s);
        generate(out,deep,var);
        out.write(")");
        return;
       } } } }}matchlab_match7_pattern12: {  TomTerm var = null;  TomNumberList l1 = null;  Option option1 = null;  int number;  TomType type1 = null; if(tom_is_fun_sym_GetSubterm(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomNumber tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_GetSubterm_variable(tom_match7_1); tom_match7_1_2 = ( TomNumber) tom_get_slot_GetSubterm_number(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; option1 = ( Option) tom_match7_1_1_1; if(tom_is_fun_sym_PositionName(tom_match7_1_1_2)) {  TomNumberList tom_match7_1_1_2_1 = null; tom_match7_1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_1_2); l1 = ( TomNumberList) tom_match7_1_1_2_1; type1 = ( TomType) tom_match7_1_1_3; if(tom_is_fun_sym_Number(tom_match7_1_2)) {  int tom_match7_1_2_1; tom_match7_1_2_1 = ( int) tom_get_slot_Number_integer(tom_match7_1_2); number = ( int) tom_match7_1_2_1;

 
        String s = (String)getSubtermMap.get(type1);
        if(s == null) {
          s = "tom_get_subterm_" + getTomType(type1) + "(";
          getSubtermMap.put(type1,s);
        } 
        out.write(s);
        generate(out,deep,var);
        out.write(", " + number + ")");
        return;
       } } } }}matchlab_match7_pattern13: {  String slotName = null;  String opname = null;  TomTerm var = null; if(tom_is_fun_sym_GetSlot(tom_match7_1)) {  TomName tom_match7_1_1 = null;  String tom_match7_1_2 = null;  TomTerm tom_match7_1_3 = null; tom_match7_1_1 = ( TomName) tom_get_slot_GetSlot_astName(tom_match7_1); tom_match7_1_2 = ( String) tom_get_slot_GetSlot_slotNameString(tom_match7_1); tom_match7_1_3 = ( TomTerm) tom_get_slot_GetSlot_variable(tom_match7_1); if(tom_is_fun_sym_Name(tom_match7_1_1)) {  String tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( String) tom_get_slot_Name_string(tom_match7_1_1); opname = ( String) tom_match7_1_1_1; slotName = ( String) tom_match7_1_2; if(tom_is_fun_sym_Variable(tom_match7_1_3)) {  Option tom_match7_1_3_1 = null;  TomName tom_match7_1_3_2 = null;  TomType tom_match7_1_3_3 = null; tom_match7_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_3); tom_match7_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_3); tom_match7_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_3); var = ( TomTerm) tom_match7_1_3;

 
        out.write("tom_get_slot_" + opname + "_" + slotName + "(");
        generate(out,deep,var);
        out.write(")");
        return;
       } } }}matchlab_match7_pattern14: {  TomNumberList l1 = null;  TomType type1 = null;  TomTerm var = null;  Option option1 = null; if(tom_is_fun_sym_GetHead(tom_match7_1)) {  TomTerm tom_match7_1_1 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_GetHead_kid1(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; option1 = ( Option) tom_match7_1_1_1; if(tom_is_fun_sym_PositionName(tom_match7_1_1_2)) {  TomNumberList tom_match7_1_1_2_1 = null; tom_match7_1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_1_2); l1 = ( TomNumberList) tom_match7_1_1_2_1; type1 = ( TomType) tom_match7_1_1_3;

 
        out.write("tom_get_head_" + getTomType(type1) + "(");
        generate(out,deep,var);
        out.write(")");
        return;
       } } }}matchlab_match7_pattern15: {  TomTerm var = null;  TomNumberList l1 = null;  Option option1 = null;  TomType type1 = null; if(tom_is_fun_sym_GetTail(tom_match7_1)) {  TomTerm tom_match7_1_1 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_GetTail_kid1(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; option1 = ( Option) tom_match7_1_1_1; if(tom_is_fun_sym_PositionName(tom_match7_1_1_2)) {  TomNumberList tom_match7_1_1_2_1 = null; tom_match7_1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_1_2); l1 = ( TomNumberList) tom_match7_1_1_2_1; type1 = ( TomType) tom_match7_1_1_3;

 
        out.write("tom_get_tail_" + getTomType(type1) + "(");
        generate(out,deep,var); 
        out.write(")");
        return;
       } } }}matchlab_match7_pattern16: {  TomTerm var = null;  TomType type1 = null;  TomNumberList l1 = null;  Option option1 = null; if(tom_is_fun_sym_GetSize(tom_match7_1)) {  TomTerm tom_match7_1_1 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_GetSize_kid1(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); var = ( TomTerm) tom_match7_1_1; option1 = ( Option) tom_match7_1_1_1; if(tom_is_fun_sym_PositionName(tom_match7_1_1_2)) {  TomNumberList tom_match7_1_1_2_1 = null; tom_match7_1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_1_2); l1 = ( TomNumberList) tom_match7_1_1_2_1; type1 = ( TomType) tom_match7_1_1_3;

 
        out.write("tom_get_size_" + getTomType(type1) + "(");
        generate(out,deep,var);
        out.write(")");
        return;
       } } }}matchlab_match7_pattern17: {  Option option2 = null;  TomTerm varName = null;  TomType type1 = null;  TomTerm varIndex = null;  TomType type2 = null;  TomNumberList l2 = null;  TomNumberList l1 = null;  Option option1 = null; if(tom_is_fun_sym_GetElement(tom_match7_1)) {  TomTerm tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_GetElement_kid1(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_GetElement_kid2(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1)) {  Option tom_match7_1_1_1 = null;  TomName tom_match7_1_1_2 = null;  TomType tom_match7_1_1_3 = null; tom_match7_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_1); tom_match7_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_1); tom_match7_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_1); varName = ( TomTerm) tom_match7_1_1; option1 = ( Option) tom_match7_1_1_1; if(tom_is_fun_sym_PositionName(tom_match7_1_1_2)) {  TomNumberList tom_match7_1_1_2_1 = null; tom_match7_1_1_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_1_2); l1 = ( TomNumberList) tom_match7_1_1_2_1; type1 = ( TomType) tom_match7_1_1_3; if(tom_is_fun_sym_Variable(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomType tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_2); varIndex = ( TomTerm) tom_match7_1_2; option2 = ( Option) tom_match7_1_2_1; if(tom_is_fun_sym_PositionName(tom_match7_1_2_2)) {  TomNumberList tom_match7_1_2_2_1 = null; tom_match7_1_2_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_2_2); l2 = ( TomNumberList) tom_match7_1_2_2_1; type2 = ( TomType) tom_match7_1_2_3;


 
        out.write("tom_get_element_" + getTomType(type1) + "(");
        generate(out,deep,varName);
        out.write(",");
        generate(out,deep,varIndex);
        out.write(")");
        return;
       } } } } }}matchlab_match7_pattern18: {  Option option2 = null;  TomNumberList l2 = null;  TomTerm varBegin = null;  TomTerm varEnd = null;  Option option1 = null;  String name = null;  TomNumberList l1 = null;  TomType type1 = null;  TomType type2 = null; if(tom_is_fun_sym_GetSliceList(tom_match7_1)) {  TomName tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null;  TomTerm tom_match7_1_3 = null; tom_match7_1_1 = ( TomName) tom_get_slot_GetSliceList_astName(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_GetSliceList_variableBeginAST(tom_match7_1); tom_match7_1_3 = ( TomTerm) tom_get_slot_GetSliceList_variableEndAST(tom_match7_1); if(tom_is_fun_sym_Name(tom_match7_1_1)) {  String tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( String) tom_get_slot_Name_string(tom_match7_1_1); name = ( String) tom_match7_1_1_1; if(tom_is_fun_sym_Variable(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomType tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_2); varBegin = ( TomTerm) tom_match7_1_2; option1 = ( Option) tom_match7_1_2_1; if(tom_is_fun_sym_PositionName(tom_match7_1_2_2)) {  TomNumberList tom_match7_1_2_2_1 = null; tom_match7_1_2_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_2_2); l1 = ( TomNumberList) tom_match7_1_2_2_1; type1 = ( TomType) tom_match7_1_2_3; if(tom_is_fun_sym_Variable(tom_match7_1_3)) {  Option tom_match7_1_3_1 = null;  TomName tom_match7_1_3_2 = null;  TomType tom_match7_1_3_3 = null; tom_match7_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_3); tom_match7_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_3); tom_match7_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_3); varEnd = ( TomTerm) tom_match7_1_3; option2 = ( Option) tom_match7_1_3_1; if(tom_is_fun_sym_PositionName(tom_match7_1_3_2)) {  TomNumberList tom_match7_1_3_2_1 = null; tom_match7_1_3_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_3_2); l2 = ( TomNumberList) tom_match7_1_3_2_1; type2 = ( TomType) tom_match7_1_3_3;



 
        
        out.write("tom_get_slice_" + name + "(");
        generate(out,deep,varBegin);
        out.write(",");
        generate(out,deep,varEnd);
        out.write(")");
        return;
       } } } } } }}matchlab_match7_pattern19: {  String name = null;  TomTerm expEnd = null;  TomTerm varBegin = null;  TomType type1 = null;  Option option2 = null;  TomType type2 = null;  TomTerm varArray = null;  TomNumberList l1 = null;  Option option1 = null;  TomNumberList l2 = null; if(tom_is_fun_sym_GetSliceArray(tom_match7_1)) {  TomName tom_match7_1_1 = null;  TomTerm tom_match7_1_2 = null;  TomTerm tom_match7_1_3 = null;  TomTerm tom_match7_1_4 = null; tom_match7_1_1 = ( TomName) tom_get_slot_GetSliceArray_astName(tom_match7_1); tom_match7_1_2 = ( TomTerm) tom_get_slot_GetSliceArray_subjectListName(tom_match7_1); tom_match7_1_3 = ( TomTerm) tom_get_slot_GetSliceArray_variableBeginAST(tom_match7_1); tom_match7_1_4 = ( TomTerm) tom_get_slot_GetSliceArray_variableEndAST(tom_match7_1); if(tom_is_fun_sym_Name(tom_match7_1_1)) {  String tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( String) tom_get_slot_Name_string(tom_match7_1_1); name = ( String) tom_match7_1_1_1; if(tom_is_fun_sym_Variable(tom_match7_1_2)) {  Option tom_match7_1_2_1 = null;  TomName tom_match7_1_2_2 = null;  TomType tom_match7_1_2_3 = null; tom_match7_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_2); tom_match7_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_2); tom_match7_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_2); varArray = ( TomTerm) tom_match7_1_2; option1 = ( Option) tom_match7_1_2_1; if(tom_is_fun_sym_PositionName(tom_match7_1_2_2)) {  TomNumberList tom_match7_1_2_2_1 = null; tom_match7_1_2_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_2_2); l1 = ( TomNumberList) tom_match7_1_2_2_1; type1 = ( TomType) tom_match7_1_2_3; if(tom_is_fun_sym_Variable(tom_match7_1_3)) {  Option tom_match7_1_3_1 = null;  TomName tom_match7_1_3_2 = null;  TomType tom_match7_1_3_3 = null; tom_match7_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match7_1_3); tom_match7_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match7_1_3); tom_match7_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match7_1_3); varBegin = ( TomTerm) tom_match7_1_3; option2 = ( Option) tom_match7_1_3_1; if(tom_is_fun_sym_PositionName(tom_match7_1_3_2)) {  TomNumberList tom_match7_1_3_2_1 = null; tom_match7_1_3_2_1 = ( TomNumberList) tom_get_slot_PositionName_numberList(tom_match7_1_3_2); l2 = ( TomNumberList) tom_match7_1_3_2_1; type2 = ( TomType) tom_match7_1_3_3; expEnd = ( TomTerm) tom_match7_1_4;




 
        
        out.write("tom_get_slice_" + name + "(");
        generate(out,deep,varArray);
        out.write(",");
        generate(out,deep,varBegin);
        out.write(",");
        generate(out,deep,expEnd);
        out.write(")");
        return;
       } } } } } }}matchlab_match7_pattern20: {  TomTerm t = null; if(tom_is_fun_sym_TomTermToExpression(tom_match7_1)) {  TomTerm tom_match7_1_1 = null; tom_match7_1_1 = ( TomTerm) tom_get_slot_TomTermToExpression_astTerm(tom_match7_1); t = ( TomTerm) tom_match7_1_1;

 
        generate(out,deep,t);
        return;
       }}matchlab_match7_pattern21: {  Expression t = null; t = ( Expression) tom_match7_1;

 
        System.out.println("Cannot generate code for expression: " + t);
        System.exit(1);
      } }
 
  }

  public void generateInstruction(jtom.tools.OutputCode out, int deep, Instruction subject)
    throws IOException {
    if(subject==null) { return; }
    
     {  Instruction tom_match8_1 = null; tom_match8_1 = ( Instruction) subject;matchlab_match8_pattern1: {  TomName name1 = null;  TomTerm var = null;  TomType tlType = null;  String type = null;  OptionList list = null;  TomType tomType = null;  Expression exp = null; if(tom_is_fun_sym_Assign(tom_match8_1)) {  TomTerm tom_match8_1_1 = null;  Expression tom_match8_1_2 = null; tom_match8_1_1 = ( TomTerm) tom_get_slot_Assign_kid1(tom_match8_1); tom_match8_1_2 = ( Expression) tom_get_slot_Assign_source(tom_match8_1); if(tom_is_fun_sym_Variable(tom_match8_1_1)) {  Option tom_match8_1_1_1 = null;  TomName tom_match8_1_1_2 = null;  TomType tom_match8_1_1_3 = null; tom_match8_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match8_1_1); tom_match8_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match8_1_1); tom_match8_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match8_1_1); var = ( TomTerm) tom_match8_1_1; if(tom_is_fun_sym_Option(tom_match8_1_1_1)) {  OptionList tom_match8_1_1_1_1 = null; tom_match8_1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match8_1_1_1); list = ( OptionList) tom_match8_1_1_1_1; name1 = ( TomName) tom_match8_1_1_2; if(tom_is_fun_sym_Type(tom_match8_1_1_3)) {  TomType tom_match8_1_1_3_1 = null;  TomType tom_match8_1_1_3_2 = null; tom_match8_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match8_1_1_3); tom_match8_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match8_1_1_3); if(tom_is_fun_sym_TomType(tom_match8_1_1_3_1)) {  String tom_match8_1_1_3_1_1 = null; tom_match8_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match8_1_1_3_1); tomType = ( TomType) tom_match8_1_1_3_1; type = ( String) tom_match8_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match8_1_1_3_2)) {  TargetLanguage tom_match8_1_1_3_2_1 = null; tom_match8_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match8_1_1_3_2); tlType = ( TomType) tom_match8_1_1_3_2; exp = ( Expression) tom_match8_1_2;


 
        out.indent(deep);
        generate(out,deep,var);
        if(cCode || jCode) {
          out.write(" = (" + getTLCode(tlType) + ") ");
        } else if(eCode) {
          if(isBoolType(type) || isIntType(type) || isDoubleType(type)) {
            out.write(" := ");
          } else {
              //out.write(" ?= ");
            String assignSign = " := ";
             {  Expression tom_match9_1 = null; tom_match9_1 = ( Expression) exp;matchlab_match9_pattern1: { if(tom_is_fun_sym_GetSubterm(tom_match9_1)) {  TomTerm tom_match9_1_1 = null;  TomNumber tom_match9_1_2 = null; tom_match9_1_1 = ( TomTerm) tom_get_slot_GetSubterm_variable(tom_match9_1); tom_match9_1_2 = ( TomNumber) tom_get_slot_GetSubterm_number(tom_match9_1);
 
                assignSign = " ?= ";
               }} }
 
            out.write(assignSign);
          }
        }
        generateExpression(out,deep,exp);
        out.writeln(";");
        if(debugMode && !list.isEmpty()) {
          out.write("jtom.debug.TomDebugger.debugger.addSubstitution(\""+debugKey+"\",\"");
          generate(out,deep,var);
          out.write("\", ");
          generate(out,deep,var); // generateExpression(out,deep,exp);
          out.write(");\n");
        }
        return;
       } } } } } }}matchlab_match8_pattern2: {  Expression exp = null;  TomType tomType = null;  TomTerm var = null;  TomName name1 = null;  String type = null;  TomType tlType = null;  Option option1 = null; if(tom_is_fun_sym_AssignMatchSubject(tom_match8_1)) {  TomTerm tom_match8_1_1 = null;  Expression tom_match8_1_2 = null; tom_match8_1_1 = ( TomTerm) tom_get_slot_AssignMatchSubject_kid1(tom_match8_1); tom_match8_1_2 = ( Expression) tom_get_slot_AssignMatchSubject_source(tom_match8_1); if(tom_is_fun_sym_Variable(tom_match8_1_1)) {  Option tom_match8_1_1_1 = null;  TomName tom_match8_1_1_2 = null;  TomType tom_match8_1_1_3 = null; tom_match8_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match8_1_1); tom_match8_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match8_1_1); tom_match8_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match8_1_1); var = ( TomTerm) tom_match8_1_1; option1 = ( Option) tom_match8_1_1_1; name1 = ( TomName) tom_match8_1_1_2; if(tom_is_fun_sym_Type(tom_match8_1_1_3)) {  TomType tom_match8_1_1_3_1 = null;  TomType tom_match8_1_1_3_2 = null; tom_match8_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match8_1_1_3); tom_match8_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match8_1_1_3); if(tom_is_fun_sym_TomType(tom_match8_1_1_3_1)) {  String tom_match8_1_1_3_1_1 = null; tom_match8_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match8_1_1_3_1); tomType = ( TomType) tom_match8_1_1_3_1; type = ( String) tom_match8_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match8_1_1_3_2)) {  TargetLanguage tom_match8_1_1_3_2_1 = null; tom_match8_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match8_1_1_3_2); tlType = ( TomType) tom_match8_1_1_3_2; exp = ( Expression) tom_match8_1_2;


 
        out.indent(deep);
        generate(out,deep,var);
        if(cCode || jCode) {
          out.write(" = (" + getTLCode(tlType) + ") ");
        } else if(eCode) {
          if(isBoolType(type) || isIntType(type) || isDoubleType(type)) {
            out.write(" := ");
          } else {
              //out.write(" ?= ");
            String assignSign = " := ";
             {  Expression tom_match10_1 = null; tom_match10_1 = ( Expression) exp;matchlab_match10_pattern1: { if(tom_is_fun_sym_GetSubterm(tom_match10_1)) {  TomTerm tom_match10_1_1 = null;  TomNumber tom_match10_1_2 = null; tom_match10_1_1 = ( TomTerm) tom_get_slot_GetSubterm_variable(tom_match10_1); tom_match10_1_2 = ( TomNumber) tom_get_slot_GetSubterm_number(tom_match10_1);
 
                assignSign = " ?= ";
               }} }
 
            out.write(assignSign);
          }
        }
        generateExpression(out,deep,exp);
        out.writeln(";");
        if (debugMode) {
          out.write("jtom.debug.TomDebugger.debugger.specifySubject(\""+debugKey+"\",\"");
          generateExpression(out,deep,exp);
          out.write("\",");
          generateExpression(out,deep,exp);
          out.writeln(");");
        }
        return;
       } } } } }}matchlab_match8_pattern3: {  TomList instList = null;  String blockName = null; if(tom_is_fun_sym_NamedBlock(tom_match8_1)) {  String tom_match8_1_1 = null;  TomList tom_match8_1_2 = null; tom_match8_1_1 = ( String) tom_get_slot_NamedBlock_blockName(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_NamedBlock_instList(tom_match8_1); blockName = ( String) tom_match8_1_1; instList = ( TomList) tom_match8_1_2;

 
        if(cCode) {
          out.writeln("{");
          generateList(out,deep+1,instList);
          out.writeln("}" + blockName +  ":;");
        } else if(jCode) {
          out.writeln(blockName + ": {");
          generateList(out,deep+1,instList);
          out.writeln("}");
        } else if(eCode) {
          System.out.println("NamedBlock: Eiffel code not yet implemented");
            //System.exit(1);
        }  
        return;
       }}matchlab_match8_pattern4: {  Expression exp = null;  TomList succesList = null; if(tom_is_fun_sym_IfThenElse(tom_match8_1)) {  Expression tom_match8_1_1 = null;  TomList tom_match8_1_2 = null;  TomList tom_match8_1_3 = null; tom_match8_1_1 = ( Expression) tom_get_slot_IfThenElse_condition(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_IfThenElse_succesList(tom_match8_1); tom_match8_1_3 = ( TomList) tom_get_slot_IfThenElse_failureList(tom_match8_1); exp = ( Expression) tom_match8_1_1; succesList = ( TomList) tom_match8_1_2; if(tom_is_fun_sym_emptyTomList(tom_match8_1_3)) {


 
        if(cCode || jCode) {
          out.write(deep,"if("); generateExpression(out,deep,exp); out.writeln(") {");
          generateList(out,deep+1,succesList);
          out.writeln(deep,"}");
        } else if(eCode) {
          out.write(deep,"if "); generateExpression(out,deep,exp); out.writeln(" then ");
          generateList(out,deep+1,succesList);
          out.writeln(deep,"end;");
        }
        return;
       } }}matchlab_match8_pattern5: {  Expression exp = null;  TomList failureList = null;  TomList succesList = null; if(tom_is_fun_sym_IfThenElse(tom_match8_1)) {  Expression tom_match8_1_1 = null;  TomList tom_match8_1_2 = null;  TomList tom_match8_1_3 = null; tom_match8_1_1 = ( Expression) tom_get_slot_IfThenElse_condition(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_IfThenElse_succesList(tom_match8_1); tom_match8_1_3 = ( TomList) tom_get_slot_IfThenElse_failureList(tom_match8_1); exp = ( Expression) tom_match8_1_1; succesList = ( TomList) tom_match8_1_2; failureList = ( TomList) tom_match8_1_3;

 
        if(cCode || jCode) {
          out.write(deep,"if("); generateExpression(out,deep,exp); out.writeln(") {");
          generateList(out,deep+1,succesList);
          out.writeln(deep,"} else {");
          generateList(out,deep+1,failureList);
          out.writeln(deep,"}");
        } else if(eCode) {
          out.write(deep,"if "); generateExpression(out,deep,exp); out.writeln(" then ");
          generateList(out,deep+1,succesList);
          out.writeln(deep," else ");
          generateList(out,deep+1,failureList);
          out.writeln(deep,"end;");
        }
        return;
       }}matchlab_match8_pattern6: {  TomList succesList = null;  Expression exp = null; if(tom_is_fun_sym_DoWhile(tom_match8_1)) {  TomList tom_match8_1_1 = null;  Expression tom_match8_1_2 = null; tom_match8_1_1 = ( TomList) tom_get_slot_DoWhile_instList(tom_match8_1); tom_match8_1_2 = ( Expression) tom_get_slot_DoWhile_condition(tom_match8_1); succesList = ( TomList) tom_match8_1_1; exp = ( Expression) tom_match8_1_2;

 
        out.writeln(deep,"do {");
        generateList(out,deep+1,succesList);
        out.write(deep,"} while("); generateExpression(out,deep,exp); out.writeln(");");
        return;
       }}matchlab_match8_pattern7: {  TomName name1 = null;  Expression exp = null;  String type = null;  TomTerm var = null;  TomType tlType = null;  OptionList list = null; if(tom_is_fun_sym_Assign(tom_match8_1)) {  TomTerm tom_match8_1_1 = null;  Expression tom_match8_1_2 = null; tom_match8_1_1 = ( TomTerm) tom_get_slot_Assign_kid1(tom_match8_1); tom_match8_1_2 = ( Expression) tom_get_slot_Assign_source(tom_match8_1); if(tom_is_fun_sym_VariableStar(tom_match8_1_1)) {  Option tom_match8_1_1_1 = null;  TomName tom_match8_1_1_2 = null;  TomType tom_match8_1_1_3 = null; tom_match8_1_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match8_1_1); tom_match8_1_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match8_1_1); tom_match8_1_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match8_1_1); var = ( TomTerm) tom_match8_1_1; if(tom_is_fun_sym_Option(tom_match8_1_1_1)) {  OptionList tom_match8_1_1_1_1 = null; tom_match8_1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match8_1_1_1); list = ( OptionList) tom_match8_1_1_1_1; name1 = ( TomName) tom_match8_1_1_2; if(tom_is_fun_sym_Type(tom_match8_1_1_3)) {  TomType tom_match8_1_1_3_1 = null;  TomType tom_match8_1_1_3_2 = null; tom_match8_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match8_1_1_3); tom_match8_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match8_1_1_3); if(tom_is_fun_sym_TomType(tom_match8_1_1_3_1)) {  String tom_match8_1_1_3_1_1 = null; tom_match8_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match8_1_1_3_1); type = ( String) tom_match8_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match8_1_1_3_2)) {  TargetLanguage tom_match8_1_1_3_2_1 = null; tom_match8_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match8_1_1_3_2); tlType = ( TomType) tom_match8_1_1_3_2; exp = ( Expression) tom_match8_1_2;


 
        out.indent(deep);
        generate(out,deep,var);
        if(cCode || jCode) {
          out.write(" = (" + getTLCode(tlType) + ") ");
        } else if(eCode) {
          out.write(" := ");
        }
        generateExpression(out,deep,exp);
        out.writeln(";");
        if(debugMode && !list.isEmpty()) {
          out.write("jtom.debug.TomDebugger.debugger.addSubstitution(\""+debugKey+"\",\"");
          generate(out,deep,var);
          out.write("\", ");
          generate(out,deep,var); // generateExpression(out,deep,exp);
          out.write(");\n");
        }
        return;
       } } } } } }}matchlab_match8_pattern8: {  TomTerm var = null; if(tom_is_fun_sym_Increment(tom_match8_1)) {  TomTerm tom_match8_1_1 = null; tom_match8_1_1 = ( TomTerm) tom_get_slot_Increment_kid1(tom_match8_1); if(tom_is_fun_sym_Variable(tom_match8_1_1)) {  Option tom_match8_1_1_1 = null;  TomName tom_match8_1_1_2 = null;  TomType tom_match8_1_1_3 = null; tom_match8_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match8_1_1); tom_match8_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match8_1_1); tom_match8_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match8_1_1); var = ( TomTerm) tom_match8_1_1;

 
        generate(out,deep,var);
        out.write(" = ");
        generate(out,deep,var);
        out.writeln(" + 1;");
        return;
       } }}matchlab_match8_pattern9: {  TomList l = null; if(tom_is_fun_sym_Action(tom_match8_1)) {  TomList tom_match8_1_1 = null; tom_match8_1_1 = ( TomList) tom_get_slot_Action_instList(tom_match8_1); l = ( TomList) tom_match8_1_1;

 
        while(!l.isEmpty()) {
          generate(out,deep,l.getHead());
          l = l.getTail();
        }
          //out.writeln("// ACTION: " + l);
        return;
       }}matchlab_match8_pattern10: {  TomNumberList numberList = null; if(tom_is_fun_sym_ExitAction(tom_match8_1)) {  TomNumberList tom_match8_1_1 = null; tom_match8_1_1 = ( TomNumberList) tom_get_slot_ExitAction_numberList(tom_match8_1); numberList = ( TomNumberList) tom_match8_1_1;

 
        if(cCode) {
          out.writeln(deep,"goto matchlab" + numberListToIdentifier(numberList) + ";");
        } else if(jCode) {
          out.writeln(deep,"break matchlab" + numberListToIdentifier(numberList) + ";");
        } else if(eCode) {
          System.out.println("ExitAction: Eiffel code not yet implemented");
            //System.exit(1);
        }
        return;
       }}matchlab_match8_pattern11: {  TomTerm exp = null; if(tom_is_fun_sym_Return(tom_match8_1)) {  TomTerm tom_match8_1_1 = null; tom_match8_1_1 = ( TomTerm) tom_get_slot_Return_kid1(tom_match8_1); exp = ( TomTerm) tom_match8_1_1;

 
        if(cCode || jCode) {
          out.write(deep,"return ");
          generate(out,deep,exp);
          out.writeln(deep,";");
        } else if(eCode) {
          out.writeln(deep,"if Result = Void then");
          out.write(deep+1,"Result := ");
          generate(out,deep+1,exp);
          out.writeln(deep+1,";");
          out.writeln(deep,"end;");
        }
        return;
       }}matchlab_match8_pattern12: { if(tom_is_fun_sym_OpenBlock(tom_match8_1)) {

  out.writeln(deep,"{"); return;  }}matchlab_match8_pattern13: { if(tom_is_fun_sym_CloseBlock(tom_match8_1)) {
  out.writeln(deep,"}"); return;  }}matchlab_match8_pattern14: {  Instruction t = null; t = ( Instruction) tom_match8_1;


 
        System.out.println("Cannot generate code for instruction: " + t);
        System.exit(1);
      } }
 
  }

      
  
  public void generateTargetLanguage(jtom.tools.OutputCode out, int deep, TargetLanguage subject)
    throws IOException {
    if(subject==null) { return; }
     {  TargetLanguage tom_match11_1 = null; tom_match11_1 = ( TargetLanguage) subject;matchlab_match11_pattern1: {  int startLine;  int endLine;  String t = null; if(tom_is_fun_sym_TL(tom_match11_1)) {  String tom_match11_1_1 = null;  Position tom_match11_1_2 = null;  Position tom_match11_1_3 = null; tom_match11_1_1 = ( String) tom_get_slot_TL_code(tom_match11_1); tom_match11_1_2 = ( Position) tom_get_slot_TL_start(tom_match11_1); tom_match11_1_3 = ( Position) tom_get_slot_TL_end(tom_match11_1); t = ( String) tom_match11_1_1; if(tom_is_fun_sym_Position(tom_match11_1_2)) {  int tom_match11_1_2_1;  int tom_match11_1_2_2; tom_match11_1_2_1 = ( int) tom_get_slot_Position_line(tom_match11_1_2); tom_match11_1_2_2 = ( int) tom_get_slot_Position_column(tom_match11_1_2); startLine = ( int) tom_match11_1_2_1; if(tom_is_fun_sym_Position(tom_match11_1_3)) {  int tom_match11_1_3_1;  int tom_match11_1_3_2; tom_match11_1_3_1 = ( int) tom_get_slot_Position_line(tom_match11_1_3); tom_match11_1_3_2 = ( int) tom_get_slot_Position_column(tom_match11_1_3); endLine = ( int) tom_match11_1_3_1;
 
        out.write(deep,t, startLine, endLine-startLine);
        return;
       } } }}matchlab_match11_pattern2: {  String t = null; if(tom_is_fun_sym_ITL(tom_match11_1)) {  String tom_match11_1_1 = null; tom_match11_1_1 = ( String) tom_get_slot_ITL_code(tom_match11_1); t = ( String) tom_match11_1_1;

 
        out.write(deep,t);
        return;
       }}matchlab_match11_pattern3: {  TargetLanguage t = null; t = ( TargetLanguage) tom_match11_1;

 
        System.out.println("Cannot generate code for: " + t);
        System.exit(1);
      } }
 
  }

  public void generateOption(jtom.tools.OutputCode out, int deep, Option subject)
    throws IOException {
    if(subject==null) { return; }
    
     {  Option tom_match12_1 = null; tom_match12_1 = ( Option) subject;matchlab_match12_pattern1: {  Declaration decl = null; if(tom_is_fun_sym_DeclarationToOption(tom_match12_1)) {  Declaration tom_match12_1_1 = null; tom_match12_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match12_1); decl = ( Declaration) tom_match12_1_1;
 
        generateDeclaration(out,deep,decl);
        return;
       }}matchlab_match12_pattern2: { if(tom_is_fun_sym_OriginTracking(tom_match12_1)) {  TomName tom_match12_1_1 = null;  int tom_match12_1_2;  TomName tom_match12_1_3 = null; tom_match12_1_1 = ( TomName) tom_get_slot_OriginTracking_astName(tom_match12_1); tom_match12_1_2 = ( int) tom_get_slot_OriginTracking_line(tom_match12_1); tom_match12_1_3 = ( TomName) tom_get_slot_OriginTracking_fileName(tom_match12_1);
  return;  }}matchlab_match12_pattern3: { if(tom_is_fun_sym_DefinedSymbol(tom_match12_1)) {
  return;  }}matchlab_match12_pattern4: { if(tom_is_fun_sym_Constructor(tom_match12_1)) {  TomName tom_match12_1_1 = null; tom_match12_1_1 = ( TomName) tom_get_slot_Constructor_astName(tom_match12_1);
  return;  }}matchlab_match12_pattern5: {  Option t = null; t = ( Option) tom_match12_1;

 
        System.out.println("Cannot generate code for option: " + t);
        System.exit(1);
      } }
 
  }
  
  public void generateDeclaration(jtom.tools.OutputCode out, int deep, Declaration subject)
    throws IOException {
    if(subject==null) { return; }
    
     {  Declaration tom_match13_1 = null; tom_match13_1 = ( Declaration) subject;matchlab_match13_pattern1: { if(tom_is_fun_sym_EmptyDeclaration(tom_match13_1)) {
 
        return;
       }}matchlab_match13_pattern2: {  String tomName = null; if(tom_is_fun_sym_SymbolDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null; tom_match13_1_1 = ( TomName) tom_get_slot_SymbolDecl_astName(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); tomName = ( String) tom_match13_1_1_1;
 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        OptionList optionList = tomSymbol.getOption().getOptionList();
        SlotList slotList = tomSymbol.getSlotList();
        TomTypeList l = getSymbolDomain(tomSymbol);
        TomType type1 = getSymbolCodomain(tomSymbol);
        String name1 = tomSymbol.getAstName().getString();

        if(cCode && isDefinedSymbol(tomSymbol)) {
            // TODO: build an abstract declaration
          int argno=1;
            /*
              String s = "";
          if(!l.isEmpty()) {
            s = getTLType(type1) + " " + name1;
            
            if(!l.isEmpty()) {
              s += "(";
              while (!l.isEmpty()) {
                s += getTLType(l.getHead()) + " _" + argno;
                argno++;
                l = l.getTail() ;
                if(!l.isEmpty()) {
                  s += ",";
                }
              }
              s += ");";
            }
          }
            generate(out,deep,makeTL(s));
            */

          out.indent(deep);
          if(!l.isEmpty()) {
            out.write(getTLType(type1));
            out.writeSpace();
            out.write(name1);
            if(!l.isEmpty()) {
              out.writeOpenBrace();
              while (!l.isEmpty()) {
                out.write(getTLType(l.getHead()));
                  //out.writeUnderscore();
                  //out.write(argno);
                argno++;
                l = l.getTail() ;
                if(!l.isEmpty()) {
                  out.writeComa();
                }
              }
              out.writeCloseBrace();
              out.writeSemiColon();
            }
          }
          out.writeln();
        } else if(jCode) {
            // do nothing
        } else if(eCode) {
            // do nothing
        }

          // inspect the optionList
        generateOptionList(out, deep, optionList);
          // inspect the slotlist
        generateSlotList(out, deep, slotList);
        return ;
       } }}matchlab_match13_pattern3: {  String tomName = null; if(tom_is_fun_sym_ArraySymbolDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null; tom_match13_1_1 = ( TomName) tom_get_slot_ArraySymbolDecl_astName(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); tomName = ( String) tom_match13_1_1_1;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        OptionList optionList = tomSymbol.getOption().getOptionList();
        SlotList slotList = tomSymbol.getSlotList();        
        TomTypeList l = getSymbolDomain(tomSymbol);
        TomType type1 = getSymbolCodomain(tomSymbol);
        String name1 = tomSymbol.getAstName().getString();
        
        if(cCode) {
            // TODO: build an abstract declaration
          int argno=1;
          out.indent(deep);
          if(!l.isEmpty()) {
            out.write(getTLType(type1));
            out.writeSpace();
            out.write(name1);
            if(!l.isEmpty()) {
              out.writeOpenBrace();
              while (!l.isEmpty()) {
                out.write(getTLType(l.getHead()));
                out.writeUnderscore();
                out.write(argno);
                argno++;
                l = l.getTail() ;
                if(!l.isEmpty()) {
                  out.writeComa();
                }
              }
              out.writeCloseBrace();
              out.writeSemiColon();
            }
          }
          out.writeln();
        } else if(jCode) {
            // do nothing
        } else if(eCode) {
            // do nothing
        }

          // inspect the optionList
        generateOptionList(out, deep, optionList);
          // inspect the slotlist
        generateSlotList(out, deep, slotList);
        return ;
             } }}matchlab_match13_pattern4: {  String tomName = null; if(tom_is_fun_sym_ListSymbolDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null; tom_match13_1_1 = ( TomName) tom_get_slot_ListSymbolDecl_astName(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); tomName = ( String) tom_match13_1_1_1;

 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        OptionList optionList = tomSymbol.getOption().getOptionList();
        SlotList slotList = tomSymbol.getSlotList();
        TomTypeList l = getSymbolDomain(tomSymbol);
        TomType type1 = getSymbolCodomain(tomSymbol);
        String name1 = tomSymbol.getAstName().getString();
        
        
        if(cCode) {
            // TODO: build an abstract declaration
          int argno=1;
          out.indent(deep);
          if(!l.isEmpty()) {
            out.write(getTLType(type1));
            out.writeSpace();
            out.write(name1);
            if(!l.isEmpty()) {
              out.writeOpenBrace();
              while (!l.isEmpty()) {
                out.write(getTLType(l.getHead()));
                out.writeUnderscore();
                out.write(argno);
                argno++;
                l = l.getTail() ;
                if(!l.isEmpty()) {
                  out.writeComa();
                }
              }
              out.writeCloseBrace();
              out.writeSemiColon();
            }
          }
          out.writeln();
        } else if(jCode) {
            // do nothing
        } else if(eCode) {
            // do nothing
        }

          // inspect the optionList
        generateOptionList(out, deep, optionList);
          // inspect the slotlist
        generateSlotList(out, deep, slotList);
        return ;
       } }}matchlab_match13_pattern5: {  String name = null;  TargetLanguage tlCode = null;  TomType tlType = null;  Option option = null;  String type = null; if(tom_is_fun_sym_GetFunctionSymbolDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetFunctionSymbolDecl_termArg(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_GetFunctionSymbolDecl_tlCode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_GetFunctionSymbolDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType = ( TomType) tom_match13_1_1_3_2; tlCode = ( TargetLanguage) tom_match13_1_2;



 
        String args[];
        if(!strictType) {
          TomType argType = getUniversalType();
          if(isIntType(type)) {
            argType = getIntType();
          } else if(isDoubleType(type)) {
            argType = getDoubleType();
          }
          args = new String[] { getTLType(argType), name };
        } else {
          args = new String[] { getTLCode(tlType), name };
        }

        TomType returnType = getUniversalType();
          if(isIntType(type)) {
            returnType = getIntType();
          } else if(isDoubleType(type)) {
            returnType = getDoubleType();
          }
        generateTargetLanguage(out,deep,
                               genDecl(getTLType(returnType),
                                       "tom_get_fun_sym", type,args,tlCode));
        return;
       } } } } } }}matchlab_match13_pattern6: {  TomType tlType1 = null;  TargetLanguage tlCode = null;  String name2 = null;  TomType tlType2 = null;  Option option2 = null;  String name1 = null;  Option option1 = null;  String type2 = null;  String type1 = null; if(tom_is_fun_sym_GetSubtermDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetSubtermDecl_termArg(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_GetSubtermDecl_variable(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_GetSubtermDecl_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_GetSubtermDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type1 = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType1 = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option2 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name2 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type2 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_2_3_2)) {  TargetLanguage tom_match13_1_2_3_2_1 = null; tom_match13_1_2_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_2_3_2); tlType2 = ( TomType) tom_match13_1_2_3_2; tlCode = ( TargetLanguage) tom_match13_1_3;





 
        String args[];
        if(strictType || eCode) {
          args = new String[] { getTLCode(tlType1), name1,
                                getTLCode(tlType2), name2 };
        } else {
          args = new String[] { getTLType(getUniversalType()), name1,
                                getTLCode(tlType2), name2 };
        }
        generateTargetLanguage(out,deep, genDecl(getTLType(getUniversalType()), "tom_get_subterm", type1,
                                                 args, tlCode));
        return;
       } } } } } } } } } } }}matchlab_match13_pattern7: {  TargetLanguage tlCode = null;  String tomName = null;  String type1 = null;  Option option1 = null;  String name1 = null;  TomType tlType = null; if(tom_is_fun_sym_IsFsymDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomName) tom_get_slot_IsFsymDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_IsFsymDecl_variable(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_IsFsymDecl_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_IsFsymDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); tomName = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option1 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name1 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type1 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_2_3_2)) {  TargetLanguage tom_match13_1_2_3_2_1 = null; tom_match13_1_2_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_2_3_2); tlType = ( TomType) tom_match13_1_2_3_2; if(tom_is_fun_sym_TL(tom_match13_1_3)) {  String tom_match13_1_3_1 = null;  Position tom_match13_1_3_2 = null;  Position tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( String) tom_get_slot_TL_code(tom_match13_1_3); tom_match13_1_3_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_3); tom_match13_1_3_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_3); tlCode = ( TargetLanguage) tom_match13_1_3;



 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        String opname = tomSymbol.getAstName().getString();

	  TomType returnType = getBoolType();
	  String argType;
	  if(strictType) {
	      argType = getTLCode(tlType);
	  } else {
	      argType = getTLType(getUniversalType());
	  }

	  generateTargetLanguage(out,deep, genDecl(getTLType(returnType),
				     "tom_is_fun_sym", opname,
				     new String[] { argType, name1 },
				     tlCode));
	  return;
       } } } } } } } }}matchlab_match13_pattern8: {  TargetLanguage tlCode = null;  String name1 = null;  TomType tlType = null;  String type1 = null;  String tomName = null;  TomName slotName = null;  Option option1 = null; if(tom_is_fun_sym_GetSlotDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomName tom_match13_1_2 = null;  TomTerm tom_match13_1_3 = null;  TargetLanguage tom_match13_1_4 = null;  Option tom_match13_1_5 = null; tom_match13_1_1 = ( TomName) tom_get_slot_GetSlotDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomName) tom_get_slot_GetSlotDecl_slotName(tom_match13_1); tom_match13_1_3 = ( TomTerm) tom_get_slot_GetSlotDecl_variable(tom_match13_1); tom_match13_1_4 = ( TargetLanguage) tom_get_slot_GetSlotDecl_tlCode(tom_match13_1); tom_match13_1_5 = ( Option) tom_get_slot_GetSlotDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); tomName = ( String) tom_match13_1_1_1; slotName = ( TomName) tom_match13_1_2; if(tom_is_fun_sym_Variable(tom_match13_1_3)) {  Option tom_match13_1_3_1 = null;  TomName tom_match13_1_3_2 = null;  TomType tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_3); tom_match13_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_3); tom_match13_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_3); option1 = ( Option) tom_match13_1_3_1; if(tom_is_fun_sym_Name(tom_match13_1_3_2)) {  String tom_match13_1_3_2_1 = null; tom_match13_1_3_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_3_2); name1 = ( String) tom_match13_1_3_2_1; if(tom_is_fun_sym_Type(tom_match13_1_3_3)) {  TomType tom_match13_1_3_3_1 = null;  TomType tom_match13_1_3_3_2 = null; tom_match13_1_3_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_3_3); tom_match13_1_3_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_3_3); if(tom_is_fun_sym_TomType(tom_match13_1_3_3_1)) {  String tom_match13_1_3_3_1_1 = null; tom_match13_1_3_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_3_3_1); type1 = ( String) tom_match13_1_3_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_3_3_2)) {  TargetLanguage tom_match13_1_3_3_2_1 = null; tom_match13_1_3_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_3_3_2); tlType = ( TomType) tom_match13_1_3_3_2; if(tom_is_fun_sym_TL(tom_match13_1_4)) {  String tom_match13_1_4_1 = null;  Position tom_match13_1_4_2 = null;  Position tom_match13_1_4_3 = null; tom_match13_1_4_1 = ( String) tom_get_slot_TL_code(tom_match13_1_4); tom_match13_1_4_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_4); tom_match13_1_4_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_4); tlCode = ( TargetLanguage) tom_match13_1_4;




 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        String opname = tomSymbol.getAstName().getString();
        TomTypeList typesList = tomSymbol.getTypesToType().getDomain();
        
        int slotIndex = getSlotIndex(tomSymbol.getSlotList(),slotName);
        TomTypeList l = typesList;
        for(int index = 0; !l.isEmpty() && index<slotIndex ; index++) {
          l = l.getTail();
        }
        TomType returnType = l.getHead();
        
	String argType;
	  if(strictType) {
	      argType = getTLCode(tlType);
	  } else {
	      argType = getTLType(getUniversalType());
	  }
        generateTargetLanguage(out,deep, genDecl(getTLType(returnType),
                                   "tom_get_slot", opname  + "_" + slotName.getString(),
                                   new String[] { argType, name1 },
                                   tlCode));
        return;
       } } } } } } } }}matchlab_match13_pattern9: {  Option option2 = null;  String name1 = null;  String type2 = null;  String name2 = null;  TargetLanguage tlCode = null;  String type1 = null;  Option option1 = null; if(tom_is_fun_sym_CompareFunctionSymbolDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_CompareFunctionSymbolDecl_symbolArg1(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_CompareFunctionSymbolDecl_symbolArg2(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_CompareFunctionSymbolDecl_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_CompareFunctionSymbolDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type1 = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option2 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name2 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type2 = ( String) tom_match13_1_2_3_1_1; tlCode = ( TargetLanguage) tom_match13_1_3;



 
        TomType argType1 = getUniversalType();
        if(isIntType(type1)) {
          argType1 = getIntType();
        } else if(isDoubleType(type2)) {
          argType1 = getDoubleType();
        }
        TomType argType2 = getUniversalType();
        if(isIntType(type2)) {
          argType2 = getIntType();
        } else if(isDoubleType(type2)) {
          argType2 = getDoubleType();
        }
        
        generateTargetLanguage(out,deep, genDecl(getTLType(getBoolType()), "tom_cmp_fun_sym", type1,
                                                 new String[] {
                                                   getTLType(argType1), name1,
                                                   getTLType(argType2), name2
                                                 },
                                                 tlCode));
        return;
       } } } } } } } } }}matchlab_match13_pattern10: {  TargetLanguage tlCode = null;  String type2 = null;  Option option1 = null;  Option option2 = null;  String type1 = null;  String name2 = null;  String name1 = null; if(tom_is_fun_sym_TermsEqualDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_TermsEqualDecl_termArg1(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_TermsEqualDecl_termArg2(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_TermsEqualDecl_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_TermsEqualDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type1 = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option2 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name2 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type2 = ( String) tom_match13_1_2_3_1_1; tlCode = ( TargetLanguage) tom_match13_1_3;



 
        TomType argType1 = getUniversalType();
        if(isIntType(type1)) {
          argType1 = getIntType();
        } else if(isDoubleType(type2)) {
          argType1 = getDoubleType();
        }
        TomType argType2 = getUniversalType();
        if(isIntType(type2)) {
          argType2 = getIntType();
        } else if(isDoubleType(type2)) {
          argType2 = getDoubleType();
        }

        generateTargetLanguage(out,deep, genDecl(getTLType(getBoolType()), "tom_terms_equal", type1,
                                                 new String[] {
                                                   getTLType(argType1), name1,
                                                   getTLType(argType2), name2
                                                 },
                                                 tlCode));
        return;
       } } } } } } } } }}matchlab_match13_pattern11: {  Option option1 = null;  String name1 = null;  TargetLanguage tlCode = null;  TomType tlType = null;  String type = null; if(tom_is_fun_sym_GetHeadDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetHeadDecl_var(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_GetHeadDecl_tlcode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_GetHeadDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_TL(tom_match13_1_2)) {  String tom_match13_1_2_1 = null;  Position tom_match13_1_2_2 = null;  Position tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( String) tom_get_slot_TL_code(tom_match13_1_2); tom_match13_1_2_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_2); tom_match13_1_2_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_2); tlCode = ( TargetLanguage) tom_match13_1_2;


 
        String argType;
        if(strictType) {
          argType = getTLCode(tlType);
        } else {
          argType = getTLType(getUniversalType());
        }

        generateTargetLanguage(out,deep,
                               genDecl(getTLType(getUniversalType()),
                                       "tom_get_head", type,
                                       new String[] { argType, name1 },
                                       tlCode));
        return;
       } } } } } } }}matchlab_match13_pattern12: {  Option option1 = null;  String name1 = null;  TomType tlType = null;  TargetLanguage tlCode = null;  String type = null; if(tom_is_fun_sym_GetTailDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetTailDecl_var(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_GetTailDecl_tlcode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_GetTailDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_TL(tom_match13_1_2)) {  String tom_match13_1_2_1 = null;  Position tom_match13_1_2_2 = null;  Position tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( String) tom_get_slot_TL_code(tom_match13_1_2); tom_match13_1_2_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_2); tom_match13_1_2_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_2); tlCode = ( TargetLanguage) tom_match13_1_2;


 
        String returnType, argType;
        if(strictType) {
          returnType = getTLCode(tlType);
          argType = getTLCode(tlType);
        } else {
          returnType = getTLType(getUniversalType());
          argType = getTLType(getUniversalType());
        }
        
        generateTargetLanguage(out,deep,
                               genDecl(returnType, "tom_get_tail", type,
                                       new String[] { argType, name1 },
                                       tlCode));
        return;
       } } } } } } }}matchlab_match13_pattern13: {  String name1 = null;  Option option1 = null;  TargetLanguage tlCode = null;  String type = null;  TomType tlType = null; if(tom_is_fun_sym_IsEmptyDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_IsEmptyDecl_var(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_IsEmptyDecl_tlcode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_IsEmptyDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_TL(tom_match13_1_2)) {  String tom_match13_1_2_1 = null;  Position tom_match13_1_2_2 = null;  Position tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( String) tom_get_slot_TL_code(tom_match13_1_2); tom_match13_1_2_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_2); tom_match13_1_2_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_2); tlCode = ( TargetLanguage) tom_match13_1_2;


 
        String argType;
        if(strictType) {
          argType = getTLCode(tlType);
        } else {
          argType = getTLType(getUniversalType());
        }

        generateTargetLanguage(out,deep,
                               genDecl(getTLType(getBoolType()),
                                       "tom_is_empty", type,
                                       new String[] { argType, name1 },
                                       tlCode));
        return;
       } } } } } } }}matchlab_match13_pattern14: {  String opname = null;  TargetLanguage tlCode = null; if(tom_is_fun_sym_MakeEmptyList(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomName) tom_get_slot_MakeEmptyList_astName(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_MakeEmptyList_tlCode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_MakeEmptyList_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); opname = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_TL(tom_match13_1_2)) {  String tom_match13_1_2_1 = null;  Position tom_match13_1_2_2 = null;  Position tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( String) tom_get_slot_TL_code(tom_match13_1_2); tom_match13_1_2_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_2); tom_match13_1_2_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_2); tlCode = ( TargetLanguage) tom_match13_1_2;

 
        generateTargetLanguage(out,deep,
                               genDecl(getTLType(getUniversalType()),
                                       "tom_make_empty", opname,
                                       new String[] { },
                                       tlCode));
        return;
       } } }}matchlab_match13_pattern15: {  String name1 = null;  String type2 = null;  String type1 = null;  Option option2 = null;  TargetLanguage tlCode = null;  TomType fullListType = null;  TomType tlType2 = null;  TomType tlType1 = null;  Option option1 = null;  TomType fullEltType = null;  String opname = null;  String name2 = null; if(tom_is_fun_sym_MakeAddList(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TomTerm tom_match13_1_3 = null;  TargetLanguage tom_match13_1_4 = null;  Option tom_match13_1_5 = null; tom_match13_1_1 = ( TomName) tom_get_slot_MakeAddList_astName(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_MakeAddList_varElt(tom_match13_1); tom_match13_1_3 = ( TomTerm) tom_get_slot_MakeAddList_varList(tom_match13_1); tom_match13_1_4 = ( TargetLanguage) tom_get_slot_MakeAddList_tlCode(tom_match13_1); tom_match13_1_5 = ( Option) tom_get_slot_MakeAddList_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); opname = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option1 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name1 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); fullEltType = ( TomType) tom_match13_1_2_3; if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type1 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_2_3_2)) {  TargetLanguage tom_match13_1_2_3_2_1 = null; tom_match13_1_2_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_2_3_2); tlType1 = ( TomType) tom_match13_1_2_3_2; if(tom_is_fun_sym_Variable(tom_match13_1_3)) {  Option tom_match13_1_3_1 = null;  TomName tom_match13_1_3_2 = null;  TomType tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_3); tom_match13_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_3); tom_match13_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_3); option2 = ( Option) tom_match13_1_3_1; if(tom_is_fun_sym_Name(tom_match13_1_3_2)) {  String tom_match13_1_3_2_1 = null; tom_match13_1_3_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_3_2); name2 = ( String) tom_match13_1_3_2_1; if(tom_is_fun_sym_Type(tom_match13_1_3_3)) {  TomType tom_match13_1_3_3_1 = null;  TomType tom_match13_1_3_3_2 = null; tom_match13_1_3_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_3_3); tom_match13_1_3_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_3_3); fullListType = ( TomType) tom_match13_1_3_3; if(tom_is_fun_sym_TomType(tom_match13_1_3_3_1)) {  String tom_match13_1_3_3_1_1 = null; tom_match13_1_3_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_3_3_1); type2 = ( String) tom_match13_1_3_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_3_3_2)) {  TargetLanguage tom_match13_1_3_3_2_1 = null; tom_match13_1_3_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_3_3_2); tlType2 = ( TomType) tom_match13_1_3_3_2; if(tom_is_fun_sym_TL(tom_match13_1_4)) {  String tom_match13_1_4_1 = null;  Position tom_match13_1_4_2 = null;  Position tom_match13_1_4_3 = null; tom_match13_1_4_1 = ( String) tom_get_slot_TL_code(tom_match13_1_4); tom_match13_1_4_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_4); tom_match13_1_4_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_4); tlCode = ( TargetLanguage) tom_match13_1_4;




 
        String returnType, argListType,argEltType;
        if(strictType) {
          argEltType = getTLCode(tlType1);
          argListType = getTLCode(tlType2);
          returnType = argListType;
        } else {
          argEltType  = getTLType(getUniversalType());
          argListType = getTLType(getUniversalType());
          returnType  = argListType;
        }
        
        generateTargetLanguage(out,deep, genDecl(returnType,
                                                 "tom_make_insert", opname,
                                                 new String[] {
                                                   argEltType, name1,
                                                   argListType, name2
                                                 },
                                                 tlCode));
        
        generateTargetLanguage(out,deep, genDeclList(opname, fullListType,fullEltType));
        return;
       } } } } } } } } } } } } }}matchlab_match13_pattern16: {  String name1 = null;  TomType tlType1 = null;  String name2 = null;  Option option2 = null;  String type2 = null;  String type1 = null;  Option option1 = null;  TomType tlType2 = null;  TargetLanguage tlCode = null; if(tom_is_fun_sym_GetElementDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetElementDecl_kid1(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_GetElementDecl_kid2(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_GetElementDecl_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_GetElementDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type1 = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType1 = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option2 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name2 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type2 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_2_3_2)) {  TargetLanguage tom_match13_1_2_3_2_1 = null; tom_match13_1_2_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_2_3_2); tlType2 = ( TomType) tom_match13_1_2_3_2; if(tom_is_fun_sym_TL(tom_match13_1_3)) {  String tom_match13_1_3_1 = null;  Position tom_match13_1_3_2 = null;  Position tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( String) tom_get_slot_TL_code(tom_match13_1_3); tom_match13_1_3_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_3); tom_match13_1_3_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_3); tlCode = ( TargetLanguage) tom_match13_1_3;



 
        String returnType, argType;
        if(strictType) {
          returnType = getTLType(getUniversalType());
          argType = getTLCode(tlType1);
        } else {
          returnType = getTLType(getUniversalType());
          argType = getTLType(getUniversalType());
        }
      
        generateTargetLanguage(out,deep, genDecl(returnType,
                               "tom_get_element", type1,
                               new String[] {
                                 argType, name1,
                                 getTLType(getIntType()), name2
                               },
                               tlCode));
        return;
       } } } } } } } } } } } }}matchlab_match13_pattern17: {  String name1 = null;  TargetLanguage tlCode = null;  TomType tlType = null;  String type = null;  Option option1 = null; if(tom_is_fun_sym_GetSizeDecl(tom_match13_1)) {  TomTerm tom_match13_1_1 = null;  TargetLanguage tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomTerm) tom_get_slot_GetSizeDecl_kid1(tom_match13_1); tom_match13_1_2 = ( TargetLanguage) tom_get_slot_GetSizeDecl_tlCode(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_GetSizeDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Variable(tom_match13_1_1)) {  Option tom_match13_1_1_1 = null;  TomName tom_match13_1_1_2 = null;  TomType tom_match13_1_1_3 = null; tom_match13_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_1); tom_match13_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_1); tom_match13_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_1); option1 = ( Option) tom_match13_1_1_1; if(tom_is_fun_sym_Name(tom_match13_1_1_2)) {  String tom_match13_1_1_2_1 = null; tom_match13_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1_2); name1 = ( String) tom_match13_1_1_2_1; if(tom_is_fun_sym_Type(tom_match13_1_1_3)) {  TomType tom_match13_1_1_3_1 = null;  TomType tom_match13_1_1_3_2 = null; tom_match13_1_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_1_3); tom_match13_1_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_1_3); if(tom_is_fun_sym_TomType(tom_match13_1_1_3_1)) {  String tom_match13_1_1_3_1_1 = null; tom_match13_1_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_1_3_1); type = ( String) tom_match13_1_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_1_3_2)) {  TargetLanguage tom_match13_1_1_3_2_1 = null; tom_match13_1_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_1_3_2); tlType = ( TomType) tom_match13_1_1_3_2; if(tom_is_fun_sym_TL(tom_match13_1_2)) {  String tom_match13_1_2_1 = null;  Position tom_match13_1_2_2 = null;  Position tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( String) tom_get_slot_TL_code(tom_match13_1_2); tom_match13_1_2_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_2); tom_match13_1_2_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_2); tlCode = ( TargetLanguage) tom_match13_1_2;


 
        String argType;
        if(strictType) {
          argType = getTLCode(tlType);
        } else {
          argType = getTLType(getUniversalType());
        }
        
        generateTargetLanguage(out,deep,
                               genDecl(getTLType(getIntType()),
                                       "tom_get_size", type,
                                       new String[] { argType, name1 },
                                       tlCode));
        return;
       } } } } } } }}matchlab_match13_pattern18: {  TargetLanguage tlCode = null;  String type1 = null;  String name1 = null;  Option option1 = null;  String opname = null; if(tom_is_fun_sym_MakeEmptyArray(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TargetLanguage tom_match13_1_3 = null;  Option tom_match13_1_4 = null; tom_match13_1_1 = ( TomName) tom_get_slot_MakeEmptyArray_astName(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_MakeEmptyArray_varSize(tom_match13_1); tom_match13_1_3 = ( TargetLanguage) tom_get_slot_MakeEmptyArray_tlCode(tom_match13_1); tom_match13_1_4 = ( Option) tom_get_slot_MakeEmptyArray_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); opname = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option1 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name1 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type1 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TL(tom_match13_1_3)) {  String tom_match13_1_3_1 = null;  Position tom_match13_1_3_2 = null;  Position tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( String) tom_get_slot_TL_code(tom_match13_1_3); tom_match13_1_3_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_3); tom_match13_1_3_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_3); tlCode = ( TargetLanguage) tom_match13_1_3;



 
        generateTargetLanguage(out,deep, genDecl(getTLType(getUniversalType()), "tom_make_empty", opname,
                                   new String[] {
                                     getTLType(getIntType()), name1,
                                   },
                                   tlCode));
        return;
       } } } } } } }}matchlab_match13_pattern19: {  TomType fullArrayType = null;  Option option2 = null;  String opname = null;  String type1 = null;  TargetLanguage tlCode = null;  String name1 = null;  TomType fullEltType = null;  TomType tlType2 = null;  TomType tlType1 = null;  String name2 = null;  Option option1 = null;  String type2 = null; if(tom_is_fun_sym_MakeAddArray(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null;  TomTerm tom_match13_1_3 = null;  TargetLanguage tom_match13_1_4 = null;  Option tom_match13_1_5 = null; tom_match13_1_1 = ( TomName) tom_get_slot_MakeAddArray_astName(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_MakeAddArray_varElt(tom_match13_1); tom_match13_1_3 = ( TomTerm) tom_get_slot_MakeAddArray_varList(tom_match13_1); tom_match13_1_4 = ( TargetLanguage) tom_get_slot_MakeAddArray_tlCode(tom_match13_1); tom_match13_1_5 = ( Option) tom_get_slot_MakeAddArray_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); opname = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_Variable(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomType tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_2); option1 = ( Option) tom_match13_1_2_1; if(tom_is_fun_sym_Name(tom_match13_1_2_2)) {  String tom_match13_1_2_2_1 = null; tom_match13_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_2_2); name1 = ( String) tom_match13_1_2_2_1; if(tom_is_fun_sym_Type(tom_match13_1_2_3)) {  TomType tom_match13_1_2_3_1 = null;  TomType tom_match13_1_2_3_2 = null; tom_match13_1_2_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_2_3); tom_match13_1_2_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_2_3); fullEltType = ( TomType) tom_match13_1_2_3; if(tom_is_fun_sym_TomType(tom_match13_1_2_3_1)) {  String tom_match13_1_2_3_1_1 = null; tom_match13_1_2_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_2_3_1); type1 = ( String) tom_match13_1_2_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_2_3_2)) {  TargetLanguage tom_match13_1_2_3_2_1 = null; tom_match13_1_2_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_2_3_2); tlType1 = ( TomType) tom_match13_1_2_3_2; if(tom_is_fun_sym_Variable(tom_match13_1_3)) {  Option tom_match13_1_3_1 = null;  TomName tom_match13_1_3_2 = null;  TomType tom_match13_1_3_3 = null; tom_match13_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match13_1_3); tom_match13_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match13_1_3); tom_match13_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match13_1_3); option2 = ( Option) tom_match13_1_3_1; if(tom_is_fun_sym_Name(tom_match13_1_3_2)) {  String tom_match13_1_3_2_1 = null; tom_match13_1_3_2_1 = ( String) tom_get_slot_Name_string(tom_match13_1_3_2); name2 = ( String) tom_match13_1_3_2_1; if(tom_is_fun_sym_Type(tom_match13_1_3_3)) {  TomType tom_match13_1_3_3_1 = null;  TomType tom_match13_1_3_3_2 = null; tom_match13_1_3_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match13_1_3_3); tom_match13_1_3_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match13_1_3_3); fullArrayType = ( TomType) tom_match13_1_3_3; if(tom_is_fun_sym_TomType(tom_match13_1_3_3_1)) {  String tom_match13_1_3_3_1_1 = null; tom_match13_1_3_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match13_1_3_3_1); type2 = ( String) tom_match13_1_3_3_1_1; if(tom_is_fun_sym_TLType(tom_match13_1_3_3_2)) {  TargetLanguage tom_match13_1_3_3_2_1 = null; tom_match13_1_3_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match13_1_3_3_2); tlType2 = ( TomType) tom_match13_1_3_3_2; if(tom_is_fun_sym_TL(tom_match13_1_4)) {  String tom_match13_1_4_1 = null;  Position tom_match13_1_4_2 = null;  Position tom_match13_1_4_3 = null; tom_match13_1_4_1 = ( String) tom_get_slot_TL_code(tom_match13_1_4); tom_match13_1_4_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_4); tom_match13_1_4_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_4); tlCode = ( TargetLanguage) tom_match13_1_4;




 

        String returnType, argListType,argEltType;
        if(strictType) {
          argEltType  = getTLCode(tlType1);
          argListType = getTLCode(tlType2);
          returnType  = argListType;

        } else {
          argEltType  = getTLType(getUniversalType());
          argListType = getTLType(getUniversalType());
          returnType  = argListType;
        }

        generateTargetLanguage(out,deep,
                               genDecl(argListType,
                                       "tom_make_append", opname,
                                       new String[] {
                                         argEltType, name1,
                                         argListType, name2
                                       },
                                       tlCode));
        generateTargetLanguage(out,deep, genDeclArray(opname, fullArrayType, fullEltType));
        return;
       } } } } } } } } } } } } }}matchlab_match13_pattern20: {  TargetLanguage tlCode = null;  String opname = null;  TomList argList = null;  TomType returnType = null; if(tom_is_fun_sym_MakeDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomType tom_match13_1_2 = null;  TomList tom_match13_1_3 = null;  TargetLanguage tom_match13_1_4 = null;  Option tom_match13_1_5 = null; tom_match13_1_1 = ( TomName) tom_get_slot_MakeDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomType) tom_get_slot_MakeDecl_astType(tom_match13_1); tom_match13_1_3 = ( TomList) tom_get_slot_MakeDecl_args(tom_match13_1); tom_match13_1_4 = ( TargetLanguage) tom_get_slot_MakeDecl_tlCode(tom_match13_1); tom_match13_1_5 = ( Option) tom_get_slot_MakeDecl_orgTrack(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); opname = ( String) tom_match13_1_1_1; returnType = ( TomType) tom_match13_1_2; argList = ( TomList) tom_match13_1_3; if(tom_is_fun_sym_TL(tom_match13_1_4)) {  String tom_match13_1_4_1 = null;  Position tom_match13_1_4_2 = null;  Position tom_match13_1_4_3 = null; tom_match13_1_4_1 = ( String) tom_get_slot_TL_code(tom_match13_1_4); tom_match13_1_4_2 = ( Position) tom_get_slot_TL_start(tom_match13_1_4); tom_match13_1_4_3 = ( Position) tom_get_slot_TL_end(tom_match13_1_4); tlCode = ( TargetLanguage) tom_match13_1_4;

 
        generateTargetLanguage(out,deep, genDeclMake(opname, returnType, argList, tlCode));
        return;
       } } }}matchlab_match13_pattern21: {  TomList declList = null; if(tom_is_fun_sym_TypeTermDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomList tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomName) tom_get_slot_TypeTermDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomList) tom_get_slot_TypeTermDecl_keywordList(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_TypeTermDecl_orgTrack(tom_match13_1); declList = ( TomList) tom_match13_1_2;

  
        TomTerm term;
        while(!declList.isEmpty()) {
          term = declList.getHead();
           {  TomTerm tom_match14_1 = null; tom_match14_1 = ( TomTerm) term;matchlab_match14_pattern1: {  Declaration declaration = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match14_1)) {  Declaration tom_match14_1_1 = null; tom_match14_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match14_1); declaration = ( Declaration) tom_match14_1_1;
 generateDeclaration(out, deep, declaration); }} }
 
          declList = declList.getTail();
        }
        return;
       }}matchlab_match13_pattern22: {  TomList declList = null; if(tom_is_fun_sym_TypeListDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomList tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomName) tom_get_slot_TypeListDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomList) tom_get_slot_TypeListDecl_keywordList(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_TypeListDecl_orgTrack(tom_match13_1); declList = ( TomList) tom_match13_1_2;

  
        TomTerm term;
        while(!declList.isEmpty()) {
          term = declList.getHead();
           {  TomTerm tom_match15_1 = null; tom_match15_1 = ( TomTerm) term;matchlab_match15_pattern1: {  Declaration declaration = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match15_1)) {  Declaration tom_match15_1_1 = null; tom_match15_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match15_1); declaration = ( Declaration) tom_match15_1_1;
 generateDeclaration(out, deep, declaration); }} }
 
          declList = declList.getTail();
        }
        return;
       }}matchlab_match13_pattern23: {  TomList declList = null; if(tom_is_fun_sym_TypeArrayDecl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomList tom_match13_1_2 = null;  Option tom_match13_1_3 = null; tom_match13_1_1 = ( TomName) tom_get_slot_TypeArrayDecl_astName(tom_match13_1); tom_match13_1_2 = ( TomList) tom_get_slot_TypeArrayDecl_keywordList(tom_match13_1); tom_match13_1_3 = ( Option) tom_get_slot_TypeArrayDecl_orgTrack(tom_match13_1); declList = ( TomList) tom_match13_1_2;

  
        TomTerm term;
        while(!declList.isEmpty()) {
          term = declList.getHead();
           {  TomTerm tom_match16_1 = null; tom_match16_1 = ( TomTerm) term;matchlab_match16_pattern1: {  Declaration declaration = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match16_1)) {  Declaration tom_match16_1_1 = null; tom_match16_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match16_1); declaration = ( Declaration) tom_match16_1_1;
 generateDeclaration(out, deep, declaration); }} }
 
          declList = declList.getTail();
        }
        return;
       }}matchlab_match13_pattern24: {  Declaration t = null; t = ( Declaration) tom_match13_1;

 
        System.out.println("Cannot generate code for declaration: " + t);
        System.exit(1);
      } }
 
  }

  public void generateList(OutputCode out, int deep, TomList subject)
    throws IOException {
      //%variable
    if(subject.isEmpty()) {
      return;
    }

    TomTerm t = subject.getHead();
    TomList l = subject.getTail(); 
    generate(out,deep,t);
    generateList(out,deep,l);
  }

  public void generateOptionList(OutputCode out, int deep, OptionList subject)
    throws IOException {
      //%variable
    if(subject.isEmpty()) {
      return;
    }

    Option t = subject.getHead();
    OptionList l = subject.getTail(); 
    generateOption(out,deep,t);
    generateOptionList(out,deep,l);
  }

  public void generateSlotList(OutputCode out, int deep, SlotList slotList)
    throws IOException {
    while ( !slotList.isEmpty() ) {
      generateDeclaration(out, deep, slotList.getHead().getSlotDecl());
      slotList = slotList.getTail();
    }
  }
  
  // ------------------------------------------------------------
  private TargetLanguage genDecl(String returnType,
                        String declName,
                        String suffix,
                        String args[],
                        TargetLanguage tlCode) {
    String s = "";
    if(!genDecl) { return null; }

    if(cCode) {
      s = "#define " + declName + "_" + suffix + "(";
      for(int i=0 ; i<args.length ; ) {
        s+= args[i+1];
        i+=2;
        if(i<args.length) {
          s+= ", ";
        }
      }
      s += ") (" + tlCode.getCode() +")";
    } else if(jCode) {
      String modifier ="public ";
      if(staticFunction) {
        modifier +="static ";
      }

      s = modifier + returnType + " " + declName + "_" + suffix + "(";
      for(int i=0 ; i<args.length ; ) {
        s+= args[i] + " " + args[i+1];
        i+=2;
        if(i<args.length) {
          s+= ", ";
        }
      } 
      s += ") { return " + tlCode.getCode() + "; }";
    } else if(eCode) {
      s = declName + "_" + suffix + "(";
      for(int i=0 ; i<args.length ; ) {
        s+= args[i+1] + ": " + args[i];
        i+=2;
        if(i<args.length) {
          s+= "; ";
        }
      } 
      s += "): " + returnType + " is do Result := " + tlCode.getCode() + "end;";
    }
    if(tlCode.isTL())
      return tom_make_TL(s,tlCode .getStart(),tlCode .getEnd()) ;
    else
      return tom_make_ITL(s) ;
  }

  
  private TargetLanguage genDeclMake(String opname, TomType returnType, TomList argList, TargetLanguage tlCode) {
    //%variable
    String s = "";
    if(!genDecl) { return null; }

    if(cCode) {
        //System.out.println("genDeclMake: not yet implemented for C");
        //System.exit(1);
      s = "#define tom_make_" + opname + "(";
      while(!argList.isEmpty()) {
        TomTerm arg = argList.getHead();
        matchBlock: {
           {  TomTerm tom_match17_1 = null; tom_match17_1 = ( TomTerm) arg;matchlab_match17_pattern1: {  String name = null; if(tom_is_fun_sym_Variable(tom_match17_1)) {  Option tom_match17_1_1 = null;  TomName tom_match17_1_2 = null;  TomType tom_match17_1_3 = null; tom_match17_1_1 = ( Option) tom_get_slot_Variable_option(tom_match17_1); tom_match17_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match17_1); tom_match17_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match17_1); if(tom_is_fun_sym_Name(tom_match17_1_2)) {  String tom_match17_1_2_1 = null; tom_match17_1_2_1 = ( String) tom_get_slot_Name_string(tom_match17_1_2); name = ( String) tom_match17_1_2_1;
 
              s += name;
              break matchBlock;
             } }}matchlab_match17_pattern2: {

 
              System.out.println("genDeclMake: strange term: " + arg);
              System.exit(1);
            } }
 
        }
        argList = argList.getTail();
        if(!argList.isEmpty()) {
          s += ", ";
        }
      }
      s += ") (" + tlCode.getCode() + ")";
    } else if(jCode) {
      String modifier ="public ";
      if(staticFunction) {
        modifier +="static ";
      }

      s = modifier + getTLType(returnType) + " tom_make_" + opname + "(";
      while(!argList.isEmpty()) {
        TomTerm arg = argList.getHead();
        matchBlock: {
           {  TomTerm tom_match18_1 = null; tom_match18_1 = ( TomTerm) arg;matchlab_match18_pattern1: {  TomType tlType = null;  String name = null;  Option option = null;  String type = null; if(tom_is_fun_sym_Variable(tom_match18_1)) {  Option tom_match18_1_1 = null;  TomName tom_match18_1_2 = null;  TomType tom_match18_1_3 = null; tom_match18_1_1 = ( Option) tom_get_slot_Variable_option(tom_match18_1); tom_match18_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match18_1); tom_match18_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match18_1); option = ( Option) tom_match18_1_1; if(tom_is_fun_sym_Name(tom_match18_1_2)) {  String tom_match18_1_2_1 = null; tom_match18_1_2_1 = ( String) tom_get_slot_Name_string(tom_match18_1_2); name = ( String) tom_match18_1_2_1; if(tom_is_fun_sym_Type(tom_match18_1_3)) {  TomType tom_match18_1_3_1 = null;  TomType tom_match18_1_3_2 = null; tom_match18_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match18_1_3); tom_match18_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match18_1_3); if(tom_is_fun_sym_TomType(tom_match18_1_3_1)) {  String tom_match18_1_3_1_1 = null; tom_match18_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match18_1_3_1); type = ( String) tom_match18_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match18_1_3_2)) {  TargetLanguage tom_match18_1_3_2_1 = null; tom_match18_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match18_1_3_2); tlType = ( TomType) tom_match18_1_3_2;
 
              s += getTLCode(tlType) + " " + name;
              break matchBlock;
             } } } } }}matchlab_match18_pattern2: {

 
              System.out.println("genDeclMake: strange term: " + arg);
              System.exit(1);
            } }
 
        }
        argList = argList.getTail();
        if(!argList.isEmpty()) {
          s += ", ";
        }
      }
      s += ") { ";
      if (debugMode) {
        s += "\n"+getTLType(returnType)+ " debugVar = " + tlCode.getCode() +";\n";
        s += "jtom.debug.TomDebugger.debugger.termCreation(debugVar);\n";
        s += "return  debugVar;\n}";
      } else {
        s += "return " + tlCode.getCode() + "; }";
      }
    } else if(eCode) {
      boolean braces = !argList.isEmpty();
      s = "tom_make_" + opname;
      if(braces) {
        s = s + "(";
      }
      while(!argList.isEmpty()) {
        TomTerm arg = argList.getHead();
        matchBlock: {
           {  TomTerm tom_match19_1 = null; tom_match19_1 = ( TomTerm) arg;matchlab_match19_pattern1: {  TomType tlType = null;  String name = null;  String type = null;  Option option = null; if(tom_is_fun_sym_Variable(tom_match19_1)) {  Option tom_match19_1_1 = null;  TomName tom_match19_1_2 = null;  TomType tom_match19_1_3 = null; tom_match19_1_1 = ( Option) tom_get_slot_Variable_option(tom_match19_1); tom_match19_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match19_1); tom_match19_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match19_1); option = ( Option) tom_match19_1_1; if(tom_is_fun_sym_Name(tom_match19_1_2)) {  String tom_match19_1_2_1 = null; tom_match19_1_2_1 = ( String) tom_get_slot_Name_string(tom_match19_1_2); name = ( String) tom_match19_1_2_1; if(tom_is_fun_sym_Type(tom_match19_1_3)) {  TomType tom_match19_1_3_1 = null;  TomType tom_match19_1_3_2 = null; tom_match19_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match19_1_3); tom_match19_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match19_1_3); if(tom_is_fun_sym_TomType(tom_match19_1_3_1)) {  String tom_match19_1_3_1_1 = null; tom_match19_1_3_1_1 = ( String) tom_get_slot_TomType_string(tom_match19_1_3_1); type = ( String) tom_match19_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match19_1_3_2)) {  TargetLanguage tom_match19_1_3_2_1 = null; tom_match19_1_3_2_1 = ( TargetLanguage) tom_get_slot_TLType_tl(tom_match19_1_3_2); tlType = ( TomType) tom_match19_1_3_2;
 
              s += name + ": " + getTLCode(tlType);
              break matchBlock;
             } } } } }}matchlab_match19_pattern2: {

 
              System.out.println("genDeclMake: strange term: " + arg);
              System.exit(1);
            } }
 
        }
        argList = argList.getTail();
        if(!argList.isEmpty()) {
          s += "; ";
        }
      }
      if(braces) {
        s = s + ")";
      }
      s += ": " + getTLType(returnType) + " is do Result := " + tlCode.getCode() + "end;";
    }
    return tom_make_TL(s,tlCode .getStart(),tlCode .getEnd()) ;
  }
  
  private TargetLanguage genDeclList(String name, TomType listType, TomType eltType) {
    //%variable
    String s = "";
    if(!genDecl) { return null; }

    String tomType = getTomType(listType);
    String glType = getTLType(listType);
    String tlEltType = getTLType(eltType);

    String utype = glType;
    String modifier = "";
    if(eCode) {
      System.out.println("genDeclList: Eiffel code not yet implemented");
    } else if(jCode) {
      modifier ="public ";
      if(staticFunction) {
        modifier +="static ";
      }
      if(!strictType) {
        utype =  getTLType(getUniversalType());
      }
    }

    String listCast = "(" + glType + ")";
    String eltCast = "(" + getTLType(eltType) + ")";
    String make_empty = listCast +  "tom_make_empty_" + name;
    String is_empty = "tom_is_empty_" + tomType;
    String make_insert = listCast + "tom_make_insert_" + name;
    String get_head = eltCast + "tom_get_head_" + tomType;
    String get_tail = listCast + "tom_get_tail_" + tomType;
    String reverse = listCast + "tom_reverse_" + name;

    s+= modifier + utype + " tom_reverse_" + name + "(" + utype + " l) {\n"; 
    s+= "   " + glType + " result = " + make_empty + "();\n"; 
    s+= "    while(!" + is_empty + "(l) ) {\n"; 
    s+= "      result = " + make_insert + "(" + get_head + "(l),result);\n";    
    s+= "      l = " + get_tail + "(l);\n";  
    s+= "    }\n";
    s+= "    return result;\n";
    s+= "  }\n";
    s+= "\n";

    s+= modifier + utype + " tom_insert_list_" + name +  "(" + utype + " l1, " + utype + " l2) {\n";
    s+= "   if(" + is_empty + "(l1)) {\n";
    s+= "    return l2;\n";  
    s+= "   } else if(" + is_empty + "(l2)) {\n";
    s+= "    return l1;\n";  
    s+= "   } else if(" + is_empty + "(" + get_tail + "(l1))) {\n";  
    s+= "    return " + make_insert + "(" + get_head + "(l1),l2);\n";
    s+= "   } else { \n";  
    s+= "    return " + make_insert + "(" + get_head + "(l1),tom_insert_list_" + name +  "(" + get_tail + "(l1),l2));\n";
    s+= "   }\n";
    s+= "  }\n";
    s+= "\n";


    
    s+= modifier + utype + " tom_get_slice_" + name + "(" + utype + " begin, " + utype + " end) {\n"; 
    s+= "   " + glType + " result = " + make_empty + "();\n"; 
    s+= "    while(!tom_terms_equal_" + tomType + "(begin,end)) {\n";
    s+= "      result = " + make_insert + "(" + get_head + "(begin),result);\n";
    s+= "      begin = " + get_tail + "(begin);\n";
    s+="     }\n";
    s+= "    result = " + reverse + "(result);\n";
    s+= "    return result;\n";
    s+= "  }\n";
    
    TargetLanguage resultTL = tom_make_ITL(s) ;
      //If necessary we remove \n code depending on --pretty option
    resultTL = ast().reworkTLCode(resultTL, pretty);
    return resultTL;
  }

  private TargetLanguage genDeclArray(String name, TomType listType, TomType eltType) {
    //%variable
    String s = "";
    if(!genDecl) { return null; }

    String tomType = getTomType(listType);
    String glType = getTLType(listType);
    String tlEltType = getTLType(eltType);
    String utype = glType;
    String modifier = "";
    if(eCode) {
      System.out.println("genDeclArray: Eiffel code not yet implemented");
    } else if(jCode) {
      modifier ="public ";
      if(staticFunction) {
        modifier +="static ";
      }
      if(!strictType) {
        utype =  getTLType(getUniversalType());
      }
    }

    String listCast = "(" + glType + ")";
    String eltCast = "(" + getTLType(eltType) + ")";
    String make_empty = listCast + "tom_make_empty_" + name;
    String make_append = listCast + "tom_make_append_" + name;
    String get_element = eltCast + "tom_get_element_" + tomType;

    
    s = modifier + utype + " tom_get_slice_" + name +  "(" + utype + " subject, int begin, int end) {\n";
    s+= "   " + glType + " result = " + make_empty + "(end - begin);\n";
    s+= "    while( begin != end ) {\n";
    s+= "      result = " + make_append + "(" + get_element + "(subject, begin),result);\n";
    s+= "      begin++;\n";
    s+="     }\n";
    s+= "    return result;\n";
    s+= "  }\n";
    s+= "\n";
    
    s+= modifier + utype + " tom_append_array_" + name +  "(" + utype + " l2, " + utype + " l1) {\n";
    s+= "    int size1 = tom_get_size_" + tomType + "(l1);\n";
    s+= "    int size2 = tom_get_size_" + tomType + "(l2);\n";
    s+= "    int index;\n";
    s+= "   " + glType + " result = " + make_empty + "(size1+size2);\n";

    s+= "    index=size1;\n";
    s+= "    while(index > 0) {\n";
    s+= "      result = " + make_append + "(" + get_element + "(l1,(size1-index)),result);\n";
    s+= "      index--;\n";
    s+= "    }\n";

    s+= "    index=size2;\n";
    s+= "    while(index > 0) {\n";
    s+= "      result = " + make_append + "(" + get_element + "(l2,(size2-index)),result);\n";
    s+= "      index--;\n";
    s+= "    }\n";
   
    s+= "    return result;\n";
    s+= "  }\n";

    TargetLanguage resultTL = tom_make_ITL(s) ;
      //If necessary we remove \n code depending on --pretty option
    resultTL = ast().reworkTLCode(resultTL, pretty);
    return resultTL;
  }
  
  private HashMap getSubtermMap = new HashMap();
  private HashMap getFunSymMap = new HashMap();
  private HashMap isFsymMap = new HashMap();
}
  
