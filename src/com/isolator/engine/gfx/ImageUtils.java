package com.isolator.engine.gfx;

import com.isolator.engine.core.Size;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {

    public static final int SPRITE_SIZE = 64;
    public static final double SCALE = 1.5f;
    public static final int ALPHA_OPAQUE = 1;
    public static final int ALPHA_BIT_MASKED = 2;

    public static Image loadImage(String filePath) {
        try {
            BufferedImage readImage = ImageIO.read(ImageUtils.class.getResource(filePath));
            BufferedImage convertedImage = (BufferedImage) ImageUtils.createCompatibleImage(
                    new Size(readImage.getWidth(), readImage.getHeight()),
                    ALPHA_BIT_MASKED);
            Graphics2D g2d = convertedImage.createGraphics ();
            g2d.drawImage ( readImage, 0, 0, readImage.getWidth (), readImage.getHeight (), null );
            g2d.dispose();

            return convertedImage;
        } catch (IOException e) {
            System.out.println("Could not load image from path: " + filePath);
        }

        return SpritesLibrary.DEFAULT;
    }

    public static Image createCompatibleImage(Size size, int transparency) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        return gc.createCompatibleImage(
                size.getWidth(),
                size.getHeight(),
                transparency);
    }

    public static Image scale(Image image, double amount) {
        Image result = createCompatibleImage(
                new Size(
                        (int)(image.getWidth(null) * amount),
                        (int)(image.getHeight(null) * amount)),
                ALPHA_BIT_MASKED);

        Graphics2D graphics = (Graphics2D) result.getGraphics();
        Image scaledImage = image.getScaledInstance(
                (int) (image.getWidth(null) * amount),
                (int) (image.getHeight(null) * amount),
                Image.SCALE_FAST);

        graphics.drawImage(scaledImage,
                0,
                0,
                null);

        graphics.dispose();
        return result;
    }
}
