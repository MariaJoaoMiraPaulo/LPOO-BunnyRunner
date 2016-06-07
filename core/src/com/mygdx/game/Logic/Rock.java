package com.mygdx.game.Logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.GUI.LoadGraphics;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 05/06/2016.
 */

/**
 * Class used to re present a Rock
 */
public class Rock extends Sprite {

    /**
     * world of the object
     */
    protected World world;

    /**
     *  map of the object
     */
    protected TiledMap map;

    /**
     * bounds of the object on the map
     */
    protected Rectangle bounds;

    /**
     * to simulate the physics the world needs a body from the object
     */
    protected Body body;

    /**
     * that fixture from the body, this fixture has the bit type
     */
    protected Fixture fixture;

    /**
     * Rock constructor
     * @param world world of the rock
     * @param map map of the rock
     * @param bounds bounds of the rock on map
     */
    public Rock(World world, TiledMap map, Rectangle bounds){

        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.type= BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ BunnyGame.PPM,(bounds.getY()+bounds.getHeight()/2)/ BunnyGame.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/ BunnyGame.PPM,bounds.getHeight()/2/ BunnyGame.PPM);
        fdef.shape=shape;

        fdef.filter.categoryBits = BunnyGame.ROCK_BIT;

        fixture = body.createFixture(fdef);
    }

    /**
     * To draw brick image when the games is running
     * @param batch batch
     */
    @Override
    public void draw(Batch batch) {
        if(fixture.getFilterData().categoryBits == BunnyGame.ROCK_BIT)
            batch.draw(LoadGraphics.getBrickImage(), bounds.getX() / BunnyGame.PPM, bounds.getY() / BunnyGame.PPM, bounds.getWidth()/BunnyGame.PPM, bounds.getHeight()/BunnyGame.PPM);
    }
}
