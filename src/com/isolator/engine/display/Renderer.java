package com.isolator.engine.display;

import com.isolator.engine.state.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.state.State;

import java.awt.*;

public class Renderer {

    final DebugRenderer debugRenderer;

    public Renderer() {
        this.debugRenderer = new DebugRenderer();
    }

    public void renderState(State state, Graphics2D screenGraphics) {
        renderScene(state, screenGraphics);
        renderUI(state, screenGraphics);
        renderObjects(state, screenGraphics);
    }

    private void renderObjects(State state, Graphics2D screenGraphics) {
        if(!(state instanceof GameState)) return;

        GameState gameState = (GameState) state;
        Camera camera = gameState.getCamera();
        Position camPos = gameState.getCamera().getPosition();

        gameState.getObjectsWithinViewingBounds().forEach(object -> {
            Position objectPos = object.getPosition();
            Position renderOffset = object.getRenderOffset(camera);
            Image sprite = object.getDrawGraphics(camera);

            screenGraphics.drawImage(
                    sprite,
                    objectPos.getX() - camPos.getX() - renderOffset.getX(),
                    objectPos.getY() - camPos.getY() - renderOffset.getY(),
                    null);
        });
    }

    private void renderScene(State state, Graphics2D screenGraphics) {
        screenGraphics.drawImage(
                state.getSceneImage(),
                0,
                0,
                null
        );
    }

    private void renderUI(State state, Graphics2D screenGraphics) {
        screenGraphics.drawImage(state.getUiScreen().getUiImage(), 0, 0, null);
    }
}
