package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 */

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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menu = new MainMenu(this);
        calculator = new CalcView(this);
        tutor = new TutView(this);
        marker = new MarkView(this);
        this.setContentPane(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand == "Main menu") {
            setPanel(menu);
        } else if (actionCommand == "Calculator") {

            setPanel(calculator);

        } else if (actionCommand == "Tutor") {
            setPanel(tutor);
        } else if (actionCommand == "Marker") {


        }
    }

  /**
     * @param panel
     */
    private void setPanel(JPanel panel) {
        removePane();
        this.setContentPane(panel);
        pack();
    }

    /**
     * Removes the current contentPane
=======
     *  Removes the current contentPane
>>>>>>> develop
     */
    private void removePane() {
        remove(getContentPane());
        revalidate();
        repaint();
    }


}