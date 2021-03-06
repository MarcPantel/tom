import java.util.Hashtable;
import java.util.HashSet;
import systemf.types.*;

public class SystemF {

  %include { systemf/SystemF.tom }

  public static void testRawCompilation() {
    RawLTerm t = `RawTAbs(RawTypeTermAbs("T",
          RawLAbs(RawTermTermAbs("x",RawTVar("T"),RawLVar("x")))));

  }

  public static boolean testRename() {
    LTerm t1 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(1,"x"))))));
    LTerm t2 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(2,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(2,"x"))))));

    Hashtable<TermVar,TermVar> map = new Hashtable<TermVar,TermVar>();
    map.put(`TermVar(1,"x"),`TermVar(2,"x"));
    return t2 == t1.renameTermVar(map);
  }

  public static boolean testRefresh() {
    TypeTermAbs t1 = `TypeTermAbs(TypeVar(0,"T"),
        LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(1,"x")))));

    TypeTermAbs t2 = `TypeTermAbs(TypeVar(1,"T"),
        LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(1,"T")),LVar(TermVar(1,"x")))));
    return t2 == t1.refresh();
  }

  public static boolean testExport() {
    boolean ok = true;

    // correct export
    LTerm t1 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(1,"x"))))));
    RawLTerm t2 = `RawTAbs(RawTypeTermAbs("T",
          RawLAbs(RawTermTermAbs("x",RawTVar("T"),RawLVar("x")))));

    ok = ok && (t2 == t1.export()); 

    // malformed term 
    LTerm t3 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(2,"y"))))));
    try { t3.export(); }
    catch(Exception e) { ok = ok && true; }

    // malformed term 
    LTerm t4 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(2,"T")),LVar(TermVar(1,"x"))))));
    try { t4.export(); }
    catch(Exception e) { ok = ok && true; }

    // correct export
    LTerm t5 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"), TVar(TypeVar(0,"T")),
              LAbs(TermTermAbs(TermVar(2,"x"),Arrow(TVar(TypeVar(0,"T")),TVar(TypeVar(0,"T"))),
                  LApp(LVar(TermVar(2,"x")),LVar(TermVar(1,"x")))))))));
    RawLTerm t6 = `RawTAbs(RawTypeTermAbs("T",
          RawLAbs(RawTermTermAbs("x",RawTVar("T"),
              RawLAbs(RawTermTermAbs("x1",RawArrow(RawTVar("T"),RawTVar("T")),
                  RawLApp(RawLVar("x1"),RawLVar("x"))))))));

    ok = ok && (t6 == t5.export()); 

    return ok;
  }

  public static boolean testGetBound() {
    TypeTermAbs t1 = `TypeTermAbs(TypeVar(0,"T"),
        LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(0,"T")),LVar(TermVar(1,"x")))));

    HashSet<TypeVar> s = new HashSet<TypeVar>();
    s.add(`TypeVar(0,"T"));
    
    return s.equals(t1.getBoundTypeVar());
  }

  public static boolean testGetBoundRaw() {
    RawTypeTermAbs t1 = `RawTypeTermAbs("T",
        RawLAbs(RawTermTermAbs("x",RawTVar("T"),RawLVar("x"))));

    tom.library.freshgom.ConvertMap<TypeVar> m = t1.getBoundTypeVarMap();
    return m.size() == 1 && m.get("T").gethint().equals("T");
  }

  // warning : depends of teh history of tests
  public static boolean testConvert() {
    RawLTerm t1 = `RawTAbs(RawTypeTermAbs("T",
          RawLAbs(RawTermTermAbs("x",RawTVar("T"),
              RawLAbs(RawTermTermAbs("x1",RawArrow(RawTVar("T"),RawTVar("T")),
                  RawLApp(RawLVar("x1"),RawLVar("x"))))))));

    LTerm t2 = `TAbs(TypeTermAbs(TypeVar(3,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"),TVar(TypeVar(3,"T")),
              LAbs(TermTermAbs(TermVar(2,"x"),Arrow(TVar(TypeVar(3,"T")),TVar(TypeVar(3,"T"))),
                  LApp(LVar(TermVar(2,"x")),LVar(TermVar(1,"x")))))))));

    return t2.equals(t1.convert());
  }

  public static boolean testAlpha() {
    LTerm t1 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"), TVar(TypeVar(0,"T")),
              LAbs(TermTermAbs(TermVar(2,"x"),Arrow(TVar(TypeVar(0,"T")),TVar(TypeVar(0,"T"))),
                  LApp(LVar(TermVar(2,"x")),LVar(TermVar(1,"x")))))))));

    LTerm t2 = `TAbs(TypeTermAbs(TypeVar(2,"T"),
          LAbs(TermTermAbs(TermVar(4,"x"), TVar(TypeVar(2,"T")),
              LAbs(TermTermAbs(TermVar(1,"x"),Arrow(TVar(TypeVar(2,"T")),TVar(TypeVar(2,"T"))),
                  LApp(LVar(TermVar(1,"x")),LVar(TermVar(4,"x")))))))));

    LTerm t3 = `TAbs(TypeTermAbs(TypeVar(2,"T"),
          LAbs(TermTermAbs(TermVar(4,"x"), TVar(TypeVar(2,"T")),
              LAbs(TermTermAbs(TermVar(1,"x"),Arrow(TVar(TypeVar(2,"T")),TVar(TypeVar(2,"T"))),
                  LApp(LVar(TermVar(1,"x")),LVar(TermVar(1,"x")))))))));

    return (t1.equals(t2) && (! t1.equals(t3)));
  }

  public static boolean testConvertAlpha() {
    LTerm t1 = `TAbs(TypeTermAbs(TypeVar(0,"T"),
          LAbs(TermTermAbs(TermVar(1,"x"), TVar(TypeVar(0,"T")),
              LAbs(TermTermAbs(TermVar(2,"x"),Arrow(TVar(TypeVar(0,"T")),TVar(TypeVar(0,"T"))),
                  LApp(LVar(TermVar(2,"x")),LVar(TermVar(1,"x")))))))));

    return t1.equals(t1.export().convert());
  }

  public static void main(String[] args) {
    System.out.println("testRename: " +  testRename());
    System.out.println("testRefresh: " +  testRefresh());
    System.out.println("testExport: " +  testExport());
    System.out.println("testGetBound: " +  testGetBound());
    System.out.println("testGetBoundRaw: " +  testGetBoundRaw());
    System.out.println("testConvert: " +  testConvert());
    System.out.println("testAlpha: " +  testAlpha());
    System.out.println("testConvertAlpha: " +  testConvertAlpha());
  }
}
