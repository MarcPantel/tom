package jtom.adt;

import aterm.*;
import java.io.InputStream;
import java.io.IOException;

abstract public class TomSymbolTableImpl extends TomSignatureConstructor
{
  protected TomSymbolTableImpl(TomSignatureFactory factory) {
     super(factory);
  }
  protected void init(int hashCode, aterm.ATermList annos, aterm.AFun fun,	aterm.ATerm[] args) {
    super.init(hashCode, annos, fun, args);
  }
  protected void initHashCode(aterm.ATermList annos, aterm.AFun fun, aterm.ATerm[] i_args) {
  	super.initHashCode(annos, fun, i_args);
  }
  public boolean isEqual(TomSymbolTable peer)
  {
    return term.isEqual(peer.toTerm());
  }
  public boolean isSortTomSymbolTable()  {
    return true;
  }

  public boolean isTable()
  {
    return false;
  }

  public boolean hasEntryList()
  {
    return false;
  }

  public TomEntryList getEntryList()
  {
     throw new RuntimeException("This TomSymbolTable has no EntryList");
  }

  public TomSymbolTable setEntryList(TomEntryList _entryList)
  {
     throw new RuntimeException("This TomSymbolTable has no EntryList");
  }

}

