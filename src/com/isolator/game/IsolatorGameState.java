package com.isolator.game;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.controller.HumanController;
import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.objects.BaseEntity;
import com.isolator.game.objects.Player;
import com.isolator.game.objects.Visitor;
import com.isolator.game.map.GameMap;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IsolatorGameState extends GameState {

    private GameMap map;
    private Size cellSize;

    public IsolatorGameState() {
        super();
        cellSize = new Size(64, 64);
        this.map = new GameMap(16, 12, cellSize);
        this.map.addWallsToPerimeter(this);
        this.scene = map;
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        map.setAtRandomAvailableLocation(this, player);
        addObject(player);

        for(int i = 0; i < 100; i++) {
            Visitor visitor = new Visitor(new AIController());
            map.setAtRandomAvailableLocation(this, visitor);
            addObject(visitor);
        }

        camera.followEntity(player);
    }

    public void update() {
        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));
        camera.update(this);
    }

    public GameMap getMap() {
        return map;
    }

    public List<BaseEntity> getEntitiesWithinViewingBounds() {
        return getEntities()
                .stream()
                .filter(entity -> withinViewingBounds(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<BaseObject> getObjectsWithinViewingBounds() {
        return getObjects()
                .stream()
                .filter(entity -> withinViewingBounds(entity))
                .collect(Collectors.toList());
    }

    private boolean withinViewingBounds(BaseObject entity) {
        Position startRenderingPosition = map.getViewableStartingPosition(camera);
        Position endRenderingPosition = map.getViewableEndingPosition(camera);

        int y = (entity.getPosition().getY() / cellSize.getHeight());
        int x = (entity.getPosition().getX() / cellSize.getWidth());

        return y > startRenderingPosition.getY() - 2 && y < endRenderingPosition.getY()
                && x > startRenderingPosition.getX() - 3 && x < endRenderingPosition.getX();
    }

    @Override
    public void setupGame(Camera camera, Input input) {
        this.camera = camera;
        this.input = input;
        initGame();
    }
}
