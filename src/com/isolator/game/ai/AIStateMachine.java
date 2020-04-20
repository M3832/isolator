package com.isolator.game.ai;

import com.isolator.engine.GameState;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.entity.Visitor;

public class AIStateMachine {

    private AIState currentState;
    private Runnable runnable;

    public AIStateMachine() {
        currentState = new AIStand(1);
    }

    public void update(GameState state, Visitor entity) {
        currentState.update(state, entity);

        if(isReadyToTransition() && runnable != null) {
            runnable.run();
            runnable = null;
        }
    }

    public void setCurrentState(AIState state) {
        currentState = state;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public AIState getCurrentState() {
        return currentState;
    }

    public boolean isReadyToTransition() {
        return currentState.isReadyToTransition();
    }
}
