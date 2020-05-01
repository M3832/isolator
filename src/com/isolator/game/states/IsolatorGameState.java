package com.isolator.game.states;

import com.isolator.engine.game.Condition;
import com.isolator.engine.state.GameState;
import com.isolator.game.controller.AIController;
import com.isolator.game.controller.HumanController;
import com.isolator.engine.input.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.UIContainer;
import com.isolator.game.entity.Player;
import com.isolator.game.entity.Visitor;
import com.isolator.game.logic.Group;
import com.isolator.game.logic.InfectionStatus;
import com.isolator.game.logic.RemoveTrigger;
import com.isolator.game.map.GameMap;
import com.isolator.game.map.Pathfinder;
import com.isolator.game.ui.DefeatScreen;
import com.isolator.game.ui.GameTimePanel;
import com.isolator.game.ui.UIInfectionPanel;
import com.isolator.game.ui.VictoryScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IsolatorGameState extends GameState {

    private GameMap map;
    private final Size cellSize;
    private Pathfinder pathfinder;
    private UIContainer victoryScreen;
    private UIContainer defeatScreen;
    protected List<Condition> victoryConditions;
    protected List<Condition> losingConditions;

    public IsolatorGameState(Size windowSize, Input input) {
        super(windowSize);
        this.input = input;
        camera = new Camera(windowSize);
        cellSize = new Size(64, 64);
        initConditions();
        initMap();
        initUI();
        initGame();
        pathfinder = new Pathfinder(getObjects(), 32, 16);
    }

    private void initUI() {
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        uiScreen.addContainers(List.of(new UIInfectionPanel(), new GameTimePanel(), defeatScreen, victoryScreen));
    }

    private void initConditions() {
        victoryConditions = new ArrayList<>();
        losingConditions = new ArrayList<>();
        losingConditions.add(state -> ((IsolatorGameState)state).getStreamOfVisitors()
                .filter(v -> v.isSick())
                .count() > 10);

        victoryConditions.add(state -> ((IsolatorGameState)state).getStreamOfVisitors()
                .filter(v -> v.isSick() || v.isInfected())
                .count() == 0);
    }

    private void initMap() {
        this.map = new GameMap(32, 16, cellSize);
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
        addObject(new RemoveTrigger(new Position(getSceneSize().getWidth()/2, getSceneSize().getHeight() - cellSize.getHeight() / 2), cellSize));

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
        checkConditions();
    }

    private void checkConditions() {
        if(victoryConditionsMet() && !victoryScreen.isVisible()) {
            victoryScreen.toggleVisibility();
        }
        if(loseConditionsMet() && !defeatScreen.isVisible()) {
            defeatScreen.toggleVisibility();
        }
    }

    private boolean victoryConditionsMet() {
        return victoryConditions.stream().allMatch(victoryCondition -> victoryCondition.condition(this));
    }

    private boolean loseConditionsMet() {
        return losingConditions.stream().allMatch(victoryCondition -> victoryCondition.condition(this));
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
