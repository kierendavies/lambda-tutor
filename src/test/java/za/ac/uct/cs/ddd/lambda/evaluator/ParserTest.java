package za.ac.uct.cs.ddd.lambda.evaluator;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    public void assertExpressionValid(String expression) {
        try {
            Parser.parse(expression);
        } catch (InvalidExpressionException e) {
            fail("Expression is valid: " + expression);
        }
    }

    public void assertExpressionInvalid(String expression) {
        try {
            Parser.parse(expression);
            fail("Expression is invalid: " + expression);
        } catch (InvalidExpressionException ignored) {}
    }

    @Test
    public void testEmptyExpressionInvalid() throws Exception {
        assertExpressionInvalid("");
        assertExpressionInvalid("()");
        assertExpressionInvalid("(())");
    }

    @Test
    public void testRejectMismatchedBrackets() throws Exception {
        assertExpressionInvalid("(");
        assertExpressionInvalid(")");
        assertExpressionInvalid(")(");
        assertExpressionInvalid("(()");
        assertExpressionInvalid("())");
    }

    @Test
    public void testAcceptSimpleExpressions() throws Exception {
        assertExpressionValid("x");
        assertExpressionValid("x x");
        assertExpressionValid("x y");
        assertExpressionValid("(x y)");
        assertExpressionValid("\\x.x");
        assertExpressionValid("\u03bbx.x");
        assertExpressionValid("\\x.y");
        assertExpressionValid("(\\x.y)");
    }

    @Test
    public void testAcceptComplexExpressions() throws Exception {
        assertExpressionValid("\\g.(\\x.g (x x)) (\\x.g (x x))");
        assertExpressionValid("(\\n.\\f.\\x.f (n f x)) (\\f.\\x.f x)");
        assertExpressionValid("(\\m.\\n.\\f.\\x.m f (n f x)) (\\f.\\x.f (f x)) (\\f.\\x.f (f (f x)))");
    }

    @Test
    public void testRejectInvalidExpressions() throws Exception {
        assertExpressionInvalid("\\");
        assertExpressionInvalid("\\x");
        assertExpressionInvalid("\\x.");
        assertExpressionInvalid("\\.x");
        assertExpressionInvalid("\\x.()");
        assertExpressionInvalid("\\(x).x");
        assertExpressionInvalid("\\x(.)x");
        assertExpressionInvalid("\\(x.x)");
        assertExpressionInvalid("x () x");
    }
}