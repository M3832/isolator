package com.isolator.core;

import java.awt.*;

public class CollisionBox {
    private Rectangle box;

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
}
