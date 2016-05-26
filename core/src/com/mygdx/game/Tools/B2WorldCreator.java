package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Character.Carrot;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class B2WorldCreator {


    public B2WorldCreator(World world, TiledMap map){
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

            new Carrot(world,map,rect);
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

    }


}
