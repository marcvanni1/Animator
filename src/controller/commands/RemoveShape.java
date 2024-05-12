package controller.commands;

import controller.ShapeCommand;
import model.IAnimatorModel;

/**
 * A class to represent the action of removing a shape from an animation.
 */
public class RemoveShape implements ShapeCommand {
  private final String s;

  /**
   * Constructor for RemoveShape that takes in the name of the shape to remove.
   *
   * @param s the name of the shape to be removed
   * @throws IllegalArgumentException if s is null
   */
  public RemoveShape(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    this.s = s;
  }

  @Override
  public void execute(IAnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("args contained a null");
    }
    model.removeShape(s);
  }
}
