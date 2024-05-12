package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import model.IAnimatorModelView;
import model.IMotion;
import model.Motion;

/**
 * A class to extend JPanel in order to draw the Shapes and Motions at the tick specified so the
 * Views can show them accurately.
 */
public class AnimatorPanel extends JPanel {
  private final IAnimatorModelView model;
  private Map<String, IMotion> currentFrames;
  private int tick;

  /**
   * A constructor for AnimatorPanel that takes in both a model and a rate for the animation.
   *
   * @param model the AnimatorModel for the AnimatorPanel to draw //   * @param tps   the user input
   *              tick rate to override the default tick rate
   * @param tick  the tick that the panel is using to draw the frames from the model
   * @throws IllegalArgumentException if model is null or tps is not positive
   */
  public AnimatorPanel(IAnimatorModelView model, int tick) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model is a null");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Tick is negative!");
    }
    this.model = model;
    this.currentFrames = new LinkedHashMap<String, IMotion>();
    this.setBackground(Color.WHITE);
    this.interpolateByTick();
    this.tick = tick;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    Set<String> names = this.currentFrames.keySet();
    for (String s : names) {
      IMotion frame = this.currentFrames.get(s);
      switch (model.getShapeType(s)) {
        case RECTANGLE:
          g2d.setColor(new Color(frame.getRed(), frame.getGreen(), frame.getBlue()));
          g2d.fillRect(frame.getX() - model.getXBound(),
                  frame.getY() - model.getYBound(), frame.getWidth(), frame.getHeight());
          break;
        case ELLIPSE:
          g2d.setColor(new Color(frame.getRed(), frame.getGreen(), frame.getBlue()));
          g2d.fillOval(frame.getX() - model.getXBound(),
                  frame.getY() - model.getYBound(), frame.getWidth(), frame.getHeight());
          break;
        case CIRCLE:
          g2d.setColor(new Color(frame.getRed(), frame.getGreen(), frame.getBlue()));
          g2d.fillOval(frame.getX() - model.getXBound(),
                  frame.getY() - model.getYBound(), frame.getWidth(), frame.getWidth());
          break;
        default:
          break;
      }
    }
  }

  // Populates currentFrames with the correct motions based on the tick the view is on.
  private void interpolateByTick() {
    currentFrames.clear();
    for (String key : model.getKeys()) {
      List<IMotion> motions = model.getMotions(key);
      if (motions.size() > 1) {
        for (int j = 1; j < motions.size(); j++) {
          IMotion thisFrame = motions.get(j);
          IMotion lastFrame = motions.get(j - 1);
          if (thisFrame.getTick() == tick) {
            currentFrames.put(key, thisFrame);
            break;
          } else if (lastFrame.getTick() == tick) {
            currentFrames.put(key, lastFrame);
            break;
          } else if (thisFrame.getTick() > tick && lastFrame.getTick() < tick) {
            IMotion frame = change(thisFrame, lastFrame);
            currentFrames.put(key, frame);
            break;
          }
        }
      } else if (motions.size() == 1) {
        if (motions.get(0).getTick() == tick) {
          currentFrames.put(key, motions.get(0));
        }
      }
    }
  }

  // interpolates the last motion based on the next motion and the current tick.
  private IMotion change(IMotion motion, IMotion lastMotion) {
    int x = lastMotion.getX();
    int y = lastMotion.getY();
    int w = lastMotion.getWidth();
    int h = lastMotion.getHeight();
    int r = lastMotion.getRed();
    int g = lastMotion.getGreen();
    int b = lastMotion.getBlue();
    if (motion.move(lastMotion)) {
      x = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getX(), motion.getX());
      y = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getY(), motion.getY());
    }
    if (motion.changeSize(lastMotion)) {
      w = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getWidth(), motion.getWidth());
      h = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getHeight(), motion.getHeight());
    }
    if (motion.changeColor(lastMotion)) {
      r = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getRed(), motion.getRed());
      g = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getGreen(), motion.getGreen());
      b = (int) this.interpolate(lastMotion.getTick(), motion.getTick(),
              lastMotion.getBlue(), motion.getBlue());
    }
    return new Motion(this.tick, x, y, w, h, r, g, b);
  }

  // the method containing the interpolation formula, briefly
  // casting the int variables to doubles.
  private double interpolate(int tai, int tbi, int ai, int bi) {
    double ta = tai;
    double tb = tbi;
    double a = ai;
    double b = bi;
    return (a * ((tb - this.tick) / (tb - ta))) + (b * ((this.tick - ta) / (tb - ta)));
  }

  /**
   * Sets the tick of the panel to the given tick and repopulates the map of current frames
   * corresponding to that tick.
   *
   * @param tick the tick the panel is being changed to
   */
  public void setFrames(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("tick negative");
    }
    this.tick = tick;
    interpolateByTick();
  }
}
