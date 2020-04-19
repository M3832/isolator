package com.isolator.engine;

import com.isolator.engine.controller.Input;
import com.isolator.engine.core.Size;
import com.isolator.engine.display.Camera;
import com.isolator.engine.display.Display;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UIText;

public class Game implements Runnable {
    private GameState state;
    private Display display;
    private Input input;

    private UIContainer fpsContainer;

    private boolean running;

    private final float UPDATE_RATE = 1.0f/60.0f;

    private long statisticsTimer = System.currentTimeMillis();
    private int updates = 0, renders = 0;

    public Game(Size windowSize, GameState state) {
        this.state = state;
        this.input = new Input();
        this.display = new Display(windowSize, input);
        this.fpsContainer = new UIContainer(false);
        state.setupGame(new Camera(windowSize), input);
        state.addUIContainer(fpsContainer);
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
