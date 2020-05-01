package com.isolator.game.controller;

public interface Controller {
    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingRight();
    boolean isRequestingLeft();
    boolean isRequestingAction();
    boolean isRequestingMove();
}
