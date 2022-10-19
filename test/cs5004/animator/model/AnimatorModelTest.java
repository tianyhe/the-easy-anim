package cs5004.animator.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.IShape;
import cs5004.animator.model.shapeutil.Location;
import cs5004.animator.model.shapeutil.Oval;
import cs5004.animator.model.shapeutil.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the AnimatedModel class.
 */
public class AnimatorModelTest {

  private AnimatorModel model;
  private String oval1id = "oval1";
  private String rect1id = "rect1";
  private IShape oval1;
  private IShape rect1;
  private Location o1p1;
  private Location o1p3;
  private int o1w1;
  private int o1h1;
  private int o1h3;
  private int o1w3;
  private Color o1c1;
  private Color o1c3;
  private Location r1p2;
  private Location r1p3;
  private int r1w2;
  private int r1w3;
  private int r1h2;
  private int r1h3;
  private Color r1c2;
  private Color r1c3;
  int r1t1;
  int r1t2;
  int r1t3;

  /**
   * Sets up the test.
   */
  @Before
  public void setup() {
    this.model = new AnimatorModel();
    o1p1 = new Location(10, 20);
    Location o1p2 = new Location(10, 20);
    o1w1 = 1;
    int o1w2 = 5;
    o1h1 = 10;
    int o1h2 = 20;
    o1c1 = new Color(25, 25, 25);
    Color o1c2 = new Color(30, 30, 30);
    int o1t1 = 1;
    int o1t2 = 5;
    o1p3 = new Location(20, 40);
    o1w3 = 5;
    o1h3 = 40;
    o1c3 = new Color(25, 25, 25);
    int o1t3 = 6;
    oval1 = new Oval(oval1id);
    rect1 = new Rectangle(rect1id);
    Location r1p1 = new Location(10, 20);
    r1p2 = new Location(10, 20);
    r1p3 = new Location(15, 25);
    int r1w1 = 1;
    r1w2 = 5;
    r1w3 = 10;
    int r1h1 = 10;
    r1h2 = 20;
    r1h3 = 25;
    Color r1c1 = new Color(25, 25, 25);
    r1c2 = new Color(30, 30, 30);
    r1c3 = new Color(35, 35, 35);
    r1t1 = 1;
    r1t2 = 5;
    r1t3 = 10;
    oval1 = new Oval(oval1id);
    rect1 = new Rectangle(rect1id);
    this.model.addShape(oval1);
    this.model.addShape(rect1);
    this.model.addMotion(oval1id, new Motion(o1p1, o1p2, o1w1, o1w2, o1h1, o1h2,
        o1c1, o1c2, o1t1, o1t2));
    this.model.addMotion(oval1id,
        new Motion(o1p2, o1p3, o1w2, o1w3, o1h2, o1h3, o1c2, o1c3, o1t2, o1t3));
    this.model.addMotion(rect1id, new Motion(r1p1, r1p2, r1w1, r1w2, r1h1, r1h2,
        r1c1, r1c2, r1t1, r1t2));
    this.model.addMotion(rect1id,
        new Motion(r1p2, r1p3, r1w2, r1w3, r1h2, r1h3, r1c2, r1c3, r1t2, r1t3));
  }

  /**
   * Tests for the addMotion method.
   */
  @Test
  public void testAddMotions() {
    this.model.addMotion(oval1id, new Motion(new Location(20, 40),
        new Location(25, 45),
        5, 10, 40, 45,
        new Color(25, 25, 25), new Color(30, 30, 30),
        6, 11));
    IShape s = this.model.getShapeAt(oval1id, 7);
    assertEquals(s.getId(), oval1id);

    // Tests increments of motions and equality of position/color.
    assertEquals(s.getColor(), new Color(26, 26, 26));
    assertEquals(s.getHeight(), 41);
    assertEquals(s.getWidth(), 6);
    assertEquals(s.getLocation(), new Location(21, 41));
    assertFalse(s.isInvisible());
    this.model.getShapes().stream().forEach(shape -> {
      if (!shape.isInvisible()) {
        fail("Shapes should be invisible.");
      }
    });
    // Testing invalid motion adds
    try {
      this.model.addMotion(rect1id, new Motion(r1p2, o1p1, r1w3, o1w1,
          r1h3, o1w1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Location teleportation detection fail.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addMotion(rect1id, new Motion(r1p3, o1p1, r1w2, o1w1,
          r1h3, o1h1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Width teleportation detection fail.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addMotion(rect1id, new Motion(r1p3, o1p1, r1w3, o1w1,
          r1h2, o1w1, r1c3, o1c1, r1t3, r1t3 + 1));
      fail("Height teleportation detection fail.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addMotion(rect1id, new Motion(r1p3, o1p1, r1w3, o1w1,
          r1h3, o1h1, r1c2, o1c1, r1t3, r1t3 + 1));
      fail("Color teleportation detection fail.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      this.model.addMotion(rect1id, new Motion(r1p3, o1p1, r1w3, o1w1,
          r1h3, o1h1, r1c3, o1c1, r1t2, r1t3 + 1));
      fail("Tick teleportation detection fail.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    // Adding valid motion to rectangle
    this.model.addMotion(rect1id, new Motion(r1p3, new Location(r1p3.getX() + 5,
        r1p3.getY() + 5), r1w3, r1w3 + 5,
        r1h3, r1h3 + 5, r1c3, new Color(r1c3.getR(), r1c3.getG(), r1c3.getB()),
        r1t3, r1t3 + 5));
    List<IShape> shapes = this.model.getShapes();

    // Ticks not applied; shapes should be invisible
    for (int i = 0; i < shapes.size(); i++) {
      assertTrue(shapes.get(i).isInvisible());
    }
    this.model.applyTick(6);
    shapes = this.model.getShapesAt(6);
    assertEquals(this.model.getShape(rect1id), shapes.get(1));
    assertEquals(new Oval(oval1id, o1w3, o1h3, o1c3, o1p3), this.model.getShape(oval1id));
    this.model.applyTick(7);
    assertEquals(new Rectangle(rect1id, r1w2 + 2, r1h2 + 2,
            new Color(32, 32, 32), new Location(12, 22)),
        this.model.getShape(rect1id));
    this.model.applyTick(11);
    assertEquals(new Oval(oval1id, 10, 45, new Color(30, 30, 30),
        new Location(25, 45)), this.model.getShape(oval1id));
    this.model.applyTick(15);
    assertFalse(this.model.getShape(rect1id).isInvisible());
    assertTrue(this.model.getShape(oval1id).isInvisible());
  }

  /**
   * Tests for the getShape method.
   */
  @Test
  public void testGetShapes() {
    List<IShape> shapes = new ArrayList(Arrays.asList(oval1, rect1));
    for (int i = 0; i < shapes.size(); i++) {
      assertEquals(model.getShapes().get(i), shapes.get(i));
    }
  }

}