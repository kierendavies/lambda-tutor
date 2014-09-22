package za.ac.uct.cs.ddd.lambda.tutor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of problems with a title. This is stateful, and tracks the current problem being solved.
 */
public class ProblemSet {

    private String title;
    private int currentProblem;
    private List<Problem> problems;

    public ProblemSet(String title, List<Problem> problems){
        this.title = title;
        currentProblem = 0;
        this.problems = new ArrayList<>(problems);
    }

    /**
     * Create a problem set from a given xml file. The general format for the file is as follows:
     *
     * <problemset>
     *     <name>Problem Set 1</name>
     *     <problems>
     *         <problem>
     *             <type>simplification | conversion | reduction | bracketing | labelling</type>
     *             <order>reductionNormal | reductionApplicative</order>
     *             <start>(λn.λf.λx.f (n f x)) (λf.λx.x)</start>
     *             <steps>10</steps> (optional)
     *         </problem>
     *         <problem>
     *             ...
     *         </problem>
     *         (etc.)
     *     </problems>
     * </problemset>
     *
     * Where each problem follows the same format as found in the problem class.
     * @param xmlFile A file object containing xml with a problem set defined as above.
     */
    public ProblemSet(File xmlFile){
        currentProblem = 0;

        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            title = rootNode.getChildText("name");

            List<Element> problemNodes = rootNode.getChild("problems").getChildren();
            problems = new ArrayList<>();
            for (Element problemNode : problemNodes) {
                problems.add(Problem.parseProblemNode(problemNode));
            }
        } catch (JDOMException | IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ProblemSet(ProblemSet pset){
        this.title = pset.title;
        this.currentProblem = pset.currentProblem;
        this.problems = new ArrayList<>(pset.problems);
    }

    /**
     * Returns the current problem and advances the cursor to the next problem.
     * @return A reference to the current problem. Returns null if the last problem has already been returned.
     */
    public Problem nextProblem(){
        if(currentProblem >= problems.size())
            return null;
        else {
            Problem next = problems.get(currentProblem);
            currentProblem++;
            return next;
        }
    }

    /**
     * Returns the current problem without affecting the cursor.
     * @return A reference to the current problem.
     */
    public Problem getProblem(){
        return problems.get(currentProblem);
    }

    /**
     * Returns the title of the problem set.
     * @return The title of this problem set.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Calculates an evenly weighted average of the marks of all of the problems in this problem set. The mark is a
     * percentage in the range [0, 100].
     * @return An overall mark for the problem set, normalised to be in the interval [0,1].
     */
    public double getMark(){
        double mark = 0;
        for (Problem problem : problems) {
            mark += problem.getMark();
        }
        return mark/problems.size()*100;
    }

    /**
     * Returns the size of the underlying list of problems.
     * @return The number of problems in this problem set.
     */
    public int size(){
        return problems.size();
    }

    /**
     * Resets the current problem to the beginning of the problem set and resets all problems in the problem set.
     */
    public void reset(){
        currentProblem = 0;
        for (Problem problem : problems) {
            problem.reset();
        }
    }

    public static void main(String[] args) {
        File psetFile = new File(args[0]);
        ProblemSet set = new ProblemSet(psetFile);
    }
}
