package com.isolator.map;

import com.isolator.core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BaseTile {
    protected Size size;
    protected boolean walkable;

    public BaseTile(Size size) {
        this.size = size;
        walkable = true;
    }

    public Image getTileSprite() {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = bufferedImage.createGraphics();

        imageGraphics.setColor(Color.lightGray);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        imageGraphics.setColor(Color.darkGray);
        imageGraphics.drawRect(0, 0, size.getWidth(), size.getHeight());

        return bufferedImage;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
