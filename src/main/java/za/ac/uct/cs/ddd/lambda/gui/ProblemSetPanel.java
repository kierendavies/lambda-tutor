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

import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;
import za.ac.uct.cs.ddd.lambda.tutor.SimplificationProblem;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class ProblemSetPanel extends JPanel {
    private ProblemSet problemSet;
    private Problem problem;
    private JPanel submissionsPanel;
    private JTextField submissionTextField;
    private JButton submitButton;
    private JLabel submissionFeedback;
    private int submissionRow;

    public ProblemSetPanel(ProblemSet problemSet) {
        this.problemSet = problemSet;

        setLayout(new GridBagLayout());

        loadProblem(problemSet.nextProblem());
    }

    private void loadProblem(Problem problem) {
        this.problem = problem;

        removeAll();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 1;
        constraints.weighty = 0;

        // Add problem description
        if (problem instanceof SimplificationProblem) {
            add(new JLabel(String.format("Reduce the following expression by %s order:", problem.getReductionOrder())), constraints);
            constraints.gridy++;

            JLabel expressionLabel = new JLabel(problem.getExpression().toString());
            expressionLabel.setFont(Fonts.mono);
            add(expressionLabel, constraints);
        }

        constraints.gridy++;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        submissionsPanel = new JPanel(new GridBagLayout());
        submissionRow = 0;

        constraints.gridy = 1000;  // below everything else
        submissionsPanel.add(Box.createVerticalGlue(), constraints);

        add(new JScrollPane(submissionsPanel), constraints);
        addSubmissionLine();

        revalidate();
    }

    private void addSubmissionLine() {
        if (submissionTextField != null) {
            submissionTextField.setEditable(false);
        }
        if (submitButton != null) {
            submissionsPanel.remove(submitButton);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = submissionRow;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 1;
        constraints.weighty = 0;

        submissionTextField = new JTextField();
        submissionTextField.setFont(Fonts.mono);
        ((AbstractDocument) submissionTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, text.replaceAll("\\\\", "\u03bb"), attr);
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
                fb.replace(offset, length, text.replaceAll("\\\\", "\u03bb"), attr);
            }
        });
        submissionTextField.addActionListener(event -> submit());
        submissionsPanel.add(submissionTextField, constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        submitButton = new JButton("Submit");
        submitButton.addActionListener(event -> submit());
        submissionsPanel.add(submitButton, constraints);

        Dimension size = submissionTextField.getPreferredSize();
        size.height = submitButton.getPreferredSize().height;
        submissionTextField.setPreferredSize(size);

        submissionRow++;
        constraints.gridy++;
        constraints.gridx = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        submissionFeedback = new JLabel();
        submissionsPanel.add(submissionFeedback, constraints);

        submissionRow++;
        constraints.gridy++;
    }

    private void addNextProblemLine() {
        if (submissionTextField != null) {
            submissionTextField.setEditable(false);
        }
        if (submitButton != null) {
            submissionsPanel.remove(submitButton);
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = submissionRow;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.weightx = 1;
        constraints.weighty = 0;

        Problem nextProblem = problemSet.nextProblem();
        if (nextProblem == null) {
            submissionsPanel.add(new JLabel("All problems complete!"), constraints);
        } else {
            submissionsPanel.add(new JLabel("Problem complete!"), constraints);

            constraints.gridy++;
            JButton nextProblemButton = new JButton("Next problem");
            nextProblemButton.addActionListener(event -> loadProblem(problemSet.nextProblem()));
            submissionsPanel.add(nextProblemButton, constraints);
        }
    }

    private void submit() {
        boolean correct = problem.submitStep(submissionTextField.getText());

        if (correct) {
            submissionFeedback.setText("Correct!");
            if (problem.isComplete()) {
                addNextProblemLine();
            } else {
                addSubmissionLine();
            }
        } else {
            String message = problem.getMessages().get(problem.getMessages().size() - 1).split("\n")[0];
            submissionFeedback.setText("Incorrect: " + message);
        }
    }
}
