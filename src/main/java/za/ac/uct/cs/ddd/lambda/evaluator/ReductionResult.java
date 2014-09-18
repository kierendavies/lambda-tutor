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
