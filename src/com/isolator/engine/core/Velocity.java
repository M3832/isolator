package com.isolator.engine.core;

import com.isolator.engine.controller.Controller;

public class Velocity {
    private double velocity;
    private final Vector2 direction;
    private final double accelerationRate;
    private final double maxVelocity;
    private final double damper;

    public Velocity(double accelerationRate, double maxVelocity) {
        this.velocity = 0;
        this.direction = new Vector2();
        this.accelerationRate = accelerationRate;
        this.maxVelocity = maxVelocity;
        this.damper = 0.93f;
    }

    public void update(Controller controller) {
        direction.damper(0.5f);

        if(controller.isRequestingRight()) {
            direction.setX(1);
        }

        if(controller.isRequestingLeft()) {
            direction.setX(-1);
        }

        if(controller.isRequestingDown()) {
            direction.setY(1);
        }

        if(controller.isRequestingUp()) {
            direction.setY(-1);

        }

        if(controller.isRequestingMove())
            velocity += accelerationRate;

        clamp();
        velocity *= damper;
    }

    private void clamp() {
        if(velocity < 0.3 && velocity > -0.3) {
            velocity = 0;
        }
    }

    public void immediateStopInDirections(boolean collideX, boolean collideY) {
        if(collideX) {
            direction.setX(0);
        }

        if(collideY) {
            direction.setY(0);
        }
    }

    public boolean isMoving() {
        return velocity != 0;
    }

    public Vector2 getMovement() {
        direction.normalize();
        return direction.multiply(Math.min(velocity, maxVelocity));
    }

    public Vector2 getDirection() {
        return direction;
    }

    public double getVelocity() {
        return velocity;
    }

    public void apply(Vector2 direction, double force) {
        this.direction.add(direction);
        this.velocity = Math.min(this.velocity + accelerationRate, force);
    }
}