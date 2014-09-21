package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends SizeablePanel {
    ActionListener parentFrame;


    public MainMenu(ActionListener l) {

        this.parentFrame = l;

        //Add title
        addTitle("\u03bbambda");

        //Add Border
        setBorder(new EmptyBorder(0, width / 4, height / 8, width / 4));

        //Create and add button pane:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));


        buttonPane.add(Box.createRigidArea(new Dimension(0, width / 50)));


        //Adding  buttons to the pane
        addButton("Calculator", buttonPane);
        addButton("Tutor", buttonPane);
        addButton("Marker", buttonPane);
        add(buttonPane);


        setVisible(true);
    }


    private void addButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.PLAIN, 20));
        button.setMaximumSize(new Dimension((2 * width) / 3, height / 3));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(parentFrame);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0, width / 70)));


    }


}

