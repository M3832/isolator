package com.isolator.ai;

import com.isolator.ai.states.AIStand;
import com.isolator.ai.states.AIState;
import com.isolator.ai.states.AIWander;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;

public class AIStateMachine {

    private AIState currentState;

    public AIStateMachine() {
        decideOnNewState();
    }

    public void update(GameState state, Visitor entity) {
        currentState.update(state, entity);

        if(currentState.readyToTransition()) {
            decideOnNewState();
        }
    }

    private void decideOnNewState() {
        int random = (int)(Math.random() * 2) + 1;
        if(random == 1) {
            currentState = new AIWander();
        } else if (random == 2) {
            currentState = new AIStand();
        }
    }

    public AIState getCurrentState() {
        return currentState;
    }
}
