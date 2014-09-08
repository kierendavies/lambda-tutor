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
        HashMap<String, LambdaExpression> builtins = new HashMap<>();
        try {
            builtins.put("I", Parser.parse("\\x.x"));
            builtins.put("K", Parser.parse("\\x.\\y.x"));
            builtins.put("S", Parser.parse("\\x.\\y.\\z.x z (y z)"));
            builtins.put("Y", Parser.parse("\\g.(\\x.g (x x)) (\\x.g (x x))"));

            builtins.put("ZERO", Parser.parse("\\f.\\x.x"));
            builtins.put("ONE", Parser.parse("\\f.\\x.f x"));
            builtins.put("TWO", Parser.parse("\\f.\\x.f (f x)"));
            builtins.put("THREE", Parser.parse("\\f.\\x.f (f (f x))"));

            builtins.put("SUCC", Parser.parse("\\n.\\f.\\x.f (n f x)"));
            builtins.put("PLUS", Parser.parse("\\m.\\n.\\f.\\x.m f (n f x)"));

            builtins.put("TRUE", Parser.parse("\\x.\\y.x"));
            builtins.put("FALSE", Parser.parse("\\x.\\y.y"));
            builtins.put("AND", Parser.parse("\\p.\\q.p q p"));
            builtins.put("OR", Parser.parse("\\p.\\q.p p q"));
            builtins.put("NOT", Parser.parse("\\p.\\a.\\b.p b a"));
        } catch (InvalidExpressionException e) {
            e.printStackTrace();
            return;
        }

        try {
            ConsoleReader reader = new ConsoleReader();
            String line;
            LambdaExpression expression;

            line = reader.readLine(">>> ");
            while (line != null) {
                if (line.isEmpty()) {
                    line = reader.readLine(">>> ");
                    continue;
                }
                try {
//                    while (hasUnclosedBrackets(line)) {
//                        line += "\n" + reader.readLine("... ");
//                    }
                    expression = Parser.parse(line);
                    expression = expression.substituteAll(builtins);
                    System.out.println("Parsed expression: " + expression.toString());
                    System.out.println("Fully bracketed:   " + expression.toStringBracketed());
                    System.out.println("Free variables:    " + expression.getFreeVariables());

                    System.out.println("Applicative order reduction:");
                    for (LambdaExpression reduction : expression.reductions(APPLICATIVE, 15)) {
                        System.out.println(reduction);
                    }

                    System.out.println("Normal order reduction:");
                    for (LambdaExpression reduction : expression.reductions(NORMAL, 15)) {
                        System.out.println(reduction);
                    }

                    LambdaExpression reduction = expression.reduce(NORMAL);
                    for (String builtin : builtins.keySet()) {
                        if (reduction.alphaEquivalentTo(builtins.get(builtin))) {
                            System.out.println("Equivalent to:     " + builtin);
                            break;
                        }
                    }

                } catch (InvalidExpressionException e) {
                    for (int i = 0; i < e.getColumn() + 4; i++) {
                        System.out.print(' ');
                    }
                    System.out.println('^');
                    System.out.println("Syntax error: " + e.getMessage());
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
