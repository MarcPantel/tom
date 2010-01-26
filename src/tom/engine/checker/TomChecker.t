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
 * Julien Guyon
 *
 **/

package tom.engine.checker;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import tom.engine.TomBase;
import tom.engine.TomMessage;
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

import tom.engine.tools.TomGenericPlugin;
import tom.platform.PlatformLogRecord;


abstract public class TomChecker extends TomGenericPlugin {
  
    // ------------------------------------------------------------
  %include { ../adt/tomsignature/TomSignature.tom }
    // ------------------------------------------------------------
  
    // Different kind of structures
  protected final static int TERM_APPL               = 0;
  protected final static int UNAMED_APPL             = 1;
  protected final static int APPL_DISJUNCTION        = 2;
  protected final static int RECORD_APPL             = 3;
  protected final static int RECORD_APPL_DISJUNCTION = 4;
  protected final static int XML_APPL                = 5;
  protected final static int VARIABLE_STAR           = 6;
  protected final static int UNAMED_VARIABLE_STAR    = 7;
  protected final static int UNAMED_VARIABLE         = 8;
  protected final static int VARIABLE                = 9;
  
  protected boolean strictType = false;
  protected Option currentTomStructureOrgTrack;
    
  public TomChecker(String name) {
    super(name);
  }

  protected void reinit() {
    currentTomStructureOrgTrack = null;
  }

  public int getClass(TomTerm term) {
    %match(term) {
      TermAppl[NameList=(Name(""))] -> { return UNAMED_APPL;}
      TermAppl[NameList=(Name(_))] -> { return TERM_APPL;}
      TermAppl[NameList=(Name(_), _*)] -> { return APPL_DISJUNCTION;}
      RecordAppl[NameList=(Name(_))] -> { return RECORD_APPL;}
      RecordAppl[NameList=(Name(_), _*)] -> { return RECORD_APPL_DISJUNCTION;}
      XMLAppl[] -> { return XML_APPL;}
      UnamedVariable[] -> { return UNAMED_VARIABLE;}
      VariableStar[] -> { return VARIABLE_STAR;}
      Variable[] -> { return VARIABLE;}
      UnamedVariableStar[] -> { return UNAMED_VARIABLE_STAR;}
    }
    throw new TomRuntimeException("Invalid Term");
  }
  
  public String getName(TomTerm term) {
    String dijunctionName = "";
    %match(term) {
      TermAppl[NameList=(Name(name))] -> { return `name;}
      TermAppl[NameList=nameList] -> {
        String head;
        dijunctionName = `nameList.getHeadconcTomName().getString();
        while(!`nameList.isEmptyconcTomName()) {
          head = `nameList.getHeadconcTomName().getString();
          dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;
          `nameList = `nameList.getTailconcTomName();
        }
        return dijunctionName;
      }
      RecordAppl[NameList=(Name(name))] -> { return `name;}
      RecordAppl[NameList=nameList] -> {
        String head;
        dijunctionName = `nameList.getHeadconcTomName().getString();
        while(!`nameList.isEmptyconcTomName()) {
          head = `nameList.getHeadconcTomName().getString();
          dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;
          `nameList = `nameList.getTailconcTomName();
        }
        return dijunctionName;
      }
      XMLAppl[NameList=(Name(name), _*)] ->{ return `name;}
      XMLAppl[NameList=nameList] -> {
        String head;
        dijunctionName = `nameList.getHeadconcTomName().getString();
        while(!`nameList.isEmptyconcTomName()) {
          head = `nameList.getHeadconcTomName().getString();
          dijunctionName = ( dijunctionName.compareTo(head) > 0)?dijunctionName:head;
          `nameList = `nameList.getTailconcTomName();
        }
        return dijunctionName;
      }
      Variable[AstName=Name(name)] -> { return `name;}
      VariableStar[AstName=Name(name)] -> { return `name+"*";}
      UnamedVariable[] -> { return "_";}
      UnamedVariableStar[] -> { return "_*";}
      AntiTerm(t) -> { return getName(`t); }
    }
    throw new TomRuntimeException("Invalid Term:" + term);
  }
  
  /**
   * Shared Functions 
   */
  protected String extractType(TomSymbol symbol) {
    TomType type = TomBase.getSymbolCodomain(symbol);
    return TomBase.getTomType(type);
  }
  
  protected String findOriginTrackingFileName(OptionList optionList) {
    %match(optionList) {
      concOption(_*,OriginTracking[FileName=fileName],_*) -> { return `fileName; }
    }
    return "unknown filename";
  }

  protected int findOriginTrackingLine(OptionList optionList) {
    %match(optionList) {
      concOption(_*,OriginTracking[Line=line],_*) -> { return `line; }
    }
    return -1;
  }

  protected void ensureOriginTrackingLine(int line) {
    if(line < 0) {
      getLogger().log(Level.SEVERE,
                      TomMessage.findOTL.getMessage(),
                      getStreamManager().getInputFileName());
      //System.out.println("findOriginTrackingLine: not found ");
    }
  }

  /**
   * Message Functions
   */
  protected void messageError(String fileName, int errorLine, TomMessage msg, Object[] msgArgs) {
    TomMessage.error(getLogger(),fileName,errorLine,msg,msgArgs);
    //getLogger().log(new PlatformLogRecord(Level.SEVERE, msg, msgArgs,fileName, errorLine));
  }
  
  protected void messageWarning(String fileName, int errorLine, TomMessage msg, Object[] msgArgs) {
    TomMessage.warning(getLogger(),fileName,errorLine,msg,msgArgs);
    //getLogger().log(new PlatformLogRecord(Level.WARNING,msg,msgArgs,fileName, errorLine));
  }
  
  public static void messageError(String className,String fileName, int errorLine, TomMessage msg, Object[] msgArgs) {
    TomMessage.error(Logger.getLogger(className),fileName,errorLine,msg,msgArgs);
    //Logger.getLogger(className).log(new PlatformLogRecord(Level.SEVERE, msg, msgArgs,fileName, errorLine));
  }
  
  public static void messageWarning(String className,String fileName, int errorLine, TomMessage msg, Object[] msgArgs) {
    TomMessage.warning(Logger.getLogger(className),fileName,errorLine,msg,msgArgs);
    //Logger.getLogger(className).log(new PlatformLogRecord(Level.WARNING, msg, msgArgs,fileName, errorLine));
  }
  
}
