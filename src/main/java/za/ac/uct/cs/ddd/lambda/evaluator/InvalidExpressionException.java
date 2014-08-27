package za.ac.uct.cs.ddd.lambda.evaluator;

public class InvalidExpressionException extends Exception {
    protected int line, column;

    public InvalidExpressionException() {
    }

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, int line, int column) {
        super(String.format("%s at line %d column %d", message, line, column));
        this.line = line;
        this.column = column;
    }
}
