package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Tutor extends JPanel implements ActionListener {

    String[] problems = new String[5];
    JComboBox<String> problemList;
    JLabel problemTitleLabel;
    JLabel problemTextLabel;

    public Tutor(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null);

        // set up problems
        String[] problemTitles = new String[problems.length];
        for (int i = 0; i < problems.length; i++) {
            problemTitles[i] = "Problem " + (i+1);
            if(i == 0){
                problems[0] = "\\x.(x y) z";
            } else{
                problems[i] = "<<no problem inserted yet>>";
            }

            /*switch (i){
                case 0: problems[i] = "\\x.(x y) z";
                default: problems[i] = "<<no problem inserted yet>>";
            }*/
        }

        JLabel title = new JLabel("Tutor");
        title.setLocation(350, 50);
        title.setSize(150, 50);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        add(title);

        problemList = new JComboBox<String>(problemTitles);
        problemList.setLocation(200, 125);
        problemList.setSize(150, 30);
        problemList.setFont(new Font("Serif", Font.PLAIN, 14));
        problemList.addActionListener(this);
        add(problemList);

        problemTitleLabel = new JLabel(problemList.getItemAt(0));
        problemTitleLabel.setLocation(200, 175);
        problemTitleLabel.setSize(150, 30);
        problemTitleLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        add(problemTitleLabel);

        JButton checkAnswerButton = new JButton("Check Answer");
        checkAnswerButton.setLocation(375, 175);
        checkAnswerButton.setSize(150, 30);
        checkAnswerButton.addActionListener(this);
        checkAnswerButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(checkAnswerButton);

        problemTextLabel = new JLabel(problems[0]);
        problemTextLabel.setLocation(200, 200);
        problemTextLabel.setSize(150, 30);
        problemTextLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        add(problemTextLabel);

        int numFields = 3;
        ArrayList<JTextField> textFields = new ArrayList<JTextField>(numFields);
        for (int i = 0; i < numFields; i++) {
            JTextField textField = new JTextField();
            textField.setLocation(200, 250+i*50);
            textField.setSize(325, 30);
            textField.setFont(new Font("Serif", Font.PLAIN, 14));
            textFields.add(i, textField);
            add(textField);
        }

        JButton menuButton = new JButton("Main menu");
        menuButton.setLocation(200, 400);
        menuButton.setSize(150, 30);
        menuButton.addActionListener(parentFrame);
        menuButton.setFont(new Font("Serif", Font.PLAIN, 14));
        add(menuButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand == "comboBoxChanged"){
            problemTitleLabel.setText(problemList.getItemAt(problemList.getSelectedIndex()));
            problemTextLabel.setText(problems[problemList.getSelectedIndex()]);
        }

    }
}
