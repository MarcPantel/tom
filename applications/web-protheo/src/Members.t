import tom.library.xml.*;
import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;
import aterm.*;
import java.io.*;

import tom.library.sl.*;

public class Members {

  %include{sl.tom}
  %include{adt/tnode/TNode.tom}

  private XmlTools xtools;
  private TNode members;

  public Members(TNode members,XmlTools xtools){
    this.members = members;
    this.xtools = xtools;
  }

  /**
   * Generate members page body
   */
  public TNode getContent(String lang) throws Exception{
    Strategy ruleId = `RewriteSystemId(lang);
    TNode output = null;
    try { output = (TNode) `BottomUp(ruleId).visit(members); }
    catch(VisitFailure e) { e.printStackTrace(); }

    TNodeList result = `concTNode(output);
    // Go reverse when doing concTNode, otherwise it will added at the end of file
    %match(String lang){
      "fr" -> {
        result = `concTNode(<p>#TEXT("Vous pouvez trouver les contacts dans l'") <a href="http://www.loria.fr/services/annuaire/annuaire.php">#TEXT("annuaire")</a>#TEXT(".")</p>,result*);
        //result = `concTNode(<h2>#TEXT("Les membres du projet Prothéo")</h2>,result*);
        result = `concTNode(<h2>#TEXT("Les membres du projet Proth\u00E9o")</h2>,result*);
      }
      "en" ->  {
        result = `concTNode(<p>#TEXT("You can find contacts in the ") <a href="http://www.loria.fr/services/annuaire/annuaire.php">#TEXT("addressbook")</a>#TEXT(".")</p>,result*);
        result = `concTNode(<h2>#TEXT("Protheo project members")</h2>,result*);
      } 
    }
    return `xml(<div id="content">result*</div>);
  }

  /*********************************************************************************************************************/

  /**
   * Translate XML into xHTML
   */
  %strategy RewriteSystemId(lang: String) extends `Identity() {

    visit TNode {
      <members>(g*)</members> -> {
        return `xml(<div id="members">g*</div>);
      }
      <group class=x>(_*,tag@<(title_fr|title_en)>title</(title_fr|title_en)>,p*)</group> -> {
        if((`tag.getName()).endsWith(lang)) {
          return `xml(<div id=x><h3><a name=x>#TEXT("")</a>title</h3>p*</div>);
        }
      }
      tag@<(title_fr|title_en)>title</(title_fr|title_en)> -> {
        // Remove bad title
        if(!(`tag.getName()).endsWith(lang)) {
          return `xml(#TEXT(""));
        }
      }
      <person>(p*)</person> -> {
        TNodeList tags = `concTNode();
        %match(TNodeList p) {
          (_*,<firstname>#TEXT(first)</firstname>, _*,<lastname>#TEXT(last)</lastname>,_*) -> {
            String name = `first+" "+`last;
            %match(TNodeList p) {
              //photo attribute is optional
              (_*,<photo>#TEXT(t_photo)</photo>,_*) -> {
                String photo =  "images/trombi/"+`t_photo;
                tags = `concTNode(tags*,<img src=photo alt=name />);
                tags = `concTNode(tags*,<br />);
              }
            }
            //name attribute is mandatory
            tags = `concTNode(tags*,#TEXT(name));
            //homepage attribute is optional
            %match(TNodeList p) {
              (_*,<homepage>#TEXT(t_home)</homepage>,_*) -> {
                String home = `t_home;
                tags = `concTNode(<a href=home>tags*</a>);
              }
            }
          }
        }
        return `xml(<div class="person">tags*</div>);
      }
    }
  }
}
