package com.isolator.game.ui;

import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.game.states.IsolatorGameState;

public class DefeatScreen extends HorizontalContainer {
    public DefeatScreen() {
        setWindowAlignment(Alignment.CENTER_CENTER);
        setPadding(new UISpacing(10));
        toggleVisibility();
    }

    @Override
    public void update(State state) {
        clear();
        addElement(new UIText("DEFEAT", 64));
    }
}
