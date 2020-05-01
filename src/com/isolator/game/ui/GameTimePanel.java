package com.isolator.game.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.engine.ui.components.UIText;
import com.isolator.engine.ui.containers.VerticalContainer;
import com.isolator.game.states.IsolatorGameState;

public class GameTimePanel extends VerticalContainer {

    public GameTimePanel(Size parentSize) {
        super(parentSize);
        setWindowAlignment(Alignment.TOP_CENTER);
        setPadding(new Spacing(10));
    }

    @Override
    public void update(State state) {
        IsolatorGameState isolatorState = (IsolatorGameState) state;
        clear();
        addElement(new UIText(isolatorState.getGameTimer().toString()));
    }
}
