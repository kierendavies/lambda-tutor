package za.ac.uct.cs.ddd.lambda.evaluator;

public abstract class LambdaExpression {
    @Override
    public abstract String toString();

    abstract void resolveScope(Scope scope);

    void resolveScope() {
        resolveScope(new Scope());
    }
}
