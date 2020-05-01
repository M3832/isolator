package com.isolator.engine.display;

import com.isolator.engine.state.GameState;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Size;
import com.isolator.engine.state.State;

import java.awt.*;

public class Renderer {

    final DebugRenderer debugRenderer;

    public Renderer() {
        this.debugRenderer = new DebugRenderer();
    }

    public void renderState(GameState state, Size windowSize, Graphics2D screenGraphics) {
        renderScene(state, screenGraphics);
        renderObjects(state, screenGraphics);
        renderUI(state, screenGraphics);
    }

    private void renderObjects(GameState state, Graphics2D screenGraphics) {
        Camera camera = state.getCamera();
        Position camPos = state.getCamera().getPosition();

        state.getObjectsWithinViewingBounds().forEach(object -> {
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

    private void renderScene(GameState state, Graphics2D screenGraphics) {
        screenGraphics.drawImage(
                state.getActiveScene().getSceneGraphics(state),
                0,
                0,
                null
        );
    }

    private void renderUI(State state, Graphics2D screenGraphics) {
        screenGraphics.drawImage(state.getUiScreen().getUiImage(), 0, 0, null);
    }
}
