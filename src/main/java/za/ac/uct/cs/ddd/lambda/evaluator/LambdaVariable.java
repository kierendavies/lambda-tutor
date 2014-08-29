package za.ac.uct.cs.ddd.lambda.evaluator;

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

    /**
     * Returns the name of the variable.
     * @return The name of the variable
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LambdaExpression && ((LambdaVariable) obj).getName().equals(name);
    }
}
