package com.isolator.game;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.display.Camera;
import com.isolator.entity.BaseEntity;
import com.isolator.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<BaseEntity> entities;
    private GameMap map;
    private Camera camera;
    private float gameSpeed = 1;

    public GameState(Camera camera) {
        entities = new ArrayList<>();
        this.map = new GameMap(20, 20, new Size(100, 100));
        this.camera = camera;
    }

    public void update() {
        entities.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    public List<BaseEntity> getEntities() {
        return entities;
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
        System.out.println("Decreasing game speed by 0.5");
        if(gameSpeed >= 1.0)
            gameSpeed -= 0.5;
    }

    public float getGameSpeed() {
       // System.out.println(gameSpeed);
        return gameSpeed;
    }
}
