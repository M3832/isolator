package com.isolator;

import com.isolator.engine.game.GameRunner;
import com.isolator.engine.core.Size;
import com.isolator.game.Isolator;

public class ApplicationStarter {
    public static void main(String[] args) {
        final GameRunner game = new GameRunner(new Isolator(new Size(800, 640)));
        final Thread gameRunner = new Thread(game);

        gameRunner.start();
    }
}
