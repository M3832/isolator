package com.isolator.ai;

import com.isolator.ai.states.AIState;
import com.isolator.ai.states.AIWander;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;

public class AIStateMachine {

    private AIState currentState;

    public AIStateMachine() {
        currentState = new AIWander();
    }

    public void update(GameState state, Visitor entity) {
        currentState.update(state, entity);

        if(currentState.readyToTransition()) {
            int random = ((int)(Math.random() * 3)) + 1;
            currentState = new AIWander();
        }
    }

    public AIState getCurrentState() {
        return currentState;
    }
}
