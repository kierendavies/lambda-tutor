package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * Indicates that an expression is not valid in terms of the definition of lambda calculus.
 */
public class InvalidExpressionException extends Exception {
    protected int line, column;

    public InvalidExpressionException() {
    }

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
