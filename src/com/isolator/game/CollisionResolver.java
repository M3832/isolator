package com.isolator.game;

import com.isolator.engine.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.Blocker;
import com.isolator.engine.gameobjects.Collidable;
import com.isolator.game.objects.BaseEntity;
import com.isolator.game.objects.Player;

public class CollisionResolver {

    public void handleCollisions(GameState state, BaseEntity entity) {
        handleEntityCollision(state, entity);
    }

    private void handleEntityCollision(GameState state, BaseEntity entity) {
        state.getCollidables().stream()
                .filter(e -> !e.equals(entity))
                .filter(e -> Position.withinCloseProximity(e.getCollisionBox().getPosition(), entity.getPosition()))
                .forEach(e -> handleCollision(e, entity));
    }

    private void handleCollision(Collidable other, BaseEntity current) {
        if(current.getNextPositionCollisionBox().checkCollision(other.getCollisionBox())) {

            if(current instanceof Player
                    && other instanceof BaseEntity
                    && current.isMovingToward(other.getCollisionBox().getPosition())) {
                collidePlayerToUnit((BaseEntity) other, (Player) current);

            } else if(other instanceof Blocker) {
                CollisionBox intersection = other.getCollisionBox().getIntersection(current.getNextPositionCollisionBox());
                current.immediateStopInDirections(
                        intersection.getBox().getWidth() > 0,
                        intersection.getBox().getHeight() > 0);
            }
        }
    }

    private void collidePlayerToUnit(BaseEntity other, Player current) {
        Vector2 direction = Vector2.directionBetweenPositions(current.getPosition(), other.getPosition());
        direction.normalize();
        other.push(direction, -current.getCurrentSpeed());
    }
}
