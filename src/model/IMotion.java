package model;

/**
 * This interface represents the operations offered by a motion. They hold properties of where a
 * shape is at at what time and can have this information retrieved.
 */
public interface IMotion {

  /**
   * Gets the tick this motion occurs at.
   *
   * @return the the tick field of the motion object
   */
  public int getTick();

  /**
   * Gets the x-coordinate of the shape making the motion.
   *
   * @return the x field of the motion object
   */
  public int getX();

  /**
   * Gets the y-coordinate of the shape making the motion.
   *
   * @return the y field of the motion object
   */
  public int getY();

  /**
   * Gets the width of the shape making the motion.
   *
   * @return the width field of the motion object
   */
  public int getWidth();

  /**
   * Gets the height of the shape making the motion.
   *
   * @return the height field of the motion object
   */
  public int getHeight();

  /**
   * Gets the red RGB value of the shape making the motion.
   *
   * @return the red field of the motion object
   */
  public int getRed();

  /**
   * Gets the green RGB value of the shape making the motion.
   *
   * @return the green field of the motion object
   */
  public int getGreen();

  /**
   * Gets the blue RGB value of the shape making the motion.
   *
   * @return the blue field of the motion object
   */
  public int getBlue();

  /**
   * Creates a copy of the motion.
   *
   * @return an IMotion with the same properties as the current motion
   */
  public IMotion copy();

  /**
   * Checks if two motions occur at the same time and have different positions.
   *
   * @return true if the motions occur at the same time and have different positions.
   */
  public boolean move(IMotion m);

  /**
   * Checks if two motions have different colors.
   *
   * @return true if the motions have different colors.
   */
  public boolean changeColor(IMotion m);

  /**
   * Checks if two motions have different sizes.
   *
   * @return true if the motions have different sizes.
   */
  public boolean changeSize(IMotion m);

  @Override
  public boolean equals(Object that);

  @Override
  public int hashCode();
}
