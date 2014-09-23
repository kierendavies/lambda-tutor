package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;

public class SizablePanel extends JPanel {
    static final int WIDTH = 640;
    static final int HEIGHT = 480;

    public SizablePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
    }

    /**
     * Set the biggest of this panel to the specified string
     *
     * @param title The biggest to be displayed at the top of the panel.
     */
    public void addTitle(String title) {
        JPanel titlePane = new JPanel();
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Fonts.biggest);
        titlePane.add(titleLabel);
        add(titlePane, BorderLayout.PAGE_START);
    }
}
