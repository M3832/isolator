package com.isolator.engine.ui.components;

import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;

import java.awt.*;

public class UIButton extends UIText implements Clickable {

    private Color focusColor;
    private Color originalColor;
    private boolean hasFocus;
    private ClickAction action;

    public UIButton(String text, ClickAction action) {
        super(text);
        focusColor = new Color(132, 220, 236);
        originalColor = new Color(244, 244, 244);
        this.action = action;
    }

    @Override
    public void handleFocus(Position mousePosition) {
        if(CollisionBox.of(position, getSize()).getBox().contains(mousePosition.getX(), mousePosition.getY()) && !hasFocus) {
            color = focusColor;
            hasFocus = true;
        } else if (!CollisionBox.of(position, getSize()).getBox().contains(mousePosition.getX(), mousePosition.getY()) && hasFocus) {
            color = originalColor;
            hasFocus = false;
        }
    }

    @Override
    public boolean hasFocus() {
        return hasFocus;
    }

    @Override
    public void onClick(State state) {
        action.execute(state);
    }
}
