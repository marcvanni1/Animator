package controller;

import org.junit.Test;

import model.AnimatorModelImpl;
import model.IAnimatorModel;
import view.SvgView;
import view.TextualView;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Controller that ensures the constructor and methods work as
 * intended.
 */
public class ControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullModel() {
    IController cont = new Controller(null,
            new SvgView(AnimatorModelImpl.builder().build(), new StringBuilder(), 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullView() {
    IController cont = new Controller(AnimatorModelImpl.builder().build(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullCommand() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    Appendable output = new StringBuilder();
    IController cont = new Controller(model, new TextualView(model, output));
    cont.processCommand(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    Appendable output = new StringBuilder();
    IController cont = new Controller(model, new TextualView(model, output));
    cont.start();
    assertEquals("Canvas 0 0 500 500\n", output.toString());
  }
}