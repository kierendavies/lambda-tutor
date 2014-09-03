package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;

/**
 * A representation of a variable.
 */
class LambdaVariable extends LambdaExpression {
    private String name;

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
    protected void renameShadowedVariables(Scope scope) {
        // do nothing
    }

    @Override
    protected void substitute(LambdaVariable variable, LambdaExpression expression) {
        // do nothing
    }

    /**
     * Returns the name of the variable.
     * @return The name of the variable
     */
    public String getName() {
        return name;
    }

    /**
     * Renames the variable.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
