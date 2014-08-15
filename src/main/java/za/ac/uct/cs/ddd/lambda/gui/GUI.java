package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame implements ActionListener{

    MainMenu menu;
    Calculator calculator;
    // WindowListener idea found here: http://cs.nyu.edu/~yap/classes/visual/03s/lect/l7/
    private class windowHandler extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }

    public GUI(){
        //Basic setup
        setTitle("Lambda Calculus");
        setSize(800, 600);
        setLocation(50, 50);
        setResizable(false);

        addWindowListener(new windowHandler());

        menu = new MainMenu(this);
        calculator = new Calculator(this);
        this.setContentPane(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Main menu"){
            this.setContentPane(menu);
        }
        else if (e.getActionCommand() == "Calculator"){
            this.setContentPane(calculator);
        }
    }
}