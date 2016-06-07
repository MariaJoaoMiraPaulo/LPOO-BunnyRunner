import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Logic.Bunny;
import com.mygdx.game.Logic.GameLogic;
import com.mygdx.game.Logic.Hunter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Nuno on 06/06/2016.
 */
@RunWith(TestsLauncher.class)
public class LogicTests {
    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFirstState() {
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        assertEquals(Bunny.State.STANDING.toString(), bunny.stateBunny.toString());
    }

    @Test
    public void testNumberOfCarrots(){
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        bunny.incNumberOfCarrots();
        bunny.incNumberOfCarrots();

        Assert.assertEquals(2, bunny.getNumberOfCarrots());
    }

    @Test
    public void testBunnyJump(){
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        bunny.jump();

        assertEquals(Bunny.State.JUMPING.toString(), bunny.stateBunny.toString());
    }

    @Test
    public void testBunnyRotate(){
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        bunny.rotateBunny();

        assertEquals(Bunny.State.CRAWL.toString(), bunny.stateBunny.toString());
    }

    @Test
    public void testBunnySlowDown(){
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        bunny.setState(Bunny.State.SLOWDOWN);

        assertEquals(Bunny.State.SLOWDOWN.toString(), bunny.stateBunny.toString());
    }

    @Test
    public void testBunnyCheckSpeed(){
        World  world = new World(new Vector2(0,-10),true);
        Bunny bunny = new Bunny(world, true);

        for(int i=0; i<25; i++){
            bunny.incNumberOfCarrots();
        }

        bunny.checkSpeed();

        Assert.assertEquals(25, bunny.getNumberOfCarrots());
        assertEquals(Bunny.State.SPEED.toString(), bunny.stateBunny.toString());
    }

    @Test
    public void testHunterState(){
        World  world = new World(new Vector2(0,-10),true);
        Hunter hunter = new Hunter(world, 20, 20);

        Assert.assertEquals(Hunter.MovementState.RIGHT, hunter.hunterState);

    }

    @Test
    public void testHunterTurnState(){
        World  world = new World(new Vector2(0,-10),true);
        Hunter hunter = new Hunter(world, 20, 20);

        hunter.switchState();

        Assert.assertEquals(Hunter.MovementState.LEFT, hunter.hunterState);

    }

    @Test
    public void testCatchingCarrots(){
        GameLogic logic = new GameLogic(1);

        logic.getBunny().setState(Bunny.State.RUNNING);

        sleep(5000);

        assertNotSame(0, logic.getBunny().getNumberOfCarrots());
    }

    @Test
    public void testBunnyDeadWithSpkies(){
        GameLogic logic = new GameLogic(1);

        logic.getBunny().setState(Bunny.State.RUNNING);
        logic.getBunny().setState(Bunny.State.JUMPING);

        sleep(5000);

        Assert.assertEquals(Bunny.State.DEAD.toString(), logic.getBunny().stateBunny.toString());
    }

    @Test
    public void testBunnyDeadWithHunter(){
        GameLogic logic = new GameLogic(0);

        logic.getBunny().setState(Bunny.State.RUNNING);

       sleep(10000);

        Assert.assertEquals(Bunny.State.DEAD.toString(), logic.getBunny().stateBunny.toString());
    }

    @Test
    public void testBunnyNextLevel(){
        GameLogic logic = new GameLogic(0);

        logic.getBunny().setState(Bunny.State.RUNNING);

        for(int i =0 ;i<7;i++){
            logic.getBunny().setState(Bunny.State.SPEED);
            sleep(2000);
        }

        logic.getBunny().setState(Bunny.State.RUNNING);

        sleep(8000);

        Assert.assertEquals(Bunny.State.NEXT_LEVEL.toString(), logic.getBunny().stateBunny.toString());
    }

    @Test
    public void testGainSpeed(){
        GameLogic logic = new GameLogic(0);

        logic.getBunny().setState(Bunny.State.RUNNING);
        logic.getHunters().get(0).hunterState = Hunter.MovementState.DEAD;

        sleep(14000);

        logic.getBunny().checkSpeed();

        sleep(1000);

        Assert.assertEquals(Bunny.State.SPEED.toString(), logic.getBunny().stateBunny.toString());
    }

    @Test
    public void testFallState(){
        GameLogic logic = new GameLogic(0);

        logic.getBunny().setState(Bunny.State.RUNNING);
        logic.getHunters().get(0).hunterState = Hunter.MovementState.DEAD;

        sleep(1000);

        logic.getBunny().jump();

        sleep(500);

        Assert.assertEquals(Bunny.State.FALLING.toString(), logic.getBunny().stateBunny.toString());
    }

    @Test
    public void testSlowDown(){
        GameLogic logic = new GameLogic(0);

        logic.getBunny().setState(Bunny.State.RUNNING);
        logic.getBunny().setState(Bunny.State.SLOWDOWN);

        Assert.assertEquals(Bunny.State.SLOWDOWN.toString(), logic.getBunny().stateBunny.toString());

        sleep(2000);

        Assert.assertEquals(Bunny.State.RUNNING.toString(), logic.getBunny().stateBunny.toString());
    }


}