import org.junit.Test;

import model.AnimatorModelImpl;

/**
 * Represents a test class for the factory that ensures the constructor and methods work as
 * intended.
 */
public class FactoryViewsTest {

  @Test(expected = IllegalArgumentException.class)
  public void testFactoryNullModel() {
    FactoryViews nully = new FactoryViews(null, System.out, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFactoryNullString() {
    FactoryViews nully = new FactoryViews(AnimatorModelImpl.builder().build(),
            null, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructWrongViewType() {
    FactoryViews factory = new FactoryViews(AnimatorModelImpl.builder().build(),
            System.out, 9);
    factory.construct("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructWrongTPS() {
    FactoryViews factory = new FactoryViews(AnimatorModelImpl.builder().build(),
            System.out, -9);
    factory.construct("");
  }
}