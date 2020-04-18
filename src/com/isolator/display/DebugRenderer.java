package com.isolator.display;

import com.isolator.core.CollisionBox;
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
                .forEach(entity -> {
                    int cameraX = state.getCamera().getPosition().getX();
                    int cameraY = state.getCamera().getPosition().getY();
                    screenGraphics.setColor(Color.GREEN);
                    screenGraphics.drawLine(
                            entity.getPosition().getX() - cameraX - entity.getSize().getWidth() / 2,
                            entity.getPosition().getY() - cameraY,
                            entity.getPosition().getX() - cameraX + entity.getSize().getWidth() / 2,
                            entity.getPosition().getY() - cameraY);
                    screenGraphics.drawLine(
                            entity.getPosition().getX() - cameraX,
                            entity.getPosition().getY() - cameraY - entity.getSize().getHeight() / 2,
                            entity.getPosition().getX() - cameraX,
                            entity.getPosition().getY() - cameraY + entity.getSize().getHeight() / 2);
                        }
                );
    }
}
