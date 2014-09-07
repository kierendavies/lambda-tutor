package za.ac.uct.cs.ddd.lambda.evaluator;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import static za.ac.uct.cs.ddd.lambda.evaluator.TokenType.*;

/**
 * A collection of static methods for parsing lambda expressions.
 */
public class Parser {
    /**
     * Consumes tokens from a lexer until EOF is reached, and parses them into a semantically structured lambda
     * expression.
     * @param lexer The lexer from which to consume tokens
     * @return The lambda expression
     * @throws IOException If the lexer encounters an IO error
     * @throws InvalidExpressionException If the lexer does not produce a valid lambda expression
     */
    public static LambdaExpression parse(Lexer lexer) throws IOException, InvalidExpressionException {
        // generate tree of bracketed expressions
        BracketedExpression bracketedExpression = parseBrackets(lexer);

        // check that there are no extra closing brackets
        Token nextToken = lexer.next();
        if (nextToken.getType() != TokenType.END_OF_FILE) {
            throw new MismatchedBracketException("Expected end of expression, found " + nextToken,
                    nextToken.getLine(), nextToken.getColumn());
        }

        // parse lambda forms
        LambdaExpression lambdaExpression = parseLambda(bracketedExpression);

        return lambdaExpression;
    }

    /**
     * Parses a string into a semantically structured lambda expression.
     * @param expression The string to parse
     * @return The lambda expression
     * @throws InvalidExpressionException If the string is not a valid lambda expression
     */
    public static LambdaExpression parse(String expression) throws InvalidExpressionException {
        try {
            return parse(new Lexer(new StringReader(expression)));
        } catch (IOException e) {  // shouldn't happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given a lexer, produces a bracketed expression by consuming tokens until the correct closing bracket is found or
     * end of stream is reached.  Assumes the opening bracket (if any) has already been consumed, and replaces the
     * closing bracket to be consumed by the caller.
     * @param lexer The lexer from which to consume tokens
     * @return A nested sequence of tokens
     * @throws IOException If the lexer encounters an IO error
     * @throws InvalidExpressionException If the lexer does not produce a correctly bracketed expression
     */
    private static BracketedExpression parseBrackets(Lexer lexer) throws IOException, InvalidExpressionException {
        Token token = lexer.next();
        BracketedExpression be = new BracketedExpression(token.getLine(), token.getColumn());

        // read until EOF or closing bracket, and add to tokens list
        while (token.getType() != END_OF_FILE && token.getType() != CLOSING_BRACKET) {
            if (token.getType() == OPENING_BRACKET) {
                be.addToken(parseBrackets(lexer));
                token = lexer.next();
                if (token.getType() != CLOSING_BRACKET) {  // shouldn't happen
                    throw new MismatchedBracketException("Expected closing bracket, found " + token,
                            token.getLine(), token.getColumn());
                }
            } else {
                be.addToken(token);
            }
            token = lexer.next();
        }

        // put that closing bracket back to be consumed by parent
        if (token.getType() == CLOSING_BRACKET) {
            lexer.yypushback(1);
        }

        // remove pointless nesting
        if (be.getTokens().size() == 1 && be.getTokens().get(0) instanceof BracketedExpression) {
            be.hoistOnlyChild();
        }
        for (int i = 0; i < be.getTokens().size(); i++) {
            if (be.getTokens().get(i) instanceof BracketedExpression) {
                BracketedExpression child = (BracketedExpression) be.getTokens().get(i);
                if (child.getTokens().size() == 1) {
                    be.hoistSingletonChild(i);
                }
            }
        }

        return be;
    }

    /**
     * Parses a token, which may be a BracketedExpression, into a semantically structured lambda expression, where
     * variables are taken from the given scope.
     * @param token The token to parse
     * @param scope The variables which are currently in scope
     * @return The lambda expression
     * @throws InvalidExpressionException If the token does not form a valid lambda expression
     */
    private static LambdaExpression parseLambda(Token token, Scope scope) throws InvalidExpressionException {
        if (token instanceof BracketedExpression) {
            BracketedExpression be = (BracketedExpression) token;
            if (be.getTokens().size() == 0) {
                throw new InvalidExpressionException("Empty expression", be.getLine(), be.getColumn());
            }
            return parseLambda(be.getTokens(), scope);
        } else {
            if (token.getType() != IDENTIFIER) {
                // if a singleton was anything other than an identifier, it should have been hoisted
                throw new InvalidExpressionException("Expected identifier, found " + token,
                        token.getLine(), token.getColumn());
            }
            return scope.getOrAddNew(token.getContent());
        }
    }

    /**
     * Parses a token, which may be a BracketedExpression, into a semantically structured lambda expression.
     * @param token The token to parse
     * @return The lambda expression
     * @throws InvalidExpressionException If the token does not form a valid lambda expression
     */
    private static LambdaExpression parseLambda(Token token) throws InvalidExpressionException {
        return parseLambda(token, new Scope());
    }

    /**
     * Parses a list of tokens into a semantically structured lambda expression, where variables are taken from the
     * given scope.
     * @param tokens The list of tokens to parse
     * @param scope The variables which are currently in scope
     * @return The lambda expression
     * @throws InvalidExpressionException If the tokens do not form a valid lambda expression
     */
    private static LambdaExpression parseLambda(List<Token> tokens, Scope scope) throws InvalidExpressionException {
        if (tokens.size() == 0) {
            throw new InvalidExpressionException("Empty expression");
        }

        if (tokens.size() == 1) {  // parse single identifier
            return parseLambda(tokens.get(0), scope);

        } else if (tokens.get(0).getType() == LAMBDA) {  // parse abstraction
            int arrowIndex = -1;
            for (int i = 1; i < tokens.size()-1; i++) {
                Token token = tokens.get(i);
                if (token.getType() == ARROW) {
                    arrowIndex = i;
                    break;
                }
            }
            if (arrowIndex == 1) {
                Token token = tokens.get(1);
                throw new InvalidExpressionException("Missing variable binding",
                        token.getLine(), token.getColumn());
            } else if (arrowIndex == -1) {
                Token token = tokens.get(tokens.size() - 1);
                throw new InvalidExpressionException("Missing abstraction body",
                        token.getLine(), (token.getColumn() + token.getLength()));
            }

            List<LambdaVariable> variables = new LinkedList<LambdaVariable>();
            for (Token token : tokens.subList(1, arrowIndex)) {
                if (token.getType() != IDENTIFIER) {
                    throw new InvalidExpressionException("Expected identifier, found " + token,
                            token.getLine(), token.getColumn());
                }
                LambdaVariable variable = new LambdaVariable(token.getContent());
                variables.add(variable);
                scope.add(variable);
            }
            LambdaExpression body = parseLambda(tokens.subList(arrowIndex + 1, tokens.size()), scope);

            // clean up scope
            for (LambdaVariable variable : variables) {
                scope.remove(variable);
            }

            return new LambdaAbstraction(variables, body);

        } else {  // parse application
            List<LambdaExpression> expressions = new LinkedList<LambdaExpression>();

            for (int i = 0; i < tokens.size(); i++) {
                Token token = tokens.get(i);
                if (token.getType() == LAMBDA) {
                    expressions.add(parseLambda(tokens.subList(i, tokens.size()), scope));
                    break;
                } else {
                    expressions.add(parseLambda(token, scope));
                }
            }

            return new LambdaApplication(expressions);
        }
    }

    /**
     * Parses a list of tokens into a semantically structured lambda expression.
     * @param tokens The list of tokens to parse
     * @return The lambda expression
     * @throws InvalidExpressionException If the tokens do not form a valid lambda expression
     */
    private static LambdaExpression parseLambda(List<Token> tokens) throws InvalidExpressionException {
        return parseLambda(tokens, new Scope());
    }
}