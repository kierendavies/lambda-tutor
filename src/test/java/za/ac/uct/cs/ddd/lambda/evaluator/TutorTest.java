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
