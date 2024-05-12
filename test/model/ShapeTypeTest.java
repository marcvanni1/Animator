package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for ShapeType that ensures the toString() works as intended.
 */
public class ShapeTypeTest {

  @Test
  public void testShapeTypeToString() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    model.addShape("final circle");
    assertEquals("rectangle", model.getShapeType("test").toString());
    assertEquals("ellipse", model.getShapeType("another").toString());
    assertEquals("circle", model.getShapeType("final").toString());
  }
}