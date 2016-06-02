package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by mariajoaomirapaulo on 02/06/16.
 */
public class Hunter extends Sprite implements Disposable{

    public enum MovementState {RIGHT, LEFT, DEAD};

    public MovementState hunterState;
    private float stateTime;

    private World world;
    private PlayScreen screen;
    public Body b2body;
    private Fixture fixture;

    public Texture hunterImage;
    public TextureRegion[] hunterFrames;
    public TextureRegion currentFrame;
    public Animation hunterAnimationRight;
    public Texture hunterImageLeft;
    public TextureRegion[] hunterFramesLeft;
    public Animation hunterAnimationLeft;
    public Texture deadHunterImage;
    public TextureRegion[] deadHunterFrames;
    public Animation deadHunterAnimation;

    public float animationStateTime;

    private float distanceMax;
    private float inicialPosition;


    public Hunter(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();

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

        hunterState=MovementState.RIGHT;

        hunterImage = new Texture("hunter_right.png");
        TextureRegion[][] tmp = TextureRegion.split(hunterImage, hunterImage.getWidth()/6, hunterImage.getHeight());
        hunterFrames = new TextureRegion[6];
        int index = 0;
        for(int i=0;i<6;i++){
            hunterFrames[index] = tmp[0][i];
            index++;
        }
        hunterAnimationRight = new Animation(0.1f, hunterFrames);
        animationStateTime = 0f;

        hunterImageLeft = new Texture("hunter_left.png");
        TextureRegion[][] tmp1 = TextureRegion.split(hunterImageLeft, hunterImageLeft.getWidth()/6, hunterImageLeft.getHeight());
        hunterFramesLeft = new TextureRegion[6];
        int index2 = 0;
        for(int i=0;i<6;i++){
            hunterFramesLeft[index2] = tmp1[0][i];
            index2++;
        }
        hunterAnimationLeft = new Animation(0.1f, hunterFramesLeft);

        deadHunterImage = new Texture("dead_hunter.png");
        TextureRegion[][] tmp2 = TextureRegion.split(deadHunterImage, deadHunterImage.getWidth()/5, deadHunterImage.getHeight());
        deadHunterFrames = new TextureRegion[5];
        int index3   = 0;
        for(int i=0;i<5;i++){
            deadHunterFrames[index3] = tmp2[0][i];
            index3++;
        }
        deadHunterAnimation = new Animation(0.2f, deadHunterFrames);

        inicialPosition=b2body.getPosition().x;

        distanceMax = b2body.getPosition().x + (247 / BunnyGame.PPM);

        stateTime = 0;

    }

    public void update(float dt){
        stateTime += dt;

        switch (hunterState){
            case RIGHT:
                if(screen.getBunny().stateBunny != Bunny.State.DEAD)
                    b2body.setLinearVelocity(1,0);
                break;
            case LEFT:
                if(screen.getBunny().stateBunny != Bunny.State.DEAD)
                    b2body.setLinearVelocity(-1,0);
                break;
            case DEAD:
                break;
        }

        Gdx.app.log("Cheguei ", " X: "+b2body.getPosition().x);


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
        hunterImage.dispose();
        hunterImageLeft.dispose();
        deadHunterImage.dispose();

        for(TextureRegion image : hunterFrames)
            image.getTexture().dispose();

        for(TextureRegion image : hunterFramesLeft)
            image.getTexture().dispose();

        for(TextureRegion image : deadHunterFrames)
            image.getTexture().dispose();
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

    public float getStateTime() {
        return stateTime;
    }
}
