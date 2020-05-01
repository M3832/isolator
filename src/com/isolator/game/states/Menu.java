package com.isolator.game.states;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;

import java.awt.*;

public class Menu extends State {


    public Menu(Size windowSize) {
        super(windowSize);
        UIContainer menuContainer = new VerticalContainer();
        menuContainer.setBackgroundColor(new Color(158, 158, 158));
        menuContainer.setWindowAlignment(Alignment.CENTER_CENTER);
        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setBackgroundColor(new Color(92, 92, 92));
        buttonContainer.setMargin(new UISpacing(5, 0));
        buttonContainer.addElement(new UIText("ISOLATOR", 48, Font.BOLD));
        buttonContainer.addElement(new UIText("NEW GAME"));
        buttonContainer.addElement(new UIText("EXIT"));
        UIText text = new UIText("Markus Gustafsson, 2020 ", 12);
        text.setMargin(new UISpacing(50, 0, 0, 0));
        buttonContainer.addElement(text);
        menuContainer.addElement(buttonContainer);
        uiScreen.addContainer(menuContainer);
    }

    public void update() {
        super.update();
    }

}
