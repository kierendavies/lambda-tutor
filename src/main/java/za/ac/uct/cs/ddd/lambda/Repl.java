package za.ac.uct.cs.ddd.lambda;

import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Repl {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        try {
            System.out.print(">>> ");
            line = reader.readLine();
            while (!line.toLowerCase().equals("exit")) {
                try {
                    LambdaExpression expression = Parser.parse(line);
                    System.out.println(expression);
                    System.out.println(expression.getFreeVariables());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print(">>> ");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
