package com.isolator.engine.state;

import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.game.GameTimer;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.UIScreen;
import com.isolator.game.CollisionResolver;
import com.isolator.game.entity.Blocker;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GameState extends State {

    protected GameTimer gameTimer;
    protected final CollisionResolver collisionResolver;

    protected final List<BaseObject> gameObjects;
    protected final List<BaseObject> readyForSpawn;

    public GameState() {
        super();
        gameTimer = new GameTimer(60);
        gameObjects = new ArrayList<>();
        readyForSpawn = new ArrayList<>();

        this.collisionResolver = new CollisionResolver();
    }

    public GameState(Size windowSize) {
        this();
        uiScreen = new UIScreen(windowSize);
    }

    public void update() {
        super.update();

        gameTimer.update();

        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));

        camera.update(this);

        spawnObjects();
        removeObjects();
    }

    private void spawnObjects() {
        gameObjects.addAll(readyForSpawn);
        readyForSpawn.clear();
    }

    private void removeObjects() {
        for(int i = gameObjects.size() - 1; i >= 0; i--) {
            if(gameObjects.get(i).readyToRemove()) {
                gameObjects.remove(i);
            }
        }
    }

    public void addObject(BaseObject object) {
        gameObjects.add(object);
    }

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }

    public List<BaseObject> getObjects() {
        return gameObjects;
    }

    public abstract Iterable<BaseObject> getObjectsWithinViewingBounds();

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public void spawn(BaseObject object) {
        this.readyForSpawn.add(object);
    }

    public boolean checkCollision(CollisionBox targetCollisionBox) {
        return gameObjects.stream().filter(o -> o instanceof Blocker).anyMatch(o -> o.getCollisionBox().checkCollision(targetCollisionBox));
    }

    public List<Shape> getDebugShapes() {
        return List.of();
    }
}
