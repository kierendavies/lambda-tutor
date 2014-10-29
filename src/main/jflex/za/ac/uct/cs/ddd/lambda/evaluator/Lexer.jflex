/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.ac.uct.cs.ddd.lambda.evaluator;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.*;

%%

%public
%class Lexer
%function next
%type Token
%yylexthrow InvalidExpressionException
%unicode
%line
%column

%{
    private Token token(TokenType type) {
        return new Token(type, yyline, yycolumn);
    }

    private Token token(TokenType type, String content) {
        return new Token(type, yyline, yycolumn, content);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

OpeningBracket = "("
ClosingBracket = ")"

Lambda = "\\" | "\u03bb"
Dot = "." | "->" | "\u2192"

IdentifierStart = [[:letter:]--[\u03bb]] | "_"
IdentifierPart = {IdentifierStart} | [:digit:]
IdentifierEnd = "\u2034"* ("\u2033" | "\u2032")?
Identifier = {IdentifierStart} {IdentifierPart}* {IdentifierEnd}?

%%

{WhiteSpace}        { /* ignore */ }
{OpeningBracket}    { return token(OPENING_BRACKET); }
{ClosingBracket}    { return token(CLOSING_BRACKET); }
{Lambda}            { return token(LAMBDA); }
{Dot}             { return token(DOT); }
{Identifier}        { return token(IDENTIFIER, yytext()); }
<<EOF>>             { return token(END_OF_FILE); }

/* error fallback */
[^]             { throw new InvalidExpressionException("Unexpected character \""+yytext()+"\"", yyline, yycolumn); }