import jtom.runtime.xml.*;
import jtom.runtime.xml.adt.*;
import java.io.*;
import aterm.*;
import jtom.runtime.*;

public class HTMLToLaTeX {
  
  %include{ TNode.tom }
    
  private XmlTools xtools;
  private GenericTraversal traversal = new GenericTraversal();
  private TNodeFactory getTNodeFactory() {
    return xtools.getTNodeFactory();
  }

  public static void main (String args[]) {
    HTMLToLaTeX html2latex = new HTMLToLaTeX();
    if (args.length == 2) {
      html2latex.run(args[0],args[1]);
    } else {
      System.out.println("Usage : java HTMLToLaTeX file.html output.tex\n"+
			 "\tfile.html must be a valid XHTML file");
    }
  }

  private FileWriter out;
  
  private void write(String s) {
    try {
      out.write(s);
      //System.out.print(s);
    } catch (IOException e) {
      System.out.println("Error in write");
    }
  }

  private void run(String filename, String output){
    try {
      out = new FileWriter(output);
      xtools = new XmlTools();
      ATerm term = xtools.convertXMLToATerm(filename);
      toLaTeX(term);
      out.flush();
      out.close();
    } catch (IOException e) {
      System.out.println("Cannot open file "+output);
    }
  }
    
  private void toLaTeX(ATerm subject) {
    Collect1 collect = new Collect1 () {
	public boolean apply(ATerm t) {
	    if (t instanceof TNode) {
	    %match(TNode t) {
	      <html><head>h*</head> <body>b*</body></html> -> {
                  //System.out.println("t = " + t);
                  //System.out.println("h = " + h);
		write("\\documentclass{article}\n");
		toLaTeX(h);
	        write("\\begin{document}\n\\maketitle\n");
		toLaTeX(b);
		write("\\end{document}\n");
		return false;
	      }
	      <title>#TEXT(title)</title> -> {
		write("\\title{"+title+"}\n\\author{}\n"+
		      "\\date{Auto-generated by HTMLToLaTeX}\n");
		return false;
	      }
	      <p>data*</p> |
              <pre>data*</pre> |
              <blockquote>data*</blockquote> |
	      <u>data*</u> |
              <s>data*</s> |
              <strike>data*</strike> | 
	      <font>data*</font> |
              <basefont>data*</basefont> -> {
                toLaTeX(data);
		return false;
	      }
	      <h1>h*</h1> -> {
		write("\\section{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <h2>h*</h2> -> {
		write("\\subsection{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <h3>h*</h3> -> {
		write("\\subsubsection{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <h4>h*</h4> -> {
		write("\\subsubsubsection{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <h5>h*</h5> -> {
		write("\\subsubsubsubsection{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <h6>h*</h6> -> {
		write("\\subsubsubsubsubsection{");
		toLaTeX(h);
		write("}\n");
		return false;
	      }
	      <ul>data*</ul> -> {
		write("\\begin{itemize}\n");
		toLaTeX(data);
		write("\\end{itemize}\n");
		return false;
	      }
	      <ol>data*</ol> -> {
		write("\\begin{enumerate}\n");
		toLaTeX(data);
		write("\\end{enumerate}\n");
		return false;
	      }
	      <dl>data*</dl> -> {
		write("\\begin{description}\n");
		toLaTeX(data);
		write("\\end{description}\n");
		return false;
	      }
	      <a href=hrefurl>data*</a> -> {
		toLaTeX(data);
		//write(" (URL :"+hrefurl+") ");
		return false;
	      }
	      <img alt=text/> -> {
		write("img : "+text);
		return false;
	      }
	      <li>data*</li> |
              <dd>data*</dd> |
              <dt>data*</dt> -> {
		write("\\item ");
		toLaTeX(data);
		write("\n");
		return false;
	      }
	      <code>data*</code> -> {
		write("{\\tt ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <tt>data*</tt> -> {
		write("{\\tt ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <i>data*</i> -> {
		write("{\\it ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <b>data*</b> -> {
		write("{\\bf ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <big>data*</big> -> {
		write("{\\big ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <small>data*</small> -> {
		write("{\\small ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <em>data*</em> -> {
		write("{\\em ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <br/> -> {
		write("\n\n");
		return false;
	      }
	      <center>data*</center> -> {
		write("\\begin{center}\n");
		toLaTeX(data);
		write("\\end{center}\n");
		return false;
	      }
	      <meta content=c/> -> {
		write("% meta : content="+c);
		return false;
	      }
	      <link/> |
              <style/> -> {
		return false;
	      }
	      <table>data*</table> -> {
		write("\\begin{tabular}{");
		write(columnSpec(tableMaxCol(data)));
		write("}\n");
		firstLine = true;
		toLaTeX(data);
		write("\\end{tabular}\n");
		return false;
	      }
	      <tr>data*</tr> -> {
		if (!firstLine) {
		  write(" \\\\\n");
		} else {
		  firstLine = false;
		}
		firstCol = true;
		toLaTeX(data);
		return false;
	      }
	      <td>data*</td> -> {
		if (!firstCol) {
		  write(" & ");
		} else {
		  firstCol = false;
		}
		toLaTeX(data);
		return false;
	      }
	      <th>data*</th> -> {
		if (!firstCol) {
		  write(" & ");
		} else {
		  firstCol = false;
		}
		write("{\\bf ");
		toLaTeX(data);
		write("}");
		return false;
	      }
	      <div id=id>data*</div> -> {
		write("\\label{"+id+"}\n");
		toLaTeX(data);
		return false;
	      }
	      <div>data*</div> -> {
		toLaTeX(data);
		return false;
	      }
	      <hr/> |
              <script/> -> {
		return false;
	      }
                
	      #TEXT(text) -> {
		write(text);
		return false;
	      }
	      #COMMENT(text) -> {
		write("% "+text);
		return false;
	      }
                
	      _ -> {
		return true;
	      }
	    } // match 
	  } else {
	      return true;
	  }
	} //apply
      };
    traversal.genericCollect(subject, collect);
  } //toLaTeX

  private String columnSpec(int nbCol) {
    if (nbCol == 0){
      return "";
    } else {
      return "l"+columnSpec(nbCol-1);
    }
  }

  private int max, currentMax;
  private boolean firstLine, firstCol;

  private int tableMaxCol(ATerm t) {
    Collect1 collect = new Collect1 () {
	public boolean apply(ATerm t) {
	    if (t instanceof TNode) {
	      %match(TNode t) {
		<tr>data*</tr> -> {
		  tableMaxCol(data);
		  max = (max<currentMax ? currentMax : max);
		  currentMax = 0;
		  return false;
		}
		<td/> | <th/> -> {
		  currentMax++;
		  return false;
		}
		_ -> {
		  return true;
		}
	      }
	    } else {
	      return true;
	    }
	}
      };
    traversal.genericCollect(t, collect);
    return max;
  }

  private String outFilter(String s) {
    String result;
    result = s.replaceAll("$","\\$");
    //result = result.replaceAll("%","\\%");
    return result;
  }

}
