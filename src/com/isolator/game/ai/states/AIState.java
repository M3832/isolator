package com.isolator.game.ai.states;

import com.isolator.engine.GameState;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.entity.BaseEntity;
import com.isolator.game.entity.Visitor;
import com.isolator.engine.ui.UIBase;

public abstract class AIState {

    public abstract void update(GameState state, Visitor controlledEntity);
    public abstract boolean isReadyToTransition();
    public abstract boolean isTimeoutReached();
    public UIBase getDebugUI(GameState state, BaseEntity baseEntity) {
        return new UIContainer();
    }
}
