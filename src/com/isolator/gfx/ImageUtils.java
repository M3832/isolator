package com.isolator.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ImageUtils {

    public static final int SPRITE_SIZE = 64;

    public static Image loadImage(String filePath) {
        try {
            return ImageIO.read(ImageUtils.class.getResource(filePath));
        } catch (IOException e) {
            System.out.println("Could not load image from path: " + filePath);
        }

        return SpritesLibrary.defaultSprite;
    }
}
