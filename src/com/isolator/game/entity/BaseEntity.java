package com.isolator.game.entity;

import com.isolator.engine.controller.Controller;
import com.isolator.engine.core.*;
import com.isolator.engine.display.Camera;
import com.isolator.engine.gameobjects.BaseObject;
import com.isolator.engine.gfx.AnimationController;
import com.isolator.engine.ui.UIContainer;
import com.isolator.engine.ui.UISpacing;
import com.isolator.engine.ui.UIText;
import com.isolator.game.IsolatorGameState;

import java.awt.*;

public abstract class BaseEntity extends BaseObject {
    private static int ID_COUNTER = 1;

    private final int id;

    protected final Controller controller;
    protected Direction direction;
    protected Velocity velocity;

    protected Size collisionBoxSize;

    protected final Position renderOffset;
    protected final AnimationController animationController;
    protected UIContainer uiContainer;

    public BaseEntity(Controller controller) {
        super();
        this.id = ID_COUNTER++;
        animationController = AnimationController.randomUnit();
        this.size = new Size(64, 64);
        this.renderOffset = new Position(0, -12);
        this.collisionBoxSize = new Size(24, 32);
        this.velocity = new Velocity(0.5f, 3.0f);
        this.controller = controller;
        this.direction = Direction.N;
        initUIElements();
    }

    private void initUIElements() {
        this.uiContainer = new UIContainer();
        uiContainer.setBackgroundColor(new Color(0, 0, 0, 0));
        uiContainer.setPadding(new UISpacing(0));
        updateDebugContainer();
    }

    private void updateDebugContainer() {
        uiContainer.clear();
        uiContainer.addElement(getDebugUIText());
    }

    @Override
    public Image getDrawGraphics(Camera camera) {
        return animationController.getDrawGraphics();
    }

    public Image getDebugUI() {
        return uiContainer.getUIElement();
    }

    public void push(Vector2 direction, double force) {
        this.velocity.apply(direction, force);
    }

    public void update(IsolatorGameState state) {
        velocity.update(controller);

        checkCollisions(state);

        setAnimation(velocity);
        animationController.update(state, direction);

        position.apply(velocity);
        direction = Direction.fromVelocity(velocity, direction);
        updateDebugContainer();
    }

    protected abstract void checkCollisions(IsolatorGameState state);

    private void setAnimation(Velocity velocity) {
         if(velocity.isMoving()) {
            animationController.setAnimation("walk");
        } else {
            animationController.setAnimation("stand");
        }
    }

    public CollisionBox getNextPositionCollisionBox() {
        Position nextPosition = position.getNextPosition(velocity);
        Rectangle collisionBounds = new Rectangle(
                nextPosition.getX() - collisionBoxSize.getWidth() / 2,
                nextPosition.getY() - collisionBoxSize.getHeight() / 2,
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight());

        return new CollisionBox(collisionBounds);
    }

    public UIText getDebugUIText() {
        return new UIText(this.toString(), 12);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id;
    }

    public void immediateStopInDirections(boolean collideX, boolean collideY) {
        velocity.immediateStopInDirections(collideX, collideY);
    }

    public double getCurrentSpeed() {
        return velocity.getVelocity();
    }

    public boolean isMovingToward(Position position) {
        Vector2 direction = Vector2.directionBetweenPositions(position, this.position);
        double dotProduct = Vector2.dotProduct(direction, velocity.getDirection());

        return dotProduct > 0;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return getNextPositionCollisionBox();
    }

    @Override
    public Position getRenderOffset(Camera camera) {
        Position zoomedOffset = new Position(
                (int)((size.getWidth() * camera.getZoom()) / 2),
                (int)((size.getHeight() * camera.getZoom()) / 2));
        zoomedOffset.subtract(renderOffset);

        return zoomedOffset;
    }

    public void lookAt(Position targetPosition) {
        Vector2 lookDirection = Vector2.directionBetweenPositions(targetPosition, this.position);

        this.direction = Direction.fromVector(lookDirection);
    }
}
