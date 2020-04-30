package com.isolator.engine.state;

import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.game.GameScene;
import com.isolator.engine.ui.UIContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class State {
    protected Input input;
    protected GameScene scene;
    protected List<UIContainer> uiContainers;
    protected Random random;

    public State() {
        uiContainers = new ArrayList<>();
        random = new Random(123);
    }

    public void update() {
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
    }

    public Random getRandomGenerator() {
        return random;
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public GameScene getActiveScene() {
        return scene;
    }

    public Size getSceneSize() {
        return scene.getSceneSize();
    }
}
