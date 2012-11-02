package lib;

/**
 * Created with IntelliJ IDEA.
 * User: christophe
 * Date: 23/10/12
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */

/**
 * Abstract class of functions. A Fun<X,Y> is a function from X to Y. The abstract method apply is the actual function
 * and the only one to be defined in order to instantiate the class.
 *
 * @param <A> the domain.
 * @param <B> to codomain.
 * @return a function fron X to Y.
 */
public abstract class Fun<A,B> {
    /**
     * Abstract class of functions. A Fun<X,Y> is a function from X to Y. The abstract method apply is the actual function
     * and the only one to be defined in order to instantiate the class.
     *
     * @param arg the argument.
     * @return the return value.
     * @throws MOFException
     * @see MOFException
     */
    public abstract B apply(A arg) throws MOFException;

    /**
     * The left functional composition.
     *
     * @param g the left function of the composition (g o this).
     * @return the functionnal composition (g o this)
     */
    public <C> Fun<A,C> andthen (final Fun<B,C> g) {
        final Fun<A,B> f = this;
        return new Fun<A,C>() {  public C apply(A a) throws MOFException { return g.apply(f.apply(a)); } } ;
    }

    /**
     * The right functional composition.
     *
     * @param g the right function of the composition (this o g).
     * @return the functionnal composition (this o g)
     */
    public <C> Fun<C,B> compose (final Fun<C,A> g) {
        final Fun<A,B> f = this;
        return new Fun<C,B>() { public B apply(C a) throws MOFException { return f.apply(g.apply(a)); } } ;
    }


    /**
     * The functional product (component wise) of this and g. Given two functions f: A->B and g:C->D the functional
     * product of f and g (written f * g) is the function f*g from A * C to B * D defined by f*g: (a,c) |-> (f a, g c).
     *
     * @param g a function.
     * @return this * g
     * @see P
     */
    public <C,D> Fun<P<A,C>,P<B,D>> times(final Fun<C,D> g) {
        final Fun<A,B> f = this;
        return new Fun<P<A,C>,P<B,D>>() {
                 public P<B,D> apply(P<A,C> p) throws MOFException { return new P<B,D>(f.apply(p.left), g.apply(p.right)) ; }
               } ;
    }

    public static <T> Fun<T,T> id(T t) { return new Fun<T,T>() { public T apply(T u) { return u; } }; }
}