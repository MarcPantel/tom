package ppeditor.test;

import org.junit.Test;
import org.junit.*;
import org.junit.Assert.*;
import ppeditor.*;
import java.io.*;
import java.util.ArrayList;

public class TestPPCursor {

  private PPCursor cursor;

  @Before
  public void initializeAttributes() {
    this.cursor=new PPCursor(14,8);
  }

  @After
  public void endTest() {
    (new File("./test.txt")).delete();
  }

  @Test
  public void testMove() {
    cursor.move(new PPTextPosition(2,-3));
    Assert.assertEquals("Is the cursor where it should be?", cursor.getPosition(), new PPTextPosition(16,5));
  }

  @Test
  public void testWrite1() {
    cursor.write("For great justice!");
    Assert.assertEquals("Text written on one non-already existing line.", cursor.getFileBuffer().get(13), "        For great justice!");
  }

  @Test
  public void testWrite2() {
    ArrayList<StringBuffer> expectedBuffer = new ArrayList<StringBuffer>();
    for(int i=0; i<9;i++) {
      expectedBuffer.add(new StringBuffer(""));
      expectedBuffer.add(new StringBuffer("All your base are belong to us."));
      expectedBuffer.add(new StringBuffer("For great justice!"));
    cursor.setPosition(new PPTextPosition(10,0));
    cursor.write("All your base are belong to us.\nFor great justice!");
    Assert.assertEquals("Text written on two non-already existing lines.", cursor.getFileBuffer(), expectedBuffer);
  }
  }

  @Test
  public void test(){
  } 




}
