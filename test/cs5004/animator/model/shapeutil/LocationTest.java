package cs5004.animator.model.shapeutil;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * A JUnit test class for the Location class.
 */
public class LocationTest {

  Location zero = new Location(0, 0);
  Location one = new Location(1, 1);

  /**
   * Tests for the getX method.
   */
  @Test
  public void getXLocation() {
    assertEquals(0, zero.getX(), 0);
    assertEquals(1, one.getX(), 0);
  }

  /**
   * Tests for the getY method.
   */
  @Test
  public void getYLocation() {
    assertEquals(0, zero.getY(), 0);
    assertEquals(1, one.getY(), 0);
  }
}