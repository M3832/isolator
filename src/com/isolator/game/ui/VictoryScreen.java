package com.isolator.game.ui;

import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;

public class VictoryScreen extends HorizontalContainer {
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
