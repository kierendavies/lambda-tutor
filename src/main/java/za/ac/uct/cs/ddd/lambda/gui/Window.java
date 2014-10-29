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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame implements ActionListener {
    MainMenu menu;
    ContentWindow calculator;
    ContentWindow problems;

    public Window() {
        //Basic setup
        setTitle("Lambda Calculus Tutor");
        setLocation(50, 50);

        menu = new MainMenu(this);
        calculator = new ContentWindow(this, "Calculator", new Calculator());
        problems = new ContentWindow(this, "Problems", new ProblemSets());
        this.setContentPane(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Main menu":
                setPanel(menu);
                break;
            case "Calculator":
                setPanel(calculator);
                break;
            case "Problems":
                setPanel(problems);
                break;
        }
    }

    private void setPanel(JPanel panel) {
        removePane();
        this.setContentPane(panel);
        pack();
    }

    /**
     * Removes the current contentPane
     */
    private void removePane() {
        remove(getContentPane());
        revalidate();
    }
}