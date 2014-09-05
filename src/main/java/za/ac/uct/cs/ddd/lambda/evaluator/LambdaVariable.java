package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;

/**
 * A representation of a variable.
 */
class LambdaVariable extends LambdaExpression {
    String name;

    /**
     * Creates a new variable with the given name.
     * @param name The name of the variable
     */
    public LambdaVariable(String name) {
        this.name = name;
    }

    @Override
    public LambdaVariable clone(Scope scope) {
        return scope.getOrAddNew(this.name);
    }

    @Override
    protected boolean equivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaVariable) {
            LambdaVariable variable = (LambdaVariable) expr;
            if (depths.containsKey(this)) {
                return depths.containsKey(variable) && depths.get(this).equals(depths.get(variable));
            } else {
                return !depths.containsKey(variable) && variable.name.equals(name);
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toStringBracketed() {
        return name;
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = new Scope();
        freeVariables.add(this);
        return freeVariables;
    }

    @Override
    public LambdaExpression renameDuplicateVariables(Scope scope) {
        return this;
    }

    @Override
    public LambdaExpression substitute(LambdaVariable variable, LambdaExpression expression) {
        if (this == variable) {
            return expression;
        } else {
            return this;
        }
    }

    @Override
    public LambdaExpression reduceOnce(ReductionOrder order) {
        return this;
    }

}
