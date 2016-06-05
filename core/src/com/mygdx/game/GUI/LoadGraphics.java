package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Nuno on 05/06/2016.
 */
public class LoadGraphics implements Disposable{

    private static Texture bunnyRunningImage;
    private static Texture bunnyStartImage;
    private static Texture bunnyFallingImage;
    private static Texture bunnyDeadImage;
    private static Texture bunnyCrawlImage;
    private static Texture bunnySlowDownImage;
    public static Texture hunterImageRight;
    public static Texture hunterImageLeft;
    public static Texture deadHunterImage;

    private static TextureRegion[] runningFrames;
    private static TextureRegion[] startingFrames;
    private static TextureRegion[] fallingFrames;
    private static TextureRegion[] deadFrames;
    private static TextureRegion[] crawlFrames;
    private static TextureRegion[] slowDownFrames;
    public static TextureRegion[] hunterFramesRight;
    public static TextureRegion[] hunterFramesLeft;
    public static TextureRegion[] deadHunterFrames;

    public static Texture getBunnyRunningImage() {
        if(bunnyRunningImage == null)
            bunnyRunningImage = new Texture("bunny.png");

        return bunnyRunningImage;
    }

    public static Texture getBunnyStartImage() {
        if(bunnyStartImage == null)
            bunnyStartImage = new Texture("bunnyStart.png");

        return bunnyStartImage;
    }

    public static Texture getBunnyFallingImage() {
        if(bunnyFallingImage == null)
            bunnyFallingImage = new Texture("falling_bunny.png");

        return bunnyFallingImage;
    }

    public static Texture getBunnyDeadImage() {
        if(bunnyDeadImage == null)
            bunnyDeadImage = new Texture("dead_bunny.png");

        return bunnyDeadImage;
    }

    public static Texture getBunnyCrawlImage() {
        if(bunnyCrawlImage == null)
            bunnyCrawlImage = new Texture("bunny_crawl.png");

        return bunnyCrawlImage;
    }

    public static Texture getBunnySlowDownImage() {
        if(bunnySlowDownImage == null)
            bunnySlowDownImage = new Texture("bunny_slowdown.png");

        return bunnySlowDownImage;
    }

    public static Texture getHunterImageLeft() {
        if(hunterImageLeft == null)
            hunterImageLeft = new Texture("hunter_left.png");

        return hunterImageLeft;
    }

    public static Texture getDeadHunterImage() {
        if(deadHunterImage == null)
            deadHunterImage = new Texture("dead_hunter.png");

        return deadHunterImage;
    }

    public static Texture getHunterImageRight() {
        if(hunterImageRight == null)
            hunterImageRight = new Texture("hunter_right.png");

        return hunterImageRight;
    }

    public static TextureRegion[] getRunningFrames() {
        if(runningFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnyRunningImage(),getBunnyRunningImage().getWidth()/5, getBunnyRunningImage().getHeight());
            runningFrames = new TextureRegion[5];
            int index = 0;
            for(int i=0;i<5;i++){
                runningFrames[index] = tmp[0][i];
                index++;
            }
        }

        return runningFrames;
    }

    public static TextureRegion[] getStartingFrames() {
        if(startingFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnyStartImage(),getBunnyStartImage().getWidth()/3, getBunnyStartImage().getHeight());
            startingFrames = new TextureRegion[3];
            int index = 0;
            for(int i=0;i<3;i++){
                startingFrames[index] = tmp[0][i];
                index++;
            }
        }

        return startingFrames;
    }

    public static TextureRegion[] getFallingFrames() {
        if(fallingFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnyFallingImage(),getBunnyFallingImage().getWidth()/2, getBunnyFallingImage().getHeight());
            fallingFrames = new TextureRegion[2];
            int index = 0;
            for(int i=0;i<2;i++){
                fallingFrames[index] = tmp[0][i];
                index++;
            }
        }

        return fallingFrames;
    }

    public static TextureRegion[] getDeadFrames() {
        if(deadFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnyDeadImage(),getBunnyDeadImage().getWidth()/2, getBunnyDeadImage().getHeight());
            deadFrames = new TextureRegion[2];
            int index = 0;
            for(int i=0;i<2;i++){
                deadFrames[index] = tmp[0][i];
                index++;
            }
        }

        return deadFrames;
    }

    public static TextureRegion[] getCrawlFrames() {
        if(crawlFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnyCrawlImage(),getBunnyCrawlImage().getWidth()/2, getBunnyCrawlImage().getHeight());
            crawlFrames = new TextureRegion[2];
            int index = 0;
            for(int i=0;i<2;i++){
                crawlFrames[index] = tmp[0][i];
                index++;
            }
        }

        return crawlFrames;
    }

    public static TextureRegion[] getSlowDownFrames() {
        if(slowDownFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getBunnySlowDownImage(),getBunnySlowDownImage().getWidth()/2, getBunnySlowDownImage().getHeight());
            slowDownFrames = new TextureRegion[2];
            int index = 0;
            for(int i=0;i<2;i++){
                slowDownFrames[index] = tmp[0][i];
                index++;
            }
        }

        return slowDownFrames;
    }

    public static TextureRegion[] getHunterFramesRight() {
        if(hunterFramesRight == null){
            TextureRegion[][] tmp = TextureRegion.split(getHunterImageRight(), getHunterImageRight().getWidth()/6, getHunterImageRight().getHeight());
            hunterFramesRight = new TextureRegion[6];
            int index = 0;
            for(int i=0;i<6;i++){
                hunterFramesRight[index] = tmp[0][i];
                index++;
            }
        }

        return hunterFramesRight;
    }

    public static TextureRegion[] getHunterFramesLeft() {
        if(hunterFramesLeft == null){
            TextureRegion[][] tmp = TextureRegion.split(getHunterImageLeft(), getHunterImageLeft().getWidth()/6, getHunterImageLeft().getHeight());
            hunterFramesLeft = new TextureRegion[6];
            int index = 0;
            for(int i=0;i<6;i++){
                hunterFramesLeft[index] = tmp[0][i];
                index++;
            }
        }

        return hunterFramesLeft;
    }

    public static TextureRegion[] getDeadHunterFrames() {
        if(deadHunterFrames == null){
            TextureRegion[][] tmp = TextureRegion.split(getDeadHunterImage(), getDeadHunterImage().getWidth()/5, getDeadHunterImage().getHeight());
            deadHunterFrames = new TextureRegion[5];
            int index   = 0;
            for(int i=0;i<5;i++){
                deadHunterFrames[index] = tmp[0][i];
                index++;
            }
        }

        return deadHunterFrames;
    }

    @Override
    public void dispose() {
        bunnyRunningImage.dispose();
        bunnyStartImage.dispose();
        bunnyFallingImage.dispose();
        bunnyDeadImage.dispose();
        bunnyCrawlImage.dispose();

        for(TextureRegion image : runningFrames)
            image.getTexture().dispose();

        for(TextureRegion image : startingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : fallingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : deadFrames)
            image.getTexture().dispose();

        for(TextureRegion image : crawlFrames)
            image.getTexture().dispose();

        hunterImageRight.dispose();
        hunterImageLeft.dispose();
        deadHunterImage.dispose();

        for(TextureRegion image : hunterFramesRight)
            image.getTexture().dispose();

        for(TextureRegion image : hunterFramesLeft)
            image.getTexture().dispose();

        for(TextureRegion image : deadHunterFrames)
            image.getTexture().dispose();
    }
}
