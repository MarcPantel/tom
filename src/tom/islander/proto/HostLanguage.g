grammar HostLanguage;

options {
  backtrack=true;
  output=AST;
  ASTLabelType=Tree;
  tokenVocab=HostTokens;
}

@header{
//  package islander.proto;
  import org.antlr.runtime.tree.*;
  import org.antlr.runtime.*;
}

@lexer::header{
//  package islander.proto;
  import org.antlr.runtime.tree.*;
}

@parser::members{
  public static Tree intermediateBQResult; //subTree
  public static Tree intermediateTomResult; //subTree
}

@lexer::members{
  public static int hnesting = 0;
}

// start
program :
  block* -> ^(Program block*)
  ;

block :
   hostConstruct -> ^(HostBlock hostConstruct)
  | tomConstruct -> ^(TomBlock tomConstruct)
  /* and few other : all '%something' */
  | backquoteConstruct -> ^(BackQuoteBlock backquoteConstruct)
//  | '{' block '}' -> block

;

//host
hostConstruct :
  (variable)* (method)+ -> ^(HostConstruct ^(VariableList (variable)*) ^(MethodList (method)+))
  | statement -> ^(StatementConstruct statement)
  ;

variable :
  'int' id=ID e=('=' expr)? ';' -> ^(Variable $id)
/*  -> {e!=null} ^(VarExp $id $e)*/
  
  ;

method  :
  'method' id=ID '(' ')' '{' (variable)* (statement)+ '}' -> ^(Method $id  ^(VariableList (variable)*) ^(StatementList (statement)+))
  ;

statement :
  id=ID '=' expr ';' -> ^(Equal $id expr)
  | 'return' expr ';' -> ^(Return expr)
  ;

expr :
  ID
  | INT
  ;


//Tom
tomConstruct :
  MATCH -> ^({intermediateTomResult})
  /* and other keywords  */
  ;

//BackQuote
backquoteConstruct :
   BACKQUOTE  ->  ^({intermediateBQResult}) 
  ;

//Lexer
ID  : ('a'..'z'|'A'..'Z')+ ;

INT : ('0'..'9')+ ;

LBRACE : '{' //{ hnesting++; System.out.println("host hnesting++ = " + hnesting);}
         ;

RBRACE : '}'
  {
    if ( hnesting<=0 ) {
      emit(Token.EOF_TOKEN);
      //System.out.println("exit embedded hostlanguage\n");
    }
    else {
      hnesting--;
      //System.out.println("host hnesting-- = " + hnesting);
    }
  }
  ;

MATCH
: '%match'
  {
    System.out.println("\nbefore new Tom*");
    TomLanguageLexer lexer = new TomLanguageLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    System.out.println("host, tokenstream = " + tokens.toString() + " /fin");
    System.out.println("host, tokens list = " + tokens.getTokens().toString());
    TomLanguageParser parser = new TomLanguageParser(tokens);
    System.out.println("before parser.matchConstruct()");
    TomLanguageParser.matchConstruct_return res = parser.matchConstruct();
    System.out.println("(host - tom) res.getTree() = " + ((Tree)res.getTree()).toStringTree());
    HostLanguageParser.intermediateTomResult = (Tree)res.getTree();
  }
;

BACKQUOTE
: '`('
{
  System.out.println("\nbefore new BackQuote*");
  BackQuoteLanguageLexer lexer = new BackQuoteLanguageLexer(input);
  CommonTokenStream tokens = new CommonTokenStream(lexer);
  System.out.println("host, tokens = " + tokens.toString() + " /fin");
  System.out.println("host, tokens list = " + tokens.getTokens().toString());
  BackQuoteLanguageParser parser = new BackQuoteLanguageParser(tokens);
  System.out.println("before parser.backQuoteConstruct()");
  BackQuoteLanguageParser.backQuoteConstruct_return res = parser.backQuoteConstruct();
  System.out.println("(host - bq) res.getTree() = " + ((Tree)res.getTree()).toStringTree());
  HostLanguageParser.intermediateBQResult = (Tree)res.getTree();
  //System.out.println("(host - bq) res.getTree() = " + HostLanguageParser.intermediateBQResult.toStringTree());
}
  ;

WS  : (' '|'\t'|'\n')+ { $channel=HIDDEN; } ;

SL_COMMENT :
  '//'
  (~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
  { $channel=HIDDEN; }
  ;

ML_COMMENT :
  '/*' ( options {greedy=false;} : . )* '*/'
  { $channel=HIDDEN; }
  ;

