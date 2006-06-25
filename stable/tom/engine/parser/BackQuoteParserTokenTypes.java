// $ANTLR 2.7.6 (20060516): "BackQuoteLanguage.g" -> "BackQuoteParser.java"$
/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (c) 2000-2006, INRIA
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

package tom.engine.parser;
  

public interface BackQuoteParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int BQ_BACKQUOTE = 4;
	int BQ_ID = 5;
	int BQ_STAR = 6;
	int BQ_LPAREN = 7;
	int BQ_RPAREN = 8;
	int BQ_DOT = 9;
	int XML = 10;
	int BQ_COMMA = 11;
	int BQ_STRING = 12;
	int BQ_WS = 13;
	int BQ_INTEGER = 14;
	int BQ_MINUS = 15;
	int DOUBLE_QUOTE = 16;
	int XML_START = 17;
	int XML_EQUAL = 18;
	int XML_CLOSE = 19;
	int ANY = 20;
	int XML_START_ENDING = 21;
	int XML_CLOSE_SINGLETON = 22;
	int XML_TEXT = 23;
	int XML_COMMENT = 24;
	int XML_PROC = 25;
	int XML_SKIP = 26;
	int BQ_SIMPLE_ID = 27;
	int BQ_MINUS_ID = 28;
	int BQ_MINUS_ID_PART = 29;
	int BQ_DIGIT = 30;
	int BQ_UNDERSCORE = 31;
	int BQ_ESC = 32;
	int BQ_HEX_DIGIT = 33;
}
