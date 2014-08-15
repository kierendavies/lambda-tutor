package za.ac.uct.cs.ddd.lambda.evaluator;

import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaExpressionTest {
    @Test
    public void testHasMatchedBrackets() {
        assertTrue(LambdaExpression.hasMatchedBrackets(""));
        assertTrue(LambdaExpression.hasMatchedBrackets("()"));
        assertTrue(LambdaExpression.hasMatchedBrackets("(foo (bar baz))"));

        assertFalse(LambdaExpression.hasMatchedBrackets("("));
        assertFalse(LambdaExpression.hasMatchedBrackets(")"));
        assertFalse(LambdaExpression.hasMatchedBrackets(")("));
        assertFalse(LambdaExpression.hasMatchedBrackets("())"));
        assertFalse(LambdaExpression.hasMatchedBrackets("(]"));
    }

    @Test
    public void testIsAllBracketed() {
        assertTrue(LambdaExpression.isAllBracketed("()"));
        assertTrue(LambdaExpression.isAllBracketed("(foo)"));
        assertTrue(LambdaExpression.isAllBracketed("((((foo))))"));
        assertTrue(LambdaExpression.isAllBracketed("((foo) (bar))"));

        assertFalse(LambdaExpression.isAllBracketed("(foo) bar"));
        assertFalse(LambdaExpression.isAllBracketed("foo (bar)"));
        assertFalse(LambdaExpression.isAllBracketed("(foo) (bar)"));
    }

    @Test
    public void testStripOuterBrackets() {
        assertEquals(LambdaExpression.stripOuterBrackets("foo"), "foo");
        assertEquals(LambdaExpression.stripOuterBrackets("(foo)"), "foo");
        assertEquals(LambdaExpression.stripOuterBrackets("((foo))"), "foo");
        assertEquals(LambdaExpression.stripOuterBrackets("((foo) (bar))"), "(foo) (bar)");
    }

    // TODO test other helper methods

    @Test
    public void parsingExpressionDoesNotThrowExceptions() {
        // TODO I can't understand this JUnit exception expecting
    }
}