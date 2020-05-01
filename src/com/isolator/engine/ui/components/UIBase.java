package com.isolator.engine.ui.components;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.ui.Spacing;

import java.awt.*;

public abstract class UIBase {
    protected Position position;
    protected Size size;
    protected Spacing margin;
    protected Spacing padding;

    public UIBase() {
        this.margin = new Spacing(0);
        this.padding = new Spacing(5);
        this.position = new Position();
        this.size = new Size();
    }

    public abstract Image getUIElement();

    public Spacing getMargin() {
        return margin;
    }

    public Spacing getPadding() {
        return padding;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }
}
