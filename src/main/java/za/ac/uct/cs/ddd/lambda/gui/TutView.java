/*
 *
 *
 */
package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class TutView extends ContentWindow {

    JComboBox problemList;
    JLabel problemTitleLabel;
    JLabel problemTextLabel;
    JTextArea inputArea;
    JButton menuButton;

    public TutView(Window parent) {
        super(parent);


        inputArea = new JTextArea(5, 20);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 2;
        gbc.weighty = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(inputArea, gbc);


        JPanel comboPane = new JPanel(new BorderLayout());
        populateProblemSet(null);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        comboPane.add(problemList, BorderLayout.CENTER);
        add(comboPane, gbc);


    }

    //Not Happy

    void populateProblemSet(String fileName) {
        ProblemSet problemSet = new ProblemSet(fileName);

        problemList = new JComboBox(problemSet.getProblems().toArray());


    }

    private class ProblemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}