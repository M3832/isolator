package com.isolator.game.entity;

import com.isolator.engine.state.GameState;
import com.isolator.engine.core.Vector2;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.states.IsolatorGameState;
import com.isolator.game.ai.AIStateMachine;
import com.isolator.game.controller.Controller;
import com.isolator.engine.core.MovementMotor;
import com.isolator.game.ai.states.AICough;
import com.isolator.game.ai.states.AIState;
import com.isolator.game.ai.states.AIWander;
import com.isolator.game.gfx.ColorOverlay;
import com.isolator.game.gfx.ImageEffect;
import com.isolator.game.gfx.Outline;
import com.isolator.game.logic.InfectionStatus;
import com.isolator.game.logic.RemoveTrigger;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Visitor extends BaseEntity {

    private final AIStateMachine ai;
    private InfectionStatus infectionStatus;
    private ImageEffect markedEffect;

    public Visitor(Controller controller, Random random) {
        super(controller);
        ai = new AIStateMachine();
        infectionStatus = new InfectionStatus();
        double maxVelocity = random.nextDouble() * (3.5f - 1.5f) + 1.5f;
        this.movementMotor = new MovementMotor(0.5f, maxVelocity);
        markedEffect = new Outline(new Color(94, 156, 255));
    }

    @Override
    public void update(GameState state) {
        super.update((IsolatorGameState) state);
        ai.update(state, this);
        infectionStatus.update((IsolatorGameState) state, this);
        updateUIContainer(state);
    }

    private void updateUIContainer(GameState state) {
        uiContainer.addElement(getDebugUIText());
        AIState aiState = ai.getCurrentState();
        uiContainer.addElement(aiState.getDebugUI(state, this));
    }

    @Override
    protected List<ImageEffect> getImageEffects() {
        return Stream.of(imageEffects, ai.getCurrentState().getImageEffects())
                .flatMap(imageEffects -> imageEffects.stream())
                .collect(Collectors.toList());
    }

    @Override
    protected void decideOnAnimation() {
        animationController.playAnimation(ai.getCurrentState().getAnimationName());
    }

    public void focus() {
        imageEffects.add(markedEffect);
    }

    public void removeFocus() {
        imageEffects.remove(markedEffect);
    }

    public Controller getController() {
        return controller;
    }

    public boolean isIdle() {
        return ai.isReadyToTransition();
    }

    public void perform(AIState action) {
        if(!infectionStatus.isIsolated()) {
            ai.setCurrentState(action);
        }
    }

    public void handleCollision(IsolatorGameState state, BaseObject other) {
        super.handleCollision(state, other);

        if(other instanceof BaseEntity
                && !(other instanceof Player)
                && controller.isRequestingMove()
                && isFacingExactly(other.getCollisionBox().getPosition())) {
            BaseEntity entity = (BaseEntity) other;
            Vector2 direction = Vector2.directionBetweenPositions(getPosition(), other.getPosition());
            Vector2 normalizedDirection = direction.normalized();
            Vector2 experiment = new Vector2(-normalizedDirection.getY(), -normalizedDirection.getX());
            entity.push(experiment, getCurrentSpeed());
        }
    }

    public boolean isHealthy() {
        return infectionStatus.isWell();
    }
    public boolean isInfected() {
        return infectionStatus.isInfected();
    }
    public boolean isSick() {
        return infectionStatus.isSick();
    }

    public void cough(IsolatorGameState state) {
        perform(new AICough(state));
        state.spawn(new Cough(position));
    }

    public void setInfectionStatus(InfectionStatus infectionStatus) {
        this.infectionStatus = infectionStatus;
    }

    public void immediateStop() {
        movementMotor.stop();
    }

    public void exposeToVirus(IsolatorGameState state) {
        infectionStatus.exposeWithRisk(state.getRandomGenerator(), 0.01);
    }

    public void isolate(IsolatorGameState state) {
        RemoveTrigger trigger = state.getObjects().stream()
                .filter(o -> o instanceof RemoveTrigger)
                .map(o -> (RemoveTrigger) o)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No RemoveTrigger present"));
        moveTo(state, trigger);
        infectionStatus = new InfectionStatus(InfectionStatus.Status.ISOLATED);
        imageEffects.add(new ColorOverlay(new Color(0.5f, 0.5f, 0.5f, 0.5f)));
    }

    private void moveTo(IsolatorGameState state, BaseObject trigger) {
        perform(new AIWander(state.getPath(trigger.getPosition(), position)));
    }

    public boolean isIsolated() {
        return infectionStatus.isIsolated();
    }
}
