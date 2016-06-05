package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;

/**
 * Created by mariajoaomirapaulo on 05/06/16.
 */
public class HighScoreMenu implements Screen {

    private Viewport viewport;
    private Texture background;
    private BunnyGame game;
    public Stage stage;
    private Label score1;

    public HighScoreMenu(BunnyGame game){

        this.game=game;
        int score=0;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport,game.batch);
        background = new Texture("HighScoreMenu.png");

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        score1 = new Label(String.format("%02d", score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score1.setFontScale(3);
        score1.setAlignment(Align.center);

        table.center();
        table.padTop(115);
        table.add(score1).padLeft(225);


        stage.addActor(table);




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
        stage.draw();
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
