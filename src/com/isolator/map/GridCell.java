package com.isolator.map;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Size;

import java.awt.*;

public class GridCell {

    private BaseTile tile;
    private Position position;
    private Size size;

    public GridCell(BaseTile tile, Position position, Size size) {
        this.tile = tile;
        this.position = position;
        this.size = size;
    }

    public void setTile(BaseTile tile) {
        this.tile = tile;
    }

    public boolean isWalkable() {
        return tile.isWalkable();
    }

    public CollisionBox getCollisionBox() {
        return CollisionBox.of(new Position(position.getX() + size.getWidth() / 2, position.getY() + size.getHeight() / 2), size);
    }

    public Image getTileSprite() {
        return tile.getTileSprite();
    }
}
