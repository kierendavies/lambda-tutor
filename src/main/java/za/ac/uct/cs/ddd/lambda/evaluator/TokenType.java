package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * The types of supported tokens.
 */
public enum TokenType {
    LAMBDA,
    ARROW,
    IDENTIFIER,
    OPENING_BRACKET,
    CLOSING_BRACKET,
    END_OF_FILE,
    BRACKETED_EXPRESSION
}