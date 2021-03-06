
























package tom.engine.parser;



import java.io.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;



import tom.engine.TomMessage;
import tom.engine.TomStreamManager;
import tom.engine.exception.TomException;
import tom.engine.exception.TomIncludeException;
import tom.engine.tools.TomGenericPlugin;
import tom.engine.tools.Tools;
import tom.engine.tools.SymbolTable;
import tom.platform.adt.platformoption.types.PlatformOptionList;
import tom.engine.parser.TomParserTool;



import tom.engine.adt.tomsignature.types.TomSymbol;
import tom.engine.adt.cst.types.*;
import tom.engine.adt.code.types.*;



import tom.library.sl.Visitable;



import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.TokenStreamSelector;



import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.tree.Tree;



import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;











public class TomParserPlugin extends TomGenericPlugin {
     private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_append_list_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList l1,  tom.platform.adt.platformoption.types.PlatformOptionList  l2) {     if( l1.isEmptyconcPlatformOption() ) {       return l2;     } else if( l2.isEmptyconcPlatformOption() ) {       return l1;     } else if(  l1.getTailconcPlatformOption() .isEmptyconcPlatformOption() ) {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,l2) ;     } else {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,tom_append_list_concPlatformOption( l1.getTailconcPlatformOption() ,l2)) ;     }   }   private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_get_slice_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList  begin,  tom.platform.adt.platformoption.types.PlatformOptionList  end, tom.platform.adt.platformoption.types.PlatformOptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcPlatformOption()  ||  (end== tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( begin.getHeadconcPlatformOption() ,( tom.platform.adt.platformoption.types.PlatformOptionList )tom_get_slice_concPlatformOption( begin.getTailconcPlatformOption() ,end,tail)) ;   }   

  
  
  public static final String PARSED_SUFFIX = ".tfix.parsed";
  public static final String PARSED_TABLE_SUFFIX = ".tfix.parsed.table";

  
  public static final PlatformOptionList PLATFORM_OPTIONS =
     tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make("parse", "", "Parser (not activated by default)",  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make( tom.platform.adt.platformoption.types.platformboolean.False.make() ) , "") , tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make("newparser", "np", "New Parser (not activated by default)",  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make( tom.platform.adt.platformoption.types.platformboolean.False.make() ) , "") , tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make("tomjava", "tj", "Parser tailored for Tom+Java (activated by default)",  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make( tom.platform.adt.platformoption.types.platformboolean.True.make() ) , "") , tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make("printcst", "cst", "print post-parsing cst (only with new parser)",  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make( tom.platform.adt.platformoption.types.platformboolean.False.make() ) , "") , tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make("printast", "ast", "print post-parsing ast",  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make( tom.platform.adt.platformoption.types.platformboolean.False.make() ) , "") , tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) ) ) ) ) 





;
  
  
  private String currentFileName;
  private Reader currentReader;
  
  
  private tom.engine.parser.antlr2.HostParser parser = null;

  private TomParserTool parserTool = null;
  
  
  public TomParserPlugin() {
    super("TomParserPlugin");
  }

  private TomParserTool getParserTool() {
    return this.parserTool;
  }
  
  
  public static tom.engine.parser.antlr2.HostParser createHostParser(Reader reader,String filename,
                                        HashSet<String> includedFiles,
                                        HashSet<String> alreadyParsedFiles,
                                        TomParserTool parserTool)
    throws FileNotFoundException,IOException {
    
    TokenStreamSelector selector = new TokenStreamSelector();
    
    tom.engine.parser.antlr2.HostLexer targetlexer = new tom.engine.parser.antlr2.HostLexer(reader);
    
    tom.engine.parser.antlr2.TomLexer tomlexer = new tom.engine.parser.antlr2.TomLexer(targetlexer.getInputState());
    
    tom.engine.parser.antlr2.BackQuoteLexer bqlexer = new tom.engine.parser.antlr2.BackQuoteLexer(targetlexer.getInputState());
    
    selector.addInputStream(targetlexer,"targetlexer");
    selector.addInputStream(tomlexer, "tomlexer");
    selector.addInputStream(bqlexer, "bqlexer");
    selector.select("targetlexer");
    
    
    return new tom.engine.parser.antlr2.HostParser(selector, filename,
        includedFiles, alreadyParsedFiles,
        parserTool);
  }

  
  public void setArgs(Object[] arg) {
    if (arg[0] instanceof TomStreamManager) {
      setStreamManager((TomStreamManager)arg[0]);
      currentFileName = getStreamManager().getInputFileName();  
      currentReader = getStreamManager().getInputReader();
      
    } else {
      System.out.println("(DEBUG) error old parser");
      TomMessage.error(getLogger(), null, 0, TomMessage.invalidPluginArgument,
          "TomParserPlugin", "[TomStreamManager]", getArgumentArrayString(arg));
    }
  }

  
  public synchronized void run(Map informationTracker) {
    long startChrono = System.currentTimeMillis();
    boolean intermediate = ((Boolean)getOptionManager().getOptionValue("intermediate")).booleanValue();
    boolean java         = ((Boolean)getOptionManager().getOptionValue("jCode")).booleanValue();
    boolean eclipse      = ((Boolean)getOptionManager().getOptionValue("eclipse")).booleanValue();
    boolean newparser    = ((Boolean)getOptionManager().getOptionValue("newparser")).booleanValue();
    boolean tomjava      = ((Boolean)getOptionManager().getOptionValue("tomjava")).booleanValue();
    boolean printcst     = ((Boolean)getOptionManager().getOptionValue("printcst")).booleanValue();
    boolean printast     = ((Boolean)getOptionManager().getOptionValue("printast")).booleanValue();

    SymbolTable symbolTable = getStreamManager().getSymbolTable();
    if(newparser==false && tomjava==false) {
      
      try {
        
        if(java && (!currentFileName.equals("-"))) {
          
          tom.engine.parser.antlr2.TomJavaParser javaParser = tom.engine.parser.antlr2.TomJavaParser.createParser(currentFileName);
          String packageName = "";
          try {
            packageName = javaParser.javaPackageDeclaration();
          } catch (TokenStreamException tse) {
            
          }
          
          getStreamManager().setPackagePath(packageName);
        }

        
        this.parserTool = new TomParserTool(getStreamManager(),getOptionManager());

        
        HashSet<String> includedFiles = new HashSet<String>();
        HashSet<String> alreadyParsedFiles = new HashSet<String>();
        parser = createHostParser(currentReader, currentFileName,includedFiles, alreadyParsedFiles, getParserTool());
        

        setWorkingTerm(parser.input());
        
        Iterator it = symbolTable.keySymbolIterator();
        while(it.hasNext()) {
          String tomName = (String)it.next();
          TomSymbol tomSymbol = getSymbolFromName(tomName);
          tomSymbol = symbolTable.updateConstrainedSymbolCodomain(tomSymbol, symbolTable);

          if(printast) {
            getParserTool().printTree(tomSymbol);
          }
        }

        if(printast) {
          getParserTool().printTree((Visitable)getWorkingTerm());
        }

        
        TomMessage.info(getLogger(), currentFileName, getLineFromTomParser(), TomMessage.tomParsingPhase,
            Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
      } catch (TokenStreamException e) {
        TomMessage.error(getLogger(), currentFileName, getLineFromTomParser(),
            TomMessage.tokenStreamException, e.getMessage());
        return;
      } catch (RecognitionException e){
        TomMessage.error(getLogger(), currentFileName, getLineFromTomParser(), 
            TomMessage.recognitionException, e.getMessage());
        return;
      } catch (TomException e) {
        TomMessage.error(getLogger(), currentFileName, getLineFromTomParser(), 
            e.getPlatformMessage(), e.getParameters());
        return;
      } catch (FileNotFoundException e) {
        TomMessage.error(getLogger(), currentFileName, 0, TomMessage.fileNotFound); 
        e.printStackTrace();
        return;
      } catch (Exception e) {
        e.printStackTrace();
        TomMessage.error(getLogger(), currentFileName, 0, TomMessage.exceptionMessage,
            getClass().getName(), currentFileName);
        return;
      }
      
      if(eclipse) {
        String outputFileName = getStreamManager().getInputParentFile()+
          File.separator + "."+
          getStreamManager().getRawFileName()+ PARSED_TABLE_SUFFIX;
        Tools.generateOutput(outputFileName, symbolTable.toTerm().toATerm());
      }
    } else if(newparser==true) { 
      
      try {

        if(!currentFileName.equals("-")) {
          
          this.parserTool = new TomParserTool(getStreamManager(),getOptionManager());

          tom.engine.parser.antlr4.TomParser parser = new tom.engine.parser.antlr4.TomParser(currentFileName, getParserTool(), symbolTable);
          ANTLRInputStream input = new ANTLRInputStream(currentReader);

          System.out.print("antlr4: " + currentFileName);
          long start = System.currentTimeMillis();
          if(java) {
            String packageName = parser.parseJavaPackage(input);
            if(packageName.length() > 0) {
              
              
              getStreamManager().setPackagePath(packageName);
            }
          }

          CstProgram cst = parser.parse(input);
          if(printcst) {
            getParserTool().printTree(cst);
          }
          System.out.print("\tparsing + building cst:" + (System.currentTimeMillis()-start) + " ms");

          start = System.currentTimeMillis();
          tom.engine.parser.antlr4.AstBuilder astBuilder = new tom.engine.parser.antlr4.AstBuilder(getParserTool(),symbolTable);
          Code code = astBuilder.convert(cst);
          System.out.println("\tbuilding ast:" + (System.currentTimeMillis()-start) + " ms");

          


          setWorkingTerm(code);
          
          Iterator it = symbolTable.keySymbolIterator();
          while(it.hasNext()) {
            String tomName = (String)it.next();
            TomSymbol tomSymbol = getSymbolFromName(tomName);
            

            if(printast) {
              getParserTool().printTree(tomSymbol);
            }
          }

          if(printast) {
            getParserTool().printTree((Visitable)getWorkingTerm());
          }
        }
        
        TomMessage.info(getLogger(), currentFileName, getLineFromTomParser(),
            TomMessage.tomParsingPhase,
            Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
      } catch(IOException e) {
        TomMessage.error(getLogger(), currentFileName, -1,
            TomMessage.fileNotFound, e.getMessage());
      }
    } else if(newparser==false && tomjava==true) {
      
      try {

        if(!currentFileName.equals("-")) {
          
          this.parserTool = new TomParserTool(getStreamManager(),getOptionManager());

          tom.engine.parser.tomjava.TomParser parser = new tom.engine.parser.tomjava.TomParser(currentFileName, getParserTool(), symbolTable);
          ANTLRInputStream input = new ANTLRInputStream(currentReader);

          System.out.print("tomjava antlr4: " + currentFileName);
          long start = System.currentTimeMillis();
          if(java) {
            String packageName = parser.parseJavaPackage(input);
            if(packageName.length() > 0) {
              
              
              getStreamManager().setPackagePath(packageName);
            }
          }

          CstBlockList cst = parser.parse(input, tom.engine.parser.tomjava.TomParser.JAVA_TOP_LEVEL);
          if(printcst) {
            getParserTool().printTree(cst);
          }
          System.out.print("\tparsing + building cst:" + (System.currentTimeMillis()-start) + " ms");

          start = System.currentTimeMillis();
          tom.engine.parser.tomjava.AstBuilder astBuilder = new tom.engine.parser.tomjava.AstBuilder(getParserTool(),symbolTable);
          Code code = astBuilder.convert(  tom.engine.adt.cst.types.cstprogram.Cst_Program.make(cst)  );
          System.out.println("\tbuilding ast:" + (System.currentTimeMillis()-start) + " ms");

          


          setWorkingTerm(code);
          
          Iterator it = symbolTable.keySymbolIterator();
          while(it.hasNext()) {
            String tomName = (String)it.next();
            TomSymbol tomSymbol = getSymbolFromName(tomName);
            

            if(printast) {
              getParserTool().printTree(tomSymbol);
            }
          }

          if(printast) {
            getParserTool().printTree((Visitable)getWorkingTerm());
          }
        }
        
        TomMessage.info(getLogger(), currentFileName, getLineFromTomParser(),
            TomMessage.tomParsingPhase,
            Integer.valueOf((int)(System.currentTimeMillis()-startChrono)));
      } catch(IOException e) {
        TomMessage.error(getLogger(), currentFileName, -1,
            TomMessage.fileNotFound, e.getMessage());
      }

    } else {
      System.out.println("no active parser");
    }

    if(intermediate) {
      Tools.generateOutput(getStreamManager().getOutputFileName() 
          + PARSED_SUFFIX, (tom.library.sl.Visitable)getWorkingTerm());
      Tools.generateOutput(getStreamManager().getOutputFileName() 
          + PARSED_TABLE_SUFFIX, symbolTable.toTerm().toATerm());
    }

  }
 
  
  public PlatformOptionList getDeclaredOptionList() {
    return PLATFORM_OPTIONS; 
  }

  
  private int getLineFromTomParser() {
    if(parser == null) {
      return TomMessage.DEFAULT_ERROR_LINE_NUMBER;
    } 
    return parser.getLine();
  }



} 
