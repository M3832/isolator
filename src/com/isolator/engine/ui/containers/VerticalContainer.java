package com.isolator.engine.ui.containers;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.ui.components.UIBase;

public class VerticalContainer extends UIContainer {
    public VerticalContainer(Size parentSize) {
        super(parentSize);
    }

    @Override
    public Size calculateSize() {
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

    @Override
    protected void calculatePositions() {
        int currentY = padding.getTop();

        for(UIBase element : elements) {
            currentY += element.getMargin().getTop();
            element.setPosition(new Position(padding.getLeft(), currentY));
            currentY += element.getSize().getHeight();
            currentY += element.getMargin().getBottom();
        }
    }
}
