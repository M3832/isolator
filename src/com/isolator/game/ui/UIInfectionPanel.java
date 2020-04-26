package com.isolator.game.ui;

import com.isolator.engine.game.GameState;
import com.isolator.engine.ui.ContainerDirection;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UISpacing;
import com.isolator.engine.ui.UIText;
import com.isolator.game.entity.Visitor;

import java.awt.*;

public class UIInfectionPanel extends UIContainer {

    @Override
    public void update(GameState state) {
        clear();
        long healthy = state.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isHealthy()).count();
        long infected = state.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isInfected()).count();
        long sick = state.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isSick()).count();

        UIContainer scoreContainer = new UIContainer();
        scoreContainer.setMargin(new UISpacing(5));
        scoreContainer.setPadding(new UISpacing(5,  0));
        scoreContainer.setDirection(ContainerDirection.HORIZONTAL);
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
