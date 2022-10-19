package cs5004.animator.model;

import static junit.framework.TestCase.assertEquals;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.Location;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the Motion class.
 */
public class MotionTest {

  Motion motion1;

  /**
   * Sets up the test.
   */
  @Before
  public void setup() {
    motion1 = new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), 1, 10);
  }

  /**
   * Tests for the getter methods for the motion.
   */
  @Test
  public void testMotionGetMethods() {
    assertEquals(new Location(100, 100), motion1.getStartLocation());
    assertEquals(new Location(100, 100), motion1.getEndLocation());
    assertEquals(5, motion1.getStartWidth());
    assertEquals(6, motion1.getEndWidth());
    assertEquals(5, motion1.getStartHeight());
    assertEquals(5, motion1.getEndHeight());
    assertEquals(new Color(0, 0, 255), motion1.getStartColor());
    assertEquals(new Color(0, 0, 255), motion1.getEndColor());
    assertEquals(1, motion1.getStartTick());
    assertEquals(10, motion1.getEndTick());
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEndTickSmallerThanStartTick() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), 5, 4);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartTick() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndTick() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), 1, -1);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullStartLocation() {
    new Motion(null, new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullEndLocation() {
    new Motion(new Location(100, 100), null, 5, 6,
        5, 5, new Color(0, 0, 255),
        new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartWidth() {
    new Motion(new Location(100, 100), new Location(100, 100),
        -1, 6, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndWidth() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, -1, 5, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeStartHeight() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, -1, 5,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeEndHeight() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, -1,
        new Color(0, 0, 255), new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullStartColor() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        null, new Color(0, 0, 255), -1, 5);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullEndColor() {
    new Motion(new Location(100, 100), new Location(100, 100),
        5, 6, 5, 5,
        new Color(0, 0, 255), null, -1, 5);
  }
}