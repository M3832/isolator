package com.isolator.game.ai;

import com.isolator.engine.GameState;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.entity.Visitor;

public class AIStateMachine {

    private AIState currentState;

    public AIStateMachine() {
        currentState = new AIStand(1);
    }

    public void update(GameState state, Visitor entity) {
        currentState.update(state, entity);
    }

    public void setCurrentState(AIState state) {
        currentState = state;
    }

    public AIState getCurrentState() {
        return currentState;
    }

    public boolean isReadyToTransition() {
        return currentState.isReadyToTransition();
    }
}
