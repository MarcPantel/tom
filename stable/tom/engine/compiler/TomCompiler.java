/* Generated by TOM: Do not edit this file */ /*
  
    TOM - To One Matching Compiler

    Copyright (C) 2000-2004 INRIA
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
  
import java.util.*;

import jtom.adt.tomsignature.types.*;

import jtom.runtime.Replace1;
import jtom.tools.TomTask;
import jtom.tools.TomTaskInput;
import jtom.tools.Tools;
import aterm.*;
import jtom.exception.TomRuntimeException;
import jtom.TomEnvironment;

public class TomCompiler extends TomTask {
  TomKernelCompiler tomKernelCompiler;
  private String debugKey = null;
  private boolean debugMode = false, eCode = false;
  private int absVarNumber = 0;
  
  public TomCompiler(TomEnvironment environment,
                     TomKernelCompiler tomKernelCompiler) {
    super("Tom Compiler", environment);
    this.tomKernelCompiler = tomKernelCompiler;
  }
  
// ------------------------------------------------------------
  /* Generated by TOM: Do not edit this file */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
// ------------------------------------------------------------

  public void initProcess() {
    debugMode = getInput().isDebugMode();
    eCode = getInput().isECode();
  }
	
  public void process() {
    try {
      long startChrono = 0;
      boolean verbose = getInput().isVerbose(), intermediate = getInput().isIntermediate();
      if(verbose) { startChrono = System.currentTimeMillis();}
      
      TomTerm preCompiledTerm = preProcessing(getInput().getTerm());
        //System.out.println("preCompiledTerm = \n" + preCompiledTerm);
      TomTerm compiledTerm = tomKernelCompiler.compileMatching(preCompiledTerm);
      
      if(verbose) {
        System.out.println("TOM compilation phase (" + (System.currentTimeMillis()-startChrono)+ " ms)");
      }
      if(intermediate) {
        Tools.generateOutput(getInput().getBaseInputFileName() + TomTaskInput.compiledSuffix, compiledTerm);
      }
      getInput().setTerm(compiledTerm);
      
    } catch (Exception e) {
      addError("Exception occurs in TomCompiler: "+e.getMessage(), getInput().getInputFileName(), 0, 0);
      e.printStackTrace();
      return;
    }
  }

  private OptionList option() {
    return ast().makeOption();
  }

    /* 
     * preProcessing:
     * replaces MakeTerm by BuildList, BuildArray or BuildTerm
     *
     * transforms RuleSet into Function + Match + MakeTerm
     * abstract list-matching patterns
     * rename non-linear patterns
     */

  Replace1 replace_preProcessing = new Replace1() {
      public ATerm apply(ATerm subject) {
        if(subject instanceof TomTerm) {
           { TomTerm tom_match1_1 =(( TomTerm)subject);{ if(tom_is_fun_sym_MakeTerm(tom_match1_1) ||  false ) { { TomTerm tom_match1_1_1 =tom_get_slot_MakeTerm_kid1(tom_match1_1); if(tom_is_fun_sym_Variable(tom_match1_1_1) ||  false ) { { TomTerm var =tom_match1_1_1; { TomName tom_match1_1_1_2 =tom_get_slot_Variable_astName(tom_match1_1_1); { TomName name =tom_match1_1_1_2;
 
              return var;
            }}} }} } if(tom_is_fun_sym_MakeTerm(tom_match1_1) ||  false ) { { TomTerm tom_match1_1_1 =tom_get_slot_MakeTerm_kid1(tom_match1_1); if(tom_is_fun_sym_VariableStar(tom_match1_1_1) ||  false ) { { TomTerm var =tom_match1_1_1; { TomName tom_match1_1_1_2 =tom_get_slot_VariableStar_astName(tom_match1_1_1); { TomName name =tom_match1_1_1_2;

 
              return var;
            }}} }} } if(tom_is_fun_sym_MakeTerm(tom_match1_1) ||  false ) { { TomTerm tom_match1_1_1 =tom_get_slot_MakeTerm_kid1(tom_match1_1); if(tom_is_fun_sym_Appl(tom_match1_1_1) ||  false ) { { OptionList tom_match1_1_1_1 =tom_get_slot_Appl_option(tom_match1_1_1); { NameList tom_match1_1_1_2 =tom_get_slot_Appl_nameList(tom_match1_1_1); { TomList tom_match1_1_1_3 =tom_get_slot_Appl_args(tom_match1_1_1); { OptionList optionList =tom_match1_1_1_1; if(tom_is_fun_sym_concTomName(tom_match1_1_1_2) ||  false ) { { NameList tom_match1_1_1_2_list1 =tom_match1_1_1_2; if(!(tom_is_empty_NameList(tom_match1_1_1_2_list1))) { { TomName tom_match1_1_1_2_1 =(( TomName)tom_get_head_NameList(tom_match1_1_1_2_list1));{ tom_match1_1_1_2_list1=tom_get_tail_NameList(tom_match1_1_1_2_list1); if(tom_is_fun_sym_Name(tom_match1_1_1_2_1) ||  false ) { { TomName name =tom_match1_1_1_2_1; { String tom_match1_1_1_2_1_1 =tom_get_slot_Name_string(tom_match1_1_1_2_1); { String tomName =tom_match1_1_1_2_1_1; if(tom_is_empty_NameList(tom_match1_1_1_2_list1)) { { TomList termArgs =tom_match1_1_1_3;

 
              TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
              TomList newTermArgs = (TomList) traversal().genericTraversal(termArgs,replace_preProcessing_makeTerm);
              if(tomSymbol==null || isDefinedSymbol(tomSymbol)) {
                return tom_make_FunctionCall(name,newTermArgs) ;
              } else {
                if(isListOperator(tomSymbol)) {
                  return tom_make_BuildList(name,newTermArgs) ;
                } else if(isArrayOperator(tomSymbol)) {
                  return tom_make_BuildArray(name,newTermArgs) ;
                } else {
                  return tom_make_BuildTerm(name,newTermArgs) ;
                }
              }
            } }}}} }}} }} }}}}} }} }}}

  // end match
        } else if(subject instanceof Instruction) {
           { Instruction tom_match2_1 =(( Instruction)subject);{ if(tom_is_fun_sym_Match(tom_match2_1) ||  false ) { { TomTerm tom_match2_1_1 =tom_get_slot_Match_subjectList(tom_match2_1); { TomTerm tom_match2_1_2 =tom_get_slot_Match_patternList(tom_match2_1); { OptionList tom_match2_1_3 =tom_get_slot_Match_option(tom_match2_1); if(tom_is_fun_sym_SubjectList(tom_match2_1_1) ||  false ) { { TomList tom_match2_1_1_1 =tom_get_slot_SubjectList_tomList(tom_match2_1_1); { TomList l1 =tom_match2_1_1_1; if(tom_is_fun_sym_PatternList(tom_match2_1_2) ||  false ) { { TomList tom_match2_1_2_1 =tom_get_slot_PatternList_tomList(tom_match2_1_2); { TomList l2 =tom_match2_1_2_1; { OptionList matchOptionList =tom_match2_1_3;
 
              Option orgTrack = findOriginTracking(matchOptionList);
              if(debugMode) {
                debugKey = orgTrack.getFileName().getString() + orgTrack.getLine();
              }
              TomList newPatternList = empty();
              while(!l2.isEmpty()) {
                  /*
                   * the call to preProcessing performs the recursive expansion
                   * of nested match constructs
                   */
                TomTerm elt = preProcessing(l2.getHead());
                TomTerm newPatternAction = elt;
              
                matchBlock: {
                   { TomTerm tom_match3_1 =(( TomTerm)elt);{ if(tom_is_fun_sym_PatternAction(tom_match3_1) ||  false ) { { TomTerm tom_match3_1_1 =tom_get_slot_PatternAction_termList(tom_match3_1); { Instruction tom_match3_1_2 =tom_get_slot_PatternAction_action(tom_match3_1); { OptionList tom_match3_1_3 =tom_get_slot_PatternAction_option(tom_match3_1); if(tom_is_fun_sym_TermList(tom_match3_1_1) ||  false ) { { TomList tom_match3_1_1_1 =tom_get_slot_TermList_tomList(tom_match3_1_1); { TomList termList =tom_match3_1_1_1; { Instruction actionInst =tom_match3_1_2; { OptionList option =tom_match3_1_3;
 
                      TomList newTermList = empty();
                      Instruction newActionInst = actionInst;
                        /* generate equality checks */
                      ArrayList equalityCheck = new ArrayList();
                      TomList renamedTermList = linearizePattern(termList,equalityCheck);
											newPatternAction = tom_make_PatternAction(tom_make_TermList(renamedTermList),actionInst,option) ;        
                    
                        /* abstract patterns */
                      ArrayList abstractedPattern  = new ArrayList();
                      ArrayList introducedVariable = new ArrayList();
                      newTermList = abstractPatternList(renamedTermList, abstractedPattern, introducedVariable);

                      if(abstractedPattern.size() > 0) {
                          /* generate a new match construct */
                      
                        TomTerm generatedPatternAction =
                          tom_make_PatternAction(tom_make_TermList(ast() .makeList(abstractedPattern)),newActionInst,tom_make_empty_concOption()) ;        
                          /* We reconstruct only a list of option with orgTrack and GeneratedMatch*/
                        OptionList generatedMatchOptionList = tom_make_insert_concOption(orgTrack,tom_make_insert_concOption(tom_make_GeneratedMatch(),tom_make_empty_concOption())) ;
                        Instruction generatedMatch =
                          tom_make_Match(tom_make_SubjectList(ast() .makeList(introducedVariable)),tom_make_PatternList(cons(generatedPatternAction,empty())),generatedMatchOptionList)

 ;
                          /*System.out.println("Generate new Match"+generatedMatch); */
                        generatedMatch = preProcessingInstruction(generatedMatch);
                        newPatternAction =
                          tom_make_PatternAction(tom_make_TermList(newTermList),generatedMatch,option) ;
                      
                          /*System.out.println("newPatternAction = " + newPatternAction); */
                      }
                        /* do nothing */
                      break matchBlock;
                    }}}} }}}} }

 
                      System.out.println("preProcessing: strange PatternAction: " + elt);
                        //System.out.println("termList = " + elt.getTermList());
                        //System.out.println("tom      = " + elt.getTom()); 
                      throw new TomRuntimeException(new Throwable("preProcessing: strange PatternAction: " + elt));
                    }}
 
                } // end matchBlock
              
                newPatternList = append(newPatternAction,newPatternList);
                l2 = l2.getTail();
              }
            
              Instruction newMatch = tom_make_Match(tom_make_SubjectList(l1),tom_make_PatternList(newPatternList),matchOptionList)

 ;
              return newMatch;
            }}} }}} }}}} } if(tom_is_fun_sym_RuleSet(tom_match2_1) ||  false ) { { TomRuleList tom_match2_1_1 =tom_get_slot_RuleSet_ruleList(tom_match2_1); { Option tom_match2_1_2 =tom_get_slot_RuleSet_orgTrack(tom_match2_1); if(tom_is_fun_sym_manyTomRuleList(tom_match2_1_1) ||  false ) { { TomRuleList ruleList =tom_match2_1_1; { TomRule tom_match2_1_1_1 =tom_get_slot_manyTomRuleList_head(tom_match2_1_1); { TomRuleList tom_match2_1_1_2 =tom_get_slot_manyTomRuleList_tail(tom_match2_1_1); if(tom_is_fun_sym_RewriteRule(tom_match2_1_1_1) ||  false ) { { TomTerm tom_match2_1_1_1_1 =tom_get_slot_RewriteRule_lhs(tom_match2_1_1_1); if(tom_is_fun_sym_Term(tom_match2_1_1_1_1) ||  false ) { { TomTerm tom_match2_1_1_1_1_1 =tom_get_slot_Term_tomTerm(tom_match2_1_1_1_1); if(tom_is_fun_sym_Appl(tom_match2_1_1_1_1_1) ||  false ) { { NameList tom_match2_1_1_1_1_1_2 =tom_get_slot_Appl_nameList(tom_match2_1_1_1_1_1); if(tom_is_fun_sym_concTomName(tom_match2_1_1_1_1_1_2) ||  false ) { { NameList tom_match2_1_1_1_1_1_2_list1 =tom_match2_1_1_1_1_1_2; if(!(tom_is_empty_NameList(tom_match2_1_1_1_1_1_2_list1))) { { TomName tom_match2_1_1_1_1_1_2_1 =(( TomName)tom_get_head_NameList(tom_match2_1_1_1_1_1_2_list1));{ tom_match2_1_1_1_1_1_2_list1=tom_get_tail_NameList(tom_match2_1_1_1_1_1_2_list1); if(tom_is_fun_sym_Name(tom_match2_1_1_1_1_1_2_1) ||  false ) { { String tom_match2_1_1_1_1_1_2_1_1 =tom_get_slot_Name_string(tom_match2_1_1_1_1_1_2_1); { String tomName =tom_match2_1_1_1_1_1_2_1_1; if(tom_is_empty_NameList(tom_match2_1_1_1_1_1_2_list1)) { { TomRuleList tail =tom_match2_1_1_2; { Option orgTrack =tom_match2_1_2;


 
              if(debugMode) {
                debugKey = orgTrack.getFileName().getString() + orgTrack.getLine();
              }
              TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
              TomName name = tomSymbol.getAstName();
              TomTypeList typesList = tomSymbol.getTypesToType().getDomain();        
              TomNumberList path = tsf().makeTomNumberList();
              TomList matchArgumentsList = empty();
              TomList patternActionList  = empty();
              TomTerm variable;
              int index = 0;
            
              path = (TomNumberList) path.append(tom_make_RuleVar() );
            
              while(!typesList.isEmpty()) {
                TomType subtermType = typesList.getHead();
                variable = tom_make_Variable(option(),tom_make_PositionName(appendNumber(index,path)),subtermType,tom_make_empty_concConstraint()) ;
                matchArgumentsList = append(variable,matchArgumentsList);
                typesList = typesList.getTail();
                index++;
              }
            
              while(!ruleList.isEmpty()) {
                TomRule rule = ruleList.getHead();
                 { TomRule tom_match4_1 =(( TomRule)rule);{ if(tom_is_fun_sym_RewriteRule(tom_match4_1) ||  false ) { { TomTerm tom_match4_1_1 =tom_get_slot_RewriteRule_lhs(tom_match4_1); { TomTerm tom_match4_1_2 =tom_get_slot_RewriteRule_rhs(tom_match4_1); { InstructionList tom_match4_1_3 =tom_get_slot_RewriteRule_condList(tom_match4_1); { OptionList tom_match4_1_4 =tom_get_slot_RewriteRule_option(tom_match4_1); if(tom_is_fun_sym_Term(tom_match4_1_1) ||  false ) { { TomTerm tom_match4_1_1_1 =tom_get_slot_Term_tomTerm(tom_match4_1_1); if(tom_is_fun_sym_Appl(tom_match4_1_1_1) ||  false ) { { TomList tom_match4_1_1_1_3 =tom_get_slot_Appl_args(tom_match4_1_1_1); { TomList matchPatternsList =tom_match4_1_1_1_3; if(tom_is_fun_sym_Term(tom_match4_1_2) ||  false ) { { TomTerm tom_match4_1_2_1 =tom_get_slot_Term_tomTerm(tom_match4_1_2); { TomTerm rhsTerm =tom_match4_1_2_1; { InstructionList condList =tom_match4_1_3; { OptionList option =tom_match4_1_4;



 
                  
                    TomTerm newRhs = preProcessing(tom_make_MakeTerm(rhsTerm) );
                    Instruction rhsInst = tom_make_Return(newRhs) ;
                    if(debugMode) {
                      TargetLanguage tl = tsf().makeTargetLanguage_ITL("jtom.debug.TomDebugger.debugger.patternSuccess(\""+debugKey+"\");\n");
                      rhsInst = tom_make_UnamedBlock(tom_make_insert_concInstruction(tom_make_TargetLanguageToInstruction(tl),tom_make_insert_concInstruction(rhsInst,tom_make_empty_concInstruction()))) ;
                    }
                    Instruction newRhsInst = buildCondition(condList,rhsInst);
                  
                    patternActionList = append(tom_make_PatternAction(tom_make_TermList(matchPatternsList),newRhsInst,option) ,patternActionList);
                  }}}} }}} }} }}}}} }}}
  
                ruleList = ruleList.getTail();
              }
            
              TomTerm subjectListAST = tom_make_SubjectList(matchArgumentsList) ;
              Instruction makeFunctionBeginAST = tom_make_MakeFunctionBegin(name,subjectListAST) ;
              ArrayList optionList = new ArrayList();
              optionList.add(orgTrack);
                //optionList.add(tsf().makeOption_GeneratedMatch());
              OptionList generatedOptions = ast().makeOptionList(optionList);
              Instruction matchAST = tom_make_Match(tom_make_SubjectList(matchArgumentsList),tom_make_PatternList(patternActionList),generatedOptions)

 ;
              Instruction buildAST = tom_make_Return(tom_make_BuildTerm(name, (TomList )traversal() .genericTraversal(matchArgumentsList,replace_preProcessing_makeTerm))) ;
              InstructionList l;
              if(eCode) {
                l = tom_make_insert_concInstruction(makeFunctionBeginAST,tom_make_insert_concInstruction(tom_make_LocalVariable(),tom_make_insert_concInstruction(tom_make_EndLocalVariable(),tom_make_insert_concInstruction(matchAST,tom_make_insert_concInstruction(buildAST,tom_make_insert_concInstruction(tom_make_MakeFunctionEnd(),tom_make_empty_concInstruction()))))))






 ;
              } else {
                l = tom_make_insert_concInstruction(makeFunctionBeginAST,tom_make_insert_concInstruction(matchAST,tom_make_insert_concInstruction(buildAST,tom_make_insert_concInstruction(tom_make_MakeFunctionEnd(),tom_make_empty_concInstruction()))))




 ;
              }
            
              return preProcessingInstruction(tom_make_AbstractBlock(l) );
            }} }}} }}} }} }} }} }} }}}} }}} }}}

  // end match

        } // end instanceof Instruction

          /*
           * Defaul case: traversal
           */
        return traversal().genericTraversal(subject,this);
      } // end apply
    };
  
  Replace1 replace_preProcessing_makeTerm = new Replace1() {
      public ATerm apply(ATerm t) {
        return preProcessing(tom_make_MakeTerm( (TomTerm )t) );
      }
    }; 

  private TomTerm preProcessing(TomTerm subject) {
      //System.out.println("preProcessing subject: " + subject);
    return (TomTerm) replace_preProcessing.apply(subject); 
  }
  
  private Instruction preProcessingInstruction(Instruction subject) {
      //System.out.println("preProcessing subject: " + subject);
    return (Instruction) replace_preProcessing.apply(subject); 
  }
 
  private Instruction buildCondition(InstructionList condList, Instruction action) {
     { InstructionList tom_match5_1 =(( InstructionList)condList);{ if(tom_is_fun_sym_emptyInstructionList(tom_match5_1) ||  false ) {
  return action;  } if(tom_is_fun_sym_manyInstructionList(tom_match5_1) ||  false ) { { Instruction tom_match5_1_1 =tom_get_slot_manyInstructionList_head(tom_match5_1); { InstructionList tom_match5_1_2 =tom_get_slot_manyInstructionList_tail(tom_match5_1); if(tom_is_fun_sym_MatchingCondition(tom_match5_1_1) ||  false ) { { TomTerm tom_match5_1_1_1 =tom_get_slot_MatchingCondition_lhs(tom_match5_1_1); { TomTerm tom_match5_1_1_2 =tom_get_slot_MatchingCondition_rhs(tom_match5_1_1); { TomTerm pattern =tom_match5_1_1_1; { TomTerm subject =tom_match5_1_1_2; { InstructionList tail =tom_match5_1_2;

 
        Instruction newAction = buildCondition(tail,action);

        TomType subjectType = getTermType(pattern);
        TomNumberList path = tsf().makeTomNumberList();
        path = (TomNumberList) path.append(tom_make_RuleVar() );
        TomTerm newSubject = preProcessing(tom_make_MakeTerm(subject) );
        TomTerm introducedVariable = newSubject;
        TomTerm generatedPatternAction =
          tom_make_PatternAction(tom_make_TermList(cons(pattern,empty())),newAction,option()) ;        

          // Warning: The options are not good
        Instruction generatedMatch =
          tom_make_Match(tom_make_SubjectList(cons(introducedVariable,empty())),tom_make_PatternList(cons(generatedPatternAction,empty())),option())

 ;
        return generatedMatch;
      }}}}} }}} } if(tom_is_fun_sym_manyInstructionList(tom_match5_1) ||  false ) { { Instruction tom_match5_1_1 =tom_get_slot_manyInstructionList_head(tom_match5_1); { InstructionList tom_match5_1_2 =tom_get_slot_manyInstructionList_tail(tom_match5_1); if(tom_is_fun_sym_EqualityCondition(tom_match5_1_1) ||  false ) { { TomTerm tom_match5_1_1_1 =tom_get_slot_EqualityCondition_lhs(tom_match5_1_1); { TomTerm tom_match5_1_1_2 =tom_get_slot_EqualityCondition_rhs(tom_match5_1_1); { TomTerm lhs =tom_match5_1_1_1; { TomTerm rhs =tom_match5_1_1_2; { InstructionList tail =tom_match5_1_2;

 
        Instruction newAction = buildCondition(tail,action);

        TomTerm newLhs = preProcessing(tom_make_MakeTerm(lhs) );
        TomTerm newRhs = preProcessing(tom_make_MakeTerm(rhs) );
        Expression equality = tom_make_EqualTerm(newLhs,newRhs) ;
        Instruction generatedTest = tom_make_IfThenElse(equality,newAction,tom_make_Nop()) ;
        return generatedTest;
      }}}}} }}} }

 
        System.out.println("buildCondition strange term: " + condList);
        throw new TomRuntimeException(new Throwable("buildCondition strange term: " + condList));
      }}
 
  }
  
  private TomTerm renameVariable(TomTerm subject,
                                 Map multiplicityMap,
                                 Map maxmultiplicityMap,
                                 ArrayList equalityCheck) {
    TomTerm renamedTerm = subject;
    
     { TomTerm tom_match6_1 =(( TomTerm)subject);{ if(tom_is_fun_sym_UnamedVariable(tom_match6_1) ||  false ) { { OptionList tom_match6_1_1 =tom_get_slot_UnamedVariable_option(tom_match6_1); { TomType tom_match6_1_2 =tom_get_slot_UnamedVariable_astType(tom_match6_1); { OptionList optionList =tom_match6_1_1; { TomType type =tom_match6_1_2;
 
        OptionList newOptionList = renameVariableInOptionList(optionList,multiplicityMap,maxmultiplicityMap,equalityCheck);
        return tom_make_UnamedVariable(newOptionList,type) ;
      }}}} } if(tom_is_fun_sym_UnamedVariableStar(tom_match6_1) ||  false ) { { OptionList tom_match6_1_1 =tom_get_slot_UnamedVariableStar_option(tom_match6_1); { TomType tom_match6_1_2 =tom_get_slot_UnamedVariableStar_astType(tom_match6_1); { OptionList optionList =tom_match6_1_1; { TomType type =tom_match6_1_2;

 
        OptionList newOptionList = renameVariableInOptionList(optionList,multiplicityMap,maxmultiplicityMap,equalityCheck);
        return tom_make_UnamedVariableStar(newOptionList,type) ;
      }}}} } if(tom_is_fun_sym_Variable(tom_match6_1) ||  false ) { { OptionList tom_match6_1_1 =tom_get_slot_Variable_option(tom_match6_1); { TomName tom_match6_1_2 =tom_get_slot_Variable_astName(tom_match6_1); { TomType tom_match6_1_3 =tom_get_slot_Variable_astType(tom_match6_1); { ConstraintList tom_match6_1_4 =tom_get_slot_Variable_constraints(tom_match6_1); { OptionList optionList =tom_match6_1_1; { TomName name =tom_match6_1_2; { TomType type =tom_match6_1_3; { ConstraintList clist =tom_match6_1_4;

 
				Integer multiplicity = (Integer) multiplicityMap.get(name);
				int mult = multiplicity.intValue();
				Integer maxmultiplicity = (Integer) maxmultiplicityMap.get(name);
				int maxmult = maxmultiplicity.intValue();
				if(mult > 1) {
					mult = mult-1;
					multiplicityMap.put(name,new Integer(mult));

					TomNumberList path = tsf().makeTomNumberList();
					path = (TomNumberList) path.append(tom_make_RenamedVar(name) );
					path = (TomNumberList) path.append(makeNumber(mult));
					OptionList newOptionList = renameVariableInOptionList(optionList,multiplicityMap,maxmultiplicityMap,equalityCheck);
					if (mult < maxmult -1) {
						// add the constraint renamedVariable = renamedVariable+1
						TomTerm oldVar;
						TomNumberList oldpath = tsf().makeTomNumberList();
						oldpath = (TomNumberList) oldpath.append(tom_make_RenamedVar(name) );
						oldpath = (TomNumberList) oldpath.append(makeNumber(mult + 1));
						oldVar = tom_make_Variable(optionList,tom_make_PositionName(oldpath),type,tom_make_empty_concConstraint()) ;
						renamedTerm = tom_make_Variable(newOptionList,tom_make_PositionName(path),type,tom_make_insert_concConstraint(tom_make_Equal(oldVar),tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint()))) ;
					} else {
						// No constraint for the first renamed variable
						renamedTerm = tom_make_Variable(newOptionList,tom_make_PositionName(path),type,tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint())) ;
					}
					// System.out.println("renamedTerm = " + renamedTerm);

					Expression newEquality = tom_make_EqualTerm(subject,renamedTerm) ;
					equalityCheck.add(newEquality);
				}
				else if (maxmult > 1) {
					TomNumberList oldpath = tsf().makeTomNumberList();
					oldpath = (TomNumberList) oldpath.append(tom_make_RenamedVar(name) );
					oldpath = (TomNumberList) oldpath.append(makeNumber(mult));
					TomTerm oldVar = tom_make_Variable(optionList,tom_make_PositionName(oldpath),type,tom_make_empty_concConstraint()) ;
					renamedTerm = tom_make_Variable(optionList,name,type,tom_make_insert_concConstraint(tom_make_Equal(oldVar),tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint()))) ;
				}
				return renamedTerm;
			}}}}}}}} } if(tom_is_fun_sym_VariableStar(tom_match6_1) ||  false ) { { OptionList tom_match6_1_1 =tom_get_slot_VariableStar_option(tom_match6_1); { TomName tom_match6_1_2 =tom_get_slot_VariableStar_astName(tom_match6_1); { TomType tom_match6_1_3 =tom_get_slot_VariableStar_astType(tom_match6_1); { ConstraintList tom_match6_1_4 =tom_get_slot_VariableStar_constraints(tom_match6_1); { OptionList optionList =tom_match6_1_1; { TomName name =tom_match6_1_2; { TomType type =tom_match6_1_3; { ConstraintList clist =tom_match6_1_4;

 
				Integer multiplicity = (Integer)multiplicityMap.get(name);
				int mult = multiplicity.intValue();
				Integer maxmultiplicity = (Integer) maxmultiplicityMap.get(name);
				int maxmult = maxmultiplicity.intValue();
				if(mult > 1) {
					mult = mult-1;
					multiplicityMap.put(name,new Integer(mult));

					TomNumberList path = tsf().makeTomNumberList();
					path = (TomNumberList) path.append(tom_make_RenamedVar(name) );
					path = appendNumber(mult,path);
					OptionList newOptionList = renameVariableInOptionList(optionList,multiplicityMap,maxmultiplicityMap,equalityCheck);
					if (mult < maxmult -1) {
						// add the constraint renamedVariable = renamedVariable+1
						TomTerm oldVar;
						TomNumberList oldpath = tsf().makeTomNumberList();
						oldpath = (TomNumberList) oldpath.append(tom_make_RenamedVar(name) );
						oldpath = (TomNumberList) oldpath.append(makeNumber(mult + 1));
						oldVar = tom_make_VariableStar(optionList,tom_make_PositionName(oldpath),type,tom_make_empty_concConstraint()) ;
						renamedTerm = tom_make_VariableStar(newOptionList,tom_make_PositionName(path),type,tom_make_insert_concConstraint(tom_make_Equal(oldVar),tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint()))) ;
					} else {
						// No constraint for the first renamed variable
						renamedTerm = tom_make_VariableStar(newOptionList,tom_make_PositionName(path),type,tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint())) ;
					}
					//System.out.println("renamedTerm = " + renamedTerm);

					Expression newEquality = tom_make_EqualTerm(subject,renamedTerm) ;
					equalityCheck.add(newEquality);
				} 
				else if (maxmult > 1) {
					TomNumberList oldpath = tsf().makeTomNumberList();
					oldpath = (TomNumberList) oldpath.append(tom_make_RenamedVar(name) );
					oldpath = (TomNumberList) oldpath.append(makeNumber(mult));
					TomTerm oldVar = tom_make_VariableStar(optionList,tom_make_PositionName(oldpath),type,tom_make_empty_concConstraint()) ;
					renamedTerm =tom_make_VariableStar(optionList,name,type,tom_make_insert_concConstraint(tom_make_Equal(oldVar),tom_insert_list_concConstraint(clist,tom_make_empty_concConstraint()))) ;
				}
				return renamedTerm;
			}}}}}}}} } if(tom_is_fun_sym_Appl(tom_match6_1) ||  false ) { { OptionList tom_match6_1_1 =tom_get_slot_Appl_option(tom_match6_1); { NameList tom_match6_1_2 =tom_get_slot_Appl_nameList(tom_match6_1); { TomList tom_match6_1_3 =tom_get_slot_Appl_args(tom_match6_1); { OptionList optionList =tom_match6_1_1; { NameList nameList =tom_match6_1_2; { TomList args =tom_match6_1_3;

 
        TomList newArgs = empty();
        while(!args.isEmpty()) {
          TomTerm elt = args.getHead();
          TomTerm newElt = renameVariable(elt,multiplicityMap,maxmultiplicityMap,equalityCheck);
          newArgs = append(newElt,newArgs);
          args = args.getTail();
        }
        OptionList newOptionList = renameVariableInOptionList(optionList,multiplicityMap,maxmultiplicityMap,equalityCheck);
        renamedTerm = tom_make_Appl(newOptionList,nameList,newArgs) ;
        return renamedTerm;
      }}}}}} }}}
 
    return renamedTerm;
  }

  private OptionList renameVariableInOptionList(OptionList optionList,
                                                Map multiplicityMap,
                                                Map maxmultiplicityMap,
                                                ArrayList equalityCheck) {
    ArrayList list = new ArrayList();
    while(!optionList.isEmpty()) {
      Option optElt = optionList.getHead();
      Option newOptElt = optElt;
       { Option tom_match7_1 =(( Option)optElt);{ if(tom_is_fun_sym_TomTermToOption(tom_match7_1) ||  false ) { { TomTerm tom_match7_1_1 =tom_get_slot_TomTermToOption_astTerm(tom_match7_1); if(tom_is_fun_sym_Variable(tom_match7_1_1) ||  false ) { { TomTerm var =tom_match7_1_1;
 
          newOptElt = tom_make_TomTermToOption(renameVariable(var,multiplicityMap,maxmultiplicityMap,equalityCheck)) ;
        } }} }}}
 
      list.add(newOptElt);
      optionList = optionList.getTail();
    }
    return ast().makeOptionList(list);
  }

  
  private TomList linearizePattern(TomList subject, ArrayList equalityCheck) {
    Map multiplicityMap = collectMultiplicity(subject);
    Map maxmultiplicityMap = new HashMap();
		// maxMultMap must be a deep clone of multMap
		Iterator it = multiplicityMap.keySet().iterator();
		Integer value;
		while (it.hasNext()) {
			Object key = it.next();
			value = new Integer(((Integer)multiplicityMap.get(key)).intValue());
			maxmultiplicityMap.put(key,value);
		}
      // perform the renaming and generate equality checks
    TomList newList = empty();
    while(!subject.isEmpty()) {
      TomTerm elt = subject.getHead();
      TomTerm newElt = renameVariable(elt,multiplicityMap,maxmultiplicityMap,equalityCheck);
      newList = append(newElt,newList);
      subject = subject.getTail();
    }
    return newList;
  }
  
  private TomTerm abstractPattern(TomTerm subject,
                                  ArrayList abstractedPattern,
                                  ArrayList introducedVariable)  {
    TomTerm abstractedTerm = subject;
     { TomTerm tom_match8_1 =(( TomTerm)subject);{ if(tom_is_fun_sym_Appl(tom_match8_1) ||  false ) { { OptionList tom_match8_1_1 =tom_get_slot_Appl_option(tom_match8_1); { NameList tom_match8_1_2 =tom_get_slot_Appl_nameList(tom_match8_1); { TomList tom_match8_1_3 =tom_get_slot_Appl_args(tom_match8_1); { OptionList option =tom_match8_1_1; if(tom_is_fun_sym_concTomName(tom_match8_1_2) ||  false ) { { NameList nameList =tom_match8_1_2; { NameList tom_match8_1_2_list1 =tom_match8_1_2; if(!(tom_is_empty_NameList(tom_match8_1_2_list1))) { { TomName tom_match8_1_2_1 =(( TomName)tom_get_head_NameList(tom_match8_1_2_list1));{ tom_match8_1_2_list1=tom_get_tail_NameList(tom_match8_1_2_list1); if(tom_is_fun_sym_Name(tom_match8_1_2_1) ||  false ) { { String tom_match8_1_2_1_1 =tom_get_slot_Name_string(tom_match8_1_2_1); { String tomName =tom_match8_1_2_1_1; { TomList args =tom_match8_1_3;
 
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        
        TomList newArgs = empty();
        if(isListOperator(tomSymbol) || isArrayOperator(tomSymbol)) {
          while(!args.isEmpty()) {
            TomTerm elt = args.getHead();
            TomTerm newElt = elt;
             { TomTerm tom_match9_1 =(( TomTerm)elt);{ if(tom_is_fun_sym_Appl(tom_match9_1) ||  false ) { { TomTerm appl =tom_match9_1; { NameList tom_match9_1_2 =tom_get_slot_Appl_nameList(tom_match9_1); if(tom_is_fun_sym_concTomName(tom_match9_1_2) ||  false ) { { NameList tom_match9_1_2_list1 =tom_match9_1_2; if(!(tom_is_empty_NameList(tom_match9_1_2_list1))) { { TomName tom_match9_1_2_1 =(( TomName)tom_get_head_NameList(tom_match9_1_2_list1));{ tom_match9_1_2_list1=tom_get_tail_NameList(tom_match9_1_2_list1); if(tom_is_fun_sym_Name(tom_match9_1_2_1) ||  false ) { { String tom_match9_1_2_1_1 =tom_get_slot_Name_string(tom_match9_1_2_1); { String tomName2 =tom_match9_1_2_1_1;
 
                /*
                 * we no longer abstract syntactic subterm
                 * they are compiled by the TomKernelCompiler
                 */

                  //System.out.println("Abstract: " + appl);
                TomSymbol tomSymbol2 = symbolTable().getSymbol(tomName2);
                if(isListOperator(tomSymbol2) || isArrayOperator(tomSymbol2)) {
                  TomType type2 = tomSymbol2.getTypesToType().getCodomain();
                  abstractedPattern.add(appl);
                  
                  TomNumberList path = tsf().makeTomNumberList();
                  //path = append(`AbsVar(makeNumber(introducedVariable.size())),path);
                  absVarNumber++;
                  path = (TomNumberList) path.append(tom_make_AbsVar(makeNumber(absVarNumber)) );
                  
                  TomTerm newVariable = tom_make_Variable(option(),tom_make_PositionName(path),type2,tom_make_empty_concConstraint()) ;
                  
                  //System.out.println("newVariable = " + newVariable);
                  
                  introducedVariable.add(newVariable);
                  newElt = newVariable;
                }
              }} }}} }} }}} }}}
 
            newArgs = append(newElt,newArgs);
            args = args.getTail();
          }
        } else {
          newArgs = abstractPatternList(args,abstractedPattern,introducedVariable);
        }
        abstractedTerm = tom_make_Appl(option,nameList,newArgs) ;
      }}} }}} }}} }}}}} }}}
  // end match
    return abstractedTerm;
  }

  private TomList abstractPatternList(TomList subjectList,
                                      ArrayList abstractedPattern,
                                      ArrayList introducedVariable)  {
    TomList newList = empty();
    while(!subjectList.isEmpty()) {
      TomTerm elt = subjectList.getHead();
      TomTerm newElt = abstractPattern(elt,abstractedPattern,introducedVariable);
      newList = append(newElt,newList);
      subjectList = subjectList.getTail();
    }
    return newList;
  }
  
} //class TomCompiler
