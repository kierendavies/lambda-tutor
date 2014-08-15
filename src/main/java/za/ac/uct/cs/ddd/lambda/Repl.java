package za.ac.uct.cs.ddd.lambda;

import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Repl {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        try {
            System.out.print(">>> ");
            line = reader.readLine();
            while (!line.toLowerCase().equals("exit")) {
                try {
                    LambdaExpression expression = new LambdaExpression(line);
                    System.out.println(expression);
                } catch (Exception e) {
                    System.out.println("Invalid expression");
                }
                System.out.print(">>> ");
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
