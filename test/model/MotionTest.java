package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Represents a test class for Motion that ensures the constructor and methods work as intended.
 */
public class MotionTest {

  @Test(expected = IllegalArgumentException.class)
  public void NegativeTickMotion() {
    IMotion m = new Motion(-1, 10, 20, 20, 10, 10, 0, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeRedMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, -5, 0, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRedMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, 256, 0, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeGreenMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, 0, -156, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidGreenMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, 0, 256, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeBlueMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, 0, 0, -300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidBlueMotion() {
    IMotion m = new Motion(1, 10, 20, 20, 10, 0, 0, 300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidWidthMotion() {
    IMotion m = new Motion(1, 10, 20, 0, 10, 0, 0, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHeightMotion() {
    IMotion m = new Motion(1, 10, 20, 20, -10, 0, 0, 255);
  }

  @Test
  public void testGetters() {
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    assertEquals(1, m.getTick());
    assertEquals(10, m.getX());
    assertEquals(20, m.getY());
    assertEquals(20, m.getWidth());
    assertEquals(100, m.getHeight());
    assertEquals(0, m.getRed());
    assertEquals(0, m.getGreen());
    assertEquals(255, m.getBlue());
  }

  @Test
  public void testCopy() {
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = m.copy();
    assertEquals(1, m2.getTick());
    assertEquals(10, m2.getX());
    assertEquals(20, m2.getY());
    assertEquals(20, m2.getWidth());
    assertEquals(100, m2.getHeight());
    assertEquals(0, m2.getRed());
    assertEquals(0, m2.getGreen());
    assertEquals(255, m2.getBlue());
  }

  @Test
  public void testMoveTrueX() {
    IMotion m = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(5, 0, 20, 20, 100, 0, 0, 255);
    assertEquals(true, m.move(m2));
  }

  @Test
  public void testMoveTrueY() {
    IMotion m = new Motion(1, 10, 2, 20, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    assertEquals(true, m.move(m2));
  }

  @Test
  public void testMoveFalse() {
    IMotion m = new Motion(1, 10, 20, 2, 1000, 10, 10, 25);
    IMotion m2 = new Motion(10, 10, 20, 20, 100, 0, 0, 255);
    assertEquals(false, m.move(m2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNull() {
    IMotion m = new Motion(1, 10, 2, 20, 100, 0, 0, 255);
    m.move(null);
  }

  @Test
  public void testChangeSizeTrueWidth() {
    IMotion m = new Motion(1, 10, 20, 10, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    assertEquals(true, m.changeSize(m2));
  }

  @Test
  public void testChangeSizeFalse() {
    IMotion m = new Motion(1, 0, 2, 20, 100, 20, 30, 255);
    IMotion m2 = new Motion(11, 10, 20, 20, 100, 0, 0, 25);
    assertEquals(false, m.changeSize(m2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNull() {
    IMotion m = new Motion(1, 10, 2, 20, 100, 0, 0, 255);
    m.changeSize(null);
  }

  @Test
  public void testChangeColorTrueRed() {
    IMotion m = new Motion(1, 10, 20, 10, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 10, 0, 255);
    assertEquals(true, m.changeColor(m2));
  }

  @Test
  public void testChangeColorTrueGreen() {
    IMotion m = new Motion(1, 10, 20, 10, 100, 0, 0, 255);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 0, 10, 255);
    assertEquals(true, m.changeColor(m2));
  }

  @Test
  public void testChangeColorTrueBlue() {
    IMotion m = new Motion(1, 10, 20, 10, 100, 0, 0, 200);
    IMotion m2 = new Motion(1, 10, 20, 20, 100, 0, 0, 255);
    assertEquals(true, m.changeColor(m2));
  }

  @Test
  public void testOverlapColorFalse() {
    IMotion m = new Motion(1, 10, 200, 20, 100, 0, 0, 255);
    IMotion m2 = m.copy();
    assertEquals(false, m.changeColor(m2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNull() {
    IMotion m = new Motion(1, 10, 2, 20, 100, 0, 0, 255);
    m.changeSize(null);
  }
}