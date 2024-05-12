package controller;

import model.IAnimatorModel;

/**
 * This interface represents the only function of a command from the view. They take in fields and
 * call methods from the model that use those fields. Execute() is just a common method used by the
 * classes.
 */
public interface ShapeCommand {

  /**
   * Run the current ShapeCommand on the given model.
   *
   * @param model the model to alter with the current ShapeCommand
   * @throws IllegalArgumentException if model is null
   */
  void execute(IAnimatorModel model) throws IllegalArgumentException;
}
