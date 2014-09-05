package za.ac.uct.cs.ddd.lambda;

import jline.console.ConsoleReader;
import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.Parser;

import java.io.IOException;
import java.util.HashMap;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.*;

public class Repl {

    public static void main(String[] args) {
        HashMap<String, LambdaExpression> substitutions = new HashMap<>();
        try {
            substitutions.put("I", Parser.parse("\\x.x"));
            substitutions.put("K", Parser.parse("\\x.\\y.x"));
            substitutions.put("S", Parser.parse("\\x.\\y.\\z.x z (y z)"));
            substitutions.put("Y", Parser.parse("\\g.(\\x.g (x x)) (\\x.g (x x))"));

            substitutions.put("ZERO", Parser.parse("\\f.\\x.x"));
            substitutions.put("ONE", Parser.parse("\\f.\\x.f x"));
            substitutions.put("TWO", Parser.parse("\\f.\\x.f (f x)"));
            substitutions.put("THREE", Parser.parse("\\f.\\x.f (f (f x))"));

            substitutions.put("SUCC", Parser.parse("\\n.\\f.\\x.f (n f x)"));
            substitutions.put("PLUS", Parser.parse("\\m.\\n.\\f.\\x.m f (n f x)"));

            substitutions.put("TRUE", Parser.parse("\\x.\\y.x"));
            substitutions.put("FALSE", Parser.parse("\\x.\\y.y"));
            substitutions.put("AND", Parser.parse("\\p.\\q.p q p"));
            substitutions.put("OR", Parser.parse("\\p.\\q.p p q"));
            substitutions.put("NOT", Parser.parse("\\p.\\a.\\b.p b a"));
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
            return;
        }

        try {
            ConsoleReader reader = new ConsoleReader();
            String line;

            line = reader.readLine(">>> ");
            while (line != null) {
                if (line.isEmpty()) continue;
                try {
                    while (hasUnclosedBrackets(line)) {
                        line += "\n" + reader.readLine("... ");
                    }
                    LambdaExpression expression = Parser.parse(line);
                    expression = expression.substituteAll(substitutions);
                    System.out.println("Parsed expression: " + expression.toString());
                    System.out.println("Fully bracketed:   " + expression.toStringBracketed());
                    System.out.println("Free variables:    " + expression.getFreeVariables());

                    System.out.println("Applicative order reduction:");
                    for (LambdaExpression reduction : expression.reductions(APPLICATIVE)) {
                        System.out.println(reduction);
                    }

                    System.out.println("Normal order reduction:");
                    for (LambdaExpression reduction : expression.reductions(NORMAL)) {
                        System.out.println(reduction);
                    }
                } catch (InvalidExpressionException e) {
                    for (int i = 0; i < e.getColumn() + 4; i++) {
                        System.out.print(' ');
                    }
                    System.out.println('^');
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                line = reader.readLine(">>> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean hasUnclosedBrackets(String s) {
        int opening = 0;
        int closing = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                opening++;
            } else if (s.charAt(i) == ')') {
                closing++;
            }
        }
        return opening > closing;
    }
}
