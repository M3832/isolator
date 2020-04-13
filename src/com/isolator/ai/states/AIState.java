package com.isolator.ai.states;

import com.isolator.entity.BaseEntity;
import com.isolator.entity.Visitor;
import com.isolator.game.GameState;
import com.isolator.ui.UIBase;

public abstract class AIState {

    public abstract void update(GameState state, Visitor controlledEntity);
    public abstract boolean readyToTransition();
    public abstract UIBase getDebugUI(GameState state, BaseEntity baseEntity);
}
