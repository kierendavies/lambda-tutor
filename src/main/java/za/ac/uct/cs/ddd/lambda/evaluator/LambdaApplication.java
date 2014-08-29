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

    @Override
    public LambdaApplication clone(Scope scope) {
        return new LambdaApplication(fn.clone(scope), body.clone(scope));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (fn instanceof LambdaAbstraction) {
            builder.append('(');
            builder.append(fn);
            builder.append(')');
        } else {
            builder.append(fn);
        }

        builder.append(' ');

        if (body instanceof LambdaVariable) {
            builder.append(body);
        } else {
            builder.append('(');
            builder.append(body);
            builder.append(')');
        }

        return builder.toString();
    }

    @Override
    public String toStringBracketed() {
        return String.format("(%s %s)", fn.toStringBracketed(), body.toStringBracketed());
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = fn.getFreeVariables();
        freeVariables.addAll(body.getFreeVariables());
        return freeVariables;
    }
}
