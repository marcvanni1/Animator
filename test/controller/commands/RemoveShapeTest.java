package controller.commands;

import org.junit.Test;

import controller.ShapeCommand;
import model.AnimatorModelImpl;
import model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Remove Shape Command that ensures the constructor and execution
 * of the command work as intended.
 */
public class RemoveShapeTest {
  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullString() {
    ShapeCommand cmd = new RemoveShape(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullModel() {
    ShapeCommand cmd = new RemoveShape("Tset");
    cmd.execute(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("Tset rectangle");
    ShapeCommand cmd = new RemoveShape("Tset");
    cmd.execute(model);
    assertEquals(0, model.getKeys().size());
  }
}