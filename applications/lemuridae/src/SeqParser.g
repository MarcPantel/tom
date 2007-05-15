//header {
//package parsingtests;
//}

class SeqParser extends Parser;
options {
  buildAST = true;  // uses CommonAST by default
  k = 3;
  defaultErrorHandler=false;
}

tokens {
    VOIDLIST;
    RULEALONE;
}

/*
seq: SEQ list_pred  
   | list_pred SEQ list_pred 
   ;

list_pred: pred (LIST^ pred)* ;
*/

pred: FORALL^ ID COMMA! implpred
    | EXISTS^ ID COMMA! implpred
    | implpred
    ;

implpred: equivpred (IMPL^ pred)? ;

equivpred: andpred (EQUIV^ andpred)* ;

andpred: orpred (AND^ orpred)* ;

orpred: negpred (OR^ negpred)* ;

negpred: NOT^ simplepred
       | simplepred
       ;
       
simplepred: LPAREN! pred RPAREN!
          | atom
          ;
atom: appl
    | ID
    | BOTTOM
    | TOP
    ;

appl : ID LPAREN^ term_list  RPAREN!
     ;

term_list: term (COMMA^ term)* 
         | { #term_list = #[VOIDLIST,"VOIDLIST"]; }
         ;

term : minterm (PLUS^ minterm)*
     ;

minterm: mterm (MINUS^ mterm)*
       ;

mterm : divterm (TIMES^ divterm)*
      ;

divterm: fterm (DIV^ fterm)*
       ;
      
fterm : LPAREN! term RPAREN!
      | funappl
      | ID
      | NUMBER
      ;

funappl : ID LPAREN^ term_list RPAREN!;


// points d'entree pour les programmes

command: PROOF^ ID COLUMN! pred DOT!
       | RRULE^ atom ARROW! pred DOT!
       | TRULE^ term ARROW! term DOT!
       | PRULE^ atom ARROW! pred DOT!
       | NORMALIZE! TERM^ term DOT!
       | NORMALIZE! PROP^ pred DOT!
       | DISPLAY^ ID DOT!
       | PROOFTERM^ ID DOT!
       | QUIT DOT!
       | REINIT DOT!
       | PROOFCHECK^ ID DOT!
       | PRINT^ ID DOT!
       | RESUME^ ID DOT!
       | GIBBER DOT!
       | IMPORT^ PATH DOT!
       | EOF
       ;

proofcommand: FOCUS^ ID DOT!
            | RRULE^ NUMBER DOT!
            | RRULE DOT { #proofcommand = #[RULEALONE,"RULEALONE"]; }
            | CUT^ pred DOT!
            | THEOREM^ ID DOT!
            | NORMALIZE^ DOT!
            | ASKRULES DOT!
            | DISPLAY DOT!
            | ID DOT!
            | QUIT DOT!
            | REINIT DOT!
            | ABORT DOT!
            | EOF
            ;

start1: pred DOT! ;
start2: term DOT! ;
ident: ID DOT! ;
      
class SeqLexer extends Lexer;
options {
  k=7;
  defaultErrorHandler=false;
}

WS
  : (' '
    | '\t'
    | '\n'
    | '\r')
    { _ttype = Token.SKIP; }
  ;

SL_COMMENT
: "//" (~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
{
  $setType(Token.SKIP);
  newline();
}
;

ML_COMMENT
: "/*"
(
 options {
 generateAmbigWarnings=false;
 }
 :
 { LA(2)!='/' }? '*'
 |       '\r' '\n' {newline();}
 |       '\r'      {newline();}
 |       '\n'      {newline();}
 |       ~('*'|'\n'|'\r')
 )*
"*/"
{$setType(Token.SKIP);}
;

LPAREN : '(' ;
RPAREN : ')' ;
SEQ : "|-";
IMPL : "=>";
EQUIV : "<=>";
OR : "\\/" ;
AND :  "/\\" ;
NOT : '!'
    | '^'
    | '~' ;
DOT : '.' ;
COMMA : ',' ;
FORALL  : "\\A" | "forall";
EXISTS : "\\E" | "exists";
BOTTOM : "\\B" | "False";
TOP: "\\T" | "True";
ARROW : "->";
COLUMN: ':';
TIMES: '*';
PLUS: '+';
DIV: '/';
MINUS: '-';
GT: '>';
LT: '<';

PROOF: "proof";
RRULE: "rule";
TRULE: "termrule";
PRULE: "proprule";
FOCUS: "focus";
ASKRULES: "showrules";
CUT: "cut";
DISPLAY: "display";
PROOFTERM: "proofterm";
QUIT: "quit";
REINIT: "init";
THEOREM: "theorem";
PRINT: "print";
PROOFCHECK: "proofcheck";
ABORT: "abort";
RESUME: "resume";
GIBBER: "gibber";
IMPORT: "import";
NORMALIZE: "reduce";
TERM: "term";
PROP: "proposition";


//VAR: ('a'..'z')('a'..'z'|'0'..'9')*;
ID : ('_'|'A'..'Z'|'a'..'z')('_'|'A'..'Z'|'a'..'z'|'0'..'9')*;
NUMBER: ('0'..'9')+;
PATH:  '\"'((~'\"')+)'\"';
