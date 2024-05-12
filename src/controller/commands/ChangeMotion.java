package controller.commands;

import controller.ShapeCommand;
import model.IAnimatorModel;
import model.IMotion;
import model.Motion;

import java.util.List;

/**
 * A class to represent the action of changing a motion in the animation.
 */
public class ChangeMotion implements ShapeCommand {
  private final String s;
  private final List<Integer> fields;
  private final IMotion motion;

  /**
   * Constructor for ChangeMotion that takes in the name of the shape, the index of the old motion,
   * and the fields to use for the new motion.
   *
   * @param s      the name of the shape to change a motion within
   * @param fields the fields to use to create the new shape
   * @throws IllegalArgumentException if s or fields is null
   */
  public ChangeMotion(String s, List<Integer> fields) throws IllegalArgumentException {
    if (s == null || fields == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    this.s = s;
    this.fields = fields;
    this.motion = new Motion(fields.get(0), fields.get(1), fields.get(2), fields.get(3),
            fields.get(4), fields.get(5), fields.get(6), fields.get(7));
  }

  @Override
  public void execute(IAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    for (IMotion m : model.getMotions(s)) {
      if (m.getTick() == fields.get(0)) {
        model.removeMotion(s, m);
        model.addMotion(s, motion);
        return;
      }
    }
    throw new IllegalArgumentException("invalid time");
  }
}
