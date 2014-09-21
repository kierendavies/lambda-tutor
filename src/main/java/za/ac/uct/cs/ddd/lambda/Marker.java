package za.ac.uct.cs.ddd.lambda;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

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
     * Parses the reductions in a file and checks for correctness using markReductions. This assumes the file consists
     * only of newline-separated paragraphs of reductions. Currently assumes that the first line in a reduction is the
     * correct starting point.
     * @param filename The name of the file containing the lambda reductions to be marked.
     * @return A boolean indicating whether the reductions are correct.
     */
    public static boolean markReductionsFromFile(String filename, ReductionOrder order){ // TODO: add a second file for the questions
        // Count the number of expressions in the file by counting the number of clusters of newlines
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder file = new StringBuilder();
        while (fileScanner.hasNext()) {
            file.append(fileScanner.nextLine());
        }

        String filetext = file.toString();
        // Ignore repeated newlines using regex
        Pattern newlines = Pattern.compile("\n*");
        Matcher matcher = newlines.matcher(filetext);

        int countExpressions = 0;
        while (matcher.find())
            countExpressions++;
        countExpressions++; // Count the last expression

        // Parse the expressions
        try {
            fileScanner = new Scanner(new File(filename));
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

        return markReductions(userReductions, order);
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
        // TODO: remove duplication in markReductions
        //userReductions[0].get(0);

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
     * @param args Command-line arguments. The first is the name of the file to check.
     */
    public static void main(String[] args) {

        try {
            System.out.println(markReductionsFromFile(args[0], ReductionOrder.NORMAL) ?
                    "Correct" :
                    "Incorrect");
        } catch(IndexOutOfBoundsException e){
            System.out.println("Error: expected filename as the first argument");
        }
    }
}
