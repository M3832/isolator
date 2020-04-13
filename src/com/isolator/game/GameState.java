package com.isolator.game;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.display.Camera;
import com.isolator.entity.BaseEntity;
import com.isolator.map.GameMap;
import com.isolator.ui.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameState {

    private GameMap map;
    private Camera camera;
    private Size cellSize;
    private RunMode mode;

    private List<BaseEntity> entities;
    private List<UIContainer> uiContainers;

    private float gameSpeed = 1;

    public GameState(Camera camera) {
        cellSize = new Size(50, 50);
        this.map = new GameMap(50, 50, cellSize);
        this.camera = camera;
        entities = new ArrayList<>();
        mode = RunMode.DEFAULT;
        initUI();
    }

    private void initUI() {
        uiContainers = new ArrayList<>();
    }

    public void update() {
        entities.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        entities.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public GameMap getMap() {
        return map;
    }

    public Camera getCamera() {
        return camera;
    }

    public void addEntityAtPosition(BaseEntity entity, Position position) {
        entity.setPosition(position);
        entities.add(entity);
    }

    public boolean checkCollisionWithWalls(CollisionBox collisionBox) {
        return map.getUnwalkableGridCells(camera).stream()
                .anyMatch(gridCell -> gridCell.getCollisionBox().checkCollision(collisionBox));
    }

    public CollisionBox getCollisionIntersection(CollisionBox collisionBox) {
        return map.getUnwalkableGridCells(camera).stream()
                .filter(gridCell -> gridCell.getCollisionBox().checkCollision(collisionBox))
                .findFirst()
                .get()
                .getCollisionBox()
                .getIntersection(collisionBox);
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

    public void addUIContainer(UIContainer container) {
        uiContainers.add(container);
    }

    public Size getCellSize() {
        return cellSize;
    }

    public void toggleDebugMode() {
        if(mode == RunMode.DEFAULT) {
            mode = RunMode.DEBUG;
        } else {
            mode = RunMode.DEFAULT;
        }
    }

    public RunMode getRunMode() {
        return mode;
    }
}
