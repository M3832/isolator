package com.isolator.engine.game;

import com.isolator.engine.controller.Input;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.CollisionResolver;
import com.isolator.game.entity.BaseEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class GameState {

    protected GameTimer gameTimer;
    protected Input input;
    protected RunMode mode;
    protected final CollisionResolver collisionResolver;
    protected Camera camera;
    protected GameScene scene;

    protected final List<BaseObject> gameObjects;
    protected final List<BaseObject> readyForSpawn;
    protected final List<UIContainer> uiContainers;
    protected final List<Condition> victoryConditions;
    protected final List<Condition> losingConditions;

    protected Random random;

    protected float gameSpeed = 1;

    public GameState() {
        gameTimer = new GameTimer(60);
        gameObjects = new ArrayList<>();
        readyForSpawn = new ArrayList<>();
        uiContainers = new ArrayList<>();
        victoryConditions = new ArrayList<>();
        losingConditions = new ArrayList<>();
        this.collisionResolver = new CollisionResolver();
        mode = RunMode.DEFAULT;
        random = new Random(123);
    }

    public void update() {
        gameTimer.update();
        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));
        gameObjects.addAll(readyForSpawn);
        camera.update(this);
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        readyForSpawn.clear();
        removeObjects();
    }

    private void removeObjects() {
        for(int i = gameObjects.size() - 1; i >= 0; i--) {
            if(gameObjects.get(i).readyToRemove()) {
                gameObjects.remove(i);
            }
        }
    }

    public boolean victoryConditionsMet() {
        return victoryConditions.stream().allMatch(victoryCondition -> victoryCondition.condition(this));
    }

    public boolean loseConditionsMet() {
        return losingConditions.stream().allMatch(victoryCondition -> victoryCondition.condition(this));
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

    public void spawn(BaseObject object) {
        this.readyForSpawn.add(object);
    }

    public boolean checkCollision(CollisionBox targetCollisionBox) {
        return gameObjects.stream().filter(o -> !(o instanceof BaseEntity)).anyMatch(o -> o.getCollisionBox().checkCollision(targetCollisionBox));
    }

    public List<Shape> getDebugShapes() {
        return List.of();
    }
}
