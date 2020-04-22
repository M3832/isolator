package com.isolator.game.entity;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.Size;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;

public class Player extends BaseEntity {

    public Player(Controller controller) {
        super(controller);
        this.collisionBoxSize = new Size(24, 32);
    }

    public void handleCollision(IsolatorGameState state, BaseObject other) {
        super.handleCollision(state, other);

        if(other instanceof BaseEntity && isMovingToward(other.getCollisionBox().getPosition())) {
            BaseEntity entity = (BaseEntity) other;
            Vector2 direction = Vector2.directionBetweenPositions(getPosition(), other.getPosition());
            direction.normalize();
            entity.push(direction, -getCurrentSpeed());
        }
    }

    @Override
    public void update(GameState state) {
        super.update((IsolatorGameState) state);

        if(controller.isRequestingAction()) {
            state.toggleDebugMode();
        }

        if(controller.isRequestingSpeedUp()) {
            state.increaseGameSpeed();
        }

        if(controller.isRequestingSlowDown()) {
            state.decreaseGameSpeed();
        }
    }
}
