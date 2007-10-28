/* Generated by TOM (version 2.6alpha): Do not edit this file *//*
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
 * Julien Guyon
 *
 **/

package tom.engine.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import tom.engine.TomBase;
import tom.engine.TomMessage;
import tom.platform.OptionParser;

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

import tom.platform.adt.platformoption.types.PlatformOptionList;
import aterm.ATerm;
import tom.library.sl.*;

/**
 * The TomTypeChecker plugin.
 */
public class TomTypeChecker extends TomChecker {

  /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */private static boolean tom_is_sort_char(char t) { return  true ;}  /* Generated by TOM (version 2.6alpha): Do not edit this file */private static boolean tom_is_sort_int(int t) { return  true ;} private static  tom.engine.adt.tomtype.types.TomForwardType  tom_make_EmptyForward() { return  tom.engine.adt.tomtype.types.tomforwardtype.EmptyForward.make() ; }private static  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tom_empty_list_concConstraintInstruction() { return  tom.engine.adt.tominstruction.types.constraintinstructionlist.EmptyconcConstraintInstruction.make() ; }   private static   tom.engine.adt.tominstruction.types.ConstraintInstructionList  tom_append_list_concConstraintInstruction( tom.engine.adt.tominstruction.types.ConstraintInstructionList l1,  tom.engine.adt.tominstruction.types.ConstraintInstructionList  l2) {     if( l1.isEmptyconcConstraintInstruction() ) {       return l2;     } else if( l2.isEmptyconcConstraintInstruction() ) {       return l1;     } else if(  l1.getTailconcConstraintInstruction() .isEmptyconcConstraintInstruction() ) {       return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( l1.getHeadconcConstraintInstruction() ,l2) ;     } else {       return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( l1.getHeadconcConstraintInstruction() ,tom_append_list_concConstraintInstruction( l1.getTailconcConstraintInstruction() ,l2)) ;     }   }   private static   tom.engine.adt.tominstruction.types.ConstraintInstructionList  tom_get_slice_concConstraintInstruction( tom.engine.adt.tominstruction.types.ConstraintInstructionList  begin,  tom.engine.adt.tominstruction.types.ConstraintInstructionList  end, tom.engine.adt.tominstruction.types.ConstraintInstructionList  tail) {     if( begin.equals(end) ) {       return tail;     } else {       return  tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction.make( begin.getHeadconcConstraintInstruction() ,( tom.engine.adt.tominstruction.types.ConstraintInstructionList )tom_get_slice_concConstraintInstruction( begin.getTailconcConstraintInstruction() ,end,tail)) ;     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file *//* Generated by TOM (version 2.6alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_Identity() { return ( new tom.library.sl.Identity() ); }private static  tom.library.sl.Strategy  tom_empty_list_Sequence() { return ( null ); }   private static   tom.library.sl.Strategy  tom_append_list_Sequence( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 == null )) {       return l2;     } else if(( l2 == null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Sequence) )) {       if(( ((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):tom_empty_list_Sequence()) == null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):tom_empty_list_Sequence()),l2)==null)?((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1):new tom.library.sl.Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.FIRST) ):l1),tom_append_list_Sequence(((( (l1 instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Sequence.THEN) ):tom_empty_list_Sequence()),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Sequence(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Sequence( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else {       return ( (( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):tom_empty_list_Sequence()),end,tail)==null)?((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin):new tom.library.sl.Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Sequence(((( (begin instanceof tom.library.sl.Sequence) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Sequence.THEN) ):tom_empty_list_Sequence()),end,tail)) );     }   }   private static  tom.library.sl.Strategy  tom_empty_list_Choice() { return ( null ); }   private static   tom.library.sl.Strategy  tom_append_list_Choice( tom.library.sl.Strategy  l1,  tom.library.sl.Strategy  l2) {     if(( l1 ==null )) {       return l2;     } else if(( l2 ==null )) {       return l1;     } else if(( (l1 instanceof tom.library.sl.Choice) )) {       if(( ((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):tom_empty_list_Choice()) ==null )) {         return ( (l2==null)?((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1):new tom.library.sl.Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1),l2) );       } else {         return ( (tom_append_list_Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):tom_empty_list_Choice()),l2)==null)?((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1):new tom.library.sl.Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.FIRST) ):l1),tom_append_list_Choice(((( (l1 instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)l1.getChildAt(tom.library.sl.Choice.THEN) ):tom_empty_list_Choice()),l2)) );       }     } else {       return ( (l2==null)?l1:new tom.library.sl.Choice(l1,l2) );     }   }   private static   tom.library.sl.Strategy  tom_get_slice_Choice( tom.library.sl.Strategy  begin,  tom.library.sl.Strategy  end, tom.library.sl.Strategy  tail) {     if( (begin.equals(end)) ) {       return tail;     } else {       return ( (( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):tom_empty_list_Choice()),end,tail)==null)?((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin):new tom.library.sl.Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.FIRST) ):begin),( tom.library.sl.Strategy )tom_get_slice_Choice(((( (begin instanceof tom.library.sl.Choice) ))?( (tom.library.sl.Strategy)begin.getChildAt(tom.library.sl.Choice.THEN) ):tom_empty_list_Choice()),end,tail)) );     }   }    /* Generated by TOM (version 2.6alpha): Do not edit this file */ /* Generated by TOM (version 2.6alpha): Do not edit this file */private static  tom.library.sl.Strategy  tom_make_Try( tom.library.sl.Strategy  v) { return ( ( (( (tom_empty_list_Choice()==null)?tom_make_Identity():new tom.library.sl.Choice(tom_make_Identity(),tom_empty_list_Choice()) )==null)?v:new tom.library.sl.Choice(v,( (tom_empty_list_Choice()==null)?tom_make_Identity():new tom.library.sl.Choice(tom_make_Identity(),tom_empty_list_Choice()) )) ) ); }   


  /** the declared options string */
  public static final String DECLARED_OPTIONS = "<options><boolean name='noTypeCheck' altName='' description='Do not perform type checking' value='false'/></options>";

  /**
   * inherited from OptionOwner interface (plugin) 
   */
  public PlatformOptionList getDeclaredOptionList() {
    return OptionParser.xmlToOptionList(TomTypeChecker.DECLARED_OPTIONS);
  }

  /** Constructor */
  public TomTypeChecker() {
    super("TomTypeChecker");
  }

  public void run() {
    if(isActivated()) {
      strictType = !getOptionBooleanValue("lazyType");
      long startChrono = System.currentTimeMillis();
      try {
        // clean up internals
        reinit();
        // perform analyse
        try {
          ( new tom.library.sl.Mu(( new tom.library.sl.MuVar("x") ),tom_make_Try(( (( (tom_empty_list_Sequence()==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),tom_empty_list_Sequence()) )==null)?tom_make_checkTypeInference(this):new tom.library.sl.Sequence(tom_make_checkTypeInference(this),( (tom_empty_list_Sequence()==null)?( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ):new tom.library.sl.Sequence(( new tom.library.sl.All(( new tom.library.sl.MuVar("x") )) ),tom_empty_list_Sequence()) )) ))) ).visitLight((TomTerm)getWorkingTerm());
        } catch(tom.library.sl.VisitFailure e) {
          System.out.println("strategy failed");
        }
        // verbose
        getLogger().log( Level.INFO, TomMessage.tomTypeCheckingPhase.getMessage(),
            new Integer((int)(System.currentTimeMillis()-startChrono)) );
      } catch (Exception e) {
        getLogger().log( Level.SEVERE, TomMessage.exceptionMessage.getMessage(),
            new Object[]{getClass().getName(), getStreamManager().getInputFileName(),e.getMessage()} );
        e.printStackTrace();
      }
    } else {
      // type checker desactivated    
      getLogger().log(Level.INFO, TomMessage.typeCheckerInactivated.getMessage());
    }
  }

  private boolean isActivated() {
    return !getOptionBooleanValue("noTypeCheck");
  }

  /**
   * Main type checking entry point:
   * We check all Match
   */
  private static class checkTypeInference extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy  {private  TomTypeChecker  ttc; public checkTypeInference( TomTypeChecker  ttc) { super(tom_make_Identity());this.ttc=ttc;}public  TomTypeChecker  getttc() { return ttc;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.engine.adt.tominstruction.types.Instruction  visit_Instruction( tom.engine.adt.tominstruction.types.Instruction  tom__arg) throws tom.library.sl.VisitFailure {if ( tom__arg instanceof tom.engine.adt.tominstruction.types.Instruction ) {{  tom.engine.adt.tominstruction.types.Instruction  tomMatch121NameNumberfreshSubject_1=(( tom.engine.adt.tominstruction.types.Instruction )tom__arg);if ( (tomMatch121NameNumberfreshSubject_1 instanceof tom.engine.adt.tominstruction.types.instruction.Match) ) {{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch121NameNumber_freshVar_0= tomMatch121NameNumberfreshSubject_1.getConstraintInstructionList() ;{  tom.engine.adt.tomoption.types.OptionList  tomMatch121NameNumber_freshVar_1= tomMatch121NameNumberfreshSubject_1.getOption() ;if ( true ) {






  
        ttc.currentTomStructureOrgTrack = TomBase.findOriginTracking(tomMatch121NameNumber_freshVar_1);
        ttc.verifyMatchVariable(tomMatch121NameNumber_freshVar_0);
        throw new tom.library.sl.VisitFailure();
      }}}}}}return super.visit_Instruction(tom__arg); }public  tom.engine.adt.tomdeclaration.types.Declaration  visit_Declaration( tom.engine.adt.tomdeclaration.types.Declaration  tom__arg) throws tom.library.sl.VisitFailure {if ( tom__arg instanceof tom.engine.adt.tomdeclaration.types.Declaration ) {{  tom.engine.adt.tomdeclaration.types.Declaration  tomMatch122NameNumberfreshSubject_1=(( tom.engine.adt.tomdeclaration.types.Declaration )tom__arg);if ( (tomMatch122NameNumberfreshSubject_1 instanceof tom.engine.adt.tomdeclaration.types.declaration.Strategy) ) {{  tom.engine.adt.tomname.types.TomName  tomMatch122NameNumber_freshVar_0= tomMatch122NameNumberfreshSubject_1.getSName() ;{  tom.engine.adt.tomterm.types.TomTerm  tomMatch122NameNumber_freshVar_1= tomMatch122NameNumberfreshSubject_1.getExtendsTerm() ;{  tom.engine.adt.tomsignature.types.TomVisitList  tomMatch122NameNumber_freshVar_2= tomMatch122NameNumberfreshSubject_1.getVisitList() ;{  tom.engine.adt.tomoption.types.Option  tomMatch122NameNumber_freshVar_3= tomMatch122NameNumberfreshSubject_1.getOrgTrack() ;if ( true ) {



        ttc.currentTomStructureOrgTrack = tomMatch122NameNumber_freshVar_3;
        ttc.verifyStrategyVariable(tomMatch122NameNumber_freshVar_2);
        throw new tom.library.sl.VisitFailure();
      }}}}}}}}return super.visit_Declaration(tom__arg); }}private static  tom.library.sl.Strategy  tom_make_checkTypeInference( TomTypeChecker  t0) { return new checkTypeInference(t0); }

 //checkTypeInference

  /* 
   * Collect unknown (not in symbol table) appls without ()
   */
  private static class collectUnknownAppls extends  tom.engine.adt.tomsignature.TomSignatureBasicStrategy  {private  TomTypeChecker  ttc; public collectUnknownAppls( TomTypeChecker  ttc) { super(tom_make_Identity());this.ttc=ttc;}public  TomTypeChecker  getttc() { return ttc;}public tom.library.sl.Visitable[] getChildren() {tom.library.sl.Visitable[] stratChilds = new tom.library.sl.Visitable[getChildCount()];stratChilds[0] = super.getChildAt(0);return stratChilds;}public tom.library.sl.Visitable setChildren(tom.library.sl.Visitable[] children) {super.setChildAt(0, children[0]);return this;}public int getChildCount() { return 1; }public tom.library.sl.Visitable getChildAt(int index) {switch (index) {case 0: return super.getChildAt(0);default: throw new IndexOutOfBoundsException();}}public tom.library.sl.Visitable setChildAt(int index, tom.library.sl.Visitable child) {switch (index) {case 0: return super.setChildAt(0, child);default: throw new IndexOutOfBoundsException();}}public  tom.engine.adt.tomterm.types.TomTerm  visit_TomTerm( tom.engine.adt.tomterm.types.TomTerm  tom__arg) throws tom.library.sl.VisitFailure {if ( tom__arg instanceof tom.engine.adt.tomterm.types.TomTerm ) {{  tom.engine.adt.tomterm.types.TomTerm  tomMatch123NameNumberfreshSubject_1=(( tom.engine.adt.tomterm.types.TomTerm )tom__arg);if ( (tomMatch123NameNumberfreshSubject_1 instanceof tom.engine.adt.tomterm.types.tomterm.TermAppl) ) {{  tom.engine.adt.tomterm.types.TomTerm  tom_app=tomMatch123NameNumberfreshSubject_1;if ( true ) {


        if(ttc.symbolTable().getSymbolFromName(ttc.getName(tom_app))==null) {
          ttc.messageError(findOriginTrackingFileName(tom_app.getOption()),
              findOriginTrackingLine(tom_app.getOption()),
              TomMessage.unknownVariableInWhen,
              new Object[]{ttc.getName(tom_app)});
        }
        // else, it's actually app()
        // else, it's a unknown (ie : java) function
      }}}}}return super.visit_TomTerm(tom__arg); }}



  private void verifyMatchVariable(ConstraintInstructionList constraintInstructionList) throws VisitFailure{
    if ( constraintInstructionList instanceof tom.engine.adt.tominstruction.types.ConstraintInstructionList ) {{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumberfreshSubject_1=(( tom.engine.adt.tominstruction.types.ConstraintInstructionList )constraintInstructionList);if ( ((tomMatch124NameNumberfreshSubject_1 instanceof tom.engine.adt.tominstruction.types.constraintinstructionlist.ConsconcConstraintInstruction) || (tomMatch124NameNumberfreshSubject_1 instanceof tom.engine.adt.tominstruction.types.constraintinstructionlist.EmptyconcConstraintInstruction)) ) {{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumber_freshVar_0=tomMatch124NameNumberfreshSubject_1;{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumber_begin_2=tomMatch124NameNumber_freshVar_0;{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumber_end_3=tomMatch124NameNumber_freshVar_0;do {{{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumber_freshVar_1=tomMatch124NameNumber_end_3;if (!( tomMatch124NameNumber_freshVar_1.isEmptyconcConstraintInstruction() )) {if ( ( tomMatch124NameNumber_freshVar_1.getHeadconcConstraintInstruction()  instanceof tom.engine.adt.tominstruction.types.constraintinstruction.ConstraintInstruction) ) {{  tom.engine.adt.tomconstraint.types.Constraint  tomMatch124NameNumber_freshVar_6=  tomMatch124NameNumber_freshVar_1.getHeadconcConstraintInstruction() .getConstraint() ;{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch124NameNumber_freshVar_4= tomMatch124NameNumber_freshVar_1.getTailconcConstraintInstruction() ;if ( true ) {

        // collect variables
        ArrayList variableList = new ArrayList();
        TomBase.collectVariable(variableList, tomMatch124NameNumber_freshVar_6);
        verifyVariableTypeListCoherence(variableList);        
      }}}}}}if ( tomMatch124NameNumber_end_3.isEmptyconcConstraintInstruction() ) {tomMatch124NameNumber_end_3=tomMatch124NameNumber_begin_2;} else {tomMatch124NameNumber_end_3= tomMatch124NameNumber_end_3.getTailconcConstraintInstruction() ;}}} while(!( tomMatch124NameNumber_end_3.equals(tomMatch124NameNumber_begin_2) ));}}}}}}
    
  }

  private void verifyStrategyVariable(TomVisitList list) throws VisitFailure{
    TomForwardType visitorFwd = null;
    TomForwardType currentVisitorFwd = null;
    while(!list.isEmptyconcTomVisit()) {
      TomVisit visit = list.getHeadconcTomVisit();
      if ( visit instanceof tom.engine.adt.tomsignature.types.TomVisit ) {{  tom.engine.adt.tomsignature.types.TomVisit  tomMatch125NameNumberfreshSubject_1=(( tom.engine.adt.tomsignature.types.TomVisit )visit);if ( (tomMatch125NameNumberfreshSubject_1 instanceof tom.engine.adt.tomsignature.types.tomvisit.VisitTerm) ) {{  tom.engine.adt.tomtype.types.TomType  tomMatch125NameNumber_freshVar_0= tomMatch125NameNumberfreshSubject_1.getVNode() ;{  tom.engine.adt.tominstruction.types.ConstraintInstructionList  tomMatch125NameNumber_freshVar_1= tomMatch125NameNumberfreshSubject_1.getAstConstraintInstructionList() ;{  tom.engine.adt.tomoption.types.OptionList  tomMatch125NameNumber_freshVar_2= tomMatch125NameNumberfreshSubject_1.getOption() ;{  tom.engine.adt.tomtype.types.TomType  tom_visitType=tomMatch125NameNumber_freshVar_0;{  tom.engine.adt.tomoption.types.OptionList  tom_options=tomMatch125NameNumber_freshVar_2;if ( true ) {

          String fileName =findOriginTrackingFileName(tom_options);
          if ( tom_visitType instanceof tom.engine.adt.tomtype.types.TomType ) {{  tom.engine.adt.tomtype.types.TomType  tomMatch126NameNumberfreshSubject_1=(( tom.engine.adt.tomtype.types.TomType )tom_visitType);if ( (tomMatch126NameNumberfreshSubject_1 instanceof tom.engine.adt.tomtype.types.tomtype.TomTypeAlone) ) {{  String  tomMatch126NameNumber_freshVar_0= tomMatch126NameNumberfreshSubject_1.getString() ;if ( true ) {

              messageError(fileName,
                  findOriginTrackingLine(tom_options),
                  TomMessage.unknownVisitedType,
                  new Object[]{(tomMatch126NameNumber_freshVar_0)});
            }}}if ( (tomMatch126NameNumberfreshSubject_1 instanceof tom.engine.adt.tomtype.types.tomtype.Type) ) {{  tom.engine.adt.tomtype.types.TomType  tomMatch126NameNumber_freshVar_1= tomMatch126NameNumberfreshSubject_1.getTomType() ;{  tom.engine.adt.tomtype.types.TomType  tomMatch126NameNumber_freshVar_2= tomMatch126NameNumberfreshSubject_1.getTlType() ;if ( (tomMatch126NameNumber_freshVar_1 instanceof tom.engine.adt.tomtype.types.tomtype.ASTTomType) ) {{  String  tomMatch126NameNumber_freshVar_3= tomMatch126NameNumber_freshVar_1.getString() ;{  String  tom_ASTVisitType=tomMatch126NameNumber_freshVar_3;if ( (tomMatch126NameNumber_freshVar_2 instanceof tom.engine.adt.tomtype.types.tomtype.TLType) ) {{  tom.engine.adt.tomsignature.types.TargetLanguage  tomMatch126NameNumber_freshVar_4= tomMatch126NameNumber_freshVar_2.getTl() ;{  tom.engine.adt.tomsignature.types.TargetLanguage  tom_TLVisitType=tomMatch126NameNumber_freshVar_4;if ( true ) {

              //check that all visitType have same visitorFwd

              currentVisitorFwd = symbolTable().getForwardType(tom_ASTVisitType);

              //noVisitorFwd defined for visitType
              if (currentVisitorFwd == null || currentVisitorFwd == tom_make_EmptyForward()){ 
                messageError(fileName,tom_TLVisitType.getStart().getLine(),
                    TomMessage.noVisitorForward,
                    new Object[]{(tom_ASTVisitType)});
              } else if (visitorFwd == null) {
                //first visit 
                visitorFwd = currentVisitorFwd;
              } else {
                //check if current visitor equals to previous visitor
                if (currentVisitorFwd != visitorFwd){ 
                  messageError(fileName,tom_TLVisitType.getStart().getLine(),
                      TomMessage.differentVisitorForward,
                      new Object[]{visitorFwd.getString(),currentVisitorFwd.getString()});
                }
              }
              verifyMatchVariable(tomMatch125NameNumber_freshVar_1);
            }}}}}}}}}}}}

        }}}}}}}}}

      // next visit
      list = list.getTailconcTomVisit();
    }
  }

  private void verifyVariableTypeListCoherence(ArrayList list) {
    // compute multiplicities
    //System.out.println("list = " + list);
    HashMap map = new HashMap();
    Iterator it = list.iterator();
    while(it.hasNext()) {
      TomTerm variable = (TomTerm)it.next();
      TomName name = variable.getAstName();
      if(map.containsKey(name)) {
        TomTerm var = (TomTerm)map.get(name);
        //System.out.println("variable = " + variable);
        //System.out.println("var = " + var);
        TomType type1 = var.getAstType();
        TomType type2 = variable.getAstType();
        if(!(type1==type2)) {
          messageError(findOriginTrackingFileName(variable.getOption()),
              findOriginTrackingLine(variable.getOption()),
              TomMessage.incoherentVariable,
              new Object[]{name.getString(), TomBase.getTomType(type1), TomBase.getTomType(type2)});
        }
      } else {
        map.put(name, variable);
      }
    }
  }

} // class TomTypeChecker
