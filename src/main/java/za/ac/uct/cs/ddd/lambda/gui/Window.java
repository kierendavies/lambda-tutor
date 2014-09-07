package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame implements ActionListener {

    MainMenu menu;
    CalcView calculator;
    TutView tutor;
    MarkView marker;
    GraphicsDevice gd;

    public Window() {


        //Basic setup
        setTitle("Lambda Calculus");
        setLocation(50, 50);
        //setResizable(false);

        addWindowListener(new windowHandler());

        menu = new MainMenu(this);
        calculator = new CalcView(this);
        //tutor = new Tutor(this);
        marker = new MarkView(this);
        this.setContentPane(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand == "Main menu") {
            this.setContentPane(menu);
        } else if (actionCommand == "Calculator") {
            removePane();
            this.setContentPane(calculator);
            CalcController c_controller = new CalcController(calculator);
            pack();
        } else if (actionCommand == "Tutor") {
            removePane();

            pack();
        } else if (actionCommand == "Marker") {
            removePane();
            this.setContentPane(marker);
            MarkController m_controller = new MarkController(marker);
            pack();
        }
    }

    private void removePane() {
        remove(getContentPane());
        revalidate();
        repaint();
    }



    // WindowListener idea found here: http://cs.nyu.edu/~yap/classes/visual/03s/lect/l7/
    private class windowHandler extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}