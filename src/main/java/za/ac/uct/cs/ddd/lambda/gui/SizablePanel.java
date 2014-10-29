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
