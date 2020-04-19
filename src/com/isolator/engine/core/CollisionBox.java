package com.isolator.engine.core;

import java.awt.*;

public class CollisionBox {
    private final Rectangle box;

    public CollisionBox(Rectangle box) {
        this.box = box;
    }

    public boolean checkCollision(CollisionBox otherBox) {
        return box.intersects(otherBox.getBox());
    }

    public Rectangle getBox() {
        return box;
    }

    public CollisionBox getIntersection(CollisionBox otherBox) {
        return new CollisionBox(box.intersection(otherBox.getBox()));
    }

    public Position getPosition() {
        return new Position((int) box.getX(), (int) box.getY());
    }

    public static CollisionBox empty() {
        return CollisionBox.of(new Position(), new Size());
    }

    public static CollisionBox of(Position position, Size size) {
        return new CollisionBox(
                new Rectangle(
                        position.getX(),
                        position.getY(),
                        size.getWidth(),
                        size.getHeight()
                )
        );
    }

    public String toString() {
        return "CollisionBox(" + new Position(box.x, box.y) + ", " + new Size(box.width, box.height) + ")";
    }
}
