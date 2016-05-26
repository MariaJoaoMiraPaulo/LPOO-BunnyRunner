package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Character.InteractiveTileObject;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData()=="frontBunny" || fixtureB.getUserData()=="frontBunny"){
            //Colison with Front Bunny
            Fixture front;
            if(fixtureA.getUserData()=="frontBunny")
                front=fixtureA;
            else front=fixtureB;

            //Object that bunny colides with
            Fixture object;
            if(front==fixtureA.getUserData())
               object = fixtureB;
            else object = fixtureA;

            //Test if the object is of the type InterativeTileObject
            if(object.getUserData() instanceof InteractiveTileObject)
                ( (InteractiveTileObject) object.getUserData()).FrontBunnyHit();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
