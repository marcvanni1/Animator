package model;


/**
 * This interface represents the operations offered by the model. There is currently one class that
 * implements it. The interface allows the user to add a motion, add a shape, get the motions a
 * specified shape has, get a specified shape's type, get the shapes, and interpret the model as a
 * String.
 */
public interface IAnimatorModel extends IAnimatorModelView {

  /**
   * Adds a motion to the list of motions for the given shape.
   *
   * @param s the shape to add the motion to
   * @param m the motion to be added
   * @throws IllegalArgumentException if s or m are null
   */
  public void addMotion(String s, IMotion m) throws IllegalArgumentException;

  /**
   * Adds a new shape with no motions to the map of animations, and a new shape and its type to the
   * map of shape types.
   *
   * @param s the shape name and type to be added to the mappings
   * @throws IllegalArgumentException if s is null
   */
  public void addShape(String s) throws IllegalArgumentException;

  /**
   * Removes a shape from the model given the shape name.
   *
   * @param s the name of the shape that will be removed
   * @throws IllegalArgumentException if s is null
   */
  public void removeShape(String s) throws IllegalArgumentException;

  /**
   * Removes a motion from the given shape.
   *
   * @param s the name of the shape that will have the motion removed from it
   * @param m the motion that will be removed
   * @throws IllegalArgumentException if s or m is null
   */
  public void removeMotion(String s, IMotion m);

  /**
   * Sets the x coordinate for the canvas to desired number.
   *
   * @param x the desired x coord for the canvas
   * @throws IllegalArgumentException if x is less than 0
   */
  public void setX(int x) throws IllegalArgumentException;

  /**
   * Sets the y coordinate for the canvas to desired number.
   *
   * @param y the desired y coord for the canvas
   * @throws IllegalArgumentException if y is less than 0
   */
  public void setY(int y) throws IllegalArgumentException;

  /**
   * Sets the width for the canvas to desired number.
   *
   * @param width the desired width of the canvas
   * @throws IllegalArgumentException if width is not positive
   */
  public void setWidth(int width) throws IllegalArgumentException;

  /**
   * Sets the height for the canvas to desired number.
   *
   * @param height the desired width of the canvas
   * @throws IllegalArgumentException if height is not positive
   */
  public void setHeight(int height) throws IllegalArgumentException;
}
