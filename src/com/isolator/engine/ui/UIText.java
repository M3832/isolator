package com.isolator.engine.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIBase {

    private final int fontSize;
    private final String text;
    private final int fontStyle;
    private Color color;

    public UIText(String text) {
        this(text, 20);
    }

    public UIText(String text, Color color) {
        this(text, 20);
        this.color = color;
    }

    public UIText(String text, int fontSize) {
        this(text, fontSize, Font.PLAIN);
    }

    public UIText(String text, int fontSize, int fontStyle) {
        super();
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.color = Color.WHITE;
    }

    @Override
    public Size getSize() {
        return new Size(text.length() * fontSize / 2 + padding.getHorizontal(), fontSize + padding.getVertical());
    }

    @Override
    public Image getUIElement() {
        BufferedImage bufferedImage = (BufferedImage) ImageUtils.createCompatibleImage(getSize(), ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setFont(new Font("Helvetica", fontStyle, fontSize));

        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        graphics.setColor(color);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop() - 4);

        return bufferedImage;
    }
}
