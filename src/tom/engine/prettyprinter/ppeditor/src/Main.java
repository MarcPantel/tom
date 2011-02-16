package ppeditor;

public class Main {

  public static void main(String[] args){

  /*Step one: writing were there was nothing before (insertion mode)*/
  String text1="Première ligne";
  String text2="Troisième ligne\n\nEt cinquième si tout va bien";
  PPCursor aCursor = new PPCursor(0,0);

  aCursor.write(text1);
  aCursor.setPosition(new PPTextPosition(2,0));
  aCursor.write(text2);

  /*Step two: writing inside of a line (insertion mode)*/
  String text3="Septième ligne";
  String text4="// Insertion entre \"Sept\" et \"ième ligne\" //";

  aCursor.setPosition(new PPTextPosition(6,0));
  aCursor.write(text3);
  aCursor.setPosition(new PPTextPosition(6,4));
  aCursor.write(text4);

  aCursor.dump("./Test.txt");
  }
}
