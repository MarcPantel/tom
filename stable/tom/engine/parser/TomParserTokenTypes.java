// $ANTLR 2.7.7 (20060906): "TomLanguage.g" -> "TomParser.java"$

/*
 *
 * TOM - To One Matching Compiler
 *
 * Copyright (c) 2000-2010, INPL, INRIA
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


public interface TomParserTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int LPAREN = 4;
	int RPAREN = 5;
	int LBRACE = 6;
	int RBRACE = 7;
	int COMMA = 8;
	int BACKQUOTE = 9;
	int ALL_ID = 10;
	int COLON = 11;
	int AND_CONNECTOR = 12;
	int OR_CONNECTOR = 13;
	int ARROW = 14;
	int MATCH_CONSTRAINT = 15;
	int XML_START = 16;
	int LESSOREQUAL_CONSTRAINT = 17;
	int XML_CLOSE = 18;
	int GREATEROREQUAL_CONSTRAINT = 19;
	int DOUBLEEQ = 20;
	int DIFFERENT_CONSTRAINT = 21;
	int EXTENDS = 22;
	int LITERAL_visit = 23;
	int AT = 24;
	int ANTI_SYM = 25;
	int QQMARK = 26;
	int QMARK = 27;
	int STAR = 28;
	int NUM_INT = 29;
	int STRING = 30;
	int XML_CLOSE_SINGLETON = 31;
	int XML_START_ENDING = 32;
	int XML_TEXT = 33;
	int XML_COMMENT = 34;
	int XML_PROC = 35;
	int LBRACKET = 36;
	int RBRACKET = 37;
	int EQUAL = 38;
	int UNDERSCORE = 39;
	int ALTERNATIVE = 40;
	int CHARACTER = 41;
	int NUM_FLOAT = 42;
	int NUM_LONG = 43;
	int NUM_DOUBLE = 44;
	int SUBTYPE_CONSTRAINT = 45;
	int IMPLEMENT = 46;
	int EQUALS = 47;
	int IS_SORT = 48;
	int GET_HEAD = 49;
	int GET_TAIL = 50;
	int IS_EMPTY = 51;
	int GET_ELEMENT = 52;
	int GET_SIZE = 53;
	int IS_FSYM = 54;
	int GET_IMPLEMENTATION = 55;
	int GET_SLOT = 56;
	int MAKE = 57;
	int MAKE_EMPTY = 58;
	int MAKE_INSERT = 59;
	int MAKE_APPEND = 60;
	int WHERE = 61;
	int IF = 62;
	int WHEN = 63;
	int DOULEARROW = 64;
	int AFFECT = 65;
	int DOUBLE_QUOTE = 66;
	int WS = 67;
	int SLCOMMENT = 68;
	int ML_COMMENT = 69;
	int CONSTRAINT_GROUP_START = 70;
	int CONSTRAINT_GROUP_END = 71;
	int ESC = 72;
	int HEX_DIGIT = 73;
	int LETTER = 74;
	int DIGIT = 75;
	int ID = 76;
	int ID_MINUS = 77;
	int MINUS = 78;
	int PLUS = 79;
	int EXPONENT = 80;
	int DOT = 81;
	int FLOAT_SUFFIX = 82;
}
