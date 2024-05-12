package controller.commands;

import controller.ShapeCommand;
import model.IAnimatorModel;

/**
 * A class to represent the action of adding a shape to the animation.
 */
public class AddShape implements ShapeCommand {
  private final String s;


  /**
   * Constructor for AddShape that takes in the name of the new shape to add.
   *
   * @param s the type and name of the shape that is being added
   * @throws IllegalArgumentException if s is null
   */
  public AddShape(String s) throws IllegalArgumentException {
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
    model.addShape(s);
  }
}
