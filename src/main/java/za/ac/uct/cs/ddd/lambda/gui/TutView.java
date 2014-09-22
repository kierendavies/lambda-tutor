/*
 *
 *
 */
package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TutView extends ContentWindow {

    JScrollPane scrollPane;
    JLabel problem;
    ProblemSet problemSet;


    /**
     * Creates a new
     * @param parent
     */
    public  TutView (Window parent){
        super(parent);
    }

    /**
     * Reset the problem label to reflect the current problem
     */
    private void updateProblem(){
        problemSet.getProblem();
    }

    /*String[] problems = new String[5];
    JComboBox problemList;
    JLabel problemTitleLabel;
    JLabel problemTextLabel;
    JTextArea inputArea;
    JButton menuButton;*

    public TutView(Window parent, String file) {
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
        populateProblemSet(file);
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

        try {
            BufferedReader input = new BufferedReader((new FileReader(fileName)));
            List<String> set = new ArrayList<String>();
            String line = null;
            while ((line = input.readLine()) != null) {
                set.add(line);
            }
            String[] probSetArr = set.toArray(new String[]{});
            problemList = new JComboBox(probSetArr);
            input.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    } */
    //Not Happy

   /* void addComboBoxListener(ActionListener actionListener) {
        problemList.addActionListener(actionListener);
    }

   /* @Override

    }*/
}
