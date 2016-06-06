package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Tools.WorldCreator;

/**
 * Created by Nuno on 05/06/2016.
 */
public class GameLogic implements Disposable{

    private Bunny bunny;
    private Array<Hunter> hunters;
    private Array<Rock> rocks;

    private TmxMapLoader mapLoader;
    private TiledMap map;

    private World world;

    public Timer gameTimer;

    public GameLogic(int mapLevel){
        mapLoader=new TmxMapLoader();
        map = mapLoader.load("level"+mapLevel+".tmx");

        world = new World(new Vector2(0,-10),true);

        bunny = new Bunny(world);

        hunters = new Array<Hunter>();
        rocks = new Array<Rock>();

        new WorldCreator(world ,map, this);

        world.setContactListener(new WorldContactListener());

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update(1/60f);
            }
        }, 0, 1/60f);

    }

    public void update(float dt){
        
        world.step(1/60f, 6 , 2);

        bunny.update(dt);
        for(Hunter hunter : hunters)
            hunter.update(dt);


    }

    public void setHunters(Array<Hunter> hunters) {
        this.hunters = hunters;
    }

    public void setRocks(Array<Rock> rocks) {
        this.rocks = rocks;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public Array<Hunter> getHunters() {
        return hunters;
    }

    public Array<Rock> getRocks() {
        return rocks;
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
    }
}
