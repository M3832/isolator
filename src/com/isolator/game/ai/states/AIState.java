package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.game.objects.BaseEntity;
import com.isolator.game.objects.Visitor;
import com.isolator.engine.ui.UIBase;

public abstract class AIState {

    public abstract void update(GameState state, Visitor controlledEntity);
    public abstract boolean readyToTransition();
    public abstract UIBase getDebugUI(GameState state, BaseEntity baseEntity);
}
