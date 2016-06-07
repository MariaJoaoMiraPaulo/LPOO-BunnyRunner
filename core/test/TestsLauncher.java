

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import static org.mockito.Mockito.mock;

/**
 * Created by Nuno on 06-06-2016.
 */
public class TestsLauncher extends BlockJUnit4ClassRunner implements ApplicationListener {
  /*  public TestsLauncher(Class<?> klass) throws InitializationError {
        super(klass);

        new HeadlessApplication(this, new HeadlessApplicationConfiguration());
    }*/
   public TestsLauncher(Class<?> klass) throws InitializationError {
       super(klass);
       HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();

       new HeadlessApplication(this, conf);
       Gdx.gl = mock(GL20.class);
   }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        for(FrameworkMethod children : getChildren()) {
            runChild(children, new RunNotifier());
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}