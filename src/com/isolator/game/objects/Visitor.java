package com.isolator.game.objects;

import com.isolator.engine.GameState;
import com.isolator.game.ai.AIStateMachine;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.Velocity;
import com.isolator.game.IsolatorGameState;

public class Visitor extends BaseEntity {

    private AIStateMachine ai;
    private double maxVelocity;

    public Visitor(Controller controller) {
        super(controller);
        ai = new AIStateMachine();
        maxVelocity = Math.random() * (3 - 1.5f) + 1.5f;
        this.velocity = new Velocity(0.5f, maxVelocity);
    }

    @Override
    public void update(GameState state) {
        super.update(state);
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
