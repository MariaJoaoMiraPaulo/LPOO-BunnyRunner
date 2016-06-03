package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.BunnyGame;

/**
 * Created by mariajoaomirapaulo on 03/06/16.
 */

public class MainMenu implements Screen {


    private Texture background;
    private Image playButton;
    private Stage stage;
    private BunnyGame game;



    public MainMenu(BunnyGame game) {

        this.game = game;

        stage = new Stage();

        background = new Texture("MainMenu.png");
        Gdx.app.log("Background", " comprimento :"+ Gdx.graphics.getWidth()+ " altura:"+ Gdx.graphics.getHeight());

        float xPlayButton=Gdx.graphics.getWidth()*14.5f/100;
        Gdx.app.log("Background", " x :"+Gdx.graphics.getHeight()*19/100);
        float yPlayButton=Gdx.graphics.getHeight()*19/100;
        Gdx.app.log("Background", " y :"+Gdx.graphics.getHeight()*19/100);
        float wPlayButton = Gdx.graphics.getWidth()*32/100;
        Gdx.app.log("Background", " comprimento :"+Gdx.graphics.getWidth()*32/100);
        float hPlayButton = Gdx.graphics.getHeight()*12/100;
        Gdx.app.log("Background", " altura :"+Gdx.graphics.getHeight()*12/100);


        playButton = new Image(background);
        playButton.setWidth(wPlayButton);
        playButton.setHeight(hPlayButton);
        playButton.setPosition(xPlayButton,yPlayButton);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(playButton);
        //stage.addActor(exitButton);

        playButton.addListener(new ClickListener(){
           @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Entrei","no botão");
               setPlayScreen();

           }
        });


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
        game.batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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
