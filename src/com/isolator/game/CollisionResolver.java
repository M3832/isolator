package com.isolator.game;

import com.isolator.game.entity.BaseEntity;

public class CollisionResolver {

    public void checkCollisionsFor(IsolatorGameState state, BaseEntity entity) {
        state.getObjects().stream()
                .filter(o -> o != entity)
                .filter(o -> entity.getPosition().isWithinRangeOf(128, o.getPosition()))
                .filter(o -> entity.getNextPositionCollisionBox().checkCollision(o.getCollisionBox()))
                .forEach(o -> entity.handleCollision(state, o));
    }
}
