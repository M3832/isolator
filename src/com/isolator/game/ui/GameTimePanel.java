package com.isolator.game.ui;

import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UISpacing;
import com.isolator.engine.ui.UIText;
import com.isolator.game.states.IsolatorGameState;

public class GameTimePanel extends UIContainer {

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
