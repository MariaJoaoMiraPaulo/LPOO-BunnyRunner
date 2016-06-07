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

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 06/06/16.
 */
public class FinalMenu implements Screen {

    private BunnyGame game;
    private Texture background;
    private Image restartButton;
    private Stage stage;
    private Viewport gamePort;

    public FinalMenu(BunnyGame game){
        this.game=game;

        this.background = new Texture("FinalMenu.png");
        this.restartButton = new Image (new Texture("button.png"));

        gamePort=new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamePort.apply();
        stage = new Stage(gamePort, game.batch);


        float xPlayButton= Gdx.graphics.getWidth()*47.6f/100;
        float yPlayButton=Gdx.graphics.getHeight()*33.3f/100;
        float wPlayButton = Gdx.graphics.getWidth()*11/100;
        float hPlayButton = Gdx.graphics.getHeight()*8/100;


        restartButton.setWidth(wPlayButton);
        restartButton.setHeight(hPlayButton);
        restartButton.setPosition(xPlayButton,yPlayButton);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(restartButton);

        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Emtrei","botao");
                restartGame();
                setMainMenuScreen();

            }
        });

    }

    public void restartGame(){game.setWon(false);
        game.setCurrentLevel(1);}

    public void setMainMenuScreen(){
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
        gamePort.update(width,height);    }

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
        background.dispose();
    }
}
