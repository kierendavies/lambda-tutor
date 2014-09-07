package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.Parser;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CalcController {
    private CalcView view;


    public CalcController(CalcView v) {
        view = v;
    //... Add listeners to the view
     view.addReduceListener(new ReduceListener());
     }

    class ReduceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String userInput = "";
            ReductionOrder order;
            try {
                userInput = view.getExpression();
                order = view.getOrder();
                LambdaExpression expr = Parser.parse(userInput);
                view.setReductionSteps(expr.reductions(order));

            } catch (InvalidExpressionException ex ) {
                ex.printStackTrace();
            }

        }
    }

}
