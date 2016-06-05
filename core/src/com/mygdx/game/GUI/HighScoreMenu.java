package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Label score2;
    private Label score3;
    private Image button;

    public HighScoreMenu(BunnyGame game){

        this.game=game;
        this.button=new Image(new Texture("button.png"));

        float xButton= Gdx.graphics.getWidth()*82/100;
        float yButton=Gdx.graphics.getHeight()*5.45f/100;
        float wButton = Gdx.graphics.getWidth()*11/100;
        float hButton = Gdx.graphics.getHeight()*8/100;

        this.button.setWidth(wButton);
        this.button.setHeight(hButton);
        this.button.setPosition(xButton,yButton);

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport,game.batch);
        background = new Texture("HighScoreMenu.png");
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Gdx.app.log("HS"," "+game.getHighscore());


        //Score level 1
        score1 = new Label(String.format("%02d",game.getHighscore()),new Label.LabelStyle(LoadGraphics.getFont(), Color.BLACK));
        score1.setFontScale(0.75f);
        score1.setAlignment(Align.center);
        table.center();
        table.padTop(125);
        table.add(score1).padLeft(200).padBottom(20);
        table.row();

        //Score Level 2
        score2 = new Label(String.format("%02d",game.getHighscore()),new Label.LabelStyle(LoadGraphics.getFont(), Color.BLACK));
        score2.setFontScale(0.75f);
        score2.setAlignment(Align.center);
        table.add(score2).padLeft(200).padBottom(20);
        table.padTop(170);
        table.row();

        //Score Level 3
        score3 = new Label(String.format("%02d",game.getHighscore()),new Label.LabelStyle(LoadGraphics.getFont(), Color.BLACK));
        score3.setFontScale(0.75f);
        score3.setAlignment(Align.center);
        table.add(score3).padLeft(200).padBottom(20);
        table.padTop(230);


        Gdx.input.setInputProcessor(stage);
        stage.addActor(table);
        stage.addActor(button);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setMainMenu();

            }
        });

        Gdx.input.setInputProcessor(stage);


    }

    public void setMainMenu(){
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

    }

    public void setLabel(){
        score1.setText(String.format("%02d",game.getHighscore()));
    }
}
