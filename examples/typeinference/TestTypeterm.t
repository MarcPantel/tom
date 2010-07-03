public class TestTypeterm{
  %gom {
    module TestTypeterm
      imports int
      abstract syntax
      B = b()
  }

  public static void main(String[] args) {
    TestTypeterm test = new TestTypeterm();
    test.plus10(10);
  }

  public int plus10(int n) {
    %match{
      x << int n -> { 
        if (`x>1) {  
          return `plus10(x + 10); 
        }
      }
    }
    return -1;
  }
}
