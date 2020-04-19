package com.isolator.game.objects;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.Controller;

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
