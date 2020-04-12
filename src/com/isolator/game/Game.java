package com.isolator.game;

import com.isolator.controller.HumanController;
import com.isolator.controller.Input;
import com.isolator.core.Position;
import com.isolator.entity.Player;
import com.isolator.display.Display;

public class Game implements Runnable {
    private Display display;
    private GameState state;
    private Input input;

    private float speed;
    private boolean running;

    private final float UPDATE_RATE = 1.0f/60.0f;

    private long statisticsTimer = System.currentTimeMillis();
    private int updates = 0, renders = 0;

    public Game(int width, int height) {
        input = new Input();
        state = new GameState();
        display = new Display(width, height, input);
        speed = 1f;

        initializeGame();
    }

    private void initializeGame() {
        state.addEntityAtPosition(new Player(new HumanController(input)), new Position(100, 100));
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0.0;
        long currentTime, lastUpdate = System.currentTimeMillis();

        while(running) {
            currentTime = System.currentTimeMillis();
            float lastRenderDurationInSeconds = (float)(currentTime - lastUpdate) / 1000;
            accumulator += lastRenderDurationInSeconds * speed;
            lastUpdate = currentTime;

            while(accumulator >= UPDATE_RATE) {
                update();
                accumulator -= UPDATE_RATE;
            }
            render();
            printStatistics();
        }
    }

    private void printStatistics() {
        long currentTime = System.currentTimeMillis();
        if(statisticsTimer + 1000 <= currentTime) {
            System.out.println(String.format("UPS: %d, FPS: %d", updates, renders));
            statisticsTimer = currentTime;
            renders = 0;
            updates = 0;
        }
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
