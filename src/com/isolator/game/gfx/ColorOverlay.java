package com.isolator.game.gfx;

import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorOverlay extends ImageEffect {

    private Color color;

    public ColorOverlay(Color color) {
        super();
        this.color = color;
    }
    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.AFTER;
    }

    @Override
    public Image getEffect(Image sprite) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(
                new Size(sprite.getWidth(null), sprite.getHeight(null)),
                ImageUtils.ALPHA_BIT_MASKED);

        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(sprite, 0, 0, null);
        graphics.setComposite(AlphaComposite.SrcAtop);
        graphics.setColor(color);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        return image;
    }
}
