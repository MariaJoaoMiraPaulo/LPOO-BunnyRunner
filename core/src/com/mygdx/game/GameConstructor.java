package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GUI.PlayScreen;

/**
 * Created by Nuno on 03/06/2016.
 */
public class GameConstructor extends ApplicationAdapter {

    private BunnyGame game;
    private Screen screen;

    private long lastUpdateTime = TimeUtils.millis();

    @Override
    public void create() {
        game = new BunnyGame(this);
        screen = new PlayScreen(game, 1);

        game.setScreen(screen);
    }

    @Override
    public void render() {
        long currentTime = TimeUtils.millis();
        screen.render(lastUpdateTime - currentTime);
        lastUpdateTime = currentTime;
    }

    public void newGame(){
        game = new BunnyGame(this);
        //((PlayScreen)screen).setGame(game);
        screen = new PlayScreen(game, 1);
        game.setScreen(screen);
    }
}
