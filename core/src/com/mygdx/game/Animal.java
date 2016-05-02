package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animal extends ApplicationAdapter {


    private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 1;
    SpriteBatch batch;
    Texture img,background;
    TextureRegion[] animationFrames;
    Animation animation;
    float elapsedTime;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("bunny.png");
        background = new Texture("background.jpg");
        TextureRegion[][] tmpFrames=TextureRegion.split(img,img.getWidth()/FRAME_COLS,img.getHeight()/FRAME_ROWS);
        animationFrames=new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index=0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmpFrames[i][j];
            }
        }

        animation=new Animation(0.1f,animationFrames);
        elapsedTime = 0f;
    }

    @Override
    public void render () {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(animation.getKeyFrame(elapsedTime,true),300,100);
        batch.end();
    }

}
