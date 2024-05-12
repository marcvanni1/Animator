package model;

import java.util.List;
import java.util.Set;

/**
 * This interface represents the operations offered by the model. There is currently one class that
 * implements it. It only allows the retrieval of information from the model. The interface allows
 * the user to get the motions a specified shape has, get a specified shape's type, get the shapes,
 * and interpret the model as a String.
 */
public interface IAnimatorModelView {

  /**
   * Returns a copy of the motions for a given shape.
   *
   * @param s the shape to get the motions of
   * @return a copy of the motions of the shape
   */
  public List<IMotion> getMotions(String s) throws IllegalArgumentException;

  /**
   * Returns the type of shape associated with the key.
   *
   * @param s the shape to get the motions of
   * @return the string associated with the key
   * @throws IllegalArgumentException if s is null or s isn't in the list of shapes
   */
  public ShapeType getShapeType(String s) throws IllegalArgumentException;

  /**
   * Returns a list of the names associated with each shape and animation.
   *
   * @return a list with the names of the model's shapes
   */
  public Set<String> getKeys();

  public int getLastMotionTick();

  /**
   * Get the model's X-bound.
   *
   * @return the x-bound of the model canvas
   */
  public int getXBound();

  /**
   * Get the model's Y-bound.
   *
   * @return the y-bound of the model canvas
   */
  public int getYBound();

  /**
   * Get the model's width.
   *
   * @return the width of the model canvas
   */
  public int getWidth();

  /**
   * Get the model's height.
   *
   * @return the height of the model canvas
   */
  public int getHeight();
}
