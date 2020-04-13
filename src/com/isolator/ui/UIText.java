package com.isolator.ui;

import com.isolator.core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIText extends UIBase {

    private int fontSize;
    private String text;

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
        return new Size(text.length() * fontSize + padding.getHorizontal(), fontSize + padding.getVertical());
    }

    @Override
    public Image getUIElement() {
        BufferedImage bufferedImage = new BufferedImage(
                getSize().getWidth(),
                getSize().getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0,
                getSize().getWidth() - 1,
                getSize().getHeight() - 1);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop() - 4);

        return bufferedImage;
    }
}
