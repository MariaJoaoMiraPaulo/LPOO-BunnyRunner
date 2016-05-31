package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Character.Bunny;
import com.mygdx.game.Character.Carrot;
import com.mygdx.game.Character.InteractiveTileObject;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.PLATFORM_BIT ){
           ((Bunny) fixtureA.getUserData()).applyForce(new Vector2(0, -2f));
        }
        else if(fixtureB.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureA.getFilterData().categoryBits == BunnyGame.PLATFORM_BIT){
           ((Bunny) fixtureB.getUserData()).applyForce(new Vector2(0, -2f));
        }

        if(fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT ||
                fixtureB.getFilterData().categoryBits == BunnyGame.BUNNY_BIT){
            Gdx.app.log("Colidiu", "Com o coelho");

            //Colison with Front Bunny
            Fixture bunny;
            Fixture object;

            if(fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT){
                bunny=fixtureA;
                object=fixtureB;
            }
            else{
                bunny=fixtureB;
                object=fixtureA;
            }

            Gdx.app.log("Colidiu", "Top:  "+fixtureA.getFilterData().categoryBits + " com " + fixtureB.getFilterData().categoryBits);

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
