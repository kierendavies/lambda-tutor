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

public class Icons {
    public static final Icon back;
    public static final Icon expand;
    public static final Icon collapse;
    public static final Icon setInput;

    static {
        back = new ImageIcon(Icons.class.getResource("/icons/back.gif"), "Back");
        expand = new ImageIcon(Icons.class.getResource("/icons/expand.gif"), "Expand");
        collapse = new ImageIcon(Icons.class.getResource("/icons/collapse.gif"), "Collapse");
        setInput = new ImageIcon(Icons.class.getResource("/icons/set_input.gif"), "Set input");
    }
}
