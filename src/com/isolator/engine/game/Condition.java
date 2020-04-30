package com.isolator.engine.game;

import com.isolator.engine.state.GameState;

public interface Condition {
    boolean condition(GameState state);
}
