package view;

import org.junit.Test;

import model.AnimatorModelImpl;

/**
 * Represents a test class for the visual view that ensures the constructor works as intended.
 */
public class VisualViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testVisualNullModel() {
    IView visual = new VisualView(null, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTPS() {
    IView visual = new VisualView(AnimatorModelImpl.builder().build(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTPS() {
    IView visual = new VisualView(AnimatorModelImpl.builder().build(), -3);
  }
}