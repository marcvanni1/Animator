package controller.commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import controller.ShapeCommand;
import model.AnimatorModelImpl;
import model.IAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Add Motion Command that ensures the constructor and execution of
 * the command work as intended.
 */
public class AddMotionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullString() {
    ShapeCommand cmd = new AddMotion(null, new ArrayList<Integer>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullList() {
    ShapeCommand cmd = new AddMotion("Tset", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullModel() {
    ShapeCommand cmd = new AddMotion("Tset",
            new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
    cmd.execute(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().declareShape("Tset",
            "ellipse").build();
    ShapeCommand cmd = new AddMotion("Tset",
            new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
    cmd.execute(model);
    assertEquals(1, model.getMotions("Tset").size());
  }
}