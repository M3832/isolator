package com.isolator.engine.gameobjects;

import com.isolator.engine.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;

import java.awt.*;

public abstract class BaseObject {

    protected Position position;
    protected Position renderOffset;
    protected Size size;

    public BaseObject() {
        this.position = new Position();
        this.renderOffset = new Position();
        this.size = new Size();
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
}
