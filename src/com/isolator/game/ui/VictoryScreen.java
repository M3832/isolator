package com.isolator.game.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.engine.ui.containers.HorizontalContainer;
import com.isolator.engine.ui.components.UIText;

public class VictoryScreen extends HorizontalContainer {
    public VictoryScreen(Size parentSize) {
        super(parentSize);
        setWindowAlignment(Alignment.CENTER_CENTER);
        setPadding(new Spacing(10));
        toggleVisibility();
    }

    @Override
    public void update(State state) {
        clear();
        addElement(new UIText("VICTORY", 64));
    }
}
