package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;

/**
 * Created by mariajoaomirapaulo on 03/06/16.
 */

public class MainMenu implements Screen {


    private Texture background;
    private Texture button;
    private Image playButton;
    private Image exitButton;
    private Image highScoreButton;
    private Stage stage;
    private BunnyGame game;

    private Viewport gamePort;

    public MainMenu(BunnyGame game) {

        this.game = game;

        gamePort=new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort.apply();
        stage = new Stage(gamePort, game.batch);

        background = new Texture("MainMenu.png");
        button = new Texture("button.png");

        float xPlayButton=Gdx.graphics.getWidth()*40/100;
        float yPlayButton=Gdx.graphics.getHeight()*28/100;
        float wPlayButton = Gdx.graphics.getWidth()*20/100;
        float hPlayButton = Gdx.graphics.getHeight()*7/100;


        playButton = new Image(button);
        playButton.setWidth(wPlayButton);
        playButton.setHeight(hPlayButton);
        playButton.setPosition(xPlayButton,yPlayButton);

        yPlayButton=Gdx.graphics.getHeight()*19/100;


        highScoreButton = new Image(button);
        highScoreButton.setWidth(wPlayButton);
        highScoreButton.setHeight(hPlayButton);
        highScoreButton.setPosition(xPlayButton,yPlayButton);

        yPlayButton=Gdx.graphics.getHeight()*9/100;


        exitButton = new Image(button);
        exitButton.setWidth(wPlayButton);
        exitButton.setHeight(hPlayButton);
        exitButton.setPosition(xPlayButton,yPlayButton);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(highScoreButton);

        playButton.addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
               setPlayScreen();

           }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();

            }
        });

    }

    public void setPlayScreen(){
        game.setToPlayScreen();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
