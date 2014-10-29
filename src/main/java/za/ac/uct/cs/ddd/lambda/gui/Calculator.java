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

package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;

public class Calculator extends JPanel {
    private static final String ERROR = "error";
    private static final String EXPRESSION = "expression";

    private HashSet<String> history;
    private LambdaExpression expression;

    private JComboBox<String> inputBox;
    private JPanel body;
    private CardLayout bodyLayout;
    private JLabel errorLabel;
    private ExpressionTree expressionTree;
    private ReductionOrder reductionOrder;
    private JPanel reductionPanel;

    public Calculator() {
        super();

        setLayout(new GridBagLayout());

        history = new HashSet<String>();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 1;
        constraints.weighty = 0;

        inputBox = new JComboBox<String>();
        inputBox.setEditable(true);
        inputBox.setFont(Fonts.mono);
        inputBox.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        PlainDocument doc = (PlainDocument) ((JTextComponent) inputBox.getEditor().getEditorComponent()).getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, text.replaceAll("\\\\", "\u03bb"), attr);
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
                fb.replace(offset, length, text.replaceAll("\\\\", "\u03bb"), attr);
            }
        });
        add(inputBox, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(calculateButton.getMinimumSize());
        calculateButton.addActionListener(event -> setInput((String) inputBox.getSelectedItem()));
        add(calculateButton, constraints);

        body = new JPanel(new CardLayout());
        bodyLayout = (CardLayout) body.getLayout();

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
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

        JPanel expressionPanel = new JPanel(new GridBagLayout());

        constraints.gridy = 0;
        expressionTree = new ExpressionTree();
        expressionPanel.add(new JScrollPane(expressionTree), constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        JButton reduceButton = new JButton("Reduce");
        reduceButton.setPreferredSize(reduceButton.getMinimumSize());
        reduceButton.addActionListener(event -> reduce());
        expressionPanel.add(reduceButton, constraints);

        ButtonGroup orderButtons = new ButtonGroup();
        constraints.gridx = 1;
        JRadioButton applicativeOrderButton = new JRadioButton("Applicative order");
        applicativeOrderButton.addActionListener(event -> reductionOrder = ReductionOrder.APPLICATIVE);
        orderButtons.add(applicativeOrderButton);
        expressionPanel.add(applicativeOrderButton, constraints);
        constraints.gridx = 2;
        JRadioButton normalOrderButton = new JRadioButton("Normal order");
        normalOrderButton.addActionListener(e -> reductionOrder = ReductionOrder.NORMAL);
        orderButtons.add(normalOrderButton);
        expressionPanel.add(normalOrderButton, constraints);

        constraints.gridx = 3;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        expressionPanel.add(Box.createHorizontalGlue(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        reductionPanel = new JPanel();
        reductionPanel.setLayout(new GridBagLayout());
        expressionPanel.add(new JScrollPane(reductionPanel), constraints);

        body.add(expressionPanel, EXPRESSION);
    }

    public static void main(String[] args) throws InvalidExpressionException, MalformedURLException {
        JFrame frame = new JFrame();
        frame.add(new Calculator());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setInput(String input) {
        if (input == null) return;

        history.add(input);
        if (history.contains(input)) {
            inputBox.removeItem(input);
        }
        inputBox.insertItemAt(input, 0);
        inputBox.setSelectedItem(input);

        try {
            expression = Parser.parse(input);
            expressionTree.setExpression(expression);
            reductionPanel.removeAll();
            bodyLayout.show(body, EXPRESSION);
        } catch (InvalidExpressionException exception) {
            errorLabel.setText("Syntax error: " + exception.getMessage());
            bodyLayout.show(body, ERROR);
        }
    }

    public void reduce() {
        if (reductionOrder == null) return;

        reductionPanel.removeAll();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 0;
        constraints.weighty = 0;

        List<ReductionResult> reductions = expression.reductions(reductionOrder);
        if (reductions.isEmpty()) {
            reductionPanel.add(new JLabel("No reductions"), constraints);
            constraints.gridy++;
        } else {
            for (ReductionResult reduction : reductions) {
                String reductionString = reduction.getReducedExpression().toString();

                constraints.gridx = 0;
                reductionPanel.add(new JLabel(reduction.getType().toString()), constraints);

                constraints.gridx++;
                reductionPanel.add(Box.createHorizontalStrut(0), constraints);

                constraints.gridx++;
                JButton button = new JButton(Icons.setInput);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setPreferredSize(new Dimension(17, 17));
                button.addActionListener(event -> setInput(reductionString));
                reductionPanel.add(button, constraints);

                constraints.gridx++;
                JLabel reductionLabel = new JLabel(reductionString);
                reductionLabel.setFont(Fonts.mono);
                reductionPanel.add(reductionLabel, constraints);

                constraints.gridy++;
            }
        }

        constraints.gridx = 4;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        reductionPanel.add(Box.createGlue(), constraints);

        body.revalidate();
    }
}
