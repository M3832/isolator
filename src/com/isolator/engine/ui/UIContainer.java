package com.isolator.engine.ui;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UIBase {
    protected Alignment windowAlignment;
    protected Color backgroundColor;
    protected boolean visible;

    protected final List<UIBase> elements;

    public UIContainer() {
        super();
        position = new Position(0, 0);
        padding = new UISpacing(0, 5);
        backgroundColor = new Color(217, 189, 142, 0);
        windowAlignment = new Alignment(AlignmentPosition.START, AlignmentPosition.START);
        elements = new ArrayList<>();
    }

    public void addElement(UIBase uiBase) {
        elements.add(uiBase);
        calculatePositions();
        this.size = calculateSize();
    }

    protected abstract Size calculateSize();

    protected abstract void calculatePositions();

    public boolean isVisible() {
        return visible;
    }

    public void toggleVisibility() {
        visible = !visible;
    }

    @Override
    public Image getUIElement() {
        if (elements.isEmpty())
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        Size containerSize = getSize();
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(containerSize, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, containerSize.getWidth(), containerSize.getHeight());

        for(UIBase element : elements) {
            graphics.drawImage(
                    element.getUIElement(),
                    element.getPosition().getX(),
                    element.getPosition().getY(),
                    null);
        }

        graphics.dispose();
        return image;
    }

    public Position getPosition() {
        return position;
    }

    public void clear() {
        elements.clear();
    }

    public void setWindowAlignment(Alignment windowAlignment) {
        this.windowAlignment = windowAlignment;
    }

    public Alignment getWindowAlignment() {
        return windowAlignment;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
