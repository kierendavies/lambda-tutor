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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates
 */
public class MainMenu extends SizablePanel {
    ActionListener actionListener;

    public MainMenu(ActionListener actionListener) {
        this.actionListener = actionListener;

        // Add title
        addTitle("\u03bbambda Tutor");

        // Add Border
        setBorder(new EmptyBorder(0, WIDTH / 4, HEIGHT / 8, WIDTH / 4));

        // Create and add button pane
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));

        buttonPane.add(Box.createRigidArea(new Dimension(0, WIDTH / 50)));

        // Add buttons to the pane
        addButton(buttonPane, "Calculator");
        addButton(buttonPane, "Problems");
        add(buttonPane);

        setVisible(true);
    }

    /**
     * Creates a button with text and adds it to the specified container.
     *
     * @param container The container to which to add the button
     * @param text      The text of the button
     */
    private void addButton(Container container, String text) {
        JButton button = new JButton(text);
        button.setFont(Fonts.bigger);
        button.setMaximumSize(new Dimension((2 * WIDTH) / 3, HEIGHT / 3));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        container.add(button);
        container.add(Box.createRigidArea(new Dimension(0, WIDTH / 50)));
    }
}