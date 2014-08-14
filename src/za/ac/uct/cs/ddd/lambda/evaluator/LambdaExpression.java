package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.ArrayDeque;
import java.util.Deque;

public class LambdaExpression {
    private LambdaExpression contents;

    public LambdaExpression() {
    }

    public LambdaExpression(String expr) throws InvalidExpressionException {
        this();

        // parse the given expression string

        if (!hasMatchedBrackets(expr)) {
            throw new InvalidExpressionException("Unmatched brackets");
        }

        // clean up the string
        expr = expr.trim();
        expr = expr.replace("\t", " ");
        expr = expr.replace("\n", " ");
        while (expr.contains("  ")) {
            expr = expr.replace("  ", " ");
        }
        expr = stripOuterBrackets(expr);
        expr = expr.trim();

        // check if it's an unbracketed term
        if (!expr.contains("(") && !expr.contains(")")) {
            if (!expr.contains(" ")) {
                contents = new LambdaVariable(expr);
            }
            // TODO parse application, abstraction
        }

        // TODO match bracketed expression

    }

    static boolean hasMatchedBrackets(String expr) {
        Deque<Character> stack = new ArrayDeque<Character>();

        for (int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') {
                stack.push('(');
            } else if (expr.charAt(i) == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    static boolean isAllBracketed(String expr) {
        // expr must be trimmed
        if (expr.charAt(0) != '(' || expr.charAt(expr.length()-1) != ')') {
            return false;
        }
        int bracketLevel = 1;  // already counting the first opening bracket and last closing bracket
        for (int i = 1; i < expr.length() - 1; i++) {
            if (expr.charAt(i) == '(') {
                bracketLevel++;
            } else if (expr.charAt(i) == ')') {
                bracketLevel--;
            }
            if (bracketLevel < 1) {
                return false;
            }
        }
        return true;
    }

    static String stripOuterBrackets(String expr) {
        while (isAllBracketed(expr)) {
            expr = expr.substring(1, expr.length()-1);
        }
        return expr;
    }

    public String toString() {
        if (contents == null) {
            return "";
        }
        return contents.toString();
    }
}
