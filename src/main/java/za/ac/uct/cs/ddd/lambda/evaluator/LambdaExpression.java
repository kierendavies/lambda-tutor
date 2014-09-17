package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static za.ac.uct.cs.ddd.lambda.evaluator.ExpressionType.*;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.*;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.*;

/**
 * A structured representation of a lambda expression.
 */
public abstract class LambdaExpression {
    private static final int defaultMaxIterations = 50;
    protected static final String HIGHLIGHT = "\033[4m";
    protected static final String UNHIGHLIGHT = "\033[0m";

    protected Scope freeVariables = null;

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
     * Indicates whether some other lambda expression is alpha-equivalent to this one.
     * @param expr The expression to compare
     * @return {@code true} if this expression is equivalent to the argument; {@code false} otherwise
     */
    public boolean alphaEquivalentTo(LambdaExpression expr) {
        return alphaEquivalentTo(expr, 0, new HashMap<LambdaVariable, Integer>());
    }

    /**
     * Indicates whether some other lambda expression is equal to this one, up to alpha equivalence, when it occurs at
     * the specified depth of abstractions.
     * @param expr The expression to compare
     * @param depth The current depth in the tree structure
     * @param depths A map of the depths at which variables are declared in both this and the reference expression.
     * @return {@code true} if this expression is equivalent to the argument; {@code false} otherwise
     */
    protected abstract boolean alphaEquivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths);

    /**
     * Returns the string representation, with brackets following the conventional shorthand.
     * @return The string representation
     */
    @Override
    public String toString() {
        return toString(false, null);
    }

    /**
     * Returns the string representation.
     * @param fullBrackets Whether all optional brackets should be included
     * @return The string representation
     */
    public String toString(boolean fullBrackets) {
        return toString(fullBrackets, null);
    }

    /**
     * Returns the string representation.  Uses VT-100 escape sequences to underline a highlighted sub-expression.
     * @param fullBrackets Whether all optional brackets should be included
     * @param highlighted A reference to an expression to highlight; typically a redex
     * @return The string representation
     */
    public String toString(boolean fullBrackets, LambdaExpression highlighted) {
        StringBuilder builder = new StringBuilder();
        buildString(builder, fullBrackets, highlighted);
        return builder.toString();
    }

    protected abstract void buildString(StringBuilder builder, boolean fullBrackets, LambdaExpression highlighted);

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

    protected abstract ReductionResult reduceSubstitute(LambdaVariable variable, LambdaExpression expression);

    /**
     * Performs one reduction, if possible, according to the specified order.
     * @param order The reduction order
     * @return The result containing the reduced expression and reduction type
     */
    public abstract ReductionResult reduceOnce(ReductionOrder order);

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
            expression = expression.reduceOnce(order).reduced;
            iterations++;
        }
        return expression;
    }

    /**
     * Returns a list of iterations of reduction according to the specified order.
     * @param order The reduction order
     * @return The list of reduction results, up to and including the result in normal form, or an empty list if the
     *         expression is already in normal form.
     */
    public List<ReductionResult> reductions(ReductionOrder order) {
        return reductions(order, defaultMaxIterations);
    }

    /**
     * Returns a list of iterations of reduction according to the specified order, not iterating more than the
     * specified number of times.
     * @param order The reduction order
     * @param maxIterations The maximum number of iterations
     * @return The list of reduction results, up to and including the result in normal form, or an empty list if the
     *         expression is already in normal form.
     */
    public List<ReductionResult> reductions(ReductionOrder order, int maxIterations) {
        ArrayList<ReductionResult> results = new ArrayList<ReductionResult>();
        int iterations = 0;
        ReductionResult reduction = this.reduceOnce(order);
        while (reduction.type != NONE && iterations < maxIterations) {
            results.add(reduction);
            reduction = reduction.reduced.reduceOnce(order);
            iterations++;
        }
        return results;
    }

    /**
     * Checks if another lambda expression if extensionally equivalent to this one.  May produce false negatives.
     * @param expr The expression to compare
     * @return {@code true} if this expression is equivalent to the argument; {@code false} otherwise
     */
    public boolean equivalentTo(LambdaExpression expr) {
        // try the easiest approach first
        if (this.alphaEquivalentTo(expr)) {
            return true;
        }

        for (ReductionResult thisReduction : this.reductions(NORMAL)) {
            for (ReductionResult thatReduction : expr.reductions(NORMAL)) {
                if (thisReduction.reduced.alphaEquivalentTo(thatReduction.reduced)) {
                    return true;
                }
            }
        }

        for (ReductionResult thisReduction : this.reductions(APPLICATIVE)) {
            for (ReductionResult thatReduction : expr.reductions(APPLICATIVE)) {
                if (thisReduction.reduced.alphaEquivalentTo(thatReduction.reduced)) {
                    return true;
                }
            }
        }

        return false;
    }

    public ExpressionType getType() {
        if (this instanceof LambdaAbstraction) {
            return ABSTRACTION;
        } else if (this instanceof LambdaApplication) {
            return APPLICATION;
        } else {
            return VARIABLE;
        }
    }
}
