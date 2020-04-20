package com.isolator.engine;

import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.CollisionResolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public abstract class GameState {

    protected GameTimer gameTimer;
    protected Input input;
    protected RunMode mode;
    protected final CollisionResolver collisionResolver;
    protected Camera camera;
    protected GameScene scene;

    protected final List<BaseObject> gameObjects;
    protected final List<UIContainer> uiContainers;

    protected Random random;

    protected float gameSpeed = 1;

    public GameState() {
        gameTimer = new GameTimer(60);
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        this.collisionResolver = new CollisionResolver();
        mode = RunMode.DEFAULT;
        random = new Random(123);
    }

    public void update() {
        gameTimer.update();
        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public Camera getCamera() {
        return camera;
    }

    public void addObject(BaseObject object) {
        gameObjects.add(object);
    }

    public void increaseGameSpeed() {
        if(gameSpeed <= 3)
            gameSpeed += 0.5;
    }

    public void decreaseGameSpeed() {
        if(gameSpeed >= 1.0)
            gameSpeed -= 0.5;
    }

    public void addUIContainer(UIContainer container) {
        uiContainers.add(container);
    }

    public void toggleDebugMode() {
        if(mode == RunMode.DEFAULT) {
            mode = RunMode.DEBUG;
        } else {
            mode = RunMode.DEFAULT;
        }
    }

    protected void setupGame(Camera camera, Input input) {
        this.camera = camera;
        this.input = input;
    }

    public RunMode getRunMode() {
        return mode;
    }

    public float getGameSpeed() {
        return gameSpeed;
    }

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }

    public GameScene getActiveScene() {
        return scene;
    }

    public Size getSceneSize() {
        return scene.getSceneSize();
    }

    public List<BaseObject> getObjects() {
        return gameObjects;
    }

    public abstract Iterable<BaseObject> getObjectsWithinViewingBounds();

    public Random getRandomGenerator() {
        return random;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }
}
