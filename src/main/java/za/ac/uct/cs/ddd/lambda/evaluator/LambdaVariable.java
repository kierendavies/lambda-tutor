package za.ac.uct.cs.ddd.lambda.evaluator;

class LambdaVariable extends LambdaExpression {
    private String name;

    public LambdaVariable(String name) {
        this.name = name;
    }

    public LambdaVariable clone() {
        return new LambdaVariable(name);
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
