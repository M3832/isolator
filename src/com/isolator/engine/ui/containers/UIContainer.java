package com.isolator.engine.ui.containers;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.Spacing;
import com.isolator.engine.ui.components.Clickable;
import com.isolator.engine.ui.components.UIBase;
import com.isolator.engine.ui.utils.UIAlignmentUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UIBase implements Clickable {
    protected Size parentSize;
    protected Alignment windowAlignment;
    protected Color backgroundColor;
    protected boolean visible;

    protected final List<UIBase> elements;

    public UIContainer(Size parentSize) {
        super();
        this.parentSize = parentSize;
        padding = new Spacing(0, 5);
        backgroundColor = new Color(217, 189, 142, 0);
        windowAlignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        elements = new ArrayList<>();
        visible = true;
        calculateContainerPosition();
    }

    private void calculateContainerPosition() {
        this.position = UIAlignmentUtils.calculatePosition(this, parentSize);
    }

    public void addElement(UIBase uiBase) {
        elements.add(uiBase);
        this.size = calculateSize();
        calculateContainerPosition();
        calculatePositions();
    }

    public void update(State state) {
        handleHover(state);
        handleClick(state);
    }

    protected void handleClick(State state) {
        if(state.mouseButtonClicked()) {
            onClick(state);
        }
    }

    public void onClick(State state) {
        elements.stream()
                .filter(e -> (e instanceof Clickable))
                .map(e -> (Clickable) e)
                .filter(c -> c.hasFocus())
                .forEach(c -> c.onClick(state));
    }

    public void handleHovering(Position mousePosition) {
        Position positionInContext = new Position(mousePosition.getX(), mousePosition.getY());
        positionInContext.subtract(position);
        elements.stream()
                .filter(e -> e instanceof Clickable)
                .map(e -> (Clickable) e)
                .forEach(element -> element.handleFocus(positionInContext));
    }

    public void handleFocus(Position position) {
        handleHovering(position);
    }

    public boolean hasFocus() {
        return true;
    }

    public void handleHover(State state) {
        Position mousePosition = new Position(state.getMousePosition().getX(), state.getMousePosition().getY());
        handleHovering(mousePosition);
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
        if (elements.isEmpty() || !visible)
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
        calculateContainerPosition();
        calculatePositions();
    }

    public Alignment getWindowAlignment() {
        return windowAlignment;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
