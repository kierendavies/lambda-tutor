package za.ac.uct.cs.ddd.lambda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator extends JPanel{

    public Calculator(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("Calculator");
        title.setLocation(350, 50);
        title.setSize(new Dimension(100, 50));
        add(title);

        JTextField lambdaInputField = new JTextField("<<enter lambda expression here>>");
        lambdaInputField.setLocation(200, 125);
        lambdaInputField.setSize(300, 30);
        add(lambdaInputField);

        JButton reduceButton = new JButton("Reduce");
        reduceButton.setLocation(550, 125);
        reduceButton.setSize(100, 30);
        add(reduceButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setLocation(200, 175);
        outputArea.setSize(450, 50);
        add(outputArea);

        JButton menuButton = new JButton("Main menu");
        menuButton.setLocation(200, 250);
        menuButton.setSize(150, 30);
        menuButton.addActionListener(parentFrame);
        add(menuButton);
    }
}
