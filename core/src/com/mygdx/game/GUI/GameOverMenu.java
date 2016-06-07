package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 04/06/16.
 */
public class GameOverMenu implements Screen {

    private Texture background;
    private Texture button;
    private Image playAgainButton;
    private Image MainMenuButoon;
    private Image exitButoon;
    private Stage stage;
    private BunnyGame game;

    private Viewport gamePort;

    public GameOverMenu(BunnyGame game) {

        this.game = game;
        gamePort=new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort.apply();
        stage = new Stage(gamePort, game.batch);


        background = new Texture("gameOver.png");
        button = new Texture("button.png");


        float xPlayButton= Gdx.graphics.getWidth()*41/100;
        float yPlayButton=Gdx.graphics.getHeight()*29/100;
        float wPlayButton = Gdx.graphics.getWidth()*21/100;
        float hPlayButton = Gdx.graphics.getHeight()*8/100;


        playAgainButton = new Image(button);
        playAgainButton.setWidth(wPlayButton);
        playAgainButton.setHeight(hPlayButton);
        playAgainButton.setPosition(xPlayButton,yPlayButton);


        xPlayButton=Gdx.graphics.getWidth()*41/100;
        yPlayButton=Gdx.graphics.getHeight()*19/100;
        wPlayButton = Gdx.graphics.getWidth()*21/100;
        hPlayButton = Gdx.graphics.getHeight()*8/100;


        MainMenuButoon = new Image(button);
        MainMenuButoon.setWidth(wPlayButton);
        MainMenuButoon.setHeight(hPlayButton);
        MainMenuButoon.setPosition(xPlayButton,yPlayButton);


        xPlayButton=Gdx.graphics.getWidth()*41/100;
        yPlayButton=Gdx.graphics.getHeight()*9/100;
        wPlayButton = Gdx.graphics.getWidth()*21/100;
        hPlayButton = Gdx.graphics.getHeight()*8/100;

        exitButoon = new Image(button);
        exitButoon.setWidth(wPlayButton);
        exitButoon.setHeight(hPlayButton);
        exitButoon.setPosition(xPlayButton,yPlayButton);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(playAgainButton);
        stage.addActor(MainMenuButoon);
        stage.addActor(exitButoon);



        playAgainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setPlayScreen();

            }
        });

        MainMenuButoon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setMainMenuScreen();

            }
        });

        exitButoon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();

            }
        });

    }

    public void setMainMenuScreen(){
        game.setToMainMenu();
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
