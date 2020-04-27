package com.isolator.game.entity;

import com.isolator.engine.game.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;

import java.awt.*;

public class PathBlocker extends BaseObject implements Blocker {

    final CollisionBox collisionBox;
    private Image sprite;

    public PathBlocker(Position position, Size size) {
        super();
        this.position = position;
        this.size = size;
        this.collisionBox = CollisionBox.of(position, size);
    }

    @Override
    public Image getDrawGraphics(Camera camera) {
        return sprite;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void update(GameState state) {}

    @Override
    public Position getRenderOffset(Camera camera) {
        return new Position();
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
}
