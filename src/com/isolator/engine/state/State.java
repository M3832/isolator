package com.isolator.engine.state;

import com.isolator.engine.core.Position;
import com.isolator.engine.display.Camera;
import com.isolator.engine.input.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.game.Scene;
import com.isolator.engine.scene.FloatingShapeBackgroundScene;
import com.isolator.engine.ui.UIScreen;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public abstract class State {
    protected Input input;
    protected Camera camera;
    protected Scene scene;
    protected UIScreen uiScreen;
    protected Random random;

    protected State nextState;

    public State() {
        this(new Size(1, 1), new Input());
    }

    public State(Size windowSize, Input input) {
        random = new Random(123);
        uiScreen = new UIScreen(windowSize);
        camera = new Camera(windowSize);
        scene = new FloatingShapeBackgroundScene(windowSize);
        this.input = input;
    }

    public void update() {
        uiScreen.update(this);
        scene.update(this);
    }

    public void setNextState(State state) {
        nextState = state;
    }

    public Random getRandomGenerator() {
        return random;
    }

    public UIScreen getUiScreen() {
        return uiScreen;
    }

    public Size getSceneSize() {
        return scene.getSceneSize();
    }

    public Camera getCamera() {
        return camera;
    }

    public Position getMousePosition() {
        return input.getMousePosition();
    }

    public Image getSceneImage() {
        return scene.getSceneGraphics(this);
    }

    public boolean mouseButtonClicked() {
        return input.mouseButtonClicked(MouseEvent.BUTTON1);
    }

    public boolean hasNextState() {
        return nextState != null;
    }

    public State getNextState() {
        return nextState;
    }
}
