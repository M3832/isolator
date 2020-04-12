package com.stopcoronagame.entity;

import com.stopcoronagame.controller.Controller;
import com.stopcoronagame.controller.Input;
import com.stopcoronagame.core.*;
import com.stopcoronagame.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BaseEntity {
    private Controller controller;
    private Velocity velocity;
    private Position position;
    private Direction direction;
    private Size size;

    public BaseEntity() {
        this(null);
    }

    public BaseEntity(Controller controller) {
        this.position = new Position(0, 0);
        this.size = new Size(50, 50);
        this.velocity = new Velocity(0.5f, 5.0f);
        this.controller = controller;
        this.direction = Direction.N;
    }

    public Image getSprite() {
        BufferedImage bufferedImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = bufferedImage.createGraphics();

        imageGraphics.setColor(Color.RED);
        imageGraphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        return bufferedImage;
    }

    public void update(GameState state) {
        velocity.update(controller);
        position.apply(velocity);
        direction = Direction.fromVelocity(velocity, direction);

        if(state.isOutOfBoundsOnX(position.getX())) {
            position.setX(0);
        }
    }

    public Position getPosition() {
        return position;
    }
}
