package com.isolator.entity;

import com.isolator.controller.Controller;
import com.isolator.core.*;
import com.isolator.game.GameState;
import com.isolator.gfx.AnimationController;
import com.isolator.ui.UIContainer;
import com.isolator.ui.UISpacing;
import com.isolator.ui.UIText;

import java.awt.*;

public abstract class BaseEntity {
    private static int ID_COUNTER = 1;

    private int id;

    protected Controller controller;
    protected Position position;
    protected Direction direction;
    protected Size size;
    protected Velocity velocity;

    protected Size collisionBoxSize;

    protected Position renderOffset;
    protected AnimationController animationController;
    protected UIContainer uiContainer;

    public BaseEntity(Controller controller) {
        this.id = ID_COUNTER++;
        animationController = AnimationController.randomUnit();
        this.position = new Position();
        this.size = new Size(64, 64);
        this.renderOffset= new Position(0, -12);
        this.collisionBoxSize = new Size(24, 32);
        this.velocity = new Velocity(0.5f, 5.0f);
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
        uiContainer.addElement(new UIText(String.format("Direction: %s", velocity.getDirection())));
        uiContainer.addElement(new UIText(String.format("Speed: %f", velocity.getVelocity())));
    }

    public Image getDrawGraphics() {
        return animationController.getDrawGraphics();
    }

    public Image getDebugUI() {
        return uiContainer.getUIElement();
    }

    public void push(Vector2 direction, double force) {
        this.velocity.apply(direction, force);
    }

    public void update(GameState state) {
        velocity.update(controller);

        state.getCollisionResolver().handleCollisions(state, this);

        setAnimation(velocity);
        animationController.update(state, direction);

        position.apply(velocity);
        direction = Direction.fromVelocity(velocity, direction);
        updateDebugContainer();
    }

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    };

    public UIText getDebugUIText() {
        return new UIText(this.toString(), 12);
    }

    public Size getSize() {
        return size;
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

    public boolean isMovingToward(BaseEntity other) {
        Vector2 direction = Vector2.directionBetweenPositions(other.getPosition(), position);
        double dotProduct = Vector2.dotProduct(direction, velocity.getDirection());

        return dotProduct > 0;
    }

    public Position getRenderOffset() {
        return renderOffset;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public Size getCollisionBoxSize() {
        return collisionBoxSize;
    }
}
