package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
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

    public Texture bunnyRunningImage;
    public Texture bunnyStartImage;
    public Texture bunnyFallingImage;
    public Texture bunnyDeadImage;
    public Texture bunnyCrawlImage;
    public Texture bunnySlowDownImage;
    public TextureRegion[] runningFrames;
    public TextureRegion currentFrame;
    public Animation runningAnimation;
    public TextureRegion[] startingFrames;
    public Animation startingAnimation;
    public TextureRegion[] fallingFrames;
    public Animation fallingAnimation;
    public TextureRegion[] deadFrames;
    public Animation deadAnimation;
    public TextureRegion[] crawlFrames;
    public Animation crawlAnimation;
    public TextureRegion[] slowDownFrames;
    public Animation slowDownAnimation;


    public State stateBunny;

    public float animationStateTime;
    public float stateTime;

    public Bunny(World world, BunnyGame game){

        bunnyRunningImage = new Texture("bunny.png");
        TextureRegion[][] tmp = TextureRegion.split(bunnyRunningImage, bunnyRunningImage.getWidth()/5, bunnyRunningImage.getHeight());
        runningFrames = new TextureRegion[5];
        int index = 0;
        for(int i=0;i<5;i++){
            runningFrames[index] = tmp[0][i];
            index++;
        }
        runningAnimation = new Animation(0.1f, runningFrames);
        animationStateTime = 0f;
        stateTime = 0f;

        bunnyStartImage= new Texture("bunnyStart.png");
        TextureRegion[][] tmp2 = TextureRegion.split(bunnyStartImage, bunnyStartImage.getWidth()/3, bunnyStartImage.getHeight());
        startingFrames = new TextureRegion[3];
        int index2 = 0;
        for(int i=0;i<3;i++){
            startingFrames[index2] = tmp2[0][i];
            index2++;
        }
        startingAnimation = new Animation(1f, startingFrames);

        bunnyFallingImage= new Texture("falling_bunny.png");
        TextureRegion[][] tmp3 = TextureRegion.split(bunnyFallingImage, bunnyFallingImage.getWidth()/2, bunnyFallingImage.getHeight());
        fallingFrames = new TextureRegion[2];
        int index3 = 0;
        for(int i=0;i<2;i++){
            fallingFrames[index3] = tmp3[0][i];
            index3++;
        }
        fallingAnimation = new Animation(1f, fallingFrames);

        bunnyDeadImage= new Texture("dead_bunny.png");
        TextureRegion[][] tmp4 = TextureRegion.split(bunnyDeadImage, bunnyDeadImage.getWidth()/2, bunnyDeadImage.getHeight());
        deadFrames = new TextureRegion[2];
        int index4 = 0;
        for(int i=0;i<2;i++){
            deadFrames[index4] = tmp4[0][i];
            index4++;
        }
        deadAnimation= new Animation(0.2f, deadFrames);

        bunnyCrawlImage= new Texture("bunny_crawl.png");
        TextureRegion[][] tmp5 = TextureRegion.split(bunnyCrawlImage, bunnyCrawlImage.getWidth()/2, bunnyCrawlImage.getHeight());
        crawlFrames = new TextureRegion[2];
        int index5 = 0;
        for(int i=0;i<2;i++){
            crawlFrames[index5] = tmp5[0][i];
            index5++;
        }
        crawlAnimation= new Animation(0.2f, crawlFrames);

        bunnySlowDownImage= new Texture("bunny_slowdown.png");
        TextureRegion[][] tmp6 = TextureRegion.split(bunnySlowDownImage, bunnySlowDownImage.getWidth()/2, bunnySlowDownImage.getHeight());
        slowDownFrames = new TextureRegion[2];
        int index6 = 0;
        for(int i=0;i<2;i++){
            slowDownFrames[index6] = tmp6[0][i];
            index6++;
        }
        slowDownAnimation= new Animation(0.8f, slowDownFrames);


        this.game = game;
        this.world = world;
        defineBunny();

        this.stateBunny=State.STANDING;
    }

    public void defineBunny(){
        BodyDef bdef = new  BodyDef();
        bdef.position.set(135 / BunnyGame.PPM, 32 / BunnyGame.PPM);
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

    public void update(float dt){

        stateTime += dt;
        Gdx.app.log("Tempo ", " "+ stateTime + "  " + stateBunny);

        if(b2body.getLinearVelocity().x < 2 && stateBunny==State.RUNNING)
            b2body.setLinearVelocity(MOVEMENT, 0);

        if(stateBunny==State.DEAD && stateTime < 3)
            b2body.setLinearVelocity(0,0);
        else if(stateBunny==State.DEAD && stateTime >=3)
            game.setToGameOverMenu();

        if(b2body.getLinearVelocity().y<0 && stateBunny==State.JUMPING){
            stateBunny=State.FALLING;
            stateTime=0;
        }

        if(stateBunny==State.CRAWL && stateTime>2) {
            rotateBunny();
        }

        if(stateBunny==State.SLOWDOWN && stateTime>3) {
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
        bunnyRunningImage.dispose();
        bunnyStartImage.dispose();
        bunnyFallingImage.dispose();
        bunnyDeadImage.dispose();
        bunnyCrawlImage.dispose();
        currentFrame.getTexture().dispose();
        for(TextureRegion image : runningFrames)
            image.getTexture().dispose();

        for(TextureRegion image : startingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : fallingFrames)
            image.getTexture().dispose();

        for(TextureRegion image : deadFrames)
            image.getTexture().dispose();

        for(TextureRegion image : crawlFrames)
            image.getTexture().dispose();
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

}
