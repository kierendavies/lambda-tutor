package za.ac.uct.cs.ddd.lambda.evaluator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaExpressionTest {
    LambdaExpression Ix, Iy, xy;

    @Before
    public void setUp() throws Exception {
        Ix = Parser.parse("\\x.x");
        Iy = Parser.parse("\\y.y");
        xy = Parser.parse("\\x.y");
    }

    @Test
    public void testClone() throws Exception {
        assertNotSame(Ix, Ix.clone());
        assertTrue(Ix.alphaEquivalentTo(Ix.clone()));
    }

    @Test
    public void testAlphaEquivalentTo() throws Exception {
        assertTrue(Ix.alphaEquivalentTo(Iy));
        assertFalse(Ix.alphaEquivalentTo(xy));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(Ix.toString(), "\u03bbx.x");
        assertEquals(Ix.toString(true), "(\u03bbx.x)");
    }

    @Test
    public void testGetFreeVariables() throws Exception {
        assertFalse(Ix.getFreeVariables().contains("x"));
        assertFalse(xy.getFreeVariables().contains("x"));
        assertTrue(xy.getFreeVariables().contains("y"));
    }

    @Test
    public void testSubstitute() throws Exception {

    }

    @Test
    public void testReduceOnce() throws Exception {

    }

    @Test
    public void testReduce() throws Exception {

    }

    @Test
    public void testEquivalentTo() throws Exception {
        assertTrue(Ix.equivalentTo(Iy));
        assertFalse(Ix.equivalentTo(xy));
    }
}