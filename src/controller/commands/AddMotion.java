package controller.commands;

import controller.ShapeCommand;
import model.IAnimatorModel;
import model.IMotion;
import model.Motion;

import java.util.List;

/**
 * A class to represent the action of adding a motion to the animation.
 */
public class AddMotion implements ShapeCommand {
  private final String s;
  private final IMotion m;

  /**
   * A constructor for AddMotion that takes in a shape name and the field to create a new motion.
   *
   * @param s      the name of the shape to add a motion to
   * @param fields the fields of the new motion to add
   * @throws IllegalArgumentException if s or fields is null
   */
  public AddMotion(String s, List<Integer> fields) throws IllegalArgumentException {
    if (s == null || fields == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    this.s = s;
    m = new Motion(fields.get(0), fields.get(1), fields.get(2), fields.get(3), fields.get(4),
            fields.get(5), fields.get(6), fields.get(7));
  }

  @Override
  public void execute(IAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    model.addMotion(s, m);
  }
}
