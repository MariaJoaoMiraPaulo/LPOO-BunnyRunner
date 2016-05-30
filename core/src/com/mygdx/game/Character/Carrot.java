package com.mygdx.game.Character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class Carrot extends InteractiveTileObject{

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
         fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    @Override
    public void FrontBunnyHit() {
        System.out.println("CARROT");

        //TODO Video 13 collision with carrots
    }

}
