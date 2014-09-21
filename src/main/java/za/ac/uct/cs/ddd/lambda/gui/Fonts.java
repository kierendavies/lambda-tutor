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

            title = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSans-Bold.ttf"))
                    .deriveFont(36f);

            mono = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/fonts/DejaVuSansMono.ttf"))
                    .deriveFont(12f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
