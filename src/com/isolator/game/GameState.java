package com.isolator.game;

import com.isolator.core.Size;
import com.isolator.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private Size mapSize;
    private List<BaseEntity> entities;

    public GameState(Size mapSize) {
        entities = new ArrayList<>();
        this.mapSize = mapSize;
    }

    public void update() {
        entities.forEach(entity -> entity.update(this));
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public void addEntity(BaseEntity entity) {
        entities.add(entity);
    }

    public Size getMapSize() {
        return mapSize;
    }

    public boolean isOutOfBoundsOnX(int posX) {
        return posX >= getMapSize().getWidth();
    }
}
