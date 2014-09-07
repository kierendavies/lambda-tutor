package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.*;

/**
 * A representation of a lambda abstraction.
 */
class LambdaAbstraction extends LambdaExpression {
    LambdaVariable var;
    LambdaExpression body;

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
    public LambdaExpression renameDuplicateVariables(Scope scope) {
        LambdaVariable newVar;
        LambdaExpression newBody;
        if (scope.contains(var.name)) {
            String newName = Util.nextVariableName(var.name);
            while (scope.contains(newName)) {
                newName = Util.nextVariableName(newName);
            }
            newVar = new LambdaVariable(newName);
            newBody = body.substitute(var, newVar);
        } else {
            newVar = var;
            newBody = body;
        }

        scope.add(newVar);
        newBody = newBody.renameDuplicateVariables(scope);

        if (newVar == var && newBody == body) {
            return this;
        } else {
            return new LambdaAbstraction(newVar, newBody);
        }
    }

    @Override
    public LambdaExpression substitute(LambdaVariable variable, LambdaExpression expression) {
        Scope freeVariables = expression.getFreeVariables();
        if (freeVariables.contains(var.name)) {
            // rename this variable to avoid capture
            String newName = Util.nextVariableName(var.name);
            freeVariables.addAll(body.getFreeVariables());
            while (freeVariables.contains(newName)) {
                newName = Util.nextVariableName(newName);
            }
            LambdaVariable newVar = new LambdaVariable(newName);
            return new LambdaAbstraction(newVar, body.substitute(var, newVar).substitute(variable, expression));
        } else {
            LambdaExpression newBody = body.substitute(variable, expression);
            if (newBody == body) {
                return this;
            } else {
                return new LambdaAbstraction(var, newBody);
            }
        }
    }

    /**
     * Checks if this abstraction is eta-reducible.
     * @return {@code true} if it is eta-reducible; {@code false} otherwise
     */
    private boolean etaReducible() {
        if (body instanceof LambdaApplication) {
            LambdaApplication application = (LambdaApplication) body;
            return application.body == var && !application.fn.getFreeVariables().contains(var);
        }
        return false;
    }

    /**
     * Returns the eta reduction of this abstraction, assuming it is eta-reducible.
     * @return The reduced expression
     */
    private LambdaExpression etaReduce() {
        return ((LambdaApplication) body).fn;
    }

    @Override
    public LambdaExpression reduceOnce(ReductionOrder order) {
        if (order == NORMAL && etaReducible()) {
            return etaReduce();
        }

        LambdaExpression newBody = body.reduceOnce(order);
        if (newBody != body) {
            return new LambdaAbstraction(var, newBody);
        }

        if (order == APPLICATIVE && etaReducible()) {
            return etaReduce();
        }

        return this;
    }
}