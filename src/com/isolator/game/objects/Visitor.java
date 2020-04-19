package com.isolator.game.objects;

import com.isolator.engine.GameState;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.ai.AIStateMachine;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.Velocity;

public class Visitor extends BaseEntity {

    private final AIStateMachine ai;

    public Visitor(Controller controller) {
        super(controller);
        ai = new AIStateMachine();
        double maxVelocity = Math.random() * (3 - 1.5f) + 1.5f;
        this.velocity = new Velocity(0.5f, maxVelocity);
    }

    @Override
    protected void checkCollisions(IsolatorGameState state) {
        state.getCollisionResolver().handlePropCollisions(state, this);
    }

    @Override
    public void update(GameState state) {
        super.update((IsolatorGameState) state);
        ai.update(state, this);
        updateUIContainer(state);
    }

    private void updateUIContainer(GameState state) {
        uiContainer.addElement(getDebugUIText());
        uiContainer.addElement(ai.getCurrentState().getDebugUI(state, this));
    }

    public Controller getController() {
        return controller;
    }
}
