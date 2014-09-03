package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

/**
 * A representation of an application.
 */
class LambdaApplication extends LambdaExpression {
    private LambdaExpression fn;
    private LambdaExpression body;

    /**
     * Creates a lambda expression with some function on the left applied to a body on the right.
     * @param fn The left side
     * @param body The right side
     */
    public LambdaApplication(LambdaExpression fn, LambdaExpression body) {
        this.fn = fn;
        this.body = body;
    }

    /**
     * Creates a lambda expression from a chain of applications.
     * @param expressions The expressions to be applied in sequence
     */
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
    protected boolean equivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaApplication) {
            LambdaApplication application = (LambdaApplication) expr;
            return application.fn.equivalentTo(fn, depth, depths) && application.body.equivalentTo(body, depth, depths);
        } else {
            return false;
        }
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
