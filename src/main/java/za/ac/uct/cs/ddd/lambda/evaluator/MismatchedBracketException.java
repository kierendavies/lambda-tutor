package za.ac.uct.cs.ddd.lambda.evaluator;

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
