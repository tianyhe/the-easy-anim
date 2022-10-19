package cs5004.animator.view;

/**
 * Represents a visual view for an animation. The visual view can display the animation using a
 * GUI. This interface contains methods for displaying the animation.
 */
public interface IVisualView {

  /**
   * Displays the animation.
   */
  void display();

  /**
   * Refreshes the view.
   */
  void refresh();

}
