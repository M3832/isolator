package com.isolator.engine.controller;

public class AIController implements Controller {
    private boolean up, down, left, right;

    @Override
    public boolean isRequestingUp() {
        return up;
    }

    @Override
    public boolean isRequestingDown() {
        return down;
    }

    @Override
    public boolean isRequestingRight() {
        return right;
    }

    @Override
    public boolean isRequestingLeft() {
        return left;
    }

    @Override
    public boolean isRequestingAction() {
        return false;
    }

    @Override
    public boolean isRequestingSpeedUp() {
        return false;
    }

    @Override
    public boolean isRequestingSlowDown() {
        return false;
    }

    @Override
    public boolean isRequestingMove() {
        return isRequestingDown() || isRequestingLeft() || isRequestingRight() ||isRequestingUp();
    }

    @Override
    public boolean isRequestingDebugMode() {
        return false;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void stop() {
        up = false;
        left = false;
        right = false;
        down = false;
    }
}
