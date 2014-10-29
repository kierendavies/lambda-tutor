/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.HashMap;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.APPLICATIVE;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.NORMAL;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.*;

/**
 * A representation of a lambda abstraction.
 */
public class LambdaAbstraction extends LambdaExpression {
    final LambdaVariable var;
    final LambdaExpression body;

    /**
     * Creates a lambda abstraction with a variable and body.
     *
     * @param var  The variable
     * @param body The body
     */
    public LambdaAbstraction(LambdaVariable var, LambdaExpression body) {
        this.var = var;
        this.body = body;
    }

    /**
     * Creates a lambda abstraction of many variables.
     *
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

    public LambdaVariable getVariable() {
        return var;
    }

    public LambdaExpression getBody() {
        return body;
    }

    @Override
    public LambdaAbstraction clone(Scope scope) {
        scope.add(var);
        LambdaAbstraction cloned = new LambdaAbstraction(var.clone(scope), body.clone(scope));
        scope.remove(var);
        return cloned;
    }

    @Override
    protected boolean alphaEquivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaAbstraction) {
            LambdaAbstraction abstraction = (LambdaAbstraction) expr;
            depths.put(var, depth);
            depths.put(abstraction.var, depth);
            return body.alphaEquivalentTo(abstraction.body, depth + 1, depths);
        } else {
            return false;
        }
    }

    @Override
    protected void buildString(StringBuilder builder, boolean fullBrackets, LambdaExpression highlighted) {
        if (this == highlighted) builder.append(HIGHLIGHT);
        if (fullBrackets) builder.append('(');

        builder.append('\u03bb');
        var.buildString(builder, fullBrackets, highlighted);
        builder.append('.');
        body.buildString(builder, fullBrackets, highlighted);

        if (fullBrackets) builder.append(')');
        if (this == highlighted) builder.append(UNHIGHLIGHT);
    }

    @Override
    public Scope getFreeVariables() {
        if (freeVariables == null) {
            freeVariables = new Scope();
            freeVariables.addAll(body.getFreeVariables());
            freeVariables.remove(var);
        }
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
            freeVariables.addAll(body.getFreeVariables());
            String newName = Util.nextVariableName(var.name);
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

    @Override
    protected ReductionResult reduceSubstitute(LambdaVariable variable, LambdaExpression expression) {
        Scope freeVariables = expression.getFreeVariables();
        if (freeVariables.contains(var.name)) {
            // rename this variable to avoid capture
            freeVariables.addAll(body.getFreeVariables());
            String newName = Util.nextVariableName(var.name);
            while (freeVariables.contains(newName)) {
                newName = Util.nextVariableName(newName);
            }
            LambdaVariable newVar = new LambdaVariable(newName);
            LambdaAbstraction reduced = new LambdaAbstraction(newVar, body.substitute(var, newVar));
            return new ReductionResult(this, this, reduced, ALPHA_CA);
        } else {
            ReductionResult bodyResult = body.reduceSubstitute(variable, expression);
            if (bodyResult.type != NONE) {
                LambdaAbstraction reduced = new LambdaAbstraction(var, bodyResult.reduced);
                return new ReductionResult(this, body, reduced, bodyResult.type);
            } else {
                return new ReductionResult(this, null, this, NONE);
            }
        }
    }

    /**
     * Checks if this abstraction is eta-reducible.
     *
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
     *
     * @return The reduced expression
     */
    private ReductionResult etaReduce() {
        return new ReductionResult(this, this, ((LambdaApplication) body).fn, ETA);
    }

    @Override
    public ReductionResult reduceOnce(ReductionOrder order) {
        if (order == NORMAL && etaReducible()) {
            return etaReduce();
        }

        ReductionResult bodyResult = body.reduceOnce(order);
        if (bodyResult.type != NONE) {
            LambdaExpression reducedExpression = new LambdaAbstraction(var, bodyResult.reduced);
            return new ReductionResult(this, bodyResult.redex, reducedExpression, bodyResult.type);
        }

        if (order == APPLICATIVE && etaReducible()) {
            return etaReduce();
        }

        return new ReductionResult(this, null, this, NONE);
    }
}
