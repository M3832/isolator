package com.isolator.display;

import com.isolator.core.CollisionBox;
import com.isolator.core.Position;
import com.isolator.core.Vector2;
import com.isolator.game.GameState;

import java.awt.*;
import java.util.List;


public class DebugRenderer {

    public static int ENTITY_POSITION = 0;
    public static int COLLISION_BOX = 1;
    public static int ENTITY_DEBUG_UI = 2;

    public void render(List<Integer> features, GameState state, Graphics2D screenGraphics) {
        if (features.contains(COLLISION_BOX)) renderCollisionBoxes(state, screenGraphics);
        if (features.contains(ENTITY_DEBUG_UI)) renderEntityUI(state, screenGraphics);
        if (features.contains(ENTITY_POSITION)) renderEntityPositions(state, screenGraphics);
    }

    private void renderCollisionBoxes(GameState state, Graphics2D screenGraphics) {
        state.getViewableEntities().stream()
                .forEach(entity -> {
                            CollisionBox box = entity.getNextPositionCollisionBox();
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

        state.getViewableEntities()
                .stream()
                .forEach(entity -> screenGraphics.drawImage(
                        entity.getDebugUI(),
                        entity.getPosition().getX() - camera.getPosition().getX() - entity.getDebugUI().getWidth(null) / 2 + entity.getSize().getWidth() / 2,
                        entity.getPosition().getY() - camera.getPosition().getY() + entity.getSize().getHeight(),
                        null));
    }

    private void renderEntityPositions(GameState state, Graphics2D screenGraphics) {
        state.getViewableEntities().stream()
                .forEach(entity -> drawPoint(entity.getPosition(), state, screenGraphics));
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

    private void drawPoint(Position position, GameState state, Graphics2D graphics2D) {
        int pointSize = 25;
        int cameraX = state.getCamera().getPosition().getX();
        int cameraY = state.getCamera().getPosition().getY();
        graphics2D.setColor(Color.GREEN);
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
