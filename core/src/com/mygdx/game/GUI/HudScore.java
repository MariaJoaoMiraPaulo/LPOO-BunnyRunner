package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by mariajoaomirapaulo on 04/06/16.
 */
public class HudScore extends Stage {

    public Stage stage;
    private Viewport viewport;
    private Label numberCarrot;
    private Image carrot;
    private Array<Texture> carrotLevel;
    private SpriteBatch batch;

    private int score;
    private int levelCarrot;
    private int imageState=0;


    public HudScore(SpriteBatch batch){
        score = 0;
        this.batch=batch;
        carrotLevel = new Array<Texture>();
        carrotLevel.add(new Texture("carrot0.png"));
        carrotLevel.add(new Texture("carrot1.png"));
        carrotLevel.add(new Texture("carrot2.png"));
        carrotLevel.add(new Texture("carrot3.png"));
        carrotLevel.add(new Texture("carrot4.png"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport,batch);
        carrot = new Image(new Texture("carrot.png"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        numberCarrot = new Label(String.format("%02d", score),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numberCarrot.setFontScale(3);
        numberCarrot.setAlignment(Align.topLeft);

        carrot.setWidth(15);
        carrot.setHeight(20);

        table.add(carrot).padRight(9);
        table.add(numberCarrot);

        table.padLeft(20);
        table.top().left();

        stage.addActor(table);
    }

    public void setScore(int score) {
        this.score=score;
        this.levelCarrot=levelCarrot;
        numberCarrot.setText(String.format("%02d", score));

    }

    @Override
    public void draw() {
        super.draw();
        batch.begin();
        Gdx.app.log("Score"," "+ score);
        if(score==0)
            batch.draw(carrotLevel.get(0),150,Gdx.graphics.getHeight()- 100,233,100);
        else if (score <10)
            batch.draw(carrotLevel.get(1),150,Gdx.graphics.getHeight()- 100,233,100);
        else if( score <20)
            batch.draw(carrotLevel.get(2),150,Gdx.graphics.getHeight()- 100,233,100);
        else if (score < 25)
            batch.draw(carrotLevel.get(3),150,Gdx.graphics.getHeight()- 100,233,100);
        else  batch.draw(carrotLevel.get(4),150,Gdx.graphics.getHeight()- 100,233,100);

        batch.end();

        Gdx.app.log("Entrei"," ");
    }
}
