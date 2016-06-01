package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Character.Bunny;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class PlayScreen implements Screen, InputProcessor {

    public BunnyGame game;

    private Bunny bunny;

    //Tiled Map
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box 2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private boolean dead=false;
    private float gameTime;
    private Vector2 screenDelta;
    private Vector2 startingPoint;


    public PlayScreen(BunnyGame game){
        this.game=game;
        gamecam = new OrthographicCamera();
        //gamePort=new FitViewport(Gdx.graphics.getWidth()/200,Gdx.graphics.getHeight()/200,gamecam);
        //gamePort=new StretchViewport(400,208,gamecam);
        mapLoader=new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BunnyGame.PPM);
        gamePort=new FitViewport(BunnyGame.V_WIDTH / BunnyGame.PPM, BunnyGame.V_HEIGHT / BunnyGame.PPM,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();



        new B2WorldCreator(world,map);

        bunny = new Bunny(world, this);

        gameTime = 0;

        world.setContactListener(new WorldContactListener());

        //Telling Libgdx what it input process so it can be called when a new input event arrives
        Gdx.input.setInputProcessor(this);

        screenDelta = new Vector2(0,0);
        startingPoint = new Vector2(0,0);


    }

    public void update(float dt){
        gameTime += dt;

        //handleInput(dt);

        world.step(1/60f, 6 , 2);

        bunny.update(dt);


        gamecam.position.x = bunny.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    private void handleInput(float dt) {
     /*   if(gameTime < 2) {
            if (Gdx.input.justTouched()) {
                switch (bunny.stateBunny) {
                    case RUNNING:
                        if (bunny.b2body.getLinearVelocity().y == 0) {
                            bunny.jump();
                            bunny.stateBunny = Bunny.State.JUMPING;
                        }
                        break;
                    case STANDING:
                        bunny.stateBunny = Bunny.State.RUNNING;
                        break;
                    case JUMPING:
                        break;
                }
            }
        }
        else  if (Gdx.input.isTouched()) {
            switch (bunny.stateBunny) {
                case RUNNING:
                    if (bunny.b2body.getLinearVelocity().y == 0) {
                        bunny.jump();
                        bunny.stateBunny = Bunny.State.JUMPING;
                    }
                    break;
                case STANDING:
                    bunny.stateBunny = Bunny.State.RUNNING;
                    break;
                case JUMPING:
                    break;
            }
        }
        */
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        renderer.render();
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if(bunny.stateBunny!=Bunny.State.DEAD)//18 32   //36 22
            game.batch.draw(bunny.getCurrentFrame(), bunny.b2body.getPosition().x - 13 / BunnyGame.PPM, bunny.b2body.getPosition().y - 18f / BunnyGame.PPM, 25/BunnyGame.PPM, 38/BunnyGame.PPM);
        else
            game.batch.draw(bunny.getCurrentFrame(), bunny.b2body.getPosition().x - 10 / BunnyGame.PPM, bunny.b2body.getPosition().y - 13.5f / BunnyGame.PPM, 43/BunnyGame.PPM, 27/BunnyGame.PPM);
        game.batch.end();

/*
        if(bunny.stateBunny==Bunny.State.DEAD){
            float time=Gdx.graphics.getDeltaTime();
            int sleep=0;
            if(dead==true){
                while(sleep<5){
                    sleep+=time;
                }
                bunny.getScreen().newGame();
            }
            dead=true;

        }
*/
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    public void newGame(){
        game.setScreen(new PlayScreen(game));
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

        switch (bunny.stateBunny) {
            case RUNNING:
                if (bunny.b2body.getLinearVelocity().y == 0) {
                    bunny.jump();
                    bunny.setState(Bunny.State.JUMPING);
                }
                break;
            case STANDING:
                bunny.setState(Bunny.State.RUNNING);
                break;
            case JUMPING:
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenDelta.set(0,0);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
       /* if(screenDelta.x != screenX && screenDelta.y != screenY)
            screenDelta.set(screenX-screenDelta.x,screenY-screenDelta.y);
        else if(screenDelta.x == screenX && screenDelta.y != screenY)
            screenDelta.set(screenDelta.x,screenY-screenDelta.y);
        else if(screenDelta.x != screenX && screenDelta.y == screenY)
            screenDelta.set(screenX-screenDelta.x, screenDelta.y);*/
        screenDelta.set(screenX - startingPoint.x, screenY - startingPoint.y);

        Gdx.app.log("Eventos: ", "Drag "+screenDelta.x + " " + screenDelta.y);
        if(screenDelta.x > 300)
            bunny.rotateBunny();
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
