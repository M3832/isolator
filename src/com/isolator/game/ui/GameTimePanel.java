package com.isolator.game.ui;

import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.game.states.IsolatorGameState;

public class GameTimePanel extends VerticalContainer {

    public GameTimePanel() {
        setWindowAlignment(Alignment.TOP_CENTER);
        setPadding(new UISpacing(10));
    }

    @Override
    public void update(State state) {
        IsolatorGameState isolatorState = (IsolatorGameState) state;
        clear();
        addElement(new UIText(isolatorState.getGameTimer().toString()));
    }
}
