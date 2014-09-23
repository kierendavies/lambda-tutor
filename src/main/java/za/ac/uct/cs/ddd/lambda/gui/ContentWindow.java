package za.ac.uct.cs.ddd.lambda.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ContentWindow extends SizablePanel {
    public ContentWindow(Window parent, String title, JPanel content) {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(3, 3, 3, 3);

        JButton menuButton = new JButton("Main menu");
        menuButton.setIcon(Icons.back);
        menuButton.setPreferredSize(menuButton.getMinimumSize());
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setContentPane(parent.menu);
            }
        });
        add(menuButton, constraints);

        constraints.gridx = 1;
        add(Box.createHorizontalStrut(12), constraints);

        constraints.gridx = 2;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Fonts.bigger);
        add(titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(Box.createVerticalStrut(12), constraints);

        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(content, constraints);
    }
}
