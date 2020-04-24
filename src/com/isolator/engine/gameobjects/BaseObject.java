package com.isolator.engine.gameobjects;

import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;

import java.awt.*;

public abstract class BaseObject {

    protected boolean remove;
    protected Position position;
    protected final Position renderOffset;
    protected Size size;

    public BaseObject() {
        this.position = new Position();
        this.renderOffset = new Position();
        this.size = new Size();
        remove = false;
    }

    public abstract void update(GameState state);

    public Image getDrawGraphics(Camera camera) {
        return null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getRenderOffset(Camera camera) {
        return renderOffset;
    }

    public Size getSize() {
        return size;
    }

    public CollisionBox getCollisionBox() {
        return CollisionBox.empty();
    }

    public boolean readyToRemove() {
        return remove;
    }
}
