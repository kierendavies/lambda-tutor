package za.ac.uct.cs.ddd.lambda.tutor;

import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;

public class ProblemSet {

    Problem[] problems;
    int currentProblem;

    public ProblemSet(String[] expressions){
        problems = new Problem[expressions.length];
        for (int i = 0; i < expressions.length; i++) {
            problems[i] = new Problem(expressions[i]);
        }
    }

    public ProblemSet(LambdaExpression[] expressions){
        problems = new Problem[expressions.length];
        for (int i = 0; i < expressions.length; i++) {
            problems[i] = new Problem(expressions[i]);
        }
    }

    public Problem getProblem(){
        return problems[currentProblem];
    }
}
