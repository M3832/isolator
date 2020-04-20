package com.isolator.engine.ui;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;

public class UIAlignmentUtils {

    public static Position calculateDrawPosition(UIContainer uiContainer, Size screenSize) {
        Alignment alignment = uiContainer.getWindowAlignment();
        AlignmentPosition alignmentHorizontal = alignment.getHorizontal();
        AlignmentPosition alignmentVertical = alignment.getVertical();

        int screenWidth = screenSize.getWidth();
        int x = 0;
        if(alignmentHorizontal == AlignmentPosition.CENTER)
            x = screenWidth / 2 - uiContainer.getSize().getWidth() / 2;
        if(alignmentHorizontal == AlignmentPosition.END)
            x = screenWidth - uiContainer.getSize().getWidth();

        int screenHeight = screenSize.getHeight();
        int y = 0;
        if(alignmentVertical == AlignmentPosition.CENTER)
            y = screenHeight / 2 - uiContainer.getSize().getHeight() / 2;
        if(alignmentVertical == AlignmentPosition.END)
            y = screenHeight - uiContainer.getSize().getHeight();

        return new Position(x, y);
    }
}