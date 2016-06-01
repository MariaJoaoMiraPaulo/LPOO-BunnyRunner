package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by mariajoaomirapaulo on 12/05/16.
 */
public class Bunny extends Sprite implements Disposable{
    public static final int MOVEMENT = 2;



    public enum State {STANDING, RUNNING, JUMPING, FALLING, DEAD};

    public PlayScreen screen;

    public World world;
    public Body b2body;

    public Texture bunnyRunningImage;
    public Texture bunnyStartImage;
    public Texture bunnyFallingImage;
    public Texture bunnyDeadImage;
    public TextureRegion[] runningFrames;
    public TextureRegion currentFrame;
    public Animation runningAnimation;
    public TextureRegion[] startingFrames;
    public Animation startingAnimation;
    public TextureRegion[] fallingFrames;
    public Animation fallingAnimation;
    public TextureRegion[] deadFrames;
    public Animation deadAnimation;


    public State stateBunny;

    public float animationStateTime;
    public float stateTime;

    public Bunny(World world, PlayScreen screen){

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

        this.screen = screen;
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
        shape.setAsBox(10/BunnyGame.PPM,13.5f/BunnyGame.PPM);

       // CircleShape shape = new CircleShape();
       // shape.setRadius(10 / BunnyGame.PPM);
        fdef.filter.categoryBits = BunnyGame.BUNNY_BIT;
        fdef.filter.maskBits= BunnyGame.CARROT_BIT |
                BunnyGame.DEFAULT_BIT |
                BunnyGame.GROUND_BIT |
                BunnyGame.PLATFORM_BIT |
                BunnyGame.SPIKE_BIT |
                BunnyGame.BORDER_BIT;

        fdef.shape = shape;

       b2body.createFixture(fdef).setUserData(this);

        //Line between two different points to simulate the contact with tile Objects

      //  EdgeShape headBunny = new EdgeShape();
       // headBunny.set(new Vector2(-4/BunnyGame.PPM,17/BunnyGame.PPM), new Vector2(4/BunnyGame.PPM,17/BunnyGame.PPM));
        //fdef.shape = headBunny;
       // fdef.isSensor=true;
/*
        b2body.createFixture(fdef).setUserData(this);
/*
        //Line between two different points to simulate the contact with tile Objects
        EdgeShape frontBunny = new EdgeShape();
        frontBunny.set(new Vector2(15/BunnyGame.PPM,17/BunnyGame.PPM), new Vector2(15/BunnyGame.PPM,-7/BunnyGame.PPM));
        fdef.shape = frontBunny;
        fdef.isSensor=true; //A sensor shape collects contact information but never generates a collision response

        b2body.createFixture(fdef).setUserData(this);

        //Line between two different points to simulate the contact with tile Objects
        EdgeShape feetBunny = new EdgeShape();
        frontBunny.set(new Vector2(-10/BunnyGame.PPM,-10/BunnyGame.PPM), new Vector2(10/BunnyGame.PPM,-10/BunnyGame.PPM));
        fdef.shape = feetBunny;
        fdef.isSensor=true; //A sensor shape collects contact information but never generates a collision response

        b2body.createFixture(fdef).setUserData(this);
*/
    }

    public void update(float dt){

        stateTime += dt;

        if(b2body.getLinearVelocity().x < 2 && stateBunny==State.RUNNING)
            b2body.setLinearVelocity(MOVEMENT, 0);

        if(stateBunny==State.DEAD && stateTime < 3)
            b2body.setLinearVelocity(0,0);
        else if(stateBunny==State.DEAD && stateTime >=3)
            screen.newGame();


        if(b2body.getLinearVelocity().y<0 && stateBunny==State.JUMPING){
            stateBunny=State.FALLING;
            stateTime=0;
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
            default:
                currentFrame = startingAnimation.getKeyFrame(animationStateTime, true);
                break;


        }


    }

    public void jump(){
            b2body.applyLinearImpulse(new Vector2(0, 3.7f), b2body.getWorldCenter(), true);
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void applyForce(Vector2 force){
        b2body.applyLinearImpulse(force, b2body.getWorldCenter(), true);
    }

    @Override
    public void dispose() {
        bunnyRunningImage.dispose();
        bunnyStartImage.dispose();
        currentFrame.getTexture().dispose();
        for(TextureRegion image : runningFrames)
            image.getTexture().dispose();

        for(TextureRegion image : startingFrames)
            image.getTexture().dispose();


    }

    public PlayScreen getScreen(){
        return screen;
    }

    public void setState(State state) {
        this.stateTime = 0;
        this.stateBunny=state;

    }

}
