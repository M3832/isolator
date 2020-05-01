package com.isolator.engine.ui;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.gfx.ImageUtils;
import com.isolator.engine.state.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIScreen {

    private Size size;
    private List<UIContainer> containers;

    public UIScreen(Size size) {
        this.size = size;
        containers = new ArrayList<>();
    }

    public void update(State state) {
        containers.forEach(c -> c.update(state));
    }

    public Size getSize() {
        return size;
    }

    public void addContainers(List<UIContainer> uiContainers) {
        containers.addAll(uiContainers);
    }

    public void addContainer(UIContainer uiContainers) {
        containers.add(uiContainers);
    }

    public Image getUiImage() {
        Image uiImage = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D screenGraphics = (Graphics2D) uiImage.getGraphics();

        containers.forEach(container -> {
            Position drawPosition = UIAlignmentUtils.calculateDrawPosition(container, size);
            screenGraphics.drawImage(
                    container.getUIElement(),
                    drawPosition.getX(),
                    drawPosition.getY(),
                    null
            );
        });

        screenGraphics.dispose();
        return uiImage;
    }
}
