package jtom.adt;

import aterm.*;
import java.io.InputStream;
import java.io.IOException;

abstract public class TomSymbolImpl extends TomSignatureConstructor
{
  protected TomSymbolImpl(TomSignatureFactory factory) {
     super(factory);
  }
  protected void init(int hashCode, aterm.ATermList annos, aterm.AFun fun,	aterm.ATerm[] args) {
    super.init(hashCode, annos, fun, args);
  }
  protected void initHashCode(aterm.ATermList annos, aterm.AFun fun, aterm.ATerm[] i_args) {
  	super.initHashCode(annos, fun, i_args);
  }
  public boolean isEqual(TomSymbol peer)
  {
    return term.isEqual(peer.toTerm());
  }
  public boolean isSortTomSymbol()  {
    return true;
  }

  public boolean isSymbol()
  {
    return false;
  }

  public boolean hasAstName()
  {
    return false;
  }

  public boolean hasTypesToType()
  {
    return false;
  }

  public boolean hasSlotList()
  {
    return false;
  }

  public boolean hasOption()
  {
    return false;
  }

  public boolean hasTlCode()
  {
    return false;
  }

  public TomName getAstName()
  {
     throw new RuntimeException("This TomSymbol has no AstName");
  }

  public TomSymbol setAstName(TomName _astName)
  {
     throw new RuntimeException("This TomSymbol has no AstName");
  }

  public TomType getTypesToType()
  {
     throw new RuntimeException("This TomSymbol has no TypesToType");
  }

  public TomSymbol setTypesToType(TomType _typesToType)
  {
     throw new RuntimeException("This TomSymbol has no TypesToType");
  }

  public SlotList getSlotList()
  {
     throw new RuntimeException("This TomSymbol has no SlotList");
  }

  public TomSymbol setSlotList(SlotList _slotList)
  {
     throw new RuntimeException("This TomSymbol has no SlotList");
  }

  public Option getOption()
  {
     throw new RuntimeException("This TomSymbol has no Option");
  }

  public TomSymbol setOption(Option _option)
  {
     throw new RuntimeException("This TomSymbol has no Option");
  }

  public TargetLanguage getTlCode()
  {
     throw new RuntimeException("This TomSymbol has no TlCode");
  }

  public TomSymbol setTlCode(TargetLanguage _tlCode)
  {
     throw new RuntimeException("This TomSymbol has no TlCode");
  }

}

