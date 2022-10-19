package cs5004.animator.model;

import cs5004.animator.model.shapeutil.IShape;
import cs5004.animator.view.ITextualView;

import java.util.List;

/**
 * <p>
 * Represents the animation of a shape. The animated shape is described in terms the keyframes that
 * represent the status of the shape at specific moments in time and the motions that describe the
 * changes of the shape throughout the animation. The animated shape is composed of an immutable
 * {@link IShape} object, a list of keyframes {@link Keyframe} and a list of motions {@link Motion}
 * associated with the shape.
 * </p>
 * The list of keyframes {@link Keyframe} represent the state of the shape at a specific tick, The
 * list of motions {@link Motion} represent the change in state of the shape between two keyframes.
 */
public interface IAnimatedShape {

  /**
   * Returns the shape that is animated.
   *
   * @return the shape that is animated
   */
  IShape getShape();

  /**
   * Returns the list of {@link Motion} that should be applied to the shape from start to end of the
   * animation.
   *
   * @return the list of motions
   */
  List<Motion> getMotions();

  /**
   * Adds a new motion to the list of motions.
   *
   * @param motion the motion to add
   * @throws IllegalArgumentException if the motion is invalid
   */
  void addMotion(Motion motion) throws IllegalArgumentException;

  /**
   * Returns the id of the shape.
   *
   * @return the id of the shape
   */
  String getId();

  /**
   * Applies the motion at the given tick to the shape.
   *
   * @param tick the tick to be applied
   */
  void applyTick(int tick);

  /**
   * Returns the shape at the given tick, without actually updating the shape.
   *
   * @param tick the tick to be applied
   * @return the shape at the given tick or an invisible copy of the shape if the shape is not
   *         animated at the given tick
   * @throws IllegalArgumentException if the shape does not have a keyframe at the given tick.
   */
  IShape getShapeAt(int tick) throws IllegalArgumentException;

  /**
   * Returns the string representation of the type of the animated shape.
   *
   * @return the string representation of the type of the animated shape
   */
  String getShapeAsString();

  /**
   * Returns the string representation of the type of the animated shape depending on the view.
   *
   * @param view the view to be used
   * @return the string representation of the type of the animated shape
   */
  String getShapeAsString(ITextualView view);

  /**
   * Return a deep copy of the animated shape.
   *
   * @return a deep copy of the animated shape
   */
  IAnimatedShape copy();

  /**
   * Gets the state of the shape at the tick of it's first action.
   *
   * @return a deep copy of the shape at it's first action tick
   */
  IShape getShapeAtStart();

  /**
   * Returns the last tick of this shape's last motion.
   *
   * @return the last tick of this shape's animation
   * @throws IllegalArgumentException if there are no keyframes for the shape in the animation
   */
  int getLastTick();

  /**
   * Returns the first tick of this shape's first motion.
   *
   * @return the first tick
   * @throws IllegalArgumentException if there are no keyframes for the shape in the animation
   */
  int getFirstTick();

  /**
   * Returns the list of keyframes in this animation.
   *
   * @return the list of keyframes
   */
  List<Keyframe> getKeyframes();

}
