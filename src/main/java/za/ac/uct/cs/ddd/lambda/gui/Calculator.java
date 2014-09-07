package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JPanel implements ActionListener{

    JTextField lambdaInputField;
    JButton reduceButton;
    JTextArea outputArea;
    JButton menuButton;

    public Calculator(ActionListener parentFrame) {
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("Calculator");
        title.setLocation(350, 50);
        title.setSize(150, 50);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        add(title);

        lambdaInputField = new JTextField("<<enter lambda expression here>>");
        lambdaInputField.setLocation(200, 125);
        lambdaInputField.setSize(300, 30);
        lambdaInputField.setFont(new Font("Serif", Font.PLAIN, 14));
        add(lambdaInputField);

        reduceButton = new JButton("Reduce");
        reduceButton.setLocation(550, 125);
        reduceButton.setSize(100, 30);
        reduceButton.setFont(new Font("Serif", Font.PLAIN, 14));
        reduceButton.addActionListener(this);
        add(reduceButton);

        outputArea = new JTextArea();
        outputArea.setLocation(200, 175);
        outputArea.setSize(450, 150);
        //JTextArea.setFont(new Font("Serif", Font.PLAIN, 26));
        add(outputArea);

        menuButton = new JButton("Main menu");
        menuButton.setLocation(200, 350);
        menuButton.setSize(150, 30);
        menuButton.addActionListener(parentFrame);
        menuButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(menuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if(e.getActionCommand() == "Reduce"){
            //outputArea.setText("reduce pushed");
            try{
                LambdaExpression expression = Parser.parse(lambdaInputField.getText());
                outputArea.setText(expression.toString());
            }catch (InvalidExpressionException ex){
                ex.printStackTrace();
                outputArea.setText("Invalid expression found.");
            }
        }
    }
}
