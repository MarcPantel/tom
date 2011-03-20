header{/*
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
  
}

{
import java.util.LinkedList;

import tom.engine.TomBase;
import tom.engine.xml.Constants;

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
import tom.engine.adt.code.types.bqterm.Composite;

import tom.engine.tools.ASTFactory;
import tom.engine.tools.SymbolTable;
import antlr.TokenStreamSelector;
import aterm.*;
}
class BackQuoteParser extends Parser;

options{
    // antlr does not catch exceptions automaticaly
    defaultErrorHandler = false;
    // default lookahead
    k=1;
}

{
	private final static String DEFAULT_MODULE_NAME = "default";
	private final static String TNODE_MODULE_NAME = "tnode";
    %include{ ../adt/tomsignature/TomSignature.tom }
    
    // the lexer for backquote language
    BackQuoteLexer bqlexer = null;

    // the parser for tom language
    TomParser tomparser = null;

    // the current file's name
    String currentFile(){
      return tomparser.currentFile();
    }

    //constructor
    public BackQuoteParser(ParserSharedInputState state, TomParser tomparser){
      this(state);
      this.tomparser = tomparser;
      bqlexer = (BackQuoteLexer) selector().getStream("bqlexer");
    }

    // add token t to the buffer containing the target code
    private void addTargetCode(Token t){
      tomparser.addTargetCode(t);
    }

    // returns the selector
    private TokenStreamSelector selector(){
      return tomparser.selector();
    }
    
   private BQTerm buildBqAppl(Token id, LinkedList<BQTerm> blockList, BQTerm term, boolean composite) {
     OptionList option = `concOption(OriginTracking(Name(id.getText()), id.getLine(), id.getColumn(), currentFile()), ModuleName(DEFAULT_ MODULE_NAME));
     Composite target = (Composite) ((term==null)?
       `Composite():
       `Composite(CompositeTL(ITL(".")),CompositeBQTerm(term)));

     if(composite) {
			 BQTermList list = ASTFactory.makeBQTermList(blockList);
			 return `Composite(CompositeBQTerm(BQAppl(option,Name(id.getText()),list)),target*);
     } else {
			 return `Composite(CompositeBQTerm(BQVariable(option,Name(id.getText()),SymbolTable.TYPE_UNKNOWN)),target*);
		 }

   }
 
   /*
    * add a term to a list of term
    * when newComposite is true, this means that a ',' has been read before the term
    */
    private void addTerm(LinkedList<BQTerm> list, BQTerm term, boolean newComposite) {
      // if the list is empty put an empty composite in it to simplify the code
      if(list.isEmpty()) {
        list.add(`Composite());
      }
      BQTerm lastElement = (BQTerm) list.getLast();
      /*
       * when newComposite is true, we add the term, eventually wrapped by a Composite 
       * otherwise, the term is inserted (eventually unwrapped) into the last Composite of the list
       */
      if(newComposite) {
        %match(lastElement, term) {
          Composite(_*), t2@Composite(_*) -> { 
            list.add(`t2); 
            return; 
          }
          Composite(_*), t2 -> { 
            list.add(`Composite(CompositeBQTerm(t2))); 
            return; 
          }
        }
      } else {
        %match(lastElement, term) {
          Composite(l1*), Composite(l2*) -> { 
            list.set(list.size()-1,`Composite(l1*,l2*)); 
            return;
          }
          Composite(l1*), t2 -> { 
            list.set(list.size()-1,`Composite(l1*,CompositeBQTerm(t2))); 
            return;
          }
        }
      }
    }

    // sorts attributes of xml term with lexicographical order
    private BQTermList sortAttributeList(BQTermList list){
      %match(list) {
        concBQTerm() -> { return list; }
        concBQTerm(X1*,e1,X2*,e2,X3*) -> {
          %match(e1, e2) {
            BQAppl[Args=concBQTerm(BQAppl[AstName=Name(name1)],_*)],
            BQAppl[Args=concBQTerm(BQAppl[AstName=Name(name2)],_*)] -> {
              if(`name1.compareTo(`name2) > 0) {
                return `sortAttributeList(concBQTerm(X1*,e2,X2*,e1,X3*));
              }
            }
          }
        }
      }
      return list;
    }
    
    // built a sorted BQTermList from a LinkedList
    private BQTermList buildAttributeList(LinkedList<BQTerm> list) {
      return sortAttributeList(ASTFactory.makeBQTermList(list));
    }
    
    // add double quotes around a string
    private String encodeName(String name) {
      return "\"" + name + "\"";
    }

}

/*
 * Backquoted Term
 */
beginBackquote returns [BQTerm result]
{ 
  result = null; 
  BQTermList context = `concBQTerm();
}
:
ws (BQ_BACKQUOTE)? ( result = mainBqTerm[context] ) { selector().pop(); }
;


mainBqTerm [BQTermList context] returns [BQTerm result]
{
    result = null;
    BQTerm term = null;
    BQTermList list = `concBQTerm();

    Token t = null;
    LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
}
    :
        (
         // xml(...) or (...)
            result = basicTerm[list]            
        | id:BQ_ID
          (
           // `X*
           {LA(1) == BQ_STAR}? BQ_STAR 
           {   
             String name = id.getText();
             Option ot = `OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile());
             result = `BQVariableStar(concOption(ot),Name(name),SymbolTable.TYPE_UNKNOWN);  
           }
           // `X*{type}
           /*
           | {LA(1) == BQ_STAR && LA(2) == BQ_LBRACE}? BQ_STAR BQ_LBRACE type1:BQ_ID BQ_RBRACE

           {   
             String name = id.getText();
             OptionList ol = `concOption(TypeForVariable(type1.getText()),OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile()));
             result = `BQVariableStar(ol,Name(name),SymbolTable.TYPE_UNKNOWN);  
           }
           */
           | {LA(1) == BQ_RBRACE}?
           {
           // generate an ERROR when a '}' is encoutered
           //System.out.println("ERROR");
           }
           | ws /*ws*/ 
                (
                 // `x(...)
                 {LA(1) == BQ_LPAREN}? BQ_LPAREN ws ( termList[blockList,list] )? BQ_RPAREN 
                 {   
                   result = buildBqAppl(id,blockList,term,true);
                 }
                 // `X{type}
                 | {LA(1) == BQ_LBRACE}? BQ_LBRACE type:BQ_ID BQ_RBRACE
                {   
                   String name = id.getText();
                   OptionList ol = `concOption(TypeForVariable(type.getText()), OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile()), ModuleName(DEFAULT_MODULE_NAME));
                   result = `BQVariable(ol,Name(name),SymbolTable.TYPE_UNKNOWN);
                }
                 // `x
                |   t = targetCode 
                 {
                   //System.out.println("targetCode = " + t);
                   addTargetCode(t);
                   String name = id.getText();
                   OptionList ol = `concOption(OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile()), ModuleName(DEFAULT_MODULE_NAME));
                   //result = `BQAppl(ol,Name(name),concBQTerm());
                   result = `BQVariable(ol,Name(name),SymbolTable.TYPE_UNKNOWN);
                 }
                )
            )
        )
    ;

bqTerm [BQTermList context] returns [BQTerm result]
{
    result = null;
    BQTerm term = null;
    BQTermList xmlTermList = `concBQTerm();

    Token t = null;
    LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
    boolean arguments = false;
}
    :
         // xml(...) or (...)
      result = basicTerm[context]
    |   id:BQ_ID
        (
         // X*
            {LA(1) == BQ_STAR}? BQ_STAR
            {   
              String name = id.getText();
              Option ot = `OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile());
              result = `BQVariableStar(concOption(ot),Name(name),SymbolTable.TYPE_UNKNOWN);      
            }
            
            |  ws /*ws*/ 
            // x(...)
            (
             {LA(1) == BQ_LPAREN}? BQ_LPAREN {arguments = true;} ws (termList[blockList,context])? BQ_RPAREN 
            )?
            ( (BQ_DOT term = bqTerm[null] ) => BQ_DOT term = bqTerm[context] )?
            {   
                result = buildBqAppl(id,blockList,term,arguments);
            }
        )
    |
        // <...> ... </...>
        (xmlTerm[null]) => result = xmlTerm[context]
        
    |
        // x
        t = target
        {
          //System.out.println("target = " + t);
          result = `Composite(CompositeTL(ITL(t.getText())));
        }
    ;


/*
 *    xml(c1,...,cn,t)
 * or (t1 ... tn) 
 */
basicTerm [BQTermList context] returns [BQTerm result]
{
    result = null;
    BQTerm term = null;
    BQTermList localContext = `concBQTerm();

    LinkedList<BQTerm> blockList = new LinkedList<BQTerm>();
}
    :
        (
            XML ws BQ_LPAREN ws 
            ( 
             (bqTerm[null] BQ_COMMA) => term = bqTerm[context] BQ_COMMA ws
             { blockList.add(term); } 
             )* 
            { localContext = ASTFactory.makeBQTermList(blockList); }
            result = bqTerm[localContext]
            BQ_RPAREN
            
        |   BQ_LPAREN ws ( termList[blockList,context] )? BQ_RPAREN
            {
              Composite compositeList = ASTFactory.makeComposite(blockList);
                result = `Composite(
                        CompositeTL(ITL("(")),
                        compositeList*,
                        CompositeTL(ITL(")"))
                );
            }
        )
    ;

/*
 * termList parses a list of terms, eventually separated by a comma
 */
termList [LinkedList<BQTerm> list,BQTermList context]
{
    BQTerm term = null;
}
    :
      term = bqTerm[context] /*ws*/ { addTerm(list,term,false); }
        ( ( c:BQ_COMMA  ws )? term = bqTerm[context] /*ws*/
        { addTerm(list,term, (c!=null)); c = null; }
        )*
    ;

xmlAttributeStringOrBQVariable returns [BQTerm result]
  { result = null; } : 
  (
     id:BQ_ID 
		 {
       String name = id.getText();
       OptionList ol = `concOption(OriginTracking(Name(name), id.getLine(), id.getColumn(), currentFile()), ModuleName(DEFAULT_MODULE_NAME));
       result = `BQVariable(ol,Name(name),SymbolTable.TYPE_UNKNOWN);
		   //result = `Composite(CompositeTL(ITL(id.getText()))); 
		 }
   | string:BQ_STRING
	   {
		   result = `Composite(CompositeTL(ITL(string.getText())));
		 }
  )
    ;

ws  :  ( options{ greedy = true; }: BQ_WS )*
    ;
 
target returns [Token result]
{
    result = null;
}
    :
        in:BQ_INTEGER {result = in;}
    |   str:BQ_STRING {result = str;}
    |   m:BQ_MINUS {result = m;}
    |   s:BQ_STAR {result = s;}
    |   w:BQ_WS {result = w;}
    |   d:BQ_DOT {result = d;} 
    |   dq:DOUBLE_QUOTE {result = dq;}
    |   xs:XML_START {result = xs;}
    |   xe:XML_EQUAL {result = xe;}
    |   xc:XML_CLOSE  {result = xc;}
    |   a:ANY {result = a;}
    ;

targetCode returns [Token result]
{
    result = null;
}
    :
        result = target
    |   c:BQ_COMMA {result = c;}   
    |   i:BQ_ID {result = i;} 
    |   r:BQ_RPAREN {result = r;}
    |   t:XML_START_ENDING    {result = t;}
    |   xcs:XML_CLOSE_SINGLETON {result = xcs;}
    |   xt:XML_TEXT {result = xt;}
    |   xc:XML_COMMENT {result = xc;}
    |   xp:XML_PROC {result = xp;}
    ;



/*
 * XML
 */

xmlAttribute [BQTermList context] returns [BQTerm result]
{
    result = null;
    BQTerm value = null;
}
    :
        (
            id:BQ_ID
            (
                ws XML_EQUAL ws value = xmlAttributeStringOrBQVariable
                {
                    BQTermList args = `concBQTerm(
                        BQAppl(
                            concOption(Constant(),ModuleName(TNODE_MODULE_NAME)),
                            Name(encodeName(id.getText())),
                            concBQTerm()
                        ),
                        BQAppl(
                            concOption(Constant(),ModuleName(TNODE_MODULE_NAME)),
                            Name("\"true\""),
                            concBQTerm()
                        ),
                        value
                    );
		    if(context != null) {
		    args = `concBQTerm(context*,args*);
		    }
		    result = `BQAppl(
		      concOption(ModuleName(TNODE_MODULE_NAME)),
		      Name(Constants.ATTRIBUTE_NODE),
		      args);
                }
            | BQ_STAR
              {
		result = `BQVariableStar(
		    concOption(),
		    Name(id.getText()),
		    SymbolTable.TYPE_UNKNOWN);
              }
            )
        )
    ;

xmlAttributeList [LinkedList<BQTerm> attributeList, BQTermList context]
{
    BQTerm term = null;
}
    :
        (
            term = xmlAttribute[context] ws
            {
                attributeList.add(term);
            }
        )*
    ;

xmlChildren[LinkedList<BQTerm> children, BQTermList context]
{
    BQTerm term = null;
}
    :
        (
            {LA(1) != XML_START_ENDING && LA(1) != XML_CLOSE}? 
            term = bqTerm[context]
            {children.add(term);}
        )*
    ;

xmlTerm[BQTermList context] returns [BQTerm result]
{
    result = null;
    BQTermList attributeBQTermList = `concBQTerm();
    BQTermList childrenBQTermList = `concBQTerm();
    BQTerm term = null;

    LinkedList<BQTerm> attributes = new LinkedList<BQTerm>();
    LinkedList<BQTerm> children = new LinkedList<BQTerm>();
}
    :
        (
            XML_START ws id:BQ_ID ws xmlAttributeList[attributes,context]
            {
                attributeBQTermList = buildAttributeList(attributes);
            }
                
                ( 
                    XML_CLOSE_SINGLETON ws
                |   XML_CLOSE
                    ws xmlChildren[children,context]
                {
                  childrenBQTermList = ASTFactory.makeBQTermList(children);
                }
                    XML_START_ENDING ws BQ_ID ws XML_CLOSE ws
                )
                {
                    BQTermList args = `concBQTerm(
                        BQAppl(
                            concOption(Constant(),ModuleName(TNODE_MODULE_NAME)),
                            Name(encodeName(id.getText())),
                            concBQTerm()
                        ),
                        BQAppl(
                            concOption(ModuleName(TNODE_MODULE_NAME)),
                            Name(Constants.CONC_TNODE),
                            attributeBQTermList
                        ),
                        BQAppl(
                            concOption(ModuleName(TNODE_MODULE_NAME)),
                            Name(Constants.CONC_TNODE),
                            childrenBQTermList
                        )
                    );
                    
                    if(context == null){
                        result = `BQAppl(
                            concOption(ModuleName(TNODE_MODULE_NAME)),
                            Name(Constants.ELEMENT_NODE),
                            args
                        );
                    } else {
											result = `BQAppl(
													concOption(ModuleName(TNODE_MODULE_NAME)),
													Name(Constants.ELEMENT_NODE),
													concBQTerm(
														context*,
														args*)
                        );
                    }

                }

        |   XML_TEXT BQ_LPAREN term = bqTerm[context] BQ_RPAREN
            {
                result = `BQAppl(
										concOption(ModuleName(TNODE_MODULE_NAME)),
                    Name(Constants.TEXT_NODE),
                    concBQTerm(
                        context*,
                        term
                    )
                );
            }
        )
        
    ;


class BackQuoteLexer extends Lexer;
options {
    charVocabulary = '\u0000'..'\uffff'; // each character can be read
    k=2;
}

tokens {
    XML = "xml";
}

{
  public void uponEOF()
    throws TokenStreamException, CharStreamException
    {
      throw new TokenStreamException("Premature EOF");
    }
}

BQ_LPAREN      :    '('   ;
BQ_RPAREN      :    ')'   ;
BQ_LBRACE      :    "{"   ;
BQ_RBRACE      :    "}"   ;
BQ_COMMA       :    ','   ;
BQ_STAR        :    '*'   ;
BQ_BACKQUOTE   :   "`" ;

//XML Tokens
XML_EQUAL   :   '=' ;
XML_START_ENDING    : "</" ;
XML_CLOSE_SINGLETON : "/>" ;  
XML_START   :   '<';
XML_CLOSE   :   '>' ;
DOUBLE_QUOTE:   '\"';
XML_TEXT    :   "#TEXT";
XML_COMMENT :   "#COMMENT";
XML_PROC    :   "#PROCESSING-INSTRUCTION";

BQ_DOT    :    '.'   ;

// tokens to skip : white spaces
BQ_WS : ( ' '
    | '\t'
    | '\f'
    // handle newlines
    | ( "\r\n"  // Evil DOS
      | '\r'    // Macintosh
      | '\n'    // Unix (the right way)
      )
      { newline(); }
    )
    ;

XML_SKIP
    :
        "<?" ( ~('>') )* '>'
        {
            $setType(Token.SKIP);
        }
    ;

BQ_ID
options{ testLiterals = true; }   
    :
        (
            (BQ_MINUS_ID) => BQ_MINUS_ID
        |   BQ_SIMPLE_ID
        )
    ;

protected
BQ_SIMPLE_ID
options{ testLiterals = true; }   
    :   ('_')? ('a'..'z' | 'A'..'Z') 
        ( 
            ('a'..'z' | 'A'..'Z') 
        |   '_' 
//        |   BQ_DOT
        |   BQ_DIGIT
        )*
    ;

protected
BQ_MINUS_ID
    :
        BQ_SIMPLE_ID BQ_MINUS BQ_SIMPLE_ID 
        (BQ_MINUS_ID_PART)?
    ;

protected
BQ_MINUS_ID_PART :
  (
        BQ_MINUS BQ_SIMPLE_ID
  )+ 
;

BQ_INTEGER :   ( BQ_MINUS )? ( BQ_DIGIT )+     ;

BQ_STRING  :   '"' (BQ_ESC|~('"'|'\\'|'\n'|'\r'))* '"'
    ;

ANY 
options{ testLiterals = true; } 
//    :   '\u0000'..'\uffff'  
    :  .
    ;
   
BQ_MINUS   :   '-'  ;

protected
BQ_DIGIT   :   ('0'..'9')  ;

protected 
BQ_UNDERSCORE : {!Character.isJavaIdentifierPart(LA(2))}? '_' ;

protected
BQ_ESC
  : '\\'
    ( 'n'
    | 'r'
    | 't'
    | 'b'
    | 'f'
    | '"'
    | '\''
    | '\\'
    | ('u')+ BQ_HEX_DIGIT BQ_HEX_DIGIT BQ_HEX_DIGIT BQ_HEX_DIGIT
    | '0'..'3'
      (
        options {
          warnWhenFollowAmbig = false;
        }
      : '0'..'7'
        (
          options {
            warnWhenFollowAmbig = false;
          }
        : '0'..'7'
        )?
      )?
    | '4'..'7'
      (
        options {
          warnWhenFollowAmbig = false;
        }
      : '0'..'7'
      )?
    )
  ;

protected
BQ_HEX_DIGIT
  : ('0'..'9'|'A'..'F'|'a'..'f')
  ;

