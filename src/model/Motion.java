package model;

import java.util.Objects;

/**
 * A class to represent a motion, including the properties of the shape for when the motion
 * finishes.
 */
public class Motion implements IMotion {

  private final int tick;
  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a motion out of properties provided entirely by user input.
   *
   * @param tick   the starting tick of the motion
   * @param x      the x-coordinate of the shape's center
   * @param y      the y-coordinate of the shape's center
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param red    the red RGB value of the shape
   * @param green  the green RGB value of the shape
   * @param blue   the blue RGB value of the shape
   * @throws IllegalArgumentException if red, green, or blue are outside 0 - 255 range, if height or
   *                                  width are not positive, or if tick is negative
   */
  public Motion(int tick, int x, int y, int width, int height, int red, int green, int blue)
          throws IllegalArgumentException {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException("Not a valid color value.");
    }
    if (height < 1 || width < 1) {
      throw new IllegalArgumentException("dimension is not positive");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("tick is negative!");
    }
    this.tick = tick;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public IMotion copy() {
    return new Motion(this.tick, this.x, this.y, this.width, this.height, this.red, this.green,
            this.blue);
  }

  @Override
  public boolean move(IMotion m) {
    motionNull(m);
    return (this.x != m.getX() || this.y != m.getY());
  }

  @Override
  public boolean changeColor(IMotion m) {
    motionNull(m);
    return (this.red != m.getRed() || this.green != m.getGreen() || this.blue != m.getBlue());
  }

  @Override
  public boolean changeSize(IMotion m) {
    motionNull(m);
    return (this.width != m.getWidth() || this.height != m.getHeight());
  }

  // throws an IllegalArgumentException if the motion is null.
  private static void motionNull(IMotion m) throws IllegalArgumentException {
    if (m == null) {
      throw new IllegalArgumentException("Motion was a null!");
    }
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Motion)) {
      return false;
    }

    Motion thatMotion = (Motion) that;

    return (this.tick == thatMotion.tick && this.x == thatMotion.x && this.y == thatMotion.y &&
            this.width == thatMotion.width && this.height == thatMotion.height &&
            this.red == thatMotion.red && this.green == thatMotion.green &&
            this.blue == thatMotion.blue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tick, this.x, this.y, this.width, this.height,
            this.red, this.green, this.blue);
  }
}
