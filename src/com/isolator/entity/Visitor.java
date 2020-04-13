package com.isolator.entity;

import com.isolator.ai.AIStateMachine;
import com.isolator.ai.states.AIState;
import com.isolator.controller.Controller;
import com.isolator.core.Velocity;
import com.isolator.game.GameState;
import com.isolator.ui.UIText;

import java.awt.*;

public class Visitor extends BaseEntity {

    private AIStateMachine ai;

    public Visitor(Controller controller) {
        super(controller);
        ai = new AIStateMachine();
        this.velocity = new Velocity(0.5f, (Math.random() * 3 - 1) + 1);
    }

    @Override
    protected void drawSprite(Graphics2D imageGraphics) {
        imageGraphics.setColor(Color.BLUE);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
    }

    @Override
    public void update(GameState state) {
        super.update(state);
        ai.update(state, this);
        refreshUIContainer(state);
    }

    private void refreshUIContainer(GameState state) {
        uiContainer.clear();
        uiContainer.addElement(ai.getCurrentState().getDebugUI(state, this));
    }

    public Controller getController() {
        return controller;
    }
}
