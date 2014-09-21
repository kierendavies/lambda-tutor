package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class SizeablePanel extends JPanel {

    static final int WIDTH = 600;
    static final int HEIGHT = 450;


    public SizeablePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());


    }


    public void addTitle(String title) {
        JPanel titlePane = new JPanel();
        Font fnt0 = new Font("arial", Font.BOLD, 65);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(fnt0);
        titlePane.add(titleLabel);
        add(titlePane, BorderLayout.PAGE_START);

    }

}
