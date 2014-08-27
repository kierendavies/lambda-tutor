package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.*;

class BracketedExpression extends Token {
    private List<Token> tokens;

    public BracketedExpression() {
        tokens = new ArrayList<Token>();
    }

    void addToken(Token token) {
        tokens.add(token);
    }

    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    void hoistOnlyChild() {
        tokens = ((BracketedExpression) tokens.get(0)).tokens;
    }

    void hoistSingletonChild(int index) {
        if (!(tokens.get(index) instanceof BracketedExpression)) {
            // let's try not to reach this
            throw new RuntimeException("Child is not a bracketed expression");
        }
        tokens.set(index, ((BracketedExpression) tokens.get(index)).getTokens().get(0));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("( ");
        for (Token token : tokens) {
            builder.append(token.toString());
            builder.append(' ');
        }
        builder.append(')');
        return builder.toString();
    }
}
