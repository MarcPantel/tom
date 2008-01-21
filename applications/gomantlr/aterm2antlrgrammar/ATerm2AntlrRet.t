/*
 *
 * Gomantlr
 *
 * Copyright (c) 2006-2008, INRIA
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
 * Eric Deplagne <Eric.Deplagne@loria.fr>
 *
 **/

package aterm2antlrgrammar;

import aterm.*;
import aterm.pure.*;

import antlrgrammar.antlrcommons.types.AntlrRet;

import aterm2antlrgrammar.exceptions.AntlrWrongRetException;

public class ATerm2AntlrRet {
   
    %include { ../antlrgrammar/AntlrGrammar.tom }
    %include { ../antlr.tom }

    public static AntlrRet getAntlrRet(ATerm t) throws AntlrWrongRetException {
        %match(t) {
            RET(_,concATerm()) -> {
                return `AntlrNilRet();
            }
            RET(_,x@concATerm(_,_*)) -> {
                return `AntlrRet(ATerm2AntlrUnrecognized.getAntlrUnrecognized(x));
            }
        }
        throw new AntlrWrongRetException(ATerm2AntlrWrong.getAntlrWrong(t));
    }
}
