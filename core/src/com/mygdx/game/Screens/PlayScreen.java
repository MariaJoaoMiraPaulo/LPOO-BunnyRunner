package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
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
public class PlayScreen implements Screen {

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

        world.setContactListener(new WorldContactListener());


    }

    public void update(float dt){
        boolean playing = false;

        handleInput(dt);

        world.step(1/60f, 6 , 2);

        bunny.update(dt);


        gamecam.position.x = bunny.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    private void handleInput(float dt) {
        if(Gdx.input.isTouched()){
            switch(bunny.stateBunny){
                case RUNNING:
                    if( bunny.b2body.getLinearVelocity().y == 0){
                        bunny.jump();
                        bunny.stateBunny=Bunny.State.JUMPING;
                    }
                    break;
                case STANDING:
                    Gdx.app.log("Entrei","aqui");
                    bunny.stateBunny=Bunny.State.RUNNING;
                    break;
                case JUMPING:
                    break;
            }
        }
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
        game.batch.draw(bunny.getCurrentFrame(), bunny.b2body.getPosition().x - 10 / BunnyGame.PPM, bunny.b2body.getPosition().y - 10 / BunnyGame.PPM, 18/BunnyGame.PPM, 32/BunnyGame.PPM);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    public void newGame(){
        game.setScreen(new PlayScreen(game));
    }

}
