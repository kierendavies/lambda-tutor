package za.ac.uct.cs.ddd.lambda.evaluator;

import java.util.ArrayDeque;
import java.util.Deque;

public class LambdaExpression {
    private LambdaExpression body;

    public LambdaExpression() {
    }

    public LambdaExpression(String expr) throws InvalidExpressionException {
        this();

        // parse the given expression string

        if (!hasMatchedBrackets(expr)) {
            throw new InvalidExpressionException("Unmatched brackets");
        }

        // clean up the string
        expr = normalizeWhitespace(expr);
        expr = stripOuterBrackets(expr);

        if (expr.charAt(0) == '\\') {  // first check if it's an abstraction
            int dotIndex = expr.indexOf('.');
            // TODO check it's not inside any brackets or anything
            LambdaVariable var = new LambdaVariable(expr.substring(1, dotIndex));
            LambdaExpression body = new LambdaExpression(expr.substring(dotIndex + 1));
            this.body = new LambdaAbstraction(var, body);
        } else if (isVariableName(expr)) {  // then check if it's a variable
            this.body = new LambdaVariable(expr);
            // TODO detect scope
        } else {  // finally it must be an application
            int lastTermStart = -1;  // flag for not found
            if (expr.charAt(expr.length()-1) == ')') {
                int bracketLevel = 1;  // counting the last bracket
                // scanning backwards
                for (int i = expr.length()-2; i >= 0; i++) {
                    if (expr.charAt(i) == ')') {
                        bracketLevel++;
                    } else if (expr.charAt(i) == '(') {
                        bracketLevel--;
                        if (bracketLevel == 0) {
                            lastTermStart = i;
                            break;
                        }
                    }
                }
            } else {
                lastTermStart = expr.lastIndexOf(' ');
            }
            if (lastTermStart == -1) {
                throw new InvalidExpressionException("Not a valid application");
            }
            LambdaExpression fn = new LambdaExpression(expr.substring(0, lastTermStart));
            LambdaExpression body = new LambdaExpression(expr.substring(lastTermStart, expr.length()));
            this.body = new LambdaApplication(fn, body);
        }
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
        expr = expr.trim();
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
        expr = expr.trim();
        while (isAllBracketed(expr)) {
            expr = expr.substring(1, expr.length()-1).trim();
        }
        return expr;
    }

    static String normalizeWhitespace(String expr) {
        expr = expr.replace("\t", " ");
        expr = expr.replace("\n", " ");
        while (expr.contains("  ")) {
            expr = expr.replace("  ", " ");
        }
        return expr;
    }

    static boolean isVariableName(String expr) {
        for (int i = 0; i < expr.length(); i++) {
            if (Character.isWhitespace(expr.charAt(i))
                    || expr.charAt(i) == '\\'
                    || expr.charAt(i) == '.'
                    || expr.charAt(i) == '('
                    || expr.charAt(i) == ')') {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (body == null) {
            return "";
        }
        return body.toString();
    }

    public static void main(String[] args) {
        try {
            LambdaExpression expr = new LambdaExpression("\\f.(\\x.((f x)))");
            System.out.println(expr);
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
        }
    }
}
