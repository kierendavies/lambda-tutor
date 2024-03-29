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
    protected final LambdaExpression firstExpression;
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
    public Problem(String startExpression, ReductionOrder order) throws InvalidExpressionException{
        expression = Parser.parse(startExpression);
        firstExpression = expression.clone();
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
        firstExpression = expression.clone();
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
    public Problem(String startExpression, ReductionOrder order, int maxIterations) throws InvalidExpressionException{
        expression = Parser.parse(startExpression);
        firstExpression = expression.clone();
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
        expression = startExpression.clone();
        firstExpression = expression.clone();
        reductionOrder = order;
        reductions = expression.reductions(order, maxIterations);
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
    }

    /**
     * Copy constructor. Creates a deep copy of the other problem.
     * @param other The Problem to copy.
     */
    public Problem(Problem other){
        this.expression = other.expression.clone();
        this.firstExpression = other.firstExpression.clone();
        this.reductionOrder = other.reductionOrder;
        this.reductions = new ArrayList<>(other.reductions);
        this.messages = new ArrayList<>(other.messages);
        this.mark = other.mark;
        this.totalMark = other.totalMark;
    }

    /**
     * Checks that a given submission is correct for this problem. Might change the underlying expression.
     * @param submission A LambdaExpression representing the attempted reduction.
     * @return true if the given reduction is correct, false otherwise.
     */
    public abstract boolean submitStep(String submission);

    /**
     * Checks whether this problem is complete.
     * @return true if the problem finished, false otherwise.
     */
    public abstract boolean isComplete();

    /**
     * Parses the text in an xml file into a problem. The general format for the file is as follows:
     * <problem>
     *     <type>simplification | conversion | reduction | bracketing | labelling</type>
     *     <order>normal | applicative</order>
     *     <start>(λn.λf.λx.f (n f x)) (λf.λx.x)</start>
     *     <steps>10</steps> (optional)
     * </problem>
     * @param url A string containing a path to an xml file with a single problem.
     * @return A Problem subclass with the specified configuration.
     */
    public static Problem parseProblemFromURL(String url) throws NoSuchFieldException{
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(url);

        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            return parseProblemNode(rootNode);
        } catch (JDOMException | IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Parses a single problem node into a Problem.
     * @param problemNode The jdom element representing a problem.
     * @return A problem constructed from the information in the problem node, or null if no information is provided.
     * @throws NoSuchFieldException If incorrect values are found for either the order or type elements.
     */
    protected static Problem parseProblemNode(Element problemNode) throws NoSuchFieldException {
        String type = problemNode.getChildText("type");
        String expression = problemNode.getChildText("start");
        String orderString = problemNode.getChildText("order");
        String stepsText = problemNode.getChildText("steps");
        int maxSteps = stepsText == null ? -1 : Integer.parseInt(stepsText);

        ReductionOrder order;
        switch (orderString) {
            case "normal":
                order = ReductionOrder.NORMAL;
                break;
            case "applicative":
                order = ReductionOrder.APPLICATIVE;
                break;
            default:
                throw new NoSuchFieldException("Invalid order supplied. Order must be one of normal or " +
                        "applicative.");
        }

        try {
            if (type.equals("simplification")) {
                if (maxSteps > 0) {
                    return new SimplificationProblem(expression, order, maxSteps);
                } else {
                    return new SimplificationProblem(expression, order);
                }
            } else {
                //TODO: add in new types of problems for conversion, reduction, bracketing and labelling.
                throw new NoSuchFieldException("Invalid problem type supplied. Problem type must be simplification.");
            }
        } catch (InvalidExpressionException e){
//            System.out.println("Invalid starting lambda expression found: "+e.getMessage());
            return null;
        }
    }

    public LambdaExpression getExpression() {
        return expression;
    }

    public ReductionOrder getReductionOrder() {
        return reductionOrder;
    }

    /**
     * Returns the current mark for this problem.
     * @return The mark acquired so far. This mark is normalised to be in the closed interval [0,1].
     */
    public double getMark(){
        if(totalMark != 0)
            return ((double) mark) / totalMark;
        else
            return 0;
    }

    /**
     * Returns the messages accumulated for this problem.
     * @return The list of messages this problem has accumulated.
     */
    public List<String> getMessages(){
        return new ArrayList<>(messages);
    }

    /**
     * Resets the messages and marks in this Problem.
     */
    public void reset(){
        expression = firstExpression.clone();
        messages = new ArrayList<>();
        mark = 0;
        totalMark = 0;
    }
}
