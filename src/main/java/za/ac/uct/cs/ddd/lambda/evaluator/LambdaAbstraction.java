package za.ac.uct.cs.ddd.lambda.evaluator;

class LambdaAbstraction extends LambdaExpression {
    private LambdaVariable var;
    private LambdaExpression body;

    public LambdaAbstraction(LambdaVariable var, LambdaExpression body) {
        this.var = var;
        this.body = body;
    }

    public String toString() {
        return String.format("(\\%s.%s)", var, body);
    }
}
