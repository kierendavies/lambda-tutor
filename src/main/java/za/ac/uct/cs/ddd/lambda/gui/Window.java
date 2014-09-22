package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    MainMenu menu;
    ContentWindow calculator;
    ContentWindow tutor;

    public Window() {
        //Basic setup
        setTitle("Lambda Calculus");
        setLocation(50, 50);

        menu = new MainMenu(this);
        calculator = new ContentWindow(this, "Calculator", new Calculator());
        tutor = new ContentWindow(this, "Problems", new JPanel());
        this.setContentPane(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Main menu":
                setPanel(menu);
                break;
            case "Calculator":
                setPanel(calculator);
                break;
            case "Tutor":
                setPanel(tutor);
                break;
        }
    }

    private void setPanel(JPanel panel) {
        removePane();
        this.setContentPane(panel);
        pack();
    }

    /**
     * Removes the current contentPane
     */
    private void removePane() {
        remove(getContentPane());
        revalidate();
    }
}