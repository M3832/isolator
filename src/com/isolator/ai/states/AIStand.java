package com.isolator.ai.states;

import com.isolator.entity.Visitor;
import com.isolator.game.GameState;

public class AIStand extends AIState {

    private long standUntil;
    private boolean done;

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        if(standUntil == 0) {
            long waitSeconds = ((int)(Math.random() * 10));
            standUntil = (long) (waitSeconds * 1000 / state.getGameSpeed()) + System.currentTimeMillis();
        }

        if(System.currentTimeMillis() >= standUntil) {
            done = true;
        }
    }

    @Override
    public boolean readyToTransition() {
        return done;
    }
}
