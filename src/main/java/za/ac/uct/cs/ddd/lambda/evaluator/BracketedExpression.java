package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.BRACKETED_EXPRESSION;

/**
 * A sequence of tokens which occurred between a matched pair of brackets.
 * This class subclasses Token to allow nesting.
 */
class BracketedExpression extends Token {
    private List<Token> tokens;

    /**
     * Creates a bracketed expression.
     * @param line The line at which it started
     * @param column The column at which it started
     */
    public BracketedExpression(int line, int column) {
        super(BRACKETED_EXPRESSION, line, column);
        tokens = new ArrayList<Token>();
    }

    /**
     * Adds a token to the end of this expression.
     * @param token The token to add
     */
    void addToken(Token token) {
        tokens.add(token);
    }

    /**
     * Returns the tokens in an unmodifiable list.
     * @return The unmodifiable list of tokens
     */
    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    /**
     * Sets the list of tokens to be the first child's list of tokens.  This flattens the tree structure to make it more
     * efficient when there is only one child token.
     */
    void hoistOnlyChild() {
        if (tokens.size() > 1) {
            throw new RuntimeException("More than one child");
        }
        tokens = ((BracketedExpression) tokens.get(0)).tokens;
    }

    /**
     * Replaces a token which is a BracketedExpression with its contents.  This flattens the tree structure to make it
     * more efficient when the nested expression contains only one token.
     * @param index
     */
    void hoistSingletonChild(int index) {
        if (!(tokens.get(index) instanceof BracketedExpression)) {
            throw new RuntimeException("Child is not a bracketed expression");
        }
        tokens.set(index, ((BracketedExpression) tokens.get(index)).getTokens().get(0));
    }

    /**
     * Returns a string representation of this bracketed expression.
     * @return The string representation.
     */
    @Override
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
