/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package definitions;

import java.util.HashSet;

/**
 *
 * @author hubert
 */
public class MakeAllStrategy extends Request {

  public MakeAllStrategy(int i) {
    super(i);
  }

  @Override
  HashSet<Hole> fillATerm(Hole aTerm) {
    HashSet<Hole> res = new HashSet<Hole>();

    //fill the term by choosing one of its constructors
    Hole[] deps = aTerm.chooseConstructor();

    //dispatch fields of the term between two categories: these whose dimension 
    //equals dimension of the term, and the others
    HashSet<Hole> listHigherDim = new HashSet<Hole>();
    for (int i = 0; i < deps.length; i++) {
      Hole dep = deps[i];
      if (dep.getDimention() < aTerm.getDimention()) {
        res.add(dep);
      } else {
        listHigherDim.add(dep);
      }
    }

    //spread number of recursions of the curent term into each fields with the 
    //same dimension
    spreadBetweenHigherDim(listHigherDim);

    //re-apply algorithm on same dimension fields in order to eliminate them
    for (Hole term : listHigherDim) {
      Request req = term.getRequest();
      res.addAll(req.fillATerm(term));
    }
    return res;
  }
}
