package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.BunnyGame;

/**
 * Created by mariajoaomirapaulo on 04/06/16.
 */
public class GameOverMenu implements Screen {

    private Texture background;
    private Image playAgainButton;
    private Image MainMenuButoon;
    private Image exitButoon;
    private Stage stage;
    private BunnyGame game;



    public GameOverMenu(BunnyGame game) {

        this.game = game;

        stage = new Stage();

        background = new Texture("gameOver.png");


        float xPlayButton= Gdx.graphics.getWidth()*41/100;
        Gdx.app.log("Background", " x :"+Gdx.graphics.getHeight()*19/100);
        float yPlayButton=Gdx.graphics.getHeight()*29/100;
        Gdx.app.log("Background", " y :"+Gdx.graphics.getHeight()*19/100);
        float wPlayButton = Gdx.graphics.getWidth()*21/100;
        Gdx.app.log("Background", " comprimento :"+Gdx.graphics.getWidth()*32/100);
        float hPlayButton = Gdx.graphics.getHeight()*8/100;
        Gdx.app.log("Background", " altura :"+Gdx.graphics.getHeight()*12/100);


        playAgainButton = new Image(background);
        playAgainButton.setWidth(wPlayButton);
        playAgainButton.setHeight(hPlayButton);
        playAgainButton.setPosition(xPlayButton,yPlayButton);


        xPlayButton=Gdx.graphics.getWidth()*41/100;
        Gdx.app.log("Background", " x :"+Gdx.graphics.getHeight()*19/100);
        yPlayButton=Gdx.graphics.getHeight()*19/100;
        Gdx.app.log("Background", " y :"+Gdx.graphics.getHeight()*19/100);
        wPlayButton = Gdx.graphics.getWidth()*21/100;
        Gdx.app.log("Background", " comprimento :"+Gdx.graphics.getWidth()*32/100);
        hPlayButton = Gdx.graphics.getHeight()*8/100;
        Gdx.app.log("Background", " altura :"+Gdx.graphics.getHeight()*12/100);


        MainMenuButoon = new Image(background);
        MainMenuButoon.setWidth(wPlayButton);
        MainMenuButoon.setHeight(hPlayButton);
        MainMenuButoon.setPosition(xPlayButton,yPlayButton);


        xPlayButton=Gdx.graphics.getWidth()*41/100;
        Gdx.app.log("Background", " x :"+Gdx.graphics.getHeight()*19/100);
        yPlayButton=Gdx.graphics.getHeight()*9/100;
        Gdx.app.log("Background", " y :"+Gdx.graphics.getHeight()*19/100);
        wPlayButton = Gdx.graphics.getWidth()*21/100;
        Gdx.app.log("Background", " comprimento :"+Gdx.graphics.getWidth()*32/100);
        hPlayButton = Gdx.graphics.getHeight()*8/100;
        Gdx.app.log("Background", " altura :"+Gdx.graphics.getHeight()*12/100);

        exitButoon = new Image(background);
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
        game.setScreen(new MainMenu(game));
    }

    public void setPlayScreen(){
        game.setScreen(new PlayScreen(game,game.getAtualLevel()));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        game.batch.begin();
        //game.batch.draw(background,0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        game.batch.draw(background,0,0);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
