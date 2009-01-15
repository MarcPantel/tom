/*
 *
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2008, INRIA
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
 * Radu Kopetz e-mail: Radu.Kopetz@loria.fr
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/
package tom.engine.compiler;

import tom.engine.tools.TomGenericPlugin;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomname.types.tomname.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.tomterm.*;
import tom.library.sl.*;
import tom.engine.tools.SymbolTable;
import tom.engine.exception.TomRuntimeException;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.TomBase;
import tom.engine.adt.tomconstraint.types.*;
import java.util.*;
import tom.engine.tools.ASTFactory;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import tom.platform.OptionParser;
import tom.engine.tools.Tools;
import java.util.logging.Level;
import tom.engine.TomMessage;

/**
 * Tom compiler based on constraints.
 * 
 * It controls different phases of compilation:
 * - propagation of constraints
 * - instruction generation from constraints
 * - ...   
 */
public class Compiler extends TomGenericPlugin {

  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../../library/mapping/java/sl.tom}
  %include { ../../library/mapping/java/util/types/ArrayList.tom}
  %include { ../../library/mapping/java/util/types/Collection.tom}

  %typeterm Compiler { implement { Compiler } }

  private static SymbolTable symbolTable = null;
  private static TomNumberList rootpath = null;
  // keeps track of the match number to insure distinct variables' 
  // names for distinct match constructs
  private static int matchNumber = 0;
  // keeps track of the subject number to insure distinct variables' 
  // names when renaming subjects
  private static int freshSubjectCounter = 0;	
  private static int freshVarCounter = 0;
  private static final String freshVarPrefix = "_freshVar_";
  private static final String freshBeginPrefix = "_begin_";
  private static final String freshEndPrefix = "_end_";

  /** some output suffixes */
  public static final String COMPILED_SUFFIX = ".tfix.compiled";

  /** the declared options string*/
  public static final String DECLARED_OPTIONS = "<options>" +
    "<boolean name='compile' altName='' description='Compiler (activated by default)' value='true'/>" +
    "</options>";

  /** Constructor */
  public Compiler() {
    super("Compiler");
  }

  public void run() {
    long startChrono = System.currentTimeMillis();
    boolean intermediate = getOptionBooleanValue("intermediate");    
    try {
      TomTerm compiledTerm = Compiler.compile((TomTerm)getWorkingTerm(),getStreamManager().getSymbolTable());
      //System.out.println("compiledTerm = \n" + compiledTerm);            
      Collection hashSet = new HashSet();
      TomTerm renamedTerm = (TomTerm) `TopDownIdStopOnSuccess(findRenameVariable(hashSet)).visitLight(compiledTerm);
      // add the aditional functions needed by the AC operators
      renamedTerm = addACFunctions(renamedTerm);      
      setWorkingTerm(renamedTerm);
      if(intermediate) {
        Tools.generateOutput(getStreamManager().getOutputFileName() + COMPILED_SUFFIX, renamedTerm);
      }
      getLogger().log(Level.INFO, TomMessage.tomCompilationPhase.getMessage(),
          Integer.valueOf((int)(System.currentTimeMillis()-startChrono)) );
    } catch (Exception e) {
      getLogger().log(Level.SEVERE, TomMessage.exceptionMessage.getMessage(),
          new Object[]{getStreamManager().getInputFileName(), "Compiler", e.getMessage()} );
      e.printStackTrace();
    }
  }

  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(Compiler.DECLARED_OPTIONS);
  }

  public static TomTerm compile(TomTerm termToCompile,SymbolTable symbolTable) throws VisitFailure {
    Compiler.symbolTable = symbolTable;
    // we use TopDown  and not TopDownIdStopOnSuccess to compile nested-match
    return (TomTerm) `TopDown(CompileMatch()).visitLight(termToCompile);		
  }

  // looks for a 'Match' instruction:
  // 1. transforms each sequence of patterns into a conjuction of MatchConstraint
  // 2. launch PropagationManager
  // 3. launch PreGenerator
  // 4. launch GenerationManager
  // 5. launch PostGenerator  
  // 6. transforms resulted expression into a CompiledMatch
  %strategy CompileMatch() extends Identity() {
    visit Instruction {			
      Match(constraintInstructionList, matchOptionList)  -> {        
        matchNumber++;
        rootpath = `concTomNumber(MatchNumber(matchNumber));
        freshSubjectCounter = 0;
        freshVarCounter = 0;
        int actionNumber = 0;
        TomList automataList = `concTomTerm();	
        ArrayList<TomTerm> subjectList = new ArrayList<TomTerm>();
        ArrayList<TomTerm> renamedSubjects = new ArrayList<TomTerm>();
        // for each pattern action <term>,...,<term> -> { action }
        // build a matching automata
        %match(constraintInstructionList) {
          concConstraintInstruction(_*,ConstraintInstruction(constraint,action,optionList),_*) -> {
            actionNumber++;
            try {
              // get the new names for subjects and generates casts -- needed especially for lists
              // this is performed here, and not above, because in the case of nested matches, we do not want 
              // to go in the action and collect from there              
              Constraint newConstraint = (Constraint) `TopDownIdStopOnSuccess(renameSubjects(subjectList,renamedSubjects)).visitLight(`constraint);

              Constraint propagationResult = ConstraintPropagator.performPropagations(newConstraint);
              Expression preGeneratedExpr = PreGenerator.performPreGenerationTreatment(propagationResult);
              Instruction matchingAutomata = ConstraintGenerator.performGenerations(preGeneratedExpr, `action);
              Instruction postGenerationAutomata = PostGenerator.performPostGenerationTreatment(matchingAutomata);
              TomNumberList numberList = `concTomNumber(rootpath*,PatternNumber(actionNumber));
              TomTerm automata = `Automata(optionList,newConstraint,numberList,postGenerationAutomata);
              automataList = `concTomTerm(automataList*,automata); //append(automata,automataList);
            } catch(Exception e) {
              e.printStackTrace();
              throw new TomRuntimeException("Propagation or generation exception:" + e);
            }																	    						
          }
        }// end %match				
        /*
         * return the compiled Match construction
         */        
        InstructionList astAutomataList = Compiler.automataListCompileMatchingList(automataList);
        // the block is useful in case we have a label on the %match: we would like it to be on the whole Match instruction 
        return `UnamedBlock(concInstruction(CompiledMatch(AbstractBlock(astAutomataList), matchOptionList)));
      }
    }// end visit
  }// end strategy  

  /**
   * Takes all MatchConstraints and renames the subjects;
   * (this ensures that the subject is not constructed more than once) 
   * Match(p,s) -> Match(object,s) /\ IsSort(object) /\ Match(freshSubj,Cast(object)) /\ Match(p,freshSubj) 
   * 
   * @param subjectList the list of old subjects
   */
  %strategy renameSubjects(ArrayList subjectList,ArrayList renamedSubjects) extends Identity() {
    visit Constraint {
      constr@MatchConstraint[Pattern=pattern, Subject=subject] -> {
        if(renamedSubjects.contains(`pattern) || renamedSubjects.contains(`subject) ) {
          // make sure we don't process generated contraints
          return `constr; 
        }        
        // test if we already renamed this subject 
        if(subjectList.contains(`subject)) {
          TomTerm renamedSubj = (TomTerm) renamedSubjects.get(subjectList.indexOf(`subject));
          Constraint newConstraint = `constr.setSubject(renamedSubj);
          TomType freshSubjectType = ((Variable)renamedSubj).getAstType();
          TomTerm freshVar = getUniversalObjectForSubject(freshSubjectType);
          return `AndConstraint(
              MatchConstraint(freshVar,subject),
              IsSortConstraint(freshSubjectType,freshVar),
              MatchConstraint(renamedSubj,ExpressionToTomTerm(Cast(freshSubjectType,TomTermToExpression(freshVar)))),
              newConstraint);
        }
        TomName freshSubjectName  = `PositionName(concTomNumber(rootpath*,NameNumber(Name("freshSubject_" + (++freshSubjectCounter)))));
        TomType freshSubjectType = `EmptyType();
        %match(subject) {
          (Variable|VariableStar)[AstType=variableType] -> { 
            freshSubjectType = `variableType;
          }          
          sv@(BuildTerm|FunctionCall|BuildConstant|BuildEmptyList|BuildConsList|BuildAppendList|BuildEmptyArray|BuildConsArray|BuildAppendArray)[AstName=Name(tomName)] -> {
            TomSymbol tomSymbol = symbolTable.getSymbolFromName(`tomName);                      
            if(tomSymbol != null) {
              freshSubjectType = TomBase.getSymbolCodomain(tomSymbol);
            } else if(`sv.isFunctionCall()) {
              freshSubjectType =`sv.getAstType();
            }
          }
        }
        TomTerm renamedVar = `Variable(concOption(),freshSubjectName,freshSubjectType,concConstraint());
        subjectList.add(`subject);
        renamedSubjects.add(renamedVar);
        Constraint newConstraint = `constr.setSubject(renamedVar);   
        TomTerm freshVar = getUniversalObjectForSubject(freshSubjectType);
        return `AndConstraint(
            MatchConstraint(freshVar,subject),
            IsSortConstraint(freshSubjectType,freshVar),
            MatchConstraint(renamedVar,ExpressionToTomTerm(Cast(freshSubjectType,TomTermToExpression(freshVar)))),
            newConstraint);
      }
    }
  }
  private static TomTerm getUniversalObjectForSubject(TomType subjectType){    
    if (symbolTable.isBuiltinType(TomBase.getTomType(subjectType))) {
      return getFreshVariable(subjectType);
    } else {
      return getFreshVariable(symbolTable.getUniversalType());
    }
  }

  /**
   * builds a list of instructions from a list of automata
   */
  private static InstructionList automataListCompileMatchingList(TomList automataList) {
    %match(automataList) {
      concTomTerm() -> { return `concInstruction(); }
      concTomTerm(Automata(optionList,constraint,_,instruction),l*)  -> {
        InstructionList newList = automataListCompileMatchingList(`l);				
        // if a label is assigned to a pattern (label:pattern ->
        // action) we generate corresponding labeled-block				 
        %match(optionList) {
          concOption(_*,Label(Name(name)),_*) -> {            
            return `concInstruction(CompiledPattern(constraint,NamedBlock(name,concInstruction(instruction))), newList*);
          }
        }
        return `concInstruction(CompiledPattern(constraint,instruction), newList*);
      }
    }
    return null;
  }

  /******************************************************************************/
  
  /**
   * AC methods
   */
  
  /**
   * Adds the necessary functions to the ADT of the program
   * 
   * @param subject the AST of the program
   */
  private static TomTerm addACFunctions(TomTerm subject) throws VisitFailure {
    // we use the symbol table as all AC the operators were marked as
    // used when the loop was generated
    SymbolTable symbolTable = Compiler.getSymbolTable();
    TomList l = `concTomTerm();
    Iterator<String> it = symbolTable.keySymbolIterator();
    while(it.hasNext()){
      String op = it.next();
      TomSymbol opSymbol = symbolTable.getSymbolFromName(op);
      if(TomBase.isACOperator(opSymbol)
          && symbolTable.isUsedSymbolConstructor(op)) {
        // gen all
        TomType opType = opSymbol.getTypesToType().getCodomain();        
        // 1. computeLenght
        l = `concTomTerm(DeclarationToTomTerm(getPILforComputeLength(op,opType)),l*);
        // 2. getMultiplicities
        l = `concTomTerm(DeclarationToTomTerm(getPILforGetMultiplicities(op,opType)),l*);
        // 3. getTerm        
        l = `concTomTerm(DeclarationToTomTerm(getPILforGetTermForMultiplicity(op,opType)),l*);
      }
    }
    // make sure the variables are correctly defined
    l = PostGenerator.changeVarDeclarations(`l);
    subject = (TomTerm)`OnceTopDownId(InsertDeclarations(l)).visitLight(subject);          
    return subject;
  }
  
  %strategy InsertDeclarations(TomList l) extends Identity() {
    visit TomList {
      concTomTerm(X*,d@DeclarationToTomTerm[],Y*) -> {        
        %match(l) {
          concTomTerm(Z*) -> { return `concTomTerm(X*,Z*,d,Y*); }
        }         
      }
    }
  }
  
  /**
   *    // Generates the PIL for the following function (used by the AC algorithm)
   * 
   *     private int[] getMultiplicities(Term subj) {
   *       int length = computeLenght(subj);
   *       int[] mult = new int[length];
   *       Term oldElem = null;
   *       // if we realy have a list
   *       // TODO: is this really necessary ?
   *       if (subj.isConsf()) {      
   *         oldElem = subj.getHeadf();      
   *       } else {      
   *         mult[0] = 1;
   *         return mult;      
   *       }
   *       int counter = 0;  
   *       // = subj.length;
   *       while(subj.isConsf()) {
   *         Term elem = subj.getHeadf();        
   *         // another element of this type
   *         if (elem.equals(oldElem)){
   *           mult[counter] += 1; 
   *         } else {
   *           counter++;
   *           oldElem = elem;
   *           mult[counter] = 1;
   *         }
   *         subj = subj.getTailf();
   *         // if we got to the end of the list
   *         if(!subj.isConsf()) {
   *           if (subj.equals(oldElem)){
   *             mult[counter] += 1; 
   *           } else {
   *             counter++;          
   *             mult[counter] = 1;
   *           }
   *           // break; // break the while
   *         } 
   *       }
   *       return mult;
   *     }
   */
  private static Declaration getPILforGetMultiplicities(String opNameString, TomType opType) {
    TomType intType = Compiler.getIntType();
    SymbolTable symbolTable = Compiler.getSymbolTable();
    TomType intArrayType = symbolTable.getIntArrayType();
    // the name of the int[] operator
    TomName intArrayName = `Name(symbolTable.getIntArrayOp());    
    
    TomTerm subject = `Variable(concOption(),Name("subject"),opType,concConstraint());
    TomTerm length = Compiler.getFreshVariable("length",intType);
    TomTerm mult = Compiler.getFreshVariable("mult",intArrayType);
    TomTerm oldElem = `Variable(concOption(),Name("oldElem"),opType,concConstraint());
    
    TomName opName = `Name(opNameString);
    Instruction ifList = `If(IsFsym(opName,subject),
        LetRef(oldElem,GetHead(opName,opType,subject),Nop()),        
        LetAssignArray(mult,ExpressionToTomTerm(Integer(0)),Integer(1), Return(mult)));
    
    // the two ifs
    TomTerm elem = `Variable(concOption(),Name("elem"),opType,concConstraint());
    TomTerm counter = `Variable(concOption(),Name("counter"),Compiler.getIntType(),concConstraint());
    Instruction ifAnotherElem = `If(EqualTerm(opType, elem, oldElem),
        LetAssignArray(mult,counter,AddOne(ExpressionToTomTerm(GetElement(intArrayName,intType,mult,counter))),Nop()),
        LetRef(counter,AddOne(counter),LetRef(oldElem,TomTermToExpression(elem),LetAssignArray(mult, counter, Integer(1),Nop()))));
    Instruction ifEndList = `If(Negation(IsFsym(opName,subject)),
        If(EqualTerm(opType, subject, oldElem),
            LetAssignArray(mult,counter,AddOne(ExpressionToTomTerm(GetElement(intArrayName,intType,mult,counter))),Nop()),
            LetRef(counter,AddOne(counter),LetAssignArray(mult,counter,Integer(1),Nop()))),
      Nop());
    
    Instruction whileBlock = `UnamedBlock(concInstruction(
        LetRef(elem,GetHead(opName,opType,subject),ifAnotherElem),
        LetAssign(subject,GetTail(opName,subject),ifEndList))); // subject is the method's argument     
    Instruction whileLoop = `WhileDo(IsFsym(opName,subject),whileBlock);
         
    // var declarations + ifList + counter declaration + the while + return
    Instruction functionBody = `LetRef(length, TomTermToExpression(FunctionCall(
        Name(ConstraintGenerator.computeLengthFuncName + "_" + opNameString),
        intType,concTomTerm(subject))),
        LetRef(mult,TomTermToExpression(BuildEmptyArray(intArrayName,length)),
            LetRef(oldElem,Bottom(opType),
                UnamedBlock(concInstruction(
                    ifList,
                    LetRef(counter,Integer(0),whileLoop),
                    Return(mult))))));
    
    return `MethodDef(Name(ConstraintGenerator.multiplicityFuncName+"_"+opNameString),
        concTomTerm(subject),intArrayType,EmptyType(),functionBody);
  }
  
  /**
   * // Generates the PIL for the following function (used by the AC algorithm)
   * 
   * private int computeLength(Term subj) {
   *  // a single element
   *  if(!subj.isConsf()) {
   *    return 1;
   *  }
   *  Term old = null;
   *  int counter = 0;
   *  while(subj.isConsf()) {
   *    Term elem = subj.getHeadf();
   *    // a new element
   *    if (!elem.equals(old)){
   *      counter++;
   *      old = elem;
   *    } 
   *    subj = subj.getTailf();
   *    // if we got to the end of the list
   *    if(!subj.isConsf()) {
   *      if (!subj.equals(old)) { counter++; }
   *      // break; // break the while - the while stops due to its condition
   *    } 
   *  }     
   *  return counter;    
   * }
   */
  private static Declaration getPILforComputeLength(String opNameString, TomType opType) {    
    // all the variables
    TomTerm subject = `Variable(concOption(),Name("subject"),opType,concConstraint());    
    TomTerm old = `Variable(concOption(),Name("old"),opType,concConstraint());       
    TomTerm counter = `Variable(concOption(),Name("counter"),Compiler.getIntType(),concConstraint());
    TomTerm elem = `Variable(concOption(),Name("elem"),opType,concConstraint());    
    // test if a new element
    Instruction isNewElem = `If(Negation(EqualTerm(opType,elem,old)), UnamedBlock(concInstruction(
        LetRef(counter,AddOne(counter),LetRef(old,TomTermToExpression(elem),Nop())))),Nop());    

    TomName opName = `Name(opNameString);
    // test if end of list
    Instruction isEndList = `If(Negation(IsFsym(opName,subject)), 
        If(Negation(EqualTerm(opType,subject,old)),LetRef(counter,AddOne(counter),Nop()),Nop()),Nop());
    
    Instruction whileBlock = `UnamedBlock(concInstruction(
        LetRef(elem,GetHead(opName,opType,subject),isNewElem),
        LetAssign(subject,GetTail(opName,subject),isEndList))); // subject is the method's argument    
    Instruction whileLoop = `WhileDo(IsFsym(opName,subject),whileBlock);
    
    // test if subj is consOpName
    Instruction isConsOpName = `If(Negation(IsFsym(opName,subject)),Return(ExpressionToTomTerm(Integer(1))),Nop());
    
    Instruction functionBody = `UnamedBlock(concInstruction(
        isConsOpName,
        LetRef(old,Bottom(opType),LetRef(counter,Integer(0),
            UnamedBlock(concInstruction(
                whileLoop,
                Return(counter)))))));
        
    return `MethodDef(Name(ConstraintGenerator.computeLengthFuncName+"_"+opNameString),
        concTomTerm(subject),Compiler.getIntType(),EmptyType(),functionBody);
  }  
  
  /**
   * Generates the PIL for the following function (used by the AC algorithm):
   * (tempSol contains the multiplicities of the elements of the current solution, 
   *  while alpha contains all the multiplicities )
   *  
   * ex: 
   *   subject = f(a,a,b,b,b);
   *   alpha = [2,3]
   *   tempSol = [1,2]; 
   *   => the function should return f(a,b,b) if isComplement=false 
   *                                 f(a,b) if isComplement=true                         
   *    
   * private OpType getTerm(int[] tempSol, int[] alpha, OpType subject, bool isComplement){
   *  int tempSolVal = 0;
   *  // a single element
   *  if(!subj.isConsf()) {
   *    tempSolVal = isComplement ? alpha[0] - tempSol[0] : tempSol[0]; 
   *    if(tempSolVal == 0) {
   *      return EmptyList(); 
   *    } else {
   *      return subject;      
   *    }    
   *  }
   *  Term result = EmptyList();
   *  Term old = null;
   *  int elemCounter = 0;
   *  int tempSolIndex = -1;
   *  while(true) {
   *    Term elem = null;
   *    // the end of the list
   *    if(subj.isConsf()) { 
   *      elem = subj.getHeadf();
   *    } else {
   *      elem = subj;
   *    }
   *    // a new element
   *    if (!elem.equals(old)){
   *      tempSolIndex++;
   *      old = elem;
   *      elemCounter=0;
   *    } 
   *    
   *    tempSolVal = isComplement ? alpha[tempSolIndex] - tempSol[tempSolIndex] : tempSol[tempSolIndex];
   *    if (tempSolVal != 0 && elemCounter < tempSolVal) {
   *      // we take this element
   *      result = conc(result*,elem);
   *      elemCounter++;
   *    }
   *    
   *    // if we got to the end of the list
   *    if(elem == subj) {
   *      break;  // break the loop            
   *    } else {
   *      subj = subj.getTailf();
   *    }
   *  }     
   *  return result;
   * }    
   * 
   */
  private static Declaration getPILforGetTermForMultiplicity(String opNameString, TomType opType) {
      
      TomType intArrayType = symbolTable.getIntArrayType();
      
      // the arguments      
      TomTerm tempSol = Compiler.getFreshVariable("tempSol",intArrayType);
      TomTerm alpha = Compiler.getFreshVariable("alpha",intArrayType);
      TomTerm subject = `Variable(concOption(),Name("subject"),opType,concConstraint());

      // TODO
      Instruction functionBody = `Nop();
   
      return `MethodDef(Name(ConstraintGenerator.getTermForMultiplicityFuncName + "_" + opNameString),
              concTomTerm(tempSol,alpha,subject),opType,EmptyType(),functionBody);
  } 
  
  /********************************************************************************/
  
  /**
   * helper functions - mostly related to free var generation
   */

  public static TomNumberList getRootpath() {
    return rootpath;
  }

  public static SymbolTable getSymbolTable() {
    return symbolTable;
  }

  public static TomType getTermTypeFromName(TomName tomName) {
    String stringName = ((Name)tomName).getString();
    TomSymbol tomSymbol = symbolTable.getSymbolFromName(stringName);    
    return tomSymbol.getTypesToType().getCodomain();
  }

  public static TomType getSlotType(TomName tomName, TomName slotName) {
    String stringName = ((Name)tomName).getString();
    TomSymbol tomSymbol = symbolTable.getSymbolFromName(stringName);
    return TomBase.getSlotType(tomSymbol,slotName);    
  } 

  // [pem] really useful ?
  public static TomType getIntType() {
    return symbolTable.getIntType();
  }

  // [pem] really useful ?
  public static TomType getBooleanType() {
    return symbolTable.getBooleanType();
  }

  public static TomType getTermTypeFromTerm(TomTerm tomTerm) {    
    return TomBase.getTermType(tomTerm,symbolTable);    
  }

  public static TomTerm getFreshVariable(TomType type) {
    return getFreshVariable(freshVarPrefix, type);    
  }

  public static TomTerm getFreshVariable(String name, TomType type) {
    TomNumberList path = getRootpath();
    name += freshVarCounter++;
    TomName freshVarName  = `PositionName(concTomNumber(path*,NameNumber(Name(name))));
    return `Variable(concOption(),freshVarName,type,concConstraint());
  }

  public static TomTerm getFreshVariableStar(TomType type) {
    return getFreshVariableStar(freshVarPrefix, type);
  }

  public static TomTerm getFreshVariableStar(String name, TomType type) {
    TomNumberList path = getRootpath();
    name += freshVarCounter++;
    TomName freshVarName  = `PositionName(concTomNumber(path*,NameNumber(Name(name))));
    return `VariableStar(concOption(),freshVarName,type,concConstraint());
  }

  public static TomTerm getBeginVariableStar(TomType type) {
    return getFreshVariableStar(freshBeginPrefix,type);
  }

  public static TomTerm getEndVariableStar(TomType type) {
    return Compiler.getFreshVariableStar(freshEndPrefix,type);
  }

  /*
   * add a prefix (tom_) to back-quoted variables which comes from the lhs
   */
  %strategy findRenameVariable(context:Collection) extends Identity() {
    visit TomTerm {
      var@(Variable|VariableStar)[AstName=astName@Name(name)] -> {
        if(context.contains(`astName)) {          
          return `var.setAstName(`Name(ASTFactory.makeTomVariableName(name)));
        }
      }
    }

    visit Instruction {
      CompiledPattern(patternList,instruction) -> {
        // only variables found in LHS have to be renamed (this avoids that the JAVA ones are renamed)
        Collection newContext = new ArrayList();
        `TopDownCollect(CollectLHSVars(newContext)).visitLight(`patternList);        
        newContext.addAll(context);
        return (Instruction)`TopDownIdStopOnSuccess(findRenameVariable(newContext)).visitLight(`instruction);
      }
    }  
  }  

  %strategy CollectLHSVars(Collection bag) extends Identity() {
    visit Constraint {
      MatchConstraint(p,_) -> {        
        Map map = TomBase.collectMultiplicity(`p);
        Collection newContext = new HashSet(map.keySet());
        bag.addAll(newContext);
        throw new VisitFailure();// to stop the top-down
      }
    }
  }


}
