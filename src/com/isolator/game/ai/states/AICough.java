package com.isolator.game.ai.states;

import com.isolator.engine.state.GameState;
import com.isolator.game.controller.AIController;
import com.isolator.game.states.IsolatorGameState;
import com.isolator.game.entity.Visitor;

public class AICough extends AIStand {

    public AICough(IsolatorGameState state) {
        super(state.getGameTimer().getGameTimeFromNow(1.5));
    }

    @Override
    public void update(GameState state, Visitor controlledEntity) {
        super.update(state, controlledEntity);
        controlledEntity.immediateStop();
        ((AIController)controlledEntity.getController()).stop();
    }

    @Override
    public String getAnimationName() { return "cough"; }
}
