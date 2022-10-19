package cs5004.animator.model;

import static junit.framework.Assert.fail;

import cs5004.animator.model.shapeutil.Color;
import cs5004.animator.model.shapeutil.Location;
import cs5004.animator.model.shapeutil.Oval;
import cs5004.animator.model.shapeutil.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test class for the AnimatedShape class.
 */
public class AnimatedShapeTest {

  IAnimatedShape oval1;
  IAnimatedShape rectangle1;
  IAnimatedShape oval2;
  IAnimatedShape rectangle2;
  Location o2p1;
  Location o2p2;
  Location o2p3;
  Location r2p1;
  int o2w1;
  int o2w2;
  int o2h1;
  int o2h2;
  int o2h3;
  int o2w3;
  Color o2c1;
  Color o2c2;
  Color o2c3;
  int o2t1;
  int o2t2;
  int o2t3;

  /**
   * Sets up the test.
   */
  @Before
  public void setup() {
    o2p1 = new Location(10, 20);
    o2p2 = new Location(10, 20);
    o2w1 = 1;
    o2w2 = 5;
    o2h1 = 10;
    o2h2 = 20;
    o2c1 = new Color(25, 25, 25);
    o2c2 = new Color(30, 30, 30);
    o2t1 = 1;
    o2t2 = 5;
    o2p3 = new Location(20, 40);
    o2w3 = 5;
    o2h3 = 40;
    o2c3 = new Color(25, 25, 25);
    o2t3 = 6;
    r2p1 = new Location(10, 20);

    List<Keyframe> oval2Keyframes = new ArrayList<>((
        Arrays.asList(
            new Keyframe(o2c1, o2p1, o2w1, o2h1, o2t1),
            new Keyframe(o2c2, o2p2, o2w2, o2h2, o2t2),
            new Keyframe(o2c3, o2p3, o2w3, o2h3, o2t3))));

    List<Motion> oval2Motions = new ArrayList<Motion>(
        Arrays.asList(
            new Motion(o2p1, o2p2, o2w1, o2w2, o2h1, o2h2, o2c1, o2c2, o2t1, o2t2),
            new Motion(o2p2, o2p3, o2w2, o2w3, o2h2, o2h3, o2c2, o2c3, o2t2, o2t3)));

    List<Keyframe> rectangle2Keyframes = new ArrayList<>(
        Arrays.asList(new Keyframe(new Color(25, 25, 25),
                r2p1, 1, 10, 1),
            new Keyframe(new Color(30, 30, 30),
                new Location(10, 20), 5, 20, 5),
            new Keyframe(new Color(25, 25, 25),
                new Location(20, 40), 10, 40, 6),
            new Keyframe(new Color(25, 25, 25),
                new Location(20, 40), 10, 40, 11)));
    List<Motion> rectangle2Motions = new ArrayList<Motion>(
        Arrays.asList(
            new Motion(r2p1,
                new Location(10, 20), 1, 5,
                10, 20,
                new Color(25, 25, 25),
                new Color(30, 30, 30), 1, 5),
            new Motion(new Location(10, 20), new Location(20, 40),
                5, 10, 20, 40,
                new Color(30, 30, 30), new Color(25, 25, 25),
                5, 6),
            new Motion(new Location(20, 40), new Location(20, 40),
                10, 10, 40, 40,
                new Color(25, 25, 25), new Color(25, 25, 25),
                6, 11)));
    oval1 = new AnimatedShape(new Oval("oval1"));
    rectangle1 = new AnimatedShape(new Rectangle("rectangle1"));
    oval2 = new AnimatedShape(new Oval("oval1"), oval2Keyframes);
    rectangle2 = new AnimatedShape(new Rectangle("rectangle1"), rectangle2Keyframes);
  }

  /**
   * Tests for the addMotion method.
   */
  @Test
  public void testAddMotions() {
    try {
      oval2.addMotion(new Motion(o2p2, new Location(20, 10),
          10, 10, 40, 40,
          new Color(25, 25, 25), new Color(25, 25, 25),
          6, 10));
      fail("Start position for oval2 should be invalid.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    oval2.addMotion(new Motion(o2p3, new Location(20, 40),
        o2w3, 10, o2h3, 40,
        o2c3, new Color(25, 25, 25),
        o2t3, 10));
    try {
      rectangle2.addMotion(new Motion(new Location(20, 41),
          new Location(22, 42), 10,
          12, 40, 42,
          new Color(25, 25, 25), new Color(27, 27, 27),
          11, 13));
      fail("Start position should be invalid.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addMotion(new Motion(new Location(20, 40),
          new Location(22, 42), 11, 12,
          40, 42,
          new Color(25, 25, 25), new Color(27, 27, 27),
          11, 13));
      fail("Start width should be invalid.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addMotion(new Motion(
          new Location(20, 40), new Location(22, 42),
          10, 12, 39, 42,
          new Color(25, 25, 25), new Color(27, 27, 27),
          11, 13));
      fail("Start height should be invalid.");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      rectangle2.addMotion(new Motion(new Location(20, 40),
          new Location(22, 42),
          10, 12, 40, 42,
          new Color(25, 26, 25), new Color(27, 27, 27),
          11, 13));
      fail("Start color should be invalid.");
    } catch (IllegalArgumentException e) {
      // Do noting
    }
    rectangle2.addMotion(new Motion(new Location(20, 40), new Location(22, 42),
        10, 12, 40, 42,
        new Color(25, 25, 25), new Color(27, 27, 27),
        11, 13));
  }

}