package com.isolator.display;

import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.entity.BaseEntity;
import com.isolator.game.GameState;

import java.util.Optional;

public class Camera {

    private Position position;
    private Size size;
    private double zoom;

    private Optional<BaseEntity> entityToFollow;

    public Camera(Position position, Size size) {
        this.position = position;
        this.size = size;
        zoom = 2f;
    }

    public void followEntity(BaseEntity entityToFollow) {
        this.entityToFollow = Optional.of(entityToFollow);
    }

    public void update(GameState state) {
        if(entityToFollow.isPresent()) {
            Position entityPosition = entityToFollow.get().getPosition();

            this.position.setX(entityPosition.getX() - size.getWidth() / 2);
            this.position.setY(entityPosition.getY() - size.getHeight() / 2);

            clampPositionWithinBounds(state);
        }
    }

    private void clampPositionWithinBounds(GameState state) {
        if(position.getX() < 0)
            position.setX(0);
        if(position.getY() < 0)
            position.setY(0);

        if(position.getX() + size.getWidth() > state.getMap().getSize().getWidth())
            position.setX(state.getMap().getSize().getWidth() - size.getWidth());

        if(position.getY() + size.getHeight() > state.getMap().getSize().getHeight())
            position.setY(state.getMap().getSize().getHeight() - size.getHeight());
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public double getZoom() {
        return zoom;
    }
}
