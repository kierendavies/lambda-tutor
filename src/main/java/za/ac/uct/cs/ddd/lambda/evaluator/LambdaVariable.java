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

    public String getName() {
        return name;
    }

    public boolean equals(LambdaVariable other) {
        return name.equals(other.name);
    }
}
