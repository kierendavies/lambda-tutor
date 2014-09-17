package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

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
    private static ImageIcon expandIcon = null;
    private static ImageIcon collapseIcon = null;

    public ExpressionTree() {
        super(new CardLayout());
        layout = (CardLayout) getLayout();

        if (expandIcon == null) {
            expandIcon = new ImageIcon(getClass().getResource("/icon_expand.gif"), "expand");
        }
        if (collapseIcon == null) {
            collapseIcon = new ImageIcon(getClass().getResource("/icon_collapse.gif"), "collapse");
        }
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

        JButton button = new JButton(expandIcon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(17, 17));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expand();
            }
        });
        collapsed.add(button, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.LINE_START;
        collapsed.add(new JLabel(expression.toString()), constraints);

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

        JButton button = new JButton(collapseIcon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(17, 17));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collapse();
            }
        });
        expanded.add(button, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.LINE_START;
        expanded.add(new JLabel(expression.toString()), constraints);

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridy = 1;
        if (expression instanceof LambdaAbstraction) {
            LambdaAbstraction abstraction = (LambdaAbstraction) expression;

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Abstraction"), constraints);

            constraints.gridy = 2;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Variable:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel(abstraction.getVariable().toString()), constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Body:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(abstraction.getBody()), constraints);

        } else if (expression instanceof LambdaApplication) {
            LambdaApplication application = (LambdaApplication) expression;

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Application"), constraints);

            constraints.gridy = 2;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Function:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(application.getFunction()), constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.gridwidth = 1;
            expanded.add(new JLabel("Input:"), constraints);
            constraints.gridx = 2;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new ExpressionTree(application.getInput()), constraints);

        } else if (expression instanceof LambdaVariable) {
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            expanded.add(new JLabel("Variable"), constraints);
        }

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
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
