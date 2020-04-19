package com.isolator.engine;

import com.isolator.engine.core.Size;

import java.awt.*;

public abstract class GameScene {
    public abstract Size getSceneSize();
    public abstract Image getSceneGraphics(GameState state);
}
