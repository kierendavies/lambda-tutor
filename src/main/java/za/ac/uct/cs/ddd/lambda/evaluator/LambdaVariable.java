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

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.*;

/**
 * A representation of a variable.
 */
public class LambdaVariable extends LambdaExpression {
    final String name;

    /**
     * Creates a new variable with the given name.
     *
     * @param name The name of the variable
     */
    public LambdaVariable(String name) {
        this.name = name;
        freeVariables = null;
    }

    @Override
    public LambdaVariable clone(Scope scope) {
        return scope.getOrAddNew(this.name);
    }

    @Override
    protected boolean alphaEquivalentTo(LambdaExpression expr, int depth, HashMap<LambdaVariable, Integer> depths) {
        if (expr instanceof LambdaVariable) {
            LambdaVariable variable = (LambdaVariable) expr;
            if (depths.containsKey(this)) {
                return depths.containsKey(variable) && depths.get(this).equals(depths.get(variable));
            } else {
                return !depths.containsKey(variable) && variable.name.equals(name);
            }
        } else {
            return false;
        }
    }

    @Override
    protected void buildString(StringBuilder builder, boolean fullBrackets, LambdaExpression highlighted) {
        if (this == highlighted) builder.append(HIGHLIGHT);

        builder.append(name);

        if (this == highlighted) builder.append(UNHIGHLIGHT);
    }

    @Override
    public Scope getFreeVariables() {
        if (freeVariables == null) {
            freeVariables = new Scope();
            freeVariables.add(this);
        }
        return freeVariables;
    }

    @Override
    public LambdaExpression renameDuplicateVariables(Scope scope) {
        return this;
    }

    @Override
    public LambdaExpression substitute(LambdaVariable variable, LambdaExpression expression) {
        if (this == variable) {
            return expression;
        } else {
            return this;
        }
    }

    @Override
    protected ReductionResult reduceSubstitute(LambdaVariable variable, LambdaExpression expression) {
        if (this == variable) {
            ReductionType type = (expression instanceof LambdaVariable) ? ALPHA : BETA;
            return new ReductionResult(this, this, expression, type);
        } else {
            return new ReductionResult(this, null, this, NONE);
        }
    }

    @Override
    public ReductionResult reduceOnce(ReductionOrder order) {
        return new ReductionResult(this, null, this, NONE);
    }
}
