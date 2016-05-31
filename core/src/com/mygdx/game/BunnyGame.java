package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class    BunnyGame extends Game{

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short BUNNY_BIT = 2;
    public static final short CARROT_BIT = 4;
    public static final short DESTROYED_BIT = 8;
    public static final short GROUND_BIT = 16;
    public static final short PLATFORM_BIT = 32;

    public SpriteBatch batch;

    @Override
    public void create() {
    batch= new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }
}
