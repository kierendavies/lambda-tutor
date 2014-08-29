package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * Indicates that an expression has mismatched brackets.
 */
public class MismatchedBracketException extends InvalidExpressionException {
    public MismatchedBracketException() {
    }

    public MismatchedBracketException(String message) {
        super(message);
    }

    public MismatchedBracketException(String message, int line, int column) {
        super(message, line, column);
    }
}
