package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper around LambdaExpression for tracking the progress of reductions.
 */
public abstract class Problem {

    protected LambdaExpression expression;
    protected ReductionOrder reductionOrder;
    protected List<ReductionResult> reductions;
    protected List<String> messages;
    protected int mark;
    protected int totalMark;

    /**
     * Creates a problem from a string by parsing the string into a LambdaExpression.
     * @param startExpression A string representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     */
    public Problem(String startExpression, ReductionOrder order){
        try {
            expression = Parser.parse(startExpression);
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
        }
        reductionOrder = order;
        reductions = expression.reductions(order);
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
    }

    /**
     * Creates a problem from a LambdaExpression reference.
     * @param startExpression A LambdaExpression representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     */
    public Problem(LambdaExpression startExpression, ReductionOrder order){
        expression = startExpression.clone();
        reductionOrder = order;
        reductions = expression.reductions(order);
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
    }

    /**
     * Creates a problem from a string by parsing the string into a LambdaExpression.
     * @param startExpression A string representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     * @param maxIterations The maximum number of iterations that should be done when reducing the expression.
     */
    public Problem(String startExpression, ReductionOrder order, int maxIterations){
        try {
            expression = Parser.parse(startExpression);
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
        }
        reductionOrder = order;
        reductions = expression.reductions(order, maxIterations);
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
    }

    /**
     * Creates a problem from a LambdaExpression reference.
     * @param startExpression A LambdaExpression representing the starting expression of the problem.
     * @param order The order in which the expressions in this problem must be reduced.
     * @param maxIterations The maximum number of iterations that should be done when reducing the expression.
     */
    public Problem(LambdaExpression startExpression, ReductionOrder order, int maxIterations){
        mark = 0;
        expression = startExpression.clone();
        reductionOrder = order;
        reductions = expression.reductions(order, maxIterations);
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
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
