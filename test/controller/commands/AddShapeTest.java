package controller.commands;

import org.junit.Test;

import controller.ShapeCommand;
import model.AnimatorModelImpl;
import model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Add Shape Command that ensures the constructor and execution of
 * the command work as intended.
 */
public class AddShapeTest {

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullString() {
    ShapeCommand cmd = new AddShape(null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullModel() {
    ShapeCommand cmd = new AddShape("Tset");
    cmd.execute(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    ShapeCommand cmd = new AddShape("Tset rectangle");
    cmd.execute(model);
    assertEquals(0, model.getMotions("Tset").size());
  }
}