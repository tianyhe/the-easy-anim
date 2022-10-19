package cs5004.animator.view;

import cs5004.animator.controller.IAnimatorController;

/**
 * Represents an interface for an interactive view. This interface contains methods for displaying
 * the animation and allowing the interaction with the user.
 */
public interface IInteractiveView extends IVisualView {

  /**
   * Sets listeners for button, text field, and check box components in the view.
   */
  void setListener(IAnimatorController listener);

  /**
   * Returns the file path of the file that the user wants to load the animation from.
   *
   * @return the file path of the file that the user wants to load the animation from
   */
  String getLoadFilePath();

  /**
   * Returns the file path of the file that the user wants to save the animation to.
   *
   * @return the file path of the file that the user wants to save the animation to
   */
  String getSaveFilePath();

  /**
   * Refreshes the animation panel only (not the control panel). This is used for playing the
   * animation.
   */
  void refreshAnimation();
}
