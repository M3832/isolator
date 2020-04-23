package com.isolator.game.gfx;

import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Outline extends ImageEffect {
    private Color color;

    public Outline(Color color) {
        this.color = color;
    }
    @Override
    public RenderOrder getRenderOrder() {
        return RenderOrder.BEFORE;
    }

    @Override
    public Image getEffect(Image sprite) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(
                new Size(sprite.getWidth(null), sprite.getHeight(null)),
                ImageUtils.ALPHA_BIT_MASKED);

        Image scaledSprite = ImageUtils.scale(sprite, 1.3f, 1.1f);

        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(
                scaledSprite,
                -((scaledSprite.getWidth(null) - sprite.getWidth(null))/2),
                -((scaledSprite.getHeight(null) - sprite.getHeight(null))/2),
                null);
        graphics.setComposite(AlphaComposite.SrcAtop);
        graphics.setColor(color);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();

        return image;
    }
}
