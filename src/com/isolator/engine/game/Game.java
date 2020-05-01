package com.isolator.engine.game;

import com.isolator.engine.input.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Display;
import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    protected List<State> states;
    protected State current;
    protected RunMode mode;
    protected Input input;
    protected Display display;

    protected float gameSpeed = 1;

    public Game(Size windowSize) {
        states = new ArrayList<>();
        mode = RunMode.DEFAULT;
        this.input = new Input();
        this.display = new Display(windowSize, input);
    }

    public void addState(State state) {
        states.add(state);

        if(current == null) {
            current = state;
        }
    }

    public void update() {
        current.update();
        handleGameInput();
        if(current.hasNextState()) {
            current = current.getNextState();
        }
    }

    private void handleGameInput() {
        if(input.isClicked(KeyEvent.VK_ASTERISK)) {
            toggleDebugMode();
        }

        if(input.isClicked(KeyEvent.VK_PLUS)) {
            increaseGameSpeed();
        }

        if(input.isClicked(KeyEvent.VK_MINUS)) {
            decreaseGameSpeed();
        }
    }

    public void toggleDebugMode() {
        if(mode == RunMode.DEFAULT) {
            mode = RunMode.DEBUG;
        } else {
            mode = RunMode.DEFAULT;
        }
    }

    public void increaseGameSpeed() {
        if(gameSpeed <= 3)
            gameSpeed += 0.5;
    }
    public void decreaseGameSpeed() {
        if(gameSpeed >= 1.0)
            gameSpeed -= 0.5;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public State getCurrentState() {
        return current;
    }

    public abstract void render();
}

