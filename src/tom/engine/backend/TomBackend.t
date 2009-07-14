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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.*;

import tom.engine.TomMessage;
import tom.engine.TomBase;

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

import tom.engine.tools.*;
import tom.engine.exception.TomRuntimeException;
import tom.platform.OptionParser;
import tom.platform.PluginPlatformMessage;
import tom.platform.PlatformException;
import tom.platform.adt.platformoption.types.PlatformOptionList;

import tom.library.sl.*;
import tom.library.sl.VisitFailure;


/**
 * The TomBackend "plugin".
 * Has to create the generator depending on OptionManager, create the output 
 * writer and generting the output code.
 */
public class TomBackend extends TomGenericPlugin {

  %include { ../adt/tomsignature/TomSignature.tom }
  %include { ../../platform/adt/platformoption/PlatformOption.tom }
  %include { ../../library/mapping/java/sl.tom }

  /** the tabulation starting value */
  private final static int defaultDeep = 2;

  /** the declared options string */
  public static final String DECLARED_OPTIONS = 
    "<options>" +
    "<boolean name='noOutput' altName=''  description='Do not generate code' value='false'/>" +
    "<boolean name='jCode'    altName='j' description='Generate Java code' value='true'/>" + 
    "<boolean name='csCode'   altName=''  description='Generate C# code' value='false'/>" + 
    "<boolean name='cCode'    altName='c' description='Generate C code' value='false'/>" +
    "<boolean name='camlCode' altName=''  description='Generate Caml code' value='false'/>" + 
    "<boolean name='pCode'    altName=''  description='Generate Python code' value='false'/>" + 
    "<boolean name='inline'   altName=''  description='Inline mapping' value='false'/>" +
    "<boolean name='inlineplus'   altName=''  description='Inline mapping' value='false'/>" +
    "</options>";

  /** the generated file name */
  private String generatedFileName = null;

  /** Constructor*/
  public TomBackend() {
    super("TomBackend");
  }

  /**
   *
   */
  public void run(Map informationTracker) {
    //System.out.println("(debug) I'm in the Tom backend : TSM"+getStreamManager().toString());
    try {
      if(isActivated() == true) {
        TomAbstractGenerator generator = null;
        Writer writer;
        long startChrono = System.currentTimeMillis();
        try {
          String encoding = getOptionStringValue("encoding");
          writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getStreamManager().getOutputFile()),encoding));
          OutputCode output = new OutputCode(writer, getOptionManager());
          if(getOptionBooleanValue("noOutput")) {
            throw new TomRuntimeException("Backend activated, but noOutput is set");
          } else if(getOptionBooleanValue("cCode")) {
            generator = new TomCGenerator(output, getOptionManager(), symbolTable());
          } else if(getOptionBooleanValue("camlCode")) {
            generator = new TomCamlGenerator(output, getOptionManager(), symbolTable());
          } else if(getOptionBooleanValue("pCode")) {
            generator = new TomPythonGenerator(output, getOptionManager(), symbolTable());
          } else if(getOptionBooleanValue("csCode")) {
            generator = new TomCSharpGenerator(output, getOptionManager(), symbolTable());
          } else if(getOptionBooleanValue("jCode")) {
            generator = new TomJavaGenerator(output, getOptionManager(), symbolTable());
          } else {
            throw new TomRuntimeException("no selected language for the Backend");
          }

          TomTerm pilCode = (TomTerm) getWorkingTerm();

          markUsedConstructorDestructor(pilCode);

          generator.generate(defaultDeep, generator.operatorsTogenerate(pilCode),TomBase.DEFAULT_MODULE_NAME);
          // verbose
          getLogger().log(Level.INFO,
              TomMessage.tomGenerationPhase.getMessage(),
              Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
          output.close();
        } catch (IOException e) {
          getLogger().log(Level.SEVERE,
              TomMessage.backendIOException.getMessage(),
              new Object[]{getStreamManager().getOutputFile().getName(), e.getMessage()} );
          return;
        } catch (Exception e) {
          String fileName = getStreamManager().getInputFileName();
          int line = -1;
          TomMessage.error(getLogger(),fileName,line,TomMessage.exceptionMessage, new Object[]{fileName});
          e.printStackTrace();
           return;
        }
        // set the generated File Name
        try {
          generatedFileName = getStreamManager().getOutputFile().getCanonicalPath();
        } catch (IOException e) {
          System.out.println("IO Exception when computing generatedFileName");
          e.printStackTrace();
        }
      } else {
        // backend is desactivated
        getLogger().log(Level.INFO,TomMessage.backendInactivated.getMessage());
      }
    } catch(PlatformException e) {
      getLogger().log( Level.SEVERE, PluginPlatformMessage.platformStopped.getMessage());
      return;
    }
  }

  public void optionChanged(String optionName, Object optionValue) {
    //System.out.println("optionChanged: " + optionName + " --> " + optionValue);
    if(optionName.equals("camlCode") && ((Boolean)optionValue).booleanValue() ) { 
      setOptionValue("jCode", Boolean.FALSE);        
      setOptionValue("cCode", Boolean.FALSE);        
      setOptionValue("pCode", Boolean.FALSE);        
    } else if(optionName.equals("cCode") && ((Boolean)optionValue).booleanValue() ) { 
      setOptionValue("jCode", Boolean.FALSE);        
      setOptionValue("camlCode", Boolean.FALSE);        
      setOptionValue("pCode", Boolean.FALSE);        
    } else if(optionName.equals("jCode") && ((Boolean)optionValue).booleanValue() ) { 
      setOptionValue("cCode", Boolean.FALSE);        
      setOptionValue("camlCode", Boolean.FALSE);        
      setOptionValue("pCode", Boolean.FALSE);        
    } else if(optionName.equals("pCode") && ((Boolean)optionValue).booleanValue() ) { 
      setOptionValue("cCode", Boolean.FALSE);        
      setOptionValue("camlCode", Boolean.FALSE);        
      setOptionValue("jCode", Boolean.FALSE);        
    }
  }

  /**
   * inherited from OptionOwner interface (plugin) 
   */
  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(TomBackend.DECLARED_OPTIONS);
  }

  private boolean isActivated() {
    return !getOptionBooleanValue("noOutput");
  }

  protected SymbolTable getSymbolTable(String moduleName) {
    //TODO//
    //Using of the moduleName
    ////////

    //System.out.println(symbolTable().toTerm());

    return symbolTable();
  }
  /**
   * inherited from plugin interface
   * returns the generated file name
   */
  public Object[] getArgs() {
    return new Object[]{generatedFileName};
  }

  %typeterm StringStack {
    implement { Stack<String> }
    is_sort(t) { ($t instanceof Stack) }
  }

  %typeterm TomBackend {
    implement { TomBackend }
    is_sort(t) { ($t instanceof TomBackend) }
  }

  private void markUsedConstructorDestructor(TomTerm pilCode) {
    Stack<String> stack = new Stack<String>();
    stack.push(TomBase.DEFAULT_MODULE_NAME);
    try {
      `mu(MuVar("markStrategy"),TopDownCollect(Collector(MuVar("markStrategy"),this,stack))).visitLight(pilCode);
    } catch(VisitFailure e) { /* Ignored */ }
  }

  private void setUsedSymbolConstructor(String moduleName, TomSymbol tomSymbol, Strategy markStrategy) {
    SymbolTable st = getSymbolTable(moduleName);
    if(!st.isUsedSymbolConstructor(tomSymbol) && !st.isUsedSymbolDestructor(tomSymbol)) {
      try {
        markStrategy.visitLight(tomSymbol);
      } catch(VisitFailure e) { /* Ignored */ }
    }
    getSymbolTable(moduleName).setUsedSymbolConstructor(tomSymbol);
  }

  private void setUsedSymbolDestructor(String moduleName, TomSymbol tomSymbol, Strategy markStrategy) {    
    SymbolTable st = getSymbolTable(moduleName);
    if(!st.isUsedSymbolConstructor(tomSymbol) && !st.isUsedSymbolDestructor(tomSymbol)) {
      try {
        markStrategy.visitLight(tomSymbol);
      } catch(VisitFailure e) { /* Ignored */ }
    }
    getSymbolTable(moduleName).setUsedSymbolDestructor(tomSymbol);
  }

  private void setUsedType(String moduleName, String tomTypeName, Strategy markStrategy) {
    getSymbolTable(moduleName).setUsedType(tomTypeName);
  }

  /*
   * the strategy Collector is used collect the part of the mapping that is really used
   * this strategy also collect the declarations (IsFsymDecl, GetSLotDecl, etc)
   * to fill the mapInliner used by the backend to inline calls to IsFsym, GetSlot, etc.
   */
  %strategy Collector(markStrategy:Strategy,tb:TomBackend,stack:StringStack) extends `Identity() {
    visit Instruction {
      CompiledMatch[AutomataInst=inst, Option=optionList] -> {

        String moduleName = TomBase.getModuleName(`optionList);
        /*
         * push the modulename
         * or the wrapping modulename if the current one
         * (nested match for example) does not have one
         */
        if (moduleName==null) {
          try {
            moduleName = stack.peek();
            stack.push(moduleName);
            //System.out.println("push2: " + moduleName);
          } catch (EmptyStackException e) {
            System.out.println("No moduleName in stack");
          }
        } else {
          stack.push(moduleName);
          //System.out.println("push1: " + moduleName);
        }
        //System.out.println("match -> moduleName = " + moduleName);
        markStrategy.visitLight(`inst);
        //String pop = (String) stack.pop();
        //System.out.println("pop: " + pop);
        throw new tom.library.sl.VisitFailure();

      }

      TypedAction[AstInstruction=inst] -> {
        markStrategy.visitLight(`inst);
        throw new tom.library.sl.VisitFailure();
      }
    }

    visit Expression {
      (IsEmptyList|IsEmptyArray|GetHead|GetTail)[Opname=Name(name)] -> {
        try {
          // System.out.println("list check: " + `name);
          String moduleName = stack.peek();
          //System.out.println("moduleName: " + moduleName);
          TomSymbol tomSymbol = TomBase.getSymbolFromName(`name,tb.getSymbolTable(moduleName)); 
          tb.setUsedSymbolConstructor(moduleName,tomSymbol,markStrategy);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }
      
      IsFsym[AstName=Name(name)] -> {
        try {
          // System.out.println("list check: " + `name);
          String moduleName = stack.peek();
          //System.out.println("moduleName: " + moduleName);
          TomSymbol tomSymbol = TomBase.getSymbolFromName(`name,tb.getSymbolTable(moduleName)); 
          tb.setUsedSymbolDestructor(moduleName,tomSymbol,markStrategy);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }
    }

    visit TomType {
      Type(type,_) -> {
        try {
          String moduleName = stack.peek();
          tb.setUsedType(moduleName,`type,markStrategy);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }
    }

    visit TomTerm {
      (TermAppl|RecordAppl)[NameList=nameList] -> {
        TomNameList l = `nameList;
        // System.out.println("dest " + `l);
        while(!l.isEmptyconcTomName()) {
          try {
            //System.out.println("op: " + l.getHead());
            String moduleName = stack.peek();
            //System.out.println("moduleName: " + moduleName);
            TomSymbol tomSymbol = TomBase.getSymbolFromName(l.getHeadconcTomName().getString(),tb.getSymbolTable(moduleName)); 
            //System.out.println("mark: " + tomSymbol);
            // if it comes from java
            if (tomSymbol != null) { tb.setUsedSymbolDestructor(moduleName,tomSymbol,markStrategy);}
          } catch (EmptyStackException e) {
            System.out.println("No moduleName in stack");
          }
          l = l.getTailconcTomName();
        }
        /*
         * here we can fail because the subterms appear in isFsym tests
         * therefore, they are marked when traversing the compiledAutomata
         */
        throw new tom.library.sl.VisitFailure();
      }
      (BuildTerm|BuildEmptyArray)[AstName=Name(name)] -> {
        try {
          // System.out.println("build: " + `name);
          String moduleName = stack.peek();
          //System.out.println("moduleName: " + moduleName);
          TomSymbol tomSymbol = TomBase.getSymbolFromName(`name,tb.getSymbolTable(moduleName)); 
          tb.setUsedSymbolConstructor(moduleName,tomSymbol,markStrategy);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }
      (BuildConsList|BuildEmptyList|BuildAppendList|BuildConsArray|BuildAppendArray)[AstName=Name(name)] -> {
        try {
          // System.out.println("build: " + `name);
          String moduleName = stack.peek();
          //System.out.println("moduleName: " + moduleName);
          TomSymbol tomSymbol = TomBase.getSymbolFromName(`name,tb.getSymbolTable(moduleName)); 
          tb.setUsedSymbolConstructor(moduleName,tomSymbol,markStrategy);
          /* XXX: Also mark the destructors as used, since some generated
           * functions will use them */
          tb.setUsedSymbolDestructor(moduleName,tomSymbol,markStrategy);
          // resolve uses in the symbol declaration
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }
    }

    visit Declaration {
      //TypeTermDecl[] -> {
        // should not search under a declaration
        //throw new tom.library.sl.VisitFailure();
      //}

      /*
       * collect all declarations and add them in the mapInliner
       * this is needed because a %op or %typeterm may be defined
       * after the usage of the sort or the operator
       */
      IsFsymDecl[AstName=Name(opname),Expr=Code(code)] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putIsFsym(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      IsSortDecl[TermArg=Variable[AstType=Type(type,_)],Expr=Code(code)] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putIsSort(`type,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      EqualTermDecl[TermArg1=Variable[AstType=Type(type,_)],Expr=Code(code)] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putEqualTerm(`type,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      GetSlotDecl[AstName=Name(opname),SlotName=Name(slotName),Expr=Code(code)] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putGetSlot(`opname,`slotName,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      MakeDecl[AstName=Name(opname),Instr=ExpressionToInstruction(Code(code))] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putMake(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      MakeEmptyList[AstName=Name(opname),Instr=ExpressionToInstruction(Code(code))] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putMakeEmptyList(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      MakeAddList[AstName=Name(opname),Instr=ExpressionToInstruction(Code(code))] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putMakeAddList(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      MakeEmptyArray[AstName=Name(opname),Instr=ExpressionToInstruction(Code(code))] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putMakeEmptyArray(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      MakeAddArray[AstName=Name(opname),Instr=ExpressionToInstruction(Code(code))] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putMakeAddArray(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

      GetSizeDecl[Opname=Name(opname),Expr=Code(code)] -> {
        try {
          String moduleName = stack.peek();
          tb.getSymbolTable(moduleName).putGetSizeArray(`opname,`code);
        } catch (EmptyStackException e) {
          System.out.println("No moduleName in stack");
        }
      }

    }
  }
} // class TomBackend
