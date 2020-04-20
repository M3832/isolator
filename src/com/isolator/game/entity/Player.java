package com.isolator.game.entity;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.Size;
import com.isolator.game.IsolatorGameState;

public class Player extends BaseEntity {

    public Player(Controller controller) {
        super(controller);
        this.collisionBoxSize = new Size(24, 32);
    }

    @Override
    protected void checkCollisions(IsolatorGameState state) {
        state.getCollisionResolver().handleCollisions(state, this);
        state.getCollisionResolver().handlePropCollisions(state, this);
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
