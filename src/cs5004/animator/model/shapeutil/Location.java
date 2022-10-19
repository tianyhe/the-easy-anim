package cs5004.animator.model.shapeutil;

/**
 * A class representing the location data of a shape.
 */
public final class Location {

  private final int x;
  private final int y;

  /**
   * Constructs a new Location with the given x and y coordinates.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the x coordinate of the location.
   *
   * @return the x coordinate of the location
   */
  public int getX() {
    return this.x;
  }

  /**
   * Returns the y coordinate of the location.
   *
   * @return the y coordinate of the location
   */
  public int getY() {
    return this.y;
  }

  /**
   * Determines if this location is equal to the given location.
   *
   * @param that the location to compare to
   * @return true if the locations are equal, false otherwise
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Location)) {
      return false;
    }
    Location l = (Location) that;
    return this.x == l.getX()
            && this.y == l.getY();
  }

  /**
   * Returns a hash code of this location.
   *
   * @return a hash code of this location
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(this.x + this.y);
  }

  /**
   * Returns a string representation of this location.
   *
   * @return a string representation of this location
   */
  @Override
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

}
