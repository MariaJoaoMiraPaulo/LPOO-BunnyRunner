package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;

/**
 * Created by Nuno on 07/06/2016.
 */
public class HelpMenu implements Screen {


    private Viewport viewport;
    private Texture background;
    private BunnyGame game;
    public Stage stage;
    private Image button;

    public HelpMenu(BunnyGame game){
        this.game = game;
        this.background = new Texture("helpMenu.png");
        this.button=new Image(new Texture("button.png"));
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        this.stage = new Stage(viewport,game.batch);

        float xButton= Gdx.graphics.getWidth()*82/100;
        float yButton=Gdx.graphics.getHeight()*5.45f/100;
        float wButton = Gdx.graphics.getWidth()*11/100;
        float hButton = Gdx.graphics.getHeight()*8/100;

        this.button.setWidth(wButton);
        this.button.setHeight(hButton);
        this.button.setPosition(xButton,yButton);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(button);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setMenuScreen();

            }
        });

    }

    private void setMenuScreen() {
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
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
        background.dispose();
    }
}
