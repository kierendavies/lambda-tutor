package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A structured representation of a lambda expression.
 */
public abstract class LambdaExpression {
    private static final int defaultMaxIterations = 50;

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
    public LambdaExpression renameDuplicateVariables() {
        return renameDuplicateVariables(getFreeVariables());
    }

    /**
     * Renames all variables which shadow others with the same name so that there are no possible conflicts.
     * @param scope Variables that have already occurred.
     */
    public abstract LambdaExpression renameDuplicateVariables(Scope scope);

    /**
     * Substitute all occurrences of a variable with another expression.
     * @param variable The variable to substitute
     * @param expression The expression with which to substitute the variable
     * @return The new expression
     */
    public abstract LambdaExpression substitute(LambdaVariable variable, LambdaExpression expression);

    /**
     * Substitute all occurrences of a named variable with another expression.
     * @param variableName The name of the variable to substitute
     * @param expression The expression with which to substitute the variable
     * @return The new expression
     */
    public LambdaExpression substitute(String variableName, LambdaExpression expression) {
        return substitute(getFreeVariables().get(variableName), expression);
    }

    /**
     * Substitute all occurrences of some named variables with corresponding expressions.
     * @param substitutions A map from variable names to expressions with which to substitute
     * @return The new expression
     */
    public LambdaExpression substituteAll(Map<String, LambdaExpression> substitutions) {
        Scope freeVariables = getFreeVariables();
        LambdaExpression newExpr = this;
        for (String name : substitutions.keySet()) {
            if (freeVariables.contains(name)) {
                newExpr = newExpr.substitute(freeVariables.get(name), substitutions.get(name));
            }
        }
        return newExpr;
    }

    /**
     * Performs one reduction, if possible, according to the specified order.
     * @param order The reduction order
     * @return The reduced expression
     */
    public abstract LambdaExpression reduceOnce(ReductionOrder order);

    /**
     * Reduces the lambda expression as much as possible.
     * @param order The reduction order
     * @return The reduced expression
     */
    public LambdaExpression reduce(ReductionOrder order) {
        return reduce(order, defaultMaxIterations);
    }

    /**
     * Reduces the lambda expression as much as possible according to the specified order, not iterating more than the
     * specified number of times.
     * @param order The reduction order
     * @param maxIterations The maximum number of iterations
     * @return The reduced expression
     */
    public LambdaExpression reduce(ReductionOrder order, int maxIterations) {
        int iterations = 0;
        LambdaExpression expression = this;
        LambdaExpression last = null;
        while (expression != last) {
            if (iterations == maxIterations) {
                return this;
            }
            last = expression;
            expression = expression.reduceOnce(order);
            iterations++;
        }
        return expression;
    }

    /**
     * Returns a list of iterations of reduction according to the specified order.
     * @param order The reduction order
     * @return The list of expressions, beginning with the original and ending with the fully reduced expression
     */
    public List<LambdaExpression> reductions(ReductionOrder order) {
        return reductions(order, defaultMaxIterations);
    }

    /**
     * Returns a list of iterations of reduction according to the specified order, not iterating more than the
     * specified number of times.
     * @param order The reduction order
     * @param maxIterations The maximum number of iterations
     * @return The list of expressions, beginning with the original and ending with the fully reduced expression
     */
    public List<LambdaExpression> reductions(ReductionOrder order, int maxIterations) {
        ArrayList<LambdaExpression> results = new ArrayList<LambdaExpression>();
        int iterations = 0;
        LambdaExpression expression = this;
        LambdaExpression last = null;
        while (expression != last && iterations < maxIterations) {
            results.add(expression);
            last = expression;
            expression = expression.reduceOnce(order);
            iterations++;
        }
        return results;
    }
}