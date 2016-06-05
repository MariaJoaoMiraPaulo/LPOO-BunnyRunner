package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Logic.Bunny;

/**
 * Created by Nuno on 05/06/2016.
 */
public class PauseMenu implements Screen {

    private BunnyGame game;

    private Image playButton;
    private Image menuButton;
    private Image optionsButton;

    private Texture button;
    private Texture background;

    private Viewport gamePort;

    private Stage stage;

    public PauseMenu(BunnyGame game){
        this.game = game;

        button = new Texture("button.png");
        background = new Texture("pauseMenu.png");

        gamePort=new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort.apply();
        stage = new Stage(gamePort, game.batch);

        float xPlayButton= Gdx.graphics.getWidth()*39/100;
        float yPlayButton=Gdx.graphics.getHeight()*41/100;
        float wPlayButton = Gdx.graphics.getWidth()*11/100;
        float hPlayButton = Gdx.graphics.getHeight()*8/100;

        playButton = new Image(button);
        playButton.setWidth(wPlayButton);
        playButton.setHeight(hPlayButton);
        playButton.setPosition(xPlayButton,yPlayButton);

        xPlayButton= Gdx.graphics.getWidth()*53/100;

        menuButton = new Image(button);
        menuButton.setWidth(wPlayButton);
        menuButton.setHeight(hPlayButton);
        menuButton.setPosition(xPlayButton,yPlayButton);

        xPlayButton= Gdx.graphics.getWidth()*66/100;

        optionsButton = new Image(button);
        optionsButton.setWidth(wPlayButton);
        optionsButton.setHeight(hPlayButton);
        optionsButton.setPosition(xPlayButton,yPlayButton);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(playButton);
        stage.addActor(menuButton);
        stage.addActor(optionsButton);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setPlayScreen();
            }
        });

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setMenuScreen();
            }
        });

        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Adicionar menu das opçoes
            }
        });

    }

    public void setPlayScreen(){
        game.setToSamePlayScreen();
    }

    public void setMenuScreen(){
        game.setToMainMenu();
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
