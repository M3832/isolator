package com.isolator.engine.ui;

public class Alignment {

    public enum Position {
        START, CENTER, END
    }

    private final Position vertical;
    private final Position horizontal;

    public Alignment(Position horizontal, Position vertical) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public Position getVertical() {
        return vertical;
    }

    public Position getHorizontal() {
        return horizontal;
    }

    public static final Alignment BOTTOM_LEFT = new Alignment(Position.START, Position.END);
    public static final Alignment TOP_RIGHT = new Alignment(Position.END, Position.START);
    public static final Alignment TOP_CENTER = new Alignment(Position.CENTER, Position.START);
    public static final Alignment CENTER_CENTER = new Alignment(Position.CENTER, Position.CENTER);
    public static final Alignment BOTTOM_RIGHT = new Alignment(Position.END, Position.END);
}
