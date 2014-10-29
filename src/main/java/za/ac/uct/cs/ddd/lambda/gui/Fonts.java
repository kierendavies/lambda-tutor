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

import java.awt.*;

public class Fonts {
    public static Font normal;
    public static Font bigger;
    public static Font biggest;
    public static Font mono;

    static {
        try {
            normal = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans.ttf"))
                    .deriveFont(12f);

            bigger = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans-Bold.ttf"))
                    .deriveFont(16f);

            biggest = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans-Bold.ttf"))
                    .deriveFont(36f);

            mono = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSansMono.ttf"))
                    .deriveFont(12f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
