package lib.sl;

/**
 * Created with IntelliJ IDEA.
 * User: christophe
 * Date: 25/10/12
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */

import lib.Fun;
import tom.library.sl.VisitFailure;
import lib.Zip;

/** The Fail Visitor. It throws VisitFailure regardless of its input */
public class Fail<X,Y> extends Visitor<X,Y> {
   public <T> X visitZK(Zip<T,X> z, Fun<Zip<X, Y>,Zip<X, Y>> k) throws VisitFailure { throw new VisitFailure(); }
}
