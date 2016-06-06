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
public class TryingToTest {
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotSame(0, logic.getBunny().getNumberOfCarrots());
    }

    @Test
    public void testBunnyDeadWithSpkies(){
        GameLogic logic = new GameLogic(1);

        logic.getBunny().setState(Bunny.State.RUNNING);
        logic.getBunny().setState(Bunny.State.JUMPING);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(Bunny.State.DEAD.toString(), logic.getBunny().stateBunny.toString());
    }

}
