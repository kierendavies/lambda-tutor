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
