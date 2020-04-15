package com.isolator.map;

import com.isolator.core.Size;
import com.isolator.gfx.SpritesLibrary;

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
        return SpritesLibrary.woodFloor;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
