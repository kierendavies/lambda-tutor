package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;

public class Icons {
    public static final Icon back;
    public static final Icon expand;
    public static final Icon collapse;
    public static final Icon setInput;

    static {
        back = new ImageIcon(Icons.class.getResource("/icons/back.gif"), "Back");
        expand = new ImageIcon(Icons.class.getResource("/icons/expand.gif"), "Expand");
        collapse = new ImageIcon(Icons.class.getResource("/icons/collapse.gif"), "Collapse");
        setInput = new ImageIcon(Icons.class.getResource("/icons/set_input.gif"), "Set input");
    }
}
