package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Character.Carrot;
import com.mygdx.game.Character.InteractiveTileObject;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Colidiu", "aleluia");
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Gdx.app.log("Colidiu", "aaaa "+fixtureA.getFilterData().categoryBits + " com " + fixtureB.getFilterData().categoryBits);

        if(fixtureA.getUserData()=="frontBunny" || fixtureB.getUserData()=="frontBunny" ||
                fixtureA.getUserData()=="headBunny" || fixtureB.getUserData()=="headBunny" ||
                fixtureA.getUserData()=="feetBunny" || fixtureB.getUserData()=="feetBunny"){
             Gdx.app.log("Colidiu", "Com o coelho");
                //Colison with Front Bunny
                Fixture bunny;
                Fixture object;
                if(fixtureA.getUserData()=="frontBunny" ||
                        fixtureA.getUserData()=="headBunny" ||
                        fixtureA.getUserData()=="feetBunny"){

                    bunny=fixtureA;
                    object=fixtureB;
                }
                else{
                    bunny=fixtureB;
                    object=fixtureA;
                }

              /*  //Object that bunny colides with
                Fixture object;
                if(front==fixtureA.getUserData())
                    object = fixtureB;
                else object = fixtureA;*/

                Gdx.app.log("Colidiu", "aaaa "+object.getFilterData().categoryBits);

                //Test if the object is of the type InterativeTileObject
                if(object.getUserData() instanceof InteractiveTileObject){
                    Gdx.app.log("Colidiu", "Com uma cenoura");
                    ( (InteractiveTileObject) object.getUserData()).bunnyHit();
                }
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
