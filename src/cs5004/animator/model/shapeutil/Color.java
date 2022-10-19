package cs5004.animator.model.shapeutil;

/**
 * A class representing the color data of a shape.
 */
public final class Color {
  private final int r;
  private final int g;
  private final int b;

  /**
   * Constructs a new Color with the given RGB values.
   *
   * @param r red value
   * @param g green value
   * @param b blue value
   */
  public Color(int r, int g, int b) {
    if (r > 255 || g > 255 || b > 255 || r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("Color values must be between 0 and 255");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Returns the red value of the color.
   *
   * @return the red value of the color
   */
  public int getR() {
    return this.r;
  }

  /**
   * Returns the green value of the color.
   *
   * @return the green value of the color
   */
  public int getG() {
    return this.g;
  }

  /**
   * Return the blue value of the color.
   *
   * @return the blue value of the color
   */
  public int getB() {
    return this.b;
  }

  /**
   * Determines if this color is equal to the given color.
   *
   * @param that the color to compare to
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Color)) {
      return false;
    }
    Color c = (Color) that;
    return c.getG() == this.getG() && c.getR() == this.getR() && c.getB() == this.getB();
  }

  /**
   * Returns a hash code of this color.
   *
   * @return a hash code of this color
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(this.g + this.b + this.r);
  }

  /**
   * Returns a string representation of this color.
   *
   * @return a string representation of this color
   */
  @Override
  public String toString() {
    return "rgb(" + this.r + "," + this.g + "," + this.b + ")";
  }
}
