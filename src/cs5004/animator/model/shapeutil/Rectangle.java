package cs5004.animator.model.shapeutil;

import cs5004.animator.view.ITextualView;

/**
 * This class represents a rectangle. It defines all the operations mandated by the IShape
 * interface.
 */
public class Rectangle extends AbstractShape {

  /**
   * Constructs an invisible rectangle object with the given name.
   *
   * @param id the name of the shape
   */
  public Rectangle(String id) {
    super(id);
  }

  /**
   * Constructs a visible rectangle object with the given fields.
   *
   * @param id       the name of the shape
   * @param width    the width of the shape
   * @param height   the height of the shape
   * @param color    the color of the shape
   * @param location the location of the shape
   */
  public Rectangle(String id, int width, int height, Color color, Location location) {
    super(id, width, height, color, location);
  }

  @Override
  public IShape copy() {
    if (!this.isVisible) {
      return new Rectangle(this.id);
    }
    return new Rectangle(this.id, this.width, this.height, this.color, this.location);
  }

  @Override
  public boolean isOval() {
    return false;
  }

  @Override
  public boolean isRectangle() {
    return true;
  }

  @Override
  public String getShapeAsString() {
    return "Rectangle";
  }

  @Override
  public String getShapeAsString(ITextualView view) {
    return view.getRectangleAsString();
  }

}
