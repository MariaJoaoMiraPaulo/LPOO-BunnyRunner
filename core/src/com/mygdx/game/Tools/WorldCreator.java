package com.mygdx.game.Tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Logic.Carrot;
import com.mygdx.game.Logic.GameLogic;
import com.mygdx.game.Logic.Hunter;
import com.mygdx.game.Logic.Rock;
import com.mygdx.game.Logic.Spike;
import com.mygdx.game.GUI.PlayScreen;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 26/05/16.
 */
public class WorldCreator {


    public WorldCreator(World world, TiledMap map, GameLogic logic){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //Hunter 8
        //Rock 7
        //Door 6
        //Platform 5
        //Spikes 4
        //Ground 3
        //Carrot 2
        //Grapihcs 1
        //Background 0

        // Creating Carrots
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            new Carrot(world,map,rect);
        }

        // Creating Ground
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;

            fdef.filter.categoryBits = BunnyGame.GROUND_BIT;

            body.createFixture(fdef);
        }

        // Creating Spikes
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
           new Spike(world,map,rect);
        }

        // Creating Platform
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;

            fdef.filter.categoryBits = BunnyGame.PLATFORM_BIT;

            body.createFixture(fdef);
        }


        // Creating Door
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;

            fdef.filter.categoryBits = BunnyGame.DOOR_BIT;

            body.createFixture(fdef);
        }

        Array<Rock> rocks= new Array<Rock>();

        // Creating Rock
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
         /*   bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ BunnyGame.PPM,(rect.getY()+rect.getHeight()/2)/ BunnyGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/ BunnyGame.PPM,rect.getHeight()/2/ BunnyGame.PPM);
            fdef.shape=shape;

            fdef.filter.categoryBits = BunnyGame.ROCK_BIT;

            body.createFixture(fdef);*/
            rocks.add(new Rock(world, map, rect));
        }

        logic.setRocks(rocks);

        Array<Hunter> hunters= new Array<Hunter>();

        // Creating Hunter
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            //screen.setHunter(new Hunter(screen ,rect.getX(),rect.getY()));
            hunters.add(new Hunter(logic ,rect.getX(),rect.getY()));
        }

        logic.setHunters(hunters);


    }


}
