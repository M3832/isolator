package com.isolator;

import com.isolator.game.Game;

public class ApplicationStarter {
    public static void main(String[] args) {
        final Game game = new Game(800, 640);
        final Thread gameRunner = new Thread(game);

        gameRunner.run();
    }
}
