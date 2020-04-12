package com.isolator.game;

import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.entity.BaseEntity;
import com.isolator.entity.Player;
import com.isolator.map.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameState {

    private Size mapSize;
    private List<BaseEntity> entities;
    private GameMap map;

    public GameState(Size mapSize) {
        entities = new ArrayList<>();
        this.map = new GameMap(20, 20, new Size(50, 50));
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

    public GameMap getMap() {
        return map;
    }

    public void addEntityAtPosition(BaseEntity entity, Position position) {
        entity.setPosition(position);
        entities.add(entity);
    }
}
