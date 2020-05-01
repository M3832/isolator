package com.isolator.engine.ui;

public class Spacing {
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;

    public Spacing(int padding) {
        this(padding, padding);
    }

    public Spacing(int vertical, int horizontal) {
        this.top = vertical;
        this.bottom = vertical;
        this.right = horizontal;
        this.left = horizontal;
    }

    public Spacing(int top, int bottom, int right, int left) {
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
