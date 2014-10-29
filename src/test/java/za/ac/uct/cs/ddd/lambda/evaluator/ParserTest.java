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