package view;

import model.IAnimatorModelView;
import model.IMotion;
import model.ShapeType;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

/**
 * A class to represent an SVG view form of the animator model.
 */
public class SvgView implements IView {

  private final IAnimatorModelView model;
  private final Set<String> keys;
  private final int ticksPerSec;
  private final Appendable output;

  /**
   * Constructor for SvgView, taking in a mode, a desire name for the output, and a desire rate for
   * the view to run at.
   *
   * @param model       an AnimatorModel to translate to the View
   * @param output      the appendable that the view will write to
   * @param ticksPerSec the ticks per second for the SvgView to run at
   * @throws IllegalArgumentException if model or output are null or if ticksPerSec isn't positive.
   */
  public SvgView(IAnimatorModelView model, Appendable output, int ticksPerSec)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("name or model is a null.");
    }
    if (output == null) {
      throw new IllegalArgumentException("output name is a null!");
    }
    if (ticksPerSec <= 0) {
      throw new IllegalArgumentException("rate must be positive");
    }
    this.model = model;
    this.keys = model.getKeys();
    this.ticksPerSec = ticksPerSec;
    this.output = output;
  }

  @Override
  public void draw() {
    try {
      this.output.append(fileText());
    } catch (IOException e) {
      System.out.println("Appending failed!");
    }
  }

  // Makes up the body of what's put in the file.
  private String fileText() {
    StringBuilder svgWrite = new StringBuilder();
    svgWrite.append(String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" " +
                    "xmlns=\"http://www.w3.org/2000/svg\">\n\n",
            model.getWidth(), model.getHeight()));
    for (String key : keys) {
      List<IMotion> motions = model.getMotions(key);
      String tag = shapeTag(key);
      if (motions.size() > 0) {
        IMotion motion = motions.get(0);
        svgWrite.append(String.format("<%s id=\"%s\" %s %s fill=%s visibility=\"visible\">\n",
                tag, key, shapePos(tag, motion), shapeDim(tag, motion), rgb(motion)));
        for (int j = 1; j < motions.size(); j++) {
          svgWrite.append(checkMove(tag, motions, j));
          svgWrite.append(checkColor(motions, j));
          svgWrite.append(checkSize(tag, motions, j));
        }
        svgWrite.append(String.format("%s\n", endTag(key)));
      }
    }
    svgWrite.append("</svg>");
    return svgWrite.toString();
  }

  // converts and formats an RGB color to a string.
  private String rgb(IMotion motion) {
    return String.format("\"rgb(%d,%d,%d)\"",
            motion.getRed(), motion.getGreen(), motion.getBlue());
  }

  // returns a tag for the drawn shape based on its shape type.
  private String shapeTag(String key) {
    if (model.getShapeType(key) == ShapeType.CIRCLE) {
      return "circle";
    } else if (model.getShapeType(key) == ShapeType.ELLIPSE) {
      return "ellipse";
    } else if (model.getShapeType(key) == ShapeType.RECTANGLE) {
      return "rect";
    } else {
      throw new IllegalArgumentException("Unsupported shape type");
    }
  }

  // provides the end tag for a shape in SVG form.
  private String endTag(String key) {
    return String.format("</%s>\n", shapeTag(key));
  }

  // converts and formats a shape's position to SVG form.
  private String shapePos(String tag, IMotion motion) {
    if (tag.equalsIgnoreCase("rect")) {
      return String.format("x=\"%d\" y=\"%d\"",
              motion.getX() - model.getXBound(), motion.getY() - model.getYBound());
    } else {
      return String.format("cx=\"%d\" cy=\"%d\"",
              motion.getX() - model.getXBound(), motion.getY() - model.getYBound());
    }
  }

  // converts and formats a shape's dimensions to SVG form.
  private String shapeDim(String tag, IMotion motion) {
    if (tag.equalsIgnoreCase("rect")) {
      return String.format("width=\"%d\" height=\"%d\"", motion.getWidth(), motion.getHeight());
    } else if (tag.equalsIgnoreCase("circle")) {
      return String.format("r=\"%.1f\"", (motion.getWidth() / 2.0));
    } else {
      return String.format("rx=\"%.1f\" ry=\"%.1f\"",
              (motion.getWidth() / 2.0), (motion.getWidth() / 2.0));
    }
  }

  // adds a move animation if a move has occurred between motions
  private String checkMove(String tag, List<IMotion> motions, int i) {
    IMotion lastMotion = motions.get(i - 1);
    IMotion motion = motions.get(i);
    StringBuilder ret = new StringBuilder();
    if (lastMotion.move(motion)) {
      if (lastMotion.getX() != motion.getX()) {
        if (tag.equalsIgnoreCase("rect")) {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"x\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getX() - model.getXBound(),
                  motion.getX() - model.getXBound()));
        } else {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"cx\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getX() - model.getXBound(),
                  motion.getX() - model.getXBound()));
        }
      }
      if (lastMotion.getY() != motion.getY()) {
        if (tag.equalsIgnoreCase("rect")) {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"y\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getY() - model.getYBound(),
                  motion.getY() - model.getYBound()));
        } else {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"cy\"" +
                          " from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getY() - model.getYBound(),
                  motion.getY() - model.getYBound()));
        }
      }
    }
    return ret.toString();
  }

  // adds a RGB color animation if a color change has occurred between motions
  private String checkColor(List<IMotion> motions, int i) {
    IMotion lastMotion = motions.get(i - 1);
    IMotion motion = motions.get(i);
    StringBuilder ret = new StringBuilder();
    if (lastMotion.changeColor(motion)) {
      ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"fill\" " +
                      "from=%s to=%s fill=\"freeze\" />\n",
              shapeTicks(motions, i), rgb(lastMotion), rgb(motion)));
    }
    return ret.toString();
  }

  // adds a grow/shrink animation if a size change has occurred between motions
  private String checkSize(String tag, List<IMotion> motions, int i) {
    IMotion lastMotion = motions.get(i - 1);
    IMotion motion = motions.get(i);
    StringBuilder ret = new StringBuilder();
    if (lastMotion.changeSize(motion)) {
      if (lastMotion.getWidth() != motion.getWidth()) {
        if (tag.equalsIgnoreCase("rect")) {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"width\" " +
                          "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getWidth(), motion.getWidth()));
        } else if (tag.equalsIgnoreCase("ellipse")) {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"rx\" " +
                          "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getWidth(), motion.getWidth()));
        } else {
          ret.append(String.format("<animate attributeType=\"xml\" %s attributeName=\"r\" " +
                          "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getWidth(), motion.getWidth()));
        }
      }
      if (lastMotion.getHeight() != motion.getHeight()) {
        if (tag.equalsIgnoreCase("rect")) {
          ret.append(String.format("<animate attributeType=\"xml\" %s " +
                          "attributeName=\"height\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getWidth(), motion.getWidth()));
        } else {
          ret.append(String.format("<animate attributeType=\"xml\" %s " +
                          "attributeName=\"ry\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                  shapeTicks(motions, i), lastMotion.getWidth(), motion.getWidth()));
        }
      }
    }
    return ret.toString();
  }

  // converts and formats the ticks of the shape to SVG form
  private String shapeTicks(List<IMotion> motions, int i) {
    IMotion lastMotion = motions.get(i - 1);
    IMotion motion = motions.get(i);
    return (String.format("begin=\"%dms\" dur=\"%dms\"",
            (lastMotion.getTick() * (1000 / this.ticksPerSec)),
            ((motion.getTick() - lastMotion.getTick()) * (1000 / this.ticksPerSec))));
  }

  @Override
  public void setListener(ActionListener listener) {
    // Set listener is not supported but is used in the controller.
  }

  @Override
  public String getEditCommand() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Timer getTimer() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getTicksPerSecond() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTicksPerSecond(int tps) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTick(int tick) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLoop(boolean loop) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setStarted(boolean started) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean getStarted() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void errorMessage(String error) {
    throw new UnsupportedOperationException();
  }
}
