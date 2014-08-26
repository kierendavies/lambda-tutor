package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.OPENING_BRACKET;
import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.CLOSING_BRACKET;

class BracketedExpression extends Token {
    private List<Token> tokens;

    public BracketedExpression() {
        tokens = new ArrayList<Token>();
    }

    /**
     * Given a lexer, populate this bracketed expression by consuming tokens until the correct closing bracket is found
     * or end of stream is reached.  Assumes the opening bracket (if any) has already been consumed, and replaces the
     * closing bracket to be consumed by the caller.
     *
     * @param lexer The lexer from which to consume tokens
     */
    public BracketedExpression(Lexer lexer) throws IOException, MismatchedBracketException {
        this();

        Token nextToken = lexer.next();

        // read until EOF or closing bracket, and add to tokens list
        while (!nextToken.isEOF() && nextToken.getType() != CLOSING_BRACKET) {
            if (nextToken.getType() == OPENING_BRACKET) {
                tokens.add(new BracketedExpression(lexer));
                nextToken = lexer.next();
                if (nextToken.getType() != CLOSING_BRACKET) {  // shouldn't happen
                    throw new MismatchedBracketException("Expected closing bracket at line " + nextToken.getLine() +
                                                         " column " + nextToken.getColumn() +
                                                         ", found " + nextToken);
                }
            } else {
                tokens.add(nextToken);
            }
            nextToken = lexer.next();
        }

        // put that closing bracket back to be consumed by parent
        if (nextToken.getType() == CLOSING_BRACKET) {
            lexer.yypushback(1);
        }

        // remove pointless nesting
        if (tokens.size() == 1 && tokens.get(0) instanceof BracketedExpression) {
            tokens = ((BracketedExpression) tokens.get(0)).tokens;
        }
    }

    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("( ");
        for (Token token : tokens) {
            builder.append(token.toString());
            builder.append(' ');
        }
        builder.append(')');
        return builder.toString();
    }
}
