package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Character.Bunny;
import com.mygdx.game.Character.Hunter;
import com.mygdx.game.GUI.PlayScreen;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Tools.WorldCreator;
import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class BunnyGame extends Game {

    public GameState getGameState() {
        return gameState;
    }

    public enum GameState { PLAYING, OVER};

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short BUNNY_BIT = 2;
    public static final short CARROT_BIT = 4;
    public static final short DESTROYED_BIT = 8;
    public static final short GROUND_BIT = 16;
    public static final short PLATFORM_BIT = 32;
    public static final short SPIKE_BIT = 64;
    public static final short DOOR_BIT = 128;
    public static final short ROCK_BIT = 256;
    public static final short HUNTER_BIT = 512;

    private int atualLevel=1;
    GameConstructor gameConst;

    private World world;
    private TmxMapLoader mapLoader;
    private TiledMap map;

    private Timer gameTimer;

    private GameState gameState;
    private Bunny bunny;
    private Hunter hunter;

    public BunnyGame(GameConstructor gameConst){
        this.gameConst = gameConst;

        mapLoader=new TmxMapLoader();
        map = mapLoader.load("level"+1+".tmx");

        world = new World(new Vector2(0,-10),true);

        new WorldCreator(world, map, this);

        bunny = new Bunny(world, this);

        world.setContactListener(new WorldContactListener());

        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                //FIXME: Actually use the elapsed time
                update( 1/65f);
            }
        }, 0,  1/65f);

        gameState = GameState.PLAYING;

    }


    @Override
    public void create() {
      /*  batch= new SpriteBatch();
        setScreen(new PlayScreen(this,1));*/

    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        world.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    public void update(float dt){
        world.step(1/60f, 6 , 2);

        bunny.update(dt);
        hunter.update(dt);
    }

    public int getAtualLevel() {
        return atualLevel;
    }

    public void newLevel() {
        if(this.atualLevel <2)
            this.atualLevel = 2;
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public Bunny getBunny() {
        return bunny;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public void setHunter(Hunter hunter) {
        this.hunter = hunter;
    }

    public void newGame(){
        setScreen(new PlayScreen(this,1));
    }

    public GameConstructor getGameConst() {
        return gameConst;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
