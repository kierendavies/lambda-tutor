package za.ac.uct.cs.ddd.lambda.gui;

import java.awt.*;
import java.io.IOException;

public class Fonts {
    public static Font normal;
    public static Font title;
    public static Font mono;

    static {
        try {
            normal = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans.ttf"))
                    .deriveFont(12f);
//            normal = new Font("Dialog", Font.PLAIN, 12);

            title = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans-Bold.ttf"))
                    .deriveFont(36f);
//            title = new Font("Dialog", Font.BOLD, 36);

            mono = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSansMono.ttf"))
                    .deriveFont(12f);
//            mono = new Font("Monospaced", Font.PLAIN, 12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
