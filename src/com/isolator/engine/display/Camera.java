package com.isolator.engine.display;

import com.isolator.engine.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.game.objects.BaseEntity;

import java.util.Optional;

public class Camera {

    private Position position;
    private Size size;
    private double zoom;

    private Optional<BaseEntity> entityToFollow;

    public Camera(Size size) {
        this.position = new Position();
        this.size = size;
        zoom = 1.5f;
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

        if(position.getX() + size.getWidth() > state.getSceneSize().getWidth())
            position.setX(state.getSceneSize().getWidth() - size.getWidth());

        if(position.getY() + size.getHeight() > state.getSceneSize().getHeight())
            position.setY(state.getSceneSize().getHeight() - size.getHeight());
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
