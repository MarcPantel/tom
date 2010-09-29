package mi2.gom;

import base.data.types.t1.*;
import base.data.types.t2.*;
import base.data.types.listt1.*;
import base.data.types.T2;
import base.data.types.T1;
import base.data.types.ListT1;

/**
 * @author nvintila
 * @date 2:32:06 PM Jun 13, 2009
 */
public class Module {

  public static final Module instance = new Module();

  /**
   * for each constructor f, the interface f_MappingI and the abstract class
   * f_Introspector can be generated by Tom from the Module specification. If
   * the user do not need introspector, he just has to implement f_MappingI. 
   */

  public static interface a_MappingI {
    // should be generated automatically
    boolean isSym(Object t);
    a make();
  }

  public static abstract class a_Introspector extends mi2.mapping.Mapping implements tom.library.sl.Introspector,a_MappingI {
    // should be generated automatically
    public abstract a make();

    public <T> T setChildren(T o, Object[] children) {
      return (T) make();
    }

    public Object[] getChildren(Object o) {
      return new Object[]{ };
    }

    public <T> T setChildAt(T o, int i, Object child) {
      assert false : "Unexpected call.";
      return null;
    }

    public Object getChildAt(Object o, int i) {
      return null;
    }

    public int getChildCount(Object o) {
      return 0;
    }

    public Class forType() {
      return a.class;
    }
  }


  public static class a_Mapping extends a_Introspector {
    // written by hand or generated by Gom
    public static a_Mapping instance = new a_Mapping();

    public boolean isSym(Object t) {
      return t instanceof a;
    }

    public a make() {
      return a.make();
    }
  }

  /** ------------------------------ */
  public static interface b_MappingI {
    boolean isSym(Object t);
    b make();
  }

  public static abstract class b_Introspector extends mi2.mapping.Mapping implements tom.library.sl.Introspector,b_MappingI {
    public abstract b make();

    public <T> T setChildren(T o, Object[] children) {
      return (T) make();
    }

    public Object[] getChildren(Object o) {
      return new Object[]{ };
    }

    public <T> T setChildAt(T o, int i, Object child) {
      assert false : "Unexpected call.";
      return null;
    }

    public Object getChildAt(Object o, int i) {
      return null;
    }

    public int getChildCount(Object o) {
      return 0;
    }

    public Class forType() {
      return b.class;
    }
  }


  public static class b_Mapping extends b_Introspector {
    public static b_Mapping instance = new b_Mapping();

    public boolean isSym(Object t) {
      return t instanceof b;
    }

    public b make() {
      return b.make();
    }
  }

  /** ------------------------------ */
  public static interface f_MappingI {
    // Test
    boolean isSym(Object t);

    f make(Object s1, Object s2);

    // Slot getters
    T1 gets1(Object t);

    T2 gets2(Object t);

  }

  public static abstract class f_Introspector extends mi2.mapping.Mapping implements tom.library.sl.Introspector,f_MappingI {

    // LocalIntrospector
    public <T> T setChildren(T o, Object[] children) {
      return (T) make(children[0], children[1]);
    }

    public Object[] getChildren(Object o) {
      return new Object[]{ ((f)o).gets1(), ((f)o).gets2() };
    }

    public <T> T setChildAt(T o, int i, Object child) {
      switch (i) {
        case 0:
          return (T) make(child, ((f)o).gets2());
        case 1:
          return (T) make(((f)o).gets1(), (T2)child);
          //todo: or ((f)o).setS2((T2)child); ?
      }
      assert false : "Unexpected call.";
      return null;
    }

    public Object getChildAt(Object o, int i) {
      switch (i) {
        case 0:
          return gets1(o);
        case 1:
          return gets2(o);
      }
      assert false : "Unexpected call.";
      return null;
    }

    public int getChildCount(Object o) {
      return 2;
    }

    public f make(Object[] children) {
      return make(children[0], children[1]);
    }

    public Class forType() {
      return f.class;
    }

  }


  public static class f_Mapping extends f_Introspector {
    public static f_Mapping instance = new f_Mapping();

    // Test
    public boolean isSym(Object t) {
      return t instanceof f;
    }

    // Make
    public f make(Object s1, Object s2) {
      return f.make((T1)s1, (T2)s2);
    }

    public T1 gets1(Object t) {
      return ((f)t).gets1();
    }

    public T2 gets2(Object t) {
      return ((f)t).gets2();
    }

  }



  /** ------------------------------ */
  public static interface g_MappingI {
    // Test
    boolean isSym(Object t);

    g make(Object s2);

    // Slot getters
    T2 gets2(Object t);

  }

  public static abstract class g_Introspector extends mi2.mapping.Mapping implements tom.library.sl.Introspector,g_MappingI {
    public <T> T setChildren(T o, Object[] children) {
      return (T) make(children[0]);
    }

    public Object[] getChildren(Object o) {
      return new Object[]{ (((g)o).gets2()) };
    }

    public <T> T setChildAt(T o, int i, Object child) {
      switch (i) {
        case 0:
          return (T) make(child);
          //todo : or ((g)o).setS2((T2)child); ?
      }
      assert false : "Unexpected call.";
      return null;
    }

    public Object getChildAt(Object o, int i) {
      switch (i) {
        case 0:
          return gets2(o);
      }
      assert false : "Unexpected call.";
      return null;
    }

    public int getChildCount(Object o) {
      return 1;
    }

    public Class forType() {
      return g.class;
    }
  }

  public static class g_Mapping extends g_Introspector {
    public static g_Mapping instance = new g_Mapping();

    // Test
    public boolean isSym(Object t) {
      return t instanceof g;
    }

    // Make
    public g make(Object s2) {
      return g.make((T2)s2);
    }

    public g make(Object[] children) {
      return make(children[0]);
    }

    public T2 gets2(Object t) {
      return ((g)t).gets2();
    }

  }
  /** ------------------------------ */
  public static interface h_MappingI {
    boolean isSym(Object t);
    h make(Object s);
    ListT1 getts(Object t);

  }

  public static abstract class h_Introspector extends mi2.mapping.Mapping implements tom.library.sl.Introspector,h_MappingI {
    public <T> T setChildren(T o, Object[] children) {
      return (T) make(children[0]);
    }

    public Object[] getChildren(Object o) {
      return new Object[]{ (((h)o).gets2()) };
    }

    public <T> T setChildAt(T o, int i, Object child) {
      switch (i) {
        case 0:
          return (T) make(child);
      }
      assert false : "Unexpected call.";
      return null;
    }

    public Object getChildAt(Object o, int i) {
      switch (i) {
        case 0:
          return getts(o);
      }
      assert false : "Unexpected call.";
      return null;
    }

    public int getChildCount(Object o) {
      return 1;
    }

    public Class forType() {
      return h.class;
    }
  }

  public static class h_Mapping extends h_Introspector {
    public static h_Mapping instance = new h_Mapping();

    public boolean isSym(Object t) {
      return t instanceof h;
    }

    public h make(Object s) {
      return h.make((ListT1)s);
    }

    public h make(Object[] children) {
      return make(children[0]);
    }

    public ListT1 getts(Object t) {
      return ((h)t).getts();
    }

  }

    /**
     * ------------------------------
     */
    public static abstract class concT1_Introspector extends mi2.mapping.List_Mapping<T1,ListT1> implements tom.library.sl.Introspector {
      // should be generated automatically
      public <T> T setChildren(T o, Object[] children) {
        //TODO
        assert false : "Unexpected call!";
        return null;
      }

      public Object[] getChildren(Object o) {
        //TODO
        assert false : "Unexpected call!";
        return null;
      }

      public <T> T setChildAt(T o, int i, Object child) {
        //TODO
        assert false : "Unexpected call.";
        return null;
      }

      public Object getChildAt(Object o, int i) {
        //TODO
        assert false : "Unexpected call.";
        return null;
      }

      public int getChildCount(Object o) {
        //TODO
        assert false : "Unexpected call!";
        return 0;
      }

      public Class forType() {
        return ListT1.class;
      }
    }

    public static class concT1_Mapping extends concT1_Introspector {
      public static concT1_Mapping instance = new concT1_Mapping();

      public boolean isSym(Object t) {
        return (t instanceof base.data.types.listt1.ConsconcT1) || (t instanceof base.data.types.listt1.EmptyconcT1);
      }

      public boolean isEmpty(ListT1 l) {
        return l.isEmptyconcT1();
      }

      public ListT1 makeEmpty() {
        return base.data.types.listt1.EmptyconcT1.make();
      }

      public ListT1 makeInsert(T1 o, ListT1 l) {
        return base.data.types.listt1.ConsconcT1.make(o,l);
      }

      public T1 getHead(ListT1 l) {
        return l.getHeadconcT1();
      }

      public ListT1 getTail(ListT1 l) {
        return l.getTailconcT1();
      }

    }

}
