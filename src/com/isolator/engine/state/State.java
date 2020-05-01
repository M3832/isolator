package com.isolator.engine.state;

import com.isolator.engine.core.Position;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.input.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.game.Scene;
import com.isolator.engine.scene.FloatingShapeBackgroundScene;
import com.isolator.engine.ui.UIScreen;

import java.awt.*;
import java.util.Random;

public abstract class State {
    protected Input input;
    protected Camera camera;
    protected Scene scene;
    protected UIScreen uiScreen;
    protected Random random;

    public State() {
        this(new Size(1, 1));
    }

    public State(Size windowSize) {
        random = new Random(123);
        uiScreen = new UIScreen(windowSize);
        camera = new Camera(windowSize);
        scene = new FloatingShapeBackgroundScene(windowSize);
    }

    public void update() {
        uiScreen.update(this);
        scene.update(this);
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
}
