package cs5004.animator.view;

/**
 * An enum that represents the different types of buttons that can be used in the GUI.
 */
public enum Buttons {

  PLAY("Play"),
  PAUSE("Pause"),
  RESET("Reset"),
  ENABLE_LOOPING("Enable Looping"),
  DISABLE_LOOPING("Disable Looping"),
  SET_SPEED("Set Speed (ticks / second)"),
  SAVE("Save"),
  LOAD("Load");

  private final String name;

  /**
   * Constructs a button with the given name.
   *
   * @param n the name of the button
   */
  Buttons(String n) {
    this.name = n;
  }

  /**
   * Returns the name of the button.
   *
   * @return the name of the button
   */
  public String toString() {
    return this.name;
  }
}
