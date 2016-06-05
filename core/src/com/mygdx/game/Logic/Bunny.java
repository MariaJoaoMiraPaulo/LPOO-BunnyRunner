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
 * Created by mariajoaomirapaulo on 12/05/16.
 */
public class Bunny extends Sprite implements Disposable{
    public static final int MOVEMENT = 2;

    public enum State {STANDING, RUNNING, JUMPING, FALLING, CRAWL, DEAD, SLOWDOWN};

    public BunnyGame game;

    public World world;
    public Body b2body;


    private TextureRegion currentFrame;
    private Animation runningAnimation;
    private Animation startingAnimation;
    private Animation fallingAnimation;
    private Animation deadAnimation;
    private Animation crawlAnimation;
    private Animation slowDownAnimation;

    private int numberOfCarrots;

    public State stateBunny;

    private float animationStateTime;
    private float stateTime;

    public Bunny(World world, BunnyGame game){
        defineAnimations();

        this.game = game;
        this.world = world;
        defineBunny();

        this.stateBunny=State.STANDING;

        numberOfCarrots = 0;
    }

    public void defineBunny(){
        BodyDef bdef = new  BodyDef();
        bdef.position.set(224 / BunnyGame.PPM, 32 / BunnyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(13/BunnyGame.PPM,16.5f/BunnyGame.PPM);

        fdef.filter.categoryBits = BunnyGame.BUNNY_BIT;
        fdef.filter.maskBits= BunnyGame.CARROT_BIT |
                BunnyGame.DEFAULT_BIT |
                BunnyGame.GROUND_BIT |
                BunnyGame.PLATFORM_BIT |
                BunnyGame.SPIKE_BIT |
                BunnyGame.DOOR_BIT |
                BunnyGame.ROCK_BIT |
                BunnyGame.HUNTER_BIT |
                BunnyGame.DOOR_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    public void defineAnimations(){

        animationStateTime = 0f;
        stateTime = 0f;

        runningAnimation = new Animation(0.1f, LoadGraphics.getRunningFrames());

        startingAnimation = new Animation(1f, LoadGraphics.getStartingFrames());

        fallingAnimation = new Animation(1f, LoadGraphics.getFallingFrames());

        deadAnimation= new Animation(0.2f, LoadGraphics.getDeadFrames());

        crawlAnimation= new Animation(0.2f, LoadGraphics.getCrawlFrames());

        slowDownAnimation= new Animation(0.8f, LoadGraphics.getSlowDownFrames());
    }

    public void update(float dt){

        stateTime += dt;
        Gdx.app.log("Tempo ", " "+ stateTime + "  " + stateBunny);

        if(b2body.getLinearVelocity().x < 2 && stateBunny==State.RUNNING)
            b2body.setLinearVelocity(MOVEMENT, 0);

        if(stateBunny==State.DEAD && stateTime < 3)
            b2body.setLinearVelocity(0,0);
        else if(stateBunny==State.DEAD && stateTime >=3){
            ((PlayScreen)game.getScreen()).saveHighscore();
            game.setToGameOverMenu();
        }

        if(b2body.getLinearVelocity().y<0 && stateBunny==State.JUMPING){
            stateBunny=State.FALLING;
            stateTime=0;
        }

        if(stateBunny==State.CRAWL && stateTime>2) {
            rotateBunny();
        }

        if(stateBunny==State.SLOWDOWN && stateTime>1.5f) {
            setState(State.RUNNING);
        }


        animationStateTime += dt;

        switch (stateBunny){
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
            default:
                currentFrame = startingAnimation.getKeyFrame(animationStateTime, true);
                break;
        }

    }

    public void jump(){
        b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void applyForce(Vector2 force){
        b2body.applyLinearImpulse(force, b2body.getWorldCenter(), true);
    }

    @Override
    public void draw(Batch batch) {
        if(stateBunny!=Bunny.State.DEAD && stateBunny != State.CRAWL)
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 13 / BunnyGame.PPM, b2body.getPosition().y - 18f / BunnyGame.PPM, 25/BunnyGame.PPM, 38/BunnyGame.PPM);
        else
            batch.draw(getCurrentFrame(), b2body.getPosition().x - 18 / BunnyGame.PPM, b2body.getPosition().y - 18f / BunnyGame.PPM, 43/BunnyGame.PPM, 27/BunnyGame.PPM);
    }

    @Override
    public void dispose() {
        currentFrame.getTexture().dispose();
      /*  bunnyRunningImage.dispose();
        bunnyStartImage.dispose();
        bunnyFallingImage.dispose();
        bunnyDeadImage.dispose();
        bunnyCrawlImage.dispose();
        for(TextureRegion image : runningFrames)
            image.getTexture().dispose();

        for(TextureRegion image : startingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : fallingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : deadFrames)
            image.getTexture().dispose();

        for(TextureRegion image : crawlFrames)
            image.getTexture().dispose();*/
    }

    public void setState(State state) {
        if(stateBunny != State.DEAD){
            this.stateTime = 0;
            this.stateBunny=state;
        }
    }

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

    public void incNumberOfCarrots(){
        numberOfCarrots++;
        ((PlayScreen)game.getScreen()).getHud().setScore(numberOfCarrots);
        Gdx.app.log("Carrots", "Apanhei uma "+ numberOfCarrots);
    }

    public int getNumberOfCarrots() {
        return numberOfCarrots;
    }

    public BunnyGame getGame() {
        return game;
    }
}
