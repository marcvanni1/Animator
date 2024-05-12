package controller.commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import controller.ShapeCommand;
import model.AnimatorModelImpl;
import model.IAnimatorModel;
import model.IMotion;
import model.Motion;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Change Motion Command that ensures the constructor and execution
 * of the command work as intended.
 */
public class ChangeMotionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullString() {
    ShapeCommand cmd = new ChangeMotion(null, new ArrayList<Integer>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullList() {
    ShapeCommand cmd = new ChangeMotion("Tset", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCommandNullModel() {
    ShapeCommand cmd = new ChangeMotion("Tset",
            new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
    cmd.execute(null);
  }

  @Test
  public void testControllerStart() {
    IAnimatorModel model = AnimatorModelImpl.builder().declareShape("Tset",
            "ellipse").build();
    IMotion m1 = new Motion(1, 2, 3, 4, 5, 6, 7, 8);
    model.addMotion("Tset", m1);
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m1)), model.getMotions("Tset"));
    IMotion m2 = new Motion(1, 3, 4, 5, 6, 7, 8, 9);
    ShapeCommand cmd = new ChangeMotion("Tset",
            new ArrayList<Integer>(Arrays.asList(1, 3, 4, 5, 6, 7, 8, 9)));
    cmd.execute(model);
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m2)), model.getMotions("Tset"));
  }
}