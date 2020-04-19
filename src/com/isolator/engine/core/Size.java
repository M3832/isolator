package com.isolator.engine.core;

public class Size {

    private final int width;
    private final int height;

    public Size() {
        this(0, 0);
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return String.format("Size(w: %d, h: %d)", width, height);
    }
}
