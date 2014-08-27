package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.List;

class LambdaApplication extends LambdaExpression {
    private LambdaExpression fn;
    private LambdaExpression body;

    public LambdaApplication(LambdaExpression fn, LambdaExpression body) {
        this.fn = fn;
        this.body = body;
    }

    public LambdaApplication(List<LambdaExpression> expressions) {
        this.body = expressions.get(expressions.size()-1);
        expressions.remove(expressions.size() - 1);

        if (expressions.size() == 1) {
            this.fn = expressions.get(0);
        } else {
            this.fn = new LambdaApplication(expressions);
        }
    }

    public String toString() {
        return String.format("(%s %s)", fn, body);
    }
}
