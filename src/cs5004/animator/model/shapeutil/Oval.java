package cs5004.animator.model.shapeutil;

import cs5004.animator.view.ITextualView;

/**
 * This class represents an oval. It defines all the operations mandated by the IShape interface.
 */
public class Oval extends AbstractShape {

  /**
   * Constructs an invisible oval object with the given name.
   *
   * @param id the name of the shape
   */
  public Oval(String id) {
    super(id);
  }

  /**
   * Constructs a visible oval object with the given fields.
   *
   * @param id       the name of the shape
   * @param width    the width of the shape
   * @param height   the height of the shape
   * @param color    the color of the shape
   * @param location the location of the shape
   */
  public Oval(String id, int width, int height, Color color, Location location) {
    super(id, width, height, color, location);
  }

  @Override
  public IShape copy() {
    if (!this.isVisible) {
      return new Oval(this.id);
    }
    return new Oval(this.id, this.width, this.height, this.color, this.location);
  }

  @Override
  public boolean isOval() {
    return true;
  }

  @Override
  public boolean isRectangle() {
    return false;
  }

  @Override
  public String getShapeAsString() {
    return "Oval";
  }

  @Override
  public String getShapeAsString(ITextualView view) {
    return view.getOvalAsString();
  }

}
