package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the animator model that ensures the constructor and methods work as
 * intended.
 */
public class AnimatorModelImplTest {

  @Test
  public void testDefaultConstructor() {
    IAnimatorModel m = AnimatorModelImpl.builder().build();
    assertEquals(new LinkedHashSet<String>(), m.getKeys());
    assertEquals(new ArrayList<String>(), m.getMotions(""));
  }

  // Add motion tests.
  @Test
  public void testAddMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("poopy", m);
    assertEquals(new LinkedHashSet<String>(Arrays.asList("poopy")), model.getKeys());
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m.copy())), model.getMotions("poopy"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("poopy"));
  }

  @Test
  public void testAddMotionOrder() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(17, 10, 20, 20, 100, 0, 0, 255);
    IMotion m3 = new Motion(138, 10, 20, 20, 100, 0, 0, 255);
    IMotion m4 = new Motion(16, 10, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("poopy", m);
    model.addMotion("poopy", m2);
    model.addMotion("poopy", m3);
    model.addMotion("poopy", m4);
    assertEquals(new LinkedHashSet<String>(Arrays.asList("poopy")), model.getKeys());
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m.copy(), m4.copy(), m2.copy(), m3.copy())),
            model.getMotions("poopy"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("poopy"));
  }

  @Test
  public void testAddMotionInvalidKey() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(17, 10, 20, 20, 100, 0, 0, 255);
    IMotion m3 = new Motion(138, 10, 20, 20, 100, 0, 0, 255);
    IMotion m4 = new Motion(16, 10, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("s", m);
    model.addMotion("r", m2);
    model.addMotion("t", m3);
    model.addMotion("v", m4);
    assertEquals(new LinkedHashSet<String>(Arrays.asList("poopy")), model.getKeys());
    assertEquals(new ArrayList<IMotion>(), model.getMotions("poopy"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("poopy"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionNullMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("poopy rectangle");
    model.addMotion("poopy", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionNullString() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion(null, m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionOverlapPos() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 1, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("poopy", m);
    model.addMotion("poopy", m2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionOverlapColor() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 10, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("poopy", m);
    model.addMotion("poopy", m2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionOverlapDim() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 10, 0, 0, 255);
    model.addShape("poopy rectangle");
    model.addMotion("poopy", m);
    model.addMotion("poopy", m2);
  }

  // Add shape tests.
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddShape() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape(null);
  }

  @Test
  public void testAddShape() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    assertEquals(new LinkedHashSet<String>(Arrays.asList("loony")), model.getKeys());
    assertEquals(new ArrayList<IMotion>(), model.getMotions("loony"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("loony"));
  }

  @Test
  public void testAddShapeMultiple() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    model.addShape("franky ellipse");
    model.addShape("leralt circle");
    model.addShape("d circle");
    assertEquals(new LinkedHashSet<String>(Arrays.asList("loony", "franky", "leralt", "d")),
            model.getKeys());
    assertEquals(new ArrayList<IMotion>(), model.getMotions("loony"));
    assertEquals(new ArrayList<IMotion>(), model.getMotions("franky"));
    assertEquals(new ArrayList<IMotion>(), model.getMotions("leralt"));
    assertEquals(new ArrayList<IMotion>(), model.getMotions("d"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("loony"));
    assertEquals(ShapeType.ELLIPSE, model.getShapeType("franky"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("leralt"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("d"));
  }

  // Getter tests.
  @Test(expected = IllegalArgumentException.class)
  public void testGetMotionsNull() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.getMotions(null);
  }

  @Test
  public void testGetMotionsNone() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    assertEquals(new ArrayList<IMotion>(), model.getMotions(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeTypeNull() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.getShapeType(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetShapeTypeNone() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.getShapeType("");
  }

  @Test
  public void testRemoveShape() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    assertEquals(new LinkedHashSet<String>(Arrays.asList("test", "another")),
            model.getKeys());
    model.removeShape("test");
    assertEquals(new LinkedHashSet<String>(Arrays.asList("another")),
            model.getKeys());
    model.removeShape("another");
    assertEquals(new LinkedHashSet<String>(Arrays.asList()),
            model.getKeys());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullRemoveShape() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    model.removeShape(null);
  }

  @Test
  public void testRemoveMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    IMotion m1 = new Motion(1, 20, 30, 20, 30, 0, 0, 255);
    IMotion m2 = new Motion(10, 40, 60, 20, 30, 0, 0, 255);
    IMotion m3 = new Motion(1, 20, 30, 50, 80, 100, 0, 255);
    model.addMotion("test", m1);
    model.addMotion("test", m2);
    model.addMotion("another", m3);
    assertEquals(model.getMotions("test").size(), 2);
    assertEquals(model.getMotions("another").size(), 1);
    model.removeMotion("test", m2);
    model.removeMotion("another", m3);
    assertEquals(model.getMotions("test").size(), 1);
    assertEquals(model.getMotions("another").size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullStringRemoveMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    IMotion m1 = new Motion(1, 20, 30, 20, 30, 0, 0, 255);
    IMotion m2 = new Motion(10, 40, 60, 20, 30, 0, 0, 255);
    IMotion m3 = new Motion(1, 20, 30, 50, 80, 100, 0, 255);
    model.addMotion("test", m1);
    model.addMotion("test", m2);
    model.addMotion("another", m3);
    model.removeMotion(null, m1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMotionRemoveMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("test rectangle");
    model.addShape("another ellipse");
    IMotion m1 = new Motion(1, 20, 30, 20, 30, 0, 0, 255);
    IMotion m2 = new Motion(10, 40, 60, 20, 30, 0, 0, 255);
    IMotion m3 = new Motion(1, 20, 30, 50, 80, 100, 0, 255);
    model.addMotion("test", m1);
    model.addMotion("test", m2);
    model.addMotion("another", m3);
    model.removeMotion("test", null);
  }

  @Test
  public void testSetX() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.setX(10);
    assertEquals(model.getXBound(), 10);
    model.setX(-10);
    assertEquals(model.getXBound(), -10);
  }

  @Test
  public void testSetY() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.setY(20);
    assertEquals(model.getYBound(), 20);
    model.setY(-20);
    assertEquals(model.getYBound(), -20);
  }

  @Test
  public void testSetWidth() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.setWidth(100);
    assertEquals(model.getWidth(), 100);
  }

  @Test
  public void testSetHeight() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.setHeight(1000);
    assertEquals(model.getHeight(), 1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidDimension() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.setWidth(0);
  }

  @Test
  public void testModel() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    model.addShape("franky ellipse");
    model.addShape("leralt circle");
    model.addShape("d cIrcle");
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(17, 10, 20, 20, 100, 0, 0, 255);
    IMotion m3 = new Motion(138, 10, 20, 20, 100, 0, 0, 255);
    IMotion m4 = new Motion(16, 10, 20, 20, 100, 0, 0, 255);
    model.addMotion("loony", m);
    model.addMotion("loony", m2);
    model.addMotion("loony", m4);
    model.addMotion("franky", m);
    model.addMotion("franky", m2);
    model.addMotion("franky", m3);
    model.addMotion("leralt", m4);
    assertEquals(new LinkedHashSet<String>(Arrays.asList("loony", "franky", "leralt", "d")),
            model.getKeys());
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m, m4, m2)), model.getMotions("loony"));
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m, m2, m3)), model.getMotions("franky"));
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m4)), model.getMotions("leralt"));
    assertEquals(new ArrayList<IMotion>(), model.getMotions("d"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("loony"));
    assertEquals(ShapeType.ELLIPSE, model.getShapeType("franky"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("leralt"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("d"));
  }
}