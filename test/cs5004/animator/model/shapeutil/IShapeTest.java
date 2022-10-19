package cs5004.animator.model.shapeutil;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the IShape interface.
 */
public class IShapeTest {

  Location zero;
  Location neg;
  Color red;
  IShape circle;
  IShape rectangle;

  /**
   * Sets up the test.
   */
  @Before
  public void setup() {
    zero = new Location(0, 0);
    neg = new Location(-1, -1);
    red = new Color(255, 0, 0);
    circle = new Oval("circle", 5, 5, red, zero);
    rectangle = new Rectangle("rectangle", 5, 5, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOvalBadColor() {
    Color bad = new Color(256, 256, 256);
    IShape oval = new Oval("oval", 5, 5, bad, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOvalNullId() {
    IShape oval = new Oval(null, 5, 5, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOvalNegHeight() {
    IShape oval = new Oval("oval", -1, 5, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOvalNegWidth() {
    IShape oval = new Oval("oval", 5, -1, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOvalNullColor() {
    IShape oval = new Oval("oval", 5, 5, null, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNegHeight() {
    IShape rect = new Rectangle("rect", -5, 5, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNegWidth() {
    IShape rect = new Rectangle("rect", 5, -5, red, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectBadColor() {
    Color bad = new Color(256, 256, 256);
    IShape rect = new Rectangle("rect", 5, 5, bad, zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNullId() {
    IShape rect = new Rectangle(null, 5, 5, red,
        zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNullHeight() {
    IShape rect = new Rectangle("rect", -1, 5, red,
        zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNullWidth() {
    IShape rect = new Rectangle("rect", 5, -1, red,
        zero);
  }

  /**
   * Tests the constructor for bad inputs.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRectNullColor() {
    IShape rect = new Rectangle("rect", 5, 5, null,
        zero);
  }

  /**
   * Tests the getter methods for the oval.
   */
  @Test
  public void testOvalGetMethods() {
    assertEquals("circle", circle.getId());
    assertEquals(5, circle.getHeight());
    assertEquals(5, circle.getWidth());
    assertEquals(red, circle.getColor());
  }

  /**
   * Tests the getter methods for the rectangle.
   */
  @Test
  public void testRectGetMethods() {
    assertEquals("rectangle", rectangle.getId());
    assertEquals(5, rectangle.getHeight());
    assertEquals(5, rectangle.getWidth());
    assertEquals(red, rectangle.getColor());
  }

  /**
   * Tests to check if the isOval method returns true for an oval and false for a rectangle.
   */
  @Test
  public void testIsOval() {
    assertTrue(circle.isOval());
    assertFalse(rectangle.isOval());
  }

  /**
   * Tests to check if the isRectangle method works.
   */
  @Test
  public void testIsRectangle() {
    assertFalse(circle.isRectangle());
    assertTrue(rectangle.isRectangle());
  }
}