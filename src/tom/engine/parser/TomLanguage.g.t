header{
/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2008, INRIA
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
import java.io.StringReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.engine.exception.TomException;

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

import tom.engine.tools.SymbolTable;
import tom.engine.tools.ASTFactory;
import tom.engine.xml.Constants;
import tom.platform.OptionManager;
import tom.platform.PlatformLogRecord;
import aterm.*;
import antlr.TokenStreamSelector;
}
class TomParser extends Parser;

options{
    k=1; // the lookahead value during parsing
    defaultErrorHandler = false;        
}

    {
    //--------------------------
    %include{ ../adt/tomsignature/TomSignature.tom }
    //--------------------------
        
    public String currentFile(){
        return targetparser.getCurrentFile();
    }

    // the default-mode parser
    private HostParser targetparser;
    protected BackQuoteParser bqparser;
    private TomLexer tomlexer;

    //store information for the OriginalText contained in the OptionList
    private StringBuilder text = new StringBuilder();
    
    private int lastLine; 

    private SymbolTable symbolTable;

    public TomParser(ParserSharedInputState state, HostParser target,
                     OptionManager optionManager){
        this(state);
        this.targetparser = target;
        this.bqparser = new BackQuoteParser(state,this);
        this.tomlexer = (TomLexer) selector().getStream("tomlexer");
        this.symbolTable = target.getSymbolTable();
    }
    
    private void putType(String name, TomType type) {
        symbolTable.putType(name,type);
    }

    private void putSymbol(String name, TomSymbol symbol) {
        symbolTable.putSymbol(name,symbol);
    }
    
    private int getLine(){
        return tomlexer.getLine();
    }

    public void updatePosition(int i, int j){
        targetparser.updatePosition(i,j);
    }
    
    public void addTargetCode(Token t){
        targetparser.addTargetCode(t);
    }

    private void setLastLine(int line){
        lastLine = line;
    }

    private void clearText(){
        text.delete(0,text.length());
    }

    protected TokenStreamSelector selector(){
        return targetparser.getSelector();
    }
    
    private Logger getLogger() {
      return Logger.getLogger(getClass().getName());
    }

}

constant returns [Token result]
{
    result = null;
}
  : (
          t1:NUM_INT {result = t1;}
        | t2:CHARACTER {result = t2;}
        | t3:STRING {result = t3;}
        | t4:NUM_FLOAT {result = t4;}
        | t5:NUM_LONG {result = t5;}
        | t6:NUM_DOUBLE {result = t6;}
        )
    ;

// the %match construct :
matchConstruct [Option ot] returns [Instruction result] throws TomException
{ 
    result = null;
    OptionList optionList = `concOption(ot,ModuleName(TomBase.DEFAULT_MODULE_NAME));
    List argumentList = new LinkedList();
    List constraintInstructionList = new LinkedList();
    TomList subjectList = null;
    TomType patternType = SymbolTable.TYPE_UNKNOWN;
}
  : (
            LPAREN matchArguments[argumentList] RPAREN
            LBRACE { subjectList = ASTFactory.makeList(argumentList); }
            ( 
             patternInstruction[subjectList,constraintInstructionList,patternType]
            )* 
            t1:RBRACE 
            { 
               result = `Match(ASTFactory.makeConstraintInstructionList(constraintInstructionList),optionList);
               // update for new target block...
               updatePosition(t1.getLine(),t1.getColumn());
               // Match is finished : pop the tomlexer and return in the target parser.  
               selector().pop(); 
            }
            |
            LBRACE { subjectList = ASTFactory.makeList(argumentList); }
            ( 
             constraintInstruction[constraintInstructionList,patternType]
            )* 
            t2:RBRACE 
            { 
               result = `Match(ASTFactory.makeConstraintInstructionList(constraintInstructionList),optionList);
               // update for new target block...
               updatePosition(t2.getLine(),t2.getColumn());
               // Match is finished : pop the tomlexer and return in the target parser.  
               selector().pop(); 
            }

        )
  ;

matchArguments [List list] throws TomException
    :   
        ( matchArgument[list] ( COMMA matchArgument[list] )*)
    ;

matchArgument [List list] throws TomException
{
  TomTerm subject1 = null;
  TomTerm subject2 = null;
  TomType tomType = null;
  
  String s1 = null;
  String s2 = null;
}
    :   

    //(type:ALL_ID { tomType = `TomTypeAlone(type.getText()); })?
    //(BACKQUOTE)?
    subject1 = plainTerm[null,null,0] { s1 = text.toString();text.delete(0, text.length()); }
    (BACKQUOTE { text.delete(0, text.length()); } )?
    (subject2 = plainTerm[null,null,0] { s2 = text.toString(); })?
{
      if(subject2==null) {
        // System.out.println("matchArgument = " + subject1);
        list.add(subject1);        
      } else {
        if(subject1.isVariable()) {
          String type = subject1.getAstName().getString();
          %match(subject2){
            Variable[AstName=name] -> {
              Option ot = `OriginTracking(name, lastLine, currentFile());
              list.add(`Variable(concOption(ot),name,TomTypeAlone(type),concConstraint()));  
              return;
            }
            t@TermAppl[] -> {
              list.add(`BuildReducedTerm(t,TomTypeAlone(type)));
              return;
            }
          }        
        }  
        throw new TomException(TomMessage.invalidMatchSubject, new Object[]{subject1, subject2});
      }
}
;

patternInstruction [TomList subjectList, List list, TomType rhsType] throws TomException
{    
    List<Option> optionListLinked = new LinkedList<Option>();
    List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
        
    Constraint constraint = `TrueConstraint();    
    Constraint constr = null;
    OptionList optionList = null;
    Option option = null;
        
    boolean isAnd = false;
    
    clearText();
}
    :   (
            ( (ALL_ID COLON) => label:ALL_ID COLON )?
             option = matchPattern[matchPatternList,true] 
            {
              if(matchPatternList.size() != subjectList.length()) {                       
                getLogger().log(new PlatformLogRecord(Level.SEVERE, TomMessage.badMatchNumberArgument,
                    new Object[]{new Integer(subjectList.length()), new Integer(matchPatternList.size())},
                    currentFile(), getLine()));
                return;
              }
              
              int counter = 0;
              %match(subjectList) {
                concTomTerm(_*,subjectAtIndex,_*) -> {
                  constraint = `AndConstraint(constraint,MatchConstraint(matchPatternList.get(counter),subjectAtIndex));
                  counter++;
                }
              }
              
              optionList = `concOption(option, OriginalText(Name(text.toString())));
              
              matchPatternList.clear();
              clearText();
            } 
            (
              ( 
               AND_CONNECTOR { isAnd = true;} 
               | OR_CONNECTOR  { isAnd = false;} 
              )
//              constr = matchConstraintComposition[optionListLinked]
              constr = matchOrConstraint[optionListLinked]
              { 
                constraint = isAnd ? `AndConstraint(constraint,constr) : `OrConstraint(constraint,constr);
              }                                         
            )?
            arrowAndAction[list,optionList,optionListLinked,label,rhsType,constraint]
        )
    ;

visitInstruction [TomList subjectList, List list, TomType rhsType] throws TomException
{
    List<Option> optionListLinked = new LinkedList<Option>();
    List<TomTerm> matchPatternList = new LinkedList<TomTerm>();
        
    Constraint constraint = `TrueConstraint();    
    Constraint constr = null;
    OptionList optionList = null;
    Option option = null;
        
    boolean isAnd = false;
 
    List blockList = new LinkedList();
    TomTerm rhsTerm = null;
   
    clearText();
}
    :   (
            ( (ALL_ID COLON) => label:ALL_ID COLON )?
             option = matchPattern[matchPatternList,true] 
            {
              if(matchPatternList.size() != subjectList.length()) {                       
                getLogger().log(new PlatformLogRecord(Level.SEVERE, TomMessage.badMatchNumberArgument,
                    new Object[]{new Integer(subjectList.length()), new Integer(matchPatternList.size())},
                    currentFile(), getLine()));
                return;
              }
              
              int counter = 0;
              %match(subjectList) {
                concTomTerm(_*,subjectAtIndex,_*) -> {
                  constraint = `AndConstraint(constraint,MatchConstraint(matchPatternList.get(counter),subjectAtIndex));
                  counter++;
                }
              }
              
              optionList = `concOption(option, OriginalText(Name(text.toString())));
              
              matchPatternList.clear();
              clearText();
            } 
            (
              ( 
               AND_CONNECTOR { isAnd = true;} 
               | OR_CONNECTOR  { isAnd = false;} 
              )
              constr = matchOrConstraint[optionListLinked]
              { 
                constraint = isAnd ? `AndConstraint(constraint,constr) : `OrConstraint(constraint,constr);
              }                                         
            )?
   ARROW 
   {
       optionList = `concOption();
       for(Option op:optionListLinked) {
         optionList = `concOption(optionList*,op);
       }
       optionList = `concOption(optionList*,OriginalText(Name(text.toString())));
   }
(t:LBRACE 
 {
 // update for new target block
 updatePosition(t.getLine(),t.getColumn());
 // actions in target language : call the target lexer and
 // call the target parser
 selector().push("targetlexer");
 TargetLanguage tlCode = targetparser.targetLanguage(blockList);
 // target parser finished : pop the target lexer
 selector().pop();
 blockList.add(tlCode);
 list.add(`ConstraintInstruction(
     constraint,
     RawAction(AbstractBlock(ASTFactory.makeInstructionList(blockList))),
     optionList)
   );
 }
 | rhsTerm = plainTerm[null,null,0]
 {
 // case where the rhs of a rule is an algebraic term
 list.add(`ConstraintInstruction(
     constraint,
     Return(BuildReducedTerm(rhsTerm,rhsType)),
     optionList)
   );

     }
)
)
;

arrowAndAction[List list, OptionList optionList, List<Option> optionListLinked, Token label, TomType rhsType, Constraint constraint] throws TomException
{
  List blockList = new LinkedList();
  TomTerm rhsTerm = null;
} :
   ARROW 
   {
       optionList = `concOption();
       for(Option op:optionListLinked) {
         optionList = `concOption(optionList*,op);
       }
       optionList = `concOption(optionList*,OriginalText(Name(text.toString())));
       if(label != null) {
         optionList = `concOption(Label(Name(label.getText())),optionList*);
       }
   }
   t:LBRACE 
   {
       // update for new target block
       updatePosition(t.getLine(),t.getColumn());
       // actions in target language : call the target lexer and
       // call the target parser
       selector().push("targetlexer");
       TargetLanguage tlCode = targetparser.targetLanguage(blockList);
       // target parser finished : pop the target lexer
       selector().pop();
       blockList.add(tlCode);
       list.add(`ConstraintInstruction(
           constraint,
           RawAction(AbstractBlock(ASTFactory.makeInstructionList(blockList))),
           optionList)
       );
   } 
  ;


constraintInstruction [List list, TomType rhsType] throws TomException
{    
    List<Option> optionListLinked = new LinkedList<Option>();
    Constraint constraint = `TrueConstraint();
    clearText();
}
    :   ( 
            //constraint = matchConstraintComposition[optionListLinked]
            constraint = matchOrConstraint[optionListLinked]
            arrowAndAction[list,null,optionListLinked,null,rhsType,constraint]                                 
        )
    ;

matchOrConstraint [List<Option> optionListLinked] returns [Constraint result] throws TomException
  {     
    result = null;  
    Constraint constr = null;
  } :  
    result = matchAndConstraint[optionListLinked] 
    ( 
        OR_CONNECTOR 
        constr = matchAndConstraint[optionListLinked]
        {
          result = `OrConstraint(result,constr);
        }
    )*
  ;

matchAndConstraint [List<Option> optionListLinked] returns [Constraint result] throws TomException
  {     
    result = null;  
    Constraint constr = null;
  } :  
    result = matchParanthesedConstraint[optionListLinked]
    ( 
        AND_CONNECTOR 
        constr = matchParanthesedConstraint[optionListLinked]
        {
          result = `AndConstraint(result,constr);
        }
    )*
  ;

matchParanthesedConstraint [List<Option> optionListLinked] returns [Constraint result] throws TomException
    {     
      result = null; 
      List matchPatternList = new LinkedList();
    } :  
      (matchPattern[matchPatternList,true]) => result = matchConstraint[optionListLinked]
      | LPAREN result = matchOrConstraint[optionListLinked] RPAREN
    ;    
       

matchConstraint [List<Option> optionListLinked] returns [Constraint result] throws TomException
{
  List matchPatternList = new LinkedList();
  List matchSubjectList = new LinkedList();
  Option option = null;
  result = null;
  int consType = -1;
}
:   
  (
    option = matchPattern[matchPatternList,true] 
    consType = constraintType 
    matchArgument[matchSubjectList]
    {
      optionListLinked.add(option);
      TomTerm left  = (TomTerm)matchPatternList.get(0);
      TomTerm right = (TomTerm)matchSubjectList.get(0);
      switch(consType) {
        case MATCH_CONSTRAINT : {
          return `MatchConstraint(left,right);           
        }
        case /*LESS_CONSTRAINT*/XML_START : {         
          return `NumericConstraint(left,right, NumLessThan());           
        }
        case LESSOREQUAL_CONSTRAINT : {         
          return `NumericConstraint(left,right, NumLessOrEqualThan());           
        }
        case /*GREATER_CONSTRAINT*/XML_CLOSE : {         
          return `NumericConstraint(left,right, NumGreaterThan());           
        }
        case GREATEROREQUAL_CONSTRAINT : {         
          return `NumericConstraint(left,right, NumGreaterOrEqualThan());           
        }
        case DIFFERENT_CONSTRAINT : {         
          return `NumericConstraint(left,right, NumDifferent());           
        }
        case DOUBLEEQ : {         
          return `NumericConstraint(left,right, NumEqual());           
        }      
      } 
      // should never reach this statement because of the parsing error that should occur before
      throw new TomException(TomMessage.invalidConstraintType);
    }
  )  
;

constraintType returns [int result]
{
  result = -1;
}
:   (
      MATCH_CONSTRAINT              { result = MATCH_CONSTRAINT; }
      | /*LESS_CONSTRAINT*/XML_START             { result = /*LESS_CONSTRAINT;*/XML_START; }
      | LESSOREQUAL_CONSTRAINT      { result = LESSOREQUAL_CONSTRAINT; }
      | /*GREATER_CONSTRAINT*/XML_CLOSE          { result = /*GREATER_CONSTRAINT*/XML_CLOSE; }
      | GREATEROREQUAL_CONSTRAINT   { result = GREATEROREQUAL_CONSTRAINT; }
      | DOUBLEEQ                    { result = DOUBLEEQ; }
      | DIFFERENT_CONSTRAINT        { result = DIFFERENT_CONSTRAINT; }
    )      
;

matchPattern [List list,boolean allowImplicit] returns [Option result] throws TomException
{
    result = null;
    TomTerm term = null;
}
    :   (
             term = annotatedTerm[allowImplicit] 
            {
                list.add(term);
                result = `OriginTracking(Name("Pattern"),lastLine,currentFile());
            } 
            ( 
                COMMA {text.append('\n');}  
                term = annotatedTerm[allowImplicit] {list.add(term);}
            )*
        )
    ;

/* 
extendsBqTerm returns [TomTerm bqTerm] throws TomException
{
  bqTerm = null;
}
  :
    EXTENDS { selector().push("bqlexer"); bqTerm = bqparser.beginBackquote(); }
;
*/

// The %strategy construct
strategyConstruct [Option orgTrack] returns [Declaration result] throws TomException
{
    result = null;
    TomTerm extendsTerm = null;
    List visitList = new LinkedList();
    TomVisitList astVisitList = `concTomVisit();
    TomName orgText = null;
    TomTypeList types = `concTomType();
    List options = new LinkedList();
    List slotNameList = new LinkedList();
    List pairNameDeclList = new LinkedList();
    String stringSlotName = null;
    String stringTypeArg = null;

    clearText();
}
    :(
        name:ALL_ID
        {
        Option ot = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
        options.add(ot);
        if(symbolTable.getSymbolFromName(name.getText()) != null) {
          throw new TomException(TomMessage.invalidStrategyName, new Object[]{name.getText()});
        }
        }
        (
            LPAREN (firstSlot1:ALL_ID (colon:COLON)? secondSlot1:ALL_ID 
            {
              if(colon != null) {
                stringSlotName = firstSlot1.getText(); 
                stringTypeArg = secondSlot1.getText(); 
                } else {
                stringSlotName = secondSlot1.getText(); 
                stringTypeArg = firstSlot1.getText(); 
                }
                TomName astName = `Name(stringSlotName);
                slotNameList.add(astName); 

                TomType strategyType = `TomTypeAlone("Strategy");

                // Define get<slot> method.
                Option slotOption = `OriginTracking(Name(stringSlotName),firstSlot1.getLine(),currentFile());
                String varname = "t";
                TomTerm slotVar = `Variable(concOption(slotOption),Name(varname),strategyType,concConstraint());
                String code = ASTFactory.abstractCode("((" + name.getText() + ")$"+varname+").get" + stringSlotName + "()",varname);
                Declaration slotDecl = `GetSlotDecl(Name(name.getText()),Name(stringSlotName),slotVar, Code(code), slotOption);

                pairNameDeclList.add(`PairNameDecl(astName,slotDecl)); 
                types = `concTomType(types*,TomTypeAlone(stringTypeArg));
            }
            (
                COMMA
                firstSlot2:ALL_ID (colon2:COLON)? secondSlot2:ALL_ID
                {
              if(colon != null) {
              stringSlotName = firstSlot2.getText(); 
              stringTypeArg = secondSlot2.getText(); 
              } else {
              stringSlotName = secondSlot2.getText(); 
              stringTypeArg = firstSlot2.getText(); 
              }
              TomName astName = ASTFactory.makeName(stringSlotName);
              if(slotNameList.indexOf(astName) != -1) {
              getLogger().log(new PlatformLogRecord(Level.SEVERE, TomMessage.repeatedSlotName,
                  new Object[]{stringSlotName},
                  currentFile(), getLine()));
              }
              slotNameList.add(astName); 

                TomType strategyType = `TomTypeAlone("Strategy");
                    // Define get<slot> method.
                    Option slotOption = `OriginTracking(Name(stringSlotName),firstSlot2.getLine(),currentFile());
                    String varname = "t";
                    TomTerm slotVar = `Variable(concOption(slotOption),Name(varname),strategyType,concConstraint());
                    String code = ASTFactory.abstractCode("((" + name.getText() + ")$"+varname+").get" + stringSlotName + "()",varname);
                    Declaration slotDecl = `GetSlotDecl(Name(name.getText()),Name(stringSlotName),slotVar, Code(code), slotOption);

                    pairNameDeclList.add(`PairNameDecl(Name(stringSlotName),slotDecl)); 
                    types = `concTomType(types*,TomTypeAlone(stringTypeArg));
                }
            )*
            )? RPAREN
        )
        //extendsTerm = extendsBqTerm
        EXTENDS (BACKQUOTE)? extendsTerm = plainTerm[null,null,0]
        LBRACE
        strategyVisitList[visitList] { astVisitList = ASTFactory.makeTomVisitList(visitList); }
        t:RBRACE
       {
         //initialize arrayList with argument names
				 TomList makeArgs = `concTomTerm();
         int index = 0;
         TomTypeList makeTypes = types;//keep a copy of types
				 String makeTlCode = "new " + name.getText() + "(";
         while(!makeTypes.isEmptyconcTomType()) {
					 String argName = "t"+index;
           if (index>0) {//if many parameters
             makeTlCode = makeTlCode.concat(",");
           }
					 makeTlCode += argName;

           TomTerm arg = `Variable(concOption(),Name(argName),makeTypes.getHeadconcTomType(),concConstraint());
           makeArgs = `concTomTerm(makeArgs*,arg);
           
					 makeTypes = makeTypes.getTailconcTomType();
           index++;
         }
				 makeTlCode += ")";

         TomType strategyType = `TomTypeAlone("Strategy");
				 Option makeOption = `OriginTracking(Name(name.getText()),t.getLine(),currentFile());
				 Declaration makeDecl = `MakeDecl(Name(name.getText()), strategyType,makeArgs, TargetLanguageToInstruction(ITL(makeTlCode)), makeOption);
          options.add(makeDecl);

          // Define the is_fsym method.
          Option fsymOption = `OriginTracking(Name(name.getText()),t.getLine(),currentFile());
          String varname = "t";
          TomTerm fsymVar = `Variable(concOption(fsymOption),Name(varname),strategyType,concConstraint());
          String code = ASTFactory.abstractCode("($"+varname+" instanceof " + name.getText() + ")",varname);
          Declaration fsymDecl = `IsFsymDecl(Name(name.getText()),fsymVar,Code(code),fsymOption);
          options.add(fsymDecl);

          TomSymbol astSymbol = ASTFactory.makeSymbol(name.getText(), strategyType, types, ASTFactory.makePairNameDeclList(pairNameDeclList), options);
          putSymbol(name.getText(),astSymbol);
          // update for new target block...
          updatePosition(t.getLine(),t.getColumn());

          result = `AbstractDecl(concDeclaration(Strategy(Name(name.getText()), BuildReducedTerm(extendsTerm,strategyType),astVisitList,orgTrack),SymbolDecl(Name(name.getText()))));

          // %strat finished: go back in target parser.
            selector().pop();
        }
     )
    ;

strategyVisitList [List list] throws TomException
    :   ( strategyVisit[list] )* ;

strategyVisit [List list] throws TomException
{
  List constraintInstructionList = new LinkedList();
  TomType vType = null;
  TomList subjectList = `concTomTerm();

  clearText();
}
    :   
  (
    visit:ALL_ID { 
      if (!"visit".equals(visit.getText())) {
        throw new TomException(TomMessage.malformedStrategy,
          new Object[]{currentFile(), new Integer(getLine()),
          "strat","visit",visit.getText()});
      }
    }
    type:ALL_ID  LBRACE
    {
      vType = `TomTypeAlone(type.getText());
      subjectList = `concTomTerm(TomTypeToTomTerm(vType));
    }
    (  
      visitInstruction[subjectList,constraintInstructionList,vType] 
    )* 
    RBRACE
  )
  {
    List optionList = new LinkedList();
    optionList.add(`OriginTracking(Name(type.getText()),type.getLine(),currentFile()));
    OptionList options = ASTFactory.makeOptionList(optionList);
    list.add(`VisitTerm(vType,ASTFactory.makeConstraintInstructionList(constraintInstructionList),options));
  }
;

// terms for %match
annotatedTerm [boolean allowImplicit] returns [TomTerm result] throws TomException
{
    result = null;
    TomName labeledName = null;
    TomName annotatedName = null;
    int line = 0;
    boolean anti = false;
}
    :   (
            ( 
                (ALL_ID COLON) => lname:ALL_ID COLON 
                {
                    text.append(lname.getText());
                    text.append(':');
                    labeledName = `Name(lname.getText());
                    line = lname.getLine();
                }
            )? 
            ( 
                (ALL_ID AT) => name:ALL_ID AT 
                {
                    text.append(name.getText());
                    text.append('@');
                    annotatedName = `Name(name.getText());
                    line = name.getLine();
                }
            )? 
          (
            {allowImplicit}?
            result = plainTerm[labeledName,annotatedName,line] 
            | {!allowImplicit}?
              (a:ANTI_SYM {anti = !anti;} )* 
              result = simplePlainTerm[labeledName,annotatedName,line, new LinkedList(), new LinkedList(), new LinkedList(), new LinkedList(), anti]
          )
       )
    ;

// a plainTerm that doesn't allow the notation (...)
simplePlainTerm [TomName astLabeledName, TomName astAnnotedName, int line, List list, List secondOptionList, 
                 List optionList, List constraintList, boolean anti] 
                 returns [TomTerm result] throws TomException
{
    result = null;   
    TomNameList nameList = `concTomName();
    TomName name = null;    
    boolean implicit = false;

    if(astLabeledName != null) {
      constraintList.add(ASTFactory.makeStorePosition(astLabeledName, line, currentFile()));
    }
    if(astAnnotedName != null) {
      constraintList.add(ASTFactory.makeAssignTo(astAnnotedName, line, currentFile()));
    }    
}
    :         
        ( // xml term
          result = xmlTerm[optionList, constraintList]
          {
            if(anti) { result = `AntiTerm(result); }
          }

        | // var* or _*
          {!anti}? // do not allow anti symbols on var* or _* 
          (variableStar[null,null]) => result = variableStar[optionList,constraintList] 

        | // _
          {!anti}? // do not allow anti symbols on _
          result = unamedVariable[optionList,constraintList] 

        | // for a single constant. 
          // ambiguous with the next rule so:
          {LA(2) != LPAREN && LA(2) != LBRACKET && LA(2) != QMARK}? 
          name = headSymbol[optionList] 
          {            
            result = `Variable(ASTFactory.makeOptionList(optionList),name,
              SymbolTable.TYPE_UNKNOWN,ASTFactory.makeConstraintList(constraintList));
            if(anti) { result = `AntiTerm(result); }
          }

        | // for a single constant. 
          // ambiguous with the next rule so:
          {LA(2) != LPAREN && LA(2) != LBRACKET && LA(2) != QMARK}? 
            nameList = headConstantList[optionList] 
          {
        //nameList = `concTomName(nameList*,name);
        optionList.add(`Constant());
        result = `TermAppl(
                ASTFactory.makeOptionList(optionList),
                nameList,
                ASTFactory.makeList(list),
                ASTFactory.makeConstraintList(constraintList));
            if (anti) { result = `AntiTerm(result); }
          }

        | // f(...) or f[...] or !f(...) or !f[...]
          name = headSymbol[optionList] 
      (qm:QMARK)?
          { 
        if(qm!=null) {
              //name = `Name(name.getString() + "__qm__"); 
              name = `Name(name.getString()); 
              optionList.add(`MatchingTheory(concElementaryTheory(TrueAU())));
            }
        nameList = `concTomName(nameList*,name);
          }
          implicit = args[list,secondOptionList]
          {
            if(implicit) {
              result = `RecordAppl(
                  ASTFactory.makeOptionList(optionList),
                  nameList,
                  ASTFactory.makeSlotList(list),
                  ASTFactory.makeConstraintList(constraintList)
                  );
            } else {
              result = `TermAppl(
                  ASTFactory.makeOptionList(optionList),
                  nameList,
                  ASTFactory.makeList(list),
                  ASTFactory.makeConstraintList(constraintList)
                  );
            }
            if(anti) { result = `AntiTerm(result); }
          }
            
        | // (f|g...) 
          // ambiguity with the last rule so use a lookahead
          // if ALTERNATIVE then parse headSymbolList
          {LA(3) == ALTERNATIVE || LA(4) == ALTERNATIVE}? nameList = headSymbolList[optionList] 
          implicit = args[list, secondOptionList] 
          {
            if(implicit) {
              result = `RecordAppl(
                  ASTFactory.makeOptionList(optionList),
                  nameList,
                  ASTFactory.makeSlotList(list),
                  ASTFactory.makeConstraintList(constraintList)
                  );
            } else {
              result = `TermAppl(
                  ASTFactory.makeOptionList(optionList),
                  nameList,
                  ASTFactory.makeList(list),
                  ASTFactory.makeConstraintList(constraintList)
                  );
            }
            if(anti) { result = `AntiTerm(result); }
          }      
        )
    ;


plainTerm [TomName astLabeledName, TomName astAnnotedName, int line] returns [TomTerm result] throws TomException
{
    List optionList = new LinkedList();
    List secondOptionList = new LinkedList();
    List list = new LinkedList();
    List constraintList = new LinkedList(); 
    result = null;
    boolean anti = false;
}
    : 
      (a:ANTI_SYM {anti = !anti;} )* 
      ( {LA(1) != LPAREN  || ( LA(1) == LPAREN && ( LA(3) == ALTERNATIVE || LA(4) == ALTERNATIVE) ) }? 
        result = simplePlainTerm[astLabeledName, astAnnotedName, line, list,secondOptionList,optionList,constraintList,anti]             
        | result = implicitNotationPlainTerm[astLabeledName, astAnnotedName, line, list,secondOptionList,optionList,constraintList,anti] )
      {  return result; } 
    ;

// a plainTerm that allows the (...) notation
implicitNotationPlainTerm[TomName astLabeledName, TomName astAnnotedName, int line, 
                          List list, List secondOptionList, List optionList, List constraintList, boolean anti]
                          returns [TomTerm result] throws TomException
{
    TomNameList nameList = null;
    result = null;

    if(astLabeledName != null) {
      constraintList.add(ASTFactory.makeStorePosition(astLabeledName, line, currentFile()));
    }
    if(astAnnotedName != null) {
      constraintList.add(ASTFactory.makeAssignTo(astAnnotedName, line, currentFile()));
    }

}
:
 args[list,secondOptionList]
{
  nameList = `concTomName(Name(""));
  optionList.addAll(secondOptionList);
  result = `TermAppl(
      ASTFactory.makeOptionList(optionList),
      nameList,
      ASTFactory.makeList(list),
      ASTFactory.makeConstraintList(constraintList)
  );
  if(anti) { result = `AntiTerm(result); }
}
;


xmlTerm [List optionList, List constraintList] returns [TomTerm result] throws TomException
{
  result = null;
  TomTerm arg1, arg2;
  List pairSlotList = new LinkedList();
  List attributeList = new LinkedList();
  List childs = new LinkedList();
  String keyword = "";
  boolean implicit;
  TomNameList nameList, closingNameList;
  OptionList option = null;
  ConstraintList constraint;  
}
    :
        (
            // < NODE attributes [ /> | > childs </NODE> ]
            XML_START {text.append("<");}
            nameList = xmlNameList[optionList, true]
            implicit = xmlAttributeList[attributeList]
            {
                if(implicit) { optionList.add(`ImplicitXMLAttribut()); }
            }
            (   // case: /> 
                XML_CLOSE_SINGLETON
                {
                    text.append("\\>");
                    option =  ASTFactory.makeOptionList(optionList);
                }
            |   // case: > childs  </NODE>
                XML_CLOSE  {text.append(">");}
                implicit = xmlChilds[childs]

                XML_START_ENDING {text.append("</"); }
                closingNameList = xmlNameList[optionList, false]
                t:XML_CLOSE  {text.append(">");}
                {
                    if(!nameList.equals(closingNameList)) {
                        StringBuilder found = new StringBuilder();
                        StringBuilder expected = new StringBuilder();
                        while(!nameList.isEmptyconcTomName()) {
                            expected.append("|"+nameList.getHeadconcTomName().getString());
                            nameList = nameList.getTailconcTomName();
                        }
                        while(!closingNameList.isEmptyconcTomName()) {
                            found.append("|"+closingNameList.getHeadconcTomName().getString());
                            closingNameList = closingNameList.getTailconcTomName();
                        }
                        // TODO find the orgTrack of the match
                        throw new TomException(TomMessage.malformedXMLTerm,
                            new Object[]{currentFile(), new Integer(getLine()), 
                            "match", expected.substring(1), found.substring(1)});
                    }
                    if(implicit) {
                        // Special case when XMLChilds() is reduced to a singleton
                        // when XMLChilds() is reduced to a singleton
                        // Appl(...,Name(""),args)
                        if(ASTFactory.isExplicitTermList(childs)) {
                            childs = ASTFactory.metaEncodeExplicitTermList(symbolTable, (TomTerm)childs.get(0));
                        } else {
                            optionList.add(`ImplicitXMLChild());
                        }
                    }
                    option = ASTFactory.makeOptionList(optionList);    
                }
            ) // end choice
            {
                result = `XMLAppl(
                    option,
                    nameList,
                    ASTFactory.makeList(attributeList),
                    ASTFactory.makeList(childs),
                    ASTFactory.makeConstraintList(constraintList));
            }

        | // #TEXT(...)
            XML_TEXT LPAREN arg1 = annotatedTerm[true] RPAREN
            {
                keyword = Constants.TEXT_NODE;
                pairSlotList.add(`PairSlotAppl(Name(Constants.SLOT_DATA),arg1));

                optionList.add(`OriginTracking(Name(keyword),getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                constraint = ASTFactory.makeConstraintList(constraintList);
                nameList = `concTomName(Name(keyword));
                result = `RecordAppl(option,
                    nameList,
                    ASTFactory.makeSlotList(pairSlotList),
                    constraint);
            }
        | // #COMMENT(...)
            XML_COMMENT LPAREN arg1 = termStringIdentifier[null] RPAREN
            {
                keyword = Constants.COMMENT_NODE;
                pairSlotList.add(`PairSlotAppl(Name(Constants.SLOT_DATA),arg1));

                optionList.add(`OriginTracking(Name(keyword),getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                constraint = ASTFactory.makeConstraintList(constraintList);
                nameList = `concTomName(Name(keyword));
                result = `RecordAppl(option,
                    nameList,
                    ASTFactory.makeSlotList(pairSlotList),
                    constraint);
            }
        | // #PROCESSING-INSTRUCTION(... , ...)
            XML_PROC LPAREN
            arg1 = termStringIdentifier[null] COMMA arg2 = termStringIdentifier[null]
            RPAREN
            {
                keyword = Constants.PROCESSING_INSTRUCTION_NODE;
                pairSlotList.add(`PairSlotAppl(Name(Constants.SLOT_TARGET),arg1));
                pairSlotList.add(`PairSlotAppl(Name(Constants.SLOT_DATA),arg2));

                optionList.add(`OriginTracking(Name(keyword),getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                constraint = ASTFactory.makeConstraintList(constraintList);
                nameList = `concTomName(Name(keyword));
                result = `RecordAppl(option,
                    nameList,
                    ASTFactory.makeSlotList(pairSlotList),
                    constraint);
            }
        )
    ;


xmlAttributeList [List list] returns [boolean result] throws TomException
{
    result = false;
    TomTerm term;
}
    :
        (
            LBRACKET {text.append("[");}
            (
                term = xmlAttribute {list.add(term);}
                (
                    COMMA {text.append("(");}
                    term = xmlAttribute {list.add(term);}
                )*  
            )?
            RBRACKET
            {
                text.append("]");
                result = true;
            }
        |
            LPAREN {text.append("(");}
            (
                term = xmlAttribute {list.add(term);}
                (
                    COMMA {text.append(",");}
                    term = xmlAttribute {list.add(term);}
                )*  
            )?
            RPAREN
            {
                text.append(")");
                result = false;
            }
        |
            (
               {LA(1) != XML_CLOSE}? term = xmlAttribute {list.add(term);}
            )*
            {result = true;}
        )
    ;

xmlAttribute returns [TomTerm result] throws TomException
{
    result = null;
    List slotList = new LinkedList();
    TomTerm term = null;
    TomTerm termName = null;
    String name;
    OptionList option = null;
    ConstraintList constraint;
    List optionList = new LinkedList();
    List constraintList = new LinkedList();
    List optionListAnno2 = new LinkedList();
    TomNameList nameList;
    boolean varStar = false;
    boolean anti = false;
}
    :
        (
            // _* | X*
            {LA(2) == STAR}?
            result = variableStar[optionList,constraintList] {varStar = true;}
        |   // name = [anno2@](_|String|Identifier)
            {LA(2) == EQUAL}?  id:ALL_ID EQUAL {text.append(id.getText()+"=");}
            (
                {LA(2) == AT}? anno2:ALL_ID AT
                {
                    text.append(anno2.getText()+"@");
                    optionListAnno2.add(`Name(anno2.getText()));
                }
            )?
            (a:ANTI_SYM {anti = !anti;} )*
            term = unamedVariableOrTermStringIdentifier[optionListAnno2]
            {
                name = ASTFactory.encodeXMLString(symbolTable,id.getText());
                nameList = `concTomName(Name(name));
                termName = `TermAppl(concOption(),nameList,concTomTerm(),concConstraint());                
            }
        | // [anno1@]_ = [anno2@](_|String|Identifier)
            (
                anno1:ALL_ID AT
                {
                    text.append(anno1.getText()+"@");
                    optionList.add(`Name(anno1.getText()));
                }
            )?
            termName = unamedVariable[optionList,constraintList]
            e:EQUAL {text.append("=");}
            (
                {LA(2) == AT}? anno3:ALL_ID AT
                {
                    text.append(anno3.getText()+"@");
                    optionListAnno2.add(`Name(anno3.getText()));
                }
            )?
            (b:ANTI_SYM {anti = !anti;} )*		
            term = unamedVariableOrTermStringIdentifier[optionListAnno2]
        )
        {
            if (!varStar) {
            	
            	if (anti){
            		term = `AntiTerm(term);
            	}
            	
                slotList.add(`PairSlotAppl(Name(Constants.SLOT_NAME),termName));
                // we add the specif value : _
                slotList.add(`PairSlotAppl(Name(Constants.SLOT_SPECIFIED),UnamedVariable(concOption(),SymbolTable.TYPE_UNKNOWN,concConstraint())));
                // no longer necessary ot metaEncode Strings in attributes
                slotList.add(`PairSlotAppl(Name(Constants.SLOT_VALUE),term));
                optionList.add(`OriginTracking(Name(Constants.ATTRIBUTE_NODE),getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);            
                constraint = ASTFactory.makeConstraintList(constraintList);
                
                nameList = `concTomName(Name(Constants.ATTRIBUTE_NODE));
                result = `RecordAppl(option,
                    nameList,
                    ASTFactory.makeSlotList(slotList),
                    constraint);
            }   
        }
    ;

// This corresponds to the implicit notation
xmlTermList [List list] returns [boolean result] throws TomException
{
    result = false;
    TomTerm term;
}
    :
        (
            term = annotatedTerm[true] {list.add(term);}
        )*
        {result = true;}
    ;

xmlNameList [List optionList, boolean needOrgTrack] returns [TomNameList result] throws TomException
{
    result = `concTomName();
    StringBuilder XMLName = new StringBuilder("");
    int decLine = 0;
    boolean anti = false;
}
    :    	
        (
        	(a:ANTI_SYM {anti = !anti;} )*	
            name:ALL_ID
            {
                text.append(name.getText());
                XMLName.append(name.getText());
                decLine = name.getLine();                
                if (anti) { 
                	result =  `concTomName(AntiName(Name(name.getText())));
                }else{
	               	result = `concTomName(Name(name.getText()));
                }
            }
        |   name2:UNDERSCORE
            {
                text.append(name2.getText());
                XMLName.append(name2.getText());
                decLine = name2.getLine();
                result = `concTomName(Name(name2.getText()));
            }
        |   LPAREN (b:ANTI_SYM {anti = !anti;} )* name3:ALL_ID
            {
                text.append(name3.getText());
                XMLName.append(name3.getText());
                decLine = name3.getLine();
                if (anti) { 
                	result =  `concTomName(AntiName(Name(name3.getText())));
                }else{
                	result = `concTomName(Name(name3.getText()));
                }

            }
            (
                ALTERNATIVE (c:ANTI_SYM {anti = !anti;} )* name4:ALL_ID
                {
                    text.append("|"+name4.getText());
                    XMLName.append("|"+name4.getText());
                    if (anti) { 
                    	result = `concTomName(result*,AntiName(Name(name4.getText())));
                    }else{
                    	result = `concTomName(result*,Name(name4.getText()));
                    }
                }
            )*
            RPAREN
        )
        {
            if(needOrgTrack) {
                optionList.add(`OriginTracking(Name(XMLName.toString()), decLine, currentFile()));
            }
        }
    ;

termStringIdentifier [List options] returns [TomTerm result] throws TomException
{
  result = null;
  List optionList = (options==null)?new LinkedList():options;
  OptionList option = null;
  TomNameList nameList = null;
}
    :
        (
            nameID:ALL_ID
            {
                text.append(nameID.getText());
                optionList.add(`OriginTracking(Name(nameID.getText()),nameID.getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                result = `Variable(option,Name(nameID.getText()),SymbolTable.TYPE_UNKNOWN,concConstraint());
            }
        |
            nameString:STRING
            {
                text.append(nameString.getText());
                optionList.add(`OriginTracking(Name(nameString.getText()),nameString.getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                ASTFactory.makeStringSymbol(symbolTable,nameString.getText(),optionList);
                nameList = `concTomName(Name(nameString.getText()));
                result = `TermAppl(option,nameList,concTomTerm(),concConstraint());
            }
        )
    ;


unamedVariableOrTermStringIdentifier [List options] returns [TomTerm result] throws TomException
{
  result = null;
  List optionList = (options==null)?new LinkedList():options;
  OptionList option = null;
  TomNameList nameList = null;
}
    :
        (
            result = unamedVariable[optionList, new LinkedList()]
        |
            nameID:ALL_ID
            {
                text.append(nameID.getText());
                optionList.add(`OriginTracking(Name(nameID.getText()),nameID.getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                result = `Variable(option,Name(nameID.getText()),SymbolTable.TYPE_UNKNOWN,concConstraint());
            }
        |
            nameString:STRING 
            {
                text.append(nameString.getText());
                optionList.add(`OriginTracking(Name(nameString.getText()),nameString.getLine(),currentFile()));
                option = ASTFactory.makeOptionList(optionList);
                ASTFactory.makeStringSymbol(symbolTable,nameString.getText(),optionList);
                nameList = `concTomName(Name(nameString.getText()));
                result = `TermAppl(option,nameList,concTomTerm(),concConstraint());
            }
        )
    ;

// return true for implicit mode
implicitTermList [List list] returns [boolean result] throws TomException
{
    result = false;
    TomTerm term;
}
    :
        (
            LBRACKET
            { text.append("["); }
            (
                term = annotatedTerm[true] { list.add(term); }
                (
                    COMMA { text.append(","); }
                    term = annotatedTerm[true] { list.add(term); }
                )*
            )?
            RBRACKET
            {
                text.append("]");
                result=true;
            }
        )
    ;


xmlChilds [List list] returns [boolean result] throws TomException
{
  result = false;
  List childs = new LinkedList();
  Iterator it;
}
    :
        (
            //(implicitTermList[null]) => 
            {LA(1) == LBRACKET}? result = implicitTermList[childs]
        |   result = xmlTermList[childs]
        )
        {
            it = childs.iterator();
            while(it.hasNext()) {
                list.add(ASTFactory.metaEncodeXMLAppl(symbolTable,(TomTerm)it.next()));
            }
        }
    ;


args [List list, List optionList] returns [boolean result] throws TomException
{
    result = false;
}
    :   (
            // (term , term , ...)
            t1:LPAREN {text.append('(');} 
            ( termList[list] )? 
            t2:RPAREN 
            {
                // setting line number for origin tracking
                // in %rule construct
                setLastLine(t2.getLine());

                text.append(t2.getText());
            
                result = false;
                optionList.add(`OriginTracking(Name(""),t1.getLine(),currentFile()));
            }
            
        |   // [term = term , term = term , ...]
            t3:LBRACKET {text.append('[');} 
            ( pairList[list] )? 
            t4:RBRACKET 
            {
                // setting line number for origin tracking
                // in %rule construct
                setLastLine(t4.getLine());
                text.append(t4.getText());
                
                result = true;
                optionList.add(`OriginTracking(Name(""),t3.getLine(),currentFile()));
            }
        )
    ;

termList [List list] throws TomException
{
    TomTerm term = null;
}
    :   (
            term = annotatedTerm[true] {list.add(term);}
            ( COMMA {text.append(',');} term = annotatedTerm[true] {list.add(term);})*
        )
    ;

pairList [List list] throws TomException
{
    TomTerm term = null;
}
    :   (
            name:ALL_ID EQUAL 
            {
                text.append(name.getText());
                text.append('=');
            } 
            term = annotatedTerm[true] 
            {list.add(`PairSlotAppl(Name(name.getText()),term));}
            ( COMMA {text.append(',');} 
                name2:ALL_ID EQUAL 
                {
                    text.append(name2.getText());
                    text.append('=');
                } 
                term = annotatedTerm[true] 
                {list.add(`PairSlotAppl(Name(name2.getText()),term));}
            )*
        )
;
   
// _* or var*       
variableStar [List optionList, List constraintList] returns [TomTerm result]
{ 
    result = null; 
    String name = null;
    int line = 0;
    OptionList options = null;
    ConstraintList constraints = null;
}
    :   (
            ( 
                name1:ALL_ID 
                {
                    name = name1.getText();
                    line = name1.getLine();
                }
            |   name2:UNDERSCORE 
                {
                    name = name2.getText();
                    line = name2.getLine();
                }
            ) 
            t:STAR 
            {
                text.append(name);
                text.append(t.getText());

                // setting line number for origin tracking
                // in %rule construct
                setLastLine(t.getLine());
                
                optionList.add(`OriginTracking(Name(name),line,currentFile()));
                options = ASTFactory.makeOptionList(optionList);
                constraints = ASTFactory.makeConstraintList(constraintList);
                if(name1 == null) {
                    result = `UnamedVariableStar(
                        options,
                        SymbolTable.TYPE_UNKNOWN,
                        constraints
                    );
                } else {
                    result = `VariableStar(
                        options,
                        Name(name),
                        SymbolTable.TYPE_UNKNOWN,
                        constraints
                    );
                }
            }
        )
    ;

// _
unamedVariable [List optionList, List constraintList] returns [TomTerm result]
{ 
    result = null;
    OptionList options = null;
    ConstraintList constraints = null;
}
    :   (
            t:UNDERSCORE 
            {
                text.append(t.getText());
                setLastLine(t.getLine());

                optionList.add(`OriginTracking(Name(t.getText()),t.getLine(),currentFile()));
                options = ASTFactory.makeOptionList(optionList);
                constraints = ASTFactory.makeConstraintList(constraintList);
                result = `UnamedVariable(options,SymbolTable.TYPE_UNKNOWN,constraints);
            } 
        )
    ;

// ( id | id | ...)
headSymbolList [List optionList] returns [TomNameList result]
{ 
    result = `concTomName();
    TomName name = null;
}
    :
        (
            LPAREN {text.append('(');}
            name = headSymbolOrConstant[optionList] 
            {
            	result = `concTomName(result*,name);            	
            }

            ALTERNATIVE {text.append('|');}
            name = headSymbolOrConstant[optionList] 
            {
            	result = `concTomName(result*,name);                
            }

            ( 
                ALTERNATIVE {text.append('|');}
                name = headSymbolOrConstant[optionList] 
                {
                	result = `concTomName(result*,name);                    
                }
            )* 
            t:RPAREN 
            {
                text.append(t.getText());
                setLastLine(t.getLine());
            }
        )
    ;

headSymbolOrConstant [List optionList] returns [TomName result]
{
  result = null;
} : ( result = headSymbol[optionList]
    | result = headConstant[optionList]
    );

headSymbol [List optionList] returns [TomName result]
{ 
    result = null; 
}
: 
  (i:ALL_ID
  {
		String name = i.getText();                
		int line = i.getLine();
		text.append(name);
		setLastLine(line);
		result = `Name(name);
		optionList.add(`OriginTracking(result,line, currentFile()));
	}
	)
;

headConstantList [List optionList] returns [TomNameList result]
{
    result = `concTomName();
    TomName name = null;
} :
  name=headConstant[optionList] { result = `concTomName(result*,name); }
  (
    ALTERNATIVE { text.append('|'); }
    name=headConstant[optionList] { result = `concTomName(result*,name); }
  )*
;

headConstant [List optionList] returns [TomName result]
{ 
    result = null;
    Token t;
} : 
        t=constant // add to symbol table
{  
	String name = t.getText();        
	int line = t.getLine();
	text.append(name);
	setLastLine(line);
	result = `Name(name);
	optionList.add(`OriginTracking(result,line, currentFile()));

	switch(t.getType()) {
		case NUM_INT:
			ASTFactory.makeIntegerSymbol(symbolTable,name,optionList);
			break;
		case NUM_LONG:
			ASTFactory.makeLongSymbol(symbolTable,name,optionList);
			break;
		case CHARACTER:
			ASTFactory.makeCharSymbol(symbolTable,name,optionList);
			break;
		case NUM_DOUBLE:
			ASTFactory.makeDoubleSymbol(symbolTable,name,optionList);
			break;
		case STRING:
			ASTFactory.makeStringSymbol(symbolTable,name,optionList);
			break;
		default:
	}
}
;

// Operator Declaration
operator returns [Declaration result] throws TomException
{
    result=null;
    Option ot = null;
    TomTypeList types = `concTomType();
    List options = new LinkedList();
    List slotNameList = new LinkedList();
    List pairNameDeclList = new LinkedList();
    TomName astName = null;
    String stringSlotName = null;
    Declaration attribute;
}
    :
      type:ALL_ID name:ALL_ID 
        {
            ot = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
            options.add(ot);
        }
        (
            LPAREN (slotName:ALL_ID COLON typeArg:ALL_ID 
            {
                stringSlotName = slotName.getText(); 
                astName = `Name(stringSlotName);
                slotNameList.add(astName); 
                pairNameDeclList.add(`PairNameDecl(astName,EmptyDeclaration())); 
                types = `concTomType(types*,TomTypeAlone(typeArg.getText()));
            }
            (
                COMMA
                slotName2:ALL_ID COLON typeArg2:ALL_ID
                {
                    stringSlotName = slotName2.getText(); 
                    astName = ASTFactory.makeName(stringSlotName);
                    if(slotNameList.indexOf(astName) != -1) {
                      getLogger().log(new PlatformLogRecord(Level.SEVERE, TomMessage.repeatedSlotName,
                        new Object[]{stringSlotName},
                        currentFile(), getLine()));
                    }
                    slotNameList.add(astName); 
                    pairNameDeclList.add(`PairNameDecl(Name(stringSlotName),EmptyDeclaration())); 
                    types = `concTomType(types*,TomTypeAlone(typeArg2.getText()));
                }
            )*
            )? RPAREN
        )
        (
        LBRACE
        {
            astName = `Name(name.getText());
        }
        (
            attribute = keywordMake[name.getText(),`TomTypeAlone(type.getText()),types]
            { options.add(attribute); }

        |   attribute = keywordGetSlot[astName,type.getText()]
            {
              TomName sName = attribute.getSlotName();
              /*
               * ensure that sName appears in slotNameList, only once
               * ensure that sName has not already been generated
               */
              //System.out.println("slotNameList = " + slotNameList);
              //System.out.println("sName      = " + sName);

              TomMessage msg = null;
              int index = slotNameList.indexOf(sName);
              if(index == -1) {
                msg = TomMessage.errorIncompatibleSlotDecl;
              } else {
                PairNameDecl pair = (PairNameDecl) pairNameDeclList.get(index);
                %match(PairNameDecl pair) {
                  PairNameDecl[SlotDecl=decl] -> {
                    if(`decl != `EmptyDeclaration()) {
                      msg = TomMessage.errorTwoSameSlotDecl;
                    }
                  }
                }
              }
              if(msg != null) {
                getLogger().log(new PlatformLogRecord(Level.SEVERE, msg,
                      new Object[]{currentFile(), new Integer(attribute.getOrgTrack().getLine()),
                      "%op "+type.getText(), new Integer(ot.getLine()), sName.getString()} ,
                    currentFile(), getLine()));
              } else {
                pairNameDeclList.set(index,`PairNameDecl(sName,attribute));
              }
            }   
        |   attribute = keywordIsFsym[astName,type.getText()]
            { options.add(attribute); }
        )*
        t:RBRACE
	)
        {

          //System.out.println("pairNameDeclList = " + pairNameDeclList);

          TomSymbol astSymbol = ASTFactory.makeSymbol(name.getText(), `TomTypeAlone(type.getText()), types, ASTFactory.makePairNameDeclList(pairNameDeclList), options);
          putSymbol(name.getText(),astSymbol);
          result = `SymbolDecl(astName);
          updatePosition(t.getLine(),t.getColumn());
          selector().pop(); 
        }
    ;

operatorList returns [Declaration result] throws TomException
{
    result = null;
    TomTypeList types = `concTomType();
    List options = new LinkedList();
    Declaration attribute = null;
    String opName = "";
}
    :
        type:ALL_ID name:ALL_ID (qm:QMARK)?
        {
	  opName = name.getText(); // + ((qm!=null)?"__qm__":"");
	  Option ot = `OriginTracking(Name(opName),name.getLine(),currentFile());
	  options.add(ot);
        }
        LPAREN typeArg:ALL_ID STAR RPAREN
        {
            types = `concTomType(types*,TomTypeAlone(typeArg.getText()));
        }
        LBRACE
        (
            attribute = keywordMakeEmptyList[opName]
            { options.add(attribute); }
        |   attribute = keywordMakeAddList[opName,type.getText(),typeArg.getText()]
            { options.add(attribute); }
        |   attribute = keywordIsFsym[`Name(opName), type.getText()]
            { options.add(attribute); }
        |   attribute = keywordGetHead[`Name(opName), type.getText()]
            { options.add(attribute); }
        |   attribute = keywordGetTail[`Name(opName), type.getText()]
            { options.add(attribute); }
        |   attribute = keywordIsEmpty[`Name(opName), type.getText()]
            { options.add(attribute); }
        )*
        t:RBRACE
        { 
            PairNameDeclList pairNameDeclList = `concPairNameDecl(PairNameDecl(EmptyName(), EmptyDeclaration()));
            TomSymbol astSymbol = ASTFactory.makeSymbol(opName, `TomTypeAlone(type.getText()), types, pairNameDeclList, options);
            putSymbol(opName,astSymbol);
            result = `ListSymbolDecl(Name(opName));
            updatePosition(t.getLine(),t.getColumn());
            selector().pop(); 
        }
    ;

operatorArray returns [Declaration result] throws TomException
{
    result = null;
    TomTypeList types = `concTomType();
    List options = new LinkedList();
    Declaration attribute = null;
    String opName = "";
}
    :
        type:ALL_ID name:ALL_ID (qm:QMARK)?
        {
	  opName = name.getText(); // + ((qm!=null)?"__qm__":"");
	  Option ot = `OriginTracking(Name(opName),name.getLine(),currentFile());
	  options.add(ot);
        }
        LPAREN typeArg:ALL_ID STAR RPAREN
        {
            types = `concTomType(types*,TomTypeAlone(typeArg.getText()));
        }
        LBRACE
        (
            attribute = keywordMakeEmptyArray[opName,type.getText()]
            { options.add(attribute); }
        |   attribute = keywordMakeAddArray[opName,type.getText(),typeArg.getText()]
            { options.add(attribute); }
        |   attribute = keywordIsFsym[`Name(opName),type.getText()]
            { options.add(attribute); }
        |   attribute = keywordGetElement[`Name(opName), type.getText()]
            { options.add(attribute); }
        |   attribute = keywordGetSize[`Name(opName), type.getText()]
            { options.add(attribute); }
        )*
        t:RBRACE
        { 
            PairNameDeclList pairNameDeclList = `concPairNameDecl(PairNameDecl(EmptyName(), EmptyDeclaration()));
            TomSymbol astSymbol = ASTFactory.makeSymbol(opName, `TomTypeAlone(type.getText()), types, pairNameDeclList, options);
            putSymbol(opName,astSymbol);

            result = `ArraySymbolDecl(Name(opName));

            updatePosition(t.getLine(),t.getColumn());

            selector().pop(); 
        }
    ;

typeTerm returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
    Declaration attribute = null;
    TargetLanguage implement = null;
    DeclarationList declarationList = `concDeclaration();
    String s;
}
    :   (
            type:ALL_ID
            { 
                ot = `OriginTracking(Name(type.getText()), type.getLine(),currentFile());
            }
            LBRACE

            implement = keywordImplement
            (  attribute = keywordEquals[type.getText()]
                { declarationList = `concDeclaration(attribute,declarationList*); }
            |   attribute = keywordIsSort[type.getText()]
                { declarationList = `concDeclaration(attribute,declarationList*); }
            |   attribute = keywordGetImplementation[type.getText()]
                { declarationList = `concDeclaration(attribute,declarationList*); }
            )*
            t:RBRACE
        )
{
  TomType astType = `Type(ASTTomType(type.getText()),TLType(implement));
          putType(type.getText(), astType); 
          result = `TypeTermDecl(Name(type.getText()),declarationList,ot);
          updatePosition(t.getLine(),t.getColumn());
          selector().pop();
        }
    ;

keywordImplement returns [TargetLanguage tlCode] throws TomException
{
    tlCode = null;
}
    :
        (
            IMPLEMENT
            {
                selector().push("targetlexer");
                tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();
            }
        )
    ;

 
keywordEquals[String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:EQUALS 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name1:ALL_ID COMMA name2:ALL_ID RPAREN
            {
                Option info1 = `OriginTracking(Name(name1.getText()),name1.getLine(),currentFile());
                Option info2 = `OriginTracking(Name(name2.getText()),name2.getLine(),currentFile());
                OptionList option1 = `concOption(info1);
                OptionList option2 = `concOption(info2);
                
                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  
                String code = ASTFactory.abstractCode(tlCode.getCode(),name1.getText(),name2.getText());
                result = `EqualTermDecl(
                    Variable(option1,Name(name1.getText()),TomTypeAlone(type),concConstraint()),
                    Variable(option2,Name(name2.getText()),TomTypeAlone(type),concConstraint()),
                    Code(code), ot);
            }
        )
    ;

keywordIsSort[String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:IS_SORT 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name:ALL_ID RPAREN
            {
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);
                
                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  
                
                String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
                result = `IsSortDecl(
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                    Code(code), ot);
            }
        )
    ;

keywordGetHead[TomName opname, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:GET_HEAD 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name:ALL_ID RPAREN
            {
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);

                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  

                result = `GetHeadDecl(opname,
                    symbolTable.getUniversalType(),
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                    Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),
                    ot);
            }
        )
    ;

keywordGetTail[TomName opname, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:GET_TAIL 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name:ALL_ID RPAREN
            {
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);

                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  

                result = `GetTailDecl(opname,
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                    Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),
                    ot);
            }
        )
    ;

keywordIsEmpty[TomName opname, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:IS_EMPTY 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name:ALL_ID RPAREN
            {
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);

                selector().push("targetlexer");
                TargetLanguage  tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop(); 

                result = `IsEmptyDecl(opname,
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                    Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),
                    ot); 
            }
        )
    ;

keywordGetElement[TomName opname, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:GET_ELEMENT 
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name1:ALL_ID COMMA name2:ALL_ID RPAREN
            {
                Option info1 = `OriginTracking(Name(name1.getText()),name1.getLine(),currentFile());
                Option info2 = `OriginTracking(Name(name2.getText()),name2.getLine(),currentFile());
                OptionList option1 = `concOption(info1);
                OptionList option2 = `concOption(info2);
                
                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  
                
                result = `GetElementDecl(opname,
                    Variable(option1,Name(name1.getText()),TomTypeAlone(type),concConstraint()),
                    Variable(option2,Name(name2.getText()),TomTypeAlone("int"),concConstraint()),
                    Code(ASTFactory.abstractCode(tlCode.getCode(),name1.getText(),name2.getText())), ot);
            }
        )
    ;

keywordGetSize[TomName opname, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:GET_SIZE
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN name:ALL_ID RPAREN
            {
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);

                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop();  

                result = `GetSizeDecl(opname,
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                    Code(ASTFactory.abstractCode(tlCode.getCode(),name.getText())),ot);
            }
        )
    ;

keywordIsFsym[TomName astName, String typeString] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:IS_FSYM
        { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
        LPAREN name:ALL_ID RPAREN
        {
            Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
            OptionList option = `concOption(info);

            selector().push("targetlexer");
            TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
            selector().pop();

            String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
            result = `IsFsymDecl(astName,
                Variable(option,Name(name.getText()),TomTypeAlone(typeString),concConstraint()),
                Code(code),ot);
        }
    ;

keywordGetImplementation [String typeString] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:GET_IMPLEMENTATION
        { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
        LPAREN name:ALL_ID RPAREN
        {
            Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
            OptionList option = `concOption(info);

            selector().push("targetlexer");
            TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
            selector().pop();

            result = `GetImplementationDecl(Variable(option,Name(name.getText()),TomTypeAlone(typeString),concConstraint()),
                Return(TargetLanguageToTomTerm(tlCode)),ot);
        }
    ;


keywordGetSlot [TomName astName, String type] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        (
            t:GET_SLOT
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
            LPAREN slotName:ALL_ID COMMA name:ALL_ID RPAREN
            {                
                Option info = `OriginTracking(Name(name.getText()),name.getLine(),currentFile());
                OptionList option = `concOption(info);
                
                selector().push("targetlexer");
                TargetLanguage tlCode = targetparser.goalLanguage(new LinkedList());
                selector().pop(); 
                String code = ASTFactory.abstractCode(tlCode.getCode(),name.getText());
                result = `GetSlotDecl(astName,
                    Name(slotName.getText()),
                    Variable(option,Name(name.getText()),TomTypeAlone(type),concConstraint()),
                     Code(code), ot);
            }
        )
    ;

keywordMake[String opname, TomType returnType, TomTypeList types] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
    TomList args = `concTomTerm();
    ArrayList<String> varnameList = new ArrayList<String>();
    int index = 0;
    TomType type;
    int nbTypes = types.length();
}
    :
        (
            t:MAKE
            { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
                (LPAREN 
                ( 
                    nameArg:ALL_ID
                    {
                        if( !(nbTypes > 0) ) {
                            type = `EmptyType();
                        } else {
                            type = TomBase.elementAt(types,index++);
                        }
                        Option info1 = `OriginTracking(Name(nameArg.getText()),nameArg.getLine(),currentFile());  
                        OptionList option1 = `concOption(info1);
                        
                        args = `concTomTerm(args*,Variable(
                                option1,
                                Name(nameArg.getText()),
                                type,concConstraint()
                            ));
                        varnameList.add(nameArg.getText());
                    }
                    ( 
                        COMMA nameArg2:ALL_ID
                        {
                            if( index >= nbTypes ) {
                                type = `EmptyType();
                            } else {
                              type = TomBase.elementAt(types,index++);
                            }
                            Option info2 = `OriginTracking(Name(nameArg2.getText()),nameArg2.getLine(),currentFile());
                            OptionList option2 = `concOption(info2);
                            
                            args = `concTomTerm(args*,Variable(
                                    option2,
                                    Name(nameArg2.getText()),
                                    type,concConstraint()
                                ));
                            varnameList.add(nameArg2.getText());
                        }
                    )*
                )? 
                RPAREN )?
            l:LBRACE
            {
                updatePosition(t.getLine(),t.getColumn());
                selector().push("targetlexer");
                List blockList = new LinkedList();
                TargetLanguage tlCode = targetparser.targetLanguage(blockList);
                selector().pop();
                blockList.add(tlCode);
                if(blockList.size()==1) {
                  String[] vars = new String[varnameList.size()];
                  String code = ASTFactory.abstractCode(tlCode.getCode(),varnameList.toArray(vars));
                  result = `MakeDecl(Name(opname),returnType,args,ExpressionToInstruction(Code(code)),ot);
                } else {
                  result = `MakeDecl(Name(opname),returnType,args,AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
                }
            }
        )
    ;

keywordMakeEmptyList[String name] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:MAKE_EMPTY (LPAREN RPAREN)?
        { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
        
        LBRACE
        {   
            selector().push("targetlexer");
            List blockList = new LinkedList();
            TargetLanguage tlCode = targetparser.targetLanguage(blockList);
            selector().pop();
            blockList.add(tlCode);
            if(blockList.size()==1) {
              result = `MakeEmptyList(Name(name),ExpressionToInstruction(Code(tlCode.getCode())),ot);
            } else {
              result = `MakeEmptyList(Name(name),AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
            }
        }
    ;

keywordMakeAddList[String name, String listType, String elementType] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:MAKE_INSERT
        {ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile());}

        LPAREN elementName:ALL_ID COMMA listName:ALL_ID RPAREN
        LBRACE
        {
            Option listInfo = `OriginTracking(Name(listName.getText()),listName.getLine(),currentFile());  
            Option elementInfo = `OriginTracking(Name(elementName.getText()),elementName.getLine(),currentFile());
            OptionList listOption = `concOption(listInfo);
            OptionList elementOption = `concOption(elementInfo);

            selector().push("targetlexer");
            List blockList = new LinkedList();
            TargetLanguage tlCode = targetparser.targetLanguage(blockList);
            selector().pop();
            blockList.add(tlCode);
            if(blockList.size()==1) {
              String code = ASTFactory.abstractCode(tlCode.getCode(),elementName.getText(),listName.getText());
              result = `MakeAddList(Name(name),
                  Variable(elementOption,Name(elementName.getText()),TomTypeAlone(elementType),concConstraint()),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()),
                  ExpressionToInstruction(Code(code)),ot);
            } else {
              result = `MakeAddList(Name(name),
                  Variable(elementOption,Name(elementName.getText()),TomTypeAlone(elementType),concConstraint()),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()),
                  AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
            }
        }
    ;

keywordMakeEmptyArray[String name, String listType] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:MAKE_EMPTY { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
        LPAREN listName:ALL_ID RPAREN
        LBRACE
        {
            Option listInfo = `OriginTracking(Name(listName.getText()),listName.getLine(),currentFile());  
            OptionList listOption = `concOption(listInfo);

            selector().push("targetlexer");
            List blockList = new LinkedList();
            TargetLanguage tlCode = targetparser.targetLanguage(blockList);
            selector().pop();
            blockList.add(tlCode);
            if(blockList.size()==1) {
              String code = ASTFactory.abstractCode(tlCode.getCode(),listName.getText());
              result = `MakeEmptyArray(Name(name),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()), ExpressionToInstruction(Code(code)),ot);
            } else {
              result = `MakeEmptyArray(Name(name),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()),
                  AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
            }
        }
    ;   

keywordMakeAddArray[String name, String listType, String elementType] returns [Declaration result] throws TomException
{
    result = null;
    Option ot = null;
}
    :
        t:MAKE_APPEND { ot = `OriginTracking(Name(t.getText()),t.getLine(),currentFile()); }
          LPAREN elementName:ALL_ID COMMA listName:ALL_ID RPAREN
          LBRACE
        {
            selector().push("targetlexer");
            List blockList = new LinkedList();
            TargetLanguage tlCode = targetparser.targetLanguage(blockList);
            selector().pop();
            blockList.add(tlCode);

            Option listInfo = `OriginTracking(Name(listName.getText()),listName.getLine(),currentFile());  
            Option elementInfo = `OriginTracking(Name(elementName.getText()),elementName.getLine(),currentFile());
            OptionList listOption = `concOption(listInfo);
            OptionList elementOption = `concOption(elementInfo);
            if(blockList.size()==1) {
              String code = ASTFactory.abstractCode(tlCode.getCode(),elementName.getText(),listName.getText());
              result = `MakeAddArray(Name(name),
                  Variable(elementOption,Name(elementName.getText()),TomTypeAlone(elementType),concConstraint()),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()),
                  ExpressionToInstruction(Code(code)),ot);
            } else {
              result = `MakeAddArray(Name(name),
                  Variable(elementOption,Name(elementName.getText()),TomTypeAlone(elementType),concConstraint()),
                  Variable(listOption,Name(listName.getText()),TomTypeAlone(listType),concConstraint()),
                  AbstractBlock(ASTFactory.makeInstructionList(blockList)),ot);
            }
        }
    ;

class TomLexer extends Lexer;
options {
  k=3; // default lookahead
    charVocabulary = '\u0000'..'\uffff'; // each character can be read
    testLiterals = false;
}

tokens { 
    WHERE="where";
    IF="if";
    EXTENDS="extends";
    MAKE_EMPTY = "make_empty";
    MAKE_INSERT = "make_insert";
    MAKE_APPEND = "make_append";
    MAKE = "make";
    GET_SLOT = "get_slot";
    IS_FSYM = "is_fsym";
    GET_IMPLEMENTATION = "get_implementation";
    EQUALS = "equals";
    IS_SORT = "is_sort";
    GET_HEAD = "get_head";
    GET_TAIL = "get_tail";
    IS_EMPTY = "is_empty";
    IMPLEMENT = "implement";
    GET_ELEMENT = "get_element";
    GET_SIZE = "get_size";
    WHEN = "when";
}

LBRACE      :   '{' ;
RBRACE      :   '}' ;
LPAREN      :   '(' ;
RPAREN      :   ')' ;
LBRACKET    :   '[' ;
RBRACKET    :   ']' ;
COMMA       :   ',' ;
ARROW       :   "->";
DOULEARROW  :   "=>";
ALTERNATIVE :   '|' ;
AFFECT      :   ":=";
DOUBLEEQ    :   "==";
COLON       :   ':' ;
EQUAL       :   '=' ;
AT          :   '@' ;
STAR        :   '*' ;
QMARK       :   '?' ;
UNDERSCORE  :   {!Character.isJavaIdentifierPart(LA(2))}? '_' ; 
BACKQUOTE   :   "`" ;

//XML Tokens

XML_START   :   '<';
XML_CLOSE   :   '>' ;
DOUBLE_QUOTE:   '\"';
XML_TEXT    :   "#TEXT";
XML_COMMENT :   "#COMMENT";
XML_PROC    :   "#PROCESSING-INSTRUCTION";
XML_START_ENDING    : "</" ;
XML_CLOSE_SINGLETON : "/>" ;

// tokens to skip : white spaces
WS  : ( ' '
    | '\t'
    | '\f'
    // handle newlines
    | ( "\r\n"  // Evil DOS
      | '\r'    // Macintosh
      | '\n'    // Unix (the right way)
      )
      { newline(); }
    )
        { $setType(Token.SKIP); }
  ;


// tokens to skip : Single Line Comments
SLCOMMENT
  : "//"
    (~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
    {
            $setType(Token.SKIP); 
            newline();
        }
  ;

// tokens to skip : Multi Lines Comments
ML_COMMENT
  : "/*"
    ( 
      options {
        generateAmbigWarnings=false;
      }
    :
      { LA(2)!='/' }? '*'
    | '\r' '\n'   {newline();if(LA(1)==EOF_CHAR) throw new TokenStreamException("premature EOF");}
    | '\r'      {newline();if(LA(1)==EOF_CHAR) throw new TokenStreamException("premature EOF");}
    | '\n'      {newline();if(LA(1)==EOF_CHAR) throw new TokenStreamException("premature EOF");}
    | ~('*'|'\n'|'\r'){if(LA(1)==EOF_CHAR) throw new TokenStreamException("premature EOF");}
    )*
    "*/"
    {$setType(Token.SKIP);}
  ;


CHARACTER
  : '\'' ( ESC | ~('\''|'\n'|'\r'|'\\') )+ '\''
  ;

STRING
  : '"' (ESC|~('"'|'\\'|'\n'|'\r'))* '"'
  ;

ANTI_SYM  : '!';
MATCH_CONSTRAINT  : "<<";
LESS_CONSTRAINT  : "<:";
LESSOREQUAL_CONSTRAINT  : "<=";  
//GREATER_CONSTRAINT  : ":>";
GREATEROREQUAL_CONSTRAINT  : ">=";  
DIFFERENT_CONSTRAINT  : "!=";
//EQUAL_CONSTRAINT = DOUBLEEQ;
  
AND_CONNECTOR  : "&&";
OR_CONNECTOR  : "||";

CONSTRAINT_GROUP_START : '{' ;  
CONSTRAINT_GROUP_END : '}' ;

protected
ESC
  : '\\'
    ( 'n'
    | 'r'
    | 't'
    | 'b'
    | 'f'
    | '"'
    | '\''
    | '\\'
    | ('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
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
HEX_DIGIT
  : ('0'..'9'|'A'..'F'|'a'..'f')
  ;

protected LETTER    :   ('a'..'z' | 'A'..'Z')   ;
protected DIGIT     :   ('0'..'9')  ;

ALL_ID
options{testLiterals = true;}
    :
        (
            (ID_MINUS) => ID_MINUS
        |   ID
        )
    ;

protected ID
options{testLiterals = true;}
    :
        ('_')? LETTER
        ( 
            options{greedy = true;}:
            ( LETTER | DIGIT | '_' )
        )* 
    ;   

protected ID_MINUS
    :
        ID MINUS ('a'..'z' | 'A'..'Z') 
        ( 
            MINUS ('a'..'z' | 'A'..'Z') 
        |   ID
        )*
    ;

NUM_INT
  {boolean isDecimal=false; Token t=null;}
    :   (MINUS)?
    (
    DOT
    ( ('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
      {
        if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
          _ttype = NUM_FLOAT;
        } else {
          _ttype = NUM_DOUBLE; // assume double
        }
      }
    )?

  | ( '0' {isDecimal = true;} // special case for just '0'
      ( ('x'|'X')
        (                     // hex
          // the 'e'|'E' and float suffix stuff look
          // like hex digits, hence the (...)+ doesn't
          // know when to stop: ambig.  ANTLR resolves
          // it correctly by matching immediately.  It
          // is therefor ok to hush warning.
          options {
            warnWhenFollowAmbig=false;
          }
        : HEX_DIGIT
        )+

      | //float or double with leading zero
        (('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

      | ('0'..'7')+                 // octal
      )?
    | ('1'..'9') ('0'..'9')*  {isDecimal=true;}   // non-zero decimal
    )
    ( ('l'|'L') { _ttype = NUM_LONG; }

    // only check to see if it's a float if looks like decimal so far
    | {isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
            |   f4:FLOAT_SUFFIX {t=f4;}
            )
            {
      if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                _ttype = NUM_FLOAT;
      }
            else {
              _ttype = NUM_DOUBLE; // assume double
      }
      }
        )?
    )
  ;
protected MINUS         :   '-' ;
protected PLUS          :   '+' ;
protected QUOTE         :   '\''    ;
protected EXPONENT      :   ('e'|'E') ( PLUS | MINUS )? ('0'..'9')+  ;
protected DOT           :   '.' ;
protected FLOAT_SUFFIX  : 'f'|'F'|'d'|'D' ;
