package com.isolator.game;

import com.isolator.controller.AIController;
import com.isolator.controller.HumanController;
import com.isolator.controller.Input;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.display.Camera;
import com.isolator.entity.BaseEntity;
import com.isolator.entity.Player;
import com.isolator.entity.Visitor;
import com.isolator.map.GameMap;
import com.isolator.ui.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {

    private GameMap map;
    private Camera camera;
    private Input input;
    private Size cellSize;
    private RunMode mode;
    private CollisionResolver collisionResolver;

    private List<BaseEntity> entities;
    private List<UIContainer> uiContainers;

    private float gameSpeed = 1;

    public GameState(Camera camera, Input input) {
        cellSize = new Size(64, 64);
        this.map = new GameMap(12, 12, cellSize);
        this.camera = camera;
        this.collisionResolver = new CollisionResolver();
        entities = new ArrayList<>();
        this.input = input;
        mode = RunMode.DEFAULT;
        initUI();
        initGame();
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        map.setAtRandomAvailableLocation(player);
        addEntity(player);

        for(int i = 0; i < 100; i++) {
            Visitor visitor = new Visitor(new AIController());
            map.setAtRandomAvailableLocation(visitor);
            addEntity(visitor);
        }

        camera.followEntity(player);
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

    public void addEntity(BaseEntity entity) {
        entities.add(entity);
    }

    public void increaseGameSpeed() {
        if(gameSpeed <= 3)
            gameSpeed += 0.5;
    }

    public void decreaseGameSpeed() {
        if(gameSpeed >= 1.0)
            gameSpeed -= 0.5;
    }

    public List<BaseEntity> getViewableEntities() {
        return entities
                .stream()
                .filter(entity -> withinViewingBounds(entity))
                .collect(Collectors.toList());
    }

    private boolean withinViewingBounds(BaseEntity entity) {
        Position startRenderingPosition = map.getViewableStartingPosition(camera);
        Position endRenderingPosition = map.getViewableEndingPosition(camera);

        int y = (entity.getPosition().getY() / cellSize.getHeight());
        int x = (entity.getPosition().getX() / cellSize.getWidth());

        return y > startRenderingPosition.getY() - 2 && y < endRenderingPosition.getY()
                && x > startRenderingPosition.getX() - 3 && x < endRenderingPosition.getX();
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

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }
}
