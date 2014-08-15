package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator extends JPanel{

    public Calculator(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null);

        JLabel title = new JLabel("Calculator");
        title.setLocation(350, 50);
        title.setSize(150, 50);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        add(title);

        JTextField lambdaInputField = new JTextField("<<enter lambda expression here>>");
        lambdaInputField.setLocation(200, 125);
        lambdaInputField.setSize(300, 30);
        lambdaInputField.setFont(new Font("Serif", Font.PLAIN, 14));
        add(lambdaInputField);

        JButton reduceButton = new JButton("Reduce");
        reduceButton.setLocation(550, 125);
        reduceButton.setSize(100, 30);
        reduceButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(reduceButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setLocation(200, 175);
        outputArea.setSize(450, 150);
        //JTextArea.setFont(new Font("Serif", Font.PLAIN, 26));
        add(outputArea);

        JButton menuButton = new JButton("Main menu");
        menuButton.setLocation(200, 350);
        menuButton.setSize(150, 30);
        menuButton.addActionListener(parentFrame);
        menuButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(menuButton);
    }
}
