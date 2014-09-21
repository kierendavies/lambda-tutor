package za.ac.uct.cs.ddd.lambda.tutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of problems.
 */
public class ProblemSet {

    private List<Problem> problems;
    private int currentProblem;

    public ProblemSet(List<Problem> problems){
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
     * @param xmlFile A file containing text in the format above representing the problem set.
     */
    public ProblemSet(File xmlFile){
        currentProblem = 0;
        //TODO: parse the file
    }

    /**
     * Returns the current problem and advances the cursor to the next problem.
     * @return A reference to the current problem.
     */
    public Problem nextProblem(){
        Problem next = problems.get(currentProblem);
        currentProblem++;
        return next;
    }

    /**
     * Returns the current problem without affecting the cursor.
     * @return A reference to the current problem.
     */
    public Problem getProblem(){
        return problems.get(currentProblem);
    }

    /**
     * Calculates an evenly weighted, normalised average of the marks of all of the problems in this problem set.
     * @return An overall mark for the problem set, normalised to be in the interval [0,1].
     */
    public double getMark(){
        double mark = 0;
        for (Problem problem : problems) {
            mark += problem.getMark();
        }
        return mark/problems.size();
    }
}
