package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.io.StringReader;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.*;

public class Parser {
    public static LambdaExpression parse(Lexer lexer) throws IOException, InvalidExpressionException {
        // generate tree of bracketed expressions
        BracketedExpression bracketedExpression = parseBrackets(lexer);

        // check that there are no extra closing brackets
        Token nextToken = lexer.next();
        if (nextToken.getType() != TokenType.END_OF_FILE) {
            throw new MismatchedBracketException("Expected end of expression at line " + nextToken.getLine() +
                                                 " column " + nextToken.getColumn() +
                                                 ", found " + nextToken);
        }

        System.out.println(bracketedExpression);

        LambdaExpression lambdaExpression = parseLambda(bracketedExpression);

        return lambdaExpression;
    }

    public static LambdaExpression parse(String expression) throws InvalidExpressionException {
        try {
            return parse(new Lexer(new StringReader(expression)));
        } catch (IOException e) {  // shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given a lexer, produce a bracketed expression by consuming tokens until the correct closing bracket is found or
     * end of stream is reached.  Assumes the opening bracket (if any) has already been consumed, and replaces the
     * closing bracket to be consumed by the caller.
     *
     * @param lexer The lexer from which to consume tokens
     */
    private static BracketedExpression parseBrackets(Lexer lexer) throws IOException, InvalidExpressionException {
        BracketedExpression be = new BracketedExpression();

        Token nextToken = lexer.next();

        // read until EOF or closing bracket, and add to tokens list
        while (!nextToken.isEOF() && nextToken.getType() != CLOSING_BRACKET) {
            if (nextToken.getType() == OPENING_BRACKET) {
                be.addToken(parseBrackets(lexer));
                nextToken = lexer.next();
                if (nextToken.getType() != CLOSING_BRACKET) {  // shouldn't happen
                    throw new MismatchedBracketException("Expected closing bracket at line " + nextToken.getLine() +
                            " column " + nextToken.getColumn() +
                            ", found " + nextToken);
                }
            } else {
                be.addToken(nextToken);
            }
            nextToken = lexer.next();
        }

        // put that closing bracket back to be consumed by parent
        if (nextToken.getType() == CLOSING_BRACKET) {
            lexer.yypushback(1);
        }

        // remove pointless nesting
        if (be.getTokens().size() == 1 && be.getTokens().get(0) instanceof BracketedExpression) {
            be.hoistOnlyChild();
        }
        for (int i = 0; i < be.getTokens().size(); i++) {
            if (be.getTokens().get(i) instanceof BracketedExpression) {
                BracketedExpression child = (BracketedExpression) be.getTokens().get(i);
                if (child.getTokens().size() == 1) {
                    be.hoistSingletonChild(i);
                }
            }
        }

        return be;
    }

    private static LambdaExpression parseLambda(BracketedExpression bracketedExpression) {
        return null;
    }
}
