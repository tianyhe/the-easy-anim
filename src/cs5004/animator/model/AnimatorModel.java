package cs5004.animator.model;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.IShape;
import cs5004.animator.model.shapeutil.Location;
import cs5004.animator.model.shapeutil.Oval;
import cs5004.animator.model.shapeutil.Rectangle;
import cs5004.animator.util.AnimationBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A class implementing the IAnimatorModel interface that represents the model of an animator to
 * create an animation. The model contains a list of animated shape where each shape owns a list of
 * keyframes and a list of motions. The model plays the animation by applying the motions at the
 * given tick to the shapes.
 */
public class AnimatorModel implements IAnimatorModel {

  private static int DEFAULT_WIDTH = 500;
  private static int DEFAULT_HEIGHT = 500;
  private static int DEFAULT_XPOS = 200;
  private static int DEFAULT_YPOS = 200;
  private int tick = 1;

  private final List<IAnimatedShape> shapes;

  /**
   * Constructs an empty animator model and initializes the lists of animated shapes.
   */
  public AnimatorModel() {
    this.shapes = new ArrayList<>();
  }

  /**
   * Constructor for the animator model with custom canvas size that takes in a list of animated
   * shapes.
   */
  public AnimatorModel(int width, int height, int x, int y, List<IAnimatedShape> shapes) {
    this.shapes = shapes;
    DEFAULT_WIDTH = width;
    DEFAULT_HEIGHT = height;
    DEFAULT_XPOS = x;
    DEFAULT_YPOS = y;
  }

  /**
   * Builder for the animator model that implements the AnimationBuilder interface.
   */
  public static final class Builder implements AnimationBuilder<IAnimatorModel> {

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int x = DEFAULT_XPOS;
    private int y = DEFAULT_YPOS;
    List<IAnimatedShape> shapes = new ArrayList<>();

    @Override
    public AnimatorModel build() {
      return new AnimatorModel(width, height, x, y, shapes);
    }

    @Override
    public AnimationBuilder<IAnimatorModel> setBounds(int x, int y, int width, int height) {
      if (width < 0 || height < 0) {
        throw new IllegalArgumentException("Width and height must be positive");
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> declareShape(String name, String type) {
      IShape s = type.equals("ellipse") ? new Oval(name) : new Rectangle(name);
      shapes.add(new AnimatedShape(s));
      return this;
    }

    @Override
    public AnimationBuilder<IAnimatorModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {
      return addMotionHelper(name,
          new Motion(new Location(x1, y1), new Location(x2, y2), w1, w2, h1, h2,
              new Color(r1, g1, b1), new Color(r2, g2, b2), t1, t2));
    }

    private AnimationBuilder<IAnimatorModel> addMotionHelper(String name, Motion m) {
      if (m.getStartTick() == m.getEndTick() && !m.isTeleportedFrom(m)) {
        return this;
      }
      for (IAnimatedShape s : this.shapes) {
        if (s.getId().equals(name)) {
          s.addMotion(m);
        }
      }
      return this;
    }
  }

  @Override
  public void addMotion(String id, Motion motion) {
    Objects.requireNonNull(motion);
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    for (IAnimatedShape s : this.shapes) {
      if (id.equals(s.getId())) {
        s.addMotion(motion);
      }
    }
  }

  @Override
  public void addShape(IShape shape) {
    validateAddShape(shape);
    this.shapes.add(new AnimatedShape(shape));
  }

  /**
   * Ensures that the given shape does not already exist within the model.
   *
   * @param shape the shape being attempted to be added
   */
  private void validateAddShape(IShape shape) {
    Objects.requireNonNull(shape);
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(shape.getId())) {
        throw new IllegalArgumentException("Invalid ID: ID already exists.");
      }
    }
  }

  @Override
  public void applyTick(int tick) {
    this.tick = tick;
    for (IAnimatedShape shape : this.shapes) {
      shape.applyTick(tick);
    }
  }

  @Override
  public void addAnimatedShape(IAnimatedShape animatedShape) {
    if (animatedShape == null) {
      throw new IllegalArgumentException("Animated Shape cannot be null");
    }
    for (IAnimatedShape as : this.getAnimatedShapes()) {
      if (Objects.equals(as.getId(), animatedShape.getId())) {
        throw new IllegalArgumentException("Can't add a duplicate shape");
      }
    }
    this.shapes.add(animatedShape);
  }

  @Override
  public void clear() {
    this.shapes.removeAll(shapes);
  }

  @Override
  public List<IShape> getShapes() {
    List<IShape> shapes = new ArrayList<>();
    for (IAnimatedShape s : this.shapes) {
      shapes.add(s.getShape().copy());
    }
    return shapes;
  }

  @Override
  public IShape getShape(String id) {
    for (IAnimatedShape shape : this.shapes) {
      if (shape.getId().equals(id)) {
        return shape.getShape();
      }
    }
    throw new IllegalArgumentException("No shape with id=" + id + " exists");
  }

  @Override
  public IShape getShapeAt(String id, int tick) {
    for (IAnimatedShape s : this.shapes) {
      if (s.getId().equals(id)) {
        return s.getShapeAt(tick);
      }
    }
    throw new IllegalArgumentException("No such shape exists at tick=" + tick);
  }

  @Override
  public List<IShape> getShapesAt(int tick) {
    List<IShape> shapes = new ArrayList<>();
    for (IAnimatedShape shape : this.shapes) {
      shapes.add(shape.getShapeAt(tick));
    }
    return shapes;
  }

  @Override
  public List<IAnimatedShape> getAnimatedShapes() {
    List<IAnimatedShape> copy = new ArrayList<>();
    for (IAnimatedShape s : this.shapes) {
      copy.add(s.copy());
    }
    return copy;
  }

  @Override
  public int getLastTick() {
    int tick = 1;
    for (IAnimatedShape s : this.shapes) {
      tick = Math.max(s.getLastTick(), tick);
    }
    return tick;
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public int getCanvasWidth() {
    return DEFAULT_WIDTH;
  }

  @Override
  public int getCanvasHeight() {
    return DEFAULT_HEIGHT;
  }

  @Override
  public int getCanvasStartingX() {
    return DEFAULT_XPOS;
  }

  @Override
  public int getCanvasStartingY() {
    return DEFAULT_YPOS;
  }

  @Override
  public String toString() {
    List<String> createShapeStrings = new ArrayList<>();
    List<String> motionStrings = new ArrayList<>();
    for (IAnimatedShape s : this.shapes) {
      createShapeStrings.add("Create " + s.getShape().getShapeAsString().toLowerCase()
          + " named \"" + s.getShape().getId() + "\"");
      motionStrings.add(s.toString());
    }
    String actions = String.join("\n\n", motionStrings);
    String creations = String.join("\n", createShapeStrings);
    return String.join("\n\n", new ArrayList<>(Arrays.asList(creations, actions)));
  }

}
