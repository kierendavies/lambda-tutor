package za.ac.uct.cs.ddd.lambda.evaluator;

class LambdaApplication extends LambdaExpression {
    private LambdaExpression fn;
    private LambdaExpression body;

    public LambdaApplication(LambdaExpression fn, LambdaExpression body) {
        this.fn = fn;
        this.body = body;
    }

    public String toString() {
        return String.format("(%s %s)", fn, body);
    }
}
