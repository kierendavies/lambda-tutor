package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;

public class Icons {
    public static final Icon expand;
    public static final Icon collapse;
    public static final Icon setInput;

    static {
        expand = new ImageIcon(Icons.class.getResource("/icons/expand.gif"), "expand");
        collapse = new ImageIcon(Icons.class.getResource("/icons/collapse.gif"), "collapse");
        setInput = new ImageIcon(Icons.class.getResource("/icons/set_input.gif"), "set input");
    }
}
