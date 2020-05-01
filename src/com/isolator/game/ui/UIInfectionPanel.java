package com.isolator.game.ui;

import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;
import com.isolator.engine.ui.*;
import com.isolator.engine.ui.containers.HorizontalContainer;
import com.isolator.engine.ui.containers.UIContainer;
import com.isolator.engine.ui.components.UIText;
import com.isolator.engine.ui.containers.VerticalContainer;
import com.isolator.game.entity.Visitor;
import com.isolator.game.states.IsolatorGameState;

import java.awt.*;

public class UIInfectionPanel extends VerticalContainer {

    public UIInfectionPanel(Size parentSize) {
        super(parentSize);
    }

    @Override
    public void update(State state) {
        IsolatorGameState isolatorState = (IsolatorGameState) state;
        clear();
        long healthy = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isHealthy()).count();
        long infected = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isInfected()).count();
        long sick = isolatorState.getObjects().stream().filter(o -> o instanceof Visitor).filter(v -> ((Visitor)v).isSick()).count();

        UIContainer scoreContainer = new HorizontalContainer(getSize());
        scoreContainer.setMargin(new Spacing(5));
        scoreContainer.setPadding(new Spacing(5,  0));
        scoreContainer.setBackgroundColor(new Color(243, 243, 243));
        scoreContainer.addElement(createScoreText(healthy + "", new Color(0, 228, 12)));
        scoreContainer.addElement(createScoreText(infected + "", Color.YELLOW));
        scoreContainer.addElement(createScoreText(sick + "", Color.RED));

        addElement(scoreContainer);
    }

    private UIText createScoreText(String text, Color color) {
        UIText uiText = new UIText(text, color);
        uiText.setPadding(new Spacing(0, 0, 5, 5));
        return uiText;
    }
}
