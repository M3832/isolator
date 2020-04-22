package com.isolator.engine.gfx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class AnimationSet {

    private final Map<String, Image> animationSheets;

    public AnimationSet() {
        this.animationSheets = new HashMap<>();
    }

    public void addAnimationSheet(String name, Image animationSheet) {
        animationSheets.put(name, animationSheet);
    }

    public Image getRandomThatBeginsWith(String animationName) {
        List<String> animations = animationSheets.keySet().stream()
                .filter(key -> key.startsWith(animationName))
                .collect(Collectors.toList());

        String animation = animations.get((int)(Math.random() * animations.size()));
        return animationSheets.get(animation);
    }

    public boolean exists(String currentAnimationName) {
        return animationSheets.keySet().stream().anyMatch(key -> key.startsWith(currentAnimationName));
    }
}
