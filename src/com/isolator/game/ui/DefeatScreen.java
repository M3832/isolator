package com.isolator.game.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.engine.ui.containers.HorizontalContainer;
import com.isolator.engine.ui.components.UIText;

public class DefeatScreen extends HorizontalContainer {
    public DefeatScreen(Size parentSize) {
        super(parentSize);
        setWindowAlignment(Alignment.CENTER_CENTER);
        setPadding(new Spacing(10));
        toggleVisibility();
    }

    @Override
    public void update(State state) {
        clear();
        addElement(new UIText("DEFEAT", 64));
    }
}
