package com.isolator.ui;

import com.isolator.core.Position;

import java.awt.*;

public class UIAlignmentUtils {

    public static Position calculateDrawPosition(UIContainer uiContainer, Graphics2D graphics) {
        Alignment alignment = uiContainer.getWindowAlignment();
        AlignmentPosition alignmentHorizontal = alignment.getHorizontal();
        AlignmentPosition alignmentVertical = alignment.getVertical();

        int screenWidth = (int) graphics.getDeviceConfiguration().getBounds().getWidth();
        int x = 0;
        if(alignmentHorizontal == AlignmentPosition.START)
            x = 0;
        if(alignmentHorizontal == AlignmentPosition.CENTER)
            x = screenWidth / 2 - uiContainer.getSize().getWidth() / 2;
        if(alignmentHorizontal == AlignmentPosition.END)
            x = screenWidth - uiContainer.getSize().getWidth();

        int screenHeight = (int) graphics.getDeviceConfiguration().getBounds().getHeight();
        int y = 0;
        if(alignmentVertical == AlignmentPosition.START)
            y = 0;
        if(alignmentVertical == AlignmentPosition.CENTER)
            y = screenHeight / 2 - uiContainer.getSize().getHeight() / 2;
        if(alignmentVertical == AlignmentPosition.END)
            y = screenHeight - uiContainer.getSize().getHeight();

        return new Position(x, y);
    }
}