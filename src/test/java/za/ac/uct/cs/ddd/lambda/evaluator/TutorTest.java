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
import za.ac.uct.cs.ddd.lambda.tutor.SimplificationProblem;

import static org.junit.Assert.fail;

public class TutorTest {
    private void assertReductionValid(String startExpression, String reduction){
        try{
            SimplificationProblem problem = new SimplificationProblem(startExpression, ReductionOrder.NORMAL);
            if(!problem.submitStep(reduction))
                fail("Invalid reduction: "+reduction+" for expression "+startExpression);
        }catch (InvalidExpressionException e){
            fail("Beginning expression invalid: "+ startExpression);
        }
    }

    private void assertReductionInvalid(String startExpression, String reduction) {
        try {
            SimplificationProblem problem = new SimplificationProblem(startExpression, ReductionOrder.NORMAL);
            if(problem.submitStep(reduction))
                fail("Valid reduction: "+reduction+" for expression "+startExpression);
        } catch (InvalidExpressionException e) {
            fail("Beginning expression invalid: "+ startExpression);
        }
    }

    @Test
    public void testSimpleReductions() throws Exception {
        assertReductionValid("(\\x.x) y", "y");
    }

    @Test
    public void testEmptyReductionInvalid() throws Exception {
        assertReductionInvalid("(\\x.x) y", "");
    }
}
