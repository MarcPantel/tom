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

package jtom.compiler;
  
import java.util.HashSet;

import jtom.TomBase;
import jtom.adt.*;
import jtom.runtime.Replace1;
import aterm.ATerm;
import jtom.tools.TomTask;
import jtom.tools.TomTaskInput;
import jtom.tools.Tools;

public class TomExpander extends TomBase implements TomTask {
  private TomTask nextTask;
  TomKernelExpander tomKernelExpander;
  
  public TomExpander(jtom.TomEnvironment environment,
                     TomKernelExpander tomKernelExpander) {
    super(environment);
    this.tomKernelExpander = tomKernelExpander;
  }

// ------------------------------------------------------------
    
// ------------------------------------------------------------

  public void addTask(TomTask task) {
  	this.nextTask = task;
  }
  public void process(TomTaskInput input) {
  	try {
	  long startChrono = 0;
	  boolean verbose = input.isVerbose(), intermediate = input.isIntermediate(), debugMode = input.isDebugMode();
	  if(verbose) {
		startChrono = System.currentTimeMillis();
	  }
	  TomTerm syntaxExpandedTerm = expandTomSyntax(input.getTerm());
      tomKernelExpander.updateSymbolTable();
      TomTerm context = null;
      TomTerm expandedTerm  = expandVariable(context, syntaxExpandedTerm);
      if(debugMode) {
        tomKernelExpander.expandMatchPattern(expandedTerm);
        //generateOutput(input.inputFileName + input.debugTableSuffix, tomParser.getStructTable());
      }
	  if(verbose) {
		System.out.println("TOM expansion phase (" + (System.currentTimeMillis()-startChrono)+ " ms)");
	  }
      if(intermediate) {
          Tools.generateOutput(input.inputFileName + input.expandedSuffix, expandedTerm);
          Tools.generateOutput(input.inputFileName + input.expandedTableSuffix, symbolTable().toTerm());
	  }
      input.setTerm(expandedTerm);
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
     * The 'expandTomSyntax' phase replaces:
     * -each 'RecordAppl' by its expanded term form:
     *   (unused slots a replaced by placeholders)
     * - each BackQuoteTerm by its compiled form
     */
  
  public TomTerm expandTomSyntax(TomTerm subject) {
    Replace1 replace = new Replace1() { 
        public ATerm apply(ATerm subject) {
          if(subject instanceof TomTerm) {
             {  TomTerm tom_match1_1 = null; tom_match1_1 = ( TomTerm) subject;matchlab_match1_pattern1: {  TomTerm backQuoteTerm = null; if(tom_is_fun_sym_BackQuoteAppl(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomList tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_BackQuoteAppl_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_BackQuoteAppl_astName(tom_match1_1); tom_match1_1_3 = ( TomList) tom_get_slot_BackQuoteAppl_args(tom_match1_1); backQuoteTerm = ( TomTerm) tom_match1_1;
 
                return expandBackQuoteAppl(backQuoteTerm);
               }}matchlab_match1_pattern2: {  TomList args = null;  String tomName = null;  Option option = null; if(tom_is_fun_sym_RecordAppl(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomList tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match1_1); tom_match1_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match1_1); option = ( Option) tom_match1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); tomName = ( String) tom_match1_1_2_1; args = ( TomList) tom_match1_1_3;

 
                return expandRecordAppl(option,tomName,args);
               } }}matchlab_match1_pattern3: {

 
                return traversal().genericTraversal(subject,this);
              } }
  // end match
          } else {
            return traversal().genericTraversal(subject,this);
          }
        } // end apply
      }; // end new
    
    return (TomTerm) replace.apply(subject); 
  }

  protected TomTerm expandRecordAppl(Option option, String tomName, TomList args) {
    TomSymbol tomSymbol = getSymbol(tomName);
    SlotList slotList = tomSymbol.getSlotList();
    TomList subtermList = empty();
      // For each slotName (from tomSymbol)
    while(!slotList.isEmpty()) {
      TomName slotName = slotList.getHead().getSlotName();
        //debugPrintln("\tslotName  = " + slotName);
      TomList pairList = args;
      TomTerm newSubterm = null;
      if(!slotName.isEmptyName()) {
          // look for a same name (from record)
        whileBlock: {
          while(newSubterm==null && !pairList.isEmpty()) {
            TomTerm pairSlotName = pairList.getHead();
             {  TomName tom_match2_1 = null;  TomTerm tom_match2_2 = null; tom_match2_1 = ( TomName) slotName; tom_match2_2 = ( TomTerm) pairSlotName;matchlab_match2_pattern1: {  String tom_renamedvar_name_1 = null;  String name = null;  TomTerm slotSubterm = null; if(tom_is_fun_sym_Name(tom_match2_1)) {  String tom_match2_1_1 = null; tom_match2_1_1 = ( String) tom_get_slot_Name_string(tom_match2_1); tom_renamedvar_name_1 = ( String) tom_match2_1_1; if(tom_is_fun_sym_PairSlotAppl(tom_match2_2)) {  TomName tom_match2_2_1 = null;  TomTerm tom_match2_2_2 = null; tom_match2_2_1 = ( TomName) tom_get_slot_PairSlotAppl_slotName(tom_match2_2); tom_match2_2_2 = ( TomTerm) tom_get_slot_PairSlotAppl_appl(tom_match2_2); if(tom_is_fun_sym_Name(tom_match2_2_1)) {  String tom_match2_2_1_1 = null; tom_match2_2_1_1 = ( String) tom_get_slot_Name_string(tom_match2_2_1); name = ( String) tom_match2_2_1_1; slotSubterm = ( TomTerm) tom_match2_2_2; if(tom_terms_equal_String(name, tom_renamedvar_name_1) &&  true ) {
 
                  // bingo
                newSubterm = expandTomSyntax(slotSubterm);
                break whileBlock;
               } } } }}matchlab_match2_pattern2: {
 pairList = pairList.getTail();} }
 
          }
        } // end whileBlock
      }
      
      if(newSubterm == null) {
        newSubterm = tom_make_Placeholder(ast() .makeOption()) ;
      }
      subtermList = append(newSubterm,subtermList);
      slotList = slotList.getTail();
    }
    
    return tom_make_Appl(option,tom_make_Name(tomName),subtermList) ;
  }

  protected TomTerm expandBackQuoteAppl(TomTerm t) {
    Replace1 replaceSymbol = new Replace1() {
        public ATerm apply(ATerm t) {
          if(t instanceof TomTerm) {
             {  TomTerm tom_match3_1 = null; tom_match3_1 = ( TomTerm) t;matchlab_match3_pattern1: {  OptionList optionList = null;  TomName name = null;  TomList l = null;  String tomName = null; if(tom_is_fun_sym_BackQuoteAppl(tom_match3_1)) {  Option tom_match3_1_1 = null;  TomName tom_match3_1_2 = null;  TomList tom_match3_1_3 = null; tom_match3_1_1 = ( Option) tom_get_slot_BackQuoteAppl_option(tom_match3_1); tom_match3_1_2 = ( TomName) tom_get_slot_BackQuoteAppl_astName(tom_match3_1); tom_match3_1_3 = ( TomList) tom_get_slot_BackQuoteAppl_args(tom_match3_1); if(tom_is_fun_sym_Option(tom_match3_1_1)) {  OptionList tom_match3_1_1_1 = null; tom_match3_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match3_1_1); optionList = ( OptionList) tom_match3_1_1_1; if(tom_is_fun_sym_Name(tom_match3_1_2)) {  String tom_match3_1_2_1 = null; tom_match3_1_2_1 = ( String) tom_get_slot_Name_string(tom_match3_1_2); name = ( TomName) tom_match3_1_2; tomName = ( String) tom_match3_1_2_1; l = ( TomList) tom_match3_1_3;
 
                TomSymbol tomSymbol = getSymbol(tomName);
                TomList args  = (TomList) traversal().genericTraversal(l,this);
                
                if(tomSymbol != null) {
                  if(isListOperator(tomSymbol)) {
                    return tom_make_BuildList(name,args) ;
                  } else if(isArrayOperator(tomSymbol)) {
                    return tom_make_BuildArray(name,args) ;
                  } else {
                    return tom_make_BuildTerm(name,args) ;
                  }
                } else if(args.isEmpty() && !hasConstructor(optionList)) {
                  return tom_make_BuildVariable(name) ;
                } else {
                  return tom_make_FunctionCall(name,args) ;
                }
               } } }} }
  // end match 
          } else {
            return traversal().genericTraversal(t,this);
          }
          return traversal().genericTraversal(t,this);
        } // end apply
      }; // end replaceSymbol
    return (TomTerm) replaceSymbol.apply(t);
  }
  
    /*
     * At Tom expander level, we worry only about RewriteRule and
     *  their condlist
     * replace Name by Symbol
     * replace Name by Variable
     */
  public TomTerm expandVariable(TomTerm contextSubject, TomTerm subject) {
    if(!(subject instanceof TomTerm)) {
      return tomKernelExpander.expandVariable(contextSubject,subject);
    }

      //System.out.println("expandVariable is a tomTerm:\n\t" + subject );
     {  TomTerm tom_match4_1 = null;  TomTerm tom_match4_2 = null; tom_match4_1 = ( TomTerm) contextSubject; tom_match4_2 = ( TomTerm) subject;matchlab_match4_pattern1: {  String tomName = null;  TomList condList = null;  TomList l = null;  OptionList optionList = null;  TomTerm rhs = null;  Option option = null;  TomTerm context = null;  TomTerm lhs = null; context = ( TomTerm) tom_match4_1; if(tom_is_fun_sym_TomRuleToTomTerm(tom_match4_2)) {  TomRule tom_match4_2_1 = null; tom_match4_2_1 = ( TomRule) tom_get_slot_TomRuleToTomTerm_astTomRule(tom_match4_2); if(tom_is_fun_sym_RewriteRule(tom_match4_2_1)) {  TomTerm tom_match4_2_1_1 = null;  TomTerm tom_match4_2_1_2 = null;  TomList tom_match4_2_1_3 = null;  Option tom_match4_2_1_4 = null; tom_match4_2_1_1 = ( TomTerm) tom_get_slot_RewriteRule_lhs(tom_match4_2_1); tom_match4_2_1_2 = ( TomTerm) tom_get_slot_RewriteRule_rhs(tom_match4_2_1); tom_match4_2_1_3 = ( TomList) tom_get_slot_RewriteRule_condList(tom_match4_2_1); tom_match4_2_1_4 = ( Option) tom_get_slot_RewriteRule_option(tom_match4_2_1); if(tom_is_fun_sym_Term(tom_match4_2_1_1)) {  TomTerm tom_match4_2_1_1_1 = null; tom_match4_2_1_1_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match4_2_1_1); if(tom_is_fun_sym_Appl(tom_match4_2_1_1_1)) {  Option tom_match4_2_1_1_1_1 = null;  TomName tom_match4_2_1_1_1_2 = null;  TomList tom_match4_2_1_1_1_3 = null; tom_match4_2_1_1_1_1 = ( Option) tom_get_slot_Appl_option(tom_match4_2_1_1_1); tom_match4_2_1_1_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_2_1_1_1); tom_match4_2_1_1_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_2_1_1_1); lhs = ( TomTerm) tom_match4_2_1_1_1; if(tom_is_fun_sym_Option(tom_match4_2_1_1_1_1)) {  OptionList tom_match4_2_1_1_1_1_1 = null; tom_match4_2_1_1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match4_2_1_1_1_1); optionList = ( OptionList) tom_match4_2_1_1_1_1_1; if(tom_is_fun_sym_Name(tom_match4_2_1_1_1_2)) {  String tom_match4_2_1_1_1_2_1 = null; tom_match4_2_1_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_2_1_1_1_2); tomName = ( String) tom_match4_2_1_1_1_2_1; l = ( TomList) tom_match4_2_1_1_1_3; if(tom_is_fun_sym_Term(tom_match4_2_1_2)) {  TomTerm tom_match4_2_1_2_1 = null; tom_match4_2_1_2_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match4_2_1_2); rhs = ( TomTerm) tom_match4_2_1_2_1; condList = ( TomList) tom_match4_2_1_3; option = ( Option) tom_match4_2_1_4;




  
          //debugPrintln("expandVariable.13: Rule(" + lhs + "," + rhs + ")");
        TomSymbol tomSymbol = getSymbol(tomName);
        TomType symbolType = getSymbolCodomain(tomSymbol);
        TomTerm newLhs = tom_make_Term(expandVariable(context, lhs)) ;
        TomTerm newRhs = tom_make_Term(expandVariable(tom_make_TomTypeToTomTerm(symbolType), rhs)) ;
        
          // build the list of variables that occur in the lhs
        HashSet set = new HashSet();
        collectVariable(set,newLhs);
        TomList varList = ast().makeList(set);
        TomList newCondList = empty();
        while(!condList.isEmpty()) {
          TomTerm cond = condList.getHead();
          TomTerm newCond = expandVariable(tom_make_Tom(varList) ,cond);
          newCondList = append(newCond,newCondList);
          collectVariable(set,newCond);
          varList = ast().makeList(set);
          condList = condList.getTail();
        }
        
        return tom_make_TomRuleToTomTerm(tom_make_RewriteRule(newLhs,newRhs,newCondList,option)) ;
       } } } } } } }}matchlab_match4_pattern2: {  String rhsName = null;  TomTerm lhs = null;  TomList varList = null;  TomTerm rhs = null;  String lhsName = null; if(tom_is_fun_sym_Tom(tom_match4_1)) {  TomList tom_match4_1_1 = null; tom_match4_1_1 = ( TomList) tom_get_slot_Tom_tomList(tom_match4_1); varList = ( TomList) tom_match4_1_1; if(tom_is_fun_sym_MatchingCondition(tom_match4_2)) {  TomTerm tom_match4_2_1 = null;  TomTerm tom_match4_2_2 = null; tom_match4_2_1 = ( TomTerm) tom_get_slot_MatchingCondition_lhs(tom_match4_2); tom_match4_2_2 = ( TomTerm) tom_get_slot_MatchingCondition_rhs(tom_match4_2); if(tom_is_fun_sym_Appl(tom_match4_2_1)) {  Option tom_match4_2_1_1 = null;  TomName tom_match4_2_1_2 = null;  TomList tom_match4_2_1_3 = null; tom_match4_2_1_1 = ( Option) tom_get_slot_Appl_option(tom_match4_2_1); tom_match4_2_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_2_1); tom_match4_2_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_2_1); lhs = ( TomTerm) tom_match4_2_1; if(tom_is_fun_sym_Name(tom_match4_2_1_2)) {  String tom_match4_2_1_2_1 = null; tom_match4_2_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_2_1_2); lhsName = ( String) tom_match4_2_1_2_1; if(tom_is_fun_sym_Appl(tom_match4_2_2)) {  Option tom_match4_2_2_1 = null;  TomName tom_match4_2_2_2 = null;  TomList tom_match4_2_2_3 = null; tom_match4_2_2_1 = ( Option) tom_get_slot_Appl_option(tom_match4_2_2); tom_match4_2_2_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_2_2); tom_match4_2_2_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_2_2); rhs = ( TomTerm) tom_match4_2_2; if(tom_is_fun_sym_Name(tom_match4_2_2_2)) {  String tom_match4_2_2_2_1 = null; tom_match4_2_2_2_1 = ( String) tom_get_slot_Name_string(tom_match4_2_2_2); rhsName = ( String) tom_match4_2_2_2_1;


 
        TomSymbol lhsSymbol = getSymbol(lhsName);
        TomSymbol rhsSymbol = getSymbol(rhsName);
        TomType type;
        
        if(lhsSymbol != null) {
          type = getSymbolCodomain(lhsSymbol);
        } else if(rhsSymbol != null) {
          type = getSymbolCodomain(rhsSymbol);
        } else {
            // both lhs and rhs are variables
            // since lhs is a fresh variable, we look for rhs
          type = getTypeFromVariableList(tom_make_Name(rhsName) ,varList);
        }
        
        TomTerm newLhs = expandVariable(tom_make_TomTypeToTomTerm(type), lhs) ;
        TomTerm newRhs = expandVariable(tom_make_TomTypeToTomTerm(type), rhs) ;
        return tom_make_MatchingCondition(newLhs,newRhs) ;
       } } } } } }}matchlab_match4_pattern3: {  String rhsName = null;  TomTerm rhs = null;  TomTerm lhs = null;  TomList varList = null;  String lhsName = null; if(tom_is_fun_sym_Tom(tom_match4_1)) {  TomList tom_match4_1_1 = null; tom_match4_1_1 = ( TomList) tom_get_slot_Tom_tomList(tom_match4_1); varList = ( TomList) tom_match4_1_1; if(tom_is_fun_sym_EqualityCondition(tom_match4_2)) {  TomTerm tom_match4_2_1 = null;  TomTerm tom_match4_2_2 = null; tom_match4_2_1 = ( TomTerm) tom_get_slot_EqualityCondition_lhs(tom_match4_2); tom_match4_2_2 = ( TomTerm) tom_get_slot_EqualityCondition_rhs(tom_match4_2); if(tom_is_fun_sym_Appl(tom_match4_2_1)) {  Option tom_match4_2_1_1 = null;  TomName tom_match4_2_1_2 = null;  TomList tom_match4_2_1_3 = null; tom_match4_2_1_1 = ( Option) tom_get_slot_Appl_option(tom_match4_2_1); tom_match4_2_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_2_1); tom_match4_2_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_2_1); lhs = ( TomTerm) tom_match4_2_1; if(tom_is_fun_sym_Name(tom_match4_2_1_2)) {  String tom_match4_2_1_2_1 = null; tom_match4_2_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_2_1_2); lhsName = ( String) tom_match4_2_1_2_1; if(tom_is_fun_sym_Appl(tom_match4_2_2)) {  Option tom_match4_2_2_1 = null;  TomName tom_match4_2_2_2 = null;  TomList tom_match4_2_2_3 = null; tom_match4_2_2_1 = ( Option) tom_get_slot_Appl_option(tom_match4_2_2); tom_match4_2_2_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_2_2); tom_match4_2_2_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_2_2); rhs = ( TomTerm) tom_match4_2_2; if(tom_is_fun_sym_Name(tom_match4_2_2_2)) {  String tom_match4_2_2_2_1 = null; tom_match4_2_2_2_1 = ( String) tom_get_slot_Name_string(tom_match4_2_2_2); rhsName = ( String) tom_match4_2_2_2_1;


 
        TomSymbol lhsSymbol = getSymbol(lhsName);
        TomSymbol rhsSymbol = getSymbol(rhsName);
        TomType type;
        
        if(lhsSymbol != null) {
          type = getSymbolCodomain(lhsSymbol);
        } else if(rhsSymbol != null) {
          type = getSymbolCodomain(rhsSymbol);
        } else {
            // both lhs and rhs are variables
          type = getTypeFromVariableList(tom_make_Name(lhsName) ,varList);
        }
        
          //System.out.println("EqualityCondition type = " + type);
        
        TomTerm newLhs = expandVariable(tom_make_TomTypeToTomTerm(type), lhs) ;
        TomTerm newRhs = expandVariable(tom_make_TomTypeToTomTerm(type), rhs) ;
        
          //System.out.println("lhs    = " + lhs);
          //System.out.println("newLhs = " + newLhs);
        
        return tom_make_EqualityCondition(newLhs,newRhs) ;
       } } } } } }}matchlab_match4_pattern4: {  TomTerm context = null;  TomTerm t = null; context = ( TomTerm) tom_match4_1; t = ( TomTerm) tom_match4_2;


 
        return tomKernelExpander.expandVariable(context,t);
      } }
  // end match
  }

  private TomType getTypeFromVariableList(TomName name, TomList list) {

      //System.out.println("name = " + name);
      //System.out.println("list = " + list);
    
     {  TomName tom_match5_1 = null;  TomList tom_match5_2 = null; tom_match5_1 = ( TomName) name; tom_match5_2 = ( TomList) list;matchlab_match5_pattern1: { if(tom_is_fun_sym_emptyTomList(tom_match5_2)) {
 
        System.out.println("getTypeFromVariableList. Stange case '" + name + "' not found");
        System.exit(1);
       }}matchlab_match5_pattern2: {  TomList tail = null;  TomName tom_renamedvar_varName_1 = null;  TomName varName = null;  TomType type = null; tom_renamedvar_varName_1 = ( TomName) tom_match5_1; if(tom_is_fun_sym_manyTomList(tom_match5_2)) {  TomTerm tom_match5_2_1 = null;  TomList tom_match5_2_2 = null; tom_match5_2_1 = ( TomTerm) tom_get_slot_manyTomList_head(tom_match5_2); tom_match5_2_2 = ( TomList) tom_get_slot_manyTomList_tail(tom_match5_2); if(tom_is_fun_sym_Variable(tom_match5_2_1)) {  Option tom_match5_2_1_1 = null;  TomName tom_match5_2_1_2 = null;  TomType tom_match5_2_1_3 = null; tom_match5_2_1_1 = ( Option) tom_get_slot_Variable_option(tom_match5_2_1); tom_match5_2_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match5_2_1); tom_match5_2_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match5_2_1); varName = ( TomName) tom_match5_2_1_2; if(tom_is_fun_sym_Type(tom_match5_2_1_3)) {  TomType tom_match5_2_1_3_1 = null;  TomType tom_match5_2_1_3_2 = null; tom_match5_2_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match5_2_1_3); tom_match5_2_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match5_2_1_3); type = ( TomType) tom_match5_2_1_3; tail = ( TomList) tom_match5_2_2; if(tom_terms_equal_TomName(varName, tom_renamedvar_varName_1) &&  true ) {

  return type;  } } } }}matchlab_match5_pattern3: {  TomType type = null;  TomList tail = null;  TomName tom_renamedvar_varName_1 = null;  TomName varName = null; tom_renamedvar_varName_1 = ( TomName) tom_match5_1; if(tom_is_fun_sym_manyTomList(tom_match5_2)) {  TomTerm tom_match5_2_1 = null;  TomList tom_match5_2_2 = null; tom_match5_2_1 = ( TomTerm) tom_get_slot_manyTomList_head(tom_match5_2); tom_match5_2_2 = ( TomList) tom_get_slot_manyTomList_tail(tom_match5_2); if(tom_is_fun_sym_VariableStar(tom_match5_2_1)) {  Option tom_match5_2_1_1 = null;  TomName tom_match5_2_1_2 = null;  TomType tom_match5_2_1_3 = null; tom_match5_2_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match5_2_1); tom_match5_2_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match5_2_1); tom_match5_2_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match5_2_1); varName = ( TomName) tom_match5_2_1_2; if(tom_is_fun_sym_Type(tom_match5_2_1_3)) {  TomType tom_match5_2_1_3_1 = null;  TomType tom_match5_2_1_3_2 = null; tom_match5_2_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match5_2_1_3); tom_match5_2_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match5_2_1_3); type = ( TomType) tom_match5_2_1_3; tail = ( TomList) tom_match5_2_2; if(tom_terms_equal_TomName(varName, tom_renamedvar_varName_1) &&  true ) {
  return type;  } } } }}matchlab_match5_pattern4: {  TomTerm t = null;  TomList tail = null; if(tom_is_fun_sym_manyTomList(tom_match5_2)) {  TomTerm tom_match5_2_1 = null;  TomList tom_match5_2_2 = null; tom_match5_2_1 = ( TomTerm) tom_get_slot_manyTomList_head(tom_match5_2); tom_match5_2_2 = ( TomList) tom_get_slot_manyTomList_tail(tom_match5_2); t = ( TomTerm) tom_match5_2_1; tail = ( TomList) tom_match5_2_2;
  return getTypeFromVariableList(name,tail);  }} }

 
    return null;
  }
 
} // Class TomExpander
