package com.isolator.core;

import com.isolator.controller.Controller;

public class Velocity {
    private double velocityX, velocityY;
    private double accelerationRate;
    private double maxVelocity;
    private double dampener;

    public Velocity(double accelerationRate, double maxVelocity) {
        this.velocityX = 0;
        this.velocityY = 0;
        this.accelerationRate = accelerationRate;
        this.maxVelocity = maxVelocity;
        this.dampener = 0.90f;
    }

    public void update(Controller controller) {
        boolean addingForceX = false, addingForceY = false;

        if(controller.isRequestingRight()) {
            velocityX += accelerationRate;
            addingForceX = true;
        }

        if(controller.isRequestingLeft()) {
            velocityX -= accelerationRate;
            addingForceX = true;
        }

        if(controller.isRequestingDown()) {
            velocityY += accelerationRate;
            addingForceY = true;
        }

        if(controller.isRequestingUp()) {
            velocityY -= accelerationRate;
            addingForceY = true;
        }

        clampVelocity();
        dampen(addingForceX, addingForceY);
    }

    private void dampen(boolean addingForceX, boolean addingForceY) {
        if(!addingForceX) {
            velocityX *= dampener;
        }

        if(!addingForceY) {
            velocityY *= dampener;
        }
    }

    private void clampVelocity() {
        velocityX = Math.max(Math.min(velocityX, maxVelocity), -maxVelocity);
        velocityY = Math.max(Math.min(velocityY, maxVelocity), -maxVelocity);

        if(getVelocityVectorLength() > maxVelocity) {
            normalize();
        }

        if(velocityX < 0.3 && velocityX > -0.3) {
            velocityX = 0;
        }

        if(velocityY < 0.3 && velocityY > -0.3) {
            velocityY = 0;
        }
    }

    private void normalize() {
        double vectorLength = getVelocityVectorLength();
        velocityX = velocityX / vectorLength * maxVelocity;
        velocityY = velocityY / vectorLength * maxVelocity;
    }

    private double getVelocityVectorLength() {
        double x = Math.abs(velocityX);
        double y = Math.abs(velocityY);
        return Math.sqrt(x * x + y * y);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void immediateStopInDirections(boolean collideX, boolean collideY) {
        if(collideX) {
            velocityX = 0;
        }

        if(collideY) {
            velocityY = 0;
        }
    }

    public boolean isMoving() {
        return velocityX != 0 || velocityY != 0;
    }
}
