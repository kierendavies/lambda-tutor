package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.*;

/**
 * A representation of an application.
 */
class LambdaApplication extends LambdaExpression {
    LambdaExpression fn;
    LambdaExpression body;

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

    @Override
    public LambdaExpression renameDuplicateVariables(Scope scope) {
        LambdaExpression newFn = fn.renameDuplicateVariables(scope);
        LambdaExpression newBody = body.renameDuplicateVariables(scope);

        if (newFn == fn && newBody == body) {
            return this;
        } else {
            return new LambdaApplication(newFn, newBody);
        }
    }

    @Override
    public LambdaExpression substitute(LambdaVariable variable, LambdaExpression expression) {
        LambdaExpression newFn = fn.substitute(variable, expression);
        LambdaExpression newBody = body.substitute(variable, expression);
        if (newFn == fn && newBody == body) {
            return this;
        } else {
            return new LambdaApplication(newFn, newBody);
        }
    }

    /**
     * Checks if this application is beta-reducible.
     * @return {@code true} if it is beta-reducible; {@code false} otherwise
     */
    private boolean betaReducible() {
        return fn instanceof LambdaAbstraction;
    }

    /**
     * Returns the beta reduction of this abstraction, assuming it is beta-reducible.
     * @return The reduced expression
     */
    private LambdaExpression betaReduce() {
        LambdaAbstraction abstraction = (LambdaAbstraction) fn;
        return abstraction.body.substitute(abstraction.var, body);
    }

    @Override
    public LambdaExpression reduceOnce(ReductionOrder order) {
        if (order == NORMAL && betaReducible()) {
            return betaReduce();
        }

        LambdaExpression newFn = fn.reduceOnce(order);
        if (newFn != fn) {
            return new LambdaApplication(newFn, body);
        }

        LambdaExpression newBody = body.reduceOnce(order);
        if (newBody != body) {
            return new LambdaApplication(fn, newBody);
        }

        if (order == APPLICATIVE && betaReducible()) {
            return betaReduce();
        }

        return this;
    }
}