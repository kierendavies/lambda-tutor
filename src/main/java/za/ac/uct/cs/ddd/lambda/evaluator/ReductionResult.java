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

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.ALPHA;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionType.ALPHA_CA;

public class ReductionResult {
    final LambdaExpression initial;
    final LambdaExpression redex;
    final LambdaExpression reduced;
    final ReductionType type;

    public ReductionResult(LambdaExpression initial, LambdaExpression redex, LambdaExpression reduced, ReductionType type) {
        this.initial = initial;
        this.redex = redex;
        this.reduced = reduced;
        this.type = type;
    }

    public LambdaExpression getInitialExpression() {
        return initial;
    }

    public LambdaExpression getRedex() {
        return redex;
    }

    public LambdaExpression getReducedExpression() {
        return reduced;
    }

    public ReductionType getType() {
        if (type == ALPHA_CA) {
            return ALPHA;
        }
        return type;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", type, reduced);
    }
}
