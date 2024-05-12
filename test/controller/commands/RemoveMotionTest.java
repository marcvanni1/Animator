package controller.commands;

import org.junit.Test;

import controller.ShapeCommand;
import model.AnimatorModelImpl;
import model.IAnimatorModel;
import model.Motion;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Remove Motion Command that ensures the constructor and execution
 * of the command work as intended.
 */
public class RemoveMotionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullString() {
    ShapeCommand cmd = new RemoveMotion(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullModel() {
    ShapeCommand cmd = new RemoveMotion("Tset", 1);
    cmd.execute(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().declareShape("Tset",
            "ellipse").build();
    model.addMotion("Tset", new Motion(1, 200, 200,
            200, 20, 20, 20, 20));
    assertEquals(1, model.getMotions("Tset").size());
    ShapeCommand cmd = new RemoveMotion("Tset", 1);
    cmd.execute(model);
    assertEquals(0, model.getMotions("Tset").size());
  }
}