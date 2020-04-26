package com.isolator.engine.display;

import com.isolator.engine.game.GameState;
import com.isolator.engine.core.CollisionBox;
import com.isolator.engine.core.Position;
import com.isolator.engine.core.Vector2;
import com.isolator.game.ai.states.AIWander;
import com.isolator.game.entity.BaseEntity;
import com.isolator.game.entity.Visitor;

import java.awt.*;
import java.util.List;


public class DebugRenderer {

    public static final int ENTITY_POSITION = 0;
    public static final int COLLISION_BOX = 1;
    public static final int ENTITY_DEBUG_UI = 2;

    public void render(List<Integer> features, GameState state, Graphics2D screenGraphics) {
        if (features.contains(COLLISION_BOX)) renderCollisionBoxes(state, screenGraphics);
        if (features.contains(ENTITY_DEBUG_UI)) renderEntityUI(state, screenGraphics);
        if (features.contains(ENTITY_POSITION)) renderEntityPositions(state, screenGraphics);
    }

    private void renderCollisionBoxes(GameState state, Graphics2D screenGraphics) {
        state.getObjects()
                .forEach(object -> {
                            CollisionBox box = object.getCollisionBox();
                            screenGraphics.fillRect(
                                    box.getBox().x - state.getCamera().getPosition().getX(),
                                    box.getBox().y - state.getCamera().getPosition().getY(),
                                    (int) box.getBox().getWidth(),
                                    (int) box.getBox().getHeight()
                            );
                        }
                );
    }

    private void renderEntityUI(GameState state, Graphics2D screenGraphics) {
        Camera camera = state.getCamera();

        state.getObjectsWithinViewingBounds()
                .forEach(object -> {
                    if(object instanceof BaseEntity) {
                        BaseEntity entity = (BaseEntity) object;
                        screenGraphics.drawImage(
                                entity.getDebugUI(),
                                entity.getPosition().getX() - camera.getPosition().getX() - entity.getDebugUI().getWidth(null) / 2 + object.getSize().getWidth() / 2,
                                entity.getPosition().getY() - camera.getPosition().getY() + entity.getSize().getHeight(),
                                null);
                    }
                });
    }

    private void renderEntityPositions(GameState state, Graphics2D screenGraphics) {
        state.getObjectsWithinViewingBounds()
                .forEach(object -> drawPoint(object.getPosition(), state, screenGraphics, Color.GREEN));

        state.getObjects().stream()
                .filter(o -> o instanceof Visitor)
                .map(o -> (Visitor) o)
                .filter(v -> v.getCurrentAction() instanceof AIWander)
                .map(v -> (AIWander) v.getCurrentAction())
                .forEach(wander -> wander.getPositions().forEach(p -> drawPoint(p, state, screenGraphics, Color.RED)));
    }

    private void drawString(Position position, String string, GameState state, Graphics2D graphics) {
        int cameraX = state.getCamera().getPosition().getX();
        int cameraY = state.getCamera().getPosition().getY();

        graphics.setColor(Color.WHITE);
        graphics.drawString(
                string,
                position.getX() - cameraX,
                position.getY() - cameraY + 32);
    }

    private void drawVector(Position startPosition, Vector2 vector, GameState state, Graphics2D graphics) {
        int cameraX = state.getCamera().getPosition().getX();
        int cameraY = state.getCamera().getPosition().getY();

        graphics.setColor(Color.YELLOW);
        graphics.drawLine(
                startPosition.getX() - cameraX,
                startPosition.getY() - cameraY,
                startPosition.getX() - cameraX + (int) (vector.getX() * 20),
                startPosition.getY() - cameraY + (int) (vector.getY() * 20));
    }

    private void drawPoint(Position position, GameState state, Graphics2D graphics2D, Color color) {
        int pointSize = 25;
        int cameraX = state.getCamera().getPosition().getX();
        int cameraY = state.getCamera().getPosition().getY();
        graphics2D.setColor(color);
        graphics2D.drawLine(
                position.getX() - cameraX - pointSize / 2,
                position.getY() - cameraY,
                position.getX() - cameraX + pointSize / 2,
                position.getY() - cameraY);
        graphics2D.drawLine(
                position.getX() - cameraX,
                position.getY() - cameraY - pointSize / 2,
                position.getX() - cameraX,
                position.getY() - cameraY + pointSize / 2);
    }
}
