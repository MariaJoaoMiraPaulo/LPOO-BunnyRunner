package com.mygdx.game.Logic;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 26/05/16.
 */

/**
 * Class used to re present an object from tile
 */
public abstract class InteractiveTileObject {

    /**
     * world of the object
     */
    protected World world;

    /**
     * map of the object
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
     * InteractiveTileObject constructor
     * @param world world of the object
     * @param map map of the object
     * @param bounds bounds of the object on map
     */
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;
    }

    /**
     * Called when the bunny its the object
     */
    public abstract void bunnyHit();

    /**
     * To set category bit, its used when the bunny hits the object to set the category bit to destroyed bit
     * @param filterBit new category bit
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    /**
     * To get a cell from the tile map
     * @return Tiled Map cell
     */
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x * BunnyGame.PPM /16), (int) (body.getPosition().y * BunnyGame.PPM/16));
    }
}
