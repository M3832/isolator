package com.isolator.game.states;

import com.isolator.engine.core.Size;
import com.isolator.engine.input.Input;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.engine.ui.components.UIButton;
import com.isolator.engine.ui.containers.UIContainer;
import com.isolator.engine.ui.components.UIText;
import com.isolator.engine.ui.containers.VerticalContainer;

import java.awt.*;

public class Menu extends State {

    public Menu(Size windowSize, Input input) {
        super(windowSize, input);
        UIContainer menuContainer = new VerticalContainer(uiScreen.getSize());
        menuContainer.setBackgroundColor(new Color(158, 158, 158));
        menuContainer.setWindowAlignment(Alignment.CENTER_CENTER);
        UIContainer buttonContainer = new VerticalContainer(uiScreen.getSize());
        buttonContainer.setBackgroundColor(new Color(92, 92, 92));
        buttonContainer.setMargin(new Spacing(5, 0));
        buttonContainer.addElement(new UIText("ISOLATOR", 48, Font.BOLD));
        buttonContainer.addElement(new UIButton("NEW GAME", state -> state.setNextState(createGameState(windowSize, input))));
        buttonContainer.addElement(new UIButton("EXIT", state -> System.exit(0)));
        UIText text = new UIText("Markus Gustafsson 2020 ", 12);
        text.setMargin(new Spacing(50, 0, 0, 0));
        buttonContainer.addElement(text);
        menuContainer.addElement(buttonContainer);
        uiScreen.addContainer(menuContainer);
    }

    private State createGameState(Size windowSize, Input input) {
        IsolatorGameState state = new IsolatorGameState(windowSize, input);
        return state;
    }

    public void update() {
        super.update();
    }

}
