package view;

import org.junit.Test;

import model.AnimatorModelImpl;
import model.IAnimatorModel;
import model.IMotion;
import model.Motion;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for the SVG View that ensures the constructor and methods work as
 * intended.
 */
public class SvgViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testTextualNullModel() {
    IView view = new SvgView(null, new StringBuilder(), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualNullName() {
    IView view = new SvgView(AnimatorModelImpl.builder().build(), null, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualZeroTPS() {
    IView view = new SvgView(AnimatorModelImpl.builder().build(), new StringBuilder(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualNegativeTPS() {
    IView view = new SvgView(AnimatorModelImpl.builder().build(),
            new StringBuilder(), -10);
  }

  @Test
  public void testDrawInFileEmpty() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    StringBuilder contents = new StringBuilder();
    new SvgView(model, contents, 1).draw();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">\n\n</svg>", contents.toString());
  }

  @Test
  public void testDrawInFile() {
    IAnimatorModel model = AnimatorModelImpl.builder().build();
    model.addShape("loony rectangle");
    model.addShape("franky circle");
    model.addShape("leralt ellipse");
    model.addShape("d rectangle");
    IMotion m = new Motion(1, 400, 10, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(17, 200, 20, 20, 100, 0, 0, 255);
    IMotion m3 = new Motion(45, 200, 200, 20, 100, 0, 0, 255);
    IMotion m4 = new Motion(38, 400, 400, 20, 100, 0, 0, 255);
    model.addMotion("loony", m);
    model.addMotion("loony", m2);
    model.addMotion("loony", m4);
    model.addMotion("franky", m);
    model.addMotion("franky", m2);
    model.addMotion("franky", m3);
    model.addMotion("leralt", m4);
    StringBuilder contents = new StringBuilder();
    new SvgView(model, contents, 20).draw();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "\n" +
            "<rect id=\"loony\" x=\"400\" y=\"10\" width=\"20\" height=\"100\"" +
            " fill=\"rgb(0,0,255)\" visibility=\"visible\">\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"800ms\" " +
            "attributeName=\"x\" from=\"400\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"800ms\" " +
            "attributeName=\"y\" from=\"10\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"850ms\" dur=\"1050ms\" " +
            "attributeName=\"x\" from=\"200\" to=\"400\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"850ms\" dur=\"1050ms\" " +
            "attributeName=\"y\" from=\"20\" to=\"400\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "\n" +
            "<circle id=\"franky\" cx=\"400\" cy=\"10\" r=\"10.0\" " +
            "fill=\"rgb(0,0,255)\" visibility=\"visible\">\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"800ms\" " +
            "attributeName=\"cx\" from=\"400\" to=\"200\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"50ms\" dur=\"800ms\" " +
            "attributeName=\"cy\" from=\"10\" to=\"20\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"850ms\" dur=\"1400ms\" " +
            "attributeName=\"cy\" from=\"20\" to=\"200\" fill=\"freeze\" />\n" +
            "</circle>\n" +
            "\n" +
            "<ellipse id=\"leralt\" cx=\"400\" cy=\"400\" rx=\"10.0\" ry=\"10.0\" " +
            "fill=\"rgb(0,0,255)\" visibility=\"visible\">\n" +
            "</ellipse>\n" +
            "\n" +
            "</svg>", contents.toString());
  }
}