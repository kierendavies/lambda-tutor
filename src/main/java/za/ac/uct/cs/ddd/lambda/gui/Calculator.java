package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.InvalidExpressionException;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.Parser;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Calculator extends JPanel {
    private static final String ERROR = "error";
    private static final String EXPRESSION = "expression";

    private LambdaExpression expression;
    private HashSet<String> history;

    JComboBox<String> inputBox;
    JPanel body;
    CardLayout bodyLayout;
    JLabel errorLabel;
    ExpressionTree expressionTree;
//    GridBagConstraints constraints;

    public Calculator() {
        super();
        setLayout(new GridBagLayout());

        history = new HashSet<String>();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 1;
        constraints.weighty = 0;

        inputBox = new JComboBox<String>();
        inputBox.setEditable(true);
        inputBox.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        add(inputBox, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        JButton button = new JButton("Calculate");
        button.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        button.setPreferredSize(button.getMinimumSize());
        add(button, constraints);

        body = new JPanel(new CardLayout());
        bodyLayout = (CardLayout) body.getLayout();

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(body, constraints);

        JPanel errorPanel = new JPanel(new GridBagLayout());
        errorLabel = new JLabel("Waiting for input");
        constraints.gridy = 0;
        constraints.weighty = 0;
        errorPanel.add(errorLabel, constraints);
        constraints.gridy = 1;
        constraints.weighty = 1;
        errorPanel.add(Box.createGlue(), constraints);
        body.add(errorPanel, ERROR);
        bodyLayout.show(body, ERROR);

        constraints.gridy = 0;
        JPanel expressionPanel = new JPanel(new GridBagLayout());
        expressionTree = new ExpressionTree();
        expressionPanel.add(new JScrollPane(expressionTree), constraints);
        // TODO add reductions
        body.add(expressionPanel, EXPRESSION);
    }

    public void setInput(String input) {
        if (input == null) return;
        try {
            expression = Parser.parse(input);

            history.add(input);
            if (history.contains(input)) {
                inputBox.removeItem(input);
            }
            inputBox.insertItemAt(input, 0);

            expressionTree.setExpression(expression);
            bodyLayout.show(body, EXPRESSION);
        } catch (InvalidExpressionException exception) {
            errorLabel.setText("Syntax error: " + exception.getMessage());
            bodyLayout.show(body, ERROR);
        }
    }

    public static void main(String[] args) throws InvalidExpressionException, MalformedURLException {
        JFrame frame = new JFrame();
        frame.add(new Calculator());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
