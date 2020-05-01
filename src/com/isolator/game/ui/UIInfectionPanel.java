package com.isolator.game.ui;

import com.isolator.engine.state.GameState;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.game.entity.Visitor;
import com.isolator.game.states.IsolatorGameState;

import java.awt.*;

public class UIInfectionPanel extends VerticalContainer {

    @Override
    public void update(State state) {
        IsolatorGameState isolatorState = (IsolatorGameState) state;
        clear();
        long healthy = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isHealthy()).count();
        long infected = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isInfected()).count();
        long sick = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isSick()).count();

        UIContainer scoreContainer = new HorizontalContainer();
        scoreContainer.setMargin(new UISpacing(5));
        scoreContainer.setPadding(new UISpacing(5,  0));
        scoreContainer.setBackgroundColor(new Color(243, 243, 243));
        scoreContainer.addElement(createScoreText(healthy + "", new Color(0, 228, 12)));
        scoreContainer.addElement(createScoreText(infected + "", Color.YELLOW));
        scoreContainer.addElement(createScoreText(sick + "", Color.RED));

        addElement(scoreContainer);
    }

    private UIText createScoreText(String text, Color color) {
        UIText uiText = new UIText(text, color);
        uiText.setPadding(new UISpacing(0, 0, 5, 5));
        return uiText;
    }
}
