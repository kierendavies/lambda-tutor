package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel{

    public MainMenu(ActionListener parentFrame){
        setSize(800, 600);
        setLayout(null); //new BorderLayout(5, 10)

        JLabel title = new JLabel("\\ambda");
        title.setLocation(350, 50);
        title.setSize(100, 50);
        title.setFont(new Font("Serif", Font.PLAIN, 26));
        add(title);

        JButton calculatorButton = new JButton("Calculator");
        calculatorButton.setLocation(250, 150);
        calculatorButton.setSize(300, 100);
        calculatorButton.setFont(new Font("Serif", Font.PLAIN, 20));
        calculatorButton.addActionListener(parentFrame);
        add(calculatorButton);

        JButton tutorButton = new JButton("Tutor");
        tutorButton.setLocation(250, 275);
        tutorButton.setSize(300, 100);
        tutorButton.setFont(new Font("Serif", Font.PLAIN, 20));
        tutorButton.addActionListener(parentFrame);
        add(tutorButton);

        /*JButton markerButton = new JButton("Marker");
        markerButton.setLocation(250, 400);
        markerButton.setSize(300, 100);
        markerButton.setFont(new Font("Serif", Font.PLAIN, 20));
        markerButton.addActionListener(parentFrame);
        add(markerButton);*/

        setVisible(true);
    }
}