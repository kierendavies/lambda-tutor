package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.List;

class LambdaAbstraction extends LambdaExpression {
    private LambdaVariable var;
    private LambdaExpression body;

    public LambdaAbstraction(LambdaVariable var, LambdaExpression body) {
        this.var = var;
        this.body = body;
    }

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
}
