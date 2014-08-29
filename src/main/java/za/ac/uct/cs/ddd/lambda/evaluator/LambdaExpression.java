package za.ac.uct.cs.ddd.lambda.evaluator;

public abstract class LambdaExpression {
    public abstract LambdaExpression clone();

    @Override
    public abstract String toString();
    public abstract String toStringBracketed();

    abstract void resolveScope(Scope scope);

    void resolveScope() {
        resolveScope(new Scope());
    }

    public abstract Scope getFreeVariables();
}
