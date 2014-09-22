package za.ac.uct.cs.ddd.lambda;

import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionResult;
import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * A collection of static methods for marking lambda reductions in a given text file.
 */
public class Marker {

    /**
     * Checks that an array of reductions are correct by reducing the first LambdaExpression and checking that the
     * subsequent steps in userReductions are alpha-equivalent to the reductions produced by the first LambdaExpression.
     *
     * @param userReductions An array of expression reductions to be marked. (where each expression reduction is a list
     *                       of LambdaExpressions.
     * @return A boolean indicating whether the reductions are correct.
     */
    public static boolean markReductions(List<LambdaExpression>[] userReductions, ReductionOrder order){
        return checkReductions(userReductions, order).equals("All reductions are correct.");
    }

    /**
     * Creates a ProblemSet (using the file at problemSetFilename) and parses the answer to each question (found in the
     * file at answerFilename) as a list of LambdaExpressions. Each answer's LambdaExpressions are submitted to the
     * corresponding problem, and the resulting mark is returned.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetFilename The path to the xml file containing the problems.
     * @param answerFilename The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     */
    public static double markReductionsFromFile(String problemSetFilename, String answerFilename) throws IOException {
        // Read in the questions
        ProblemSet problemSet = new ProblemSet(new File(problemSetFilename));

        // Submit the answers to the problems
        Problem currentProblem = problemSet.nextProblem();
        BufferedReader answerReader = new BufferedReader(new FileReader(answerFilename));

        // Eat empty lines at beginning of file
        String line = answerReader.readLine();
        while (line != null && line.isEmpty()) {
            line = answerReader.readLine();
        }

        while (line != null) {
            if (line.isEmpty()) {
                currentProblem = problemSet.nextProblem();
                while (line != null && line.isEmpty()) {
                    line = answerReader.readLine();
                }
            } else {
                currentProblem.submitStep(line);
                line = answerReader.readLine();
            }
        }

        return problemSet.getMark();
    }

    /**
     * Checks that an array of reductions are correct by reducing the first LambdaExpression and checking that the
     * subsequent steps in userReductions are alpha-equivalent to the reductions produced by the first LambdaExpression.
     * @param userReductions An array of expression reductions to be marked. (where each expression reduction is a list
     *                       of LambdaExpressions.
     * @return A string message indicating whether the reductions are correct and the location of the first error, if
     * any.
     */
    public static String checkReductions(List<LambdaExpression>[] userReductions, ReductionOrder order){
        for (List<LambdaExpression> l : userReductions) {
            List<ReductionResult> calculatedReductions = l.get(0).reductions(ReductionOrder.NORMAL);

            for (int i = 0; i < l.size() && i < calculatedReductions.size(); i++) {
                if(!l.get(i).alphaEquivalentTo(calculatedReductions.get(i).getReducedExpression())){
                    return String.format("Reduction error found in reduction for expression %s:\n" +
                                    "Expected: %s\n" +
                                    "Found: %s",
                            calculatedReductions.get(0).toString(),
                            calculatedReductions.get(i),
                            l.get(i));
                }
            }
        }

        return "All reductions are correct.";
    }

    /**
     * Marks a given file's reductions
     * @param args Command-line arguments. The first is the path to the problem set, the second is the path to the
     *             answer file.
     */
    public static void main(String[] args) {

        try {
            System.out.println("Score for reductions in "+args[1]+
                                "using problem set"+args[0]+": "+
                                markReductionsFromFile(args[0], args[1]));
        } catch(IndexOutOfBoundsException e){
            System.out.println("Error: expected problem set filename as the first argument and answer file name as " +
                    "the second argument.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
