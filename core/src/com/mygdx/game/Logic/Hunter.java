package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.GUI.LoadGraphics;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 02/06/16.
 */
public class Hunter extends Sprite implements Disposable{

    public enum MovementState {RIGHT, LEFT, DEAD};

    public MovementState hunterState;
    private float stateTime;

    private World world;
    private GameLogic logic;
    public Body b2body;
    private Fixture fixture;

    public TextureRegion currentFrame;

    public Animation hunterAnimationRight;

    public Animation hunterAnimationLeft;

    public Animation deadHunterAnimation;

    public float animationStateTime;

    private float distanceMax;
    private float inicialPosition;


    public Hunter(GameLogic logic, float x, float y){
        this.logic = logic;
        this.world = logic.getWorld();

        hunterState=MovementState.RIGHT;

        defineHunter(x, y);
        defineHunterAnimation();

        inicialPosition=b2body.getPosition().x;

        distanceMax = b2body.getPosition().x + (247 / BunnyGame.PPM);

        stateTime = 0;

        currentFrame = new TextureRegion(LoadGraphics.getHunterImageLeft());
    }

    public Hunter(World world, float x, float y){

        this.world = world;

        hunterState=MovementState.RIGHT;

        defineHunter(x, y);

        inicialPosition=b2body.getPosition().x;

        distanceMax = b2body.getPosition().x + (247 / BunnyGame.PPM);

        stateTime = 0;
    }

    private void defineHunter(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set( x / BunnyGame.PPM, y / BunnyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(13/ BunnyGame.PPM,16.5f/BunnyGame.PPM);

        fdef.filter.categoryBits = BunnyGame.HUNTER_BIT;
        fdef.filter.maskBits= BunnyGame.BUNNY_BIT |
                BunnyGame.DEFAULT_BIT |
                BunnyGame.GROUND_BIT ;

        fdef.shape = shape;

        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);

        EdgeShape head = new EdgeShape();

        head.set(new Vector2(-20 / BunnyGame.PPM, 26f / BunnyGame.PPM), new Vector2(20 / BunnyGame.PPM, 26f / BunnyGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        fdef.filter.categoryBits = BunnyGame.HUNTER_HEAD_BIT;
        fdef.filter.maskBits= BunnyGame.BUNNY_BIT ;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void defineHunterAnimation(){

        animationStateTime = 0f;

        hunterAnimationRight = new Animation(0.1f, LoadGraphics.getHunterFramesRight());

        hunterAnimationLeft = new Animation(0.1f, LoadGraphics.getHunterFramesLeft());

        deadHunterAnimation = new Animation(0.2f, LoadGraphics.getDeadHunterFrames());
    }

    public void update(float dt){
        stateTime += dt;

        switch (hunterState){
            case RIGHT:
                if(logic.getBunny().stateBunny != Bunny.State.DEAD)
                    b2body.setLinearVelocity(1,0);
                break;
            case LEFT:
                if(logic.getBunny().stateBunny != Bunny.State.DEAD)
                    b2body.setLinearVelocity(-1,0);
                break;
            case DEAD:
                break;
        }


        if(b2body.getPosition().x>=distanceMax && hunterState == MovementState.RIGHT){
            switchState();
        }
        else if(b2body.getPosition().x<= inicialPosition && hunterState == MovementState.LEFT)
            switchState();

        animationStateTime += dt;

        switch (hunterState){
            case RIGHT:
                currentFrame = hunterAnimationRight.getKeyFrame(animationStateTime, true);
                break;
            case LEFT:
                currentFrame = hunterAnimationLeft.getKeyFrame(animationStateTime, true);
                break;
            case DEAD:
                currentFrame = deadHunterAnimation.getKeyFrame(animationStateTime, false);
                break;
        }
    }

    @Override
    public void draw(Batch batch) {
        if(hunterState != Hunter.MovementState.DEAD)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 23f / BunnyGame.PPM, 35/BunnyGame.PPM, 50/BunnyGame.PPM);
        else if(stateTime < 1)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 23f / BunnyGame.PPM, 35/BunnyGame.PPM, 50/BunnyGame.PPM);

    }

    @Override
    public void dispose() {
        currentFrame.getTexture().dispose();
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void switchState(){
        stateTime = 0;

        if(hunterState==MovementState.RIGHT)
            hunterState=MovementState.LEFT;
        else hunterState=MovementState.RIGHT;
    }

    public void setHunterState(MovementState hunterState) {
        stateTime = 0;
        if(hunterState == MovementState.DEAD){
            Filter filter = new Filter();
            filter.categoryBits = BunnyGame.DESTROYED_BIT;
            fixture.setFilterData(filter);
        }
        this.hunterState = hunterState;
    }

}
