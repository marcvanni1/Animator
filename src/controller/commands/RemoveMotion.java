package controller.commands;

import controller.ShapeCommand;
import model.IAnimatorModel;
import model.IMotion;

/**
 * A class to represent the action of removing a motion from the animation.
 */
public class RemoveMotion implements ShapeCommand {
  private final String s;
  private final int i;
  private IMotion m;

  /**
   * Constructor for RemoveMotion that takes in a name of a shape and the index of the motion to
   * remove.
   *
   * @param s the name of the shape to remove a motion from
   * @param i the index of the motion to remove within the shape's motions
   * @throws IllegalArgumentException if s is null
   */
  public RemoveMotion(String s, int i) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    this.s = s;
    this.i = i;
  }

  @Override
  public void execute(IAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    for (IMotion m : model.getMotions(s)) {
      if (m.getTick() == i) {
        model.removeMotion(s, m);
        return;
      }
    }
    throw new IllegalArgumentException("Improper time");
  }
}
