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

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class PlayScreen implements Screen {

    public enum State {PLAYING, WAITING_FOR_TOUCH};

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

    private State state;

    public PlayScreen(BunnyGame game){
        this.game=game;
        gamecam = new OrthographicCamera();
        //gamePort=new FitViewport(Gdx.graphics.getWidth()/200,Gdx.graphics.getHeight()/200,gamecam);
        //gamePort=new StretchViewport(400,208,gamecam);
        mapLoader=new TmxMapLoader();
        map = mapLoader.load("teste.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BunnyGame.PPM);
        gamePort=new FitViewport(BunnyGame.V_WIDTH / BunnyGame.PPM, BunnyGame.V_HEIGHT / BunnyGame.PPM,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Spikes Layer   8
        //Hood Layer     7
        //Hole Layer     6
        //Ground Layer   5
        //Carrots Layer  4
        //Graphics       3
        //Grpahics1      2
        //Graphics2      1
        //BackGround     0


        // Creating Carrots
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }



        // Creating Ground
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Hole
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Hood
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Spikes
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        bunny = new Bunny(world, this);

        state = State.WAITING_FOR_TOUCH;

    }

    public void update(float dt){
        boolean playing = false;

        handleInput(dt);

        world.step(1/60f, 6 , 2);

        switch(state){
            case PLAYING:
                playing = true;
                break;
        }
        bunny.update(dt,playing);


        gamecam.position.x = bunny.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    private void handleInput(float dt) {
        if(Gdx.input.isTouched()){
            switch(state){
                case PLAYING:
                    if( bunny.b2body.getLinearVelocity().y == 0){
                        bunny.jump();
                    }
                    break;
                case WAITING_FOR_TOUCH:
                    state = State.PLAYING;
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
        game.batch.draw(bunny.getCurrentFrame(), bunny.b2body.getPosition().x - 10 / BunnyGame.PPM, bunny.b2body.getPosition().y - 10 / BunnyGame.PPM, 16/BunnyGame.PPM, 32/BunnyGame.PPM);
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

    }

}
