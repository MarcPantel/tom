import org.junit.Assert;

public class Test {
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main(Test.class.getName());
  }

%gom {
  module test
  imports
  abstract syntax
}

  @org.junit.Test
  public void test1() {
    if(true) {
      return ;
    } 
    Assert.fail("should not be there");
  }

}
