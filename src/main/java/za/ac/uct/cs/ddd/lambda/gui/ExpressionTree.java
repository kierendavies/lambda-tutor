package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.LambdaAbstraction;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaApplication;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaExpression;
import za.ac.uct.cs.ddd.lambda.evaluator.LambdaVariable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionTree extends JPanel {
    private static final String COLLAPSED = "collapsed";
    private static final String EXPANDED = "expanded";
    private LambdaExpression expression;
    private CardLayout layout;
    private JPanel collapsed, expanded;

    public ExpressionTree() {
        super();
        layout = new CardLayout();
        setLayout(layout);
    }

    public ExpressionTree(LambdaExpression expression) {
        this();
        this.expression = expression;

        initCollapsed();
        add(collapsed, COLLAPSED);

        expanded = null;
    }

    public void setExpression(LambdaExpression expression) {
        this.expression = expression;
        initCollapsed();
        add(collapsed, COLLAPSED);
        collapse();
        expanded = null;
    }

    private void initCollapsed() {
        collapsed = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.weightx = 0;
        constraints.weighty = 0;

        JButton button = new JButton(Icons.expand);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(17, 17));
        button.addActionListener(event -> expand());
        collapsed.add(button, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.LINE_START;
        JLabel expressionLabel = new JLabel(expression.toString());
        expressionLabel.setFont(Fonts.mono);
        collapsed.add(expressionLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        collapsed.add(Box.createGlue(), constraints);
    }

    private void initExpanded() {
        expanded = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.weightx = 0;
        constraints.weighty = 0;

        JButton button = new JButton(Icons.collapse);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(17, 17));
        button.addActionListener(event -> collapse());
        expanded.add(button, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.LINE_START;
        JLabel expressionLabel = new JLabel(expression.toString());
        expressionLabel.setFont(Fonts.mono);
        expanded.add(expressionLabel, constraints);

//        constraints.gridy++;
//        constraints.gridwidth = 1;
//        expanded.add(new JLabel("Free variables:"), constraints);
//        constraints.gridx = 2;
//        constraints.gridwidth = GridBagConstraints.REMAINDER;
//        expanded.add(new JLabel(expression.getFreeVariables().toString()), constraints);

        constraints.gridx = 1;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        if (expression instanceof LambdaAbstraction) {
            LambdaAbstraction abstraction = (LambdaAbstraction) expression;

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Abstraction"), constraints);

            constraints.gridy++;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Variable:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            JLabel variableLabel = new JLabel(abstraction.getVariable().toString());
            variableLabel.setFont(Fonts.mono);
            expanded.add(variableLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy++;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Body:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(abstraction.getBody()), constraints);

        } else if (expression instanceof LambdaApplication) {
            LambdaApplication application = (LambdaApplication) expression;

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Application"), constraints);

            constraints.gridy++;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Function:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(application.getFunction()), constraints);

            constraints.gridx = 1;
            constraints.gridy++;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Input:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(application.getInput()), constraints);

        } else if (expression instanceof LambdaVariable) {
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Variable"), constraints);
        }

        constraints.gridx = 3;
        constraints.gridy++;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        expanded.add(Box.createGlue(), constraints);
    }

    public void expand() {
        if (expanded == null) {
            initExpanded();
            add(expanded, EXPANDED);
        }
        layout.show(this, EXPANDED);
        setPreferredSize(null);
    }

    public void collapse() {
        layout.show(this, COLLAPSED);
        setPreferredSize(collapsed.getPreferredSize());
    }
}
