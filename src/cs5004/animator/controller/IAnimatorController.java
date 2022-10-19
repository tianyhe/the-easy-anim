package cs5004.animator.controller;

import java.awt.event.ActionListener;

/**
 * Represents an interface for an animator controller that responsible for mediating the interaction
 * between the view and the model. The controller provides the user with the ability to play the
 * animation, pause the animation, reset the animation, rewind the animation, change the speed of
 * the animation, and load and save the animation.
 */
public interface IAnimatorController extends ActionListener {

  /**
   * Starts the animation. Displays the view to the user.
   */
  void start();

  /**
   * Pauses the animation.
   */
  void pause();

  /**
   * Sets the speed of the animation.
   *
   * @param ticksPerSecond ticks per second
   */
  void setSpeed(String ticksPerSecond);

  /**
   * Returns the speed of the animation currently.
   *
   * @return the speed
   */
  int getSpeed();

  /**
   * Sets the tick. This is used for rewinding the animation to a specific tick.
   *
   * @param tick the tick
   */
  void setTick(int tick);

  /**
   * Displays an error message to the user when user tries to perform unsupported operation.
   *
   * @param errorMessage the error message
   */
  void displayError(String errorMessage);

  /**
   * Displays a success message to the user when user successfully performs an operation.
   *
   * @param message the success message
   */
  void displaySuccess(String message);

}
