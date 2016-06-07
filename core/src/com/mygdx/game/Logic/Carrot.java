package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 26/05/16.
 */

/**
 * Class used to re present a carrot
 */
public class Carrot extends InteractiveTileObject{

    /**
     * Carrot constructor
     * @param world current world
     * @param map current map level
     * @param bounds bounds on map
     */
    public Carrot(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type= BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ BunnyGame.PPM,(bounds.getY()+bounds.getHeight()/2)/ BunnyGame.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/ BunnyGame.PPM,bounds.getHeight()/2/ BunnyGame.PPM);
        fdef.shape=shape;
        fdef.isSensor=true; //this make no collision but it warns the world listener when the bunny passes the carrot
        fixture = body.createFixture(fdef);
        fixture.setUserData(this);


        setCategoryFilter(BunnyGame.CARROT_BIT);
    }

    /**
     * When the bunny hits the carrot this function is called, this function makes the carrot disappear from the map
     */
    @Override
    public void bunnyHit() {
        setCategoryFilter(BunnyGame.DESTROYED_BIT);
        getCell().setTile(null);
    }

}
