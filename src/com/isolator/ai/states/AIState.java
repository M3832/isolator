package com.isolator.ai.states;

import com.isolator.entity.Visitor;
import com.isolator.game.GameState;

public abstract class AIState {

    public abstract void update(GameState state, Visitor controlledEntity);
    public abstract boolean readyToTransition();

}
