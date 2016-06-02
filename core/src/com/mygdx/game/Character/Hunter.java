package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
    private World world;
    private PlayScreen screen;
    public Body b2body;

    public Texture hunterImage;
    public TextureRegion[] hunterFrames;
    public TextureRegion currentFrame;
    public Animation hunterAnimationRight;
    public Texture hunterImageLeft;
    public TextureRegion[] hunterFramesLeft;
    public Animation hunterAnimationLeft;

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

        b2body.createFixture(fdef).setUserData(this);

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
        inicialPosition=b2body.getPosition().x;


        distanceMax = b2body.getPosition().x + (247 / BunnyGame.PPM);

    }


    public void update(float dt){




        switch (hunterState){
            case RIGHT:
                b2body.setLinearVelocity(1,0);
                break;
            case LEFT:
                b2body.setLinearVelocity(-1,0);
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
        }
    }

    @Override
    public void dispose() {

    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void switchState(){
        if(hunterState==MovementState.RIGHT)
            hunterState=MovementState.LEFT;
        else hunterState=MovementState.RIGHT;
    }
}
