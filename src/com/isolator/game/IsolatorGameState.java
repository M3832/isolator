package com.isolator.game;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.controller.HumanController;
import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.gameobjects.Grouping;
import com.isolator.game.objects.Player;
import com.isolator.game.objects.Visitor;
import com.isolator.game.map.GameMap;

import java.util.*;

public class IsolatorGameState extends GameState {

    private final GameMap map;
    private final Size cellSize;

    private final List<BaseObject> objectsInView;

    private final Map<Integer, Grouping> groupings;

    public IsolatorGameState() {
        super();
        groupings = new HashMap<>();
        cellSize = new Size(64, 64);
        this.map = new GameMap(16, 12, cellSize);
        this.map.addWallsToPerimeter(this);
        objectsInView = new ArrayList<>();
        this.scene = map;
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        player.setPosition(
                map.getRandomAvailableLocation(this, player.getSize())
        );
        addObjectWithGroupings(player, List.of(Grouping.ENTITIES));

        for(int i = 0; i < 100; i++) {
            Visitor visitor = new Visitor(new AIController());
            visitor.setPosition(
                    map.getRandomAvailableLocation(this, visitor.getSize())
            );
            addObjectWithGroupings(visitor, List.of(Grouping.ENTITIES));
        }

        camera.followEntity(player);
    }

    public void update() {
        gameObjects.sort(Comparator.comparing(baseEntity -> baseEntity.getPosition().getY()));
        gameObjects.forEach(entity -> entity.update(this));
        camera.update(this);
        updateObjectsInView();
    }

    public void addObjectWithGroupings(BaseObject object, List<Integer> groupings) {
        addObject(object);
        for(int i : groupings) {
            addToGrouping(i, object);
        }
    }

    private void addToGrouping(int groupingType, BaseObject object) {
        if(!groupings.containsKey(groupingType)) {
            groupings.put(groupingType, new Grouping());
        }

        Grouping grouping = groupings.get(groupingType);
        grouping.addObject(object);
    }

    public GameMap getMap() {
        return map;
    }

    @Override
    public Iterable<BaseObject> getObjectsWithinViewingBounds() {
        return objectsInView;
    }

    private void updateObjectsInView() {
        objectsInView.clear();

        for(BaseObject o : gameObjects) {
            if(withinViewingBounds(o)) {
                objectsInView.add(o);
            }
        }
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

    public Grouping getGrouping(int groupingType) {
        return groupings.get(groupingType);
    }
}
