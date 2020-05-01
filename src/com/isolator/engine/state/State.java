package com.isolator.engine.state;

import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.input.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.game.GameScene;
import com.isolator.engine.ui.UIScreen;

import java.awt.*;
import java.util.Random;

public abstract class State {
    protected Input input;
    protected GameScene scene;
    protected UIScreen uiScreen;
    protected Random random;

    public State() {
        random = new Random(123);
        uiScreen = new UIScreen(new Size(1, 1));
    }

    public State(Size windowSize) {
        this();
        uiScreen = new UIScreen(windowSize);
    }

    public void update() {
        uiScreen.update(this);
    }

    public Random getRandomGenerator() {
        return random;
    }

    public UIScreen getUiScreen() {
        return uiScreen;
    }

    public GameScene getActiveScene() {
        return scene;
    }

    public Size getSceneSize() {
        return scene.getSceneSize();
    }
}
