package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

/**
 * A representation of a lambda abstraction.
 */
class LambdaAbstraction extends LambdaExpression {
    private LambdaVariable var;
    private LambdaExpression body;

    /**
     * Creates a lambda abstraction with a variable and body.
     * @param var The variable
     * @param body The body
     */
    public LambdaAbstraction(LambdaVariable var, LambdaExpression body) {
        this.var = var;
        this.body = body;
    }

    /**
     * Creates a lambda abstraction of many variables.
     * @param vars The list of variables
     * @param body The body
     */
    public LambdaAbstraction(List<LambdaVariable> vars, LambdaExpression body) {
        this.var = vars.get(0);
        if (vars.size() == 1) {
            this.body = body;
        } else {
            vars.remove(0);
            this.body = new LambdaAbstraction(vars, body);
        }
    }

    @Override
    public LambdaAbstraction clone(Scope scope) {
        scope.add(var);
        LambdaAbstraction cloned = new LambdaAbstraction(var.clone(scope), body.clone(scope));
        scope.remove(var);
        return cloned;
    }

    @Override
    protected boolean equivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaAbstraction) {
            LambdaAbstraction abstraction = (LambdaAbstraction) expr;
            depths.put(var, depth);
            depths.put(abstraction.var, depth);
            return body.equivalentTo(abstraction.body, depth+1, depths);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("\u03bb%s.%s", var, body);
    }

    @Override
    public String toStringBracketed() {
        return String.format("(\u03bb%s.%s)", var.toStringBracketed(), body.toStringBracketed());
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = body.getFreeVariables();
        freeVariables.remove(var);
        return freeVariables;
    }

    @Override
    protected void substitute(LambdaVariable variable, LambdaExpression expression) {
        if (body == variable) {
            body = expression;
        } else {
            body.substitute(variable, expression);
        }
    }
}
