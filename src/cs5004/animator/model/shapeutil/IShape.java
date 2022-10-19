package cs5004.animator.model.shapeutil;

import cs5004.animator.model.Keyframe;
import cs5004.animator.view.ITextualView;

/**
 * Represents a 2D Shape in the animation. This interface contains all operations that all types of
 * shapes should support.
 */
public interface IShape {

  /**
   * Returns the name of the shape.
   *
   * @return the id
   */
  String getId();

  /**
   * Returns the height of the shape.
   *
   * @return the height
   */
  int getHeight();

  /**
   * Returns the width of the shape.
   *
   * @return the width
   */
  int getWidth();

  /**
   * Returns the color of the shape.
   *
   * @return the color
   */
  Color getColor();

  /**
   * Returns the location of the shape.
   *
   * @return the location of the shape
   */
  Location getLocation();

  /**
   * Set the width of the shape.
   *
   * @param width the new width of the shape
   */
  void setWidth(int width);

  /**
   * Set the height of the shape.
   *
   * @param height the new height of the shape
   */
  void setHeight(int height);

  /**
   * Set the color of the shape.
   *
   * @param color the new color of the shape
   */
  void setColor(Color color);

  /**
   * Set the location of the shape.
   *
   * @param location the new location of the shape
   */
  void setLocation(Location location);

  /**
   * Make a copy of the shape.
   *
   * @return the copy Shape
   */
  IShape copy();

  /**
   * Returns whether this shape is currently invisible.
   *
   * @return true if this shape is currently invisible (has not yet appeared), false otherwise
   */
  boolean isInvisible();

  /**
   * Returns whether this shape is an oval.
   *
   * @return true if this shape is an oval, false otherwise
   */
  boolean isOval();

  /**
   * Returns whether this shape is a rectangle.
   *
   * @return true if this shape is a rectangle, false otherwise
   */
  boolean isRectangle();

  /**
   * Returns the string representation of this type of shape.
   *
   * @return the string representation of this type of shape
   */
  String getShapeAsString();

  /**
   * Returns the string representation of the type of shape, depending on the view.
   *
   * @return the string representation of the type of shape
   */
  String getShapeAsString(ITextualView view);

  /**
   * Make the shape visible.
   */
  void makeInvisible();

  /**
   * Set the state of the shape.
   *
   * @param width    the new width of the shape
   * @param height   the new height of the shape
   * @param color    the new color of the shape
   * @param location the new location of the shape
   */
  void setState(int width, int height, Color color, Location location);

  /**
   * Apply a keyframe to the shape.
   *
   * @param kf the keyframe to apply
   */
  void applyKeyframe(Keyframe kf);

}
