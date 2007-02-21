package tom.engine.compiler;

import tom.engine.tools.TomGenericPlugin;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomexpression.types.*;
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

/**
 * Tom compiler based on constraints.
 * 
 * It controls different phases of compilation:
 * - propagation of constraints
 * - instruction generation from constraints
 * - ...   
 */
public class TomConstraintCompiler extends TomBase {

	%include { adt/tomsignature/TomSignature.tom }
	%include { sl.tom }
	
	private static SymbolTable symbolTable = null;
	private static TomNumberList rootpath = null;
	// keeps track of the match number to insure distinct variables' 
	// names for distinct match constructs
	private static short matchNumber = 0;
	
	public static TomTerm compile(TomTerm termToCompile,SymbolTable symbolTable){
		TomConstraintCompiler.symbolTable = symbolTable;
		return  (TomTerm)((Strategy)`InnermostId(CompileMatch())).fire(termToCompile);		
	}
	
	// looks for a 'Match' instruction:
	// 1. transforms each sequence of patterns into a conjuction of MatchConstraint
	// 2. launch PropagationManager
	// 3. launch GenerationManager
	// 4. transforms resulted expression into a CompiledMatch
	%strategy CompileMatch() extends Identity(){
		visit Instruction {			
			Match(SubjectList(subjectList),patternInstructionList, matchOptionList)  -> {				
				matchNumber++;
				rootpath = `concTomNumber(MatchNumber(Number(matchNumber)));
				short actionNumber = 0;
				TomList automataList = `concTomTerm();				
				// for each pattern action <term>,...,<term> -> { action }
				// build a matching automata  
				%match(patternInstructionList){
					concPatternInstruction(_*,PatternInstruction(Pattern[TomList=patternList],action,optionList),_*) ->{
						ConstraintList constraintList = TomConstraintCompiler.buildConstraintConjunction(`patternList,`subjectList);
						try{
							actionNumber++;
							Constraint propagationResult = TomPropagationManager.performPropagations(`AndConstraint(constraintList));							
							Instruction matchingAutomata = TomInstructionGenerationManager.performGenerations(propagationResult, `action);
							
							TomNumberList numberList = `concTomNumber(rootpath*,PatternNumber(Number(actionNumber)));
						    TomTerm automata = `Automata(optionList,patternList,numberList,matchingAutomata);
						    automataList = append(automata,automataList);
						}catch(Exception e){
							throw new TomRuntimeException("Propagation or generation exception:" + e);
						}																	    						
					}
				}// end %match				
				/*
				 * return the compiled Match construction
				 */
				InstructionList astAutomataList = TomConstraintCompiler.automataListCompileMatchingList(automataList);
				return `CompiledMatch(collectVariableFromSubjectList(subjectList,AbstractBlock(astAutomataList)), matchOptionList);
			}
		}// end visit
	}// end strategy
	
	/**
	 * collects match variables (from match(t1,...,tn)) and 
	 * checks their instance type 
	 */
	private static Instruction collectVariableFromSubjectList(TomList subjectList, Instruction body) {
		%match(subjectList) { 
			concTomTerm() -> { return body; }
			concTomTerm(subjectVar@Variable[Option=option,AstType=variableType],tail*) -> {
				body = collectVariableFromSubjectList(`tail,body);				
				// Check that the matched variable has the correct type
				return `CheckInstance(variableType,TomTermToExpression(subjectVar),body);
			}			
		}
		throw new TomRuntimeException("collectVariableFromSubjectList: strange term: " + `subjectList);
	}
	
	/**
	 * takes a list of patterns (p1...pn) and a list of subjects (s1...sn)
	 * and generates p1 << s1 /\ .... /\ pn << sn
	 */
	private static ConstraintList buildConstraintConjunction(TomList patternList, TomList subjectList){
		ConstraintList constraintList = `concConstraint();
		while(!`patternList.isEmptyconcTomTerm()) {
			TomTerm pattern = `patternList.getHeadconcTomTerm();
			TomTerm subject = `subjectList.getHeadconcTomTerm();
			
			constraintList = `concConstraint(MatchConstraint(pattern, subject),constraintList*);
			
			`patternList = `patternList.getTailconcTomTerm();
			`subjectList = `subjectList.getTailconcTomTerm();
		}// end while
		return constraintList;
	}	
	
	/**
	 * builds a list of instructions from a list of automata
	 */
	private static InstructionList automataListCompileMatchingList(TomList automataList) {
		%match(automataList) {
			concTomTerm() -> { return `concInstruction(); }
			concTomTerm(Automata(optionList,patternList,_,instruction),l*)  -> {
				InstructionList newList = automataListCompileMatchingList(`l);
				/*
				 * if a label is assigned to a pattern (label:pattern ->
				 * action) we generate corresponding labeled-block
				 */
				%match(optionList) {
				      concOption(_*,Label(name@Name[]),_*) -> { 
				    	  `instruction = `NamedBlock(name.getString(),concInstruction(instruction));
				      }
				}				
				return `concInstruction(CompiledPattern(patternList,instruction), newList*);
			}
		}
		return null;
	}
	
	public static TomNumberList getRootpath(){
		return rootpath;
	}
	
	public static SymbolTable getSymbolTable(){
		return symbolTable;
	}
	
	public static TomType getTermTypeFromName(TomName tomName){
		String stringName = ((Name)tomName).getString();
        TomSymbol tomSymbol = symbolTable.getSymbolFromName(stringName);
        return tomSymbol.getTypesToType().getCodomain();
	}
	
	public static TomType getTermTypeFromTerm(TomTerm tomTerm){
		%match(tomTerm){
			RecordAppl[NameList=nameList@(headName,_*)] ->{
				return getTermTypeFromName(`headName);
			}
			Variable[AstType=type] ->{
				return `type;
			}
			Subterm(constructorName, slotName, term) ->{
				TomSymbol tomSymbol = symbolTable.getSymbolFromName(((TomName)`constructorName).getString());
	        	return TomBase.getSlotType(tomSymbol, `slotName);
			}
		}
        throw new TomRuntimeException("getTermTypeFromTerm: cannot find the type for: " + tomTerm);
	}
}