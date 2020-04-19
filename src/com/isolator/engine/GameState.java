package com.isolator.engine;

import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.gameobjects.Collidable;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.CollisionResolver;
import com.isolator.game.objects.BaseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GameState {

    protected Input input;
    protected RunMode mode;
    protected CollisionResolver collisionResolver;
    protected Camera camera;
    protected GameScene scene;

    protected List<BaseObject> gameObjects;
    protected List<UIContainer> uiContainers;

    protected float gameSpeed = 1;

    public GameState() {
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        this.collisionResolver = new CollisionResolver();
        mode = RunMode.DEFAULT;
    }

    public void update() {
        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    public List<BaseEntity> getEntities() {
        return gameObjects.stream()
                .filter((BaseObject o) -> o instanceof BaseEntity)
                .map((BaseObject o) -> (BaseEntity) o)
                .collect(Collectors.toList());
    }

    public List<Collidable> getCollidables() {
        return gameObjects.stream()
                .filter((BaseObject o) -> o instanceof Collidable)
                .map((BaseObject o) -> (Collidable) o)
                .collect(Collectors.toList());
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

    public List<BaseEntity> getEntitiesWithinViewingBounds() {
        return getEntities();
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

    public Iterable<BaseObject> getObjectsWithinViewingBounds() {
        return gameObjects;
    }
}
