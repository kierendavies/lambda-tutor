/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
