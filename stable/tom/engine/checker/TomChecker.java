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
    Christophe Mayer            ESIAL Student
    Julien Guyon

*/

package jtom.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jtom.TomBase;
import jtom.adt.Declaration;
import jtom.adt.Option;
import jtom.adt.OptionList;
import jtom.adt.SlotList;
import jtom.adt.TargetLanguage;
import jtom.adt.TomList;
import jtom.adt.TomName;
import jtom.adt.TomRule;
import jtom.adt.TomRuleList;
import jtom.adt.TomSymbol;
import jtom.adt.TomTerm;
import jtom.adt.TomType;
import jtom.adt.TomTypeList;
import jtom.runtime.Collect1;
import jtom.tools.TomTask;
import aterm.ATerm;
import jtom.TomEnvironment;
import jtom.exception.CheckErrorException;

abstract class TomChecker extends TomBase implements TomTask {
  
  private ArrayList alreadyStudiedSymbol =  new ArrayList();
  private ArrayList alreadyStudiedType =  new ArrayList();  
  protected Option currentTomStructureOrgTrack;
  private int nullInteger = -1;
  private List errorMessage = new ArrayList();
  protected TomTask nextTask;
  protected boolean strictType = false, warningAll = false, noWarning = false;
  
  public TomChecker(TomEnvironment environment) { 
    super(environment);
  }
  
    // ------------------------------------------------------------
    
    // ------------------------------------------------------------
  
  public void addTask(TomTask task) {
  	this.nextTask = task;
  }
  
  public TomTask getTask() {
    return nextTask;
  }
  
  public int getNumberFoundError() {
    return errorMessage.size();
  }
  
  public String getMessage(int n) {
    return (String)errorMessage.get(n);
  }
  
     // Main syntax checking entry point: We check all interesting Tom Structure
  protected void syntaxCheck(TomTerm parsedTerm) {
    Collect1 collectAndVerify = new Collect1() {  
        public boolean apply(ATerm term) {
          if(term instanceof TomTerm) {
             {  TomTerm tom_match1_1 = null; tom_match1_1 = ( TomTerm) term;matchlab_match1_pattern1: {  TomList patternActionList = null;  OptionList list = null;  TomList matchArgsList = null; if(tom_is_fun_sym_Match(tom_match1_1)) {  TomTerm tom_match1_1_1 = null;  TomTerm tom_match1_1_2 = null;  Option tom_match1_1_3 = null; tom_match1_1_1 = ( TomTerm) tom_get_slot_Match_subjectList(tom_match1_1); tom_match1_1_2 = ( TomTerm) tom_get_slot_Match_patternList(tom_match1_1); tom_match1_1_3 = ( Option) tom_get_slot_Match_option(tom_match1_1); if(tom_is_fun_sym_SubjectList(tom_match1_1_1)) {  TomList tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( TomList) tom_get_slot_SubjectList_tomList(tom_match1_1_1); matchArgsList = ( TomList) tom_match1_1_1_1; if(tom_is_fun_sym_PatternList(tom_match1_1_2)) {  TomList tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( TomList) tom_get_slot_PatternList_tomList(tom_match1_1_2); patternActionList = ( TomList) tom_match1_1_2_1; if(tom_is_fun_sym_Option(tom_match1_1_3)) {  OptionList tom_match1_1_3_1 = null; tom_match1_1_3_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match1_1_3); list = ( OptionList) tom_match1_1_3_1;
   
                currentTomStructureOrgTrack = findOriginTracking(list);
                verifyMatch(matchArgsList, patternActionList);
                return true;
               } } } }}matchlab_match1_pattern2: {  Declaration declaration = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match1_1)) {  Declaration tom_match1_1_1 = null; tom_match1_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match1_1); declaration = ( Declaration) tom_match1_1_1;
 
                verifyDeclaration(declaration);
                return false;
               }}matchlab_match1_pattern3: {  OptionList options = null;  String name = null;  TomList args = null; if(tom_is_fun_sym_Appl(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomList tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_Appl_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match1_1); tom_match1_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match1_1); if(tom_is_fun_sym_Option(tom_match1_1_1)) {  OptionList tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match1_1_1); options = ( OptionList) tom_match1_1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); name = ( String) tom_match1_1_2_1; args = ( TomList) tom_match1_1_3;
 
                verifyApplStructure(options, name, args);
                return true;
               } } }}matchlab_match1_pattern4: {  String name = null;  TomList args = null;  OptionList options = null; if(tom_is_fun_sym_RecordAppl(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomList tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match1_1); tom_match1_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match1_1); if(tom_is_fun_sym_Option(tom_match1_1_1)) {  OptionList tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match1_1_1); options = ( OptionList) tom_match1_1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); name = ( String) tom_match1_1_2_1; args = ( TomList) tom_match1_1_3;
 
                verifyRecordStructure(options, name, args);
                return true;
               } } }}matchlab_match1_pattern5: {  Option orgTrack = null;  TomRuleList list = null; if(tom_is_fun_sym_RuleSet(tom_match1_1)) {  TomRuleList tom_match1_1_1 = null;  Option tom_match1_1_2 = null; tom_match1_1_1 = ( TomRuleList) tom_get_slot_RuleSet_ruleList(tom_match1_1); tom_match1_1_2 = ( Option) tom_get_slot_RuleSet_orgTrack(tom_match1_1); list = ( TomRuleList) tom_match1_1_1; orgTrack = ( Option) tom_match1_1_2;
 
                currentTomStructureOrgTrack = orgTrack;
                verifyRule(list);
                return false;
               }}matchlab_match1_pattern6: {  TomList args = null;  String name = null;  OptionList options = null;  TomTerm backQuoteTerm = null; if(tom_is_fun_sym_BackQuoteAppl(tom_match1_1)) {  Option tom_match1_1_1 = null;  TomName tom_match1_1_2 = null;  TomList tom_match1_1_3 = null; tom_match1_1_1 = ( Option) tom_get_slot_BackQuoteAppl_option(tom_match1_1); tom_match1_1_2 = ( TomName) tom_get_slot_BackQuoteAppl_astName(tom_match1_1); tom_match1_1_3 = ( TomList) tom_get_slot_BackQuoteAppl_args(tom_match1_1); backQuoteTerm = ( TomTerm) tom_match1_1; if(tom_is_fun_sym_Option(tom_match1_1_1)) {  OptionList tom_match1_1_1_1 = null; tom_match1_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match1_1_1); options = ( OptionList) tom_match1_1_1_1; if(tom_is_fun_sym_Name(tom_match1_1_2)) {  String tom_match1_1_2_1 = null; tom_match1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match1_1_2); name = ( String) tom_match1_1_2_1; args = ( TomList) tom_match1_1_3;
 
                permissiveVerify(backQuoteTerm);
                return false;
               } } }}matchlab_match1_pattern7: {

  return true; } }
 
          } else {
            return true;
          }
        }// end apply
      }; // end new
    traversal().genericCollect(parsedTerm, collectAndVerify);   
  }
  
    // Main type checking entry point: We check all interesting Tom Structure
  protected void typeCheck(TomTerm expandedTerm) {
    Collect1 collectAndVerify = new Collect1() 
      {  
        public boolean apply(ATerm term) {
          if(term instanceof TomTerm) {
             {  TomTerm tom_match2_1 = null; tom_match2_1 = ( TomTerm) term;matchlab_match2_pattern1: {  TomList list = null;  OptionList oplist = null; if(tom_is_fun_sym_Match(tom_match2_1)) {  TomTerm tom_match2_1_1 = null;  TomTerm tom_match2_1_2 = null;  Option tom_match2_1_3 = null; tom_match2_1_1 = ( TomTerm) tom_get_slot_Match_subjectList(tom_match2_1); tom_match2_1_2 = ( TomTerm) tom_get_slot_Match_patternList(tom_match2_1); tom_match2_1_3 = ( Option) tom_get_slot_Match_option(tom_match2_1); if(tom_is_fun_sym_PatternList(tom_match2_1_2)) {  TomList tom_match2_1_2_1 = null; tom_match2_1_2_1 = ( TomList) tom_get_slot_PatternList_tomList(tom_match2_1_2); list = ( TomList) tom_match2_1_2_1; if(tom_is_fun_sym_Option(tom_match2_1_3)) {  OptionList tom_match2_1_3_1 = null; tom_match2_1_3_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match2_1_3); oplist = ( OptionList) tom_match2_1_3_1;
   
                currentTomStructureOrgTrack = findOriginTracking(oplist);
                verifyMatchVariable(list);
                return false;
               } } }}matchlab_match2_pattern2: {  TomRuleList list = null;  Option orgTrack = null; if(tom_is_fun_sym_RuleSet(tom_match2_1)) {  TomRuleList tom_match2_1_1 = null;  Option tom_match2_1_2 = null; tom_match2_1_1 = ( TomRuleList) tom_get_slot_RuleSet_ruleList(tom_match2_1); tom_match2_1_2 = ( Option) tom_get_slot_RuleSet_orgTrack(tom_match2_1); list = ( TomRuleList) tom_match2_1_1; orgTrack = ( Option) tom_match2_1_2;
 
                currentTomStructureOrgTrack = orgTrack;
                verifyRuleVariable(list);
                return false;
               }}matchlab_match2_pattern3: {
  return true; } }
 
          } else {
            return true;
          }
        }// end apply
      }; // end new
    traversal().genericCollect(expandedTerm, collectAndVerify);
  }
  
  private void verifyMatchVariable(TomList patternList) {
    while(!patternList.isEmpty()) {
      TomTerm pa = patternList.getHead();
      TomTerm patterns = pa.getTermList();
        // collect variables
      ArrayList variableList = new ArrayList();
      collectVariable(variableList, patterns);      
      verifyVariableType(variableList);
      patternList = patternList.getTail();
    }
  }
  
  private void verifyRuleVariable(TomRuleList list) {
    while(!list.isEmpty()) {
      TomRule rewriteRule = list.getHead();
      TomTerm lhs = rewriteRule.getLhs();
      TomTerm rhs = rewriteRule.getRhs();
      TomList condList = rewriteRule.getCondList();
      Option orgTrack = findOriginTracking(rewriteRule.getOption().getOptionList());
      
      ArrayList variableLhs = new ArrayList();
      collectVariable(variableLhs, lhs);
      HashSet lhsSet = verifyVariableType(variableLhs);
      
      ArrayList variableRhs = new ArrayList();
      collectVariable(variableRhs, rhs);
      HashSet rhsSet = verifyVariableType(variableRhs);
      
      ArrayList variableCond = new ArrayList();
      collectVariable(variableCond, tom_make_Tom(condList) );
      HashSet condSet = verifyVariableType(variableCond);
      
      lhsSet.addAll(condSet);
      if(!condSet.isEmpty()) {
        System.out.println("Warning: improve verifyRuleVariable for matchingCondition");
      }
      
      if( !lhsSet.containsAll(rhsSet) ) {
        Iterator it = lhsSet.iterator();
        while(it.hasNext()) {
          rhsSet.remove(it.next());
        }
        messageRuleErrorUnknownVariable(rhsSet, orgTrack);
      }
        // case of rhs is a single variable
       {  TomTerm tom_match3_1 = null; tom_match3_1 = ( TomTerm) rhs;matchlab_match3_pattern1: {  String name = null; if(tom_is_fun_sym_Term(tom_match3_1)) {  TomTerm tom_match3_1_1 = null; tom_match3_1_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match3_1); if(tom_is_fun_sym_Variable(tom_match3_1_1)) {  Option tom_match3_1_1_1 = null;  TomName tom_match3_1_1_2 = null;  TomType tom_match3_1_1_3 = null; tom_match3_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match3_1_1); tom_match3_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match3_1_1); tom_match3_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match3_1_1); if(tom_is_fun_sym_Name(tom_match3_1_1_2)) {  String tom_match3_1_1_2_1 = null; tom_match3_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match3_1_1_2); name = ( String) tom_match3_1_1_2_1;
 
          String methodName = "";
           {  TomTerm tom_match4_1 = null; tom_match4_1 = ( TomTerm) lhs;matchlab_match4_pattern1: {  String name1 = null; if(tom_is_fun_sym_Term(tom_match4_1)) {  TomTerm tom_match4_1_1 = null; tom_match4_1_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match4_1); if(tom_is_fun_sym_Appl(tom_match4_1_1)) {  Option tom_match4_1_1_1 = null;  TomName tom_match4_1_1_2 = null;  TomList tom_match4_1_1_3 = null; tom_match4_1_1_1 = ( Option) tom_get_slot_Appl_option(tom_match4_1_1); tom_match4_1_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match4_1_1); tom_match4_1_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match4_1_1); if(tom_is_fun_sym_Name(tom_match4_1_1_2)) {  String tom_match4_1_1_2_1 = null; tom_match4_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_1_1_2); name1 = ( String) tom_match4_1_1_2_1;
 
              methodName = name1;
             } } }}matchlab_match4_pattern2: {  String name1 = null; if(tom_is_fun_sym_Term(tom_match4_1)) {  TomTerm tom_match4_1_1 = null; tom_match4_1_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match4_1); if(tom_is_fun_sym_RecordAppl(tom_match4_1_1)) {  Option tom_match4_1_1_1 = null;  TomName tom_match4_1_1_2 = null;  TomList tom_match4_1_1_3 = null; tom_match4_1_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match4_1_1); tom_match4_1_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match4_1_1); tom_match4_1_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match4_1_1); if(tom_is_fun_sym_Name(tom_match4_1_1_2)) {  String tom_match4_1_1_2_1 = null; tom_match4_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_1_1_2); name1 = ( String) tom_match4_1_1_2_1;
 
              methodName = name1;
             } } }} }
 
          TomType typeRhs = getSymbolCodomain(symbolTable().getSymbol(methodName));
          Iterator it = variableLhs.iterator();
          while(it.hasNext()) {
            TomTerm term = (TomTerm)it.next();
            if(term.getAstName().getString() == name) {
              TomType typeLhs = term.getAstType();
              if(typeLhs != typeRhs) {
                messageRuleErrorBadRhsVariable(name, typeRhs.getTomType().getString(), typeLhs.getTomType().getString(), orgTrack);
              }
            }
          }
         } } }} }
 
      list = list.getTail();
    }
  }
  
  private HashSet verifyVariableType(ArrayList list) {
      // compute multiplicities
    HashSet set = new HashSet();
    HashMap multiplicityMap = new HashMap();
    Iterator it = list.iterator();
    while(it.hasNext()) {
      TomTerm variable = (TomTerm)it.next();
      TomName name = variable.getAstName();
      
      if(set.contains(name)) {
        TomTerm var = (TomTerm)multiplicityMap.get(name);
        TomType type = var.getAstType();
        TomType type2 = variable.getAstType();
        if(!(type==type2)) {
          messageErrorIncoherentVariable(name.getString(), type.getTomType().getString(), type2.getTomType().getString(), variable.getOption().getOptionList()); 
        }
      } else {
        multiplicityMap.put(name, variable);
        set.add(name);
      }
    }
    return set;
  }
  
  private void messageErrorIncoherentVariable(String name, String type, String type2, OptionList options) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = findOriginTrackingLine(options);
    String s = "Bad variable type for '"+name+"': it has both type '"+type+"' and '"+type2+"' in structure declared line "+declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorUnknownVariable(Collection variableCollectionRhs, Option rewriteRuleOrgTrack) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = rewriteRuleOrgTrack.getLine();
    String s = "Unknown variable(s) " +variableCollectionRhs+ " used in right part of %rule declared line "+declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorBadRhsVariable(String name, String type, String type2, Option rewriteRuleOrgTrack) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = rewriteRuleOrgTrack.getLine();    
    String s = "Alone variable '"+name+"' has type '"+type+"' instead of type '"+type2+"' in right part of %rule declared line "+declLine;
    messageError(line,s);
  }
  
  private void permissiveVerify(TomTerm term) {
    Collect1 permissiveCollectAndVerify = new Collect1() 
      {  
        public boolean apply(ATerm term) {
          if(term instanceof TomTerm) {
             {  TomTerm tom_match5_1 = null; tom_match5_1 = ( TomTerm) term;matchlab_match5_pattern1: {  String name = null;  TomList args = null;  OptionList options = null; if(tom_is_fun_sym_BackQuoteAppl(tom_match5_1)) {  Option tom_match5_1_1 = null;  TomName tom_match5_1_2 = null;  TomList tom_match5_1_3 = null; tom_match5_1_1 = ( Option) tom_get_slot_BackQuoteAppl_option(tom_match5_1); tom_match5_1_2 = ( TomName) tom_get_slot_BackQuoteAppl_astName(tom_match5_1); tom_match5_1_3 = ( TomList) tom_get_slot_BackQuoteAppl_args(tom_match5_1); if(tom_is_fun_sym_Option(tom_match5_1_1)) {  OptionList tom_match5_1_1_1 = null; tom_match5_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match5_1_1); options = ( OptionList) tom_match5_1_1_1; if(tom_is_fun_sym_Name(tom_match5_1_2)) {  String tom_match5_1_2_1 = null; tom_match5_1_2_1 = ( String) tom_get_slot_Name_string(tom_match5_1_2); name = ( String) tom_match5_1_2_1; args = ( TomList) tom_match5_1_3;
 
                permissiveVerifyApplStructure(options, name, args);
                return true;
               } } }}matchlab_match5_pattern2: {  OptionList options = null;  String name = null;  TomList args = null; if(tom_is_fun_sym_Appl(tom_match5_1)) {  Option tom_match5_1_1 = null;  TomName tom_match5_1_2 = null;  TomList tom_match5_1_3 = null; tom_match5_1_1 = ( Option) tom_get_slot_Appl_option(tom_match5_1); tom_match5_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match5_1); tom_match5_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match5_1); if(tom_is_fun_sym_Option(tom_match5_1_1)) {  OptionList tom_match5_1_1_1 = null; tom_match5_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match5_1_1); options = ( OptionList) tom_match5_1_1_1; if(tom_is_fun_sym_Name(tom_match5_1_2)) {  String tom_match5_1_2_1 = null; tom_match5_1_2_1 = ( String) tom_get_slot_Name_string(tom_match5_1_2); name = ( String) tom_match5_1_2_1; args = ( TomList) tom_match5_1_3;
 
                permissiveVerifyApplStructure(options, name, args);
                return true;
               } } }}matchlab_match5_pattern3: {  OptionList options = null;  String name = null; if(tom_is_fun_sym_RecordAppl(tom_match5_1)) {  Option tom_match5_1_1 = null;  TomName tom_match5_1_2 = null;  TomList tom_match5_1_3 = null; tom_match5_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match5_1); tom_match5_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match5_1); tom_match5_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match5_1); if(tom_is_fun_sym_Option(tom_match5_1_1)) {  OptionList tom_match5_1_1_1 = null; tom_match5_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match5_1_1); options = ( OptionList) tom_match5_1_1_1; if(tom_is_fun_sym_Name(tom_match5_1_2)) {  String tom_match5_1_2_1 = null; tom_match5_1_2_1 = ( String) tom_get_slot_Name_string(tom_match5_1_2); name = ( String) tom_match5_1_2_1;
 
                messageRuleErrorRhsImpossibleRecord(options, name);
                return true;
               } } }}matchlab_match5_pattern4: {  OptionList orgTrack = null; if(tom_is_fun_sym_Placeholder(tom_match5_1)) {  Option tom_match5_1_1 = null; tom_match5_1_1 = ( Option) tom_get_slot_Placeholder_option(tom_match5_1); if(tom_is_fun_sym_Option(tom_match5_1_1)) {  OptionList tom_match5_1_1_1 = null; tom_match5_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match5_1_1); orgTrack = ( OptionList) tom_match5_1_1_1;
 
                messageRuleErrorRhsImpossiblePlaceholder(orgTrack);
               } }}matchlab_match5_pattern5: {
  return true; } }
 
          } else {
            return true;
          }
        }// end apply
      }; // end new
    traversal().genericCollect(term, permissiveCollectAndVerify);
  }
  
    /////////////////////////////////
    // MATCH VERIFICATION CONCERNS //
    /////////////////////////////////
  
    // Given a subject list, we test types in match signature
    // and then number and type of slots found in each pattern
  private void verifyMatch(TomList subjectList, TomList patternList) {
    ArrayList typeMatchArgs = new ArrayList();
    while( !subjectList.isEmpty() ) {
      TomTerm term = subjectList.getHead();
       {  TomTerm tom_match6_1 = null; tom_match6_1 = ( TomTerm) term;matchlab_match6_pattern1: {  String type = null;  String name = null; if(tom_is_fun_sym_TLVar(tom_match6_1)) {  String tom_match6_1_1 = null;  TomType tom_match6_1_2 = null; tom_match6_1_1 = ( String) tom_get_slot_TLVar_strName(tom_match6_1); tom_match6_1_2 = ( TomType) tom_get_slot_TLVar_astType(tom_match6_1); name = ( String) tom_match6_1_1; if(tom_is_fun_sym_TomTypeAlone(tom_match6_1_2)) {  String tom_match6_1_2_1 = null; tom_match6_1_2_1 = ( String) tom_get_slot_TomTypeAlone_string(tom_match6_1_2); type = ( String) tom_match6_1_2_1;
 
          if(symbolTable().getType(type) == null) {
            messageMatchTypeVariableError(name, type);
          }
          typeMatchArgs.add(type);
         } }} }
 
      subjectList = subjectList.getTail();
    }
    
    while(!patternList.isEmpty()) {
      TomTerm terms = patternList.getHead();
      verifyMatchPattern(terms, typeMatchArgs);
      patternList = patternList.getTail();
    }
  }
    // For each Pattern we count and collect type information
    // but also we test that terms are well formed
    // No top variable star are allowed
  private void verifyMatchPattern(TomTerm pattern, ArrayList typeMatchArgs) {
    int nbExpectedArgs = typeMatchArgs.size();
    TomList termList = pattern.getTermList().getTomList();
    ArrayList foundTypeMatch = new ArrayList();
    int line = nullInteger;
    int nbFoundArgs = 0;
    
    while( !termList.isEmpty() ) {
      TomTerm termAppl = termList.getHead();
      
       {  TomTerm tom_match7_1 = null; tom_match7_1 = ( TomTerm) termAppl;matchlab_match7_pattern1: {  OptionList list = null;  String name = null; if(tom_is_fun_sym_Appl(tom_match7_1)) {  Option tom_match7_1_1 = null;  TomName tom_match7_1_2 = null;  TomList tom_match7_1_3 = null; tom_match7_1_1 = ( Option) tom_get_slot_Appl_option(tom_match7_1); tom_match7_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match7_1); tom_match7_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match7_1); if(tom_is_fun_sym_Option(tom_match7_1_1)) {  OptionList tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match7_1_1); list = ( OptionList) tom_match7_1_1_1; if(tom_is_fun_sym_Name(tom_match7_1_2)) {  String tom_match7_1_2_1 = null; tom_match7_1_2_1 = ( String) tom_get_slot_Name_string(tom_match7_1_2); name = ( String) tom_match7_1_2_1;
 
          line = findOriginTrackingLine(list);
          nbFoundArgs++;
          foundTypeMatch.add(extractType(symbolTable().getSymbol(name)));
         } } }}matchlab_match7_pattern2: {  OptionList list = null; if(tom_is_fun_sym_Placeholder(tom_match7_1)) {  Option tom_match7_1_1 = null; tom_match7_1_1 = ( Option) tom_get_slot_Placeholder_option(tom_match7_1); if(tom_is_fun_sym_Option(tom_match7_1_1)) {  OptionList tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match7_1_1); list = ( OptionList) tom_match7_1_1_1;
 
          line = findOriginTrackingLine(list);
          nbFoundArgs++;
          foundTypeMatch.add((TomTerm) null);
         } }}matchlab_match7_pattern3: {  String name = null;  OptionList list = null; if(tom_is_fun_sym_VariableStar(tom_match7_1)) {  Option tom_match7_1_1 = null;  TomName tom_match7_1_2 = null;  TomType tom_match7_1_3 = null; tom_match7_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match7_1); tom_match7_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match7_1); tom_match7_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match7_1); if(tom_is_fun_sym_Option(tom_match7_1_1)) {  OptionList tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match7_1_1); list = ( OptionList) tom_match7_1_1_1; if(tom_is_fun_sym_Name(tom_match7_1_2)) {  String tom_match7_1_2_1 = null; tom_match7_1_2_1 = ( String) tom_get_slot_Name_string(tom_match7_1_2); name = ( String) tom_match7_1_2_1;

  
          line = findOriginTrackingLine(name,list);
          nbFoundArgs++;
          foundTypeMatch.add((TomTerm) null);
          messageMatchErrorVariableStar(name, line); 
         } } }}matchlab_match7_pattern4: {  OptionList options = null;  String name = null;  TomList args = null; if(tom_is_fun_sym_RecordAppl(tom_match7_1)) {  Option tom_match7_1_1 = null;  TomName tom_match7_1_2 = null;  TomList tom_match7_1_3 = null; tom_match7_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match7_1); tom_match7_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match7_1); tom_match7_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match7_1); if(tom_is_fun_sym_Option(tom_match7_1_1)) {  OptionList tom_match7_1_1_1 = null; tom_match7_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match7_1_1); options = ( OptionList) tom_match7_1_1_1; if(tom_is_fun_sym_Name(tom_match7_1_2)) {  String tom_match7_1_2_1 = null; tom_match7_1_2_1 = ( String) tom_get_slot_Name_string(tom_match7_1_2); name = ( String) tom_match7_1_2_1; args = ( TomList) tom_match7_1_3;
 
          line = findOriginTrackingLine(options);
          nbFoundArgs++;
          foundTypeMatch.add(extractType(symbolTable().getSymbol(name)));
         } } }} }
 
      termList = termList.getTail();
    }
      // nb elements in %match subject = nb elements in the pattern-action ?
    if(nbExpectedArgs != nbFoundArgs) {
      messageMatchErrorNumberArgument(nbExpectedArgs, nbFoundArgs, line);
      return;
    }
      // we test the type egality between arguments and pattern-action,
      // if it is not a variable  => type is null
    if(!strictType) return;
    for( int slot = 0; slot < nbExpectedArgs; slot++ ) {
      if ( (foundTypeMatch.get(slot) !=  typeMatchArgs.get(slot)) && (foundTypeMatch.get(slot) != null))
      { 	
        messageMatchErrorTypeArgument(slot+1, (String) typeMatchArgs.get(slot), (String) foundTypeMatch.get(slot), line); 
      }
    }
  }
  
  private void messageMatchErrorNumberArgument(int nbExpectedVar, int nbFoundVar, int line) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Bad number of arguments: "+nbExpectedVar+" argument(s) required but "+nbFoundVar+" found in %match structure declared line "+declLine; 
    messageError(line,s);
  }
  
  private void messageMatchErrorTypeArgument(int slotNumber, String expectedType, String givenType, int line) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s =  "Bad type for slot "+ slotNumber +" :Type '"+expectedType+"' required but Type '"+givenType+"' found in %match declared line "+declLine;
    messageError(line, s);
  }
  
  private void messageMatchTypeVariableError(String name, String type) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Variable '" + name + "' has an unknown type '"+type+"' in %match construct declared line "+declLine;
    messageError(declLine,s);
  }
  
  private void messageMatchErrorVariableStar(String nameVariableStar, int line) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Single list variable '"+nameVariableStar+"*' is not allowed on left most part of %match structure declared line "+declLine;
    messageError(line,s);
  }
  
    //////////////////////////////
    // SYMBOL AND TYPE CONCERNS //
    //////////////////////////////
  
  private void verifyDeclaration(Declaration declaration) {
     {  Declaration tom_match8_1 = null; tom_match8_1 = ( Declaration) declaration;matchlab_match8_pattern1: {  String tomName = null; if(tom_is_fun_sym_SymbolDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null; tom_match8_1_1 = ( TomName) tom_get_slot_SymbolDecl_astName(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1;
 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        verifySymbol("%op", tomSymbol);
       } }}matchlab_match8_pattern2: {  String tomName = null; if(tom_is_fun_sym_ArraySymbolDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null; tom_match8_1_1 = ( TomName) tom_get_slot_ArraySymbolDecl_astName(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1;
 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        verifySymbol("%oparray", tomSymbol);
       } }}matchlab_match8_pattern3: {  String tomName = null; if(tom_is_fun_sym_ListSymbolDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null; tom_match8_1_1 = ( TomName) tom_get_slot_ListSymbolDecl_astName(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1;
 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        verifySymbol("%oplist", tomSymbol);
       } }}matchlab_match8_pattern4: {  String tomName = null;  Option orgTrack = null;  TomList tomList = null; if(tom_is_fun_sym_TypeTermDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null;  TomList tom_match8_1_2 = null;  Option tom_match8_1_3 = null; tom_match8_1_1 = ( TomName) tom_get_slot_TypeTermDecl_astName(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_TypeTermDecl_keywordList(tom_match8_1); tom_match8_1_3 = ( Option) tom_get_slot_TypeTermDecl_orgTrack(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1; tomList = ( TomList) tom_match8_1_2; orgTrack = ( Option) tom_match8_1_3;
 
	currentTomStructureOrgTrack = orgTrack;
        verifyMultipleDefinitionOfType(tomName);
        verifyTypeDecl("%typeterm", tomList);
       } }}matchlab_match8_pattern5: {  String tomName = null;  TomList tomList = null;  Option orgTrack = null; if(tom_is_fun_sym_TypeListDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null;  TomList tom_match8_1_2 = null;  Option tom_match8_1_3 = null; tom_match8_1_1 = ( TomName) tom_get_slot_TypeListDecl_astName(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_TypeListDecl_keywordList(tom_match8_1); tom_match8_1_3 = ( Option) tom_get_slot_TypeListDecl_orgTrack(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1; tomList = ( TomList) tom_match8_1_2; orgTrack = ( Option) tom_match8_1_3;

 
	currentTomStructureOrgTrack = orgTrack;
        verifyMultipleDefinitionOfType(tomName);
        verifyTypeDecl("%typelist", tomList);
       } }}matchlab_match8_pattern6: {  TomList tomList = null;  Option orgTrack = null;  String tomName = null; if(tom_is_fun_sym_TypeArrayDecl(tom_match8_1)) {  TomName tom_match8_1_1 = null;  TomList tom_match8_1_2 = null;  Option tom_match8_1_3 = null; tom_match8_1_1 = ( TomName) tom_get_slot_TypeArrayDecl_astName(tom_match8_1); tom_match8_1_2 = ( TomList) tom_get_slot_TypeArrayDecl_keywordList(tom_match8_1); tom_match8_1_3 = ( Option) tom_get_slot_TypeArrayDecl_orgTrack(tom_match8_1); if(tom_is_fun_sym_Name(tom_match8_1_1)) {  String tom_match8_1_1_1 = null; tom_match8_1_1_1 = ( String) tom_get_slot_Name_string(tom_match8_1_1); tomName = ( String) tom_match8_1_1_1; tomList = ( TomList) tom_match8_1_2; orgTrack = ( Option) tom_match8_1_3;

 
	currentTomStructureOrgTrack = orgTrack;
        verifyMultipleDefinitionOfType(tomName);
        verifyTypeDecl("%typearray", tomList);
       } }} }
 
  }
  
    ///////////////////////////////
    // TYPE DECLARATION CONCERNS //
    ///////////////////////////////
  private void verifyTypeDecl(String declType, TomList listOfDeclaration) {
    ArrayList verifyList = new ArrayList();
    verifyList.add("get_fun_sym");
    verifyList.add("cmp_fun_sym");
    verifyList.add("equals");
    if(declType == "%typeterm")
    {
      verifyList.add("get_subterm");
    } else if(declType == "%typearray") {
      verifyList.add("get_element");
      verifyList.add("get_size");
    }
    else if(declType == "%typelist") {
      verifyList.add("get_head");
      verifyList.add("get_tail");
      verifyList.add("is_empty");
    }
    else {
      System.out.println("Invalid verifyTypeDecl parameter: "+declType);
      //throw new CheckErrorException();
    }
    
    while(!listOfDeclaration.isEmpty()) {
      TomTerm term = listOfDeclaration.getHead();
       {  TomTerm tom_match9_1 = null; tom_match9_1 = ( TomTerm) term;matchlab_match9_pattern1: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetFunctionSymbolDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TargetLanguage tom_match9_1_1_2 = null;  Option tom_match9_1_1_3 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetFunctionSymbolDecl_termArg(tom_match9_1_1); tom_match9_1_1_2 = ( TargetLanguage) tom_get_slot_GetFunctionSymbolDecl_tlCode(tom_match9_1_1); tom_match9_1_1_3 = ( Option) tom_get_slot_GetFunctionSymbolDecl_orgTrack(tom_match9_1_1); orgTrack = ( Option) tom_match9_1_1_3;
 
          checkField("get_fun_sym",verifyList,orgTrack, declType);
         } }}matchlab_match9_pattern2: {  Option orgTrack = null;  String name2 = null;  String name1 = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_CompareFunctionSymbolDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TomTerm tom_match9_1_1_2 = null;  TargetLanguage tom_match9_1_1_3 = null;  Option tom_match9_1_1_4 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_CompareFunctionSymbolDecl_symbolArg1(tom_match9_1_1); tom_match9_1_1_2 = ( TomTerm) tom_get_slot_CompareFunctionSymbolDecl_symbolArg2(tom_match9_1_1); tom_match9_1_1_3 = ( TargetLanguage) tom_get_slot_CompareFunctionSymbolDecl_tlCode(tom_match9_1_1); tom_match9_1_1_4 = ( Option) tom_get_slot_CompareFunctionSymbolDecl_orgTrack(tom_match9_1_1); if(tom_is_fun_sym_Variable(tom_match9_1_1_1)) {  Option tom_match9_1_1_1_1 = null;  TomName tom_match9_1_1_1_2 = null;  TomType tom_match9_1_1_1_3 = null; tom_match9_1_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_1); tom_match9_1_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_1); tom_match9_1_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_1); if(tom_is_fun_sym_Name(tom_match9_1_1_1_2)) {  String tom_match9_1_1_1_2_1 = null; tom_match9_1_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_1_2); name1 = ( String) tom_match9_1_1_1_2_1; if(tom_is_fun_sym_Variable(tom_match9_1_1_2)) {  Option tom_match9_1_1_2_1 = null;  TomName tom_match9_1_1_2_2 = null;  TomType tom_match9_1_1_2_3 = null; tom_match9_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_2); tom_match9_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_2); tom_match9_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_2); if(tom_is_fun_sym_Name(tom_match9_1_1_2_2)) {  String tom_match9_1_1_2_2_1 = null; tom_match9_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_2_2); name2 = ( String) tom_match9_1_1_2_2_1; orgTrack = ( Option) tom_match9_1_1_4;
 
          checkFieldAndLinearArgs("cmp_fun_sym",verifyList,orgTrack,name1,name2, declType);
         } } } } } }}matchlab_match9_pattern3: {  String name1 = null;  Option orgTrack = null;  String name2 = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_TermsEqualDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TomTerm tom_match9_1_1_2 = null;  TargetLanguage tom_match9_1_1_3 = null;  Option tom_match9_1_1_4 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_TermsEqualDecl_termArg1(tom_match9_1_1); tom_match9_1_1_2 = ( TomTerm) tom_get_slot_TermsEqualDecl_termArg2(tom_match9_1_1); tom_match9_1_1_3 = ( TargetLanguage) tom_get_slot_TermsEqualDecl_tlCode(tom_match9_1_1); tom_match9_1_1_4 = ( Option) tom_get_slot_TermsEqualDecl_orgTrack(tom_match9_1_1); if(tom_is_fun_sym_Variable(tom_match9_1_1_1)) {  Option tom_match9_1_1_1_1 = null;  TomName tom_match9_1_1_1_2 = null;  TomType tom_match9_1_1_1_3 = null; tom_match9_1_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_1); tom_match9_1_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_1); tom_match9_1_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_1); if(tom_is_fun_sym_Name(tom_match9_1_1_1_2)) {  String tom_match9_1_1_1_2_1 = null; tom_match9_1_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_1_2); name1 = ( String) tom_match9_1_1_1_2_1; if(tom_is_fun_sym_Variable(tom_match9_1_1_2)) {  Option tom_match9_1_1_2_1 = null;  TomName tom_match9_1_1_2_2 = null;  TomType tom_match9_1_1_2_3 = null; tom_match9_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_2); tom_match9_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_2); tom_match9_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_2); if(tom_is_fun_sym_Name(tom_match9_1_1_2_2)) {  String tom_match9_1_1_2_2_1 = null; tom_match9_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_2_2); name2 = ( String) tom_match9_1_1_2_2_1; orgTrack = ( Option) tom_match9_1_1_4;
 
          checkFieldAndLinearArgs("equals",verifyList,orgTrack,name1,name2, declType);
         } } } } } }}matchlab_match9_pattern4: {  String name1 = null;  String name2 = null;  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetSubtermDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TomTerm tom_match9_1_1_2 = null;  TargetLanguage tom_match9_1_1_3 = null;  Option tom_match9_1_1_4 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetSubtermDecl_termArg(tom_match9_1_1); tom_match9_1_1_2 = ( TomTerm) tom_get_slot_GetSubtermDecl_variable(tom_match9_1_1); tom_match9_1_1_3 = ( TargetLanguage) tom_get_slot_GetSubtermDecl_tlCode(tom_match9_1_1); tom_match9_1_1_4 = ( Option) tom_get_slot_GetSubtermDecl_orgTrack(tom_match9_1_1); if(tom_is_fun_sym_Variable(tom_match9_1_1_1)) {  Option tom_match9_1_1_1_1 = null;  TomName tom_match9_1_1_1_2 = null;  TomType tom_match9_1_1_1_3 = null; tom_match9_1_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_1); tom_match9_1_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_1); tom_match9_1_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_1); if(tom_is_fun_sym_Name(tom_match9_1_1_1_2)) {  String tom_match9_1_1_1_2_1 = null; tom_match9_1_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_1_2); name1 = ( String) tom_match9_1_1_1_2_1; if(tom_is_fun_sym_Variable(tom_match9_1_1_2)) {  Option tom_match9_1_1_2_1 = null;  TomName tom_match9_1_1_2_2 = null;  TomType tom_match9_1_1_2_3 = null; tom_match9_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_2); tom_match9_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_2); tom_match9_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_2); if(tom_is_fun_sym_Name(tom_match9_1_1_2_2)) {  String tom_match9_1_1_2_2_1 = null; tom_match9_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_2_2); name2 = ( String) tom_match9_1_1_2_2_1; orgTrack = ( Option) tom_match9_1_1_4;

 
          checkFieldAndLinearArgs("get_subterm",verifyList,orgTrack,name1,name2, declType);
         } } } } } }}matchlab_match9_pattern5: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetHeadDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TargetLanguage tom_match9_1_1_2 = null;  Option tom_match9_1_1_3 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetHeadDecl_var(tom_match9_1_1); tom_match9_1_1_2 = ( TargetLanguage) tom_get_slot_GetHeadDecl_tlcode(tom_match9_1_1); tom_match9_1_1_3 = ( Option) tom_get_slot_GetHeadDecl_orgTrack(tom_match9_1_1); orgTrack = ( Option) tom_match9_1_1_3;

 
          checkField("get_head",verifyList,orgTrack, declType);
         } }}matchlab_match9_pattern6: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetTailDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TargetLanguage tom_match9_1_1_2 = null;  Option tom_match9_1_1_3 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetTailDecl_var(tom_match9_1_1); tom_match9_1_1_2 = ( TargetLanguage) tom_get_slot_GetTailDecl_tlcode(tom_match9_1_1); tom_match9_1_1_3 = ( Option) tom_get_slot_GetTailDecl_orgTrack(tom_match9_1_1); orgTrack = ( Option) tom_match9_1_1_3;
 
          checkField("get_tail",verifyList,orgTrack, declType);
         } }}matchlab_match9_pattern7: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_IsEmptyDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TargetLanguage tom_match9_1_1_2 = null;  Option tom_match9_1_1_3 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_IsEmptyDecl_var(tom_match9_1_1); tom_match9_1_1_2 = ( TargetLanguage) tom_get_slot_IsEmptyDecl_tlcode(tom_match9_1_1); tom_match9_1_1_3 = ( Option) tom_get_slot_IsEmptyDecl_orgTrack(tom_match9_1_1); orgTrack = ( Option) tom_match9_1_1_3;
 
          checkField("is_empty",verifyList,orgTrack, declType);
         } }}matchlab_match9_pattern8: {  Option orgTrack = null;  String name2 = null;  String name1 = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetElementDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TomTerm tom_match9_1_1_2 = null;  TargetLanguage tom_match9_1_1_3 = null;  Option tom_match9_1_1_4 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetElementDecl_kid1(tom_match9_1_1); tom_match9_1_1_2 = ( TomTerm) tom_get_slot_GetElementDecl_kid2(tom_match9_1_1); tom_match9_1_1_3 = ( TargetLanguage) tom_get_slot_GetElementDecl_tlCode(tom_match9_1_1); tom_match9_1_1_4 = ( Option) tom_get_slot_GetElementDecl_orgTrack(tom_match9_1_1); if(tom_is_fun_sym_Variable(tom_match9_1_1_1)) {  Option tom_match9_1_1_1_1 = null;  TomName tom_match9_1_1_1_2 = null;  TomType tom_match9_1_1_1_3 = null; tom_match9_1_1_1_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_1); tom_match9_1_1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_1); tom_match9_1_1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_1); if(tom_is_fun_sym_Name(tom_match9_1_1_1_2)) {  String tom_match9_1_1_1_2_1 = null; tom_match9_1_1_1_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_1_2); name1 = ( String) tom_match9_1_1_1_2_1; if(tom_is_fun_sym_Variable(tom_match9_1_1_2)) {  Option tom_match9_1_1_2_1 = null;  TomName tom_match9_1_1_2_2 = null;  TomType tom_match9_1_1_2_3 = null; tom_match9_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match9_1_1_2); tom_match9_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match9_1_1_2); tom_match9_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match9_1_1_2); if(tom_is_fun_sym_Name(tom_match9_1_1_2_2)) {  String tom_match9_1_1_2_2_1 = null; tom_match9_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match9_1_1_2_2); name2 = ( String) tom_match9_1_1_2_2_1; orgTrack = ( Option) tom_match9_1_1_4;

  
          checkFieldAndLinearArgs("get_element",verifyList,orgTrack,name1,name2, declType);
         } } } } } }}matchlab_match9_pattern9: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToTomTerm(tom_match9_1)) {  Declaration tom_match9_1_1 = null; tom_match9_1_1 = ( Declaration) tom_get_slot_DeclarationToTomTerm_astDeclaration(tom_match9_1); if(tom_is_fun_sym_GetSizeDecl(tom_match9_1_1)) {  TomTerm tom_match9_1_1_1 = null;  TargetLanguage tom_match9_1_1_2 = null;  Option tom_match9_1_1_3 = null; tom_match9_1_1_1 = ( TomTerm) tom_get_slot_GetSizeDecl_kid1(tom_match9_1_1); tom_match9_1_1_2 = ( TargetLanguage) tom_get_slot_GetSizeDecl_tlCode(tom_match9_1_1); tom_match9_1_1_3 = ( Option) tom_get_slot_GetSizeDecl_orgTrack(tom_match9_1_1); orgTrack = ( Option) tom_match9_1_1_3;
 
          checkField("get_size",verifyList,orgTrack, declType);
         } }} }
 
      listOfDeclaration = listOfDeclaration.getTail();
    }
    
    if(verifyList.contains("equals")) {
      verifyList.remove(verifyList.indexOf("equals"));
    }    
    if(!verifyList.isEmpty()) {
      messageMissingMacroFunctions(declType, verifyList);
    }
  }
  
  private void verifyMultipleDefinitionOfType(String name) {
    if(alreadyStudiedType.contains(name)) {
      messageTypeErrorYetDefined(name);
    }
    else {
      alreadyStudiedType.add(name);
    }
  }
  
  private void messageTypeErrorYetDefined(String name) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Multiple definition of type: Type '"+ name +"' is already defined";
    messageError(declLine, s);
  }
  
    /////////////////////////////////
    // SYMBOL DECLARATION CONCERNS //
    /////////////////////////////////
  private void verifySymbol(String symbolType, TomSymbol tomSymbol){
    int nbArgs=0;
    OptionList optionList = tomSymbol.getOption().getOptionList();
      // We save first the origin tracking of the symbol declaration
    currentTomStructureOrgTrack = findOriginTracking(optionList);
    TomTypeList l = getSymbolDomain(tomSymbol);
    TomType type = getSymbolCodomain(tomSymbol);
    String name = tomSymbol.getAstName().getString();
    int line  = findOriginTrackingLine(optionList);
    SlotList slotList = tomSymbol.getSlotList();
    verifyMultipleDefinitionOfSymbol(name, line);
    verifySymbolCodomain(type.getString(), name, line);
    verifySymbolArguments(l, name, line);
    verifySymbolOptions(symbolType, optionList);
  }
  
  private void verifyMultipleDefinitionOfSymbol(String name, int line) {
    if(alreadyStudiedSymbol.contains(name)) {
      messageOperatorErrorYetDefined(name,line);
    }
    else {
      alreadyStudiedSymbol.add(name);
    }
  }
  
  private void messageOperatorErrorYetDefined(String name, int line) {
    String s = "Multiple definition of operator: Operator '"+ name +"' is already defined";
    messageError(line, s);
  }
  
  private void verifySymbolCodomain(String returnTypeName, String symbName, int symbLine) {
    if (symbolTable().getType(returnTypeName) == null){
      messageTypeOperatorError(returnTypeName, symbName, symbLine);
    }
  }
  
  private void messageTypeOperatorError(String type, String name, int line) {
    String s = "Operator '" + name + "' has an unknown return type: '" + type + "'";
    messageError(line,s);
  }
  
  private void verifySymbolArguments(TomTypeList args, String symbName, int symbLine) {
    TomType type;
    int nbArgs = 0;
    while(!args.isEmpty()) {
      type = args.getHead();
      verifyTypeExist(type.getString(), nbArgs, symbName, symbLine);
      nbArgs++;
      args = args.getTail();
    }
  }
  
  private void verifyTypeExist(String typeName, int slotPosition, String symbName, int symbLine) {
    if (symbolTable().getType(typeName) == null){
      messageTypesOperatorError(typeName, slotPosition, symbName, symbLine);
    }
  }
  
  private void messageTypesOperatorError(String type, int slotPosition, String name, int line) {
    String s = "Slot "+slotPosition + " in operator '"+ name + "' signature has an unknown type: '" + type + "'";
    messageError(line,s);
  }
  
  private void verifySymbolOptions(String symbType, OptionList list) {
    ArrayList verifyList = new ArrayList();
    boolean foundOpMake = false;
    if(symbType == "%op"){
    } else if(symbType == "%oparray" ) {
      verifyList.add("make_empty");
      verifyList.add("make_append");
    }
    else if(symbType == "%oplist") {
      verifyList.add("make_empty");
      verifyList.add("make_insert"); 
    }
    else {
      System.out.println("Invalid verifySymbolOptions parameter: "+symbType);
      //throw new CheckErrorException();
    }
    
    while(!list.isEmpty()) {
      Option term = list.getHead();
       {  Option tom_match10_1 = null; tom_match10_1 = ( Option) term;matchlab_match10_pattern1: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToOption(tom_match10_1)) {  Declaration tom_match10_1_1 = null; tom_match10_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match10_1); if(tom_is_fun_sym_MakeEmptyArray(tom_match10_1_1)) {  TomName tom_match10_1_1_1 = null;  TomTerm tom_match10_1_1_2 = null;  TargetLanguage tom_match10_1_1_3 = null;  Option tom_match10_1_1_4 = null; tom_match10_1_1_1 = ( TomName) tom_get_slot_MakeEmptyArray_astName(tom_match10_1_1); tom_match10_1_1_2 = ( TomTerm) tom_get_slot_MakeEmptyArray_varSize(tom_match10_1_1); tom_match10_1_1_3 = ( TargetLanguage) tom_get_slot_MakeEmptyArray_tlCode(tom_match10_1_1); tom_match10_1_1_4 = ( Option) tom_get_slot_MakeEmptyArray_orgTrack(tom_match10_1_1); orgTrack = ( Option) tom_match10_1_1_4;

  
          checkField("make_empty",verifyList,orgTrack, symbType);
         } }}matchlab_match10_pattern2: {  Option orgTrack = null;  String name2 = null;  String name1 = null; if(tom_is_fun_sym_DeclarationToOption(tom_match10_1)) {  Declaration tom_match10_1_1 = null; tom_match10_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match10_1); if(tom_is_fun_sym_MakeAddArray(tom_match10_1_1)) {  TomName tom_match10_1_1_1 = null;  TomTerm tom_match10_1_1_2 = null;  TomTerm tom_match10_1_1_3 = null;  TargetLanguage tom_match10_1_1_4 = null;  Option tom_match10_1_1_5 = null; tom_match10_1_1_1 = ( TomName) tom_get_slot_MakeAddArray_astName(tom_match10_1_1); tom_match10_1_1_2 = ( TomTerm) tom_get_slot_MakeAddArray_varElt(tom_match10_1_1); tom_match10_1_1_3 = ( TomTerm) tom_get_slot_MakeAddArray_varList(tom_match10_1_1); tom_match10_1_1_4 = ( TargetLanguage) tom_get_slot_MakeAddArray_tlCode(tom_match10_1_1); tom_match10_1_1_5 = ( Option) tom_get_slot_MakeAddArray_orgTrack(tom_match10_1_1); if(tom_is_fun_sym_Variable(tom_match10_1_1_2)) {  Option tom_match10_1_1_2_1 = null;  TomName tom_match10_1_1_2_2 = null;  TomType tom_match10_1_1_2_3 = null; tom_match10_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match10_1_1_2); tom_match10_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match10_1_1_2); tom_match10_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match10_1_1_2); if(tom_is_fun_sym_Name(tom_match10_1_1_2_2)) {  String tom_match10_1_1_2_2_1 = null; tom_match10_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match10_1_1_2_2); name2 = ( String) tom_match10_1_1_2_2_1; if(tom_is_fun_sym_Variable(tom_match10_1_1_3)) {  Option tom_match10_1_1_3_1 = null;  TomName tom_match10_1_1_3_2 = null;  TomType tom_match10_1_1_3_3 = null; tom_match10_1_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match10_1_1_3); tom_match10_1_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match10_1_1_3); tom_match10_1_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match10_1_1_3); if(tom_is_fun_sym_Name(tom_match10_1_1_3_2)) {  String tom_match10_1_1_3_2_1 = null; tom_match10_1_1_3_2_1 = ( String) tom_get_slot_Name_string(tom_match10_1_1_3_2); name1 = ( String) tom_match10_1_1_3_2_1; orgTrack = ( Option) tom_match10_1_1_5;
 
          checkFieldAndLinearArgs("make_append", verifyList, orgTrack, name1, name2, symbType);
         } } } } } }}matchlab_match10_pattern3: {  Option orgTrack = null; if(tom_is_fun_sym_DeclarationToOption(tom_match10_1)) {  Declaration tom_match10_1_1 = null; tom_match10_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match10_1); if(tom_is_fun_sym_MakeEmptyList(tom_match10_1_1)) {  TomName tom_match10_1_1_1 = null;  TargetLanguage tom_match10_1_1_2 = null;  Option tom_match10_1_1_3 = null; tom_match10_1_1_1 = ( TomName) tom_get_slot_MakeEmptyList_astName(tom_match10_1_1); tom_match10_1_1_2 = ( TargetLanguage) tom_get_slot_MakeEmptyList_tlCode(tom_match10_1_1); tom_match10_1_1_3 = ( Option) tom_get_slot_MakeEmptyList_orgTrack(tom_match10_1_1); orgTrack = ( Option) tom_match10_1_1_3;

 
          checkField("make_empty",verifyList,orgTrack, symbType);
          
         } }}matchlab_match10_pattern4: {  String name1 = null;  Option orgTrack = null;  String name2 = null; if(tom_is_fun_sym_DeclarationToOption(tom_match10_1)) {  Declaration tom_match10_1_1 = null; tom_match10_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match10_1); if(tom_is_fun_sym_MakeAddList(tom_match10_1_1)) {  TomName tom_match10_1_1_1 = null;  TomTerm tom_match10_1_1_2 = null;  TomTerm tom_match10_1_1_3 = null;  TargetLanguage tom_match10_1_1_4 = null;  Option tom_match10_1_1_5 = null; tom_match10_1_1_1 = ( TomName) tom_get_slot_MakeAddList_astName(tom_match10_1_1); tom_match10_1_1_2 = ( TomTerm) tom_get_slot_MakeAddList_varElt(tom_match10_1_1); tom_match10_1_1_3 = ( TomTerm) tom_get_slot_MakeAddList_varList(tom_match10_1_1); tom_match10_1_1_4 = ( TargetLanguage) tom_get_slot_MakeAddList_tlCode(tom_match10_1_1); tom_match10_1_1_5 = ( Option) tom_get_slot_MakeAddList_orgTrack(tom_match10_1_1); if(tom_is_fun_sym_Variable(tom_match10_1_1_2)) {  Option tom_match10_1_1_2_1 = null;  TomName tom_match10_1_1_2_2 = null;  TomType tom_match10_1_1_2_3 = null; tom_match10_1_1_2_1 = ( Option) tom_get_slot_Variable_option(tom_match10_1_1_2); tom_match10_1_1_2_2 = ( TomName) tom_get_slot_Variable_astName(tom_match10_1_1_2); tom_match10_1_1_2_3 = ( TomType) tom_get_slot_Variable_astType(tom_match10_1_1_2); if(tom_is_fun_sym_Name(tom_match10_1_1_2_2)) {  String tom_match10_1_1_2_2_1 = null; tom_match10_1_1_2_2_1 = ( String) tom_get_slot_Name_string(tom_match10_1_1_2_2); name2 = ( String) tom_match10_1_1_2_2_1; if(tom_is_fun_sym_Variable(tom_match10_1_1_3)) {  Option tom_match10_1_1_3_1 = null;  TomName tom_match10_1_1_3_2 = null;  TomType tom_match10_1_1_3_3 = null; tom_match10_1_1_3_1 = ( Option) tom_get_slot_Variable_option(tom_match10_1_1_3); tom_match10_1_1_3_2 = ( TomName) tom_get_slot_Variable_astName(tom_match10_1_1_3); tom_match10_1_1_3_3 = ( TomType) tom_get_slot_Variable_astType(tom_match10_1_1_3); if(tom_is_fun_sym_Name(tom_match10_1_1_3_2)) {  String tom_match10_1_1_3_2_1 = null; tom_match10_1_1_3_2_1 = ( String) tom_get_slot_Name_string(tom_match10_1_1_3_2); name1 = ( String) tom_match10_1_1_3_2_1; orgTrack = ( Option) tom_match10_1_1_5;
 
          checkFieldAndLinearArgs("make_insert", verifyList, orgTrack, name1, name2, symbType);
         } } } } } }}matchlab_match10_pattern5: {  Option orgTrack = null;  TomList makeArgsList = null; if(tom_is_fun_sym_DeclarationToOption(tom_match10_1)) {  Declaration tom_match10_1_1 = null; tom_match10_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match10_1); if(tom_is_fun_sym_MakeDecl(tom_match10_1_1)) {  TomName tom_match10_1_1_1 = null;  TomType tom_match10_1_1_2 = null;  TomList tom_match10_1_1_3 = null;  TargetLanguage tom_match10_1_1_4 = null;  Option tom_match10_1_1_5 = null; tom_match10_1_1_1 = ( TomName) tom_get_slot_MakeDecl_astName(tom_match10_1_1); tom_match10_1_1_2 = ( TomType) tom_get_slot_MakeDecl_astType(tom_match10_1_1); tom_match10_1_1_3 = ( TomList) tom_get_slot_MakeDecl_args(tom_match10_1_1); tom_match10_1_1_4 = ( TargetLanguage) tom_get_slot_MakeDecl_tlCode(tom_match10_1_1); tom_match10_1_1_5 = ( Option) tom_get_slot_MakeDecl_orgTrack(tom_match10_1_1); makeArgsList = ( TomList) tom_match10_1_1_3; orgTrack = ( Option) tom_match10_1_1_5;

 
          if (!foundOpMake) {
            foundOpMake = true;
            verifyMakeDeclArgs(makeArgsList, orgTrack, symbType);
          }
          else {messageMacroFunctionRepeated("make", orgTrack, symbType);}
         } }}matchlab_match10_pattern6: {
       list = list.getTail();} }
 
    }
    if(!verifyList.isEmpty()) {
      messageMissingMacroFunctions(symbType, verifyList);
    }
  }

  private void verifyMakeDeclArgs(TomList argsList, Option orgTrack, String symbType){
      // we test the necessity to use different names for each variable-parameter.
    ArrayList listVar = new ArrayList();
    while(!argsList.isEmpty()) {
      TomTerm termVar = argsList.getHead();
       {  TomTerm tom_match11_1 = null; tom_match11_1 = ( TomTerm) termVar;matchlab_match11_pattern1: {  OptionList listOption = null;  String name = null; if(tom_is_fun_sym_Variable(tom_match11_1)) {  Option tom_match11_1_1 = null;  TomName tom_match11_1_2 = null;  TomType tom_match11_1_3 = null; tom_match11_1_1 = ( Option) tom_get_slot_Variable_option(tom_match11_1); tom_match11_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match11_1); tom_match11_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match11_1); if(tom_is_fun_sym_Option(tom_match11_1_1)) {  OptionList tom_match11_1_1_1 = null; tom_match11_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match11_1_1); listOption = ( OptionList) tom_match11_1_1_1; if(tom_is_fun_sym_Name(tom_match11_1_2)) {  String tom_match11_1_2_1 = null; tom_match11_1_2_1 = ( String) tom_get_slot_Name_string(tom_match11_1_2); name = ( String) tom_match11_1_2_1;
 
          if(listVar.contains(name)) {
            messageTwoSameNameVariableError("make", name, orgTrack, symbType);
          } else {
            listVar.add(name);
          }
         } } }} }
 
      argsList = argsList.getTail();
    }
  }

  private void checkField(String field, ArrayList findFunctions, Option orgTrack, String declType) {
    if(findFunctions.contains(field)) {
      findFunctions.remove(findFunctions.indexOf(field)); 
    } else {
      messageMacroFunctionRepeated(field, orgTrack, declType);
    }
  }
  
  private void checkFieldAndLinearArgs(String field, ArrayList findFunctions, Option orgTrack, String name1, String name2, String declType) {
    checkField(field,findFunctions, orgTrack, declType);
    if(name1.equals(name2)) { 
      messageTwoSameNameVariableError(field, name1, orgTrack, declType);
    }
  }
  
  private void messageMacroFunctionRepeated(String nameFunction, Option orgTrack, String declType) {
    int line = orgTrack.getLine(), declLine = currentTomStructureOrgTrack.getLine();
    String nameDecl = currentTomStructureOrgTrack.getAstName().getString();
    String s = "Repeated macro-function '"+nameFunction+"' in '"+declType+" "+nameDecl+"' declared line "+declLine;
    messageError(line, s);
  }
  
  private void messageTwoSameNameVariableError(String nameFunction, String nameVar, Option orgTrack, String declType) {
    int line = orgTrack.getLine(), declLine = currentTomStructureOrgTrack.getLine();
    String nameDecl = currentTomStructureOrgTrack.getAstName().getString();
    String s =  "Arguments must be linear in method '"+nameFunction+"' of '"+declType+" "+nameDecl+"' declared at line "+declLine+" :Variable '"+nameVar+"' is repeated";
    messageError(line, s);
  }
  
  private void messageMissingMacroFunctions(String nameConstruct, ArrayList list) {
    int line = currentTomStructureOrgTrack.getLine();
    String name = currentTomStructureOrgTrack.getAstName().getString();
    String s = "Missing macro-function(s) "+list+" in '"+nameConstruct+" "+name+"'";
    messageError(line, s);
  }
  
    //////////////////////
    // RECORDS CONCERNS //
    //////////////////////
  
  private void verifyRecordStructure(OptionList option, String tomName, TomList args) {
    TomSymbol symbol = symbolTable().getSymbol(tomName);
    if(symbol != null) {
      SlotList slotList = symbol.getSlotList();
        // constants have an emptySlotList
        // the length of the slotList corresponds to the arity of the operator
        // list operator with [] no allowed
      if(slotList.isEmpty() || (args.isEmpty() && (isListOperator(symbol) ||  isArrayOperator(symbol)))) {
        messageBracketError(tomName, option);
      }
      verifyRecordSlots(args,slotList, tomName);
    } else {
      messageSymbolError(tomName, option);
    }
  }
  
    // We test the existence of slotName contained in pairSlotAppl
    // and then the associated term
  private void verifyRecordSlots(TomList listPair, SlotList slotList, String methodName) {
    ArrayList slotIndex = new ArrayList();
    while( !listPair.isEmpty() ) {
      TomTerm pair = listPair.getHead();
      TomName name = pair.getSlotName();
      int index = getSlotIndex(slotList,name);
      if(index < 0) {
        messageSlotNameError(pair,slotList, methodName); 
      } else {
        Integer integerIndex = new Integer(index);
        if(slotIndex.contains(integerIndex)) {
          messageSlotRepeatedError(pair, methodName);
        }
        slotIndex.add(integerIndex);
      }
      listPair = listPair.getTail();
    }
  }

  private void messageSlotNameError(TomTerm pairSlotName, SlotList slotList, String methodName) {
    ArrayList listOfPossibleSlot = new ArrayList();
    while ( !slotList.isEmpty() ) {
      TomName name = slotList.getHead().getSlotName();
      if ( !name.isEmptyName()) listOfPossibleSlot.add(name.getString());
      slotList = slotList.getTail();
    }
    int line = nullInteger;
    String s = "";
     {  TomTerm tom_match12_1 = null; tom_match12_1 = ( TomTerm) pairSlotName;matchlab_match12_pattern1: {  String name = null;  OptionList list = null; if(tom_is_fun_sym_PairSlotAppl(tom_match12_1)) {  TomName tom_match12_1_1 = null;  TomTerm tom_match12_1_2 = null; tom_match12_1_1 = ( TomName) tom_get_slot_PairSlotAppl_slotName(tom_match12_1); tom_match12_1_2 = ( TomTerm) tom_get_slot_PairSlotAppl_appl(tom_match12_1); if(tom_is_fun_sym_Name(tom_match12_1_1)) {  String tom_match12_1_1_1 = null; tom_match12_1_1_1 = ( String) tom_get_slot_Name_string(tom_match12_1_1); name = ( String) tom_match12_1_1_1; if(tom_is_fun_sym_Appl(tom_match12_1_2)) {  Option tom_match12_1_2_1 = null;  TomName tom_match12_1_2_2 = null;  TomList tom_match12_1_2_3 = null; tom_match12_1_2_1 = ( Option) tom_get_slot_Appl_option(tom_match12_1_2); tom_match12_1_2_2 = ( TomName) tom_get_slot_Appl_astName(tom_match12_1_2); tom_match12_1_2_3 = ( TomList) tom_get_slot_Appl_args(tom_match12_1_2); if(tom_is_fun_sym_Option(tom_match12_1_2_1)) {  OptionList tom_match12_1_2_1_1 = null; tom_match12_1_2_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match12_1_2_1); list = ( OptionList) tom_match12_1_2_1_1;
 
        line = findOriginTrackingLine(list);
        s += "Slot Name '" + name + "' is not correct: See method '"+methodName+ "' -  Line : "+line;
       } } } }} }
 
    s += "\nPossible slot names are : "+listOfPossibleSlot;
    messageError(line, s);
  }
  
  private void messageSlotRepeatedError(TomTerm pairSlotName, String methodName) {
     {  TomTerm tom_match13_1 = null; tom_match13_1 = ( TomTerm) pairSlotName;matchlab_match13_pattern1: {  OptionList list = null;  String name = null; if(tom_is_fun_sym_PairSlotAppl(tom_match13_1)) {  TomName tom_match13_1_1 = null;  TomTerm tom_match13_1_2 = null; tom_match13_1_1 = ( TomName) tom_get_slot_PairSlotAppl_slotName(tom_match13_1); tom_match13_1_2 = ( TomTerm) tom_get_slot_PairSlotAppl_appl(tom_match13_1); if(tom_is_fun_sym_Name(tom_match13_1_1)) {  String tom_match13_1_1_1 = null; tom_match13_1_1_1 = ( String) tom_get_slot_Name_string(tom_match13_1_1); name = ( String) tom_match13_1_1_1; if(tom_is_fun_sym_Appl(tom_match13_1_2)) {  Option tom_match13_1_2_1 = null;  TomName tom_match13_1_2_2 = null;  TomList tom_match13_1_2_3 = null; tom_match13_1_2_1 = ( Option) tom_get_slot_Appl_option(tom_match13_1_2); tom_match13_1_2_2 = ( TomName) tom_get_slot_Appl_astName(tom_match13_1_2); tom_match13_1_2_3 = ( TomList) tom_get_slot_Appl_args(tom_match13_1_2); if(tom_is_fun_sym_Option(tom_match13_1_2_1)) {  OptionList tom_match13_1_2_1_1 = null; tom_match13_1_2_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match13_1_2_1); list = ( OptionList) tom_match13_1_2_1_1;
 
        int line = findOriginTrackingLine(list);
        String s = "Same slot names can not be used several times: See method '"+methodName+ "' -  Line : "+line;
        s += "Repeated slot Name : '"+name+"'";
        messageError(line, s);
       } } } }} }
 
  }
  
  private void messageBracketError(String name, OptionList optionList) {
    int line = findOriginTrackingLine(name,optionList);
    String s = "[] are not allowed on lists or arrays nor constants, see: "+name;
    messageError(line,s);
  }

    //////////////////////
    // APPL CONCERNS    //
    //////////////////////
  
  private void verifyApplStructure(OptionList optionList, String name, TomList argsList) {
    TomSymbol symbol = symbolTable().getSymbol(name);
    if(symbol==null  && (hasConstructor(optionList) || !argsList.isEmpty())) {
      messageSymbolError(name, optionList);
    } else {
      if(!argsList.isEmpty()) {
          //in case of oparray or oplist, the number of arguments is not tested
        boolean listOrArray =  (isListOperator(symbol) ||  isArrayOperator(symbol));
        int nbFoundArgs = 0;
        ArrayList foundType = new ArrayList();
        Map optionMap  = new HashMap();
          // we shall test also the type of each args
        while(!argsList.isEmpty()) {
          TomTerm term = argsList.getHead();
           {  TomTerm tom_match14_1 = null; tom_match14_1 = ( TomTerm) term;matchlab_match14_pattern1: {  OptionList options = null;  String name1 = null; if(tom_is_fun_sym_Appl(tom_match14_1)) {  Option tom_match14_1_1 = null;  TomName tom_match14_1_2 = null;  TomList tom_match14_1_3 = null; tom_match14_1_1 = ( Option) tom_get_slot_Appl_option(tom_match14_1); tom_match14_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match14_1); tom_match14_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match14_1); if(tom_is_fun_sym_Option(tom_match14_1_1)) {  OptionList tom_match14_1_1_1 = null; tom_match14_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match14_1_1); options = ( OptionList) tom_match14_1_1_1; if(tom_is_fun_sym_Name(tom_match14_1_2)) {  String tom_match14_1_2_1 = null; tom_match14_1_2_1 = ( String) tom_get_slot_Name_string(tom_match14_1_2); name1 = ( String) tom_match14_1_2_1;
 
              optionMap.put(new Integer(nbFoundArgs), options);
              nbFoundArgs++;
              foundType.add(extractType(symbolTable().getSymbol(name1)));
             } } }}matchlab_match14_pattern2: {  OptionList options = null;  String name1 = null; if(tom_is_fun_sym_RecordAppl(tom_match14_1)) {  Option tom_match14_1_1 = null;  TomName tom_match14_1_2 = null;  TomList tom_match14_1_3 = null; tom_match14_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match14_1); tom_match14_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match14_1); tom_match14_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match14_1); if(tom_is_fun_sym_Option(tom_match14_1_1)) {  OptionList tom_match14_1_1_1 = null; tom_match14_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match14_1_1); options = ( OptionList) tom_match14_1_1_1; if(tom_is_fun_sym_Name(tom_match14_1_2)) {  String tom_match14_1_2_1 = null; tom_match14_1_2_1 = ( String) tom_get_slot_Name_string(tom_match14_1_2); name1 = ( String) tom_match14_1_2_1;
 
              optionMap.put(new Integer(nbFoundArgs), options);
              nbFoundArgs++;
              foundType.add(extractType(symbolTable().getSymbol(name1)));
             } } }}matchlab_match14_pattern3: { if(tom_is_fun_sym_VariableStar(tom_match14_1)) {  Option tom_match14_1_1 = null;  TomName tom_match14_1_2 = null;  TomType tom_match14_1_3 = null; tom_match14_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match14_1); tom_match14_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match14_1); tom_match14_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match14_1);
 nbFoundArgs++;foundType.add((TomTerm)null); }}matchlab_match14_pattern4: {  OptionList options = null; if(tom_is_fun_sym_Placeholder(tom_match14_1)) {  Option tom_match14_1_1 = null; tom_match14_1_1 = ( Option) tom_get_slot_Placeholder_option(tom_match14_1); if(tom_is_fun_sym_Option(tom_match14_1_1)) {  OptionList tom_match14_1_1_1 = null; tom_match14_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match14_1_1); options = ( OptionList) tom_match14_1_1_1;
 
              if (listOrArray) {
                messageImpossiblePlaceHolderInListStructure(name,options);
              } else {
                nbFoundArgs++;foundType.add((TomTerm)null);
              }
             } }} }
 
          argsList =  argsList.getTail();
        }

        TomTypeList l = getSymbolDomain(symbol);
        if (!listOrArray) {
          int nbExpectedArgs = l.getLength();
            // We test the number of args vs its definition
          if (nbExpectedArgs != nbFoundArgs) {
            int line = findOriginTrackingLine(name,optionList);
            messageNumberArgumentsError(nbExpectedArgs, nbFoundArgs, name, line);
            return;
          }        
          for( int slot = 0; slot < nbExpectedArgs; slot++ ) {
            String s = getTomType(l.getHead());
            if ( (foundType.get(slot) != s) && (foundType.get(slot) != null))
            {
              int line = findOriginTrackingLine((OptionList) optionMap.get(new Integer(slot)));
              messageApplErrorTypeArgument(name, slot+1, s, (String) foundType.get(slot), line); 
            }
            l = l.getTail();
          }
        } else {
            // We worry only about returned type
          String s = getTomType(l.getHead());
          for( int slot = 0; slot <foundType.size() ; slot++ ) {
            if ( (foundType.get(slot) != s) && (foundType.get(slot) != null)) {
              int line = findOriginTrackingLine((OptionList) optionMap.get(new Integer(slot)));
              messageApplErrorTypeArgument(name, slot+1, s, (String) foundType.get(slot), line);
            }
          }
        }
      }
    }
  }

  private void  messageImpossiblePlaceHolderInListStructure(String listApplName, OptionList optionList) {
    int line = findOriginTrackingLine(optionList);
    String s = "Placeholder is not allowed in list operator '"+listApplName+"'";
    messageError(line,s);
  }
  
  private void  messageApplErrorTypeArgument(String applName, int slotNumber, String expectedType, String givenType, int line) {
    String s = "Bad type of argument: Argument "+slotNumber+" of method '" + applName + "' has type '"+expectedType+"' required but type '"+givenType+"' found";
    messageError(line,s);
  }
    
  private void permissiveVerifyApplStructure(OptionList optionList, String name, TomList argsList) {
    TomSymbol symbol = symbolTable().getSymbol(name);
    if(symbol==null && !argsList.isEmpty()) {
      messageWarningSymbol(name, optionList);
    } else {
      if(!argsList.isEmpty()) {
          //in case of oparray or oplist, the number of arguments is not tested
        if ( !isListOperator(symbol) &&  !isArrayOperator(symbol) ) {
            // We test the number of args vs its definition
          int nbExpectedArgs = getSymbolDomain(symbol).getLength();
          int nbFoundArgs = length(argsList);
          if (nbExpectedArgs != nbFoundArgs) {
            int line = findOriginTrackingLine(name,optionList);
            messageNumberArgumentsError(nbExpectedArgs, nbFoundArgs, name, line);
          }
        }
          // We ensure that the symbol has a Make declaration if not list nor array
        if (!isListOperator(symbol) &&  !isArrayOperator(symbol)) {
          if ( !findMakeDeclOrDefSymbol(symbol.getOption().getOptionList()) ) {
            messageNoMakeForSymbol(name, optionList);
          }
        }
      }
    }
  }

  private boolean findMakeDeclOrDefSymbol(OptionList list) {
    while(!list.isEmpty()) {
      Option term = list.getHead();
       {  Option tom_match15_1 = null; tom_match15_1 = ( Option) term;matchlab_match15_pattern1: {  Option orgTrack = null;  TomList makeArgsList = null; if(tom_is_fun_sym_DeclarationToOption(tom_match15_1)) {  Declaration tom_match15_1_1 = null; tom_match15_1_1 = ( Declaration) tom_get_slot_DeclarationToOption_astDeclaration(tom_match15_1); if(tom_is_fun_sym_MakeDecl(tom_match15_1_1)) {  TomName tom_match15_1_1_1 = null;  TomType tom_match15_1_1_2 = null;  TomList tom_match15_1_1_3 = null;  TargetLanguage tom_match15_1_1_4 = null;  Option tom_match15_1_1_5 = null; tom_match15_1_1_1 = ( TomName) tom_get_slot_MakeDecl_astName(tom_match15_1_1); tom_match15_1_1_2 = ( TomType) tom_get_slot_MakeDecl_astType(tom_match15_1_1); tom_match15_1_1_3 = ( TomList) tom_get_slot_MakeDecl_args(tom_match15_1_1); tom_match15_1_1_4 = ( TargetLanguage) tom_get_slot_MakeDecl_tlCode(tom_match15_1_1); tom_match15_1_1_5 = ( Option) tom_get_slot_MakeDecl_orgTrack(tom_match15_1_1); makeArgsList = ( TomList) tom_match15_1_1_3; orgTrack = ( Option) tom_match15_1_1_5;
 
          return true;
         } }}matchlab_match15_pattern2: { if(tom_is_fun_sym_DefinedSymbol(tom_match15_1)) {
 return true; }}matchlab_match15_pattern3: {
 list = list.getTail();} }
 
    }
    return false;
  }

  private void messageSymbolError(String name, OptionList optionList) {
    int line = findOriginTrackingLine(name, optionList);
    String s = "Symbol method : '" + name + "' not found";
    messageError(line,s);
  }
  
  private void messageWarningSymbol(String name, OptionList optionList) {
    if(!warningAll || noWarning) return;
    String nameDecl = currentTomStructureOrgTrack.getAstName().getString();
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = findOriginTrackingLine(name, optionList);
    System.out.println("\n *** Warning *** Possible error in structure "+nameDecl+" declared line "+declLine);
    System.out.println(" *** Unknown method '"+name+"' : Ensure the type coherence by yourself line : " + line);
  }
  
  private void messageNumberArgumentsError(int nbArg, int nbArg2, String name, int line) {
    String s = "Bad number of arguments for method '" + name + "':" + nbArg + " arguments are required but " + nbArg2 + " are given";
    messageError(line,s);
  }

    ////////////////////////////////
    // RULE VERIFICATION CONCERNS //
    ////////////////////////////////
    /* for each rewrite rule we make test on: 
     * Rhs shall have no underscore, be a var*?, be a record?
     * Each Lhs shall start with the same production name
     * This symbol name shall not be already declared
     * Lhs and Rhs shall have the same return type
     * Used variable on rhs shall be coherent and declared on lhs
     */ 
  private void verifyRule(TomRuleList ruleList) {
    int i = 0;
    TomRule currentRule;
    String name = "Unknown";
    while(!ruleList.isEmpty()) {
      currentRule = ruleList.getHead();
      matchBlock: {
         {  TomRule tom_match16_1 = null; tom_match16_1 = ( TomRule) currentRule;matchlab_match16_pattern1: {  TomTerm rhs = null;  Option option = null;  TomTerm lhs = null;  TomList condList = null; if(tom_is_fun_sym_RewriteRule(tom_match16_1)) {  TomTerm tom_match16_1_1 = null;  TomTerm tom_match16_1_2 = null;  TomList tom_match16_1_3 = null;  Option tom_match16_1_4 = null; tom_match16_1_1 = ( TomTerm) tom_get_slot_RewriteRule_lhs(tom_match16_1); tom_match16_1_2 = ( TomTerm) tom_get_slot_RewriteRule_rhs(tom_match16_1); tom_match16_1_3 = ( TomList) tom_get_slot_RewriteRule_condList(tom_match16_1); tom_match16_1_4 = ( Option) tom_get_slot_RewriteRule_option(tom_match16_1); if(tom_is_fun_sym_Term(tom_match16_1_1)) {  TomTerm tom_match16_1_1_1 = null; tom_match16_1_1_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match16_1_1); lhs = ( TomTerm) tom_match16_1_1_1; if(tom_is_fun_sym_Term(tom_match16_1_2)) {  TomTerm tom_match16_1_2_1 = null; tom_match16_1_2_1 = ( TomTerm) tom_get_slot_Term_tomTerm(tom_match16_1_2); rhs = ( TomTerm) tom_match16_1_2_1; condList = ( TomList) tom_match16_1_3; option = ( Option) tom_match16_1_4;
 
            name = verifyLhsRuleAndConstructorEgality(lhs, name, i);
            if(i == 0) {
                // update the root of lhs: it becomes a defined symbol
              ast().updateDefinedSymbol(symbolTable(),lhs);
            }
            verifyRhsRuleStructure(rhs);
              // TODO: verify the list of conditions
            break matchBlock;
           } } }}matchlab_match16_pattern2: {
 
            System.out.println("Strange rule:\n" + currentRule);
            //throw new CheckErrorException();
          } }
 
      }
      ruleList = ruleList.getTail();
      i++;
    }
  }
  
  private String verifyLhsRuleAndConstructorEgality(TomTerm lhs, String  ruleName, int ruleNumber) {
    String methodName = "";
    OptionList options = tsf().makeOptionList();
     {  TomTerm tom_match17_1 = null; tom_match17_1 = ( TomTerm) lhs;matchlab_match17_pattern1: {  String name = null;  OptionList optionList = null;  TomTerm appl = null;  TomList args = null; if(tom_is_fun_sym_Appl(tom_match17_1)) {  Option tom_match17_1_1 = null;  TomName tom_match17_1_2 = null;  TomList tom_match17_1_3 = null; tom_match17_1_1 = ( Option) tom_get_slot_Appl_option(tom_match17_1); tom_match17_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match17_1); tom_match17_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match17_1); appl = ( TomTerm) tom_match17_1; if(tom_is_fun_sym_Option(tom_match17_1_1)) {  OptionList tom_match17_1_1_1 = null; tom_match17_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match17_1_1); optionList = ( OptionList) tom_match17_1_1_1; if(tom_is_fun_sym_Name(tom_match17_1_2)) {  String tom_match17_1_2_1 = null; tom_match17_1_2_1 = ( String) tom_get_slot_Name_string(tom_match17_1_2); name = ( String) tom_match17_1_2_1; args = ( TomList) tom_match17_1_3;
 
        // No alone variable nor simple constructor
        if( args.isEmpty() && !hasConstructor(optionList)) {
          int line = findOriginTrackingLine(optionList);
          messageRuleErrorVariable(name, line);
        }
        syntaxCheck(appl);
          // lhs outermost symbol shall have a corresponding make
        TomSymbol symb = symbolTable().getSymbol(name);
        if (symb == null) {
          messageRuleErrorUnknownSymbol(name);
          return methodName;
        }
        if ( !findMakeDeclOrDefSymbol(symbolTable().getSymbol(name).getOption().getOptionList())) {
          messageNoMakeForSymbol(name, optionList);
        }
        options = optionList;
        methodName = name;
       } } }}matchlab_match17_pattern2: {  String name1 = null;  OptionList optionList = null; if(tom_is_fun_sym_VariableStar(tom_match17_1)) {  Option tom_match17_1_1 = null;  TomName tom_match17_1_2 = null;  TomType tom_match17_1_3 = null; tom_match17_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match17_1); tom_match17_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match17_1); tom_match17_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match17_1); if(tom_is_fun_sym_Option(tom_match17_1_1)) {  OptionList tom_match17_1_1_1 = null; tom_match17_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match17_1_1); optionList = ( OptionList) tom_match17_1_1_1; if(tom_is_fun_sym_Name(tom_match17_1_2)) {  String tom_match17_1_2_1 = null; tom_match17_1_2_1 = ( String) tom_get_slot_Name_string(tom_match17_1_2); name1 = ( String) tom_match17_1_2_1;
 
        int line = findOriginTrackingLine(name1,optionList);    
        messageRuleErrorVariableStar(name1, line);
        return ruleName;
       } } }}matchlab_match17_pattern3: {  OptionList options2 = null; if(tom_is_fun_sym_Placeholder(tom_match17_1)) {  Option tom_match17_1_1 = null; tom_match17_1_1 = ( Option) tom_get_slot_Placeholder_option(tom_match17_1); if(tom_is_fun_sym_Option(tom_match17_1_1)) {  OptionList tom_match17_1_1_1 = null; tom_match17_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match17_1_1); options2 = ( OptionList) tom_match17_1_1_1;
  
        messageRuleErrorLhsImpossiblePlaceHolder(options2);
        return ruleName;
       } }}matchlab_match17_pattern4: {  OptionList optionList = null;  TomList args = null;  String name = null;  TomTerm rec = null; if(tom_is_fun_sym_RecordAppl(tom_match17_1)) {  Option tom_match17_1_1 = null;  TomName tom_match17_1_2 = null;  TomList tom_match17_1_3 = null; tom_match17_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match17_1); tom_match17_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match17_1); tom_match17_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match17_1); rec = ( TomTerm) tom_match17_1; if(tom_is_fun_sym_Option(tom_match17_1_1)) {  OptionList tom_match17_1_1_1 = null; tom_match17_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match17_1_1); optionList = ( OptionList) tom_match17_1_1_1; if(tom_is_fun_sym_Name(tom_match17_1_2)) {  String tom_match17_1_2_1 = null; tom_match17_1_2_1 = ( String) tom_get_slot_Name_string(tom_match17_1_2); name = ( String) tom_match17_1_2_1; args = ( TomList) tom_match17_1_3;
 
        syntaxCheck(rec);
        options = optionList;
        methodName = name;
       } } }} }
 
    if ( ruleNumber != 0 && !methodName.equals(ruleName)) {   
      messageRuleErrorConstructorEgality(methodName, ruleName, options);
      return ruleName;
    }
    return methodName;
  }

  private void messageRuleErrorUnknownSymbol(String symbolName) {
    int line = currentTomStructureOrgTrack.getLine();
    String s = "Symbol '" +symbolName+ "' has no been declared in rule declared line "+line;
    messageError(line,s); 
  }
  
  private void messageNoMakeForSymbol(String name, OptionList optionList) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = findOriginTrackingLine(optionList);
    String s = "Symbol '" +name+ "' has no 'make' method associated in structure declared line "+declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorVariable(String nameVariableStar, int line) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Alone variable " +nameVariableStar+ " is not allowed on left hand side of structure %rule declared line "+declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorVariableStar(String nameVariableStar, int line) {
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Single list variable '" +nameVariableStar+ "*' is not allowed on left hand side of structure %rule declared line "+declLine;
    messageError(line,s);
  }

  private void messageRuleErrorLhsImpossiblePlaceHolder(OptionList optionList) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = findOriginTrackingLine(optionList);
    String s = "Alone placeholder is not allowed in left hand side of structure %rule declared line " +declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorConstructorEgality(String  name, String nameExpected, OptionList optionList) {
    int declLine = currentTomStructureOrgTrack.getLine();
    int line = findOriginTrackingLine(name, optionList);
    String s = "Left most symbol name '" + nameExpected + "' expected, but '" + name + "' found in left hand side of structure %rule declared line " +declLine;
    messageError(line,s);
  }
  
  private void verifyRhsRuleStructure(TomTerm ruleRhs) {
    matchBlock: {
       {  TomTerm tom_match18_1 = null; tom_match18_1 = ( TomTerm) ruleRhs;matchlab_match18_pattern1: {  TomTerm appl = null;  String name = null;  OptionList options = null;  TomList args = null; if(tom_is_fun_sym_Appl(tom_match18_1)) {  Option tom_match18_1_1 = null;  TomName tom_match18_1_2 = null;  TomList tom_match18_1_3 = null; tom_match18_1_1 = ( Option) tom_get_slot_Appl_option(tom_match18_1); tom_match18_1_2 = ( TomName) tom_get_slot_Appl_astName(tom_match18_1); tom_match18_1_3 = ( TomList) tom_get_slot_Appl_args(tom_match18_1); appl = ( TomTerm) tom_match18_1; if(tom_is_fun_sym_Option(tom_match18_1_1)) {  OptionList tom_match18_1_1_1 = null; tom_match18_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match18_1_1); options = ( OptionList) tom_match18_1_1_1; if(tom_is_fun_sym_Name(tom_match18_1_2)) {  String tom_match18_1_2_1 = null; tom_match18_1_2_1 = ( String) tom_get_slot_Name_string(tom_match18_1_2); name = ( String) tom_match18_1_2_1; args = ( TomList) tom_match18_1_3;
 
          permissiveVerify(appl);
          break matchBlock;
         } } }}matchlab_match18_pattern2: {  OptionList option = null; if(tom_is_fun_sym_Placeholder(tom_match18_1)) {  Option tom_match18_1_1 = null; tom_match18_1_1 = ( Option) tom_get_slot_Placeholder_option(tom_match18_1); if(tom_is_fun_sym_Option(tom_match18_1_1)) {  OptionList tom_match18_1_1_1 = null; tom_match18_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match18_1_1); option = ( OptionList) tom_match18_1_1_1;
 
          messageRuleErrorRhsImpossiblePlaceholder(option);
          break matchBlock;
         } }}matchlab_match18_pattern3: {  OptionList option = null;  String name = null; if(tom_is_fun_sym_VariableStar(tom_match18_1)) {  Option tom_match18_1_1 = null;  TomName tom_match18_1_2 = null;  TomType tom_match18_1_3 = null; tom_match18_1_1 = ( Option) tom_get_slot_VariableStar_option(tom_match18_1); tom_match18_1_2 = ( TomName) tom_get_slot_VariableStar_astName(tom_match18_1); tom_match18_1_3 = ( TomType) tom_get_slot_VariableStar_astType(tom_match18_1); if(tom_is_fun_sym_Option(tom_match18_1_1)) {  OptionList tom_match18_1_1_1 = null; tom_match18_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match18_1_1); option = ( OptionList) tom_match18_1_1_1; if(tom_is_fun_sym_Name(tom_match18_1_2)) {  String tom_match18_1_2_1 = null; tom_match18_1_2_1 = ( String) tom_get_slot_Name_string(tom_match18_1_2); name = ( String) tom_match18_1_2_1;
 
          messageRuleErrorRhsImpossibleVarStar(option, name);
          break matchBlock;
         } } }}matchlab_match18_pattern4: {  OptionList option = null;  String tomName = null; if(tom_is_fun_sym_RecordAppl(tom_match18_1)) {  Option tom_match18_1_1 = null;  TomName tom_match18_1_2 = null;  TomList tom_match18_1_3 = null; tom_match18_1_1 = ( Option) tom_get_slot_RecordAppl_option(tom_match18_1); tom_match18_1_2 = ( TomName) tom_get_slot_RecordAppl_astName(tom_match18_1); tom_match18_1_3 = ( TomList) tom_get_slot_RecordAppl_args(tom_match18_1); if(tom_is_fun_sym_Option(tom_match18_1_1)) {  OptionList tom_match18_1_1_1 = null; tom_match18_1_1_1 = ( OptionList) tom_get_slot_Option_optionList(tom_match18_1_1); option = ( OptionList) tom_match18_1_1_1; if(tom_is_fun_sym_Name(tom_match18_1_2)) {  String tom_match18_1_2_1 = null; tom_match18_1_2_1 = ( String) tom_get_slot_Name_string(tom_match18_1_2); tomName = ( String) tom_match18_1_2_1;
 
          messageRuleErrorRhsImpossibleRecord(option, tomName);
          break matchBlock;
         } } }}matchlab_match18_pattern5: {
 
          System.out.println("Strange rule rhs:\n" + ruleRhs);
          //throw new CheckErrorException();
        } }
 
    }
  }

  private void messageRuleErrorRhsImpossiblePlaceholder(OptionList optionList) {
    int line = findOriginTrackingLine(optionList);
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Placeholder is not allowed on right part of structure %rule declared line "+declLine;
    messageError(line,s);
  }
  
  private void messageRuleErrorRhsImpossibleRecord(OptionList optionList, String name) {
    int line = findOriginTrackingLine(optionList);
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Record '"+name+"[...]' is not allowed on right part of structure %rule declared line "+declLine;
    messageError(line,s);
  }
  private void messageRuleErrorRhsImpossibleVarStar(OptionList optionList, String name) {
    int line = findOriginTrackingLine(optionList);
    int declLine = currentTomStructureOrgTrack.getLine();
    String s = "Single list variable '"+name+"*' is not allowed in right hand side of structure %rule declared line " +declLine;
    messageError(line,s);
  }
    
    /////////////
    // GLOBALS //
    /////////////
  
  private String extractType(TomSymbol symbol) {
    TomType type = getSymbolCodomain(symbol);
    return getTomType(type);
  }

  private void messageError(int line, String msg) {
    String s = "\n"+msg+"\n-- Error occured at line: " + line +" in file: "+currentTomStructureOrgTrack.getFileName().getString()+"\n";
    errorMessage.add(s);
  }
  
    // findOriginTrackingLine(_,_) method returns the line (stocked in optionList)  of object 'name'.
  private int findOriginTrackingLine(String name, OptionList optionList) {
    while(!optionList.isEmpty()) {
      Option subject = optionList.getHead();
       {  Option tom_match19_1 = null; tom_match19_1 = ( Option) subject;matchlab_match19_pattern1: {  String origName = null;  int line; if(tom_is_fun_sym_OriginTracking(tom_match19_1)) {  TomName tom_match19_1_1 = null;  int tom_match19_1_2;  TomName tom_match19_1_3 = null; tom_match19_1_1 = ( TomName) tom_get_slot_OriginTracking_astName(tom_match19_1); tom_match19_1_2 = ( int) tom_get_slot_OriginTracking_line(tom_match19_1); tom_match19_1_3 = ( TomName) tom_get_slot_OriginTracking_fileName(tom_match19_1); if(tom_is_fun_sym_Name(tom_match19_1_1)) {  String tom_match19_1_1_1 = null; tom_match19_1_1_1 = ( String) tom_get_slot_Name_string(tom_match19_1_1); origName = ( String) tom_match19_1_1_1; line = ( int) tom_match19_1_2;
 
          if(name.equals(origName)) {
            return line;
          }
         } }} }
 
      optionList = optionList.getTail();
    }
    System.out.println("findOriginTrackingLine: '" + name + "' not found in " + optionList);
    return -1;
    //throw new CheckErrorException();
  }
  
    // findOriginTrackingLine(_) method returns the first number of line (stocked in optionList).
  private int findOriginTrackingLine(OptionList optionList) {
    while(!optionList.isEmpty()) {
      Option subject = optionList.getHead();
       {  Option tom_match20_1 = null; tom_match20_1 = ( Option) subject;matchlab_match20_pattern1: {  int line; if(tom_is_fun_sym_OriginTracking(tom_match20_1)) {  TomName tom_match20_1_1 = null;  int tom_match20_1_2;  TomName tom_match20_1_3 = null; tom_match20_1_1 = ( TomName) tom_get_slot_OriginTracking_astName(tom_match20_1); tom_match20_1_2 = ( int) tom_get_slot_OriginTracking_line(tom_match20_1); tom_match20_1_3 = ( TomName) tom_get_slot_OriginTracking_fileName(tom_match20_1); line = ( int) tom_match20_1_2;
 
          return line;
         }} }
 
      optionList = optionList.getTail();
    }
    System.out.println("findOriginTrackingLine:  not found");
    return -1;
    //throw new CheckErrorException();
  }
  
} //class TomChecker
