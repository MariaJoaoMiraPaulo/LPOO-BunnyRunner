package com.mygdx.game.Logic;

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
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 05/06/2016.
 */

/**
 * Class that contains the game logic ( used to separate graphics from logic )
 */
public class GameLogic implements Disposable{

    /**
     * Timer update rate
     */
    public static final float TIMER_RATE = 1/300f;

    /**
     * world step rate
     */
    public static final float WORLD_RATE = 1/300f;

    /**
     * main character from the game
     */
    private Bunny bunny;

    /**
     * all the hunters from the game
     */
    private Array<Hunter> hunters;

    /**
     * all the rocks from the game
     */
    private Array<Rock> rocks;

    /**
     * used do load the tiled map
     */
    private TmxMapLoader mapLoader;

    /**
     * the actual map to be playd
     */
    private TiledMap map;

    /**
     * world that simulates all the physics
     */
    private World world;

    /**
     * timer used to update the logic, is this timer that makes logic independent from graphic
     */
    public Timer timer;

    /**
     * boolean that indicates if the game is on pause or not
     */
    public boolean pause = false;

    /**
     * Constructor of GameLogic
     * @param mapLevel for logic to know which map to load
     */
    public GameLogic(int mapLevel){
        mapLoader=new TmxMapLoader();
        map = mapLoader.load("level"+mapLevel+".tmx");

        world = new World(new Vector2(0,-10),true);

        bunny = new Bunny(world);

        hunters = new Array<Hunter>();
        rocks = new Array<Rock>();

        new WorldCreator(world ,map, this);

        world.setContactListener(new WorldContactListener());

        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                update(TIMER_RATE);
            }
        }, 0, TIMER_RATE);

    }

    /**
     * Where all the logic updates
     * @param dt time, in seconds, since last update
     */
    public void update(float dt){

        if(!pause) {
            //updating world
            world.step(WORLD_RATE, 6, 2);

            //updating bunny
            bunny.update(dt);

            //updating hunter
            for (Hunter hunter : hunters)
                hunter.update(dt);
        }

    }

    /**
     * Function used to pass the hunters from the class world creator to game logic
     * @param hunters hunters off the game
     */
    public void setHunters(Array<Hunter> hunters) {
        this.hunters = hunters;
    }

    /**
     * Function used to pass the rocks from the class world creator to game logic
     * @param rocks rocks off the game
     */
    public void setRocks(Array<Rock> rocks) {
        this.rocks = rocks;
    }

    /**
     * To get the bunny
     * @return bunny off the game
     */
    public Bunny getBunny() {
        return bunny;
    }

    /**
     * To get the world
     * @return world off the current level
     */
    public World getWorld() {
        return world;
    }

    /**
     * To get the map
     * @return map off the current level
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * To get the hunters
     * @return hunters
     */
    public Array<Hunter> getHunters() {
        return hunters;
    }

    /**
     * To get the rocks
     * @return rocks
     */
    public Array<Rock> getRocks() {
        return rocks;
    }

    /**
     * To set pause
     * @param pause boolean that indicates if the game is on pause or not
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * Frees memory
     */
    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
    }
}
