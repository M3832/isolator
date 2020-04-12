package com.isolator.entity;

import com.isolator.ai.AIStateMachine;
import com.isolator.controller.Controller;
import com.isolator.core.Velocity;
import com.isolator.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Visitor extends BaseEntity {

    private AIStateMachine ai;

    public Visitor(Controller controller) {
        super(controller);
        ai = new AIStateMachine();
        this.velocity = new Velocity(0.5f, (Math.random() * 3 - 1) + 1);
    }

    @Override
    public Image getSprite() {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = bufferedImage.createGraphics();

        imageGraphics.setColor(Color.BLUE);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());
        imageGraphics.setColor(Color.BLACK);
        imageGraphics.setFont(new Font("Serif", Font.BOLD, 50));
        imageGraphics.drawString("HELLO", 0, 0);

        return bufferedImage;
    }

    @Override
    public void update(GameState state) {
        super.update(state);
        ai.update(state, this);
    }

    public Controller getController() {
        return controller;
    }
}
