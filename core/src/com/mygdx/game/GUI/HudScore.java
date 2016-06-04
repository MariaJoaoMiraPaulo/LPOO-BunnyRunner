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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.String;


/**
 * Created by mariajoaomirapaulo on 04/06/16.
 */
public class HudScore {

    public Stage stage;
    private Viewport viewport;
    private Label numberCarrot;
    private Image carrot;


    public HudScore(SpriteBatch batch){
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport,batch);
        carrot = new Image(new Texture("carrot.png"));

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        numberCarrot = new Label("Cenouras",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
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
}
