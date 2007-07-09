/* Generated by TOM (version 2.5): Do not edit this file *//*
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
import tom.engine.tools.ASTFactory;
import tom.platform.OptionManager;

public abstract class TomCFamilyGenerator extends TomGenericGenerator {

  // ------------------------------------------------------------
  /* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file *//* Generated by TOM (version 2.5): Do not edit this file */ private static boolean tom_equal_term_String(String t1, String t2) { return  (t1.equals(t2)) ;}private static boolean tom_is_sort_String(String t) { return  t instanceof String ;}  /* Generated by TOM (version 2.5): Do not edit this file */private static boolean tom_equal_term_int(int t1, int t2) { return  (t1==t2) ;}private static boolean tom_is_sort_int(int t) { return  true ;} private static boolean tom_equal_term_TomType(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomType(Object t) { return  t instanceof tom.engine.adt.tomtype.types.TomType ;}private static boolean tom_equal_term_Position(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_Position(Object t) { return  t instanceof tom.engine.adt.tomsignature.types.Position ;}private static boolean tom_equal_term_TargetLanguage(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TargetLanguage(Object t) { return  t instanceof tom.engine.adt.tomsignature.types.TargetLanguage ;}private static boolean tom_equal_term_TomName(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomName(Object t) { return  t instanceof tom.engine.adt.tomname.types.TomName ;}private static boolean tom_equal_term_TomTerm(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_TomTerm(Object t) { return  t instanceof tom.engine.adt.tomterm.types.TomTerm ;}private static boolean tom_equal_term_OptionList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_OptionList(Object t) { return  t instanceof tom.engine.adt.tomoption.types.OptionList ;}private static boolean tom_equal_term_ConstraintList(Object t1, Object t2) { return  t1.equals(t2) ;}private static boolean tom_is_sort_ConstraintList(Object t) { return  t instanceof tom.engine.adt.tomconstraint.types.ConstraintList ;}private static boolean tom_is_fun_sym_Type( tom.engine.adt.tomtype.types.TomType  t) { return  t instanceof tom.engine.adt.tomtype.types.tomtype.Type ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_Type_TomType( tom.engine.adt.tomtype.types.TomType  t) { return  t.getTomType() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_Type_TlType( tom.engine.adt.tomtype.types.TomType  t) { return  t.getTlType() ;}private static boolean tom_is_fun_sym_TLType( tom.engine.adt.tomtype.types.TomType  t) { return  t instanceof tom.engine.adt.tomtype.types.tomtype.TLType ;}private static  tom.engine.adt.tomsignature.types.TargetLanguage  tom_get_slot_TLType_Tl( tom.engine.adt.tomtype.types.TomType  t) { return  t.getTl() ;}private static boolean tom_is_fun_sym_TextPosition( tom.engine.adt.tomsignature.types.Position  t) { return  t instanceof tom.engine.adt.tomsignature.types.position.TextPosition ;}private static  int  tom_get_slot_TextPosition_Line( tom.engine.adt.tomsignature.types.Position  t) { return  t.getLine() ;}private static  int  tom_get_slot_TextPosition_Column( tom.engine.adt.tomsignature.types.Position  t) { return  t.getColumn() ;}private static boolean tom_is_fun_sym_TL( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t instanceof tom.engine.adt.tomsignature.types.targetlanguage.TL ;}private static  String  tom_get_slot_TL_Code( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t.getCode() ;}private static  tom.engine.adt.tomsignature.types.Position  tom_get_slot_TL_Start( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t.getStart() ;}private static  tom.engine.adt.tomsignature.types.Position  tom_get_slot_TL_End( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t.getEnd() ;}private static boolean tom_is_fun_sym_ITL( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t instanceof tom.engine.adt.tomsignature.types.targetlanguage.ITL ;}private static  String  tom_get_slot_ITL_Code( tom.engine.adt.tomsignature.types.TargetLanguage  t) { return  t.getCode() ;}private static boolean tom_is_fun_sym_Name( tom.engine.adt.tomname.types.TomName  t) { return  t instanceof tom.engine.adt.tomname.types.tomname.Name ;}private static  String  tom_get_slot_Name_String( tom.engine.adt.tomname.types.TomName  t) { return  t.getString() ;}private static boolean tom_is_fun_sym_Variable( tom.engine.adt.tomterm.types.TomTerm  t) { return  t instanceof tom.engine.adt.tomterm.types.tomterm.Variable ;}private static  tom.engine.adt.tomoption.types.OptionList  tom_get_slot_Variable_Option( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getOption() ;}private static  tom.engine.adt.tomname.types.TomName  tom_get_slot_Variable_AstName( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstName() ;}private static  tom.engine.adt.tomtype.types.TomType  tom_get_slot_Variable_AstType( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getAstType() ;}private static  tom.engine.adt.tomconstraint.types.ConstraintList  tom_get_slot_Variable_Constraints( tom.engine.adt.tomterm.types.TomTerm  t) { return  t.getConstraints() ;} 
  // ------------------------------------------------------------


  public TomCFamilyGenerator(OutputCode output, OptionManager optionManager,
                                SymbolTable symbolTable) {
    super(output, optionManager, symbolTable);
  }
  
   protected void buildAssignVar(int deep, TomTerm var, OptionList list, Expression exp, String moduleName) throws IOException {
    //output.indent(deep);
    generate(deep,var,moduleName);
    output.write("=");
    generateExpression(deep,exp,moduleName);
    output.writeln(";");
  } 

  protected void buildComment(int deep, String text) throws IOException {
    output.writeln("/* " + text + " */");
    return;
  }

  protected void buildDoWhile(int deep, Instruction succes, Expression exp, String moduleName) throws IOException {
    output.writeln(deep,"do {");
    generateInstruction(deep+1,succes,moduleName);
    output.write(deep,"} while(");
    generateExpression(deep,exp,moduleName);
    output.writeln(");");
  }

  protected void buildExpEqualTerm(int deep, TomType type, TomTerm exp1,TomTerm exp2, String moduleName) throws IOException {
    if(getSymbolTable(moduleName).isBooleanType(TomBase.getTomType(type))) {
      output.write("(");
      generate(deep,exp1,moduleName);
      output.write(" == ");
      generate(deep,exp2,moduleName);
      output.write(")");
    } else {
      output.write("tom_equal_term_" + TomBase.getTomType(type) + "(");
      generate(deep,exp1,moduleName);
      output.write(", ");
      generate(deep,exp2,moduleName);
      output.write(")");
    }
  }

  protected void buildExpConditional(int deep, Expression cond,Expression exp1, Expression exp2, String moduleName) throws IOException {
    output.write("((");
    generateExpression(deep,cond,moduleName);
    output.write(")?(");
    generateExpression(deep,exp1,moduleName);
    output.write("):(");
    generateExpression(deep,exp2,moduleName);
    output.write("))");
  }

  protected void buildExpAnd(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException {
	output.write(" ( ");
	generateExpression(deep,exp1,moduleName);
    output.write(" && ");
    generateExpression(deep,exp2,moduleName);
    output.write(" ) ");
  }

  protected void buildExpOr(int deep, Expression exp1, Expression exp2, String moduleName) throws IOException {
	output.write(" ( ");  
    generateExpression(deep,exp1,moduleName);
    output.write(" || ");
    generateExpression(deep,exp2,moduleName);
    output.write(" ) ");
  }
 
  protected void buildExpCast(int deep, TomType tlType, Expression exp, String moduleName) throws IOException {
    output.write("((" + TomBase.getTLCode(tlType) + ")");
    generateExpression(deep,exp,moduleName);
    output.write(")");
  }
 
  protected void buildExpNegation(int deep, Expression exp, String moduleName) throws IOException {
    output.write("!(");
    generateExpression(deep,exp,moduleName);
    output.write(")");
  }

  protected void buildIf(int deep, Expression exp, Instruction succes, String moduleName) throws IOException {
    output.write(deep,"if ("); 
    generateExpression(deep,exp, moduleName); 
    output.writeln(") {");
    generateInstruction(deep+1,succes, moduleName);
    output.writeln(deep,"}");
  }
  
  protected void buildIfWithFailure(int deep, Expression exp, Instruction succes, Instruction failure, String moduleName) throws IOException {
    output.write(deep,"if ("); 
    generateExpression(deep,exp,moduleName); 
    output.writeln(") {");
    generateInstruction(deep+1,succes,moduleName);
    output.writeln(deep,"} else {");
    generateInstruction(deep+1,failure,moduleName);
    output.writeln(deep,"}");
  }

  protected void buildInstructionSequence(int deep, InstructionList instructionList, String moduleName) throws IOException {
    generateInstructionList(deep, instructionList, moduleName);
    return;
  }

  protected void buildLet(int deep, TomTerm var, OptionList optionList, TomType tlType, 
                          Expression exp, Instruction body, String moduleName) throws IOException {
    output.write(deep,"{ " + TomBase.getTLCode(tlType) + " ");
    buildAssignVar(deep,var,optionList,exp,moduleName);
    generateInstruction(deep,body,moduleName);
    output.writeln(deep,"}");
  }
  
  protected void buildLetRef(int deep, TomTerm var, OptionList optionList, TomType tlType, 
                             Expression exp, Instruction body, String moduleName) throws IOException {
    buildLet(deep,var,optionList,tlType,exp,body, moduleName);
  }
 
  protected void buildLetAssign(int deep, TomTerm var, OptionList list, Expression exp, Instruction body, String moduleName) throws IOException {
    buildAssignVar(deep, var, list, exp, moduleName);
    generateInstruction(deep,body, moduleName);
  }

  protected void buildRef(int deep, TomTerm term, String moduleName) throws IOException {
    generate(deep,term,moduleName);
  }

  protected void buildReturn(int deep, TomTerm exp, String moduleName) throws IOException {
    output.write(deep,"return ");
    generate(deep,exp,moduleName);
    output.writeln(deep,";");
  }

  protected void buildUnamedBlock(int deep, InstructionList instList, String moduleName) throws IOException {
    output.writeln(deep, "{");
    generateInstructionList(deep+1,instList, moduleName);
    output.writeln(deep, "}");
  }
 
  protected void buildWhileDo(int deep, Expression exp, Instruction succes, String moduleName) throws IOException {
    output.write(deep,"while (");
    generateExpression(deep,exp,moduleName);
    output.writeln(") {");
    generateInstruction(deep+1,succes,moduleName);
    output.writeln(deep,"}");
  }

  protected void genDecl(String returnType,
                         String declName,
                         String suffix,
                         String args[],
                         TargetLanguage tlCode,
                         String moduleName) throws IOException {
    if(nodeclMode) {
      return;
    }

    StringBuffer s = new StringBuffer();
    s.append(modifier);
    s.append(returnType);
    s.append(" ");
    s.append(declName);
    s.append("_");
    s.append(suffix);
    s.append("(");
    for(int i=0 ; i<args.length ; ) {
      s.append(args[i]); // parameter type
      s.append(" ");
      s.append(args[i+1]); // parameter name
      i+=2;
      if(i<args.length) {
        s.append(", ");
      }
    } 
    String returnValue = getSymbolTable(moduleName).isVoidType(returnType)?tlCode.getCode():"return " + tlCode.getCode();
    s.append(") { " + returnValue + "; }");

    if (tom_is_sort_TargetLanguage(tlCode)) {{  tom.engine.adt.tomsignature.types.TargetLanguage  tomMatch50NameNumberfreshSubject_1=(( tom.engine.adt.tomsignature.types.TargetLanguage )tlCode);if (tom_is_fun_sym_TL(tomMatch50NameNumberfreshSubject_1)) {{  String  tomMatch50NameNumber_freshVar_0=tom_get_slot_TL_Code(tomMatch50NameNumberfreshSubject_1);{  tom.engine.adt.tomsignature.types.Position  tomMatch50NameNumber_freshVar_1=tom_get_slot_TL_Start(tomMatch50NameNumberfreshSubject_1);{  tom.engine.adt.tomsignature.types.Position  tomMatch50NameNumber_freshVar_2=tom_get_slot_TL_End(tomMatch50NameNumberfreshSubject_1);if (tom_is_fun_sym_TextPosition(tomMatch50NameNumber_freshVar_1)) {{  int  tomMatch50NameNumber_freshVar_3=tom_get_slot_TextPosition_Line(tomMatch50NameNumber_freshVar_1);{  int  tom_startLine=tomMatch50NameNumber_freshVar_3;if (tom_is_fun_sym_TextPosition(tomMatch50NameNumber_freshVar_2)) {{  int  tomMatch50NameNumber_freshVar_4=tom_get_slot_TextPosition_Line(tomMatch50NameNumber_freshVar_2);if ( true ) {

        output.write(0,s, tom_startLine, tomMatch50NameNumber_freshVar_4- tom_startLine);
        return;
      }}}}}}}}}}if (tom_is_fun_sym_ITL(tomMatch50NameNumberfreshSubject_1)) {{  String  tomMatch50NameNumber_freshVar_5=tom_get_slot_ITL_Code(tomMatch50NameNumberfreshSubject_1);if ( true ) {


        output.write(s);
        return;
      }}}}}


  }

  protected void genDeclInstr(String returnType,
                         String declName,
                         String suffix,
                         String args[],
                         Instruction instr,
                         int deep, String moduleName) throws IOException {
    if(nodeclMode) {
      return;
    }

    StringBuffer s = new StringBuffer();
    s.append(modifier);
    s.append(returnType);
    s.append(" ");
    s.append(declName);
    s.append("_");
    s.append(suffix);
    s.append("(");
    for(int i=0 ; i<args.length ; ) {
      s.append(args[i]); // parameter type
      s.append(" ");
      s.append(args[i+1]); // parameter name
      i+=2;
      if(i<args.length) {
        s.append(", ");
      }
    } 
    s.append(") { ");
    output.write(s);
    generateInstruction(deep,instr,moduleName);
    output.write("}");
    output.writeln();
  }

  private String genDeclGetHead(String name, TomType domain, TomType codomain, String subject) {
    String tomType = TomBase.getTomType(codomain);
    String get = "tom_get_head_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"_"/* Generated by TOM (version 2.5): Do not edit this file */+tomType+"("/* Generated by TOM (version 2.5): Do not edit this file */+subject+")";
    String is_conc = "tom_is_fun_sym_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+subject+")";
    String cast = "";// "(" + TomBase.getTLType(domain) + ")";
    if(domain==codomain) { 
      return cast+"(("/* Generated by TOM (version 2.5): Do not edit this file */+is_conc+")?"/* Generated by TOM (version 2.5): Do not edit this file */+get+":"/* Generated by TOM (version 2.5): Do not edit this file */+subject+")";
    }
    return cast+get;
  }
  private String genDeclGetTail(String name, TomType domain, TomType codomain, String subject) {
    String tomType = TomBase.getTomType(codomain);
    String get= "tom_get_tail_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"_"/* Generated by TOM (version 2.5): Do not edit this file */+tomType+"("/* Generated by TOM (version 2.5): Do not edit this file */+subject+")";
    String is_conc = "tom_is_fun_sym_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+subject+")";
    String empty = "tom_empty_list_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"()";
    String cast = ""; //"(" + TomBase.getTLType(codomain)+ ")";
    if(domain==codomain) { 
      return cast+"(("/* Generated by TOM (version 2.5): Do not edit this file */+is_conc+")?"/* Generated by TOM (version 2.5): Do not edit this file */+get+":"/* Generated by TOM (version 2.5): Do not edit this file */+empty+")";
    }
    return cast+get;
  }

  protected void genDeclList(String name, String moduleName) throws IOException {
    if(nodeclMode) {
      return;
    }

    TomSymbol tomSymbol = getSymbolTable(moduleName).getSymbolFromName(name);
    TomType listType = TomBase.getSymbolCodomain(tomSymbol);
    TomType eltType = TomBase.getSymbolDomain(tomSymbol).getHeadconcTomType();
    String tomType = TomBase.getTomType(listType);
    String glType = TomBase.getTLType(listType);

    String utype = glType;
    if(lazyMode) {
      utype = TomBase.getTLType(getUniversalType());
    }
    
    String listCast = "(" + glType + ")";
    String is_empty = "tom_is_empty_" + name + "_" + tomType;
    String is_conc = "tom_is_fun_sym_" + name;
    String equal_term = "tom_equal_term_" + tomType;
    String make_insert = listCast + "tom_cons_list_" + name;
    String make_empty = listCast + "tom_empty_list_" + name;
    String get_slice = listCast + "tom_get_slice_" + name;
    String get_index = "tom_get_index_" + name;

    String s = "";
    if(listType == eltType) {
s = "\n  "/* Generated by TOM (version 2.5): Do not edit this file */+modifier+" "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" tom_append_list_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+utype+"l1, "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" l2) {\n    if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"(l1)) {\n      return l2;\n    } else if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"(l2)) {\n      return l1;\n    } else if("/* Generated by TOM (version 2.5): Do not edit this file */+is_conc+"(l1)) {\n      if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetTail(name,eltType,listType,"l1")+")) {\n        return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetHead(name,eltType,listType,"l1")+",l2);\n      } else {\n        return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetHead(name,eltType,listType,"l1")+",tom_append_list_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetTail(name,eltType,listType,"l1")+",l2));\n      }\n    } else {\n      return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"(l1, l2);\n    }\n  }"














;

    } else {

s = "\n  "/* Generated by TOM (version 2.5): Do not edit this file */+modifier+" "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" tom_append_list_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+utype+"l1, "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" l2) {\n    if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"(l1)) {\n      return l2;\n    } else if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"(l2)) {\n      return l1;\n    } else if("/* Generated by TOM (version 2.5): Do not edit this file */+is_empty+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetTail(name,eltType,listType,"l1")+")) {\n      return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetHead(name,eltType,listType,"l1")+",l2);\n    } else {\n      return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetHead(name,eltType,listType,"l1")+",tom_append_list_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetTail(name,eltType,listType,"l1")+",l2));\n    }\n  }"










;

    }

    s+= "\n  "/* Generated by TOM (version 2.5): Do not edit this file */+modifier+" "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" tom_get_slice_"/* Generated by TOM (version 2.5): Do not edit this file */+name+"("/* Generated by TOM (version 2.5): Do not edit this file */+utype+" begin, "/* Generated by TOM (version 2.5): Do not edit this file */+utype+" end,"/* Generated by TOM (version 2.5): Do not edit this file */+utype+" tail) {\n    if("/* Generated by TOM (version 2.5): Do not edit this file */+equal_term+"(begin,end)) {\n      return tail;\n    } else {\n      return "/* Generated by TOM (version 2.5): Do not edit this file */+make_insert+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetHead(name,eltType,listType,"begin")+","/* Generated by TOM (version 2.5): Do not edit this file */+get_slice+"("/* Generated by TOM (version 2.5): Do not edit this file */+genDeclGetTail(name,eltType,listType,"begin")+",end,tail));\n    }\n  }\n  "







;
   
    //If necessary we remove \n code depending on pretty option
    s = ASTFactory.makeSingleLineCode(s, prettyMode);
    output.write(s);
  }

  protected void genDeclMake(String funName, TomType returnType, 
                             TomList argList, Instruction instr, String moduleName) throws IOException {
    if(nodeclMode) {
      return;
    }

    StringBuffer s = new StringBuffer();
    s.append(modifier + TomBase.getTLType(returnType) + " " + funName + "(");
    while(!argList.isEmptyconcTomTerm()) {
      TomTerm arg = argList.getHeadconcTomTerm();
      matchBlock: {
        if (tom_is_sort_TomTerm(arg)) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch51NameNumberfreshSubject_1=(( tom.engine.adt.tomterm.types.TomTerm )arg);if (tom_is_fun_sym_Variable(tomMatch51NameNumberfreshSubject_1)) {{  tom.engine.adt.tomname.types.TomName  tomMatch51NameNumber_freshVar_1=tom_get_slot_Variable_AstName(tomMatch51NameNumberfreshSubject_1);{  tom.engine.adt.tomtype.types.TomType  tomMatch51NameNumber_freshVar_2=tom_get_slot_Variable_AstType(tomMatch51NameNumberfreshSubject_1);if (tom_is_fun_sym_Name(tomMatch51NameNumber_freshVar_1)) {{  String  tomMatch51NameNumber_freshVar_3=tom_get_slot_Name_String(tomMatch51NameNumber_freshVar_1);if (tom_is_fun_sym_Type(tomMatch51NameNumber_freshVar_2)) {{  tom.engine.adt.tomtype.types.TomType  tomMatch51NameNumber_freshVar_4=tom_get_slot_Type_TlType(tomMatch51NameNumber_freshVar_2);{  tom.engine.adt.tomtype.types.TomType  tomMatch51NameNumber_freshVar_0=tomMatch51NameNumber_freshVar_4;if (tom_is_fun_sym_TLType(tomMatch51NameNumber_freshVar_0)) {if ( true ) {

            s.append(TomBase.getTLCode(tomMatch51NameNumber_freshVar_0) + " " + tomMatch51NameNumber_freshVar_3);
            break matchBlock;
          }}}}}}}}}}if ( true ) {


            System.out.println("genDeclMake: strange term: " + arg);
            throw new TomRuntimeException("genDeclMake: strange term: " + arg);
          }}}

      }
      argList = argList.getTailconcTomTerm();
      if(!argList.isEmptyconcTomTerm()) {
        s.append(", ");
      }
    }
    s.append(") { ");
    output.write(s);
    output.write("return ");
    generateInstruction(0,instr,moduleName);
    output.write("; }");
  }
}
