package com.isolator.engine.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.isolator.engine.gfx.ImageUtils.SPRITE_SIZE;

public class SpritesLibrary {

    public static final Image DEFAULT = createDefaultSprite();
    private static final List<AnimationSet> units = loadUnitSets();

    public static final Image WOOD_FLOOR = ImageUtils.loadImage("/tiles/woodfloor.png");
    public static final Image WOOD_WALL_N = ImageUtils.loadImage("/tiles/woodwallN.png");
    public static final Image STAGE_FLOOR = ImageUtils.loadImage("/tiles/stagefloor.png");

    private static final String PATH_TO_UNITS = "/units";

    private static List<AnimationSet> loadUnitSets() {
        List<AnimationSet> units = new ArrayList<>();
        String[] folderNames = getFolderNames();

        for(String folderName : folderNames) {
            AnimationSet animationSet = new AnimationSet();
            String[] animationSheetsInFolder = getAnimationSheetFileNamesInFolder(folderName);
            for(String animationSheetName : animationSheetsInFolder) {
                animationSet.addAnimationSheet(
                        animationSheetName.substring(0, animationSheetName.length() - 4),
                        ImageUtils.scale(ImageUtils.loadImage(PATH_TO_UNITS + "/" + folderName + "/" +animationSheetName), ImageUtils.SCALE));
            }

            units.add(animationSet);
        }

        return units;
    }

    private static String[] getFolderNames() {
        URL resource = SpritesLibrary.class.getResource(PATH_TO_UNITS);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    private static String[] getAnimationSheetFileNamesInFolder(String folderName) {
        URL resource = SpritesLibrary.class.getResource(PATH_TO_UNITS + "/" + folderName);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private static Image createDefaultSprite() {
        Image defaultSprite = new BufferedImage(SPRITE_SIZE, SPRITE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) defaultSprite.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, SPRITE_SIZE, SPRITE_SIZE);

        return defaultSprite;
    }

    public static AnimationSet getRandomUnitSet() {
        int random = (int)(Math.random() * (units.size() + 1) - 1);
        return units.get(random);
    }
}
