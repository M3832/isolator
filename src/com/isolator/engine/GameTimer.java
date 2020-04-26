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


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        double minutes = updatesSinceStart / updatesPerSecond / 60;
        double seconds = updatesSinceStart / updatesPerSecond % 60;
        if(minutes < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append((int) minutes);
        stringBuilder.append(":");
        if(seconds < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append((int) seconds);

        return stringBuilder.toString();
    }
}
