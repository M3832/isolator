package com.isolator.game.entity;

import com.isolator.engine.GameState;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.ai.AIStateMachine;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.MovementMotor;
import com.isolator.game.ai.states.AIState;

public class Visitor extends BaseEntity {

    private final AIStateMachine ai;

    public Visitor(Controller controller, double maxVelocity) {
        super(controller);
        ai = new AIStateMachine();
        this.movementMotor = new MovementMotor(0.5f, maxVelocity);
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

    public boolean isIdle() {
        return ai.isReadyToTransition();
    }

    public void perform(AIState action) {
        ai.setCurrentState(action);
    }
}
