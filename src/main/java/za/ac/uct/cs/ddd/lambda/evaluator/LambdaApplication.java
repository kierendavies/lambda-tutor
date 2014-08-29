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
    public LambdaApplication clone() {
        return new LambdaApplication(fn.clone(), body.clone());
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
    void resolveScope(Scope scope) {
        // separately resolve fn and body
        if (fn instanceof LambdaVariable) {
            String fnName = ((LambdaVariable) fn).getName();
            if (scope.contains(fnName)) {
                fn = scope.get(fnName);
            }
        } else {
            fn.resolveScope(scope);
        }
        if (body instanceof LambdaVariable) {
            String bodyName = ((LambdaVariable) body).getName();
            if (scope.contains(bodyName)) {
                body = scope.get(bodyName);
            }
        } else {
            body.resolveScope(scope);
        }
    }

    @Override
    public Scope getFreeVariables() {
        Scope freeVariables = fn.getFreeVariables();
        freeVariables.addAll(body.getFreeVariables());
        return freeVariables;
    }
}
