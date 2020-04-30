package com.isolator.game.map;

import com.isolator.engine.state.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gameobjects.BaseObject;

import java.awt.*;

public class GridCell extends BaseObject {

    private Image sprite;

    public GridCell(Image sprite, Position position, Size size) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;
    }

    public Image getTileSprite() {
        return sprite;
    }

    @Override
    public void update(GameState state) {}

    public void setTileSprite(Image sprite) {
        this.sprite = sprite;
    }
}
