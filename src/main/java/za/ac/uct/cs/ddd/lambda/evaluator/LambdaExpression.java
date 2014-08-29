package za.ac.uct.cs.ddd.lambda.evaluator;

public abstract class LambdaExpression {
    protected abstract LambdaExpression clone(Scope scope);
    public LambdaExpression clone() {
        return clone(new Scope());
    }

    @Override
    public abstract String toString();
    public abstract String toStringBracketed();

    public abstract Scope getFreeVariables();

//    public abstract boolean reduceApplicativeOrder();
//    public abstract boolean reduceOnceApplicativeOrder();
//    public abstract boolean reduceNormalOrder();
//    public abstract boolean reduceOnceNormalOrder();
//    public abstract boolean isReduced();
}
