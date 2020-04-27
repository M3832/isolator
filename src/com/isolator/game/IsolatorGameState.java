package com.isolator.game;

import com.isolator.engine.game.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.controller.HumanController;
import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.entity.Player;
import com.isolator.game.entity.Visitor;
import com.isolator.game.logic.Group;
import com.isolator.game.logic.InfectionStatus;
import com.isolator.game.map.GameMap;
import com.isolator.game.map.Pathfinder;
import com.isolator.game.ui.DefeatScreen;
import com.isolator.game.ui.GameTimePanel;
import com.isolator.game.ui.UIInfectionPanel;
import com.isolator.game.ui.VictoryScreen;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IsolatorGameState extends GameState {

    private GameMap map;
    private final Size cellSize;
    private Pathfinder pathfinder;
    private UIContainer victoryScreen;
    private UIContainer defeatScreen;

    public IsolatorGameState() {
        super();
        cellSize = new Size(64, 64);
        initMap();
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        uiContainers.addAll(List.of(new UIInfectionPanel(), new GameTimePanel(), defeatScreen, victoryScreen));
        pathfinder = new Pathfinder(getObjects(), 24, 12);
        initConditions();
    }

    private void initConditions() {
        losingConditions.add(state -> ((IsolatorGameState)state).getStreamOfVisitors()
                .filter(v -> v.isSick())
                .count() > 10);

        victoryConditions.add(state -> ((IsolatorGameState)state).getStreamOfVisitors()
                .filter(v -> v.isSick() || v.isInfected())
                .count() == 0);
    }

    private void initMap() {
        this.map = new GameMap(24, 12, cellSize);
        this.map.addWallsToPerimeter(this);
        this.scene = map;
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        player.setPosition(map.getRandomAvailableLocation(this));
        addObject(player);

        for(int i = 0; i < 25; i++) {
            generateGroupOfVisitors();
        }
        initSickPeople(1);

        camera.followEntity(player);
    }

    private void initSickPeople(int amount) {
        getStreamOfVisitors()
                .limit(amount)
                .forEach(v -> v.setInfectionStatus(new InfectionStatus(InfectionStatus.Status.SICK)));
    }

    private void generateGroupOfVisitors() {
        Group group = new Group();
        int numberOfMembers = random.nextInt(9) + 1;
        for(int i = 0; i < numberOfMembers; i++) {
            Visitor visitor = new Visitor(new AIController(), random);
            visitor.setPosition(map.getRandomAvailableLocation(this));

            group.addMember(visitor);
            addObject(visitor);
        }

        addObject(group);
    }

    @Override
    public void update() {
        super.update();
        if(victoryConditionsMet() && !victoryScreen.isVisible()) {
            victoryScreen.toggleVisibility();
        }
        if(loseConditionsMet() && !defeatScreen.isVisible()) {
            defeatScreen.toggleVisibility();
        }
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

    public List<Position> getPath(Position target, Position start) {
        return pathfinder.findPathTo(target, start);
    }

    public Stream<Visitor> getStreamOfVisitors() {
        return gameObjects.stream()
                .filter(o -> o instanceof Visitor)
                .map(o -> (Visitor) o);
    }

    public List<Shape> getDebugShapes() {
        return List.of(map.getWalkableArea());
    }

}
