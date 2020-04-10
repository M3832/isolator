package com.stopcoronagame;

import com.stopcoronagame.display.Display;

public class Handler {

    private static Handler handler;
    private Display display;

    public static Handler getHandler() {
        if(handler == null) {
            handler = new Handler();
        }

        return handler;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}
