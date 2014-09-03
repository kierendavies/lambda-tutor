package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;

/**
 * A structured representation of a lambda expression.
 */
public abstract class LambdaExpression {
    /**
     * Creates a deep copy of this lambda expression.
     * @return A new lambda expression
     */
    public LambdaExpression clone() {
        return clone(new Scope());
    }

    /**
     * Creates a deep copy of this lambda expression, with variables taken from the given scope.
     * @param scope The variable scope
     * @return A new lambda expression
     */
    protected abstract LambdaExpression clone(Scope scope);

    /**
     * Indicates whether some other lambda expression is equal to this one, up to alpha equivalence.
     * @param expr The expression to compare
     * @return {@code true} if this expression is equivalent to the argument; {@code false} otherwise
     */
    public boolean equivalentTo(LambdaExpression expr) {
        return equivalentTo(expr, 0, new HashMap<LambdaVariable, Integer>());
    }

    /**
     * Indicates whether some other lambda expression is equal to this one, up to alpha equivalence, when it occurs at
     * the specified depth of abstractions.
     * @param expr The expression to compare
     * @param depth The current depth in the tree structure
     * @param depths A map of the depths at which variables are declared in both this and the reference expression.
     * @return {@code true} if this expression is equivalent to the argument; {@code false} otherwise
     */
    protected abstract boolean equivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths);

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

    /**
     * Renames all variables which shadow others with the same name so that there are no possible conflicts.  This is
     * done by appending prime symbols to the names.
     */
    public void renameShadowedVariables() {
        renameShadowedVariables(getFreeVariables());
    }

    /**
     * Renames all variables which shadow others with the same name so that there are no possible conflicts.
     * @param scope Variables that have already occurred.
     */
    protected abstract void renameShadowedVariables(Scope scope);

    /**
     * Substitute all occurences of a variable with another expression.
     * @param variable The variable to substitute
     * @param expression The expression with which to substitute the variable
     */
    protected abstract void substitute(LambdaVariable variable, LambdaExpression expression);

//    public abstract boolean reduceApplicativeOrder();
//    public abstract boolean reduceOnceApplicativeOrder();
//    public abstract boolean reduceNormalOrder();
//    public abstract boolean reduceOnceNormalOrder();
//    public abstract boolean isReduced();
}
