package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel{

    public MainMenu(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null); //new BorderLayout(5, 10)

        JLabel title = new JLabel("\\ambda");
        title.setLocation(350, 50);
        title.setSize(100, 50);
        add(title);

        JButton calculatorButton = new JButton("Calculator");
        calculatorButton.setLocation(300, 150);
        calculatorButton.setSize(150, 25);
        calculatorButton.addActionListener(parentFrame);
        add(calculatorButton);

        JButton tutorButton = new JButton("Tutor");
        tutorButton.setLocation(300, 200);
        tutorButton.setSize(150, 25);
        add(tutorButton);

        JButton markerButton = new JButton("Marker");
        markerButton.setLocation(300, 250);
        markerButton.setSize(150, 25);
        add(markerButton);

        setVisible(true);
    }
}