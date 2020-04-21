package com.isolator.game;

import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.entity.BaseEntity;
import com.isolator.game.entity.Player;

public class CollisionResolver {

    public void handleCollisions(IsolatorGameState state, BaseEntity entity) {
        handleEntityCollision(state, entity);
    }

    public void handlePropCollisions(IsolatorGameState state, BaseEntity current) {
        state.getObjects().stream()
                .filter(object -> !(object instanceof BaseEntity))
                .filter(object -> current.getPosition().isWithinRangeOf(128, object.getPosition()))
                .forEach(object -> {
                    CollisionBox box = object.getCollisionBox();
                    if(box.checkCollision(current.getNextPositionCollisionBox())) {
                        CollisionBox intersection = box.getIntersection(current.getNextPositionCollisionBox());
                        current.immediateStopInDirections(
                                intersection.getBox().getWidth() > 0,
                                intersection.getBox().getHeight() > 0);
                    }
                });

    }

    private void handleEntityCollision(IsolatorGameState state, BaseEntity entity) {
        state.getObjects().stream()
                .filter(e -> !e.equals(entity))
                .filter(e -> Position.withinCloseProximity(e.getPosition(), entity.getPosition()))
                .forEach(e -> handleCollision(e, entity));
    }

    private void handleCollision(BaseObject other, BaseEntity current) {
        if(current.getNextPositionCollisionBox().checkCollision(other.getCollisionBox())) {
            if(current instanceof Player
                    && other instanceof BaseEntity
                    && current.isMovingToward(other.getCollisionBox().getPosition())) {
                collidePlayerToUnit((BaseEntity) other, (Player) current);
            }
        }
    }

    private void collidePlayerToUnit(BaseEntity other, Player current) {
        Vector2 direction = Vector2.directionBetweenPositions(current.getPosition(), other.getPosition());
        direction.normalize();
        other.push(direction, -current.getCurrentSpeed());
    }
}
