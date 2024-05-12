package model;

/**
 * An enum representing shape types of Rectangle, Circle, and Ellipse.
 */
public enum ShapeType {
  RECTANGLE, CIRCLE, ELLIPSE;

  @Override
  public String toString() {
    switch (this) {
      case RECTANGLE:
        return "rectangle";
      case CIRCLE:
        return "circle";
      case ELLIPSE:
        return "ellipse";
      default:
        return "";
    }
  }
}
