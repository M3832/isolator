package com.isolator.ui;

import com.isolator.core.Position;
import com.isolator.core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIContainer extends UIBase {
    private Position position;
    private ContainerDirection containerDirection;
    private Alignment windowAlignment;

    private List<UIBase> elements;

    public UIContainer() {
        super();
        position = new Position(0, 0);
        padding = new UISpacing(0, 10);
        containerDirection = ContainerDirection.VERTICAL;
        windowAlignment = new Alignment(AlignmentPosition.START, AlignmentPosition.START);
        elements = new ArrayList<>();
    }

    public void addElement(UIBase uiBase) {
        elements.add(uiBase);
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
        if(elements.isEmpty())
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

        Size containerSize = getSize();
        BufferedImage image = new BufferedImage(containerSize.getWidth(), containerSize.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(new Color(0, 0, 0, 0.5f));
        graphics.fillRect(0, 0, containerSize.getWidth(), containerSize.getHeight());

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
}
