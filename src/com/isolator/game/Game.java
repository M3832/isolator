package com.isolator.game;

import com.isolator.controller.AIController;
import com.isolator.controller.HumanController;
import com.isolator.controller.Input;
import com.isolator.core.Position;
import com.isolator.core.Size;
import com.isolator.display.Camera;
import com.isolator.entity.Player;
import com.isolator.display.Display;
import com.isolator.entity.Visitor;
import com.isolator.ui.Alignment;
import com.isolator.ui.UIContainer;
import com.isolator.ui.UIText;

public class Game implements Runnable {
    private Display display;
    private GameState state;
    private Camera camera;
    private Input input;

    private UIContainer fpsContainer;

    private boolean running;

    private final float UPDATE_RATE = 1.0f/60.0f;

    private long statisticsTimer = System.currentTimeMillis();
    private int updates = 0, renders = 0;

    public Game(int width, int height) {
        input = new Input();
        camera = new Camera(new Position(0, 0), new Size(width, height));
        display = new Display(width, height, input);
        state = new GameState(camera);
        fpsContainer = new UIContainer(false);

        initializeGame();
    }

    private void initializeGame() {
        addEntities();
        state.addUIContainer(fpsContainer);
        state.toggleDebugMode();
    }

    private void addEntities() {
        Player player = new Player(new HumanController(input));
        state.addEntityAtPosition(player, new Position(100, 100));

        for(int i = 0; i < 100; i++) {
            Visitor visitor = new Visitor(new AIController());
            state.addEntityAtPosition(visitor, state.getMap().randomLocation());
        }

        camera.followEntity(player);
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0.0;
        long currentTime, lastUpdate = System.currentTimeMillis();

        while(running) {
            currentTime = System.currentTimeMillis();
            float lastRenderDurationInSeconds = (float)(currentTime - lastUpdate) / 1000;
            accumulator += lastRenderDurationInSeconds * state.getGameSpeed();
            lastUpdate = currentTime;

            while(accumulator >= UPDATE_RATE) {
                update();
                accumulator -= UPDATE_RATE;
            }
            render();

            if(state.getRunMode() == RunMode.DEBUG) {
                printStatistics();
            }
        }
    }

    private void printStatistics() {
        long currentTime = System.currentTimeMillis();
        if(statisticsTimer + 1000 <= currentTime) {
            drawStatisticsUI();
            statisticsTimer = currentTime;
            renders = 0;
            updates = 0;
        }
    }

    private void drawStatisticsUI() {
        if(!fpsContainer.isVisible())
            fpsContainer.toggleVisibility();

        fpsContainer.clear();
        fpsContainer.addElement(new UIText(String.format("UPS: %d", updates)));
        fpsContainer.addElement(new UIText(String.format("FPS: %d", renders)));
        fpsContainer.setWindowAlignment(Alignment.BOTTOM_RIGHT);
    }

    private void render() {
        display.render(state);
        renders++;
    }

    private void update() {
        state.update();
        updates++;
    }
}
