package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

/**
 * A problem in which the user simplifies the underlying expression using any combination of conversion or reduction.
 */
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
     * Copy constructor. Creates a deep copy of the other Problem.
     * @param other The Problem to copy.
     */
    public SimplificationProblem(Problem other){
        super(other);
    }

    /**
     * Submits a reduction to the problem. This checks whether the submitted reduction is correct and changes the mark
     * appropriately. Any extra alpha conversions are ignored.
     * @param submission A LambdaExpression representing the attempted reduction.
     * @return true if the reduction is valid, false otherwise.
     */
    @Override
    public boolean submitStep(String submission) {
        LambdaExpression userReduction;
        try {
            userReduction = Parser.parse(submission);
        } catch (InvalidExpressionException e) {
            totalMark++;
            messages.add("Invalid expression ("+this.mark+"/"+this.totalMark+"): "+submission);
            return false;
        }

        ReductionResult tutorReduction = expression.reduceOnce(this.reductionOrder);
        if(userReduction.alphaEquivalentTo(tutorReduction.getReducedExpression())) {
            mark++;
            totalMark++;
            expression = tutorReduction.getReducedExpression();
            messages.add("Correct reduction ("+this.mark+"/"+this.totalMark+"): "+userReduction.toString());
            return true;
        } else if(userReduction.alphaEquivalentTo(expression)) {
            messages.add("Extra alpha conversion: "+userReduction.toString());
            return true;
        }
        else {
            totalMark++;
            expression = tutorReduction.getReducedExpression();
            messages.add("Incorrect reduction ("+this.mark+"/"+this.totalMark+"): "+userReduction.toString()+
                    ";\nExpected: "+tutorReduction.getReducedExpression().toString());
            return userReduction.alphaEquivalentTo(expression);
        }
    }
}
