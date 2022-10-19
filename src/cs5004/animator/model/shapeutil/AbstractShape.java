package cs5004.animator.model.shapeutil;

import cs5004.animator.model.Keyframe;

/**
 * This class is an abstract class that is used to represent a shape in the animation. This class
 * contains fields and methods that are common to all shapes.
 */
public abstract class AbstractShape implements IShape {

  protected final String id;
  protected int width;
  protected int height;
  protected Color color;
  protected Location location;
  protected boolean isVisible;

  /**
   * Base constructor for an abstract shape object.
   *
   * @param id the id of the shape
   */
  public AbstractShape(String id) {
    this.id = id;
    this.makeInvisible();
  }

  /**
   * Constructor for an abstract shape object.
   *
   * @param id       the id of the shape
   * @param width    the width of the shape
   * @param height   the height of the shape
   * @param color    the color of the shape
   * @param location the location of the shape
   * @throws IllegalArgumentException if the width or height are negative or zero
   * @throws IllegalArgumentException if fields are not properly initialized
   */
  public AbstractShape(String id, int width, int height, Color color, Location location) {
    if (id == null || height < 0 || width < 0 || color == null || location == null) {
      throw new IllegalArgumentException("Fields must not be initialized to null or negative");
    }
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Width and height must be positive");
    }
    this.id = id;
    this.width = width;
    this.height = height;
    this.color = color;
    this.isVisible = true;
    this.location = location;
  }

  @Override
  public String getId() {
    return this.id;
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
  public Color getColor() {
    return this.color;
  }

  @Override
  public Location getLocation() {
    return this.location;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public boolean isInvisible() {
    return !this.isVisible;
  }

  @Override
  public void makeInvisible() {
    this.isVisible = false;
    this.width = -1;
    this.height = -1;
    this.color = null;
    this.location = null;
  }

  @Override
  public void setState(int width, int height, Color color, Location location) {
    this.setWidth(width);
    this.setHeight(height);
    this.setColor(color);
    this.setLocation(location);
    this.isVisible = true;
  }

  @Override
  public void applyKeyframe(Keyframe kf) {
    this.setState(kf.getWidth(), kf.getHeight(), kf.getColor(), kf.getLocation());
    this.isVisible = kf.getWidth() != -1;
  }

  /**
   * Determines if this shape is equal to another shape.
   *
   * @param that the other shape to compare to
   * @return true if the shapes are equal, false otherwise
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof AbstractShape)) {
      return false;
    }
    AbstractShape s = (AbstractShape) that;
    if (!this.isVisible) {
      return this.id.equals(s.id) && !s.isVisible;
    }
    return s.isVisible && this.location.equals(s.location)
        && this.id.equals(s.id) && this.color.equals(s.color)
        && this.width == s.width
        && this.height == s.height
        && this.getShapeAsString().equals(s.getShapeAsString());
  }

  /**
   * Returns a hash code for this shape.
   *
   * @return a hash code for this shape
   */
  @Override
  public int hashCode() {
    if (!this.isVisible) {
      return this.id.hashCode();
    }
    return Integer.hashCode(this.height + this.width)
        + id.hashCode() + getShapeAsString().hashCode()
        + this.location.hashCode();
  }

  /**
   * Returns a string representation of this shape.
   *
   * @return a string representation of this shape
   */
  @Override
  public String toString() {
    if (!isVisible) {
      return "Invisible " + this.getShapeAsString() + " \'" + this.id + "\'";
    }
    return this.getShapeAsString() + " \'" + this.id + "\': width = "
        + this.width + ", height = " + this.height
        + ", color = " + this.color.toString() + ", location = " + this.location.toString()
        + ", visible: " + this.isVisible;
  }

}
