package com.stopcoronagame.controller;

public interface Controller {
    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingRight();
    boolean isRequestingLeft();
    boolean isDoingAction();
}
