package com.isolator.game.ai;

import com.isolator.engine.state.GameState;
import com.isolator.game.ai.states.AIStand;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.entity.Visitor;

import java.util.ArrayList;
import java.util.List;

public class AIStateMachine {

    private List<AIState> states;

    public AIStateMachine() {
        states = new ArrayList<>();
        states.add(new AIStand(1));
    }

    public void update(GameState state, Visitor entity) {
        if(!states.isEmpty()) {
            states.get(0).update(state, entity);
            if(states.get(0).isReadyToTransition()) {
                states.remove(0);
            }
        }
    }

    public void setCurrentState(AIState state) {
        states.add(0, state);
    }

    public AIState getCurrentState() {
        return states.isEmpty() ? new AIStand(0) : states.get(0);
    }

    public boolean isReadyToTransition() {
        return states.isEmpty();
    }
}
