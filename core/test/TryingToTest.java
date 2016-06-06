import com.mygdx.game.Logic.GameLogic;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Nuno on 06/06/2016.
 */
@RunWith(TestsLauncher.class)
public class TryingToTest {
    @Test
    public void testTest() {
        assertEquals(1, 1);
    }

    @Test
    public void testTest2(){
        GameLogic logic = new GameLogic(1);

        logic.getBunny().incNumberOfCarrots();
        logic.getBunny().incNumberOfCarrots();
        logic.getBunny().incNumberOfCarrots();

        Assert.assertEquals(3, logic.getBunny().getNumberOfCarrots());
    }
}
