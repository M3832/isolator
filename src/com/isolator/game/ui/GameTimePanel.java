package com.isolator.game.ui;

import com.isolator.engine.GameState;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UISpacing;
import com.isolator.engine.ui.UIText;

public class GameTimePanel extends UIContainer {

    public GameTimePanel() {
        setWindowAlignment(Alignment.TOP_CENTER);
        setPadding(new UISpacing(10));
    }

    @Override
    public void update(GameState state) {
        clear();
        addElement(new UIText(state.getGameTimer().toString()));
    }
}
