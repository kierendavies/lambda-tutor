/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.ac.uct.cs.ddd.lambda;

import jline.console.ConsoleReader;
import za.ac.uct.cs.ddd.lambda.evaluator.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.APPLICATIVE;
import static za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder.NORMAL;

public class Repl {
    private static final int maxReductions = 25;

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
            builtins.put("FOUR", Parser.parse("\\f.\\x.f (f (f (f x)))"));
            builtins.put("FIVE", Parser.parse("\\f.\\x.f (f (f (f (f x))))"));

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
                    System.out.println("Fully bracketed:   " + expression.toString(true));
                    System.out.println("Free variables:    " + expression.getFreeVariables());

                    System.out.println("Applicative order reduction:");
                    printReductions(expression, APPLICATIVE);

                    System.out.println("Normal order reduction:");
                    printReductions(expression, NORMAL);

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

    private static void printReductions(LambdaExpression expression, ReductionOrder order) {
        List<ReductionResult> reductions = expression.reductions(order, maxReductions);

        if (reductions.isEmpty()) {
            System.out.println("None");
            return;
        }

        System.out.println("    " + expression.toString(false, reductions.get(0).getRedex()));
        int l = reductions.size();
        for (int i = 0; i < l - 1; i++) {
            ReductionResult reduction = reductions.get(i);
            System.out.print(String.format("[%s] ", reductions.get(i).getType()));
            System.out.println(reductions.get(i).getReducedExpression().toString(false, reductions.get(i + 1).getRedex()));
        }
        System.out.print(String.format("[%s] ", reductions.get(l - 1).getType()));
        System.out.println(reductions.get(l - 1).getReducedExpression());
    }
}
