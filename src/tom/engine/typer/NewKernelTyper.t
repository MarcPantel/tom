/*
 *
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2010, INPL, INRIA
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
 * Claudia Tavares  e-mail: Claudia.Tavares@loria.fr
 * Jean-Christophe Bach e-mail: Jeanchristophe.Bach@loria.fr
 *
 **/



package tom.engine.typer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.logging.Logger;

import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.code.types.*;
import tom.engine.adt.typeconstraints.types.*;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.exception.TomRuntimeException;

import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;

import tom.library.sl.*;

public class NewKernelTyper {
  %include { ../../library/mapping/java/sl.tom}
  %include { ../adt/tomsignature/TomSignature.tom }

  private static Logger logger = Logger.getLogger("tom.engine.typer.NewKernelTyper");

  %typeterm NewKernelTyper {
    implement { NewKernelTyper }
    is_sort(t) { ($t instanceof NewKernelTyper) }
  }

  private int freshTypeVarCounter;
  private int limTVarSymbolTable;

  /*
   * pem: why use a state variable here ?
   */
  // List for variables of pattern (match constraints)
  private TomList varPatternList;
  // List for variables of subject and of numeric constraints
  private BQTermList varList;

  /*
   * pem: why use a state variable here ?
   */
  // List for equation constraints (for fresh type variables)
  private TypeConstraintList equationConstraints;
  // List for subtype constraints (for fresh type variables)
  private TypeConstraintList subtypeConstraints;
  // Set of pairs (freshVar,type)
  private HashMap<TomType,TomType> substitutions;
  // Set of supertypes for each type
  private HashMap<String,TomTypeList> dependencies = new
    HashMap<String,TomTypeList>();

  private SymbolTable symbolTable;

  private String currentInputFileName;
  private boolean lazyType = false;

  protected void setLazyType() {
    lazyType = true;
  }

  protected void setSymbolTable(SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
  }

  protected void putSymbol(String name, TomSymbol astSymbol) {
    symbolTable.putSymbol(name,astSymbol);
  }

  protected SymbolTable getSymbolTable() {
    return symbolTable;
  }

  protected void setCurrentInputFileName(String currentInputFileName) {
    this.currentInputFileName = currentInputFileName;
  }

  protected String getCurrentInputFileName() {
    return currentInputFileName;
  }
  protected TomType getCodomain(TomSymbol tSymbol) {
    return TomBase.getSymbolCodomain(tSymbol);
  }

  protected TomSymbol getSymbolFromTerm(TomTerm tTerm) {
    return TomBase.getSymbolFromTerm(tTerm, symbolTable);
   }

  protected TomSymbol getSymbolFromTerm(BQTerm bqTerm) {
    return TomBase.getSymbolFromTerm(bqTerm,symbolTable);
  }

  protected TomSymbol getSymbolFromName(String tName) {
    return TomBase.getSymbolFromName(tName, symbolTable);
   }
  /*
  protected TomSymbol getSymbolFromName(String tName) {
    return symbolTable.getSymbolFromName(tName);
   }
   */

  protected TomSymbol getSymbolFromType(TomType tType) {
    return TomBase.getSymbolFromType(tType,symbolTable); 
  }

  /**
   * The method <code>addSubstitution</code> adds a substitutions (i.e. a pair
   * (type1,type2) where type2 is the substitution for type1) into the
   * global list "substitutions" and saturate it.
   * For example, to add a pair (X,Y) where X is a type variable and Y is a type
   * which can be a type variable or a ground type, we follow two steps:
   * <p>
   * STEP 1:  a) put(X,Z) if (Y,Z) is in substitutions or
   *          b) put(X,Y) otherwise
   * <p>
   * STEP 2:  a) put(W,Z) after step 1.a or put(W,Y) after step 1.b
   *             if there exist (W,X) in substitutions for each (W,X) in substitutions
   *          b) do nothing otherwise
   * @param key   the first argument of the pair to be inserted (i.e. the type1) 
   * @param value the second argument of the pair to be inserted (i.e. the type2) 
   */
  private void addSubstitution(TomType key, TomType value) {
    /* STEP 1 */
    TomType newValue = value;
    if (substitutions.containsKey(value)) {
      newValue = substitutions.get(value); 
    } 
    substitutions.put(key,newValue);

    /* STEP 2 */
    if (substitutions.containsValue(key)) {
      TomType valueOfCurrentKey;
      for (TomType currentKey : substitutions.keySet()) {
        valueOfCurrentKey = substitutions.get(currentKey);
        if (valueOfCurrentKey == key) {
          substitutions.put(currentKey,newValue);
        }
      }
    }
  }
 
  /**
   * The method <code>getType</code> gets the type of a term by consulting the
   * SymbolTable.
   * @param bqTerm  the BQTerm requesting a type
   * @return        the type of the BQTerm
   */
  protected TomType getType(BQTerm bqTerm) {
    %match(bqTerm) {
      (BQVariable|BQVariableStar|FunctionCall)[AstType=aType] -> { return `aType; }
      (BQAppl|BuildConstant|BuildTerm|BuildEmptyList|BuildConsList|BuildAppendList|BuildEmptyArray|BuildConsArray|BuildAppendArray)[AstName=Name[String=name]] -> {
        TomSymbol tSymbol = getSymbolFromName(`name);
        //DEBUG System.out.println("In getType with BQAppl " + `bqTerm + "\n");
        //DEBUG System.out.println("In getType with type " + getCodomain(tSymbol) + "\n");
        return getCodomain(tSymbol);
      }
    } 
    throw new TomRuntimeException("getType(BQTerm): should not be here.");
  }

  /**
   * The method <code>getType</code> gets the type of a term by consulting the
   * SymbolTable
   * @param tTerm the TomTerm requesting a type
   * @return      the type of the TomTerm
   */
  protected TomType getType(TomTerm tTerm) {
    %match(tTerm) {
      AntiTerm[TomTerm=atomicTerm] -> { return getType(`atomicTerm); }
      (Variable|VariableStar)[AstType=aType] -> { return `aType; }
      RecordAppl[NameList=concTomName(Name[String=name],_*)] -> {
        TomSymbol tSymbol = getSymbolFromName(`name);
        return getCodomain(tSymbol);
      }
    } 
    throw new TomRuntimeException("getType(TomTerm): should not be here.");
  }

  /**
   * The method <code>getInfoFromTomTerm</code> creates a pair
   * (name,information) for a given term by consulting its attributes.
   * @param tTerm  the TomTerm requesting the informations
   * @return       the information about the TomTerm
   */
  protected Info getInfoFromTomTerm(TomTerm tTerm) {
    %match(tTerm) {
      AntiTerm[TomTerm=atomicTerm] -> { return getInfoFromTomTerm(`atomicTerm); }
      (Variable|VariableStar)[Options=optionList,AstName=aName] -> { 
        return `PairNameOptions(aName,optionList); 
      }
      RecordAppl[Options=optionList,NameList=concTomName(aName,_*)] ->  { 
        return `PairNameOptions(aName,optionList); 
      }
    } 
    return `PairNameOptions(Name(""),concOption()); 
  }

  /**
   * The method <code>getInfoFromTomTerm</code> creates a pair
   * (name,information) for a given term by consulting its attributes.
   * @param bqTerm the BQTerm requesting the informations
   * @return       the information about the BQTerm
   */
  protected Info getInfoFromBQTerm(BQTerm bqTerm) {
    %match(bqTerm) {
      (BQVariable|BQVariableStar|BQAppl)[Options=optionList,AstName=aName] -> { 
        return `PairNameOptions(aName,optionList); 
      }
    } 
    return `PairNameOptions(Name(""),concOption()); 
  }

  /**
   * The method <code>setLimTVarSymbolTable</code> sets the lower bound of the
   * counter of type variables. This methods is called by the TyperPlugin
   * after replacing all unknown types of the SymbolTable by type variables and
   * before start the type inference.
   * @param freshTVarSymbolTable  the lower bound of the counter of type
   *                              variables
   */
  protected void setLimTVarSymbolTable(int freshTVarSymbolTable) {
    limTVarSymbolTable = freshTVarSymbolTable;
  }

  /**
   * The method <code>getFreshTlTIndex</code> increments the counter of type variables. 
   * @return  the incremented counter of type variables
   */
  protected int getFreshTlTIndex() {
    return freshTypeVarCounter++;
  }

  /**
   * The method <code>getUnknownFreshTypeVar</code> generates a fresh type
   * variable (by considering the global counter of type variables)
   * @return  a new (fresh) type variable
   */
  protected TomType getUnknownFreshTypeVar() {
    TomType tType = symbolTable.TYPE_UNKNOWN;
    %match(tType) {
      Type[TomType=tomType] -> { return `TypeVar(tomType,getFreshTlTIndex()); }
    }
    throw new TomRuntimeException("getUnknownFreshTypeVar: should not be here.");
   }

  /**
   * The method <code>containsConstraint</code> checks if a given constraint
   * already exists in a constraint type list. The method considers symmetry for
   * equation constraints. 
   * @param tConstraint the constraint to be considered
   * @param tCList      the type constraint list to be traversed
   * @return            'true' if the constraint already exists in the list
   *                    'false' otherwise            
   */
  protected boolean containsConstraint(TypeConstraint tConstraint, TypeConstraintList
      tCList) {
    %match {
      Subtype[Type1=t1,Type2=t2] << TypeConstraint tConstraint &&
        concTypeConstraint(_*,(Subtype|Equation)[Type1=t1,Type2=t2],_*) << tCList 
        -> { return true; }

      Equation[Type1=t1,Type2=t2] << TypeConstraint tConstraint &&
        concTypeConstraint(_*,Equation[Type1=t1,Type2=t2],_*) << tCList 
        -> { return true; }

      Equation[Type1=t1,Type2=t2] << TypeConstraint tConstraint &&
        concTypeConstraint(_*,Equation[Type1=t2,Type2=t1],_*) << tCList 
        -> { return true; }
    }
    return false;
  } 

  /*
   * pem: use if(...==... && typeConstraints.contains(...))
   */
  /**
   * The method <code>addEqConstraint</code> insert an equation (i.e. a type
   * constraint) into a given type constraint list only if this constraint does
   * not yet exist into the list and if it does not contains "EmptyTypes". 
   * @param tConstraint the equation to be inserted into the type constraint list
   * @param tCList      the constraint type list where the constraint will be
   *                    inserted
   * @return            the list resulting of the insertion
   */
  protected TypeConstraintList addEqConstraint(TypeConstraint tConstraint,
      TypeConstraintList tCList) {
    if (!containsConstraint(tConstraint,tCList)) {
      %match {
        Equation[Type1=t1@!EmptyType(),Type2=t2@!EmptyType()] <<
          tConstraint && (t1 != t2) -> { 
            return `concTypeConstraint(tConstraint,tCList*);
          }
      }
    }
    return tCList;
  }

  /**
   * The method <code>addSubConstraint</code> insert a subtype constraint (i.e. a type
   * constraint) into a given type constraint list only if this constraint does
   * not yet exist into the list and if it does not contains "EmptyTypes". 
   * @param tConstraint the subtype constraint to be inserted into the type
   *                    constraint list
   * @param tCList      the constraint type list where the constraint will be
   *                    inserted
   * @return            the list resulting of the insertion
   */
  protected TypeConstraintList addSubConstraint(TypeConstraint tConstraint,
      TypeConstraintList tCList) {
    if (!containsConstraint(tConstraint,tCList)) {
      %match {
        Subtype[Type1=t1@!EmptyType(),Type2=t2@!EmptyType()] << tConstraint 
          && (t1 != t2) -> { 
            return`concTypeConstraint(tConstraint,tCList*);
          }
      }
    }
    return tCList;
  }

  /**
   * The method <code>generateDependencies</code> generates a
   * hashMap called "dependencies" having pairs (typeName,supertypesList), where
   * supertypeslist is a list with all the related proper supertypes for each
   * ground type used into a code. The list is obtained by reflexive and
   * transitive closure over the direct supertype relation defined by the user
   * when defining the mappings.
   * For example, to generate the supertypes of T2, where T2 is a ground type, we
   * follow two steps:
   * <p>
   * STEP 1:  a) get the list supertypes_T3, put(T2,({T3} U supertype_T3)) and go
   *          to step 2, if there exists a declaration of form T2<:T3
   *          b) put(T2,{}), otherwise 
   * <p>
   * STEP 2:  put(T1,(supertypes_T1 U supertypes_T2)), for each (T1,{...,T2,...}) in dependencies
   */
  protected void generateDependencies() {
    TomTypeList superTypes;
    TomTypeList supOfSubTypes;
    String currentTypeName;
    for(TomType currentType:symbolTable.getUsedTypes()) {
      currentTypeName = currentType.getTomType();
      superTypes = `concTomType();
      //DEBUG System.out.println("In generateDependencies -- for 1 : currentType = " +
      //DEBUG    currentType);
      %match {
        /* STEP 1 */
        Type[TypeOptions=concTypeOption(_*,SubtypeDecl[TomType=supTypeName],_*),TomType=tName] << currentType -> {
          //DEBUG System.out.println("In generateDependencies -- match : supTypeName = "
          //DEBUG     + `supTypeName + " and supType = " + supType);
          if (dependencies.containsKey(`supTypeName)) {
            superTypes = dependencies.get(`supTypeName); 
          }
          TomType supType = symbolTable.getType(`supTypeName);
          // TODO : add verification of valid types here
          superTypes = `concTomType(supType,superTypes*);  

          /* STEP 2 */
          for(String subType:dependencies.keySet()) {
            supOfSubTypes = dependencies.get(`subType);
            //DEBUG System.out.println("In generateDependencies -- for 2: supOfSubTypes = " +
            //DEBUG     supOfSubTypes);
            %match {
              // The same tName of currentType
              concTomType(_*,Type[TomType=suptName],_*) << supOfSubTypes &&
                (suptName == tName) -> {
                /* 
                 * Replace list of superTypes of "subType" by a new one
                 * containing the superTypes of "currentType" which is also a
                 * superType 
                 */
                dependencies.put(subType,`concTomType(supOfSubTypes*,superTypes*));
              }
            }
          }
        }
      }
      //DEBUG System.out.println("In generateDependencies -- end: superTypes = " +
      //DEBUG     superTypes);
      dependencies.put(currentTypeName,superTypes);
    }
  }

  /**
   * The method <code>addTomTerm</code> insert a TomTerm into the global
   * <code>varPatternList</code>.
   * @param tTerm  the TomTerm to be inserted
   */
  protected void addTomTerm(TomTerm tTerm) {
    varPatternList = `concTomTerm(tTerm,varPatternList*);
  }

  /**
   * The method <code>addBQTerm</code> insert a BQTerm into the global
   * <code>varList</code>.
   * @param bqTerm  the BQTerm to be inserted
   */
  protected void addBQTerm(BQTerm bqTerm) {
    varList = `concBQTerm(bqTerm,varList*);
  }

  /**
   * The method <code>resetVarPatternList</code> empties the
   * <code>varPatternList</code> after
   * checking if <code>varList</code> contains
   * a corresponding BQTerm in order to remove it from <code>varList</code> too.
   */
  protected void resetVarPatternList() {
    for(TomTerm tTerm: varPatternList.getCollectionconcTomTerm()) {
      %match(tTerm,varList) {
        (Variable|VariableStar)[AstName=aName],concBQTerm(x*,(BQVariable|BQVariableStar)[AstName=aName],y*)
          -> {
            varList = `concBQTerm(x*,y*);
          }
      }
    }
    varPatternList = `concTomTerm();
  }

  /**
   * The method <code>init</code> reset the counter of type variables
   * <code>freshTypeVarCounter</code> and empties all global lists and hashMaps which means to
   * empty <code>varPatternList</code>, <code>varList</code>,
   * <code>equationConstraints</code>, <code>subtypeConstraints</code> and <code>substitutions</code>
   */
  private void init() {
    freshTypeVarCounter = limTVarSymbolTable;
    varPatternList = `concTomTerm();
    varList = `concBQTerm();
    equationConstraints = `concTypeConstraint();
    subtypeConstraints = `concTypeConstraint();
    substitutions = new HashMap<TomType,TomType>();
  }

  /**
   * The method <code>collectKnownTypesFromCode</code> creates an instance of
   * the class <code>CollectKnownTypes</code> and calls its method
   * <code>visitLight</code> to traverse a code. 
   * @param  subject the code to be traversed/transformed
   * @return         the code resulting of a transformation
   */
  private Code collectKnownTypesFromCode(Code subject) {
    try {
      return `TopDownIdStopOnSuccess(CollectKnownTypes(this)).visitLight(subject);
    } catch(tom.library.sl.VisitFailure e) {
      throw new TomRuntimeException("typeUnknownTypes: failure on " + subject);
    }
  }

  /**
   * The class <code>CollectKnownTypes</code> is generated from a strategy which
   * initially types all terms by using their correspondent type in symbol table
   * or a fresh type variable :
   * CASE 1 : Type(name, EmptyTargetLanguageType()) -> Type(name, foundType) if
   * name is in TypeTable
   * CASE 2 : Type(name, EmptyTargetLanguageType()) -> TypeVar(name, Index(i))
   * if name is not in TypeTable
   * @param nkt an instance of object NewKernelTyper
   * @return    the code resulting of a transformation
   */
  %strategy CollectKnownTypes(nkt:NewKernelTyper) extends Identity() {
    visit TomType {
      Type[TomType=tomType,TlType=EmptyTargetLanguageType()] -> {
        TomType newType = nkt.symbolTable.getType(`tomType);
        if (newType == null) {
          // This happens when :
          // * tomType != unknown type AND (newType == null)
          // * tomType == unknown type
          newType = `TypeVar(tomType,nkt.getFreshTlTIndex());
        }
        return newType;
      }
    }
  }

  /**
   * The method <code>inferAllTypes</code> is the start-up of the inference
   * process. It is a generic method and it is called for the first time by the
   * NewTyper.
   * @param term        the expression needing type inference
   * @param contextType the type related to the current expression
   * @return            the resulting typed expression 
   */
  public <T extends tom.library.sl.Visitable> T inferAllTypes(T term, TomType contextType) {
    try {
      return
        `TopDownStopOnSuccess(inferTypes(contextType,this)).visitLight(term); 
    } catch(tom.library.sl.VisitFailure e) {
      throw new TomRuntimeException("inferAllTypes: failure on " + term);
    }
  }

  /**
   * The class <code>inferTypes</code> is generated from a strategy which
   * tries to infer types of all variables on a given expression
   * <p> 
   * It starts by searching for a Code <code>Tom</code> or
   * <code>TomInclude</code> and calling <code>inferCodeList</code> in order to
   * apply rule CT-BLOCK for each block of ConstraintInstruction.
   * <p>
   * Then it searches for a Instruction
   * <code>Match(constraintInstructionList,option)</code> and calling
   * <code>inferConstraintInstructionList</code> in order to apply rule CT-RULE
   * for each single constraintInstruction
   * <p>
   * Then it searches for variables and star variables (TomTerms and BQTerms) and
   * applies rules CT-ALIAS, CT-ANTI, CT-VAR, CT-SVAR, CT-FUN,
   * CT-EMPTY, CT-ELEM, CT-MERGE or CT-STAR to a "pattern" (a TomTerm) or a
   * "subject" (a BQTerm) in order to infer types of its variables.
   * <p>
   * Let 'Ai' and 'Ti' be type variables and ground types, respectively:
   * <p>
   * CT-ALIAS rule:
   * IF found "x@e:A and "x:T" already exists in context
   * THEN adds a type constraint "A = T" and infers type of "e:A"
   * <p>
   * CT-ANTI rule:
   * IF found "!(e):A"
   * THEN infers type of "e:A"
   * <p>
   * CT-VAR rule (resp. CT-SVAR): 
   * IF found "x:A" (resp. "x*:A") and "x:A1" (resp. "x*:A1") already exists in
   *    context 
   * THEN adds a type constraint "A1 <: A"
   * <p>
   * CT-FUN rule:
   * IF found "f(e1,...,en):A" and "f:T1,...,Tn->T" exists in SymbolTable
   * THEN infers type of arguments and add a type constraint "T <:A"
   * <p>
   * CT-EMPTY rule:
   * IF found "l():A" and "l:T1*->T" exists in SymbolTable
   * THEN adds a type constraint "T <: A"       
   * <p>
   * CT-ELEM rule:
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" does not represent a list with head symbol "l"
   * <p>
   * CT-MERGE rule:
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" represents a list with
   *      head symbol "l"
   * <p>
   * CT-STAR rule:
   * Equals to CT-MERGE but with a star variable "x*" instead of "e"
   * This rule is necessary because it differed from CT-MERGE in the
   * sense of the type of the last argument ("x*" here) is unknown 
   * @param contextType the fresh generated previously and attributed to the term
   * @param nkt an instance of object NewKernelTyper
   */
  %strategy inferTypes(contextType:TomType,nkt:NewKernelTyper) extends Fail() {
    // We put "returns" for each "case" of a visit in order to interrupt the
    // flow of the strategy.
    // e.g. for "f(g(x))" the strategy will be applied over "x" three times (1
    // when visiting "f(g(x))" + 1 when visiting "g(x)" + 1 when visiting "x")
    
    visit Code {
      code@(Tom|TomInclude)[CodeList=cList] -> {
        nkt.generateDependencies();
        System.out.println("Dependencies: " + nkt.dependencies);
        //DEBUG System.out.println("Code with term = " + `code + " and contextType = " +
        //DEBUG     contextType);
        CodeList newCList = nkt.inferCodeList(`cList);
        return `code.setCodeList(newCList);
      }
    }

    visit Instruction {
      Match[ConstraintInstructionList=ciList,Options=optionList] -> {
        BQTermList BQTList = nkt.varList;
        ConstraintInstructionList newCIList =
          nkt.inferConstraintInstructionList(`ciList);
        nkt.varList = BQTList;
        return `Match(newCIList,optionList);
      }      
    }
    
    visit TomVisit {
      VisitTerm[VNode=vNode,AstConstraintInstructionList=ciList,Options=optionList] -> {
        BQTermList BQTList = nkt.varList;
        ConstraintInstructionList newCIList =
          nkt.inferConstraintInstructionList(`ciList);
        nkt.varList = BQTList;
        return `VisitTerm(vNode,newCIList,optionList);
      }
    }
   
    visit TomTerm {
      AntiTerm[TomTerm=atomicTerm] -> { nkt.inferAllTypes(`atomicTerm,contextType); }

      var@Variable[Options=optionList,AstName=aName,AstType=aType,Constraints=cList] -> {
        //DEBUG System.out.println("InferTypes:TomTerm var = " + `var);
        nkt.checkNonLinearityOfVariables(`var);
        TypeConstraintList newSubConstraints = nkt.subtypeConstraints;
        nkt.subtypeConstraints =
          nkt.addSubConstraint(`Subtype(aType,contextType,PairNameOptions(aName,optionList)),newSubConstraints);  
        //DEBUG System.out.println("InferTypes:TomTerm var -- constraint = " +
        //DEBUG     `aType + " <: " + contextType);
        ConstraintList newCList = `cList;
        %match(cList) {
          // How many "AliasTo" constructors can concConstraint have?
          concConstraint(AliasTo(boundTerm)) -> {
            //DEBUG System.out.println("InferTypes:TomTerm aliasvar -- constraint = " +
            //DEBUG   nkt.getType(`boundTerm) + " = " + `contextType);
            //nkt.addConstraint(`Equation(nkt.getType(boundTerm),contextType,nkt.getInfoFromTomTerm(boundTerm))); 
            TypeConstraintList newEqConstraints = nkt.equationConstraints;
            nkt.equationConstraints =
              nkt.addEqConstraint(`Equation(nkt.getType(boundTerm),aType,nkt.getInfoFromTomTerm(boundTerm)),newEqConstraints); 
          }
        }
        return `var.setConstraints(newCList);
      }

      varStar@VariableStar[Options=optionList,AstName=aName,AstType=aType,Constraints=cList] -> {
        //DEBUG System.out.println("InferTypes:TomTerm varStar = " + `varStar);
        nkt.checkNonLinearityOfVariables(`varStar);
        TypeConstraintList newEqConstraints = nkt.equationConstraints;
        nkt.equationConstraints =
          nkt.addEqConstraint(`Equation(aType,contextType,PairNameOptions(aName,optionList)),newEqConstraints);  
        //DEBUG System.out.println("InferTypes:TomTerm varStar -- constraint = " +
        //DEBUG     `aType + " = " + contextType);
        ConstraintList newCList = `cList;
        %match(cList) {
          // How many "AliasTo" constructors can concConstraint have?
          concConstraint(AliasTo(boundTerm)) -> {
            //DEBUG System.out.println("InferTypes:TomTerm aliasvar -- constraint = " +
            //DEBUG   nkt.getType(`boundTerm) + " = " + `contextType);
            //nkt.addConstraint(`Equation(nkt.getType(boundTerm),contextType,nkt.getInfoFromTomTerm(boundTerm))); 
            newEqConstraints = nkt.equationConstraints;
            nkt.equationConstraints =
              nkt.addEqConstraint(`Equation(nkt.getType(boundTerm),aType,nkt.getInfoFromTomTerm(boundTerm)),newEqConstraints); 
          }
        }
        return `varStar.setConstraints(newCList);
      }

      RecordAppl[Options=optionList,NameList=nList@concTomName(aName@Name(tomName),_*),Slots=sList,Constraints=cList] -> {
        // In case of a String, tomName is "" for ("a","b")
        TomSymbol tSymbol = nkt.getSymbolFromName(`tomName);
        // IF_1
        if (tSymbol == null) {
          //The contextType is used here, so it must be a ground type, not a
          //type variable
          tSymbol = nkt.getSymbolFromType(contextType);

          // IF_2
          if (tSymbol != null) {
            // In case of contextType is "TypeVar(name,i)"
            `nList = `concTomName(tSymbol.getAstName());
          } 
        }
        //DEBUG System.out.println("\n Test pour TomTerm-inferTypes in RecordAppl. tSymbol = " + `tSymbol);
        //DEBUG System.out.println("\n Test pour TomTerm-inferTypes in RecordAppl. astName = " +`concTomName(tSymbol.getAstName()));
        //DEBUG System.out.println("\n Test pour TomTerm-inferTypes in RecordAppl.
        //tSymbol = " + tSymbol);

        TomType codomain = contextType;

        // IF_3
        if (tSymbol == null) {
          //DEBUG System.out.println("tSymbol is still null!");
          tSymbol = `EmptySymbol();
        } else {
          // This code can not be moved to IF_2 because tSymbol may don't be
          // "null" since the begginning and then does not enter into neither IF_1 nor
          // IF_2
          codomain = nkt.getCodomain(tSymbol);
          //DEBUG System.out.println("\n Test pour TomTerm-inferTypes in RecordAppl. codomain = " + codomain);
          TypeConstraintList newSubConstraints = nkt.subtypeConstraints;
          nkt.subtypeConstraints = nkt.addSubConstraint(`Subtype(codomain,contextType,PairNameOptions(aName,optionList)),newSubConstraints);
          //DEBUG System.out.println("InferTypes:TomTerm recordappl -- constraint" +
          //DEBUG     codomain + " <: " + contextType);
        }

        ConstraintList newCList = `cList;
        %match(cList) {
          // How many "AliasTo" constructors can concConstraint have?
          concConstraint(AliasTo(boundTerm)) -> {
            //DEBUG System.out.println("InferTypes:TomTerm aliasrecordappl -- constraint = " +
            //DEBUG     nkt.getType(`boundTerm) + " = " + contextType);
            //nkt.addConstraint(`Equation(nkt.getType(boundTerm),contextType,nkt.getInfoFromTomTerm(boundTerm))); 
            TypeConstraintList newEqConstraints = nkt.equationConstraints;
            nkt.equationConstraints =
              nkt.addEqConstraint(`Equation(nkt.getType(boundTerm),codomain,nkt.getInfoFromTomTerm(boundTerm)),newEqConstraints); 
          }
        }

        SlotList newSList = `concSlot();
        if (!`sList.isEmptyconcSlot()) {
          `newSList =
            nkt.inferSlotList(`sList,tSymbol,codomain);
        }
        return `RecordAppl(optionList,nList,newSList,newCList);
      }
    }

    visit BQTerm {
      bqVar@BQVariable[Options=optionList,AstName=aName,AstType=aType] -> {
        //DEBUG System.out.println("InferTypes:BQTerm bqVar -- contextType = " +
        //DEBUG     contextType);
        nkt.checkNonLinearityOfBQVariables(`bqVar);
        TypeConstraintList newSubConstraints = nkt.subtypeConstraints;
          nkt.subtypeConstraints = nkt.addSubConstraint(`Subtype(aType,contextType,PairNameOptions(aName,optionList)),newSubConstraints);  
        //DEBUG System.out.println("InferTypes:BQTerm bqVar -- constraint = " +
        //DEBUG `aType + " <: " + contextType);
        return `bqVar;
      }

      bqVarStar@BQVariableStar[Options=optionList,AstName=aName,AstType=aType] -> {
        //DEBUG System.out.println("InferTypes:BQTerm bqVarStar -- contextType = " +
        //DEBUG     contextType);
        nkt.checkNonLinearityOfBQVariables(`bqVarStar);
        TypeConstraintList newEqConstraints = nkt.equationConstraints;
        nkt.equationConstraints =
          nkt.addEqConstraint(`Equation(aType,contextType,PairNameOptions(aName,optionList)),newEqConstraints);  
        //DEBUG System.out.println("InferTypes:BQTerm bqVarStar -- constraint = " +
        //DEBUG `aType + " = " + contextType);
        return `bqVarStar;
      }

      BQAppl[Options=optionList,AstName=aName@Name(name),Args=bqTList] -> {
        //DEBUG System.out.println("\n Test pour BQTerm-inferTypes in BQAppl. tomName = " + `name);
        TomSymbol tSymbol = nkt.getSymbolFromName(`name);
        if (tSymbol == null) {
          //The contextType is used here, so it must be a ground type, not a
          //type variable
          //DEBUG System.out.println("visit contextType = " + contextType);
          tSymbol = nkt.getSymbolFromType(contextType);
          if (tSymbol != null && `name.equals("")) {
            // In case of contextType is "TypeVar(name,i)"
            `aName = tSymbol.getAstName();
          }
        }

        TomType codomain = contextType;
        if (tSymbol == null) {
          tSymbol = `EmptySymbol();
        } else {
          codomain = nkt.getCodomain(tSymbol);
          TypeConstraintList newSubConstraints = nkt.subtypeConstraints;
          nkt.subtypeConstraints = nkt.addSubConstraint(`Subtype(codomain,contextType,PairNameOptions(aName,optionList)),newSubConstraints);
          //DEBUG System.out.println("InferTypes:BQTerm bqappl -- constraint = "
          //DEBUG + `codomain + " = " + contextType);
        }
        
        BQTermList newBQTList = `bqTList;
        if (!`bqTList.isEmptyconcBQTerm()) {
          //DEBUG System.out.println("\n Test pour BQTerm-inferTypes in BQAppl. bqTList = " + `bqTList);
          newBQTList =
            nkt.inferBQTermList(`bqTList,`tSymbol,codomain);
        }
      
        // TO VERIFY
        %match(tSymbol) {
          EmptySymbol() -> {
            return `FunctionCall(aName,contextType,newBQTList); 
          }
        }
        return `BQAppl(optionList,aName,newBQTList);
      }
    }
  }
    
  /**
   * The method <code>checkNonLinearityOfVariables</code> searches for variables
   * occurring more than once in a condition.
   * <p>
   * For each variable of type
   * "TomTerm" that already exists in <code>varPatternList</code> or in
   * <code>varList</code>, a type constraint is added to
   * <code>equationConstraints</code> to ensure that  both
   * variables have same type (this happens in case of non-linearity).
   * <p>
   * OBS.: we also need to check the <code>varList</code> since a Variable/VariableStar can have
   * occurred in a previous condition as a BQVariable/BQVariableStar, in the
   * case of a composed condition
   * e.g. (x < 10 ) && f(x) << e -> { action }
   * @param var the variable to have the linearity checked
   */
  private void checkNonLinearityOfVariables(TomTerm var) {
    %match {
      // If the variable already exists in varPatternList or in varList (in
      // the case of a bad order of the conditions of a
      // conjunction/disjunction
      (Variable|VariableStar)[Options=optionList,AstName=aName,AstType=aType1] << var &&
        (concTomTerm(_*,(Variable|VariableStar)[AstName=aName,AstType=aType2@!aType1],_*) << varPatternList ||
         concBQTerm(_*,(BQVariable|BQVariableStar)[AstName=aName,AstType=aType2@!aType1],_*) << varList) -> {
            TypeConstraintList newEqConstraints = equationConstraints;
            equationConstraints = addEqConstraint(`Equation(aType1,aType2,PairNameOptions(aName,optionList)),newEqConstraints);}
    }
  }

  /**
   * The method <code>checkNonLinearityOfBQVariables</code> searches for variables
   * occurring more than once in a condition.
   * <p>
   * For each variable of type
   * "BQTerm" that already exists in <code>varPatternList</code> or in
   * <code>varList</code>, a type constraint is added to
   * <code>equationConstraints</code> to ensure that  both
   * variables have same type (this happens in case of non-linearity).
   * <p>
   * OBS.: we also need to check the <code>varPatternList</code> since a
   * BQVariable/BQVariableStar can have occurred in a previous condition as a
   * Variable/VariableStar, in the case of a composed condition or of a inner match
   * e.g. f(x) << e && (x < 10 ) -> { action } 
   * @param bqvar the variable to have the linearity checked
   */
  private void checkNonLinearityOfBQVariables(BQTerm bqvar) {
    %match {
      // If the backquote variable already exists in varPatternList 
      // (in the case of a inner match) or in varList
      (BQVariable|BQVariableStar)[Options=optionList,AstName=aName,AstType=aType1] << bqvar &&
        (concBQTerm(_*,(BQVariable|BQVariableStar)[AstName=aName,AstType=aType2@!aType1],_*) << varList ||
         concTomTerm(_*,(Variable|VariableStar)[AstName=aName,AstType=aType2@!aType1],_*) << varPatternList) -> {
          TypeConstraintList newEqConstraints = equationConstraints;
          equationConstraints =
            addEqConstraint(`Equation(aType1,aType2,PairNameOptions(aName,optionList)),newEqConstraints); }
    }
  }

  /**
   * The method <code>inferCodeList</code> applies rule CT-BLOCK. It starts
   * inference process which takes one code at a time
   * <ul>
   *  <li> all lists and hashMaps are reset
   *  <li> each code is typed with fresh type variables
   *  <li> each code is traversed in order to generate type constraints
   *  <li> the type constraints of "equationConstraints" and
   *        "subtypeConstraints" lists are solved at the end
   *        of the current code generating a mapping (a set of
   *        substitutions for each type variable)
   *  <li> the mapping is applied over the code and the symbol table
   * </ul>
   * <p>
   * CT-BLOCK rule:
   * IF found "(rule_1,...,rule_n)" where "rule_i" are ConstraintInstructions
   * THEN infers types of each ConstraintInstruction
   * @param cList the tom code list to be type inferred
   * @return      the tom typed code list
   */
  private CodeList inferCodeList(CodeList cList) {
    CodeList newCList = `concCode();
    for (Code code : cList.getCollectionconcCode()) {
      init();
      code =  collectKnownTypesFromCode(`code);
      //DEBUG System.out.println("------------- Code typed with typeVar:\n code = " +
      //DEBUG     `code);
      code = inferAllTypes(code,`EmptyType());
      //DEBUG printGeneratedConstraints(subtypeConstraints);
      solveConstraints();
      //DEBUG System.out.println("substitutions = " + substitutions);
      code = replaceInCode(code);
      //DEBUG System.out.println("------------- Code typed with substitutions:\n code = " +
      //DEBUG     `code);
      replaceInSymbolTable();
      newCList = `concCode(code,newCList*);
    }
    return newCList.reverse();
  }

  /**
   * The method <code>inferConstraintInstructionList</code> applies rule CT-RULE
   * to a pair "condition -> action" in order to collect all variables occurring
   * in the condition and put them into <code>varPatternList</code> (for those
   * variables occurring in match constraints) and <code>varList</code> (for
   * those variables occurring in numeric constraints) to be able to handle
   * non-linearity.
   * <p>
   * The condition (left-hand side) is traversed and then the action (right-hand
   * side) is traversed in order to generate type constraints.
   * <p>
   * CT-RULE rule:
   * IF found "cond --> action)" where "action" is a list of terms or a code to
   *    be type inferred
   * THEN infers types of condition (by calling <code>inferConstraint</code>
   *      method) and action (by calling <code>inferAllTypes</code>) 
   * @param ciList  the list of pairs "condition -> action" to be type inferred 
   * @return        the typed list resulting
   */
  private ConstraintInstructionList inferConstraintInstructionList(ConstraintInstructionList ciList) {
    ConstraintInstructionList newCIList = `concConstraintInstruction();
    for (ConstraintInstruction cInst :
        ciList.getCollectionconcConstraintInstruction()) {
      try {
        %match(cInst) {
          ConstraintInstruction(constraint,action,optionList) -> {
            TomList TTList = varPatternList;
            `TopDownCollect(CollectVars(this)).visitLight(`constraint);
            Constraint newConstraint = inferConstraint(`constraint);
            //DEBUG System.out.println("inferConstraintInstructionList: action " +
            //DEBUG     `action);
            Instruction newAction = `inferAllTypes(action,EmptyType());
            varPatternList = TTList;
            newCIList =
              `concConstraintInstruction(ConstraintInstruction(newConstraint,newAction,optionList),newCIList*);
          } 
        }
      } catch(tom.library.sl.VisitFailure e) {
        throw new TomRuntimeException("inferConstraintInstructionList: failure on " + `cInst);
      }
    }
    return newCIList.reverse();
  }

  /**
   * The class <code>CollectVars</code> is generated from a strategy which
   * collect all variables (Variable, VariableStar, BQVariable, BQVariableStar
   * occurring in a condition.
   * @param nkt an instance of object NewKernelTyper
   */
  %strategy CollectVars(nkt:NewKernelTyper) extends Identity() {
    visit TomTerm {
      var@(Variable|VariableStar)[] -> { nkt.addTomTerm(`var); }
    }

    visit BQTerm {
      bqvar@(BQVariable|BQVariableStar)[] -> { nkt.addBQTerm(`bqvar); }
    }
  }

  /**
   * The method <code>inferConstraint</code> applies rule CT-MATCH, CT-NUM, CT-CONJ,
   * or CT-DISJ to a "condition" in order to infer the types of its variables.
   * <p>
   * Let 'Ai' and 'Ti' be type variables and ground types, respectively:
   * <p>
   * CT-MATCH rule:
   * IF found "e1 << [A] e2" 
   * THEN infers type of e1 and e2 and add the type constraints "T1 = A" and "A
   * <: T2"
   * <p>
   * CT-NUM rule:
   * IF found "e1 == e2", "e1 <= e2", "e1 < e2", "e1 >= e2" or "e1 > e2"
   * THEN infers type of e1 and e2 and add the type constraints "A <:T1" and "A
   * <: T2", where "A" is a fresh type variable   
   * <p>
   * CT-CONJ rule (resp. CT-DISJ):
   * IF found "cond1 && cond2" (resp. "cond1 || cond2")
   * THEN infers type of cond1 and cond2 (by calling
   * <code>inferConstraint</code> for each condition)
   * @param constraint  the "condition" to be type inferred 
   * @return            the typed condition resulting
   */
  private Constraint inferConstraint(Constraint constraint) {
    %match(constraint) {
      MatchConstraint[Pattern=pattern,Subject=subject,AstType=aType] -> { 
        //DEBUG System.out.println("inferConstraint l1 -- subject = " + `subject);
        TomType tPattern = getType(`pattern);
        TomType tSubject = getType(`subject);
        if (tPattern == null || tPattern == `EmptyType()) {
          tPattern = getUnknownFreshTypeVar();
        }
        if (tSubject == null || tSubject == `EmptyType()) {
          tSubject = getUnknownFreshTypeVar();
        }
        //DEBUG System.out.println("inferConstraint: match -- constraint " +
        //DEBUG     tPattern + " = " + tSubject);
        %match(aType) {
          (Type|TypeVar)[] -> {
            /* T_pattern = T_cast and T_cast <: T_subject */
            TypeConstraintList newEqConstraints = equationConstraints;
            TypeConstraintList newSubConstraints = subtypeConstraints;
            equationConstraints =
              addEqConstraint(`Equation(tPattern,aType,getInfoFromTomTerm(pattern)),newEqConstraints);
            subtypeConstraints = addSubConstraint(`Subtype(aType,tSubject,getInfoFromBQTerm(subject)),newSubConstraints);
          }
        }
        TomTerm newPattern = `inferAllTypes(pattern,tPattern);
        BQTerm newSubject = `inferAllTypes(subject,tSubject);
        return `MatchConstraint(newPattern,newSubject,aType);
      }

      NumericConstraint(left,right,kind) -> {
        TomType tLeft = getType(`left);
        TomType tRight = getType(`right);
        if (tLeft == null || tLeft == `EmptyType()) {
          tLeft = getUnknownFreshTypeVar();
        }
        if (tRight == null || tRight == `EmptyType()) {
          tRight = getUnknownFreshTypeVar();
        }
        //DEBUG System.out.println("inferConstraint: match -- constraint " +
        //DEBUG     tLeft + " = " + tRight);

        // To represent the relationshipo between both argument types
        TomType lowerType = getUnknownFreshTypeVar();
        TypeConstraintList newSubConstraints = subtypeConstraints;
        newSubConstraints =
          addSubConstraint(`Subtype(lowerType,tLeft,getInfoFromBQTerm(left)),newSubConstraints);
        subtypeConstraints =
          addSubConstraint(`Subtype(lowerType,tRight,getInfoFromBQTerm(right)),newSubConstraints);
        BQTerm newLeft = inferAllTypes(`left,tLeft);
        BQTerm newRight = inferAllTypes(`right,tRight);
        return `NumericConstraint(newLeft,newRight,kind);
      }

      AndConstraint(headCList,tailCList*) -> {
        ConstraintList cList = `concConstraint(headCList,tailCList);
        Constraint newAConstraint = `AndConstraint();
        for (Constraint cArg : cList.getCollectionconcConstraint()) {
          cArg = inferConstraint(cArg);
          newAConstraint = `AndConstraint(newAConstraint,cArg);
        }
        return newAConstraint;
      }

      OrConstraint(headCList,tailCList*) -> {
        ConstraintList cList = `concConstraint(headCList,tailCList);
        Constraint newOConstraint = `OrConstraint();
        for (Constraint cArg : cList.getCollectionconcConstraint()) {
          cArg = inferConstraint(cArg);
          newOConstraint = `OrConstraint(newOConstraint,cArg);
        }
        return newOConstraint;
      }
    }
    return constraint;
  }

  /**
   * The method <code>inferSlotList</code> infers types of the arguments of
   * lists and functions (which are TomTerms) 
   * <p> 
   * It continues the application of rules CT-FUN, CT-ELEM, CT-MERGE or CT-STAR
   * to each argument in order to infer the types of its variables.
   * <p>
   * Let 'Ai' and 'Ti' be type variables and ground types, respectively:
   * <p>
   * Continuation of CT-STAR rule (applying to premises):
   * IF found "l(e1,...,en,x*):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "x", where "x" represents a list with
   *      head symbol "l"
   * <p>
   * Continuation of CT-ELEM rule (applying to premises which are
   * not lists):
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" does not represent a list with head symbol "l"
   * <p>
   * Continuation of CT-MERGE rule (applying to premises which are lists with
   * the same operator):
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" represents a list with
   *      head symbol "l"
   * <p>
   * Continuation of CT-FUN rule (applying to premises):
   * IF found "f(e1,...,en):A" and "f:T1,...,Tn->T" exists in SymbolTable
   * THEN infers type of arguments and add a type constraint "T <:A"
   * <p>
   * @param sList       a list of arguments of a list/function
   * @param tSymbol     the TomSymbol related to the list/function
   * @param contextType the codomain of the list/function 
   * @return            the typed list of arguments resulting
   */
  private SlotList inferSlotList(SlotList sList, TomSymbol tSymbol, TomType
      contextType) {
    TomType argType = contextType;
    SlotList newSList = `concSlot();
    %match(tSymbol) {
      EmptySymbol() -> {
        TomName argName;
        TomTerm argTerm;
        TomSymbol argSymb;
        for (Slot slot : sList.getCollectionconcSlot()) {
          argName = slot.getSlotName();
          argTerm = slot.getAppl();
          argSymb = getSymbolFromTerm(argTerm);
          if(!(TomBase.isListOperator(`argSymb) || TomBase.isArrayOperator(`argSymb))) {
            %match(argTerm) {
              !VariableStar[] -> { 
                //DEBUG System.out.println("InferSlotList CT-ELEM -- tTerm = " + `tTerm);
                argType = getUnknownFreshTypeVar();
                //DEBUG System.out.println("InferSlotList getUnknownFreshTypeVar = " +
                //DEBUG     `argType);
              }
            }
          }
          argTerm = `inferAllTypes(argTerm,argType);
          newSList = `concSlot(PairSlotAppl(argName,argTerm),newSList*);
        }
        return newSList.reverse(); 
      }

      Symbol[AstName=symName@Name(name),TypesToType=TypesToType(concTomType(headTTList,_*),codomain@Type[TypeOptions=tOptions,TomType=tomCodomain,TlType=tlCodomain]),PairNameDeclList=pndList,Options=options] -> {
        TomTerm argTerm;
        if(TomBase.isListOperator(`tSymbol) || TomBase.isArrayOperator(`tSymbol)) {
          TomSymbol argSymb;
          for (Slot slot : sList.getCollectionconcSlot()) {
            argTerm = slot.getAppl();
            argSymb = getSymbolFromTerm(argTerm);
            if(!(TomBase.isListOperator(`argSymb) || TomBase.isArrayOperator(`argSymb))) {
              %match(argTerm) {
                VariableStar[] -> {
                  /* Case CT-STAR rule (applying to premises) */
                  argType = `codomain;
                }

                !VariableStar[] -> { 
                  /* Case CT-ELEM rule (applying to premises which are not lists) */
                  argType = `headTTList;
                  //DEBUG System.out.println("inferSlotList: !VariableStar -- constraint "
                  //DEBUG     + `headTTList + " = " + argType);
                  //addConstraint(`Equation(argType,headTTList,getInfoFromTomTerm(argTerm)));
                }
              }
            } else if (`symName != argSymb.getAstName()) {
              /*
               * Case CT-ELEM rule where premise is a list
               * A list with a sublist whose constructor is different
               * e.g. 
               * A = ListA(A*) | a() and B = ListB(A*)
               * ListB(ListA(a()))
               */
              argType = `headTTList;
            } 

            /* Case CT-MERGE rule (applying to premises) */
            argTerm = `inferAllTypes(argTerm,argType);
            newSList = `concSlot(PairSlotAppl(slot.getSlotName(),argTerm),newSList*);
          }
        } else {
          /* Case CT-FUN rule (applying to premises) */
          TomName argName;
          for (Slot slot : sList.getCollectionconcSlot()) {
            argName = slot.getSlotName();
            argType = TomBase.getSlotType(tSymbol,argName);
            argTerm = `inferAllTypes(slot.getAppl(),argType);
            newSList = `concSlot(PairSlotAppl(argName,argTerm),newSList*);
            //DEBUG System.out.println("InferSlotList CT-FUN -- end of for with slotappl = " + `argTerm);
          }
        }
        return newSList.reverse(); 
      }
    }
    throw new TomRuntimeException("inferSlotList: failure on " + `sList);
  }

  /**
   * The method <code>inferBQTermList</code> infers types of the arguments of
   * lists, functions and calls of methods (which are BQTerms) 
   * <p> 
   * It continues the application of rules CT-FUN, CT-ELEM, CT-MERGE or CT-STAR
   * to each argument in order to infer the types of its variables.
   * <p>
   * Let 'Ai' and 'Ti' be type variables and ground types, respectively:
   * <p>
   * Continuation of CT-STAR rule (applying to premises):
   * IF found "l(e1,...,en,x*):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "x", where "x" represents a list with
   *      head symbol "l"
   * <p>
   * Continuation of CT-ELEM rule (applying to premises which are
   * not lists):
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" does not represent a list with head symbol "l"
   * <p>
   * Continuation of CT-MERGE rule (applying to premises which are lists with
   * the same operator):
   * IF found "l(e1,...,en,e):A" and "l:T1*->T" exists in SymbolTable
   * THEN infers type of both sublist "l(e1,...,en)" and last argument
   *      "e" and adds a type constraint "T <: A", where "e" represents a list with
   *      head symbol "l"
   * <p>
   * Continuation of CT-FUN rule (applying to premises):
   * IF found "f(e1,...,en):A" and "f:T1,...,Tn->T" exists in SymbolTable
   * THEN infers type of arguments and add a type constraint "T <:A"
   * <p>
   * @param bqList      a list of arguments of a list/function/method
   * @param tSymbol     the TomSymbol related to the list/function
   * @param contextType the codomain of the list/function 
   * @return            the typed list of arguments resulting
   */
  private BQTermList inferBQTermList(BQTermList bqTList, TomSymbol tSymbol, TomType
      contextType) {
    TomType argType = contextType;
    BQTermList newBQTList = `concBQTerm();
    %match(tSymbol) {
      EmptySymbol() -> {
        for (BQTerm argTerm : bqTList.getCollectionconcBQTerm()) {
          argTerm = `inferAllTypes(argTerm,EmptyType());
          newBQTList = `concBQTerm(argTerm,newBQTList*);
        }
        return newBQTList.reverse(); 
      }

      Symbol[AstName=symName,TypesToType=TypesToType(domain@concTomType(headTTList,_*),codomain@Type[TypeOptions=tOptions,TomType=tomCodomain,TlType=tlCodomain]),PairNameDeclList=pNDList,Options=oList] -> {
        TomTypeList symDomain = `domain;
        TomSymbol argSymb;
        if(TomBase.isListOperator(`tSymbol) || TomBase.isArrayOperator(`tSymbol)) {
          for (BQTerm argTerm : bqTList.getCollectionconcBQTerm()) {
            argSymb = getSymbolFromTerm(argTerm);
            if(!(TomBase.isListOperator(`argSymb) || TomBase.isArrayOperator(`argSymb))) {
              %match(argTerm) {
                Composite(_*) -> {
                  /*
                   * We don't know what is into the Composite
                   * It can be a BQVariableStar or a list operator or a list of
                   * CompositeBQTerm or something else
                   */
                  argType = `EmptyType();
                }

                BQVariableStar[] -> {

                  /* Case CT-STAR rule (applying to premises) */
                  argType = `codomain;
                }

                //TO VERIFY : which constructors must be tested here?
                /* Case CT-ELEM rule (applying to premises which are not lists) */
                (BQVariable|BQAppl)[] -> {
                  argType = `headTTList;
                }
              }
            } else if (`symName != argSymb.getAstName()) {
              /*
               * Case CT-ELEM rule which premise is a list
               * A list with a sublist whose constructor is different
               * e.g. 
               * A = ListA(A*) and B = ListB(A*) | b()
               * ListB(ListA(a()))
               */
              argType = `headTTList;
            }

            /* Case CT-MERGE rule (applying to premises) */
            argTerm = `inferAllTypes(argTerm,argType);
            newBQTList = `concBQTerm(argTerm,newBQTList*);
          }
        } else {
          /* Case CT-FUN rule (applying to premises) */
          if(`pNDList.length() != bqTList.length()) {
            Option option = TomBase.findOriginTracking(`oList);
            %match(option) {
              OriginTracking(_,line,fileName) -> {
                TomMessage.error(logger,`fileName, `line,
                    TomMessage.symbolNumberArgument,`symName.getString(),`pNDList.length(),bqTList.length());
              }
            }
          } else {
            for (BQTerm argTerm : bqTList.getCollectionconcBQTerm()) {
              argType = symDomain.getHeadconcTomType();
              %match(argTerm) {
                /*
                 * We don't know what is into the Composite
                 * If it is a "composed" Composite with more than one element and
                 * representing the argument of a BQAppl "f", so we can not give the same
                 * type of the domain of "f" for all elements of the Composite
                 * e.g.:
                 *    `b(n.getvalue()) is represented by
                 * BQAppl(
                 *    concOption(...),
                 *    Name("b"),
                 *    concBQTerm(
                 *      Composite(
                 *        CompositeBQTerm(BQVariable(concOption(...),Name("n"),TypeVar("unknown type",0))),
                 *        CompositeTL(ITL(".")),
                 *        CompositeBQTerm(Composite(CompositeBQTerm(BQAppl(concOption(...),Name("getvalue"),concBQTerm())))))))
                 */
                Composite(_*,_,_*,_,_*) -> { argType = `EmptyType(); }
              }
              argTerm = `inferAllTypes(argTerm,argType);
              newBQTList = `concBQTerm(argTerm,newBQTList*);
              symDomain = symDomain.getTailconcTomType();
              //DEBUG System.out.println("InferBQTermList CT-FUN -- end of for with bqappl = " + `argTerm);
            }
          }
        }
        return newBQTList.reverse(); 
      }
    }
    throw new TomRuntimeException("inferBQTermList: failure on " + `bqTList);
  }

  /**
   * The method <code>solveConstraints</code> calls
   * <code>solveEquationConstraints</code> to solve the list of equation
   * constraints and generate a set os substitutions for type variables. Then
   * the substitutions are applied to the list of subtype constraints and the
   * method <code>solveEquationConstraints</code> to solve this list. 
   */
  private void solveConstraints() {
    //DEBUG System.out.println("\nsolveConstraints 1:");
    //DEBUG printGeneratedConstraints(equationConstraints);
    //DEBUG printGeneratedConstraints(subtypeConstraints);
    solveEquationConstraints(equationConstraints);
    %match {
      !concTypeConstraint() << subtypeConstraints -> {
        TypeConstraintList simplifiedConstraints =
          replaceInSubtypingConstraints(subtypeConstraints);
        //DEBUG printGeneratedConstraints(simplifiedConstraints);
        try {
          simplifiedConstraints = 
            `RepeatId(solveSubtypingConstraints(this)).visitLight(simplifiedConstraints);
          //DEBUG System.out.println("\nsolveConstraints 3:");
          //DEBUG printGeneratedConstraints(simplifiedConstraints);
        } catch(tom.library.sl.VisitFailure e) {
          throw new TomRuntimeException("solveConstraints: failure on " +
              subtypeConstraints);
        }
      }
    }

  }


  /**
   * The method <code>solveEquationConstraints</code> tries to solve all
   * equations constraints collected during the inference process.
   * <p> 
   * There exists 3 kinds of types : variable types Ai, ground types Ti and
   * ground types Ti^c which are decorated with a given symbol c. Since a type
   * constraints is a pair (type1,type2) representing an equation relation
   * between two types, them the set of all possibilities of arrangement between
   * types is a sequence with repetition. Then, we have 9 possible cases (since
   * 3^2 = 9).
   * <p>
   * CASE 1: tCList = {(T1 = T2),...)} and Map
   *  --> detectFail(T1 = T2) to verify if T1 is equals to T2 
   * <p>
   * CASE 2: tCList = {(T1 = T2^c),...)} and Map
   *  --> detectFail(T1 = T2^c) to verify if T1 is equals to T2 
   * <p>
   * CASE 3: tCList = {(T1^c = T2),...)} and Map
   *  --> detectFail(T1^c = T2) to verify if T1 is equals to T2 
   * <p>
   * CASE 4: tCList = {(T1^a = T2^b),...)} and Map
   *  --> detectFail(T1^a = T2^b) to verify if T1^a is equals to T2^b 
   * <p>
   * CASE 5: tCList = {(T1 = A1),...)} and Map 
   *  a) Map(A1) does not exist   --> {(A,T1)} U Map 
   *  b) Map(A1) = T2             --> detectFail(T1 = T2)
   *  c) Map(A1) = A2             --> {(A2,T1)} U Map, since Map is saturated and
   *                                  then Map(A2) does not exist 
   * <p>
   * CASE 6: tCList = {(T1^c = A1),...)} and Map 
   *  a) Map(A1) does not exist   --> {(A1,T1^c)} U Map 
   *  b) Map(A1) = T2 (or T2^b)   --> detectFail(T1^c = T2) (or detectFail(T1^c = T2^b))
   *  c) Map(A1) = A2             --> {(A2,T1^c)} U Map, since Map is saturated and
   *                                  then Map(A2) does not exist 
   * <p>
   * CASE 7: tCList = {(A1 = T1),...)} and Map 
   *  a) Map(A1) does not exist   --> {(A1,T1)} U Map 
   *  b) Map(A1) = T2             --> detectFail(T1 = T2)
   *  c) Map(A1) = A2             --> {(A2,A1)} U Map, since Map is saturated and
   *                                  then Map(A2) does not exist 
   * <p>
   * CASE 8: tCList = {(A1 = T1^c),...)} and Map 
   *  a) Map(A1) does not exist   --> {(A1,T1^c)} U Map 
   *  b) Map(A1) = T2 (or T2^b)   --> detectFail(T1^c = T2) (or detectFail(T1^c = T2^b))
   *  c) Map(A1) = A2             --> {(A2,T1^c)} U Map, since Map is saturated and
   *                                  then Map(A2) does not exist 
   * <p>
   * CASE 9: tCList = {(A1 = A2),...)} and Map
   *  a) Map(A1) = T1 (or T1^a) and
   *    i)    Map(A2) does not exist    --> {(A2,T1)} U Map (or {(A2,T1^a)} U Map) 
   *    ii)   Map(A2) = T2 (or T2^b)    --> detectFail(T1 = T2) (or detectFail(T1^a = T2^b))
   *    iii)  Map(A2) = A3              --> {(A3,T1)} U Map (or {(A3,T1^a)} U Map), since Map is saturated and
   *                                        then Map(A3) does not exist 
   *
   *  b) Map(A1) = A3 and
   *    i)    Map(A2) does not exist    --> {(A2,A3)} U Map 
   *    ii)   Map(A2) = T2 (or T2^b)    --> {(A3,T2)} U Map, since Map is saturated and
   *                                        then Map(A3) does not exist 
   *    iii)  Map(A2) = A4              --> {(A3,A4)} U Map, since Map is saturated and
   *                                        then Map(A3) does not exist 
   *  c) Map(A1) does not exist and
   *    i)    Map(A2) does not exist    --> {(A1,A2)} U Map
   *    ii)   Map(A2) = T1 (or T1^a)    --> {(A1,T1)} U Map (or {(A1,T1^a)} U Map)
   *    iii)  Map(A2) = A3              --> {(A1,A3)} U Map 
   */
  private TypeConstraintList solveEquationConstraints(TypeConstraintList tCList) {
    for (TypeConstraint tConstraint :
        tCList.getCollectionconcTypeConstraint()) {
matchBlockAdd :
      {
        %match {
          // CASES 1, 2, 3 and 4 :
          eConstraint@Equation[Type1=groundType1@!TypeVar[],Type2=groundType2@!TypeVar[]] <<
            tConstraint && (groundType1 != groundType2) -> {
              //DEBUG System.out.println("In solveEquationConstraints:" + `groundType1 +
              //DEBUG     " = " + `groundType2);
              `detectFail(eConstraint);
              break matchBlockAdd;
            }
          // CASES 5 and 6 :
          Equation[Type1=groundType@!TypeVar[],Type2=typeVar@TypeVar[],Info=info] <<
            tConstraint -> {
              if (substitutions.containsKey(`typeVar)) {
                TomType mapTypeVar = substitutions.get(`typeVar);
                if (!isTypeVar(mapTypeVar)) {
                  `detectFail(Equation(groundType,mapTypeVar,info));
                } else {
                // if (isTypeVar(mapTypeVar))
                addSubstitution(mapTypeVar,`groundType);
                }
              } else {
                addSubstitution(`typeVar,`groundType);
              }
              break matchBlockAdd;
            }

          // CASES 7 and 8 :
          Equation[Type1=typeVar@TypeVar[],Type2=groundType@!TypeVar[],Info=info] << tConstraint -> {
            if (substitutions.containsKey(`typeVar)) {
              TomType mapTypeVar = substitutions.get(`typeVar);
              if (!isTypeVar(mapTypeVar)) {
                `detectFail(Equation(mapTypeVar,groundType,info));
              } else {
                // if (isTypeVar(mapTypeVar))
                addSubstitution(mapTypeVar,`groundType);
              }
            } else {
              addSubstitution(`typeVar,`groundType);
            }
            break matchBlockAdd;
          }

          // CASE 9 :
          Equation[Type1=typeVar1@TypeVar[],Type2=typeVar2@TypeVar[],Info=info] << tConstraint
            && (typeVar1 != typeVar2) -> {
              TomType mapTypeVar1;
              TomType mapTypeVar2;
              if (substitutions.containsKey(`typeVar1) && substitutions.containsKey(`typeVar2)) {
                mapTypeVar1 = substitutions.get(`typeVar1);
                mapTypeVar2 = substitutions.get(`typeVar2);
                if (isTypeVar(mapTypeVar1)) {
                  addSubstitution(mapTypeVar1,mapTypeVar2);
                } else {
                  if (isTypeVar(mapTypeVar2)) {
                    addSubstitution(mapTypeVar2,mapTypeVar1);
                  } else {
                    `detectFail(Equation(mapTypeVar1,mapTypeVar2,info));
                  }
                }
                break matchBlockAdd;
              } else if (substitutions.containsKey(`typeVar1)) {
                mapTypeVar1 = substitutions.get(`typeVar1);
                addSubstitution(`typeVar2,mapTypeVar1);
                break matchBlockAdd;
              } else if (substitutions.containsKey(`typeVar2)){
                mapTypeVar2 = substitutions.get(`typeVar2);
                addSubstitution(`typeVar1,mapTypeVar2);
                break matchBlockAdd;
              } else {
                addSubstitution(`typeVar1,`typeVar2);
                break matchBlockAdd;
              }
            }
        }
      }
    }
    return tCList;
  }

  /**
   * The method <code>detectFail</code> checks if a type constraint
   * relating two ground types has a solution. In the negative case, an
   * 'incompatible types' message error is printed.  
   * <p> 
   * There exists 2 kinds of ground types: ground types Ti and
   * ground types Ti^c which are decorated with a given symbol c. Considering
   * type constraints as pairs (type1,type2) representing an equation or
   * a subtype relation between two ground types, them the set of all possibilities of arrangement between
   * ground types is a sequence with repetition. Then, we have 4 possible cases (since
   * 2^2 = 4).
   * <p>
   * CASE 1: tCList = {(T1 = T2),...)} and Map
   *  a) --> Fail if T1 is different from T2
   *  b) --> Nothing if T1 is equals to T2
   * <p>
   * CASE 2: tCList = {(T1 = T2^c),...)} and Map
   *  a) --> Fail if T1 is different from T2
   *  b) --> Nothing if T1 is equals to T2
   * <p>
   * CASE 3: tCList = {(T1^c = T2),...)} and Map
   *  a) --> Fail if T1 is different from T2
   *  b) --> Nothing if T1 is equals to T2
   * <p>
   * CASE 4: tCList = {(T1^a = T2^b),...)} and Map
   *  a) --> Fail if T1 is different from T2 or 'a' is different from 'b'
   *  b) --> Nothing if T1 is equals to T2 and 'a' is equals to 'b'
   * <p>
   * CASE 5: tCList = {(T1 <: T2),...)} and Map
   *  a) --> Fail if T1 is not subtype of T2
   *  b) --> Nothing if T1 is subtype of T2
   * <p>
   * CASE 6: tCList = {(T1 <: T2^c),...)} and Map
   *   --> Fail
   * <p>
   * CASE 7: tCList = {(T1^c <: T2),...)} and Map
   *  a) --> Fail if T1 is not subtype of T2
   *  b) --> Nothing if T1 is subtype of T2
   * <p>
   * CASE 8: tCList = {(T1^a <: T2^b),...)} and Map
   *  a) --> Fail if T1 is not subtype of T2 or 'a' is different from 'b'
   *  b) --> Nothing if T1 is subtype of T2 and 'a' is equals to 'b'
   * <p>
   * @param tConstraint the type constraint to be verified 
   */
  private void detectFail(TypeConstraint tConstraint) {
matchBlockFail : 
    {
      %match {
        /* CASES 1a, 2a and 3a */
        Equation[Type1=Type[TomType=tName1],Type2=Type[TomType=tName2@!tName1]]
          << tConstraint && (tName1 != "unknown type") && (tName2 != "unknown type")  -> {
            printError(`tConstraint);
            break matchBlockFail;
          }

        /* CASE 4a */  
        Equation[Type1=Type[TypeOptions=tOptions1,TomType=tName1],Type2=Type[TypeOptions=tOptions2@!tOptions1,TomType=tName1]]
          << tConstraint
          && concTypeOption(_*,WithSymbol[RootSymbolName=rsName1],_*) << tOptions1
          && concTypeOption(_*,WithSymbol[RootSymbolName=rsName2@!rsName1],_*) << tOptions2 -> {
            printError(`tConstraint);
            break matchBlockFail;
          }

        Subtype[Type1=t1,Type2=t2@!t1] << tConstraint -> {
          if (!isSubtypeOf(`t1,`t2)) {
            printError(`tConstraint);
            break matchBlockFail;
          }
        }
      }
    }
  }

  /**
   * The method <code>replaceInSubtypingConstraints</code> applies the
   * substitutions to a given type constraint list.
   * @param tCList the type constraint list to be replaced
   * @return       the type constraint list resulting of replacement
   */
  private TypeConstraintList replaceInSubtypingConstraints(TypeConstraintList
      tCList) {
    TypeConstraintList replacedtCList = `concTypeConstraint();
    TomType mapT1;
    TomType mapT2;
    for (TypeConstraint tConstraint: tCList.getCollectionconcTypeConstraint()) {
      %match(tConstraint) {
        Subtype[Type1=t1,Type2=t2,Info=info] -> {
          mapT1 = substitutions.get(`t1);
          mapT2 = substitutions.get(`t2); 
          if (mapT1 == null) {
            mapT1 = `t1;
          }
          if (mapT2 == null) {
            mapT2 = `t2;
          }
          if (mapT1 != mapT2) {
            replacedtCList =
              `addSubConstraint(Subtype(mapT1,mapT2,info),replacedtCList);
          }
        }
      }

    }
    return replacedtCList;
  }

  /**
   * The method <code>solveSubtypingConstraints</code> is generated by a
   * strategy which simplifies a subtype constraints list replacing them by equations
   * or detecting type inconsistencies.
   * <p>
   * Let 'Ai' and 'Ti' be type variables and ground types, respectively:
   * <p>
   * PHASE 1: Simplification in equations (it is a specialization of rules for
   * closed form): 
   * tCList = {T1 <: T2, T2 <: T1} U tCList' and Map -->  {T1 = T2} U tCList' and Map
   * tCList = {A1 <: A2, A2 <: A1} U tCList' and Map -->  {A1 = A2} U tCList' and Map
   * <p>
   * PHASE 2: Reduction in closed form:
   * tCList = {T1 <: A,A <: T2} U tCList' and Map --> {T1 <: T2} U tCList and Map
   * tCList = {A1 <:A,A <: A2} U tCList' and Map --> {A1 <: A2} U tCList and Map
   * <p>
   * PHASE 3: Garbage collection:
   * tCList = {T1 <: T2} U tCList' and Map --> detectFail(T1 <: T2)
   * <p>
   * PHASE 4: Reduction in canonical form:
   * tCList = {A <: T1,A <:T2} U tCList' and Map 
   *   --> {A <: lowerType(T1,T2)} U tCList' and Map
   * tCList = {T1 <: A,T2 <: A} U tCList' and Map
   *   --> {supType(T1,T2) <: A} U tCList' and Map
   * <p>
   * PHASE 5: Enumerate possible solutions
   * <p>
   * @param nkt an instance of object NewKernelTyper
   * @return    the subtype constraint list resulting
   */
  %strategy solveSubtypingConstraints(nkt:NewKernelTyper) extends Identity() {
    visit TypeConstraintList {
      /* PHASE 1 */
      tcl@concTypeConstraint(tcl1*,Subtype[Type1=t1,Type2=t2,Info=info],tcl2*,Subtype[Type1=t2,Type2=t1],tcl3*) -> {
        System.out.println("\nsolve1: " + `tcl);
        nkt.solveEquationConstraints(`concTypeConstraint(Equation(t1,t2,info)));
        return
          nkt.`concTypeConstraint(tcl1,tcl2,tcl3);
      }

      /*
       * We test if "t1 <: t2" already exist in tcl to avoid apply the strategy
       * unnecessarily
       */
      /* PHASE 2 */
      tcl@concTypeConstraint(_*,Subtype[Type1=t1,Type2=tVar@TypeVar[],Info=info],_*,Subtype[Type1=tVar,Type2=t2],_*)
        && !concTypeConstraint(_*,Subtype[Type1=t1,Type2=t2],_*) << tcl -> {
          System.out.println("\nsolve2a: " + `tcl);
          return
            nkt.`addSubConstraint(Subtype(t1,t2,info),tcl);
        }
      tcl@concTypeConstraint(_*,Subtype[Type1=tVar,Type2=t2,Info=info],_*,Subtype[Type1=t1,Type2=tVar@TypeVar[]],_*)
        && !concTypeConstraint(_*,Subtype[Type1=t1,Type2=t2],_*) << tcl -> {
          System.out.println("\nsolve2b: " + `tcl);
          return
            nkt.`addSubConstraint(Subtype(t1,t2,info),tcl);
        }

      /* PHASE 3 */
      tcl@concTypeConstraint(_*,sConstraint@Subtype[Type1=!TypeVar[],Type2=!TypeVar[]],_*) -> {
        System.out.println("\nsolve3: " + `tcl);
        nkt.detectFail(`sConstraint);
      }

      /* PHASE 4 */
      concTypeConstraint(tcl1*,constraint@Subtype[Type1=tVar@TypeVar[],Type2=t1@!TypeVar[],Info=info],tcl2*,c2@Subtype[Type1=tVar,Type2=t2@!TypeVar[]],tcl3*) -> {
        System.out.println("\nsolve6: " + `constraint + " and " + `c2);
        TomType lowerType = nkt.`minType(t1,t2);
        System.out.println("\nminType(" + `t1.getTomType() + "," +
            `t2.getTomType() + ") = " + lowerType);

        if (lowerType == `EmptyType()) {
          nkt.printError(`Subtype(t1,t2,info));
          return `concTypeConstraint(tcl1,tcl2,tcl3); 
        }

        return
          nkt.`addSubConstraint(Subtype(tVar,lowerType,info),concTypeConstraint(tcl1,tcl2,tcl3));
      }
      concTypeConstraint(tcl1*,constraint@Subtype[Type1=t1@!TypeVar[],Type2=tVar@TypeVar[],Info=info],tcl2*,c2@Subtype[Type1=t2@!TypeVar[],Type2=tVar],tcl3*) -> {
        System.out.println("\nsolve7: " + `constraint + " and " + `c2);
        TomType supType = nkt.`supType(t1,t2);
        System.out.println("\nsupType(" + `t1.getTomType() + "," +
           `t2.getTomType() + ") = " + supType);

        if (supType == `EmptyType()) {
          nkt.printError(`Subtype(t1,t2,info));
          return `concTypeConstraint(tcl1,tcl2,tcl3); 
        }

        return
          nkt.`addSubConstraint(Subtype(supType,tVar,info),concTypeConstraint(tcl1,tcl2,tcl3));
      }

      /* PHASE 5 */
      tcl -> {
        System.out.println("\nsolve8: " + `tcl);
        return nkt.enumerateSolutions(`tcl);
      }
    }
  }

  /**
   * The method <code>findVar</code> checks if a given type variable occurs in
   * a given type constraint list.
   * @param tVar   the type variable to be found
   * @param tCList the type constraint list to be checked
   * @return        'true' if the type variable occurs in the list
   *                'false' otherwise
   */
  private boolean findVar(TomType tVar, TypeConstraintList tCList) {
    %match {
      concTypeConstraint(_*,(Equation|Subtype)[Type1=t1],_*) << tCList &&
        t1 << TomType tVar -> { return true; }

      concTypeConstraint(_*,(Equation|Subtype)[Type2=t2],_*) << tCList &&
        t2 << TomType tVar -> { return true; }
    }
    return false;
  }

  /**
   * The method <code>isSubtypeOf</code> checks if type t1 is a subtype of type
   * t2 considering symmetry and type decorations of both t1 and t2.
   * <p> 
   * There exists 2 kinds of ground types: ground types Ti (represented by Ti^?) and
   * ground types Ti^c which are decorated with a given symbol c. Considering a
   * partial order over ground types "<:" as a binary relation where "T1^c1 <: T2^c2"
   * is equivalent to "T1 is subtype of T2 and (c1 == c2 or c2 == ?)", then the set of all possibilities of arrangement between
   * ground types is a sequence with repetition. Then, we have 4 possible cases (since
   * 2^2 = 4).
   * <p>
   * CASE 1: T1^? <: T2^?
   *  a) --> True if T1 == T2 or T1 is a proper subtype of T2
   *  b) --> False otherwise
   * <p>
   * CASE 2: T1^? <: T2^c
   *   --> False
   * <p>
   * CASE 3: T1^c <: T2^?
   *  a) --> True if T1 == T2 or T1 is a proper subtype of T2
   *  b) --> False otherwise
   * <p>
   * CASE 4: T1^c1 <: T2^c2
   *  a) --> True if T1 'a' is equals to 'b' and (T1 == T2 or T1 is a proper
   *  subtype of T2)
   *  b) --> False otherwise
   * <p>
   * @param t1 a ground (decorated) type
   * @param t2 another ground (decorated) type
   * @return   true if t1 <: t2 and false otherwise
   */
  private boolean isSubtypeOf(TomType t1, TomType t2) {
    %match {
      Type[TypeOptions=tOptions1,TomType=tName1] << t1 
        && Type[TypeOptions=tOptions2,TomType=tName2] << t2 -> {
          %match {
            // CASES 1a and 3a
            !concTypeOption(_*,WithSymbol[],_*) << tOptions2 
              && (tName1 == tName2) -> {
                return true; 
              }
            !concTypeOption(_*,WithSymbol[],_*) << tOptions2
              && concTomType(_*,Type[TomType=tName2],_*) <<
              dependencies.get(tName1) -> {
                return true; 
              }

            // CASE 4a
            concTypeOption(_*,WithSymbol[RootSymbolName=rsName],_*) << tOptions1
              && concTypeOption(_*,WithSymbol[RootSymbolName=rsName],_*) << tOptions2 
              && (tName1 == tName2) -> {
                return true; 
              }
            concTypeOption(_*,WithSymbol[RootSymbolName=rsName],_*) << tOptions1
              && concTypeOption(_*,WithSymbol[RootSymbolName=rsName],_*) << tOptions2 
              && (tName1 == tName2) && concTomType(_*,Type[TomType=tName2],_*) <<
              dependencies.get(tName1) -> {
                return true; 
              }
          }
        }
    }
    // Remain cases
    return false;
  }

  /**
   * The method <code>minType</code> tries to find the common lowertype
   * between two given ground types.
   * @param t1 a type
   * @param t2 another type
   * @return    the lowertype between 't1' and 't2' if the subtype relation
   *            holds between them (since a multiple inheritance is forbidden)
   *            the 'EmptyType()' otherwise
   */
  private TomType minType(TomType t1, TomType t2) {
    if (isSubtypeOf(t1,t2)) { return t1; } 
    if (isSubtypeOf(t2,t1)) { return t2; }
    return `EmptyType();
  }

  /**
   * The method <code>supType</code> tries to find the lowest common uppertype
   * of two given ground types.
   * When the subtype relation does not directly hold between two types 't1' and
   * 't2', the method considers:
   * CASE 1 : T1^c1 <: T2^c2
   *  a) --> True if T1 'a' is equals to 'b' and (T1 == T2 or T1 is a proper
   *  subtype of T2)
   *  b) --> False otherwise 
   *
   * the intersection of the supertypes lists
   * supertypes_t1 and supertypes_t2 and searches for the common supertype 'ti' which
   * has the bigger supertypes_ti list 
   * @param t1  a type
   * @param t2  another type
   * @return    the uppertype between 't1' and 't2' if the subtype relation
   *            holds between them or the lowest common uppertype of them if
   *            they are not in subtype relation 
   *            the 'EmptyType()' otherwise
   */
  private TomType supType(TomType t1, TomType t2) {
    TomType newt1 = t1;
    TomType newt2 = t2;
    %match {
      Type[TypeOptions=concTypeOption(ltoList1*,WithSymbol[RootSymbolName=rsName1],rtoList1*),TomType=tName1,TlType=tlType1] << t1 
        &&
        Type[TypeOptions=concTypeOption(ltoList2*,WithSymbol[RootSymbolName=rsName2],rtoList2*),TomType=tName2,TlType=tltype2] << t2 -> {
          newt1 = `Type(concTypeOption(ltoList1*,rtoList1*),tName1,tlType1);
          newt2 = `Type(concTypeOption(ltoList2*,rtoList2*),tName2,tltype2);
        }
    }
    if (isSubtypeOf(newt1,newt2)) { return newt2; } 
    if (isSubtypeOf(newt2,newt1)) { return newt1; }

    TomTypeList supTypes1 = dependencies.get(t1.getTomType());
    TomTypeList supTypes2 = dependencies.get(t2.getTomType());
    %match {
      !concTomType() << supTypes1 && !concTomType() << supTypes2 -> {
        int st1Size = `supTypes1.length();
        int st2Size = `supTypes2.length();

        int currentIntersectionSize = -1;
        int commonTypeIntersectionSize = -1;
        TomType lowestCommonType = `EmptyType();
        for (TomType currentType:`supTypes1.getCollectionconcTomType()) {
          currentIntersectionSize = dependencies.get(currentType.getTomType()).length();
          if (`supTypes2.getCollectionconcTomType().contains(currentType) &&
              (currentIntersectionSize > commonTypeIntersectionSize)) {
            commonTypeIntersectionSize = currentIntersectionSize;
            lowestCommonType = currentType;
          }
        }
        return lowestCommonType;
      }
    }
    return `EmptyType();
  }

  /**
   * The method <code>enumerateSolutions</code> chooses a solution for a subtype
   * constraint list when many possibilities are available. The algorithm picks
   * the lowest possible uppertype proposed for each type variable. For this
   * reason, the order of rules is important.
   * <p>
   * A subtype constraint can be written by 3 formats:
   * F1 - A <: T
   * F2 - T <: A
   * F3 - A1 <: A2
   * where 'Ai' and 'Ti' are respectively type variables and ground types (and
   * the constraint 'T1 <: T2' was already handled by garbage collecting in
   * <code>solveSubtypeConstraints</code> method.
   * These constraints are enumerated by cases:
   * <p>
   * CASE 1:
   * tCList = {A <: T} U tCList' and Map 
   *  -->  tCList = tCList' and {(A,T)} U Map if A is not in Var(tCList')
   * <p>
   * CASE 2:
   * tCList = {T <: A} U tCList' and Map 
   *  -->  tCList = tCList' and {(A,T)} U Map if A is not in Var(tCList')
   * <p>
   * CASE 3:
   * tCList = {A1 <: A2} U tCList' and Map 
   *  -->  tCList = tCList' and {(A1,A2)} U Map if Ai are not in tCList'
   * <p>
   * Considering combination of {F1 x F3}, {F2 x F3}, {F3 x F3} and {F1 x F2} (since 
   * {F1 x F1} and {F2 x F2} are treated by PHASE 4 of
   * <code>solveSubtypingConstraints</code> or by previous cases):
   * <p>
   * CASE 4 {F1 x F3} and {F3 x F1}:
   *    a) tCList = {A <: T, A <: A1} U tCList' and Map
   *      -->  tCList = {T <: A1} U [A/T]tCList' and {(A,T)} U Map
   *    b) tCList = {A <: T, A1 <: A} U tCList' and Map 
   *      --> CASE 3 for A1 <: A 
   *    c) tCList = {A <: T, A1 <: A2} U tCList' and Map 
   *      --> CASE 1 for A <: T and CASE 3 for A1 <: A2 
   * <p>
   * CASE 5 {F2 x F3} and {F3 x F2}:
   *    a) tCList = {T <: A, A1 <: A} U tCList' and Map 
   *      -->  tCList = {A1 <: T} U [A/T]tCList' and {(A,T)} U Map
   *    b) tCList = {T <: A, A <: A1} U tCList' and Map 
   *      --> CASE 3 for A <: A1 
   *    c) tCList = {T <: A, A1 <: A2} U tCList' and Map 
   *      --> CASE 1 for T <: A and CASE 3 for A1 <: A2
   * <p>
   * CASE 6 {F1 x F2} and {F2 x F1}:
   *    a) tCList = {A <: T, T <: A1} U tCList' and Map 
   *      --> CASE 1 for A <: T and CASE 2 for T <: A1 
   *    b) tCList = {A <: T, T1 <: A1} U tCList' and Map 
   *      --> CASE 1 for A <: T and CASE 2 for T1 <: A1 
   *    c) tCList = {A <: T, T1 <: A} U tCList' and Map 
   *      -->  tCList = {T1 <: T} U [A/T]tCList' and {(A,T)} U Map
   *     Note: in this case T1 is different from T (since the case where T1 is
   *     equals to T is treated by PHASE 1 of
   *     <code>solveSubtypingConstraints</code>.
   * <p>
   * CASE 7 {F3 x F3}:
   *    a) tCList = {A <: A1, A <: A2} U tCList' and Map 
   *      --> Nothing, java must infer the right type 
   *    b) tCList = {A <: A1, A2 <: A3} U tCList' and Map 
   *      --> Nothing, java must infer the right type 
   *    c) tCList = {A1 <: A, A2 <: A} U tCList' and Map 
   *      --> Nothing, java must infer the right type 
   *    d) tCList = {A1 <: A, A <: A2} U tCList' and Map 
   *      --> Nothing, java must infer the right type 
   * <p>
   * @param nkt an instance of object NewKernelTyper
   * @return    the subtype constraint list resulting
   */
  private TypeConstraintList enumerateSolutions(TypeConstraintList tCList) {
    TypeConstraintList newtCList = tCList;
matchBlockSolve :
    {
      %match(tCList) {
        /* CASE 1 */
        concTypeConstraint(leftTCL*,c1@Subtype[Type1=tVar@TypeVar[],Type2=groundType@!TypeVar[]],rightTCL*) -> {
          //DEBUG System.out.println("\nenumerateSolutions1: " + `c1);
          TypeConstraintList newLeftTCL = `leftTCL;
          TypeConstraintList newRightTCL = `rightTCL;
          if (!`findVar(tVar,concTypeConstraint(leftTCL,rightTCL))) {
            // Same code of cases 7 and 8 of solveEquationConstraints
            addSubstitution(`tVar,`groundType);
            newtCList =
              `replaceInSubtypingConstraints(concTypeConstraint(newLeftTCL*,newRightTCL*));
            break matchBlockSolve;
          }
        }

        /* CASE 2 */
        concTypeConstraint(leftTCL*,c1@Subtype[Type1=groundType@!TypeVar[],Type2=tVar@TypeVar[]],rightTCL*) -> {
          //DEBUG System.out.println("\nenumerateSolutions2: " + `c1);
          TypeConstraintList newLeftTCL = `leftTCL;
          TypeConstraintList newRightTCL = `rightTCL;
          if (!`findVar(tVar,concTypeConstraint(leftTCL,rightTCL))) {
            addSubstitution(`tVar,`groundType);
            newtCList =
              `replaceInSubtypingConstraints(concTypeConstraint(newLeftTCL*,newRightTCL*));
            break matchBlockSolve;
          }
        }

        /* CASE 3 */
        concTypeConstraint(leftTCL*,c1@Subtype[Type1=tVar1@TypeVar[],Type2=tVar2@TypeVar[]],rightTCL*) -> {
          //DEBUG System.out.println("\nenumerateSolutions3: " + `c1);
          TypeConstraintList newLeftTCL = `leftTCL;
          TypeConstraintList newRightTCL = `rightTCL;
          if (!`findVar(tVar1,concTypeConstraint(leftTCL,rightTCL)) &&
              !`findVar(tVar2,concTypeConstraint(leftTCL,rightTCL))) {
            // Same code of cases 7 and 8 of solveEquationConstraints
            addSubstitution(`tVar1,`tVar2);
            newtCList =
              `replaceInSubtypingConstraints(concTypeConstraint(newLeftTCL*,newRightTCL*));
            break matchBlockSolve;
          }
        }

        /* CASE 4a */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=tVar1@TypeVar[],Type2=groundType@!TypeVar[]],tcl2*,c2@Subtype[Type1=tVar1,Type2=tVar2@TypeVar[],Info=info],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions4a: " + `c1 + " and " + `c2);
          addSubstitution(`tVar1,`groundType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c2,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 4a (symmetric) */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=tVar1,Type2=tVar2@TypeVar[],Info=info],tcl2*,c2@Subtype[Type1=tVar1@TypeVar[],Type2=groundType@!TypeVar[]],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions4a-sym: " + `c1 + " and " + `c2);
          addSubstitution(`tVar1,`groundType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c1,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 5a */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=groundType@!TypeVar[],Type2=tVar1@TypeVar[]],tcl2*,c2@Subtype[Type1=tVar2@TypeVar[],Type2=tVar1,Info=info],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions5a: " + `c1 + " and " + `c2);
          addSubstitution(`tVar1,`groundType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c2,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 5a (symmetric) */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=tVar2@TypeVar[],Type2=tVar1,Info=info],tcl2*,c2@Subtype[Type1=groundType@!TypeVar[],Type2=tVar1@TypeVar[]],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions5a-sym: " + `c1 + " and " + `c2);
          addSubstitution(`tVar1,`groundType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c1,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 6c */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=tVar@TypeVar[],Type2=groundSupType@!TypeVar[]],tcl2*,c2@Subtype[Type1=groundSubType@!TypeVar[],Type2=tVar,Info=info],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions6c: " + `c1 + " and " + `c2);
          addSubstitution(`tVar,`groundSupType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c2,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 6c (symmetric) */
        concTypeConstraint(tcl1*,c1@Subtype[Type1=groundSubType@!TypeVar[],Type2=tVar,Info=info],tcl2*,c2@Subtype[Type1=tVar@TypeVar[],Type2=groundSupType@!TypeVar[]],tcl3*) -> {
          //DEBUG System.out.println("\nenumerateSolutions6c: " + `c1 + " and " + `c2);
          addSubstitution(`tVar,`groundSupType);
          newtCList =
            `replaceInSubtypingConstraints(concTypeConstraint(c1,tcl1*,tcl2*,tcl3*));
          break matchBlockSolve;
        }

        /* CASE 7 */
        _ -> { 
          //DEBUG System.out.println("When none of previous rules can be applied!");
          //DEBUG System.out.println("newtCList = " + `tCList + '\n');
        }
      }
    }
    return newtCList;
  }

  /**
   * The method <code>isTypeVar</code> checks if a given type is a type variable.
   * @param type the type to be checked
   * @return     'true' if teh type is a variable type
   *             'false' otherwise
   */
  private boolean isTypeVar(TomType type) {
    %match(type) {
      TypeVar(_,_) -> { return true; }
    }
    return false;
  }

  /**
   * The method <code>printError</code> prints an 'incompatible types' message
   * enriched by informations about a given type constraint.
   * @param tConstraint  the type constraint to be printed
   */
  private void printError(TypeConstraint tConstraint) {
    %match {
      (Equation|Subtype)[Type1=tType1,Type2=tType2,Info=info] << tConstraint &&
        Type[TomType=tName1] << tType1 &&
        Type[TomType=tName2] << tType2 &&
        PairNameOptions(Name(termName),optionList) << info
        -> {
          Option option = TomBase.findOriginTracking(`optionList);
          %match(option) {
            OriginTracking(_,line,fileName) -> {
              if(lazyType==false) {
                TomMessage.error(logger,`fileName, `line,
                    TomMessage.incompatibleTypes,`tName1,`tName2,`termName); 
              }
            }
          }
        }
    }
  }

  /**
   * The method <code>replaceInCode</code> calls the strategy
   * <code>replaceFreshTypeVar</code> to apply substitution on each type variable occuring in
   * a given Code.
   * @param code the code to be replaced
   * @return     the replaced code resulting
   */
  private Code replaceInCode(Code code) {
    Code replacedCode = code;
    try {
      replacedCode = `InnermostId(replaceFreshTypeVar(this)).visitLight(code);
      `InnermostId(checkTypeOfBQVariables(this)).visitLight(replacedCode);
    } catch(tom.library.sl.VisitFailure e) {
      throw new TomRuntimeException("replaceInCode: failure on " +
          replacedCode);
    }
    return replacedCode;
  }

  /**
   * The method <code>replaceInSymbolTable</code> calls the strategy
   * <code>replaceFreshTypeVar</code> to apply substitution on each type variable occuring in
   * the Symbol Table.
   */
  private void replaceInSymbolTable() {
    for(String tomName:symbolTable.keySymbolIterable()) {
      //DEBUG System.out.println("replaceInSymboltable() - tomName : " + tomName);
      TomSymbol tSymbol = getSymbolFromName(tomName);
      //DEBUG System.out.println("replaceInSymboltable() - tSymbol before strategy: "
      //DEBUG     + tSymbol);
      try {
        tSymbol = `InnermostId(replaceFreshTypeVar(this)).visitLight(tSymbol);
      } catch(tom.library.sl.VisitFailure e) {
        throw new TomRuntimeException("replaceInSymbolTable: failure on " +
            tSymbol);
      }
      //DEBUG System.out.println("replaceInSymboltable() - tSymbol after strategy: "
      //DEBUG     + tSymbol);
      symbolTable.putSymbol(tomName,tSymbol);
    }
  }

  /**
   * The class <code>replaceFreshTypeVar</code> is generated from a strategy
   * which replace each type variable occurring in a given expression by its corresponding substitution. 
   * @param nkt an instance of object NewKernelTyper
   * @return    the expression resulting of a transformation
   */
  %strategy replaceFreshTypeVar(nkt:NewKernelTyper) extends Identity() {
    visit TomType {
      typeVar@TypeVar(_,_) -> {
        if (nkt.substitutions.containsKey(`typeVar)) {
          return nkt.substitutions.get(`typeVar);
        } 
      }
    }
  }

  /**
   * The class <code>checkTypeOfBQVariables</code> is generated from a strategy
   * which checks if all cast type (in a MatchConstraint) have been inferred. In
   * the negative case, a 'canotGuessMatchType' message error is printed.
   * @param nkt an instance of object NewKernelTyper
   */
  %strategy checkTypeOfBQVariables(nkt:NewKernelTyper) extends Identity() {
    visit Constraint {
      MatchConstraint[Pattern=Variable[],Subject=BQVariable[Options=oList,AstName=Name(name),AstType=TypeVar(_,_)]] -> {
        Option option = TomBase.findOriginTracking(`oList);
        %match(option) {
          OriginTracking(_,line,fileName) -> {
            TomMessage.error(logger,`fileName, `line,
                TomMessage.cannotGuessMatchType,`name); 
          }
        }
      }
    }
  }

  /**
   * The method <code>printGeneratedConstraints</code> prints braces and calls the method
   * <code>printEachConstraint</code> for a given list.
   * @param tCList the type constraint list to be printed
   */
  public void printGeneratedConstraints(TypeConstraintList tCList) {
    %match(tCList) {
      !concTypeConstraint() -> { 
        System.out.print("\n------ Type Constraints : \n {");
        printEachConstraint(tCList);
        System.out.print("}");
      }
    }
  }

  /**
   * The method <code>printEachConstraint</code> prints symbols '=' and '<:' and calls the method
   * <code>printType</code> for each type occurring in a given type constraint.
   * @param tCList the type constraint list to be printed
   */
  public void printEachConstraint(TypeConstraintList tCList) {
    %match(tCList) {
      concTypeConstraint(Equation(type1,type2,_),tailtCList*) -> {
        printType(`type1);
        System.out.print(" = ");
        printType(`type2);
        if (`tailtCList != `concTypeConstraint()) {
            System.out.print(", "); 
            printEachConstraint(`tailtCList);
        }
      }

      concTypeConstraint(Subtype(type1,type2,_),tailtCList*) -> {
        printType(`type1);
        System.out.print(" <: ");
        printType(`type2);
        if (`tailtCList != `concTypeConstraint()) {
            System.out.print(", "); 
            printEachConstraint(`tailtCList);
        }
      }
    }
  }
 
  /**
   * The method <code>printType</code> prints a given type.
   * @param tCList the type to be printed
   */   
  public void printType(TomType type) {
    System.out.print(type);
  }
} // NewKernelTyper
