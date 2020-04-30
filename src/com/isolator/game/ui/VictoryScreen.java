package com.isolator.game.ui;

import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UISpacing;
import com.isolator.engine.ui.UIText;

public class VictoryScreen extends UIContainer {
    public VictoryScreen() {
        setWindowAlignment(Alignment.CENTER_CENTER);
        setPadding(new UISpacing(10));
        toggleVisibility();
    }

    @Override
    public void update(State state) {
        clear();
        addElement(new UIText("VICTORY", 64));
    }
}
