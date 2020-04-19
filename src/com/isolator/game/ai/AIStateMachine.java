package com.isolator.game.ai;

import com.isolator.engine.GameState;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.ai.states.AIWander;
import com.isolator.game.objects.Visitor;
import com.isolator.game.IsolatorGameState;

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
