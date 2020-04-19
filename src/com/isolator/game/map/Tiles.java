package com.isolator.game.map;

import com.isolator.engine.gfx.SpritesLibrary;

import java.awt.*;

public class Tiles {

    public static final int WOOD_FLOOR = 1;

    public static Image get(int tile) {
        if (tile == WOOD_FLOOR) {
            return SpritesLibrary.WOOD_FLOOR;
        }
        return SpritesLibrary.DEFAULT;
    }
}
