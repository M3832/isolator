package com.isolator.engine.ui;

public class UISpacing {
    private int top;
    private int bottom;
    private int right;
    private int left;

    public UISpacing(int padding) {
        this(padding, padding);
    }

    public UISpacing(int vertical, int horizontal) {
        this.top = vertical;
        this.bottom = vertical;
        this.right = horizontal;
        this.left = horizontal;
    }

    public UISpacing(int top, int bottom, int right, int left) {
        this.top = top;
        this.bottom = bottom;
        this.right = right;
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public int getHorizontal() {
        return left + right;
    }

    public int getVertical() {
        return top + bottom;
    }
}
