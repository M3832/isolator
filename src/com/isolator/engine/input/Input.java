package com.isolator.engine.input;

import com.isolator.engine.core.Position;
import javafx.scene.input.MouseButton;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Input extends MouseAdapter implements KeyListener, MouseListener, MouseMotionListener {

    public final Map<Integer, KeyPress> keys;
    public final Map<Integer, MouseButtonPress> mouseButtons;

    private Position mousePosition;

    public Input() {
        this.keys = new HashMap<>();
        this.mouseButtons = new HashMap<>();
        this.mousePosition = new Position();
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

    private void checkButtonInMap(int buttonCode) {
        if(mouseButtons.get(buttonCode) == null) {
            mouseButtons.put(buttonCode, new MouseButtonPress());
        }
    }

    public boolean isClicked(Integer keyCode) {
        checkKeyInMap(keyCode);

        return keys.get(keyCode).isTyped();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        checkButtonInMap(mouseEvent.getButton());
        MouseButtonPress button = mouseButtons.get(mouseEvent.getButton());
        if(!button.isClicked()) {
            button.click();
        } else {
            button.press();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseButtons.get(mouseEvent.getButton()).release();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mousePosition = new Position(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mousePosition = new Position(mouseEvent.getX(), mouseEvent.getY());
    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean mouseButtonClicked(int mouseButton) {
        checkButtonInMap(mouseButton);
        return mouseButtons.get(mouseButton).isClicked();
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

    public static class MouseButtonPress {
        private boolean clicked;
        private boolean pressed;

        public void click() {
            clicked = true;
        }

        public void press() {
            pressed = true;
        }

        public void release() {
            clicked = false;
            pressed = false;
        }

        public boolean isClicked() {
            if(clicked && !pressed) {
                pressed = true;
                return true;
            }
            return false;
        }
    }
}
