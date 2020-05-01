package com.isolator.engine.ui;

import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;

import java.awt.*;

public abstract class UIBase {
    protected Position position;
    protected Size size;
    protected UISpacing margin;
    protected UISpacing padding;

    public UIBase() {
        this.margin = new UISpacing(0);
        this.padding = new UISpacing(5);
        this.position = new Position();
        this.size = new Size();
    }

    public void update(State state) {

    }

    public abstract Image getUIElement();

    public UISpacing getMargin() {
        return margin;
    }

    public UISpacing getPadding() {
        return padding;
    }

    public void setPadding(UISpacing padding) {
        this.padding = padding;
    }

    public void setMargin(UISpacing margin) {
        this.margin = margin;
    }

    void setPosition(Position position) {
        this.position = position;
    }

    Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    void setSize(Size size) {
        this.size = size;
    }
}
