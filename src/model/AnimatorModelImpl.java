package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.AnimationBuilder;

/**
 * A class that represents the model of the animator. Holds the shapes with their associated motions
 * and shape types.
 */
public final class AnimatorModelImpl implements IAnimatorModel, IAnimatorModelView {

  private final Map<String, List<IMotion>> animations;
  private final Map<String, ShapeType> shapeTypes;
  private int xBound;
  private int yBound;
  private int width;
  private int height;

  /**
   * Constructs a model that initializes the map of motions and map of shape types to an empty map.
   */
  private AnimatorModelImpl() {
    this.animations = new LinkedHashMap<String, List<IMotion>>();
    this.shapeTypes = new LinkedHashMap<String, ShapeType>();
    this.xBound = 0;
    this.yBound = 0;
    this.width = 500;
    this.height = 500;
  }

  // Orders the motions in the animatable shape by tick.
  private static void orderMotions(List<IMotion> motions) {
    int size = motions.size();
    for (int i = 0; i < size - 1; i++) {
      int min = i;
      for (int j = i + 1; j < size; j++) {
        if (motions.get(j).getTick() < motions.get(min).getTick()) {
          min = j;
        }
      }
      IMotion temp = motions.get(min);
      motions.set(min, motions.get(i));
      motions.set(i, temp);
    }
  }

  // Throws an Illegal Argument Exception if any motions overlap
  private static void checkOverlap(List<IMotion> motions) throws IllegalArgumentException {
    int size = motions.size();
    if (motions.size() > 1) {
      for (int i = 0; i < size - 1; i++) {
        for (int j = i + 1; j < size; j++) {
          if ((motions.get(i).getTick() == motions.get(j).getTick() &&
                  motions.get(i).move(motions.get(j))) ||
                  (motions.get(i).getTick() == motions.get(j).getTick() &&
                          motions.get(i).changeSize(motions.get(j))) ||
                  (motions.get(i).getTick() == motions.get(j).getTick() &&
                          motions.get(i).changeColor(motions.get(j)))) {
            throw new IllegalArgumentException("Overlapping motions!");
          }
        }
      }
    }
  }

  @Override
  public void addMotion(String key, IMotion m) throws IllegalArgumentException {
    if (m == null || key == null) {
      throw new IllegalArgumentException("passed in a null!");
    }
    if (this.animations.containsKey(key)) {
      if (!(this.animations.get(key).contains(m))) {
        this.animations.get(key).add(m);
        orderMotions(this.animations.get(key));
        checkOverlap(this.animations.get(key));
      }
    }
  }

  @Override
  public void addShape(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("passed in a null!");
    }
    String key = s.substring(0, s.indexOf(' '));
    String val = s.substring(s.indexOf(' ') + 1);
    this.animations.put(key, new ArrayList<IMotion>());
    switch (val.toLowerCase()) {
      case "rectangle":
        this.shapeTypes.put(key, ShapeType.RECTANGLE);
        break;
      case "ellipse":
        this.shapeTypes.put(key, ShapeType.ELLIPSE);
        break;
      case "circle":
        this.shapeTypes.put(key, ShapeType.CIRCLE);
        break;
      default:
        throw new IllegalArgumentException("Unsupported shape type!");
    }
  }

  @Override
  public void removeShape(String s) {
    if (s == null) {
      throw new IllegalArgumentException("null string");
    }
    this.animations.remove(s);
    this.shapeTypes.remove(s);
  }

  @Override
  public void removeMotion(String s, IMotion m) throws IllegalArgumentException {
    if (s == null || m == null) {
      throw new IllegalArgumentException("null parameter!");
    }
    if (this.animations.containsKey(s)) {
      if (this.animations.get(s).contains(m)) {
        this.animations.get(s).remove(m);
      }
    }
  }

  @Override
  public void setX(int x) throws IllegalArgumentException {
    this.xBound = x;
  }

  @Override
  public void setY(int y) throws IllegalArgumentException {
    this.yBound = y;
  }

  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    if (width < 1) {
      throw new IllegalArgumentException("width is not positive");
    }
    this.width = width;
  }

  @Override
  public void setHeight(int height) throws IllegalArgumentException {
    if (height < 1) {
      throw new IllegalArgumentException("height is not positive");
    }
    this.height = height;
  }

  @Override
  public List<IMotion> getMotions(String key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException("passed in a null!");
    }
    List<IMotion> copy = new ArrayList<IMotion>();
    for (int i = 0; i < this.animations.getOrDefault(key, new ArrayList<IMotion>()).size(); i++) {
      copy.add(this.animations.get(key).get(i).copy());
    }
    return copy;
  }

  @Override
  public ShapeType getShapeType(String s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("passed in a null!");
    }
    if (shapeTypes.containsKey(s)) {
      return this.shapeTypes.get(s);
    }
    throw new IllegalArgumentException("shape isn't in the map");
  }

  @Override
  public Set<String> getKeys() {
    return new LinkedHashSet<String>(this.animations.keySet());
  }

  @Override
  public int getLastMotionTick() {
    int maxTick = 0;
    for (String s : this.animations.keySet()) {
      for (IMotion i : this.animations.get(s)) {
        maxTick = Math.max(maxTick, i.getTick());
      }
    }
    return maxTick;
  }

  @Override
  public int getXBound() {
    return this.xBound;
  }

  @Override
  public int getYBound() {
    return this.yBound;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Creates a new Builder object.
   *
   * @return a new Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * A class that represents a builder for the animator model. Used to create a model while keeping
   * the model's constructor private.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel> {

    private final IAnimatorModel model;

    public Builder() {
      model = new AnimatorModelImpl();
    }

    @Override
    public IAnimatorModel build() {
      return this.model;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> setBounds(int x, int y, int width, int height) {
      this.model.setX(x);
      this.model.setY(y);
      this.model.setWidth(width);
      this.model.setHeight(height);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> declareShape(String name, String type) {
      if (name == null || type == null) {
        throw new IllegalArgumentException("null string");
      }
      this.model.addShape(name + " " + type);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                      int h1, int r1, int g1, int b1, int t2,
                                                      int x2, int y2, int w2, int h2, int r2,
                                                      int g2, int b2) {
      if (name == null) {
        throw new IllegalArgumentException("null string");
      }
      IMotion m1 = new Motion(t1, x1, y1, w1, h1, r1, g1, b1);
      IMotion m2 = new Motion(t2, x2, y2, w2, h2, r2, g2, b2);
      this.model.addMotion(name, m1);
      this.model.addMotion(name, m2);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addKeyframe(String name, int t, int x, int y, int w,
                                                        int h, int r, int g, int b) {
      if (name == null) {
        throw new IllegalArgumentException("null string");
      }
      IMotion m = new Motion(t, x, y, w, h, r, g, b);
      this.model.addMotion(name, m);
      return this;
    }
  }
}
