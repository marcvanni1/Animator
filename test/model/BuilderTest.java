package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import util.AnimationBuilder;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the Builder of the animator model that ensures the constructor and
 * methods work as intended.
 */
public class BuilderTest {
  @Test
  public void testBuilderConstructor() {
    IAnimatorModel m = AnimatorModelImpl.builder().build();
    assertEquals(new LinkedHashSet<String>(), m.getKeys());
    assertEquals(new ArrayList<String>(), m.getMotions(""));
    assertEquals(0, m.getXBound());
    assertEquals(0, m.getYBound());
    assertEquals(500, m.getWidth());
    assertEquals(500, m.getHeight());
  }

  @Test
  public void testBuilderChangeCanvas() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.setBounds(12, 15, 600, 700);
    IAnimatorModel m = build.build();
    assertEquals(12, m.getXBound());
    assertEquals(15, m.getYBound());
    assertEquals(600, m.getWidth());
    assertEquals(700, m.getHeight());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuilderChangeCanvasInvalidWidthZero() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.setBounds(0, 0, 0, 700);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuilderChangeCanvasInvalidWidthNegative() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.setBounds(0, 0, -10, 700);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuilderChangeCanvasInvalidHeightZero() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.setBounds(0, 0, 120, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuilderChangeCanvasInvalidHeightNegative() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.setBounds(0, 0, 10, -600);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddMotionNull() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    build.addMotion(null, 1, 10, 20, 20, 100, 0, 0, 255,
            1, 10, 20, 20, 10, 0, 0, 255);
  }

  @Test
  public void testAddMotion() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    IMotion m = new Motion(1, 10, 20, 20, 100, 10, 0, 255);
    IMotion m2 = new Motion(5, 10, 20, 20, 100, 10, 0, 0);
    build.addMotion("loony", 1, 10, 20, 20, 100, 10, 0, 255,
            5, 10, 20, 20, 100, 10, 0, 0);
    IAnimatorModel model = build.build();
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m, m2)), model.getMotions("loony"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddKeyframeNull() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    build.addKeyframe(null, 1, 10, 20, 20, 100, 0, 0, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidAddKeyframeOverlap() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    build.addKeyframe("loony", 1, 10, 20, 20, 100, 0, 0, 255);
    build.addKeyframe("loony", 1, 20, 20, 20, 100, 0, 0, 0);
  }

  @Test
  public void testAddKeyframe() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    IMotion m = new Motion(1, 10, 20, 20, 100, 10, 0, 255);
    build.addKeyframe("loony", 1, 10, 20, 20, 100, 10, 0, 255);
    IAnimatorModel model = build.build();
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m)), model.getMotions("loony"));
  }

  // Add shape tests.
  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNullName() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape(null, "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNullType() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("um yes", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeNullBoth() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeclareShapeInvalid() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("error", "rect");
  }

  @Test
  public void testDeclareShape() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    IAnimatorModel model = build.build();
    assertEquals(new LinkedHashSet<String>(Arrays.asList("loony")), model.getKeys());
    assertEquals(new ArrayList<IMotion>(), model.getMotions("loony"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("loony"));
  }

  @Test
  public void testDeclareShapeMultiple() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    build.declareShape("franky", "ellipse");
    build.declareShape("leralt", "circle");
    build.declareShape("d", "cIrcle");
    IAnimatorModel model = build.build();
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

  @Test
  public void testBuildModel() {
    AnimationBuilder<IAnimatorModel> build = AnimatorModelImpl.builder();
    build.declareShape("loony", "rectangle");
    build.declareShape("franky", "ellipse");
    build.declareShape("leralt", "circle");
    build.declareShape("d", "cIrcle");
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(17, 10, 20, 20, 100, 0, 0, 255);
    IMotion m3 = new Motion(138, 10, 20, 20, 100, 0, 0, 255);
    IMotion m4 = new Motion(16, 10, 20, 20, 100, 0, 0, 255);
    build.addKeyframe("loony", 1, 10, 20, 20, 100, 0, 0, 255);
    build.addMotion("loony", 17, 10, 20, 20, 100, 0, 0, 255,
            16, 10, 20, 20, 100, 0, 0, 255);
    build.addMotion("franky", 16, 10, 20, 20, 100, 0, 0, 255,
            1, 10, 20, 20, 100, 0, 0, 255);
    build.addMotion("franky", 17, 10, 20, 20, 100, 0, 0, 255,
            138, 10, 20, 20, 100, 0, 0, 255);
    build.addKeyframe("leralt", 16, 10, 20, 20, 100, 0, 0, 255);
    IAnimatorModel model = build.build();
    assertEquals(new LinkedHashSet<String>(Arrays.asList("loony", "franky", "leralt", "d")),
            model.getKeys());
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m, m4, m2)), model.getMotions("loony"));
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m, m4, m2, m3)),
            model.getMotions("franky"));
    assertEquals(new ArrayList<IMotion>(Arrays.asList(m4)), model.getMotions("leralt"));
    assertEquals(new ArrayList<IMotion>(), model.getMotions("d"));
    assertEquals(ShapeType.RECTANGLE, model.getShapeType("loony"));
    assertEquals(ShapeType.ELLIPSE, model.getShapeType("franky"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("leralt"));
    assertEquals(ShapeType.CIRCLE, model.getShapeType("d"));
  }
}
