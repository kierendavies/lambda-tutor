package za.ac.uct.cs.ddd.lambda.evaluator;

/**
 * A structured representation of a lambda expression.
 */
public abstract class LambdaExpression {
    /**
     * Creates a deep copy of this lambda expression, with variables taken from the given scope.
     * @param scope The variable scope
     * @return A new lambda expression
     */
    protected abstract LambdaExpression clone(Scope scope);

    /**
     * Create a deep copy of this lambda expression.
     * @return A new lambda expression
     */
    public LambdaExpression clone() {
        return clone(new Scope());
    }

    /**
     * Returns the string representation, with brackets following the conventional shorthand.
     * @return The string representation
     */
    @Override
    public abstract String toString();

    /**
     * Returns the string representation, with all optional brackets.
     * @return The string representation
     */
    public abstract String toStringBracketed();

    /**
     * Finds all free variables in this expression.
     * @return A Scope containing all the free variables
     */
    public abstract Scope getFreeVariables();

//    public abstract boolean reduceApplicativeOrder();
//    public abstract boolean reduceOnceApplicativeOrder();
//    public abstract boolean reduceNormalOrder();
//    public abstract boolean reduceOnceNormalOrder();
//    public abstract boolean isReduced();
}
