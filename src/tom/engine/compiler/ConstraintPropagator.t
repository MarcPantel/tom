/*
 *
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2007, INRIA
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

import java.util.ArrayList;

import tom.engine.TomBase;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.compiler.propagator.*;
import tom.engine.exception.TomRuntimeException;
import tom.library.sl.*;

/**
 * This class is in charge with launching all the propagators,
 * until no more propagations can be made 
 */
public class ConstraintPropagator {

//------------------------------------------------------	
  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../adt/tomsignature/_TomSignature.tom }
  %include { ../../library/mapping/java/sl.tom}
  %include { java/util/types/ArrayList.tom}
//------------------------------------------------------

  private static final String propagatorsPackage = "tom.engine.compiler.propagator.";

  private static final String[] propagatorsNames = {"SyntacticPropagator","VariadicPropagator","ArrayPropagator"};

  public static Constraint performPropagations(Constraint constraintToCompile) 
    throws ClassNotFoundException,InstantiationException,IllegalAccessException,VisitFailure{
    
    // counts the propagators that didn't change the expression
    int propCounter = 0;
    int propNb = propagatorsNames.length;    	

    // some preparations
    constraintToCompile = preparePropagations(constraintToCompile);
    // cache the propagators
    IBasePropagator[] prop = new IBasePropagator[propNb];
    for(int i=0 ; i < propNb ; i++) {
      prop[i] = (IBasePropagator)Class.forName(propagatorsPackage + propagatorsNames[i]).newInstance();
    }
    
    Constraint result= null;
    mainLoop: while(true) {
      for(int i=0 ; i < propNb ; i++) {
        result = prop[i].propagate(constraintToCompile);
        // if nothing was done, start counting 
        propCounter = (result == constraintToCompile) ? (propCounter+1) : 0;        
        // if we applied all the propagators and nothing changed,
        // it's time to stop
        if(propCounter == propNb) { break mainLoop; }
        // reinitialize
        constraintToCompile = result;
      }
    } // end while    
    return result;
  }

  /**
   * Before propagations
   * - make sure that all constraints attached to terms are handled
   * - do nothing for the anti-patterns
   */
  private static Constraint preparePropagations(Constraint constraintToCompile) throws VisitFailure {
    ArrayList<Constraint> constraintList = new ArrayList<Constraint>(); 
    /* anti-terms are a little bit special and constraint detachment is performed in propagators
     * here we shouldn't do it because of the non-linearity ()
     * 
     * The strategy makes a TopDown, and when finding an AntiTerm, it doesn't traverse its children
     */    

//    Constraint newConstr = (Constraint)`mu(MuVar("xx"),IfThenElse(Is_AntiTerm(),Identity(),
//        Sequence(DetachConstraints(constraintList),All(MuVar("xx"))))).visit(constraintToCompile);
    
    Constraint newConstr = (Constraint)`TopDown(DetachConstraints(constraintList)).visit(constraintToCompile);    


    Constraint andList = `AndConstraint();
    for(Constraint constr: constraintList) {
      andList = `AndConstraint(andList*,constr);
    }
    return `AndConstraint(newConstr,andList*);
  }
  

  // TODO - wouldn't it be better to do this in propagators ?
  
  /**
   * f(x,a@b@g(y)) << t -> f(x,z) << t /\ g(y) << z /\ a << z /\ b << z
   */
  %strategy DetachConstraints(bag:ArrayList) extends Identity() {
    // if the constraints  = empty list, then is nothing to do
    visit TomTerm {
      t@(RecordAppl|Variable|UnamedVariable|VariableStar|UnamedVariableStar)[Constraints=constraints@!concConstraint()] -> {
        return `performDetach(bag,t.setConstraints(concConstraint()),constraints,false);
      }
      AntiTerm(t@(RecordAppl|Variable|VariableStar)[Constraints=constraints@!concConstraint()]) -> {
        return `performDetach(bag,t.setConstraints(concConstraint()),constraints,true);
      }
    } // end visit
  } // end strategy
    
  /**
   * a@...b@g(y) << t -> g(y) << z /\ a << z /\ ... /\ b << z
   * a@...b@!g(y) << t -> !g(y) << z /\ a << z /\ ... /\ b << z
   *
   */
  private static TomTerm performDetach(ArrayList bag, TomTerm subject, ConstraintList constraints, boolean isAnti) throws VisitFailure {
    TomType freshVarType = ConstraintCompiler.getTermTypeFromTerm(subject);
    TomTerm freshVariable = null;
    // make sure that if we had a varStar, we replace with a varStar also
match : %match(subject) {
      (VariableStar|UnamedVariableStar)[] -> {
        freshVariable = ConstraintCompiler.getFreshVariableStar(freshVarType);
        break match;
      }
      _ -> {
        freshVariable = ConstraintCompiler.getFreshVariable(freshVarType);
      }
    }// end match
    //make sure to apply on its subterms also    
    subject = isAnti ? `AntiTerm(subject) : subject;
    bag.add(preparePropagations(`MatchConstraint(subject,freshVariable)));
    // for each constraint
    %match(constraints) {
      concConstraint(_*,AssignTo(var),_*) -> {
        // add constraint to bag and delete it from the term
        bag.add(`MatchConstraint(var,freshVariable));                                                                                                                       
      }
    }// end match                   
    return freshVariable;      
  }
}
