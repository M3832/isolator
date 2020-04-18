package com.isolator.game;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Vector2;
import com.isolator.entity.BaseEntity;
import com.isolator.entity.Player;
import com.isolator.map.GridCell;

import java.util.Optional;

public class CollisionResolver {

    public void handleCollisions(GameState state, BaseEntity entity) {
        handleWallCollision(state, entity);
        handleEntityCollision(state, entity);
    }

    private void handleEntityCollision(GameState state, BaseEntity entity) {
        state.getEntities().stream()
                .filter(e -> !e.equals(entity))
                .filter(e -> Position.withinCloseProximity(e.getPosition(), entity.getPosition()))
                .forEach(e -> handleCollision(e, entity));
    }

    private void handleCollision(BaseEntity other, BaseEntity current) {
        if(current.getNextPositionCollisionBox().checkCollision(other.getNextPositionCollisionBox())) {
            if(current instanceof Player && current.isMovingToward(other) ) {
                Vector2 direction = Vector2.directionBetweenPositions(current.getPosition(), other.getPosition());
                direction.normalize();
                System.out.println(direction);
                other.push(direction, -current.getCurrentSpeed());
            }
        }
    }

    private void handleWallCollision(GameState state, BaseEntity entity) {
        CollisionBox intersection = getWallIntersections(state, entity.getNextPositionCollisionBox());
        boolean collideX = intersection.getBox().getWidth() != 0;
        boolean collideY = intersection.getBox().getHeight() != 0;
        entity.immediateStopInDirections(collideX, collideY);
    }

    public CollisionBox getWallIntersections(GameState state, CollisionBox collisionBox) {
        Optional<GridCell> collidingGridCell = state.getMap().getUnwalkableGridCells().stream()
                .filter(gridCell -> gridCell.getCollisionBox().checkCollision(collisionBox))
                .findFirst();

        if(collidingGridCell.isPresent()) {
            return collidingGridCell.get()
                    .getCollisionBox()
                    .getIntersection(collisionBox);
        }

        return CollisionBox.emptyBox();
    }
}
