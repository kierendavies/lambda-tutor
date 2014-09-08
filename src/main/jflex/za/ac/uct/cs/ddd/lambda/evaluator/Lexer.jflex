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