package com.isolator.game;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.entity.BaseEntity;
import com.isolator.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<BaseEntity> entities;
    private GameMap map;

    public GameState() {
        entities = new ArrayList<>();
        this.map = new GameMap(20, 20, new Size(50, 50));
    }

    public void update() {
        entities.forEach(entity -> entity.update(this));
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public GameMap getMap() {
        return map;
    }

    public void addEntityAtPosition(BaseEntity entity, Position position) {
        entity.setPosition(position);
        entities.add(entity);
    }

    public boolean checkCollisionWithWalls(CollisionBox collisionBox) {
        return map.getUnwalkableGridCells().stream()
                .anyMatch(gridCell -> gridCell.getCollisionBox().checkCollision(collisionBox));
    }

    public CollisionBox getCollisionIntersection(CollisionBox collisionBox) {
        return map.getUnwalkableGridCells().stream()
                .filter(gridCell -> gridCell.getCollisionBox().checkCollision(collisionBox))
                .findFirst()
                .get()
                .getCollisionBox()
                .getIntersection(collisionBox);
    }
}
