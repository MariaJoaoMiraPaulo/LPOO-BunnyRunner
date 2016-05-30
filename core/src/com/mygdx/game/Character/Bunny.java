package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by mariajoaomirapaulo on 12/05/16.
 */
public class Bunny extends Sprite implements Disposable{
    public static final int MOVEMENT = 2;

    public PlayScreen screen;

    public World world;
    public Body b2body;

    public Texture bunnyRunningImage;
    public Texture bunnyStartImage;
    public TextureRegion[] runningFrames;
    public TextureRegion currentFrame;
    public Animation runningAnimation;
    public TextureRegion[] startingFrames;
    public Animation startingAnimation;

    float stateTime;

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
        stateTime = 0f;

        bunnyStartImage= new Texture("bunny_start.png");
        TextureRegion[][] tmp2 = TextureRegion.split(bunnyStartImage, bunnyStartImage.getWidth()/3, bunnyStartImage.getHeight());
        startingFrames = new TextureRegion[3];
        int index2 = 0;
        for(int i=0;i<3;i++){
            Gdx.app.log("BUNNY", "i:"+i);
            startingFrames[index2] = tmp2[0][i];
            index2++;
        }
        startingAnimation = new Animation(0.1f, startingFrames);

        this.screen = screen;
        this.world = world;
        defineBunny();

    }

    public void defineBunny(){
        BodyDef bdef = new  BodyDef();
        bdef.position.set(135 / BunnyGame.PPM, 32 / BunnyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / BunnyGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        //Line between two different points to simulate the contact with tile Objects
        EdgeShape frontBunny = new EdgeShape();
        frontBunny.set(new Vector2(7/BunnyGame.PPM,1/BunnyGame.PPM), new Vector2(7/BunnyGame.PPM,20/BunnyGame.PPM));
        fdef.shape = frontBunny;
        fdef.isSensor=true; //A sensor shape collects contact information but never generates a collision response

        b2body.createFixture(fdef).setUserData("frontBunny");
    }

    public void update(float dt, boolean playing){

        if(b2body.getLinearVelocity().x < 2 && playing)
            b2body.setLinearVelocity(MOVEMENT, 0);


        stateTime += dt;

        switch (screen.getState()){
            case PLAYING:
                currentFrame = runningAnimation.getKeyFrame(stateTime, true);
                break;
            case WAITING_FOR_TOUCH:
                currentFrame = startingAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                currentFrame = startingAnimation.getKeyFrame(stateTime, true);
                break;


        }

    }

    public void jump(){
            b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
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
}
