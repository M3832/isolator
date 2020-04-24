package com.isolator.game.entity;

import com.isolator.engine.GameState;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.IsolatorGameState;
import com.isolator.game.ai.AIStateMachine;
import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.MovementMotor;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.gfx.Outline;
import com.isolator.game.logic.InfectionStatus;

import java.awt.*;

public class Visitor extends BaseEntity {

    private final AIStateMachine ai;
    private final InfectionStatus status;

    public Visitor(Controller controller, double maxVelocity) {
        super(controller);
        ai = new AIStateMachine();
        status = new InfectionStatus();
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

    @Override
    protected void decideOnAnimation() {
        animationController.playAnimation(ai.getCurrentState().getAnimationName());
    }

    public void focus() {
        imageEffects.add(new Outline(new Color(94, 156, 255)));
    }

    public void removeFocus() {
        imageEffects.clear();
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

    public void handleCollision(IsolatorGameState state, BaseObject object) {
        super.handleCollision(state, object);

        if(object instanceof BaseEntity && !(object instanceof Player)) {
            if(isMovingToward(object.getPosition())) {
                movementMotor.rotateByAngle(20);
            }
        }
    }

    public boolean isHealthy() {
        return status.isWell();
    }
    public boolean isInfected() {
        return status.isInfected();
    }
    public boolean isSick() {
        return status.isSick();
    }
}
