package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;

public class Driver {

    private  static  void createAndShowGUI(){
        //Create and set up window
        Window window = new Window();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display Window
        window.pack();
        window.setVisible(true);

    }
    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
              createAndShowGUI();
          }
      });
    }
}
