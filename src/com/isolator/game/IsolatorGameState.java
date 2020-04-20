package com.isolator.game;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.controller.HumanController;
import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.game.entity.Player;
import com.isolator.game.entity.Visitor;
import com.isolator.game.logic.Group;
import com.isolator.game.map.GameMap;

import java.util.stream.Collectors;

public class IsolatorGameState extends GameState {

    private final GameMap map;
    private final Size cellSize;

    public IsolatorGameState() {
        super();
        cellSize = new Size(64, 64);
        this.map = new GameMap(16, 25, cellSize);
        this.map.addWallsToPerimeter(this);
        this.scene = map;
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        player.setPosition(
                map.getRandomAvailableLocation(this, player.getSize())
        );
        addObject(player);

        for(int i = 0; i < 75; i++) {
            generateGroupOfVisitors();
        }

        camera.followEntity(player);
    }

    private void generateGroupOfVisitors() {
        Group group = new Group();
        int numberOfMembers = random.nextInt(4) + 1;
        for(int i = 0; i < numberOfMembers; i++) {
            double maxVelocity = random.nextDouble() * (3.5f - 1.5f) + 1.5f;
            Visitor visitor = new Visitor(new AIController(), maxVelocity);
            visitor.setPosition(
                    map.getRandomAvailableLocation(this, visitor.getSize())
            );

            group.addMember(visitor);
            addObject(visitor);
        }

        addObject(group);
    }

    public GameMap getMap() {
        return map;
    }

    @Override
    public Iterable<BaseObject> getObjectsWithinViewingBounds() {
        return gameObjects.stream().filter(this::withinViewingBounds).collect(Collectors.toList());
    }

    public boolean withinViewingBounds(BaseObject entity) {
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
