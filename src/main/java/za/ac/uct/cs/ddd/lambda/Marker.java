package za.ac.uct.cs.ddd.lambda;

import za.ac.uct.cs.ddd.lambda.evaluator.*;
import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * Creates a ProblemSet (using the file at problemSetURL) and parses the answer to each question (found in the file
     * at answerURL) as a list of LambdaExpressions. Each answer's LambdaExpressions are submitted to the corresponding
     * Problem, and the resulting mark is returned.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetURL The path to the xml file containing the problems.
     * @param answerURL The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     */
    public static double markReductionsFromFile(String problemSetURL, String answerURL){
        // Count the number of expressions in the file by counting the number of clusters of newlines
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(answerURL));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder file = new StringBuilder();
        while (fileScanner.hasNext()) {
            file.append(fileScanner.nextLine());
            file.append("\n");
        }

        String filetext = file.toString();
        // Ignore repeated newlines using regex
        Pattern newlines = Pattern.compile("\n{2,}");
        Matcher matcher = newlines.matcher(filetext);

        int countExpressions = 0;
        while (matcher.find())
            countExpressions++;
        countExpressions++; // Count the last expression

        // Parse the answer expressions
        try {
            fileScanner = new Scanner(new File(answerURL));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        List<LambdaExpression>[] userReductions = new List[countExpressions];

        for (int i = 0; i < userReductions.length; i++) {
            userReductions[i] = new ArrayList<>();

            line = fileScanner.nextLine();
            while(!"".equals(line) && fileScanner.hasNext()){
                try {
                    userReductions[i].add(Parser.parse(line));
                } catch (InvalidExpressionException e) {
                    e.printStackTrace();
                }
                line = fileScanner.nextLine();
            }
        }

        // Read in the questions
        ProblemSet pset = null;
        pset = new ProblemSet(new File(problemSetURL));

        // Submit the answers to the problems
        for (int i = 0; i < pset.size(); i++) {
            Problem currentProblem = pset.nextProblem();

            for (LambdaExpression userReduction : userReductions[i]) {
                currentProblem.submitStep(userReduction);
            }
        }

        return pset.getMark();
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
        }
    }
}
