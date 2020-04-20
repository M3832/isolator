package com.isolator.engine.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIBase {

    private final int fontSize;
    private final String text;

    public UIText(String text) {
        this(text, 20);
    }

    public UIText(String text, int fontSize) {
        super();
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public Size getSize() {
        return new Size(text.length() * fontSize / 2 + padding.getHorizontal(), fontSize + padding.getVertical());
    }

    @Override
    public Image getUIElement() {
        BufferedImage bufferedImage = (BufferedImage) ImageUtils.createCompatibleImage(getSize(), ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop() - 4);

        return bufferedImage;
    }
}