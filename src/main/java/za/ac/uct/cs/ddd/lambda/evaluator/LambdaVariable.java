package za.ac.uct.cs.ddd.lambda.evaluator;

class LambdaVariable extends LambdaExpression {
    private String name;

    public LambdaVariable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "@" + Integer.toHexString(hashCode());
    }

    @Override
    void resolveScope(Scope scope) {
        // do nothing
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = new Scope();
        freeVariables.add(this);
        return freeVariables;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LambdaExpression && ((LambdaVariable) obj).getName().equals(name);
    }
}
