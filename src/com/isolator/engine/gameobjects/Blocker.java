package com.isolator.engine.gameobjects;

import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;

public class Blocker extends BaseObject implements Collidable {

    CollisionBox collisionBox;

    public Blocker(Position position, Size size) {
        super();
        this.position = position;
        this.size = size;
        this.collisionBox = CollisionBox.of(position, size);
    }

    @Override
    public Image getDrawGraphics(Camera camera) {
        return null;
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
}
