package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionResult;

public class SimplificationProblem extends Problem{

    /**
     * Creates a simplification problem from a string by parsing the string into a LambdaExpression.
     * @param startExpression A string representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     */
    public SimplificationProblem(String startExpression, ReductionOrder order){
        super(startExpression, order);
    }

    /**
     * Creates a simplification problem from a LambdaExpression reference.
     * @param startExpression A LambdaExpression representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     */
    public SimplificationProblem(LambdaExpression startExpression, ReductionOrder order){
        super(startExpression, order);
    }

    /**
     * Creates a simplification problem from a string by parsing the string into a LambdaExpression.
     * @param startExpression A string representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     * @param maxIterations The maximum number of iterations that should be done when reducing the expression.
     */
    public SimplificationProblem(String startExpression, ReductionOrder order, int maxIterations){
        super(startExpression, order, maxIterations);
    }

    /**
     * Creates a simplification problem from a LambdaExpression reference.
     * @param startExpression A LambdaExpression representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     * @param maxIterations The maximum number of iterations that should be done when reducing the expression.
     */
    public SimplificationProblem(LambdaExpression startExpression, ReductionOrder order, int maxIterations) {
        super(startExpression, order, maxIterations);
    }

    /**
     * Submits a reduction to the problem. This checks whether the submitted reduction is correct and changes the mark
     * appropriately. Any extra alpha conversions are ignored.
     * @param userReduction A LambdaExpression representing the attempted reduction.
     * @return true if the reduction is valid, false otherwise.
     */
    @Override
    public boolean submitStep(LambdaExpression userReduction) {
        ReductionResult tutorReduction = expression.reduceOnce(this.reductionOrder);
        if(userReduction.alphaEquivalentTo(tutorReduction.getReducedExpression())) {
            expression = userReduction;
            mark++;
            totalMark++;
            return true;
        } else if(userReduction.alphaEquivalentTo(expression))
            return true;
        else {
            totalMark++;
            return userReduction.alphaEquivalentTo(expression);
        }
    }
}
