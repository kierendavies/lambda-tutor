package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentWindow extends SizablePanel {
    public ContentWindow(Window parent) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1;
        gbc.insets = new Insets(3, 10, 3, 10);

        JButton menuButton = new JButton("Main menu");
        menuButton.setPreferredSize(menuButton.getMaximumSize());
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setContentPane(parent.menu);
            }
        });
        add(menuButton, gbc);
    }
}
