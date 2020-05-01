package com.isolator.game.ai.states;

import com.isolator.engine.state.GameState;
import com.isolator.engine.ui.components.UIText;
import com.isolator.game.entity.BaseEntity;
import com.isolator.game.entity.Visitor;
import com.isolator.engine.ui.components.UIBase;
import com.isolator.game.gfx.ImageEffect;

import java.util.List;

public abstract class AIState {

    public abstract void update(GameState state, Visitor controlledEntity);
    public abstract boolean isReadyToTransition();
    public UIBase getDebugUI(GameState state, BaseEntity baseEntity) {
        return new UIText(this.getClass().getSimpleName());
    }
    public String getAnimationName() { return "stand"; }

    public List<ImageEffect> getImageEffects() {
        return List.of();
    }
}
