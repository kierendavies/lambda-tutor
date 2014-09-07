package za.ac.uct.cs.ddd.lambda.gui;

import za.ac.uct.cs.ddd.lambda.evaluator.Parser;

import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        GUI app = new GUI();
        app.pack();
        //new CalculatorController( app.calculator );
        app.setVisible(true);
        System.out.println("Started app...");
    }
}
