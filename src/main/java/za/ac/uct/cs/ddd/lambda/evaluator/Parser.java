package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.io.StringReader;

public class Parser {
    public static LambdaExpression parse(Lexer lexer) throws IOException, MismatchedBracketException {
        // generate tree of bracketed expressions
        BracketedExpression bracketedExpressions = new BracketedExpression(lexer);

        // check that there are no extra closing brackets
        Token nextToken = lexer.next();
        if (nextToken.getType() != TokenType.END_OF_FILE) {
            throw new MismatchedBracketException("Expected end of expression at line " + nextToken.getLine() +
                                                 " column " + nextToken.getColumn() +
                                                 ", found " + nextToken);
        }

        System.out.println(bracketedExpressions.toString());

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
