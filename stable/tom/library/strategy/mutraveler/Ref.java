/* Generated by TOM (version 2.5alpha): Do not edit this file */package tom.library.strategy.mutraveler;

import tom.library.strategy.mutraveler.AbstractMuStrategy;
import tom.library.strategy.mutraveler.MuStrategy;
import tom.library.strategy.mutraveler.MuReference;
import tom.library.strategy.mutraveler.Position;
import jjtraveler.Visitable;
import jjtraveler.VisitFailure;

/**
 * Basic visitor combinator with one visitor argument and one visitable argument
 */

public class Ref extends AbstractMuStrategy {
  public final static int ARG = 0;
  private Visitable subject;
  // is it an absolute or a relative reference
  private boolean relative = false;
  // strict means that it fails when it is not a Ref
  private boolean strict = false;

  private Ref(Visitable v, MuStrategy s, boolean relative,  boolean strict) {
    initSubterm(s);
    this.subject = v;
    this.relative=relative;
    this.strict = strict;
  }

  public static Ref make(Visitable v, MuStrategy s) {
    return new Ref(v,s,false,false);
  }
  
  public static Ref makeRelative(Visitable v, MuStrategy s) {
    return new Ref(v,s,true,false);
  }

  public static Ref makeStrict(Visitable v, MuStrategy s) {
    return new Ref(v,s,false,true);
  }
  
  public static Ref makeRelativeStrict(Visitable v, MuStrategy s) {
    return new Ref(v,s,true,true);
  }

  public boolean isRelative() { return relative; }
  public boolean isStrict() { return strict; }
  public Visitable getSubject() { return subject; }

  public Visitable visit(Visitable any) throws VisitFailure {
    if (any instanceof MuReference){
      return visitReference((MuReference)any,(MuStrategy)visitors[ARG]);
    } else { 
      if(strict) {
	// does nothing when it is not a Ref
	return any;
      } else {
	return visitors[ARG].visit(any);
      }
    }
  }

  private MuReference visitReference(MuReference ref, MuStrategy strat) throws VisitFailure{
    if(relative) {
      RelativePosition relativePos = new RelativePosition(ref.toArray());
      Position pos = relativePos.getAbsolutePosition(getPosition()); 
      visitPosition(pos,strat);
      return ref;
    } else {
      Position pos = new Position(ref.toArray());
      visitPosition(pos,strat);
      return ref;
    }
  }

  private void visitPosition(Position pos, MuStrategy strat) throws VisitFailure{
    int[] oldpos = getPosition().toArray();
    getPosition().init(pos);
    try{
      subject = pos.getOmega(strat).visit(subject);
    } catch(VisitFailure e) {
      getPosition().init(oldpos);
      throw new VisitFailure();
    }
    getPosition().init(oldpos);
  }


}


