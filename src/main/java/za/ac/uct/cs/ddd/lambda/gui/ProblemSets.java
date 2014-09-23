package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ProblemSets extends JPanel {
    private Vector<ProblemSet> problemSets;

    private JComboBox<ProblemSet> problemSetComboBox;
    private JPanel body;
    private CardLayout bodyLayout;

    public ProblemSets() {
        problemSets = readProblemSets();

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(3, 3, 3, 3);

        add(new JLabel("Problem set:"), constraints);

        constraints.gridx = 1;
        problemSetComboBox = new JComboBox<>(problemSets);
        problemSetComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bodyLayout.show(body, problemSetComboBox.getSelectedItem().toString());
            }
        });
        add(problemSetComboBox, constraints);

        constraints.gridx = 2;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        add(Box.createHorizontalGlue(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        body = new JPanel();
        bodyLayout = new CardLayout();
        body.setLayout(bodyLayout);
        add(body, constraints);

        for (ProblemSet problemSet : problemSets) {
            body.add(new ProblemSetPanel(problemSet), problemSet.toString());
        }

    }

    private Vector<ProblemSet> readProblemSets() {
        Vector<ProblemSet> problemSets = new Vector<>();

        try {
            problemSets.add(new ProblemSet(getClass().getResource("/problemsets/intro.xml")));
            problemSets.add(new ProblemSet(getClass().getResource("/problemsets/orders.xml")));
            problemSets.add(new ProblemSet(getClass().getResource("/problemsets/combinators.xml")));
        } catch (IOException ignored) {}

        return problemSets;
    }
}
