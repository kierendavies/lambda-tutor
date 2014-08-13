package za.ac.uct.cs.ddd.lambda.evaluator;

public class LambdaExpression {
//    private List<Expression> terms;
    private LambdaExpression contents;

    public LambdaExpression() {
//        terms = new ArrayList<Expression>();
    }

    public LambdaExpression(String expr) {
        this();

        // clean up the string
        expr = expr.trim();
        expr = expr.replace("\t", " ");
        while (expr.contains("  ")) {
            expr = expr.replace("  ", " ");
        }

        // check if it's an unbracketed term
        if (!expr.contains("(") && !expr.contains(")")) {
            if (!expr.contains(" ")) {
                contents = new LambdaVariable(expr);
            }
            // TODO parse application, abstraction
        }

        // TODO match bracketed expression

    }

    public String toString() {
        return contents.toString();
    }
}
