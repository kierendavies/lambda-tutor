package za.ac.uct.cs.ddd.lambda.evaluator;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.*;

%%

%public
%class Lexer
%type Token
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

LeftBracket = "("
RightBracket = ")"

Lambda = "\\" | "\u039b" | "\u03bb"
Arrow = "->" | "."

Identifier = [:jletter:] [:jletterdigit:]*

%%

{WhiteSpace}    { /* ignore */ }
{LeftBracket}   { return token(LEFT_BRACKET); }
{RightBracket}  { return token(RIGHT_BRACKET); }
{Lambda}        { return token(LAMBDA); }
{Arrow}         { return token(ARROW); }
{Identifier}    { return token(IDENTIFIER, yytext()); }
