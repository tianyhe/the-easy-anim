package cs5004.animator.view;

/**
 * Represents a textual view for an animation. The textual view will be used for both text views and
 * svg views. This interface contains methods that are common to both text and svg views.
 */
public interface ITextualView {

  /**
   * Retrieves the textual representation of the view.
   *
   * @return the textual representation of the view
   */
  String getText();

  /**
   * Retrieves the textual representation of the type of oval.
   *
   * @return the textual representation of the type of oval.
   */
  String getOvalAsString();

  /**
   * Retrieves the textual representation of the type of rectangle.
   *
   * @return the textual representation of the type of rectangle.
   */
  String getRectangleAsString();

  /**
   * Writes  the textual representation of the animation into a file.
   */
  void write();
}
