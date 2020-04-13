package com.isolator.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprites {
    private static Image VISITOR_SPRITE;

    public static Image getVisitorSprite() {
        if(VISITOR_SPRITE == null) {
            BufferedImage bufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D imageGraphics = bufferedImage.createGraphics();
            imageGraphics.setColor(Color.GREEN);
            imageGraphics.fillRect(0, 0, 50, 50);
            imageGraphics.setColor(Color.BLACK);
            imageGraphics.drawRect(0, 0, 49, 49);

            VISITOR_SPRITE = bufferedImage;
        }

        return VISITOR_SPRITE;
    }
}
