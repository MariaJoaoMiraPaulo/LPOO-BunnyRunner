package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Character.Bunny;
import com.mygdx.game.Character.Hunter;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Tools.WorldCreator;

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class PlayScreen implements Screen, InputProcessor {

    public BunnyGame game;

    //Tiled Map
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;

    //Box 2d variables
    private Box2DDebugRenderer b2dr;
    private float gameTime;

    //Variables relationed to the touch events
    private Vector2 screenDelta;
    private Vector2 startingPoint;
    private boolean dragDone;


    public PlayScreen(BunnyGame game, int mapLevel){
        this.game=game;
        gamecam = new OrthographicCamera();

        //gamePort=new FitViewport(Gdx.graphics.getWidth()/200,Gdx.graphics.getHeight()/200,gamecam);
        //gamePort=new StretchViewport(400,208,gamecam);

        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1 / BunnyGame.PPM);
        gamePort=new FitViewport(BunnyGame.V_WIDTH / BunnyGame.PPM, BunnyGame.V_HEIGHT / BunnyGame.PPM,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        b2dr = new Box2DDebugRenderer();

        gameTime = 0;

        //Telling Libgdx what it input process so it can be called when a new input event arrives
        Gdx.input.setInputProcessor(this);

        screenDelta = new Vector2(0,0);
        startingPoint = new Vector2(0,0);
        dragDone = false;


    }

    public void update(float dt){
        gameTime += dt;

        game.getWorld().step(1/60f, 6 , 2);

        game.getBunny().update(dt);
        game.getHunter().update(dt);

        gamecam.position.x = game.getBunny().b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderer.render();
        b2dr.render(game.getWorld(),gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.getBunny().draw(game.batch);
        game.getHunter().draw(game.batch);
        game.batch.end();
    }
    
    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        //map.dispose();
        renderer.dispose();
       // world.dispose();
        b2dr.dispose();
    }

    public void newGame(){
        game.setScreen(new PlayScreen(game,game.getAtualLevel()));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        startingPoint.set(screenX, screenY);

        if(game.getBunny().stateBunny == Bunny.State.STANDING)
            game.getBunny().setState(Bunny.State.RUNNING);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenDelta.set(0,0);
        dragDone = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        screenDelta.set(screenX - startingPoint.x, screenY - startingPoint.y);

        Gdx.app.log("Eventos: ", "Drag "+screenDelta.x + " " + screenDelta.y);

        if(screenDelta.y > 20 && !dragDone && game.getBunny().stateBunny != Bunny.State.CRAWL){
            game.getBunny().rotateBunny();
            dragDone = true;
        }

        if(game.getBunny().stateBunny== Bunny.State.RUNNING && screenDelta.y<-20 && !dragDone) {
            game.getBunny().jump();
            game.getBunny().setState(Bunny.State.JUMPING);
            dragDone = true;
        }

        if(game.getBunny().stateBunny== Bunny.State.RUNNING && screenDelta.x<-20 && !dragDone) {
            game.getBunny().setState(Bunny.State.SLOWDOWN);
            dragDone=true;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
