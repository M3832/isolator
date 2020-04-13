package com.isolator.entity;

import com.isolator.controller.Controller;
import com.isolator.game.GameState;

public class Player extends BaseEntity {

    public Player(Controller controller) {
        super(controller);
    }

    @Override
    public void update(GameState state) {
        super.update(state);

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
