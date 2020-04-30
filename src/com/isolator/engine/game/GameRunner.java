package com.isolator.engine.game;

public class GameRunner implements Runnable {

    private final Game game;
    private boolean running;
    private final float UPDATE_RATE = 1.0f/60.0f;

    public GameRunner(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0.0;
        long currentTime, lastUpdate = System.currentTimeMillis();

        while(running) {
            currentTime = System.currentTimeMillis();
            float lastRenderDurationInSeconds = (float)(currentTime - lastUpdate) / 1000;
            accumulator += lastRenderDurationInSeconds * game.getGameSpeed();
            lastUpdate = currentTime;

            while(accumulator >= UPDATE_RATE) {
                game.update();
                accumulator -= UPDATE_RATE;
            }

            game.render();
        }
    }
}
