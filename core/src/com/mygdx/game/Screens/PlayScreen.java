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

/**
 * Created by mariajoaomirapaulo on 10/05/16.
 */
public class PlayScreen implements Screen {

    public BunnyGame game;

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
        //texture=new Texture("test.jpg");
        gamecam = new OrthographicCamera();
        //gamePort=new FitViewport(Gdx.graphics.getWidth()/200,Gdx.graphics.getHeight()/200,gamecam);
        //gamePort=new StretchViewport(400,208,gamecam);
        mapLoader=new TmxMapLoader();
        map = mapLoader.load("teste.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamePort=new FitViewport(400,208,gamecam);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,0),true);
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
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }



        // Creating Ground
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Hole
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Hood
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }

        // Creating Spikes
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);
        }



    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }

    private void handleInput(float dt) {
        if(Gdx.input.isTouched())
            gamecam.position.x += 100*dt;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
       /* game.batch.begin();
        game.batch.draw(texture,0,0,texture.getWidth(),texture.getHeight());
        game.batch.end();
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

    }
}
