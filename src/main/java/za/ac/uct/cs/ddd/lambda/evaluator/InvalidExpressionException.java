package za.ac.uct.cs.ddd.lambda.evaluator;

public class InvalidExpressionException extends Exception {
    public InvalidExpressionException() {
        super();
    }

    public InvalidExpressionException(Throwable cause) {
        super(cause);
    }

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
