/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2012, INPL, INRIA
 * Nancy, France.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 * Pierre-Etienne Moreau  e-mail: Pierre-Etienne.Moreau@loria.fr
 *
 **/

package tom.platform;

import java.io.*;

import aterm.*;
import aterm.pure.*;

import tom.library.adt.tnode.*;
import tom.library.adt.tnode.types.*;
import tom.platform.adt.platformoption.*;
import tom.platform.adt.platformoption.types.*;
import tom.library.xml.*;

/**
 * Helper class to parse OptionOwner options.
 * The options have to comply with the following this DTD
 *
 * <PRE><CODE>
 * < !ELEMENT options (boolean*,integer*,string*) >
 *
 * < !ELEMENT boolean EMPTY >
 * < !ATTLIST boolean
 *   name CDATA #REQUIRED
 *   altName CDATA ""
 *   description CDATA ""
 *   value (true|false) #REQUIRED >
 *
 * < !ELEMENT integer EMPTY >
 * < !ATTLIST integer
 *   name CDATA #REQUIRED
 *   altName CDATA ""
 *   description CDATA ""
 *   value CDATA #REQUIRED
 *   attrName CDATA #REQUIRED >
 *
 * < !ELEMENT string EMPTY >
 * < !ATTLIST string
 *   name CDATA #REQUIRED
 *   altName CDATA ""
 *   description CDATA ""
 *   value CDATA #REQUIRED
 *   attrName CDATA #REQUIRED >
 * </CODE></PRE>
 */
public class OptionParser {

       private static   tom.library.adt.tnode.types.TNodeList  tom_append_list_concTNode( tom.library.adt.tnode.types.TNodeList l1,  tom.library.adt.tnode.types.TNodeList  l2) {     if( l1.isEmptyconcTNode() ) {       return l2;     } else if( l2.isEmptyconcTNode() ) {       return l1;     } else if(  l1.getTailconcTNode() .isEmptyconcTNode() ) {       return  tom.library.adt.tnode.types.tnodelist.ConsconcTNode.make( l1.getHeadconcTNode() ,l2) ;     } else {       return  tom.library.adt.tnode.types.tnodelist.ConsconcTNode.make( l1.getHeadconcTNode() ,tom_append_list_concTNode( l1.getTailconcTNode() ,l2)) ;     }   }   private static   tom.library.adt.tnode.types.TNodeList  tom_get_slice_concTNode( tom.library.adt.tnode.types.TNodeList  begin,  tom.library.adt.tnode.types.TNodeList  end, tom.library.adt.tnode.types.TNodeList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcTNode()  ||  (end== tom.library.adt.tnode.types.tnodelist.EmptyconcTNode.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.library.adt.tnode.types.tnodelist.ConsconcTNode.make( begin.getHeadconcTNode() ,( tom.library.adt.tnode.types.TNodeList )tom_get_slice_concTNode( begin.getTailconcTNode() ,end,tail)) ;   }        private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_append_list_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList l1,  tom.platform.adt.platformoption.types.PlatformOptionList  l2) {     if( l1.isEmptyconcPlatformOption() ) {       return l2;     } else if( l2.isEmptyconcPlatformOption() ) {       return l1;     } else if(  l1.getTailconcPlatformOption() .isEmptyconcPlatformOption() ) {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,l2) ;     } else {       return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( l1.getHeadconcPlatformOption() ,tom_append_list_concPlatformOption( l1.getTailconcPlatformOption() ,l2)) ;     }   }   private static   tom.platform.adt.platformoption.types.PlatformOptionList  tom_get_slice_concPlatformOption( tom.platform.adt.platformoption.types.PlatformOptionList  begin,  tom.platform.adt.platformoption.types.PlatformOptionList  end, tom.platform.adt.platformoption.types.PlatformOptionList  tail) {     if( (begin==end) ) {       return tail;     } else if( (end==tail)  && ( end.isEmptyconcPlatformOption()  ||  (end== tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) )) {       /* code to avoid a call to make, and thus to avoid looping during list-matching */       return begin;     }     return  tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( begin.getHeadconcPlatformOption() ,( tom.platform.adt.platformoption.types.PlatformOptionList )tom_get_slice_concPlatformOption( begin.getTailconcPlatformOption() ,end,tail)) ;   }    



  /**
   * An XMLTools for doing the stuff
   */
    // non static XmlTools
  //private static XmlTools xtools = new XmlTools();

  /**
   * @return a PlatformOptionList extracted from the a String
   */
  public static PlatformOptionList xmlToOptionList(String xmlString) {
    InputStream stream = new ByteArrayInputStream(xmlString.getBytes());
    // non static XmlTools
    XmlTools xtools = new XmlTools();
    TNode node = xtools.convertXMLToTNode(stream);
    return xmlNodeToOptionList(node.getDocElem());
  }

  /**
   * @return a PlatformOptionList extracted from a TNode
   */
  public static PlatformOptionList xmlNodeToOptionList(TNode optionsNode) {
    PlatformOptionList list =  tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ;
    {{if ( (((Object)optionsNode) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )((Object)optionsNode)) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )(( tom.library.adt.tnode.types.TNode )((Object)optionsNode))) instanceof tom.library.adt.tnode.types.tnode.ElementNode) ) { String  tomMatch725_1= (( tom.library.adt.tnode.types.TNode )((Object)optionsNode)).getName() ; tom.library.adt.tnode.types.TNodeList  tomMatch725_3= (( tom.library.adt.tnode.types.TNode )((Object)optionsNode)).getChildList() ;if ( true ) {if ( "options".equals(tomMatch725_1) ) {if ( (((( tom.library.adt.tnode.types.TNodeList ) (( tom.library.adt.tnode.types.TNode )((Object)optionsNode)).getAttrList() ) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList ) (( tom.library.adt.tnode.types.TNode )((Object)optionsNode)).getAttrList() ) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch725_3) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch725_3) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) { tom.library.adt.tnode.types.TNodeList  tomMatch725__end__13=tomMatch725_3;do {{if (!( tomMatch725__end__13.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tom_option= tomMatch725__end__13.getHeadconcTNode() ;{{if ( (((Object)tom_option) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )((Object)tom_option)) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )(( tom.library.adt.tnode.types.TNode )((Object)tom_option))) instanceof tom.library.adt.tnode.types.tnode.ElementNode) ) { String  tomMatch726_1= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getName() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_2= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getAttrList() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_3= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getChildList() ;if ( true ) {if ( "boolean".equals(tomMatch726_1) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_2) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_2) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726__end__11=tomMatch726_2;do {{if (!( tomMatch726__end__11.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_30= tomMatch726__end__11.getHeadconcTNode() ;if ( (tomMatch726_30 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_30) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_27= tomMatch726_30.getName() ;if ( true ) {if ( "altName".equals(tomMatch726_27) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_12= tomMatch726__end__11.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__15=tomMatch726_12;do {{if (!( tomMatch726__end__15.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_37= tomMatch726__end__15.getHeadconcTNode() ;if ( (tomMatch726_37 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_37) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_34= tomMatch726_37.getName() ;if ( true ) {if ( "description".equals(tomMatch726_34) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_16= tomMatch726__end__15.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__19=tomMatch726_16;do {{if (!( tomMatch726__end__19.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_44= tomMatch726__end__19.getHeadconcTNode() ;if ( (tomMatch726_44 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_44) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_41= tomMatch726_44.getName() ;if ( true ) {if ( "name".equals(tomMatch726_41) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_20= tomMatch726__end__19.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__23=tomMatch726_20;do {{if (!( tomMatch726__end__23.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_51= tomMatch726__end__23.getHeadconcTNode() ;if ( (tomMatch726_51 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_51) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_48= tomMatch726_51.getName() ;if ( true ) {if ( "value".equals(tomMatch726_48) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_3) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_3) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) {if ( tomMatch726_3.isEmptyconcTNode() ) {



            PlatformBoolean bool = Boolean.valueOf( tomMatch726_51.getValue() ).booleanValue()? tom.platform.adt.platformoption.types.platformboolean.True.make() : tom.platform.adt.platformoption.types.platformboolean.False.make() ;
            list = tom_append_list_concPlatformOption(list, tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make( tomMatch726_44.getValue() ,  tomMatch726_30.getValue() ,  tomMatch726_37.getValue() ,  tom.platform.adt.platformoption.types.platformvalue.BooleanValue.make(bool) , "") , tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) );
          }}}}}}}if ( tomMatch726__end__23.isEmptyconcTNode() ) {tomMatch726__end__23=tomMatch726_20;} else {tomMatch726__end__23= tomMatch726__end__23.getTailconcTNode() ;}}} while(!( (tomMatch726__end__23==tomMatch726_20) ));}}}}}if ( tomMatch726__end__19.isEmptyconcTNode() ) {tomMatch726__end__19=tomMatch726_16;} else {tomMatch726__end__19= tomMatch726__end__19.getTailconcTNode() ;}}} while(!( (tomMatch726__end__19==tomMatch726_16) ));}}}}}if ( tomMatch726__end__15.isEmptyconcTNode() ) {tomMatch726__end__15=tomMatch726_12;} else {tomMatch726__end__15= tomMatch726__end__15.getTailconcTNode() ;}}} while(!( (tomMatch726__end__15==tomMatch726_12) ));}}}}}if ( tomMatch726__end__11.isEmptyconcTNode() ) {tomMatch726__end__11=tomMatch726_2;} else {tomMatch726__end__11= tomMatch726__end__11.getTailconcTNode() ;}}} while(!( (tomMatch726__end__11==tomMatch726_2) ));}}}}}}}{if ( (((Object)tom_option) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )((Object)tom_option)) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )(( tom.library.adt.tnode.types.TNode )((Object)tom_option))) instanceof tom.library.adt.tnode.types.tnode.ElementNode) ) { String  tomMatch726_56= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getName() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_57= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getAttrList() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_58= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getChildList() ;if ( true ) {if ( "integer".equals(tomMatch726_56) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_57) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_57) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726__end__66=tomMatch726_57;do {{if (!( tomMatch726__end__66.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_89= tomMatch726__end__66.getHeadconcTNode() ;if ( (tomMatch726_89 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_89) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_86= tomMatch726_89.getName() ;if ( true ) {if ( "altName".equals(tomMatch726_86) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_67= tomMatch726__end__66.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__70=tomMatch726_67;do {{if (!( tomMatch726__end__70.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_96= tomMatch726__end__70.getHeadconcTNode() ;if ( (tomMatch726_96 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_96) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_93= tomMatch726_96.getName() ;if ( true ) {if ( "attrName".equals(tomMatch726_93) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_71= tomMatch726__end__70.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__74=tomMatch726_71;do {{if (!( tomMatch726__end__74.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_103= tomMatch726__end__74.getHeadconcTNode() ;if ( (tomMatch726_103 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_103) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_100= tomMatch726_103.getName() ;if ( true ) {if ( "description".equals(tomMatch726_100) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_75= tomMatch726__end__74.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__78=tomMatch726_75;do {{if (!( tomMatch726__end__78.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_110= tomMatch726__end__78.getHeadconcTNode() ;if ( (tomMatch726_110 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_110) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_107= tomMatch726_110.getName() ;if ( true ) {if ( "name".equals(tomMatch726_107) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_79= tomMatch726__end__78.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__82=tomMatch726_79;do {{if (!( tomMatch726__end__82.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_117= tomMatch726__end__82.getHeadconcTNode() ;if ( (tomMatch726_117 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_117) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_114= tomMatch726_117.getName() ;if ( true ) {if ( "value".equals(tomMatch726_114) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_58) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_58) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) {if ( tomMatch726_58.isEmptyconcTNode() ) {

            list = tom_append_list_concPlatformOption(list, tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make( tomMatch726_110.getValue() ,  tomMatch726_89.getValue() ,  tomMatch726_103.getValue() ,  tom.platform.adt.platformoption.types.platformvalue.IntegerValue.make(Integer.parseInt( tomMatch726_117.getValue() )) ,  tomMatch726_96.getValue() ) , tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) );
          }}}}}}}if ( tomMatch726__end__82.isEmptyconcTNode() ) {tomMatch726__end__82=tomMatch726_79;} else {tomMatch726__end__82= tomMatch726__end__82.getTailconcTNode() ;}}} while(!( (tomMatch726__end__82==tomMatch726_79) ));}}}}}if ( tomMatch726__end__78.isEmptyconcTNode() ) {tomMatch726__end__78=tomMatch726_75;} else {tomMatch726__end__78= tomMatch726__end__78.getTailconcTNode() ;}}} while(!( (tomMatch726__end__78==tomMatch726_75) ));}}}}}if ( tomMatch726__end__74.isEmptyconcTNode() ) {tomMatch726__end__74=tomMatch726_71;} else {tomMatch726__end__74= tomMatch726__end__74.getTailconcTNode() ;}}} while(!( (tomMatch726__end__74==tomMatch726_71) ));}}}}}if ( tomMatch726__end__70.isEmptyconcTNode() ) {tomMatch726__end__70=tomMatch726_67;} else {tomMatch726__end__70= tomMatch726__end__70.getTailconcTNode() ;}}} while(!( (tomMatch726__end__70==tomMatch726_67) ));}}}}}if ( tomMatch726__end__66.isEmptyconcTNode() ) {tomMatch726__end__66=tomMatch726_57;} else {tomMatch726__end__66= tomMatch726__end__66.getTailconcTNode() ;}}} while(!( (tomMatch726__end__66==tomMatch726_57) ));}}}}}}}{if ( (((Object)tom_option) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )((Object)tom_option)) instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )(( tom.library.adt.tnode.types.TNode )((Object)tom_option))) instanceof tom.library.adt.tnode.types.tnode.ElementNode) ) { String  tomMatch726_122= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getName() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_123= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getAttrList() ; tom.library.adt.tnode.types.TNodeList  tomMatch726_124= (( tom.library.adt.tnode.types.TNode )((Object)tom_option)).getChildList() ;if ( true ) {if ( "string".equals(tomMatch726_122) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_123) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_123) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726__end__132=tomMatch726_123;do {{if (!( tomMatch726__end__132.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_155= tomMatch726__end__132.getHeadconcTNode() ;if ( (tomMatch726_155 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_155) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_152= tomMatch726_155.getName() ;if ( true ) {if ( "altName".equals(tomMatch726_152) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_133= tomMatch726__end__132.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__136=tomMatch726_133;do {{if (!( tomMatch726__end__136.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_162= tomMatch726__end__136.getHeadconcTNode() ;if ( (tomMatch726_162 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_162) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_159= tomMatch726_162.getName() ;if ( true ) {if ( "attrName".equals(tomMatch726_159) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_137= tomMatch726__end__136.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__140=tomMatch726_137;do {{if (!( tomMatch726__end__140.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_169= tomMatch726__end__140.getHeadconcTNode() ;if ( (tomMatch726_169 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_169) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_166= tomMatch726_169.getName() ;if ( true ) {if ( "description".equals(tomMatch726_166) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_141= tomMatch726__end__140.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__144=tomMatch726_141;do {{if (!( tomMatch726__end__144.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_176= tomMatch726__end__144.getHeadconcTNode() ;if ( (tomMatch726_176 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_176) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_173= tomMatch726_176.getName() ;if ( true ) {if ( "name".equals(tomMatch726_173) ) { tom.library.adt.tnode.types.TNodeList  tomMatch726_145= tomMatch726__end__144.getTailconcTNode() ; tom.library.adt.tnode.types.TNodeList  tomMatch726__end__148=tomMatch726_145;do {{if (!( tomMatch726__end__148.isEmptyconcTNode() )) { tom.library.adt.tnode.types.TNode  tomMatch726_183= tomMatch726__end__148.getHeadconcTNode() ;if ( (tomMatch726_183 instanceof tom.library.adt.tnode.types.TNode) ) {if ( ((( tom.library.adt.tnode.types.TNode )tomMatch726_183) instanceof tom.library.adt.tnode.types.tnode.AttributeNode) ) { String  tomMatch726_180= tomMatch726_183.getName() ;if ( true ) {if ( "value".equals(tomMatch726_180) ) {if ( (((( tom.library.adt.tnode.types.TNodeList )tomMatch726_124) instanceof tom.library.adt.tnode.types.tnodelist.ConsconcTNode) || ((( tom.library.adt.tnode.types.TNodeList )tomMatch726_124) instanceof tom.library.adt.tnode.types.tnodelist.EmptyconcTNode)) ) {if ( tomMatch726_124.isEmptyconcTNode() ) {

            list = tom_append_list_concPlatformOption(list, tom.platform.adt.platformoption.types.platformoptionlist.ConsconcPlatformOption.make( tom.platform.adt.platformoption.types.platformoption.PluginOption.make( tomMatch726_176.getValue() ,  tomMatch726_155.getValue() ,  tomMatch726_169.getValue() ,  tom.platform.adt.platformoption.types.platformvalue.StringValue.make( tomMatch726_183.getValue() ) ,  tomMatch726_162.getValue() ) , tom.platform.adt.platformoption.types.platformoptionlist.EmptyconcPlatformOption.make() ) );
          }}}}}}}if ( tomMatch726__end__148.isEmptyconcTNode() ) {tomMatch726__end__148=tomMatch726_145;} else {tomMatch726__end__148= tomMatch726__end__148.getTailconcTNode() ;}}} while(!( (tomMatch726__end__148==tomMatch726_145) ));}}}}}if ( tomMatch726__end__144.isEmptyconcTNode() ) {tomMatch726__end__144=tomMatch726_141;} else {tomMatch726__end__144= tomMatch726__end__144.getTailconcTNode() ;}}} while(!( (tomMatch726__end__144==tomMatch726_141) ));}}}}}if ( tomMatch726__end__140.isEmptyconcTNode() ) {tomMatch726__end__140=tomMatch726_137;} else {tomMatch726__end__140= tomMatch726__end__140.getTailconcTNode() ;}}} while(!( (tomMatch726__end__140==tomMatch726_137) ));}}}}}if ( tomMatch726__end__136.isEmptyconcTNode() ) {tomMatch726__end__136=tomMatch726_133;} else {tomMatch726__end__136= tomMatch726__end__136.getTailconcTNode() ;}}} while(!( (tomMatch726__end__136==tomMatch726_133) ));}}}}}if ( tomMatch726__end__132.isEmptyconcTNode() ) {tomMatch726__end__132=tomMatch726_123;} else {tomMatch726__end__132= tomMatch726__end__132.getTailconcTNode() ;}}} while(!( (tomMatch726__end__132==tomMatch726_123) ));}}}}}}}}

      }if ( tomMatch725__end__13.isEmptyconcTNode() ) {tomMatch725__end__13=tomMatch725_3;} else {tomMatch725__end__13= tomMatch725__end__13.getTailconcTNode() ;}}} while(!( (tomMatch725__end__13==tomMatch725_3) ));}}}}}}}}}

    return list;
  }

}
