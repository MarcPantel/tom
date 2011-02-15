import org.junit.*;
import org.junit.Assert.*;
import ppeditor;
import org.junit.Test;

public class TestPPTextPosition{

  PPTextPosition pptextposition;

  @Before
  public void createPPTextPosition() {
    this.pptextposition = new PPTextPosition(14,8);
  }

  @After
  public void end() {}

  @Test
  public void testGetLine() {
    Assert.assertEquals("Line is 14", 14, pptextposition.getLine());
  }

  @Test
  public void testGetColumn() {
    Assert.assertEquals("Column is 8", 8, pptextposition.getColumn());
  }

}
