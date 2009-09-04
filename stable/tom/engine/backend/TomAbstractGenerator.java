/*
 *
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2009, INRIA
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
 *
 **/

package tom.engine.backend;

import java.io.IOException;

import tom.engine.TomBase;
import tom.engine.exception.TomRuntimeException;

import tom.engine.adt.tomsignature.*;
import tom.engine.adt.tomconstraint.types.*;
import tom.engine.adt.tomdeclaration.types.*;
import tom.engine.adt.tomexpression.types.*;
import tom.engine.adt.tominstruction.types.*;
import tom.engine.adt.tomname.types.*;
import tom.engine.adt.tomoption.types.*;
import tom.engine.adt.tomsignature.types.*;
import tom.engine.adt.tomterm.types.*;
import tom.engine.adt.tomslot.types.*;
import tom.engine.adt.tomtype.types.*;

import tom.engine.tools.OutputCode;
import tom.engine.tools.SymbolTable;
import tom.platform.OptionManager;

import aterm.*;

public abstract class TomAbstractGenerator {

  protected OutputCode output;
  protected OptionManager optionManager;
  protected SymbolTable symbolTable;
  protected boolean prettyMode;

  public TomAbstractGenerator(OutputCode output, OptionManager optionManager,
                              SymbolTable symbolTable) {
    this.symbolTable = symbolTable;
    this.optionManager = optionManager;
    this.output = output;
    this.prettyMode = ((Boolean)optionManager.getOptionValue("pretty")).booleanValue();
  }

  protected SymbolTable getSymbolTable(String moduleName) {
    //TODO//
    //Using of the moduleName
    ////////
    return symbolTable;
  }

  protected TomSymbol getSymbolFromName(String tomName) {
    return TomBase.getSymbolFromName(tomName, symbolTable);
  }

  protected TomSymbol getSymbolFromType(TomType tomType) {
    return TomBase.getSymbolFromType(tomType, symbolTable);
  }

  protected TomType getTermType(TomTerm t) {
    return TomBase.getTermType(t, symbolTable);
  }

  protected TomType getUniversalType() {
    return symbolTable.getUniversalType();
  }
// ------------------------------------------------------------
        private static   tom.engine.adt.tomterm.types.TomList  tom_append_list_concTomTerm( tom.engine.adt.tomterm.types.TomList l1,  tom.engine.adt.tomterm.types.TomList  l2) {     if( l1.isEmptyconcTomTerm() ) {       return l2;     } else if( l2.isEmptyconcTomTerm() ) {       return l1;     } else if(  l1.getTailconcTomTerm() .isEmptyconcTomTerm() ) {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,l2) ;     } else {       return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( l1.getHeadconcTomTerm() ,tom_append_list_concTomTerm( l1.getTailconcTomTerm() ,l2)) ;     }   }   private static   tom.engine.adt.tomterm.types.TomList  tom_get_slice_concTomTerm( tom.engine.adt.tomterm.types.TomList  begin,  tom.engine.adt.tomterm.types.TomList  end, tom.engine.adt.tomterm.types.TomList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTomTerm()  ||  (end== tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make( begin.getHeadconcTomTerm() ,( tom.engine.adt.tomterm.types.TomList )tom_get_slice_concTomTerm( begin.getTailconcTomTerm() ,end,tail)) ;   }    
// ------------------------------------------------------------

  protected TomTerm operatorsTogenerate(TomTerm subject)throws IOException {
    //System.out.println("Subject "+subject);
    //collectMake(subject);
    return subject;
  }

  /**
   * Generate the goal language
   * 
   * @param deep 
   * 		The distance from the right side (allows the computation of the column number)
   */
  protected void generate(int deep, TomTerm subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Tom) ) {


        generateList(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getTomList() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.TomInclude) ) {


        generateListInclude(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getTomList() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildConstant) ) { tom.engine.adt.tomname.types.TomName  tomMatch34NameNumber_freshVar_7= (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() ;if ( (tomMatch34NameNumber_freshVar_7 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_name= tomMatch34NameNumber_freshVar_7.getString() ;


        if(tom_name.charAt(0)=='\'' && tom_name.charAt(tom_name.length()-1)=='\'') {
          String substring = tom_name.substring(1,tom_name.length()-1);
          //System.out.println("BuildConstant: " + substring);
          substring = substring.replace("\\","\\\\"); // replace backslash by backslash-backslash
          substring = substring.replace("'","\\'"); // replace quote by backslash-quote
          output.write("'" + substring + "'");
          return;
        }
        output.write(tom_name);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildTerm) ) { tom.engine.adt.tomname.types.TomName  tomMatch34NameNumber_freshVar_12= (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() ;if ( (tomMatch34NameNumber_freshVar_12 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildTerm(deep, tomMatch34NameNumber_freshVar_12.getString() , (( tom.engine.adt.tomterm.types.TomTerm )subject).getArgs() , (( tom.engine.adt.tomterm.types.TomTerm )subject).getModuleName() )


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch34NameNumber_freshVar_20= false ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildEmptyList) ) {tomMatch34NameNumber_freshVar_20= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildEmptyArray) ) {tomMatch34NameNumber_freshVar_20= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildConsList) ) {tomMatch34NameNumber_freshVar_20= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildAppendList) ) {tomMatch34NameNumber_freshVar_20= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildConsArray) ) {tomMatch34NameNumber_freshVar_20= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.BuildAppendArray) ) {tomMatch34NameNumber_freshVar_20= true ;}}}}}}if ((tomMatch34NameNumber_freshVar_20 ==  true )) {


        buildListOrArray(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject), moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.FunctionCall) ) { tom.engine.adt.tomname.types.TomName  tomMatch34NameNumber_freshVar_22= (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() ;if ( (tomMatch34NameNumber_freshVar_22 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        buildFunctionCall(deep, tomMatch34NameNumber_freshVar_22.getString() ,  (( tom.engine.adt.tomterm.types.TomTerm )subject).getArgs() , moduleName);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Composite) ) {


        generateList(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getArgs() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {boolean tomMatch34NameNumber_freshVar_32= false ;if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {tomMatch34NameNumber_freshVar_32= true ;} else {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {tomMatch34NameNumber_freshVar_32= true ;}}if ((tomMatch34NameNumber_freshVar_32 ==  true )) {


        output.write(deep,getVariableName((( tom.engine.adt.tomterm.types.TomTerm )subject)));
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.TargetLanguageToTomTerm) ) {


        generateTargetLanguage(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getTl() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm) ) {


        generateExpression(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstExpression() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.InstructionToTomTerm) ) {


        generateInstruction(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstInstruction() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.DeclarationToTomTerm) ) {


        generateDeclaration(deep, (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstDeclaration() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) { tom.engine.adt.tomterm.types.TomTerm  tom_t=(( tom.engine.adt.tomterm.types.TomTerm )subject);


        System.out.println("Cannot generate code for: " + tom_t);
        throw new TomRuntimeException("Cannot generate code for: " + tom_t);
      }}}

  }

  protected String getVariableName(TomTerm var) {
    {{if ( (var instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )var) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch35NameNumber_freshVar_1= (( tom.engine.adt.tomterm.types.TomTerm )var).getAstName() ;if ( (tomMatch35NameNumber_freshVar_1 instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {

        return ("tom" + TomBase.tomNumberListToString( tomMatch35NameNumber_freshVar_1.getNumberList() ));
      }}}}{if ( (var instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )var) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch35NameNumber_freshVar_6= (( tom.engine.adt.tomterm.types.TomTerm )var).getAstName() ;if ( (tomMatch35NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        return  tomMatch35NameNumber_freshVar_6.getString() ;
      }}}}{if ( (var instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )var) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) { tom.engine.adt.tomname.types.TomName  tomMatch35NameNumber_freshVar_11= (( tom.engine.adt.tomterm.types.TomTerm )var).getAstName() ;if ( (tomMatch35NameNumber_freshVar_11 instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {


        return ("tom" + TomBase.tomNumberListToString( tomMatch35NameNumber_freshVar_11.getNumberList() ));
      }}}}{if ( (var instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )var) instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) { tom.engine.adt.tomname.types.TomName  tomMatch35NameNumber_freshVar_16= (( tom.engine.adt.tomterm.types.TomTerm )var).getAstName() ;if ( (tomMatch35NameNumber_freshVar_16 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        return  tomMatch35NameNumber_freshVar_16.getString() ;
      }}}}}

    return null;
  }
  public void generateExpression(int deep, Expression subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Code) ) {

        output.write( (( tom.engine.adt.tomexpression.types.Expression )subject).getCode() );
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Integer) ) {


        output.write( (( tom.engine.adt.tomexpression.types.Expression )subject).getvalue() );
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Negation) ) {


        buildExpNegation(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Conditional) ) {


        buildExpConditional(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getCond() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getThen() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getElse() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.And) ) {


        buildExpAnd(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Or) ) {


        buildExpOr(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GreaterThan) ) {


        buildExpGreaterThan(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GreaterOrEqualThan) ) {


        buildExpGreaterOrEqualThan(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.LessThan) ) {


        buildExpLessThan(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.LessOrEqualThan) ) {


        buildExpLessOrEqualThan(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getArg2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Bottom) ) {



        buildExpBottom(deep, (( tom.engine.adt.tomexpression.types.Expression )subject).getTomType() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.TrueTL) ) {


        buildExpTrue(deep);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.FalseTL) ) {


        buildExpFalse(deep);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.IsEmptyList) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_46= (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() ;if ( (tomMatch36NameNumber_freshVar_46 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { tom.engine.adt.tomterm.types.TomTerm  tom_expList= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;buildExpIsEmptyList(deep, tomMatch36NameNumber_freshVar_46.getString() ,getTermType(tom_expList),tom_expList,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.IsEmptyArray) ) { tom.engine.adt.tomterm.types.TomTerm  tom_expArray= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;


        buildExpIsEmptyArray(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() , getTermType(tom_expArray),  (( tom.engine.adt.tomexpression.types.Expression )subject).getIndex() , tom_expArray, moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.EqualTerm) ) {buildExpEqualTerm(deep, (( tom.engine.adt.tomexpression.types.Expression )subject).getTomType() , (( tom.engine.adt.tomexpression.types.Expression )subject).getKid1() , (( tom.engine.adt.tomexpression.types.Expression )subject).getKid2() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.IsSort) ) { tom.engine.adt.tomtype.types.TomType  tomMatch36NameNumber_freshVar_62= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstType() ;boolean tomMatch36NameNumber_freshVar_67= false ; String  tomMatch36NameNumber_freshVar_65= "" ;if ( (tomMatch36NameNumber_freshVar_62 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {{tomMatch36NameNumber_freshVar_67= true ;tomMatch36NameNumber_freshVar_65= tomMatch36NameNumber_freshVar_62.getTomType() ;}} else {if ( (tomMatch36NameNumber_freshVar_62 instanceof tom.engine.adt.tomtype.types.tomtype.TypeWithSymbol) ) {{tomMatch36NameNumber_freshVar_67= true ;tomMatch36NameNumber_freshVar_65= tomMatch36NameNumber_freshVar_62.getTomType() ;}}}if ((tomMatch36NameNumber_freshVar_67 ==  true )) {buildExpIsSort(deep,tomMatch36NameNumber_freshVar_65, (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.IsFsym) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_69= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() ;if ( (tomMatch36NameNumber_freshVar_69 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        buildExpIsFsym(deep,  tomMatch36NameNumber_freshVar_69.getString() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() , moduleName);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Cast) ) { tom.engine.adt.tomtype.types.TomType  tomMatch36NameNumber_freshVar_75= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstType() ;boolean tomMatch36NameNumber_freshVar_81= false ; tom.engine.adt.tomtype.types.TomType  tomMatch36NameNumber_freshVar_78= null ;if ( (tomMatch36NameNumber_freshVar_75 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {{tomMatch36NameNumber_freshVar_81= true ;tomMatch36NameNumber_freshVar_78= tomMatch36NameNumber_freshVar_75.getTlType() ;}} else {if ( (tomMatch36NameNumber_freshVar_75 instanceof tom.engine.adt.tomtype.types.tomtype.TypeWithSymbol) ) {{tomMatch36NameNumber_freshVar_81= true ;tomMatch36NameNumber_freshVar_78= tomMatch36NameNumber_freshVar_75.getTlType() ;}}}if ((tomMatch36NameNumber_freshVar_81 ==  true )) {if ( (tomMatch36NameNumber_freshVar_78 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {


        buildExpCast(deep, tomMatch36NameNumber_freshVar_78,  (( tom.engine.adt.tomexpression.types.Expression )subject).getSource() , moduleName);
        return;
      }}}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Cast) ) { tom.engine.adt.tomtype.types.TomType  tomMatch36NameNumber_freshVar_83= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstType() ;if ( (tomMatch36NameNumber_freshVar_83 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {


        buildExpCast(deep, tomMatch36NameNumber_freshVar_83,  (( tom.engine.adt.tomexpression.types.Expression )subject).getSource() , moduleName);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSlot) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_89= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch36NameNumber_freshVar_91= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;if ( (tomMatch36NameNumber_freshVar_89 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {boolean tomMatch36NameNumber_freshVar_96= false ;if ( (tomMatch36NameNumber_freshVar_91 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {tomMatch36NameNumber_freshVar_96= true ;} else {if ( (tomMatch36NameNumber_freshVar_91 instanceof tom.engine.adt.tomterm.types.tomterm.BuildTerm) ) {tomMatch36NameNumber_freshVar_96= true ;} else {if ( (tomMatch36NameNumber_freshVar_91 instanceof tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm) ) {tomMatch36NameNumber_freshVar_96= true ;}}}if ((tomMatch36NameNumber_freshVar_96 ==  true )) {buildExpGetSlot(deep, tomMatch36NameNumber_freshVar_89.getString() , (( tom.engine.adt.tomexpression.types.Expression )subject).getSlotNameString() ,tomMatch36NameNumber_freshVar_91,moduleName)


;
        return;
      }}}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSlot) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_99= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch36NameNumber_freshVar_101= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;if ( (tomMatch36NameNumber_freshVar_99 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch36NameNumber_freshVar_101 instanceof tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm) ) {if ( ( tomMatch36NameNumber_freshVar_101.getAstExpression()  instanceof tom.engine.adt.tomexpression.types.expression.GetSlot) ) {buildExpGetSlot(deep, tomMatch36NameNumber_freshVar_99.getString() , (( tom.engine.adt.tomexpression.types.Expression )subject).getSlotNameString() ,tomMatch36NameNumber_freshVar_101,moduleName)

;
        return;
      }}}}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetHead) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_109= (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() ;if ( (tomMatch36NameNumber_freshVar_109 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { tom.engine.adt.tomterm.types.TomTerm  tom_exp= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;buildExpGetHead(deep, tomMatch36NameNumber_freshVar_109.getString() ,getTermType(tom_exp), (( tom.engine.adt.tomexpression.types.Expression )subject).getCodomain() ,tom_exp,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetTail) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_116= (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() ;if ( (tomMatch36NameNumber_freshVar_116 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { tom.engine.adt.tomterm.types.TomTerm  tom_exp= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;buildExpGetTail(deep, tomMatch36NameNumber_freshVar_116.getString() ,getTermType(tom_exp),tom_exp,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.AddOne) ) {


        buildAddOne(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.SubstractOne) ) {


        buildSubstractOne(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.Substract) ) {


        buildSubstract(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getTerm1() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getTerm2() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSize) ) { tom.engine.adt.tomterm.types.TomTerm  tom_exp= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;


        buildExpGetSize(deep, (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() ,getTermType(tom_exp), tom_exp, moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetElement) ) { tom.engine.adt.tomterm.types.TomTerm  tom_varName= (( tom.engine.adt.tomexpression.types.Expression )subject).getVariable() ;


        buildExpGetElement(deep, (( tom.engine.adt.tomexpression.types.Expression )subject).getOpname() ,getTermType(tom_varName), (( tom.engine.adt.tomexpression.types.Expression )subject).getCodomain() , tom_varName,  (( tom.engine.adt.tomexpression.types.Expression )subject).getIndex() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceList) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_142= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() ;if ( (tomMatch36NameNumber_freshVar_142 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        buildExpGetSliceList(deep,  tomMatch36NameNumber_freshVar_142.getString() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableBeginAST() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableEndAST() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getTail() ,moduleName);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.GetSliceArray) ) { tom.engine.adt.tomname.types.TomName  tomMatch36NameNumber_freshVar_150= (( tom.engine.adt.tomexpression.types.Expression )subject).getAstName() ;if ( (tomMatch36NameNumber_freshVar_150 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {


        buildExpGetSliceArray(deep,  tomMatch36NameNumber_freshVar_150.getString() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getSubjectListName() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableBeginAST() ,  (( tom.engine.adt.tomexpression.types.Expression )subject).getVariableEndAST() , moduleName);
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.TomTermToExpression) ) {


        generate(deep, (( tom.engine.adt.tomexpression.types.Expression )subject).getAstTerm() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) {if ( ((( tom.engine.adt.tomexpression.types.Expression )subject) instanceof tom.engine.adt.tomexpression.types.expression.TomInstructionToExpression) ) {


        generateInstruction(deep,  (( tom.engine.adt.tomexpression.types.Expression )subject).getInstruction() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomexpression.types.Expression) ) { tom.engine.adt.tomexpression.types.Expression  tom_t=(( tom.engine.adt.tomexpression.types.Expression )subject);


        System.out.println("Cannot generate code for expression: " + tom_t);
        throw new TomRuntimeException("Cannot generate code for expression: " + tom_t);
      }}}

  }

  /**
   * generates var[index] 
   */
  protected void generateArray(int deep, TomTerm subject, TomTerm index, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch37NameNumber_freshVar_1= (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() ;if ( (tomMatch37NameNumber_freshVar_1 instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {
        
        output.write("tom" + TomBase.tomNumberListToString( tomMatch37NameNumber_freshVar_1.getNumberList() ));        
      }}}}{if ( (subject instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )subject) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch37NameNumber_freshVar_6= (( tom.engine.adt.tomterm.types.TomTerm )subject).getAstName() ;if ( (tomMatch37NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

        output.write( tomMatch37NameNumber_freshVar_6.getString() );        
      }}}}}{{if ( (index instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )index) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch38NameNumber_freshVar_1= (( tom.engine.adt.tomterm.types.TomTerm )index).getAstName() ;if ( (tomMatch38NameNumber_freshVar_1 instanceof tom.engine.adt.tomname.types.tomname.PositionName) ) {



        output.write("[");
        output.write("tom" + TomBase.tomNumberListToString( tomMatch38NameNumber_freshVar_1.getNumberList() ));
        output.write("]");        
      }}}}{if ( (index instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )index) instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch38NameNumber_freshVar_6= (( tom.engine.adt.tomterm.types.TomTerm )index).getAstName() ;if ( (tomMatch38NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {

        output.write("[");
        output.write( tomMatch38NameNumber_freshVar_6.getString() );
        output.write("]");
      }}}}{if ( (index instanceof tom.engine.adt.tomterm.types.TomTerm) ) {if ( ((( tom.engine.adt.tomterm.types.TomTerm )index) instanceof tom.engine.adt.tomterm.types.tomterm.ExpressionToTomTerm) ) { tom.engine.adt.tomexpression.types.Expression  tomMatch38NameNumber_freshVar_11= (( tom.engine.adt.tomterm.types.TomTerm )index).getAstExpression() ;if ( (tomMatch38NameNumber_freshVar_11 instanceof tom.engine.adt.tomexpression.types.expression.Integer) ) {

        output.write("[");
        output.write( tomMatch38NameNumber_freshVar_11.getvalue() );
        output.write("]");  
      }}}}}
    
  } 

  public void generateInstruction(int deep, Instruction subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.TargetLanguageToInstruction) ) {generateTargetLanguage(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getTl() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.TomTermToInstruction) ) {generate(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getTom() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.ExpressionToInstruction) ) {generateExpression(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getExpr() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Nop) ) {


        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Assign) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_12= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;boolean tomMatch39NameNumber_freshVar_17= false ; tom.engine.adt.tomoption.types.OptionList  tomMatch39NameNumber_freshVar_15= null ;if ( (tomMatch39NameNumber_freshVar_12 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch39NameNumber_freshVar_17= true ;tomMatch39NameNumber_freshVar_15= tomMatch39NameNumber_freshVar_12.getOption() ;}} else {if ( (tomMatch39NameNumber_freshVar_12 instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch39NameNumber_freshVar_17= true ;tomMatch39NameNumber_freshVar_15= tomMatch39NameNumber_freshVar_12.getOption() ;}}}if ((tomMatch39NameNumber_freshVar_17 ==  true )) {buildAssign(deep,tomMatch39NameNumber_freshVar_12,tomMatch39NameNumber_freshVar_15, (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Assign) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_19= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;boolean tomMatch39NameNumber_freshVar_23= false ;if ( (tomMatch39NameNumber_freshVar_19 instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariable) ) {tomMatch39NameNumber_freshVar_23= true ;} else {if ( (tomMatch39NameNumber_freshVar_19 instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariableStar) ) {tomMatch39NameNumber_freshVar_23= true ;}}if ((tomMatch39NameNumber_freshVar_23 ==  true )) {


        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.AssignArray) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_25= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;if ( (tomMatch39NameNumber_freshVar_25 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {buildAssignArray(deep,tomMatch39NameNumber_freshVar_25, tomMatch39NameNumber_freshVar_25.getOption() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getIndex() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {boolean tomMatch39NameNumber_freshVar_38= false ; tom.engine.adt.tominstruction.types.Instruction  tomMatch39NameNumber_freshVar_34= null ; tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_32= null ; tom.engine.adt.tomexpression.types.Expression  tomMatch39NameNumber_freshVar_33= null ;if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Let) ) {{tomMatch39NameNumber_freshVar_38= true ;tomMatch39NameNumber_freshVar_32= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;tomMatch39NameNumber_freshVar_33= (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ;tomMatch39NameNumber_freshVar_34= (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ;}} else {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.LetRef) ) {{tomMatch39NameNumber_freshVar_38= true ;tomMatch39NameNumber_freshVar_32= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;tomMatch39NameNumber_freshVar_33= (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ;tomMatch39NameNumber_freshVar_34= (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ;}}}if ((tomMatch39NameNumber_freshVar_38 ==  true )) {boolean tomMatch39NameNumber_freshVar_37= false ;if ( (tomMatch39NameNumber_freshVar_32 instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariable) ) {tomMatch39NameNumber_freshVar_37= true ;} else {if ( (tomMatch39NameNumber_freshVar_32 instanceof tom.engine.adt.tomterm.types.tomterm.UnamedVariableStar) ) {tomMatch39NameNumber_freshVar_37= true ;}}if ((tomMatch39NameNumber_freshVar_37 ==  true )) {generateInstruction(deep,tomMatch39NameNumber_freshVar_34,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Let) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_42= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;boolean tomMatch39NameNumber_freshVar_53= false ; tom.engine.adt.tomoption.types.OptionList  tomMatch39NameNumber_freshVar_46= null ; tom.engine.adt.tomtype.types.TomType  tomMatch39NameNumber_freshVar_47= null ;if ( (tomMatch39NameNumber_freshVar_42 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch39NameNumber_freshVar_53= true ;tomMatch39NameNumber_freshVar_46= tomMatch39NameNumber_freshVar_42.getOption() ;tomMatch39NameNumber_freshVar_47= tomMatch39NameNumber_freshVar_42.getAstType() ;}} else {if ( (tomMatch39NameNumber_freshVar_42 instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch39NameNumber_freshVar_53= true ;tomMatch39NameNumber_freshVar_46= tomMatch39NameNumber_freshVar_42.getOption() ;tomMatch39NameNumber_freshVar_47= tomMatch39NameNumber_freshVar_42.getAstType() ;}}}if ((tomMatch39NameNumber_freshVar_53 ==  true )) { tom.engine.adt.tomoption.types.OptionList  tom_list=tomMatch39NameNumber_freshVar_46; tom.engine.adt.tomtype.types.TomType  tom_type=tomMatch39NameNumber_freshVar_47; tom.engine.adt.tomterm.types.TomTerm  tom_var=tomMatch39NameNumber_freshVar_42; tom.engine.adt.tomexpression.types.Expression  tom_exp= (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ; tom.engine.adt.tominstruction.types.Instruction  tom_body= (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ;if ( (tom_type instanceof tom.engine.adt.tomtype.types.TomType) ) {boolean tomMatch39NameNumber_freshVar_52= false ; tom.engine.adt.tomtype.types.TomType  tomMatch39NameNumber_freshVar_49= null ;if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {{tomMatch39NameNumber_freshVar_52= true ;tomMatch39NameNumber_freshVar_49= (( tom.engine.adt.tomtype.types.TomType )tom_type).getTlType() ;}} else {if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.TypeWithSymbol) ) {{tomMatch39NameNumber_freshVar_52= true ;tomMatch39NameNumber_freshVar_49= (( tom.engine.adt.tomtype.types.TomType )tom_type).getTlType() ;}}}if ((tomMatch39NameNumber_freshVar_52 ==  true )) {buildLet(deep,tom_var,tom_list,tomMatch39NameNumber_freshVar_49,tom_exp,tom_body,moduleName)


;
        return;
      }}if ( (tom_type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {buildLet(deep,tom_var,tom_list,(( tom.engine.adt.tomtype.types.TomType )tom_type),tom_exp,tom_body,moduleName);         return;       }}}}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.LetRef) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch39NameNumber_freshVar_57= (( tom.engine.adt.tominstruction.types.Instruction )subject).getVariable() ;boolean tomMatch39NameNumber_freshVar_68= false ; tom.engine.adt.tomtype.types.TomType  tomMatch39NameNumber_freshVar_62= null ; tom.engine.adt.tomoption.types.OptionList  tomMatch39NameNumber_freshVar_61= null ;if ( (tomMatch39NameNumber_freshVar_57 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {{tomMatch39NameNumber_freshVar_68= true ;tomMatch39NameNumber_freshVar_61= tomMatch39NameNumber_freshVar_57.getOption() ;tomMatch39NameNumber_freshVar_62= tomMatch39NameNumber_freshVar_57.getAstType() ;}} else {if ( (tomMatch39NameNumber_freshVar_57 instanceof tom.engine.adt.tomterm.types.tomterm.VariableStar) ) {{tomMatch39NameNumber_freshVar_68= true ;tomMatch39NameNumber_freshVar_61= tomMatch39NameNumber_freshVar_57.getOption() ;tomMatch39NameNumber_freshVar_62= tomMatch39NameNumber_freshVar_57.getAstType() ;}}}if ((tomMatch39NameNumber_freshVar_68 ==  true )) { tom.engine.adt.tomoption.types.OptionList  tom_list=tomMatch39NameNumber_freshVar_61; tom.engine.adt.tomtype.types.TomType  tom_type=tomMatch39NameNumber_freshVar_62; tom.engine.adt.tomterm.types.TomTerm  tom_var=tomMatch39NameNumber_freshVar_57; tom.engine.adt.tomexpression.types.Expression  tom_exp= (( tom.engine.adt.tominstruction.types.Instruction )subject).getSource() ; tom.engine.adt.tominstruction.types.Instruction  tom_body= (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ;if ( (tom_type instanceof tom.engine.adt.tomtype.types.TomType) ) {boolean tomMatch39NameNumber_freshVar_67= false ; tom.engine.adt.tomtype.types.TomType  tomMatch39NameNumber_freshVar_64= null ;if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {{tomMatch39NameNumber_freshVar_67= true ;tomMatch39NameNumber_freshVar_64= (( tom.engine.adt.tomtype.types.TomType )tom_type).getTlType() ;}} else {if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.TypeWithSymbol) ) {{tomMatch39NameNumber_freshVar_67= true ;tomMatch39NameNumber_freshVar_64= (( tom.engine.adt.tomtype.types.TomType )tom_type).getTlType() ;}}}if ((tomMatch39NameNumber_freshVar_67 ==  true )) {buildLetRef(deep,tom_var,tom_list,tomMatch39NameNumber_freshVar_64,tom_exp,tom_body,moduleName)


;
        return;
      }}if ( (tom_type instanceof tom.engine.adt.tomtype.types.TomType) ) {if ( ((( tom.engine.adt.tomtype.types.TomType )tom_type) instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {buildLetRef(deep,tom_var,tom_list,(( tom.engine.adt.tomtype.types.TomType )tom_type),tom_exp,tom_body,moduleName);         return;       }}}}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.AbstractBlock) ) {


        //`generateInstructionList(deep, instList);
        buildInstructionSequence(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getInstList() ,moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.UnamedBlock) ) {buildUnamedBlock(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getInstList() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.NamedBlock) ) {buildNamedBlock(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getBlockName() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getInstList() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.If) ) {if ( ( (( tom.engine.adt.tominstruction.types.Instruction )subject).getFailureInst()  instanceof tom.engine.adt.tominstruction.types.instruction.Nop) ) {buildIf(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getCondition() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getSuccesInst() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.If) ) {if ( ( (( tom.engine.adt.tominstruction.types.Instruction )subject).getSuccesInst()  instanceof tom.engine.adt.tominstruction.types.instruction.Nop) ) {buildIf(deep, tom.engine.adt.tomexpression.types.expression.Negation.make( (( tom.engine.adt.tominstruction.types.Instruction )subject).getCondition() ) , (( tom.engine.adt.tominstruction.types.Instruction )subject).getFailureInst() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.If) ) {buildIfWithFailure(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getCondition() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getSuccesInst() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getFailureInst() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.DoWhile) ) {buildDoWhile(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getDoInst() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getCondition() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.WhileDo) ) {buildWhileDo(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getCondition() , (( tom.engine.adt.tominstruction.types.Instruction )subject).getDoInst() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.TypedAction) ) { tom.engine.adt.tominstruction.types.Instruction  tomMatch39NameNumber_freshVar_105= (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ;if ( (tomMatch39NameNumber_freshVar_105 instanceof tom.engine.adt.tominstruction.types.instruction.AbstractBlock) ) {generateInstructionList(deep, tomMatch39NameNumber_freshVar_105.getInstList() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.TypedAction) ) {generateInstruction(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getAstInstruction() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.Return) ) {buildReturn(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getKid1() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.CompiledMatch) ) {


        //TODO moduleName
        generateInstruction(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getAutomataInst() ,moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) {if ( ((( tom.engine.adt.tominstruction.types.Instruction )subject) instanceof tom.engine.adt.tominstruction.types.instruction.CompiledPattern) ) {generateInstruction(deep, (( tom.engine.adt.tominstruction.types.Instruction )subject).getAutomataInst() ,moduleName)


;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tominstruction.types.Instruction) ) { tom.engine.adt.tominstruction.types.Instruction  tom_t=(( tom.engine.adt.tominstruction.types.Instruction )subject);


        System.out.println("Cannot generate code for instruction: " + tom_t);
        throw new TomRuntimeException("Cannot generate code for instruction: " + tom_t);
      }}}

  }

  public void generateTargetLanguage(int deep, TargetLanguage subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ) {if ( ((( tom.engine.adt.tomsignature.types.TargetLanguage )subject) instanceof tom.engine.adt.tomsignature.types.targetlanguage.TL) ) { tom.engine.adt.tomsignature.types.TextPosition  tomMatch40NameNumber_freshVar_2= (( tom.engine.adt.tomsignature.types.TargetLanguage )subject).getStart() ; tom.engine.adt.tomsignature.types.TextPosition  tomMatch40NameNumber_freshVar_3= (( tom.engine.adt.tomsignature.types.TargetLanguage )subject).getEnd() ;if ( (tomMatch40NameNumber_freshVar_2 instanceof tom.engine.adt.tomsignature.types.textposition.TextPosition) ) { int  tom_startLine= tomMatch40NameNumber_freshVar_2.getLine() ;if ( (tomMatch40NameNumber_freshVar_3 instanceof tom.engine.adt.tomsignature.types.textposition.TextPosition) ) {

        output.write(deep,  (( tom.engine.adt.tomsignature.types.TargetLanguage )subject).getCode() , tom_startLine,  tomMatch40NameNumber_freshVar_3.getLine() - tom_startLine);
        return;
      }}}}}{if ( (subject instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ) {if ( ((( tom.engine.adt.tomsignature.types.TargetLanguage )subject) instanceof tom.engine.adt.tomsignature.types.targetlanguage.ITL) ) {

        output.write( (( tom.engine.adt.tomsignature.types.TargetLanguage )subject).getCode() );
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ) {if ( ((( tom.engine.adt.tomsignature.types.TargetLanguage )subject) instanceof tom.engine.adt.tomsignature.types.targetlanguage.Comment) ) {buildComment(deep, (( tom.engine.adt.tomsignature.types.TargetLanguage )subject).getCode() )

;
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ) { tom.engine.adt.tomsignature.types.TargetLanguage  tom_t=(( tom.engine.adt.tomsignature.types.TargetLanguage )subject);

        System.out.println("Cannot generate code for TL: " + tom_t);
        throw new TomRuntimeException("Cannot generate code for TL: " + tom_t);
      }}}

  }

  public void generateOption(int deep, Option subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )subject) instanceof tom.engine.adt.tomoption.types.option.DeclarationToOption) ) {

        //System.out.println("generateOption: " + `decl);
        generateDeclaration(deep, (( tom.engine.adt.tomoption.types.Option )subject).getAstDeclaration() ,moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomoption.types.Option) ) {if ( ((( tom.engine.adt.tomoption.types.Option )subject) instanceof tom.engine.adt.tomoption.types.option.OriginTracking) ) {

 return; }}}{if ( (subject instanceof tom.engine.adt.tomoption.types.Option) ) {


        throw new TomRuntimeException("Cannot generate code for option: " + (( tom.engine.adt.tomoption.types.Option )subject));
      }}}

  }

  public void generateDeclaration(int deep, Declaration subject, String moduleName) throws IOException {
    {{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.EmptyDeclaration) ) {

        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.AbstractDecl) ) {


        //`generateInstructionList(deep, instList);
        generateDeclarationList(deep, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getDeclList() ,moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.FunctionDef) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_6= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_6 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildFunctionDef(deep, tomMatch42NameNumber_freshVar_6.getString() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getArgumentList() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getCodomain() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getThrowsType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstruction() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MethodDef) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_15= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_15 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildMethodDef(deep, tomMatch42NameNumber_freshVar_15.getString() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getArgumentList() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getCodomain() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getThrowsType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstruction() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.Class) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_24= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_24 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildClass(deep, tomMatch42NameNumber_freshVar_24.getString() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExtendsType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getSuperTerm() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getDeclaration() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.IntrospectorClass) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_32= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_32 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildIntrospectorClass(deep, tomMatch42NameNumber_freshVar_32.getString() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getDeclaration() ,moduleName)


;
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.SymbolDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_38= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_38 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {buildSymbolDecl(deep, tomMatch42NameNumber_freshVar_38.getString() ,moduleName)


;
        return ;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.ArraySymbolDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_43= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_43 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_tomName= tomMatch42NameNumber_freshVar_43.getString() ;


        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_tomName) 
        || getSymbolTable(moduleName).isUsedSymbolDestructor(tom_tomName)) {
          buildSymbolDecl(deep,tom_tomName,moduleName);
          genDeclArray(tom_tomName,moduleName);
        }
        //if(getSymbolTable(moduleName).isUsedSymbolAC(`tomName)) {
        //  `genDeclAC(tomName, moduleName);
       // }
        return ;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.ListSymbolDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_48= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_48 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_tomName= tomMatch42NameNumber_freshVar_48.getString() ;


        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_tomName) 
        || getSymbolTable(moduleName).isUsedSymbolDestructor(tom_tomName)) {
          buildSymbolDecl(deep,tom_tomName,moduleName);
          genDeclList(tom_tomName,moduleName);
        }
        //if(getSymbolTable(moduleName).isUsedSymbolAC(`tomName)) {
        //  `genDeclAC(tomName, moduleName);
        //}
        return ;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetImplementationDecl) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_53= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_53 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_57= tomMatch42NameNumber_freshVar_53.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_58= tomMatch42NameNumber_freshVar_53.getAstType() ;if ( (tomMatch42NameNumber_freshVar_57 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_name= tomMatch42NameNumber_freshVar_57.getString() ;if ( (tomMatch42NameNumber_freshVar_58 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_63= tomMatch42NameNumber_freshVar_58.getTlType() ;if ( (tomMatch42NameNumber_freshVar_63 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {




        if(getSymbolTable(moduleName).isUsedSymbolDestructor(tom_name)) {
          buildGetImplementationDecl(deep, tomMatch42NameNumber_freshVar_58.getTomType() ,tom_name,tomMatch42NameNumber_freshVar_63, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsFsymDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_67= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_68= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_67 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_tomName= tomMatch42NameNumber_freshVar_67.getString() ;if ( (tomMatch42NameNumber_freshVar_68 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_74= tomMatch42NameNumber_freshVar_68.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_75= tomMatch42NameNumber_freshVar_68.getAstType() ;if ( (tomMatch42NameNumber_freshVar_74 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_75 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_79= tomMatch42NameNumber_freshVar_75.getTlType() ;if ( (tomMatch42NameNumber_freshVar_79 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {



        if(getSymbolTable(moduleName).isUsedSymbolDestructor(tom_tomName)) {
          buildIsFsymDecl(deep,tom_tomName, tomMatch42NameNumber_freshVar_74.getString() ,tomMatch42NameNumber_freshVar_79, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
        }
        return;
      }}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetSlotDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_83= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_85= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_83 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_tomName= tomMatch42NameNumber_freshVar_83.getString() ;if ( (tomMatch42NameNumber_freshVar_85 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_90= tomMatch42NameNumber_freshVar_85.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_91= tomMatch42NameNumber_freshVar_85.getAstType() ;if ( (tomMatch42NameNumber_freshVar_90 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_91 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_95= tomMatch42NameNumber_freshVar_91.getTlType() ;if ( (tomMatch42NameNumber_freshVar_95 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {





          if(getSymbolTable(moduleName).isUsedSymbolDestructor(tom_tomName)) {
            buildGetSlotDecl(deep,tom_tomName, tomMatch42NameNumber_freshVar_90.getString() ,tomMatch42NameNumber_freshVar_95, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getSlotName() ,moduleName);
          }
          return;
        }}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.EqualTermDecl) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_99= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getTermArg1() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_100= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getTermArg2() ;if ( (tomMatch42NameNumber_freshVar_99 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_104= tomMatch42NameNumber_freshVar_99.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_105= tomMatch42NameNumber_freshVar_99.getAstType() ;if ( (tomMatch42NameNumber_freshVar_104 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_105 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { String  tom_type1= tomMatch42NameNumber_freshVar_105.getTomType() ;if ( (tomMatch42NameNumber_freshVar_100 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_112= tomMatch42NameNumber_freshVar_100.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_113= tomMatch42NameNumber_freshVar_100.getAstType() ;if ( (tomMatch42NameNumber_freshVar_112 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_113 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {




        if(getSymbolTable(moduleName).isUsedType(tom_type1)) {
          buildEqualTermDecl(deep, tomMatch42NameNumber_freshVar_104.getString() , tomMatch42NameNumber_freshVar_112.getString() ,tom_type1, tomMatch42NameNumber_freshVar_113.getTomType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
        }
        return;
      }}}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsSortDecl) ) { tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_121= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getTermArg() ;if ( (tomMatch42NameNumber_freshVar_121 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_125= tomMatch42NameNumber_freshVar_121.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_126= tomMatch42NameNumber_freshVar_121.getAstType() ;if ( (tomMatch42NameNumber_freshVar_125 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_126 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { String  tom_type= tomMatch42NameNumber_freshVar_126.getTomType() ;


        if(getSymbolTable(moduleName).isUsedType(tom_type)) {
          buildIsSortDecl(deep, tomMatch42NameNumber_freshVar_125.getString() ,tom_type, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
        }
        return;
      }}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetHeadDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_134= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getOpname() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_135= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getCodomain() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_136= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_134 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_134.getString() ;if ( (tomMatch42NameNumber_freshVar_135 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {if ( (tomMatch42NameNumber_freshVar_136 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_143= tomMatch42NameNumber_freshVar_136.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_144= tomMatch42NameNumber_freshVar_136.getAstType() ;if ( (tomMatch42NameNumber_freshVar_143 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_144 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_149= tomMatch42NameNumber_freshVar_144.getTlType() ;if ( (tomMatch42NameNumber_freshVar_149 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {





          //System.out.println("option GetHead: " + `opname);
          if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname) 
          || getSymbolTable(moduleName).isUsedSymbolDestructor(tom_opname)) {
            buildGetHeadDecl(deep,tomMatch42NameNumber_freshVar_134, tomMatch42NameNumber_freshVar_143.getString() , tomMatch42NameNumber_freshVar_144.getTomType() ,tomMatch42NameNumber_freshVar_149, tomMatch42NameNumber_freshVar_135.getTlType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
          }
          return;
        }}}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetTailDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_153= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getOpname() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_154= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_153 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_153.getString() ;if ( (tomMatch42NameNumber_freshVar_154 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_159= tomMatch42NameNumber_freshVar_154.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_160= tomMatch42NameNumber_freshVar_154.getAstType() ;if ( (tomMatch42NameNumber_freshVar_159 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_160 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_165= tomMatch42NameNumber_freshVar_160.getTlType() ;if ( (tomMatch42NameNumber_freshVar_165 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {




          if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname) 
          || getSymbolTable(moduleName).isUsedSymbolDestructor(tom_opname)) {
            buildGetTailDecl(deep,tomMatch42NameNumber_freshVar_153, tomMatch42NameNumber_freshVar_159.getString() , tomMatch42NameNumber_freshVar_160.getTomType() ,tomMatch42NameNumber_freshVar_165, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
          }
          return;
        }}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.IsEmptyDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_169= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getOpname() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_170= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_169 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_169.getString() ;if ( (tomMatch42NameNumber_freshVar_170 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_175= tomMatch42NameNumber_freshVar_170.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_176= tomMatch42NameNumber_freshVar_170.getAstType() ;if ( (tomMatch42NameNumber_freshVar_175 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_176 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_181= tomMatch42NameNumber_freshVar_176.getTlType() ;if ( (tomMatch42NameNumber_freshVar_181 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {




          if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname) 
              ||getSymbolTable(moduleName).isUsedSymbolDestructor(tom_opname)) {
            buildIsEmptyDecl(deep,tomMatch42NameNumber_freshVar_169, tomMatch42NameNumber_freshVar_175.getString() , tomMatch42NameNumber_freshVar_176.getTomType() ,tomMatch42NameNumber_freshVar_181, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
          }
          return;
        }}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyList) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_185= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_185 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_185.getString() ;


        TomType returnType = TomBase.getSymbolCodomain(getSymbolFromName(tom_opname));
        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname) 
            || getSymbolTable(moduleName).isUsedSymbolDestructor(tom_opname)) {
          genDeclMake("tom_empty_list_",tom_opname,returnType, tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddList) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_192= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_193= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVarElt() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_194= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVarList() ;if ( (tomMatch42NameNumber_freshVar_192 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_192.getString() ;if ( (tomMatch42NameNumber_freshVar_193 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_200= tomMatch42NameNumber_freshVar_193.getAstType() ;if ( (tomMatch42NameNumber_freshVar_200 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {if ( ( tomMatch42NameNumber_freshVar_200.getTlType()  instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {if ( (tomMatch42NameNumber_freshVar_194 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_205= tomMatch42NameNumber_freshVar_194.getAstType() ;if ( (tomMatch42NameNumber_freshVar_205 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {if ( ( tomMatch42NameNumber_freshVar_205.getTlType()  instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {





        TomType returnType = tomMatch42NameNumber_freshVar_205;
        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname) 
            ||getSymbolTable(moduleName).isUsedSymbolDestructor(tom_opname)) {
          genDeclMake("tom_cons_list_",tom_opname,returnType, tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(tomMatch42NameNumber_freshVar_193, tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(tomMatch42NameNumber_freshVar_194, tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) ) , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetElementDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_211= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getOpname() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_212= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_213= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getIndex() ;if ( (tomMatch42NameNumber_freshVar_211 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_212 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_218= tomMatch42NameNumber_freshVar_212.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_219= tomMatch42NameNumber_freshVar_212.getAstType() ;if ( (tomMatch42NameNumber_freshVar_218 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_219 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_224= tomMatch42NameNumber_freshVar_219.getTlType() ;if ( (tomMatch42NameNumber_freshVar_224 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {if ( (tomMatch42NameNumber_freshVar_213 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_227= tomMatch42NameNumber_freshVar_213.getAstName() ;if ( (tomMatch42NameNumber_freshVar_227 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {





          if(getSymbolTable(moduleName).isUsedSymbolDestructor( tomMatch42NameNumber_freshVar_211.getString() )) {
            buildGetElementDecl(deep,tomMatch42NameNumber_freshVar_211, tomMatch42NameNumber_freshVar_218.getString() , tomMatch42NameNumber_freshVar_227.getString() , tomMatch42NameNumber_freshVar_219.getTomType() ,tomMatch42NameNumber_freshVar_224, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
          }
          return;
        }}}}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.GetSizeDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_232= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getOpname() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_233= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVariable() ;if ( (tomMatch42NameNumber_freshVar_232 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_233 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_238= tomMatch42NameNumber_freshVar_233.getAstName() ; tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_239= tomMatch42NameNumber_freshVar_233.getAstType() ;if ( (tomMatch42NameNumber_freshVar_238 instanceof tom.engine.adt.tomname.types.tomname.Name) ) {if ( (tomMatch42NameNumber_freshVar_239 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_244= tomMatch42NameNumber_freshVar_239.getTlType() ;if ( (tomMatch42NameNumber_freshVar_244 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {





          if(getSymbolTable(moduleName).isUsedSymbolDestructor( tomMatch42NameNumber_freshVar_232.getString() )) {
            buildGetSizeDecl(deep,tomMatch42NameNumber_freshVar_232, tomMatch42NameNumber_freshVar_238.getString() , tomMatch42NameNumber_freshVar_239.getTomType() ,tomMatch42NameNumber_freshVar_244, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getExpr() ,moduleName);
          }
          return;
        }}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeEmptyArray) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_248= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_249= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVarSize() ;if ( (tomMatch42NameNumber_freshVar_248 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_248.getString() ;if ( (tomMatch42NameNumber_freshVar_249 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) {




        TomType returnType = TomBase.getSymbolCodomain(getSymbolFromName(tom_opname));
        TomTerm newVar =  tom.engine.adt.tomterm.types.tomterm.Variable.make( tomMatch42NameNumber_freshVar_249.getOption() ,  tomMatch42NameNumber_freshVar_249.getAstName() , getSymbolTable(moduleName).getIntType(),  tomMatch42NameNumber_freshVar_249.getConstraints() ) ;
        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname)) {
          genDeclMake("tom_empty_array_",tom_opname,returnType, tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(newVar, tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeAddArray) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_260= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_261= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVarElt() ; tom.engine.adt.tomterm.types.TomTerm  tomMatch42NameNumber_freshVar_262= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getVarList() ;if ( (tomMatch42NameNumber_freshVar_260 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_260.getString() ;if ( (tomMatch42NameNumber_freshVar_261 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_268= tomMatch42NameNumber_freshVar_261.getAstType() ;if ( (tomMatch42NameNumber_freshVar_268 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {if ( ( tomMatch42NameNumber_freshVar_268.getTlType()  instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {if ( (tomMatch42NameNumber_freshVar_262 instanceof tom.engine.adt.tomterm.types.tomterm.Variable) ) { tom.engine.adt.tomtype.types.TomType  tomMatch42NameNumber_freshVar_273= tomMatch42NameNumber_freshVar_262.getAstType() ;if ( (tomMatch42NameNumber_freshVar_273 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {if ( ( tomMatch42NameNumber_freshVar_273.getTlType()  instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {





        TomType returnType = tomMatch42NameNumber_freshVar_273;
        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname)) {
          genDeclMake("tom_cons_array_",tom_opname,returnType, tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(tomMatch42NameNumber_freshVar_261, tom.engine.adt.tomterm.types.tomlist.ConsconcTomTerm.make(tomMatch42NameNumber_freshVar_262, tom.engine.adt.tomterm.types.tomlist.EmptyconcTomTerm.make() ) ) , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}}}}}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.MakeDecl) ) { tom.engine.adt.tomname.types.TomName  tomMatch42NameNumber_freshVar_279= (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstName() ;if ( (tomMatch42NameNumber_freshVar_279 instanceof tom.engine.adt.tomname.types.tomname.Name) ) { String  tom_opname= tomMatch42NameNumber_freshVar_279.getString() ;


        if(getSymbolTable(moduleName).isUsedSymbolConstructor(tom_opname)) {
          genDeclMake("tom_make_",tom_opname, (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getAstType() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getArgs() , (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getInstr() ,moduleName);
        }
        return;
      }}}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) {if ( ((( tom.engine.adt.tomdeclaration.types.Declaration )subject) instanceof tom.engine.adt.tomdeclaration.types.declaration.TypeTermDecl) ) {


        generateDeclarationList(deep,  (( tom.engine.adt.tomdeclaration.types.Declaration )subject).getDeclarations() , moduleName);
        return;
      }}}{if ( (subject instanceof tom.engine.adt.tomdeclaration.types.Declaration) ) { tom.engine.adt.tomdeclaration.types.Declaration  tom_t=(( tom.engine.adt.tomdeclaration.types.Declaration )subject);


        System.out.println("Cannot generate code for declaration: " + tom_t);
        throw new TomRuntimeException("Cannot generate code for declaration: " + tom_t);
      }}}

  }

  public void generateListInclude(int deep, TomList subject, String moduleName) throws IOException {
    output.setSingleLine();
    generateList(deep, subject, moduleName);
    output.unsetSingleLine();
  }

  public void generateList(int deep, TomList subject, String moduleName)
    throws IOException {
      while(!subject.isEmptyconcTomTerm()) {
        generate(deep, subject.getHeadconcTomTerm(), moduleName);
        subject = subject.getTailconcTomTerm();
      }
    }

  public void generateOptionList(int deep, OptionList subject, String moduleName)
    throws IOException {
      while(!subject.isEmptyconcOption()) {
        generateOption(deep,subject.getHeadconcOption(), moduleName);
        subject = subject.getTailconcOption();
      }
    }

  public void generateInstructionList(int deep, InstructionList subject, String moduleName)
    throws IOException {
      while(!subject.isEmptyconcInstruction()) {
        generateInstruction(deep,subject.getHeadconcInstruction(), moduleName);
        subject = subject.getTailconcInstruction();
      }
      if(prettyMode) {
        output.writeln();
      }
    }

  public void generateDeclarationList(int deep, DeclarationList subject, String moduleName)
    throws IOException {
      while(!subject.isEmptyconcDeclaration()) {
        generateDeclaration(deep,subject.getHeadconcDeclaration(), moduleName);
        subject = subject.getTailconcDeclaration();
      }
    }

  public void generatePairNameDeclList(int deep, PairNameDeclList pairNameDeclList, String moduleName)
    throws IOException {
      while ( !pairNameDeclList.isEmptyconcPairNameDecl() ) {
        generateDeclaration(deep, pairNameDeclList.getHeadconcPairNameDecl().getSlotDecl(), moduleName);
        pairNameDeclList = pairNameDeclList.getTailconcPairNameDecl();
      }
    }

  // ------------------------------------------------------------

  protected abstract void genDecl(String returnType,
      String declName,
      String suffix,
      String args[],
      TargetLanguage tlCode,
      String moduleName) throws IOException;

  protected abstract void genDeclInstr(String returnType,
      String declName,
      String suffix,
      String args[],
      Instruction instr,
      int deep,
      String moduleName) throws IOException;

  protected abstract void genDeclMake(String prefix, String funName, TomType returnType,
      TomList argList, Instruction instr, String moduleName) throws IOException;

  protected abstract void genDeclList(String name, String moduleName) throws IOException;
  protected abstract void genDeclArray(String name, String moduleName) throws IOException;
  //protected abstract void genDeclAC(String name, String moduleName) throws IOException;

  // ------------------------------------------------------------

  protected abstract void buildInstructionSequence(int deep, InstructionList instructionList, String moduleName) throws IOException;
  protected abstract void buildComment(int deep, String text) throws IOException;
  protected abstract void buildTerm(int deep, String name, TomList argList, String moduleName) throws IOException;
  protected abstract void buildListOrArray(int deep, TomTerm list, String moduleName) throws IOException;

  protected abstract void buildFunctionCall(int deep, String name, TomList argList, String moduleName)  throws IOException;
  protected abstract void buildFunctionDef(int deep, String tomName, TomList argList, TomType codomain, TomType throwsType, Instruction instruction, String moduleName) throws IOException;
  protected void buildMethodDef(int deep, String tomName, TomList argList, TomType codomain, TomType throwsType, Instruction instruction, String moduleName) throws IOException {
    throw new TomRuntimeException("Backend "+getClass()+" does not support Methods");
  }

  /*buildClass is not abstract since only Java backend supports class
    only backends that supports Class should overload buildClass
   */
  protected void buildClass(int deep, String tomName, TomType extendsType, TomTerm superTerm, Declaration declaration, String moduleName) throws IOException {
    throw new TomRuntimeException("Backend does not support Class");
  }

  /*buildIntrospectorClass is not abstract since only Java backend supports class
    only backends that supports Class should overload buildIntrospectorClass
   */
  protected void buildIntrospectorClass(int deep, String tomName, Declaration declaration, String moduleName) throws IOException {
    throw new TomRuntimeException("Backend does not support Class");
  }

  protected abstract void buildExpNegation(int deep, Expression exp, String moduleName) throws IOException;

  protected abstract void buildExpConditional(int deep, Expression cond,Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpAnd(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpOr(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpGreaterThan(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpGreaterOrEqualThan(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpLessThan(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpLessOrEqualThan(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException;
  protected abstract void buildExpBottom(int deep, TomType type, String moduleName) throws IOException;
  protected abstract void buildExpTrue(int deep) throws IOException;
  protected abstract void buildExpFalse(int deep) throws IOException;
  protected abstract void buildExpIsEmptyList(int deep, String opName, TomType type, TomTerm expList, String moduleName) throws IOException;
  protected abstract void buildExpIsEmptyArray(int deep, TomName opName, TomType type, TomTerm expIndex, TomTerm expArray, String moduleName) throws IOException;
  protected abstract void buildExpEqualTerm(int deep, TomType type, TomTerm exp1,TomTerm exp2, String moduleName) throws IOException;
  protected abstract void buildExpIsSort(int deep, String type, TomTerm exp, String moduleName) throws IOException;
  protected abstract void buildExpIsFsym(int deep, String opname, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildExpCast(int deep, TomType type, Expression exp, String moduleName) throws IOException;
  protected abstract void buildExpGetSlot(int deep, String opname, String slotName, TomTerm exp, String moduleName) throws IOException;
  protected abstract void buildExpGetHead(int deep, String opName, TomType domain, TomType codomain, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildExpGetTail(int deep, String opName, TomType type1, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildExpGetSize(int deep, TomName opNameAST, TomType type1, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildExpGetElement(int deep, TomName opNameAST, TomType domain, TomType codomain, TomTerm varName, TomTerm varIndex, String moduleName) throws IOException;
  protected abstract void buildExpGetSliceList(int deep, String name, TomTerm varBegin, TomTerm varEnd, TomTerm tailSlice, String moduleName) throws IOException;
  protected abstract void buildExpGetSliceArray(int deep, String name, TomTerm varArray, TomTerm varBegin, TomTerm expEnd, String moduleName) throws IOException;
  protected abstract void buildAssign(int deep, TomTerm var, OptionList list, Expression exp, String moduleName) throws IOException ;
  protected abstract void buildAssignArray(int deep, TomTerm var, OptionList list, TomTerm index, Expression exp, String moduleName) throws IOException ;
  protected abstract void buildLet(int deep, TomTerm var, OptionList list, TomType tlType, Expression exp, Instruction body, String moduleName) throws IOException ;
  protected abstract void buildLetRef(int deep, TomTerm var, OptionList list, TomType tlType, Expression exp, Instruction body, String moduleName) throws IOException ;
  protected abstract void buildNamedBlock(int deep, String blockName, InstructionList instList, String modulename) throws IOException ;
  protected abstract void buildUnamedBlock(int deep, InstructionList instList, String moduleName) throws IOException ;
  protected abstract void buildIf(int deep, Expression exp, Instruction succes, String moduleName) throws IOException ;
  protected abstract void buildIfWithFailure(int deep, Expression exp, Instruction succes, Instruction failure, String moduleName) throws IOException ;
  protected abstract void buildDoWhile(int deep, Instruction succes, Expression exp, String moduleName) throws IOException;
  protected abstract void buildWhileDo(int deep, Expression exp, Instruction succes, String moduleName) throws IOException;
  protected abstract void buildAddOne(int deep, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildSubstractOne(int deep, TomTerm var, String moduleName) throws IOException;
  protected abstract void buildSubstract(int deep, TomTerm var1, TomTerm var2, String moduleName) throws IOException;
  protected abstract void buildReturn(int deep, TomTerm exp, String moduleName) throws IOException ;
  protected abstract void buildSymbolDecl(int deep, String tomName, String moduleName) throws IOException ;
  protected abstract void buildGetImplementationDecl(int deep, String type, String name,
      TomType tlType, Instruction instr, String moduleName) throws IOException;

  protected abstract void buildIsFsymDecl(int deep, String tomName, String name1,
      TomType tlType, Expression code, String moduleName) throws IOException;
  protected abstract void buildGetSlotDecl(int deep, String tomName, String name1,
      TomType tlType, Expression code, TomName slotName, String moduleName) throws IOException;
  protected abstract void buildEqualTermDecl(int deep, String name1, String name2, String type1, String type2, Expression code, String moduleName) throws IOException;
  protected abstract void buildIsSortDecl(int deep, String name1, 
      String type1, Expression expr, String moduleName) throws IOException;
  protected abstract void buildGetHeadDecl(int deep, TomName opNameAST, String varName, String suffix, TomType domain, TomType codomain, Expression code, String moduleName) throws IOException;
  protected abstract void buildGetTailDecl(int deep, TomName opNameAST, String varName, String type, TomType tlType, Expression code, String moduleName) throws IOException;
  protected abstract void buildIsEmptyDecl(int deep, TomName opNameAST, String varName, String type,
      TomType tlType, Expression code, String moduleName) throws IOException;
  protected abstract void buildGetElementDecl(int deep, TomName opNameAST, String name1, String name2, String type1, TomType tlType1, Expression code, String moduleName) throws IOException;
  protected abstract void buildGetSizeDecl(int deep, TomName opNameAST, String name1, String type, TomType tlType, Expression code, String moduleName) throws IOException;

} // class TomAbstractGenerator
