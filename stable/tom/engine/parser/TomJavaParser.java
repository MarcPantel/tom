// $ANTLR 2.7.4: "TomJavaParser.g" -> "TomJavaParser.java"$
/*
 * 
 * TOM - To One Matching Compiler
 * 
 * Copyright (C) 2000-2004 INRIA
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

package jtom.parser;


import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TomJavaParser extends antlr.LLkParser       implements TomJavaParserTokenTypes
 {

  public static TomJavaParser createParser(String fileName) throws FileNotFoundException,IOException {
    File file = new File(fileName);
    TomJavaLexer lexer = new TomJavaLexer(new BufferedReader(new FileReader(file)));
    return new TomJavaParser(lexer);
  }

protected TomJavaParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public TomJavaParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected TomJavaParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public TomJavaParser(TokenStream lexer) {
  this(lexer,1);
}

public TomJavaParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final String  javaPackageDeclaration() throws RecognitionException, TokenStreamException {
		String result;
		
		Token  p = null;
		
		result = "";
		
		
		{
		switch ( LA(1)) {
		case JAVA_PACKAGE:
		{
			p = LT(1);
			match(JAVA_PACKAGE);
			result = p.getText().trim();
			break;
		}
		case EOF:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		return result;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"JAVA_PACKAGE",
		"IGNORE",
		"STRING",
		"WS",
		"COMMENT",
		"SL_COMMENT",
		"ML_COMMENT"
	};
	
	
	}
