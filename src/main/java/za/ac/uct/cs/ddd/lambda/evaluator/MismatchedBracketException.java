package za.ac.uct.cs.ddd.lambda.evaluator;

public class MismatchedBracketException extends InvalidExpressionException {
    public MismatchedBracketException() {
    }

    public MismatchedBracketException(String message) {
        super(message);
    }

    public MismatchedBracketException(String message, Throwable cause) {
        super(message, cause);
    }

    public MismatchedBracketException(Throwable cause) {
        super(cause);
    }

    public MismatchedBracketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
