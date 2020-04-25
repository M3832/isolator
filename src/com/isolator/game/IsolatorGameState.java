package com.isolator.game;

import com.isolator.engine.GameState;
import com.isolator.engine.controller.AIController;
import com.isolator.engine.controller.HumanController;
import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.ui.*;
import com.isolator.game.entity.Player;
import com.isolator.game.entity.Visitor;
import com.isolator.game.logic.Group;
import com.isolator.game.logic.InfectionStatus;
import com.isolator.game.map.GameMap;
import com.isolator.game.map.Pathfinder;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class IsolatorGameState extends GameState {

    private final GameMap map;
    private final Size cellSize;
    private UIContainer statsContainer;
    private Pathfinder pathfinder;

    public IsolatorGameState() {
        super();
        cellSize = new Size(64, 64);
        this.map = new GameMap(24, 12, cellSize);
        this.map.addWallsToPerimeter(this);
        this.scene = map;
        this.setupStatsContainer();
        pathfinder = new Pathfinder(getObjects(), 24, 12);
    }

    private void setupStatsContainer() {
        statsContainer = new UIContainer();
        uiContainers.add(statsContainer);
        updateScoreContainer();
    }

    private void updateScoreContainer() {
        statsContainer.clear();
        long healthy = gameObjects.stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isHealthy()).count();
        long infected = gameObjects.stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isInfected()).count();
        long sick = gameObjects.stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isSick()).count();

        UIContainer scoreContainer = new UIContainer();
        scoreContainer.setMargin(new UISpacing(5));
        scoreContainer.setPadding(new UISpacing(5, 5, 0, 5));
        scoreContainer.setDirection(ContainerDirection.HORIZONTAL);
        scoreContainer.setBackgroundColor(new Color(243, 243, 243));
        scoreContainer.addElement(createScoreText(healthy + "", new Color(0, 228, 12)));
        scoreContainer.addElement(createScoreText(infected + "", Color.YELLOW));
        scoreContainer.addElement(createScoreText(sick + "", Color.RED));

        statsContainer.addElement(scoreContainer);
    }

    private UIText createScoreText(String text, Color color) {
        UIText uiText = new UIText(text, color);
        uiText.setPadding(new UISpacing(0, 0, 15, 5));
        return uiText;
    }

    private void initGame() {
        Player player = new Player(new HumanController(input));
        player.setPosition(
                map.getRandomAvailableLocation(this, player.getSize())
        );
        addObject(player);

        for(int i = 0; i < 10; i++) {
            generateGroupOfVisitors();
        }

        initSickPeople(0);

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

    @Override
    public void update() {
        super.update();
        updateScoreContainer();
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
