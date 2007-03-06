package tom.engine.compiler.propagator;

import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomterm.types.tomterm.*;
import tom.library.sl.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.compiler.*;

/**
 * Syntactic propagator
 */
public class TomVariadicPropagator implements TomIBasePropagator{

// --------------------------------------------------------	
	%include { adt/tomsignature/TomSignature.tom }	
	%include { mustrategy.tom}
// --------------------------------------------------------
	
	private static short freshVarCounter = 0;
	
	public Constraint propagate(Constraint constraint){
		return (Constraint)`InnermostId(VariadicPatternMatching()).apply(constraint);		
	}	

	%strategy VariadicPatternMatching() extends `Identity(){		
		visit Constraint{			
			// Decompose 
			// conc(t1,X*,t2,Y*) = g -> SymbolOf(g)=conc /\ fresh_var = g 
			// /\ NotEmpty(fresh_Var) /\ tmp = fresh_var /\ fresh_var = GetTail(fresh_var) /\ t1=GetHead(tmp) 
			// /\ begin1 = fresh_var /\ end1 = fresh_var /\	X* = VariableHeadList(begin1,end1) /\ fresh_var = end1
			// /\ NotEmpty(fresh_Var) /\ tmp = fresh_var /\ fresh_var = GetTail(fresh_var) /\ t1=GetHead(tmp) 
			// /\ begin2 = fresh_var /\ end2 = fresh_var /\	Y* = VariableHeadList(begin2,end2) /\ fresh_var = end2
			//
			// OBS: t1=GetHead(tmp)  could further generate loops by decomposition and we do not want to have 
			// fresh_var = GetTail(fresh_var) in a loop; this is the reason for tmp
			m@MatchConstraint(t@RecordAppl(options,nameList@(name@Name(tomName),_*),slots
					,constraints),g) -> {
				// if we cannot decompose, stop
				%match(g) {
					SymbolOf(_) -> {return `m;}
				}				
				// if this is not a list, nothing to do
				if(!TomConstraintCompiler.isListOperator(TomConstraintCompiler.getSymbolTable().
						getSymbolFromName(`tomName))) {return `m;}				
				// declare fresh variable
				TomType listType = TomConstraintCompiler.getTermTypeFromTerm(`t);
				TomTerm freshVariable = getFreshVariableStar(listType,"freshList_");
				TomTerm tmpVariable = getFreshVariableStar(listType,"tmpList_");
				Constraint freshVarDeclaration = `MatchConstraint(freshVariable,g);
				
				ConstraintList l = `concConstraint();		
				TomTerm lastElement = null;
				// SlotList sList = `slots;
				%match(slots){
					p:concSlot(_*,PairSlotAppl[Appl=appl],X*)->{
						if (`X.length() == 0) {
							lastElement = `appl;
							break p;
						}						
						// if we have a variable
						if(((`appl) instanceof VariableStar) || ((`appl) instanceof UnamedVariableStar)){
							TomTerm beginSublist = getFreshVariableStar(listType,"begin_");
							TomTerm endSublist = getFreshVariableStar(listType,"end_");							
							// we put them in the inverse order in the list because later on we do a 'reverse' 
							l = `concConstraint(MatchConstraint(freshVariable,endSublist),
									MatchConstraint(appl,VariableHeadList(name,beginSublist,endSublist)),
									MatchConstraint(endSublist,freshVariable),
									MatchConstraint(beginSublist,freshVariable),l*);
						}else{	// a term or a syntactic variable
							// we put them in the inverse order in the list because later on we do a 'reverse'
							l = `concConstraint(MatchConstraint(appl,ExpressionToTomTerm(GetHead(name,listType,tmpVariable))),
									MatchConstraint(freshVariable,ExpressionToTomTerm(GetTail(name,freshVariable))),
									MatchConstraint(tmpVariable,freshVariable),
									Negate(EmptyListConstraint(name,freshVariable)),l*);
						}
					}					 
					concSlot() ->{
						l = `concConstraint(EmptyListConstraint(name,freshVariable),l*);
					}
				}// end match
				// the last element needs a special treatment
				// 1. if it is a VariableStar, we shouldn't generate a while, just an assignment for the variable				
				// 2. if it is not a VariableStar, this means that we should check that the subject ends also
				// 3. if it is an UnamedVariableStar, there is nothing to do
				if (lastElement != null){
					if(lastElement instanceof VariableStar){
						l = `concConstraint(MatchConstraint(lastElement,freshVariable),l*);
					}else if (!(lastElement instanceof UnamedVariableStar)){
						l = `concConstraint(EmptyListConstraint(name,freshVariable),
								MatchConstraint(lastElement,ExpressionToTomTerm(GetHead(name,listType,tmpVariable))),
								MatchConstraint(freshVariable,ExpressionToTomTerm(GetTail(name,freshVariable))),
								MatchConstraint(tmpVariable,freshVariable),
								Negate(EmptyListConstraint(name,freshVariable)),l*);						
					}
				}
				l = l.reverse();
				// add head equality condition + fresh var declaration
				l = `concConstraint(MatchConstraint(RecordAppl(options,nameList,concSlot(),constraints),SymbolOf(g)),
						freshVarDeclaration,l*);
				
				return `AndConstraint(l);
			}					
			// Merge for star variables (we only deal with the variables of the pattern, ignoring the introduced ones)
			// X* = p1 /\ X* = p2 -> X* = p1 /\ freshVar = p2 /\ freshVar == X*
			andC@AndConstraint(concConstraint(X*,eq@MatchConstraint(v@VariableStar[AstName=x@!PositionName[]],p1),Y*)) ->{
				Constraint toApplyOn = `AndConstraint(concConstraint(Y*));
				TomNumberList path = TomConstraintCompiler.getRootpath();
				TomName freshVarName  = `PositionName(concTomNumber(path*,NameNumber(Name("freshVar_" + (++freshVarCounter)))));
				TomTerm freshVar = `v.setAstName(freshVarName);
				Constraint res = (Constraint)`OnceTopDownId(ReplaceMatchConstraint(x,freshVar)).apply(toApplyOn);
				if (res != toApplyOn){					
					return `AndConstraint(concConstraint(X*,eq,res));
				}
			}
		}
	}// end %strategy
	
	%strategy ReplaceMatchConstraint(varName:TomName, freshVar:TomTerm) extends `Identity(){
		visit Constraint {
			MatchConstraint(v@VariableStar[AstName=name],p) -> {
				if (`name == varName) {					
					return `AndConstraint(concConstraint(MatchConstraint(freshVar,p),MatchConstraint(TestVarStar(freshVar),v)));
				}				  
			}
		}
	}
	
	private static TomTerm getFreshVariableStar(TomType type, String namePart){
		TomNumberList path = TomConstraintCompiler.getRootpath();
		TomName freshVarName  = `PositionName(concTomNumber(path*,NameNumber(Name(namePart + (++freshVarCounter)))));
		return `VariableStar(concOption(),freshVarName,type,concConstraint());
	}
}