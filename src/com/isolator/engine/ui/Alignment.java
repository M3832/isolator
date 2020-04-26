package com.isolator.engine.ui;

public class Alignment {

    private final AlignmentPosition vertical;
    private final AlignmentPosition horizontal;

    public Alignment(AlignmentPosition horizontal, AlignmentPosition vertical) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public AlignmentPosition getVertical() {
        return vertical;
    }

    public AlignmentPosition getHorizontal() {
        return horizontal;
    }

    public static final Alignment BOTTOM_LEFT = new Alignment(AlignmentPosition.START, AlignmentPosition.END);
    public static final Alignment TOP_RIGHT = new Alignment(AlignmentPosition.END, AlignmentPosition.START);
    public static final Alignment TOP_CENTER = new Alignment(AlignmentPosition.CENTER, AlignmentPosition.START);
    public static final Alignment CENTER_CENTER = new Alignment(AlignmentPosition.CENTER, AlignmentPosition.CENTER);
    public static final Alignment BOTTOM_RIGHT = new Alignment(AlignmentPosition.END, AlignmentPosition.END);
}
