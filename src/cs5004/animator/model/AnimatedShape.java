package cs5004.animator.model;

import cs5004.animator.model.shapeutil.IShape;
import cs5004.animator.view.ITextualView;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an animated shape in the animation. The animated shapes contains the {@link IShape}
 * object, a list of {@link Keyframe} objects that describe the state of the shape at specific
 * moments in time, and a list of {@link Motion} objects that describe the exact sequence of the
 * shape's changes throughout the animation.
 */
public class AnimatedShape implements IAnimatedShape {

  private final IShape shape;
  private final List<Motion> motions = new ArrayList<>();
  private final List<Keyframe> keyframes = new ArrayList<>();

  /**
   * Constructs an animated shape with the given shape and list of keyframes. Add the list of
   * motions that are associated with the keyframes.
   *
   * @param s         the shape object
   * @param keyframes the list of keyframes
   */
  public AnimatedShape(IShape s, List<Keyframe> keyframes) {
    this.shape = s.copy();
    Keyframe prev = null;

    for (Keyframe kf : keyframes) {
      this.keyframes.add(kf);
      if (keyframes.indexOf(kf) > 0) {
        assert prev != null;
        this.motions.add(new Motion(prev, kf));
        prev = kf;
      } else {
        prev = keyframes.get(0);
      }
    }
  }

  /**
   * Construct an animated shape with the given shape without any events or motions given.
   *
   * @param s the shape to be animated
   */
  public AnimatedShape(IShape s) {
    this.shape = s.copy();
  }

  @Override
  public IShape getShape() {
    return this.shape.copy();
  }

  @Override
  public List<Motion> getMotions() {
    return this.motions;
  }

  @Override
  public void addMotion(Motion motion) throws IllegalArgumentException {
    // check if motion is valid before adding
    if (motion == null) {
      throw new IllegalArgumentException("Motion is NULL");
    }
    if (!this.motions.isEmpty() && motion.isTeleportedFrom(this.getLastMotion())) {
      System.out.println(this);
      System.out.println(motion);
      throw new IllegalArgumentException("Invalid Motion: Teleportation.");
    }
    this.motions.add(motion);
    if (this.keyframes.isEmpty()) {
      this.keyframes.add(motion.getStartKeyframe());
    }
    this.keyframes.add(motion.getEndKeyframe());
  }

  /**
   * Returns the last motion in this shape's motion list.
   *
   * @return the last motion in this shape's action list
   */
  private Motion getLastMotion() {
    if (this.motions.isEmpty()) {
      throw new IllegalArgumentException("No motions in this shape.");
    }
    return this.motions.get(this.motions.size() - 1);
  }

  @Override
  public String getId() {
    return this.shape.getId();
  }

  @Override
  public void applyTick(int tick) {
    try {
      this.getMotionAt(tick).applyTick(this.shape, tick);
    } catch (IllegalArgumentException e) {
      this.shape.makeInvisible();
    }
  }

  /**
   * Returns the motion that is applied to the shape at the given tick.
   *
   * @param tick the tick to be applied
   * @return the motion that is applied to the shape at the given tick
   */
  private Motion getMotionAt(int tick) {
    for (Motion motion : this.motions) {
      if (motion.occursAt(tick)) {
        return motion;
      }
    }
    throw new IllegalArgumentException("No motion occurs at tick: " + tick);
  }

  @Override
  public IShape getShapeAt(int tick) {
    IShape copy = this.shape.copy();
    try {
      for (Keyframe kf : this.keyframes) {
        if (kf.getTick() == tick) {
          IShape clone = shape.copy();
          clone.applyKeyframe(kf);
          return clone;
        }
      }
      return this.getMotionAt(tick).getShapeAt(this.shape, tick);
    } catch (IllegalArgumentException e) {
      copy.makeInvisible();
      return copy;
    }
  }

  @Override
  public String getShapeAsString() {
    return this.shape.getShapeAsString();
  }

  @Override
  public String getShapeAsString(ITextualView view) {
    return this.shape.getShapeAsString(view);
  }

  @Override
  public IAnimatedShape copy() {
    return new AnimatedShape(this.shape.copy(), this.keyframes);
  }

  @Override
  public IShape getShapeAtStart() {
    if (this.motions.isEmpty()) {
      IShape copy = this.shape.copy();
      copy.makeInvisible();
      return copy;
    }
    int tickOfFirstMotion = motions.get(0).getStartTick();
    return getShapeAt(tickOfFirstMotion).copy();
  }

  @Override
  public int getLastTick() {
    if (this.motions.isEmpty()) {
      return 1;
    }
    return this.motions.get(this.motions.size() - 1).getEndTick();
  }

  @Override
  public int getFirstTick() {
    if (this.keyframes.isEmpty()) {
      throw new IllegalArgumentException("This shape has no keyframes.");
    }
    return this.keyframes.get(0).getTick();
  }

  @Override
  public List<Keyframe> getKeyframes() {
    return this.keyframes;
  }

  /**
   * Returns the string representation of the location change of the given motion.
   *
   * @param m the motion to be represented
   * @return the string representation of the position change
   */
  private String getLocationChangeString(Motion m) {
    if (m.getStartLocation().equals(m.getEndLocation())) {
      return "stays put at " + m.getStartLocation().toString();
    }
    return "moves from " + m.getStartLocation().toString() + " to " + m.getEndLocation().toString();
  }

  /**
   * Returns the string representation of the size change of the given motion.
   *
   * @param m the motion to be represented
   * @return the string representation of the size change
   */
  private String getSizeChangeString(Motion m) {
    if (m.getStartWidth() == m.getEndWidth()
        && m.getStartHeight() == m.getEndHeight()) {
      return "stays size " + this.getSizeAsString(true, m);
    }
    if (m.getStartWidth() * m.getStartHeight() < m.getEndHeight() * m.getEndWidth()) {
      return "grows from " + this.getSizeAsString(true, m)
          + " to " + this.getSizeAsString(false, m);
    }
    if (m.getStartWidth() * m.getStartHeight() > m.getEndHeight() * m.getEndWidth()) {
      return "shrinks from " + this.getSizeAsString(true, m)
          + " to " + this.getSizeAsString(false, m);
    }
    return "changes from size " + this.getSizeAsString(true, m)
        + " to " + this.getSizeAsString(false, m);
  }

  /**
   * Returns the string representation of the given size from the given motion.
   *
   * @param isStartSize if the start size is the size to be represented (otherwise the end size)
   * @param m           the motion whose size is to be represented
   * @return the string representation of the given size
   */
  private String getSizeAsString(boolean isStartSize, Motion m) {
    if (isStartSize) {
      return m.getStartWidth() + "x" + m.getStartHeight();
    } else {
      return m.getEndWidth() + "x" + m.getEndHeight();
    }
  }

  /**
   * Returns the string representation of the color change of the given motion.
   *
   * @param m the motion to be represented
   * @return the string representation of the color change
   */
  private String getColorChangeString(Motion m) {
    if (m.getStartColor().equals(m.getEndColor())) {
      return "stays " + m.getStartColor().toString();
    }
    return "turns " + m.getEndColor().toString();
  }

  /**
   * Returns the string representation of the tick change of the given action.
   *
   * @param m the action to be represented
   * @return the string representation of the tick change
   */
  private String getTickChange(Motion m) {
    return "From time " + m.getStartTick() + " to " + m.getEndTick();
  }

  @Override
  public String toString() {
    List<String> actionsAsString = new ArrayList<>(this.motions.size());
    for (Motion m : this.motions) {
      actionsAsString.add(getTickChange(m) + ", " + this.shape.getId() + " "
          + this.getLocationChangeString(m) + ", " + this.getSizeChangeString(m) + ", and "
          + this.getColorChangeString(m) + ".");
    }
    return String.join("\n", actionsAsString);
  }

}
