package com.isolator.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {

    public static final int SPRITE_SIZE = 64;

    public static Image loadImage(String filePath) {
        try {
            BufferedImage readImage = ImageIO.read(ImageUtils.class.getResource(filePath));
            BufferedImage convertedImage = (BufferedImage) ImageUtils.createCompatibleImage(readImage.getWidth(), readImage.getHeight());
            Graphics2D g2d = convertedImage.createGraphics ();
            g2d.drawImage ( readImage, 0, 0, readImage.getWidth (), readImage.getHeight (), null );
            g2d.dispose();

            return convertedImage;
        } catch (IOException e) {
            System.out.println("Could not load image from path: " + filePath);
        }

        return SpritesLibrary.defaultSprite;
    }

    public static Image createCompatibleImage(int width, int height) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        return gc.createCompatibleImage(width,
                height,
                3);
    }

    public static Image scale(Image image, double amount) {
        return image.getScaledInstance(
                (int) (SPRITE_SIZE * amount),
                (int) (SPRITE_SIZE * amount),
                0);
    }
}
