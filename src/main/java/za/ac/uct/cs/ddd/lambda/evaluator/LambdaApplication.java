package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.*;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.*;

/**
 * A representation of an application.
 */
public class LambdaApplication extends LambdaExpression {
    final LambdaExpression fn;
    final LambdaExpression body;

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

    public LambdaExpression getFunction() {
        return fn;
    }

    public LambdaExpression getInput() {
        return body;
    }

    @Override
    public LambdaApplication clone(Scope scope) {
        return new LambdaApplication(fn.clone(scope), body.clone(scope));
    }

    @Override
    protected boolean alphaEquivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaApplication) {
            LambdaApplication application = (LambdaApplication) expr;
            return application.fn.alphaEquivalentTo(fn, depth, depths) && application.body.alphaEquivalentTo(body, depth, depths);
        } else {
            return false;
        }
    }

    @Override
    protected void buildString(StringBuilder builder, boolean fullBrackets, LambdaExpression highlighted) {
        if (this == highlighted) builder.append(HIGHLIGHT);

        if (fullBrackets) {
            builder.append('(');
            fn.buildString(builder, fullBrackets, highlighted);
            builder.append(' ');
            body.buildString(builder, fullBrackets, highlighted);
            builder.append(')');
        } else {
            if (fn instanceof LambdaAbstraction) {
                builder.append('(');
                fn.buildString(builder, fullBrackets, highlighted);
                builder.append(')');
            } else {
                fn.buildString(builder, fullBrackets, highlighted);
            }
            builder.append(' ');
            if (body instanceof LambdaVariable) {
                body.buildString(builder, fullBrackets, highlighted);
            } else {
                builder.append('(');
                body.buildString(builder, fullBrackets, highlighted);
                builder.append(')');
            }
        }

        if (this == highlighted) builder.append(UNHIGHLIGHT);
    }

    @Override
    public Scope getFreeVariables() {
        if (freeVariables == null) {
            freeVariables = fn.getFreeVariables();
            freeVariables.addAll(body.getFreeVariables());
        }
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

    @Override
    protected ReductionResult reduceSubstitute(LambdaVariable variable, LambdaExpression expression) {
        ReductionResult fnResult = fn.reduceSubstitute(variable, expression);
        if (fnResult.type == ALPHA_CA) {
            LambdaApplication reduced = new LambdaApplication(fnResult.reduced, body);
            return new ReductionResult(this, fnResult.redex, reduced, ALPHA_CA);
        }

        ReductionResult bodyResult = body.reduceSubstitute(variable, expression);
        if (bodyResult.type == ALPHA_CA) {
            LambdaApplication reduced = new LambdaApplication(fn, bodyResult.reduced);
            return new ReductionResult(this, bodyResult.redex, reduced, ALPHA_CA);
        }

        if (fnResult.type != NONE && bodyResult.type != NONE) {
            assert fnResult.type == bodyResult.type;
            LambdaApplication reduced = new LambdaApplication(fnResult.reduced, bodyResult.reduced);
            return new ReductionResult(this, this, reduced, fnResult.type);
        } else if (fnResult.type != NONE) {
            LambdaApplication reduced = new LambdaApplication(fnResult.reduced, body);
            return new ReductionResult(this, fnResult.redex, reduced, fnResult.type);
        } else if (bodyResult.type != NONE) {
            LambdaApplication reduced = new LambdaApplication(fn, bodyResult.reduced);
            return new ReductionResult(this, bodyResult.redex, reduced, bodyResult.type);
        }

        return new ReductionResult(this, null, this, NONE);
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
    private ReductionResult betaReduce() {
        LambdaAbstraction abstraction = (LambdaAbstraction) fn;
        ReductionResult result = abstraction.body.reduceSubstitute(abstraction.var, body);
        if (result.type == ALPHA_CA) {
            LambdaAbstraction newFn = new LambdaAbstraction(abstraction.var, result.reduced);
            LambdaApplication reduced = new LambdaApplication(newFn, body);
            return new ReductionResult(this, result.redex, reduced, ALPHA_CA);
        }

        return new ReductionResult(this, this, result.reduced, BETA);
    }

    @Override
    public ReductionResult reduceOnce(ReductionOrder order) {
        if (order == NORMAL && betaReducible()) {
            return betaReduce();
        }

        ReductionResult fnResult = fn.reduceOnce(order);
        if (fnResult.type != NONE) {
            LambdaExpression reducedExpression = new LambdaApplication(fnResult.reduced, body);
            return new ReductionResult(this, fnResult.redex, reducedExpression, fnResult.type);
        }

        ReductionResult bodyResult = body.reduceOnce(order);
        if (bodyResult.type != NONE) {
            LambdaExpression reducedExpression = new LambdaApplication(fn, bodyResult.reduced);
            return new ReductionResult(this, bodyResult.redex, reducedExpression, bodyResult.type);
        }

        if (order == APPLICATIVE && betaReducible()) {
            return betaReduce();
        }

        return new ReductionResult(this, null, this, NONE);
    }
}
