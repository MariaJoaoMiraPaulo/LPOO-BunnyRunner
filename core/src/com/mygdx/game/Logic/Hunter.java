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

/**
 * Class used to re present a Hunter
 */
public class Hunter extends Sprite implements Disposable{

    /**
     * Hunter's possible states
     */
    public enum MovementState {RIGHT, LEFT, DEAD};

    /**
     * Current hunter state
     */
    public MovementState hunterState;

    /**
     * Time associated to each active state
     */
    private float stateTime;

    /**
     * Box2d physics world
     */
    private World world;

    /**
     * Game Logic
     */
    private GameLogic logic;

    /**
     * Box2d physics body
     */
    public Body b2body;

    /**
     * Box2d Fixture
     */
    private Fixture fixture;


    /**
     * Defines a rectangular are of a texture
     */
    public TextureRegion currentFrame;

    /**
     * Hunter's Right state animation
     */
    public Animation hunterAnimationRight;

    /**
     * Hunter's Left state animation
     */
    public Animation hunterAnimationLeft;

    /**
     * Hunter's dead state animation
     */
    public Animation deadHunterAnimation;

    /**
     * Current time of each bunny animation, used to know which is the right image for bunny
     */
    public float animationStateTime;

    /**
     * Maximum distance that the hunter can go through
     */
    private float distanceMax;

    /**
     * Starting Position for hunter
     */
    private float inicialPosition;

    /**
     * Hunter Constructor
     * @param logic Game Logic
     * @param x hunter's x Position on map
     * @param y hunter's y Position on map
     */
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

    /**
     * Hunter Constructor
     * @param world Game world
     * @param x hunter's x Position on map
     * @param y hunter's y Position on map
     */
    public Hunter(World world, float x, float y){

        this.world = world;

        hunterState=MovementState.RIGHT;

        defineHunter(x, y);

        inicialPosition=b2body.getPosition().x;

        distanceMax = b2body.getPosition().x + (247 / BunnyGame.PPM);

        stateTime = 0;
    }

    /**
     * Defines the character Hunter, his position, height and wight for example.
     * @param x hunter's x Position on map
     * @param y hunter's y Position on map
     */
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

    /**
     * Creating Animations for hunter different states
     */
    public void defineHunterAnimation(){

        animationStateTime = 0f;

        hunterAnimationRight = new Animation(0.1f, LoadGraphics.getHunterFramesRight());

        hunterAnimationLeft = new Animation(0.1f, LoadGraphics.getHunterFramesLeft());

        deadHunterAnimation = new Animation(0.2f, LoadGraphics.getDeadHunterFrames());
    }

    /**
     * Updates hunter and changes, if it is necessary his position, hunter state or hunter animation
     * @param dt current time
     */
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


    /**
     * Draws Hunter Image
     * @param batch SpriteBatch
     */
    @Override
    public void draw(Batch batch) {
        if(hunterState != Hunter.MovementState.DEAD)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 23f / BunnyGame.PPM, 35/BunnyGame.PPM, 50/BunnyGame.PPM);
        else if(stateTime < 1)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 23f / BunnyGame.PPM, 35/BunnyGame.PPM, 50/BunnyGame.PPM);

    }

    /**
     * Frees Memory
     */
    @Override
    public void dispose() {
        currentFrame.getTexture().dispose();
    }

    /**
     * Returns a texture region of a texture
     * @return TextureRegion Rectangle area of a texture
     */
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Changes hunter's state from right to lef, or from left to right if he reaches the limit position on x axis
     */
    public void switchState(){
        stateTime = 0;

        if(hunterState==MovementState.RIGHT)
            hunterState=MovementState.LEFT;
        else hunterState=MovementState.RIGHT;
    }

    /**
     * Changes MovementState to hunterStet, equals state Time to null and destroys bit
     * @param hunterState new Hunter state
     */
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
