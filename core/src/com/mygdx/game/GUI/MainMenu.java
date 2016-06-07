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
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 03/06/16.
 */

public class MainMenu implements Screen {


    private Texture background;
    private Texture button;
    private Image playButton;
    private Image exitButton;
    private Image highScoreButton;
    private Image helpButton;
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

        float xButton=Gdx.graphics.getWidth()*40/100;
        float yButton=Gdx.graphics.getHeight()*28/100;
        float wButton = Gdx.graphics.getWidth()*20/100;
        float hButton = Gdx.graphics.getHeight()*7/100;

        playButton = new Image(button);
        playButton.setWidth(wButton);
        playButton.setHeight(hButton);
        playButton.setPosition(xButton,yButton);

        yButton=Gdx.graphics.getHeight()*19/100;


        highScoreButton = new Image(button);
        highScoreButton.setWidth(wButton);
        highScoreButton.setHeight(hButton);
        highScoreButton.setPosition(xButton,yButton);

        yButton=Gdx.graphics.getHeight()*9/100;


        exitButton = new Image(button);
        exitButton.setWidth(wButton);
        exitButton.setHeight(hButton);
        exitButton.setPosition(xButton,yButton);

        xButton= Gdx.graphics.getWidth()*82/100;
        yButton=Gdx.graphics.getHeight()*5.45f/100;
        wButton = Gdx.graphics.getWidth()*11/100;
        hButton = Gdx.graphics.getHeight()*8/100;


        helpButton = new Image(button);
        helpButton.setWidth(wButton);
        helpButton.setHeight(hButton);
        helpButton.setPosition(xButton,yButton);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(playButton);
        stage.addActor(exitButton);
        stage.addActor(highScoreButton);
        stage.addActor(helpButton);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setPlayScreen();

            }
        });

        highScoreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setHighScoreMenu();

            }
        });

        helpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setHelpMenu();
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();

            }
        });

    }

    private void setHelpMenu() {
        game.setToHelpMenu();
    }

    public void setHighScoreMenu(){
        game.setToHighScoreMenu();
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
