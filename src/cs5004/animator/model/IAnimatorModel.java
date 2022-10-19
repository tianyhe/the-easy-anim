package cs5004.animator.model;

import cs5004.animator.model.shapeutil.IShape;

import java.util.List;

/**
 * Represents the model of an animator to represents an animation. The animation is described in
 * terms of what should happen when according to a timeline when the animation plays. The model
 * represents the animation with a list of animated objects {@link IAnimatedShape} in the animation.
 * The animator model is responsible for creating the list of animated objects and applying the
 * motions to the shapes. It also keeps track of all the shapes in the animation as well as the
 * canvas size.
 */

public interface IAnimatorModel {

  /**
   * Adds the given {@link Motion} to the corresponding shape.
   *
   * @param id     id of the shape
   * @param motion the motion to add
   */
  void addMotion(String id, Motion motion);

  /**
   * Adds the given shape to the model.
   *
   * @param shape the shape to add to the model
   */
  void addShape(IShape shape);

  /**
   * Applies all the motions at the given tick to the shapes.
   *
   * @param tick the tick to be applied.
   */
  void applyTick(int tick);

  /**
   * Adds an animated shape to the model.
   *
   * @param animatedShape the animated shape to be added to the model
   */
  void addAnimatedShape(IAnimatedShape animatedShape);

  /**
   * Deletes all animated shapes from the model.
   */
  void clear();

  /**
   * Returns a deep copy of the list of shapes to be animated in the model.
   *
   * @return a deep copy of the list of shapes to be animated in the model
   */
  List<IShape> getShapes();

  /**
   * Returns a deep copy of the animated shape by the given id as a @{@link IShape} object.
   *
   * @param id the id of the shape to be retrieved
   * @return a deep copy of the animated shape by the given id as a @{@link IShape} object
   */
  IShape getShape(String id);

  /**
   * Returns the animated shape with the given id as a @{@link IShape} object at the given tick.
   *
   * @param id   the id of the shape to be retrieved
   * @param tick the tick at which the shape should be retrieved
   * @return the animated shape with the given id as a @{@link IShape} object at the given tick
   */
  IShape getShapeAt(String id, int tick);

  /**
   * Returns the list of animated shapes as a list of @{@link IShape} objects at the given tick.
   *
   * @param tick the tick
   * @return the list of animated shapes as a list of @{@link IShape} objects at the given tick
   */
  List<IShape> getShapesAt(int tick);

  /**
   * Returns the list of animated shapes in the model.
   *
   * @return the list of animated shapes in the model
   */
  List<IAnimatedShape> getAnimatedShapes();

  /**
   * Returns the last tick of this animation.
   *
   * @return the last tick of this animation
   */
  int getLastTick();

  /**
   * Returns the current tick of a running animation.
   *
   * @return the current tick of a running animation
   */
  int getTick();

  /**
   * Returns the canvas width.
   *
   * @return the canvas width
   */
  int getCanvasWidth();

  /**
   * Returns the canvas height.
   *
   * @return the canvas height
   */
  int getCanvasHeight();

  /**
   * Returns the canvas starting x position.
   *
   * @return the canvas starting x position
   */
  int getCanvasStartingX();

  /**
   * Returns the canvas starting y position.
   *
   * @return the canvas starting y position
   */
  int getCanvasStartingY();

}
