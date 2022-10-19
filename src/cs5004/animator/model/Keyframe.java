package cs5004.animator.model;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.Location;

/**
 * Represents a keyframe of a shape in the animation. The keyframe contains the state of the shape
 * at a specific tick in the animation.
 */
public final class Keyframe {

  private final Color color;
  private final Location location;
  private final int width;
  private final int height;
  private final int tick;

  /**
   * Constructs a keyframe object with given fields.
   *
   * @param c    the color of the shape
   * @param l    the location of the shape
   * @param w    the width of the shape
   * @param h    the height of the shape
   * @param tick the tick of the keyframe
   */
  public Keyframe(Color c, Location l, int w, int h, int tick) {
    color = c;
    location = l;
    width = w;
    height = h;
    this.tick = tick;
  }

  /**
   * Returns the tick of the keyframe.
   *
   * @return the tick of the keyframe
   */
  public int getTick() {
    return this.tick;
  }

  /**
   * Returns the color the shape at this keyframe.
   *
   * @return the color of the shape at this keyframe
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Returns the location of the shape at this keyframe.
   *
   * @return the location of the shape at this keyframe
   */
  public Location getLocation() {
    return this.location;
  }

  /**
   * Returns the width of the shape at this keyframe.
   *
   * @return the width of the shape at this keyframe
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Returns the height of the shape at this keyframe.
   *
   * @return the height of the shape at this keyframe
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Returns a string representation of the keyframe.
   *
   * @return a string representation of the keyframe
   */
  @Override
  public String toString() {
    assert this.location != null;
    assert this.color != null;
    return "Tick " + this.tick + ": " + String.join(", ",
        this.location.toString(),
        Integer.toString(this.width),
        Integer.toString(this.height), this.color.toString());
  }

}
