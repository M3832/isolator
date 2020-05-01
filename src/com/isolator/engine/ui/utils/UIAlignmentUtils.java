package com.isolator.engine.ui.utils;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.ui.Alignment;
import com.isolator.engine.ui.containers.UIContainer;

public class UIAlignmentUtils {

    public static Position calculatePosition(UIContainer uiContainer, Size containerSize) {
        Alignment alignment = uiContainer.getWindowAlignment();
        Alignment.Position alignmentHorizontal = alignment.getHorizontal();
        Alignment.Position alignmentVertical = alignment.getVertical();

        int screenWidth = containerSize.getWidth();
        int x = 0 + uiContainer.getMargin().getLeft();
        if(alignmentHorizontal == Alignment.Position.CENTER)
            x = screenWidth / 2 - uiContainer.getSize().getWidth() / 2;
        if(alignmentHorizontal == Alignment.Position.END)
            x = screenWidth - uiContainer.getSize().getWidth() - uiContainer.getMargin().getRight();

        int screenHeight = containerSize.getHeight();
        int y = 0 + uiContainer.getMargin().getTop();
        if(alignmentVertical == Alignment.Position.CENTER)
            y = screenHeight / 2 - uiContainer.getSize().getHeight() / 2;
        if(alignmentVertical == Alignment.Position.END)
            y = screenHeight - uiContainer.getSize().getHeight() - uiContainer.getMargin().getBottom();

        return new Position(x, y);
    }
}
