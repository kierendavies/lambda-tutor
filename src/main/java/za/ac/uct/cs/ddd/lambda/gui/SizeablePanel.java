package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by matthew on 2014/08/16.
 */
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


    public void addTitle(String title) {
        JPanel titlePane = new JPanel();
        Font fnt0 = new Font("arial", Font.BOLD, 72);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(fnt0);
        titlePane.add(titleLabel);
        add(titlePane, BorderLayout.PAGE_START);

    }

}
