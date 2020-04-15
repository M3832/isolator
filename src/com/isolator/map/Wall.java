package com.isolator.map;

import com.isolator.core.Size;
import com.isolator.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends BaseTile {
    public Wall(Size size) {
        super(size);
        this.walkable = false;
    }

    @Override
    public BufferedImage getTileSprite() {
        return null;
    }
}
