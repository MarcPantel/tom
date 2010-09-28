// $ANTLR 2.7.7 (20060906): "HostLanguage.g" -> "HostParser.java"$
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
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/

package tom.engine.parser;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

import java.util.*;
import java.util.logging.*;
import java.io.*;

import tom.engine.Tom;
import tom.engine.TomStreamManager;
import tom.engine.TomMessage;
import tom.engine.exception.*;
import tom.engine.tools.SymbolTable;

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
import tom.engine.adt.code.types.*;

import tom.engine.tools.ASTFactory;
import aterm.*;
import antlr.TokenStreamSelector;
import tom.platform.OptionManager;
import tom.platform.PluginPlatform;
import tom.platform.PlatformLogRecord;

public class HostParser extends antlr.LLkParser       implements HostParserTokenTypes
 {

  //--------------------------
    private static boolean tom_equal_term_char(char t1, char t2) {return  t1==t2 ;}private static boolean tom_is_sort_char(char t) {return  true ;} private static boolean tom_equal_term_String(String t1, String t2) {return  t1.equals(t2) ;}private static boolean tom_is_sort_String(String t) {return  t instanceof String ;} private static boolean tom_equal_term_int(int t1, int t2) {return  t1==t2 ;}private static boolean tom_is_sort_int(int t) {return  true ;} private static boolean tom_equal_term_TomSymbolTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolTable) ;}private static boolean tom_equal_term_TomSymbol(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbol(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbol) ;}private static boolean tom_equal_term_TomStructureTable(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomStructureTable(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomStructureTable) ;}private static boolean tom_equal_term_TargetLanguage(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguage(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TargetLanguage) ;}private static boolean tom_equal_term_TomEntryList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntryList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntryList) ;}private static boolean tom_equal_term_TomEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomEntry) ;}private static boolean tom_equal_term_TomVisitList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisitList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisitList) ;}private static boolean tom_equal_term_TomSymbolList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomSymbolList(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomSymbolList) ;}private static boolean tom_equal_term_TextPosition(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TextPosition(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TextPosition) ;}private static boolean tom_equal_term_TomVisit(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomVisit(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.TomVisit) ;}private static boolean tom_equal_term_KeyEntry(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_KeyEntry(Object t) {return  (t instanceof tom.engine.adt.tomsignature.types.KeyEntry) ;}private static boolean tom_equal_term_TypeConstraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraint(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraint) ;}private static boolean tom_equal_term_TypeConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TypeConstraintList(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.TypeConstraintList) ;}private static boolean tom_equal_term_Info(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Info(Object t) {return  (t instanceof tom.engine.adt.typeconstraints.types.Info) ;}private static boolean tom_equal_term_Expression(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Expression(Object t) {return  (t instanceof tom.engine.adt.tomexpression.types.Expression) ;}private static boolean tom_equal_term_TomList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomList(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomList) ;}private static boolean tom_equal_term_TomTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTerm(Object t) {return  (t instanceof tom.engine.adt.tomterm.types.TomTerm) ;}private static boolean tom_equal_term_Declaration(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Declaration(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.Declaration) ;}private static boolean tom_equal_term_DeclarationList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_DeclarationList(Object t) {return  (t instanceof tom.engine.adt.tomdeclaration.types.DeclarationList) ;}private static boolean tom_equal_term_TomType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomType) ;}private static boolean tom_equal_term_TomTypeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomTypeList(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TomTypeList) ;}private static boolean tom_equal_term_TargetLanguageType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TargetLanguageType(Object t) {return  (t instanceof tom.engine.adt.tomtype.types.TargetLanguageType) ;}private static boolean tom_equal_term_CodeList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CodeList(Object t) {return  (t instanceof tom.engine.adt.code.types.CodeList) ;}private static boolean tom_equal_term_CompositeMember(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_CompositeMember(Object t) {return  (t instanceof tom.engine.adt.code.types.CompositeMember) ;}private static boolean tom_equal_term_BQTermList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTermList(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTermList) ;}private static boolean tom_equal_term_BQTerm(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_BQTerm(Object t) {return  (t instanceof tom.engine.adt.code.types.BQTerm) ;}private static boolean tom_equal_term_Code(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Code(Object t) {return  (t instanceof tom.engine.adt.code.types.Code) ;}private static boolean tom_equal_term_ConstraintInstruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstruction) ;}private static boolean tom_equal_term_Instruction(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Instruction(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.Instruction) ;}private static boolean tom_equal_term_InstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_InstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.InstructionList) ;}private static boolean tom_equal_term_ConstraintInstructionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintInstructionList(Object t) {return  (t instanceof tom.engine.adt.tominstruction.types.ConstraintInstructionList) ;}private static boolean tom_equal_term_TomNumber(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumber(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumber) ;}private static boolean tom_equal_term_TomNameList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNameList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNameList) ;}private static boolean tom_equal_term_TomNumberList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomNumberList(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomNumberList) ;}private static boolean tom_equal_term_TomName(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_TomName(Object t) {return  (t instanceof tom.engine.adt.tomname.types.TomName) ;}private static boolean tom_equal_term_Slot(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Slot(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.Slot) ;}private static boolean tom_equal_term_SlotList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_SlotList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.SlotList) ;}private static boolean tom_equal_term_PairNameDecl(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDecl(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDecl) ;}private static boolean tom_equal_term_PairNameDeclList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_PairNameDeclList(Object t) {return  (t instanceof tom.engine.adt.tomslot.types.PairNameDeclList) ;}private static boolean tom_equal_term_OptionList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_OptionList(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.OptionList) ;}private static boolean tom_equal_term_Option(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Option(Object t) {return  (t instanceof tom.engine.adt.tomoption.types.Option) ;}private static boolean tom_equal_term_NumericConstraintType(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_NumericConstraintType(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.NumericConstraintType) ;}private static boolean tom_equal_term_Constraint(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Constraint(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.Constraint) ;}private static boolean tom_equal_term_ConstraintList(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ConstraintList(Object t) {return  (t instanceof tom.engine.adt.tomconstraint.types.ConstraintList) ;}private static boolean tom_equal_term_Theory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_Theory(Object t) {return  (t instanceof tom.engine.adt.theory.types.Theory) ;}private static boolean tom_equal_term_ElementaryTheory(Object t1, Object t2) {return  (t1==t2) ;}private static boolean tom_is_sort_ElementaryTheory(Object t) {return  (t instanceof tom.engine.adt.theory.types.ElementaryTheory) ;}private static  tom.engine.adt.tomsignature.types.TargetLanguage  tom_make_TL( String  t0,  tom.engine.adt.tomsignature.types.TextPosition  t1,  tom.engine.adt.tomsignature.types.TextPosition  t2) { return  tom.engine.adt.tomsignature.types.targetlanguage.TL.make(t0, t1, t2) ;}private static  tom.engine.adt.tomsignature.types.TargetLanguage  tom_make_ITL( String  t0) { return  tom.engine.adt.tomsignature.types.targetlanguage.ITL.make(t0) ;}private static  tom.engine.adt.tomsignature.types.TextPosition  tom_make_TextPosition( int  t0,  int  t1) { return  tom.engine.adt.tomsignature.types.textposition.TextPosition.make(t0, t1) ;}private static  tom.engine.adt.code.types.Code  tom_make_TargetLanguageToCode( tom.engine.adt.tomsignature.types.TargetLanguage  t0) { return  tom.engine.adt.code.types.code.TargetLanguageToCode.make(t0) ;}private static  tom.engine.adt.code.types.Code  tom_make_InstructionToCode( tom.engine.adt.tominstruction.types.Instruction  t0) { return  tom.engine.adt.code.types.code.InstructionToCode.make(t0) ;}private static  tom.engine.adt.code.types.Code  tom_make_DeclarationToCode( tom.engine.adt.tomdeclaration.types.Declaration  t0) { return  tom.engine.adt.code.types.code.DeclarationToCode.make(t0) ;}private static  tom.engine.adt.code.types.Code  tom_make_BQTermToCode( tom.engine.adt.code.types.BQTerm  t0) { return  tom.engine.adt.code.types.code.BQTermToCode.make(t0) ;}private static boolean tom_is_fun_sym_Tom( tom.engine.adt.code.types.Code  t) {return  (t instanceof tom.engine.adt.code.types.code.Tom) ;}private static  tom.engine.adt.code.types.Code  tom_make_Tom( tom.engine.adt.code.types.CodeList  t0) { return  tom.engine.adt.code.types.code.Tom.make(t0) ;}private static  tom.engine.adt.code.types.CodeList  tom_get_slot_Tom_CodeList( tom.engine.adt.code.types.Code  t) {return  t.getCodeList() ;}private static  tom.engine.adt.code.types.Code  tom_make_TomInclude( tom.engine.adt.code.types.CodeList  t0) { return  tom.engine.adt.code.types.code.TomInclude.make(t0) ;}private static  tom.engine.adt.tomname.types.TomName  tom_make_Name( String  t0) { return  tom.engine.adt.tomname.types.tomname.Name.make(t0) ;}private static  tom.engine.adt.tomoption.types.Option  tom_make_OriginTracking( tom.engine.adt.tomname.types.TomName  t0,  int  t1,  String  t2) { return  tom.engine.adt.tomoption.types.option.OriginTracking.make(t0, t1, t2) ;}private static boolean tom_is_fun_sym_concCode( tom.engine.adt.code.types.CodeList  t) {return  ((t instanceof tom.engine.adt.code.types.codelist.ConsconcCode) || (t instanceof tom.engine.adt.code.types.codelist.EmptyconcCode)) ;}private static  tom.engine.adt.code.types.CodeList  tom_empty_list_concCode() { return  tom.engine.adt.code.types.codelist.EmptyconcCode.make() ;}private static  tom.engine.adt.code.types.CodeList  tom_cons_list_concCode( tom.engine.adt.code.types.Code  e,  tom.engine.adt.code.types.CodeList  l) { return  tom.engine.adt.code.types.codelist.ConsconcCode.make(e,l) ;}private static  tom.engine.adt.code.types.Code  tom_get_head_concCode_CodeList( tom.engine.adt.code.types.CodeList  l) {return  l.getHeadconcCode() ;}private static  tom.engine.adt.code.types.CodeList  tom_get_tail_concCode_CodeList( tom.engine.adt.code.types.CodeList  l) {return  l.getTailconcCode() ;}private static boolean tom_is_empty_concCode_CodeList( tom.engine.adt.code.types.CodeList  l) {return  l.isEmptyconcCode() ;}   private static   tom.engine.adt.code.types.CodeList  tom_append_list_concCode( tom.engine.adt.code.types.CodeList l1,  tom.engine.adt.code.types.CodeList  l2) {     if( l1.isEmptyconcCode() ) {       return l2;     } else if( l2.isEmptyconcCode() ) {       return l1;     } else if(  l1.getTailconcCode() .isEmptyconcCode() ) {       return  tom.engine.adt.code.types.codelist.ConsconcCode.make( l1.getHeadconcCode() ,l2) ;     } else {       return  tom.engine.adt.code.types.codelist.ConsconcCode.make( l1.getHeadconcCode() ,tom_append_list_concCode( l1.getTailconcCode() ,l2)) ;     }   }   private static   tom.engine.adt.code.types.CodeList  tom_get_slice_concCode( tom.engine.adt.code.types.CodeList  begin,  tom.engine.adt.code.types.CodeList  end, tom.engine.adt.code.types.CodeList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcCode()  ||  (end==tom_empty_list_concCode()) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.engine.adt.code.types.codelist.ConsconcCode.make( begin.getHeadconcCode() ,( tom.engine.adt.code.types.CodeList )tom_get_slice_concCode( begin.getTailconcCode() ,end,tail)) ;   }    
  //--------------------------

  private static Logger logger = Logger.getLogger("tom.engine.parser.HostParser");
  // the lexer selector
  private TokenStreamSelector selector = null;

  // the file to be parsed
  private String currentFile = null;

  private HashSet<String> includedFileSet = null;
  private HashSet<String> alreadyParsedFileSet = null;
  //private static final Object lock = new Object();// verrou pour l'exec de Gom

  // the parser for tom constructs
  TomParser tomparser;

  // the lexer for target language
  HostLexer targetlexer = null;

  BackQuoteParser bqparser;

  OptionManager optionManager;

  TomStreamManager streamManager;

  // locations of target language blocks
  private int currentLine = 1;
  private int currentColumn = 1;

  private boolean skipComment = false;

  public HostParser(TokenStreamSelector selector, String currentFile,
                    HashSet<String> includedFiles, HashSet<String> alreadyParsedFiles,
                    OptionManager optionManager, TomStreamManager streamManager){
    this(selector);
    this.selector = selector;
    this.currentFile = currentFile;
    this.optionManager = optionManager;
    this.streamManager = streamManager;
    this.targetlexer = (HostLexer) selector.getStream("targetlexer");
    targetlexer.setParser(this);
    this.includedFileSet = new HashSet<String>(includedFiles);
    this.alreadyParsedFileSet = alreadyParsedFiles;

    testIncludedFile(currentFile, includedFileSet);
    // then create the Tom mode parser
    tomparser = new TomParser(getInputState(),this, optionManager);
    bqparser = tomparser.bqparser;
  }

  private void setSkipComment() {
    skipComment = true;
	}
  public boolean isSkipComment() {
    return skipComment;
	}

  private synchronized OptionManager getOptionManager() {
    return optionManager;
  }

  private synchronized TomStreamManager getStreamManager() {
    return streamManager;
  }

  public synchronized TokenStreamSelector getSelector(){
    return selector;
  }

  public synchronized String getCurrentFile(){
    return currentFile;
  }

  public synchronized SymbolTable getSymbolTable() {
    return getStreamManager().getSymbolTable();
  }

  public synchronized void updatePosition(){
    updatePosition(getLine(),getColumn());
  }

  public void updatePosition(int i, int j){
    currentLine = i;
    currentColumn = j;
  }

  public int currentLine(){
    return currentLine;
  }

  public int currentColumn(){
    return currentColumn;
  }

  // remove braces of a code block
  private String cleanCode(String code){
    return code.substring(code.indexOf('{')+1,code.lastIndexOf('}'));
  }

  // remove the last right-brace of a code block
  private String removeLastBrace(String code){
    return code.substring(0,code.lastIndexOf("}"));
  }

  // returns the current goal language code
  private String getCode() {
    String result = targetlexer.target.toString();
    targetlexer.clearTarget();
    return result;
  }

  // add a token in the target code buffer
  public void addTargetCode(Token t){
    targetlexer.target.append(t.getText());
  }

  private String pureCode(String code){
    return code.replace('\t',' ');
  }

  private boolean isCorrect(String code){
    return (! code.equals("") && ! code.matches("\\s*"));
  }

  // returns the current line number
  public int getLine(){
    return targetlexer.getLine();
  }

  // returns the current column number
  public int getColumn(){
    return targetlexer.getColumn();
  }

  private synchronized void includeFile(String fileName, List<Code> list)
    throws TomException, TomIncludeException {
    Code astTom;
    InputStream input;
    byte inputBuffer[];
    //  TomParser tomParser;
    HostParser parser = null;
    File file;
    String fileCanonicalName = "";
    fileName = fileName.trim();
    fileName = fileName.replace('/',File.separatorChar);
    fileName = fileName.replace('\\',File.separatorChar);
    if(fileName.equals("")) {
      throw new TomIncludeException(TomMessage.missingIncludedFile,new Object[]{currentFile, Integer.valueOf(getLine())});
    }

    file = new File(fileName);
    if(file.isAbsolute()) {
      try {
        file = file.getCanonicalFile();
      } catch (IOException e) {
        System.out.println("IO Exception when computing included file");
        e.printStackTrace();
      }

      if(!file.exists()) {
        file = null;
      }
    } else {
      // StreamManager shall find it
      file = getStreamManager().findFile(new File(currentFile).getParentFile(),fileName);
    }

    if(file == null) {
      throw new TomIncludeException(TomMessage.includedFileNotFound,new Object[]{fileName, currentFile, Integer.valueOf(getLine()), currentFile});
    }
    try {
      fileCanonicalName = file.getCanonicalPath();
      //if(testIncludedFile(fileCanonicalName, includedFileSet)) {
        //throw new TomIncludeException(TomMessage.includedFileCycle,new Object[]{fileName, Integer.valueOf(getLine()), currentFile});
      //}

      // if trying to include a file twice, or if in a cycle: discard
      if(testIncludedFile(fileCanonicalName, alreadyParsedFileSet) ||
	  testIncludedFile(fileCanonicalName, includedFileSet)) {
        if(!getStreamManager().isSilentDiscardImport(fileName)) {
          TomMessage.info(logger, currentFile, getLine(), TomMessage.includedFileAlreadyParsed,fileName);
        }
        return;
      }
      Reader fileReader = new BufferedReader(new FileReader(fileCanonicalName));

      if ((Boolean)optionManager.getOptionValue("newparser")) {
        parser = NewParserPlugin.newParser(fileReader,fileCanonicalName,
            includedFileSet,alreadyParsedFileSet,
            getOptionManager(), getStreamManager());
      } else {
        parser = TomParserPlugin.newParser(fileReader,fileCanonicalName,
            includedFileSet,alreadyParsedFileSet,
            getOptionManager(), getStreamManager());
      }
      parser.setSkipComment();
      astTom = parser.input();
      astTom = tom_make_TomInclude(astTom.getCodeList());
      list.add(astTom);
    } catch (Exception e) {
      if(e instanceof TomIncludeException) {
        throw (TomIncludeException)e;
      }
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      throw new TomException(TomMessage.errorWhileIncludingFile,
          new Object[]{e.getClass(),
            fileName,
            currentFile,
            Integer.valueOf(getLine()),
            sw.toString()
          });
    }
  }

  private boolean testIncludedFile(String fileName, HashSet<String> fileSet) {
    // !(true) if the set did not already contain the specified element.
    return !fileSet.add(fileName);
  }

  /*
   * this function receives a string that comes from %[ ... ]%
   * @@ corresponds to the char '@', so they a encoded into ]% (which cannot
   * appear in the string)
   * then, the string is split around the delimiter @
   * alternatively, each string correspond either to a metaString, or a string
   * to parse the @@ encoded by ]% is put back as a single '@' in the metaString
   */
  public String tomSplitter(String subject, List<Code> list) {

    String metaChar = "]%";
    String escapeChar = "@";

    //System.out.println("initial subject: '" + subject + "'");
    subject = subject.replace(escapeChar+escapeChar,metaChar);
    //System.out.println("subject: '" + subject + "'");
    String split[] = subject.split(escapeChar);
    boolean last = subject.endsWith(escapeChar);
    int numSeparator = split.length + 1 + (last ? 1 : 0);
    if (numSeparator%2==1) {
      TomMessage.error(logger, currentFile, getLine(), TomMessage.badNumberOfAt);
    }
    //System.out.println("split.length: " + split.length);
    boolean metaMode = true;
    String res = "";
    for(int i=0 ; i<split.length ; i++) {
      if(metaMode) {
        // put back escapeChar instead of metaChar
        String code = metaEncodeCode(split[i].replace(metaChar,escapeChar));
        metaMode = false;
        //System.out.println("metaString: '" + code + "'");
        list.add(tom_make_TargetLanguageToCode(tom_make_ITL(code)));
      } else {
        String code = "+"+split[i]+"+";
        metaMode = true;
        //System.out.println("prg to parse: '" + code + "'");
        try {
          Reader codeReader = new BufferedReader(new StringReader(code));
          HostParser parser;
          if (((Boolean)optionManager.getOptionValue("newparser"))) {
            parser = NewParserPlugin.newParser(codeReader,getCurrentFile(),
                getOptionManager(), getStreamManager());
          } else {
            parser = TomParserPlugin.newParser(codeReader,getCurrentFile(),
                getOptionManager(), getStreamManager());
          }
          Code astTom = parser.input();
          {{if (tom_is_sort_Code(astTom)) {if (tom_is_fun_sym_Tom((( tom.engine.adt.code.types.Code )astTom))) { tom.engine.adt.code.types.CodeList  tomMatch1_1=tom_get_slot_Tom_CodeList((( tom.engine.adt.code.types.Code )astTom));if (tom_is_fun_sym_concCode(tomMatch1_1)) { tom.engine.adt.code.types.CodeList  tomMatch1__end__6=tomMatch1_1;do {{if (!(tom_is_empty_concCode_CodeList(tomMatch1__end__6))) {

              list.add(tom_get_head_concCode_CodeList(tomMatch1__end__6));
            }if (tom_is_empty_concCode_CodeList(tomMatch1__end__6)) {tomMatch1__end__6=tomMatch1_1;} else {tomMatch1__end__6=tom_get_tail_concCode_CodeList(tomMatch1__end__6);}}} while(!(tom_equal_term_CodeList(tomMatch1__end__6, tomMatch1_1)));}}}}}

        } catch (IOException e) {
          throw new TomRuntimeException("IOException catched in tomSplitter");
        } catch (Exception e) {
          throw new TomRuntimeException("Exception catched in tomSplitter");
        }
      }
    }
    if(subject.endsWith(escapeChar)) {
      // add an empty string when %[...@...@]%
      list.add(tom_make_TargetLanguageToCode(tom_make_ITL("\"\"")));
    }
    return res;
  }

  private static String metaEncodeCode(String code) {
		/*
			 System.out.println("before: '" + code + "'");
			 for(int i=0 ; i<code.length() ; i++) {
			 System.out.print((int)code.charAt(i));
			 System.out.print(" ");
			 }
			 System.out.println();
		 */
		char bs = '\\';
		StringBuilder sb = new StringBuilder((int)1.5*code.length());
		for(int i=0 ; i<code.length() ; i++) {
			char c = code.charAt(i);
			switch(c) {
				case '\n':
					sb.append(bs);
					sb.append('n');
					break;
				case '\r':
					sb.append(bs);
					sb.append('r');
					break;
				case '\t':
					sb.append(bs);
					sb.append('t');
					break;
				case '\"':
				case '\\':
					sb.append(bs);
					sb.append(c);
					break;
				default:
					sb.append(c);
			}
		}
    //System.out.println("sb = '" + sb + "'");
		sb.insert(0,'\"');
		sb.append('\"');
		return sb.toString();
  }


protected HostParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public HostParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected HostParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public HostParser(TokenStream lexer) {
  this(lexer,1);
}

public HostParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final Code  input() throws RecognitionException, TokenStreamException, TomException {
		Code result;
		
		Token  t = null;
		
		result = null;
		List<Code> list = new LinkedList<Code>();
		
		
		blockList(list);
		t = LT(1);
		match(Token.EOF_TYPE);
		
		// This TL is last block: do no need to specify line and column
		list.add(tom_make_TargetLanguageToCode(tom_make_TL(getCode(),tom_make_TextPosition(currentLine(),currentColumn()),tom_make_TextPosition(t.getLine(),t.getColumn())))
		
		);
		//String comment = "Generated by TOM (version " + Tom.VERSION + "): Do not edit this file";
		//list.add(0,`TargetLanguageToCode(Comment(comment)));
		result = tom_make_Tom(ASTFactory.makeCodeList(list));
		
		return result;
	}
	
	public final void blockList(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		
		{
		_loop4:
		do {
			switch ( LA(1)) {
			case MATCH:
			{
				matchConstruct(list);
				break;
			}
			case STRATEGY:
			{
				strategyConstruct(list);
				break;
			}
			case GOM:
			{
				gomsignature(list);
				break;
			}
			case BACKQUOTE:
			{
				backquoteTerm(list);
				break;
			}
			case OPERATOR:
			{
				operator(list);
				break;
			}
			case OPERATORLIST:
			{
				operatorList(list);
				break;
			}
			case OPERATORARRAY:
			{
				operatorArray(list);
				break;
			}
			case INCLUDE:
			{
				includeConstruct(list);
				break;
			}
			case TYPETERM:
			{
				typeTerm(list);
				break;
			}
			case CODE:
			{
				code(list);
				break;
			}
			case STRING:
			{
				match(STRING);
				break;
			}
			case LBRACE:
			{
				match(LBRACE);
				blockList(list);
				match(RBRACE);
				break;
			}
			default:
			{
				break _loop4;
			}
			}
		} while (true);
		}
	}
	
	public final void matchConstruct(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(MATCH);
		
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn())
		)
		
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Option ot = tom_make_OriginTracking(tom_make_Name("Match"),t.getLine(),currentFile);
		
		Instruction match = tomparser.matchConstruct(ot);
		list.add(tom_make_InstructionToCode(match));
		
	}
	
	public final void strategyConstruct(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(STRATEGY);
		
		// add the target code preceeding the construct
		String textCode = getCode();
		
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn())
		)
		
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Option ot = tom_make_OriginTracking(tom_make_Name("Strategy"),t.getLine(),currentFile);
		
		// call the tomparser for the construct
		Declaration strategy = tomparser.strategyConstruct(ot);
		list.add(tom_make_DeclarationToCode(strategy));
		
	}
	
	public final void gomsignature(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		int initialGomLine;
		TargetLanguage code = null;
		List<Code> blockList = new LinkedList<Code>();
		String gomCode = null;
		
		
		t = LT(1);
		match(GOM);
		
		initialGomLine = t.getLine();
		
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn()))
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		
		synchronized(Tom.getLock()) {
		tom.gom.parser.BlockParser blockparser =
		tom.gom.parser.BlockParser.makeBlockParser(targetlexer.getInputState());
		gomCode = cleanCode(blockparser.block().trim());
		
		File config_xml = null;
		ArrayList<String> parameters = new ArrayList<String>();
		try {
		String tom_home = System.getProperty("tom.home");
		if(tom_home != null) {
		config_xml = new File(tom_home,"Gom.xml");
		} else {
		// for the eclipse plugin for example
		String tom_xml_filename =
		((String)getOptionManager().getOptionValue("X"));
		config_xml =
		new File(new File(tom_xml_filename).getParentFile(),"Gom.xml");
		// pass all the received parameters to gom in the case that it will call tom
		java.util.List<File> imp = getStreamManager().getUserImportList();
		for(File f:imp){
		parameters.add("--import");
		parameters.add(f.getCanonicalPath());
		}
		}
		config_xml = config_xml.getCanonicalFile();
		} catch (IOException e) {
		TomMessage.finer(logger, null, 0, TomMessage.failGetCanonicalPath,config_xml.getPath());
		}
		
		String destDir = getStreamManager().getDestDir().getPath();
		String packageName = getStreamManager().getPackagePath().replace(File.separatorChar, '.');
		String inputFileNameWithoutExtension = getStreamManager().getRawFileName().toLowerCase();
		String subPackageName = "";
		if (packageName.equals("")) {
		subPackageName = inputFileNameWithoutExtension;
		} else {
		subPackageName = packageName + "." + inputFileNameWithoutExtension;
		}
		
		parameters.add("-X");
		parameters.add(config_xml.getPath());
		parameters.add("--destdir");
		parameters.add(destDir);
		parameters.add("--package");
		parameters.add(subPackageName);
		if(getOptionManager().getOptionValue("wall")==Boolean.TRUE) {
		parameters.add("--wall");
		}
		if(getOptionManager().getOptionValue("intermediate")==Boolean.TRUE) {
		parameters.add("--intermediate");
		}
		if(Boolean.TRUE == getOptionManager().getOptionValue("optimize")) {
		parameters.add("--optimize");
		}
		if(Boolean.TRUE == getOptionManager().getOptionValue("optimize2")) {
		parameters.add("--optimize2");
		}
		if(Boolean.TRUE == getOptionManager().getOptionValue("newtyper")) {
		parameters.add("--newtyper");
		}
		if(Boolean.TRUE == getOptionManager().getOptionValue("newparser")) {
		parameters.add("--newparser");
		}
		parameters.add("--intermediateName");
		parameters.add(getStreamManager().getRawFileName()+".t.gom");
		if(getOptionManager().getOptionValue("verbose")==Boolean.TRUE) {
		parameters.add("--verbose");
		}
		/* treat user supplied options */
		textCode = t.getText();
		if(textCode.length() > 6) {
		String[] userOpts = textCode.substring(5,textCode.length()-1).split("\\s+");
		for(int i=0; i < userOpts.length; i++) {
		parameters.add(userOpts[i]);
		}
		}
		
		final File tmpFile;
		try {
		tmpFile = File.createTempFile("tmp", ".gom", getStreamManager().getDestDir()).getCanonicalFile();
		parameters.add(tmpFile.getPath());
		} catch (IOException e) {
		TomMessage.error(logger, null, 0, TomMessage.ioExceptionTempGom,e.getMessage());
		e.printStackTrace();
		return;
		}
		
		TomMessage.fine(logger, null, 0, TomMessage.writingExceptionTempGom,tmpFile.getPath());
		
		try {
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile)));
		writer.write(new String(gomCode.getBytes("UTF-8")));
		writer.flush();
		writer.close();
		} catch (IOException e) {
		TomMessage.error(logger, null, 0, TomMessage.writingFailureTempGom,e.getMessage());
		return;
		}
		
		/* Prepare arguments */
		Object[] preparams = parameters.toArray();
		String[] params = new String[preparams.length];
		for (int i = 0; i < preparams.length; i++) {
		params[i] = (String)preparams[i];
		}
		
		int res = 1;
		Map<String,String> informationTracker = new HashMap<String,String>();
		informationTracker.put(tom.engine.tools.TomGenericPlugin.KEY_LAST_GEN_MAPPING,null);
		
		informationTracker.put("gomBegin",""+initialGomLine);
		informationTracker.put("inputFileName",getStreamManager().getInputFileName());
		
		//5 tom.platform.PluginPlatformFactory.getInstance().getInformationTracker().put(java.lang.Thread.currentThread().getId(),null);
		res = tom.gom.Gom.exec(params,informationTracker);
		tmpFile.deleteOnExit();
		if(res != 0) {
		TomMessage.error(logger, currentFile, initialGomLine, TomMessage.gomFailure,currentFile,Integer.valueOf(initialGomLine));
		return;
		}
		String generatedMapping = (String)informationTracker.get(tom.engine.tools.TomGenericPlugin.KEY_LAST_GEN_MAPPING);
		
		// Simulate the inclusion of generated Tom file
		/*
		* We shall not need to test the validity of the generatedMapping file name
		* as gom returned <> 0 if it is not valid
		*
		* Anyway, for an empty gom signature, no files are generated
		*/
		if (generatedMapping != null) {
			includeFile(generatedMapping, list);
		}
		updatePosition();
		} //synchronized
		
	}
	
	public final void backquoteTerm(
		List<Code> list
	) throws RecognitionException, TokenStreamException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(BACKQUOTE);
		
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn())
		)
		
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Option ot = tom_make_OriginTracking(tom_make_Name("Backquote"),t.getLine(),currentFile);
		//BQTerm bqTerm = tomparser.plainBQTerm();
		BQTerm result = bqparser.beginBackquote();
		//System.out.println("parse bqterm \n"+result);
		
		// update position for new target block
		updatePosition();
		list.add(tom_make_BQTermToCode(result));
		//throw new RuntimeException("BackQuote parser not yet implemented");
		
	}
	
	public final void operator(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(OPERATOR);
		
		String textCode = pureCode(getCode());
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn()))
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Declaration operatorDecl = tomparser.operator();
		list.add(tom_make_DeclarationToCode(operatorDecl));
		
	}
	
	public final void operatorList(
		List list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t1 = null;
		
		TargetLanguage code = null;
		int line = 0;
		int column = 0;
		
		
		{
		t1 = LT(1);
		match(OPERATORLIST);
		line=t1.getLine();column=t1.getColumn();
		}
		
		String textCode = pureCode(getCode());
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(line,column))
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Declaration operatorListDecl = tomparser.operatorList();
		list.add(tom_make_DeclarationToCode(operatorListDecl));
		
	}
	
	public final void operatorArray(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(OPERATORARRAY);
		
		String textCode = pureCode(getCode());
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn()))
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		Declaration operatorArrayDecl = tomparser.operatorArray();
		list.add(tom_make_DeclarationToCode(operatorArrayDecl));
		
	}
	
	public final void includeConstruct(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage tlCode = null;
		List<Code> blockList = new LinkedList<Code>();
		
		
		t = LT(1);
		match(INCLUDE);
		
		TargetLanguage code = null;
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn()))
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		
		tlCode=goalLanguage(blockList);
		
		includeFile(tlCode.getCode(),list);
		updatePosition();
		
	}
	
	public final void typeTerm(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  tt = null;
		
		TargetLanguage code = null;
		int line, column;
		
		
		{
		tt = LT(1);
		match(TYPETERM);
		
		line = tt.getLine();
		column = tt.getColumn();
		
		}
		
		// addPreviousCode...
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(line,column))
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		Declaration termdecl = tomparser.typeTerm();
		
		list.add(tom_make_DeclarationToCode(termdecl));
		
	}
	
	public final void code(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		
		Token  t = null;
		
		TargetLanguage code = null;
		
		
		t = LT(1);
		match(CODE);
		
		String textCode = getCode();
		if(isCorrect(textCode)) {
		code = tom_make_TL(textCode,tom_make_TextPosition(currentLine,currentColumn),tom_make_TextPosition(t.getLine(),t.getColumn())
		)
		
		
		
		;
		list.add(tom_make_TargetLanguageToCode(code));
		}
		textCode = t.getText();
		String metacode = textCode.substring(2,textCode.length()-2);
		tomSplitter(metacode, list);
		updatePosition(targetlexer.getInputState().getLine(),targetlexer.getInputState().getColumn());
		
	}
	
	public final TargetLanguage  goalLanguage(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		TargetLanguage result;
		
		Token  t1 = null;
		Token  t2 = null;
		
		result =  null;
		
		
		t1 = LT(1);
		match(LBRACE);
		
		updatePosition(t1.getLine(),t1.getColumn());
		
		blockList(list);
		t2 = LT(1);
		match(RBRACE);
		
		result = tom_make_TL(cleanCode(getCode()),tom_make_TextPosition(currentLine(),currentColumn()),tom_make_TextPosition(t2.getLine(),t2.getColumn())
		)
		
		
		;
		targetlexer.clearTarget();
		
		return result;
	}
	
	public final TargetLanguage  targetLanguage(
		List<Code> list
	) throws RecognitionException, TokenStreamException, TomException {
		TargetLanguage result;
		
		Token  t = null;
		
		result = null;
		
		
		blockList(list);
		t = LT(1);
		match(RBRACE);
		
		String code = removeLastBrace(getCode());
		
		//System.out.println("code = " + code);
		//System.out.println("list = " + list);
		
		result = tom_make_TL(code,tom_make_TextPosition(currentLine(),currentColumn()),tom_make_TextPosition(t.getLine(),t.getColumn())
		)
		
		
		;
		targetlexer.clearTarget();
		
		return result;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"STRING",
		"LBRACE",
		"RBRACE",
		"STRATEGY",
		"MATCH",
		"GOM",
		"BACKQUOTE",
		"OPERATOR",
		"OPERATORLIST",
		"OPERATORARRAY",
		"INCLUDE",
		"CODE",
		"TYPETERM",
		"ESC",
		"HEX_DIGIT",
		"WS",
		"COMMENT",
		"SL_COMMENT",
		"ML_COMMENT",
		"TARGET"
	};
	
	
	}
