package com.stopcoronagame;

import com.stopcoronagame.display.Display;

public class Game implements Runnable {
    private Display display;
    public long startTime;
    public boolean running;

    public Game(int width, int height) {
        display = new Display(width, height);
        startTime = System.nanoTime();
    }

    @Override
    public void run() {
        running = true;

        while(running) {
            while( GetTickCount() > next_game_tick ) {
                update();
                next_game_tick += SKIP_TICKS;
            }
            interpolation = float( GetTickCount() + SKIP_TICKS - next_game_tick )
                    / float( SKIP_TICKS );
            render( interpolation );
        }
    }

    private void render() {
        display.render();
    }

    private void update() {

    }
}
