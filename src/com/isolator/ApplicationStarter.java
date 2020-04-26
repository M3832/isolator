package com.isolator;

import com.isolator.engine.game.Game;
import com.isolator.engine.core.Size;
import com.isolator.game.IsolatorGameState;

public class ApplicationStarter {
    public static void main(String[] args) {
        final Game game = new Game(new Size(800, 640), new IsolatorGameState());
        final Thread gameRunner = new Thread(game);

        gameRunner.start();
    }
}
