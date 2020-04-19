package com.isolator.game.map;

import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gameobjects.BaseObject;

import java.awt.*;

public class GridCell extends BaseObject {

    private int tile;

    public GridCell(int tile, Position position, Size size) {
        this.tile = tile;
        this.position = position;
        this.size = size;
    }

    public Image getTileSprite() {
        return Tiles.get(tile);
    }

    @Override
    public void update(GameState state) {}
}
