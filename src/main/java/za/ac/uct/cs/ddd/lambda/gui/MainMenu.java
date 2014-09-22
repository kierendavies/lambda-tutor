package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 *  Creates
 */
public class MainMenu extends SizablePanel {
    ActionListener parentFrame;


    public MainMenu(ActionListener l) {

        this.parentFrame = l;

        //Add title
        addTitle("\u03bbambda");

        //Add Border
        setBorder(new EmptyBorder(0, WIDTH / 4, HEIGHT / 8, WIDTH / 4));

        //Create and add button pane:
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));


        buttonPane.add(Box.createRigidArea(new Dimension(0, WIDTH / 50)));


        //Adding  buttons to the pane
        addButton("Calculator", buttonPane);
        addButton("Tutor", buttonPane);
        add(buttonPane);


        setVisible(true);
    }


    /**
     * Creates a button with text and adds it to the specified container
     * @param text the text of the button.
     * @param container The 
     */
    private void addButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension((2 * WIDTH) / 3, HEIGHT / 3));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(parentFrame);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0, WIDTH / 50)));


    }


}