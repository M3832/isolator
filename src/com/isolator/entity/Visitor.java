package com.isolator.entity;

import com.isolator.ai.AIStateMachine;
import com.isolator.controller.Controller;
import com.isolator.core.Velocity;
import com.isolator.game.GameState;
import com.isolator.gfx.Sprites;

import java.awt.*;

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
    protected void drawSprite(Graphics2D imageGraphics) {
        imageGraphics.setColor(Color.BLUE);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
    }

    @Override
    public Image getDrawGraphics(GameState state) {
        return Sprites.getVisitorSprite();
    }

    @Override
    public void update(GameState state) {
        super.update(state);
        ai.update(state, this);
        updateUIContainer(state);
    }

    private void updateUIContainer(GameState state) {
        uiContainer.clear();
        uiContainer.addElement(getDebugUIText());
        uiContainer.addElement(ai.getCurrentState().getDebugUI(state, this));
    }

    public Controller getController() {
        return controller;
    }
}
