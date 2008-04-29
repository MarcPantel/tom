package debugger;

import tom.library.sl.*;
import java.util.Arrays;

public class Decorator extends ADecorator {

  %include{sl.tom}

  protected Maker maker;

  public Decorator(String name, Maker maker, Strategy[] children) {
    initSubterm(children);
    this.maker = maker;
    this.realCalee = maker.buildDecorated(visitors);
    this.name = name;
  }

  // mimics AbstractStrategy
  public Visitable setChildren(Visitable[] cdn) {
    realCalee = maker.buildDecorated((Strategy[]) cdn);
    return supersetChildren(cdn);
  }

  // mimics AbstractStrategy
  public Visitable setChildAt(int i, Visitable child) {
    Strategy[] copy = Arrays.copyOf(visitors,visitors.length);
    copy[i] = (Strategy) child;
    realCalee = maker.buildDecorated(copy);
    return supersetChildAt(i,child);
  }
}
