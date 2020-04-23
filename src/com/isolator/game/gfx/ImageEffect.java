package com.isolator.game.gfx;

import java.awt.*;

public abstract class ImageEffect {
    public enum RenderOrder {
        BEFORE, AFTER
    }

    public abstract RenderOrder getRenderOrder();
    public abstract Image getEffect(Image sprite);
}
