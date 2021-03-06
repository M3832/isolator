package com.isolator.engine.ui.containers;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.ui.components.UIBase;

public class HorizontalContainer extends UIContainer {
    public HorizontalContainer(Size parentSize) {
        super(parentSize);
    }

    @Override
    protected Size calculateSize() {
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
    protected void calculatePositions() {
        int currentX = padding.getLeft();

        for(UIBase element : elements) {
            currentX += element.getMargin().getLeft();
            element.setPosition(new Position(currentX, padding.getTop()));
            currentX += element.getSize().getWidth();
            currentX += element.getMargin().getBottom();
        }
    }
}
