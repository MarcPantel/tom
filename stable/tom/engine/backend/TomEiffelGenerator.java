/*  Generated by TOM: Do not edit this file */  /*
  
    TOM - To One Matching Compiler

    Copyright (C) 2000-2003 INRIA
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
 
import aterm.*;

import java.io.IOException;

import jtom.adt.tomsignature.types.*;

import jtom.tools.TomTaskInput;
import jtom.tools.OutputCode;
import jtom.exception.TomRuntimeException;
import jtom.TomEnvironment;

public class TomEiffelGenerator extends TomImperativeGenerator {
  
  public TomEiffelGenerator(TomEnvironment environment, OutputCode output, TomTaskInput input) {
		super(environment, output, input);
  }

// ------------------------------------------------------------
  /*  Generated by TOM: Do not edit this file */    
// ------------------------------------------------------------

 	protected void buildTerm(int deep, String name, TomList argList) throws IOException {
		output.write("tom_make_");
		output.write(name);
		if(argList.isEmpty()) { // strange ?
		} else {
			output.writeOpenBrace();
			while(!argList.isEmpty()) {
				generate(deep,argList.getHead());
				argList = argList.getTail();
				if(!argList.isEmpty()) {
              output.writeComa();
				}
			}
			output.writeCloseBrace();
		}
	}

	protected void buildDeclaration(int deep, TomTerm var, String type, TomType tlType) throws IOException {
		generate(deep,var);
		output.write(deep,": " + getTLCode(tlType));
		output.writeln(";");
	}
	
	protected void buildDeclarationStar(int deep, TomTerm var, TomName name, String type, TomType tlType) throws IOException {
		generate(deep,var);
		output.write(deep,": " + getTLCode(tlType));
		output.writeln(";");
	}

	protected void buildFunctionBegin(int deep, String tomName, TomList varList) throws IOException {
        TomSymbol tomSymbol = symbolTable().getSymbol(tomName);
        String glType = getTLType(getSymbolCodomain(tomSymbol));
        String name = tomSymbol.getAstName().getString();
				output.write(deep,name + "(");
				while(!varList.isEmpty()) {
          TomTerm localVar = varList.getHead();
          matchBlock: {
             {  TomTerm tom_match1_1 = ( TomTerm) localVar;{_match1_pattern1: { if(tom_is_fun_sym_Variable(tom_match1_1) ||  false ) { {  TomTerm v = ( TomTerm) tom_match1_1; {  OptionList tom_match1_1_1 = ( OptionList) tom_get_slot_Variable_option(tom_match1_1); {  TomName tom_match1_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match1_1); {  TomType tom_match1_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match1_1);{ {  OptionList option2 = ( OptionList) tom_match1_1_1; {  TomName name2 = ( TomName) tom_match1_1_2; {  TomType type2 = ( TomType) tom_match1_1_3;
 
                  generate(deep,v);
                  output.write(deep,": " + getTLType(type2));
                break matchBlock;
              }}}}}}}} }}_match1_pattern2: {
 
                System.out.println("MakeFunction: strange term: " + localVar);
                throw new TomRuntimeException(new Throwable("MakeFunction: strange term: " + localVar));
              }}}
 
          }
          varList = varList.getTail();
          if(!varList.isEmpty()) {
              output.write(deep,"; ");
          }
        }
				output.writeln(deep,"): " + glType + " is");
				output.writeln(deep,"local ");
				//out.writeln(deep,"return null;");
	}
	
	protected void buildFunctionEnd(int deep) throws IOException {
			output.writeln(deep,"end;");
	}
	
	protected void buildExpNot(int deep, Expression exp) throws IOException {
		output.write("not ");
		generateExpression(deep,exp);
	}

  protected void buildExpTrue(int deep) throws IOException {
		output.write(" true ");
  }
  
  protected void buildExpFalse(int deep) throws IOException {
		output.write(" false ");
  }

  protected void buildAssignVar(int deep, TomTerm var, OptionList list, String type, TomType tlType, Expression exp) throws IOException {
    output.indent(deep);
    generate(deep,var);
    if(isBoolType(type) || isIntType(type) || isDoubleType(type)) {
      output.write(" := ");
    } else {
      //out.write(" ?= ");
      String assignSign = " := ";
       {  Expression tom_match2_1 = ( Expression) exp;{_match2_pattern1: { if(tom_is_fun_sym_GetSubterm(tom_match2_1) ||  false ) {{
 
          assignSign = " ?= ";
        } }}}}
 
      output.write(assignSign);
    }
    generateExpression(deep,exp);
    output.writeln(";");
    if(debugMode && !list.isEmpty()) {
      output.write("jtom.debug.TomDebugger.debugger.addSubstitution(\""+debugKey+"\",\"");
      generate(deep,var);
      output.write("\", ");
      generate(deep,var); // generateExpression(out,deep,exp);
      output.writeln(");");
    }
  }

  protected void buildLet(int deep, TomTerm var, OptionList list, String type, TomType tlType, 
                          Expression exp, Instruction body) throws IOException {
    System.out.println("buildLet code not yet implemented");
    throw new TomRuntimeException(new Throwable("buildLet: Eiffel code not yet implemented"));
  }


	
	protected void buildAssignMatch(int deep, TomTerm var, String type, TomType tlType, Expression exp) throws IOException {
    output.indent(deep);
    generate(deep,var);
		if(isBoolType(type) || isIntType(type) || isDoubleType(type)) {
			output.write(" := ");
		} else {
			//out.write(" ?= ");
			String assignSign = " := ";
			 {  Expression tom_match3_1 = ( Expression) exp;{_match3_pattern1: { if(tom_is_fun_sym_GetSubterm(tom_match3_1) ||  false ) {{
 
					assignSign = " ?= ";
				} }}}}
 
			output.write(assignSign);
    }
    generateExpression(deep,exp);
    output.writeln(";");
    if (debugMode) {
      output.write("jtom.debug.TomDebugger.debugger.specifySubject(\""+debugKey+"\",\"");
      generateExpression(deep,exp);
      output.write("\",");
      generateExpression(deep,exp);
      output.writeln(");");
    }
  }

  protected void buildNamedBlock(int deep, String blockName, TomList instList) throws IOException {
		System.out.println("NamedBlock: Eiffel code not yet implemented");
		throw new TomRuntimeException(new Throwable("NamedBlock: Eiffel code not yet implemented"));
  }

  protected void buildUnamedBlock(int deep, TomList instList) throws IOException {
		System.out.println("UnamedBlock: Eiffel code not yet implemented");
		throw new TomRuntimeException(new Throwable("NamedBlock: Eiffel code not yet implemented"));
  }

  protected void buildIfThenElse(int deep, Expression exp, TomList succesList) throws IOException {
		output.write(deep,"if "); generateExpression(deep,exp); output.writeln(" then ");
		generateList(deep+1,succesList);
		output.writeln(deep,"end;");
  }

  protected void buildIfThenElseWithFailure(int deep, Expression exp, TomList succesList, TomList failureList) throws IOException {
		output.write(deep,"if "); generateExpression(deep,exp); output.writeln(" then ");
		generateList(deep+1,succesList);
		output.writeln(deep," else ");
		generateList(deep+1,failureList);
		output.writeln(deep,"end;");
  }

  protected void buildExitAction(int deep, TomNumberList numberList) throws IOException {
      System.out.println("ExitAction: Eiffel code not yet implemented");
      throw new TomRuntimeException(new Throwable("ExitAction: Eiffel code not yet implemented"));
  }

  protected void buildReturn(int deep, TomTerm exp) throws IOException {
		output.writeln(deep,"if Result = Void then");
		output.write(deep+1,"Result := ");
		generate(deep+1,exp);
		output.writeln(deep+1,";");
		output.writeln(deep,"end;");
	}

	protected void buildGetSubtermDecl(int deep, String name1, String name2, String type1, TomType tlType1, TomType tlType2, TargetLanguage tlCode) throws IOException {
    String args[];
    if(strictType) {
      args = new String[] { getTLCode(tlType1), name1,
                            getTLCode(tlType2), name2 };
    } else {
			args = new String[] { getTLType(getUniversalType()), name1,
														getTLCode(tlType2), name2 };
    }
    generateTargetLanguage(deep, genDecl(getTLType(getUniversalType()), "tom_get_subterm", type1,
																				 args, tlCode));
	}
	
	protected TargetLanguage genDecl(int deep, String returnType,
																	 String declName,
																	 String suffix,
																	 String args[],
																	 TargetLanguage tlCode) {
    String s = "";
    if(!genDecl) { return null; }
		s = declName + "_" + suffix + "(";
		for(int i=0 ; i<args.length ; ) {
			s+= args[i+1] + ": " + args[i];
			i+=2;
			if(i<args.length) {
				s+= "; ";
			}
		} 
		s += "): " + returnType + " is do Result := " + tlCode.getCode() + "end;";
    if(tlCode.isTL())
      return tom_make_TL(s,tlCode .getStart(),tlCode .getEnd()) ;
    else
      return tom_make_ITL(s) ;
  }

  protected TargetLanguage genDeclMake(int deep, String opname, TomType returnType, 
                                            TomList argList, TargetLanguage tlCode) throws IOException {
      //%variable
    String s = "";
    if(!genDecl) { return null; }
		boolean braces = !argList.isEmpty();
		s = "tom_make_" + opname;
		if(braces) {
			s = s + "(";
		}
		while(!argList.isEmpty()) {
			TomTerm arg = argList.getHead();
			matchBlock: {
				 {  TomTerm tom_match4_1 = ( TomTerm) arg;{_match4_pattern1: { if(tom_is_fun_sym_Variable(tom_match4_1) ||  false ) { {  OptionList tom_match4_1_1 = ( OptionList) tom_get_slot_Variable_option(tom_match4_1); {  TomName tom_match4_1_2 = ( TomName) tom_get_slot_Variable_astName(tom_match4_1); {  TomType tom_match4_1_3 = ( TomType) tom_get_slot_Variable_astType(tom_match4_1);{ {  OptionList option = ( OptionList) tom_match4_1_1; if(tom_is_fun_sym_Name(tom_match4_1_2) ||  false ) { {  String tom_match4_1_2_1 = ( String) tom_get_slot_Name_string(tom_match4_1_2);{ {  String name = ( String) tom_match4_1_2_1; if(tom_is_fun_sym_Type(tom_match4_1_3) ||  false ) { {  TomType tom_match4_1_3_1 = ( TomType) tom_get_slot_Type_tomType(tom_match4_1_3); {  TomType tom_match4_1_3_2 = ( TomType) tom_get_slot_Type_tlType(tom_match4_1_3);{ if(tom_is_fun_sym_ASTTomType(tom_match4_1_3_1) ||  false ) { {  String tom_match4_1_3_1_1 = ( String) tom_get_slot_ASTTomType_string(tom_match4_1_3_1);{ {  String type = ( String) tom_match4_1_3_1_1; if(tom_is_fun_sym_TLType(tom_match4_1_3_2) ||  false ) { {  TomType tlType = ( TomType) tom_match4_1_3_2;{
 
						s += name + ": " + getTLCode(tlType);
						break matchBlock;
					}} }}}} }}}} }}}} }}}}}} }}_match4_pattern2: {

 
						System.out.println("genDeclMake: strange term: " + arg);
						throw new TomRuntimeException(new Throwable("genDeclMake: strange term: " + arg));
					}}}
 
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
			return tom_make_TL(s,tlCode .getStart(),tlCode .getEnd()) ;
  }

  protected TargetLanguage genDeclList(int deep, String name, TomType listType, TomType eltType) throws IOException {
      //%variable
    String s = "";
    if(!genDecl) { return null; }
		System.out.println("genDeclList: Eiffel code not yet implemented");
		return null;
  }


  protected TargetLanguage genDeclArray(int deep, String name, TomType listType, TomType eltType) throws IOException {
		//%variable
    String s = "";
    if(!genDecl) { return null; }
		System.out.println("genDeclArray: Eiffel code not yet implemented");
    return null;
  }

  protected TargetLanguage genDecl(String returnType,
                        String declName,
                        String suffix,
                        String args[],
                        TargetLanguage tlCode) {
    String s = "";
    if(!genDecl) { return null; }
		s = declName + "_" + suffix + "(";
		for(int i=0 ; i<args.length ; ) {
			s+= args[i+1] + ": " + args[i];
			i+=2;
			if(i<args.length) {
				s+= "; ";
			}
		} 
		s += "): " + returnType + " is do Result := " + tlCode.getCode() + "end;";
    if(tlCode.isTL())
      return tom_make_TL(s,tlCode .getStart(),tlCode .getEnd()) ;
    else
      return tom_make_ITL(s) ;
  }

}
