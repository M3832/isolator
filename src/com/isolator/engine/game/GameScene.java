package com.isolator.engine.game;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.GameState;

import java.awt.*;

public abstract class GameScene {
    public abstract Size getSceneSize();
    public abstract Image getSceneGraphics(GameState state);
}
