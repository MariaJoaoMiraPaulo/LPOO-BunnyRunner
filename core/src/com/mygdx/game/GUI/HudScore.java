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
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 04/06/16.
 */
public class HudScore extends Stage {

    public Stage stage;
    private Viewport viewport;
    private Label numberCarrot;
    private Image carrot;
    private Texture btnPause;
    private Array<Texture> carrotLevel;
    private SpriteBatch batch;

    private int score;
    private int numberCarrotsSpeed;
    private int imageState;


    public void setNumberCarrotsSpeed(int numberCarrotsSpeed) {
        this.numberCarrotsSpeed = numberCarrotsSpeed;
    }

    public HudScore(SpriteBatch batch){
        score = 0;
        this.batch=batch;
        this.imageState=0;
        this.numberCarrotsSpeed=0;
        carrotLevel = new Array<Texture>();
        carrotLevel.add(new Texture("carrot0.png"));
        carrotLevel.add(new Texture("carrot1.png"));
        carrotLevel.add(new Texture("carrot2.png"));
        carrotLevel.add(new Texture("carrot3.png"));
        carrotLevel.add(new Texture("carrot4.png"));

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport,batch);
        carrot = new Image(new Texture("carrot.png"));
        btnPause = new Texture("button_pause.png");

        Table table = new Table();
        table.top();
        table.padTop(10);
        table.setFillParent(true);
        numberCarrot = new Label(String.format("%03d", score),new Label.LabelStyle(LoadGraphics.getFont(), Color.BLACK));
        numberCarrot.setFontScale(0.75f);
        numberCarrot.setAlignment(Align.topLeft);

        carrot.scaleBy(0.15f);

        table.add(carrot).padRight(9);
        table.add(numberCarrot);

        table.padLeft(20);
        table.top().left();

        stage.addActor(table);
    }

    public void setScore(int score) {
        this.score=score;
        numberCarrot.setText(String.format("%03d", score));

    }

    @Override
    public void draw() {
        super.draw();
        batch.begin();
        if(numberCarrotsSpeed==0)
           imageState=0;
        else if (numberCarrotsSpeed <10)
            imageState=1;
        else if( numberCarrotsSpeed <20)
           imageState=2;
        else if (numberCarrotsSpeed < 25)
           imageState=3;
        else  imageState=4;

        batch.draw(carrotLevel.get(imageState),0,Gdx.graphics.getHeight()- 150,233,100);
        batch.draw(btnPause,Gdx.graphics.getWidth()-75,Gdx.graphics.getHeight()- 75,75,75);

        batch.end();
    }
}
