package jtom.adt;

abstract public class Expression_GetSliceArrayImpl
extends Expression
{
  protected void init(int hashCode, aterm.ATermList annos, aterm.AFun fun,	aterm.ATerm[] args) {
    super.init(hashCode, annos, fun, args);
  }
  protected void initHashCode(aterm.ATermList annos, aterm.AFun fun, aterm.ATerm[] i_args) {
  	super.initHashCode(annos, fun, i_args);
  }
  protected Expression_GetSliceArrayImpl(TomSignatureFactory factory) {
    super(factory);
  }
  private static int index_astName = 0;
  private static int index_subjectListName = 1;
  private static int index_variableBeginAST = 2;
  private static int index_variableEndAST = 3;
  public shared.SharedObject duplicate() {
    Expression_GetSliceArray clone = new Expression_GetSliceArray(factory);
     clone.init(hashCode(), getAnnotations(), getAFun(), getArgumentArray());
    return clone;
  }

  public boolean equivalent(shared.SharedObject peer) {
    if (peer instanceof Expression_GetSliceArray) {
      return super.equivalent(peer);
    }
    return false;
  }
  protected aterm.ATermAppl make(aterm.AFun fun, aterm.ATerm[] i_args, aterm.ATermList annos) {
    return getTomSignatureFactory().makeExpression_GetSliceArray(fun, i_args, annos);
  }
  public aterm.ATerm toTerm() {
    if (term == null) {
      term = getTomSignatureFactory().toTerm(this);
    }
    return term;
  }

  public boolean isGetSliceArray()
  {
    return true;
  }

  public boolean hasAstName()
  {
    return true;
  }

  public boolean hasSubjectListName()
  {
    return true;
  }

  public boolean hasVariableBeginAST()
  {
    return true;
  }

  public boolean hasVariableEndAST()
  {
    return true;
  }

  public TomName getAstName()
  {
    return (TomName) this.getArgument(index_astName) ;
  }

  public Expression setAstName(TomName _astName)
  {
    return (Expression) super.setArgument(_astName, index_astName);
  }

  public TomTerm getSubjectListName()
  {
    return (TomTerm) this.getArgument(index_subjectListName) ;
  }

  public Expression setSubjectListName(TomTerm _subjectListName)
  {
    return (Expression) super.setArgument(_subjectListName, index_subjectListName);
  }

  public TomTerm getVariableBeginAST()
  {
    return (TomTerm) this.getArgument(index_variableBeginAST) ;
  }

  public Expression setVariableBeginAST(TomTerm _variableBeginAST)
  {
    return (Expression) super.setArgument(_variableBeginAST, index_variableBeginAST);
  }

  public TomTerm getVariableEndAST()
  {
    return (TomTerm) this.getArgument(index_variableEndAST) ;
  }

  public Expression setVariableEndAST(TomTerm _variableEndAST)
  {
    return (Expression) super.setArgument(_variableEndAST, index_variableEndAST);
  }

  public aterm.ATermAppl setArgument(aterm.ATerm arg, int i) {
    switch(i) {
      case 0:
        if (! (arg instanceof TomName)) { 
          throw new RuntimeException("Argument 0 of a Expression_GetSliceArray should have type TomName");
        }
        break;
      case 1:
        if (! (arg instanceof TomTerm)) { 
          throw new RuntimeException("Argument 1 of a Expression_GetSliceArray should have type TomTerm");
        }
        break;
      case 2:
        if (! (arg instanceof TomTerm)) { 
          throw new RuntimeException("Argument 2 of a Expression_GetSliceArray should have type TomTerm");
        }
        break;
      case 3:
        if (! (arg instanceof TomTerm)) { 
          throw new RuntimeException("Argument 3 of a Expression_GetSliceArray should have type TomTerm");
        }
        break;
      default: throw new RuntimeException("Expression_GetSliceArray does not have an argument at " + i );
    }
    return super.setArgument(arg, i);
  }
  protected int hashFunction() {
    int c = 0 + (getAnnotations().hashCode()<<8);
    int a = 0x9e3779b9;
    int b = (getAFun().hashCode()<<8);
    a += (getArgument(3).hashCode() << 24);
    a += (getArgument(2).hashCode() << 16);
    a += (getArgument(1).hashCode() << 8);
    a += (getArgument(0).hashCode() << 0);

    a -= b; a -= c; a ^= (c >> 13);
    b -= c; b -= a; b ^= (a << 8);
    c -= a; c -= b; c ^= (b >> 13);
    a -= b; a -= c; a ^= (c >> 12);
    b -= c; b -= a; b ^= (a << 16);
    c -= a; c -= b; c ^= (b >> 5);
    a -= b; a -= c; a ^= (c >> 3);
    b -= c; b -= a; b ^= (a << 10);
    c -= a; c -= b; c ^= (b >> 15);

    return c;
  }
}
