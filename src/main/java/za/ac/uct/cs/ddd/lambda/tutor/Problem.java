package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

/**
 * A wrapper around LambdaExpression for tracking the progress of reductions.
 */
public class Problem {

    LambdaExpression expression;
    String message;
    double mark;

    /**
     * Creates a problem from a string by parsing the string into a LambdaExpression.
     * @param startExpression A string representing the starting expression of the problem.
     */
    public Problem(String startExpression){
        mark = 0;
        try {
            expression = Parser.parse(startExpression);
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a problem from a LambdaExpression reference.
     * @param startExpression A LambdaExpression representing the starting expression of the problem.
     */
    public Problem(LambdaExpression startExpression){
        mark = 0;
        expression = startExpression.clone();
    }

    /**
     * Checks that a given reduction is correct for this problem. If correct, the current reduction is set to the next
     * reduction.
     * @param userReduction A LambdaExpression representing the attempted reduction.
     * @param order The reduction order used.
     * @return true if the given reduction is correct, false otherwise.
     */
    public boolean submitStep(LambdaExpression userReduction, ReductionOrder order){
        ReductionResult tutorReduction = expression.reduceOnce(order);
        if(userReduction.alphaEquivalentTo(tutorReduction.getReducedExpression())) {
            expression = userReduction; //TODO: change marks when the reduction is correct
            return true;
        } else
            return userReduction.alphaEquivalentTo(expression);
    }

    public double getMark(){
        return mark;
    }

    public String getMessage(){
        return message;
    }
}
