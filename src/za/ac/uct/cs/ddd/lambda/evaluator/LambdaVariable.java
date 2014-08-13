package za.ac.uct.cs.ddd.lambda.evaluator;

public class LambdaVariable extends LambdaExpression {
    private String name;

    public LambdaVariable(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
