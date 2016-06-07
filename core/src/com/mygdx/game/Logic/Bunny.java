package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.GUI.GameOverMenu;
import com.mygdx.game.GUI.LoadGraphics;
import com.mygdx.game.GUI.PlayScreen;

/**
 * Created by Maria Joao Mira Paulo e Nuno Ramos on 12/05/16.
 */

/**
 * Class used to re present a Bunny
 */
public class Bunny extends Sprite implements Disposable{

    /**
     * Bunny's Horizontal Velocity
     */
    public static final int MOVEMENT = 2;

    /**
     * Bunny's possible states
     */
    public enum State {STANDING, RUNNING, JUMPING, FALLING, CRAWL, DEAD, SLOWDOWN ,SPEED, NEXT_LEVEL};

    /**
     * Box2d physics world
     */
    public World world;

    /**
     * Box2d physics body
     */
    public Body b2body;

    /**
     * Defines a rectangular are of a texture
     */
    private TextureRegion currentFrame;

    /**
     * Bunny's running state animation
     */
    private Animation runningAnimation;

    /**
     * Bunny's starting state animation
     */
    private Animation startingAnimation;

    /**
     * Bunny's starting falling animation
     */
    private Animation fallingAnimation;

    /**
     * Bunny's dead state animation
     */
    private Animation deadAnimation;

    /**
     * Bunny's crawl state animation
     */
    private Animation crawlAnimation;

    /**
     * Bunny's slowDown state animation
     */
    private Animation slowDownAnimation;

    /**
     * Bunny's speed state animation
     */
    private Animation speedAnimation;

    /**
     *
     * Total Number of Carrots that bunny ate
     */
    private int numberOfCarrots;

    /**
     * Number of Carrots that bunny ate from the use of power speed
     */
    private int numberOfCarrotsSpeed;

    /**
     * Current bunny state
     */
    public State stateBunny;

    /**
     * Current time of each bunny animation, used to know which is the right image for bunny
     */
    private float animationStateTime;

    /**
     * Time associated to each active state
     */
    private float stateTime;

    /**
     * Bunny Constructor
     * @param world World where bunny is going to play
     */
    public Bunny(World world){
        defineAnimations();

        this.world = world;
        defineBunny();

        this.stateBunny=State.STANDING;

        currentFrame = new TextureRegion(LoadGraphics.getBunnyStartImage());

        numberOfCarrots = 0;
    }

    /**
     * Bunny Constructor
     * @param world World where bunny is going to play
     * @param test created only to java unit tests
     */
    public Bunny(World world, boolean test){

        this.world = world;
        defineBunny();

        this.stateBunny=State.STANDING;

        numberOfCarrots = 0;
    }

    /**
     * Defines the character Bunny, his position, height and wight for example.
     */
    public void defineBunny(){
        BodyDef bdef = new  BodyDef();
        bdef.position.set(224 / BunnyGame.PPM, 32 / BunnyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(13/BunnyGame.PPM,16.5f/BunnyGame.PPM);

        fdef.filter.categoryBits = BunnyGame.BUNNY_BIT; //The collision category bits

        // The categories that the bunny would accept for collision
        fdef.filter.maskBits= BunnyGame.CARROT_BIT |
                BunnyGame.DEFAULT_BIT |
                BunnyGame.GROUND_BIT |
                BunnyGame.PLATFORM_BIT |
                BunnyGame.SPIKE_BIT |
                BunnyGame.DOOR_BIT |
                BunnyGame.ROCK_BIT |
                BunnyGame.HUNTER_BIT |
                BunnyGame.DOOR_BIT |
                BunnyGame.HUNTER_HEAD_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * Creating Animations for bunny different states
     */
    public void defineAnimations(){

        animationStateTime = 0f;
        stateTime = 0f;

        runningAnimation = new Animation(0.1f, LoadGraphics.getRunningFrames());

        startingAnimation = new Animation(1f, LoadGraphics.getStartingFrames());

        fallingAnimation = new Animation(1f, LoadGraphics.getFallingFrames());

        deadAnimation= new Animation(0.2f, LoadGraphics.getDeadFrames());

        crawlAnimation= new Animation(0.2f, LoadGraphics.getCrawlFrames());

        slowDownAnimation= new Animation(0.8f, LoadGraphics.getSlowDownFrames());

        speedAnimation= new Animation(0.03f, LoadGraphics.getRunningFrames());
    }

    /**
     * Updates bunny and changes, if it is necessary his position, bunny state or bunny animation
     * @param dt current time
     */
    public void update(float dt){

        stateTime += dt;

        if(b2body.getLinearVelocity().x < MOVEMENT && stateBunny==State.RUNNING ){
            b2body.setLinearVelocity(MOVEMENT, 0);
        }

        if(b2body.getLinearVelocity().x < 2.5f && stateBunny==State.SPEED && stateTime <3){
            b2body.setLinearVelocity(2.5f, 0);
            numberOfCarrotsSpeed=0;
        }
        else if (stateBunny==State.SPEED && stateTime >= 3) {
            setState(State.RUNNING);
        }

        if(stateBunny==State.DEAD && stateTime < 3){
            b2body.setLinearVelocity(0,0);
        }

        if(b2body.getLinearVelocity().y<0 && stateBunny==State.JUMPING){
            stateBunny=State.FALLING;
            stateTime=0;
        }

        if(stateBunny==State.CRAWL && stateTime>1.5f) {
            rotateBunny();
        }

        if(stateBunny==State.SLOWDOWN && stateTime>1.5f) {
            setState(State.RUNNING);
        }

        animationStateTime += dt;

        switch (stateBunny){
            case NEXT_LEVEL:
            case JUMPING:
            case RUNNING:
                currentFrame = runningAnimation.getKeyFrame(animationStateTime, true);
                break;
            case STANDING:
                currentFrame = startingAnimation.getKeyFrame(animationStateTime, true);
                break;
            case FALLING:
                currentFrame=fallingAnimation.getKeyFrame(animationStateTime,true);
                break;
            case DEAD:
                currentFrame=deadAnimation.getKeyFrame(animationStateTime,true);
                break;
            case CRAWL:
                currentFrame=crawlAnimation.getKeyFrame(animationStateTime,true);
                break;
            case SLOWDOWN:
                currentFrame=slowDownAnimation.getKeyFrame(animationStateTime,true);
                break;
            case SPEED:
                currentFrame=speedAnimation.getKeyFrame(animationStateTime,true);
                break;
            default:
                currentFrame = startingAnimation.getKeyFrame(animationStateTime, true);
                break;
        }

    }

    /**
     * Function that allows bunny to Jump
     */
    public void jump(){
        if(stateBunny == State.CRAWL){
            rotateBunny();
        }
        b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        setState(State.JUMPING);
    }

    /**
     * Returns a texture region of a texture
     * @return TextureRegion Rectangle area of a texture
     */
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Applies a Linear impulse on bunny
     * @param force
     */
    public void applyForce(Vector2 force){
        b2body.applyLinearImpulse(force, b2body.getWorldCenter(), true);
    }

    /**
     * Draws Bunny Image
     * @param batch SpriteBatch
     */
    @Override
    public void draw(Batch batch) {
        if(stateBunny!=Bunny.State.DEAD && stateBunny != State.CRAWL)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 18f / BunnyGame.PPM, 25/BunnyGame.PPM, 38/BunnyGame.PPM);
        else
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 18 / BunnyGame.PPM, b2body.getPosition().y - 18f / BunnyGame.PPM, 43/BunnyGame.PPM, 27/BunnyGame.PPM);
    }

    /**
     * Frees Memory
     */
    @Override
    public void dispose() {
        currentFrame.getTexture().dispose();
    }

    /**
     * Changes bunny State to state, equals state Time to null
     * @param state State
     */
    public void setState(State state) {
        if(stateBunny != State.DEAD){
            this.stateTime = 0;
            this.stateBunny=state;
        }
    }

    /**
     * Rotates bunny if his state is CRAWL (vertical to horizontal)
     */
    public void rotateBunny(){

        if(stateBunny==State.CRAWL) {
            b2body.setTransform(b2body.getPosition().x, b2body.getPosition().y, (float)Math.PI*2);
            b2body.applyLinearImpulse(new Vector2(0, 1f), b2body.getPosition(), true);
            setState(State.RUNNING);
        }
        else {
            b2body.setTransform(b2body.getPosition().x,b2body.getPosition().y, (float)Math.PI/2);
            b2body.applyLinearImpulse(new Vector2(2f, 1f), b2body.getPosition(), true);
            setState(State.CRAWL);
        }
    }

    /**
     * Increment Number of carrots
     */
    public void incNumberOfCarrots(){
        numberOfCarrots++;
        numberOfCarrotsSpeed++;
    }

    /**
     * Returns the number of Carrots
     * @return int number of carrots
     */
    public int getNumberOfCarrots() {
        return numberOfCarrots;
    }

    /**
     * Checks if Bunny has the power to change the state for speed, increasing velocity
     */
    public void checkSpeed(){
        if(numberOfCarrotsSpeed>=25) {
            setState(State.SPEED);
            b2body.applyLinearImpulse(new Vector2(4f, 0), b2body.getWorldCenter(), true);
        }
    }

    /**
     * Returns the number of carrots that bunny ate from the use of power speed
     * @return int numberOfcarrotsSpeed
     */
    public int getNumberOfCarrotsSpeed() {
        return numberOfCarrotsSpeed;
    }

    /**
     * Returns the time associated to each active state
     * @return float stateTime
     */
    public float getStateTime() {
        return stateTime;
    }
}
