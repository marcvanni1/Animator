package view;

import org.junit.Test;

import model.AnimatorModelImpl;
import model.IAnimatorModel;
import model.IMotion;
import model.Motion;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the textual view that ensures the constructor and methods work as
 * intended.
 */
public class TextualViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testTextualNullModel() {
    IView view = new TextualView(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualNullName() {
    IView view = new TextualView(AnimatorModelImpl.builder().build(), null);
  }

  @Test
  public void testMakeFileNoShape() {
    StringBuilder contents = new StringBuilder();
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    new TextualView(model, contents).draw();
    assertEquals("Canvas 0 0 500 500\n", contents.toString());
  }

  @Test
  public void testMakeFileOneMotion() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    model.addMotion("loony", m);
    StringBuilder contents = new StringBuilder();
    new TextualView(model, contents).draw();
    assertEquals("Canvas 0 0 500 500\n" +
                    "Shape loony rectangle\n" +
                    "motion loony\t1\t10\t20\t20\t100\t0\t0\t255\n",
            contents.toString());
  }

  @Test
  public void testToString() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    model.addShape("franky circle");
    model.addShape("leralt ellipse");
    model.addShape("d circle");
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
    StringBuilder contents = new StringBuilder();
    new TextualView(model, contents).draw();
    assertEquals("Canvas 0 0 500 500\n" +
            "Shape loony rectangle\n" +
            "motion loony\t1\t10\t20\t20\t100\t0\t0\t255\t\t16\t10\t20\t20\t100\t0\t0\t255\n" +
            "motion loony\t16\t10\t20\t20\t100\t0\t0\t255\t\t17\t10\t20\t20\t100\t0\t0\t255\n" +
            "Shape franky circle\n" +
            "motion franky\t1\t10\t20\t20\t100\t0\t0\t255\t\t17\t10\t20\t20\t100\t0\t0\t255\n" +
            "motion franky\t17\t10\t20\t20\t100\t0\t0\t255\t\t138\t10\t20\t20\t100\t0\t0\t255\n" +
            "Shape leralt ellipse\n" +
            "motion leralt\t16\t10\t20\t20\t100\t0\t0\t255\n" +
            "Shape d circle\n", contents.toString());
  }
}