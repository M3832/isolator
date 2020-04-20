package com.isolator.engine;

public class GameTimer {

    private int updatesPerSecond;
    private long updatesSinceStart;

    public GameTimer(int updatesPerSecond) {
        this.updatesPerSecond = updatesPerSecond;
        this.updatesSinceStart = 0;
    }

    public int getGameTimeFromNow(double seconds) {
        return (int) (updatesPerSecond * seconds + updatesSinceStart);
    }

    public void update() {
        updatesSinceStart += 1;
    }

    public long getCurrentTime() {
        return updatesSinceStart;
    }
}
