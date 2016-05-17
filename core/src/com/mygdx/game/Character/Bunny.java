package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BunnyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by mariajoaomirapaulo on 12/05/16.
 */
public class Bunny extends Sprite {
    public static final int MOVEMENT = 2;

    public PlayScreen screen;

    public World world;
    public Body b2body;

    public Texture bunnyImage;
    public TextureRegion[] runningFrames;
    public TextureRegion currentFrame;
    public Animation runningAnimation;

    float stateTime;

    public Bunny(World world, PlayScreen screen){
        bunnyImage = new Texture("bunny.png");
        TextureRegion[][] tmp = TextureRegion.split(bunnyImage, bunnyImage.getWidth()/5, bunnyImage.getHeight());
        runningFrames = new TextureRegion[5];
        int index = 0;
        Gdx.app.log("BUNNY", "Vou entrar");
        for(int i=0;i<5;i++){
            Gdx.app.log("BUNNY", "i:"+i);
            runningFrames[index] = tmp[0][i];
            index++;
        }
        runningAnimation = new Animation(0.1f, runningFrames);
        stateTime = 0f;

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
    }

    public void update(float dt, boolean playing){

        if(b2body.getLinearVelocity().x < 2 && playing)
            b2body.setLinearVelocity(MOVEMENT, 0);


        stateTime += dt;
        currentFrame = runningAnimation.getKeyFrame(stateTime, true);

    }

    public void jump(){
            b2body.applyLinearImpulse(new Vector2(0, 3f), b2body.getWorldCenter(), true);
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

}
