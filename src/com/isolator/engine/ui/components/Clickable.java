package com.isolator.engine.ui.components;

import com.isolator.engine.core.Position;
import com.isolator.engine.state.State;

public interface Clickable {
    void handleFocus(Position mousePosition);
    boolean hasFocus();
    void onClick(State state);
}
