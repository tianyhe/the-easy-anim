package cs5004.animator.view;

/**
 * An enum that represents the different types of text fields that can be used in the GUI.
 */
public enum TextFields {

  SET_SPEED("Speed (ticks/second): "),
  LOAD_FILE("Load from:"),
  SAVE_FILE("Save to:");

  private final String name;

  /**
   * Constructs a text field with the given name.
   *
   * @param n the name of the text field
   */
  TextFields(String n) {
    this.name = n;
  }

  /**
   * Returns the name of the text field.
   *
   * @return the name of the text field
   */
  public String toString() {
    return this.name;
  }
}
