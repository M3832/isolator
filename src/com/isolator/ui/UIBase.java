package com.isolator.ui;

import com.isolator.core.Size;

import java.awt.*;

public abstract class UIBase {
    protected UISpacing margin;
    protected UISpacing padding;

    public UIBase() {
        this.margin = new UISpacing(0);
        this.padding = new UISpacing(10);
    }

    public abstract Size getSize();

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
}
