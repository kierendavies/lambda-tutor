package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;


public class SizeablePanel extends JPanel {

    int width;
    int height;


    public SizeablePanel() {
        // setBackground(Color.black);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = (int) ((gd.getDisplayMode().getWidth()) * 0.41);
        height = (int) ((gd.getDisplayMode().getHeight()) * 0.47);
        setPreferredSize(new Dimension(width, height));
        //System.out.println(width);
        //System.out.println(height);
        setLayout(new BorderLayout());


    }

    /**
     * Set the title of this panel to the specified string
     * @param title The title to be displayed at the top of the pane
     */

    public void addTitle(String title) {
        JPanel titlePane = new JPanel();
        Font fnt0 = new Font("arial", Font.BOLD, 72);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(fnt0);
        titlePane.add(titleLabel);
        add(titlePane, BorderLayout.PAGE_START);

    }

}
