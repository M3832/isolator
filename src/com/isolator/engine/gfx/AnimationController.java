package com.isolator.engine.gfx;

import com.isolator.engine.game.GameState;
import com.isolator.engine.core.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.isolator.engine.gfx.ImageUtils.SCALE;
import static com.isolator.engine.gfx.ImageUtils.SPRITE_SIZE;

public class AnimationController {
    private final AnimationSet animationSet;
    private String currentAnimationName;
    private Image currentAnimationSheet;
    private Image currentAnimationFrame;
    private Direction currentDirection;
    private float currentFrameTime;
    private final float frameDisplayTime;
    private int animationFrameIndex;

    public AnimationController(AnimationSet animationSet) {
        this.animationSet = animationSet;
        frameDisplayTime = 20;
        currentFrameTime = 0;
        animationFrameIndex = 0;
        currentAnimationName = "stand";
        currentAnimationSheet = animationSet.getRandomThatBeginsWith(currentAnimationName);
        currentDirection = Direction.N;
        saveNewCurrentAnimationFrame();
    }

    public void playAnimation(String animationName) {
        if(currentAnimationName.startsWith(animationName)) {
            return;
        }

        final String animationToSet = animationSet.exists(animationName) ? animationName : "stand";
        setAnimation(animationToSet);
    }

    private void setAnimation(String animationToSet) {
        currentAnimationName = animationToSet;
        currentAnimationSheet = animationSet.getRandomThatBeginsWith(currentAnimationName);
        animationFrameIndex = 0;
    }

    public Image getDrawGraphics() {
        return currentAnimationFrame;
    }

    private void saveNewCurrentAnimationFrame() {
        this.currentAnimationFrame = getCurrentAnimationFrame();
    }

    private Image getCurrentAnimationFrame() {
        return ((BufferedImage)currentAnimationSheet).getSubimage(
                (int) (animationFrameIndex * (SPRITE_SIZE * SCALE)),
                (int) (currentDirection.getAnimationRow() * SPRITE_SIZE * SCALE),
                (int) (SPRITE_SIZE * SCALE),
                (int) (SPRITE_SIZE * SCALE)
        );
    }

    public void update(GameState state, Direction direction) {
        currentFrameTime++;
        if(state.getRandomGenerator().nextInt(100) > 95) {
            currentFrameTime++;
        }

        if(currentFrameTime >= frameDisplayTime) {
            nextAnimationFrame();
        }

        if(currentDirection != direction) {
            this.currentDirection = direction;
            saveNewCurrentAnimationFrame();
        }
    }

    private void nextAnimationFrame() {
        currentFrameTime = 0;

        int currentAnimationLength = currentAnimationSheet.getWidth(null) / (int) (SPRITE_SIZE * SCALE);
        if(animationFrameIndex >= currentAnimationLength - 1) {
            replay();
        } else {
            animationFrameIndex++;
        }
        saveNewCurrentAnimationFrame();
    }

    private void replay() {
        setAnimation(this.currentAnimationName);

    }

    public static AnimationController randomUnit() {
        return new AnimationController(SpritesLibrary.getRandomUnitSet());
    }
}
