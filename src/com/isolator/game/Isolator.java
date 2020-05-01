package com.isolator.game;

import com.isolator.engine.core.Size;
import com.isolator.engine.game.Game;
import com.isolator.game.states.IsolatorGameState;
import com.isolator.game.states.Menu;

public class Isolator extends Game {

    public Isolator(Size windowSize) {
        super(windowSize);
        addState(new Menu(windowSize, input));
    }

    @Override
    public void render() {
        display.render(getCurrentState());
    }
}
