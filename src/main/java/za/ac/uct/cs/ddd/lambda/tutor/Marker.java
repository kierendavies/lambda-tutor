package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
     * @return A string indicating whether there are errors in the reductions.
     */
    public static String markReductions(List<LambdaExpression>[] userReductions) {
        userReductions[0].get(0);

        for (List<LambdaExpression> l : userReductions) {
            List<ReductionResult> calculatedReductions = l.get(0).reductions(ReductionOrder.NORMAL);

            for (int i = 0; i < l.size() && i < calculatedReductions.size(); i++) {
                if (!l.get(i).alphaEquivalentTo(calculatedReductions.get(i).getReducedExpression())) {
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
     * Parses the reductions in a file and checks for correctness using markReductions. This assumes the file consists
     * only of newline-separated paragraphs of reductions.
     *
     * @param filename The name of the file containing the lambda reductions to be marked.
     * @return A string indicating whether there are errors in the reductions.
     */
    public static String markReductionsFromFile(String filename) {
        Scanner fileScanner = new Scanner(filename);
        int countExpressions = 0;

        // TODO: strip extra newlines
        while (fileScanner.hasNext()) {
            if (fileScanner.nextLine().equals(""))
                countExpressions++;
        }
        countExpressions++;

        fileScanner = new Scanner(filename);
        String line;
        List<LambdaExpression>[] userReductions = new List[countExpressions];

        for (int i = 0; i < countExpressions; i++) {
            userReductions[i] = new ArrayList<LambdaExpression>();

            line = fileScanner.nextLine();
            while (!line.equals("")) {
                try {
                    userReductions[i].add(Parser.parse(line));
                } catch (InvalidExpressionException e) {
                    e.printStackTrace();
                }
                line = fileScanner.nextLine();
            }
            fileScanner.nextLine(); // move past blank line
        }

        return markReductions(userReductions);
    }
}
