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
    public String toString() {
        return String.format("(\\%s.%s)", var, body);
    }

    @Override
    void resolveScope(Scope scope) {
        Scope newScope = new Scope(scope);
        newScope.add(var);

        if (body instanceof LambdaVariable) {
            String bodyName = ((LambdaVariable) body).getName();
            if (newScope.contains(bodyName)) {
                body = newScope.get(bodyName);
            }
        } else {
            body.resolveScope(newScope);
        }
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = body.getFreeVariables();
        freeVariables.remove(var.getName());
        return  freeVariables;
    }
}
