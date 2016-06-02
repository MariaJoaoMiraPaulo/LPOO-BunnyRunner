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
import com.mygdx.game.Character.Hunter;
import com.mygdx.game.Character.InteractiveTileObject;

/**
 * Created by mariajoaomirapaulo on 26/05/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int typeOfCollision = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT ||
                fixtureB.getFilterData().categoryBits == BunnyGame.BUNNY_BIT) {

            //Colison with Front Bunny
            Fixture bunny;
            Fixture object;

            if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT) {
                bunny = fixtureA;
                object = fixtureB;
            } else {
                bunny = fixtureB;
                object = fixtureA;
            }

            //Test if the object is of the type InterativeTileObject
            if (object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).bunnyHit();
            }
        }

        switch (typeOfCollision) {
            case BunnyGame.BUNNY_BIT | BunnyGame.PLATFORM_BIT:
                Gdx.app.log("Plat ", "Entrei");
                if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.PLATFORM_BIT) {
                    ((Bunny) fixtureA.getUserData()).applyForce(new Vector2(0, -1f));
                } else {
                    ((Bunny) fixtureB.getUserData()).applyForce(new Vector2(0, -1f));
                }
                break;
            case BunnyGame.BUNNY_BIT | BunnyGame.SPIKE_BIT:
                if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.SPIKE_BIT)
                    ((Bunny) fixtureA.getUserData()).setState(Bunny.State.DEAD);
                else {
                    ((Bunny) fixtureB.getUserData()).setState(Bunny.State.DEAD);
                }
                break;
            case BunnyGame.BUNNY_BIT | BunnyGame.GROUND_BIT:
                if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.GROUND_BIT) {
                    if (((Bunny) fixtureA.getUserData()).stateBunny != Bunny.State.STANDING && ((Bunny) fixtureA.getUserData()).stateBunny != Bunny.State.CRAWL)
                        ((Bunny) fixtureA.getUserData()).setState(Bunny.State.RUNNING);
                } else if (((Bunny) fixtureB.getUserData()).stateBunny != Bunny.State.STANDING && ((Bunny) fixtureB.getUserData()).stateBunny != Bunny.State.CRAWL)
                    ((Bunny) fixtureB.getUserData()).setState(Bunny.State.RUNNING);
                break;
            case BunnyGame.HUNTER_BIT | BunnyGame.BUNNY_BIT:
                if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.HUNTER_BIT) {
                    if (((Bunny) fixtureA.getUserData()).stateBunny == Bunny.State.CRAWL){
                        ((Hunter)fixtureB.getUserData()).setHunterState(Hunter.MovementState.DEAD);
                        ((Bunny) fixtureA.getUserData()).b2body.applyLinearImpulse(new Vector2(2f,0), ((Bunny) fixtureA.getUserData()).b2body.getPosition(), true);
                    }
                     else   ((Bunny) fixtureA.getUserData()).setState(Bunny.State.DEAD);
                } else if (((Bunny) fixtureB.getUserData()).stateBunny == Bunny.State.CRAWL){
                    ((Hunter)fixtureA.getUserData()).setHunterState(Hunter.MovementState.DEAD);
                    ((Bunny) fixtureB.getUserData()).b2body.applyLinearImpulse(new Vector2(2f,0), ((Bunny) fixtureB.getUserData()).b2body.getPosition(), true);
                }
                    else ((Bunny) fixtureB.getUserData()).setState(Bunny.State.DEAD);
                break;
            case BunnyGame.BUNNY_BIT | BunnyGame.DOOR_BIT:
                if (fixtureA.getFilterData().categoryBits == BunnyGame.BUNNY_BIT && fixtureB.getFilterData().categoryBits == BunnyGame.DOOR_BIT) {
                    ((Bunny) fixtureA.getUserData()).getScreen().game.newLevel();
                    ((Bunny) fixtureA.getUserData()).getScreen().newGame();
                } else {
                    ((Bunny) fixtureB.getUserData()).getScreen().game.newLevel();
                    ((Bunny) fixtureB.getUserData()).getScreen().newGame();
                }
                break;
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
