package com.isolator.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class Input implements KeyListener {

    public Map<Integer, KeyPress> keys;

    public Input() {
        this.keys = new HashMap<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        checkKeyInMap(e.getKeyCode());
        KeyPress key = keys.get(e.getKeyCode());
        if(!key.isTyped()) {
            key.click();
        } else {
            key.press();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.get(e.getKeyCode()).release();
    }

    public boolean isPressed(Integer keyCode) {
        checkKeyInMap(keyCode);

        return keys.get(keyCode).isPressed();
    }

    private void checkKeyInMap(Integer keyCode) {
        if(keys.get(keyCode) == null) {
            keys.put(keyCode, new KeyPress());
        }
    }

    public boolean isClicked(Integer keyCode) {
        checkKeyInMap(keyCode);

        return keys.get(keyCode).isTyped();
    }

    public static class KeyPress {
        private boolean typed;
        private boolean pressed;

        public void click() {
            typed = true;
        }

        public void press() {
            pressed = true;
        }

        public void release() {
            typed = false;
            pressed = false;
        }

        public boolean isPressed() {
            return pressed || typed;
        }
        public boolean isTyped() {
            if(typed && !pressed) {
                pressed = true;
                return true;
            }
            return false;
        }
    }
}
