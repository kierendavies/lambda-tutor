package za.ac.uct.cs.ddd.lambda.tutor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
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
     * reduction. Note that the same ReductionOrder should be passed to this method each time, or the result might not
     * properly conform to either order.
     * @param userReduction A LambdaExpression representing the attempted reduction.
     * @param order The reduction order used.
     * @return true if the given reduction is correct, false otherwise.
     */
    public abstract boolean submitStep(LambdaExpression userReduction);

    /**
     * Parses the text in an xml file into a problem. The general format for the file is as follows:
     * <problem>
     *     <type>simplification | conversion | reduction | bracketing | labelling</type>
     *     <order>reductionNormal | reductionApplicative</order>
     *     <start>(λn.λf.λx.f (n f x)) (λf.λx.x)</start>
     *     <steps>10</steps> (optional)
     * </problem>
     * @param xmlFile A file containing text in the format above representing the problem.
     * @return A Problem subclass with the specified configuration.
     */
    public static Problem parseXML(File xmlFile) throws NoSuchFieldException{
        SAXBuilder builder = new SAXBuilder();

        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            String type = rootNode.getChildText("type");
            String expression = rootNode.getChildText("start");
            String orderString = rootNode.getChildText("order");
            String stepsText = rootNode.getChildText("steps");
            int maxSteps = stepsText == null ? -1 : Integer.parseInt(stepsText);

            ReductionOrder order;
            switch (orderString) {
                case "reductionNormal":
                    order = ReductionOrder.NORMAL;
                    break;
                case "reductionApplicative":
                    order = ReductionOrder.APPLICATIVE;
                    break;
                default:
                    throw new NoSuchFieldException("Invalid order supplied. Order must be one of reductionNormal or " +
                            "reductionApplicative.");
            }

            if(type.equals("simplification")){
                if(maxSteps > 0){
                    return new SimplificationProblem(expression, order, maxSteps);
                } else {
                    return new SimplificationProblem(expression, order);
                }
            } else{
                throw new NoSuchFieldException("Invalid problem type supplied. Problem types must be one of " +
                        "simplification, conversion, reduction, bracketing or labelling.");
            }

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns the current mark for this problem.
     * @return The mark acquired so far. This mark is normalised to be in the closed interval [0,1].
     */
    public double getMark(){
        if(totalMark != 0)
            return (double)(mark/totalMark);
        else
            return 0;
    }

    /**
     * Returns the messages accrued for this problem.
     * @return The list of messages
     */
    public List<String> getMessage(){
        return new ArrayList<>(messages);
    }
}
