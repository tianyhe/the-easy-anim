package cs5004.animator.model;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.IShape;
import cs5004.animator.model.shapeutil.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a motion of an animated shape that describes the change in state of the shape between
 * two tick where there is a keyframe at the start tick and a keyframe at the end tick. The motion
 * is responsible for updating the shape at the given tick. The motion is also responsible for
 * calculating the linear interpolation of the properties of the shape between the start and end
 * keyframes.
 */
public class Motion {

  private final Location startLocation;
  private final Location endLocation;
  private final int startWidth;
  private final int endWidth;
  private final int startHeight;
  private final int endHeight;
  private final Color startColor;
  private final Color endColor;
  private final int startTick;
  private final int endTick;

  private final Map<Integer, Color> colors = new HashMap<>();
  private final Map<Integer, Location> locations = new HashMap<>();
  private final Map<Integer, Integer> widths = new HashMap<>();
  private final Map<Integer, Integer> heights = new HashMap<>();


  /**
   * Constructs a motion with the state of the shape at the start tick, the state of the shape at
   * the end tick, and the start and end tick.
   *
   * @param startLocation the location of the shape at the start tick
   * @param endLocation   the location of the shape at the end tick
   * @param startWidth    the width of the shape at the start tick
   * @param endWidth      the width of the shape at the end tick
   * @param startHeight   the height of the shape at the start tick
   * @param endHeight     the height of the shape at the end tick
   * @param startColor    the color of the shape at the start tick
   * @param endColor      the color of the shape at the end tick
   * @param startTick     the start tick of the motion
   * @param endTick       the end tick of the motion
   * @throws IllegalArgumentException if the provided fields are invalid
   */
  public Motion(Location startLocation, Location endLocation, int startWidth, int endWidth,
      int startHeight, int endHeight, Color startColor, Color endColor, int startTick,
      int endTick) {
    if (startLocation == null || endLocation == null || startWidth < 0 || endWidth < 0
        || startHeight < 0 || endHeight < 0 || startColor == null || endColor == null) {
      throw new IllegalArgumentException("Invalid motion fields");
    }
    if (startTick <= 0 || endTick <= 0) {
      throw new IllegalArgumentException("Tick fields must be greater than 0");
    }
    if (endTick < startTick) {
      throw new IllegalArgumentException(
          "End tick must be greater than start tick: " + startTick + " to " + endTick);
    }
    this.startLocation = startLocation;
    this.endLocation = endLocation;
    this.startWidth = startWidth;
    this.endWidth = endWidth;
    this.startHeight = startHeight;
    this.endHeight = endHeight;
    this.startColor = startColor;
    this.endColor = endColor;
    this.startTick = startTick;
    this.endTick = endTick;
    this.setShapeStates();
  }

  /**
   * Constructs a motion with a given starting keyframe and an ending keyframe.
   *
   * @param kf1 the starting keyframe
   * @param kf2 the ending keyframe
   */
  public Motion(Keyframe kf1, Keyframe kf2) {
    this(kf1.getLocation(), kf2.getLocation(), kf1.getWidth(), kf2.getWidth(), kf1.getHeight(),
        kf2.getHeight(), kf1.getColor(), kf2.getColor(), kf1.getTick(), kf2.getTick());
  }

  /**
   * Returns the state of the shape as {@link IShape} object at a specific tick if the motion is
   * applied to the shape.
   *
   * @param s    the shape
   * @param tick the tick
   * @return the state of the shape at the tick
   */
  public IShape getShapeAt(IShape s, int tick) {
    IShape copy = s.copy();
    this.applyTick(copy, tick);
    return copy;
  }

  /**
   * Applies the motion to the shape at a specific tick.
   *
   * @param s    the shape to apply the motion to
   * @param tick the tick to apply the motion to
   */
  public void applyTick(IShape s, int tick) {
    if (tick < this.startTick || tick > this.endTick) {
      s.makeInvisible();
    }
    if (this.startTick == this.endTick) {
      s.setState(endWidth, endHeight, endColor, endLocation);
    } else {
      s.setState(widths.get(tick), heights.get(tick), colors.get(tick), locations.get(tick));
    }
  }

  /**
   * Calculates the state of the shape at each tick between the start and end tick by using linear
   * interpolation. Put the state of the shape at each tick in the corresponding maps. The changes
   * will be applied to the shape based on the tick. Instead of using the provided tweening formula,
   * the change in state is calculated by the proportion of the change by each tick.
   */
  private void setShapeStates() {
    double ticks = this.endTick - this.startTick;
    // Increments per tick:
    double x = (endLocation.getX() - startLocation.getX());
    double y = (endLocation.getY() - startLocation.getY());
    double w = (endWidth - startWidth);
    double h = (endHeight - startHeight);
    double r = (endColor.getR() - startColor.getR());
    double g = (endColor.getG() - startColor.getG());
    double b = (endColor.getB() - startColor.getB());
    // Proportion of change per tick:
    if (ticks != 0) {
      x = x / ticks;
      y = y / ticks;
      w = w / ticks;
      h = h / ticks;
      r = r / ticks;
      g = g / ticks;
      b = b / ticks;
    }
    // Calculate the state of the shape at each tick:
    for (int i = 0; i <= ticks; i++) {
      this.colors.put(startTick + i, new Color((int) Math.round(startColor.getR() + r * i),
          (int) Math.round(startColor.getG() + g * i),
          (int) Math.round(startColor.getB() + b * i)));
      this.locations.put(startTick + i, new Location((int) Math.round(startLocation.getX() + x * i),
          (int) Math.round(startLocation.getY() + y * i)));
      this.widths.put(startTick + i, (int) Math.round(startWidth + w * i));
      this.heights.put(startTick + i, (int) Math.round(startHeight + h * i));
    }
  }

  /**
   * Returns the start location of the shape.
   *
   * @return the start location of the shape
   */
  public Location getStartLocation() {
    return this.startLocation;
  }

  /**
   * Returns the end location of the shape.
   *
   * @return the end location of the shape
   */
  public Location getEndLocation() {
    return this.endLocation;
  }

  /**
   * Returns the start width of the shape.
   *
   * @return the start width of the shape
   */
  public int getStartWidth() {
    return this.startWidth;
  }

  /**
   * Returns the end width of the shape.
   *
   * @return the end width of the shape
   */
  public int getEndWidth() {
    return this.endWidth;
  }

  /**
   * Returns the start height of the shape.
   *
   * @return the start height of the shape
   */
  public int getStartHeight() {
    return this.startHeight;
  }

  /**
   * Returns the end height of the shape.
   *
   * @return the end height of the shape
   */
  public int getEndHeight() {
    return this.endHeight;
  }

  /**
   * Returns the start color of the shape.
   *
   * @return the start color of the shape
   */
  public Color getStartColor() {
    return this.startColor;
  }

  /**
   * Returns the end color of the shape.
   *
   * @return the end color of the shape
   */
  public Color getEndColor() {
    return this.endColor;
  }

  /**
   * Returns the start tick of the motion.
   *
   * @return the start tick of the motion
   */
  public int getStartTick() {
    return this.startTick;
  }

  /**
   * Returns the end tick of the motion.
   *
   * @return the end tick of the motion
   */
  public int getEndTick() {
    return this.endTick;
  }

  /**
   * Determines whether the motion is occurring at a specific tick.
   *
   * @param tick the tick
   * @return true if the motion is occurring at the tick, false otherwise
   */
  public boolean occursAt(int tick) {
    return tick >= this.startTick && tick <= this.endTick;
  }

  /**
   * Determines whether the action has teleported or not.
   *
   * @param m the motion to check
   * @return true or false
   */
  public boolean isTeleportedFrom(Motion m) {
    return this.startTick != m.endTick || !this.startColor.equals(m.getEndColor())
        || this.startWidth != m.getEndWidth() || this.startHeight != m.getEndHeight()
        || !this.startLocation.equals(m.getEndLocation());
  }

  /**
   * Returns the first tick of the motion as {@link Keyframe} object.
   *
   * @return the first frame of the motion
   */
  public Keyframe getStartKeyframe() {
    return new Keyframe(this.startColor, this.startLocation, this.startWidth, this.startHeight,
        this.startTick);
  }

  /**
   * Returns the last tick of the motion as {@link Keyframe} object.
   *
   * @return the last frame of the motion
   */
  public Keyframe getEndKeyframe() {
    return new Keyframe(this.endColor, this.endLocation, this.endWidth, this.endHeight,
        this.endTick);
  }

  /**
   * Returns a string representation of the motion.
   *
   * @return a string representation of the motion
   */
  @Override
  public String toString() {
    return "Tick " + this.getStartTick() + " to tick " + this.getEndTick() + ": "
        + this.startLocation.toString() + " to " + this.endLocation.toString() + ", " + "width "
        + this.startWidth + " to " + this.endWidth + ", height " + this.startHeight + " to "
        + this.endHeight + ", " + this.startColor.toString() + " to " + this.endColor.toString()
        + ".";
  }
}
