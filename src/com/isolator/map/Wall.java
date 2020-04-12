package com.isolator.map;

import com.isolator.core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends BaseTile {
    public Wall(Size size) {
        super(size);
        this.walkable = false;
    }

    @Override
    public BufferedImage getTileSprite() {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = bufferedImage.createGraphics();

        imageGraphics.setColor(Color.pink);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        imageGraphics.setColor(Color.red);
        imageGraphics.drawRect(0, 0, size.getWidth(), size.getHeight());

        return bufferedImage;
    }
}
