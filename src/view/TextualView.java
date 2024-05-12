package view;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

import model.IAnimatorModelView;
import model.IMotion;

/**
 * A class to represent a text-based view form of the animator model.
 */
public class TextualView implements IView {

  private final IAnimatorModelView model;
  private final Appendable output;

  /**
   * Constructor for a TextualView, taking in a model and a desired output.
   *
   * @param model  an AnimatorModel to translate to the View
   * @param output the appendable that the view writes to
   * @throws IllegalArgumentException if model or output are null
   */
  public TextualView(IAnimatorModelView model, Appendable output) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("model is a null!");
    }
    if (output == null) {
      throw new IllegalArgumentException("output name is a null!");
    }
    this.model = model;
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
    StringBuilder motionsString = new StringBuilder();
    Set<String> keys = this.model.getKeys();
    motionsString.append(String.format("Canvas %d %d %d %d\n", this.model.getXBound(),
            this.model.getYBound(), this.model.getWidth(), this.model.getHeight()));
    for (String key : keys) {
      List<IMotion> motions = model.getMotions(key);
      motionsString.append(String.format("Shape %s %s\n", key, model.getShapeType(key)));
      if (motions.size() == 1) {
        motionsString.append(String.format("motion %s\t%s\n",
                key, motionToString(motions.get(0))));
      }
      for (int j = 0; j < motions.size() - 1; j++) {
        motionsString.append(String.format("motion %s\t%s\t\t%s\n", key,
                motionToString(motions.get(j)), motionToString(motions.get(j + 1))));
      }
    }
    return motionsString.toString();
  }

  // converts and formats a motion into a string. Throws an IllegalArgumentException if motion null.
  private String motionToString(IMotion m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("null motion");
    }
    return String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d", m.getTick(), m.getX(), m.getY(),
            m.getWidth(), m.getHeight(), m.getRed(), m.getGreen(), m.getBlue());
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
