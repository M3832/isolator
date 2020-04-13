package com.isolator.ui;

public class Alignment {

    private AlignmentPosition vertical;
    private AlignmentPosition horizontal;

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

    public static Alignment TOP_RIGHT = new Alignment(AlignmentPosition.END, AlignmentPosition.START);
}
