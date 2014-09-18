package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.ReductionOrder;
import za.ac.uct.cs.ddd.lambda.evaluator.ReductionResult;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

class CalcView extends ContentWindow {

    //... Components
    ReductionOrder order = ReductionOrder.NORMAL;
    JTextField lambdaInputField;
    JButton reduceButton;
    JTextArea outputArea;
    JRadioButton normalButton, applicativeButton;
    JScrollPane scrollPane;

    public CalcView(Window parent) {
        super(parent);

        //==========================================================
        lambdaInputField = new JTextField("<<enter lambda expression here>>");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 30;
        gbc.weighty = 0.75;
        gbc.insets = new Insets(10, 10, 0, 10);
        AbstractDocument doc = (AbstractDocument) lambdaInputField.getDocument();
        doc.setDocumentFilter(new lambdaDocFilter());
        add(lambdaInputField, gbc);
        //End of setting up JTextField --------

        outputArea = new JTextArea(5, 20);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        doc = (AbstractDocument) outputArea.getDocument();
        doc.setDocumentFilter(new lambdaDocFilter());
        scrollPane = new JScrollPane(outputArea);
        add(scrollPane, gbc);
        //End of setting up JScrollPane --------

        JPanel radioPane = new JPanel(new GridLayout(0, 1));
        normalButton = new JRadioButton("Normal Form");
        applicativeButton = new JRadioButton("Applicative Form");
        normalButton.setFont(new Font("Serif", Font.PLAIN, 14));
        applicativeButton.setFont(new Font("Serif", Font.PLAIN, 14));
        radioPane.add(normalButton);
        radioPane.add(applicativeButton);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 5;
        add(radioPane, gbc);
        //End of setting up JPanel --------

        ButtonGroup group = new ButtonGroup();
        group.add(normalButton);
        group.add(applicativeButton);
        //End of setting up ButtonGroup --------

        reduceButton = new JButton("Reduce");
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        add(reduceButton, gbc);
        //End of setting up JButton

    }

    public String getExpression() {
        return lambdaInputField.getText();
    }

    public ReductionOrder getOrder() {
        return order;

    }

    public void setOrder(ReductionOrder ord) {
        order = ord;
    }

    void setReductionSteps(List<ReductionResult> reductionSteps) {
        String output = "";
        for (ReductionResult step : reductionSteps) {
            output += step.toString() + "\n";


        }
        outputArea.setText(output);
    }

    void addReduceListener(ActionListener actionListener) {
        reduceButton.addActionListener(actionListener);
    }

    /* applicativeButton.putClientProperty("ORDER", ReductionOrder.APPLICATIVE);
    normalButton.putClientProperty("ORDER", ReductionOrder.NORMAL);*/


    private class lambdaDocFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.replaceAll("\\\\", "\u03bb"), attr);

        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
            fb.replace(offset, length, text.replaceAll("\\\\", "\u03bb"), attr);

        }
    }
}
