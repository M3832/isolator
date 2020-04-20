package com.isolator.engine.controller;

public interface Controller {
    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingRight();
    boolean isRequestingLeft();
    boolean isRequestingAction();
    boolean isRequestingSpeedUp();
    boolean isRequestingSlowDown();

    boolean isRequestingMove();
}