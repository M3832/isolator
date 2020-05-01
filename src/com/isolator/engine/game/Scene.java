package com.isolator.engine.game;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;

import java.awt.*;

public abstract class Scene {
    public abstract Size getSceneSize();
    public abstract void update(State state);
    public abstract Image getSceneGraphics(State state);
}
