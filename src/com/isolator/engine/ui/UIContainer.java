package com.isolator.engine.ui;

import com.isolator.engine.game.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIContainer extends UIBase {
    private final Position position;
    private ContainerDirection containerDirection;
    private Alignment windowAlignment;
    private Color backgroundColor;
    private boolean visible;

    private final List<UIBase> elements;

    public UIContainer() {
        this(true);
    }

    public UIContainer(boolean visible) {
        super();
        position = new Position(0, 0);
        padding = new UISpacing(0, 5);
        containerDirection = ContainerDirection.VERTICAL;
        backgroundColor = new Color(217, 189, 142, 0);
        windowAlignment = new Alignment(AlignmentPosition.START, AlignmentPosition.START);
        elements = new ArrayList<>();
        this.visible = visible;
    }

    public void addElement(UIBase uiBase) {
        elements.add(uiBase);
    }

    public void update(GameState state) {

    }

    @Override
    public Size getSize() {
        if(containerDirection.equals(ContainerDirection.HORIZONTAL)) {
            return getSizeHorizontal();
        }

        int width = padding.getHorizontal();
        int height = padding.getVertical();
        int widestWidth = 0;

        for(UIBase element : elements) {
            height += element.getSize().getHeight() + element.getMargin().getVertical();

            if(element.getSize().getWidth() > widestWidth) {
                widestWidth = element.getSize().getWidth();
            }
        }

        width += widestWidth;

        return new Size(width, height);
    }

    public Size getSizeHorizontal() {
        int width = padding.getHorizontal();
        int height = padding.getVertical();

        int tallestHeight = 0;

        for(UIBase element : elements) {
            width += element.getSize().getWidth() + element.getMargin().getHorizontal();

            if(element.getSize().getHeight() > tallestHeight) {
                tallestHeight = element.getSize().getHeight();
            }
        }

        height += tallestHeight;

        return new Size(width, height);
    }

    @Override
    public Image getUIElement() {
        if(elements.isEmpty() || !visible)
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        if(containerDirection.equals(ContainerDirection.HORIZONTAL)) {
            return getUIElementHorizontal();
        }

        Size containerSize = getSize();
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(containerSize, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, containerSize.getWidth(), containerSize.getHeight(), 20, 20);

        int currentY = padding.getTop();
        for(UIBase element : elements) {
            currentY += element.getMargin().getTop();

            graphics.drawImage(
                    element.getUIElement(),
                    padding.getLeft(),
                    currentY,
                    null);

            currentY += element.getSize().getHeight();
            currentY += element.getMargin().getBottom();
        }

        graphics.dispose();
        return image;
    }

    private Image getUIElementHorizontal() {
        Size containerSize = getSizeHorizontal();
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(containerSize, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, containerSize.getWidth(), containerSize.getHeight());

        int currentX = padding.getLeft();
        for(UIBase element : elements) {
            currentX += element.getMargin().getLeft();

            graphics.drawImage(
                    element.getUIElement(),
                    currentX,
                    padding.getTop(),
                    null);

            currentX += element.getSize().getWidth();
            currentX += element.getMargin().getBottom();
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

    public void toggleVisibility() {
        visible = !visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setDirection(ContainerDirection containerDirection) {
        this.containerDirection = containerDirection;
    }
}
