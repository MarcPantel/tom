package tom.engine.prettyprinter;

import ppeditor.*;
import java.lang.StringBuffer;

public class OutputFormatter{

  private String fileName;
  private PPCursor theCursor;
  
  public OutputFormatter(String n) {

    this.fileName=n;
    this.theCursor=new PPCursor(0,0);
    this.theCursor.setInsertion(false);
  }

  public void write(String text, int ligne, int colonne, boolean insertion) { 

    theCursor.setPosition(new PPTextPosition(ligne,colonne));
    theCursor.setInsertion(insertion);
    theCursor.write(text);
  }

  public StringBuffer dump(){

    return theCursor.dump("./"+this.fileName);
  }

} 
