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
import java.util.ArrayList;
import java.util.Arrays;
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
     Marks the file at answerFilename according to the problems found in the problemset defined in problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSet The problemset containing the problems.
     * @param answerFilename The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     * @throws java.io.IOException If the file at answerFilename doesn't exist.
     */
    private static double markReductionsFromPSet(ProblemSet problemSet, String answerFilename) throws IOException{
        // Submit the answers to the problems
        Problem currentProblem = problemSet.nextProblem();
        BufferedReader answerReader = new BufferedReader(new FileReader(answerFilename));

        // Eat empty lines at beginning of file
        String line = answerReader.readLine();
        while (line != null && line.isEmpty()) {
            line = answerReader.readLine();
        }

        List<String> messages;
        while (line != null) {
            if (line.isEmpty()) {
                System.out.println("  Empty line - new problem");
                currentProblem = problemSet.nextProblem();
                while (line != null && line.isEmpty()) {
                    line = answerReader.readLine();
                }
            } else {
                currentProblem.submitStep(line);
                System.out.println("  Line submitted: "+line);
                line = answerReader.readLine();
                System.out.println("  Line read: "+line);
            }
            if(currentProblem != null){
                messages = currentProblem.getMessages();
                System.out.println(messages.size() > 0 ? messages.get(messages.size()-1) : "");
            }
        }

        return problemSet.getMark();
    }

    /**
     * Marks the file at answerFilename according to the problems found in the problemset defined in problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetFilename The path to the xml file containing the problemset with the problems.
     * @param answerFilename The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     * @throws java.io.IOException
     */
    public static double markReductionsFromFile(String problemSetFilename, String answerFilename) throws IOException {
        ProblemSet problemSet = new ProblemSet(new File(problemSetFilename));

        return markReductionsFromPSet(problemSet, answerFilename);
    }

    /**
     * Marks all of the files in the answerFolder according to the problems found in the problemset defined in
     * problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetFilename The path to the xml file containing the problemset with the problems.
     * @param answerFolder Path to the directory containing answer files.
     */
    public static void markReductionsFromDir(String problemSetFilename, String answerFolder) {
        // Read in the questions
        ProblemSet problemSet = new ProblemSet(new File(problemSetFilename));

        // Find all of the .lam files in answerFolder
        if(!answerFolder.substring(answerFolder.length()-1).equals("/"))
            answerFolder += "/";

        ArrayList<String> answerFiles;
        try {
            answerFiles = new ArrayList<>(Arrays.asList(new File(answerFolder).list()));
        } catch (NullPointerException e){
            System.out.println("No such folder found. Please check that the folder "+answerFolder+" exists.");
            return;
        }

        for (int i = 0; i < answerFiles.size(); i++) {
            String filename = answerFiles.get(i);
            if(filename.length()<4 || !filename.substring(filename.length()-4).equals(".lam")){
                answerFiles.set(i, null);
            }
        }

        // Check the answers
        double mark;
        StringBuilder markList = new StringBuilder();
        for (String filename : answerFiles) {
            if(filename != null){
                mark = 0;
                try {
                    problemSet.reset();
                    mark = markReductionsFromPSet(problemSet, answerFolder+filename);
                } catch (IOException e) {
                    System.out.println("The file "+filename+" could not be found in folder "+answerFolder+".");
                }

                markList.append(filename.substring(0, filename.length()-4));
                markList.append(" - ");
                markList.append(mark);
                markList.append("\n");
                System.out.println(filename+" added to markList.");
            }
        }

        System.out.println("\n"+markList);

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

        // Mark a single file
        /*try {
            System.out.println("Score for reductions in "+args[1]+
                                "using problem set"+args[0]+": "+
                                markReductionsFromFile(args[0], args[1]));
        } catch(IndexOutOfBoundsException e){
            System.out.println("Error: expected problem set filename as the first argument and answer file name as " +
                    "the second argument.");
        } catch (IOException e) {
            System.out.println("File not found. Please check that the specified files exist and that the filenames " +
                    "passed are correct.");
        }*/

        // Mark all files in a directory
        markReductionsFromDir("/home/dave/Projects/Capstone Project/lambda_test_pset.txt",
                "/home/dave/Projects/Capstone Project");
    }
}
