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
import com.isolator.game.logic.InfectionStatus;
import com.isolator.game.map.GameMap;
import com.isolator.game.map.Pathfinder;
import com.isolator.game.ui.GameTimePanel;
import com.isolator.game.ui.UIInfectionPanel;

import java.util.List;
import java.util.stream.Collectors;

public class IsolatorGameState extends GameState {

    private GameMap map;
    private final Size cellSize;
    private UIInfectionPanel infectionPanel;
    private GameTimePanel gameTime;
    private Pathfinder pathfinder;

    public IsolatorGameState() {
        super();
        cellSize = new Size(64, 64);
        initMap();
        pathfinder = new Pathfinder(getObjects(), 24, 12);
        initUI();
    }

    private void initMap() {
        this.map = new GameMap(24, 12, cellSize);
        this.map.addWallsToPerimeter(this);
        this.scene = map;
    }

    private void initUI() {
        this.infectionPanel = new UIInfectionPanel();
        this.gameTime = new GameTimePanel();
        uiContainers.addAll(List.of(infectionPanel, gameTime));
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        player.setPosition(map.getRandomAvailableLocation(this, player.getSize()));
        addObject(player);

        for(int i = 0; i < 25; i++) {
            generateGroupOfVisitors();
        }
        initSickPeople(5);

        camera.followEntity(player);
    }

    private void initSickPeople(int amount) {
        gameObjects.stream()
                .filter(o -> o instanceof Visitor)
                .map(o -> (Visitor) o)
                .limit(amount)
                .forEach(v -> v.setInfectionStatus(new InfectionStatus(InfectionStatus.Status.SICK)));
    }

    private void generateGroupOfVisitors() {
        Group group = new Group();
        int numberOfMembers = random.nextInt(9) + 1;
        for(int i = 0; i < numberOfMembers; i++) {
            Visitor visitor = new Visitor(new AIController(), random);
            visitor.setPosition(
                    map.getRandomAvailableLocation(this, visitor.getSize())
            );

            group.addMember(visitor);
            addObject(visitor);
        }

        addObject(group);
    }

    @Override
    public void update() {
        super.update();
        infectionPanel.update(this);
        gameTime.update(this);
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

    public Pathfinder getPathFinder() {
        return pathfinder;
    }
}
