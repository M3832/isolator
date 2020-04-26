package com.isolator.game.ai.states;

import com.isolator.engine.game.GameState;
import com.isolator.game.entity.Visitor;

public class AIStand extends AIState {

    private final int standUntil;
    private boolean done;

    public AIStand(int standUntil) {
        this.standUntil = standUntil;
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(state.getGameTimer().getCurrentTime() >= standUntil) {
            done = true;
        }
    }

    @Override
    public boolean isReadyToTransition() {
        return done;
    }

    @Override
    public boolean isTimeoutReached() {
        return false;
    }
}
