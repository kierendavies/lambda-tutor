package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        // Set fonts
        setUIFont(new javax.swing.plaf.FontUIResource(Fonts.normal));

        // Create and set up window
        Window window = new Window();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Display Window
        window.pack();
        window.setVisible(true);
    }

    // Taken from http://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}
