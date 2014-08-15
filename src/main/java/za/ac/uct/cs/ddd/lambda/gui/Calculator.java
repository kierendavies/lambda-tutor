package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JPanel implements ActionListener{

    JTextField lambdaInputField;
    JButton reduceButton;
    JTextArea outputArea;
    JButton menuButton;

    public Calculator(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("Calculator");
        title.setLocation(350, 50);
        title.setSize(new Dimension(100, 50));
        add(title);

        lambdaInputField = new JTextField("<<enter lambda expression here>>");
        lambdaInputField.setLocation(200, 125);
        lambdaInputField.setSize(300, 30);
        add(lambdaInputField);

        reduceButton = new JButton("Reduce");
        reduceButton.setLocation(550, 125);
        reduceButton.setSize(100, 30);
        reduceButton.addActionListener(this);
        add(reduceButton);

        outputArea = new JTextArea();
        outputArea.setLocation(200, 175);
        outputArea.setSize(450, 50);
        outputArea.setText("reduced expression appears here...");
        outputArea.setEnabled(false);
        add(outputArea);

        menuButton = new JButton("Main menu");
        menuButton.setLocation(200, 250);
        menuButton.setSize(150, 30);
        menuButton.addActionListener(parentFrame);
        add(menuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Reduce"){
            //outputArea.setText("reduce pushed");
            try{
                LambdaExpression expression = new LambdaExpression(lambdaInputField.getText());
                outputArea.setText(expression.toString());
            }catch (InvalidExpressionException ex){
                ex.printStackTrace();
                outputArea.setText("Invalid expression found.");
            }
        }
    }
}
