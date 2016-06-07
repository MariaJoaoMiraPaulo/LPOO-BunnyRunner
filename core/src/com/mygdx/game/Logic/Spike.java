package com.mygdx.game.Logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 31/05/16.
 */

/**
 * Class used to re present a Spike
 */
public class Spike extends InteractiveTileObject {

    /**
     * Spike constructor
     * @param world world of the spikes
     * @param map map of the spikes
     * @param bounds bounds of the spikes on map
     */
    public Spike(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type= BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2)/ BunnyGame.PPM,(bounds.getY()+bounds.getHeight()/2)/ BunnyGame.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2/ BunnyGame.PPM,bounds.getHeight()/2/ BunnyGame.PPM);
        fdef.shape=shape;

        fixture = body.createFixture(fdef);

        fixture.setUserData(this);

        setCategoryFilter(BunnyGame.SPIKE_BIT);
    }
    @Override
    public void bunnyHit() {
        //when hited by the bunny, the bunny dies
    }
}
