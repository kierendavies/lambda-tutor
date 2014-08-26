package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.io.StringReader;

public class Parser {
    public static LambdaExpression parse(Lexer lexer) throws IOException, MismatchedBracketException {
        // generate tree of bracketed expressions

        System.out.println((new BracketedExpression(lexer)).toString());

        return null;
    }

    public static LambdaExpression parse(String expression) throws MismatchedBracketException {
        try {
            return parse(new Lexer(new StringReader(expression)));
        } catch (IOException e) {  // shouldn't happen
            e.printStackTrace();
            return null;
        }
    }
}
